package MPCombat;

import battlecode.common.*;
import MPCombat.Util.*;
import MPCombat.Comms.*;
import MPCombat.Debug.*;
import java.util.ArrayDeque;

import MPCombat.fast.*;

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

    static MapLocation nearestAdWell;
    static MapLocation nearestMnWell;
    static MapLocation[] locsToBuildCarriers;
    static MapLocation[] locsToBuildLaunchers;

    static final double MANA_TO_ADAM_CARRIER_RATIO = 0.7 / 0.3;
    static final int ROUNDS_TO_STALE_UNIT = 50;
    FastUnitTracker adamCarrierTracker;
    FastUnitTracker manaCarrierTracker;

    public Headquarters(RobotController r) throws GameActionException {
        super(r);
        stateStack = new ArrayDeque<State>();
        currentState = State.INIT;
        currLoc = rc.getLocation();

        if (numHqs == -1)
            numHqs = rc.getRobotCount();
        initSectorPermutation();

        nearestAdWell = null;
        nearestMnWell = null;
        locsToBuildCarriers = new MapLocation[initCarriersWanted];
        locsToBuildLaunchers = new MapLocation[initLaunchersWanted];
        setupNearestWellsAndSpawnLocs();

        carrierCount = 0;
        launcherCount = 0;
        anchorCount = 0;
        nextFlag = 0;

        adamCarrierTracker = new FastUnitTracker(rc, ROUNDS_TO_STALE_UNIT);
        manaCarrierTracker = new FastUnitTracker(rc, ROUNDS_TO_STALE_UNIT);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        // if (rc.getRoundNum() == 20) {
        // rc.resign();
        // }
        adamCarrierTracker.update();
        manaCarrierTracker.update();
        computeHqNum();
        clearOldEnemyInfo();
        setPrioritySectors();
        updateclosestEnemyHQ();
        toggleState();
        Debug.printString("current state: " + currentState);
        Debug.printString("A: " + adamCarrierTracker.size());
        Debug.printString("M: " + manaCarrierTracker.size());
        Comms.writeOurHqFlag(myHqNum, nextFlag);
        nextFlag = 0;
        doStateAction();
        // findWells();
        // printEnemySectors();
        // printCombatSectors();
        // printEnemyCombatSectors();
    }

    public void updateclosestEnemyHQ() throws GameActionException {
        if (rc.getRoundNum() == 2) {
            closestEnemyHQGuess = getClosestEnemyHQGuess();
        }
    }

    public void setupNearestWellsAndSpawnLocs() throws GameActionException {
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

        /*
         * set up 4 locations to build carriers with priority of:
         * 1. on nearest ad well
         * 2. next to nearest ad well
         * 3. on nearest mn well
         * 4. next to nearest mn well
         * 5. in the 4 cardinal directions as far away from us as possible
         */
        int i = 0;
        if (nearestAdWell != null) {
            MapLocation adWellLocInsideActionRadius = nearestAdWell;
            while (adWellLocInsideActionRadius.distanceSquaredTo(currLoc) > actionRadiusSquared) {
                adWellLocInsideActionRadius = Util.moveTowardsMe(adWellLocInsideActionRadius);
            }
            locsToBuildCarriers[i] = adWellLocInsideActionRadius;
            i++;
            // find location closest to carrier that i can build but 1 square towards me
            // each time
            MapLocation translated = Util.moveTowardsMe(adWellLocInsideActionRadius);
            while (!rc.sensePassability(translated)) {
                translated = Util.moveTowardsMe(translated);
            }
            locsToBuildCarriers[i] = translated;
            i++;
        }
        if (nearestMnWell != null) {
            MapLocation mnWellLocInsideActionRadius = nearestMnWell;
            while (mnWellLocInsideActionRadius.distanceSquaredTo(currLoc) > actionRadiusSquared) {
                mnWellLocInsideActionRadius = Util.moveTowardsMe(mnWellLocInsideActionRadius);
            }
            locsToBuildCarriers[i] = mnWellLocInsideActionRadius;
            i++;
            // find location closest to carrier that i can build but 1 square towards me
            // each time
            MapLocation translated = Util.moveTowardsMe(mnWellLocInsideActionRadius);
            while (!rc.sensePassability(translated)) {
                translated = Util.moveTowardsMe(translated);
            }
            locsToBuildCarriers[i] = translated;
            i++;
        }
        // cardinal directions if locations array not full yet
        MapLocation startingRandomLocation = currLoc.translate((int) (Math.sqrt(actionRadiusSquared)), 0);
        while (i < initCarriersWanted) {
            MapLocation candidate = startingRandomLocation;
            while (!rc.onTheMap(candidate) || !rc.sensePassability(candidate)) {
                candidate = Util.moveTowardsMe(candidate);
            }
            locsToBuildCarriers[i] = candidate;
            i++;
            startingRandomLocation = Util.rotateLoc90(startingRandomLocation);
        }

        int j = 0;
        startingRandomLocation = currLoc.translate((int) (Math.sqrt(actionRadiusSquared)) - 1,
                (int) (Math.sqrt(actionRadiusSquared)) - 1);
        while (j < initLaunchersWanted) {
            MapLocation candidate = startingRandomLocation;
            while (!rc.onTheMap(candidate) || !rc.sensePassability(candidate)) {
                candidate = Util.moveTowardsMe(candidate);
            }
            locsToBuildLaunchers[j] = candidate;
            j++;
            startingRandomLocation = Util.rotateLoc90(startingRandomLocation);
        }
    }

    public void toggleState() throws GameActionException {
        switch (currentState) {
            case INIT:
                if (carrierCount >= initCarriersWanted && launcherCount >= initLaunchersWanted) {
                    changeState(State.CHILLING);
                }
            case CHILLING:
                if (carrierCount >= minCarriersBeforeAnchor && rc.getAnchor() == null) {
                    // start saving for anchor if you have minCarriersBeforeAnchor carriers already
                    stateStack.push(currentState);
                    changeState(State.BUILDING_ANCHOR);
                }
                break;
            case BUILDING_ANCHOR:
                if (rc.getAnchor() != null) {
                    // we have an anchor already
                    changeState(stateStack.pop());
                }
                break;
        }
    }

    public void changeState(State newState) throws GameActionException {
        currentState = newState;
    }

    public void buildCarrier(MapLocation newLoc) throws GameActionException {
        Debug.printString("Trying to build a carrier");
        if (rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
            rc.buildRobot(RobotType.CARRIER, newLoc);
            carrierCount++;
            RobotInfo newCarrier = rc.senseRobotAtLocation(newLoc);
            if (newCarrier == null) {
                Debug.printString("ERROR: built carrier but can't sense it");
                return;
            }

            if (currentState == State.INIT) {
                if (nearestMnWell != null && nearestAdWell == null) {
                    // if theres an mn but not an ad, the first 2 should be mn and the second should
                    // be ad
                    if (carrierCount <= 2) {
                        // next should be an mn carrier
                        nextFlag = Comms.HQFlag.CARRIER_MANA;
                    } else {
                        nextFlag = Comms.HQFlag.CARRIER_ADAMANTIUM;
                    }
                } else {
                    // if theres both ad and mn, or neither, then first 2 should be ad and second 2
                    // should be mn
                    if (carrierCount <= 2) {
                        // next should be an ad carrier
                        nextFlag = Comms.HQFlag.CARRIER_ADAMANTIUM;
                    } else {
                        // next should be an mn carrier
                        nextFlag = Comms.HQFlag.CARRIER_MANA;
                    }
                }
            } else if (manaCarrierTracker.size() <= adamCarrierTracker.size() * MANA_TO_ADAM_CARRIER_RATIO) {
                nextFlag = Comms.HQFlag.CARRIER_MANA;
            } else {
                nextFlag = Comms.HQFlag.CARRIER_ADAMANTIUM;
            }

            if (nextFlag == Comms.HQFlag.CARRIER_MANA) {
                manaCarrierTracker.add(newCarrier.ID);
            } else {
                adamCarrierTracker.add(newCarrier.ID);
            }
        }
    }

    public MapLocation getLauncherLocation() throws GameActionException {
        MapLocation target = getCombatSector();
        Direction dir = null;
        if (target == null) {
            target = closestEnemyHQGuess;
            Debug.printString("dir of HQ " + target);
        }
        if (target == null) {
            Debug.printString("ERROR: no HQ guesses");
            dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        } else {
            dir = currLoc.directionTo(target);
        }
        MapLocation newLoc = rc.getLocation().add(dir).add(dir);
        int count = 0;
        while (!rc.onTheMap(newLoc) || !rc.sensePassability(newLoc)) {
            newLoc = Util.moveTowardsMe(newLoc);
            count++;
            if (count >= 4) {
                break;
            }
        }
        return newLoc;
    }

    public void buildLauncher(MapLocation newLoc) throws GameActionException {
        Debug.printString("Trying to build a launcher");
        if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
            rc.buildRobot(RobotType.LAUNCHER, newLoc);
            launcherCount++;
        }
    }

    public void firstRounds() throws GameActionException {
        // build carriers
        if (carrierCount < initCarriersWanted) {
            MapLocation locToBuild = locsToBuildCarriers[carrierCount];
            for (int rot = 0; rot < 4; rot++) {
                if (rc.canBuildRobot(RobotType.CARRIER, locToBuild)) {
                    buildCarrier(locToBuild);
                    break;
                } else if (rc.isActionReady()) {
                    locToBuild = Util.rotateLoc90(locToBuild);
                }
            }
            return;
        }

        // set up locations for first launchers in the 4 cardinal directions
        if (launcherCount < initLaunchersWanted) {
            MapLocation locToBuild = getLauncherLocation(); // locsToBuildLaunchers[launcherCount];
            for (int rot = 0; rot < 4; rot++) {
                if (rc.canBuildRobot(RobotType.LAUNCHER, locToBuild)) {
                    buildLauncher(locToBuild);
                    break;
                } else if (rc.isActionReady()) {
                    locToBuild = Util.rotateLoc90(locToBuild);
                }
            }
            return;
        }
    }

    public void doStateAction() throws GameActionException {
        Direction dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        // spawn as far away from us as possible
        MapLocation newLoc = rc.getLocation().add(dir).add(dir);
        int count = 0;
        while (!rc.onTheMap(newLoc) || !rc.sensePassability(newLoc)) {
            count++;
            newLoc = Util.moveTowardsMe(newLoc);
            if (newLoc.equals(currLoc)) {
                dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
                newLoc = rc.getLocation().add(dir).add(dir);
            }
            if (count >= 4) {
                break;
            }
        }
        switch (currentState) {
            case INIT:
                firstRounds();
                break;
            case CHILLING:
                if (canBuildRobotType(RobotType.LAUNCHER)) {
                    buildLauncher(getLauncherLocation());
                }
                if (canBuildRobotType(RobotType.CARRIER)) {
                    buildCarrier(newLoc);
                }
                break;
            case BUILDING_ANCHOR:
                // make 5 carriers and then switch to anchor mode
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    // If we can build an anchor do it!
                    rc.buildAnchor(Anchor.STANDARD);
                    Debug.printString("Building anchor! " + rc.getAnchor());
                    anchorCount++;
                } else {
                    if (canBuildRobotTypeAndAnchor(RobotType.LAUNCHER, Anchor.STANDARD)) {
                        buildLauncher(getLauncherLocation());
                    } else if (canBuildRobotTypeAndAnchor(RobotType.CARRIER, Anchor.STANDARD)) {
                        buildCarrier(newLoc);
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
        // On round 1, write a bad location to all HQ locations
        if (rc.getRoundNum() == 1) {
            MapLocation badLocation = new MapLocation(
                    GameConstants.MAP_MAX_WIDTH + 1,
                    GameConstants.MAP_MAX_HEIGHT + 1);
            for (int i = 0; i < GameConstants.MAX_STARTING_HEADQUARTERS; i++) {
                Comms.writeOurHqLocation(i, badLocation);
            }
            return;
        }

        if (myHqNum >= 0) {
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

        if (myHqNum == 0) {
            Comms.initPrioritySectors();
        }

        if (myHqNum == numHqs - 1) {
            lastHq = true;
        }

        Debug.println("I am HQ number " + myHqNum);
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
                        : Comms.ControlStatus.UNKNOWN;
                Comms.writeSectorControlStatus(sectorIdx, newControlStatus);
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

        int combatSectorIndex = 0;
        int exploreSectorIndex = 0;

        // Preserve combat sectors which still have enemies or are claimed
        while (combatSectorIndex < Comms.COMBAT_SECTOR_SLOTS) {
            int sector = Comms.readCombatSectorIndex(combatSectorIndex);
            if (sector == Comms.UNDEFINED_SECTOR_INDEX) {
                break;
            }
            if (Comms.readSectorControlStatus(sector) < Comms.ControlStatus.MIN_ENEMY_STATUS &&
                    Comms.readCombatSectorClaimStatus(sector) == Comms.ClaimStatus.UNCLAIMED) {
                break;
            }
            combatSectorIndex++;
        }

        // Preserve explore sectors which still have not been claimed
        while (exploreSectorIndex < Comms.EXPLORE_SECTOR_SLOTS
                && Comms.readExploreSectorIndex(exploreSectorIndex) != Comms.UNDEFINED_SECTOR_INDEX
                && Comms.readExploreSectorClaimStatus(exploreSectorIndex) == Comms.ClaimStatus.UNCLAIMED) {
            exploreSectorIndex++;
        }

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
                combatSectorIndex++;

                // Preserve combat sectors which still have enemies or are claimed
                while (combatSectorIndex < Comms.COMBAT_SECTOR_SLOTS) {
                    int sector = Comms.readCombatSectorIndex(combatSectorIndex);
                    if (sector == Comms.UNDEFINED_SECTOR_INDEX) {
                        break;
                    }
                    if (Comms.readSectorControlStatus(sector) < Comms.ControlStatus.MIN_ENEMY_STATUS &&
                            Comms.readCombatSectorClaimStatus(sector) == Comms.ClaimStatus.UNCLAIMED) {
                        break;
                    }
                    combatSectorIndex++;
                }
            }
            // Explore sector
            exploreSector: if (exploreSectorIndex < Comms.EXPLORE_SECTOR_SLOTS
                    && controlStatus == Comms.ControlStatus.UNKNOWN) {
                // If the sector is already a explore sector, don't add it again
                for (int j = Comms.EXPLORE_SECTOR_SLOTS - 1; j >= 0; j--) {
                    if (Comms.readExploreSectorIndex(j) == i) {
                        break exploreSector;
                    }
                }

                Comms.writeExploreSectorIndex(exploreSectorIndex, i);
                Comms.writeExploreSectorClaimStatus(exploreSectorIndex, Comms.ClaimStatus.UNCLAIMED);
                exploreSectorIndex++;

                // Preserve explore sectors which still have not been claimed
                while (exploreSectorIndex < Comms.EXPLORE_SECTOR_SLOTS
                        && Comms.readExploreSectorIndex(exploreSectorIndex) != Comms.UNDEFINED_SECTOR_INDEX
                        && Comms.readExploreSectorClaimStatus(exploreSectorIndex) == Comms.ClaimStatus.UNCLAIMED) {
                    exploreSectorIndex++;
                }
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
}
