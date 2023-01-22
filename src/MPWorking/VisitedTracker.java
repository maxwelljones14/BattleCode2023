package MPWorking;

import battlecode.common.MapLocation;

public class VisitedTracker {
    final static int MAX_MAP_SIZE = 64;
    final static int INT_BITS = 32;
    final static int ARRAY_SIZE = 128;

    static int[] visitedLocations;

    static void reset() {
        visitedLocations = new int[ARRAY_SIZE];
    }

    static void add(MapLocation loc) {
        int arrayPos = (loc.x) * (1 + (loc.y) / INT_BITS);
        int bitPos = loc.y % INT_BITS;
        visitedLocations[arrayPos] |= (1 << bitPos);
    }

    static boolean check(MapLocation loc) {
        int arrayPos = (loc.x) * (1 + (loc.y) / INT_BITS);
        int bitPos = loc.y % INT_BITS;
        return ((visitedLocations[arrayPos] & (1 << bitPos)) > 0);
    }

}
