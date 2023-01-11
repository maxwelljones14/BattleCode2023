package MPWorking;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;

    private static int[] thresholds = { 0, 1, 2, 3, 5, 10, 50 };


    public final static int OUR_HQ_SLOTS = 4;
    public final static int BOT_COUNT_SLOTS = 1;
    public final static int SECTOR_SLOTS = 100;
    public final static int COMBAT_SECTOR_SLOTS = 8;
    public final static int MINE_SECTOR_SLOTS = 10;

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

    public static void initPrioritySectors() throws GameActionException {
        rc.writeSharedArray(47, 15);
        rc.writeSharedArray(48, 65535);
        rc.writeSharedArray(49, 65535);
        rc.writeSharedArray(50, 65535);
        rc.writeSharedArray(51, 61948);
        rc.writeSharedArray(52, 32543);
        rc.writeSharedArray(53, 51185);
        rc.writeSharedArray(54, 64639);
        rc.writeSharedArray(55, 8135);
        rc.writeSharedArray(56, 61948);
        rc.writeSharedArray(57, 32512);
    }


    public static void resetAllSectorExplored() throws GameActionException {
        rc.writeSharedArray(4, rc.readSharedArray(4) & 32509);
        rc.writeSharedArray(5, rc.readSharedArray(5) & 64503);
        rc.writeSharedArray(6, rc.readSharedArray(6) & 61407);
        rc.writeSharedArray(7, rc.readSharedArray(7) & 49022);
        rc.writeSharedArray(8, rc.readSharedArray(8) & 65019);
        rc.writeSharedArray(9, rc.readSharedArray(9) & 63471);
        rc.writeSharedArray(10, rc.readSharedArray(10) & 57279);
        rc.writeSharedArray(11, rc.readSharedArray(11) & 32509);
        rc.writeSharedArray(12, rc.readSharedArray(12) & 64503);
        rc.writeSharedArray(13, rc.readSharedArray(13) & 61407);
        rc.writeSharedArray(14, rc.readSharedArray(14) & 49022);
        rc.writeSharedArray(15, rc.readSharedArray(15) & 65019);
        rc.writeSharedArray(16, rc.readSharedArray(16) & 63471);
        rc.writeSharedArray(17, rc.readSharedArray(17) & 57279);
        rc.writeSharedArray(18, rc.readSharedArray(18) & 32509);
        rc.writeSharedArray(19, rc.readSharedArray(19) & 64503);
        rc.writeSharedArray(20, rc.readSharedArray(20) & 61407);
        rc.writeSharedArray(21, rc.readSharedArray(21) & 49022);
        rc.writeSharedArray(22, rc.readSharedArray(22) & 65019);
        rc.writeSharedArray(23, rc.readSharedArray(23) & 63471);
        rc.writeSharedArray(24, rc.readSharedArray(24) & 57279);
        rc.writeSharedArray(25, rc.readSharedArray(25) & 32509);
        rc.writeSharedArray(26, rc.readSharedArray(26) & 64503);
        rc.writeSharedArray(27, rc.readSharedArray(27) & 61407);
        rc.writeSharedArray(28, rc.readSharedArray(28) & 49022);
        rc.writeSharedArray(29, rc.readSharedArray(29) & 65019);
        rc.writeSharedArray(30, rc.readSharedArray(30) & 63471);
        rc.writeSharedArray(31, rc.readSharedArray(31) & 57279);
        rc.writeSharedArray(32, rc.readSharedArray(32) & 32509);
        rc.writeSharedArray(33, rc.readSharedArray(33) & 64503);
        rc.writeSharedArray(34, rc.readSharedArray(34) & 61407);
        rc.writeSharedArray(35, rc.readSharedArray(35) & 49022);
        rc.writeSharedArray(36, rc.readSharedArray(36) & 65019);
        rc.writeSharedArray(37, rc.readSharedArray(37) & 63471);
        rc.writeSharedArray(38, rc.readSharedArray(38) & 57279);
        rc.writeSharedArray(39, rc.readSharedArray(39) & 32509);
        rc.writeSharedArray(40, rc.readSharedArray(40) & 64503);
        rc.writeSharedArray(41, rc.readSharedArray(41) & 61407);
        rc.writeSharedArray(42, rc.readSharedArray(42) & 49022);
        rc.writeSharedArray(43, rc.readSharedArray(43) & 65019);
        rc.writeSharedArray(44, rc.readSharedArray(44) & 63471);
        rc.writeSharedArray(45, rc.readSharedArray(45) & 57279);
        rc.writeSharedArray(46, rc.readSharedArray(46) & 32509);
        rc.writeSharedArray(47, rc.readSharedArray(47) & 64511);
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

    public static int readBotCountCarriers() throws GameActionException {
        return (rc.readSharedArray(3) & 65280) >>> 8;
    }

    public static void writeBotCountCarriers(int value) throws GameActionException {
        rc.writeSharedArray(3, (rc.readSharedArray(3) & 255) | (value << 8));
    }

    public static void writeBPBotCountCarriers(int value) throws GameActionException {
        writeToBufferPool(3, (bufferPool[3] & 255) | (value << 8));
    }

    public static int readBotCountLaunchers() throws GameActionException {
        return (rc.readSharedArray(3) & 255);
    }

    public static void writeBotCountLaunchers(int value) throws GameActionException {
        rc.writeSharedArray(3, (rc.readSharedArray(3) & 65280) | (value));
    }

    public static void writeBPBotCountLaunchers(int value) throws GameActionException {
        writeToBufferPool(3, (bufferPool[3] & 65280) | (value));
    }

    public static int readBotCountAll() throws GameActionException {
        return (rc.readSharedArray(3) & 65535);
    }

    public static void writeBotCountAll(int value) throws GameActionException {
        rc.writeSharedArray(3, (rc.readSharedArray(3) & 0) | (value));
    }

    public static void writeBPBotCountAll(int value) throws GameActionException {
        writeToBufferPool(3, (bufferPool[3] & 0) | (value));
    }

    public static int readSectorExplored(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 32768) >>> 15;
            case 1:
                return (rc.readSharedArray(4) & 256) >>> 8;
            case 2:
                return (rc.readSharedArray(4) & 2) >>> 1;
            case 3:
                return (rc.readSharedArray(5) & 1024) >>> 10;
            case 4:
                return (rc.readSharedArray(5) & 8) >>> 3;
            case 5:
                return (rc.readSharedArray(6) & 4096) >>> 12;
            case 6:
                return (rc.readSharedArray(6) & 32) >>> 5;
            case 7:
                return (rc.readSharedArray(7) & 16384) >>> 14;
            case 8:
                return (rc.readSharedArray(7) & 128) >>> 7;
            case 9:
                return (rc.readSharedArray(7) & 1);
            case 10:
                return (rc.readSharedArray(8) & 512) >>> 9;
            case 11:
                return (rc.readSharedArray(8) & 4) >>> 2;
            case 12:
                return (rc.readSharedArray(9) & 2048) >>> 11;
            case 13:
                return (rc.readSharedArray(9) & 16) >>> 4;
            case 14:
                return (rc.readSharedArray(10) & 8192) >>> 13;
            case 15:
                return (rc.readSharedArray(10) & 64) >>> 6;
            case 16:
                return (rc.readSharedArray(11) & 32768) >>> 15;
            case 17:
                return (rc.readSharedArray(11) & 256) >>> 8;
            case 18:
                return (rc.readSharedArray(11) & 2) >>> 1;
            case 19:
                return (rc.readSharedArray(12) & 1024) >>> 10;
            case 20:
                return (rc.readSharedArray(12) & 8) >>> 3;
            case 21:
                return (rc.readSharedArray(13) & 4096) >>> 12;
            case 22:
                return (rc.readSharedArray(13) & 32) >>> 5;
            case 23:
                return (rc.readSharedArray(14) & 16384) >>> 14;
            case 24:
                return (rc.readSharedArray(14) & 128) >>> 7;
            case 25:
                return (rc.readSharedArray(14) & 1);
            case 26:
                return (rc.readSharedArray(15) & 512) >>> 9;
            case 27:
                return (rc.readSharedArray(15) & 4) >>> 2;
            case 28:
                return (rc.readSharedArray(16) & 2048) >>> 11;
            case 29:
                return (rc.readSharedArray(16) & 16) >>> 4;
            case 30:
                return (rc.readSharedArray(17) & 8192) >>> 13;
            case 31:
                return (rc.readSharedArray(17) & 64) >>> 6;
            case 32:
                return (rc.readSharedArray(18) & 32768) >>> 15;
            case 33:
                return (rc.readSharedArray(18) & 256) >>> 8;
            case 34:
                return (rc.readSharedArray(18) & 2) >>> 1;
            case 35:
                return (rc.readSharedArray(19) & 1024) >>> 10;
            case 36:
                return (rc.readSharedArray(19) & 8) >>> 3;
            case 37:
                return (rc.readSharedArray(20) & 4096) >>> 12;
            case 38:
                return (rc.readSharedArray(20) & 32) >>> 5;
            case 39:
                return (rc.readSharedArray(21) & 16384) >>> 14;
            case 40:
                return (rc.readSharedArray(21) & 128) >>> 7;
            case 41:
                return (rc.readSharedArray(21) & 1);
            case 42:
                return (rc.readSharedArray(22) & 512) >>> 9;
            case 43:
                return (rc.readSharedArray(22) & 4) >>> 2;
            case 44:
                return (rc.readSharedArray(23) & 2048) >>> 11;
            case 45:
                return (rc.readSharedArray(23) & 16) >>> 4;
            case 46:
                return (rc.readSharedArray(24) & 8192) >>> 13;
            case 47:
                return (rc.readSharedArray(24) & 64) >>> 6;
            case 48:
                return (rc.readSharedArray(25) & 32768) >>> 15;
            case 49:
                return (rc.readSharedArray(25) & 256) >>> 8;
            case 50:
                return (rc.readSharedArray(25) & 2) >>> 1;
            case 51:
                return (rc.readSharedArray(26) & 1024) >>> 10;
            case 52:
                return (rc.readSharedArray(26) & 8) >>> 3;
            case 53:
                return (rc.readSharedArray(27) & 4096) >>> 12;
            case 54:
                return (rc.readSharedArray(27) & 32) >>> 5;
            case 55:
                return (rc.readSharedArray(28) & 16384) >>> 14;
            case 56:
                return (rc.readSharedArray(28) & 128) >>> 7;
            case 57:
                return (rc.readSharedArray(28) & 1);
            case 58:
                return (rc.readSharedArray(29) & 512) >>> 9;
            case 59:
                return (rc.readSharedArray(29) & 4) >>> 2;
            case 60:
                return (rc.readSharedArray(30) & 2048) >>> 11;
            case 61:
                return (rc.readSharedArray(30) & 16) >>> 4;
            case 62:
                return (rc.readSharedArray(31) & 8192) >>> 13;
            case 63:
                return (rc.readSharedArray(31) & 64) >>> 6;
            case 64:
                return (rc.readSharedArray(32) & 32768) >>> 15;
            case 65:
                return (rc.readSharedArray(32) & 256) >>> 8;
            case 66:
                return (rc.readSharedArray(32) & 2) >>> 1;
            case 67:
                return (rc.readSharedArray(33) & 1024) >>> 10;
            case 68:
                return (rc.readSharedArray(33) & 8) >>> 3;
            case 69:
                return (rc.readSharedArray(34) & 4096) >>> 12;
            case 70:
                return (rc.readSharedArray(34) & 32) >>> 5;
            case 71:
                return (rc.readSharedArray(35) & 16384) >>> 14;
            case 72:
                return (rc.readSharedArray(35) & 128) >>> 7;
            case 73:
                return (rc.readSharedArray(35) & 1);
            case 74:
                return (rc.readSharedArray(36) & 512) >>> 9;
            case 75:
                return (rc.readSharedArray(36) & 4) >>> 2;
            case 76:
                return (rc.readSharedArray(37) & 2048) >>> 11;
            case 77:
                return (rc.readSharedArray(37) & 16) >>> 4;
            case 78:
                return (rc.readSharedArray(38) & 8192) >>> 13;
            case 79:
                return (rc.readSharedArray(38) & 64) >>> 6;
            case 80:
                return (rc.readSharedArray(39) & 32768) >>> 15;
            case 81:
                return (rc.readSharedArray(39) & 256) >>> 8;
            case 82:
                return (rc.readSharedArray(39) & 2) >>> 1;
            case 83:
                return (rc.readSharedArray(40) & 1024) >>> 10;
            case 84:
                return (rc.readSharedArray(40) & 8) >>> 3;
            case 85:
                return (rc.readSharedArray(41) & 4096) >>> 12;
            case 86:
                return (rc.readSharedArray(41) & 32) >>> 5;
            case 87:
                return (rc.readSharedArray(42) & 16384) >>> 14;
            case 88:
                return (rc.readSharedArray(42) & 128) >>> 7;
            case 89:
                return (rc.readSharedArray(42) & 1);
            case 90:
                return (rc.readSharedArray(43) & 512) >>> 9;
            case 91:
                return (rc.readSharedArray(43) & 4) >>> 2;
            case 92:
                return (rc.readSharedArray(44) & 2048) >>> 11;
            case 93:
                return (rc.readSharedArray(44) & 16) >>> 4;
            case 94:
                return (rc.readSharedArray(45) & 8192) >>> 13;
            case 95:
                return (rc.readSharedArray(45) & 64) >>> 6;
            case 96:
                return (rc.readSharedArray(46) & 32768) >>> 15;
            case 97:
                return (rc.readSharedArray(46) & 256) >>> 8;
            case 98:
                return (rc.readSharedArray(46) & 2) >>> 1;
            case 99:
                return (rc.readSharedArray(47) & 1024) >>> 10;
            default:
                return -1;
        }
    }

    public static void writeSectorExplored(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 32767) | (value << 15));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65279) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65533) | (value << 1));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 64511) | (value << 10));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65527) | (value << 3));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 61439) | (value << 12));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65503) | (value << 5));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 49151) | (value << 14));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65407) | (value << 7));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65534) | (value));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65023) | (value << 9));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65531) | (value << 2));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 63487) | (value << 11));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65519) | (value << 4));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 57343) | (value << 13));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65471) | (value << 6));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 32767) | (value << 15));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65279) | (value << 8));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65533) | (value << 1));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 64511) | (value << 10));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65527) | (value << 3));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 61439) | (value << 12));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65503) | (value << 5));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 49151) | (value << 14));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65407) | (value << 7));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65534) | (value));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65023) | (value << 9));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65531) | (value << 2));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 63487) | (value << 11));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65519) | (value << 4));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 57343) | (value << 13));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65471) | (value << 6));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 32767) | (value << 15));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65279) | (value << 8));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65533) | (value << 1));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 64511) | (value << 10));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65527) | (value << 3));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 61439) | (value << 12));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65503) | (value << 5));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 49151) | (value << 14));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65407) | (value << 7));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65534) | (value));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65023) | (value << 9));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65531) | (value << 2));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 63487) | (value << 11));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65519) | (value << 4));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 57343) | (value << 13));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65471) | (value << 6));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 32767) | (value << 15));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65279) | (value << 8));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65533) | (value << 1));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 64511) | (value << 10));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65527) | (value << 3));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 61439) | (value << 12));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65503) | (value << 5));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 49151) | (value << 14));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65407) | (value << 7));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65534) | (value));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65023) | (value << 9));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65531) | (value << 2));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 63487) | (value << 11));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65519) | (value << 4));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 57343) | (value << 13));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65471) | (value << 6));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 32767) | (value << 15));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65279) | (value << 8));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65533) | (value << 1));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 64511) | (value << 10));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65527) | (value << 3));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 61439) | (value << 12));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65503) | (value << 5));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 49151) | (value << 14));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65407) | (value << 7));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65534) | (value));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65023) | (value << 9));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65531) | (value << 2));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 63487) | (value << 11));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65519) | (value << 4));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 57343) | (value << 13));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65471) | (value << 6));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 32767) | (value << 15));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65279) | (value << 8));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65533) | (value << 1));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 64511) | (value << 10));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65527) | (value << 3));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 61439) | (value << 12));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65503) | (value << 5));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 49151) | (value << 14));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65407) | (value << 7));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65534) | (value));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65023) | (value << 9));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65531) | (value << 2));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 63487) | (value << 11));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65519) | (value << 4));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 57343) | (value << 13));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65471) | (value << 6));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 32767) | (value << 15));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65279) | (value << 8));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65533) | (value << 1));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 64511) | (value << 10));
                break;
        }
    }

    public static void writeBPSectorExplored(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 32767) | (value << 15));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65279) | (value << 8));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65533) | (value << 1));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 64511) | (value << 10));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65527) | (value << 3));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 61439) | (value << 12));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65503) | (value << 5));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 49151) | (value << 14));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65407) | (value << 7));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 65534) | (value));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65023) | (value << 9));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 65531) | (value << 2));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 63487) | (value << 11));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 65519) | (value << 4));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 57343) | (value << 13));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65471) | (value << 6));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 32767) | (value << 15));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65279) | (value << 8));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 65533) | (value << 1));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 64511) | (value << 10));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 65527) | (value << 3));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 61439) | (value << 12));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65503) | (value << 5));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 49151) | (value << 14));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65407) | (value << 7));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 65534) | (value));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65023) | (value << 9));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 65531) | (value << 2));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 63487) | (value << 11));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 65519) | (value << 4));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 57343) | (value << 13));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65471) | (value << 6));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 32767) | (value << 15));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65279) | (value << 8));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 65533) | (value << 1));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 64511) | (value << 10));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 65527) | (value << 3));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 61439) | (value << 12));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65503) | (value << 5));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 49151) | (value << 14));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65407) | (value << 7));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 65534) | (value));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65023) | (value << 9));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 65531) | (value << 2));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 63487) | (value << 11));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 65519) | (value << 4));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 57343) | (value << 13));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65471) | (value << 6));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 32767) | (value << 15));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65279) | (value << 8));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 65533) | (value << 1));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 64511) | (value << 10));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 65527) | (value << 3));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 61439) | (value << 12));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65503) | (value << 5));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 49151) | (value << 14));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65407) | (value << 7));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 65534) | (value));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65023) | (value << 9));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 65531) | (value << 2));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 63487) | (value << 11));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 65519) | (value << 4));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 57343) | (value << 13));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65471) | (value << 6));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 32767) | (value << 15));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65279) | (value << 8));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 65533) | (value << 1));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 64511) | (value << 10));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 65527) | (value << 3));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 61439) | (value << 12));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65503) | (value << 5));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 49151) | (value << 14));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65407) | (value << 7));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 65534) | (value));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65023) | (value << 9));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 65531) | (value << 2));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 63487) | (value << 11));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 65519) | (value << 4));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 57343) | (value << 13));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65471) | (value << 6));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 32767) | (value << 15));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65279) | (value << 8));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 65533) | (value << 1));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 64511) | (value << 10));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 65527) | (value << 3));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 61439) | (value << 12));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65503) | (value << 5));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 49151) | (value << 14));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65407) | (value << 7));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 65534) | (value));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65023) | (value << 9));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 65531) | (value << 2));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 63487) | (value << 11));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 65519) | (value << 4));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 57343) | (value << 13));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65471) | (value << 6));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 32767) | (value << 15));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65279) | (value << 8));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 65533) | (value << 1));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 64511) | (value << 10));
                break;
        }
    }

    public static int readSectorAdamantiumFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 16384) >>> 14;
            case 1:
                return (rc.readSharedArray(4) & 128) >>> 7;
            case 2:
                return (rc.readSharedArray(4) & 1);
            case 3:
                return (rc.readSharedArray(5) & 512) >>> 9;
            case 4:
                return (rc.readSharedArray(5) & 4) >>> 2;
            case 5:
                return (rc.readSharedArray(6) & 2048) >>> 11;
            case 6:
                return (rc.readSharedArray(6) & 16) >>> 4;
            case 7:
                return (rc.readSharedArray(7) & 8192) >>> 13;
            case 8:
                return (rc.readSharedArray(7) & 64) >>> 6;
            case 9:
                return (rc.readSharedArray(8) & 32768) >>> 15;
            case 10:
                return (rc.readSharedArray(8) & 256) >>> 8;
            case 11:
                return (rc.readSharedArray(8) & 2) >>> 1;
            case 12:
                return (rc.readSharedArray(9) & 1024) >>> 10;
            case 13:
                return (rc.readSharedArray(9) & 8) >>> 3;
            case 14:
                return (rc.readSharedArray(10) & 4096) >>> 12;
            case 15:
                return (rc.readSharedArray(10) & 32) >>> 5;
            case 16:
                return (rc.readSharedArray(11) & 16384) >>> 14;
            case 17:
                return (rc.readSharedArray(11) & 128) >>> 7;
            case 18:
                return (rc.readSharedArray(11) & 1);
            case 19:
                return (rc.readSharedArray(12) & 512) >>> 9;
            case 20:
                return (rc.readSharedArray(12) & 4) >>> 2;
            case 21:
                return (rc.readSharedArray(13) & 2048) >>> 11;
            case 22:
                return (rc.readSharedArray(13) & 16) >>> 4;
            case 23:
                return (rc.readSharedArray(14) & 8192) >>> 13;
            case 24:
                return (rc.readSharedArray(14) & 64) >>> 6;
            case 25:
                return (rc.readSharedArray(15) & 32768) >>> 15;
            case 26:
                return (rc.readSharedArray(15) & 256) >>> 8;
            case 27:
                return (rc.readSharedArray(15) & 2) >>> 1;
            case 28:
                return (rc.readSharedArray(16) & 1024) >>> 10;
            case 29:
                return (rc.readSharedArray(16) & 8) >>> 3;
            case 30:
                return (rc.readSharedArray(17) & 4096) >>> 12;
            case 31:
                return (rc.readSharedArray(17) & 32) >>> 5;
            case 32:
                return (rc.readSharedArray(18) & 16384) >>> 14;
            case 33:
                return (rc.readSharedArray(18) & 128) >>> 7;
            case 34:
                return (rc.readSharedArray(18) & 1);
            case 35:
                return (rc.readSharedArray(19) & 512) >>> 9;
            case 36:
                return (rc.readSharedArray(19) & 4) >>> 2;
            case 37:
                return (rc.readSharedArray(20) & 2048) >>> 11;
            case 38:
                return (rc.readSharedArray(20) & 16) >>> 4;
            case 39:
                return (rc.readSharedArray(21) & 8192) >>> 13;
            case 40:
                return (rc.readSharedArray(21) & 64) >>> 6;
            case 41:
                return (rc.readSharedArray(22) & 32768) >>> 15;
            case 42:
                return (rc.readSharedArray(22) & 256) >>> 8;
            case 43:
                return (rc.readSharedArray(22) & 2) >>> 1;
            case 44:
                return (rc.readSharedArray(23) & 1024) >>> 10;
            case 45:
                return (rc.readSharedArray(23) & 8) >>> 3;
            case 46:
                return (rc.readSharedArray(24) & 4096) >>> 12;
            case 47:
                return (rc.readSharedArray(24) & 32) >>> 5;
            case 48:
                return (rc.readSharedArray(25) & 16384) >>> 14;
            case 49:
                return (rc.readSharedArray(25) & 128) >>> 7;
            case 50:
                return (rc.readSharedArray(25) & 1);
            case 51:
                return (rc.readSharedArray(26) & 512) >>> 9;
            case 52:
                return (rc.readSharedArray(26) & 4) >>> 2;
            case 53:
                return (rc.readSharedArray(27) & 2048) >>> 11;
            case 54:
                return (rc.readSharedArray(27) & 16) >>> 4;
            case 55:
                return (rc.readSharedArray(28) & 8192) >>> 13;
            case 56:
                return (rc.readSharedArray(28) & 64) >>> 6;
            case 57:
                return (rc.readSharedArray(29) & 32768) >>> 15;
            case 58:
                return (rc.readSharedArray(29) & 256) >>> 8;
            case 59:
                return (rc.readSharedArray(29) & 2) >>> 1;
            case 60:
                return (rc.readSharedArray(30) & 1024) >>> 10;
            case 61:
                return (rc.readSharedArray(30) & 8) >>> 3;
            case 62:
                return (rc.readSharedArray(31) & 4096) >>> 12;
            case 63:
                return (rc.readSharedArray(31) & 32) >>> 5;
            case 64:
                return (rc.readSharedArray(32) & 16384) >>> 14;
            case 65:
                return (rc.readSharedArray(32) & 128) >>> 7;
            case 66:
                return (rc.readSharedArray(32) & 1);
            case 67:
                return (rc.readSharedArray(33) & 512) >>> 9;
            case 68:
                return (rc.readSharedArray(33) & 4) >>> 2;
            case 69:
                return (rc.readSharedArray(34) & 2048) >>> 11;
            case 70:
                return (rc.readSharedArray(34) & 16) >>> 4;
            case 71:
                return (rc.readSharedArray(35) & 8192) >>> 13;
            case 72:
                return (rc.readSharedArray(35) & 64) >>> 6;
            case 73:
                return (rc.readSharedArray(36) & 32768) >>> 15;
            case 74:
                return (rc.readSharedArray(36) & 256) >>> 8;
            case 75:
                return (rc.readSharedArray(36) & 2) >>> 1;
            case 76:
                return (rc.readSharedArray(37) & 1024) >>> 10;
            case 77:
                return (rc.readSharedArray(37) & 8) >>> 3;
            case 78:
                return (rc.readSharedArray(38) & 4096) >>> 12;
            case 79:
                return (rc.readSharedArray(38) & 32) >>> 5;
            case 80:
                return (rc.readSharedArray(39) & 16384) >>> 14;
            case 81:
                return (rc.readSharedArray(39) & 128) >>> 7;
            case 82:
                return (rc.readSharedArray(39) & 1);
            case 83:
                return (rc.readSharedArray(40) & 512) >>> 9;
            case 84:
                return (rc.readSharedArray(40) & 4) >>> 2;
            case 85:
                return (rc.readSharedArray(41) & 2048) >>> 11;
            case 86:
                return (rc.readSharedArray(41) & 16) >>> 4;
            case 87:
                return (rc.readSharedArray(42) & 8192) >>> 13;
            case 88:
                return (rc.readSharedArray(42) & 64) >>> 6;
            case 89:
                return (rc.readSharedArray(43) & 32768) >>> 15;
            case 90:
                return (rc.readSharedArray(43) & 256) >>> 8;
            case 91:
                return (rc.readSharedArray(43) & 2) >>> 1;
            case 92:
                return (rc.readSharedArray(44) & 1024) >>> 10;
            case 93:
                return (rc.readSharedArray(44) & 8) >>> 3;
            case 94:
                return (rc.readSharedArray(45) & 4096) >>> 12;
            case 95:
                return (rc.readSharedArray(45) & 32) >>> 5;
            case 96:
                return (rc.readSharedArray(46) & 16384) >>> 14;
            case 97:
                return (rc.readSharedArray(46) & 128) >>> 7;
            case 98:
                return (rc.readSharedArray(46) & 1);
            case 99:
                return (rc.readSharedArray(47) & 512) >>> 9;
            default:
                return -1;
        }
    }

    public static void writeSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 49151) | (value << 14));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65407) | (value << 7));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65534) | (value));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65023) | (value << 9));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65531) | (value << 2));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 63487) | (value << 11));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65519) | (value << 4));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 57343) | (value << 13));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65471) | (value << 6));
                break;
            case 9:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 32767) | (value << 15));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65279) | (value << 8));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65533) | (value << 1));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 64511) | (value << 10));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65527) | (value << 3));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 61439) | (value << 12));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65503) | (value << 5));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 49151) | (value << 14));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65407) | (value << 7));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65534) | (value));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65023) | (value << 9));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65531) | (value << 2));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 63487) | (value << 11));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65519) | (value << 4));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 57343) | (value << 13));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65471) | (value << 6));
                break;
            case 25:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 32767) | (value << 15));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65279) | (value << 8));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65533) | (value << 1));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 64511) | (value << 10));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65527) | (value << 3));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 61439) | (value << 12));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65503) | (value << 5));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 49151) | (value << 14));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65407) | (value << 7));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65534) | (value));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65023) | (value << 9));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65531) | (value << 2));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63487) | (value << 11));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65519) | (value << 4));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 57343) | (value << 13));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65471) | (value << 6));
                break;
            case 41:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 32767) | (value << 15));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65279) | (value << 8));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65533) | (value << 1));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 64511) | (value << 10));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65527) | (value << 3));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 61439) | (value << 12));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65503) | (value << 5));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 49151) | (value << 14));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65407) | (value << 7));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65534) | (value));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65023) | (value << 9));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65531) | (value << 2));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 63487) | (value << 11));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65519) | (value << 4));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 57343) | (value << 13));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65471) | (value << 6));
                break;
            case 57:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 32767) | (value << 15));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65279) | (value << 8));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65533) | (value << 1));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 64511) | (value << 10));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65527) | (value << 3));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 61439) | (value << 12));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65503) | (value << 5));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 49151) | (value << 14));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65407) | (value << 7));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65534) | (value));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65023) | (value << 9));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65531) | (value << 2));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 63487) | (value << 11));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65519) | (value << 4));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 57343) | (value << 13));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65471) | (value << 6));
                break;
            case 73:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 32767) | (value << 15));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65279) | (value << 8));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65533) | (value << 1));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 64511) | (value << 10));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65527) | (value << 3));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 61439) | (value << 12));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65503) | (value << 5));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 49151) | (value << 14));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65407) | (value << 7));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65534) | (value));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65023) | (value << 9));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65531) | (value << 2));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63487) | (value << 11));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65519) | (value << 4));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 57343) | (value << 13));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65471) | (value << 6));
                break;
            case 89:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 32767) | (value << 15));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65279) | (value << 8));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65533) | (value << 1));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 64511) | (value << 10));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65527) | (value << 3));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 61439) | (value << 12));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65503) | (value << 5));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 49151) | (value << 14));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65407) | (value << 7));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65534) | (value));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65023) | (value << 9));
                break;
        }
    }

    public static void writeBPSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 49151) | (value << 14));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65407) | (value << 7));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65534) | (value));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 65023) | (value << 9));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65531) | (value << 2));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 63487) | (value << 11));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65519) | (value << 4));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 57343) | (value << 13));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65471) | (value << 6));
                break;
            case 9:
                writeToBufferPool(8, (bufferPool[8] & 32767) | (value << 15));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65279) | (value << 8));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 65533) | (value << 1));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 64511) | (value << 10));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 65527) | (value << 3));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 61439) | (value << 12));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65503) | (value << 5));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 49151) | (value << 14));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65407) | (value << 7));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 65534) | (value));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 65023) | (value << 9));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 65531) | (value << 2));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 63487) | (value << 11));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65519) | (value << 4));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 57343) | (value << 13));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65471) | (value << 6));
                break;
            case 25:
                writeToBufferPool(15, (bufferPool[15] & 32767) | (value << 15));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65279) | (value << 8));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 65533) | (value << 1));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 64511) | (value << 10));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 65527) | (value << 3));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 61439) | (value << 12));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65503) | (value << 5));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 49151) | (value << 14));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65407) | (value << 7));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 65534) | (value));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 65023) | (value << 9));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 65531) | (value << 2));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 63487) | (value << 11));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65519) | (value << 4));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 57343) | (value << 13));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65471) | (value << 6));
                break;
            case 41:
                writeToBufferPool(22, (bufferPool[22] & 32767) | (value << 15));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65279) | (value << 8));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 65533) | (value << 1));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 64511) | (value << 10));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 65527) | (value << 3));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 61439) | (value << 12));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65503) | (value << 5));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 49151) | (value << 14));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65407) | (value << 7));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 65534) | (value));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 65023) | (value << 9));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 65531) | (value << 2));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 63487) | (value << 11));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65519) | (value << 4));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 57343) | (value << 13));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65471) | (value << 6));
                break;
            case 57:
                writeToBufferPool(29, (bufferPool[29] & 32767) | (value << 15));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65279) | (value << 8));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 65533) | (value << 1));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 64511) | (value << 10));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 65527) | (value << 3));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 61439) | (value << 12));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65503) | (value << 5));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 49151) | (value << 14));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65407) | (value << 7));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 65534) | (value));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 65023) | (value << 9));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 65531) | (value << 2));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 63487) | (value << 11));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65519) | (value << 4));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 57343) | (value << 13));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65471) | (value << 6));
                break;
            case 73:
                writeToBufferPool(36, (bufferPool[36] & 32767) | (value << 15));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65279) | (value << 8));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 65533) | (value << 1));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 64511) | (value << 10));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 65527) | (value << 3));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 61439) | (value << 12));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65503) | (value << 5));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 49151) | (value << 14));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65407) | (value << 7));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 65534) | (value));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 65023) | (value << 9));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 65531) | (value << 2));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 63487) | (value << 11));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65519) | (value << 4));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 57343) | (value << 13));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65471) | (value << 6));
                break;
            case 89:
                writeToBufferPool(43, (bufferPool[43] & 32767) | (value << 15));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65279) | (value << 8));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 65533) | (value << 1));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 64511) | (value << 10));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 65527) | (value << 3));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 61439) | (value << 12));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65503) | (value << 5));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 49151) | (value << 14));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65407) | (value << 7));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 65534) | (value));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 65023) | (value << 9));
                break;
        }
    }

    public static int readSectorManaFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 8192) >>> 13;
            case 1:
                return (rc.readSharedArray(4) & 64) >>> 6;
            case 2:
                return (rc.readSharedArray(5) & 32768) >>> 15;
            case 3:
                return (rc.readSharedArray(5) & 256) >>> 8;
            case 4:
                return (rc.readSharedArray(5) & 2) >>> 1;
            case 5:
                return (rc.readSharedArray(6) & 1024) >>> 10;
            case 6:
                return (rc.readSharedArray(6) & 8) >>> 3;
            case 7:
                return (rc.readSharedArray(7) & 4096) >>> 12;
            case 8:
                return (rc.readSharedArray(7) & 32) >>> 5;
            case 9:
                return (rc.readSharedArray(8) & 16384) >>> 14;
            case 10:
                return (rc.readSharedArray(8) & 128) >>> 7;
            case 11:
                return (rc.readSharedArray(8) & 1);
            case 12:
                return (rc.readSharedArray(9) & 512) >>> 9;
            case 13:
                return (rc.readSharedArray(9) & 4) >>> 2;
            case 14:
                return (rc.readSharedArray(10) & 2048) >>> 11;
            case 15:
                return (rc.readSharedArray(10) & 16) >>> 4;
            case 16:
                return (rc.readSharedArray(11) & 8192) >>> 13;
            case 17:
                return (rc.readSharedArray(11) & 64) >>> 6;
            case 18:
                return (rc.readSharedArray(12) & 32768) >>> 15;
            case 19:
                return (rc.readSharedArray(12) & 256) >>> 8;
            case 20:
                return (rc.readSharedArray(12) & 2) >>> 1;
            case 21:
                return (rc.readSharedArray(13) & 1024) >>> 10;
            case 22:
                return (rc.readSharedArray(13) & 8) >>> 3;
            case 23:
                return (rc.readSharedArray(14) & 4096) >>> 12;
            case 24:
                return (rc.readSharedArray(14) & 32) >>> 5;
            case 25:
                return (rc.readSharedArray(15) & 16384) >>> 14;
            case 26:
                return (rc.readSharedArray(15) & 128) >>> 7;
            case 27:
                return (rc.readSharedArray(15) & 1);
            case 28:
                return (rc.readSharedArray(16) & 512) >>> 9;
            case 29:
                return (rc.readSharedArray(16) & 4) >>> 2;
            case 30:
                return (rc.readSharedArray(17) & 2048) >>> 11;
            case 31:
                return (rc.readSharedArray(17) & 16) >>> 4;
            case 32:
                return (rc.readSharedArray(18) & 8192) >>> 13;
            case 33:
                return (rc.readSharedArray(18) & 64) >>> 6;
            case 34:
                return (rc.readSharedArray(19) & 32768) >>> 15;
            case 35:
                return (rc.readSharedArray(19) & 256) >>> 8;
            case 36:
                return (rc.readSharedArray(19) & 2) >>> 1;
            case 37:
                return (rc.readSharedArray(20) & 1024) >>> 10;
            case 38:
                return (rc.readSharedArray(20) & 8) >>> 3;
            case 39:
                return (rc.readSharedArray(21) & 4096) >>> 12;
            case 40:
                return (rc.readSharedArray(21) & 32) >>> 5;
            case 41:
                return (rc.readSharedArray(22) & 16384) >>> 14;
            case 42:
                return (rc.readSharedArray(22) & 128) >>> 7;
            case 43:
                return (rc.readSharedArray(22) & 1);
            case 44:
                return (rc.readSharedArray(23) & 512) >>> 9;
            case 45:
                return (rc.readSharedArray(23) & 4) >>> 2;
            case 46:
                return (rc.readSharedArray(24) & 2048) >>> 11;
            case 47:
                return (rc.readSharedArray(24) & 16) >>> 4;
            case 48:
                return (rc.readSharedArray(25) & 8192) >>> 13;
            case 49:
                return (rc.readSharedArray(25) & 64) >>> 6;
            case 50:
                return (rc.readSharedArray(26) & 32768) >>> 15;
            case 51:
                return (rc.readSharedArray(26) & 256) >>> 8;
            case 52:
                return (rc.readSharedArray(26) & 2) >>> 1;
            case 53:
                return (rc.readSharedArray(27) & 1024) >>> 10;
            case 54:
                return (rc.readSharedArray(27) & 8) >>> 3;
            case 55:
                return (rc.readSharedArray(28) & 4096) >>> 12;
            case 56:
                return (rc.readSharedArray(28) & 32) >>> 5;
            case 57:
                return (rc.readSharedArray(29) & 16384) >>> 14;
            case 58:
                return (rc.readSharedArray(29) & 128) >>> 7;
            case 59:
                return (rc.readSharedArray(29) & 1);
            case 60:
                return (rc.readSharedArray(30) & 512) >>> 9;
            case 61:
                return (rc.readSharedArray(30) & 4) >>> 2;
            case 62:
                return (rc.readSharedArray(31) & 2048) >>> 11;
            case 63:
                return (rc.readSharedArray(31) & 16) >>> 4;
            case 64:
                return (rc.readSharedArray(32) & 8192) >>> 13;
            case 65:
                return (rc.readSharedArray(32) & 64) >>> 6;
            case 66:
                return (rc.readSharedArray(33) & 32768) >>> 15;
            case 67:
                return (rc.readSharedArray(33) & 256) >>> 8;
            case 68:
                return (rc.readSharedArray(33) & 2) >>> 1;
            case 69:
                return (rc.readSharedArray(34) & 1024) >>> 10;
            case 70:
                return (rc.readSharedArray(34) & 8) >>> 3;
            case 71:
                return (rc.readSharedArray(35) & 4096) >>> 12;
            case 72:
                return (rc.readSharedArray(35) & 32) >>> 5;
            case 73:
                return (rc.readSharedArray(36) & 16384) >>> 14;
            case 74:
                return (rc.readSharedArray(36) & 128) >>> 7;
            case 75:
                return (rc.readSharedArray(36) & 1);
            case 76:
                return (rc.readSharedArray(37) & 512) >>> 9;
            case 77:
                return (rc.readSharedArray(37) & 4) >>> 2;
            case 78:
                return (rc.readSharedArray(38) & 2048) >>> 11;
            case 79:
                return (rc.readSharedArray(38) & 16) >>> 4;
            case 80:
                return (rc.readSharedArray(39) & 8192) >>> 13;
            case 81:
                return (rc.readSharedArray(39) & 64) >>> 6;
            case 82:
                return (rc.readSharedArray(40) & 32768) >>> 15;
            case 83:
                return (rc.readSharedArray(40) & 256) >>> 8;
            case 84:
                return (rc.readSharedArray(40) & 2) >>> 1;
            case 85:
                return (rc.readSharedArray(41) & 1024) >>> 10;
            case 86:
                return (rc.readSharedArray(41) & 8) >>> 3;
            case 87:
                return (rc.readSharedArray(42) & 4096) >>> 12;
            case 88:
                return (rc.readSharedArray(42) & 32) >>> 5;
            case 89:
                return (rc.readSharedArray(43) & 16384) >>> 14;
            case 90:
                return (rc.readSharedArray(43) & 128) >>> 7;
            case 91:
                return (rc.readSharedArray(43) & 1);
            case 92:
                return (rc.readSharedArray(44) & 512) >>> 9;
            case 93:
                return (rc.readSharedArray(44) & 4) >>> 2;
            case 94:
                return (rc.readSharedArray(45) & 2048) >>> 11;
            case 95:
                return (rc.readSharedArray(45) & 16) >>> 4;
            case 96:
                return (rc.readSharedArray(46) & 8192) >>> 13;
            case 97:
                return (rc.readSharedArray(46) & 64) >>> 6;
            case 98:
                return (rc.readSharedArray(47) & 32768) >>> 15;
            case 99:
                return (rc.readSharedArray(47) & 256) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 57343) | (value << 13));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65471) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 32767) | (value << 15));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65279) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65533) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 64511) | (value << 10));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65527) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 61439) | (value << 12));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65503) | (value << 5));
                break;
            case 9:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 49151) | (value << 14));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65407) | (value << 7));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65534) | (value));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65023) | (value << 9));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65531) | (value << 2));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 63487) | (value << 11));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65519) | (value << 4));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 57343) | (value << 13));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65471) | (value << 6));
                break;
            case 18:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 32767) | (value << 15));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65279) | (value << 8));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65533) | (value << 1));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 64511) | (value << 10));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65527) | (value << 3));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 61439) | (value << 12));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65503) | (value << 5));
                break;
            case 25:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 49151) | (value << 14));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65407) | (value << 7));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65534) | (value));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65023) | (value << 9));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65531) | (value << 2));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 63487) | (value << 11));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65519) | (value << 4));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 57343) | (value << 13));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65471) | (value << 6));
                break;
            case 34:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 32767) | (value << 15));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65279) | (value << 8));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65533) | (value << 1));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 64511) | (value << 10));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65527) | (value << 3));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 61439) | (value << 12));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65503) | (value << 5));
                break;
            case 41:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 49151) | (value << 14));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65407) | (value << 7));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65534) | (value));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65023) | (value << 9));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65531) | (value << 2));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 63487) | (value << 11));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65519) | (value << 4));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 57343) | (value << 13));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65471) | (value << 6));
                break;
            case 50:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 32767) | (value << 15));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65279) | (value << 8));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65533) | (value << 1));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 64511) | (value << 10));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65527) | (value << 3));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 61439) | (value << 12));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65503) | (value << 5));
                break;
            case 57:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 49151) | (value << 14));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65407) | (value << 7));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65534) | (value));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65023) | (value << 9));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65531) | (value << 2));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 63487) | (value << 11));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65519) | (value << 4));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 57343) | (value << 13));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65471) | (value << 6));
                break;
            case 66:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 32767) | (value << 15));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65279) | (value << 8));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65533) | (value << 1));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 64511) | (value << 10));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65527) | (value << 3));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 61439) | (value << 12));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65503) | (value << 5));
                break;
            case 73:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 49151) | (value << 14));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65407) | (value << 7));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65534) | (value));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65023) | (value << 9));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65531) | (value << 2));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 63487) | (value << 11));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65519) | (value << 4));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 57343) | (value << 13));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65471) | (value << 6));
                break;
            case 82:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 32767) | (value << 15));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65279) | (value << 8));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65533) | (value << 1));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 64511) | (value << 10));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65527) | (value << 3));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 61439) | (value << 12));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65503) | (value << 5));
                break;
            case 89:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 49151) | (value << 14));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65407) | (value << 7));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65534) | (value));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65023) | (value << 9));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65531) | (value << 2));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 63487) | (value << 11));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65519) | (value << 4));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 57343) | (value << 13));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65471) | (value << 6));
                break;
            case 98:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 32767) | (value << 15));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65279) | (value << 8));
                break;
        }
    }

    public static void writeBPSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 57343) | (value << 13));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65471) | (value << 6));
                break;
            case 2:
                writeToBufferPool(5, (bufferPool[5] & 32767) | (value << 15));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 65279) | (value << 8));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65533) | (value << 1));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 64511) | (value << 10));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65527) | (value << 3));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 61439) | (value << 12));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65503) | (value << 5));
                break;
            case 9:
                writeToBufferPool(8, (bufferPool[8] & 49151) | (value << 14));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65407) | (value << 7));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 65534) | (value));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 65023) | (value << 9));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 65531) | (value << 2));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 63487) | (value << 11));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65519) | (value << 4));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 57343) | (value << 13));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65471) | (value << 6));
                break;
            case 18:
                writeToBufferPool(12, (bufferPool[12] & 32767) | (value << 15));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 65279) | (value << 8));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 65533) | (value << 1));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 64511) | (value << 10));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65527) | (value << 3));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 61439) | (value << 12));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65503) | (value << 5));
                break;
            case 25:
                writeToBufferPool(15, (bufferPool[15] & 49151) | (value << 14));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65407) | (value << 7));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 65534) | (value));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 65023) | (value << 9));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 65531) | (value << 2));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 63487) | (value << 11));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65519) | (value << 4));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 57343) | (value << 13));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65471) | (value << 6));
                break;
            case 34:
                writeToBufferPool(19, (bufferPool[19] & 32767) | (value << 15));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 65279) | (value << 8));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 65533) | (value << 1));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 64511) | (value << 10));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65527) | (value << 3));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 61439) | (value << 12));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65503) | (value << 5));
                break;
            case 41:
                writeToBufferPool(22, (bufferPool[22] & 49151) | (value << 14));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65407) | (value << 7));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 65534) | (value));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 65023) | (value << 9));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 65531) | (value << 2));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 63487) | (value << 11));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65519) | (value << 4));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 57343) | (value << 13));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65471) | (value << 6));
                break;
            case 50:
                writeToBufferPool(26, (bufferPool[26] & 32767) | (value << 15));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 65279) | (value << 8));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 65533) | (value << 1));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 64511) | (value << 10));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65527) | (value << 3));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 61439) | (value << 12));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65503) | (value << 5));
                break;
            case 57:
                writeToBufferPool(29, (bufferPool[29] & 49151) | (value << 14));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65407) | (value << 7));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 65534) | (value));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 65023) | (value << 9));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 65531) | (value << 2));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 63487) | (value << 11));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65519) | (value << 4));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 57343) | (value << 13));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65471) | (value << 6));
                break;
            case 66:
                writeToBufferPool(33, (bufferPool[33] & 32767) | (value << 15));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 65279) | (value << 8));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 65533) | (value << 1));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 64511) | (value << 10));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65527) | (value << 3));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 61439) | (value << 12));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65503) | (value << 5));
                break;
            case 73:
                writeToBufferPool(36, (bufferPool[36] & 49151) | (value << 14));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65407) | (value << 7));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 65534) | (value));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 65023) | (value << 9));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 65531) | (value << 2));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 63487) | (value << 11));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65519) | (value << 4));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 57343) | (value << 13));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65471) | (value << 6));
                break;
            case 82:
                writeToBufferPool(40, (bufferPool[40] & 32767) | (value << 15));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 65279) | (value << 8));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 65533) | (value << 1));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 64511) | (value << 10));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65527) | (value << 3));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 61439) | (value << 12));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65503) | (value << 5));
                break;
            case 89:
                writeToBufferPool(43, (bufferPool[43] & 49151) | (value << 14));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65407) | (value << 7));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 65534) | (value));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 65023) | (value << 9));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 65531) | (value << 2));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 63487) | (value << 11));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65519) | (value << 4));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 57343) | (value << 13));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65471) | (value << 6));
                break;
            case 98:
                writeToBufferPool(47, (bufferPool[47] & 32767) | (value << 15));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 65279) | (value << 8));
                break;
        }
    }

    public static int readSectorElixirFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 4096) >>> 12;
            case 1:
                return (rc.readSharedArray(4) & 32) >>> 5;
            case 2:
                return (rc.readSharedArray(5) & 16384) >>> 14;
            case 3:
                return (rc.readSharedArray(5) & 128) >>> 7;
            case 4:
                return (rc.readSharedArray(5) & 1);
            case 5:
                return (rc.readSharedArray(6) & 512) >>> 9;
            case 6:
                return (rc.readSharedArray(6) & 4) >>> 2;
            case 7:
                return (rc.readSharedArray(7) & 2048) >>> 11;
            case 8:
                return (rc.readSharedArray(7) & 16) >>> 4;
            case 9:
                return (rc.readSharedArray(8) & 8192) >>> 13;
            case 10:
                return (rc.readSharedArray(8) & 64) >>> 6;
            case 11:
                return (rc.readSharedArray(9) & 32768) >>> 15;
            case 12:
                return (rc.readSharedArray(9) & 256) >>> 8;
            case 13:
                return (rc.readSharedArray(9) & 2) >>> 1;
            case 14:
                return (rc.readSharedArray(10) & 1024) >>> 10;
            case 15:
                return (rc.readSharedArray(10) & 8) >>> 3;
            case 16:
                return (rc.readSharedArray(11) & 4096) >>> 12;
            case 17:
                return (rc.readSharedArray(11) & 32) >>> 5;
            case 18:
                return (rc.readSharedArray(12) & 16384) >>> 14;
            case 19:
                return (rc.readSharedArray(12) & 128) >>> 7;
            case 20:
                return (rc.readSharedArray(12) & 1);
            case 21:
                return (rc.readSharedArray(13) & 512) >>> 9;
            case 22:
                return (rc.readSharedArray(13) & 4) >>> 2;
            case 23:
                return (rc.readSharedArray(14) & 2048) >>> 11;
            case 24:
                return (rc.readSharedArray(14) & 16) >>> 4;
            case 25:
                return (rc.readSharedArray(15) & 8192) >>> 13;
            case 26:
                return (rc.readSharedArray(15) & 64) >>> 6;
            case 27:
                return (rc.readSharedArray(16) & 32768) >>> 15;
            case 28:
                return (rc.readSharedArray(16) & 256) >>> 8;
            case 29:
                return (rc.readSharedArray(16) & 2) >>> 1;
            case 30:
                return (rc.readSharedArray(17) & 1024) >>> 10;
            case 31:
                return (rc.readSharedArray(17) & 8) >>> 3;
            case 32:
                return (rc.readSharedArray(18) & 4096) >>> 12;
            case 33:
                return (rc.readSharedArray(18) & 32) >>> 5;
            case 34:
                return (rc.readSharedArray(19) & 16384) >>> 14;
            case 35:
                return (rc.readSharedArray(19) & 128) >>> 7;
            case 36:
                return (rc.readSharedArray(19) & 1);
            case 37:
                return (rc.readSharedArray(20) & 512) >>> 9;
            case 38:
                return (rc.readSharedArray(20) & 4) >>> 2;
            case 39:
                return (rc.readSharedArray(21) & 2048) >>> 11;
            case 40:
                return (rc.readSharedArray(21) & 16) >>> 4;
            case 41:
                return (rc.readSharedArray(22) & 8192) >>> 13;
            case 42:
                return (rc.readSharedArray(22) & 64) >>> 6;
            case 43:
                return (rc.readSharedArray(23) & 32768) >>> 15;
            case 44:
                return (rc.readSharedArray(23) & 256) >>> 8;
            case 45:
                return (rc.readSharedArray(23) & 2) >>> 1;
            case 46:
                return (rc.readSharedArray(24) & 1024) >>> 10;
            case 47:
                return (rc.readSharedArray(24) & 8) >>> 3;
            case 48:
                return (rc.readSharedArray(25) & 4096) >>> 12;
            case 49:
                return (rc.readSharedArray(25) & 32) >>> 5;
            case 50:
                return (rc.readSharedArray(26) & 16384) >>> 14;
            case 51:
                return (rc.readSharedArray(26) & 128) >>> 7;
            case 52:
                return (rc.readSharedArray(26) & 1);
            case 53:
                return (rc.readSharedArray(27) & 512) >>> 9;
            case 54:
                return (rc.readSharedArray(27) & 4) >>> 2;
            case 55:
                return (rc.readSharedArray(28) & 2048) >>> 11;
            case 56:
                return (rc.readSharedArray(28) & 16) >>> 4;
            case 57:
                return (rc.readSharedArray(29) & 8192) >>> 13;
            case 58:
                return (rc.readSharedArray(29) & 64) >>> 6;
            case 59:
                return (rc.readSharedArray(30) & 32768) >>> 15;
            case 60:
                return (rc.readSharedArray(30) & 256) >>> 8;
            case 61:
                return (rc.readSharedArray(30) & 2) >>> 1;
            case 62:
                return (rc.readSharedArray(31) & 1024) >>> 10;
            case 63:
                return (rc.readSharedArray(31) & 8) >>> 3;
            case 64:
                return (rc.readSharedArray(32) & 4096) >>> 12;
            case 65:
                return (rc.readSharedArray(32) & 32) >>> 5;
            case 66:
                return (rc.readSharedArray(33) & 16384) >>> 14;
            case 67:
                return (rc.readSharedArray(33) & 128) >>> 7;
            case 68:
                return (rc.readSharedArray(33) & 1);
            case 69:
                return (rc.readSharedArray(34) & 512) >>> 9;
            case 70:
                return (rc.readSharedArray(34) & 4) >>> 2;
            case 71:
                return (rc.readSharedArray(35) & 2048) >>> 11;
            case 72:
                return (rc.readSharedArray(35) & 16) >>> 4;
            case 73:
                return (rc.readSharedArray(36) & 8192) >>> 13;
            case 74:
                return (rc.readSharedArray(36) & 64) >>> 6;
            case 75:
                return (rc.readSharedArray(37) & 32768) >>> 15;
            case 76:
                return (rc.readSharedArray(37) & 256) >>> 8;
            case 77:
                return (rc.readSharedArray(37) & 2) >>> 1;
            case 78:
                return (rc.readSharedArray(38) & 1024) >>> 10;
            case 79:
                return (rc.readSharedArray(38) & 8) >>> 3;
            case 80:
                return (rc.readSharedArray(39) & 4096) >>> 12;
            case 81:
                return (rc.readSharedArray(39) & 32) >>> 5;
            case 82:
                return (rc.readSharedArray(40) & 16384) >>> 14;
            case 83:
                return (rc.readSharedArray(40) & 128) >>> 7;
            case 84:
                return (rc.readSharedArray(40) & 1);
            case 85:
                return (rc.readSharedArray(41) & 512) >>> 9;
            case 86:
                return (rc.readSharedArray(41) & 4) >>> 2;
            case 87:
                return (rc.readSharedArray(42) & 2048) >>> 11;
            case 88:
                return (rc.readSharedArray(42) & 16) >>> 4;
            case 89:
                return (rc.readSharedArray(43) & 8192) >>> 13;
            case 90:
                return (rc.readSharedArray(43) & 64) >>> 6;
            case 91:
                return (rc.readSharedArray(44) & 32768) >>> 15;
            case 92:
                return (rc.readSharedArray(44) & 256) >>> 8;
            case 93:
                return (rc.readSharedArray(44) & 2) >>> 1;
            case 94:
                return (rc.readSharedArray(45) & 1024) >>> 10;
            case 95:
                return (rc.readSharedArray(45) & 8) >>> 3;
            case 96:
                return (rc.readSharedArray(46) & 4096) >>> 12;
            case 97:
                return (rc.readSharedArray(46) & 32) >>> 5;
            case 98:
                return (rc.readSharedArray(47) & 16384) >>> 14;
            case 99:
                return (rc.readSharedArray(47) & 128) >>> 7;
            default:
                return -1;
        }
    }

    public static void writeSectorElixirFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 61439) | (value << 12));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65503) | (value << 5));
                break;
            case 2:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 49151) | (value << 14));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65407) | (value << 7));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65534) | (value));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65023) | (value << 9));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65531) | (value << 2));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 63487) | (value << 11));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65519) | (value << 4));
                break;
            case 9:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 57343) | (value << 13));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65471) | (value << 6));
                break;
            case 11:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 32767) | (value << 15));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65279) | (value << 8));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65533) | (value << 1));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 64511) | (value << 10));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65527) | (value << 3));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 61439) | (value << 12));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65503) | (value << 5));
                break;
            case 18:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 49151) | (value << 14));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65407) | (value << 7));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65534) | (value));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65023) | (value << 9));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65531) | (value << 2));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 63487) | (value << 11));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65519) | (value << 4));
                break;
            case 25:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 57343) | (value << 13));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65471) | (value << 6));
                break;
            case 27:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 32767) | (value << 15));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65279) | (value << 8));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65533) | (value << 1));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 64511) | (value << 10));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65527) | (value << 3));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 61439) | (value << 12));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65503) | (value << 5));
                break;
            case 34:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 49151) | (value << 14));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65407) | (value << 7));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65534) | (value));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65023) | (value << 9));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65531) | (value << 2));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 63487) | (value << 11));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65519) | (value << 4));
                break;
            case 41:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 57343) | (value << 13));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65471) | (value << 6));
                break;
            case 43:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 32767) | (value << 15));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65279) | (value << 8));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65533) | (value << 1));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 64511) | (value << 10));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65527) | (value << 3));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 61439) | (value << 12));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65503) | (value << 5));
                break;
            case 50:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 49151) | (value << 14));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65407) | (value << 7));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65534) | (value));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65023) | (value << 9));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65531) | (value << 2));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 63487) | (value << 11));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65519) | (value << 4));
                break;
            case 57:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 57343) | (value << 13));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65471) | (value << 6));
                break;
            case 59:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 32767) | (value << 15));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65279) | (value << 8));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65533) | (value << 1));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 64511) | (value << 10));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65527) | (value << 3));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 61439) | (value << 12));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65503) | (value << 5));
                break;
            case 66:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 49151) | (value << 14));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65407) | (value << 7));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65534) | (value));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65023) | (value << 9));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65531) | (value << 2));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 63487) | (value << 11));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65519) | (value << 4));
                break;
            case 73:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 57343) | (value << 13));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65471) | (value << 6));
                break;
            case 75:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 32767) | (value << 15));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65279) | (value << 8));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65533) | (value << 1));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 64511) | (value << 10));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65527) | (value << 3));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 61439) | (value << 12));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65503) | (value << 5));
                break;
            case 82:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 49151) | (value << 14));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65407) | (value << 7));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65534) | (value));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65023) | (value << 9));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65531) | (value << 2));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 63487) | (value << 11));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65519) | (value << 4));
                break;
            case 89:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 57343) | (value << 13));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65471) | (value << 6));
                break;
            case 91:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 32767) | (value << 15));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65279) | (value << 8));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65533) | (value << 1));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 64511) | (value << 10));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65527) | (value << 3));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 61439) | (value << 12));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65503) | (value << 5));
                break;
            case 98:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 49151) | (value << 14));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65407) | (value << 7));
                break;
        }
    }

    public static void writeBPSectorElixirFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 61439) | (value << 12));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65503) | (value << 5));
                break;
            case 2:
                writeToBufferPool(5, (bufferPool[5] & 49151) | (value << 14));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 65407) | (value << 7));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65534) | (value));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 65023) | (value << 9));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65531) | (value << 2));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 63487) | (value << 11));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65519) | (value << 4));
                break;
            case 9:
                writeToBufferPool(8, (bufferPool[8] & 57343) | (value << 13));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65471) | (value << 6));
                break;
            case 11:
                writeToBufferPool(9, (bufferPool[9] & 32767) | (value << 15));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 65279) | (value << 8));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 65533) | (value << 1));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 64511) | (value << 10));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65527) | (value << 3));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 61439) | (value << 12));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65503) | (value << 5));
                break;
            case 18:
                writeToBufferPool(12, (bufferPool[12] & 49151) | (value << 14));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 65407) | (value << 7));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 65534) | (value));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 65023) | (value << 9));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65531) | (value << 2));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 63487) | (value << 11));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65519) | (value << 4));
                break;
            case 25:
                writeToBufferPool(15, (bufferPool[15] & 57343) | (value << 13));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65471) | (value << 6));
                break;
            case 27:
                writeToBufferPool(16, (bufferPool[16] & 32767) | (value << 15));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 65279) | (value << 8));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 65533) | (value << 1));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 64511) | (value << 10));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65527) | (value << 3));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 61439) | (value << 12));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65503) | (value << 5));
                break;
            case 34:
                writeToBufferPool(19, (bufferPool[19] & 49151) | (value << 14));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 65407) | (value << 7));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 65534) | (value));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 65023) | (value << 9));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65531) | (value << 2));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 63487) | (value << 11));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65519) | (value << 4));
                break;
            case 41:
                writeToBufferPool(22, (bufferPool[22] & 57343) | (value << 13));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65471) | (value << 6));
                break;
            case 43:
                writeToBufferPool(23, (bufferPool[23] & 32767) | (value << 15));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 65279) | (value << 8));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 65533) | (value << 1));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 64511) | (value << 10));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65527) | (value << 3));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 61439) | (value << 12));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65503) | (value << 5));
                break;
            case 50:
                writeToBufferPool(26, (bufferPool[26] & 49151) | (value << 14));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 65407) | (value << 7));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 65534) | (value));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 65023) | (value << 9));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65531) | (value << 2));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 63487) | (value << 11));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65519) | (value << 4));
                break;
            case 57:
                writeToBufferPool(29, (bufferPool[29] & 57343) | (value << 13));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65471) | (value << 6));
                break;
            case 59:
                writeToBufferPool(30, (bufferPool[30] & 32767) | (value << 15));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 65279) | (value << 8));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 65533) | (value << 1));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 64511) | (value << 10));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65527) | (value << 3));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 61439) | (value << 12));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65503) | (value << 5));
                break;
            case 66:
                writeToBufferPool(33, (bufferPool[33] & 49151) | (value << 14));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 65407) | (value << 7));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 65534) | (value));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 65023) | (value << 9));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65531) | (value << 2));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 63487) | (value << 11));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65519) | (value << 4));
                break;
            case 73:
                writeToBufferPool(36, (bufferPool[36] & 57343) | (value << 13));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65471) | (value << 6));
                break;
            case 75:
                writeToBufferPool(37, (bufferPool[37] & 32767) | (value << 15));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 65279) | (value << 8));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 65533) | (value << 1));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 64511) | (value << 10));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65527) | (value << 3));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 61439) | (value << 12));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65503) | (value << 5));
                break;
            case 82:
                writeToBufferPool(40, (bufferPool[40] & 49151) | (value << 14));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 65407) | (value << 7));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 65534) | (value));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 65023) | (value << 9));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65531) | (value << 2));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 63487) | (value << 11));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65519) | (value << 4));
                break;
            case 89:
                writeToBufferPool(43, (bufferPool[43] & 57343) | (value << 13));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65471) | (value << 6));
                break;
            case 91:
                writeToBufferPool(44, (bufferPool[44] & 32767) | (value << 15));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 65279) | (value << 8));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 65533) | (value << 1));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 64511) | (value << 10));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65527) | (value << 3));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 61439) | (value << 12));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65503) | (value << 5));
                break;
            case 98:
                writeToBufferPool(47, (bufferPool[47] & 49151) | (value << 14));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 65407) | (value << 7));
                break;
        }
    }

    public static int readSectorFriendlyIsland(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 2048) >>> 11;
            case 1:
                return (rc.readSharedArray(4) & 16) >>> 4;
            case 2:
                return (rc.readSharedArray(5) & 8192) >>> 13;
            case 3:
                return (rc.readSharedArray(5) & 64) >>> 6;
            case 4:
                return (rc.readSharedArray(6) & 32768) >>> 15;
            case 5:
                return (rc.readSharedArray(6) & 256) >>> 8;
            case 6:
                return (rc.readSharedArray(6) & 2) >>> 1;
            case 7:
                return (rc.readSharedArray(7) & 1024) >>> 10;
            case 8:
                return (rc.readSharedArray(7) & 8) >>> 3;
            case 9:
                return (rc.readSharedArray(8) & 4096) >>> 12;
            case 10:
                return (rc.readSharedArray(8) & 32) >>> 5;
            case 11:
                return (rc.readSharedArray(9) & 16384) >>> 14;
            case 12:
                return (rc.readSharedArray(9) & 128) >>> 7;
            case 13:
                return (rc.readSharedArray(9) & 1);
            case 14:
                return (rc.readSharedArray(10) & 512) >>> 9;
            case 15:
                return (rc.readSharedArray(10) & 4) >>> 2;
            case 16:
                return (rc.readSharedArray(11) & 2048) >>> 11;
            case 17:
                return (rc.readSharedArray(11) & 16) >>> 4;
            case 18:
                return (rc.readSharedArray(12) & 8192) >>> 13;
            case 19:
                return (rc.readSharedArray(12) & 64) >>> 6;
            case 20:
                return (rc.readSharedArray(13) & 32768) >>> 15;
            case 21:
                return (rc.readSharedArray(13) & 256) >>> 8;
            case 22:
                return (rc.readSharedArray(13) & 2) >>> 1;
            case 23:
                return (rc.readSharedArray(14) & 1024) >>> 10;
            case 24:
                return (rc.readSharedArray(14) & 8) >>> 3;
            case 25:
                return (rc.readSharedArray(15) & 4096) >>> 12;
            case 26:
                return (rc.readSharedArray(15) & 32) >>> 5;
            case 27:
                return (rc.readSharedArray(16) & 16384) >>> 14;
            case 28:
                return (rc.readSharedArray(16) & 128) >>> 7;
            case 29:
                return (rc.readSharedArray(16) & 1);
            case 30:
                return (rc.readSharedArray(17) & 512) >>> 9;
            case 31:
                return (rc.readSharedArray(17) & 4) >>> 2;
            case 32:
                return (rc.readSharedArray(18) & 2048) >>> 11;
            case 33:
                return (rc.readSharedArray(18) & 16) >>> 4;
            case 34:
                return (rc.readSharedArray(19) & 8192) >>> 13;
            case 35:
                return (rc.readSharedArray(19) & 64) >>> 6;
            case 36:
                return (rc.readSharedArray(20) & 32768) >>> 15;
            case 37:
                return (rc.readSharedArray(20) & 256) >>> 8;
            case 38:
                return (rc.readSharedArray(20) & 2) >>> 1;
            case 39:
                return (rc.readSharedArray(21) & 1024) >>> 10;
            case 40:
                return (rc.readSharedArray(21) & 8) >>> 3;
            case 41:
                return (rc.readSharedArray(22) & 4096) >>> 12;
            case 42:
                return (rc.readSharedArray(22) & 32) >>> 5;
            case 43:
                return (rc.readSharedArray(23) & 16384) >>> 14;
            case 44:
                return (rc.readSharedArray(23) & 128) >>> 7;
            case 45:
                return (rc.readSharedArray(23) & 1);
            case 46:
                return (rc.readSharedArray(24) & 512) >>> 9;
            case 47:
                return (rc.readSharedArray(24) & 4) >>> 2;
            case 48:
                return (rc.readSharedArray(25) & 2048) >>> 11;
            case 49:
                return (rc.readSharedArray(25) & 16) >>> 4;
            case 50:
                return (rc.readSharedArray(26) & 8192) >>> 13;
            case 51:
                return (rc.readSharedArray(26) & 64) >>> 6;
            case 52:
                return (rc.readSharedArray(27) & 32768) >>> 15;
            case 53:
                return (rc.readSharedArray(27) & 256) >>> 8;
            case 54:
                return (rc.readSharedArray(27) & 2) >>> 1;
            case 55:
                return (rc.readSharedArray(28) & 1024) >>> 10;
            case 56:
                return (rc.readSharedArray(28) & 8) >>> 3;
            case 57:
                return (rc.readSharedArray(29) & 4096) >>> 12;
            case 58:
                return (rc.readSharedArray(29) & 32) >>> 5;
            case 59:
                return (rc.readSharedArray(30) & 16384) >>> 14;
            case 60:
                return (rc.readSharedArray(30) & 128) >>> 7;
            case 61:
                return (rc.readSharedArray(30) & 1);
            case 62:
                return (rc.readSharedArray(31) & 512) >>> 9;
            case 63:
                return (rc.readSharedArray(31) & 4) >>> 2;
            case 64:
                return (rc.readSharedArray(32) & 2048) >>> 11;
            case 65:
                return (rc.readSharedArray(32) & 16) >>> 4;
            case 66:
                return (rc.readSharedArray(33) & 8192) >>> 13;
            case 67:
                return (rc.readSharedArray(33) & 64) >>> 6;
            case 68:
                return (rc.readSharedArray(34) & 32768) >>> 15;
            case 69:
                return (rc.readSharedArray(34) & 256) >>> 8;
            case 70:
                return (rc.readSharedArray(34) & 2) >>> 1;
            case 71:
                return (rc.readSharedArray(35) & 1024) >>> 10;
            case 72:
                return (rc.readSharedArray(35) & 8) >>> 3;
            case 73:
                return (rc.readSharedArray(36) & 4096) >>> 12;
            case 74:
                return (rc.readSharedArray(36) & 32) >>> 5;
            case 75:
                return (rc.readSharedArray(37) & 16384) >>> 14;
            case 76:
                return (rc.readSharedArray(37) & 128) >>> 7;
            case 77:
                return (rc.readSharedArray(37) & 1);
            case 78:
                return (rc.readSharedArray(38) & 512) >>> 9;
            case 79:
                return (rc.readSharedArray(38) & 4) >>> 2;
            case 80:
                return (rc.readSharedArray(39) & 2048) >>> 11;
            case 81:
                return (rc.readSharedArray(39) & 16) >>> 4;
            case 82:
                return (rc.readSharedArray(40) & 8192) >>> 13;
            case 83:
                return (rc.readSharedArray(40) & 64) >>> 6;
            case 84:
                return (rc.readSharedArray(41) & 32768) >>> 15;
            case 85:
                return (rc.readSharedArray(41) & 256) >>> 8;
            case 86:
                return (rc.readSharedArray(41) & 2) >>> 1;
            case 87:
                return (rc.readSharedArray(42) & 1024) >>> 10;
            case 88:
                return (rc.readSharedArray(42) & 8) >>> 3;
            case 89:
                return (rc.readSharedArray(43) & 4096) >>> 12;
            case 90:
                return (rc.readSharedArray(43) & 32) >>> 5;
            case 91:
                return (rc.readSharedArray(44) & 16384) >>> 14;
            case 92:
                return (rc.readSharedArray(44) & 128) >>> 7;
            case 93:
                return (rc.readSharedArray(44) & 1);
            case 94:
                return (rc.readSharedArray(45) & 512) >>> 9;
            case 95:
                return (rc.readSharedArray(45) & 4) >>> 2;
            case 96:
                return (rc.readSharedArray(46) & 2048) >>> 11;
            case 97:
                return (rc.readSharedArray(46) & 16) >>> 4;
            case 98:
                return (rc.readSharedArray(47) & 8192) >>> 13;
            case 99:
                return (rc.readSharedArray(47) & 64) >>> 6;
            default:
                return -1;
        }
    }

    public static void writeSectorFriendlyIsland(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 63487) | (value << 11));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65519) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 57343) | (value << 13));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65471) | (value << 6));
                break;
            case 4:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 32767) | (value << 15));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65279) | (value << 8));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65533) | (value << 1));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 64511) | (value << 10));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65527) | (value << 3));
                break;
            case 9:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 61439) | (value << 12));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65503) | (value << 5));
                break;
            case 11:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 49151) | (value << 14));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65407) | (value << 7));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65534) | (value));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65023) | (value << 9));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65531) | (value << 2));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 63487) | (value << 11));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65519) | (value << 4));
                break;
            case 18:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 57343) | (value << 13));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65471) | (value << 6));
                break;
            case 20:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 32767) | (value << 15));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65279) | (value << 8));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65533) | (value << 1));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 64511) | (value << 10));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65527) | (value << 3));
                break;
            case 25:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 61439) | (value << 12));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65503) | (value << 5));
                break;
            case 27:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 49151) | (value << 14));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65407) | (value << 7));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65534) | (value));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65023) | (value << 9));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65531) | (value << 2));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 63487) | (value << 11));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65519) | (value << 4));
                break;
            case 34:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 57343) | (value << 13));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65471) | (value << 6));
                break;
            case 36:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 32767) | (value << 15));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65279) | (value << 8));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65533) | (value << 1));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 64511) | (value << 10));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65527) | (value << 3));
                break;
            case 41:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 61439) | (value << 12));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65503) | (value << 5));
                break;
            case 43:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 49151) | (value << 14));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65407) | (value << 7));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65534) | (value));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65023) | (value << 9));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65531) | (value << 2));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 63487) | (value << 11));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65519) | (value << 4));
                break;
            case 50:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 57343) | (value << 13));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65471) | (value << 6));
                break;
            case 52:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 32767) | (value << 15));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65279) | (value << 8));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65533) | (value << 1));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 64511) | (value << 10));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65527) | (value << 3));
                break;
            case 57:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 61439) | (value << 12));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65503) | (value << 5));
                break;
            case 59:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 49151) | (value << 14));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65407) | (value << 7));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65534) | (value));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65023) | (value << 9));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65531) | (value << 2));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 63487) | (value << 11));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65519) | (value << 4));
                break;
            case 66:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 57343) | (value << 13));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65471) | (value << 6));
                break;
            case 68:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 32767) | (value << 15));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65279) | (value << 8));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65533) | (value << 1));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 64511) | (value << 10));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65527) | (value << 3));
                break;
            case 73:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 61439) | (value << 12));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65503) | (value << 5));
                break;
            case 75:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 49151) | (value << 14));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65407) | (value << 7));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65534) | (value));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65023) | (value << 9));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65531) | (value << 2));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 63487) | (value << 11));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65519) | (value << 4));
                break;
            case 82:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 57343) | (value << 13));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65471) | (value << 6));
                break;
            case 84:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 32767) | (value << 15));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65279) | (value << 8));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65533) | (value << 1));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 64511) | (value << 10));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65527) | (value << 3));
                break;
            case 89:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 61439) | (value << 12));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65503) | (value << 5));
                break;
            case 91:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 49151) | (value << 14));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65407) | (value << 7));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65534) | (value));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65023) | (value << 9));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65531) | (value << 2));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63487) | (value << 11));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65519) | (value << 4));
                break;
            case 98:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 57343) | (value << 13));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65471) | (value << 6));
                break;
        }
    }

    public static void writeBPSectorFriendlyIsland(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 63487) | (value << 11));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65519) | (value << 4));
                break;
            case 2:
                writeToBufferPool(5, (bufferPool[5] & 57343) | (value << 13));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 65471) | (value << 6));
                break;
            case 4:
                writeToBufferPool(6, (bufferPool[6] & 32767) | (value << 15));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 65279) | (value << 8));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65533) | (value << 1));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 64511) | (value << 10));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65527) | (value << 3));
                break;
            case 9:
                writeToBufferPool(8, (bufferPool[8] & 61439) | (value << 12));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65503) | (value << 5));
                break;
            case 11:
                writeToBufferPool(9, (bufferPool[9] & 49151) | (value << 14));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 65407) | (value << 7));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 65534) | (value));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 65023) | (value << 9));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65531) | (value << 2));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 63487) | (value << 11));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65519) | (value << 4));
                break;
            case 18:
                writeToBufferPool(12, (bufferPool[12] & 57343) | (value << 13));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 65471) | (value << 6));
                break;
            case 20:
                writeToBufferPool(13, (bufferPool[13] & 32767) | (value << 15));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 65279) | (value << 8));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65533) | (value << 1));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 64511) | (value << 10));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65527) | (value << 3));
                break;
            case 25:
                writeToBufferPool(15, (bufferPool[15] & 61439) | (value << 12));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65503) | (value << 5));
                break;
            case 27:
                writeToBufferPool(16, (bufferPool[16] & 49151) | (value << 14));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 65407) | (value << 7));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 65534) | (value));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 65023) | (value << 9));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65531) | (value << 2));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 63487) | (value << 11));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65519) | (value << 4));
                break;
            case 34:
                writeToBufferPool(19, (bufferPool[19] & 57343) | (value << 13));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 65471) | (value << 6));
                break;
            case 36:
                writeToBufferPool(20, (bufferPool[20] & 32767) | (value << 15));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 65279) | (value << 8));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65533) | (value << 1));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 64511) | (value << 10));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65527) | (value << 3));
                break;
            case 41:
                writeToBufferPool(22, (bufferPool[22] & 61439) | (value << 12));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65503) | (value << 5));
                break;
            case 43:
                writeToBufferPool(23, (bufferPool[23] & 49151) | (value << 14));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 65407) | (value << 7));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 65534) | (value));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 65023) | (value << 9));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65531) | (value << 2));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 63487) | (value << 11));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65519) | (value << 4));
                break;
            case 50:
                writeToBufferPool(26, (bufferPool[26] & 57343) | (value << 13));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 65471) | (value << 6));
                break;
            case 52:
                writeToBufferPool(27, (bufferPool[27] & 32767) | (value << 15));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 65279) | (value << 8));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65533) | (value << 1));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 64511) | (value << 10));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65527) | (value << 3));
                break;
            case 57:
                writeToBufferPool(29, (bufferPool[29] & 61439) | (value << 12));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65503) | (value << 5));
                break;
            case 59:
                writeToBufferPool(30, (bufferPool[30] & 49151) | (value << 14));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 65407) | (value << 7));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 65534) | (value));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 65023) | (value << 9));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65531) | (value << 2));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 63487) | (value << 11));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65519) | (value << 4));
                break;
            case 66:
                writeToBufferPool(33, (bufferPool[33] & 57343) | (value << 13));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 65471) | (value << 6));
                break;
            case 68:
                writeToBufferPool(34, (bufferPool[34] & 32767) | (value << 15));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 65279) | (value << 8));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65533) | (value << 1));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 64511) | (value << 10));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65527) | (value << 3));
                break;
            case 73:
                writeToBufferPool(36, (bufferPool[36] & 61439) | (value << 12));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65503) | (value << 5));
                break;
            case 75:
                writeToBufferPool(37, (bufferPool[37] & 49151) | (value << 14));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 65407) | (value << 7));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 65534) | (value));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 65023) | (value << 9));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65531) | (value << 2));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 63487) | (value << 11));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65519) | (value << 4));
                break;
            case 82:
                writeToBufferPool(40, (bufferPool[40] & 57343) | (value << 13));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 65471) | (value << 6));
                break;
            case 84:
                writeToBufferPool(41, (bufferPool[41] & 32767) | (value << 15));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 65279) | (value << 8));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65533) | (value << 1));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 64511) | (value << 10));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65527) | (value << 3));
                break;
            case 89:
                writeToBufferPool(43, (bufferPool[43] & 61439) | (value << 12));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65503) | (value << 5));
                break;
            case 91:
                writeToBufferPool(44, (bufferPool[44] & 49151) | (value << 14));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 65407) | (value << 7));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 65534) | (value));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 65023) | (value << 9));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65531) | (value << 2));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 63487) | (value << 11));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65519) | (value << 4));
                break;
            case 98:
                writeToBufferPool(47, (bufferPool[47] & 57343) | (value << 13));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 65471) | (value << 6));
                break;
        }
    }

    public static int readSectorEnemyIsland(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 1024) >>> 10;
            case 1:
                return (rc.readSharedArray(4) & 8) >>> 3;
            case 2:
                return (rc.readSharedArray(5) & 4096) >>> 12;
            case 3:
                return (rc.readSharedArray(5) & 32) >>> 5;
            case 4:
                return (rc.readSharedArray(6) & 16384) >>> 14;
            case 5:
                return (rc.readSharedArray(6) & 128) >>> 7;
            case 6:
                return (rc.readSharedArray(6) & 1);
            case 7:
                return (rc.readSharedArray(7) & 512) >>> 9;
            case 8:
                return (rc.readSharedArray(7) & 4) >>> 2;
            case 9:
                return (rc.readSharedArray(8) & 2048) >>> 11;
            case 10:
                return (rc.readSharedArray(8) & 16) >>> 4;
            case 11:
                return (rc.readSharedArray(9) & 8192) >>> 13;
            case 12:
                return (rc.readSharedArray(9) & 64) >>> 6;
            case 13:
                return (rc.readSharedArray(10) & 32768) >>> 15;
            case 14:
                return (rc.readSharedArray(10) & 256) >>> 8;
            case 15:
                return (rc.readSharedArray(10) & 2) >>> 1;
            case 16:
                return (rc.readSharedArray(11) & 1024) >>> 10;
            case 17:
                return (rc.readSharedArray(11) & 8) >>> 3;
            case 18:
                return (rc.readSharedArray(12) & 4096) >>> 12;
            case 19:
                return (rc.readSharedArray(12) & 32) >>> 5;
            case 20:
                return (rc.readSharedArray(13) & 16384) >>> 14;
            case 21:
                return (rc.readSharedArray(13) & 128) >>> 7;
            case 22:
                return (rc.readSharedArray(13) & 1);
            case 23:
                return (rc.readSharedArray(14) & 512) >>> 9;
            case 24:
                return (rc.readSharedArray(14) & 4) >>> 2;
            case 25:
                return (rc.readSharedArray(15) & 2048) >>> 11;
            case 26:
                return (rc.readSharedArray(15) & 16) >>> 4;
            case 27:
                return (rc.readSharedArray(16) & 8192) >>> 13;
            case 28:
                return (rc.readSharedArray(16) & 64) >>> 6;
            case 29:
                return (rc.readSharedArray(17) & 32768) >>> 15;
            case 30:
                return (rc.readSharedArray(17) & 256) >>> 8;
            case 31:
                return (rc.readSharedArray(17) & 2) >>> 1;
            case 32:
                return (rc.readSharedArray(18) & 1024) >>> 10;
            case 33:
                return (rc.readSharedArray(18) & 8) >>> 3;
            case 34:
                return (rc.readSharedArray(19) & 4096) >>> 12;
            case 35:
                return (rc.readSharedArray(19) & 32) >>> 5;
            case 36:
                return (rc.readSharedArray(20) & 16384) >>> 14;
            case 37:
                return (rc.readSharedArray(20) & 128) >>> 7;
            case 38:
                return (rc.readSharedArray(20) & 1);
            case 39:
                return (rc.readSharedArray(21) & 512) >>> 9;
            case 40:
                return (rc.readSharedArray(21) & 4) >>> 2;
            case 41:
                return (rc.readSharedArray(22) & 2048) >>> 11;
            case 42:
                return (rc.readSharedArray(22) & 16) >>> 4;
            case 43:
                return (rc.readSharedArray(23) & 8192) >>> 13;
            case 44:
                return (rc.readSharedArray(23) & 64) >>> 6;
            case 45:
                return (rc.readSharedArray(24) & 32768) >>> 15;
            case 46:
                return (rc.readSharedArray(24) & 256) >>> 8;
            case 47:
                return (rc.readSharedArray(24) & 2) >>> 1;
            case 48:
                return (rc.readSharedArray(25) & 1024) >>> 10;
            case 49:
                return (rc.readSharedArray(25) & 8) >>> 3;
            case 50:
                return (rc.readSharedArray(26) & 4096) >>> 12;
            case 51:
                return (rc.readSharedArray(26) & 32) >>> 5;
            case 52:
                return (rc.readSharedArray(27) & 16384) >>> 14;
            case 53:
                return (rc.readSharedArray(27) & 128) >>> 7;
            case 54:
                return (rc.readSharedArray(27) & 1);
            case 55:
                return (rc.readSharedArray(28) & 512) >>> 9;
            case 56:
                return (rc.readSharedArray(28) & 4) >>> 2;
            case 57:
                return (rc.readSharedArray(29) & 2048) >>> 11;
            case 58:
                return (rc.readSharedArray(29) & 16) >>> 4;
            case 59:
                return (rc.readSharedArray(30) & 8192) >>> 13;
            case 60:
                return (rc.readSharedArray(30) & 64) >>> 6;
            case 61:
                return (rc.readSharedArray(31) & 32768) >>> 15;
            case 62:
                return (rc.readSharedArray(31) & 256) >>> 8;
            case 63:
                return (rc.readSharedArray(31) & 2) >>> 1;
            case 64:
                return (rc.readSharedArray(32) & 1024) >>> 10;
            case 65:
                return (rc.readSharedArray(32) & 8) >>> 3;
            case 66:
                return (rc.readSharedArray(33) & 4096) >>> 12;
            case 67:
                return (rc.readSharedArray(33) & 32) >>> 5;
            case 68:
                return (rc.readSharedArray(34) & 16384) >>> 14;
            case 69:
                return (rc.readSharedArray(34) & 128) >>> 7;
            case 70:
                return (rc.readSharedArray(34) & 1);
            case 71:
                return (rc.readSharedArray(35) & 512) >>> 9;
            case 72:
                return (rc.readSharedArray(35) & 4) >>> 2;
            case 73:
                return (rc.readSharedArray(36) & 2048) >>> 11;
            case 74:
                return (rc.readSharedArray(36) & 16) >>> 4;
            case 75:
                return (rc.readSharedArray(37) & 8192) >>> 13;
            case 76:
                return (rc.readSharedArray(37) & 64) >>> 6;
            case 77:
                return (rc.readSharedArray(38) & 32768) >>> 15;
            case 78:
                return (rc.readSharedArray(38) & 256) >>> 8;
            case 79:
                return (rc.readSharedArray(38) & 2) >>> 1;
            case 80:
                return (rc.readSharedArray(39) & 1024) >>> 10;
            case 81:
                return (rc.readSharedArray(39) & 8) >>> 3;
            case 82:
                return (rc.readSharedArray(40) & 4096) >>> 12;
            case 83:
                return (rc.readSharedArray(40) & 32) >>> 5;
            case 84:
                return (rc.readSharedArray(41) & 16384) >>> 14;
            case 85:
                return (rc.readSharedArray(41) & 128) >>> 7;
            case 86:
                return (rc.readSharedArray(41) & 1);
            case 87:
                return (rc.readSharedArray(42) & 512) >>> 9;
            case 88:
                return (rc.readSharedArray(42) & 4) >>> 2;
            case 89:
                return (rc.readSharedArray(43) & 2048) >>> 11;
            case 90:
                return (rc.readSharedArray(43) & 16) >>> 4;
            case 91:
                return (rc.readSharedArray(44) & 8192) >>> 13;
            case 92:
                return (rc.readSharedArray(44) & 64) >>> 6;
            case 93:
                return (rc.readSharedArray(45) & 32768) >>> 15;
            case 94:
                return (rc.readSharedArray(45) & 256) >>> 8;
            case 95:
                return (rc.readSharedArray(45) & 2) >>> 1;
            case 96:
                return (rc.readSharedArray(46) & 1024) >>> 10;
            case 97:
                return (rc.readSharedArray(46) & 8) >>> 3;
            case 98:
                return (rc.readSharedArray(47) & 4096) >>> 12;
            case 99:
                return (rc.readSharedArray(47) & 32) >>> 5;
            default:
                return -1;
        }
    }

    public static void writeSectorEnemyIsland(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 64511) | (value << 10));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65527) | (value << 3));
                break;
            case 2:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 61439) | (value << 12));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65503) | (value << 5));
                break;
            case 4:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 49151) | (value << 14));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65407) | (value << 7));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65534) | (value));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65023) | (value << 9));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65531) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63487) | (value << 11));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65519) | (value << 4));
                break;
            case 11:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 57343) | (value << 13));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65471) | (value << 6));
                break;
            case 13:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 32767) | (value << 15));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65279) | (value << 8));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65533) | (value << 1));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 64511) | (value << 10));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65527) | (value << 3));
                break;
            case 18:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 61439) | (value << 12));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65503) | (value << 5));
                break;
            case 20:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 49151) | (value << 14));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65407) | (value << 7));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65534) | (value));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65023) | (value << 9));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65531) | (value << 2));
                break;
            case 25:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 63487) | (value << 11));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65519) | (value << 4));
                break;
            case 27:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 57343) | (value << 13));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65471) | (value << 6));
                break;
            case 29:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 32767) | (value << 15));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65279) | (value << 8));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65533) | (value << 1));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 64511) | (value << 10));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65527) | (value << 3));
                break;
            case 34:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 61439) | (value << 12));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65503) | (value << 5));
                break;
            case 36:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 49151) | (value << 14));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65407) | (value << 7));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65534) | (value));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65023) | (value << 9));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65531) | (value << 2));
                break;
            case 41:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 63487) | (value << 11));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65519) | (value << 4));
                break;
            case 43:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 57343) | (value << 13));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65471) | (value << 6));
                break;
            case 45:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 32767) | (value << 15));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65279) | (value << 8));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65533) | (value << 1));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 64511) | (value << 10));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65527) | (value << 3));
                break;
            case 50:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 61439) | (value << 12));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65503) | (value << 5));
                break;
            case 52:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 49151) | (value << 14));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65407) | (value << 7));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65534) | (value));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65023) | (value << 9));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65531) | (value << 2));
                break;
            case 57:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63487) | (value << 11));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65519) | (value << 4));
                break;
            case 59:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 57343) | (value << 13));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65471) | (value << 6));
                break;
            case 61:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 32767) | (value << 15));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65279) | (value << 8));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65533) | (value << 1));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 64511) | (value << 10));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65527) | (value << 3));
                break;
            case 66:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 61439) | (value << 12));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65503) | (value << 5));
                break;
            case 68:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 49151) | (value << 14));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65407) | (value << 7));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65534) | (value));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65023) | (value << 9));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65531) | (value << 2));
                break;
            case 73:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 63487) | (value << 11));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65519) | (value << 4));
                break;
            case 75:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 57343) | (value << 13));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65471) | (value << 6));
                break;
            case 77:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 32767) | (value << 15));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65279) | (value << 8));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65533) | (value << 1));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 64511) | (value << 10));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65527) | (value << 3));
                break;
            case 82:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 61439) | (value << 12));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65503) | (value << 5));
                break;
            case 84:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 49151) | (value << 14));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65407) | (value << 7));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65534) | (value));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65023) | (value << 9));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65531) | (value << 2));
                break;
            case 89:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63487) | (value << 11));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65519) | (value << 4));
                break;
            case 91:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 57343) | (value << 13));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65471) | (value << 6));
                break;
            case 93:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 32767) | (value << 15));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65279) | (value << 8));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65533) | (value << 1));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 64511) | (value << 10));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65527) | (value << 3));
                break;
            case 98:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 61439) | (value << 12));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65503) | (value << 5));
                break;
        }
    }

    public static void writeBPSectorEnemyIsland(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 64511) | (value << 10));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65527) | (value << 3));
                break;
            case 2:
                writeToBufferPool(5, (bufferPool[5] & 61439) | (value << 12));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 65503) | (value << 5));
                break;
            case 4:
                writeToBufferPool(6, (bufferPool[6] & 49151) | (value << 14));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 65407) | (value << 7));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65534) | (value));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 65023) | (value << 9));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65531) | (value << 2));
                break;
            case 9:
                writeToBufferPool(8, (bufferPool[8] & 63487) | (value << 11));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65519) | (value << 4));
                break;
            case 11:
                writeToBufferPool(9, (bufferPool[9] & 57343) | (value << 13));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 65471) | (value << 6));
                break;
            case 13:
                writeToBufferPool(10, (bufferPool[10] & 32767) | (value << 15));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 65279) | (value << 8));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65533) | (value << 1));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 64511) | (value << 10));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65527) | (value << 3));
                break;
            case 18:
                writeToBufferPool(12, (bufferPool[12] & 61439) | (value << 12));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 65503) | (value << 5));
                break;
            case 20:
                writeToBufferPool(13, (bufferPool[13] & 49151) | (value << 14));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 65407) | (value << 7));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65534) | (value));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 65023) | (value << 9));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65531) | (value << 2));
                break;
            case 25:
                writeToBufferPool(15, (bufferPool[15] & 63487) | (value << 11));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65519) | (value << 4));
                break;
            case 27:
                writeToBufferPool(16, (bufferPool[16] & 57343) | (value << 13));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 65471) | (value << 6));
                break;
            case 29:
                writeToBufferPool(17, (bufferPool[17] & 32767) | (value << 15));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 65279) | (value << 8));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65533) | (value << 1));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 64511) | (value << 10));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65527) | (value << 3));
                break;
            case 34:
                writeToBufferPool(19, (bufferPool[19] & 61439) | (value << 12));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 65503) | (value << 5));
                break;
            case 36:
                writeToBufferPool(20, (bufferPool[20] & 49151) | (value << 14));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 65407) | (value << 7));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65534) | (value));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 65023) | (value << 9));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65531) | (value << 2));
                break;
            case 41:
                writeToBufferPool(22, (bufferPool[22] & 63487) | (value << 11));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65519) | (value << 4));
                break;
            case 43:
                writeToBufferPool(23, (bufferPool[23] & 57343) | (value << 13));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 65471) | (value << 6));
                break;
            case 45:
                writeToBufferPool(24, (bufferPool[24] & 32767) | (value << 15));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 65279) | (value << 8));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65533) | (value << 1));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 64511) | (value << 10));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65527) | (value << 3));
                break;
            case 50:
                writeToBufferPool(26, (bufferPool[26] & 61439) | (value << 12));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 65503) | (value << 5));
                break;
            case 52:
                writeToBufferPool(27, (bufferPool[27] & 49151) | (value << 14));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 65407) | (value << 7));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65534) | (value));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 65023) | (value << 9));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65531) | (value << 2));
                break;
            case 57:
                writeToBufferPool(29, (bufferPool[29] & 63487) | (value << 11));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65519) | (value << 4));
                break;
            case 59:
                writeToBufferPool(30, (bufferPool[30] & 57343) | (value << 13));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 65471) | (value << 6));
                break;
            case 61:
                writeToBufferPool(31, (bufferPool[31] & 32767) | (value << 15));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 65279) | (value << 8));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65533) | (value << 1));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 64511) | (value << 10));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65527) | (value << 3));
                break;
            case 66:
                writeToBufferPool(33, (bufferPool[33] & 61439) | (value << 12));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 65503) | (value << 5));
                break;
            case 68:
                writeToBufferPool(34, (bufferPool[34] & 49151) | (value << 14));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 65407) | (value << 7));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65534) | (value));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 65023) | (value << 9));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65531) | (value << 2));
                break;
            case 73:
                writeToBufferPool(36, (bufferPool[36] & 63487) | (value << 11));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65519) | (value << 4));
                break;
            case 75:
                writeToBufferPool(37, (bufferPool[37] & 57343) | (value << 13));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 65471) | (value << 6));
                break;
            case 77:
                writeToBufferPool(38, (bufferPool[38] & 32767) | (value << 15));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 65279) | (value << 8));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65533) | (value << 1));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 64511) | (value << 10));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65527) | (value << 3));
                break;
            case 82:
                writeToBufferPool(40, (bufferPool[40] & 61439) | (value << 12));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 65503) | (value << 5));
                break;
            case 84:
                writeToBufferPool(41, (bufferPool[41] & 49151) | (value << 14));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 65407) | (value << 7));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65534) | (value));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 65023) | (value << 9));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65531) | (value << 2));
                break;
            case 89:
                writeToBufferPool(43, (bufferPool[43] & 63487) | (value << 11));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65519) | (value << 4));
                break;
            case 91:
                writeToBufferPool(44, (bufferPool[44] & 57343) | (value << 13));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 65471) | (value << 6));
                break;
            case 93:
                writeToBufferPool(45, (bufferPool[45] & 32767) | (value << 15));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 65279) | (value << 8));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65533) | (value << 1));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 64511) | (value << 10));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65527) | (value << 3));
                break;
            case 98:
                writeToBufferPool(47, (bufferPool[47] & 61439) | (value << 12));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 65503) | (value << 5));
                break;
        }
    }

    public static int readSectorNeutralIsland(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 512) >>> 9;
            case 1:
                return (rc.readSharedArray(4) & 4) >>> 2;
            case 2:
                return (rc.readSharedArray(5) & 2048) >>> 11;
            case 3:
                return (rc.readSharedArray(5) & 16) >>> 4;
            case 4:
                return (rc.readSharedArray(6) & 8192) >>> 13;
            case 5:
                return (rc.readSharedArray(6) & 64) >>> 6;
            case 6:
                return (rc.readSharedArray(7) & 32768) >>> 15;
            case 7:
                return (rc.readSharedArray(7) & 256) >>> 8;
            case 8:
                return (rc.readSharedArray(7) & 2) >>> 1;
            case 9:
                return (rc.readSharedArray(8) & 1024) >>> 10;
            case 10:
                return (rc.readSharedArray(8) & 8) >>> 3;
            case 11:
                return (rc.readSharedArray(9) & 4096) >>> 12;
            case 12:
                return (rc.readSharedArray(9) & 32) >>> 5;
            case 13:
                return (rc.readSharedArray(10) & 16384) >>> 14;
            case 14:
                return (rc.readSharedArray(10) & 128) >>> 7;
            case 15:
                return (rc.readSharedArray(10) & 1);
            case 16:
                return (rc.readSharedArray(11) & 512) >>> 9;
            case 17:
                return (rc.readSharedArray(11) & 4) >>> 2;
            case 18:
                return (rc.readSharedArray(12) & 2048) >>> 11;
            case 19:
                return (rc.readSharedArray(12) & 16) >>> 4;
            case 20:
                return (rc.readSharedArray(13) & 8192) >>> 13;
            case 21:
                return (rc.readSharedArray(13) & 64) >>> 6;
            case 22:
                return (rc.readSharedArray(14) & 32768) >>> 15;
            case 23:
                return (rc.readSharedArray(14) & 256) >>> 8;
            case 24:
                return (rc.readSharedArray(14) & 2) >>> 1;
            case 25:
                return (rc.readSharedArray(15) & 1024) >>> 10;
            case 26:
                return (rc.readSharedArray(15) & 8) >>> 3;
            case 27:
                return (rc.readSharedArray(16) & 4096) >>> 12;
            case 28:
                return (rc.readSharedArray(16) & 32) >>> 5;
            case 29:
                return (rc.readSharedArray(17) & 16384) >>> 14;
            case 30:
                return (rc.readSharedArray(17) & 128) >>> 7;
            case 31:
                return (rc.readSharedArray(17) & 1);
            case 32:
                return (rc.readSharedArray(18) & 512) >>> 9;
            case 33:
                return (rc.readSharedArray(18) & 4) >>> 2;
            case 34:
                return (rc.readSharedArray(19) & 2048) >>> 11;
            case 35:
                return (rc.readSharedArray(19) & 16) >>> 4;
            case 36:
                return (rc.readSharedArray(20) & 8192) >>> 13;
            case 37:
                return (rc.readSharedArray(20) & 64) >>> 6;
            case 38:
                return (rc.readSharedArray(21) & 32768) >>> 15;
            case 39:
                return (rc.readSharedArray(21) & 256) >>> 8;
            case 40:
                return (rc.readSharedArray(21) & 2) >>> 1;
            case 41:
                return (rc.readSharedArray(22) & 1024) >>> 10;
            case 42:
                return (rc.readSharedArray(22) & 8) >>> 3;
            case 43:
                return (rc.readSharedArray(23) & 4096) >>> 12;
            case 44:
                return (rc.readSharedArray(23) & 32) >>> 5;
            case 45:
                return (rc.readSharedArray(24) & 16384) >>> 14;
            case 46:
                return (rc.readSharedArray(24) & 128) >>> 7;
            case 47:
                return (rc.readSharedArray(24) & 1);
            case 48:
                return (rc.readSharedArray(25) & 512) >>> 9;
            case 49:
                return (rc.readSharedArray(25) & 4) >>> 2;
            case 50:
                return (rc.readSharedArray(26) & 2048) >>> 11;
            case 51:
                return (rc.readSharedArray(26) & 16) >>> 4;
            case 52:
                return (rc.readSharedArray(27) & 8192) >>> 13;
            case 53:
                return (rc.readSharedArray(27) & 64) >>> 6;
            case 54:
                return (rc.readSharedArray(28) & 32768) >>> 15;
            case 55:
                return (rc.readSharedArray(28) & 256) >>> 8;
            case 56:
                return (rc.readSharedArray(28) & 2) >>> 1;
            case 57:
                return (rc.readSharedArray(29) & 1024) >>> 10;
            case 58:
                return (rc.readSharedArray(29) & 8) >>> 3;
            case 59:
                return (rc.readSharedArray(30) & 4096) >>> 12;
            case 60:
                return (rc.readSharedArray(30) & 32) >>> 5;
            case 61:
                return (rc.readSharedArray(31) & 16384) >>> 14;
            case 62:
                return (rc.readSharedArray(31) & 128) >>> 7;
            case 63:
                return (rc.readSharedArray(31) & 1);
            case 64:
                return (rc.readSharedArray(32) & 512) >>> 9;
            case 65:
                return (rc.readSharedArray(32) & 4) >>> 2;
            case 66:
                return (rc.readSharedArray(33) & 2048) >>> 11;
            case 67:
                return (rc.readSharedArray(33) & 16) >>> 4;
            case 68:
                return (rc.readSharedArray(34) & 8192) >>> 13;
            case 69:
                return (rc.readSharedArray(34) & 64) >>> 6;
            case 70:
                return (rc.readSharedArray(35) & 32768) >>> 15;
            case 71:
                return (rc.readSharedArray(35) & 256) >>> 8;
            case 72:
                return (rc.readSharedArray(35) & 2) >>> 1;
            case 73:
                return (rc.readSharedArray(36) & 1024) >>> 10;
            case 74:
                return (rc.readSharedArray(36) & 8) >>> 3;
            case 75:
                return (rc.readSharedArray(37) & 4096) >>> 12;
            case 76:
                return (rc.readSharedArray(37) & 32) >>> 5;
            case 77:
                return (rc.readSharedArray(38) & 16384) >>> 14;
            case 78:
                return (rc.readSharedArray(38) & 128) >>> 7;
            case 79:
                return (rc.readSharedArray(38) & 1);
            case 80:
                return (rc.readSharedArray(39) & 512) >>> 9;
            case 81:
                return (rc.readSharedArray(39) & 4) >>> 2;
            case 82:
                return (rc.readSharedArray(40) & 2048) >>> 11;
            case 83:
                return (rc.readSharedArray(40) & 16) >>> 4;
            case 84:
                return (rc.readSharedArray(41) & 8192) >>> 13;
            case 85:
                return (rc.readSharedArray(41) & 64) >>> 6;
            case 86:
                return (rc.readSharedArray(42) & 32768) >>> 15;
            case 87:
                return (rc.readSharedArray(42) & 256) >>> 8;
            case 88:
                return (rc.readSharedArray(42) & 2) >>> 1;
            case 89:
                return (rc.readSharedArray(43) & 1024) >>> 10;
            case 90:
                return (rc.readSharedArray(43) & 8) >>> 3;
            case 91:
                return (rc.readSharedArray(44) & 4096) >>> 12;
            case 92:
                return (rc.readSharedArray(44) & 32) >>> 5;
            case 93:
                return (rc.readSharedArray(45) & 16384) >>> 14;
            case 94:
                return (rc.readSharedArray(45) & 128) >>> 7;
            case 95:
                return (rc.readSharedArray(45) & 1);
            case 96:
                return (rc.readSharedArray(46) & 512) >>> 9;
            case 97:
                return (rc.readSharedArray(46) & 4) >>> 2;
            case 98:
                return (rc.readSharedArray(47) & 2048) >>> 11;
            case 99:
                return (rc.readSharedArray(47) & 16) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorNeutralIsland(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65023) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65531) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 63487) | (value << 11));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65519) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 57343) | (value << 13));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65471) | (value << 6));
                break;
            case 6:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 32767) | (value << 15));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65279) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65533) | (value << 1));
                break;
            case 9:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 64511) | (value << 10));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65527) | (value << 3));
                break;
            case 11:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 61439) | (value << 12));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65503) | (value << 5));
                break;
            case 13:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 49151) | (value << 14));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65407) | (value << 7));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65534) | (value));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65023) | (value << 9));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65531) | (value << 2));
                break;
            case 18:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 63487) | (value << 11));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65519) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 57343) | (value << 13));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65471) | (value << 6));
                break;
            case 22:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 32767) | (value << 15));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65279) | (value << 8));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65533) | (value << 1));
                break;
            case 25:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 64511) | (value << 10));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65527) | (value << 3));
                break;
            case 27:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 61439) | (value << 12));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65503) | (value << 5));
                break;
            case 29:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 49151) | (value << 14));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65407) | (value << 7));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65534) | (value));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65023) | (value << 9));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65531) | (value << 2));
                break;
            case 34:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 63487) | (value << 11));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65519) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 57343) | (value << 13));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65471) | (value << 6));
                break;
            case 38:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 32767) | (value << 15));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65279) | (value << 8));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65533) | (value << 1));
                break;
            case 41:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 64511) | (value << 10));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65527) | (value << 3));
                break;
            case 43:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 61439) | (value << 12));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65503) | (value << 5));
                break;
            case 45:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 49151) | (value << 14));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65407) | (value << 7));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65534) | (value));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65023) | (value << 9));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65531) | (value << 2));
                break;
            case 50:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 63487) | (value << 11));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65519) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 57343) | (value << 13));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65471) | (value << 6));
                break;
            case 54:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 32767) | (value << 15));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65279) | (value << 8));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65533) | (value << 1));
                break;
            case 57:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 64511) | (value << 10));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65527) | (value << 3));
                break;
            case 59:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 61439) | (value << 12));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65503) | (value << 5));
                break;
            case 61:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 49151) | (value << 14));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65407) | (value << 7));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65534) | (value));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65023) | (value << 9));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65531) | (value << 2));
                break;
            case 66:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 63487) | (value << 11));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65519) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 57343) | (value << 13));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65471) | (value << 6));
                break;
            case 70:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 32767) | (value << 15));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65279) | (value << 8));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65533) | (value << 1));
                break;
            case 73:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 64511) | (value << 10));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65527) | (value << 3));
                break;
            case 75:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 61439) | (value << 12));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65503) | (value << 5));
                break;
            case 77:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 49151) | (value << 14));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65407) | (value << 7));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65534) | (value));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65023) | (value << 9));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65531) | (value << 2));
                break;
            case 82:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 63487) | (value << 11));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65519) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 57343) | (value << 13));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65471) | (value << 6));
                break;
            case 86:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 32767) | (value << 15));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65279) | (value << 8));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65533) | (value << 1));
                break;
            case 89:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 64511) | (value << 10));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65527) | (value << 3));
                break;
            case 91:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 61439) | (value << 12));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65503) | (value << 5));
                break;
            case 93:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 49151) | (value << 14));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65407) | (value << 7));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65534) | (value));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65023) | (value << 9));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65531) | (value << 2));
                break;
            case 98:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 63487) | (value << 11));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65519) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorNeutralIsland(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 65023) | (value << 9));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65531) | (value << 2));
                break;
            case 2:
                writeToBufferPool(5, (bufferPool[5] & 63487) | (value << 11));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 65519) | (value << 4));
                break;
            case 4:
                writeToBufferPool(6, (bufferPool[6] & 57343) | (value << 13));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 65471) | (value << 6));
                break;
            case 6:
                writeToBufferPool(7, (bufferPool[7] & 32767) | (value << 15));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 65279) | (value << 8));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65533) | (value << 1));
                break;
            case 9:
                writeToBufferPool(8, (bufferPool[8] & 64511) | (value << 10));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 65527) | (value << 3));
                break;
            case 11:
                writeToBufferPool(9, (bufferPool[9] & 61439) | (value << 12));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 65503) | (value << 5));
                break;
            case 13:
                writeToBufferPool(10, (bufferPool[10] & 49151) | (value << 14));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 65407) | (value << 7));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65534) | (value));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 65023) | (value << 9));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65531) | (value << 2));
                break;
            case 18:
                writeToBufferPool(12, (bufferPool[12] & 63487) | (value << 11));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 65519) | (value << 4));
                break;
            case 20:
                writeToBufferPool(13, (bufferPool[13] & 57343) | (value << 13));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 65471) | (value << 6));
                break;
            case 22:
                writeToBufferPool(14, (bufferPool[14] & 32767) | (value << 15));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 65279) | (value << 8));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65533) | (value << 1));
                break;
            case 25:
                writeToBufferPool(15, (bufferPool[15] & 64511) | (value << 10));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 65527) | (value << 3));
                break;
            case 27:
                writeToBufferPool(16, (bufferPool[16] & 61439) | (value << 12));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 65503) | (value << 5));
                break;
            case 29:
                writeToBufferPool(17, (bufferPool[17] & 49151) | (value << 14));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 65407) | (value << 7));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65534) | (value));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 65023) | (value << 9));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65531) | (value << 2));
                break;
            case 34:
                writeToBufferPool(19, (bufferPool[19] & 63487) | (value << 11));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 65519) | (value << 4));
                break;
            case 36:
                writeToBufferPool(20, (bufferPool[20] & 57343) | (value << 13));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 65471) | (value << 6));
                break;
            case 38:
                writeToBufferPool(21, (bufferPool[21] & 32767) | (value << 15));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 65279) | (value << 8));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65533) | (value << 1));
                break;
            case 41:
                writeToBufferPool(22, (bufferPool[22] & 64511) | (value << 10));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 65527) | (value << 3));
                break;
            case 43:
                writeToBufferPool(23, (bufferPool[23] & 61439) | (value << 12));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 65503) | (value << 5));
                break;
            case 45:
                writeToBufferPool(24, (bufferPool[24] & 49151) | (value << 14));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 65407) | (value << 7));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65534) | (value));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 65023) | (value << 9));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65531) | (value << 2));
                break;
            case 50:
                writeToBufferPool(26, (bufferPool[26] & 63487) | (value << 11));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 65519) | (value << 4));
                break;
            case 52:
                writeToBufferPool(27, (bufferPool[27] & 57343) | (value << 13));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 65471) | (value << 6));
                break;
            case 54:
                writeToBufferPool(28, (bufferPool[28] & 32767) | (value << 15));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 65279) | (value << 8));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65533) | (value << 1));
                break;
            case 57:
                writeToBufferPool(29, (bufferPool[29] & 64511) | (value << 10));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 65527) | (value << 3));
                break;
            case 59:
                writeToBufferPool(30, (bufferPool[30] & 61439) | (value << 12));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 65503) | (value << 5));
                break;
            case 61:
                writeToBufferPool(31, (bufferPool[31] & 49151) | (value << 14));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 65407) | (value << 7));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65534) | (value));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 65023) | (value << 9));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65531) | (value << 2));
                break;
            case 66:
                writeToBufferPool(33, (bufferPool[33] & 63487) | (value << 11));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 65519) | (value << 4));
                break;
            case 68:
                writeToBufferPool(34, (bufferPool[34] & 57343) | (value << 13));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 65471) | (value << 6));
                break;
            case 70:
                writeToBufferPool(35, (bufferPool[35] & 32767) | (value << 15));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 65279) | (value << 8));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65533) | (value << 1));
                break;
            case 73:
                writeToBufferPool(36, (bufferPool[36] & 64511) | (value << 10));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 65527) | (value << 3));
                break;
            case 75:
                writeToBufferPool(37, (bufferPool[37] & 61439) | (value << 12));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 65503) | (value << 5));
                break;
            case 77:
                writeToBufferPool(38, (bufferPool[38] & 49151) | (value << 14));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 65407) | (value << 7));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65534) | (value));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 65023) | (value << 9));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65531) | (value << 2));
                break;
            case 82:
                writeToBufferPool(40, (bufferPool[40] & 63487) | (value << 11));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 65519) | (value << 4));
                break;
            case 84:
                writeToBufferPool(41, (bufferPool[41] & 57343) | (value << 13));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 65471) | (value << 6));
                break;
            case 86:
                writeToBufferPool(42, (bufferPool[42] & 32767) | (value << 15));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 65279) | (value << 8));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65533) | (value << 1));
                break;
            case 89:
                writeToBufferPool(43, (bufferPool[43] & 64511) | (value << 10));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 65527) | (value << 3));
                break;
            case 91:
                writeToBufferPool(44, (bufferPool[44] & 61439) | (value << 12));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 65503) | (value << 5));
                break;
            case 93:
                writeToBufferPool(45, (bufferPool[45] & 49151) | (value << 14));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 65407) | (value << 7));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65534) | (value));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 65023) | (value << 9));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65531) | (value << 2));
                break;
            case 98:
                writeToBufferPool(47, (bufferPool[47] & 63487) | (value << 11));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 65519) | (value << 4));
                break;
        }
    }

    public static int readSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(4) & 65024) >>> 9;
            case 1:
                return (rc.readSharedArray(4) & 508) >>> 2;
            case 2:
                return ((rc.readSharedArray(4) & 3) << 5) + ((rc.readSharedArray(5) & 63488) >>> 11);
            case 3:
                return (rc.readSharedArray(5) & 2032) >>> 4;
            case 4:
                return ((rc.readSharedArray(5) & 15) << 3) + ((rc.readSharedArray(6) & 57344) >>> 13);
            case 5:
                return (rc.readSharedArray(6) & 8128) >>> 6;
            case 6:
                return ((rc.readSharedArray(6) & 63) << 1) + ((rc.readSharedArray(7) & 32768) >>> 15);
            case 7:
                return (rc.readSharedArray(7) & 32512) >>> 8;
            case 8:
                return (rc.readSharedArray(7) & 254) >>> 1;
            case 9:
                return ((rc.readSharedArray(7) & 1) << 6) + ((rc.readSharedArray(8) & 64512) >>> 10);
            case 10:
                return (rc.readSharedArray(8) & 1016) >>> 3;
            case 11:
                return ((rc.readSharedArray(8) & 7) << 4) + ((rc.readSharedArray(9) & 61440) >>> 12);
            case 12:
                return (rc.readSharedArray(9) & 4064) >>> 5;
            case 13:
                return ((rc.readSharedArray(9) & 31) << 2) + ((rc.readSharedArray(10) & 49152) >>> 14);
            case 14:
                return (rc.readSharedArray(10) & 16256) >>> 7;
            case 15:
                return (rc.readSharedArray(10) & 127);
            case 16:
                return (rc.readSharedArray(11) & 65024) >>> 9;
            case 17:
                return (rc.readSharedArray(11) & 508) >>> 2;
            case 18:
                return ((rc.readSharedArray(11) & 3) << 5) + ((rc.readSharedArray(12) & 63488) >>> 11);
            case 19:
                return (rc.readSharedArray(12) & 2032) >>> 4;
            case 20:
                return ((rc.readSharedArray(12) & 15) << 3) + ((rc.readSharedArray(13) & 57344) >>> 13);
            case 21:
                return (rc.readSharedArray(13) & 8128) >>> 6;
            case 22:
                return ((rc.readSharedArray(13) & 63) << 1) + ((rc.readSharedArray(14) & 32768) >>> 15);
            case 23:
                return (rc.readSharedArray(14) & 32512) >>> 8;
            case 24:
                return (rc.readSharedArray(14) & 254) >>> 1;
            case 25:
                return ((rc.readSharedArray(14) & 1) << 6) + ((rc.readSharedArray(15) & 64512) >>> 10);
            case 26:
                return (rc.readSharedArray(15) & 1016) >>> 3;
            case 27:
                return ((rc.readSharedArray(15) & 7) << 4) + ((rc.readSharedArray(16) & 61440) >>> 12);
            case 28:
                return (rc.readSharedArray(16) & 4064) >>> 5;
            case 29:
                return ((rc.readSharedArray(16) & 31) << 2) + ((rc.readSharedArray(17) & 49152) >>> 14);
            case 30:
                return (rc.readSharedArray(17) & 16256) >>> 7;
            case 31:
                return (rc.readSharedArray(17) & 127);
            case 32:
                return (rc.readSharedArray(18) & 65024) >>> 9;
            case 33:
                return (rc.readSharedArray(18) & 508) >>> 2;
            case 34:
                return ((rc.readSharedArray(18) & 3) << 5) + ((rc.readSharedArray(19) & 63488) >>> 11);
            case 35:
                return (rc.readSharedArray(19) & 2032) >>> 4;
            case 36:
                return ((rc.readSharedArray(19) & 15) << 3) + ((rc.readSharedArray(20) & 57344) >>> 13);
            case 37:
                return (rc.readSharedArray(20) & 8128) >>> 6;
            case 38:
                return ((rc.readSharedArray(20) & 63) << 1) + ((rc.readSharedArray(21) & 32768) >>> 15);
            case 39:
                return (rc.readSharedArray(21) & 32512) >>> 8;
            case 40:
                return (rc.readSharedArray(21) & 254) >>> 1;
            case 41:
                return ((rc.readSharedArray(21) & 1) << 6) + ((rc.readSharedArray(22) & 64512) >>> 10);
            case 42:
                return (rc.readSharedArray(22) & 1016) >>> 3;
            case 43:
                return ((rc.readSharedArray(22) & 7) << 4) + ((rc.readSharedArray(23) & 61440) >>> 12);
            case 44:
                return (rc.readSharedArray(23) & 4064) >>> 5;
            case 45:
                return ((rc.readSharedArray(23) & 31) << 2) + ((rc.readSharedArray(24) & 49152) >>> 14);
            case 46:
                return (rc.readSharedArray(24) & 16256) >>> 7;
            case 47:
                return (rc.readSharedArray(24) & 127);
            case 48:
                return (rc.readSharedArray(25) & 65024) >>> 9;
            case 49:
                return (rc.readSharedArray(25) & 508) >>> 2;
            case 50:
                return ((rc.readSharedArray(25) & 3) << 5) + ((rc.readSharedArray(26) & 63488) >>> 11);
            case 51:
                return (rc.readSharedArray(26) & 2032) >>> 4;
            case 52:
                return ((rc.readSharedArray(26) & 15) << 3) + ((rc.readSharedArray(27) & 57344) >>> 13);
            case 53:
                return (rc.readSharedArray(27) & 8128) >>> 6;
            case 54:
                return ((rc.readSharedArray(27) & 63) << 1) + ((rc.readSharedArray(28) & 32768) >>> 15);
            case 55:
                return (rc.readSharedArray(28) & 32512) >>> 8;
            case 56:
                return (rc.readSharedArray(28) & 254) >>> 1;
            case 57:
                return ((rc.readSharedArray(28) & 1) << 6) + ((rc.readSharedArray(29) & 64512) >>> 10);
            case 58:
                return (rc.readSharedArray(29) & 1016) >>> 3;
            case 59:
                return ((rc.readSharedArray(29) & 7) << 4) + ((rc.readSharedArray(30) & 61440) >>> 12);
            case 60:
                return (rc.readSharedArray(30) & 4064) >>> 5;
            case 61:
                return ((rc.readSharedArray(30) & 31) << 2) + ((rc.readSharedArray(31) & 49152) >>> 14);
            case 62:
                return (rc.readSharedArray(31) & 16256) >>> 7;
            case 63:
                return (rc.readSharedArray(31) & 127);
            case 64:
                return (rc.readSharedArray(32) & 65024) >>> 9;
            case 65:
                return (rc.readSharedArray(32) & 508) >>> 2;
            case 66:
                return ((rc.readSharedArray(32) & 3) << 5) + ((rc.readSharedArray(33) & 63488) >>> 11);
            case 67:
                return (rc.readSharedArray(33) & 2032) >>> 4;
            case 68:
                return ((rc.readSharedArray(33) & 15) << 3) + ((rc.readSharedArray(34) & 57344) >>> 13);
            case 69:
                return (rc.readSharedArray(34) & 8128) >>> 6;
            case 70:
                return ((rc.readSharedArray(34) & 63) << 1) + ((rc.readSharedArray(35) & 32768) >>> 15);
            case 71:
                return (rc.readSharedArray(35) & 32512) >>> 8;
            case 72:
                return (rc.readSharedArray(35) & 254) >>> 1;
            case 73:
                return ((rc.readSharedArray(35) & 1) << 6) + ((rc.readSharedArray(36) & 64512) >>> 10);
            case 74:
                return (rc.readSharedArray(36) & 1016) >>> 3;
            case 75:
                return ((rc.readSharedArray(36) & 7) << 4) + ((rc.readSharedArray(37) & 61440) >>> 12);
            case 76:
                return (rc.readSharedArray(37) & 4064) >>> 5;
            case 77:
                return ((rc.readSharedArray(37) & 31) << 2) + ((rc.readSharedArray(38) & 49152) >>> 14);
            case 78:
                return (rc.readSharedArray(38) & 16256) >>> 7;
            case 79:
                return (rc.readSharedArray(38) & 127);
            case 80:
                return (rc.readSharedArray(39) & 65024) >>> 9;
            case 81:
                return (rc.readSharedArray(39) & 508) >>> 2;
            case 82:
                return ((rc.readSharedArray(39) & 3) << 5) + ((rc.readSharedArray(40) & 63488) >>> 11);
            case 83:
                return (rc.readSharedArray(40) & 2032) >>> 4;
            case 84:
                return ((rc.readSharedArray(40) & 15) << 3) + ((rc.readSharedArray(41) & 57344) >>> 13);
            case 85:
                return (rc.readSharedArray(41) & 8128) >>> 6;
            case 86:
                return ((rc.readSharedArray(41) & 63) << 1) + ((rc.readSharedArray(42) & 32768) >>> 15);
            case 87:
                return (rc.readSharedArray(42) & 32512) >>> 8;
            case 88:
                return (rc.readSharedArray(42) & 254) >>> 1;
            case 89:
                return ((rc.readSharedArray(42) & 1) << 6) + ((rc.readSharedArray(43) & 64512) >>> 10);
            case 90:
                return (rc.readSharedArray(43) & 1016) >>> 3;
            case 91:
                return ((rc.readSharedArray(43) & 7) << 4) + ((rc.readSharedArray(44) & 61440) >>> 12);
            case 92:
                return (rc.readSharedArray(44) & 4064) >>> 5;
            case 93:
                return ((rc.readSharedArray(44) & 31) << 2) + ((rc.readSharedArray(45) & 49152) >>> 14);
            case 94:
                return (rc.readSharedArray(45) & 16256) >>> 7;
            case 95:
                return (rc.readSharedArray(45) & 127);
            case 96:
                return (rc.readSharedArray(46) & 65024) >>> 9;
            case 97:
                return (rc.readSharedArray(46) & 508) >>> 2;
            case 98:
                return ((rc.readSharedArray(46) & 3) << 5) + ((rc.readSharedArray(47) & 63488) >>> 11);
            case 99:
                return (rc.readSharedArray(47) & 2032) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 511) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65027) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 2047) | ((value & 31) << 11));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 63503) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 8191) | ((value & 7) << 13));
                break;
            case 5:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 57407) | (value << 6));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 32767) | ((value & 1) << 15));
                break;
            case 7:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 33023) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65281) | (value << 1));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 1023) | ((value & 63) << 10));
                break;
            case 10:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 64519) | (value << 3));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 4095) | ((value & 15) << 12));
                break;
            case 12:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 61471) | (value << 5));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 16383) | ((value & 3) << 14));
                break;
            case 14:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 49279) | (value << 7));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65408) | (value));
                break;
            case 16:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 511) | (value << 9));
                break;
            case 17:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65027) | (value << 2));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 2047) | ((value & 31) << 11));
                break;
            case 19:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 63503) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 8191) | ((value & 7) << 13));
                break;
            case 21:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 57407) | (value << 6));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 32767) | ((value & 1) << 15));
                break;
            case 23:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 33023) | (value << 8));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65281) | (value << 1));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 1023) | ((value & 63) << 10));
                break;
            case 26:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 64519) | (value << 3));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 4095) | ((value & 15) << 12));
                break;
            case 28:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 61471) | (value << 5));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 16383) | ((value & 3) << 14));
                break;
            case 30:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 49279) | (value << 7));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65408) | (value));
                break;
            case 32:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 511) | (value << 9));
                break;
            case 33:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65027) | (value << 2));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 2047) | ((value & 31) << 11));
                break;
            case 35:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 63503) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 8191) | ((value & 7) << 13));
                break;
            case 37:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 57407) | (value << 6));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 32767) | ((value & 1) << 15));
                break;
            case 39:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 33023) | (value << 8));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65281) | (value << 1));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 1023) | ((value & 63) << 10));
                break;
            case 42:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 64519) | (value << 3));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 4095) | ((value & 15) << 12));
                break;
            case 44:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 61471) | (value << 5));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 16383) | ((value & 3) << 14));
                break;
            case 46:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 49279) | (value << 7));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65408) | (value));
                break;
            case 48:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 511) | (value << 9));
                break;
            case 49:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65027) | (value << 2));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 2047) | ((value & 31) << 11));
                break;
            case 51:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 63503) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 8191) | ((value & 7) << 13));
                break;
            case 53:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 57407) | (value << 6));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 32767) | ((value & 1) << 15));
                break;
            case 55:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 33023) | (value << 8));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65281) | (value << 1));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 1023) | ((value & 63) << 10));
                break;
            case 58:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 64519) | (value << 3));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 4095) | ((value & 15) << 12));
                break;
            case 60:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 61471) | (value << 5));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 16383) | ((value & 3) << 14));
                break;
            case 62:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 49279) | (value << 7));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65408) | (value));
                break;
            case 64:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 511) | (value << 9));
                break;
            case 65:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65027) | (value << 2));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 2047) | ((value & 31) << 11));
                break;
            case 67:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 63503) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 8191) | ((value & 7) << 13));
                break;
            case 69:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 57407) | (value << 6));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 32767) | ((value & 1) << 15));
                break;
            case 71:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 33023) | (value << 8));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65281) | (value << 1));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 1023) | ((value & 63) << 10));
                break;
            case 74:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 64519) | (value << 3));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 4095) | ((value & 15) << 12));
                break;
            case 76:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 61471) | (value << 5));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 16383) | ((value & 3) << 14));
                break;
            case 78:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 49279) | (value << 7));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65408) | (value));
                break;
            case 80:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 511) | (value << 9));
                break;
            case 81:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65027) | (value << 2));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 2047) | ((value & 31) << 11));
                break;
            case 83:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 63503) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 8191) | ((value & 7) << 13));
                break;
            case 85:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 57407) | (value << 6));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 32767) | ((value & 1) << 15));
                break;
            case 87:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 33023) | (value << 8));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65281) | (value << 1));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 1023) | ((value & 63) << 10));
                break;
            case 90:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 64519) | (value << 3));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 4095) | ((value & 15) << 12));
                break;
            case 92:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 61471) | (value << 5));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 16383) | ((value & 3) << 14));
                break;
            case 94:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 49279) | (value << 7));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65408) | (value));
                break;
            case 96:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 511) | (value << 9));
                break;
            case 97:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65027) | (value << 2));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 2047) | ((value & 31) << 11));
                break;
            case 99:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 63503) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(4, (bufferPool[4] & 511) | (value << 9));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 65027) | (value << 2));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(5, (bufferPool[5] & 2047) | ((value & 31) << 11));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 63503) | (value << 4));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(6, (bufferPool[6] & 8191) | ((value & 7) << 13));
                break;
            case 5:
                writeToBufferPool(6, (bufferPool[6] & 57407) | (value << 6));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(7, (bufferPool[7] & 32767) | ((value & 1) << 15));
                break;
            case 7:
                writeToBufferPool(7, (bufferPool[7] & 33023) | (value << 8));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 65281) | (value << 1));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(8, (bufferPool[8] & 1023) | ((value & 63) << 10));
                break;
            case 10:
                writeToBufferPool(8, (bufferPool[8] & 64519) | (value << 3));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(9, (bufferPool[9] & 4095) | ((value & 15) << 12));
                break;
            case 12:
                writeToBufferPool(9, (bufferPool[9] & 61471) | (value << 5));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(10, (bufferPool[10] & 16383) | ((value & 3) << 14));
                break;
            case 14:
                writeToBufferPool(10, (bufferPool[10] & 49279) | (value << 7));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 65408) | (value));
                break;
            case 16:
                writeToBufferPool(11, (bufferPool[11] & 511) | (value << 9));
                break;
            case 17:
                writeToBufferPool(11, (bufferPool[11] & 65027) | (value << 2));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(12, (bufferPool[12] & 2047) | ((value & 31) << 11));
                break;
            case 19:
                writeToBufferPool(12, (bufferPool[12] & 63503) | (value << 4));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(13, (bufferPool[13] & 8191) | ((value & 7) << 13));
                break;
            case 21:
                writeToBufferPool(13, (bufferPool[13] & 57407) | (value << 6));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(14, (bufferPool[14] & 32767) | ((value & 1) << 15));
                break;
            case 23:
                writeToBufferPool(14, (bufferPool[14] & 33023) | (value << 8));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 65281) | (value << 1));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(15, (bufferPool[15] & 1023) | ((value & 63) << 10));
                break;
            case 26:
                writeToBufferPool(15, (bufferPool[15] & 64519) | (value << 3));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(16, (bufferPool[16] & 4095) | ((value & 15) << 12));
                break;
            case 28:
                writeToBufferPool(16, (bufferPool[16] & 61471) | (value << 5));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(17, (bufferPool[17] & 16383) | ((value & 3) << 14));
                break;
            case 30:
                writeToBufferPool(17, (bufferPool[17] & 49279) | (value << 7));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 65408) | (value));
                break;
            case 32:
                writeToBufferPool(18, (bufferPool[18] & 511) | (value << 9));
                break;
            case 33:
                writeToBufferPool(18, (bufferPool[18] & 65027) | (value << 2));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(19, (bufferPool[19] & 2047) | ((value & 31) << 11));
                break;
            case 35:
                writeToBufferPool(19, (bufferPool[19] & 63503) | (value << 4));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(20, (bufferPool[20] & 8191) | ((value & 7) << 13));
                break;
            case 37:
                writeToBufferPool(20, (bufferPool[20] & 57407) | (value << 6));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(21, (bufferPool[21] & 32767) | ((value & 1) << 15));
                break;
            case 39:
                writeToBufferPool(21, (bufferPool[21] & 33023) | (value << 8));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 65281) | (value << 1));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(22, (bufferPool[22] & 1023) | ((value & 63) << 10));
                break;
            case 42:
                writeToBufferPool(22, (bufferPool[22] & 64519) | (value << 3));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(23, (bufferPool[23] & 4095) | ((value & 15) << 12));
                break;
            case 44:
                writeToBufferPool(23, (bufferPool[23] & 61471) | (value << 5));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(24, (bufferPool[24] & 16383) | ((value & 3) << 14));
                break;
            case 46:
                writeToBufferPool(24, (bufferPool[24] & 49279) | (value << 7));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 65408) | (value));
                break;
            case 48:
                writeToBufferPool(25, (bufferPool[25] & 511) | (value << 9));
                break;
            case 49:
                writeToBufferPool(25, (bufferPool[25] & 65027) | (value << 2));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(26, (bufferPool[26] & 2047) | ((value & 31) << 11));
                break;
            case 51:
                writeToBufferPool(26, (bufferPool[26] & 63503) | (value << 4));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(27, (bufferPool[27] & 8191) | ((value & 7) << 13));
                break;
            case 53:
                writeToBufferPool(27, (bufferPool[27] & 57407) | (value << 6));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(28, (bufferPool[28] & 32767) | ((value & 1) << 15));
                break;
            case 55:
                writeToBufferPool(28, (bufferPool[28] & 33023) | (value << 8));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 65281) | (value << 1));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(29, (bufferPool[29] & 1023) | ((value & 63) << 10));
                break;
            case 58:
                writeToBufferPool(29, (bufferPool[29] & 64519) | (value << 3));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(30, (bufferPool[30] & 4095) | ((value & 15) << 12));
                break;
            case 60:
                writeToBufferPool(30, (bufferPool[30] & 61471) | (value << 5));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(31, (bufferPool[31] & 16383) | ((value & 3) << 14));
                break;
            case 62:
                writeToBufferPool(31, (bufferPool[31] & 49279) | (value << 7));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 65408) | (value));
                break;
            case 64:
                writeToBufferPool(32, (bufferPool[32] & 511) | (value << 9));
                break;
            case 65:
                writeToBufferPool(32, (bufferPool[32] & 65027) | (value << 2));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(33, (bufferPool[33] & 2047) | ((value & 31) << 11));
                break;
            case 67:
                writeToBufferPool(33, (bufferPool[33] & 63503) | (value << 4));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(34, (bufferPool[34] & 8191) | ((value & 7) << 13));
                break;
            case 69:
                writeToBufferPool(34, (bufferPool[34] & 57407) | (value << 6));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(35, (bufferPool[35] & 32767) | ((value & 1) << 15));
                break;
            case 71:
                writeToBufferPool(35, (bufferPool[35] & 33023) | (value << 8));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 65281) | (value << 1));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(36, (bufferPool[36] & 1023) | ((value & 63) << 10));
                break;
            case 74:
                writeToBufferPool(36, (bufferPool[36] & 64519) | (value << 3));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(37, (bufferPool[37] & 4095) | ((value & 15) << 12));
                break;
            case 76:
                writeToBufferPool(37, (bufferPool[37] & 61471) | (value << 5));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(38, (bufferPool[38] & 16383) | ((value & 3) << 14));
                break;
            case 78:
                writeToBufferPool(38, (bufferPool[38] & 49279) | (value << 7));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 65408) | (value));
                break;
            case 80:
                writeToBufferPool(39, (bufferPool[39] & 511) | (value << 9));
                break;
            case 81:
                writeToBufferPool(39, (bufferPool[39] & 65027) | (value << 2));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(40, (bufferPool[40] & 2047) | ((value & 31) << 11));
                break;
            case 83:
                writeToBufferPool(40, (bufferPool[40] & 63503) | (value << 4));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(41, (bufferPool[41] & 8191) | ((value & 7) << 13));
                break;
            case 85:
                writeToBufferPool(41, (bufferPool[41] & 57407) | (value << 6));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(42, (bufferPool[42] & 32767) | ((value & 1) << 15));
                break;
            case 87:
                writeToBufferPool(42, (bufferPool[42] & 33023) | (value << 8));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 65281) | (value << 1));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(43, (bufferPool[43] & 1023) | ((value & 63) << 10));
                break;
            case 90:
                writeToBufferPool(43, (bufferPool[43] & 64519) | (value << 3));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(44, (bufferPool[44] & 4095) | ((value & 15) << 12));
                break;
            case 92:
                writeToBufferPool(44, (bufferPool[44] & 61471) | (value << 5));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(45, (bufferPool[45] & 16383) | ((value & 3) << 14));
                break;
            case 94:
                writeToBufferPool(45, (bufferPool[45] & 49279) | (value << 7));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 65408) | (value));
                break;
            case 96:
                writeToBufferPool(46, (bufferPool[46] & 511) | (value << 9));
                break;
            case 97:
                writeToBufferPool(46, (bufferPool[46] & 65027) | (value << 2));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(47, (bufferPool[47] & 2047) | ((value & 31) << 11));
                break;
            case 99:
                writeToBufferPool(47, (bufferPool[47] & 63503) | (value << 4));
                break;
        }
    }

    public static int readCombatSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(47) & 15) << 3) + ((rc.readSharedArray(48) & 57344) >>> 13);
            case 1:
                return (rc.readSharedArray(48) & 8128) >>> 6;
            case 2:
                return ((rc.readSharedArray(48) & 63) << 1) + ((rc.readSharedArray(49) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(49) & 32512) >>> 8;
            case 4:
                return (rc.readSharedArray(49) & 254) >>> 1;
            case 5:
                return ((rc.readSharedArray(49) & 1) << 6) + ((rc.readSharedArray(50) & 64512) >>> 10);
            case 6:
                return (rc.readSharedArray(50) & 1016) >>> 3;
            case 7:
                return ((rc.readSharedArray(50) & 7) << 4) + ((rc.readSharedArray(51) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 8191) | ((value & 7) << 13));
                break;
            case 1:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 57407) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 33023) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65281) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 1023) | ((value & 63) << 10));
                break;
            case 6:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 64519) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(47, (bufferPool[47] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(48, (bufferPool[48] & 8191) | ((value & 7) << 13));
                break;
            case 1:
                writeToBufferPool(48, (bufferPool[48] & 57407) | (value << 6));
                break;
            case 2:
                writeToBufferPool(48, (bufferPool[48] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(49, (bufferPool[49] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(49, (bufferPool[49] & 33023) | (value << 8));
                break;
            case 4:
                writeToBufferPool(49, (bufferPool[49] & 65281) | (value << 1));
                break;
            case 5:
                writeToBufferPool(49, (bufferPool[49] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(50, (bufferPool[50] & 1023) | ((value & 63) << 10));
                break;
            case 6:
                writeToBufferPool(50, (bufferPool[50] & 64519) | (value << 3));
                break;
            case 7:
                writeToBufferPool(50, (bufferPool[50] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(51, (bufferPool[51] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readCombatSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(47) & 15) << 3) + ((rc.readSharedArray(48) & 57344) >>> 13);
            case 1:
                return (rc.readSharedArray(48) & 8128) >>> 6;
            case 2:
                return ((rc.readSharedArray(48) & 63) << 1) + ((rc.readSharedArray(49) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(49) & 32512) >>> 8;
            case 4:
                return (rc.readSharedArray(49) & 254) >>> 1;
            case 5:
                return ((rc.readSharedArray(49) & 1) << 6) + ((rc.readSharedArray(50) & 64512) >>> 10);
            case 6:
                return (rc.readSharedArray(50) & 1016) >>> 3;
            case 7:
                return ((rc.readSharedArray(50) & 7) << 4) + ((rc.readSharedArray(51) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 8191) | ((value & 7) << 13));
                break;
            case 1:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 57407) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 33023) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65281) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 1023) | ((value & 63) << 10));
                break;
            case 6:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 64519) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(47, (bufferPool[47] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(48, (bufferPool[48] & 8191) | ((value & 7) << 13));
                break;
            case 1:
                writeToBufferPool(48, (bufferPool[48] & 57407) | (value << 6));
                break;
            case 2:
                writeToBufferPool(48, (bufferPool[48] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(49, (bufferPool[49] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(49, (bufferPool[49] & 33023) | (value << 8));
                break;
            case 4:
                writeToBufferPool(49, (bufferPool[49] & 65281) | (value << 1));
                break;
            case 5:
                writeToBufferPool(49, (bufferPool[49] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(50, (bufferPool[50] & 1023) | ((value & 63) << 10));
                break;
            case 6:
                writeToBufferPool(50, (bufferPool[50] & 64519) | (value << 3));
                break;
            case 7:
                writeToBufferPool(50, (bufferPool[50] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(51, (bufferPool[51] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readMineSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(51) & 3584) >>> 9;
            case 1:
                return ((rc.readSharedArray(51) & 3) << 1) + ((rc.readSharedArray(52) & 32768) >>> 15);
            case 2:
                return (rc.readSharedArray(52) & 224) >>> 5;
            case 3:
                return (rc.readSharedArray(53) & 14336) >>> 11;
            case 4:
                return (rc.readSharedArray(53) & 14) >>> 1;
            case 5:
                return (rc.readSharedArray(54) & 896) >>> 7;
            case 6:
                return (rc.readSharedArray(55) & 57344) >>> 13;
            case 7:
                return (rc.readSharedArray(55) & 56) >>> 3;
            case 8:
                return (rc.readSharedArray(56) & 3584) >>> 9;
            case 9:
                return ((rc.readSharedArray(56) & 3) << 1) + ((rc.readSharedArray(57) & 32768) >>> 15);
            default:
                return -1;
        }
    }

    public static void writeMineSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 61951) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 32767) | ((value & 1) << 15));
                break;
            case 2:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65311) | (value << 5));
                break;
            case 3:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 51199) | (value << 11));
                break;
            case 4:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65521) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 64639) | (value << 7));
                break;
            case 6:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 8191) | (value << 13));
                break;
            case 7:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65479) | (value << 3));
                break;
            case 8:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 61951) | (value << 9));
                break;
            case 9:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 32767) | ((value & 1) << 15));
                break;
        }
    }

    public static void writeBPMineSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(51, (bufferPool[51] & 61951) | (value << 9));
                break;
            case 1:
                writeToBufferPool(51, (bufferPool[51] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(52, (bufferPool[52] & 32767) | ((value & 1) << 15));
                break;
            case 2:
                writeToBufferPool(52, (bufferPool[52] & 65311) | (value << 5));
                break;
            case 3:
                writeToBufferPool(53, (bufferPool[53] & 51199) | (value << 11));
                break;
            case 4:
                writeToBufferPool(53, (bufferPool[53] & 65521) | (value << 1));
                break;
            case 5:
                writeToBufferPool(54, (bufferPool[54] & 64639) | (value << 7));
                break;
            case 6:
                writeToBufferPool(55, (bufferPool[55] & 8191) | (value << 13));
                break;
            case 7:
                writeToBufferPool(55, (bufferPool[55] & 65479) | (value << 3));
                break;
            case 8:
                writeToBufferPool(56, (bufferPool[56] & 61951) | (value << 9));
                break;
            case 9:
                writeToBufferPool(56, (bufferPool[56] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(57, (bufferPool[57] & 32767) | ((value & 1) << 15));
                break;
        }
    }

    public static int readMineSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(51) & 508) >>> 2;
            case 1:
                return (rc.readSharedArray(52) & 32512) >>> 8;
            case 2:
                return ((rc.readSharedArray(52) & 31) << 2) + ((rc.readSharedArray(53) & 49152) >>> 14);
            case 3:
                return (rc.readSharedArray(53) & 2032) >>> 4;
            case 4:
                return ((rc.readSharedArray(53) & 1) << 6) + ((rc.readSharedArray(54) & 64512) >>> 10);
            case 5:
                return (rc.readSharedArray(54) & 127);
            case 6:
                return (rc.readSharedArray(55) & 8128) >>> 6;
            case 7:
                return ((rc.readSharedArray(55) & 7) << 4) + ((rc.readSharedArray(56) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(56) & 508) >>> 2;
            case 9:
                return (rc.readSharedArray(57) & 32512) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65027) | (value << 2));
                break;
            case 1:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 33023) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 16383) | ((value & 3) << 14));
                break;
            case 3:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 63503) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 1023) | ((value & 63) << 10));
                break;
            case 5:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65408) | (value));
                break;
            case 6:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 57407) | (value << 6));
                break;
            case 7:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65027) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 33023) | (value << 8));
                break;
        }
    }

    public static void writeBPMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(51, (bufferPool[51] & 65027) | (value << 2));
                break;
            case 1:
                writeToBufferPool(52, (bufferPool[52] & 33023) | (value << 8));
                break;
            case 2:
                writeToBufferPool(52, (bufferPool[52] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(53, (bufferPool[53] & 16383) | ((value & 3) << 14));
                break;
            case 3:
                writeToBufferPool(53, (bufferPool[53] & 63503) | (value << 4));
                break;
            case 4:
                writeToBufferPool(53, (bufferPool[53] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(54, (bufferPool[54] & 1023) | ((value & 63) << 10));
                break;
            case 5:
                writeToBufferPool(54, (bufferPool[54] & 65408) | (value));
                break;
            case 6:
                writeToBufferPool(55, (bufferPool[55] & 57407) | (value << 6));
                break;
            case 7:
                writeToBufferPool(55, (bufferPool[55] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(56, (bufferPool[56] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(56, (bufferPool[56] & 65027) | (value << 2));
                break;
            case 9:
                writeToBufferPool(57, (bufferPool[57] & 33023) | (value << 8));
                break;
        }
    }

    public static int readMineSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(51) & 4092) >>> 2;
            case 1:
                return ((rc.readSharedArray(51) & 3) << 8) + ((rc.readSharedArray(52) & 65280) >>> 8);
            case 2:
                return ((rc.readSharedArray(52) & 255) << 2) + ((rc.readSharedArray(53) & 49152) >>> 14);
            case 3:
                return (rc.readSharedArray(53) & 16368) >>> 4;
            case 4:
                return ((rc.readSharedArray(53) & 15) << 6) + ((rc.readSharedArray(54) & 64512) >>> 10);
            case 5:
                return (rc.readSharedArray(54) & 1023);
            case 6:
                return (rc.readSharedArray(55) & 65472) >>> 6;
            case 7:
                return ((rc.readSharedArray(55) & 63) << 4) + ((rc.readSharedArray(56) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(56) & 4092) >>> 2;
            case 9:
                return ((rc.readSharedArray(56) & 3) << 8) + ((rc.readSharedArray(57) & 65280) >>> 8);
            default:
                return -1;
        }
    }

    public static void writeMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 61443) | (value << 2));
                break;
            case 1:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65532) | ((value & 768) >>> 8));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 255) | ((value & 255) << 8));
                break;
            case 2:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65280) | ((value & 1020) >>> 2));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 16383) | ((value & 3) << 14));
                break;
            case 3:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 49167) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65520) | ((value & 960) >>> 6));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 1023) | ((value & 63) << 10));
                break;
            case 5:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 64512) | (value));
                break;
            case 6:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 63) | (value << 6));
                break;
            case 7:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65472) | ((value & 1008) >>> 4));
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 61443) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65532) | ((value & 768) >>> 8));
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 255) | ((value & 255) << 8));
                break;
        }
    }

    public static void writeBPMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(51, (bufferPool[51] & 61443) | (value << 2));
                break;
            case 1:
                writeToBufferPool(51, (bufferPool[51] & 65532) | ((value & 768) >>> 8));
                writeToBufferPool(52, (bufferPool[52] & 255) | ((value & 255) << 8));
                break;
            case 2:
                writeToBufferPool(52, (bufferPool[52] & 65280) | ((value & 1020) >>> 2));
                writeToBufferPool(53, (bufferPool[53] & 16383) | ((value & 3) << 14));
                break;
            case 3:
                writeToBufferPool(53, (bufferPool[53] & 49167) | (value << 4));
                break;
            case 4:
                writeToBufferPool(53, (bufferPool[53] & 65520) | ((value & 960) >>> 6));
                writeToBufferPool(54, (bufferPool[54] & 1023) | ((value & 63) << 10));
                break;
            case 5:
                writeToBufferPool(54, (bufferPool[54] & 64512) | (value));
                break;
            case 6:
                writeToBufferPool(55, (bufferPool[55] & 63) | (value << 6));
                break;
            case 7:
                writeToBufferPool(55, (bufferPool[55] & 65472) | ((value & 1008) >>> 4));
                writeToBufferPool(56, (bufferPool[56] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(56, (bufferPool[56] & 61443) | (value << 2));
                break;
            case 9:
                writeToBufferPool(56, (bufferPool[56] & 65532) | ((value & 768) >>> 8));
                writeToBufferPool(57, (bufferPool[57] & 255) | ((value & 255) << 8));
                break;
        }
    }

    // BUFFER POOL READ AND WRITE METHODS

}
