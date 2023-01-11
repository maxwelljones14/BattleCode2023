package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

public class Robot {
    static RobotController rc;
    static int turnCount;
    static int roundNum;
    static RobotType robotType;

    static MapLocation home;
    static MapLocation[] headquarterLocations;
    static RobotInfo[] EnemySensable;
    static RobotInfo[] FriendlySensable;
    static MapLocation currLoc;

    static int actionRadiusSquared;
    static int visionRadiusSquared;

    static RobotInfo maybeAmplifier = null;
    static RobotInfo maybeBooster = null;
    static RobotInfo maybeCarrier = null;
    static RobotInfo maybeDestabilizer = null;
    static RobotInfo maybeLauncher = null;
    static RobotInfo maybeHeadquarters = null;

    static Team team;
    static Team opponent;

    static SectorDatabase sectorDatabase;

    // Sectors are 6x6, 5x6, 6x5, or 5x5
    int numSectors;
    int[] sectorHeights;
    int[] sectorWidths;
    int sectorWidthsLength;
    float xStep;
    float yStep;
    int[] whichXLoc;
    int[] whichYLoc;
    MapLocation[] sectorCenters;
    int[] sectorResources;
    int[] sectorControls;
    int[] markedSectorsBuffer;
    int[] sectorPermutation;
    int sectorToReport;

    boolean exploreMode;

    final int MIN_BC_TO_FLUSH_SECTOR_DB = 1500;
    final int BC_TO_WRITE_SECTOR = 150;
    final int MIN_BC_TO_FLUSH = 1200;

    public Robot(RobotController r) throws GameActionException {
        rc = r;
        turnCount = 0;
        robotType = rc.getType();
        actionRadiusSquared = robotType.actionRadiusSquared;
        visionRadiusSquared = robotType.visionRadiusSquared;
        team = rc.getTeam();
        opponent = team.opponent();

        if (robotType == RobotType.HEADQUARTERS) {
            home = rc.getLocation();
        } else {
            RobotInfo[] sensableWithinVisionRad = rc.senseNearbyRobots(visionRadiusSquared, rc.getTeam());
            for (RobotInfo robot : sensableWithinVisionRad) {
                if (robot.getType() == RobotType.HEADQUARTERS) {
                    MapLocation robotLoc = robot.getLocation();
                    home = robotLoc;
                }
            }
            loadArchonLocations();
        }

        if (home == null) {
            home = rc.getLocation();
        }
        setupSectors();
        precomputeSectorCenters();
        sectorResources = new int[numSectors];
        sectorControls = new int[numSectors];
        markedSectorsBuffer = new int[numSectors];
        sectorToReport = 0;

        sectorDatabase = new SectorDatabase(numSectors);

        // Precompute math for whichSector
        whichXLoc = new int[Util.MAP_WIDTH];
        whichYLoc = new int[Util.MAP_HEIGHT];
        for (int i = 0; i < Util.MAP_WIDTH; i++) {
            whichXLoc[i] = (int) (i / xStep);
        }
        for (int i = 0; i < Util.MAP_HEIGHT; i++) {
            whichYLoc[i] = (int) (i / yStep) * sectorWidths.length;
        }

        exploreMode = false;
    }

    public void loadArchonLocations() throws GameActionException {
        // headquarterLocations = Comms.getFriendlyHeadquarterLocations();
    }

    public void initTurn() throws GameActionException {
        Pathfinding.initTurn();
    }

    public void takeTurn() throws GameActionException {
        turnCount += 1;
        roundNum = rc.getRoundNum();
        EnemySensable = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        FriendlySensable = rc.senseNearbyRobots(-1, rc.getTeam());
        currLoc = rc.getLocation();
        int sector = whichSector(currLoc);
        if (!sectorDatabase.at(sector).hasReports()) {
            sectorDatabase.at(sector).exploreSector();
        }
        setSectorStates();
    }

    public void endTurn() throws GameActionException {
        Explore.initialize();

        if (Clock.getBytecodesLeft() < MIN_BC_TO_FLUSH_SECTOR_DB)
            return;

        switch (robotType) {
            case HEADQUARTERS:
                return;
            default:
                if (rc.canWriteSharedArray(0, 0)) {
                    Comms.initBufferPool();
                    int numSectorsReported = 0;
                    final int MAX_SECTORS_REPORTED = 10;

                    while (sectorToReport < numSectors &&
                            numSectorsReported < MAX_SECTORS_REPORTED &&
                            Clock.getBytecodesLeft() > numSectorsReported * BC_TO_WRITE_SECTOR + MIN_BC_TO_FLUSH) {
                        SectorInfo entry = sectorDatabase.at(sectorToReport);
                        if (entry.hasReports()) {
                            int hasAdamWell = (entry.shouldUnsetAdamWells() ? 0 : 1)
                                    & (Comms.readSectorAdamantiumFlag(sectorToReport)
                                            | (entry.numAdamWells() > 0 ? 1 : 0));
                            Comms.writeBPSectorAdamantiumFlag(sectorToReport, hasAdamWell);
                            int hasManaWell = (entry.shouldUnsetManaWells() ? 0 : 1)
                                    & (Comms.readSectorManaFlag(sectorToReport) | (entry.numManaWells() > 0 ? 1 : 0));
                            Comms.writeBPSectorManaFlag(sectorToReport, hasManaWell);
                            int hasElixirWell = (entry.shouldUnsetManaWells() ? 0 : 1)
                                    & (Comms.readSectorElixirFlag(sectorToReport) | (entry.numElxrWells() > 0 ? 1 : 0));
                            Comms.writeBPSectorElixirFlag(sectorToReport, hasElixirWell);

                            int hasIsland = (entry.numIslands() > 0 ? 1 : 0);
                            Comms.writeBPSectorIslands(sectorToReport, hasIsland);

                            Comms.writeBPSectorControlStatus(sectorToReport, entry.getControlStatus());

                            entry.reset();
                            numSectorsReported++;
                        }

                        sectorToReport++;
                    }

                    if (sectorToReport == numSectors)
                        sectorToReport = 0;

                    Comms.flushBufferPool();
                }

                // Note: markSeen should be last due to bytecode usage
                Explore.markSeen();
        }
    }

    /*
     * Prioritizes miners in general.
     * Unless you're close enough to an Archon, then prioritize soldiers.
     */
    public RobotInfo getBestEnemy() throws GameActionException {
        return getBestEnemy(rc.senseNearbyRobots(actionRadiusSquared, rc.getTeam().opponent()));
    }

    /*
     * Prioritizes attacking enemies in the given order.
     * Prioritizes attacking lower health enemies.
     */
    public void loadEnemies(RobotInfo[] enemies) {
        maybeAmplifier = null;
        maybeBooster = null;
        maybeCarrier = null;
        maybeDestabilizer = null;
        maybeLauncher = null;
        maybeHeadquarters = null;

        RobotInfo enemy;
        for (int i = enemies.length - 1; i >= 0; i--) {
            enemy = enemies[i];
            switch (enemy.type) {
                case AMPLIFIER:
                    if (maybeAmplifier == null || maybeAmplifier.health > enemy.health) {
                        maybeAmplifier = enemy;
                    }
                    break;
                case BOOSTER:
                    if (maybeBooster == null || maybeBooster.health > enemy.health) {
                        maybeBooster = enemy;
                    }
                    break;
                case CARRIER:
                    if (maybeCarrier == null || maybeCarrier.health > enemy.health) {
                        maybeCarrier = enemy;
                    }
                    break;
                case DESTABILIZER:
                    if (maybeDestabilizer == null || maybeDestabilizer.health > enemy.health) {
                        maybeDestabilizer = enemy;
                    }
                    break;
                case LAUNCHER:
                    if (maybeLauncher == null || maybeLauncher.health > enemy.health) {
                        maybeLauncher = enemy;
                    }
                    break;
                case HEADQUARTERS:
                    if (maybeHeadquarters == null || maybeHeadquarters.health > enemy.health) {
                        maybeHeadquarters = enemy;
                    }
                    break;
            }
        }
    }

    public RobotInfo getBestEnemy(RobotInfo[] sensable) throws GameActionException {
        loadEnemies(sensable);

        RobotInfo res = null;

        // Prioritize these the least
        if (maybeHeadquarters != null)
            res = maybeHeadquarters;
        if (maybeAmplifier != null)
            res = maybeAmplifier;
        if (maybeCarrier != null)
            res = maybeCarrier;
        if (maybeBooster != null)
            res = maybeBooster;
        if (maybeDestabilizer != null)
            res = maybeDestabilizer;
        if (maybeLauncher != null)
            res = maybeLauncher;

        return res;
    }

    public RobotInfo getClosestRobot(RobotInfo[] robots) {
        RobotInfo robot;
        RobotInfo closestRobot = null;
        int leastDistance = Integer.MAX_VALUE;
        int currDistance;

        for (int i = robots.length - 1; i >= 0; i--) {
            robot = robots[i];
            currDistance = robot.getLocation().distanceSquaredTo(currLoc);
            if (leastDistance > currDistance) {
                leastDistance = currDistance;
                closestRobot = robot;
            }
        }

        return closestRobot;
    }

    public RobotInfo[] getEnemyAttackable() throws GameActionException {
        return getAttackableRobots(EnemySensable);
    }

    public RobotInfo[] getFriendlyAttackable() throws GameActionException {
        return getAttackableRobots(FriendlySensable);
    }

    public RobotInfo[] getAttackableRobots(RobotInfo[] robots) throws GameActionException {
        int size = 0;
        RobotInfo[] maxAttackable = new RobotInfo[robots.length];
        RobotInfo robot;
        for (int i = robots.length; --i >= 0;) {
            robot = robots[i];
            switch (robot.getType()) {
                case LAUNCHER:
                case DESTABILIZER:
                    maxAttackable[size++] = robot;
                    break;
                default:
                    break;
            }
        }
        RobotInfo[] attackable = new RobotInfo[size];
        System.arraycopy(maxAttackable, 0, attackable, 0, size);
        return attackable;
    }

    public boolean tryAttackBestEnemy(RobotInfo bestEnemy) throws GameActionException {
        // Try to attack someone
        if (bestEnemy != null) {
            if (rc.canAttack(bestEnemy.getLocation())) {
                rc.attack(bestEnemy.getLocation());
                // Debug.printString("Attacking: " + bestEnemy.getLocation().toString());
                return true;
            } else {
                // Debug.printString("Enemy: " + bestEnemy.getLocation().toString());
            }
        }
        return false;
    }

    public boolean tryAttackBestEnemy() throws GameActionException {
        // Try to attack someone
        RobotInfo bestEnemy = getBestEnemy();
        if (bestEnemy != null) {
            if (rc.canAttack(bestEnemy.getLocation())) {
                rc.attack(bestEnemy.getLocation());
                // Debug.printString("Attacking: " + bestEnemy.getLocation().toString());
                return true;
            } else {
                // Debug.printString("Enemy: " + bestEnemy.getLocation().toString());
            }
        }
        return false;
    }

    static boolean tryMoveDest(Direction[] target_dir) throws GameActionException {
        // Debug.println(Debug.info, "Dest direction: " + dir);
        for (Direction dir : target_dir) {
            if (rc.canMove(dir)) {
                rc.move(dir);
                return true;
            }
        }
        return false;
    }

    public void recordIsland(int islandIdx, int sector) throws GameActionException {
        Team team = rc.senseTeamOccupyingIsland(islandIdx);
        if (team == rc.getTeam()) {
            sectorDatabase.at(sector).addIsland(islandIdx, Comms.ControlStatus.FRIENDLY);
        } else if (team == rc.getTeam().opponent()) {
            sectorDatabase.at(sector).addIsland(islandIdx, Comms.ControlStatus.ENEMY);
        } else {
            sectorDatabase.at(sector).addIsland(islandIdx, Comms.ControlStatus.NEUTRAL);
        }
    }

    public void setupSectors() {
        sectorHeights = computeSectorSizes(Util.MAP_HEIGHT);
        sectorWidths = computeSectorSizes(Util.MAP_WIDTH);
        sectorWidthsLength = sectorWidths.length;
        numSectors = sectorHeights.length * sectorWidthsLength;
        yStep = Util.MAP_HEIGHT / ((float) sectorHeights.length);
        xStep = Util.MAP_WIDTH / ((float) sectorWidths.length);
    }

    /**
     * Precompute x, y coordinates of centers of all sectors
     */
    public void precomputeSectorCenters() {
        sectorCenters = new MapLocation[numSectors];
        int yStart = 0;
        for (int j = 0; j < sectorHeights.length; j++) {
            int xStart = 0;
            for (int i = 0; i < sectorWidths.length; i++) {
                int xCenter = xStart + (sectorWidths[i] / 2);
                int yCenter = yStart + (sectorHeights[j] / 2);
                sectorCenters[i + j * sectorWidths.length] = new MapLocation(xCenter, yCenter);
                xStart += sectorWidths[i];
            }
            yStart += sectorHeights[j];
        }
    }

    /**
     * Returns sector given a location.
     * 
     * NOTE: THIS FUNCTION IS ONLY FOR REFERENCE. IF CALLED FREQUENTLY, INLINE THIS
     * FUNCTION!!
     * 
     * @param loc
     * @return
     */
    public int whichSector(MapLocation loc) {
        return whichXLoc[loc.x] + whichYLoc[loc.y];
    }

    public void initSectorPermutation() {
        switch (sectorWidths.length) {
            case 4:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 10, 5, 3, 12, 0, 15, 1, 7, 13, 9, 6, 4, 11, 14, 2, 8 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 10, 9, 3, 16, 0, 19, 6, 5, 18, 13, 17, 11, 2, 1, 4, 15, 12, 8,
                                7, 14 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 14, 9, 3, 20, 0, 23, 2, 7, 1, 12, 11, 22, 8, 16, 21, 15, 13,
                                17, 4, 19, 18, 5, 10, 6 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 14, 13, 3, 24, 0, 27, 9, 15, 22, 21, 26, 6, 18, 10, 20, 19, 4,
                                2, 1, 23, 11, 12, 16, 8, 17, 7, 5, 25 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 18, 13, 3, 28, 0, 31, 14, 27, 30, 23, 5, 20, 12, 2, 17, 21, 19,
                                29, 7, 24, 4, 15, 26, 6, 25, 9, 22, 10, 8, 1, 11, 16 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 18, 17, 3, 32, 0, 35, 31, 28, 24, 10, 4, 8, 13, 25, 5, 7, 12,
                                16, 30, 20, 23, 1, 22, 33, 6, 21, 14, 34, 11, 29, 26, 15, 19, 27, 2, 9 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 22, 17, 3, 36, 0, 39, 34, 29, 6, 23, 16, 13, 4, 20, 1, 14, 30,
                                27, 33, 37, 38, 2, 28, 31, 24, 10, 25, 12, 7, 15, 26, 18, 5, 9, 21, 11, 35, 8, 32, 19 };
                        break;
                }
                break;
            case 5:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 12, 7, 4, 15, 0, 19, 17, 2, 14, 6, 13, 8, 3, 10, 5, 11, 9, 1,
                                18, 16 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 12, 4, 20, 0, 24, 19, 18, 10, 23, 15, 2, 3, 5, 6, 8, 7, 14, 17,
                                21, 9, 11, 22, 1, 13, 16 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 17, 12, 4, 25, 0, 29, 9, 26, 27, 7, 16, 13, 1, 21, 10, 19, 20,
                                22, 15, 8, 23, 14, 5, 3, 11, 18, 6, 28, 2, 24 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 17, 4, 30, 0, 34, 12, 33, 32, 26, 10, 11, 5, 6, 23, 27, 8, 18,
                                15, 16, 21, 7, 2, 3, 20, 19, 28, 29, 25, 24, 31, 22, 1, 9, 14, 13 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 22, 17, 4, 35, 0, 39, 27, 11, 21, 10, 30, 7, 26, 23, 32, 38,
                                25, 3, 12, 8, 1, 34, 6, 31, 9, 14, 16, 5, 19, 13, 36, 24, 18, 20, 33, 37, 2, 29, 28,
                                15 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 22, 4, 40, 0, 44, 11, 20, 16, 41, 6, 27, 3, 1, 17, 37, 39, 9,
                                8, 15, 2, 21, 24, 35, 13, 5, 18, 38, 43, 14, 7, 19, 28, 30, 25, 26, 10, 31, 36, 12, 33,
                                29, 23, 34, 32, 42 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 27, 22, 4, 45, 0, 49, 5, 35, 14, 37, 15, 40, 13, 38, 24, 17,
                                26, 47, 39, 2, 3, 31, 6, 20, 11, 41, 30, 12, 29, 28, 23, 19, 7, 32, 8, 21, 44, 10, 33,
                                18, 34, 36, 1, 9, 42, 46, 25, 43, 16, 48 };
                        break;
                }
                break;
            case 6:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 15, 8, 5, 18, 0, 23, 17, 10, 14, 9, 13, 11, 16, 21, 4, 22, 3,
                                12, 2, 6, 19, 7, 20, 1 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 15, 14, 5, 24, 0, 29, 22, 18, 3, 16, 13, 17, 20, 4, 2, 27, 28,
                                1, 12, 11, 21, 9, 19, 6, 23, 26, 8, 10, 25, 7 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 21, 14, 5, 30, 0, 35, 22, 26, 9, 16, 32, 17, 31, 2, 12, 19, 34,
                                13, 20, 1, 4, 15, 10, 24, 28, 18, 7, 23, 6, 11, 27, 25, 33, 3, 8, 29 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 21, 20, 5, 36, 0, 41, 7, 40, 29, 38, 30, 24, 33, 13, 22, 8, 10,
                                4, 3, 37, 26, 39, 25, 32, 17, 31, 11, 2, 18, 12, 9, 34, 6, 19, 27, 35, 23, 15, 28, 14,
                                1, 16 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 27, 20, 5, 42, 0, 47, 10, 3, 6, 18, 12, 23, 34, 4, 41, 24, 8,
                                45, 2, 14, 38, 31, 46, 44, 16, 39, 13, 32, 21, 40, 17, 43, 9, 36, 22, 15, 26, 30, 35,
                                25, 1, 19, 29, 7, 37, 28, 33, 11 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 27, 26, 5, 48, 0, 53, 49, 52, 20, 37, 1, 9, 31, 50, 36, 23, 10,
                                41, 38, 45, 46, 11, 16, 2, 12, 40, 8, 25, 3, 33, 44, 35, 19, 34, 29, 43, 14, 18, 4, 24,
                                32, 47, 6, 42, 13, 22, 15, 30, 28, 39, 17, 21, 51, 7 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 33, 26, 5, 54, 0, 59, 36, 40, 25, 28, 31, 12, 56, 38, 44, 57,
                                41, 52, 3, 27, 11, 35, 21, 16, 6, 46, 23, 45, 19, 8, 30, 24, 20, 50, 17, 9, 1, 4, 7, 49,
                                32, 48, 18, 47, 10, 42, 37, 51, 22, 43, 29, 55, 15, 53, 14, 39, 2, 34, 13, 58 };
                        break;
                }
                break;
            case 7:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 17, 10, 6, 21, 0, 27, 19, 16, 14, 26, 8, 2, 22, 9, 25, 5, 18,
                                4, 11, 12, 3, 24, 20, 15, 7, 23, 13, 1 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 17, 6, 28, 0, 34, 11, 23, 31, 16, 30, 27, 32, 18, 9, 21, 4, 25,
                                29, 8, 10, 2, 7, 26, 3, 14, 20, 22, 33, 13, 12, 24, 15, 19, 5, 1 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 24, 17, 6, 35, 0, 41, 40, 21, 20, 39, 29, 10, 15, 13, 9, 31,
                                12, 16, 18, 34, 19, 32, 5, 36, 4, 22, 27, 37, 14, 33, 3, 8, 2, 28, 1, 26, 30, 7, 23, 11,
                                25, 38 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 24, 6, 42, 0, 48, 9, 21, 15, 33, 25, 43, 46, 10, 36, 28, 41,
                                45, 31, 20, 18, 14, 1, 16, 2, 37, 22, 12, 30, 32, 13, 35, 39, 44, 29, 27, 40, 5, 11, 17,
                                7, 3, 47, 8, 38, 34, 26, 4, 19, 23 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 31, 24, 6, 49, 0, 55, 32, 48, 36, 20, 11, 35, 18, 8, 45, 33,
                                28, 15, 10, 50, 22, 21, 39, 7, 4, 30, 26, 44, 16, 51, 12, 13, 19, 27, 17, 47, 41, 23,
                                40, 53, 52, 42, 34, 2, 9, 46, 43, 54, 37, 38, 5, 25, 3, 14, 1, 29 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 31, 6, 56, 0, 62, 43, 58, 10, 37, 32, 28, 11, 22, 57, 40, 49,
                                27, 14, 50, 4, 24, 53, 17, 41, 20, 3, 18, 39, 16, 30, 13, 45, 5, 2, 38, 42, 55, 60, 7,
                                46, 19, 51, 44, 1, 33, 21, 61, 34, 36, 48, 12, 9, 29, 52, 15, 47, 26, 35, 23, 54, 59, 8,
                                25 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 38, 31, 6, 63, 0, 69, 55, 62, 17, 49, 56, 21, 44, 3, 22, 19,
                                28, 39, 47, 46, 30, 12, 50, 40, 58, 53, 23, 57, 8, 65, 13, 52, 10, 24, 37, 32, 14, 54,
                                33, 51, 2, 67, 68, 5, 64, 11, 45, 48, 59, 9, 29, 34, 15, 60, 35, 43, 26, 27, 20, 25, 18,
                                36, 41, 42, 4, 66, 1, 16, 61, 7 };
                        break;
                }
                break;
            case 8:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 20, 11, 7, 24, 0, 31, 5, 29, 4, 30, 25, 21, 8, 14, 19, 1, 27,
                                23, 12, 28, 16, 17, 6, 26, 2, 9, 22, 18, 15, 13, 3, 10 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 20, 19, 7, 32, 0, 39, 2, 37, 22, 26, 14, 11, 23, 35, 10, 8, 1,
                                24, 16, 30, 4, 3, 29, 13, 34, 5, 38, 25, 27, 33, 12, 17, 36, 15, 6, 18, 21, 31, 9, 28 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 28, 19, 7, 40, 0, 47, 2, 24, 38, 10, 46, 4, 5, 9, 20, 43, 34,
                                32, 42, 8, 36, 44, 13, 27, 22, 11, 17, 29, 37, 31, 25, 39, 18, 26, 15, 33, 3, 35, 6, 23,
                                14, 45, 16, 41, 12, 30, 21, 1 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 28, 27, 7, 48, 0, 55, 35, 5, 44, 4, 11, 47, 30, 38, 43, 24, 42,
                                37, 13, 52, 15, 41, 26, 32, 10, 12, 36, 40, 19, 49, 51, 17, 33, 53, 20, 3, 29, 31, 6, 8,
                                1, 34, 9, 14, 18, 21, 54, 25, 39, 23, 2, 50, 45, 16, 22, 46 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 36, 27, 7, 56, 0, 63, 15, 51, 1, 50, 54, 18, 22, 48, 9, 47, 4,
                                35, 2, 43, 34, 11, 38, 20, 55, 29, 62, 26, 32, 52, 28, 57, 49, 41, 6, 25, 21, 60, 61,
                                13, 8, 10, 23, 24, 12, 16, 42, 45, 53, 17, 33, 5, 3, 14, 46, 31, 39, 58, 37, 40, 59, 19,
                                30, 44 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 36, 35, 7, 64, 0, 71, 3, 67, 6, 45, 17, 19, 1, 52, 44, 5, 41,
                                10, 4, 47, 46, 40, 12, 42, 32, 50, 31, 63, 48, 43, 62, 54, 58, 16, 9, 66, 60, 26, 30,
                                37, 65, 23, 15, 59, 51, 28, 25, 29, 68, 18, 34, 57, 14, 24, 11, 27, 33, 38, 55, 22, 70,
                                56, 39, 61, 20, 8, 21, 2, 53, 69, 13, 49 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 44, 35, 7, 72, 0, 79, 67, 45, 31, 65, 38, 21, 51, 27, 61, 13,
                                50, 59, 9, 68, 40, 56, 12, 48, 3, 63, 36, 24, 77, 60, 17, 66, 41, 1, 71, 18, 55, 64, 78,
                                6, 30, 2, 39, 5, 28, 57, 76, 42, 62, 73, 4, 14, 22, 53, 11, 70, 16, 52, 58, 10, 33, 46,
                                25, 49, 15, 8, 32, 74, 23, 26, 19, 29, 75, 34, 37, 43, 69, 54, 47, 20 };
                        break;
                }
                break;
            case 9:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 22, 13, 8, 27, 0, 35, 7, 4, 19, 25, 16, 9, 17, 31, 34, 30, 32,
                                20, 3, 1, 14, 12, 6, 26, 23, 2, 28, 18, 33, 5, 21, 10, 24, 11, 29, 15 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 22, 8, 36, 0, 44, 3, 30, 13, 5, 7, 32, 25, 38, 39, 41, 18, 20,
                                21, 4, 19, 24, 14, 40, 37, 29, 9, 31, 6, 12, 10, 15, 11, 23, 33, 27, 16, 28, 34, 43, 1,
                                26, 42, 35, 2, 17 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 31, 22, 8, 45, 0, 53, 36, 12, 47, 25, 39, 30, 7, 16, 28, 5, 6,
                                41, 18, 11, 51, 37, 34, 49, 2, 40, 23, 20, 38, 17, 13, 35, 24, 52, 42, 4, 26, 46, 3, 14,
                                10, 33, 15, 50, 27, 43, 9, 44, 1, 29, 19, 21, 48, 32 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 31, 8, 54, 0, 62, 25, 49, 22, 32, 5, 12, 55, 46, 34, 28, 33,
                                14, 4, 39, 6, 40, 10, 60, 42, 13, 26, 1, 7, 17, 47, 2, 20, 15, 58, 56, 3, 61, 30, 41,
                                59, 57, 38, 37, 48, 50, 45, 44, 43, 52, 18, 23, 11, 29, 51, 9, 24, 36, 21, 53, 27, 35,
                                16, 19 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 40, 31, 8, 63, 0, 71, 10, 65, 50, 4, 2, 23, 6, 35, 1, 66, 70,
                                15, 59, 62, 29, 13, 20, 57, 38, 43, 45, 51, 5, 21, 36, 32, 12, 60, 41, 28, 52, 19, 9,
                                68, 49, 61, 69, 42, 30, 18, 3, 54, 47, 34, 24, 17, 25, 58, 33, 27, 64, 37, 55, 7, 11,
                                39, 67, 44, 22, 16, 26, 53, 46, 48, 14, 56 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 40, 8, 72, 0, 80, 41, 33, 28, 43, 24, 50, 56, 26, 37, 48, 71,
                                20, 53, 3, 12, 70, 61, 75, 74, 23, 1, 68, 13, 78, 76, 79, 67, 51, 17, 55, 32, 14, 18,
                                29, 69, 42, 49, 25, 77, 52, 57, 46, 5, 35, 4, 6, 60, 47, 63, 7, 27, 65, 11, 39, 58, 31,
                                36, 9, 22, 54, 45, 19, 34, 2, 73, 59, 62, 16, 10, 66, 64, 44, 21, 38, 15, 30 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 49, 40, 8, 81, 0, 89, 10, 72, 76, 69, 75, 34, 32, 47, 82, 38,
                                22, 86, 25, 31, 48, 56, 13, 59, 19, 51, 87, 44, 79, 23, 77, 9, 60, 26, 57, 55, 88, 65,
                                80, 4, 35, 14, 39, 3, 53, 27, 70, 66, 52, 12, 83, 28, 2, 36, 50, 43, 42, 30, 6, 61, 41,
                                85, 21, 63, 5, 15, 11, 58, 16, 62, 73, 29, 74, 7, 45, 54, 20, 64, 68, 46, 1, 78, 67, 71,
                                33, 17, 24, 18, 84, 37 };
                        break;
                }
                break;
            case 10:
                switch (sectorHeights.length) {
                    case 4:
                        sectorPermutation = new int[] { 25, 14, 9, 30, 0, 39, 26, 1, 35, 16, 17, 21, 32, 12, 7, 34, 38,
                                31, 8, 28, 11, 18, 22, 13, 6, 24, 20, 23, 10, 27, 36, 5, 15, 3, 33, 2, 29, 37, 19, 4 };
                        break;
                    case 5:
                        sectorPermutation = new int[] { 25, 24, 9, 40, 0, 49, 35, 15, 14, 39, 20, 27, 43, 18, 48, 30,
                                2, 22, 3, 11, 46, 10, 28, 4, 41, 26, 12, 5, 19, 16, 8, 29, 13, 7, 23, 33, 32, 42, 17,
                                37, 6, 21, 38, 1, 45, 31, 44, 34, 36, 47 };
                        break;
                    case 6:
                        sectorPermutation = new int[] { 35, 24, 9, 50, 0, 59, 39, 12, 1, 41, 29, 37, 52, 38, 46, 42,
                                58, 13, 25, 48, 57, 10, 34, 8, 30, 21, 23, 3, 18, 31, 26, 44, 54, 49, 27, 5, 17, 55, 45,
                                15, 20, 51, 6, 47, 36, 19, 4, 28, 56, 32, 53, 43, 33, 11, 14, 7, 2, 40, 16, 22 };
                        break;
                    case 7:
                        sectorPermutation = new int[] { 35, 34, 9, 60, 0, 69, 44, 2, 61, 37, 25, 5, 31, 41, 12, 40, 22,
                                28, 36, 3, 49, 42, 27, 62, 53, 21, 14, 32, 46, 20, 1, 18, 16, 24, 45, 59, 39, 38, 50,
                                33, 4, 55, 63, 23, 30, 67, 54, 19, 17, 13, 15, 68, 65, 10, 8, 26, 57, 51, 52, 47, 43,
                                56, 11, 29, 6, 48, 66, 64, 7, 58 };
                        break;
                    case 8:
                        sectorPermutation = new int[] { 45, 34, 9, 70, 0, 79, 58, 2, 59, 33, 5, 77, 3, 12, 23, 56, 63,
                                35, 46, 60, 11, 66, 32, 6, 1, 16, 21, 18, 65, 64, 31, 43, 27, 52, 73, 26, 54, 39, 67,
                                15, 53, 61, 29, 75, 71, 24, 13, 47, 22, 76, 69, 4, 17, 36, 20, 38, 7, 74, 48, 28, 49,
                                30, 19, 44, 78, 37, 57, 68, 8, 10, 72, 50, 51, 62, 42, 55, 40, 25, 41, 14 };
                        break;
                    case 9:
                        sectorPermutation = new int[] { 45, 44, 9, 80, 0, 89, 39, 56, 88, 20, 7, 71, 33, 22, 14, 34,
                                41, 51, 1, 38, 67, 30, 68, 28, 21, 72, 18, 52, 69, 32, 57, 77, 86, 53, 60, 11, 17, 31,
                                46, 43, 74, 35, 48, 3, 87, 8, 42, 82, 36, 25, 10, 19, 84, 85, 29, 61, 23, 78, 81, 63,
                                75, 40, 4, 58, 26, 66, 2, 13, 65, 49, 12, 37, 76, 54, 73, 70, 79, 27, 15, 24, 62, 16,
                                50, 5, 55, 83, 47, 6, 59, 64 };
                        break;
                    case 10:
                        sectorPermutation = new int[] { 55, 44, 9, 90, 0, 99, 15, 29, 58, 19, 34, 24, 7, 13, 8, 97, 45,
                                78, 46, 40, 36, 67, 64, 50, 81, 95, 56, 3, 11, 37, 25, 80, 33, 59, 91, 42, 61, 4, 35,
                                88, 70, 47, 86, 49, 87, 94, 82, 6, 32, 53, 26, 51, 65, 5, 77, 89, 71, 92, 21, 18, 31, 1,
                                54, 22, 75, 85, 16, 63, 2, 20, 74, 17, 14, 48, 72, 93, 69, 68, 84, 76, 66, 52, 27, 10,
                                96, 60, 30, 79, 38, 57, 73, 39, 62, 83, 28, 43, 23, 98, 41, 12 };
                        break;
                }
                break;
        }
    }

    public int[] computeSectorSizes(int dim) {
        switch (dim) {
            case 20:
                return new int[] { 5, 5, 5, 5 };
            case 21:
                return new int[] { 5, 5, 5, 6 };
            case 22:
                return new int[] { 5, 6, 5, 6 };
            case 23:
                return new int[] { 5, 6, 6, 6 };
            case 24:
                return new int[] { 6, 6, 6, 6 };
            case 25:
                return new int[] { 5, 5, 5, 5, 5 };
            case 26:
                return new int[] { 5, 5, 5, 5, 6 };
            case 27:
                return new int[] { 5, 5, 6, 5, 6 };
            case 28:
                return new int[] { 5, 6, 5, 6, 6 };
            case 29:
                return new int[] { 5, 6, 6, 6, 6 };
            case 30:
                return new int[] { 6, 6, 6, 6, 6 };
            case 31:
                return new int[] { 5, 5, 5, 5, 5, 6 };
            case 32:
                return new int[] { 5, 5, 6, 5, 5, 5 };
            case 33:
                return new int[] { 5, 6, 5, 6, 5, 6 };
            case 34:
                return new int[] { 5, 6, 6, 5, 6, 6 };
            case 35:
                return new int[] { 5, 6, 6, 6, 6, 6 };
            case 36:
                return new int[] { 6, 6, 6, 6, 6, 6 };
            case 37:
                return new int[] { 5, 5, 5, 6, 5, 5, 6 };
            case 38:
                return new int[] { 5, 5, 6, 5, 6, 5, 6 };
            case 39:
                return new int[] { 5, 6, 5, 6, 5, 6, 5 };
            case 40:
                return new int[] { 5, 6, 6, 5, 6, 6, 6 };
            case 41:
                return new int[] { 5, 6, 6, 6, 6, 6, 5 };
            case 42:
                return new int[] { 6, 6, 6, 6, 6, 6, 6 };
            case 43:
                return new int[] { 5, 5, 6, 5, 5, 6, 5, 6 };
            case 44:
                return new int[] { 5, 6, 5, 6, 5, 6, 5, 6 };
            case 45:
                return new int[] { 5, 6, 5, 6, 6, 5, 6, 6 };
            case 46:
                return new int[] { 5, 6, 6, 6, 5, 6, 6, 6 };
            case 47:
                return new int[] { 5, 6, 6, 6, 6, 6, 6, 6 };
            case 48:
                return new int[] { 6, 6, 6, 6, 6, 6, 6, 6 };
            case 49:
                return new int[] { 5, 5, 6, 5, 6, 5, 6, 5, 5 };
            case 50:
                return new int[] { 5, 6, 5, 6, 5, 6, 5, 6, 6 };
            case 51:
                return new int[] { 5, 6, 6, 5, 6, 6, 5, 6, 5 };
            case 52:
                return new int[] { 5, 6, 6, 6, 5, 6, 6, 6, 6 };
            case 53:
                return new int[] { 5, 6, 6, 6, 6, 6, 6, 6, 6 };
            case 54:
                return new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 6 };
            case 55:
                return new int[] { 5, 6, 5, 6, 5, 6, 5, 6, 5, 6 };
            case 56:
                return new int[] { 5, 6, 5, 6, 6, 5, 6, 5, 6, 6 };
            case 57:
                return new int[] { 5, 6, 6, 5, 6, 6, 5, 6, 6, 6 };
            case 58:
                return new int[] { 5, 6, 6, 6, 6, 5, 6, 6, 6, 5 };
            case 59:
                return new int[] { 5, 6, 6, 6, 6, 6, 6, 6, 6, 5 };
            case 60:
                return new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 };
            default:
                return new int[] {};
        }
    }

    /**
     * Updates sector information. Scans nearby tiles, enemy locations, and nearby
     * resources
     * and aggregates into sectorControls and sectorResoruces as buffers. Uses
     * markedSectorsBuffer to track which buffers have been modified each turn to
     * reset them.
     * Alternates whether control or resources are scanned each turn to conserve
     * bytecode.
     * 
     * Note: This is not set up until turn 3 to save compute on initialization.
     * 
     * @throws GameActionException
     */
    public void setSectorStates() throws GameActionException {
        // int bytecodeUsed = Clock.getBytecodeNum();

        // Not initialized until turn 3
        if (roundNum == 2) {
            return;
        }

        if (roundNum % 2 == 0) {
            setSectorControlStates();
        } else {
            setSectorResourceStates();
        }

        // int bytecodeUsed2 = Clock.getBytecodeNum();
        // rc.setIndicatorString("Sector States: "+(bytecodeUsed2 - bytecodeUsed));
    }

    /**
     * Updates sector information. Scans nearby enemy locations.
     * 
     * @throws GameActionException
     */
    public void setSectorControlStates() throws GameActionException {
        // Mark nearby sectors with enemies as hostile
        // Process at max 10 enemies
        int numEnemies = Math.min(EnemySensable.length, 10);
        for (int i = 0; i < numEnemies; i++) {
            RobotInfo enemy = EnemySensable[i];
            int sectorIdx = whichXLoc[enemy.location.x] + whichYLoc[enemy.location.y];
            sectorDatabase.at(sectorIdx).setControlStatus(Comms.ControlStatus.ENEMY);
        }

        int[] islandIdxs = rc.senseNearbyIslands();
        if (islandIdxs.length > 0) {
            for (int idx : islandIdxs) {
                // This costs a lot of bytecode, but I don't know how to get around this
                recordIsland(idx, whichSector(rc.senseNearbyIslandLocations(idx)[0]));
            }
        }
    }

    /**
     * Updates sector information.
     * Scans nearby sectors to mark as explored and adds wells.
     * 
     * @throws GameActionException
     */
    public void setSectorResourceStates() throws GameActionException {
        // Mark nearby sectors as explored
        int[][] shifts = { { 0, 3 }, { 2, 2 }, { 3, 0 }, { 2, -2 }, { 0, -3 }, { -2, -2 }, { -3, 0 }, { -2, 2 } };
        for (int[] shift : shifts) {
            MapLocation shiftedLocation = currLoc.translate(shift[0], shift[1]);
            if (rc.canSenseLocation(shiftedLocation)) {
                // int sectorIdx = whichSector(shiftedLocation);
                // Note: Inlined to save bytecode
                int sectorIdx = whichXLoc[shiftedLocation.x] + whichYLoc[shiftedLocation.y];
                sectorDatabase.at(sectorIdx).setControlStatus(Comms.ControlStatus.EMPTY);
            }
        }

        for (WellInfo info : rc.senseNearbyWells()) {
            int sector = whichSector(info.getMapLocation());
            sectorDatabase.at(sector).addWell(info.getMapLocation(), info.getResourceType());
        }
    }

    /**
     * Returns nearest combat sector or UNDEFINED_SECTOR_INDEX otherwise
     * 
     * @return
     * @throws GameActionException
     */
    public int getNearestCombatSector() throws GameActionException {
        int closestSector = Comms.UNDEFINED_SECTOR_INDEX;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < Comms.COMBAT_SECTOR_SLOTS; i++) {
            int nearestSector = Comms.readCombatSectorIndex(i);
            // Break if no more combat sectors exist
            if (nearestSector == Comms.UNDEFINED_SECTOR_INDEX) {
                break;
            }
            int distance = currLoc.distanceSquaredTo(sectorCenters[nearestSector]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestSector = nearestSector;
            }
        }
        return closestSector;
    }

    /**
     * Returns nearest explore sector or UNDEFINED_SECTOR_INDEX otherwise
     * 
     * @return
     * @throws GameActionException
     */
    public int getNearestExploreSector() throws GameActionException {
        int closestSector = Comms.UNDEFINED_SECTOR_INDEX;
        int closestSectorIndex = Comms.UNDEFINED_SECTOR_INDEX;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < Comms.EXPLORE_SECTOR_SLOTS; i++) {
            int nearestSectorAll = Comms.readExploreSectorAll(i);
            int nearestSector = nearestSectorAll & 127; // 7 lowest order bits
            // Break if no more combat sectors exist
            if (nearestSector == Comms.UNDEFINED_SECTOR_INDEX) {
                break;
            }
            // Skip sectors which are fully claimed
            int nearestSectorStatus = (nearestSectorAll & 128) >> 7; // 2^7
            if (nearestSectorStatus == Comms.ClaimStatus.CLAIMED) {
                continue;
            }
            int distance = currLoc.distanceSquaredTo(sectorCenters[nearestSector]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestSector = nearestSector;
                closestSectorIndex = i;
            }
        }
        // Claim sector
        if (closestSectorIndex != Comms.UNDEFINED_SECTOR_INDEX) {
            // TODO: Have similar logic to SectorInfo?
            if (rc.canWriteSharedArray(0, 0)) {
                Comms.writeExploreSectorClaimStatus(closestSectorIndex, Comms.ClaimStatus.CLAIMED);
            }
            sectorDatabase.at(closestSector).setControlStatus(Comms.ControlStatus.EXPLORING);
            exploreMode = true;
        }
        return closestSector;
    }

    /**
     * If unit redirects from an exploration, remark the sector as unknown
     * 
     * @param destination
     * @throws GameActionException
     */
    public void resetControlStatus(MapLocation destination) throws GameActionException {
        if (exploreMode) {
            int sector = whichXLoc[destination.x] + whichYLoc[destination.y];
            sectorDatabase.at(sector).resetControlStatus();
        }
    }

    /**
     * Get the nearest sector that satisfies the given control status, encoded as
     * follows:
     * 0: unknown; 1: we control; 2: enemy controls; 3: ??.
     * Get the nearest sector that satisfies the given control status, encoded by
     * `Comms.ControlStatus`.
     * 
     */
    public int getNearestSectorByControlStatus(int status) throws GameActionException {
        int closestSector = Comms.UNDEFINED_SECTOR_INDEX;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < numSectors; i++) {
            if (Comms.readSectorControlStatus(i) == status) {
                int distance = currLoc.distanceSquaredTo(sectorCenters[i]);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestSector = i;
                }
            }
        }
        return closestSector;
    }

    // For debugging right now. Prints found adam wells
    public void findWells() throws GameActionException {
        for (int i = 0; i < numSectors; i++) {
            if (Comms.readSectorAdamantiumFlag(i) == 1) {
                Debug.println("Adam well in sector " + i + " at " + sectorCenters[i]);
            }
        }
    }

    public void printEnemySectors() throws GameActionException {
        for (int i = 0; i < numSectors; i++) {
            if (Comms.readSectorControlStatus(i) == Comms.ControlStatus.ENEMY) {
                Debug.println("Enemy sector " + i + " at " + sectorCenters[i]);
            }
        }
    }
}
