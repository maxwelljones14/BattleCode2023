package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;
import java.util.ArrayDeque;

import MPWorking.fast.*;

public class Headquarters extends Robot {
    static enum State {
        INIT,
        CHILLING,
        BUILDING_ANCHOR,
    };

    static int myHqNum = -1;
    static int numHqs = -1;
    static boolean lastHq;

    static ArrayDeque<State> stateStack;
    static State currentState;

    // robot counts
    static int carrierCount;
    static int launcherCount;
    static int anchorCount;

    static final int initCarriersWanted = 4;
    static final int initLaunchersWanted = 4;
    static final int minCarriersBeforeAnchor = 20;

    static MapLocation currLoc;

    static int nextFlag;

    static MapLocation closestEnemyHQGuess;
    static Direction closestEnemyHQGuessDir;

    static MapLocation nearestVisibleAdWell;
    static MapLocation nearestVisibleMnWell;
    static Direction nearestVisibleAdWellDir;
    static Direction nearestVisibleMnWellDir;
    static MapLocation nearestAdWellSector;
    static MapLocation nearestMnWellSector;
    static Direction nearestAdWellSectorDir;
    static Direction nearestMnWellSectorDir;

    static MapLocation[] locsToBuildCarriers;
    static MapLocation[] locsToBuildLaunchers;

    static final double MANA_TO_ADAM_CARRIER_RATIO = 0.7 / 0.3;
    static final int ROUNDS_TO_STALE_UNIT = 75;
    FastUnitTracker adamCarrierTracker;
    FastUnitTracker manaCarrierTracker;

    static boolean nearEnemyHQ;
    static MapLocation enemyHQLoc;
    static boolean nearFriendlyHQ;
    static MapLocation friendlyHQLoc;

    static FastIntIntMap combatSectorToTurnWritten;

    static int symmetryGuess;

    static Direction[] exploreDirections;

    public static int turnBuiltAnchor = -1;
    public static int BUILD_ANCHOR_COOLDOWN = 50;

    public Headquarters(RobotController r) throws GameActionException {
        super(r);
        checkIfHQNear();
        stateStack = new ArrayDeque<State>();
        currentState = State.INIT;
        currLoc = rc.getLocation();

        initSectorPermutation();

        closestEnemyHQGuess = null;
        closestEnemyHQGuessDir = null;

        nearestVisibleAdWell = null;
        nearestVisibleMnWell = null;
        nearestVisibleAdWellDir = null;
        nearestVisibleMnWellDir = null;
        nearestAdWellSector = null;
        nearestMnWellSector = null;
        nearestAdWellSectorDir = null;
        nearestMnWellSectorDir = null;

        locsToBuildCarriers = new MapLocation[initCarriersWanted];
        locsToBuildLaunchers = new MapLocation[initLaunchersWanted];

        carrierCount = 0;
        launcherCount = 0;
        anchorCount = 0;
        nextFlag = 0;

        adamCarrierTracker = new FastUnitTracker(rc, ROUNDS_TO_STALE_UNIT);
        manaCarrierTracker = new FastUnitTracker(rc, ROUNDS_TO_STALE_UNIT);

        combatSectorToTurnWritten = new FastIntIntMap();
        symmetryGuess = Util.SymmetryType.ROTATIONAL;

        exploreDirections = Explore.getOptimalExploreOrder();
    }

    public void localResign() {
        // if (rc.getRoundNum() == 20) {
        // rc.resign();
        // }
        if (rc.getRoundNum() >= 400 && rc.getRobotCount() <= numHqs)
            rc.resign();
    }

    // Functions with the prefix "debug_" are only run when the gradle property
    // "debug" is set to true
    public void debug_localResign() {
        localResign();
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // localResign();
        debug_localResign();
        adamCarrierTracker.update();
        manaCarrierTracker.update();
        clearOldEnemyInfo();

        switch (rc.getRoundNum()) {
            case 1:
                computeHqNum();
                findClosestVisibleWells();
                if (numHqs == 1) {
                    loadHQLocations();
                }
                break;
            case 2:
                if (numHqs == 1) {
                    setInitialExploreSectors();
                } else {
                    loadHQLocations();
                }
                break;
            case 3:
                if (numHqs != 1) {
                    setInitialExploreSectors();
                }
                if (headquarterLocations == null)
                    loadHQLocations();
                break;
            default:
                if (headquarterLocations == null)
                    loadHQLocations();
                break;
        }

        if (numHqs == 1 || rc.getRoundNum() >= 2) {
            loadSymmetry();
        }

        loadClosestMiningSector();
        writeElixirWellCandidate();

        setPrioritySectors();
        toggleState();
        Debug.printString("S: " + currentState);
        Debug.printString("A: " + adamCarrierTracker.size());
        Debug.printString("M: " + manaCarrierTracker.size());
        Comms.writeOurHqFlag(myHqNum, nextFlag);
        nextFlag = 0;
        doStateAction();
        // displayExploreSectors();
        // displayMineSectors();
        // displayCombatSectors();
    }

    // The symmetry guess is the one that gives the furthest average distance to HQs
    public void guessSymmetry() throws GameActionException {
        int[] symmetries = { Util.SymmetryType.HORIZONTAL, Util.SymmetryType.VERTICAL, Util.SymmetryType.ROTATIONAL };
        int symmetry;
        int bestSymmetry = Util.SymmetryType.ROTATIONAL;
        int maxDist = 0;
        int dist = 0;
        for (int i = symmetries.length; --i >= 0;) {
            if ((symmetryAll & symmetries[i]) == 0)
                continue;
            symmetry = symmetries[i];
            dist = 0;
            for (int j = 0; j < headquarterLocations.length; j++) {
                dist += home.distanceSquaredTo(Util.getValidSymmetryLocs(headquarterLocations[j], symmetry)[0]);
            }
            dist /= headquarterLocations.length;

            if (dist > maxDist) {
                maxDist = dist;
                bestSymmetry = symmetry;
            }
        }

        // Debug.println("Symmetry guess: " + bestSymmetry + " with dist " + maxDist);
        symmetryGuess = bestSymmetry;
    }

    public void loadSymmetry() throws GameActionException {
        updateSymmetryLocs();
        invalidateSymmetries();
        updateSymmetryLocs();
        symmetryAll = Comms.readSymmetryAll();
        // guessSymmetry();
        guessClosestEnemyHQ();
    }

    public void guessClosestEnemyHQ() throws GameActionException {
        MapLocation newEnemyHQGuess = getClosestEnemyHQGuess();
        // closestEnemyHQGuess = getClosestEnemyHQ(symmetryGuess);
        if (closestEnemyHQGuess == null || !newEnemyHQGuess.equals(closestEnemyHQGuess)) {
            closestEnemyHQGuess = newEnemyHQGuess;
            closestEnemyHQGuessDir = getBestDirTo(closestEnemyHQGuess);
            // Debug.println("Guessing enemy HQ at " + closestEnemyHQGuess);
        }
    }

    public void checkIfHQNear() throws GameActionException {
        EnemySensable = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        nearEnemyHQ = false;
        for (int x = 0; x < EnemySensable.length; x++) {
            RobotInfo enemy = EnemySensable[x];
            if (enemy.getType() == RobotType.HEADQUARTERS) {
                nearEnemyHQ = true;
                enemyHQLoc = enemy.getLocation();
                break;
            }
        }

        FriendlySensable = rc.senseNearbyRobots(-1, team);
        nearFriendlyHQ = false;
        for (int x = 0; x < FriendlySensable.length; x++) {
            RobotInfo enemy = FriendlySensable[x];
            if (enemy.getType() == RobotType.HEADQUARTERS) {
                nearFriendlyHQ = true;
                friendlyHQLoc = enemy.getLocation();
                break;
            }
        }
    }

    public void findClosestVisibleWells() throws GameActionException {
        // find nearest wells
        WellInfo[] wellsNearMe = rc.senseNearbyWells(visionRadiusSquared);
        int closestAdWellDistance = visionRadiusSquared;
        int closestMnWellDistance = visionRadiusSquared;

        for (WellInfo well : wellsNearMe) {
            int dist = currLoc.distanceSquaredTo(well.getMapLocation());
            if (well.getResourceType() == ResourceType.ADAMANTIUM && dist <= closestAdWellDistance) {
                closestAdWellDistance = dist;
                nearestVisibleAdWell = well.getMapLocation();
            } else if (well.getResourceType() == ResourceType.MANA && dist <= closestMnWellDistance) {
                closestMnWellDistance = dist;
                nearestVisibleMnWell = well.getMapLocation();
            }
        }

        // Immediately write these to the mine sectors
        int mineSectorIndex = getNextEmptyMineSectorIdx(0);
        if (nearestVisibleAdWell != null) {
            int sectorIdx = whichSector(nearestVisibleAdWell);
            Comms.writeSectorAdamantiumFlag(sectorIdx, 1);

            // Mine sector
            mineSector: if (mineSectorIndex < Comms.MINE_SECTOR_SLOTS) {
                // If the sector is already a mine sector, don't add it again
                for (int j = Comms.MINE_SECTOR_SLOTS - 1; j >= 0; j--) {
                    if (Comms.readMineSectorIndex(j) == sectorIdx) {
                        break mineSector;
                    }
                }
                Comms.writeMineSectorIndex(mineSectorIndex, sectorIdx);
                mineSectorIndex = getNextEmptyMineSectorIdx(mineSectorIndex + 1);
            }
        }

        if (nearestVisibleMnWell != null) {
            int sectorIdx = whichSector(nearestVisibleMnWell);
            Comms.writeSectorManaFlag(sectorIdx, 1);

            // Mine sector
            mineSector: if (mineSectorIndex < Comms.MINE_SECTOR_SLOTS) {
                // If the sector is already a mine sector, don't add it again
                for (int j = Comms.MINE_SECTOR_SLOTS - 1; j >= 0; j--) {
                    if (Comms.readMineSectorIndex(j) == sectorIdx) {
                        break mineSector;
                    }
                }

                Comms.writeMineSectorIndex(mineSectorIndex, sectorIdx);
                mineSectorIndex = getNextEmptyMineSectorIdx(mineSectorIndex + 1);
            }
        }
    }

    public void loadClosestMiningSector() throws GameActionException {
        // look in sectors for nearest mn well
        int mineSectorIndex = getNearestMineSectorIdx(ResourceType.MANA, null);
        MapLocation mnWellSector = mineSectorIndex == Comms.UNDEFINED_SECTOR_INDEX ? null
                : sectorCenters[mineSectorIndex];
        if (mnWellSector != null) {
            if (nearestMnWellSector == null || !nearestMnWellSector.equals(mnWellSector)) {
                nearestMnWellSector = mnWellSector;
                nearestMnWellSectorDir = null;
            }
        }

        // look in sectors for nearest ad well
        mineSectorIndex = getNearestMineSectorIdx(ResourceType.ADAMANTIUM, null);
        MapLocation adWellSector = mineSectorIndex == Comms.UNDEFINED_SECTOR_INDEX ? null
                : sectorCenters[mineSectorIndex];
        if (adWellSector != null) {
            if (nearestAdWellSector == null || !nearestAdWellSector.equals(adWellSector)) {
                nearestAdWellSector = adWellSector;
                nearestAdWellSectorDir = null;
            }
        }
    }

    public MapLocation getNearestMnWell() {
        if (nearestVisibleMnWell != null) {
            return nearestVisibleMnWell;
        }

        if (nearestMnWellSector == null) {
            return null;
        }

        if (shouldIgnoreMiningSector(nearestMnWellSector)) {
            return null;
        }

        return nearestMnWellSector;
    }

    public MapLocation getNearestAdWell() {
        if (nearestVisibleAdWell != null) {
            return nearestVisibleAdWell;
        }

        if (nearestAdWellSector == null) {
            return null;
        }

        if (shouldIgnoreMiningSector(nearestAdWellSector)) {
            return null;
        }

        return nearestAdWellSector;
    }

    public boolean shouldIgnoreMiningSector(MapLocation miningSector) {
        return rc.getRoundNum() < Util.TURN_TO_IGNORE_EARLY_MINING_SECTORS &&
                Util.distance(home, miningSector) > Util.DIST_TO_IGNORE_EARLY_MINING_SECTORS;
    }

    public int getNextCarrierType() throws GameActionException {
        int carrierType;
        MapLocation nearestMnWell = getNearestMnWell();
        MapLocation nearestAdWell = getNearestAdWell();

        if (currentState == State.INIT) {
            // Always build MANA first. Some logic is dependent on that.
            if (carrierCount == 0) {
                carrierType = Comms.HQFlag.CARRIER_MANA;
            } else if (isSmallMap()) {
                // 4/0
                carrierType = initCarrierRatio(4, 0);
            } else if (isSemiSmallMap()) {
                // 3/1
                carrierType = initCarrierRatio(3, 1);
            } else if (nearestMnWell != null && nearestAdWell != null) {
                // Full information. Proceed as normal.
                if (closestEnemyHQGuess == null) {
                    // Delay building another carrier until we know where the enemy HQ is
                    carrierType = -1;
                } else {
                    // Now that we have an HQ guess, decide on AD or MN based on how far it is
                    int estInterceptTime = Util.distance(home, closestEnemyHQGuess) + Util.AVG_FIRST_COMBAT_LENGTH;
                    // If the first carriers can finish a cycle before launchers can reach us,
                    // go 2/2
                    if (estInterceptTime > Util.CARRIER_TURNS_TO_FILL + 1.5 * Util.distance(nearestAdWell, home)) {
                        carrierType = initCarrierRatio(2, 2);
                    } else {
                        // Otherwise, go 3/1
                        carrierType = initCarrierRatio(3, 1);
                    }
                }
            } else if (nearestMnWell != null) {
                // If there is only mana
                if (rc.getRoundNum() == 1 && !lastHq) {
                    // If we are not the last HQ and have built one carrier already,
                    // let all HQs place their visible wells in mining sectors so
                    // that we can make a more informed decision
                    carrierType = -1;
                } else {
                    carrierType = initCarrierRatio(3, 1);
                }
            } else if (nearestAdWell != null) {
                // Only adamantium
                if (rc.getRoundNum() == 1 && !lastHq) {
                    // If we are not the last HQ and have built one carrier already,
                    // let all HQs place their visible wells in mining sectors so
                    // that we can make a more informed decision
                    carrierType = -1;
                } else {
                    // Big map: 2/2 (We've already built the first carrier)
                    carrierType = initCarrierRatio(2, 2);
                }
            } else {
                if (rc.getRoundNum() == 1 && !lastHq && carrierCount != 0) {
                    // If we are not the last HQ and have built one carrier already,
                    // let all HQs place their visible wells in mining sectors so
                    // that we can make a more informed decision
                    carrierType = -1;
                } else {
                    // Go 2/2
                    carrierType = initCarrierRatio(2, 2);
                }
            }
        } else {
            if (manaCarrierTracker.size() <= adamCarrierTracker.size() * MANA_TO_ADAM_CARRIER_RATIO) {
                carrierType = Comms.HQFlag.CARRIER_MANA;
            } else {
                carrierType = Comms.HQFlag.CARRIER_ADAMANTIUM;
            }
        }
        return carrierType;
    }

    public int initCarrierRatio(int mana, int adam) {
        if (carrierCount < mana) {
            return Comms.HQFlag.CARRIER_MANA;
        }
        return Comms.HQFlag.CARRIER_ADAMANTIUM;
    }

    public Direction getBestDirTo(MapLocation loc) throws GameActionException {
        Direction dir = Nav.getBestDir(loc, 10000);
        if (dir == Direction.CENTER) {
            dir = home.directionTo(loc);
        }
        return dir;
    }

    public MapLocation getNextCarrierLocation(int carrierType) throws GameActionException {
        Direction dirToBuild = null;

        if (carrierType == Comms.HQFlag.CARRIER_ADAMANTIUM) {
            if (nearestVisibleAdWell != null) {
                if (nearestVisibleAdWellDir == null) {
                    nearestVisibleAdWellDir = getBestDirTo(nearestVisibleAdWell);
                }
                dirToBuild = nearestVisibleAdWellDir;
            } else if (nearestAdWellSector != null && !shouldIgnoreMiningSector(nearestAdWellSector)) {
                if (nearestAdWellSectorDir == null) {
                    nearestAdWellSectorDir = getBestDirTo(nearestAdWellSector);
                }
                dirToBuild = nearestAdWellSectorDir;
            } else {
                // Build init towards the center
                if (currentState == State.INIT) {
                    MapLocation exploreLoc = findNextExploreInitLoc();
                    if (exploreLoc != null) {
                        return exploreLoc;
                    } else {
                        dirToBuild = home.directionTo(new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2));
                    }
                } else {
                    dirToBuild = Util.directions[Util.rng.nextInt(Util.directions.length)];
                }
            }
        } else {
            if (nearestVisibleMnWell != null) {
                if (nearestVisibleMnWellDir == null) {
                    nearestVisibleMnWellDir = getBestDirTo(nearestVisibleMnWell);
                }
                dirToBuild = nearestVisibleMnWellDir;
            } else if (nearestMnWellSector != null && !shouldIgnoreMiningSector(nearestMnWellSector)) {
                if (nearestMnWellSectorDir == null) {
                    nearestMnWellSectorDir = getBestDirTo(nearestMnWellSector);
                }
                dirToBuild = nearestMnWellSectorDir;
            } else {
                // Build init towards the center
                if (currentState == State.INIT) {
                    MapLocation exploreLoc = findNextExploreInitLoc();
                    if (exploreLoc != null) {
                        return exploreLoc;
                    } else {
                        dirToBuild = home.directionTo(new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2));
                    }
                } else {
                    dirToBuild = Util.directions[Util.rng.nextInt(Util.directions.length)];
                }
            }
        }
        return Util.findInitLocation(RobotType.CARRIER, dirToBuild);
    }

    public void toggleState() throws GameActionException {
        switch (currentState) {
            case INIT:
                if (carrierCount >= initCarriersWanted && launcherCount >= initLaunchersWanted) {
                    changeState(State.CHILLING);
                }
                break;
            case CHILLING:
                if (carrierCount >= minCarriersBeforeAnchor &&
                        rc.getNumAnchors(Anchor.STANDARD) == 0 &&
                        rc.getRoundNum() >= turnBuiltAnchor + BUILD_ANCHOR_COOLDOWN) {
                    // start saving for anchor if you have minCarriersBeforeAnchor carriers already
                    stateStack.push(currentState);
                    changeState(State.BUILDING_ANCHOR);
                }
                break;
            case BUILDING_ANCHOR:
                if (rc.getNumAnchors(Anchor.STANDARD) != 0) {
                    // we have an anchor already
                    changeState(stateStack.pop());
                }
                break;
        }
    }

    public void changeState(State newState) throws GameActionException {
        currentState = newState;
    }

    public boolean buildCarrier(int carrierType) throws GameActionException {
        Debug.printString("BC " + carrierType);
        if (carrierType == -1) {
            Debug.println("INVALID CARRIER TYPE. Sending mana carrier instead");
            carrierType = Comms.HQFlag.CARRIER_MANA;
        }

        // get predetermined next carrier type and location
        MapLocation newLoc = getNextCarrierLocation(carrierType);

        if (newLoc != null && rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
            rc.buildRobot(RobotType.CARRIER, newLoc);
            carrierCount++;
            RobotInfo newCarrier = rc.senseRobotAtLocation(newLoc);
            if (newCarrier == null) {
                Debug.printString("ERROR: built carrier but can't sense it");
                return false;
            }

            nextFlag = carrierType;

            if (carrierType == Comms.HQFlag.CARRIER_MANA) {
                manaCarrierTracker.add(newCarrier.ID);
            } else {
                adamCarrierTracker.add(newCarrier.ID);
            }
            return true;
        }

        return false;
    }

    public boolean buildCarrier() throws GameActionException {
        // get predetermined next carrier type and location
        int carrierType = getNextCarrierType();
        return buildCarrier(carrierType);
    }

    public MapLocation getLauncherLocation() throws GameActionException {
        return getLauncherLocation(getCombatSector());
    }

    public MapLocation getLauncherLocation(MapLocation target) throws GameActionException {
        Direction dir = null;
        if (target == null) {
            target = closestEnemyHQGuess;
            if (closestEnemyHQGuess != null) {
                dir = closestEnemyHQGuessDir;
                // Debug.printString("HQ " + target + " Dir " + dir);
                return Util.findInitLocation(RobotType.LAUNCHER, dir);
            }
        }

        if (target == null) {
            // Debug.printString("No HQ");
            dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        } else {
            dir = getBestDirTo(target);
        }

        return Util.findInitLocation(RobotType.LAUNCHER, dir);
    }

    public MapLocation getDestabilizerLocation() throws GameActionException {
        return getDestabilizerLocation(getCombatSector());
    }

    public MapLocation getDestabilizerLocation(MapLocation target) throws GameActionException {
        Direction dir = null;
        if (target == null) {
            target = closestEnemyHQGuess;
            if (closestEnemyHQGuess != null) {
                dir = closestEnemyHQGuessDir;
                // Debug.printString("HQ " + target + " Dir " + dir);
                return Util.findInitLocation(RobotType.DESTABILIZER, dir);
            }
        }

        if (target == null) {
            // Debug.printString("No HQ");
            dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        } else {
            dir = getBestDirTo(target);
        }

        return Util.findInitLocation(RobotType.DESTABILIZER, dir);
    }

    public boolean buildLauncher(MapLocation newLoc) throws GameActionException {
        if (newLoc != null && rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
            Debug.printString("BL");
            rc.buildRobot(RobotType.LAUNCHER, newLoc);
            launcherCount++;
            return true;
        }
        return false;
    }

    public boolean buildDestabilizer(MapLocation newLoc) throws GameActionException {
        if (newLoc != null && rc.canBuildRobot(RobotType.DESTABILIZER, newLoc)) {
            Debug.printString("BD");
            rc.buildRobot(RobotType.DESTABILIZER, newLoc);
            return true;
        }
        return false;
    }

    // WARNING: If the cost of initCarriersWanted or initLaunchersWanted exceed
    // that of starting resources. We will get stuck in INIT until we passively
    // gain enough resources to build the troops.
    // TODO: Restructure?
    public void firstRounds() throws GameActionException {
        while (rc.isActionReady() && Clock.getBytecodesLeft() > 3000) {
            // We wait until turn 3 to build launchers because
            // symmetry isn't guessed until then.
            buildLauncher: if (launcherCount < initLaunchersWanted) {
                MapLocation locToBuild = getLauncherLocation();
                if (nearEnemyHQ) {
                    locToBuild = getLauncherLocation(enemyHQLoc);
                } else if (isSemiSmallMap()) {
                    locToBuild = getLauncherLocation(
                            new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2));
                } else if (closestEnemyHQGuess == null) {
                    break buildLauncher;
                }

                boolean built = buildLauncher(locToBuild);
                if (built)
                    continue;
            }

            // build carriers
            if (carrierCount < initCarriersWanted) {
                boolean built = buildCarrier();
                // If the next carrier is the same time, build another.
                int nextCarrierType = getNextCarrierType();
                if (built && nextFlag == nextCarrierType)
                    continue;
            }
            break;
        }
    }

    public void doStateAction() throws GameActionException {
        switch (currentState) {
            case INIT:
                firstRounds();
                break;
            case CHILLING:
                if (canBuildRobotType(RobotType.DESTABILIZER)) {
                    buildDestabilizer(getDestabilizerLocation());
                }
                if (canBuildRobotType(RobotType.LAUNCHER)) {
                    buildLauncher(getLauncherLocation());
                }
                if (canBuildRobotType(RobotType.CARRIER)) {
                    buildCarrier();
                }
                break;
            case BUILDING_ANCHOR:
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    // If we can build an anchor do it!
                    rc.buildAnchor(Anchor.STANDARD);
                    Debug.printString("Building anchor! ");
                    anchorCount++;
                    turnBuiltAnchor = rc.getRoundNum();
                    // Immediately exit so that a carrier taking the anchor
                    // on the same turn does not make us build another anchor.
                    changeState(stateStack.pop());
                } else {
                    if (canBuildRobotTypeAndAnchor(RobotType.DESTABILIZER, Anchor.STANDARD)) {
                        buildDestabilizer(getDestabilizerLocation());
                    } else  if (canBuildRobotTypeAndAnchor(RobotType.LAUNCHER, Anchor.STANDARD)) {
                        buildLauncher(getLauncherLocation());
                    } else if (canBuildRobotTypeAndAnchor(RobotType.CARRIER, Anchor.STANDARD)) {
                        buildCarrier();
                    }
                }
                break;
        }
    }

    /**
     * First rounds, find out our HQ number and set our location
     * 
     * @throws GameActionException
     */
    public void computeHqNum() throws GameActionException {
        // If all HQ locs are 0, then we are HQ 0.
        if (Comms.readOurHqAll(0) == 0 &&
                Comms.readOurHqAll(1) == 0 &&
                Comms.readOurHqAll(2) == 0 &&
                Comms.readOurHqAll(3) == 0) {
            Comms.writeOurHqLocation(0, rc.getLocation());
            myHqNum = 0;

            // Write a bad location in the rest of the HQ locations
            MapLocation badLocation = new MapLocation(
                    GameConstants.MAP_MAX_WIDTH + 1,
                    GameConstants.MAP_MAX_HEIGHT + 1);
            Comms.writeOurHqLocation(1, badLocation);
            Comms.writeOurHqLocation(2, badLocation);
            Comms.writeOurHqLocation(3, badLocation);

            Comms.initPrioritySectors();
            Comms.initSymmetry();

            numHqs = rc.getRobotCount();
            Comms.writeNumHqs(numHqs);
            return;
        }

        MapLocation lastHqLoc;
        for (int i = 0; i < GameConstants.MAX_STARTING_HEADQUARTERS; i++) {
            lastHqLoc = Comms.readOurHqLocation(i);
            if (!Util.onTheMap(lastHqLoc)) {
                Comms.writeOurHqLocation(i, rc.getLocation());
                myHqNum = i;
                break;
            }
        }

        numHqs = Comms.readNumHqs();
        if (myHqNum == numHqs - 1) {
            lastHq = true;
        }

        // Debug.println("I am HQ number " + myHqNum);
    }

    /**
     * Clears old ENEMY_PASSIVE or ENEMY_AGGRESIVE information from sectors
     * every Util.CLEAR_ENEMY_INFO_PERIOD rounds
     */
    public void clearOldEnemyInfo() throws GameActionException {
        for (int sectorIdx = rc.getRoundNum()
                % Util.CLEAR_ENEMY_INFO_PERIOD; sectorIdx < numSectors; sectorIdx += Util.CLEAR_ENEMY_INFO_PERIOD) {
            int controlStatus = Comms.readSectorControlStatus(sectorIdx);
            // Mocked controlStatusSet
            // We assume an island is neutral if we don't know
            // A friendly island will override a neutral island if one is found.
            if (controlStatus == Comms.ControlStatus.ENEMY_PASSIVE
                    || controlStatus == Comms.ControlStatus.ENEMY_AGGRESIVE) {
                int newControlStatus = Comms.readSectorIslands(sectorIdx) == 1 ? Comms.ControlStatus.NEUTRAL_ISLAND
                        : Comms.ControlStatus.EXPLORING;
                // Mark old combat sectors as need to be explored.
                // Debug.println("Clearing enemy info sector at : " + sectorCenters[sectorIdx]);
                Comms.writeSectorControlStatus(sectorIdx, newControlStatus);
            }
        }

        // Clear old combat sectors if they have no enemy info
        int[] combatSectorsWritten = combatSectorToTurnWritten.getKeys();
        for (int combatSectorIdx : combatSectorsWritten) {
            int turn = combatSectorToTurnWritten.getVal(combatSectorIdx);
            int sectorIdx = Comms.readCombatSectorIndex(combatSectorIdx);
            if (Comms.isEnemyControlStatus(Comms.readSectorControlStatus(sectorIdx))) {
                combatSectorToTurnWritten.addReplace(combatSectorIdx, rc.getRoundNum());
            } else if (turn + Util.CLEAR_COMBAT_SECTOR_TIMEOUT < rc.getRoundNum()) {
                Comms.writeCombatSectorIndex(combatSectorIdx, Comms.UNDEFINED_SECTOR_INDEX);
                combatSectorToTurnWritten.remove(combatSectorIdx);
                // Debug.println("Clearing combat sector at : " +
                // sectorCenters[combatSectorIdx]);
            }
        }
    }

    /**
     * Sets the priority sectors list
     * 
     * @throws GameActionException
     */
    public void setPrioritySectors() throws GameActionException {
        // Sectors aren't initialized until round 2
        if (rc.getRoundNum() == 1)
            return;

        int combatSectorIndex = getNextEmptyCombatSectorIdx(0);
        int exploreSectorIndex = getNextEmptyExploreSectorIdx(0);
        int mineSectorIndex = getNextEmptyMineSectorIdx(0);

        // Alternate sweeping each half of the sectors every turn
        int mode = (myHqNum + rc.getRoundNum()) % 3;
        int startIdx = 0;
        int endIdx = 0;
        switch (mode) {
            case 0:
                startIdx = 0;
                endIdx = numSectors / 3;
                break;
            case 1:
                startIdx = numSectors / 3;
                endIdx = numSectors * 2 / 3;
                break;
            case 2:
                startIdx = numSectors * 2 / 3;
                endIdx = numSectors;
                break;
            default:
                Debug.println("[Error] Unexpected case in setPriorityQueue!");
        }

        for (int i = startIdx; i < endIdx; i++) {
            int controlStatus = Comms.readSectorControlStatus(i);
            // Combat sector
            combatSector: if (combatSectorIndex < Comms.COMBAT_SECTOR_SLOTS
                    && Comms.isEnemyControlStatus(controlStatus)) {
                // If the sector is already a combat sector, don't add it again
                for (int j = Comms.COMBAT_SECTOR_SLOTS - 1; j >= 0; j--) {
                    if (Comms.readCombatSectorIndex(j) == i) {
                        break combatSector;
                    }
                }

                Comms.writeCombatSectorIndex(combatSectorIndex, i);
                Comms.writeCombatSectorClaimStatus(combatSectorIndex, Comms.ClaimStatus.UNCLAIMED);
                combatSectorToTurnWritten.add(combatSectorIndex, rc.getRoundNum());
                // Comms.writeCombatSectorTurn(combatSectorIndex, rc.getRoundNum());
                combatSectorIndex = getNextEmptyCombatSectorIdx(combatSectorIndex + 1);
            }
            // Explore sector
            exploreSector: if (exploreSectorIndex < Comms.EXPLORE_SECTOR_SLOTS
                    && controlStatus == Comms.ControlStatus.EXPLORING) {
                // If the sector is already a explore sector, don't add it again
                for (int j = Comms.EXPLORE_SECTOR_SLOTS - 1; j >= 0; j--) {
                    if (Comms.readExploreSectorIndex(j) == i) {
                        break exploreSector;
                    }
                }

                Comms.writeExploreSectorIndex(exploreSectorIndex, i);
                Comms.writeExploreSectorClaimStatus(exploreSectorIndex, Comms.ClaimStatus.UNCLAIMED);
                exploreSectorIndex = getNextEmptyExploreSectorIdx(exploreSectorIndex + 1);
            }
            // Mine sector
            mineSector: if (mineSectorIndex < Comms.MINE_SECTOR_SLOTS &&
                    Comms.readSectorAdamantiumFlag(i) == 1 ||
                    Comms.readSectorManaFlag(i) == 1 /*
                                                      * ||
                                                      * Comms.readSectorElixirFlag(i) == 1
                                                      */) {
                // If the sector is already a mine sector, don't add it again
                for (int j = Comms.MINE_SECTOR_SLOTS - 1; j >= 0; j--) {
                    if (Comms.readMineSectorIndex(j) == i) {
                        break mineSector;
                    }
                }

                Comms.writeMineSectorIndex(mineSectorIndex, i);
                mineSectorIndex = getNextEmptyMineSectorIdx(mineSectorIndex + 1);
            }
        }
    }

    /**
     * Only checks if you have the resources for the robot type.
     * Does not check action cooldown
     */
    public boolean canBuildRobotType(RobotType robotType) {
        return hasResources(robotType.buildCostMana,
                robotType.buildCostAdamantium,
                robotType.buildCostElixir);
    }

    public boolean canBuildRobotTypeAndAnchor(RobotType robotType, Anchor anchor) {
        return hasResources(robotType.buildCostMana + anchor.manaCost,
                robotType.buildCostAdamantium + anchor.adamantiumCost,
                robotType.buildCostElixir + anchor.elixirCost, anchor);
    }

    public void displayCombatSectors() throws GameActionException {
        for (int i = 0; i < Comms.COMBAT_SECTOR_SLOTS; i++) {
            int sector = Comms.readCombatSectorIndex(i);
            if (sector == Comms.UNDEFINED_SECTOR_INDEX)
                continue;
            MapLocation loc = sectorCenters[sector];
            rc.setIndicatorDot(loc, 255, 0, 0);
        }
    }

    public void displayExploreSectors() throws GameActionException {
        for (int i = 0; i < Comms.EXPLORE_SECTOR_SLOTS; i++) {
            int sector = Comms.readExploreSectorIndex(i);
            if (sector == Comms.UNDEFINED_SECTOR_INDEX)
                continue;
            MapLocation loc = sectorCenters[sector];
            rc.setIndicatorDot(loc, 0, 0, 255);
        }
    }

    public void displayMineSectors() throws GameActionException {
        for (int i = 0; i < Comms.MINE_SECTOR_SLOTS; i++) {
            int sector = Comms.readMineSectorIndex(i);
            if (sector == Comms.UNDEFINED_SECTOR_INDEX)
                continue;
            MapLocation loc = sectorCenters[sector];
            rc.setIndicatorDot(loc, 100, 200, 50);
        }
    }

    /**
     * Sets initial explore sectors to center and symmetry locs
     */
    public void setInitialExploreSectors() throws GameActionException {
        int exploreSectorIndex = getNextEmptyExploreSectorIdx(0);

        // Add the center and the all the symmetry locations
        MapLocation center = new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2);
        int[] sectors = new int[enemyHQs.length + 1];
        sectors[0] = whichSector(center);
        for (int i = 0; i < enemyHQs.length; i++)
            sectors[i + 1] = whichSector(enemyHQs[i]);

        for (int i = 0; i < enemyHQs.length + 1; i++) {
            int sector = sectors[i];
            int controlStatus = Comms.readSectorControlStatus(sector);

            if (exploreSectorIndex < Comms.EXPLORE_SECTOR_SLOTS
                    && controlStatus == Comms.ControlStatus.UNKNOWN) {
                Comms.writeExploreSectorIndex(exploreSectorIndex, sector);
                Comms.writeExploreSectorClaimStatus(exploreSectorIndex, Comms.ClaimStatus.UNCLAIMED);
                Comms.writeSectorControlStatus(sector, Comms.ControlStatus.EXPLORING);
                // Debug.println("Added explore sector: " + sectorCenters[sector] + " at index:
                // " + exploreSectorIndex);
                exploreSectorIndex = getNextEmptyExploreSectorIdx(exploreSectorIndex + 1);
            }
        }
    }

    public MapLocation findNextExploreInitLoc() {
        for (int i = 0; i < exploreDirections.length; i++) {
            if (exploreDirections[i] == null)
                continue;

            MapLocation[] exploreLocs = Util.getInitLocs(exploreDirections[i]);
            for (int j = 0; j < exploreLocs.length; j++) {
                if (rc.canBuildRobot(RobotType.CARRIER, exploreLocs[j])) {
                    exploreDirections[i] = null;
                    return exploreLocs[j];
                }
            }
        }

        return null;
    }

    public boolean shouldMakeElixir() {
        return Util.MAP_AREA >= Util.MIN_MAP_AREA_FOR_ELIXIR &&
                rc.getRoundNum() >= Util.MIN_ROUND_NUM_FOR_ELIXIR;
    }

    // Choose the mana well that is closest to an adamantium well.
    // Break ties by avg adamantium well distance
    public int getBestElixirWellCandidate() throws GameActionException {
        // Only let the first HQ decide the elixir well
        if (myHqNum != 0)
            return Comms.UNDEFINED_SECTOR_INDEX;

        int numManaSectors = 0;
        MapLocation[] manaSectors = new MapLocation[Comms.MINE_SECTOR_SLOTS];
        int numAdamSectors = 0;
        MapLocation[] adamSectors = new MapLocation[Comms.MINE_SECTOR_SLOTS];

        for (int i = 0; i < Comms.MINE_SECTOR_SLOTS; i++) {
            int sector = Comms.readMineSectorIndex(i);
            if (sector == Comms.UNDEFINED_SECTOR_INDEX)
                continue;

            if (Comms.readSectorManaFlag(sector) == 1) {
                manaSectors[numManaSectors] = sectorCenters[sector];
                numManaSectors++;
            } else if (Comms.readSectorAdamantiumFlag(sector) == 1) {
                adamSectors[numAdamSectors] = sectorCenters[sector];
                numAdamSectors++;
            }
        }

        int bestDistance = Integer.MAX_VALUE;
        double bestAvgDistance = Double.MAX_VALUE;
        MapLocation bestSector = null;
        int closestAdamDist;
        int totalDistance;
        double avgDistance;
        for (int i = 0; i < numManaSectors; i++) {
            totalDistance = 0;
            closestAdamDist = Integer.MAX_VALUE;
            for (int j = 0; j < numAdamSectors; j++) {
                totalDistance += manaSectors[i].distanceSquaredTo(adamSectors[j]);
                closestAdamDist = Math.min(closestAdamDist, manaSectors[i].distanceSquaredTo(adamSectors[j]));
            }
            avgDistance = (double) totalDistance / numAdamSectors;
            if (totalDistance < bestDistance || (totalDistance == bestDistance && avgDistance < bestAvgDistance)) {
                bestDistance = totalDistance;
                bestAvgDistance = avgDistance;
                bestSector = manaSectors[i];
            }
        }

        if (bestSector == null)
            return Comms.UNDEFINED_SECTOR_INDEX;

        return whichSector(bestSector);
    }

    public void writeElixirWellCandidate() throws GameActionException {
        if (!shouldMakeElixir())
            return;
        if (Comms.readElixirSectorIndex() != Comms.UNDEFINED_SECTOR_INDEX)
            return;
        int bestElixirWellCandidate = getBestElixirWellCandidate();
        if (bestElixirWellCandidate != Comms.UNDEFINED_SECTOR_INDEX) {
            Comms.writeElixirSectorIndex(bestElixirWellCandidate);
        }
    }
}
