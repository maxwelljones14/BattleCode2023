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
    }

    static CarrierState currState;

    static ResourceType originalResourceTarget;
    static ResourceType resourceTarget;
    static boolean switchedResourceTarget;

    static int turnStartedMining;

    static RobotInfo[] enemyAttackable;
    static RobotInfo[] friendlyAttackable;
    static RobotInfo closestEnemy;
    static RobotInfo closestFriendly;
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

    static int lastTurnCombatSectorNearby;

    static boolean needToReportEarlyWell;

    public static final int CARRIERS_PER_WELL_TO_LEAVE = 12;
    public static final int RESET_WELLS_VISITED_TIMEOUT = 100;

    public static final int REPORTING_COOLDOWN = 10;

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        currState = CarrierState.MINING;
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
        switchedResourceTarget = false;
        needToReportEarlyWell = true;

        Explore.assignExplore3Dir(home.directionTo(rc.getLocation()));
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

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        closestHQ = getClosestFriendlyHQ(currLoc);
        resetLocalEnemyInformation();
        checkCombatSectorNearby();

        Debug.printString(resourceTarget.toString());

        trySwitchState();
        Debug.printString(currState.toString());
        doStateAction();
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
                if (shouldReportEarlyWell()) {
                    currState = CarrierState.REPORTING_EARLY_WELL;
                    sectorToReport = 1;
                } else if (shouldReport()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getResourceAmount(resourceTarget) == GameConstants.CARRIER_CAPACITY) {
                    currState = CarrierState.DEPOSITING;
                } else if (rc.getAnchor() != null) {
                    currState = CarrierState.PLACING_ANCHOR;
                } else if (rc.canTakeAnchor(home, Anchor.STANDARD)
                        && (closestNeutralIsland = getClosestNeutralIsland()) != null) {
                    currState = CarrierState.PLACING_ANCHOR;
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
                        currState = CarrierState.PLACING_ANCHOR;
                    } else if (rc.getResourceAmount(resourceTarget) != 0) {
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
                    currState = CarrierState.PLACING_ANCHOR;
                } else if (rc.canTakeAnchor(home, Anchor.STANDARD) &&
                        (closestNeutralIsland = getClosestNeutralIsland()) != null) {
                    currState = CarrierState.PLACING_ANCHOR;
                } else if (rc.getResourceAmount(resourceTarget) == 0) {
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
                    if (rc.getResourceAmount(resourceTarget) != 0) {
                        currState = CarrierState.DEPOSITING;
                    } else {
                        enterMineState();
                    }
                }
                break;
        }
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

        if (resourceTarget == ResourceType.ADAMANTIUM && shouldSwitchToMana()) {
            resourceTarget = ResourceType.MANA;
            switchedResourceTarget = true;
        } else {
            switchedResourceTarget = false;
        }

        // Propagate blacklisted wells for one cycle in case we went to report
        MapLocation[] blacklistedWellLocs = blacklistedWells.getKeys();
        for (int i = blacklistedWellLocs.length; --i >= 0;) {
            wellsVisitedThisCycle.add(blacklistedWellLocs[i]);
            wellSectorsVisitedThisCycle.add(sectorCenters[whichSector(blacklistedWellLocs[i])]);
        }

        blacklistedWells.clear();
    }

    // Blacklist a well if you see an enemy launcher within action radius of it
    // and you know about another well.
    public void blacklistWells() throws GameActionException {
        if (closestWell == null) {
            return;
        }

        MapLocation wellLoc = closestWell.getMapLocation();
        int closestEnemyLauncherDist = Integer.MAX_VALUE;
        int dist;
        RobotInfo robot;
        for (int i = enemyAttackable.length; --i >= 0;) {
            robot = enemyAttackable[i];
            dist = robot.getLocation().distanceSquaredTo(wellLoc);
            if (dist < closestEnemyLauncherDist) {
                closestEnemyLauncherDist = dist;
            }
        }

        if (closestEnemyLauncherDist <= RobotType.LAUNCHER.actionRadiusSquared) {
            wellSectorsVisitedThisCycle.add(sectorCenters[whichSector(wellLoc)]);
            int mineSectorIndex = getNearestMineSectorIdx(resourceTarget, wellSectorsVisitedThisCycle);
            if (mineSectorIndex != Comms.UNDEFINED_SECTOR_INDEX &&
                    Util.distance(sectorCenters[mineSectorIndex], wellLoc) <= Util.MAX_BLACKLIST_DIST) {
                // We found another well. Blacklist this one for this cycle and the next.
                blacklistedWells.add(wellLoc);
                wellsVisitedThisCycle.add(wellLoc);
                closestWell = null;
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
                runFromEnemy();
                // Reset wellsVisitedThisCycle every so often if we haven't found one
                if (turnStartedMining + RESET_WELLS_VISITED_TIMEOUT < rc.getRoundNum()) {
                    wellsVisitedThisCycle.clear();
                    wellSectorsVisitedThisCycle.clear();
                    turnStartedMining = rc.getRoundNum();
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
                        collect(closestWell);
                        MapLocation betterCollectLoc = Util.getBetterCollectLoc(wellLoc);
                        Nav.move(betterCollectLoc);
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
                            if (numCarriers >= Math.min(maxOpenSpots * 4, CARRIERS_PER_WELL_TO_LEAVE)) {
                                Debug.printString("Leaving");
                                closestWell = null;
                                break collect;
                            }
                        }

                        Debug.printString("Moving");
                        if (Util.seesObstacleInWay(wellLoc)) {
                            Nav.move(wellLoc);
                        } else {
                            Nav.move(Util.getBestCollectLoc(wellLoc));
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
                    Nav.move(target);
                    if (shouldMarkCorner && rc.getLocation().isAdjacentTo(target)) {
                        sectorDatabase.at(mineSectorIndex).visitCorner(target);
                    }
                }
                break;
            case PLACING_ANCHOR:
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

                // If no neutral islands, go home
                if (closestNeutralIsland == null) {
                    if (!returnAnchor()) {
                        Nav.move(home);
                        returnAnchor();
                    }
                    return;
                }

                int[] islandIdxs = rc.senseNearbyIslands();
                if (islandIdxs.length == 0) {
                    // No islands nearby yet, so just go to the closest neutral island
                    Nav.move(closestNeutralIsland);
                    return;
                }

                // If we're near a neutral island, go to it
                int islandIdx = -1;
                for (int i = islandIdxs.length; --i >= 0;) {
                    if (rc.senseTeamOccupyingIsland(islandIdxs[i]) == Team.NEUTRAL) {
                        islandIdx = islandIdxs[i];
                        break;
                    }
                }

                if (islandIdx == -1) {
                    // No neutral islands nearby, so just go to the closest neutral island
                    Nav.move(closestNeutralIsland);
                    return;
                }

                MapLocation target = Util.getClosestEmptyIslandLoc(islandIdx);
                if (target == null) {
                    // If no placement loc, just go to the closest location on the island
                    // and wait for someone to leave.
                    target = Util.getClosestIslandLoc(islandIdx);
                }

                Nav.move(target);
                placeAnchor();
                break;
            case REPORTING:
                runFromEnemy();
                Nav.move(home);
                transfer();
                break;
            case DEPOSITING:
                runFromEnemy();
                if (!transfer()) {
                    Nav.move(closestHQ);
                    transfer();
                }
                break;
            case REPORTING_EARLY_WELL:
                runFromEnemy();
                Nav.move(home);
                transfer();
                break;
        }
    }

    public void loadClosestWell() throws GameActionException {
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
            }
        }
    }

    public boolean shouldIgnoreEarlyWell(MapLocation target) {
        return roundNum <= Util.TURN_TO_IGNORE_EARLY_MINING_SECTORS &&
                Util.distance(home, target) > Util.DIST_TO_IGNORE_EARLY_MINING_SECTORS;
    }

    public void runFromEnemy() throws GameActionException {
        if (runAwayTarget != null) {
            Debug.printString("RA: " + runAwayTarget);
            Nav.move(runAwayTarget);
            currLoc = rc.getLocation();
        }
    }

    /**
     * Transfers to the closestHQ if possible. Returns true if transfer was
     * successful.
     */
    public boolean transfer() throws GameActionException {
        if (rc.canTransferResource(closestHQ, resourceTarget, rc.getResourceAmount(resourceTarget))) {
            Debug.printString("Transfering");
            rc.transferResource(closestHQ, resourceTarget, rc.getResourceAmount(resourceTarget));
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
                Nav.move(closestHQ);
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
        return !switchedResourceTarget &&
                rc.getRoundNum() - lastTurnCombatSectorNearby < Util.SWITCH_TO_MANA_TIMEOUT;
    }
}
