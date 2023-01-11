package examplefuncsplayer;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;

    private static int[] thresholds = { 0, 1, 2, 3, 5, 10, 50 };

    // CONSTS

    public class IslandTeam {
        public static final int NEUTRAL = 0;
        public static final int FRIENDLY = 1;
        public static final int ENEMY = 2;
    }

    public class ExploredStatus {
        public static final int UNEXPLORED = 0;
        public static final int EXPLORED = 1;
    }

    final int UNDEFINED_SECTOR_INDEX = 127;

    public static void init(RobotController r) {
        rc = r;

        bufferPool = new int[64];
        dirtyFlags = new boolean[64];
    }

    public static MapLocation readOurHqLocation(int idx) throws GameActionException {
        return new MapLocation(readOurHqXCoord(idx), readOurHqYCoord(idx));
    }

    public static void writeOurHqLocation(int idx, MapLocation loc) throws GameActionException {
        writeOurHqXCoord(idx, loc.x);
        writeOurHqYCoord(idx, loc.y);
    }

    public static void initBufferPool() throws GameActionException {
        for (int i = 0; i < 64; i++) {
            bufferPool[i] = rc.readSharedArray(i);
            dirtyFlags[i] = false;
        }
    }

    public static void writeToBufferPool(int idx, int value) throws GameActionException {
        bufferPool[idx] = value;
        dirtyFlags[idx] = true;
    }

    public static void flushBufferPool() throws GameActionException {
        for (int i = 0; i < 64; i++) {
            if (dirtyFlags[i]) {
                rc.writeSharedArray(i, bufferPool[i]);
            }
        }
    }

    private static int adjustCount(int value) {
        if (value <= thresholds[0]) {
            return 0;
        } else if (value <= thresholds[1]) {
            return 1;
        } else if (value <= thresholds[2]) {
            return 2;
        } else if (value <= thresholds[3]) {
            return 3;
        } else if (value <= thresholds[4]) {
            return 4;
        } else if (value <= thresholds[5]) {
            return 5;
        } else if (value <= thresholds[6]) {
            return 6;
        } else {
            return 7;
        }
    }

    // PRIORITY SECTOR INIT

    // SECTOR CONTROL STATUS RESET

    // MAIN READ AND WRITE METHODS

    // BUFFER POOL READ AND WRITE METHODS

}