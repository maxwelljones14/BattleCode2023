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

    public Carrier(RobotController r) throws GameActionException {
        super(r);
        if (Util.rng.nextInt(2) == 0) {
            resourceTarget = ResourceType.ADAMANTIUM;
        } else {
            resourceTarget = ResourceType.MANA;
        }
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

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
            MapLocation wellLocation = well.getMapLocation();
            int dist = Util.distance(rc.getLocation(), wellLocation);
            if (dist < closestDist) {
                closestDist = dist;
                closestWell = well;
            }
        }

        if (closestWell != null) {
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
