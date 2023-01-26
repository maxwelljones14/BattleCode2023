package MPEffort;

import battlecode.common.*;
import MPEffort.Util.*;
import MPEffort.Comms.*;
import MPEffort.Debug.*;
import java.util.ArrayDeque;

import MPEffort.fast.*;

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
    static final int initLaunchersWanted = 3;
    static final int minCarriersBeforeAnchor = 20;

    static MapLocation currLoc;

    static int nextFlag;

    static MapLocation closestEnemyHQGuess;
    static Direction closestEnemyHQGuessDir;

    static MapLocation nearestAdWell;
    static MapLocation nearestMnWell;
    static Direction nearestAdWellDir;
    static Direction nearestMnWellDir;
    static MapLocation nearestAdWellSector;
    static MapLocation nearestMnWellSector;

    static MapLocation[] locsToBuildCarriers;
    static MapLocation[] locsToBuildLaunchers;

    static final double MANA_TO_ADAM_CARRIER_RATIO = 0.7 / 0.3;
    static final int ROUNDS_TO_STALE_UNIT = 50;
    FastUnitTracker adamCarrierTracker;
    FastUnitTracker manaCarrierTracker;

    static boolean nearHQ;
    static MapLocation enemyHQLoc;

    static FastIntIntMap combatSectorToTurnWritten;

    public Headquarters(RobotController r) throws GameActionException {
        super(r);
        checkIfHQNear();
        stateStack = new ArrayDeque<State>();
        currentState = State.INIT;
        currLoc = rc.getLocation();

        if (numHqs == -1)
            numHqs = rc.getRobotCount();
        initSectorPermutation();

        nearestAdWell = null;
        nearestMnWell = null;
        nearestAdWellDir = null;
        nearestMnWellDir = null;
        locsToBuildCarriers = new MapLocation[initCarriersWanted];
        locsToBuildLaunchers = new MapLocation[initLaunchersWanted];

        carrierCount = 0;
        launcherCount = 0;
        anchorCount = 0;
        nextFlag = 0;

        adamCarrierTracker = new FastUnitTracker(rc, ROUNDS_TO_STALE_UNIT);
        manaCarrierTracker = new FastUnitTracker(rc, ROUNDS_TO_STALE_UNIT);

        combatSectorToTurnWritten = new FastIntIntMap();
    }

    public void localResign() {
        if (rc.getRoundNum() >= 400 && rc.getRobotCount() <= numHqs)
            rc.resign();
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // if (rc.getRoundNum() == 20) {
        // rc.resign();
        // }
        localResign();
        adamCarrierTracker.update();
        manaCarrierTracker.update();
        clearOldEnemyInfo();

        switch (rc.getRoundNum()) {
            case 1:
                computeHqNum();
                findClosestVisibleWells();
                break;
            case 2:
                loadHQLocations();
                updateSymmetryLocs();
                invalidateSymmetries();
                closestEnemyHQGuess = getClosestEnemyHQGuess();
                closestEnemyHQGuessDir = getBestDirTo(closestEnemyHQGuess);
                break;
            case 3:
                // We do this again on turn 3 so that all HQs
                // have a chance to invalidate symmetries.
                closestEnemyHQGuess = getClosestEnemyHQGuess();
                closestEnemyHQGuessDir = getBestDirTo(closestEnemyHQGuess);
                setInitialExploreSectors();
                break;
            default:
                break;
        }

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

    public void checkIfHQNear() throws GameActionException {
        EnemySensable = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        for (int x = 0; x < EnemySensable.length; x++) {
            RobotInfo enemy = EnemySensable[x];
            if (enemy.getType() == RobotType.HEADQUARTERS) {
                nearHQ = true;
                enemyHQLoc = enemy.getLocation();
                return;
            }
        }
        nearHQ = false;
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
                nearestAdWell = well.getMapLocation();
            } else if (well.getResourceType() == ResourceType.MANA && dist <= closestMnWellDistance) {
                closestMnWellDistance = dist;
                nearestMnWell = well.getMapLocation();
            }
        }

        // Immediately write these to the mine sectors
        int mineSectorIndex = getNextEmptyMineSectorIdx(0);
        if (nearestAdWell != null) {
            int sectorIdx = whichSector(nearestAdWell);
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

        if (nearestMnWell != null) {
            int sectorIdx = whichSector(nearestMnWell);
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

    public int getNextCarrierType() throws GameActionException {
        int carrierType;
        if (currentState == State.INIT) {
            if (nearestMnWell == null) {
                // if theres an mn but not an ad, the first 2 should be mn and the second should
                // be ad
                if (carrierCount < 2) {
                    // next should be an mn carrier
                    carrierType = Comms.HQFlag.CARRIER_ADAMANTIUM;
                } else {
                    carrierType = Comms.HQFlag.CARRIER_MANA;
                }
            } else {
                // if theres both ad and mn, or neither, then first 2 should be ad and second 2
                // should be mn
                if (Util.MAP_AREA <= Util.MAX_AREA_FOR_FAST_INIT) {
                    carrierType = Comms.HQFlag.CARRIER_MANA;
                } else {
                    if (carrierCount < 3) {
                        // next should be an ad carrier
                        carrierType = Comms.HQFlag.CARRIER_MANA;
                    } else {
                        // next should be an mn carrier
                        carrierType = Comms.HQFlag.CARRIER_ADAMANTIUM;
                    }
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

    public Direction getBestDirTo(MapLocation loc) throws GameActionException {
        Direction dir = Nav.getBestDir(loc, 5000);
        if (dir == Direction.CENTER) {
            dir = home.directionTo(loc);
        }
        return dir;
    }

    public MapLocation getNextCarrierLocation(int carrierType) throws GameActionException {
        /*
         * 1. figure out next carrier type
         * in firstRounds make the first 2 AD and the second 2 MN
         * otherwise copy the buildCarrier logic
         * 
         * 2. based on this, find closest of that well
         * 3. get locations for this using Util.findInitLocation(currLoc, dir from 2)
         */

        Direction dirToBuild = null;
        if (carrierType == Comms.HQFlag.CARRIER_ADAMANTIUM) {
            if (nearestAdWell != null) {
                if (nearestAdWellDir == null) {
                    nearestAdWellDir = getBestDirTo(nearestAdWell);
                }
                dirToBuild = nearestAdWellDir;
            } else {
                // look in sectors for nearest ad well
                int mineSectorIndex = getNearestMineSectorIdx(ResourceType.ADAMANTIUM, null);
                MapLocation adWellSector = mineSectorIndex == Comms.UNDEFINED_SECTOR_INDEX ? null
                        : sectorCenters[mineSectorIndex];
                if (adWellSector != null) {
                    if (nearestAdWellSector == null || !nearestAdWellSector.equals(adWellSector)) {
                        nearestAdWellSector = adWellSector;
                        nearestAdWellDir = getBestDirTo(nearestAdWellSector);
                    }
                    dirToBuild = nearestAdWellDir;
                } else {
                    dirToBuild = Util.directions[Util.rng.nextInt(Util.directions.length)];
                }
            }
        } else {
            if (nearestMnWell != null) {
                if (nearestMnWellDir == null) {
                    nearestMnWellDir = getBestDirTo(nearestMnWell);
                }
                dirToBuild = nearestMnWellDir;
            } else {
                // look in sectors for nearest mn well
                int mineSectorIndex = getNearestMineSectorIdx(ResourceType.MANA, null);
                MapLocation mnWellSector = mineSectorIndex == Comms.UNDEFINED_SECTOR_INDEX ? null
                        : sectorCenters[mineSectorIndex];
                if (mnWellSector != null) {
                    if (nearestMnWellSector == null || !nearestMnWellSector.equals(mnWellSector)) {
                        nearestMnWellSector = mnWellSector;
                        nearestMnWellDir = getBestDirTo(nearestMnWellSector);
                    }
                    dirToBuild = nearestMnWellDir;
                } else {
                    dirToBuild = Util.directions[Util.rng.nextInt(Util.directions.length)];
                }
            }
        }
        return Util.findInitLocation(currLoc, dirToBuild);
    }

    public void toggleState() throws GameActionException {
        switch (currentState) {
            case INIT:
                if (carrierCount >= initCarriersWanted && launcherCount >= initLaunchersWanted) {
                    changeState(State.CHILLING);
                }
                break;
            case CHILLING:
                if (carrierCount >= minCarriersBeforeAnchor && rc.getNumAnchors(Anchor.STANDARD) == 0) {
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

    public void buildCarrier(int carrierType) throws GameActionException {
        Debug.printString("BC " + carrierType);

        // get predetermined next carrier type and location
        MapLocation newLoc = getNextCarrierLocation(carrierType);

        if (newLoc != null && rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
            rc.buildRobot(RobotType.CARRIER, newLoc);
            carrierCount++;
            RobotInfo newCarrier = rc.senseRobotAtLocation(newLoc);
            if (newCarrier == null) {
                Debug.printString("ERROR: built carrier but can't sense it");
                return;
            }

            nextFlag = carrierType;

            if (carrierType == Comms.HQFlag.CARRIER_MANA) {
                manaCarrierTracker.add(newCarrier.ID);
            } else {
                adamCarrierTracker.add(newCarrier.ID);
            }
        }
    }

    public void buildCarrier() throws GameActionException {
        // get predetermined next carrier type and location
        int carrierType = getNextCarrierType();
        buildCarrier(carrierType);
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
                Debug.printString("HQ " + target + " Dir " + dir);
                return Util.findInitLocation(currLoc, dir);
            }
        }

        if (target == null) {
            Debug.printString("No HQ");
            dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        } else {
            dir = getBestDirTo(target);
        }

        return Util.findInitLocation(currLoc, dir);
    }

    public void buildLauncher(MapLocation newLoc) throws GameActionException {
        Debug.printString("BL");
        if (newLoc != null && rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
            rc.buildRobot(RobotType.LAUNCHER, newLoc);
            launcherCount++;
        }
    }

    // WARNING: If the cost of initCarriersWanted or initLaunchersWanted exceed
    // that of starting resources. We will get stuck in INIT until we passively
    // gain enough resources to build the troops.
    // TODO: Restructure?
    public void firstRounds() throws GameActionException {
        while (rc.isActionReady()) {
            // We wait until turn 3 to build launchers because
            // symmetry isn't guessed until then.
            buildLauncher: if (launcherCount < initLaunchersWanted) {
                MapLocation locToBuild = getLauncherLocation();
                if (nearHQ) {
                    locToBuild = getLauncherLocation(enemyHQLoc);
                } else if (isSemiSmallMap()) {
                    locToBuild = getLauncherLocation(
                            new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2));
                } else if (rc.getRoundNum() < 3) {
                    break buildLauncher;
                }

                buildLauncher(locToBuild);
                continue;
            }

            // build carriers
            if (carrierCount < initCarriersWanted) {
                buildCarrier();
                // We don't build more than one carrier at a time so that
                // we can communicate the correct resource type it should be.
                break;
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
                if (canBuildRobotType(RobotType.LAUNCHER)) {
                    buildLauncher(getLauncherLocation());
                }
                if (canBuildRobotType(RobotType.CARRIER)) {
                    buildCarrier();
                }
                break;
            case BUILDING_ANCHOR:
                // make 5 carriers and then switch to anchor mode
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    // If we can build an anchor do it!
                    rc.buildAnchor(Anchor.STANDARD);
                    Debug.printString("Building anchor! ");
                    anchorCount++;
                } else {
                    if (canBuildRobotTypeAndAnchor(RobotType.LAUNCHER, Anchor.STANDARD)) {
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
            // We assume an island is friendly if we don't know
            // A neutral/enemy island will override a friendly island if one is found.
            if (controlStatus == Comms.ControlStatus.ENEMY_PASSIVE
                    || controlStatus == Comms.ControlStatus.ENEMY_AGGRESIVE) {
                int newControlStatus = Comms.readSectorIslands(sectorIdx) == 1 ? Comms.ControlStatus.FRIENDLY_ISLAND
                        : Comms.ControlStatus.EXPLORING;
                // Mark old combat sectors as need to be explored.
                // Debug.println("Clearing combat sector at : " + sectorCenters[sectorIdx]);
                Comms.writeSectorControlStatus(sectorIdx, newControlStatus);
            }
        }

        // Clear old combat sectors
        int[] combatSectorsWritten = combatSectorToTurnWritten.getKeys();
        for (int combatSectorIdx : combatSectorsWritten) {
            int turn = combatSectorToTurnWritten.getVal(combatSectorIdx);
            if (turn + Util.CLEAR_COMBAT_SECTOR_TIMEOUT < rc.getRoundNum()) {
                Comms.writeCombatSectorIndex(combatSectorIdx, Comms.UNDEFINED_SECTOR_INDEX);
                combatSectorToTurnWritten.remove(combatSectorIdx);
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
                    && controlStatus >= Comms.ControlStatus.MIN_ENEMY_STATUS) {
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
}
