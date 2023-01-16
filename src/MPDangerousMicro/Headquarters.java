package MPDangerousMicro;

import battlecode.common.*;
import MPDangerousMicro.Util.*;
import MPDangerousMicro.Comms.*;
import MPDangerousMicro.Debug.*;
import java.util.ArrayDeque;

import MPDangerousMicro.fast.*;

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

    static MapLocation nearestAdWell;
    static MapLocation nearestMnWell;
    static MapLocation[] locsToBuildCarriers;
    static MapLocation[] locsToBuildLaunchers;

    static final double MANA_TO_ADAM_CARRIER_RATIO = 0.7 / 0.3;
    static final int ROUNDS_TO_STALE_UNIT = 50;
    FastUnitTracker adamCarrierTracker;
    FastUnitTracker manaCarrierTracker;

    static boolean nearHQ;
    static MapLocation enemyHQLoc;

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
        locsToBuildCarriers = new MapLocation[initCarriersWanted];
        locsToBuildLaunchers = new MapLocation[initLaunchersWanted];
        findClosestVisibleWells();

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
        if (rc.getRoundNum() == 2) {
            setInitialExploreSectors();
        }
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
        // displayExploreSectors();
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

    public void updateclosestEnemyHQ() throws GameActionException {
        if (rc.getRoundNum() == 2) {
            closestEnemyHQGuess = getClosestEnemyHQGuess();
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
                nearestAdWell = well.getMapLocation();
            } else if (well.getResourceType() == ResourceType.MANA && dist <= closestMnWellDistance) {
                closestMnWellDistance = dist;
                nearestMnWell = well.getMapLocation();
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
                if (Util.MAP_AREA <= Util.MAX_AREA_FOR_SEMI_FAST_INIT) {
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
                dirToBuild = currLoc.directionTo(nearestAdWell);
            } else {
                // look in sectors for nearest ad well
                MapLocation nearestAdWellSector = findClosestWell(ResourceType.ADAMANTIUM);
                if (nearestAdWellSector != null) {
                    dirToBuild = currLoc.directionTo(nearestAdWellSector);
                } else {
                    dirToBuild = Util.directions[Util.rng.nextInt(Util.directions.length)];
                }
            }
        } else {
            if (nearestMnWell != null) {
                dirToBuild = currLoc.directionTo(nearestMnWell);
            } else {
                // look in sectors for nearest mn well
                MapLocation nearestMnWellSector = findClosestWell(ResourceType.MANA);
                if (nearestMnWellSector != null) {
                    dirToBuild = currLoc.directionTo(nearestMnWellSector);
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
        Debug.printString("Trying to build a carrier of type " + carrierType);

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
        Debug.printString("Trying to build a carrier");

        // get predetermined next carrier type and location
        int carrierType = getNextCarrierType();
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

        return Util.findInitLocation(currLoc, dir);
    }

    public MapLocation getLauncherLocation(MapLocation target) throws GameActionException {
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

        return Util.findInitLocation(currLoc, dir);
    }

    public void buildLauncher(MapLocation newLoc) throws GameActionException {
        Debug.printString("Trying to build a launcher");
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
        if (nearHQ) {
            // set up locations for first launchers in the 4 cardinal directions
            if (launcherCount < initLaunchersWanted) {
                MapLocation locToBuild = getLauncherLocation(enemyHQLoc);
                buildLauncher(locToBuild);
                return;
            }
            // build carriers
            if (carrierCount < initCarriersWanted) {
                buildCarrier();
                return;
            }
        } else {
            // build carriers
            if (carrierCount < initCarriersWanted) {
                buildCarrier();
                return;
            }

            // set up locations for first launchers in the 4 cardinal directions
            if (launcherCount < initLaunchersWanted) {
                MapLocation locToBuild = getLauncherLocation();
                buildLauncher(locToBuild);
                return;
            }
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
                        : Comms.ControlStatus.EXPLORING;
                // Mark old combat sectors as need to be explored.
                // Debug.println("Clearing combat sector at : " + sectorCenters[sectorIdx]);
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

        int combatSectorIndex = getNextEmptyCombatSectorIdx(0);
        int exploreSectorIndex = getNextEmptyExploreSectorIdx(0);

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

    public void displayExploreSectors() throws GameActionException {
        for (int i = 0; i < Comms.EXPLORE_SECTOR_SLOTS; i++) {
            int sector = Comms.readExploreSectorIndex(i);
            if (sector == Comms.UNDEFINED_SECTOR_INDEX)
                break;
            MapLocation loc = sectorCenters[sector];
            rc.setIndicatorDot(loc, 0, 0, 255);
        }
    }

    /**
     * Sets initial explore sectors to center and symmetry locs
     */
    public void setInitialExploreSectors() throws GameActionException {
        int exploreSectorIndex = getNextEmptyExploreSectorIdx(0);

        MapLocation[] listOfHQs = new MapLocation[] { Comms.readOurHqLocation(0),
                Comms.readOurHqLocation(1),
                Comms.readOurHqLocation(2),
                Comms.readOurHqLocation(3) };

        // Add the center and the 3 reflections of your HQ
        MapLocation[] symmetryLocs = guessEnemyLoc(currLoc);
        MapLocation[] locs = { new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2),
                symmetryLocs[0], symmetryLocs[1], symmetryLocs[2] };
        int[] sectors = { whichSector(locs[0]), whichSector(locs[1]), whichSector(locs[2]), whichSector(locs[3]) };

        for (int i = 0; i < 4; i++) {
            MapLocation loc = locs[i];
            int sector = sectors[i];
            int controlStatus = Comms.readSectorControlStatus(sector);

            boolean isOk = true;
            for (int k = 0; k < listOfHQs.length; k++) {
                MapLocation newHQLoc = listOfHQs[k];
                if (loc.distanceSquaredTo(newHQLoc) < RobotType.HEADQUARTERS.visionRadiusSquared) {
                    isOk = false;
                }
            }

            if (!isOk)
                continue;

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
