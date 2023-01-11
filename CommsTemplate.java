package examplefuncsplayer;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;

    private static int[] thresholds = { 0, 1, 2, 3, 5, 10, 50 };
    // CONSTS

    // ControlStatus priorities are in increasing priority.
    public class ControlStatus {
        public static final int UNKNOWN = 0;
        public static final int EMPTY = 1;
        public static final int FRIENDLY_ISLAND = 2;
        public static final int NEUTRAL_ISLAND = 3;

        public static final int MIN_ENEMY_STATUS = 4;
        public static final int ENEMY_PASSIVE = 4;
        public static final int ENEMY_ISLAND = 5;
        public static final int ENEMY_AGGRESIVE = 6;

        public static final int NUM_CONTROL_STATUS = 8;
    }

    public class ClaimStatus {
        public static final int UNCLAIMED = 0;
        public static final int CLAIMED = 1;
    }

    public class HQFlag {
        public static final int CARRIER_ADAMANTIUM = 0;
        public static final int CARRIER_MANA = 1;
    }

    public final static int UNDEFINED_SECTOR_INDEX = 127;

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
        for (int i = 63; i >= 0; i--) {
            bufferPool[i] = rc.readSharedArray(i);
            dirtyFlags[i] = false;
        }
    }

    public static void writeToBufferPool(int idx, int value) throws GameActionException {
        bufferPool[idx] = value;
        dirtyFlags[idx] = true;
    }

    public static void flushBufferPool() throws GameActionException {
        for (int i = 63; i >= 0; i--) {
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
