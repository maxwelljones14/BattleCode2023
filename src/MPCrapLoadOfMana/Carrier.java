package MPCrapLoadOfMana;

import battlecode.common.*;
import MPCrapLoadOfMana.Util.*;
import MPCrapLoadOfMana.Comms.*;
import MPCrapLoadOfMana.Debug.*;

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
    WellInfo closestWell;
    MapLocation closestWellLoc;

    static RobotInfo[] enemyAttackable;
    static RobotInfo[] friendlyAttackable;
    RobotInfo closestEnemy;
    RobotInfo closestFriendly;

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        currState = CarrierState.MINING;
        closestWell = null;
        int assignment = Comms.readOurHqFlag(homeIdx);
        if (assignment == Comms.HQFlag.CARRIER_ADAMANTIUM) {
            resourceTarget = ResourceType.ADAMANTIUM;
        } else {
            resourceTarget = ResourceType.MANA;
        }
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

        Debug.printString("Target: " + resourceTarget);

        // mark the first island we see
        if (seenIsland == null) {
            seenIsland = findUnconqueredIsland();
        } else {
            Debug.printString("Island: " + seenIsland);
        }

        trySwitchState();
        Debug.printString("State: " + currState);
        doStateAction();
    }

    public void trySwitchState() throws GameActionException {
        switch (currState) {
            case MINING:
                if (shouldRunAway()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if ((seenIsland != null && rc.canTakeAnchor(home, Anchor.STANDARD)) ||
                        rc.getAnchor() != null) {
                    currState = CarrierState.PLACING_ANCHOR;
                }
                break;
            case PLACING_ANCHOR:
                if (shouldRunAway()) {
                    currState = CarrierState.REPORTING;
                    sectorToReport = 1;
                } else if (rc.getAnchor() == null) {
                    currState = CarrierState.MINING;
                }
                break;
            case REPORTING:
                if (sectorToReport == 0) {
                    if (rc.getAnchor() != null) {
                        currState = CarrierState.PLACING_ANCHOR;
                    } else if (rc.getResourceAmount(resourceTarget) != 0) {
                        currState = CarrierState.DEPOSITING;
                    } else {
                        currState = CarrierState.MINING;
                    }
                }
                break;
            case DEPOSITING:
                if (rc.getResourceAmount(resourceTarget) == 0) {
                    currState = CarrierState.MINING;
                }
                break;
        }
    }

    public void doStateAction() throws GameActionException {
        killClosestEnemy();

        switch (currState) {
            case MINING:
                // If we are at capacity, go home.
                if (rc.getResourceAmount(resourceTarget) == GameConstants.CARRIER_CAPACITY) {
                    Debug.printString("At capacity");
                    if (!transfer()) {
                        Nav.move(home);
                        transfer();
                    }
                    break;
                }

                // If we can see a well, move towards it
                WellInfo[] wells = rc.senseNearbyWells(resourceTarget);
                int closestDist = Integer.MAX_VALUE;
                for (WellInfo well : wells) {
                    MapLocation wellLocation = well.getMapLocation();
                    int dist = Util.distance(rc.getLocation(), wellLocation);
                    if (dist < closestDist) {
                        closestDist = dist;
                        closestWell = well;
                    }
                }

                if (closestWell != null) {
                    Debug.printString("Found well at " + closestWell.getMapLocation());
                    if (!collect()) {
                        Debug.printString("Moving");
                        Nav.move(closestWell.getMapLocation());
                        collect();
                    }
                } else {
                    if (closestWellLoc == null) {
                        closestWellLoc = findClosestWell(resourceTarget);
                    }
                    MapLocation target = closestWellLoc;
                    // If there is no visible well, just explore.
                    if (target == null) {
                        target = Explore.getExploreTarget();
                        Debug.printString("Exploring");
                    } else {
                        Debug.printString("Known well at " + target);
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

                if (placeAnchor())
                    break;

                if (seenIsland != null) {
                    Nav.move(seenIsland);
                } else if (!returnAnchor()) {
                    // No anchor. Go home
                    Nav.move(home);
                    returnAnchor();
                }
                break;
            case REPORTING:
                Nav.move(home);
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
            closestWell = null;
            closestWellLoc = null;
            return true;
        }
        return false;
    }

    /**
     * Collects from closestWell if possible. Returns true if collect was
     * successful.
     */
    public boolean collect() throws GameActionException {
        int amount = Math.min(GameConstants.CARRIER_CAPACITY - rc.getWeight(), closestWell.getRate());
        if (rc.canCollectResource(closestWell.getMapLocation(), amount)) {
            Debug.printString("Collecting");
            rc.collectResource(closestWell.getMapLocation(), amount);
            return true;
        }
        return false;
    }

    /**
     * Places an anchor if possible. Returns true if collect was successful.
     */
    public boolean placeAnchor() throws GameActionException {
        if (rc.canPlaceAnchor()) {
            rc.placeAnchor();
            recordIsland(rc.senseIsland(currLoc), whichSector(currLoc));
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

    public boolean shouldRunAway() throws GameActionException {
        String str = "";
        MapLocation target = null;
        // Run away if either
        // - You see fewer friendly attackers than enemy attackers
        // - the closest enemy is closer than the clsoest friendly
        if (closestEnemy != null) {
            if (enemyAttackable.length + 5 >= friendlyAttackable.length) {
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
        return target != null;
    }
}
