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

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        if (Util.rng.nextInt(2) == 0) {
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

        Debug.printString("seen island at " + seenIsland);

        // if we're near home and have an island in our history, pick up anchor if home
        // has anchor
        if (seenIsland != null && rc.canTakeAnchor(home, Anchor.STANDARD)) {
            rc.takeAnchor(home, Anchor.STANDARD);
        }

        // mark the first island we see
        if (seenIsland == null) {
            seenIsland = findUnconqueredIsland();
        }

        // If we are at capacity, go home.
        if (rc.getResourceAmount(resourceTarget) == GameConstants.CARRIER_CAPACITY) {
            rc.setIndicatorString("At capacity, going home");
            if (rc.canTransferResource(home, resourceTarget, rc.getResourceAmount(resourceTarget))) {
                rc.transferResource(home, resourceTarget, rc.getResourceAmount(resourceTarget));
            } else {
                Pathfinding.move(home);
            }
            return;
        }

        // If we can see a well, move towards it
        WellInfo[] wells = rc.senseNearbyWells(resourceTarget);
        WellInfo closestWell = null;
        int closestDist = Integer.MAX_VALUE;
        for (WellInfo well : wells) {
            recordWell(well);
            MapLocation wellLocation = well.getMapLocation();
            int dist = Util.distance(rc.getLocation(), wellLocation);
            if (dist < closestDist) {
                closestDist = dist;
                closestWell = well;
            }
        }

        // if we have an anchor and have island location, go to that first
        if (seenIsland != null && rc.getAnchor() != null) {
            Debug.printString("seen island at " + seenIsland + "; have an anchor");
            // if we're near an island and have an anchor, place the anchor and reset
            // seenIsland
            // TODO: mark this island as conquered in comms
            if (rc.canSenseLocation(seenIsland)) {
                // if i get to the island and its taken already then find another island
                if (rc.senseTeamOccupyingIsland(rc.senseIsland(seenIsland)) != Team.NEUTRAL) {
                    seenIsland = findUnconqueredIsland();
                    return;
                }
            }
            if (rc.canPlaceAnchor()) {
                rc.placeAnchor();
                seenIsland = null;
            } else {
                Pathfinding.move(seenIsland);
            }
        } else if (closestWell != null) {
            Debug.printString("Moving towards well at " + closestWell.getMapLocation());
            // TODO: canCollectResource is broken right now
            // It does not confirm the adjacency condition
            if (rc.getLocation().isAdjacentTo(closestWell.getMapLocation()) &&
                    rc.canCollectResource(closestWell.getMapLocation(), -1)) {
                rc.collectResource(closestWell.getMapLocation(), -1);
            } else {
                Pathfinding.move(closestWell.getMapLocation());
            }
            return;
        } else {
            // If there is no visible well, just explore.
            MapLocation target = Explore.getExploreTarget();
            Pathfinding.move(target);
        }
    }

}
