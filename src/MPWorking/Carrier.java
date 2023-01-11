package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Carrier extends Robot {

    ResourceType resourceTarget;
    MapLocation seenIsland;
    WellInfo closestWell;
    int homeIdx;

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        closestWell = null;
        homeIdx = 0;
        for (int i = 0; i < 4; i++) {
            if (Comms.readOurHqLocation(i) == home) {
                homeIdx = i;
                break;
            }
        }
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

    public MapLocation findClosestWell() throws GameActionException {
        MapLocation closestLoc = null;
        int closestDistance = Integer.MAX_VALUE;
        if (resourceTarget == ResourceType.ADAMANTIUM) {
            for (int x = 0; x < numSectors; x++) {
                MapLocation sectorLoc = sectorCenters[x];
                int currDistance = currLoc.distanceSquaredTo(sectorLoc);
                if (currDistance < closestDistance) {
                    int flag = Comms.readSectorAdamantiumFlag(x);
                    if (flag == 1) {
                        closestDistance = currDistance;
                        closestLoc = sectorLoc;
                    }
                }
            }
        } else {
            for (int x = 0; x < numSectors; x++) {
                MapLocation sectorLoc = sectorCenters[x];
                int currDistance = currLoc.distanceSquaredTo(sectorLoc);
                if (currDistance < closestDistance) {
                    int flag = Comms.readSectorManaFlag(x);
                    if (flag == 1) {
                        closestDistance = currDistance;
                        closestLoc = sectorLoc;
                    }
                }
            }
        }
        return closestLoc;
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        Debug.printString("my assignment: " + resourceTarget);

        if (runAway()) {
            return;
        }

        // if we're near home and have an island in our history, pick up anchor if home
        // has anchor
        if (seenIsland != null && rc.canTakeAnchor(home, Anchor.STANDARD)) {
            rc.takeAnchor(home, Anchor.STANDARD);
        }

        // mark the first island we see
        if (seenIsland == null) {
            seenIsland = findUnconqueredIsland();
        } else {
            Debug.printString("seen island at " + seenIsland);
        }

        // If we are at capacity, go home.
        if (rc.getResourceAmount(resourceTarget) == GameConstants.CARRIER_CAPACITY) {
            Debug.printString("At capacity, going home");
            if (rc.canTransferResource(home, resourceTarget, rc.getResourceAmount(resourceTarget))) {
                Debug.printString("at home to transfer");
                rc.transferResource(home, resourceTarget, rc.getResourceAmount(resourceTarget));
                closestWell = null;
            } else {
                Pathfinding.move(home);
            }
            return;
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
        // if we have an anchor and have island location, go to that first
        if (seenIsland != null && rc.getAnchor() != null) {
            Debug.printString("have an anchor");
            // if we're near an island and have an anchor, place the anchor and reset
            // seenIsland
            // TODO: mark this island as conquered in comms
            if (rc.canSenseLocation(seenIsland)) {
                // if i get to the island and its taken already then find another island
                if (rc.senseTeamOccupyingIsland(rc.senseIsland(seenIsland)) != Team.NEUTRAL) {
                    seenIsland = findUnconqueredIsland();
                    Debug.printString("Island already claimed");
                    return;
                }
            }
            if (rc.canPlaceAnchor()) {
                rc.placeAnchor();
                recordIsland(rc.senseIsland(currLoc), whichSector(currLoc));
                seenIsland = null;
                Debug.printString("Placed anchor");
            } else {
                Pathfinding.move(seenIsland);
            }
        } else if (closestWell != null && rc.getAnchor() == null) {
            // only go to a well if we have ava
            Debug.printString("Found well at " + closestWell.getMapLocation());
            if (rc.canCollectResource(closestWell.getMapLocation(), -1)) {
                Debug.printString("Collecting");
                rc.collectResource(closestWell.getMapLocation(), -1);
            } else {
                Debug.printString("Moving");
                Pathfinding.move(closestWell.getMapLocation());
            }
            return;
        } else {
            MapLocation target = findClosestWell();
            // If there is no visible well, just explore.
            if (target == null) {
                target = Explore.getExploreTarget();
                Debug.printString("Exploring");
            } else {
                Debug.printString("going to known well at " + target);
            }
            Pathfinding.move(target);
        }
    }

    public Boolean runAway() throws GameActionException {
        RobotInfo[] enemyAttackable = getEnemyAttackable();
        RobotInfo[] friendlyAttackable = getFriendlyAttackable();
        RobotInfo closestEnemy = getClosestRobot(enemyAttackable);
        RobotInfo closestFriendly = getClosestRobot(friendlyAttackable);
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
        }

        if (target != null) {
            if (rc.getResourceAmount(resourceTarget) > 0 && rc.canAttack(closestEnemy.location)) {
                rc.attack((closestEnemy.location));
            } else if (rc.getResourceAmount(resourceTarget) / 5 >= closestEnemy.health && rc.isActionReady()) {
                Pathfinding.move(closestEnemy.location);
                if (rc.canAttack(closestEnemy.location)) {
                    rc.attack(closestEnemy.location);
                }
            }
            Pathfinding.move(target);
            Debug.printString(str);
        }
        return target != null;
    }
}
