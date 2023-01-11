package MPWorking;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;

    private static int[] thresholds = { 0, 1, 2, 3, 5, 10, 50 };

    public final static int OUR_HQ_SLOTS = 4;
    public final static int SECTOR_SLOTS = 100;
    public final static int COMBAT_SECTOR_SLOTS = 8;
    public final static int EXPLORE_SECTOR_SLOTS = 10;
    public final static int MINE_SECTOR_SLOTS = 10;

    // ControlStatus priorities are in increasing priority.
    public class ControlStatus {
        public static final int UNKNOWN = 0;
        public static final int EMPTY = 1;
        public static final int EXPLORING = 2;
        public static final int FRIENDLY = 3;
        public static final int NEUTRAL = 4;
        public static final int ENEMY = 5;
    }

    public class ClaimStatus {
        public static final int UNCLAIMED = 0;
        public static final int CLAIMED = 1;
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

    public static void initPrioritySectors() throws GameActionException {
        rc.writeSharedArray(46, 15);
        rc.writeSharedArray(47, 65535);
        rc.writeSharedArray(48, 65535);
        rc.writeSharedArray(49, 65535);
        rc.writeSharedArray(50, 63479);
        rc.writeSharedArray(51, 63479);
        rc.writeSharedArray(52, 63479);
        rc.writeSharedArray(53, 63479);
        rc.writeSharedArray(54, 63479);
        rc.writeSharedArray(55, 61948);
        rc.writeSharedArray(56, 32543);
        rc.writeSharedArray(57, 51185);
        rc.writeSharedArray(58, 64639);
        rc.writeSharedArray(59, 8135);
        rc.writeSharedArray(60, 61948);
        rc.writeSharedArray(61, 32512);
    }


    public static void resetAllSectorControlStatus() throws GameActionException {
        rc.writeSharedArray(3, rc.readSharedArray(3) & 61923);
        rc.writeSharedArray(4, rc.readSharedArray(4) & 51087);
        rc.writeSharedArray(5, rc.readSharedArray(5) & 7740);
        rc.writeSharedArray(6, rc.readSharedArray(6) & 30961);
        rc.writeSharedArray(7, rc.readSharedArray(7) & 58311);
        rc.writeSharedArray(8, rc.readSharedArray(8) & 36638);
        rc.writeSharedArray(9, rc.readSharedArray(9) & 15480);
        rc.writeSharedArray(10, rc.readSharedArray(10) & 61923);
        rc.writeSharedArray(11, rc.readSharedArray(11) & 51087);
        rc.writeSharedArray(12, rc.readSharedArray(12) & 7740);
        rc.writeSharedArray(13, rc.readSharedArray(13) & 30961);
        rc.writeSharedArray(14, rc.readSharedArray(14) & 58311);
        rc.writeSharedArray(15, rc.readSharedArray(15) & 36638);
        rc.writeSharedArray(16, rc.readSharedArray(16) & 15480);
        rc.writeSharedArray(17, rc.readSharedArray(17) & 61923);
        rc.writeSharedArray(18, rc.readSharedArray(18) & 51087);
        rc.writeSharedArray(19, rc.readSharedArray(19) & 7740);
        rc.writeSharedArray(20, rc.readSharedArray(20) & 30961);
        rc.writeSharedArray(21, rc.readSharedArray(21) & 58311);
        rc.writeSharedArray(22, rc.readSharedArray(22) & 36638);
        rc.writeSharedArray(23, rc.readSharedArray(23) & 15480);
        rc.writeSharedArray(24, rc.readSharedArray(24) & 61923);
        rc.writeSharedArray(25, rc.readSharedArray(25) & 51087);
        rc.writeSharedArray(26, rc.readSharedArray(26) & 7740);
        rc.writeSharedArray(27, rc.readSharedArray(27) & 30961);
        rc.writeSharedArray(28, rc.readSharedArray(28) & 58311);
        rc.writeSharedArray(29, rc.readSharedArray(29) & 36638);
        rc.writeSharedArray(30, rc.readSharedArray(30) & 15480);
        rc.writeSharedArray(31, rc.readSharedArray(31) & 61923);
        rc.writeSharedArray(32, rc.readSharedArray(32) & 51087);
        rc.writeSharedArray(33, rc.readSharedArray(33) & 7740);
        rc.writeSharedArray(34, rc.readSharedArray(34) & 30961);
        rc.writeSharedArray(35, rc.readSharedArray(35) & 58311);
        rc.writeSharedArray(36, rc.readSharedArray(36) & 36638);
        rc.writeSharedArray(37, rc.readSharedArray(37) & 15480);
        rc.writeSharedArray(38, rc.readSharedArray(38) & 61923);
        rc.writeSharedArray(39, rc.readSharedArray(39) & 51087);
        rc.writeSharedArray(40, rc.readSharedArray(40) & 7740);
        rc.writeSharedArray(41, rc.readSharedArray(41) & 30961);
        rc.writeSharedArray(42, rc.readSharedArray(42) & 58311);
        rc.writeSharedArray(43, rc.readSharedArray(43) & 36638);
        rc.writeSharedArray(44, rc.readSharedArray(44) & 15480);
        rc.writeSharedArray(45, rc.readSharedArray(45) & 61923);
        rc.writeSharedArray(46, rc.readSharedArray(46) & 51087);
    }

    public static int readOurHqXCoord(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 64512) >>> 10;
            case 1:
                return ((rc.readSharedArray(0) & 15) << 2) + ((rc.readSharedArray(1) & 49152) >>> 14);
            case 2:
                return (rc.readSharedArray(1) & 252) >>> 2;
            case 3:
                return (rc.readSharedArray(2) & 4032) >>> 6;
            default:
                return -1;
        }
    }

    public static void writeOurHqXCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 1023) | (value << 10));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 16383) | ((value & 3) << 14));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65283) | (value << 2));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 61503) | (value << 6));
                break;
        }
    }

    public static void writeBPOurHqXCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 1023) | (value << 10));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(1, (bufferPool[1] & 16383) | ((value & 3) << 14));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65283) | (value << 2));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 61503) | (value << 6));
                break;
        }
    }

    public static int readOurHqYCoord(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 1008) >>> 4;
            case 1:
                return (rc.readSharedArray(1) & 16128) >>> 8;
            case 2:
                return ((rc.readSharedArray(1) & 3) << 4) + ((rc.readSharedArray(2) & 61440) >>> 12);
            case 3:
                return (rc.readSharedArray(2) & 63);
            default:
                return -1;
        }
    }

    public static void writeOurHqYCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 64527) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 49407) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 4095) | ((value & 15) << 12));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65472) | (value));
                break;
        }
    }

    public static void writeBPOurHqYCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 64527) | (value << 4));
                break;
            case 1:
                writeToBufferPool(1, (bufferPool[1] & 49407) | (value << 8));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(2, (bufferPool[2] & 4095) | ((value & 15) << 12));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65472) | (value));
                break;
        }
    }

    public static int readOurHqAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 65520) >>> 4;
            case 1:
                return ((rc.readSharedArray(0) & 15) << 8) + ((rc.readSharedArray(1) & 65280) >>> 8);
            case 2:
                return ((rc.readSharedArray(1) & 255) << 4) + ((rc.readSharedArray(2) & 61440) >>> 12);
            case 3:
                return (rc.readSharedArray(2) & 4095);
            default:
                return -1;
        }
    }

    public static void writeOurHqAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 15) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65520) | ((value & 3840) >>> 8));
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 255) | ((value & 255) << 8));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65280) | ((value & 4080) >>> 4));
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 4095) | ((value & 15) << 12));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 61440) | (value));
                break;
        }
    }

    public static void writeBPOurHqAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 15) | (value << 4));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65520) | ((value & 3840) >>> 8));
                writeToBufferPool(1, (bufferPool[1] & 255) | ((value & 255) << 8));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65280) | ((value & 4080) >>> 4));
                writeToBufferPool(2, (bufferPool[2] & 4095) | ((value & 15) << 12));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 61440) | (value));
                break;
        }
    }

    public static int readSectorIslands(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 32768) >>> 15;
            case 1:
                return (rc.readSharedArray(3) & 256) >>> 8;
            case 2:
                return (rc.readSharedArray(3) & 2) >>> 1;
            case 3:
                return (rc.readSharedArray(4) & 1024) >>> 10;
            case 4:
                return (rc.readSharedArray(4) & 8) >>> 3;
            case 5:
                return (rc.readSharedArray(5) & 4096) >>> 12;
            case 6:
                return (rc.readSharedArray(5) & 32) >>> 5;
            case 7:
                return (rc.readSharedArray(6) & 16384) >>> 14;
            case 8:
                return (rc.readSharedArray(6) & 128) >>> 7;
            case 9:
                return (rc.readSharedArray(6) & 1);
            case 10:
                return (rc.readSharedArray(7) & 512) >>> 9;
            case 11:
                return (rc.readSharedArray(7) & 4) >>> 2;
            case 12:
                return (rc.readSharedArray(8) & 2048) >>> 11;
            case 13:
                return (rc.readSharedArray(8) & 16) >>> 4;
            case 14:
                return (rc.readSharedArray(9) & 8192) >>> 13;
            case 15:
                return (rc.readSharedArray(9) & 64) >>> 6;
            case 16:
                return (rc.readSharedArray(10) & 32768) >>> 15;
            case 17:
                return (rc.readSharedArray(10) & 256) >>> 8;
            case 18:
                return (rc.readSharedArray(10) & 2) >>> 1;
            case 19:
                return (rc.readSharedArray(11) & 1024) >>> 10;
            case 20:
                return (rc.readSharedArray(11) & 8) >>> 3;
            case 21:
                return (rc.readSharedArray(12) & 4096) >>> 12;
            case 22:
                return (rc.readSharedArray(12) & 32) >>> 5;
            case 23:
                return (rc.readSharedArray(13) & 16384) >>> 14;
            case 24:
                return (rc.readSharedArray(13) & 128) >>> 7;
            case 25:
                return (rc.readSharedArray(13) & 1);
            case 26:
                return (rc.readSharedArray(14) & 512) >>> 9;
            case 27:
                return (rc.readSharedArray(14) & 4) >>> 2;
            case 28:
                return (rc.readSharedArray(15) & 2048) >>> 11;
            case 29:
                return (rc.readSharedArray(15) & 16) >>> 4;
            case 30:
                return (rc.readSharedArray(16) & 8192) >>> 13;
            case 31:
                return (rc.readSharedArray(16) & 64) >>> 6;
            case 32:
                return (rc.readSharedArray(17) & 32768) >>> 15;
            case 33:
                return (rc.readSharedArray(17) & 256) >>> 8;
            case 34:
                return (rc.readSharedArray(17) & 2) >>> 1;
            case 35:
                return (rc.readSharedArray(18) & 1024) >>> 10;
            case 36:
                return (rc.readSharedArray(18) & 8) >>> 3;
            case 37:
                return (rc.readSharedArray(19) & 4096) >>> 12;
            case 38:
                return (rc.readSharedArray(19) & 32) >>> 5;
            case 39:
                return (rc.readSharedArray(20) & 16384) >>> 14;
            case 40:
                return (rc.readSharedArray(20) & 128) >>> 7;
            case 41:
                return (rc.readSharedArray(20) & 1);
            case 42:
                return (rc.readSharedArray(21) & 512) >>> 9;
            case 43:
                return (rc.readSharedArray(21) & 4) >>> 2;
            case 44:
                return (rc.readSharedArray(22) & 2048) >>> 11;
            case 45:
                return (rc.readSharedArray(22) & 16) >>> 4;
            case 46:
                return (rc.readSharedArray(23) & 8192) >>> 13;
            case 47:
                return (rc.readSharedArray(23) & 64) >>> 6;
            case 48:
                return (rc.readSharedArray(24) & 32768) >>> 15;
            case 49:
                return (rc.readSharedArray(24) & 256) >>> 8;
            case 50:
                return (rc.readSharedArray(24) & 2) >>> 1;
            case 51:
                return (rc.readSharedArray(25) & 1024) >>> 10;
            case 52:
                return (rc.readSharedArray(25) & 8) >>> 3;
            case 53:
                return (rc.readSharedArray(26) & 4096) >>> 12;
            case 54:
                return (rc.readSharedArray(26) & 32) >>> 5;
            case 55:
                return (rc.readSharedArray(27) & 16384) >>> 14;
            case 56:
                return (rc.readSharedArray(27) & 128) >>> 7;
            case 57:
                return (rc.readSharedArray(27) & 1);
            case 58:
                return (rc.readSharedArray(28) & 512) >>> 9;
            case 59:
                return (rc.readSharedArray(28) & 4) >>> 2;
            case 60:
                return (rc.readSharedArray(29) & 2048) >>> 11;
            case 61:
                return (rc.readSharedArray(29) & 16) >>> 4;
            case 62:
                return (rc.readSharedArray(30) & 8192) >>> 13;
            case 63:
                return (rc.readSharedArray(30) & 64) >>> 6;
            case 64:
                return (rc.readSharedArray(31) & 32768) >>> 15;
            case 65:
                return (rc.readSharedArray(31) & 256) >>> 8;
            case 66:
                return (rc.readSharedArray(31) & 2) >>> 1;
            case 67:
                return (rc.readSharedArray(32) & 1024) >>> 10;
            case 68:
                return (rc.readSharedArray(32) & 8) >>> 3;
            case 69:
                return (rc.readSharedArray(33) & 4096) >>> 12;
            case 70:
                return (rc.readSharedArray(33) & 32) >>> 5;
            case 71:
                return (rc.readSharedArray(34) & 16384) >>> 14;
            case 72:
                return (rc.readSharedArray(34) & 128) >>> 7;
            case 73:
                return (rc.readSharedArray(34) & 1);
            case 74:
                return (rc.readSharedArray(35) & 512) >>> 9;
            case 75:
                return (rc.readSharedArray(35) & 4) >>> 2;
            case 76:
                return (rc.readSharedArray(36) & 2048) >>> 11;
            case 77:
                return (rc.readSharedArray(36) & 16) >>> 4;
            case 78:
                return (rc.readSharedArray(37) & 8192) >>> 13;
            case 79:
                return (rc.readSharedArray(37) & 64) >>> 6;
            case 80:
                return (rc.readSharedArray(38) & 32768) >>> 15;
            case 81:
                return (rc.readSharedArray(38) & 256) >>> 8;
            case 82:
                return (rc.readSharedArray(38) & 2) >>> 1;
            case 83:
                return (rc.readSharedArray(39) & 1024) >>> 10;
            case 84:
                return (rc.readSharedArray(39) & 8) >>> 3;
            case 85:
                return (rc.readSharedArray(40) & 4096) >>> 12;
            case 86:
                return (rc.readSharedArray(40) & 32) >>> 5;
            case 87:
                return (rc.readSharedArray(41) & 16384) >>> 14;
            case 88:
                return (rc.readSharedArray(41) & 128) >>> 7;
            case 89:
                return (rc.readSharedArray(41) & 1);
            case 90:
                return (rc.readSharedArray(42) & 512) >>> 9;
            case 91:
                return (rc.readSharedArray(42) & 4) >>> 2;
            case 92:
                return (rc.readSharedArray(43) & 2048) >>> 11;
            case 93:
                return (rc.readSharedArray(43) & 16) >>> 4;
            case 94:
                return (rc.readSharedArray(44) & 8192) >>> 13;
            case 95:
                return (rc.readSharedArray(44) & 64) >>> 6;
            case 96:
                return (rc.readSharedArray(45) & 32768) >>> 15;
            case 97:
                return (rc.readSharedArray(45) & 256) >>> 8;
            case 98:
                return (rc.readSharedArray(45) & 2) >>> 1;
            case 99:
                return (rc.readSharedArray(46) & 1024) >>> 10;
            default:
                return -1;
        }
    }

    public static void writeSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 32767) | (value << 15));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65279) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65533) | (value << 1));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 64511) | (value << 10));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65527) | (value << 3));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 61439) | (value << 12));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65503) | (value << 5));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 49151) | (value << 14));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65407) | (value << 7));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65534) | (value));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65023) | (value << 9));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65531) | (value << 2));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63487) | (value << 11));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65519) | (value << 4));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 57343) | (value << 13));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65471) | (value << 6));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 32767) | (value << 15));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65279) | (value << 8));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65533) | (value << 1));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 64511) | (value << 10));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65527) | (value << 3));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 61439) | (value << 12));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65503) | (value << 5));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 49151) | (value << 14));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65407) | (value << 7));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65534) | (value));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65023) | (value << 9));
                break;
            case 27:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65531) | (value << 2));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 63487) | (value << 11));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65519) | (value << 4));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 57343) | (value << 13));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65471) | (value << 6));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 32767) | (value << 15));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65279) | (value << 8));
                break;
            case 34:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65533) | (value << 1));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 64511) | (value << 10));
                break;
            case 36:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65527) | (value << 3));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 61439) | (value << 12));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65503) | (value << 5));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 49151) | (value << 14));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65407) | (value << 7));
                break;
            case 41:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65534) | (value));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65023) | (value << 9));
                break;
            case 43:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65531) | (value << 2));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 63487) | (value << 11));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65519) | (value << 4));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 57343) | (value << 13));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65471) | (value << 6));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 32767) | (value << 15));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65279) | (value << 8));
                break;
            case 50:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65533) | (value << 1));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 64511) | (value << 10));
                break;
            case 52:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65527) | (value << 3));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 61439) | (value << 12));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65503) | (value << 5));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 49151) | (value << 14));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65407) | (value << 7));
                break;
            case 57:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65534) | (value));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65023) | (value << 9));
                break;
            case 59:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65531) | (value << 2));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63487) | (value << 11));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65519) | (value << 4));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 57343) | (value << 13));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65471) | (value << 6));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 32767) | (value << 15));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65279) | (value << 8));
                break;
            case 66:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65533) | (value << 1));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 64511) | (value << 10));
                break;
            case 68:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65527) | (value << 3));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 61439) | (value << 12));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65503) | (value << 5));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 49151) | (value << 14));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65407) | (value << 7));
                break;
            case 73:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65534) | (value));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65023) | (value << 9));
                break;
            case 75:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65531) | (value << 2));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 63487) | (value << 11));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65519) | (value << 4));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 57343) | (value << 13));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65471) | (value << 6));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 32767) | (value << 15));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65279) | (value << 8));
                break;
            case 82:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65533) | (value << 1));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 64511) | (value << 10));
                break;
            case 84:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65527) | (value << 3));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 61439) | (value << 12));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65503) | (value << 5));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 49151) | (value << 14));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65407) | (value << 7));
                break;
            case 89:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65534) | (value));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65023) | (value << 9));
                break;
            case 91:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65531) | (value << 2));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63487) | (value << 11));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65519) | (value << 4));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 57343) | (value << 13));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65471) | (value << 6));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 32767) | (value << 15));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65279) | (value << 8));
                break;
            case 98:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65533) | (value << 1));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 64511) | (value << 10));
                break;
        }
    }

    public static void writeBPSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 32767) | (value << 15));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65279) | (value << 8));
                break;
            case 2:
                writeToBufferPool(3, (bufferPool[3] & 65533) | (value << 1));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 64511) | (value << 10));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65527) | (value << 3));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 61439) | (value << 12));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65503) | (value << 5));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 49151) | (value << 14));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65407) | (value << 7));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65534) | (value));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65023) | (value << 9));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65531) | (value << 2));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 63487) | (value << 11));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65519) | (value << 4));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 57343) | (value << 13));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65471) | (value << 6));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 32767) | (value << 15));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65279) | (value << 8));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65533) | (value << 1));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 64511) | (value << 10));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65527) | (value << 3));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 61439) | (value << 12));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65503) | (value << 5));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 49151) | (value << 14));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65407) | (value << 7));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 65534) | (value));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65023) | (value << 9));
                break;
            case 27:
                writeToBufferPool(14, (bufferPool[14] & 65531) | (value << 2));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 63487) | (value << 11));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65519) | (value << 4));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 57343) | (value << 13));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65471) | (value << 6));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 32767) | (value << 15));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65279) | (value << 8));
                break;
            case 34:
                writeToBufferPool(17, (bufferPool[17] & 65533) | (value << 1));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 64511) | (value << 10));
                break;
            case 36:
                writeToBufferPool(18, (bufferPool[18] & 65527) | (value << 3));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 61439) | (value << 12));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65503) | (value << 5));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 49151) | (value << 14));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65407) | (value << 7));
                break;
            case 41:
                writeToBufferPool(20, (bufferPool[20] & 65534) | (value));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65023) | (value << 9));
                break;
            case 43:
                writeToBufferPool(21, (bufferPool[21] & 65531) | (value << 2));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 63487) | (value << 11));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65519) | (value << 4));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 57343) | (value << 13));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65471) | (value << 6));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 32767) | (value << 15));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65279) | (value << 8));
                break;
            case 50:
                writeToBufferPool(24, (bufferPool[24] & 65533) | (value << 1));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 64511) | (value << 10));
                break;
            case 52:
                writeToBufferPool(25, (bufferPool[25] & 65527) | (value << 3));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 61439) | (value << 12));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65503) | (value << 5));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 49151) | (value << 14));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65407) | (value << 7));
                break;
            case 57:
                writeToBufferPool(27, (bufferPool[27] & 65534) | (value));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65023) | (value << 9));
                break;
            case 59:
                writeToBufferPool(28, (bufferPool[28] & 65531) | (value << 2));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 63487) | (value << 11));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65519) | (value << 4));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 57343) | (value << 13));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65471) | (value << 6));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 32767) | (value << 15));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65279) | (value << 8));
                break;
            case 66:
                writeToBufferPool(31, (bufferPool[31] & 65533) | (value << 1));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 64511) | (value << 10));
                break;
            case 68:
                writeToBufferPool(32, (bufferPool[32] & 65527) | (value << 3));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 61439) | (value << 12));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65503) | (value << 5));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 49151) | (value << 14));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65407) | (value << 7));
                break;
            case 73:
                writeToBufferPool(34, (bufferPool[34] & 65534) | (value));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65023) | (value << 9));
                break;
            case 75:
                writeToBufferPool(35, (bufferPool[35] & 65531) | (value << 2));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 63487) | (value << 11));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65519) | (value << 4));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 57343) | (value << 13));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65471) | (value << 6));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 32767) | (value << 15));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65279) | (value << 8));
                break;
            case 82:
                writeToBufferPool(38, (bufferPool[38] & 65533) | (value << 1));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 64511) | (value << 10));
                break;
            case 84:
                writeToBufferPool(39, (bufferPool[39] & 65527) | (value << 3));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 61439) | (value << 12));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65503) | (value << 5));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 49151) | (value << 14));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65407) | (value << 7));
                break;
            case 89:
                writeToBufferPool(41, (bufferPool[41] & 65534) | (value));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65023) | (value << 9));
                break;
            case 91:
                writeToBufferPool(42, (bufferPool[42] & 65531) | (value << 2));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 63487) | (value << 11));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65519) | (value << 4));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 57343) | (value << 13));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65471) | (value << 6));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 32767) | (value << 15));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65279) | (value << 8));
                break;
            case 98:
                writeToBufferPool(45, (bufferPool[45] & 65533) | (value << 1));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 64511) | (value << 10));
                break;
        }
    }

    public static int readSectorAdamantiumFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 16384) >>> 14;
            case 1:
                return (rc.readSharedArray(3) & 128) >>> 7;
            case 2:
                return (rc.readSharedArray(3) & 1);
            case 3:
                return (rc.readSharedArray(4) & 512) >>> 9;
            case 4:
                return (rc.readSharedArray(4) & 4) >>> 2;
            case 5:
                return (rc.readSharedArray(5) & 2048) >>> 11;
            case 6:
                return (rc.readSharedArray(5) & 16) >>> 4;
            case 7:
                return (rc.readSharedArray(6) & 8192) >>> 13;
            case 8:
                return (rc.readSharedArray(6) & 64) >>> 6;
            case 9:
                return (rc.readSharedArray(7) & 32768) >>> 15;
            case 10:
                return (rc.readSharedArray(7) & 256) >>> 8;
            case 11:
                return (rc.readSharedArray(7) & 2) >>> 1;
            case 12:
                return (rc.readSharedArray(8) & 1024) >>> 10;
            case 13:
                return (rc.readSharedArray(8) & 8) >>> 3;
            case 14:
                return (rc.readSharedArray(9) & 4096) >>> 12;
            case 15:
                return (rc.readSharedArray(9) & 32) >>> 5;
            case 16:
                return (rc.readSharedArray(10) & 16384) >>> 14;
            case 17:
                return (rc.readSharedArray(10) & 128) >>> 7;
            case 18:
                return (rc.readSharedArray(10) & 1);
            case 19:
                return (rc.readSharedArray(11) & 512) >>> 9;
            case 20:
                return (rc.readSharedArray(11) & 4) >>> 2;
            case 21:
                return (rc.readSharedArray(12) & 2048) >>> 11;
            case 22:
                return (rc.readSharedArray(12) & 16) >>> 4;
            case 23:
                return (rc.readSharedArray(13) & 8192) >>> 13;
            case 24:
                return (rc.readSharedArray(13) & 64) >>> 6;
            case 25:
                return (rc.readSharedArray(14) & 32768) >>> 15;
            case 26:
                return (rc.readSharedArray(14) & 256) >>> 8;
            case 27:
                return (rc.readSharedArray(14) & 2) >>> 1;
            case 28:
                return (rc.readSharedArray(15) & 1024) >>> 10;
            case 29:
                return (rc.readSharedArray(15) & 8) >>> 3;
            case 30:
                return (rc.readSharedArray(16) & 4096) >>> 12;
            case 31:
                return (rc.readSharedArray(16) & 32) >>> 5;
            case 32:
                return (rc.readSharedArray(17) & 16384) >>> 14;
            case 33:
                return (rc.readSharedArray(17) & 128) >>> 7;
            case 34:
                return (rc.readSharedArray(17) & 1);
            case 35:
                return (rc.readSharedArray(18) & 512) >>> 9;
            case 36:
                return (rc.readSharedArray(18) & 4) >>> 2;
            case 37:
                return (rc.readSharedArray(19) & 2048) >>> 11;
            case 38:
                return (rc.readSharedArray(19) & 16) >>> 4;
            case 39:
                return (rc.readSharedArray(20) & 8192) >>> 13;
            case 40:
                return (rc.readSharedArray(20) & 64) >>> 6;
            case 41:
                return (rc.readSharedArray(21) & 32768) >>> 15;
            case 42:
                return (rc.readSharedArray(21) & 256) >>> 8;
            case 43:
                return (rc.readSharedArray(21) & 2) >>> 1;
            case 44:
                return (rc.readSharedArray(22) & 1024) >>> 10;
            case 45:
                return (rc.readSharedArray(22) & 8) >>> 3;
            case 46:
                return (rc.readSharedArray(23) & 4096) >>> 12;
            case 47:
                return (rc.readSharedArray(23) & 32) >>> 5;
            case 48:
                return (rc.readSharedArray(24) & 16384) >>> 14;
            case 49:
                return (rc.readSharedArray(24) & 128) >>> 7;
            case 50:
                return (rc.readSharedArray(24) & 1);
            case 51:
                return (rc.readSharedArray(25) & 512) >>> 9;
            case 52:
                return (rc.readSharedArray(25) & 4) >>> 2;
            case 53:
                return (rc.readSharedArray(26) & 2048) >>> 11;
            case 54:
                return (rc.readSharedArray(26) & 16) >>> 4;
            case 55:
                return (rc.readSharedArray(27) & 8192) >>> 13;
            case 56:
                return (rc.readSharedArray(27) & 64) >>> 6;
            case 57:
                return (rc.readSharedArray(28) & 32768) >>> 15;
            case 58:
                return (rc.readSharedArray(28) & 256) >>> 8;
            case 59:
                return (rc.readSharedArray(28) & 2) >>> 1;
            case 60:
                return (rc.readSharedArray(29) & 1024) >>> 10;
            case 61:
                return (rc.readSharedArray(29) & 8) >>> 3;
            case 62:
                return (rc.readSharedArray(30) & 4096) >>> 12;
            case 63:
                return (rc.readSharedArray(30) & 32) >>> 5;
            case 64:
                return (rc.readSharedArray(31) & 16384) >>> 14;
            case 65:
                return (rc.readSharedArray(31) & 128) >>> 7;
            case 66:
                return (rc.readSharedArray(31) & 1);
            case 67:
                return (rc.readSharedArray(32) & 512) >>> 9;
            case 68:
                return (rc.readSharedArray(32) & 4) >>> 2;
            case 69:
                return (rc.readSharedArray(33) & 2048) >>> 11;
            case 70:
                return (rc.readSharedArray(33) & 16) >>> 4;
            case 71:
                return (rc.readSharedArray(34) & 8192) >>> 13;
            case 72:
                return (rc.readSharedArray(34) & 64) >>> 6;
            case 73:
                return (rc.readSharedArray(35) & 32768) >>> 15;
            case 74:
                return (rc.readSharedArray(35) & 256) >>> 8;
            case 75:
                return (rc.readSharedArray(35) & 2) >>> 1;
            case 76:
                return (rc.readSharedArray(36) & 1024) >>> 10;
            case 77:
                return (rc.readSharedArray(36) & 8) >>> 3;
            case 78:
                return (rc.readSharedArray(37) & 4096) >>> 12;
            case 79:
                return (rc.readSharedArray(37) & 32) >>> 5;
            case 80:
                return (rc.readSharedArray(38) & 16384) >>> 14;
            case 81:
                return (rc.readSharedArray(38) & 128) >>> 7;
            case 82:
                return (rc.readSharedArray(38) & 1);
            case 83:
                return (rc.readSharedArray(39) & 512) >>> 9;
            case 84:
                return (rc.readSharedArray(39) & 4) >>> 2;
            case 85:
                return (rc.readSharedArray(40) & 2048) >>> 11;
            case 86:
                return (rc.readSharedArray(40) & 16) >>> 4;
            case 87:
                return (rc.readSharedArray(41) & 8192) >>> 13;
            case 88:
                return (rc.readSharedArray(41) & 64) >>> 6;
            case 89:
                return (rc.readSharedArray(42) & 32768) >>> 15;
            case 90:
                return (rc.readSharedArray(42) & 256) >>> 8;
            case 91:
                return (rc.readSharedArray(42) & 2) >>> 1;
            case 92:
                return (rc.readSharedArray(43) & 1024) >>> 10;
            case 93:
                return (rc.readSharedArray(43) & 8) >>> 3;
            case 94:
                return (rc.readSharedArray(44) & 4096) >>> 12;
            case 95:
                return (rc.readSharedArray(44) & 32) >>> 5;
            case 96:
                return (rc.readSharedArray(45) & 16384) >>> 14;
            case 97:
                return (rc.readSharedArray(45) & 128) >>> 7;
            case 98:
                return (rc.readSharedArray(45) & 1);
            case 99:
                return (rc.readSharedArray(46) & 512) >>> 9;
            default:
                return -1;
        }
    }

    public static void writeSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 49151) | (value << 14));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65407) | (value << 7));
                break;
            case 2:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65534) | (value));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65023) | (value << 9));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65531) | (value << 2));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 63487) | (value << 11));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65519) | (value << 4));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 57343) | (value << 13));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65471) | (value << 6));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 32767) | (value << 15));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65279) | (value << 8));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65533) | (value << 1));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 64511) | (value << 10));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65527) | (value << 3));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 61439) | (value << 12));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65503) | (value << 5));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 49151) | (value << 14));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65407) | (value << 7));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65534) | (value));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65023) | (value << 9));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65531) | (value << 2));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 63487) | (value << 11));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65519) | (value << 4));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 57343) | (value << 13));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65471) | (value << 6));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 32767) | (value << 15));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65279) | (value << 8));
                break;
            case 27:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65533) | (value << 1));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 64511) | (value << 10));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65527) | (value << 3));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 61439) | (value << 12));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65503) | (value << 5));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 49151) | (value << 14));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65407) | (value << 7));
                break;
            case 34:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65534) | (value));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65023) | (value << 9));
                break;
            case 36:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65531) | (value << 2));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 63487) | (value << 11));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65519) | (value << 4));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 57343) | (value << 13));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65471) | (value << 6));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 32767) | (value << 15));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65279) | (value << 8));
                break;
            case 43:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65533) | (value << 1));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 64511) | (value << 10));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65527) | (value << 3));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 61439) | (value << 12));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65503) | (value << 5));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 49151) | (value << 14));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65407) | (value << 7));
                break;
            case 50:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65534) | (value));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65023) | (value << 9));
                break;
            case 52:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65531) | (value << 2));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 63487) | (value << 11));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65519) | (value << 4));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 57343) | (value << 13));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65471) | (value << 6));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 32767) | (value << 15));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65279) | (value << 8));
                break;
            case 59:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65533) | (value << 1));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 64511) | (value << 10));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65527) | (value << 3));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 61439) | (value << 12));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65503) | (value << 5));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 49151) | (value << 14));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65407) | (value << 7));
                break;
            case 66:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65534) | (value));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65023) | (value << 9));
                break;
            case 68:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65531) | (value << 2));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 63487) | (value << 11));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65519) | (value << 4));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 57343) | (value << 13));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65471) | (value << 6));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 32767) | (value << 15));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65279) | (value << 8));
                break;
            case 75:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65533) | (value << 1));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 64511) | (value << 10));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65527) | (value << 3));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 61439) | (value << 12));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65503) | (value << 5));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 49151) | (value << 14));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65407) | (value << 7));
                break;
            case 82:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65534) | (value));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65023) | (value << 9));
                break;
            case 84:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65531) | (value << 2));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 63487) | (value << 11));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65519) | (value << 4));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 57343) | (value << 13));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65471) | (value << 6));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 32767) | (value << 15));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65279) | (value << 8));
                break;
            case 91:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65533) | (value << 1));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 64511) | (value << 10));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65527) | (value << 3));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 61439) | (value << 12));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65503) | (value << 5));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 49151) | (value << 14));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65407) | (value << 7));
                break;
            case 98:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65534) | (value));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65023) | (value << 9));
                break;
        }
    }

    public static void writeBPSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 49151) | (value << 14));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65407) | (value << 7));
                break;
            case 2:
                writeToBufferPool(3, (bufferPool[3] & 65534) | (value));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65023) | (value << 9));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65531) | (value << 2));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 63487) | (value << 11));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65519) | (value << 4));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 57343) | (value << 13));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65471) | (value << 6));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 32767) | (value << 15));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65279) | (value << 8));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65533) | (value << 1));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 64511) | (value << 10));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65527) | (value << 3));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 61439) | (value << 12));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65503) | (value << 5));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 49151) | (value << 14));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65407) | (value << 7));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65534) | (value));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65023) | (value << 9));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65531) | (value << 2));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 63487) | (value << 11));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65519) | (value << 4));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 57343) | (value << 13));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65471) | (value << 6));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 32767) | (value << 15));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65279) | (value << 8));
                break;
            case 27:
                writeToBufferPool(14, (bufferPool[14] & 65533) | (value << 1));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 64511) | (value << 10));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65527) | (value << 3));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 61439) | (value << 12));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65503) | (value << 5));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 49151) | (value << 14));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65407) | (value << 7));
                break;
            case 34:
                writeToBufferPool(17, (bufferPool[17] & 65534) | (value));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65023) | (value << 9));
                break;
            case 36:
                writeToBufferPool(18, (bufferPool[18] & 65531) | (value << 2));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 63487) | (value << 11));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65519) | (value << 4));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 57343) | (value << 13));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65471) | (value << 6));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 32767) | (value << 15));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65279) | (value << 8));
                break;
            case 43:
                writeToBufferPool(21, (bufferPool[21] & 65533) | (value << 1));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 64511) | (value << 10));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65527) | (value << 3));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 61439) | (value << 12));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65503) | (value << 5));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 49151) | (value << 14));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65407) | (value << 7));
                break;
            case 50:
                writeToBufferPool(24, (bufferPool[24] & 65534) | (value));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65023) | (value << 9));
                break;
            case 52:
                writeToBufferPool(25, (bufferPool[25] & 65531) | (value << 2));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 63487) | (value << 11));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65519) | (value << 4));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 57343) | (value << 13));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65471) | (value << 6));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 32767) | (value << 15));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65279) | (value << 8));
                break;
            case 59:
                writeToBufferPool(28, (bufferPool[28] & 65533) | (value << 1));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 64511) | (value << 10));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65527) | (value << 3));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 61439) | (value << 12));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65503) | (value << 5));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 49151) | (value << 14));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65407) | (value << 7));
                break;
            case 66:
                writeToBufferPool(31, (bufferPool[31] & 65534) | (value));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65023) | (value << 9));
                break;
            case 68:
                writeToBufferPool(32, (bufferPool[32] & 65531) | (value << 2));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 63487) | (value << 11));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65519) | (value << 4));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 57343) | (value << 13));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65471) | (value << 6));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 32767) | (value << 15));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65279) | (value << 8));
                break;
            case 75:
                writeToBufferPool(35, (bufferPool[35] & 65533) | (value << 1));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 64511) | (value << 10));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65527) | (value << 3));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 61439) | (value << 12));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65503) | (value << 5));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 49151) | (value << 14));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65407) | (value << 7));
                break;
            case 82:
                writeToBufferPool(38, (bufferPool[38] & 65534) | (value));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65023) | (value << 9));
                break;
            case 84:
                writeToBufferPool(39, (bufferPool[39] & 65531) | (value << 2));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 63487) | (value << 11));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65519) | (value << 4));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 57343) | (value << 13));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65471) | (value << 6));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 32767) | (value << 15));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65279) | (value << 8));
                break;
            case 91:
                writeToBufferPool(42, (bufferPool[42] & 65533) | (value << 1));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 64511) | (value << 10));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65527) | (value << 3));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 61439) | (value << 12));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65503) | (value << 5));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 49151) | (value << 14));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65407) | (value << 7));
                break;
            case 98:
                writeToBufferPool(45, (bufferPool[45] & 65534) | (value));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65023) | (value << 9));
                break;
        }
    }

    public static int readSectorManaFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 8192) >>> 13;
            case 1:
                return (rc.readSharedArray(3) & 64) >>> 6;
            case 2:
                return (rc.readSharedArray(4) & 32768) >>> 15;
            case 3:
                return (rc.readSharedArray(4) & 256) >>> 8;
            case 4:
                return (rc.readSharedArray(4) & 2) >>> 1;
            case 5:
                return (rc.readSharedArray(5) & 1024) >>> 10;
            case 6:
                return (rc.readSharedArray(5) & 8) >>> 3;
            case 7:
                return (rc.readSharedArray(6) & 4096) >>> 12;
            case 8:
                return (rc.readSharedArray(6) & 32) >>> 5;
            case 9:
                return (rc.readSharedArray(7) & 16384) >>> 14;
            case 10:
                return (rc.readSharedArray(7) & 128) >>> 7;
            case 11:
                return (rc.readSharedArray(7) & 1);
            case 12:
                return (rc.readSharedArray(8) & 512) >>> 9;
            case 13:
                return (rc.readSharedArray(8) & 4) >>> 2;
            case 14:
                return (rc.readSharedArray(9) & 2048) >>> 11;
            case 15:
                return (rc.readSharedArray(9) & 16) >>> 4;
            case 16:
                return (rc.readSharedArray(10) & 8192) >>> 13;
            case 17:
                return (rc.readSharedArray(10) & 64) >>> 6;
            case 18:
                return (rc.readSharedArray(11) & 32768) >>> 15;
            case 19:
                return (rc.readSharedArray(11) & 256) >>> 8;
            case 20:
                return (rc.readSharedArray(11) & 2) >>> 1;
            case 21:
                return (rc.readSharedArray(12) & 1024) >>> 10;
            case 22:
                return (rc.readSharedArray(12) & 8) >>> 3;
            case 23:
                return (rc.readSharedArray(13) & 4096) >>> 12;
            case 24:
                return (rc.readSharedArray(13) & 32) >>> 5;
            case 25:
                return (rc.readSharedArray(14) & 16384) >>> 14;
            case 26:
                return (rc.readSharedArray(14) & 128) >>> 7;
            case 27:
                return (rc.readSharedArray(14) & 1);
            case 28:
                return (rc.readSharedArray(15) & 512) >>> 9;
            case 29:
                return (rc.readSharedArray(15) & 4) >>> 2;
            case 30:
                return (rc.readSharedArray(16) & 2048) >>> 11;
            case 31:
                return (rc.readSharedArray(16) & 16) >>> 4;
            case 32:
                return (rc.readSharedArray(17) & 8192) >>> 13;
            case 33:
                return (rc.readSharedArray(17) & 64) >>> 6;
            case 34:
                return (rc.readSharedArray(18) & 32768) >>> 15;
            case 35:
                return (rc.readSharedArray(18) & 256) >>> 8;
            case 36:
                return (rc.readSharedArray(18) & 2) >>> 1;
            case 37:
                return (rc.readSharedArray(19) & 1024) >>> 10;
            case 38:
                return (rc.readSharedArray(19) & 8) >>> 3;
            case 39:
                return (rc.readSharedArray(20) & 4096) >>> 12;
            case 40:
                return (rc.readSharedArray(20) & 32) >>> 5;
            case 41:
                return (rc.readSharedArray(21) & 16384) >>> 14;
            case 42:
                return (rc.readSharedArray(21) & 128) >>> 7;
            case 43:
                return (rc.readSharedArray(21) & 1);
            case 44:
                return (rc.readSharedArray(22) & 512) >>> 9;
            case 45:
                return (rc.readSharedArray(22) & 4) >>> 2;
            case 46:
                return (rc.readSharedArray(23) & 2048) >>> 11;
            case 47:
                return (rc.readSharedArray(23) & 16) >>> 4;
            case 48:
                return (rc.readSharedArray(24) & 8192) >>> 13;
            case 49:
                return (rc.readSharedArray(24) & 64) >>> 6;
            case 50:
                return (rc.readSharedArray(25) & 32768) >>> 15;
            case 51:
                return (rc.readSharedArray(25) & 256) >>> 8;
            case 52:
                return (rc.readSharedArray(25) & 2) >>> 1;
            case 53:
                return (rc.readSharedArray(26) & 1024) >>> 10;
            case 54:
                return (rc.readSharedArray(26) & 8) >>> 3;
            case 55:
                return (rc.readSharedArray(27) & 4096) >>> 12;
            case 56:
                return (rc.readSharedArray(27) & 32) >>> 5;
            case 57:
                return (rc.readSharedArray(28) & 16384) >>> 14;
            case 58:
                return (rc.readSharedArray(28) & 128) >>> 7;
            case 59:
                return (rc.readSharedArray(28) & 1);
            case 60:
                return (rc.readSharedArray(29) & 512) >>> 9;
            case 61:
                return (rc.readSharedArray(29) & 4) >>> 2;
            case 62:
                return (rc.readSharedArray(30) & 2048) >>> 11;
            case 63:
                return (rc.readSharedArray(30) & 16) >>> 4;
            case 64:
                return (rc.readSharedArray(31) & 8192) >>> 13;
            case 65:
                return (rc.readSharedArray(31) & 64) >>> 6;
            case 66:
                return (rc.readSharedArray(32) & 32768) >>> 15;
            case 67:
                return (rc.readSharedArray(32) & 256) >>> 8;
            case 68:
                return (rc.readSharedArray(32) & 2) >>> 1;
            case 69:
                return (rc.readSharedArray(33) & 1024) >>> 10;
            case 70:
                return (rc.readSharedArray(33) & 8) >>> 3;
            case 71:
                return (rc.readSharedArray(34) & 4096) >>> 12;
            case 72:
                return (rc.readSharedArray(34) & 32) >>> 5;
            case 73:
                return (rc.readSharedArray(35) & 16384) >>> 14;
            case 74:
                return (rc.readSharedArray(35) & 128) >>> 7;
            case 75:
                return (rc.readSharedArray(35) & 1);
            case 76:
                return (rc.readSharedArray(36) & 512) >>> 9;
            case 77:
                return (rc.readSharedArray(36) & 4) >>> 2;
            case 78:
                return (rc.readSharedArray(37) & 2048) >>> 11;
            case 79:
                return (rc.readSharedArray(37) & 16) >>> 4;
            case 80:
                return (rc.readSharedArray(38) & 8192) >>> 13;
            case 81:
                return (rc.readSharedArray(38) & 64) >>> 6;
            case 82:
                return (rc.readSharedArray(39) & 32768) >>> 15;
            case 83:
                return (rc.readSharedArray(39) & 256) >>> 8;
            case 84:
                return (rc.readSharedArray(39) & 2) >>> 1;
            case 85:
                return (rc.readSharedArray(40) & 1024) >>> 10;
            case 86:
                return (rc.readSharedArray(40) & 8) >>> 3;
            case 87:
                return (rc.readSharedArray(41) & 4096) >>> 12;
            case 88:
                return (rc.readSharedArray(41) & 32) >>> 5;
            case 89:
                return (rc.readSharedArray(42) & 16384) >>> 14;
            case 90:
                return (rc.readSharedArray(42) & 128) >>> 7;
            case 91:
                return (rc.readSharedArray(42) & 1);
            case 92:
                return (rc.readSharedArray(43) & 512) >>> 9;
            case 93:
                return (rc.readSharedArray(43) & 4) >>> 2;
            case 94:
                return (rc.readSharedArray(44) & 2048) >>> 11;
            case 95:
                return (rc.readSharedArray(44) & 16) >>> 4;
            case 96:
                return (rc.readSharedArray(45) & 8192) >>> 13;
            case 97:
                return (rc.readSharedArray(45) & 64) >>> 6;
            case 98:
                return (rc.readSharedArray(46) & 32768) >>> 15;
            case 99:
                return (rc.readSharedArray(46) & 256) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 57343) | (value << 13));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65471) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 32767) | (value << 15));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65279) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65533) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 64511) | (value << 10));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65527) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 61439) | (value << 12));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65503) | (value << 5));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 49151) | (value << 14));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65407) | (value << 7));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65534) | (value));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65023) | (value << 9));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65531) | (value << 2));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 63487) | (value << 11));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65519) | (value << 4));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 57343) | (value << 13));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65471) | (value << 6));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 32767) | (value << 15));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65279) | (value << 8));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65533) | (value << 1));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 64511) | (value << 10));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65527) | (value << 3));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 61439) | (value << 12));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65503) | (value << 5));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 49151) | (value << 14));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65407) | (value << 7));
                break;
            case 27:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65534) | (value));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65023) | (value << 9));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65531) | (value << 2));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 63487) | (value << 11));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65519) | (value << 4));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 57343) | (value << 13));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65471) | (value << 6));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 32767) | (value << 15));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65279) | (value << 8));
                break;
            case 36:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65533) | (value << 1));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 64511) | (value << 10));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65527) | (value << 3));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 61439) | (value << 12));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65503) | (value << 5));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 49151) | (value << 14));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65407) | (value << 7));
                break;
            case 43:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65534) | (value));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65023) | (value << 9));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65531) | (value << 2));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 63487) | (value << 11));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65519) | (value << 4));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 57343) | (value << 13));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65471) | (value << 6));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 32767) | (value << 15));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65279) | (value << 8));
                break;
            case 52:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65533) | (value << 1));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 64511) | (value << 10));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65527) | (value << 3));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 61439) | (value << 12));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65503) | (value << 5));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 49151) | (value << 14));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65407) | (value << 7));
                break;
            case 59:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65534) | (value));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65023) | (value << 9));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65531) | (value << 2));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 63487) | (value << 11));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65519) | (value << 4));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 57343) | (value << 13));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65471) | (value << 6));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 32767) | (value << 15));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65279) | (value << 8));
                break;
            case 68:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65533) | (value << 1));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 64511) | (value << 10));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65527) | (value << 3));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 61439) | (value << 12));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65503) | (value << 5));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 49151) | (value << 14));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65407) | (value << 7));
                break;
            case 75:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65534) | (value));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65023) | (value << 9));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65531) | (value << 2));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 63487) | (value << 11));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65519) | (value << 4));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 57343) | (value << 13));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65471) | (value << 6));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 32767) | (value << 15));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65279) | (value << 8));
                break;
            case 84:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65533) | (value << 1));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 64511) | (value << 10));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65527) | (value << 3));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 61439) | (value << 12));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65503) | (value << 5));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 49151) | (value << 14));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65407) | (value << 7));
                break;
            case 91:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65534) | (value));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65023) | (value << 9));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65531) | (value << 2));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 63487) | (value << 11));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65519) | (value << 4));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 57343) | (value << 13));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65471) | (value << 6));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 32767) | (value << 15));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65279) | (value << 8));
                break;
        }
    }

    public static void writeBPSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 57343) | (value << 13));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65471) | (value << 6));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 32767) | (value << 15));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65279) | (value << 8));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65533) | (value << 1));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 64511) | (value << 10));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65527) | (value << 3));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 61439) | (value << 12));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65503) | (value << 5));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 49151) | (value << 14));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65407) | (value << 7));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65534) | (value));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65023) | (value << 9));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65531) | (value << 2));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 63487) | (value << 11));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65519) | (value << 4));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 57343) | (value << 13));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65471) | (value << 6));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 32767) | (value << 15));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65279) | (value << 8));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65533) | (value << 1));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 64511) | (value << 10));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65527) | (value << 3));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 61439) | (value << 12));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65503) | (value << 5));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 49151) | (value << 14));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65407) | (value << 7));
                break;
            case 27:
                writeToBufferPool(14, (bufferPool[14] & 65534) | (value));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65023) | (value << 9));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65531) | (value << 2));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 63487) | (value << 11));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65519) | (value << 4));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 57343) | (value << 13));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65471) | (value << 6));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 32767) | (value << 15));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65279) | (value << 8));
                break;
            case 36:
                writeToBufferPool(18, (bufferPool[18] & 65533) | (value << 1));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 64511) | (value << 10));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65527) | (value << 3));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 61439) | (value << 12));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65503) | (value << 5));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 49151) | (value << 14));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65407) | (value << 7));
                break;
            case 43:
                writeToBufferPool(21, (bufferPool[21] & 65534) | (value));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65023) | (value << 9));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65531) | (value << 2));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 63487) | (value << 11));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65519) | (value << 4));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 57343) | (value << 13));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65471) | (value << 6));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 32767) | (value << 15));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65279) | (value << 8));
                break;
            case 52:
                writeToBufferPool(25, (bufferPool[25] & 65533) | (value << 1));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 64511) | (value << 10));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65527) | (value << 3));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 61439) | (value << 12));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65503) | (value << 5));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 49151) | (value << 14));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65407) | (value << 7));
                break;
            case 59:
                writeToBufferPool(28, (bufferPool[28] & 65534) | (value));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65023) | (value << 9));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65531) | (value << 2));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 63487) | (value << 11));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65519) | (value << 4));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 57343) | (value << 13));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65471) | (value << 6));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 32767) | (value << 15));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65279) | (value << 8));
                break;
            case 68:
                writeToBufferPool(32, (bufferPool[32] & 65533) | (value << 1));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 64511) | (value << 10));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65527) | (value << 3));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 61439) | (value << 12));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65503) | (value << 5));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 49151) | (value << 14));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65407) | (value << 7));
                break;
            case 75:
                writeToBufferPool(35, (bufferPool[35] & 65534) | (value));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65023) | (value << 9));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65531) | (value << 2));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 63487) | (value << 11));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65519) | (value << 4));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 57343) | (value << 13));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65471) | (value << 6));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 32767) | (value << 15));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65279) | (value << 8));
                break;
            case 84:
                writeToBufferPool(39, (bufferPool[39] & 65533) | (value << 1));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 64511) | (value << 10));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65527) | (value << 3));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 61439) | (value << 12));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65503) | (value << 5));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 49151) | (value << 14));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65407) | (value << 7));
                break;
            case 91:
                writeToBufferPool(42, (bufferPool[42] & 65534) | (value));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65023) | (value << 9));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65531) | (value << 2));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 63487) | (value << 11));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65519) | (value << 4));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 57343) | (value << 13));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65471) | (value << 6));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 32767) | (value << 15));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65279) | (value << 8));
                break;
        }
    }

    public static int readSectorElixirFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 4096) >>> 12;
            case 1:
                return (rc.readSharedArray(3) & 32) >>> 5;
            case 2:
                return (rc.readSharedArray(4) & 16384) >>> 14;
            case 3:
                return (rc.readSharedArray(4) & 128) >>> 7;
            case 4:
                return (rc.readSharedArray(4) & 1);
            case 5:
                return (rc.readSharedArray(5) & 512) >>> 9;
            case 6:
                return (rc.readSharedArray(5) & 4) >>> 2;
            case 7:
                return (rc.readSharedArray(6) & 2048) >>> 11;
            case 8:
                return (rc.readSharedArray(6) & 16) >>> 4;
            case 9:
                return (rc.readSharedArray(7) & 8192) >>> 13;
            case 10:
                return (rc.readSharedArray(7) & 64) >>> 6;
            case 11:
                return (rc.readSharedArray(8) & 32768) >>> 15;
            case 12:
                return (rc.readSharedArray(8) & 256) >>> 8;
            case 13:
                return (rc.readSharedArray(8) & 2) >>> 1;
            case 14:
                return (rc.readSharedArray(9) & 1024) >>> 10;
            case 15:
                return (rc.readSharedArray(9) & 8) >>> 3;
            case 16:
                return (rc.readSharedArray(10) & 4096) >>> 12;
            case 17:
                return (rc.readSharedArray(10) & 32) >>> 5;
            case 18:
                return (rc.readSharedArray(11) & 16384) >>> 14;
            case 19:
                return (rc.readSharedArray(11) & 128) >>> 7;
            case 20:
                return (rc.readSharedArray(11) & 1);
            case 21:
                return (rc.readSharedArray(12) & 512) >>> 9;
            case 22:
                return (rc.readSharedArray(12) & 4) >>> 2;
            case 23:
                return (rc.readSharedArray(13) & 2048) >>> 11;
            case 24:
                return (rc.readSharedArray(13) & 16) >>> 4;
            case 25:
                return (rc.readSharedArray(14) & 8192) >>> 13;
            case 26:
                return (rc.readSharedArray(14) & 64) >>> 6;
            case 27:
                return (rc.readSharedArray(15) & 32768) >>> 15;
            case 28:
                return (rc.readSharedArray(15) & 256) >>> 8;
            case 29:
                return (rc.readSharedArray(15) & 2) >>> 1;
            case 30:
                return (rc.readSharedArray(16) & 1024) >>> 10;
            case 31:
                return (rc.readSharedArray(16) & 8) >>> 3;
            case 32:
                return (rc.readSharedArray(17) & 4096) >>> 12;
            case 33:
                return (rc.readSharedArray(17) & 32) >>> 5;
            case 34:
                return (rc.readSharedArray(18) & 16384) >>> 14;
            case 35:
                return (rc.readSharedArray(18) & 128) >>> 7;
            case 36:
                return (rc.readSharedArray(18) & 1);
            case 37:
                return (rc.readSharedArray(19) & 512) >>> 9;
            case 38:
                return (rc.readSharedArray(19) & 4) >>> 2;
            case 39:
                return (rc.readSharedArray(20) & 2048) >>> 11;
            case 40:
                return (rc.readSharedArray(20) & 16) >>> 4;
            case 41:
                return (rc.readSharedArray(21) & 8192) >>> 13;
            case 42:
                return (rc.readSharedArray(21) & 64) >>> 6;
            case 43:
                return (rc.readSharedArray(22) & 32768) >>> 15;
            case 44:
                return (rc.readSharedArray(22) & 256) >>> 8;
            case 45:
                return (rc.readSharedArray(22) & 2) >>> 1;
            case 46:
                return (rc.readSharedArray(23) & 1024) >>> 10;
            case 47:
                return (rc.readSharedArray(23) & 8) >>> 3;
            case 48:
                return (rc.readSharedArray(24) & 4096) >>> 12;
            case 49:
                return (rc.readSharedArray(24) & 32) >>> 5;
            case 50:
                return (rc.readSharedArray(25) & 16384) >>> 14;
            case 51:
                return (rc.readSharedArray(25) & 128) >>> 7;
            case 52:
                return (rc.readSharedArray(25) & 1);
            case 53:
                return (rc.readSharedArray(26) & 512) >>> 9;
            case 54:
                return (rc.readSharedArray(26) & 4) >>> 2;
            case 55:
                return (rc.readSharedArray(27) & 2048) >>> 11;
            case 56:
                return (rc.readSharedArray(27) & 16) >>> 4;
            case 57:
                return (rc.readSharedArray(28) & 8192) >>> 13;
            case 58:
                return (rc.readSharedArray(28) & 64) >>> 6;
            case 59:
                return (rc.readSharedArray(29) & 32768) >>> 15;
            case 60:
                return (rc.readSharedArray(29) & 256) >>> 8;
            case 61:
                return (rc.readSharedArray(29) & 2) >>> 1;
            case 62:
                return (rc.readSharedArray(30) & 1024) >>> 10;
            case 63:
                return (rc.readSharedArray(30) & 8) >>> 3;
            case 64:
                return (rc.readSharedArray(31) & 4096) >>> 12;
            case 65:
                return (rc.readSharedArray(31) & 32) >>> 5;
            case 66:
                return (rc.readSharedArray(32) & 16384) >>> 14;
            case 67:
                return (rc.readSharedArray(32) & 128) >>> 7;
            case 68:
                return (rc.readSharedArray(32) & 1);
            case 69:
                return (rc.readSharedArray(33) & 512) >>> 9;
            case 70:
                return (rc.readSharedArray(33) & 4) >>> 2;
            case 71:
                return (rc.readSharedArray(34) & 2048) >>> 11;
            case 72:
                return (rc.readSharedArray(34) & 16) >>> 4;
            case 73:
                return (rc.readSharedArray(35) & 8192) >>> 13;
            case 74:
                return (rc.readSharedArray(35) & 64) >>> 6;
            case 75:
                return (rc.readSharedArray(36) & 32768) >>> 15;
            case 76:
                return (rc.readSharedArray(36) & 256) >>> 8;
            case 77:
                return (rc.readSharedArray(36) & 2) >>> 1;
            case 78:
                return (rc.readSharedArray(37) & 1024) >>> 10;
            case 79:
                return (rc.readSharedArray(37) & 8) >>> 3;
            case 80:
                return (rc.readSharedArray(38) & 4096) >>> 12;
            case 81:
                return (rc.readSharedArray(38) & 32) >>> 5;
            case 82:
                return (rc.readSharedArray(39) & 16384) >>> 14;
            case 83:
                return (rc.readSharedArray(39) & 128) >>> 7;
            case 84:
                return (rc.readSharedArray(39) & 1);
            case 85:
                return (rc.readSharedArray(40) & 512) >>> 9;
            case 86:
                return (rc.readSharedArray(40) & 4) >>> 2;
            case 87:
                return (rc.readSharedArray(41) & 2048) >>> 11;
            case 88:
                return (rc.readSharedArray(41) & 16) >>> 4;
            case 89:
                return (rc.readSharedArray(42) & 8192) >>> 13;
            case 90:
                return (rc.readSharedArray(42) & 64) >>> 6;
            case 91:
                return (rc.readSharedArray(43) & 32768) >>> 15;
            case 92:
                return (rc.readSharedArray(43) & 256) >>> 8;
            case 93:
                return (rc.readSharedArray(43) & 2) >>> 1;
            case 94:
                return (rc.readSharedArray(44) & 1024) >>> 10;
            case 95:
                return (rc.readSharedArray(44) & 8) >>> 3;
            case 96:
                return (rc.readSharedArray(45) & 4096) >>> 12;
            case 97:
                return (rc.readSharedArray(45) & 32) >>> 5;
            case 98:
                return (rc.readSharedArray(46) & 16384) >>> 14;
            case 99:
                return (rc.readSharedArray(46) & 128) >>> 7;
            default:
                return -1;
        }
    }

    public static void writeSectorElixirFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 61439) | (value << 12));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65503) | (value << 5));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 49151) | (value << 14));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65407) | (value << 7));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65534) | (value));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65023) | (value << 9));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65531) | (value << 2));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 63487) | (value << 11));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65519) | (value << 4));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 57343) | (value << 13));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65471) | (value << 6));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 32767) | (value << 15));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65279) | (value << 8));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65533) | (value << 1));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 64511) | (value << 10));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65527) | (value << 3));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 61439) | (value << 12));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65503) | (value << 5));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 49151) | (value << 14));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65407) | (value << 7));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65534) | (value));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65023) | (value << 9));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65531) | (value << 2));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 63487) | (value << 11));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65519) | (value << 4));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 57343) | (value << 13));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65471) | (value << 6));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 32767) | (value << 15));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65279) | (value << 8));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65533) | (value << 1));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 64511) | (value << 10));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65527) | (value << 3));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 61439) | (value << 12));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65503) | (value << 5));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 49151) | (value << 14));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65407) | (value << 7));
                break;
            case 36:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65534) | (value));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65023) | (value << 9));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65531) | (value << 2));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63487) | (value << 11));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65519) | (value << 4));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 57343) | (value << 13));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65471) | (value << 6));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 32767) | (value << 15));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65279) | (value << 8));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65533) | (value << 1));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 64511) | (value << 10));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65527) | (value << 3));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 61439) | (value << 12));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65503) | (value << 5));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 49151) | (value << 14));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65407) | (value << 7));
                break;
            case 52:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65534) | (value));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65023) | (value << 9));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65531) | (value << 2));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 63487) | (value << 11));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65519) | (value << 4));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 57343) | (value << 13));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65471) | (value << 6));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 32767) | (value << 15));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65279) | (value << 8));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65533) | (value << 1));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 64511) | (value << 10));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65527) | (value << 3));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 61439) | (value << 12));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65503) | (value << 5));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 49151) | (value << 14));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65407) | (value << 7));
                break;
            case 68:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65534) | (value));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65023) | (value << 9));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65531) | (value << 2));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 63487) | (value << 11));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65519) | (value << 4));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 57343) | (value << 13));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65471) | (value << 6));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 32767) | (value << 15));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65279) | (value << 8));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65533) | (value << 1));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 64511) | (value << 10));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65527) | (value << 3));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 61439) | (value << 12));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65503) | (value << 5));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 49151) | (value << 14));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65407) | (value << 7));
                break;
            case 84:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65534) | (value));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65023) | (value << 9));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65531) | (value << 2));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63487) | (value << 11));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65519) | (value << 4));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 57343) | (value << 13));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65471) | (value << 6));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 32767) | (value << 15));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65279) | (value << 8));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65533) | (value << 1));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 64511) | (value << 10));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65527) | (value << 3));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 61439) | (value << 12));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65503) | (value << 5));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 49151) | (value << 14));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65407) | (value << 7));
                break;
        }
    }

    public static void writeBPSectorElixirFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 61439) | (value << 12));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65503) | (value << 5));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 49151) | (value << 14));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65407) | (value << 7));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65534) | (value));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65023) | (value << 9));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65531) | (value << 2));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 63487) | (value << 11));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65519) | (value << 4));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 57343) | (value << 13));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65471) | (value << 6));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 32767) | (value << 15));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65279) | (value << 8));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65533) | (value << 1));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 64511) | (value << 10));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65527) | (value << 3));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 61439) | (value << 12));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65503) | (value << 5));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 49151) | (value << 14));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65407) | (value << 7));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65534) | (value));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65023) | (value << 9));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65531) | (value << 2));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 63487) | (value << 11));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65519) | (value << 4));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 57343) | (value << 13));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65471) | (value << 6));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 32767) | (value << 15));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65279) | (value << 8));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65533) | (value << 1));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 64511) | (value << 10));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65527) | (value << 3));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 61439) | (value << 12));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65503) | (value << 5));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 49151) | (value << 14));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65407) | (value << 7));
                break;
            case 36:
                writeToBufferPool(18, (bufferPool[18] & 65534) | (value));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65023) | (value << 9));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65531) | (value << 2));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 63487) | (value << 11));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65519) | (value << 4));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 57343) | (value << 13));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65471) | (value << 6));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 32767) | (value << 15));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65279) | (value << 8));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65533) | (value << 1));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 64511) | (value << 10));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65527) | (value << 3));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 61439) | (value << 12));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65503) | (value << 5));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 49151) | (value << 14));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65407) | (value << 7));
                break;
            case 52:
                writeToBufferPool(25, (bufferPool[25] & 65534) | (value));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65023) | (value << 9));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65531) | (value << 2));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 63487) | (value << 11));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65519) | (value << 4));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 57343) | (value << 13));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65471) | (value << 6));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 32767) | (value << 15));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65279) | (value << 8));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65533) | (value << 1));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 64511) | (value << 10));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65527) | (value << 3));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 61439) | (value << 12));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65503) | (value << 5));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 49151) | (value << 14));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65407) | (value << 7));
                break;
            case 68:
                writeToBufferPool(32, (bufferPool[32] & 65534) | (value));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65023) | (value << 9));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65531) | (value << 2));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 63487) | (value << 11));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65519) | (value << 4));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 57343) | (value << 13));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65471) | (value << 6));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 32767) | (value << 15));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65279) | (value << 8));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65533) | (value << 1));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 64511) | (value << 10));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65527) | (value << 3));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 61439) | (value << 12));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65503) | (value << 5));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 49151) | (value << 14));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65407) | (value << 7));
                break;
            case 84:
                writeToBufferPool(39, (bufferPool[39] & 65534) | (value));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65023) | (value << 9));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65531) | (value << 2));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 63487) | (value << 11));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65519) | (value << 4));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 57343) | (value << 13));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65471) | (value << 6));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 32767) | (value << 15));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65279) | (value << 8));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65533) | (value << 1));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 64511) | (value << 10));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65527) | (value << 3));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 61439) | (value << 12));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65503) | (value << 5));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 49151) | (value << 14));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65407) | (value << 7));
                break;
        }
    }

    public static int readSectorControlStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 3584) >>> 9;
            case 1:
                return (rc.readSharedArray(3) & 28) >>> 2;
            case 2:
                return (rc.readSharedArray(4) & 14336) >>> 11;
            case 3:
                return (rc.readSharedArray(4) & 112) >>> 4;
            case 4:
                return (rc.readSharedArray(5) & 57344) >>> 13;
            case 5:
                return (rc.readSharedArray(5) & 448) >>> 6;
            case 6:
                return ((rc.readSharedArray(5) & 3) << 1) + ((rc.readSharedArray(6) & 32768) >>> 15);
            case 7:
                return (rc.readSharedArray(6) & 1792) >>> 8;
            case 8:
                return (rc.readSharedArray(6) & 14) >>> 1;
            case 9:
                return (rc.readSharedArray(7) & 7168) >>> 10;
            case 10:
                return (rc.readSharedArray(7) & 56) >>> 3;
            case 11:
                return (rc.readSharedArray(8) & 28672) >>> 12;
            case 12:
                return (rc.readSharedArray(8) & 224) >>> 5;
            case 13:
                return ((rc.readSharedArray(8) & 1) << 2) + ((rc.readSharedArray(9) & 49152) >>> 14);
            case 14:
                return (rc.readSharedArray(9) & 896) >>> 7;
            case 15:
                return (rc.readSharedArray(9) & 7);
            case 16:
                return (rc.readSharedArray(10) & 3584) >>> 9;
            case 17:
                return (rc.readSharedArray(10) & 28) >>> 2;
            case 18:
                return (rc.readSharedArray(11) & 14336) >>> 11;
            case 19:
                return (rc.readSharedArray(11) & 112) >>> 4;
            case 20:
                return (rc.readSharedArray(12) & 57344) >>> 13;
            case 21:
                return (rc.readSharedArray(12) & 448) >>> 6;
            case 22:
                return ((rc.readSharedArray(12) & 3) << 1) + ((rc.readSharedArray(13) & 32768) >>> 15);
            case 23:
                return (rc.readSharedArray(13) & 1792) >>> 8;
            case 24:
                return (rc.readSharedArray(13) & 14) >>> 1;
            case 25:
                return (rc.readSharedArray(14) & 7168) >>> 10;
            case 26:
                return (rc.readSharedArray(14) & 56) >>> 3;
            case 27:
                return (rc.readSharedArray(15) & 28672) >>> 12;
            case 28:
                return (rc.readSharedArray(15) & 224) >>> 5;
            case 29:
                return ((rc.readSharedArray(15) & 1) << 2) + ((rc.readSharedArray(16) & 49152) >>> 14);
            case 30:
                return (rc.readSharedArray(16) & 896) >>> 7;
            case 31:
                return (rc.readSharedArray(16) & 7);
            case 32:
                return (rc.readSharedArray(17) & 3584) >>> 9;
            case 33:
                return (rc.readSharedArray(17) & 28) >>> 2;
            case 34:
                return (rc.readSharedArray(18) & 14336) >>> 11;
            case 35:
                return (rc.readSharedArray(18) & 112) >>> 4;
            case 36:
                return (rc.readSharedArray(19) & 57344) >>> 13;
            case 37:
                return (rc.readSharedArray(19) & 448) >>> 6;
            case 38:
                return ((rc.readSharedArray(19) & 3) << 1) + ((rc.readSharedArray(20) & 32768) >>> 15);
            case 39:
                return (rc.readSharedArray(20) & 1792) >>> 8;
            case 40:
                return (rc.readSharedArray(20) & 14) >>> 1;
            case 41:
                return (rc.readSharedArray(21) & 7168) >>> 10;
            case 42:
                return (rc.readSharedArray(21) & 56) >>> 3;
            case 43:
                return (rc.readSharedArray(22) & 28672) >>> 12;
            case 44:
                return (rc.readSharedArray(22) & 224) >>> 5;
            case 45:
                return ((rc.readSharedArray(22) & 1) << 2) + ((rc.readSharedArray(23) & 49152) >>> 14);
            case 46:
                return (rc.readSharedArray(23) & 896) >>> 7;
            case 47:
                return (rc.readSharedArray(23) & 7);
            case 48:
                return (rc.readSharedArray(24) & 3584) >>> 9;
            case 49:
                return (rc.readSharedArray(24) & 28) >>> 2;
            case 50:
                return (rc.readSharedArray(25) & 14336) >>> 11;
            case 51:
                return (rc.readSharedArray(25) & 112) >>> 4;
            case 52:
                return (rc.readSharedArray(26) & 57344) >>> 13;
            case 53:
                return (rc.readSharedArray(26) & 448) >>> 6;
            case 54:
                return ((rc.readSharedArray(26) & 3) << 1) + ((rc.readSharedArray(27) & 32768) >>> 15);
            case 55:
                return (rc.readSharedArray(27) & 1792) >>> 8;
            case 56:
                return (rc.readSharedArray(27) & 14) >>> 1;
            case 57:
                return (rc.readSharedArray(28) & 7168) >>> 10;
            case 58:
                return (rc.readSharedArray(28) & 56) >>> 3;
            case 59:
                return (rc.readSharedArray(29) & 28672) >>> 12;
            case 60:
                return (rc.readSharedArray(29) & 224) >>> 5;
            case 61:
                return ((rc.readSharedArray(29) & 1) << 2) + ((rc.readSharedArray(30) & 49152) >>> 14);
            case 62:
                return (rc.readSharedArray(30) & 896) >>> 7;
            case 63:
                return (rc.readSharedArray(30) & 7);
            case 64:
                return (rc.readSharedArray(31) & 3584) >>> 9;
            case 65:
                return (rc.readSharedArray(31) & 28) >>> 2;
            case 66:
                return (rc.readSharedArray(32) & 14336) >>> 11;
            case 67:
                return (rc.readSharedArray(32) & 112) >>> 4;
            case 68:
                return (rc.readSharedArray(33) & 57344) >>> 13;
            case 69:
                return (rc.readSharedArray(33) & 448) >>> 6;
            case 70:
                return ((rc.readSharedArray(33) & 3) << 1) + ((rc.readSharedArray(34) & 32768) >>> 15);
            case 71:
                return (rc.readSharedArray(34) & 1792) >>> 8;
            case 72:
                return (rc.readSharedArray(34) & 14) >>> 1;
            case 73:
                return (rc.readSharedArray(35) & 7168) >>> 10;
            case 74:
                return (rc.readSharedArray(35) & 56) >>> 3;
            case 75:
                return (rc.readSharedArray(36) & 28672) >>> 12;
            case 76:
                return (rc.readSharedArray(36) & 224) >>> 5;
            case 77:
                return ((rc.readSharedArray(36) & 1) << 2) + ((rc.readSharedArray(37) & 49152) >>> 14);
            case 78:
                return (rc.readSharedArray(37) & 896) >>> 7;
            case 79:
                return (rc.readSharedArray(37) & 7);
            case 80:
                return (rc.readSharedArray(38) & 3584) >>> 9;
            case 81:
                return (rc.readSharedArray(38) & 28) >>> 2;
            case 82:
                return (rc.readSharedArray(39) & 14336) >>> 11;
            case 83:
                return (rc.readSharedArray(39) & 112) >>> 4;
            case 84:
                return (rc.readSharedArray(40) & 57344) >>> 13;
            case 85:
                return (rc.readSharedArray(40) & 448) >>> 6;
            case 86:
                return ((rc.readSharedArray(40) & 3) << 1) + ((rc.readSharedArray(41) & 32768) >>> 15);
            case 87:
                return (rc.readSharedArray(41) & 1792) >>> 8;
            case 88:
                return (rc.readSharedArray(41) & 14) >>> 1;
            case 89:
                return (rc.readSharedArray(42) & 7168) >>> 10;
            case 90:
                return (rc.readSharedArray(42) & 56) >>> 3;
            case 91:
                return (rc.readSharedArray(43) & 28672) >>> 12;
            case 92:
                return (rc.readSharedArray(43) & 224) >>> 5;
            case 93:
                return ((rc.readSharedArray(43) & 1) << 2) + ((rc.readSharedArray(44) & 49152) >>> 14);
            case 94:
                return (rc.readSharedArray(44) & 896) >>> 7;
            case 95:
                return (rc.readSharedArray(44) & 7);
            case 96:
                return (rc.readSharedArray(45) & 3584) >>> 9;
            case 97:
                return (rc.readSharedArray(45) & 28) >>> 2;
            case 98:
                return (rc.readSharedArray(46) & 14336) >>> 11;
            case 99:
                return (rc.readSharedArray(46) & 112) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 61951) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65507) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 51199) | (value << 11));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65423) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 8191) | (value << 13));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65087) | (value << 6));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 32767) | ((value & 1) << 15));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 63743) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65521) | (value << 1));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 58367) | (value << 10));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65479) | (value << 3));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 36863) | (value << 12));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65311) | (value << 5));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 16383) | ((value & 3) << 14));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 64639) | (value << 7));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65528) | (value));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 61951) | (value << 9));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65507) | (value << 2));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 51199) | (value << 11));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65423) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 8191) | (value << 13));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65087) | (value << 6));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 32767) | ((value & 1) << 15));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 63743) | (value << 8));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65521) | (value << 1));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 58367) | (value << 10));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65479) | (value << 3));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 36863) | (value << 12));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65311) | (value << 5));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 16383) | ((value & 3) << 14));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 64639) | (value << 7));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65528) | (value));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 61951) | (value << 9));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65507) | (value << 2));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 51199) | (value << 11));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65423) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 8191) | (value << 13));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65087) | (value << 6));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 32767) | ((value & 1) << 15));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63743) | (value << 8));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65521) | (value << 1));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 58367) | (value << 10));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65479) | (value << 3));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 36863) | (value << 12));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65311) | (value << 5));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 16383) | ((value & 3) << 14));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 64639) | (value << 7));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65528) | (value));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 61951) | (value << 9));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65507) | (value << 2));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 51199) | (value << 11));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65423) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 8191) | (value << 13));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65087) | (value << 6));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 32767) | ((value & 1) << 15));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 63743) | (value << 8));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65521) | (value << 1));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 58367) | (value << 10));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65479) | (value << 3));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 36863) | (value << 12));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65311) | (value << 5));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 16383) | ((value & 3) << 14));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 64639) | (value << 7));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65528) | (value));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 61951) | (value << 9));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65507) | (value << 2));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 51199) | (value << 11));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65423) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 8191) | (value << 13));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65087) | (value << 6));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 32767) | ((value & 1) << 15));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 63743) | (value << 8));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65521) | (value << 1));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 58367) | (value << 10));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65479) | (value << 3));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 36863) | (value << 12));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65311) | (value << 5));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 16383) | ((value & 3) << 14));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 64639) | (value << 7));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65528) | (value));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 61951) | (value << 9));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65507) | (value << 2));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 51199) | (value << 11));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65423) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 8191) | (value << 13));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65087) | (value << 6));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 32767) | ((value & 1) << 15));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63743) | (value << 8));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65521) | (value << 1));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 58367) | (value << 10));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65479) | (value << 3));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 36863) | (value << 12));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65311) | (value << 5));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 16383) | ((value & 3) << 14));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 64639) | (value << 7));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65528) | (value));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 61951) | (value << 9));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65507) | (value << 2));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 51199) | (value << 11));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65423) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 61951) | (value << 9));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65507) | (value << 2));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 51199) | (value << 11));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65423) | (value << 4));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 8191) | (value << 13));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65087) | (value << 6));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(6, (bufferPool[6] & 32767) | ((value & 1) << 15));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 63743) | (value << 8));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65521) | (value << 1));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 58367) | (value << 10));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65479) | (value << 3));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 36863) | (value << 12));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65311) | (value << 5));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(9, (bufferPool[9] & 16383) | ((value & 3) << 14));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 64639) | (value << 7));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65528) | (value));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 61951) | (value << 9));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65507) | (value << 2));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 51199) | (value << 11));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65423) | (value << 4));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 8191) | (value << 13));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65087) | (value << 6));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(13, (bufferPool[13] & 32767) | ((value & 1) << 15));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 63743) | (value << 8));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65521) | (value << 1));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 58367) | (value << 10));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65479) | (value << 3));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 36863) | (value << 12));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65311) | (value << 5));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(16, (bufferPool[16] & 16383) | ((value & 3) << 14));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 64639) | (value << 7));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65528) | (value));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 61951) | (value << 9));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65507) | (value << 2));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 51199) | (value << 11));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65423) | (value << 4));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 8191) | (value << 13));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65087) | (value << 6));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(20, (bufferPool[20] & 32767) | ((value & 1) << 15));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 63743) | (value << 8));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65521) | (value << 1));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 58367) | (value << 10));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65479) | (value << 3));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 36863) | (value << 12));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65311) | (value << 5));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(23, (bufferPool[23] & 16383) | ((value & 3) << 14));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 64639) | (value << 7));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65528) | (value));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 61951) | (value << 9));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65507) | (value << 2));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 51199) | (value << 11));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65423) | (value << 4));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 8191) | (value << 13));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65087) | (value << 6));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(27, (bufferPool[27] & 32767) | ((value & 1) << 15));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 63743) | (value << 8));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65521) | (value << 1));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 58367) | (value << 10));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65479) | (value << 3));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 36863) | (value << 12));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65311) | (value << 5));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(30, (bufferPool[30] & 16383) | ((value & 3) << 14));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 64639) | (value << 7));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65528) | (value));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 61951) | (value << 9));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65507) | (value << 2));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 51199) | (value << 11));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65423) | (value << 4));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 8191) | (value << 13));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65087) | (value << 6));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(34, (bufferPool[34] & 32767) | ((value & 1) << 15));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 63743) | (value << 8));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65521) | (value << 1));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 58367) | (value << 10));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65479) | (value << 3));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 36863) | (value << 12));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65311) | (value << 5));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(37, (bufferPool[37] & 16383) | ((value & 3) << 14));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 64639) | (value << 7));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65528) | (value));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 61951) | (value << 9));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65507) | (value << 2));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 51199) | (value << 11));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65423) | (value << 4));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 8191) | (value << 13));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65087) | (value << 6));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(41, (bufferPool[41] & 32767) | ((value & 1) << 15));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 63743) | (value << 8));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65521) | (value << 1));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 58367) | (value << 10));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65479) | (value << 3));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 36863) | (value << 12));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65311) | (value << 5));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(44, (bufferPool[44] & 16383) | ((value & 3) << 14));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 64639) | (value << 7));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65528) | (value));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 61951) | (value << 9));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65507) | (value << 2));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 51199) | (value << 11));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65423) | (value << 4));
                break;
        }
    }

    public static int readSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 65024) >>> 9;
            case 1:
                return (rc.readSharedArray(3) & 508) >>> 2;
            case 2:
                return ((rc.readSharedArray(3) & 3) << 5) + ((rc.readSharedArray(4) & 63488) >>> 11);
            case 3:
                return (rc.readSharedArray(4) & 2032) >>> 4;
            case 4:
                return ((rc.readSharedArray(4) & 15) << 3) + ((rc.readSharedArray(5) & 57344) >>> 13);
            case 5:
                return (rc.readSharedArray(5) & 8128) >>> 6;
            case 6:
                return ((rc.readSharedArray(5) & 63) << 1) + ((rc.readSharedArray(6) & 32768) >>> 15);
            case 7:
                return (rc.readSharedArray(6) & 32512) >>> 8;
            case 8:
                return (rc.readSharedArray(6) & 254) >>> 1;
            case 9:
                return ((rc.readSharedArray(6) & 1) << 6) + ((rc.readSharedArray(7) & 64512) >>> 10);
            case 10:
                return (rc.readSharedArray(7) & 1016) >>> 3;
            case 11:
                return ((rc.readSharedArray(7) & 7) << 4) + ((rc.readSharedArray(8) & 61440) >>> 12);
            case 12:
                return (rc.readSharedArray(8) & 4064) >>> 5;
            case 13:
                return ((rc.readSharedArray(8) & 31) << 2) + ((rc.readSharedArray(9) & 49152) >>> 14);
            case 14:
                return (rc.readSharedArray(9) & 16256) >>> 7;
            case 15:
                return (rc.readSharedArray(9) & 127);
            case 16:
                return (rc.readSharedArray(10) & 65024) >>> 9;
            case 17:
                return (rc.readSharedArray(10) & 508) >>> 2;
            case 18:
                return ((rc.readSharedArray(10) & 3) << 5) + ((rc.readSharedArray(11) & 63488) >>> 11);
            case 19:
                return (rc.readSharedArray(11) & 2032) >>> 4;
            case 20:
                return ((rc.readSharedArray(11) & 15) << 3) + ((rc.readSharedArray(12) & 57344) >>> 13);
            case 21:
                return (rc.readSharedArray(12) & 8128) >>> 6;
            case 22:
                return ((rc.readSharedArray(12) & 63) << 1) + ((rc.readSharedArray(13) & 32768) >>> 15);
            case 23:
                return (rc.readSharedArray(13) & 32512) >>> 8;
            case 24:
                return (rc.readSharedArray(13) & 254) >>> 1;
            case 25:
                return ((rc.readSharedArray(13) & 1) << 6) + ((rc.readSharedArray(14) & 64512) >>> 10);
            case 26:
                return (rc.readSharedArray(14) & 1016) >>> 3;
            case 27:
                return ((rc.readSharedArray(14) & 7) << 4) + ((rc.readSharedArray(15) & 61440) >>> 12);
            case 28:
                return (rc.readSharedArray(15) & 4064) >>> 5;
            case 29:
                return ((rc.readSharedArray(15) & 31) << 2) + ((rc.readSharedArray(16) & 49152) >>> 14);
            case 30:
                return (rc.readSharedArray(16) & 16256) >>> 7;
            case 31:
                return (rc.readSharedArray(16) & 127);
            case 32:
                return (rc.readSharedArray(17) & 65024) >>> 9;
            case 33:
                return (rc.readSharedArray(17) & 508) >>> 2;
            case 34:
                return ((rc.readSharedArray(17) & 3) << 5) + ((rc.readSharedArray(18) & 63488) >>> 11);
            case 35:
                return (rc.readSharedArray(18) & 2032) >>> 4;
            case 36:
                return ((rc.readSharedArray(18) & 15) << 3) + ((rc.readSharedArray(19) & 57344) >>> 13);
            case 37:
                return (rc.readSharedArray(19) & 8128) >>> 6;
            case 38:
                return ((rc.readSharedArray(19) & 63) << 1) + ((rc.readSharedArray(20) & 32768) >>> 15);
            case 39:
                return (rc.readSharedArray(20) & 32512) >>> 8;
            case 40:
                return (rc.readSharedArray(20) & 254) >>> 1;
            case 41:
                return ((rc.readSharedArray(20) & 1) << 6) + ((rc.readSharedArray(21) & 64512) >>> 10);
            case 42:
                return (rc.readSharedArray(21) & 1016) >>> 3;
            case 43:
                return ((rc.readSharedArray(21) & 7) << 4) + ((rc.readSharedArray(22) & 61440) >>> 12);
            case 44:
                return (rc.readSharedArray(22) & 4064) >>> 5;
            case 45:
                return ((rc.readSharedArray(22) & 31) << 2) + ((rc.readSharedArray(23) & 49152) >>> 14);
            case 46:
                return (rc.readSharedArray(23) & 16256) >>> 7;
            case 47:
                return (rc.readSharedArray(23) & 127);
            case 48:
                return (rc.readSharedArray(24) & 65024) >>> 9;
            case 49:
                return (rc.readSharedArray(24) & 508) >>> 2;
            case 50:
                return ((rc.readSharedArray(24) & 3) << 5) + ((rc.readSharedArray(25) & 63488) >>> 11);
            case 51:
                return (rc.readSharedArray(25) & 2032) >>> 4;
            case 52:
                return ((rc.readSharedArray(25) & 15) << 3) + ((rc.readSharedArray(26) & 57344) >>> 13);
            case 53:
                return (rc.readSharedArray(26) & 8128) >>> 6;
            case 54:
                return ((rc.readSharedArray(26) & 63) << 1) + ((rc.readSharedArray(27) & 32768) >>> 15);
            case 55:
                return (rc.readSharedArray(27) & 32512) >>> 8;
            case 56:
                return (rc.readSharedArray(27) & 254) >>> 1;
            case 57:
                return ((rc.readSharedArray(27) & 1) << 6) + ((rc.readSharedArray(28) & 64512) >>> 10);
            case 58:
                return (rc.readSharedArray(28) & 1016) >>> 3;
            case 59:
                return ((rc.readSharedArray(28) & 7) << 4) + ((rc.readSharedArray(29) & 61440) >>> 12);
            case 60:
                return (rc.readSharedArray(29) & 4064) >>> 5;
            case 61:
                return ((rc.readSharedArray(29) & 31) << 2) + ((rc.readSharedArray(30) & 49152) >>> 14);
            case 62:
                return (rc.readSharedArray(30) & 16256) >>> 7;
            case 63:
                return (rc.readSharedArray(30) & 127);
            case 64:
                return (rc.readSharedArray(31) & 65024) >>> 9;
            case 65:
                return (rc.readSharedArray(31) & 508) >>> 2;
            case 66:
                return ((rc.readSharedArray(31) & 3) << 5) + ((rc.readSharedArray(32) & 63488) >>> 11);
            case 67:
                return (rc.readSharedArray(32) & 2032) >>> 4;
            case 68:
                return ((rc.readSharedArray(32) & 15) << 3) + ((rc.readSharedArray(33) & 57344) >>> 13);
            case 69:
                return (rc.readSharedArray(33) & 8128) >>> 6;
            case 70:
                return ((rc.readSharedArray(33) & 63) << 1) + ((rc.readSharedArray(34) & 32768) >>> 15);
            case 71:
                return (rc.readSharedArray(34) & 32512) >>> 8;
            case 72:
                return (rc.readSharedArray(34) & 254) >>> 1;
            case 73:
                return ((rc.readSharedArray(34) & 1) << 6) + ((rc.readSharedArray(35) & 64512) >>> 10);
            case 74:
                return (rc.readSharedArray(35) & 1016) >>> 3;
            case 75:
                return ((rc.readSharedArray(35) & 7) << 4) + ((rc.readSharedArray(36) & 61440) >>> 12);
            case 76:
                return (rc.readSharedArray(36) & 4064) >>> 5;
            case 77:
                return ((rc.readSharedArray(36) & 31) << 2) + ((rc.readSharedArray(37) & 49152) >>> 14);
            case 78:
                return (rc.readSharedArray(37) & 16256) >>> 7;
            case 79:
                return (rc.readSharedArray(37) & 127);
            case 80:
                return (rc.readSharedArray(38) & 65024) >>> 9;
            case 81:
                return (rc.readSharedArray(38) & 508) >>> 2;
            case 82:
                return ((rc.readSharedArray(38) & 3) << 5) + ((rc.readSharedArray(39) & 63488) >>> 11);
            case 83:
                return (rc.readSharedArray(39) & 2032) >>> 4;
            case 84:
                return ((rc.readSharedArray(39) & 15) << 3) + ((rc.readSharedArray(40) & 57344) >>> 13);
            case 85:
                return (rc.readSharedArray(40) & 8128) >>> 6;
            case 86:
                return ((rc.readSharedArray(40) & 63) << 1) + ((rc.readSharedArray(41) & 32768) >>> 15);
            case 87:
                return (rc.readSharedArray(41) & 32512) >>> 8;
            case 88:
                return (rc.readSharedArray(41) & 254) >>> 1;
            case 89:
                return ((rc.readSharedArray(41) & 1) << 6) + ((rc.readSharedArray(42) & 64512) >>> 10);
            case 90:
                return (rc.readSharedArray(42) & 1016) >>> 3;
            case 91:
                return ((rc.readSharedArray(42) & 7) << 4) + ((rc.readSharedArray(43) & 61440) >>> 12);
            case 92:
                return (rc.readSharedArray(43) & 4064) >>> 5;
            case 93:
                return ((rc.readSharedArray(43) & 31) << 2) + ((rc.readSharedArray(44) & 49152) >>> 14);
            case 94:
                return (rc.readSharedArray(44) & 16256) >>> 7;
            case 95:
                return (rc.readSharedArray(44) & 127);
            case 96:
                return (rc.readSharedArray(45) & 65024) >>> 9;
            case 97:
                return (rc.readSharedArray(45) & 508) >>> 2;
            case 98:
                return ((rc.readSharedArray(45) & 3) << 5) + ((rc.readSharedArray(46) & 63488) >>> 11);
            case 99:
                return (rc.readSharedArray(46) & 2032) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 511) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65027) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 2047) | ((value & 31) << 11));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 63503) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 8191) | ((value & 7) << 13));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 57407) | (value << 6));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 32767) | ((value & 1) << 15));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 33023) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65281) | (value << 1));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 1023) | ((value & 63) << 10));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 64519) | (value << 3));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 4095) | ((value & 15) << 12));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 61471) | (value << 5));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 16383) | ((value & 3) << 14));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 49279) | (value << 7));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65408) | (value));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 511) | (value << 9));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65027) | (value << 2));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 2047) | ((value & 31) << 11));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 63503) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 8191) | ((value & 7) << 13));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 57407) | (value << 6));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 32767) | ((value & 1) << 15));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 33023) | (value << 8));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65281) | (value << 1));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 1023) | ((value & 63) << 10));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 64519) | (value << 3));
                break;
            case 27:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 4095) | ((value & 15) << 12));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 61471) | (value << 5));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 16383) | ((value & 3) << 14));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 49279) | (value << 7));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65408) | (value));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 511) | (value << 9));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65027) | (value << 2));
                break;
            case 34:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 2047) | ((value & 31) << 11));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 63503) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 8191) | ((value & 7) << 13));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 57407) | (value << 6));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 32767) | ((value & 1) << 15));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 33023) | (value << 8));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65281) | (value << 1));
                break;
            case 41:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 1023) | ((value & 63) << 10));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 64519) | (value << 3));
                break;
            case 43:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 4095) | ((value & 15) << 12));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 61471) | (value << 5));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 16383) | ((value & 3) << 14));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 49279) | (value << 7));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65408) | (value));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 511) | (value << 9));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65027) | (value << 2));
                break;
            case 50:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 2047) | ((value & 31) << 11));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 63503) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 8191) | ((value & 7) << 13));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 57407) | (value << 6));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 32767) | ((value & 1) << 15));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 33023) | (value << 8));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65281) | (value << 1));
                break;
            case 57:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 1023) | ((value & 63) << 10));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 64519) | (value << 3));
                break;
            case 59:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 4095) | ((value & 15) << 12));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 61471) | (value << 5));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 16383) | ((value & 3) << 14));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 49279) | (value << 7));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65408) | (value));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 511) | (value << 9));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65027) | (value << 2));
                break;
            case 66:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 2047) | ((value & 31) << 11));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 63503) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 8191) | ((value & 7) << 13));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 57407) | (value << 6));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 32767) | ((value & 1) << 15));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 33023) | (value << 8));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65281) | (value << 1));
                break;
            case 73:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 1023) | ((value & 63) << 10));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 64519) | (value << 3));
                break;
            case 75:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 4095) | ((value & 15) << 12));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 61471) | (value << 5));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 16383) | ((value & 3) << 14));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 49279) | (value << 7));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65408) | (value));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 511) | (value << 9));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65027) | (value << 2));
                break;
            case 82:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 2047) | ((value & 31) << 11));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 63503) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 8191) | ((value & 7) << 13));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 57407) | (value << 6));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 32767) | ((value & 1) << 15));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 33023) | (value << 8));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65281) | (value << 1));
                break;
            case 89:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 1023) | ((value & 63) << 10));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 64519) | (value << 3));
                break;
            case 91:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 4095) | ((value & 15) << 12));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 61471) | (value << 5));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 16383) | ((value & 3) << 14));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 49279) | (value << 7));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65408) | (value));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 511) | (value << 9));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65027) | (value << 2));
                break;
            case 98:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 2047) | ((value & 31) << 11));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63503) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 511) | (value << 9));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65027) | (value << 2));
                break;
            case 2:
                writeToBufferPool(3, (bufferPool[3] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(4, (bufferPool[4] & 2047) | ((value & 31) << 11));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 63503) | (value << 4));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(5, (bufferPool[5] & 8191) | ((value & 7) << 13));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 57407) | (value << 6));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(6, (bufferPool[6] & 32767) | ((value & 1) << 15));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 33023) | (value << 8));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65281) | (value << 1));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(7, (bufferPool[7] & 1023) | ((value & 63) << 10));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 64519) | (value << 3));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(8, (bufferPool[8] & 4095) | ((value & 15) << 12));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 61471) | (value << 5));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(9, (bufferPool[9] & 16383) | ((value & 3) << 14));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 49279) | (value << 7));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65408) | (value));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 511) | (value << 9));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65027) | (value << 2));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(11, (bufferPool[11] & 2047) | ((value & 31) << 11));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 63503) | (value << 4));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(12, (bufferPool[12] & 8191) | ((value & 7) << 13));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 57407) | (value << 6));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(13, (bufferPool[13] & 32767) | ((value & 1) << 15));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 33023) | (value << 8));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65281) | (value << 1));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(14, (bufferPool[14] & 1023) | ((value & 63) << 10));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 64519) | (value << 3));
                break;
            case 27:
                writeToBufferPool(14, (bufferPool[14] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(15, (bufferPool[15] & 4095) | ((value & 15) << 12));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 61471) | (value << 5));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(16, (bufferPool[16] & 16383) | ((value & 3) << 14));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 49279) | (value << 7));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65408) | (value));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 511) | (value << 9));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65027) | (value << 2));
                break;
            case 34:
                writeToBufferPool(17, (bufferPool[17] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(18, (bufferPool[18] & 2047) | ((value & 31) << 11));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 63503) | (value << 4));
                break;
            case 36:
                writeToBufferPool(18, (bufferPool[18] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(19, (bufferPool[19] & 8191) | ((value & 7) << 13));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 57407) | (value << 6));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(20, (bufferPool[20] & 32767) | ((value & 1) << 15));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 33023) | (value << 8));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65281) | (value << 1));
                break;
            case 41:
                writeToBufferPool(20, (bufferPool[20] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(21, (bufferPool[21] & 1023) | ((value & 63) << 10));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 64519) | (value << 3));
                break;
            case 43:
                writeToBufferPool(21, (bufferPool[21] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(22, (bufferPool[22] & 4095) | ((value & 15) << 12));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 61471) | (value << 5));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(23, (bufferPool[23] & 16383) | ((value & 3) << 14));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 49279) | (value << 7));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65408) | (value));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 511) | (value << 9));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65027) | (value << 2));
                break;
            case 50:
                writeToBufferPool(24, (bufferPool[24] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(25, (bufferPool[25] & 2047) | ((value & 31) << 11));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 63503) | (value << 4));
                break;
            case 52:
                writeToBufferPool(25, (bufferPool[25] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(26, (bufferPool[26] & 8191) | ((value & 7) << 13));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 57407) | (value << 6));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(27, (bufferPool[27] & 32767) | ((value & 1) << 15));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 33023) | (value << 8));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65281) | (value << 1));
                break;
            case 57:
                writeToBufferPool(27, (bufferPool[27] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(28, (bufferPool[28] & 1023) | ((value & 63) << 10));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 64519) | (value << 3));
                break;
            case 59:
                writeToBufferPool(28, (bufferPool[28] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(29, (bufferPool[29] & 4095) | ((value & 15) << 12));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 61471) | (value << 5));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(30, (bufferPool[30] & 16383) | ((value & 3) << 14));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 49279) | (value << 7));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65408) | (value));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 511) | (value << 9));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65027) | (value << 2));
                break;
            case 66:
                writeToBufferPool(31, (bufferPool[31] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(32, (bufferPool[32] & 2047) | ((value & 31) << 11));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 63503) | (value << 4));
                break;
            case 68:
                writeToBufferPool(32, (bufferPool[32] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(33, (bufferPool[33] & 8191) | ((value & 7) << 13));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 57407) | (value << 6));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(34, (bufferPool[34] & 32767) | ((value & 1) << 15));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 33023) | (value << 8));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65281) | (value << 1));
                break;
            case 73:
                writeToBufferPool(34, (bufferPool[34] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(35, (bufferPool[35] & 1023) | ((value & 63) << 10));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 64519) | (value << 3));
                break;
            case 75:
                writeToBufferPool(35, (bufferPool[35] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(36, (bufferPool[36] & 4095) | ((value & 15) << 12));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 61471) | (value << 5));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(37, (bufferPool[37] & 16383) | ((value & 3) << 14));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 49279) | (value << 7));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65408) | (value));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 511) | (value << 9));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65027) | (value << 2));
                break;
            case 82:
                writeToBufferPool(38, (bufferPool[38] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(39, (bufferPool[39] & 2047) | ((value & 31) << 11));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 63503) | (value << 4));
                break;
            case 84:
                writeToBufferPool(39, (bufferPool[39] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(40, (bufferPool[40] & 8191) | ((value & 7) << 13));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 57407) | (value << 6));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(41, (bufferPool[41] & 32767) | ((value & 1) << 15));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 33023) | (value << 8));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65281) | (value << 1));
                break;
            case 89:
                writeToBufferPool(41, (bufferPool[41] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(42, (bufferPool[42] & 1023) | ((value & 63) << 10));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 64519) | (value << 3));
                break;
            case 91:
                writeToBufferPool(42, (bufferPool[42] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(43, (bufferPool[43] & 4095) | ((value & 15) << 12));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 61471) | (value << 5));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(44, (bufferPool[44] & 16383) | ((value & 3) << 14));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 49279) | (value << 7));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65408) | (value));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 511) | (value << 9));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65027) | (value << 2));
                break;
            case 98:
                writeToBufferPool(45, (bufferPool[45] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(46, (bufferPool[46] & 2047) | ((value & 31) << 11));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 63503) | (value << 4));
                break;
        }
    }

    public static int readCombatSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(46) & 15) << 3) + ((rc.readSharedArray(47) & 57344) >>> 13);
            case 1:
                return (rc.readSharedArray(47) & 8128) >>> 6;
            case 2:
                return ((rc.readSharedArray(47) & 63) << 1) + ((rc.readSharedArray(48) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(48) & 32512) >>> 8;
            case 4:
                return (rc.readSharedArray(48) & 254) >>> 1;
            case 5:
                return ((rc.readSharedArray(48) & 1) << 6) + ((rc.readSharedArray(49) & 64512) >>> 10);
            case 6:
                return (rc.readSharedArray(49) & 1016) >>> 3;
            case 7:
                return ((rc.readSharedArray(49) & 7) << 4) + ((rc.readSharedArray(50) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 8191) | ((value & 7) << 13));
                break;
            case 1:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 57407) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 33023) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65281) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 1023) | ((value & 63) << 10));
                break;
            case 6:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 64519) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(46, (bufferPool[46] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(47, (bufferPool[47] & 8191) | ((value & 7) << 13));
                break;
            case 1:
                writeToBufferPool(47, (bufferPool[47] & 57407) | (value << 6));
                break;
            case 2:
                writeToBufferPool(47, (bufferPool[47] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(48, (bufferPool[48] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(48, (bufferPool[48] & 33023) | (value << 8));
                break;
            case 4:
                writeToBufferPool(48, (bufferPool[48] & 65281) | (value << 1));
                break;
            case 5:
                writeToBufferPool(48, (bufferPool[48] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(49, (bufferPool[49] & 1023) | ((value & 63) << 10));
                break;
            case 6:
                writeToBufferPool(49, (bufferPool[49] & 64519) | (value << 3));
                break;
            case 7:
                writeToBufferPool(49, (bufferPool[49] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(50, (bufferPool[50] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readCombatSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(46) & 15) << 3) + ((rc.readSharedArray(47) & 57344) >>> 13);
            case 1:
                return (rc.readSharedArray(47) & 8128) >>> 6;
            case 2:
                return ((rc.readSharedArray(47) & 63) << 1) + ((rc.readSharedArray(48) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(48) & 32512) >>> 8;
            case 4:
                return (rc.readSharedArray(48) & 254) >>> 1;
            case 5:
                return ((rc.readSharedArray(48) & 1) << 6) + ((rc.readSharedArray(49) & 64512) >>> 10);
            case 6:
                return (rc.readSharedArray(49) & 1016) >>> 3;
            case 7:
                return ((rc.readSharedArray(49) & 7) << 4) + ((rc.readSharedArray(50) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 8191) | ((value & 7) << 13));
                break;
            case 1:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 57407) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 33023) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65281) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 1023) | ((value & 63) << 10));
                break;
            case 6:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 64519) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(46, (bufferPool[46] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(47, (bufferPool[47] & 8191) | ((value & 7) << 13));
                break;
            case 1:
                writeToBufferPool(47, (bufferPool[47] & 57407) | (value << 6));
                break;
            case 2:
                writeToBufferPool(47, (bufferPool[47] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(48, (bufferPool[48] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(48, (bufferPool[48] & 33023) | (value << 8));
                break;
            case 4:
                writeToBufferPool(48, (bufferPool[48] & 65281) | (value << 1));
                break;
            case 5:
                writeToBufferPool(48, (bufferPool[48] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(49, (bufferPool[49] & 1023) | ((value & 63) << 10));
                break;
            case 6:
                writeToBufferPool(49, (bufferPool[49] & 64519) | (value << 3));
                break;
            case 7:
                writeToBufferPool(49, (bufferPool[49] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(50, (bufferPool[50] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readExploreSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(50) & 2048) >>> 11;
            case 1:
                return (rc.readSharedArray(50) & 8) >>> 3;
            case 2:
                return (rc.readSharedArray(51) & 2048) >>> 11;
            case 3:
                return (rc.readSharedArray(51) & 8) >>> 3;
            case 4:
                return (rc.readSharedArray(52) & 2048) >>> 11;
            case 5:
                return (rc.readSharedArray(52) & 8) >>> 3;
            case 6:
                return (rc.readSharedArray(53) & 2048) >>> 11;
            case 7:
                return (rc.readSharedArray(53) & 8) >>> 3;
            case 8:
                return (rc.readSharedArray(54) & 2048) >>> 11;
            case 9:
                return (rc.readSharedArray(54) & 8) >>> 3;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 63487) | (value << 11));
                break;
            case 1:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65527) | (value << 3));
                break;
            case 2:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 63487) | (value << 11));
                break;
            case 3:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65527) | (value << 3));
                break;
            case 4:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 63487) | (value << 11));
                break;
            case 5:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65527) | (value << 3));
                break;
            case 6:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 63487) | (value << 11));
                break;
            case 7:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65527) | (value << 3));
                break;
            case 8:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 63487) | (value << 11));
                break;
            case 9:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65527) | (value << 3));
                break;
        }
    }

    public static void writeBPExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(50, (bufferPool[50] & 63487) | (value << 11));
                break;
            case 1:
                writeToBufferPool(50, (bufferPool[50] & 65527) | (value << 3));
                break;
            case 2:
                writeToBufferPool(51, (bufferPool[51] & 63487) | (value << 11));
                break;
            case 3:
                writeToBufferPool(51, (bufferPool[51] & 65527) | (value << 3));
                break;
            case 4:
                writeToBufferPool(52, (bufferPool[52] & 63487) | (value << 11));
                break;
            case 5:
                writeToBufferPool(52, (bufferPool[52] & 65527) | (value << 3));
                break;
            case 6:
                writeToBufferPool(53, (bufferPool[53] & 63487) | (value << 11));
                break;
            case 7:
                writeToBufferPool(53, (bufferPool[53] & 65527) | (value << 3));
                break;
            case 8:
                writeToBufferPool(54, (bufferPool[54] & 63487) | (value << 11));
                break;
            case 9:
                writeToBufferPool(54, (bufferPool[54] & 65527) | (value << 3));
                break;
        }
    }

    public static int readExploreSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(50) & 2032) >>> 4;
            case 1:
                return ((rc.readSharedArray(50) & 7) << 4) + ((rc.readSharedArray(51) & 61440) >>> 12);
            case 2:
                return (rc.readSharedArray(51) & 2032) >>> 4;
            case 3:
                return ((rc.readSharedArray(51) & 7) << 4) + ((rc.readSharedArray(52) & 61440) >>> 12);
            case 4:
                return (rc.readSharedArray(52) & 2032) >>> 4;
            case 5:
                return ((rc.readSharedArray(52) & 7) << 4) + ((rc.readSharedArray(53) & 61440) >>> 12);
            case 6:
                return (rc.readSharedArray(53) & 2032) >>> 4;
            case 7:
                return ((rc.readSharedArray(53) & 7) << 4) + ((rc.readSharedArray(54) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(54) & 2032) >>> 4;
            case 9:
                return ((rc.readSharedArray(54) & 7) << 4) + ((rc.readSharedArray(55) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 63503) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 63503) | (value << 4));
                break;
            case 3:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 4095) | ((value & 15) << 12));
                break;
            case 4:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 63503) | (value << 4));
                break;
            case 5:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 4095) | ((value & 15) << 12));
                break;
            case 6:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 63503) | (value << 4));
                break;
            case 7:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 63503) | (value << 4));
                break;
            case 9:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(50, (bufferPool[50] & 63503) | (value << 4));
                break;
            case 1:
                writeToBufferPool(50, (bufferPool[50] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(51, (bufferPool[51] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(51, (bufferPool[51] & 63503) | (value << 4));
                break;
            case 3:
                writeToBufferPool(51, (bufferPool[51] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(52, (bufferPool[52] & 4095) | ((value & 15) << 12));
                break;
            case 4:
                writeToBufferPool(52, (bufferPool[52] & 63503) | (value << 4));
                break;
            case 5:
                writeToBufferPool(52, (bufferPool[52] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(53, (bufferPool[53] & 4095) | ((value & 15) << 12));
                break;
            case 6:
                writeToBufferPool(53, (bufferPool[53] & 63503) | (value << 4));
                break;
            case 7:
                writeToBufferPool(53, (bufferPool[53] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(54, (bufferPool[54] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(54, (bufferPool[54] & 63503) | (value << 4));
                break;
            case 9:
                writeToBufferPool(54, (bufferPool[54] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(55, (bufferPool[55] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readExploreSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(50) & 4080) >>> 4;
            case 1:
                return ((rc.readSharedArray(50) & 15) << 4) + ((rc.readSharedArray(51) & 61440) >>> 12);
            case 2:
                return (rc.readSharedArray(51) & 4080) >>> 4;
            case 3:
                return ((rc.readSharedArray(51) & 15) << 4) + ((rc.readSharedArray(52) & 61440) >>> 12);
            case 4:
                return (rc.readSharedArray(52) & 4080) >>> 4;
            case 5:
                return ((rc.readSharedArray(52) & 15) << 4) + ((rc.readSharedArray(53) & 61440) >>> 12);
            case 6:
                return (rc.readSharedArray(53) & 4080) >>> 4;
            case 7:
                return ((rc.readSharedArray(53) & 15) << 4) + ((rc.readSharedArray(54) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(54) & 4080) >>> 4;
            case 9:
                return ((rc.readSharedArray(54) & 15) << 4) + ((rc.readSharedArray(55) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 61455) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 61455) | (value << 4));
                break;
            case 3:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 4095) | ((value & 15) << 12));
                break;
            case 4:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 61455) | (value << 4));
                break;
            case 5:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 4095) | ((value & 15) << 12));
                break;
            case 6:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 61455) | (value << 4));
                break;
            case 7:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 61455) | (value << 4));
                break;
            case 9:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(50, (bufferPool[50] & 61455) | (value << 4));
                break;
            case 1:
                writeToBufferPool(50, (bufferPool[50] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(51, (bufferPool[51] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(51, (bufferPool[51] & 61455) | (value << 4));
                break;
            case 3:
                writeToBufferPool(51, (bufferPool[51] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(52, (bufferPool[52] & 4095) | ((value & 15) << 12));
                break;
            case 4:
                writeToBufferPool(52, (bufferPool[52] & 61455) | (value << 4));
                break;
            case 5:
                writeToBufferPool(52, (bufferPool[52] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(53, (bufferPool[53] & 4095) | ((value & 15) << 12));
                break;
            case 6:
                writeToBufferPool(53, (bufferPool[53] & 61455) | (value << 4));
                break;
            case 7:
                writeToBufferPool(53, (bufferPool[53] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(54, (bufferPool[54] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(54, (bufferPool[54] & 61455) | (value << 4));
                break;
            case 9:
                writeToBufferPool(54, (bufferPool[54] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(55, (bufferPool[55] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readMineSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(55) & 3584) >>> 9;
            case 1:
                return ((rc.readSharedArray(55) & 3) << 1) + ((rc.readSharedArray(56) & 32768) >>> 15);
            case 2:
                return (rc.readSharedArray(56) & 224) >>> 5;
            case 3:
                return (rc.readSharedArray(57) & 14336) >>> 11;
            case 4:
                return (rc.readSharedArray(57) & 14) >>> 1;
            case 5:
                return (rc.readSharedArray(58) & 896) >>> 7;
            case 6:
                return (rc.readSharedArray(59) & 57344) >>> 13;
            case 7:
                return (rc.readSharedArray(59) & 56) >>> 3;
            case 8:
                return (rc.readSharedArray(60) & 3584) >>> 9;
            case 9:
                return ((rc.readSharedArray(60) & 3) << 1) + ((rc.readSharedArray(61) & 32768) >>> 15);
            default:
                return -1;
        }
    }

    public static void writeMineSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 61951) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 32767) | ((value & 1) << 15));
                break;
            case 2:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65311) | (value << 5));
                break;
            case 3:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 51199) | (value << 11));
                break;
            case 4:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 65521) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 64639) | (value << 7));
                break;
            case 6:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 8191) | (value << 13));
                break;
            case 7:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 65479) | (value << 3));
                break;
            case 8:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 61951) | (value << 9));
                break;
            case 9:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 32767) | ((value & 1) << 15));
                break;
        }
    }

    public static void writeBPMineSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(55, (bufferPool[55] & 61951) | (value << 9));
                break;
            case 1:
                writeToBufferPool(55, (bufferPool[55] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(56, (bufferPool[56] & 32767) | ((value & 1) << 15));
                break;
            case 2:
                writeToBufferPool(56, (bufferPool[56] & 65311) | (value << 5));
                break;
            case 3:
                writeToBufferPool(57, (bufferPool[57] & 51199) | (value << 11));
                break;
            case 4:
                writeToBufferPool(57, (bufferPool[57] & 65521) | (value << 1));
                break;
            case 5:
                writeToBufferPool(58, (bufferPool[58] & 64639) | (value << 7));
                break;
            case 6:
                writeToBufferPool(59, (bufferPool[59] & 8191) | (value << 13));
                break;
            case 7:
                writeToBufferPool(59, (bufferPool[59] & 65479) | (value << 3));
                break;
            case 8:
                writeToBufferPool(60, (bufferPool[60] & 61951) | (value << 9));
                break;
            case 9:
                writeToBufferPool(60, (bufferPool[60] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(61, (bufferPool[61] & 32767) | ((value & 1) << 15));
                break;
        }
    }

    public static int readMineSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(55) & 508) >>> 2;
            case 1:
                return (rc.readSharedArray(56) & 32512) >>> 8;
            case 2:
                return ((rc.readSharedArray(56) & 31) << 2) + ((rc.readSharedArray(57) & 49152) >>> 14);
            case 3:
                return (rc.readSharedArray(57) & 2032) >>> 4;
            case 4:
                return ((rc.readSharedArray(57) & 1) << 6) + ((rc.readSharedArray(58) & 64512) >>> 10);
            case 5:
                return (rc.readSharedArray(58) & 127);
            case 6:
                return (rc.readSharedArray(59) & 8128) >>> 6;
            case 7:
                return ((rc.readSharedArray(59) & 7) << 4) + ((rc.readSharedArray(60) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(60) & 508) >>> 2;
            case 9:
                return (rc.readSharedArray(61) & 32512) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65027) | (value << 2));
                break;
            case 1:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 33023) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 16383) | ((value & 3) << 14));
                break;
            case 3:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 63503) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 1023) | ((value & 63) << 10));
                break;
            case 5:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 65408) | (value));
                break;
            case 6:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 57407) | (value << 6));
                break;
            case 7:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 65027) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 33023) | (value << 8));
                break;
        }
    }

    public static void writeBPMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(55, (bufferPool[55] & 65027) | (value << 2));
                break;
            case 1:
                writeToBufferPool(56, (bufferPool[56] & 33023) | (value << 8));
                break;
            case 2:
                writeToBufferPool(56, (bufferPool[56] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(57, (bufferPool[57] & 16383) | ((value & 3) << 14));
                break;
            case 3:
                writeToBufferPool(57, (bufferPool[57] & 63503) | (value << 4));
                break;
            case 4:
                writeToBufferPool(57, (bufferPool[57] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(58, (bufferPool[58] & 1023) | ((value & 63) << 10));
                break;
            case 5:
                writeToBufferPool(58, (bufferPool[58] & 65408) | (value));
                break;
            case 6:
                writeToBufferPool(59, (bufferPool[59] & 57407) | (value << 6));
                break;
            case 7:
                writeToBufferPool(59, (bufferPool[59] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(60, (bufferPool[60] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(60, (bufferPool[60] & 65027) | (value << 2));
                break;
            case 9:
                writeToBufferPool(61, (bufferPool[61] & 33023) | (value << 8));
                break;
        }
    }

    public static int readMineSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(55) & 4092) >>> 2;
            case 1:
                return ((rc.readSharedArray(55) & 3) << 8) + ((rc.readSharedArray(56) & 65280) >>> 8);
            case 2:
                return ((rc.readSharedArray(56) & 255) << 2) + ((rc.readSharedArray(57) & 49152) >>> 14);
            case 3:
                return (rc.readSharedArray(57) & 16368) >>> 4;
            case 4:
                return ((rc.readSharedArray(57) & 15) << 6) + ((rc.readSharedArray(58) & 64512) >>> 10);
            case 5:
                return (rc.readSharedArray(58) & 1023);
            case 6:
                return (rc.readSharedArray(59) & 65472) >>> 6;
            case 7:
                return ((rc.readSharedArray(59) & 63) << 4) + ((rc.readSharedArray(60) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(60) & 4092) >>> 2;
            case 9:
                return ((rc.readSharedArray(60) & 3) << 8) + ((rc.readSharedArray(61) & 65280) >>> 8);
            default:
                return -1;
        }
    }

    public static void writeMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 61443) | (value << 2));
                break;
            case 1:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65532) | ((value & 768) >>> 8));
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 255) | ((value & 255) << 8));
                break;
            case 2:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65280) | ((value & 1020) >>> 2));
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 16383) | ((value & 3) << 14));
                break;
            case 3:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 49167) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 65520) | ((value & 960) >>> 6));
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 1023) | ((value & 63) << 10));
                break;
            case 5:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 64512) | (value));
                break;
            case 6:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 63) | (value << 6));
                break;
            case 7:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 65472) | ((value & 1008) >>> 4));
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 61443) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 65532) | ((value & 768) >>> 8));
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 255) | ((value & 255) << 8));
                break;
        }
    }

    public static void writeBPMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(55, (bufferPool[55] & 61443) | (value << 2));
                break;
            case 1:
                writeToBufferPool(55, (bufferPool[55] & 65532) | ((value & 768) >>> 8));
                writeToBufferPool(56, (bufferPool[56] & 255) | ((value & 255) << 8));
                break;
            case 2:
                writeToBufferPool(56, (bufferPool[56] & 65280) | ((value & 1020) >>> 2));
                writeToBufferPool(57, (bufferPool[57] & 16383) | ((value & 3) << 14));
                break;
            case 3:
                writeToBufferPool(57, (bufferPool[57] & 49167) | (value << 4));
                break;
            case 4:
                writeToBufferPool(57, (bufferPool[57] & 65520) | ((value & 960) >>> 6));
                writeToBufferPool(58, (bufferPool[58] & 1023) | ((value & 63) << 10));
                break;
            case 5:
                writeToBufferPool(58, (bufferPool[58] & 64512) | (value));
                break;
            case 6:
                writeToBufferPool(59, (bufferPool[59] & 63) | (value << 6));
                break;
            case 7:
                writeToBufferPool(59, (bufferPool[59] & 65472) | ((value & 1008) >>> 4));
                writeToBufferPool(60, (bufferPool[60] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(60, (bufferPool[60] & 61443) | (value << 2));
                break;
            case 9:
                writeToBufferPool(60, (bufferPool[60] & 65532) | ((value & 768) >>> 8));
                writeToBufferPool(61, (bufferPool[61] & 255) | ((value & 255) << 8));
                break;
        }
    }

    // BUFFER POOL READ AND WRITE METHODS

}
