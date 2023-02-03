package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;
import MPWorking.fast.*;

public class Carrier extends Robot {
    static enum CarrierState {
        MINING,
        PLACING_ANCHOR,
        REPORTING,
        DEPOSITING,
        REPORTING_EARLY_WELL,
        CONVERTING,
        REPORTING_ELIXIR_CONVERTED,
        KILLING_ISLAND,
    }

    static CarrierState currState;

    static ResourceType originalResourceTarget;
    static ResourceType resourceTarget;
    static boolean switchedResourceTarget;

    static int turnStartedMining;

    static RobotInfo[] friendlyAttackable;
    static RobotInfo closestEnemy;
    static RobotInfo closestFriendly;
    static RobotInfo closestEnemyCarrier;
    static int numEnemyCarriers;
    static int numAttackingEnemyCount;

    static FastLocSet blacklistedWells;
    static FastLocSet wellsVisitedThisCycle;
    static FastLocSet wellSectorsVisitedThisCycle;
    static WellInfo closestWell;
    static boolean visitedSectorCenter;

    static MapLocation closestHQ;
    static public int lastTurnReported;
    static MapLocation runAwayTarget;

    static MapLocation closestNeutralIsland;
    static MapLocation firstNeutralIslandLoc;

    static int lastTurnCombatSectorNearby;
    static boolean sawEnemyLastCycle;

    static boolean needToReportEarlyWell;

    static boolean isFirstCycle;

    static boolean isFirstPass;
    static MapLocation lastTarget;

    static int enemyIslandIdx;

    static int turnsNearWell;

    public static final int CARRIERS_PER_WELL_TO_LEAVE = 12;
    public static final int RESET_WELLS_VISITED_TIMEOUT = 100;
    public static final int WELL_TIMEOUT = 40;
    public static final int WELL_DIST_TO_START_TIMER = 20;

    public static final int REPORTING_COOLDOWN = 10;

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        turnStartedMining = 0;
        int assignment = Comms.readOurHqFlag(homeIdx);
        if (assignment == Comms.HQFlag.CARRIER_ADAMANTIUM) {
            originalResourceTarget = ResourceType.ADAMANTIUM;
            resourceTarget = ResourceType.ADAMANTIUM;
        } else {
            originalResourceTarget = ResourceType.MANA;
            resourceTarget = ResourceType.MANA;
        }

        blacklistedWells = new FastLocSet();
        wellsVisitedThisCycle = new FastLocSet();
        wellSectorsVisitedThisCycle = new FastLocSet();
        lastTurnReported = 0;
        lastTurnCombatSectorNearby = -1;
        sawEnemyLastCycle = false;
        switchedResourceTarget = false;
        needToReportEarlyWell = true;
        isFirstCycle = true;
        firstNeutralIslandLoc = null;
        isFirstPass = true;

        Explore.assignExplore3Dir(home.directionTo(rc.getLocation()));

        enterMineState();
        switchedResourceTarget = false;
    }

    public MapLocation findUnconqueredIsland() throws GameActionException {
        int[] islandIdxs = rc.senseNearbyIslands();
        if (islandIdxs.length > 0) {
            // TODO: only note islands that are not conquered yet
            for (int idx : islandIdxs) {
                if (rc.senseTeamOccupyingIsland(idx) == Team.NEUTRAL) {
                    return rc.senseNearbyIslandLocations(idx)[0];
                }
            }
        }
        return null;
    }

    public void initTurn() throws GameActionException {
        super.initTurn();
        isFirstPass = true;
    }

    public void takeTurn() throws GameActionException {
        if (!isFirstPass && Clock.getBytecodesLeft() <= 1000) {
            if (lastTarget != null) {
                move(lastTarget);
            }
            return;
        }

        super.takeTurn();

        closestHQ = getClosestFriendlyHQ(currLoc);
        resetLocalEnemyInformation();
        checkCombatSectorNearby();

        Debug.printString(resourceTarget.toString());

        trySwitchState();
        Debug.printString(currState.toString());
        doStateAction();
        isFirstPass = false;
    }

    public void resetLocalEnemyInformation() throws GameActionException {
        enemyAttackable = getEnemyAttackable();
        friendlyAttackable = getFriendlyAttackable();
        closestEnemy = getClosestRobot(enemyAttackable);
        closestFriendly = getClosestRobot(friendlyAttackable);
        numAttackingEnemyCount = 0;

        RobotInfo robot;
        for (int i = 0; i < enemyAttackable.length; i++) {
            robot = enemyAttackable[i];
            if (robot.type == RobotType.LAUNCHER
                    && robot.location.distanceSquaredTo(currLoc) <= RobotType.LAUNCHER.actionRadiusSquared) {
                numAttackingEnemyCount++;
            }
        }

        runAwayTarget = shouldRunAwayTarget();
        updateHomeifCurrHomeAttacked();
    }

    public void trySwitchState() throws GameActionException {
        switch (currState) {
            case MINING:
                if (shouldWaitOnEnemyIsland()) {
                    currState = CarrierState.KILLING_ISLAND;
                } else if (shouldReportEarlyWell()) {
                    currState = CarrierState.REPORTING_EARLY_WELL;
                    sectorToReport = 1;
                } else if (shouldReport()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getWeight() == GameConstants.CARRIER_CAPACITY) {
                    if (resourceTarget == ResourceType.ADAMANTIUM &&
                            Comms.readElixirSectorIndex() != Comms.UNDEFINED_SECTOR_INDEX) {
                        enterConvertingState();
                    } else {
                        currState = CarrierState.DEPOSITING;
                    }
                } else if (rc.getAnchor() != null) {
                    enterPlacingAnchor();
                } else if (rc.canTakeAnchor(home, Anchor.STANDARD)
                        && (closestNeutralIsland = getClosestNeutralIsland()) != null) {
                    enterPlacingAnchor();
                }
                break;
            case PLACING_ANCHOR:
                if (shouldReport()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getAnchor() == null) {
                    enterMineState();
                }
                break;
            case REPORTING:
                // Note: We don't try to take an anchor directly from reporting
                // because we probably saw an enemy recently and we don't want
                // to risk getting killed.
                if (sectorToReport == 0) {
                    if (rc.getAnchor() != null) {
                        enterPlacingAnchor();
                    } else if (rc.getWeight() != 0) {
                        currState = CarrierState.DEPOSITING;
                    } else {
                        enterMineState();
                    }
                    lastTurnReported = rc.getRoundNum();
                }
                break;
            case DEPOSITING:
                if (shouldReport()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getAnchor() != null) {
                    enterPlacingAnchor();
                } else if (rc.canTakeAnchor(home, Anchor.STANDARD) &&
                        (closestNeutralIsland = getClosestNeutralIsland()) != null) {
                    enterPlacingAnchor();
                } else if (rc.getWeight() == 0) {
                    isFirstCycle = false;
                    enterMineState();
                }
                break;
            case REPORTING_EARLY_WELL:
                // closestWell should never be null.
                if (closestWell == null) {
                    enterMineState();
                    Debug.println("ERROR: closestWell is null in REPORTING_EARLY_WELL state.");
                    break;
                }
                // Someone else reported the same well already.
                int mineSectorIndex = getNearestMineSectorIdx(resourceTarget, null);
                boolean isSameSector = whichSector(closestWell.getMapLocation()) == mineSectorIndex;
                if (isSameSector || sectorToReport == 0) {
                    if (rc.getWeight() != 0) {
                        currState = CarrierState.DEPOSITING;
                    } else {
                        enterMineState();
                    }
                }
                break;
            case CONVERTING:
                if (shouldReport()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getAnchor() != null) {
                    enterPlacingAnchor();
                } else if (rc.getWeight() == 0) {
                    enterMineState();
                } else if (Comms.readElixirSectorConverted() == 1) {
                    currState = CarrierState.DEPOSITING;
                }
                break;
            case REPORTING_ELIXIR_CONVERTED:
                if (Comms.readElixirSectorConverted() == 1) {
                    if (rc.getWeight() == 0) {
                        enterMineState();
                    } else {
                        currState = CarrierState.DEPOSITING;
                    }
                }
                break;
            case KILLING_ISLAND:
                if (!shouldWaitOnEnemyIsland()) {
                    enterMineState();
                }
                break;
        }
    }

    public void enterPlacingAnchor() {
        currState = CarrierState.PLACING_ANCHOR;
        firstNeutralIslandLoc = null;
        visitedSectorCenter = false;
    }

    public boolean shouldReport() {
        return runAwayTarget != null &&
                rc.getRoundNum() - lastTurnReported > REPORTING_COOLDOWN &&
                !closestEnemy.getLocation().isWithinDistanceSquared(closestHQ,
                        RobotType.HEADQUARTERS.visionRadiusSquared);
    }

    public boolean shouldReportEarlyWell() throws GameActionException {
        if (!needToReportEarlyWell) {
            return false;
        }

        loadClosestWell();
        if (closestWell == null) {
            return false;
        }

        int mineSectorIndex = getNearestMineSectorIdx(resourceTarget, null);
        if (mineSectorIndex != Comms.UNDEFINED_SECTOR_INDEX &&
                !shouldIgnoreEarlyWell(sectorCenters[mineSectorIndex])) {
            needToReportEarlyWell = false;
            return false;
        }

        // Make sure this was added to the sector database
        // It might not have been if we see it on an even turn.
        // since which info is updated flip flops between odd and even turns.
        sectorDatabase.at(whichSector(closestWell.getMapLocation())).addWell(closestWell.getMapLocation(),
                resourceTarget);
        needToReportEarlyWell = false;
        return true;
    }

    public void enterMineState() {
        currState = CarrierState.MINING;
        wellsVisitedThisCycle.clear();
        wellSectorsVisitedThisCycle.clear();
        turnStartedMining = rc.getRoundNum();
        closestWell = null;
        visitedSectorCenter = false;
        resourceTarget = originalResourceTarget;
        turnsNearWell = 0;

        if (resourceTarget == ResourceType.ADAMANTIUM && shouldSwitchToMana()) {
            resourceTarget = ResourceType.MANA;
            switchedResourceTarget = true;
        } else {
            switchedResourceTarget = false;
        }

        sawEnemyLastCycle = false;

        // Propagate blacklisted wells for one cycle in case we went to report
        MapLocation[] blacklistedWellLocs = blacklistedWells.getKeys();
        for (int i = blacklistedWellLocs.length; --i >= 0;) {
            wellsVisitedThisCycle.add(blacklistedWellLocs[i]);
            wellSectorsVisitedThisCycle.add(sectorCenters[whichSector(blacklistedWellLocs[i])]);
        }

        blacklistedWells.clear();
    }

    public void enterConvertingState() {
        currState = CarrierState.CONVERTING;
        wellsVisitedThisCycle.clear();
        wellSectorsVisitedThisCycle.clear();
        turnStartedMining = rc.getRoundNum();
        closestWell = null;
        visitedSectorCenter = false;
        resourceTarget = originalResourceTarget;
        sawEnemyLastCycle = false;
        blacklistedWells.clear();
    }

    // Blacklist a well if you see an enemy launcher within action radius of it
    // and you know about another well.
    public void blacklistWells() throws GameActionException {
        if (closestWell == null) {
            return;
        }

        MapLocation wellLoc = closestWell.getMapLocation();
        int closestEnemyLauncherDist = closestEnemy == null ? Integer.MAX_VALUE
                : closestEnemy.getLocation().distanceSquaredTo(wellLoc);

        if (closestEnemyLauncherDist <= RobotType.LAUNCHER.actionRadiusSquared) {
            wellSectorsVisitedThisCycle.add(sectorCenters[whichSector(wellLoc)]);
            int mineSectorIndex = getNearestMineSectorIdx(resourceTarget, wellSectorsVisitedThisCycle);
            if (mineSectorIndex != Comms.UNDEFINED_SECTOR_INDEX &&
                    Util.distance(sectorCenters[mineSectorIndex], wellLoc) <= Util.MAX_BLACKLIST_DIST) {
                // We found another well. Blacklist this one for this cycle and the next.
                blacklistedWells.add(wellLoc);
                wellsVisitedThisCycle.add(wellLoc);
                closestWell = null;
                turnsNearWell = 0;
            }
        }
    }

    public void updateHomeifCurrHomeAttacked() throws GameActionException {
        boolean[] eligibleHomes = new boolean[] { true, true, true, true };
        for (int i = 0; i < Comms.COMBAT_SECTOR_SLOTS; i++) {
            int nearestSector = Comms.readCombatSectorIndex(i);
            if (nearestSector == Comms.UNDEFINED_SECTOR_INDEX) {
                continue;
            }
            for (int j = 0; j < headquarterLocations.length; j++) {
                MapLocation loc = headquarterLocations[j];
                if (loc.x == -1 || sectorCenters[nearestSector]
                        .isWithinDistanceSquared(loc, RobotType.LAUNCHER.actionRadiusSquared)) {
                    eligibleHomes[j] = false;
                }
            }
        }

        if (!eligibleHomes[homeIdx]) {
            MapLocation closestHome = home;
            int closestDist = Util.distance(currLoc, home);
            int newIdx = 0;
            for (int j = 0; j < headquarterLocations.length; j++) {
                MapLocation possibleHome = headquarterLocations[j];
                int currDist = Util.distance(currLoc, possibleHome);
                if (possibleHome.x != -1 && eligibleHomes[j] && currDist < closestDist) {
                    closestHome = possibleHome;
                    closestDist = currDist;
                    newIdx = j;
                }
            }
            if (!home.equals(closestHome)) {
                home = closestHome;
                homeIdx = newIdx;
                Debug.printString("New home: " + home);
            }
        }
    }

    public void doStateAction() throws GameActionException {
        killClosestEnemy();

        switch (currState) {
            case MINING:
                doMine();
                break;
            case PLACING_ANCHOR:
                doPlaceAnchor();
                break;
            case REPORTING:
                runFromEnemy();
                move(home);
                transfer();
                break;
            case DEPOSITING:
                runFromEnemy();
                if (rc.getResourceAmount(ResourceType.ELIXIR) > 0) {
                    resourceTarget = ResourceType.ELIXIR;
                } else if (rc.getResourceAmount(ResourceType.MANA) > 0) {
                    resourceTarget = ResourceType.MANA;
                } else {
                    resourceTarget = ResourceType.ADAMANTIUM;
                }
                if (!transfer()) {
                    move(closestHQ);
                    transfer();
                }
                break;
            case REPORTING_EARLY_WELL:
                runFromEnemy();
                move(home);
                transfer();
                break;
            case CONVERTING:
                doConvert();
                break;
            case REPORTING_ELIXIR_CONVERTED:
                runFromEnemy();
                move(closestHQ);
                transfer();
                if (rc.canWriteSharedArray(0, 0)) {
                    Comms.writeElixirSectorConverted(1);
                }
                break;
            case KILLING_ISLAND:
                waitOnEnemyIsland();
                break;
        }
    }

    public void doMine() throws GameActionException {
        runFromEnemy();
        // Reset wellsVisitedThisCycle every so often if we haven't found one
        if (turnStartedMining + RESET_WELLS_VISITED_TIMEOUT < rc.getRoundNum()) {
            wellsVisitedThisCycle.clear();
            wellSectorsVisitedThisCycle.clear();
            turnStartedMining = rc.getRoundNum();
        }

        if (closestEnemy != null) {
            sawEnemyLastCycle = true;
        }

        loadClosestWell();
        blacklistWells();

        collect: if (closestWell != null) {
            MapLocation wellLoc = closestWell.getMapLocation();
            Debug.printString("Well: " + wellLoc);
            // Mark this sector's well as visited
            wellSectorsVisitedThisCycle.add(sectorCenters[whichSector(wellLoc)]);
            wellsVisitedThisCycle.add(wellLoc);

            // If we are adjacent to a well, collect from it.
            if (currLoc.isAdjacentTo(wellLoc)) {
                turnsNearWell = 0;
                collect(closestWell);

                MapLocation betterCollectLoc;
                closestEnemyCarrier = findCarrierSearchingForSpot(wellLoc);
                if (numEnemyCarriers < FriendlySensable.length && closestEnemyCarrier != null) {
                    // If there is an enemy carrier searching for a spot and there
                    // are more friendlies, try to box out the carrier.
                    betterCollectLoc = Util.getCollectLocClosestTo(wellLoc, closestEnemyCarrier.location);
                } else {
                    betterCollectLoc = Util.getBetterCollectLoc(wellLoc);
                }
                move(betterCollectLoc);
            } else {
                // If there are too many carriers on this well, move to another well.
                // If there are 2 available spots, skip this check
                int numOpenSpots = Util.getNumOpenCollectSpots(wellLoc);
                if (numOpenSpots <= 2) {
                    int numCarriers = 0;
                    RobotInfo robot;
                    for (int i = FriendlySensable.length; --i >= 0;) {
                        robot = FriendlySensable[i];
                        if (robot.type == RobotType.CARRIER)
                            numCarriers++;
                    }

                    int maxOpenSpots = Util.getMaxCollectSpots(wellLoc);
                    if (numCarriers >= Math.min(maxOpenSpots * 2, CARRIERS_PER_WELL_TO_LEAVE)) {
                        Debug.printString("Leaving");
                        closestWell = null;
                        turnsNearWell = 0;
                        break collect;
                    }
                }

                Debug.printString("Moving");
                if (Util.seesObstacleInWay(wellLoc)) {
                    move(wellLoc);
                } else {
                    move(Util.getBestCollectLoc(wellLoc));

                    if (currLoc.isWithinDistanceSquared(wellLoc, WELL_DIST_TO_START_TIMER)) {
                        turnsNearWell++;
                    }
                }

                collect(closestWell);
            }
        }

        if (closestWell == null) {
            boolean shouldMarkCorner = false;
            // If we can't see a well, move towards the closest mine sector
            int mineSectorIndex = getNearestMineSectorIdx(resourceTarget, wellSectorsVisitedThisCycle);
            MapLocation target = null;
            if (mineSectorIndex != Comms.UNDEFINED_SECTOR_INDEX) {
                target = resourceTarget == ResourceType.ADAMANTIUM
                        ? sectorDatabase.at(mineSectorIndex).getAdamWell()
                        : sectorDatabase.at(mineSectorIndex).getManaWell();

                // If it's too early and the well is far,
                // don't look at this well and just explore.
                if (shouldIgnoreEarlyWell(target)) {
                    target = Explore.getExplore3Target();
                    Debug.printString("Early, exploring");
                } else {
                    Debug.printString("Well in sector: " + target);
                }

                // If we have visited the center of the sector and we can't see the well,
                // travel the corners of the sector until we find the well
                if (visitedSectorCenter || currLoc.equals(target)) {
                    visitedSectorCenter = true;
                    MapLocation nextCorner = sectorDatabase.at(mineSectorIndex).getNextCorner();
                    target = nextCorner;
                    if (target == null) {
                        // We've visited all the corners and still haven't found the well???
                        target = resourceTarget == ResourceType.ADAMANTIUM
                                ? sectorDatabase.at(mineSectorIndex).getAdamWell()
                                : sectorDatabase.at(mineSectorIndex).getManaWell();
                        Debug.println("ERROR: Couldn't find well in sector");
                        sectorDatabase.at(mineSectorIndex).resetCorners();
                    } else {
                        shouldMarkCorner = true;
                    }
                    // Debug.println("Trying corner: " + target);
                }
            } else {
                target = Explore.getExplore3Target();
                Debug.printString("Exploring");
            }
            move(target);
            if (shouldMarkCorner && rc.getLocation().isAdjacentTo(target)) {
                sectorDatabase.at(mineSectorIndex).visitCorner(target);
            }
        }
    }

    public void doPlaceAnchor() throws GameActionException {
        runFromEnemy();
        // pick up anchor if home has anchor
        if (rc.canTakeAnchor(home, Anchor.STANDARD)) {
            rc.takeAnchor(home, Anchor.STANDARD);
        }

        // We only enter PLACING_ANCHOR if we can immediately take an anchor.
        // We should always have an anchor now.
        if (rc.getAnchor() == null) {
            Debug.println("ERROR: No anchor in PLACING_ANCHOR state");
        }

        closestNeutralIsland = getClosestNeutralIsland();

        int[] islandIdxs = rc.senseNearbyIslands();

        // If we're near a neutral island, go to it
        int neutralIslandIdx = -1;
        int enemyIslandIdx = -1;
        for (int i = islandIdxs.length; --i >= 0;) {
            if (rc.senseTeamOccupyingIsland(islandIdxs[i]) == Team.NEUTRAL) {
                neutralIslandIdx = islandIdxs[i];
                break;
            } else if (rc.senseTeamOccupyingIsland(islandIdxs[i]) == rc.getTeam().opponent()) {
                enemyIslandIdx = islandIdxs[i];
            }
        }

        if (neutralIslandIdx == -1) {
            // Nav to the first neutral island seen, but reset it if it was captured.
            if (firstNeutralIslandLoc != null) {
                if (rc.canSenseLocation(firstNeutralIslandLoc)) {
                    if (rc.senseTeamOccupyingIsland(rc.senseIsland(firstNeutralIslandLoc)) == Team.NEUTRAL) {
                        move(firstNeutralIslandLoc);
                        return;
                    } else {
                        firstNeutralIslandLoc = null;
                    }
                } else {
                    move(firstNeutralIslandLoc);
                    return;
                }
            }

            // Sit on an enemy island if there are no enemies nearby.
            if (enemyIslandIdx != -1 && closestEnemy == null) {
                // If there's an enemy island nearby and no enemies nearby,
                // overtake the enemy island.
                MapLocation target = Util.getClosestEmptyIslandLoc(enemyIslandIdx);
                if (target == null) {
                    // If no loc, just go to the closest location on the island
                    // and wait for someone to leave.
                    target = Util.getClosestIslandLoc(enemyIslandIdx);
                }
                move(target);
                return;
            }

            // If no neutral islands, go home
            if (closestNeutralIsland == null) {
                if (!returnAnchor()) {
                    move(home);
                    returnAnchor();
                }
                return;
            }

            // No islands nearby yet, so just go to the closest neutral island
            MapLocation target = closestNeutralIsland;
            boolean shouldMarkCorner = false;

            // If we have visited the center of the sector and we can't see the island,
            // travel the corners of the sector until we find it
            int sectorIdx = whichSector(closestNeutralIsland);
            if (visitedSectorCenter || currLoc.equals(closestNeutralIsland)) {
                visitedSectorCenter = true;
                MapLocation nextCorner = sectorDatabase.at(sectorIdx).getNextCorner();
                target = nextCorner;
                if (target == null) {
                    // We've visited all the corners and still haven't found the island???
                    target = sectorCenters[sectorIdx];
                    Debug.println("ERROR: Couldn't find island in sector");
                    sectorDatabase.at(sectorIdx).resetCorners();
                } else {
                    shouldMarkCorner = true;
                }
                // Debug.println("Trying corner: " + target);
            }

            move(target);
            if (shouldMarkCorner && rc.getLocation().isAdjacentTo(target)) {
                sectorDatabase.at(sectorIdx).visitCorner(target);
            }
            return;
        }

        MapLocation target = Util.getClosestEmptyIslandLoc(neutralIslandIdx);
        if (target == null) {
            // If no placement loc, just go to the closest location on the island
            // and wait for someone to leave.
            target = Util.getClosestIslandLoc(neutralIslandIdx);
        }

        if (firstNeutralIslandLoc == null) {
            firstNeutralIslandLoc = target;
        }

        if (Util.seesObstacleInWay(target)) {
            move(firstNeutralIslandLoc);
        } else {
            move(target);
        }

        placeAnchor();
    }

    public void doConvert() throws GameActionException {
        runFromEnemy();

        MapLocation elixirSector = sectorCenters[Comms.readElixirSectorIndex()];
        // Once we're near the sector, start loading mana wells.
        if (Util.distance(rc.getLocation(), elixirSector) <= 3) {
            loadFullestManaWell();
        }

        if (rc.senseNearbyWells(ResourceType.ELIXIR).length > 0) {
            currState = CarrierState.REPORTING_ELIXIR_CONVERTED;
        }

        if (closestWell != null) {
            MapLocation wellLoc = closestWell.getMapLocation();
            Debug.printString("Well: " + wellLoc);
            // If we are adjacent to a well, transfer to it.
            if (currLoc.isAdjacentTo(wellLoc)) {
                transfer(wellLoc);
            } else {
                Debug.printString("Moving");
                if (Util.seesObstacleInWay(wellLoc)) {
                    move(wellLoc);
                } else {
                    move(Util.getBestCollectLoc(wellLoc));
                }
                transfer(wellLoc);
            }
        } else {
            // Continue going towards the mana sector.
            boolean shouldMarkCorner = false;
            int elixirSectorIndex = Comms.readElixirSectorIndex();
            MapLocation target = sectorDatabase.at(elixirSectorIndex).getManaWell();

            // If we have visited the center of the sector and we can't see the well,
            // travel the corners of the sector until we find the well
            if (visitedSectorCenter || currLoc.equals(target)) {
                visitedSectorCenter = true;
                MapLocation nextCorner = sectorDatabase.at(elixirSectorIndex).getNextCorner();
                target = nextCorner;
                if (target == null) {
                    // We've visited all the corners and still haven't found the well???
                    target = sectorDatabase.at(elixirSectorIndex).getManaWell();
                    Debug.println("ERROR: Couldn't find well in sector");
                    sectorDatabase.at(elixirSectorIndex).resetCorners();
                } else {
                    shouldMarkCorner = true;
                }
            }

            move(target);
            if (shouldMarkCorner && rc.getLocation().isAdjacentTo(target)) {
                sectorDatabase.at(elixirSectorIndex).visitCorner(target);
            }
        }
    }

    public void loadFullestManaWell() throws GameActionException {
        WellInfo[] wells = rc.senseNearbyWells(ResourceType.MANA);
        int maxInventory = Integer.MIN_VALUE;
        for (WellInfo well : wells) {
            if (maxInventory < well.getResource(ResourceType.ADAMANTIUM)) {
                maxInventory = well.getResource(ResourceType.ADAMANTIUM);
                closestWell = well;
            }
        }
    }

    public void loadClosestWell() throws GameActionException {
        // Mana carriers should pref elixir if they see an elixir well.
        if (resourceTarget == ResourceType.MANA &&
                Comms.readElixirSectorIndex() != Comms.UNDEFINED_SECTOR_INDEX) {
            WellInfo[] elixirWells = rc.senseNearbyWells(ResourceType.ELIXIR);
            if (elixirWells.length > 0) {
                resourceTarget = ResourceType.ELIXIR;
                closestWell = elixirWells[0];
                turnsNearWell = 0;
                return;
            }
        }

        if (resourceTarget == ResourceType.ELIXIR) {
            // Reset resourceTarget if there are no elixir wells nearby.
            WellInfo[] elixirWells = rc.senseNearbyWells(ResourceType.ELIXIR);
            if (elixirWells.length == 0) {
                resourceTarget = ResourceType.MANA;
            }
        }

        if (turnsNearWell > WELL_TIMEOUT && closestWell != null) {
            closestWell = null;
            turnsNearWell = 0;
        }

        // If we can see a well, move towards it
        WellInfo[] wells = rc.senseNearbyWells(resourceTarget);
        int closestDist = Integer.MAX_VALUE;
        for (WellInfo well : wells) {
            MapLocation wellLocation = well.getMapLocation();
            int dist = Util.distance(rc.getLocation(), wellLocation);
            if (dist < closestDist &&
                    (!wellsVisitedThisCycle.contains(wellLocation) || dist <= 2) &&
                    (!blacklistedWells.contains(wellLocation))) {
                closestDist = dist;
                closestWell = well;
                turnsNearWell = 0;
            }
        }
    }

    public boolean shouldIgnoreEarlyWell(MapLocation target) {
        return roundNum <= Util.TURN_TO_IGNORE_EARLY_MINING_SECTORS &&
                Util.distance(home, target) > Util.DIST_TO_IGNORE_EARLY_MINING_SECTORS;
    }

    public boolean shouldWaitOnEnemyIsland() throws GameActionException {
        // Don't wait if you see an attacking enemy
        if (closestEnemy != null &&
                (closestEnemy.type == RobotType.LAUNCHER || closestEnemy.type == RobotType.DESTABILIZER))
            return false;

        int[] islandIdxs = rc.senseNearbyIslands();
        for (int i = islandIdxs.length; --i >= 0;) {
            if (rc.senseTeamOccupyingIsland(islandIdxs[i]) == team.opponent()) {
                enemyIslandIdx = islandIdxs[i];
                return true;
            }
        }
        return false;
    }

    public void waitOnEnemyIsland() throws GameActionException {
        MapLocation[] locs = rc.senseNearbyIslandLocations(enemyIslandIdx);

        // Find the closest island location to wait on
        MapLocation bestLoc = null;
        int bestDist = Integer.MAX_VALUE;
        int dist;
        MapLocation loc;
        for (int i = locs.length; --i >= 0;) {
            loc = locs[i];
            dist = currLoc.distanceSquaredTo(loc);
            if (dist < bestDist) {
                bestDist = dist;
                bestLoc = loc;
            }
        }

        if (bestLoc == null) {
            Debug.println("ERROR: Couldn't find island loc");
            return;
        }

        move(bestLoc);
    }

    public void runFromEnemy() throws GameActionException {
        if (runAwayTarget != null) {
            Debug.printString("RA: " + runAwayTarget);
            move(runAwayTarget);
            currLoc = rc.getLocation();
        }
    }

    /**
     * Transfers to the closestHQ if possible. Returns true if transfer was
     * successful.
     */
    public boolean transfer() throws GameActionException {
        return transfer(closestHQ);
    }

    public boolean transfer(MapLocation loc) throws GameActionException {
        if (rc.canTransferResource(loc, resourceTarget, rc.getResourceAmount(resourceTarget))) {
            Debug.printString("Transfering");
            rc.transferResource(loc, resourceTarget, rc.getResourceAmount(resourceTarget));
            return true;
        }
        return false;
    }

    /**
     * Collects from closestWell if possible. Returns true if collect was
     * successful.
     */
    public boolean collect(WellInfo well) throws GameActionException {
        int amount = Math.min(GameConstants.CARRIER_CAPACITY - rc.getWeight(), well.getRate());
        if (rc.canCollectResource(well.getMapLocation(), amount)) {
            Debug.printString("C");
            rc.collectResource(well.getMapLocation(), amount);
            collect(well);
            return true;
        }
        return false;
    }

    /**
     * Places an anchor if possible. Returns true if collect was successful.
     */
    public boolean placeAnchor() throws GameActionException {
        MapLocation loc = rc.getLocation();
        int id = rc.senseIsland(loc);
        if (id == -1)
            return false;
        Team islandTeam = rc.senseTeamOccupyingIsland(id);
        if (rc.canPlaceAnchor() && islandTeam == Team.NEUTRAL) {
            rc.placeAnchor();
            recordIsland(rc.senseIsland(loc), whichSector(loc));
            Debug.printString("Placed anchor");
            return true;
        }
        return false;
    }

    /**
     * Returns an anchor to home if possible.
     * Returns true if collect was successful.
     */
    public boolean returnAnchor() throws GameActionException {
        if (rc.canReturnAnchor(home)) {
            Debug.printString("returning anchor");
            rc.returnAnchor(home);
            return true;
        }
        return false;
    }

    /**
     * Attacks the loc if possible.
     * Returns true if the attack was successful.
     */
    public boolean attack(MapLocation loc) throws GameActionException {
        // Throw resource at any enemy you see and run.
        if (rc.getWeight() > 0 && rc.canAttack(closestEnemy.location)) {
            rc.attack((closestEnemy.location));
            Debug.printString("Attacked " + closestEnemy.location);
            Debug.setIndicatorLine(Debug.INFO, currLoc, closestEnemy.location, 255, 0, 0);
            return true;
        }
        return false;
    }

    public void killClosestEnemy() throws GameActionException {
        if (closestEnemy == null)
            return;
        // Transfering is more important. Carrier attacks are inefficient.
        if (transfer())
            return;
        // Being within 8 means we can move once and be adjacent to an HQ
        if (rc.getLocation().isWithinDistanceSquared(closestHQ, 8) && rc.isActionReady() && rc.getWeight() > 0) {
            Direction dir = rc.getLocation().directionTo(closestHQ);
            Direction[] dirs = { dir, dir.rotateLeft(), dir.rotateRight() };
            for (Direction d : dirs) {
                MapLocation newLoc = rc.getLocation().add(d);
                if (newLoc.isAdjacentTo(closestHQ) && rc.canMove(d)) {
                    rc.move(d);
                    currLoc = rc.getLocation();
                    transfer();
                    break;
                }
            }
        }

        if (numAttackingEnemyCount > 0) {
            // If the number of turns we expect to die in is less than the number of turns
            // it takes to get home, run home to transfer.
            int turnsDead = rc.getHealth() / (numAttackingEnemyCount * RobotType.LAUNCHER.damage);
            int turnsToHome = (rc.getMovementCooldownTurns()
                    + Util.distance(currLoc, closestHQ) * Pathfinding.getBaseMovementCooldown())
                    / GameConstants.COOLDOWNS_PER_TURN;
            if (turnsDead > turnsToHome) {
                move(closestHQ);
                currLoc = rc.getLocation();
                return;
            }
        }

        // Throw resource at an enemy launcher you see and run.
        if (closestEnemy.type == RobotType.LAUNCHER)
            attack(closestEnemy.location);

        // Move towards an enemy if you can kill it
        if (rc.getWeight() * GameConstants.CARRIER_DAMAGE_FACTOR >= closestEnemy.health &&
                rc.isActionReady()) {
            Direction dir = Nav.getBestDir(closestEnemy.location);
            if (rc.canMove(dir)) {
                MapLocation newLoc = rc.getLocation().add(dir);
                if (newLoc.distanceSquaredTo(closestEnemy.location) <= RobotType.CARRIER.actionRadiusSquared) {
                    rc.move(dir);
                    currLoc = rc.getLocation();
                    attack(closestEnemy.location);
                }
            }
        }
    }

    public MapLocation shouldRunAwayTarget() throws GameActionException {
        String str = "";
        MapLocation target = null;
        // Run away if either
        // - You see fewer friendly attackers than enemy attackers
        // - the closest enemy is closer than the clsoest friendly
        if (closestEnemy != null) {
            if (enemyAttackable.length + 2 >= friendlyAttackable.length) {
                target = Util.invertLocation(closestEnemy.getLocation());
                str = "Enemies++";
            }

            if (closestFriendly != null &&
                    currLoc.distanceSquaredTo(closestEnemy.location) < currLoc
                            .distanceSquaredTo(closestFriendly.location)) {
                target = Util.invertLocation(closestEnemy.getLocation());
                str = "Enemy";
            }

            Debug.printString(str);
        }
        return target;
    }

    public RobotInfo findCarrierSearchingForSpot(MapLocation wellLoc) throws GameActionException {
        RobotInfo robot;
        RobotInfo closestRobot = null;
        int leastDistance = Integer.MAX_VALUE;
        int currDistance;
        numEnemyCarriers = 0;

        for (int i = EnemySensable.length; --i >= 0;) {
            robot = EnemySensable[i];
            if (robot.type != RobotType.CARRIER || robot.location.isAdjacentTo(wellLoc))
                continue;
            numEnemyCarriers++;
            currDistance = robot.location.distanceSquaredTo(currLoc);
            if (leastDistance > currDistance) {
                leastDistance = currDistance;
                closestRobot = robot;
            }
        }

        return closestRobot;
    }

    public void checkCombatSectorNearby() throws GameActionException {
        // Switch to mining mana if there is a combat sector near the closestHQ.
        int sector = getNearestCombatSector(closestHQ);
        if (sector == Comms.UNDEFINED_SECTOR_INDEX)
            return;
        if (Util.distance(sectorCenters[sector], closestHQ) <= Util.DIST_TO_SWITCH_TO_MANA) {
            lastTurnCombatSectorNearby = rc.getRoundNum();
        }
    }

    public boolean shouldSwitchToMana() {
        if (isFirstCycle) {
            return true;
        }

        if (sawEnemyLastCycle) {
            return true;
        }

        return !switchedResourceTarget &&
                rc.getRoundNum() - lastTurnCombatSectorNearby < Util.SWITCH_TO_MANA_TIMEOUT;
    }

    // On first takeTurn pass, we allow nav to do BFS. On second pass, we don't.
    public void move(MapLocation target) throws GameActionException {
        Nav.move(target, isFirstPass ? Nav.BYTECODE_REMAINING : 9999);
        lastTarget = target;
    }
}
