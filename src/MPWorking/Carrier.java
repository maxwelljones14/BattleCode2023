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
    }

    CarrierState currState;

    ResourceType resourceTarget;
    MapLocation seenIsland;

    int turnStartedMining;

    static RobotInfo[] enemyAttackable;
    static RobotInfo[] friendlyAttackable;
    RobotInfo closestEnemy;
    RobotInfo closestFriendly;

    FastLocSet wellsVisitedThisCycle;
    FastLocSet wellSectorsVisitedThisCycle;
    WellInfo closestWell;

    public final int CARRIERS_PER_WELL_TO_LEAVE = 12;
    public final int RESET_WELLS_VISITED_TIMEOUT = 100;

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        currState = CarrierState.MINING;
        turnStartedMining = 0;
        int assignment = Comms.readOurHqFlag(homeIdx);
        if (assignment == Comms.HQFlag.CARRIER_ADAMANTIUM) {
            resourceTarget = ResourceType.ADAMANTIUM;
        } else {
            resourceTarget = ResourceType.MANA;
        }
        wellsVisitedThisCycle = new FastLocSet();
        wellSectorsVisitedThisCycle = new FastLocSet();
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

        enemyAttackable = getEnemyAttackable();
        friendlyAttackable = getFriendlyAttackable();
        closestEnemy = getClosestRobot(enemyAttackable);
        closestFriendly = getClosestRobot(friendlyAttackable);

        Debug.printString(resourceTarget.toString());

        // mark the first island we see
        if (seenIsland == null) {
            seenIsland = findUnconqueredIsland();
        } else {
            Debug.printString("Isl: " + seenIsland);
        }

        trySwitchState();
        Debug.printString(currState.toString());
        doStateAction();
    }

    public void trySwitchState() throws GameActionException {
        switch (currState) {
            case MINING:
                if (shouldRunAwayTarget() != null) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getResourceAmount(resourceTarget) == GameConstants.CARRIER_CAPACITY) {
                    currState = CarrierState.DEPOSITING;
                } else if ((seenIsland != null && rc.canTakeAnchor(home, Anchor.STANDARD)) ||
                        rc.getAnchor() != null) {
                    currState = CarrierState.PLACING_ANCHOR;
                }
                break;
            case PLACING_ANCHOR:
                if (shouldRunAwayTarget() != null) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getAnchor() == null) {
                    enterMineState();
                }
                break;
            case REPORTING:
                if (sectorToReport == 0) {
                    if (rc.getAnchor() != null) {
                        currState = CarrierState.PLACING_ANCHOR;
                    } else if (rc.getResourceAmount(resourceTarget) != 0) {
                        currState = CarrierState.DEPOSITING;
                    } else {
                        enterMineState();
                    }
                }
                break;
            case DEPOSITING:
                if (shouldRunAwayTarget() != null) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getResourceAmount(resourceTarget) == 0) {
                    enterMineState();
                }
                break;
        }
    }

    public void enterMineState() {
        currState = CarrierState.MINING;
        wellsVisitedThisCycle.clear();
        wellSectorsVisitedThisCycle.clear();
        turnStartedMining = rc.getRoundNum();
        closestWell = null;
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
                        .distanceSquaredTo(loc) <= RobotType.LAUNCHER.actionRadiusSquared) {
                    eligibleHomes[j] = false;
                }
            }

        }
        if (!eligibleHomes[homeIdx]) {
            Debug.printString("need new home");
            int closestDist = Integer.MAX_VALUE;
            MapLocation closestHome = home;
            for (int j = 0; j < headquarterLocations.length; j++) {
                MapLocation possibleHome = headquarterLocations[j];
                int currDist = Util.distance(currLoc, possibleHome);
                if (possibleHome.x != -1 && eligibleHomes[j] && currDist <= closestDist) {
                    closestHome = possibleHome;
                    closestDist = currDist;
                }
            }
            home = closestHome;
            Debug.printString("home now " + home);
        }
    }

    public void doStateAction() throws GameActionException {
        killClosestEnemy();

        switch (currState) {
            case MINING:
                // Reset wellsVisitedThisCycle every so often if we haven't found one
                if (turnStartedMining + RESET_WELLS_VISITED_TIMEOUT < rc.getRoundNum()) {
                    wellsVisitedThisCycle.clear();
                    wellSectorsVisitedThisCycle.clear();
                    turnStartedMining = rc.getRoundNum();
                }

                // If we can see a well, move towards it
                WellInfo[] wells = rc.senseNearbyWells(resourceTarget);
                int closestDist = Integer.MAX_VALUE;
                for (WellInfo well : wells) {
                    MapLocation wellLocation = well.getMapLocation();
                    int dist = Util.distance(rc.getLocation(), wellLocation);
                    if (dist < closestDist && !wellsVisitedThisCycle.contains(wellLocation)) {
                        closestDist = dist;
                        closestWell = well;
                    }
                }

                collect: if (closestWell != null) {
                    Debug.printString("Well: " + closestWell.getMapLocation());
                    // Mark this sector's well as visited
                    wellSectorsVisitedThisCycle.add(sectorCenters[whichSector(closestWell.getMapLocation())]);

                    // If we are adjacent to a well, collect from it.
                    if (rc.getLocation().isAdjacentTo(closestWell.getMapLocation())) {
                        collect(closestWell);
                    } else {
                        // If there are too many carriers on this well, move to another well.
                        // If there are 2 available spots, skip this check
                        int numOpenSpots = Util.getNumOpenCollectSpots(closestWell.getMapLocation());
                        if (numOpenSpots <= 2) {
                            int numCarriers = 0;
                            RobotInfo robot;
                            for (int i = FriendlySensable.length; --i >= 0;) {
                                robot = FriendlySensable[i];
                                if (robot.type == RobotType.CARRIER)
                                    numCarriers++;
                            }

                            int maxOpenSpots = Util.getMaxCollectSpots(closestWell.getMapLocation());
                            if (numCarriers >= Math.min(maxOpenSpots * 4, CARRIERS_PER_WELL_TO_LEAVE)) {
                                Debug.printString("Leaving");
                                wellsVisitedThisCycle.add(closestWell.getMapLocation());
                                closestWell = null;
                                break collect;
                            }
                        }

                        Debug.printString("Moving");
                        Nav.move(closestWell.getMapLocation());
                        collect(closestWell);
                    }
                }

                if (closestWell == null) {
                    // If we can't see a well, move towards the closest mine sector
                    int mineSectorIndex = getNearestMineSectorIdx(resourceTarget, wellSectorsVisitedThisCycle);
                    MapLocation target = null;
                    if (mineSectorIndex != Comms.UNDEFINED_SECTOR_INDEX) {
                        Debug.printString("Well in sector: " + target);
                        target = sectorCenters[mineSectorIndex];
                    } else {
                        target = Explore.getExploreTarget();
                        Debug.printString("Exploring");
                    }
                    Nav.move(target);
                }
                break;
            case PLACING_ANCHOR:
                // pick up anchor if home has anchor
                if (rc.canTakeAnchor(home, Anchor.STANDARD)) {
                    rc.takeAnchor(home, Anchor.STANDARD);
                }

                // We only enter PLACING_ANCHOR if we can immediately take an anchor.
                // We should always have an anchor now.
                if (rc.getAnchor() == null) {
                    Debug.println("ERROR: No anchor in PLACING_ANCHOR state");
                }

                // seenIsland might be null if it was unsuccessfully reloaded previously
                // if we're near an island and have an anchor, place the anchor and reset
                if (seenIsland != null && rc.canSenseLocation(seenIsland)) {
                    // if i get to the island and its taken already then find another island
                    if (rc.senseTeamOccupyingIsland(rc.senseIsland(seenIsland)) != Team.NEUTRAL) {
                        seenIsland = findUnconqueredIsland();
                        Debug.printString("Island already claimed");
                    }
                }

                if (placeAnchor()) {
                    Nav.move(home);
                    return;
                }

                if (seenIsland != null) {
                    Nav.move(seenIsland);
                    placeAnchor();
                } else if (!returnAnchor()) {
                    // No anchor. Go home
                    Nav.move(home);
                    returnAnchor();
                }
                break;
            case REPORTING:
                MapLocation target = shouldRunAwayTarget();
                if (target != null) {
                    Nav.move(currLoc.add(currLoc.directionTo(target)).add(currLoc.directionTo(target)));
                }
                updateHomeifCurrHomeAttacked();
                Nav.move(home);
                transfer();
                break;
            case DEPOSITING:
                if (!transfer()) {
                    Nav.move(home);
                    transfer();
                }
                break;
        }
    }

    /**
     * Transfers to home if possible. Returns true if transfer was successful.
     */
    public boolean transfer() throws GameActionException {
        if (rc.canTransferResource(home, resourceTarget, rc.getResourceAmount(resourceTarget))) {
            Debug.printString("Transfering");
            rc.transferResource(home, resourceTarget, rc.getResourceAmount(resourceTarget));
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
            Debug.printString("Collecting");
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
            seenIsland = null;
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

        // Throw resource at any enemy you see and run.
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
                target = Pathfinding.getGreedyTargetAway(closestEnemy.getLocation());
                str = "Enemies++ " + closestEnemy.type;
            }

            if (closestFriendly != null &&
                    currLoc.distanceSquaredTo(closestEnemy.location) < currLoc
                            .distanceSquaredTo(closestFriendly.location)) {
                target = Pathfinding.getGreedyTargetAway(closestEnemy.getLocation());
                str = "Close enemy " + closestEnemy.type;
            }

            Debug.printString(str);
        }
        return target;
    }
}
