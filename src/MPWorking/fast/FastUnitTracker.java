package MPWorking.fast;

import battlecode.common.*;

/**
 * Tracks units that are alive and have been seen in the last
 * numRoundsToStaleUnit
 * 
 * Add units to track with add(int id)
 */
public class FastUnitTracker {
    RobotController rc;
    int numRoundsToStaleUnit;
    FastIntIntMap unitToLastRoundSeenMap;
    FastIterableIntSet unitAliveSet;
    FastIntSet unitTrackSet;

    int unitToCheck;

    static final int MAX_UNITS_TO_PROCESS = 10;
    static final int DEFAULT_MAX_LEN = 100;

    static final int[] RADII = { 34, 20, 13, 5, 2 };

    public FastUnitTracker(RobotController r, int roundsToStaleUnit) {
        this(r, roundsToStaleUnit, DEFAULT_MAX_LEN);
    }

    public FastUnitTracker(RobotController r, int roundsToStaleUnit, int len) {
        rc = r;
        numRoundsToStaleUnit = roundsToStaleUnit;
        unitToLastRoundSeenMap = new FastIntIntMap();
        unitAliveSet = new FastIterableIntSet(len);
        unitTrackSet = new FastIntSet();
    }

    // Costs ~1500 BC
    public void update() throws GameActionException {
        RobotInfo[] robots = null;
        for (int i = 0; i < RADII.length; i++) {
            robots = rc.senseNearbyRobots(RADII[i], rc.getTeam());
            if (robots.length < MAX_UNITS_TO_PROCESS) {
                break;
            }
        }

        int id;
        for (int i = robots.length - 1; i >= 0; i--) {
            id = robots[i].ID;
            if (unitTrackSet.contains(id)) {
                unitToLastRoundSeenMap.addReplace(id, rc.getRoundNum());
                unitAliveSet.add(id);
            }
        }

        int lastUnitToCheck = Math.max(0, unitToCheck - MAX_UNITS_TO_PROCESS);
        while (unitToCheck >= lastUnitToCheck) {
            int unitID = unitAliveSet.ints[unitToCheck--];
            if (rc.getRoundNum() - unitToLastRoundSeenMap.getVal(unitID) > numRoundsToStaleUnit) {
                unitAliveSet.remove(unitID);
            }
        }

        if (unitToCheck == -1) {
            unitAliveSet.updateIterable();
            unitToCheck = unitAliveSet.size() - 1;
        }
    }

    public void add(int id) {
        unitTrackSet.add(id);
    }

    public int size() {
        return unitAliveSet.size();
    }

    public int isUnitAlive(int id) {
        return unitAliveSet.contains(id) ? 1 : 0;
    }
}
