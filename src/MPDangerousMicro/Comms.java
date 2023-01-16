package MPDangerousMicro;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;

    private static int[] thresholds = { 0, 1, 2, 3, 5, 10, 50 };

    public final static int OUR_HQ_SLOTS = 4;
    public final static int SECTOR_SLOTS = 100;
    public final static int COMBAT_SECTOR_SLOTS = 8;
    public final static int EXPLORE_SECTOR_SLOTS = 13;
    public final static int MINE_SECTOR_SLOTS = 10;

    // ControlStatus priorities are in increasing priority.
    public class ControlStatus {
        public static final int UNKNOWN = 0;
        public static final int EXPLORING = 1;
        public static final int EMPTY = 2;
        public static final int FRIENDLY_ISLAND = 3;
        public static final int NEUTRAL_ISLAND = 4;

        public static final int MIN_ENEMY_STATUS = 5;
        public static final int ENEMY_PASSIVE = 5;
        public static final int ENEMY_ISLAND = 6;
        public static final int ENEMY_AGGRESIVE = 7;

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

    public static void writeToBufferPool(int idx, int value) throws GameActionException {
        bufferPool[idx] = value;
        dirtyFlags[idx] = true;
    }

    public static void initBufferPool() throws GameActionException {
        dirtyFlags = new boolean[64];

        bufferPool[0] = rc.readSharedArray(0);
        bufferPool[1] = rc.readSharedArray(1);
        bufferPool[2] = rc.readSharedArray(2);
        bufferPool[3] = rc.readSharedArray(3);
        bufferPool[4] = rc.readSharedArray(4);
        bufferPool[5] = rc.readSharedArray(5);
        bufferPool[6] = rc.readSharedArray(6);
        bufferPool[7] = rc.readSharedArray(7);
        bufferPool[8] = rc.readSharedArray(8);
        bufferPool[9] = rc.readSharedArray(9);
        bufferPool[10] = rc.readSharedArray(10);
        bufferPool[11] = rc.readSharedArray(11);
        bufferPool[12] = rc.readSharedArray(12);
        bufferPool[13] = rc.readSharedArray(13);
        bufferPool[14] = rc.readSharedArray(14);
        bufferPool[15] = rc.readSharedArray(15);
        bufferPool[16] = rc.readSharedArray(16);
        bufferPool[17] = rc.readSharedArray(17);
        bufferPool[18] = rc.readSharedArray(18);
        bufferPool[19] = rc.readSharedArray(19);
        bufferPool[20] = rc.readSharedArray(20);
        bufferPool[21] = rc.readSharedArray(21);
        bufferPool[22] = rc.readSharedArray(22);
        bufferPool[23] = rc.readSharedArray(23);
        bufferPool[24] = rc.readSharedArray(24);
        bufferPool[25] = rc.readSharedArray(25);
        bufferPool[26] = rc.readSharedArray(26);
        bufferPool[27] = rc.readSharedArray(27);
        bufferPool[28] = rc.readSharedArray(28);
        bufferPool[29] = rc.readSharedArray(29);
        bufferPool[30] = rc.readSharedArray(30);
        bufferPool[31] = rc.readSharedArray(31);
        bufferPool[32] = rc.readSharedArray(32);
        bufferPool[33] = rc.readSharedArray(33);
        bufferPool[34] = rc.readSharedArray(34);
        bufferPool[35] = rc.readSharedArray(35);
        bufferPool[36] = rc.readSharedArray(36);
        bufferPool[37] = rc.readSharedArray(37);
        bufferPool[38] = rc.readSharedArray(38);
        bufferPool[39] = rc.readSharedArray(39);
        bufferPool[40] = rc.readSharedArray(40);
        bufferPool[41] = rc.readSharedArray(41);
        bufferPool[42] = rc.readSharedArray(42);
        bufferPool[43] = rc.readSharedArray(43);
        bufferPool[44] = rc.readSharedArray(44);
        bufferPool[45] = rc.readSharedArray(45);
        bufferPool[46] = rc.readSharedArray(46);
        bufferPool[47] = rc.readSharedArray(47);
        bufferPool[48] = rc.readSharedArray(48);
        bufferPool[49] = rc.readSharedArray(49);
        bufferPool[50] = rc.readSharedArray(50);
        bufferPool[51] = rc.readSharedArray(51);
        bufferPool[52] = rc.readSharedArray(52);
        bufferPool[53] = rc.readSharedArray(53);
        bufferPool[54] = rc.readSharedArray(54);
        bufferPool[55] = rc.readSharedArray(55);
        bufferPool[56] = rc.readSharedArray(56);
        bufferPool[57] = rc.readSharedArray(57);
        bufferPool[58] = rc.readSharedArray(58);
        bufferPool[59] = rc.readSharedArray(59);
        bufferPool[60] = rc.readSharedArray(60);
        bufferPool[61] = rc.readSharedArray(61);
        bufferPool[62] = rc.readSharedArray(62);
        bufferPool[63] = rc.readSharedArray(63);
    }

    public static void flushBufferPool() throws GameActionException {
        if (dirtyFlags[0])
            rc.writeSharedArray(0, bufferPool[0]);
        if (dirtyFlags[1])
            rc.writeSharedArray(1, bufferPool[1]);
        if (dirtyFlags[2])
            rc.writeSharedArray(2, bufferPool[2]);
        if (dirtyFlags[3])
            rc.writeSharedArray(3, bufferPool[3]);
        if (dirtyFlags[4])
            rc.writeSharedArray(4, bufferPool[4]);
        if (dirtyFlags[5])
            rc.writeSharedArray(5, bufferPool[5]);
        if (dirtyFlags[6])
            rc.writeSharedArray(6, bufferPool[6]);
        if (dirtyFlags[7])
            rc.writeSharedArray(7, bufferPool[7]);
        if (dirtyFlags[8])
            rc.writeSharedArray(8, bufferPool[8]);
        if (dirtyFlags[9])
            rc.writeSharedArray(9, bufferPool[9]);
        if (dirtyFlags[10])
            rc.writeSharedArray(10, bufferPool[10]);
        if (dirtyFlags[11])
            rc.writeSharedArray(11, bufferPool[11]);
        if (dirtyFlags[12])
            rc.writeSharedArray(12, bufferPool[12]);
        if (dirtyFlags[13])
            rc.writeSharedArray(13, bufferPool[13]);
        if (dirtyFlags[14])
            rc.writeSharedArray(14, bufferPool[14]);
        if (dirtyFlags[15])
            rc.writeSharedArray(15, bufferPool[15]);
        if (dirtyFlags[16])
            rc.writeSharedArray(16, bufferPool[16]);
        if (dirtyFlags[17])
            rc.writeSharedArray(17, bufferPool[17]);
        if (dirtyFlags[18])
            rc.writeSharedArray(18, bufferPool[18]);
        if (dirtyFlags[19])
            rc.writeSharedArray(19, bufferPool[19]);
        if (dirtyFlags[20])
            rc.writeSharedArray(20, bufferPool[20]);
        if (dirtyFlags[21])
            rc.writeSharedArray(21, bufferPool[21]);
        if (dirtyFlags[22])
            rc.writeSharedArray(22, bufferPool[22]);
        if (dirtyFlags[23])
            rc.writeSharedArray(23, bufferPool[23]);
        if (dirtyFlags[24])
            rc.writeSharedArray(24, bufferPool[24]);
        if (dirtyFlags[25])
            rc.writeSharedArray(25, bufferPool[25]);
        if (dirtyFlags[26])
            rc.writeSharedArray(26, bufferPool[26]);
        if (dirtyFlags[27])
            rc.writeSharedArray(27, bufferPool[27]);
        if (dirtyFlags[28])
            rc.writeSharedArray(28, bufferPool[28]);
        if (dirtyFlags[29])
            rc.writeSharedArray(29, bufferPool[29]);
        if (dirtyFlags[30])
            rc.writeSharedArray(30, bufferPool[30]);
        if (dirtyFlags[31])
            rc.writeSharedArray(31, bufferPool[31]);
        if (dirtyFlags[32])
            rc.writeSharedArray(32, bufferPool[32]);
        if (dirtyFlags[33])
            rc.writeSharedArray(33, bufferPool[33]);
        if (dirtyFlags[34])
            rc.writeSharedArray(34, bufferPool[34]);
        if (dirtyFlags[35])
            rc.writeSharedArray(35, bufferPool[35]);
        if (dirtyFlags[36])
            rc.writeSharedArray(36, bufferPool[36]);
        if (dirtyFlags[37])
            rc.writeSharedArray(37, bufferPool[37]);
        if (dirtyFlags[38])
            rc.writeSharedArray(38, bufferPool[38]);
        if (dirtyFlags[39])
            rc.writeSharedArray(39, bufferPool[39]);
        if (dirtyFlags[40])
            rc.writeSharedArray(40, bufferPool[40]);
        if (dirtyFlags[41])
            rc.writeSharedArray(41, bufferPool[41]);
        if (dirtyFlags[42])
            rc.writeSharedArray(42, bufferPool[42]);
        if (dirtyFlags[43])
            rc.writeSharedArray(43, bufferPool[43]);
        if (dirtyFlags[44])
            rc.writeSharedArray(44, bufferPool[44]);
        if (dirtyFlags[45])
            rc.writeSharedArray(45, bufferPool[45]);
        if (dirtyFlags[46])
            rc.writeSharedArray(46, bufferPool[46]);
        if (dirtyFlags[47])
            rc.writeSharedArray(47, bufferPool[47]);
        if (dirtyFlags[48])
            rc.writeSharedArray(48, bufferPool[48]);
        if (dirtyFlags[49])
            rc.writeSharedArray(49, bufferPool[49]);
        if (dirtyFlags[50])
            rc.writeSharedArray(50, bufferPool[50]);
        if (dirtyFlags[51])
            rc.writeSharedArray(51, bufferPool[51]);
        if (dirtyFlags[52])
            rc.writeSharedArray(52, bufferPool[52]);
        if (dirtyFlags[53])
            rc.writeSharedArray(53, bufferPool[53]);
        if (dirtyFlags[54])
            rc.writeSharedArray(54, bufferPool[54]);
        if (dirtyFlags[55])
            rc.writeSharedArray(55, bufferPool[55]);
        if (dirtyFlags[56])
            rc.writeSharedArray(56, bufferPool[56]);
        if (dirtyFlags[57])
            rc.writeSharedArray(57, bufferPool[57]);
        if (dirtyFlags[58])
            rc.writeSharedArray(58, bufferPool[58]);
        if (dirtyFlags[59])
            rc.writeSharedArray(59, bufferPool[59]);
        if (dirtyFlags[60])
            rc.writeSharedArray(60, bufferPool[60]);
        if (dirtyFlags[61])
            rc.writeSharedArray(61, bufferPool[61]);
        if (dirtyFlags[62])
            rc.writeSharedArray(62, bufferPool[62]);
        if (dirtyFlags[63])
            rc.writeSharedArray(63, bufferPool[63]);
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
        rc.writeSharedArray(47, 32639);
        rc.writeSharedArray(48, 32639);
        rc.writeSharedArray(49, 32639);
        rc.writeSharedArray(50, 32639);
        rc.writeSharedArray(51, 32639);
        rc.writeSharedArray(52, 32639);
        rc.writeSharedArray(53, 32639);
        rc.writeSharedArray(54, 32639);
        rc.writeSharedArray(55, 32639);
        rc.writeSharedArray(56, 32639);
        rc.writeSharedArray(57, 32543);
        rc.writeSharedArray(58, 51185);
        rc.writeSharedArray(59, 64639);
        rc.writeSharedArray(60, 8135);
        rc.writeSharedArray(61, 61948);
        rc.writeSharedArray(62, 32543);
        rc.writeSharedArray(63, 51184);
    }


    public static void resetAllSectorControlStatus() throws GameActionException {
        rc.writeSharedArray(3, rc.readSharedArray(3) & 65310);
        rc.writeSharedArray(4, rc.readSharedArray(4) & 15480);
        rc.writeSharedArray(5, rc.readSharedArray(5) & 61923);
        rc.writeSharedArray(6, rc.readSharedArray(6) & 51087);
        rc.writeSharedArray(7, rc.readSharedArray(7) & 7740);
        rc.writeSharedArray(8, rc.readSharedArray(8) & 30961);
        rc.writeSharedArray(9, rc.readSharedArray(9) & 58311);
        rc.writeSharedArray(10, rc.readSharedArray(10) & 36638);
        rc.writeSharedArray(11, rc.readSharedArray(11) & 15480);
        rc.writeSharedArray(12, rc.readSharedArray(12) & 61923);
        rc.writeSharedArray(13, rc.readSharedArray(13) & 51087);
        rc.writeSharedArray(14, rc.readSharedArray(14) & 7740);
        rc.writeSharedArray(15, rc.readSharedArray(15) & 30961);
        rc.writeSharedArray(16, rc.readSharedArray(16) & 58311);
        rc.writeSharedArray(17, rc.readSharedArray(17) & 36638);
        rc.writeSharedArray(18, rc.readSharedArray(18) & 15480);
        rc.writeSharedArray(19, rc.readSharedArray(19) & 61923);
        rc.writeSharedArray(20, rc.readSharedArray(20) & 51087);
        rc.writeSharedArray(21, rc.readSharedArray(21) & 7740);
        rc.writeSharedArray(22, rc.readSharedArray(22) & 30961);
        rc.writeSharedArray(23, rc.readSharedArray(23) & 58311);
        rc.writeSharedArray(24, rc.readSharedArray(24) & 36638);
        rc.writeSharedArray(25, rc.readSharedArray(25) & 15480);
        rc.writeSharedArray(26, rc.readSharedArray(26) & 61923);
        rc.writeSharedArray(27, rc.readSharedArray(27) & 51087);
        rc.writeSharedArray(28, rc.readSharedArray(28) & 7740);
        rc.writeSharedArray(29, rc.readSharedArray(29) & 30961);
        rc.writeSharedArray(30, rc.readSharedArray(30) & 58311);
        rc.writeSharedArray(31, rc.readSharedArray(31) & 36638);
        rc.writeSharedArray(32, rc.readSharedArray(32) & 15480);
        rc.writeSharedArray(33, rc.readSharedArray(33) & 61923);
        rc.writeSharedArray(34, rc.readSharedArray(34) & 51087);
        rc.writeSharedArray(35, rc.readSharedArray(35) & 7740);
        rc.writeSharedArray(36, rc.readSharedArray(36) & 30961);
        rc.writeSharedArray(37, rc.readSharedArray(37) & 58311);
        rc.writeSharedArray(38, rc.readSharedArray(38) & 36638);
        rc.writeSharedArray(39, rc.readSharedArray(39) & 15480);
        rc.writeSharedArray(40, rc.readSharedArray(40) & 61923);
        rc.writeSharedArray(41, rc.readSharedArray(41) & 51087);
        rc.writeSharedArray(42, rc.readSharedArray(42) & 7740);
        rc.writeSharedArray(43, rc.readSharedArray(43) & 30961);
        rc.writeSharedArray(44, rc.readSharedArray(44) & 58311);
        rc.writeSharedArray(45, rc.readSharedArray(45) & 36638);
        rc.writeSharedArray(46, rc.readSharedArray(46) & 15480);
    }

    public static int readOurHqFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 32768) >>> 15;
            case 1:
                return (rc.readSharedArray(0) & 4) >>> 2;
            case 2:
                return (rc.readSharedArray(1) & 32) >>> 5;
            case 3:
                return (rc.readSharedArray(2) & 256) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeOurHqFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 32767) | (value << 15));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65531) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65503) | (value << 5));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65279) | (value << 8));
                break;
        }
    }

    public static void writeBPOurHqFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 32767) | (value << 15));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65531) | (value << 2));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65503) | (value << 5));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65279) | (value << 8));
                break;
        }
    }

    public static int readOurHqXCoord(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 32256) >>> 9;
            case 1:
                return ((rc.readSharedArray(0) & 3) << 4) + ((rc.readSharedArray(1) & 61440) >>> 12);
            case 2:
                return ((rc.readSharedArray(1) & 31) << 1) + ((rc.readSharedArray(2) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(2) & 252) >>> 2;
            default:
                return -1;
        }
    }

    public static void writeOurHqXCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 33279) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65504) | ((value & 62) >>> 1));
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65283) | (value << 2));
                break;
        }
    }

    public static void writeBPOurHqXCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 33279) | (value << 9));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(1, (bufferPool[1] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65504) | ((value & 62) >>> 1));
                writeToBufferPool(2, (bufferPool[2] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65283) | (value << 2));
                break;
        }
    }

    public static int readOurHqYCoord(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 504) >>> 3;
            case 1:
                return (rc.readSharedArray(1) & 4032) >>> 6;
            case 2:
                return (rc.readSharedArray(2) & 32256) >>> 9;
            case 3:
                return ((rc.readSharedArray(2) & 3) << 4) + ((rc.readSharedArray(3) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeOurHqYCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65031) | (value << 3));
                break;
            case 1:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 61503) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 33279) | (value << 9));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPOurHqYCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 65031) | (value << 3));
                break;
            case 1:
                writeToBufferPool(1, (bufferPool[1] & 61503) | (value << 6));
                break;
            case 2:
                writeToBufferPool(2, (bufferPool[2] & 33279) | (value << 9));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(3, (bufferPool[3] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readOurHqAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 65528) >>> 3;
            case 1:
                return ((rc.readSharedArray(0) & 7) << 10) + ((rc.readSharedArray(1) & 65472) >>> 6);
            case 2:
                return ((rc.readSharedArray(1) & 63) << 7) + ((rc.readSharedArray(2) & 65024) >>> 9);
            case 3:
                return ((rc.readSharedArray(2) & 511) << 4) + ((rc.readSharedArray(3) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeOurHqAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 7) | (value << 3));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65528) | ((value & 7168) >>> 10));
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 63) | ((value & 1023) << 6));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65472) | ((value & 8064) >>> 7));
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 511) | ((value & 127) << 9));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65024) | ((value & 8176) >>> 4));
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPOurHqAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 7) | (value << 3));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65528) | ((value & 7168) >>> 10));
                writeToBufferPool(1, (bufferPool[1] & 63) | ((value & 1023) << 6));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65472) | ((value & 8064) >>> 7));
                writeToBufferPool(2, (bufferPool[2] & 511) | ((value & 127) << 9));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65024) | ((value & 8176) >>> 4));
                writeToBufferPool(3, (bufferPool[3] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readSectorIslands(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 2048) >>> 11;
            case 1:
                return (rc.readSharedArray(3) & 16) >>> 4;
            case 2:
                return (rc.readSharedArray(4) & 8192) >>> 13;
            case 3:
                return (rc.readSharedArray(4) & 64) >>> 6;
            case 4:
                return (rc.readSharedArray(5) & 32768) >>> 15;
            case 5:
                return (rc.readSharedArray(5) & 256) >>> 8;
            case 6:
                return (rc.readSharedArray(5) & 2) >>> 1;
            case 7:
                return (rc.readSharedArray(6) & 1024) >>> 10;
            case 8:
                return (rc.readSharedArray(6) & 8) >>> 3;
            case 9:
                return (rc.readSharedArray(7) & 4096) >>> 12;
            case 10:
                return (rc.readSharedArray(7) & 32) >>> 5;
            case 11:
                return (rc.readSharedArray(8) & 16384) >>> 14;
            case 12:
                return (rc.readSharedArray(8) & 128) >>> 7;
            case 13:
                return (rc.readSharedArray(8) & 1);
            case 14:
                return (rc.readSharedArray(9) & 512) >>> 9;
            case 15:
                return (rc.readSharedArray(9) & 4) >>> 2;
            case 16:
                return (rc.readSharedArray(10) & 2048) >>> 11;
            case 17:
                return (rc.readSharedArray(10) & 16) >>> 4;
            case 18:
                return (rc.readSharedArray(11) & 8192) >>> 13;
            case 19:
                return (rc.readSharedArray(11) & 64) >>> 6;
            case 20:
                return (rc.readSharedArray(12) & 32768) >>> 15;
            case 21:
                return (rc.readSharedArray(12) & 256) >>> 8;
            case 22:
                return (rc.readSharedArray(12) & 2) >>> 1;
            case 23:
                return (rc.readSharedArray(13) & 1024) >>> 10;
            case 24:
                return (rc.readSharedArray(13) & 8) >>> 3;
            case 25:
                return (rc.readSharedArray(14) & 4096) >>> 12;
            case 26:
                return (rc.readSharedArray(14) & 32) >>> 5;
            case 27:
                return (rc.readSharedArray(15) & 16384) >>> 14;
            case 28:
                return (rc.readSharedArray(15) & 128) >>> 7;
            case 29:
                return (rc.readSharedArray(15) & 1);
            case 30:
                return (rc.readSharedArray(16) & 512) >>> 9;
            case 31:
                return (rc.readSharedArray(16) & 4) >>> 2;
            case 32:
                return (rc.readSharedArray(17) & 2048) >>> 11;
            case 33:
                return (rc.readSharedArray(17) & 16) >>> 4;
            case 34:
                return (rc.readSharedArray(18) & 8192) >>> 13;
            case 35:
                return (rc.readSharedArray(18) & 64) >>> 6;
            case 36:
                return (rc.readSharedArray(19) & 32768) >>> 15;
            case 37:
                return (rc.readSharedArray(19) & 256) >>> 8;
            case 38:
                return (rc.readSharedArray(19) & 2) >>> 1;
            case 39:
                return (rc.readSharedArray(20) & 1024) >>> 10;
            case 40:
                return (rc.readSharedArray(20) & 8) >>> 3;
            case 41:
                return (rc.readSharedArray(21) & 4096) >>> 12;
            case 42:
                return (rc.readSharedArray(21) & 32) >>> 5;
            case 43:
                return (rc.readSharedArray(22) & 16384) >>> 14;
            case 44:
                return (rc.readSharedArray(22) & 128) >>> 7;
            case 45:
                return (rc.readSharedArray(22) & 1);
            case 46:
                return (rc.readSharedArray(23) & 512) >>> 9;
            case 47:
                return (rc.readSharedArray(23) & 4) >>> 2;
            case 48:
                return (rc.readSharedArray(24) & 2048) >>> 11;
            case 49:
                return (rc.readSharedArray(24) & 16) >>> 4;
            case 50:
                return (rc.readSharedArray(25) & 8192) >>> 13;
            case 51:
                return (rc.readSharedArray(25) & 64) >>> 6;
            case 52:
                return (rc.readSharedArray(26) & 32768) >>> 15;
            case 53:
                return (rc.readSharedArray(26) & 256) >>> 8;
            case 54:
                return (rc.readSharedArray(26) & 2) >>> 1;
            case 55:
                return (rc.readSharedArray(27) & 1024) >>> 10;
            case 56:
                return (rc.readSharedArray(27) & 8) >>> 3;
            case 57:
                return (rc.readSharedArray(28) & 4096) >>> 12;
            case 58:
                return (rc.readSharedArray(28) & 32) >>> 5;
            case 59:
                return (rc.readSharedArray(29) & 16384) >>> 14;
            case 60:
                return (rc.readSharedArray(29) & 128) >>> 7;
            case 61:
                return (rc.readSharedArray(29) & 1);
            case 62:
                return (rc.readSharedArray(30) & 512) >>> 9;
            case 63:
                return (rc.readSharedArray(30) & 4) >>> 2;
            case 64:
                return (rc.readSharedArray(31) & 2048) >>> 11;
            case 65:
                return (rc.readSharedArray(31) & 16) >>> 4;
            case 66:
                return (rc.readSharedArray(32) & 8192) >>> 13;
            case 67:
                return (rc.readSharedArray(32) & 64) >>> 6;
            case 68:
                return (rc.readSharedArray(33) & 32768) >>> 15;
            case 69:
                return (rc.readSharedArray(33) & 256) >>> 8;
            case 70:
                return (rc.readSharedArray(33) & 2) >>> 1;
            case 71:
                return (rc.readSharedArray(34) & 1024) >>> 10;
            case 72:
                return (rc.readSharedArray(34) & 8) >>> 3;
            case 73:
                return (rc.readSharedArray(35) & 4096) >>> 12;
            case 74:
                return (rc.readSharedArray(35) & 32) >>> 5;
            case 75:
                return (rc.readSharedArray(36) & 16384) >>> 14;
            case 76:
                return (rc.readSharedArray(36) & 128) >>> 7;
            case 77:
                return (rc.readSharedArray(36) & 1);
            case 78:
                return (rc.readSharedArray(37) & 512) >>> 9;
            case 79:
                return (rc.readSharedArray(37) & 4) >>> 2;
            case 80:
                return (rc.readSharedArray(38) & 2048) >>> 11;
            case 81:
                return (rc.readSharedArray(38) & 16) >>> 4;
            case 82:
                return (rc.readSharedArray(39) & 8192) >>> 13;
            case 83:
                return (rc.readSharedArray(39) & 64) >>> 6;
            case 84:
                return (rc.readSharedArray(40) & 32768) >>> 15;
            case 85:
                return (rc.readSharedArray(40) & 256) >>> 8;
            case 86:
                return (rc.readSharedArray(40) & 2) >>> 1;
            case 87:
                return (rc.readSharedArray(41) & 1024) >>> 10;
            case 88:
                return (rc.readSharedArray(41) & 8) >>> 3;
            case 89:
                return (rc.readSharedArray(42) & 4096) >>> 12;
            case 90:
                return (rc.readSharedArray(42) & 32) >>> 5;
            case 91:
                return (rc.readSharedArray(43) & 16384) >>> 14;
            case 92:
                return (rc.readSharedArray(43) & 128) >>> 7;
            case 93:
                return (rc.readSharedArray(43) & 1);
            case 94:
                return (rc.readSharedArray(44) & 512) >>> 9;
            case 95:
                return (rc.readSharedArray(44) & 4) >>> 2;
            case 96:
                return (rc.readSharedArray(45) & 2048) >>> 11;
            case 97:
                return (rc.readSharedArray(45) & 16) >>> 4;
            case 98:
                return (rc.readSharedArray(46) & 8192) >>> 13;
            case 99:
                return (rc.readSharedArray(46) & 64) >>> 6;
            default:
                return -1;
        }
    }

    public static void writeSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 63487) | (value << 11));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65519) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 57343) | (value << 13));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65471) | (value << 6));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 32767) | (value << 15));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65279) | (value << 8));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65533) | (value << 1));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 64511) | (value << 10));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65527) | (value << 3));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 61439) | (value << 12));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65503) | (value << 5));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 49151) | (value << 14));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65407) | (value << 7));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65534) | (value));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65023) | (value << 9));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65531) | (value << 2));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 63487) | (value << 11));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65519) | (value << 4));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 57343) | (value << 13));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65471) | (value << 6));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 32767) | (value << 15));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65279) | (value << 8));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65533) | (value << 1));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 64511) | (value << 10));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65527) | (value << 3));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 61439) | (value << 12));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65503) | (value << 5));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 49151) | (value << 14));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65407) | (value << 7));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65534) | (value));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65023) | (value << 9));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65531) | (value << 2));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 63487) | (value << 11));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65519) | (value << 4));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 57343) | (value << 13));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65471) | (value << 6));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 32767) | (value << 15));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65279) | (value << 8));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65533) | (value << 1));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 64511) | (value << 10));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65527) | (value << 3));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 61439) | (value << 12));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65503) | (value << 5));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 49151) | (value << 14));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65407) | (value << 7));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65534) | (value));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65023) | (value << 9));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65531) | (value << 2));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 63487) | (value << 11));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65519) | (value << 4));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 57343) | (value << 13));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65471) | (value << 6));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 32767) | (value << 15));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65279) | (value << 8));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65533) | (value << 1));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 64511) | (value << 10));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65527) | (value << 3));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 61439) | (value << 12));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65503) | (value << 5));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 49151) | (value << 14));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65407) | (value << 7));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65534) | (value));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65023) | (value << 9));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65531) | (value << 2));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 63487) | (value << 11));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65519) | (value << 4));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 57343) | (value << 13));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65471) | (value << 6));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 32767) | (value << 15));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65279) | (value << 8));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65533) | (value << 1));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 64511) | (value << 10));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65527) | (value << 3));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 61439) | (value << 12));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65503) | (value << 5));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 49151) | (value << 14));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65407) | (value << 7));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65534) | (value));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65023) | (value << 9));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65531) | (value << 2));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 63487) | (value << 11));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65519) | (value << 4));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 57343) | (value << 13));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65471) | (value << 6));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 32767) | (value << 15));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65279) | (value << 8));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65533) | (value << 1));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 64511) | (value << 10));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65527) | (value << 3));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 61439) | (value << 12));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65503) | (value << 5));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 49151) | (value << 14));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65407) | (value << 7));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65534) | (value));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65023) | (value << 9));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65531) | (value << 2));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 63487) | (value << 11));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65519) | (value << 4));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 57343) | (value << 13));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65471) | (value << 6));
                break;
        }
    }

    public static void writeBPSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 63487) | (value << 11));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65519) | (value << 4));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 57343) | (value << 13));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65471) | (value << 6));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 32767) | (value << 15));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65279) | (value << 8));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65533) | (value << 1));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 64511) | (value << 10));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65527) | (value << 3));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 61439) | (value << 12));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65503) | (value << 5));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 49151) | (value << 14));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65407) | (value << 7));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65534) | (value));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 65023) | (value << 9));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65531) | (value << 2));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 63487) | (value << 11));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65519) | (value << 4));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 57343) | (value << 13));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65471) | (value << 6));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 32767) | (value << 15));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65279) | (value << 8));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65533) | (value << 1));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 64511) | (value << 10));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65527) | (value << 3));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 61439) | (value << 12));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65503) | (value << 5));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 49151) | (value << 14));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65407) | (value << 7));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65534) | (value));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 65023) | (value << 9));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65531) | (value << 2));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 63487) | (value << 11));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65519) | (value << 4));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 57343) | (value << 13));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65471) | (value << 6));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 32767) | (value << 15));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65279) | (value << 8));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65533) | (value << 1));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 64511) | (value << 10));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65527) | (value << 3));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 61439) | (value << 12));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65503) | (value << 5));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 49151) | (value << 14));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65407) | (value << 7));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65534) | (value));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 65023) | (value << 9));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65531) | (value << 2));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 63487) | (value << 11));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65519) | (value << 4));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 57343) | (value << 13));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65471) | (value << 6));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 32767) | (value << 15));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65279) | (value << 8));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65533) | (value << 1));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 64511) | (value << 10));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65527) | (value << 3));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 61439) | (value << 12));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65503) | (value << 5));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 49151) | (value << 14));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65407) | (value << 7));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65534) | (value));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 65023) | (value << 9));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65531) | (value << 2));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 63487) | (value << 11));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65519) | (value << 4));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 57343) | (value << 13));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65471) | (value << 6));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 32767) | (value << 15));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65279) | (value << 8));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65533) | (value << 1));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 64511) | (value << 10));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65527) | (value << 3));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 61439) | (value << 12));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65503) | (value << 5));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 49151) | (value << 14));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65407) | (value << 7));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65534) | (value));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 65023) | (value << 9));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65531) | (value << 2));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 63487) | (value << 11));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65519) | (value << 4));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 57343) | (value << 13));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65471) | (value << 6));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 32767) | (value << 15));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65279) | (value << 8));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65533) | (value << 1));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 64511) | (value << 10));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65527) | (value << 3));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 61439) | (value << 12));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65503) | (value << 5));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 49151) | (value << 14));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65407) | (value << 7));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65534) | (value));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 65023) | (value << 9));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65531) | (value << 2));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 63487) | (value << 11));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65519) | (value << 4));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 57343) | (value << 13));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65471) | (value << 6));
                break;
        }
    }

    public static int readSectorAdamantiumFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 1024) >>> 10;
            case 1:
                return (rc.readSharedArray(3) & 8) >>> 3;
            case 2:
                return (rc.readSharedArray(4) & 4096) >>> 12;
            case 3:
                return (rc.readSharedArray(4) & 32) >>> 5;
            case 4:
                return (rc.readSharedArray(5) & 16384) >>> 14;
            case 5:
                return (rc.readSharedArray(5) & 128) >>> 7;
            case 6:
                return (rc.readSharedArray(5) & 1);
            case 7:
                return (rc.readSharedArray(6) & 512) >>> 9;
            case 8:
                return (rc.readSharedArray(6) & 4) >>> 2;
            case 9:
                return (rc.readSharedArray(7) & 2048) >>> 11;
            case 10:
                return (rc.readSharedArray(7) & 16) >>> 4;
            case 11:
                return (rc.readSharedArray(8) & 8192) >>> 13;
            case 12:
                return (rc.readSharedArray(8) & 64) >>> 6;
            case 13:
                return (rc.readSharedArray(9) & 32768) >>> 15;
            case 14:
                return (rc.readSharedArray(9) & 256) >>> 8;
            case 15:
                return (rc.readSharedArray(9) & 2) >>> 1;
            case 16:
                return (rc.readSharedArray(10) & 1024) >>> 10;
            case 17:
                return (rc.readSharedArray(10) & 8) >>> 3;
            case 18:
                return (rc.readSharedArray(11) & 4096) >>> 12;
            case 19:
                return (rc.readSharedArray(11) & 32) >>> 5;
            case 20:
                return (rc.readSharedArray(12) & 16384) >>> 14;
            case 21:
                return (rc.readSharedArray(12) & 128) >>> 7;
            case 22:
                return (rc.readSharedArray(12) & 1);
            case 23:
                return (rc.readSharedArray(13) & 512) >>> 9;
            case 24:
                return (rc.readSharedArray(13) & 4) >>> 2;
            case 25:
                return (rc.readSharedArray(14) & 2048) >>> 11;
            case 26:
                return (rc.readSharedArray(14) & 16) >>> 4;
            case 27:
                return (rc.readSharedArray(15) & 8192) >>> 13;
            case 28:
                return (rc.readSharedArray(15) & 64) >>> 6;
            case 29:
                return (rc.readSharedArray(16) & 32768) >>> 15;
            case 30:
                return (rc.readSharedArray(16) & 256) >>> 8;
            case 31:
                return (rc.readSharedArray(16) & 2) >>> 1;
            case 32:
                return (rc.readSharedArray(17) & 1024) >>> 10;
            case 33:
                return (rc.readSharedArray(17) & 8) >>> 3;
            case 34:
                return (rc.readSharedArray(18) & 4096) >>> 12;
            case 35:
                return (rc.readSharedArray(18) & 32) >>> 5;
            case 36:
                return (rc.readSharedArray(19) & 16384) >>> 14;
            case 37:
                return (rc.readSharedArray(19) & 128) >>> 7;
            case 38:
                return (rc.readSharedArray(19) & 1);
            case 39:
                return (rc.readSharedArray(20) & 512) >>> 9;
            case 40:
                return (rc.readSharedArray(20) & 4) >>> 2;
            case 41:
                return (rc.readSharedArray(21) & 2048) >>> 11;
            case 42:
                return (rc.readSharedArray(21) & 16) >>> 4;
            case 43:
                return (rc.readSharedArray(22) & 8192) >>> 13;
            case 44:
                return (rc.readSharedArray(22) & 64) >>> 6;
            case 45:
                return (rc.readSharedArray(23) & 32768) >>> 15;
            case 46:
                return (rc.readSharedArray(23) & 256) >>> 8;
            case 47:
                return (rc.readSharedArray(23) & 2) >>> 1;
            case 48:
                return (rc.readSharedArray(24) & 1024) >>> 10;
            case 49:
                return (rc.readSharedArray(24) & 8) >>> 3;
            case 50:
                return (rc.readSharedArray(25) & 4096) >>> 12;
            case 51:
                return (rc.readSharedArray(25) & 32) >>> 5;
            case 52:
                return (rc.readSharedArray(26) & 16384) >>> 14;
            case 53:
                return (rc.readSharedArray(26) & 128) >>> 7;
            case 54:
                return (rc.readSharedArray(26) & 1);
            case 55:
                return (rc.readSharedArray(27) & 512) >>> 9;
            case 56:
                return (rc.readSharedArray(27) & 4) >>> 2;
            case 57:
                return (rc.readSharedArray(28) & 2048) >>> 11;
            case 58:
                return (rc.readSharedArray(28) & 16) >>> 4;
            case 59:
                return (rc.readSharedArray(29) & 8192) >>> 13;
            case 60:
                return (rc.readSharedArray(29) & 64) >>> 6;
            case 61:
                return (rc.readSharedArray(30) & 32768) >>> 15;
            case 62:
                return (rc.readSharedArray(30) & 256) >>> 8;
            case 63:
                return (rc.readSharedArray(30) & 2) >>> 1;
            case 64:
                return (rc.readSharedArray(31) & 1024) >>> 10;
            case 65:
                return (rc.readSharedArray(31) & 8) >>> 3;
            case 66:
                return (rc.readSharedArray(32) & 4096) >>> 12;
            case 67:
                return (rc.readSharedArray(32) & 32) >>> 5;
            case 68:
                return (rc.readSharedArray(33) & 16384) >>> 14;
            case 69:
                return (rc.readSharedArray(33) & 128) >>> 7;
            case 70:
                return (rc.readSharedArray(33) & 1);
            case 71:
                return (rc.readSharedArray(34) & 512) >>> 9;
            case 72:
                return (rc.readSharedArray(34) & 4) >>> 2;
            case 73:
                return (rc.readSharedArray(35) & 2048) >>> 11;
            case 74:
                return (rc.readSharedArray(35) & 16) >>> 4;
            case 75:
                return (rc.readSharedArray(36) & 8192) >>> 13;
            case 76:
                return (rc.readSharedArray(36) & 64) >>> 6;
            case 77:
                return (rc.readSharedArray(37) & 32768) >>> 15;
            case 78:
                return (rc.readSharedArray(37) & 256) >>> 8;
            case 79:
                return (rc.readSharedArray(37) & 2) >>> 1;
            case 80:
                return (rc.readSharedArray(38) & 1024) >>> 10;
            case 81:
                return (rc.readSharedArray(38) & 8) >>> 3;
            case 82:
                return (rc.readSharedArray(39) & 4096) >>> 12;
            case 83:
                return (rc.readSharedArray(39) & 32) >>> 5;
            case 84:
                return (rc.readSharedArray(40) & 16384) >>> 14;
            case 85:
                return (rc.readSharedArray(40) & 128) >>> 7;
            case 86:
                return (rc.readSharedArray(40) & 1);
            case 87:
                return (rc.readSharedArray(41) & 512) >>> 9;
            case 88:
                return (rc.readSharedArray(41) & 4) >>> 2;
            case 89:
                return (rc.readSharedArray(42) & 2048) >>> 11;
            case 90:
                return (rc.readSharedArray(42) & 16) >>> 4;
            case 91:
                return (rc.readSharedArray(43) & 8192) >>> 13;
            case 92:
                return (rc.readSharedArray(43) & 64) >>> 6;
            case 93:
                return (rc.readSharedArray(44) & 32768) >>> 15;
            case 94:
                return (rc.readSharedArray(44) & 256) >>> 8;
            case 95:
                return (rc.readSharedArray(44) & 2) >>> 1;
            case 96:
                return (rc.readSharedArray(45) & 1024) >>> 10;
            case 97:
                return (rc.readSharedArray(45) & 8) >>> 3;
            case 98:
                return (rc.readSharedArray(46) & 4096) >>> 12;
            case 99:
                return (rc.readSharedArray(46) & 32) >>> 5;
            default:
                return -1;
        }
    }

    public static void writeSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 64511) | (value << 10));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65527) | (value << 3));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 61439) | (value << 12));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65503) | (value << 5));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 49151) | (value << 14));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65407) | (value << 7));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65534) | (value));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65023) | (value << 9));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65531) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 63487) | (value << 11));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65519) | (value << 4));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 57343) | (value << 13));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65471) | (value << 6));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 32767) | (value << 15));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65279) | (value << 8));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65533) | (value << 1));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 64511) | (value << 10));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65527) | (value << 3));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 61439) | (value << 12));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65503) | (value << 5));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 49151) | (value << 14));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65407) | (value << 7));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65534) | (value));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65023) | (value << 9));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65531) | (value << 2));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 63487) | (value << 11));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65519) | (value << 4));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 57343) | (value << 13));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65471) | (value << 6));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 32767) | (value << 15));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65279) | (value << 8));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65533) | (value << 1));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 64511) | (value << 10));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65527) | (value << 3));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 61439) | (value << 12));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65503) | (value << 5));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 49151) | (value << 14));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65407) | (value << 7));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65534) | (value));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65023) | (value << 9));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65531) | (value << 2));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 63487) | (value << 11));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65519) | (value << 4));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 57343) | (value << 13));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65471) | (value << 6));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 32767) | (value << 15));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65279) | (value << 8));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65533) | (value << 1));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 64511) | (value << 10));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65527) | (value << 3));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 61439) | (value << 12));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65503) | (value << 5));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 49151) | (value << 14));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65407) | (value << 7));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65534) | (value));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65023) | (value << 9));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65531) | (value << 2));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 63487) | (value << 11));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65519) | (value << 4));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 57343) | (value << 13));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65471) | (value << 6));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 32767) | (value << 15));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65279) | (value << 8));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65533) | (value << 1));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 64511) | (value << 10));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65527) | (value << 3));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 61439) | (value << 12));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65503) | (value << 5));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 49151) | (value << 14));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65407) | (value << 7));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65534) | (value));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65023) | (value << 9));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65531) | (value << 2));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 63487) | (value << 11));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65519) | (value << 4));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 57343) | (value << 13));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65471) | (value << 6));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 32767) | (value << 15));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65279) | (value << 8));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65533) | (value << 1));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 64511) | (value << 10));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65527) | (value << 3));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 61439) | (value << 12));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65503) | (value << 5));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 49151) | (value << 14));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65407) | (value << 7));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65534) | (value));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65023) | (value << 9));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65531) | (value << 2));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 63487) | (value << 11));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65519) | (value << 4));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 57343) | (value << 13));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65471) | (value << 6));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 32767) | (value << 15));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65279) | (value << 8));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65533) | (value << 1));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 64511) | (value << 10));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65527) | (value << 3));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 61439) | (value << 12));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65503) | (value << 5));
                break;
        }
    }

    public static void writeBPSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 64511) | (value << 10));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65527) | (value << 3));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 61439) | (value << 12));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65503) | (value << 5));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 49151) | (value << 14));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65407) | (value << 7));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65534) | (value));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65023) | (value << 9));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65531) | (value << 2));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 63487) | (value << 11));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65519) | (value << 4));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 57343) | (value << 13));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65471) | (value << 6));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 32767) | (value << 15));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 65279) | (value << 8));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65533) | (value << 1));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 64511) | (value << 10));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65527) | (value << 3));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 61439) | (value << 12));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65503) | (value << 5));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 49151) | (value << 14));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65407) | (value << 7));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65534) | (value));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 65023) | (value << 9));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65531) | (value << 2));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 63487) | (value << 11));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65519) | (value << 4));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 57343) | (value << 13));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65471) | (value << 6));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 32767) | (value << 15));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 65279) | (value << 8));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65533) | (value << 1));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 64511) | (value << 10));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65527) | (value << 3));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 61439) | (value << 12));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65503) | (value << 5));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 49151) | (value << 14));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65407) | (value << 7));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65534) | (value));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 65023) | (value << 9));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65531) | (value << 2));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 63487) | (value << 11));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65519) | (value << 4));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 57343) | (value << 13));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65471) | (value << 6));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 32767) | (value << 15));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 65279) | (value << 8));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65533) | (value << 1));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 64511) | (value << 10));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65527) | (value << 3));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 61439) | (value << 12));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65503) | (value << 5));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 49151) | (value << 14));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65407) | (value << 7));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65534) | (value));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 65023) | (value << 9));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65531) | (value << 2));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 63487) | (value << 11));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65519) | (value << 4));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 57343) | (value << 13));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65471) | (value << 6));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 32767) | (value << 15));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 65279) | (value << 8));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65533) | (value << 1));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 64511) | (value << 10));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65527) | (value << 3));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 61439) | (value << 12));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65503) | (value << 5));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 49151) | (value << 14));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65407) | (value << 7));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65534) | (value));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 65023) | (value << 9));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65531) | (value << 2));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 63487) | (value << 11));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65519) | (value << 4));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 57343) | (value << 13));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65471) | (value << 6));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 32767) | (value << 15));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 65279) | (value << 8));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65533) | (value << 1));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 64511) | (value << 10));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65527) | (value << 3));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 61439) | (value << 12));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65503) | (value << 5));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 49151) | (value << 14));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65407) | (value << 7));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65534) | (value));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 65023) | (value << 9));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65531) | (value << 2));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 63487) | (value << 11));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65519) | (value << 4));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 57343) | (value << 13));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65471) | (value << 6));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 32767) | (value << 15));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 65279) | (value << 8));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65533) | (value << 1));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 64511) | (value << 10));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65527) | (value << 3));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 61439) | (value << 12));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65503) | (value << 5));
                break;
        }
    }

    public static int readSectorManaFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 512) >>> 9;
            case 1:
                return (rc.readSharedArray(3) & 4) >>> 2;
            case 2:
                return (rc.readSharedArray(4) & 2048) >>> 11;
            case 3:
                return (rc.readSharedArray(4) & 16) >>> 4;
            case 4:
                return (rc.readSharedArray(5) & 8192) >>> 13;
            case 5:
                return (rc.readSharedArray(5) & 64) >>> 6;
            case 6:
                return (rc.readSharedArray(6) & 32768) >>> 15;
            case 7:
                return (rc.readSharedArray(6) & 256) >>> 8;
            case 8:
                return (rc.readSharedArray(6) & 2) >>> 1;
            case 9:
                return (rc.readSharedArray(7) & 1024) >>> 10;
            case 10:
                return (rc.readSharedArray(7) & 8) >>> 3;
            case 11:
                return (rc.readSharedArray(8) & 4096) >>> 12;
            case 12:
                return (rc.readSharedArray(8) & 32) >>> 5;
            case 13:
                return (rc.readSharedArray(9) & 16384) >>> 14;
            case 14:
                return (rc.readSharedArray(9) & 128) >>> 7;
            case 15:
                return (rc.readSharedArray(9) & 1);
            case 16:
                return (rc.readSharedArray(10) & 512) >>> 9;
            case 17:
                return (rc.readSharedArray(10) & 4) >>> 2;
            case 18:
                return (rc.readSharedArray(11) & 2048) >>> 11;
            case 19:
                return (rc.readSharedArray(11) & 16) >>> 4;
            case 20:
                return (rc.readSharedArray(12) & 8192) >>> 13;
            case 21:
                return (rc.readSharedArray(12) & 64) >>> 6;
            case 22:
                return (rc.readSharedArray(13) & 32768) >>> 15;
            case 23:
                return (rc.readSharedArray(13) & 256) >>> 8;
            case 24:
                return (rc.readSharedArray(13) & 2) >>> 1;
            case 25:
                return (rc.readSharedArray(14) & 1024) >>> 10;
            case 26:
                return (rc.readSharedArray(14) & 8) >>> 3;
            case 27:
                return (rc.readSharedArray(15) & 4096) >>> 12;
            case 28:
                return (rc.readSharedArray(15) & 32) >>> 5;
            case 29:
                return (rc.readSharedArray(16) & 16384) >>> 14;
            case 30:
                return (rc.readSharedArray(16) & 128) >>> 7;
            case 31:
                return (rc.readSharedArray(16) & 1);
            case 32:
                return (rc.readSharedArray(17) & 512) >>> 9;
            case 33:
                return (rc.readSharedArray(17) & 4) >>> 2;
            case 34:
                return (rc.readSharedArray(18) & 2048) >>> 11;
            case 35:
                return (rc.readSharedArray(18) & 16) >>> 4;
            case 36:
                return (rc.readSharedArray(19) & 8192) >>> 13;
            case 37:
                return (rc.readSharedArray(19) & 64) >>> 6;
            case 38:
                return (rc.readSharedArray(20) & 32768) >>> 15;
            case 39:
                return (rc.readSharedArray(20) & 256) >>> 8;
            case 40:
                return (rc.readSharedArray(20) & 2) >>> 1;
            case 41:
                return (rc.readSharedArray(21) & 1024) >>> 10;
            case 42:
                return (rc.readSharedArray(21) & 8) >>> 3;
            case 43:
                return (rc.readSharedArray(22) & 4096) >>> 12;
            case 44:
                return (rc.readSharedArray(22) & 32) >>> 5;
            case 45:
                return (rc.readSharedArray(23) & 16384) >>> 14;
            case 46:
                return (rc.readSharedArray(23) & 128) >>> 7;
            case 47:
                return (rc.readSharedArray(23) & 1);
            case 48:
                return (rc.readSharedArray(24) & 512) >>> 9;
            case 49:
                return (rc.readSharedArray(24) & 4) >>> 2;
            case 50:
                return (rc.readSharedArray(25) & 2048) >>> 11;
            case 51:
                return (rc.readSharedArray(25) & 16) >>> 4;
            case 52:
                return (rc.readSharedArray(26) & 8192) >>> 13;
            case 53:
                return (rc.readSharedArray(26) & 64) >>> 6;
            case 54:
                return (rc.readSharedArray(27) & 32768) >>> 15;
            case 55:
                return (rc.readSharedArray(27) & 256) >>> 8;
            case 56:
                return (rc.readSharedArray(27) & 2) >>> 1;
            case 57:
                return (rc.readSharedArray(28) & 1024) >>> 10;
            case 58:
                return (rc.readSharedArray(28) & 8) >>> 3;
            case 59:
                return (rc.readSharedArray(29) & 4096) >>> 12;
            case 60:
                return (rc.readSharedArray(29) & 32) >>> 5;
            case 61:
                return (rc.readSharedArray(30) & 16384) >>> 14;
            case 62:
                return (rc.readSharedArray(30) & 128) >>> 7;
            case 63:
                return (rc.readSharedArray(30) & 1);
            case 64:
                return (rc.readSharedArray(31) & 512) >>> 9;
            case 65:
                return (rc.readSharedArray(31) & 4) >>> 2;
            case 66:
                return (rc.readSharedArray(32) & 2048) >>> 11;
            case 67:
                return (rc.readSharedArray(32) & 16) >>> 4;
            case 68:
                return (rc.readSharedArray(33) & 8192) >>> 13;
            case 69:
                return (rc.readSharedArray(33) & 64) >>> 6;
            case 70:
                return (rc.readSharedArray(34) & 32768) >>> 15;
            case 71:
                return (rc.readSharedArray(34) & 256) >>> 8;
            case 72:
                return (rc.readSharedArray(34) & 2) >>> 1;
            case 73:
                return (rc.readSharedArray(35) & 1024) >>> 10;
            case 74:
                return (rc.readSharedArray(35) & 8) >>> 3;
            case 75:
                return (rc.readSharedArray(36) & 4096) >>> 12;
            case 76:
                return (rc.readSharedArray(36) & 32) >>> 5;
            case 77:
                return (rc.readSharedArray(37) & 16384) >>> 14;
            case 78:
                return (rc.readSharedArray(37) & 128) >>> 7;
            case 79:
                return (rc.readSharedArray(37) & 1);
            case 80:
                return (rc.readSharedArray(38) & 512) >>> 9;
            case 81:
                return (rc.readSharedArray(38) & 4) >>> 2;
            case 82:
                return (rc.readSharedArray(39) & 2048) >>> 11;
            case 83:
                return (rc.readSharedArray(39) & 16) >>> 4;
            case 84:
                return (rc.readSharedArray(40) & 8192) >>> 13;
            case 85:
                return (rc.readSharedArray(40) & 64) >>> 6;
            case 86:
                return (rc.readSharedArray(41) & 32768) >>> 15;
            case 87:
                return (rc.readSharedArray(41) & 256) >>> 8;
            case 88:
                return (rc.readSharedArray(41) & 2) >>> 1;
            case 89:
                return (rc.readSharedArray(42) & 1024) >>> 10;
            case 90:
                return (rc.readSharedArray(42) & 8) >>> 3;
            case 91:
                return (rc.readSharedArray(43) & 4096) >>> 12;
            case 92:
                return (rc.readSharedArray(43) & 32) >>> 5;
            case 93:
                return (rc.readSharedArray(44) & 16384) >>> 14;
            case 94:
                return (rc.readSharedArray(44) & 128) >>> 7;
            case 95:
                return (rc.readSharedArray(44) & 1);
            case 96:
                return (rc.readSharedArray(45) & 512) >>> 9;
            case 97:
                return (rc.readSharedArray(45) & 4) >>> 2;
            case 98:
                return (rc.readSharedArray(46) & 2048) >>> 11;
            case 99:
                return (rc.readSharedArray(46) & 16) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65023) | (value << 9));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65531) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 63487) | (value << 11));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65519) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 57343) | (value << 13));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65471) | (value << 6));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 32767) | (value << 15));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65279) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65533) | (value << 1));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 64511) | (value << 10));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65527) | (value << 3));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 61439) | (value << 12));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65503) | (value << 5));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 49151) | (value << 14));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65407) | (value << 7));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65534) | (value));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65023) | (value << 9));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65531) | (value << 2));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 63487) | (value << 11));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65519) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 57343) | (value << 13));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65471) | (value << 6));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 32767) | (value << 15));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65279) | (value << 8));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65533) | (value << 1));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 64511) | (value << 10));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65527) | (value << 3));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 61439) | (value << 12));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65503) | (value << 5));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 49151) | (value << 14));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65407) | (value << 7));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65534) | (value));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65023) | (value << 9));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65531) | (value << 2));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 63487) | (value << 11));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65519) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 57343) | (value << 13));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65471) | (value << 6));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 32767) | (value << 15));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65279) | (value << 8));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65533) | (value << 1));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 64511) | (value << 10));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65527) | (value << 3));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 61439) | (value << 12));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65503) | (value << 5));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 49151) | (value << 14));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65407) | (value << 7));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65534) | (value));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65023) | (value << 9));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65531) | (value << 2));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 63487) | (value << 11));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65519) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 57343) | (value << 13));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65471) | (value << 6));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 32767) | (value << 15));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65279) | (value << 8));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65533) | (value << 1));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 64511) | (value << 10));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65527) | (value << 3));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 61439) | (value << 12));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65503) | (value << 5));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 49151) | (value << 14));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65407) | (value << 7));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65534) | (value));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65023) | (value << 9));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65531) | (value << 2));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 63487) | (value << 11));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65519) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 57343) | (value << 13));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65471) | (value << 6));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 32767) | (value << 15));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65279) | (value << 8));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65533) | (value << 1));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 64511) | (value << 10));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65527) | (value << 3));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 61439) | (value << 12));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65503) | (value << 5));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 49151) | (value << 14));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65407) | (value << 7));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65534) | (value));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65023) | (value << 9));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65531) | (value << 2));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 63487) | (value << 11));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65519) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 57343) | (value << 13));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65471) | (value << 6));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 32767) | (value << 15));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65279) | (value << 8));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65533) | (value << 1));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 64511) | (value << 10));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65527) | (value << 3));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 61439) | (value << 12));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65503) | (value << 5));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 49151) | (value << 14));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65407) | (value << 7));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65534) | (value));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65023) | (value << 9));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65531) | (value << 2));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63487) | (value << 11));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65519) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65023) | (value << 9));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65531) | (value << 2));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 63487) | (value << 11));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65519) | (value << 4));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 57343) | (value << 13));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65471) | (value << 6));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 32767) | (value << 15));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65279) | (value << 8));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65533) | (value << 1));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 64511) | (value << 10));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65527) | (value << 3));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 61439) | (value << 12));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65503) | (value << 5));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 49151) | (value << 14));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 65407) | (value << 7));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65534) | (value));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 65023) | (value << 9));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65531) | (value << 2));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 63487) | (value << 11));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65519) | (value << 4));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 57343) | (value << 13));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65471) | (value << 6));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 32767) | (value << 15));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 65279) | (value << 8));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65533) | (value << 1));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 64511) | (value << 10));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65527) | (value << 3));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 61439) | (value << 12));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65503) | (value << 5));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 49151) | (value << 14));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 65407) | (value << 7));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65534) | (value));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 65023) | (value << 9));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65531) | (value << 2));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 63487) | (value << 11));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65519) | (value << 4));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 57343) | (value << 13));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65471) | (value << 6));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 32767) | (value << 15));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 65279) | (value << 8));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65533) | (value << 1));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 64511) | (value << 10));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65527) | (value << 3));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 61439) | (value << 12));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65503) | (value << 5));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 49151) | (value << 14));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 65407) | (value << 7));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65534) | (value));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 65023) | (value << 9));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65531) | (value << 2));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 63487) | (value << 11));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65519) | (value << 4));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 57343) | (value << 13));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65471) | (value << 6));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 32767) | (value << 15));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 65279) | (value << 8));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65533) | (value << 1));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 64511) | (value << 10));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65527) | (value << 3));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 61439) | (value << 12));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65503) | (value << 5));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 49151) | (value << 14));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 65407) | (value << 7));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65534) | (value));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 65023) | (value << 9));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65531) | (value << 2));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 63487) | (value << 11));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65519) | (value << 4));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 57343) | (value << 13));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65471) | (value << 6));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 32767) | (value << 15));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 65279) | (value << 8));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65533) | (value << 1));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 64511) | (value << 10));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65527) | (value << 3));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 61439) | (value << 12));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65503) | (value << 5));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 49151) | (value << 14));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 65407) | (value << 7));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65534) | (value));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 65023) | (value << 9));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65531) | (value << 2));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 63487) | (value << 11));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65519) | (value << 4));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 57343) | (value << 13));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65471) | (value << 6));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 32767) | (value << 15));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 65279) | (value << 8));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65533) | (value << 1));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 64511) | (value << 10));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65527) | (value << 3));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 61439) | (value << 12));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65503) | (value << 5));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 49151) | (value << 14));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 65407) | (value << 7));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65534) | (value));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 65023) | (value << 9));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65531) | (value << 2));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 63487) | (value << 11));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65519) | (value << 4));
                break;
        }
    }

    public static int readSectorElixirFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 256) >>> 8;
            case 1:
                return (rc.readSharedArray(3) & 2) >>> 1;
            case 2:
                return (rc.readSharedArray(4) & 1024) >>> 10;
            case 3:
                return (rc.readSharedArray(4) & 8) >>> 3;
            case 4:
                return (rc.readSharedArray(5) & 4096) >>> 12;
            case 5:
                return (rc.readSharedArray(5) & 32) >>> 5;
            case 6:
                return (rc.readSharedArray(6) & 16384) >>> 14;
            case 7:
                return (rc.readSharedArray(6) & 128) >>> 7;
            case 8:
                return (rc.readSharedArray(6) & 1);
            case 9:
                return (rc.readSharedArray(7) & 512) >>> 9;
            case 10:
                return (rc.readSharedArray(7) & 4) >>> 2;
            case 11:
                return (rc.readSharedArray(8) & 2048) >>> 11;
            case 12:
                return (rc.readSharedArray(8) & 16) >>> 4;
            case 13:
                return (rc.readSharedArray(9) & 8192) >>> 13;
            case 14:
                return (rc.readSharedArray(9) & 64) >>> 6;
            case 15:
                return (rc.readSharedArray(10) & 32768) >>> 15;
            case 16:
                return (rc.readSharedArray(10) & 256) >>> 8;
            case 17:
                return (rc.readSharedArray(10) & 2) >>> 1;
            case 18:
                return (rc.readSharedArray(11) & 1024) >>> 10;
            case 19:
                return (rc.readSharedArray(11) & 8) >>> 3;
            case 20:
                return (rc.readSharedArray(12) & 4096) >>> 12;
            case 21:
                return (rc.readSharedArray(12) & 32) >>> 5;
            case 22:
                return (rc.readSharedArray(13) & 16384) >>> 14;
            case 23:
                return (rc.readSharedArray(13) & 128) >>> 7;
            case 24:
                return (rc.readSharedArray(13) & 1);
            case 25:
                return (rc.readSharedArray(14) & 512) >>> 9;
            case 26:
                return (rc.readSharedArray(14) & 4) >>> 2;
            case 27:
                return (rc.readSharedArray(15) & 2048) >>> 11;
            case 28:
                return (rc.readSharedArray(15) & 16) >>> 4;
            case 29:
                return (rc.readSharedArray(16) & 8192) >>> 13;
            case 30:
                return (rc.readSharedArray(16) & 64) >>> 6;
            case 31:
                return (rc.readSharedArray(17) & 32768) >>> 15;
            case 32:
                return (rc.readSharedArray(17) & 256) >>> 8;
            case 33:
                return (rc.readSharedArray(17) & 2) >>> 1;
            case 34:
                return (rc.readSharedArray(18) & 1024) >>> 10;
            case 35:
                return (rc.readSharedArray(18) & 8) >>> 3;
            case 36:
                return (rc.readSharedArray(19) & 4096) >>> 12;
            case 37:
                return (rc.readSharedArray(19) & 32) >>> 5;
            case 38:
                return (rc.readSharedArray(20) & 16384) >>> 14;
            case 39:
                return (rc.readSharedArray(20) & 128) >>> 7;
            case 40:
                return (rc.readSharedArray(20) & 1);
            case 41:
                return (rc.readSharedArray(21) & 512) >>> 9;
            case 42:
                return (rc.readSharedArray(21) & 4) >>> 2;
            case 43:
                return (rc.readSharedArray(22) & 2048) >>> 11;
            case 44:
                return (rc.readSharedArray(22) & 16) >>> 4;
            case 45:
                return (rc.readSharedArray(23) & 8192) >>> 13;
            case 46:
                return (rc.readSharedArray(23) & 64) >>> 6;
            case 47:
                return (rc.readSharedArray(24) & 32768) >>> 15;
            case 48:
                return (rc.readSharedArray(24) & 256) >>> 8;
            case 49:
                return (rc.readSharedArray(24) & 2) >>> 1;
            case 50:
                return (rc.readSharedArray(25) & 1024) >>> 10;
            case 51:
                return (rc.readSharedArray(25) & 8) >>> 3;
            case 52:
                return (rc.readSharedArray(26) & 4096) >>> 12;
            case 53:
                return (rc.readSharedArray(26) & 32) >>> 5;
            case 54:
                return (rc.readSharedArray(27) & 16384) >>> 14;
            case 55:
                return (rc.readSharedArray(27) & 128) >>> 7;
            case 56:
                return (rc.readSharedArray(27) & 1);
            case 57:
                return (rc.readSharedArray(28) & 512) >>> 9;
            case 58:
                return (rc.readSharedArray(28) & 4) >>> 2;
            case 59:
                return (rc.readSharedArray(29) & 2048) >>> 11;
            case 60:
                return (rc.readSharedArray(29) & 16) >>> 4;
            case 61:
                return (rc.readSharedArray(30) & 8192) >>> 13;
            case 62:
                return (rc.readSharedArray(30) & 64) >>> 6;
            case 63:
                return (rc.readSharedArray(31) & 32768) >>> 15;
            case 64:
                return (rc.readSharedArray(31) & 256) >>> 8;
            case 65:
                return (rc.readSharedArray(31) & 2) >>> 1;
            case 66:
                return (rc.readSharedArray(32) & 1024) >>> 10;
            case 67:
                return (rc.readSharedArray(32) & 8) >>> 3;
            case 68:
                return (rc.readSharedArray(33) & 4096) >>> 12;
            case 69:
                return (rc.readSharedArray(33) & 32) >>> 5;
            case 70:
                return (rc.readSharedArray(34) & 16384) >>> 14;
            case 71:
                return (rc.readSharedArray(34) & 128) >>> 7;
            case 72:
                return (rc.readSharedArray(34) & 1);
            case 73:
                return (rc.readSharedArray(35) & 512) >>> 9;
            case 74:
                return (rc.readSharedArray(35) & 4) >>> 2;
            case 75:
                return (rc.readSharedArray(36) & 2048) >>> 11;
            case 76:
                return (rc.readSharedArray(36) & 16) >>> 4;
            case 77:
                return (rc.readSharedArray(37) & 8192) >>> 13;
            case 78:
                return (rc.readSharedArray(37) & 64) >>> 6;
            case 79:
                return (rc.readSharedArray(38) & 32768) >>> 15;
            case 80:
                return (rc.readSharedArray(38) & 256) >>> 8;
            case 81:
                return (rc.readSharedArray(38) & 2) >>> 1;
            case 82:
                return (rc.readSharedArray(39) & 1024) >>> 10;
            case 83:
                return (rc.readSharedArray(39) & 8) >>> 3;
            case 84:
                return (rc.readSharedArray(40) & 4096) >>> 12;
            case 85:
                return (rc.readSharedArray(40) & 32) >>> 5;
            case 86:
                return (rc.readSharedArray(41) & 16384) >>> 14;
            case 87:
                return (rc.readSharedArray(41) & 128) >>> 7;
            case 88:
                return (rc.readSharedArray(41) & 1);
            case 89:
                return (rc.readSharedArray(42) & 512) >>> 9;
            case 90:
                return (rc.readSharedArray(42) & 4) >>> 2;
            case 91:
                return (rc.readSharedArray(43) & 2048) >>> 11;
            case 92:
                return (rc.readSharedArray(43) & 16) >>> 4;
            case 93:
                return (rc.readSharedArray(44) & 8192) >>> 13;
            case 94:
                return (rc.readSharedArray(44) & 64) >>> 6;
            case 95:
                return (rc.readSharedArray(45) & 32768) >>> 15;
            case 96:
                return (rc.readSharedArray(45) & 256) >>> 8;
            case 97:
                return (rc.readSharedArray(45) & 2) >>> 1;
            case 98:
                return (rc.readSharedArray(46) & 1024) >>> 10;
            case 99:
                return (rc.readSharedArray(46) & 8) >>> 3;
            default:
                return -1;
        }
    }

    public static void writeSectorElixirFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65279) | (value << 8));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65533) | (value << 1));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 64511) | (value << 10));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65527) | (value << 3));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 61439) | (value << 12));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65503) | (value << 5));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 49151) | (value << 14));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65407) | (value << 7));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65534) | (value));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65023) | (value << 9));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65531) | (value << 2));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63487) | (value << 11));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65519) | (value << 4));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 57343) | (value << 13));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65471) | (value << 6));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 32767) | (value << 15));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65279) | (value << 8));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65533) | (value << 1));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 64511) | (value << 10));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65527) | (value << 3));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 61439) | (value << 12));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65503) | (value << 5));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 49151) | (value << 14));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65407) | (value << 7));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65534) | (value));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65023) | (value << 9));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65531) | (value << 2));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 63487) | (value << 11));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65519) | (value << 4));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 57343) | (value << 13));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65471) | (value << 6));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 32767) | (value << 15));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65279) | (value << 8));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65533) | (value << 1));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 64511) | (value << 10));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65527) | (value << 3));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 61439) | (value << 12));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65503) | (value << 5));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 49151) | (value << 14));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65407) | (value << 7));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65534) | (value));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65023) | (value << 9));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65531) | (value << 2));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 63487) | (value << 11));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65519) | (value << 4));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 57343) | (value << 13));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65471) | (value << 6));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 32767) | (value << 15));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65279) | (value << 8));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65533) | (value << 1));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 64511) | (value << 10));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65527) | (value << 3));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 61439) | (value << 12));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65503) | (value << 5));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 49151) | (value << 14));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65407) | (value << 7));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65534) | (value));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65023) | (value << 9));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65531) | (value << 2));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63487) | (value << 11));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65519) | (value << 4));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 57343) | (value << 13));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65471) | (value << 6));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 32767) | (value << 15));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65279) | (value << 8));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65533) | (value << 1));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 64511) | (value << 10));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65527) | (value << 3));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 61439) | (value << 12));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65503) | (value << 5));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 49151) | (value << 14));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65407) | (value << 7));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65534) | (value));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65023) | (value << 9));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65531) | (value << 2));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 63487) | (value << 11));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65519) | (value << 4));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 57343) | (value << 13));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65471) | (value << 6));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 32767) | (value << 15));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65279) | (value << 8));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65533) | (value << 1));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 64511) | (value << 10));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65527) | (value << 3));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 61439) | (value << 12));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65503) | (value << 5));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 49151) | (value << 14));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65407) | (value << 7));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65534) | (value));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65023) | (value << 9));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65531) | (value << 2));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63487) | (value << 11));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65519) | (value << 4));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 57343) | (value << 13));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65471) | (value << 6));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 32767) | (value << 15));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65279) | (value << 8));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65533) | (value << 1));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 64511) | (value << 10));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65527) | (value << 3));
                break;
        }
    }

    public static void writeBPSectorElixirFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65279) | (value << 8));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65533) | (value << 1));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 64511) | (value << 10));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65527) | (value << 3));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 61439) | (value << 12));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65503) | (value << 5));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 49151) | (value << 14));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65407) | (value << 7));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65534) | (value));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 65023) | (value << 9));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65531) | (value << 2));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 63487) | (value << 11));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65519) | (value << 4));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 57343) | (value << 13));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 65471) | (value << 6));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 32767) | (value << 15));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 65279) | (value << 8));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65533) | (value << 1));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 64511) | (value << 10));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65527) | (value << 3));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 61439) | (value << 12));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65503) | (value << 5));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 49151) | (value << 14));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 65407) | (value << 7));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65534) | (value));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 65023) | (value << 9));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65531) | (value << 2));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 63487) | (value << 11));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65519) | (value << 4));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 57343) | (value << 13));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 65471) | (value << 6));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 32767) | (value << 15));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 65279) | (value << 8));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65533) | (value << 1));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 64511) | (value << 10));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65527) | (value << 3));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 61439) | (value << 12));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65503) | (value << 5));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 49151) | (value << 14));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 65407) | (value << 7));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65534) | (value));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 65023) | (value << 9));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65531) | (value << 2));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 63487) | (value << 11));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65519) | (value << 4));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 57343) | (value << 13));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 65471) | (value << 6));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 32767) | (value << 15));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 65279) | (value << 8));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65533) | (value << 1));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 64511) | (value << 10));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65527) | (value << 3));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 61439) | (value << 12));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65503) | (value << 5));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 49151) | (value << 14));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 65407) | (value << 7));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65534) | (value));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 65023) | (value << 9));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65531) | (value << 2));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 63487) | (value << 11));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65519) | (value << 4));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 57343) | (value << 13));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 65471) | (value << 6));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 32767) | (value << 15));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 65279) | (value << 8));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65533) | (value << 1));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 64511) | (value << 10));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65527) | (value << 3));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 61439) | (value << 12));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65503) | (value << 5));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 49151) | (value << 14));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 65407) | (value << 7));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65534) | (value));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 65023) | (value << 9));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65531) | (value << 2));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 63487) | (value << 11));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65519) | (value << 4));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 57343) | (value << 13));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 65471) | (value << 6));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 32767) | (value << 15));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 65279) | (value << 8));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65533) | (value << 1));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 64511) | (value << 10));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65527) | (value << 3));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 61439) | (value << 12));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65503) | (value << 5));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 49151) | (value << 14));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 65407) | (value << 7));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65534) | (value));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 65023) | (value << 9));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65531) | (value << 2));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 63487) | (value << 11));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65519) | (value << 4));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 57343) | (value << 13));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 65471) | (value << 6));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 32767) | (value << 15));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 65279) | (value << 8));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65533) | (value << 1));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 64511) | (value << 10));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65527) | (value << 3));
                break;
        }
    }

    public static int readSectorControlStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 224) >>> 5;
            case 1:
                return ((rc.readSharedArray(3) & 1) << 2) + ((rc.readSharedArray(4) & 49152) >>> 14);
            case 2:
                return (rc.readSharedArray(4) & 896) >>> 7;
            case 3:
                return (rc.readSharedArray(4) & 7);
            case 4:
                return (rc.readSharedArray(5) & 3584) >>> 9;
            case 5:
                return (rc.readSharedArray(5) & 28) >>> 2;
            case 6:
                return (rc.readSharedArray(6) & 14336) >>> 11;
            case 7:
                return (rc.readSharedArray(6) & 112) >>> 4;
            case 8:
                return (rc.readSharedArray(7) & 57344) >>> 13;
            case 9:
                return (rc.readSharedArray(7) & 448) >>> 6;
            case 10:
                return ((rc.readSharedArray(7) & 3) << 1) + ((rc.readSharedArray(8) & 32768) >>> 15);
            case 11:
                return (rc.readSharedArray(8) & 1792) >>> 8;
            case 12:
                return (rc.readSharedArray(8) & 14) >>> 1;
            case 13:
                return (rc.readSharedArray(9) & 7168) >>> 10;
            case 14:
                return (rc.readSharedArray(9) & 56) >>> 3;
            case 15:
                return (rc.readSharedArray(10) & 28672) >>> 12;
            case 16:
                return (rc.readSharedArray(10) & 224) >>> 5;
            case 17:
                return ((rc.readSharedArray(10) & 1) << 2) + ((rc.readSharedArray(11) & 49152) >>> 14);
            case 18:
                return (rc.readSharedArray(11) & 896) >>> 7;
            case 19:
                return (rc.readSharedArray(11) & 7);
            case 20:
                return (rc.readSharedArray(12) & 3584) >>> 9;
            case 21:
                return (rc.readSharedArray(12) & 28) >>> 2;
            case 22:
                return (rc.readSharedArray(13) & 14336) >>> 11;
            case 23:
                return (rc.readSharedArray(13) & 112) >>> 4;
            case 24:
                return (rc.readSharedArray(14) & 57344) >>> 13;
            case 25:
                return (rc.readSharedArray(14) & 448) >>> 6;
            case 26:
                return ((rc.readSharedArray(14) & 3) << 1) + ((rc.readSharedArray(15) & 32768) >>> 15);
            case 27:
                return (rc.readSharedArray(15) & 1792) >>> 8;
            case 28:
                return (rc.readSharedArray(15) & 14) >>> 1;
            case 29:
                return (rc.readSharedArray(16) & 7168) >>> 10;
            case 30:
                return (rc.readSharedArray(16) & 56) >>> 3;
            case 31:
                return (rc.readSharedArray(17) & 28672) >>> 12;
            case 32:
                return (rc.readSharedArray(17) & 224) >>> 5;
            case 33:
                return ((rc.readSharedArray(17) & 1) << 2) + ((rc.readSharedArray(18) & 49152) >>> 14);
            case 34:
                return (rc.readSharedArray(18) & 896) >>> 7;
            case 35:
                return (rc.readSharedArray(18) & 7);
            case 36:
                return (rc.readSharedArray(19) & 3584) >>> 9;
            case 37:
                return (rc.readSharedArray(19) & 28) >>> 2;
            case 38:
                return (rc.readSharedArray(20) & 14336) >>> 11;
            case 39:
                return (rc.readSharedArray(20) & 112) >>> 4;
            case 40:
                return (rc.readSharedArray(21) & 57344) >>> 13;
            case 41:
                return (rc.readSharedArray(21) & 448) >>> 6;
            case 42:
                return ((rc.readSharedArray(21) & 3) << 1) + ((rc.readSharedArray(22) & 32768) >>> 15);
            case 43:
                return (rc.readSharedArray(22) & 1792) >>> 8;
            case 44:
                return (rc.readSharedArray(22) & 14) >>> 1;
            case 45:
                return (rc.readSharedArray(23) & 7168) >>> 10;
            case 46:
                return (rc.readSharedArray(23) & 56) >>> 3;
            case 47:
                return (rc.readSharedArray(24) & 28672) >>> 12;
            case 48:
                return (rc.readSharedArray(24) & 224) >>> 5;
            case 49:
                return ((rc.readSharedArray(24) & 1) << 2) + ((rc.readSharedArray(25) & 49152) >>> 14);
            case 50:
                return (rc.readSharedArray(25) & 896) >>> 7;
            case 51:
                return (rc.readSharedArray(25) & 7);
            case 52:
                return (rc.readSharedArray(26) & 3584) >>> 9;
            case 53:
                return (rc.readSharedArray(26) & 28) >>> 2;
            case 54:
                return (rc.readSharedArray(27) & 14336) >>> 11;
            case 55:
                return (rc.readSharedArray(27) & 112) >>> 4;
            case 56:
                return (rc.readSharedArray(28) & 57344) >>> 13;
            case 57:
                return (rc.readSharedArray(28) & 448) >>> 6;
            case 58:
                return ((rc.readSharedArray(28) & 3) << 1) + ((rc.readSharedArray(29) & 32768) >>> 15);
            case 59:
                return (rc.readSharedArray(29) & 1792) >>> 8;
            case 60:
                return (rc.readSharedArray(29) & 14) >>> 1;
            case 61:
                return (rc.readSharedArray(30) & 7168) >>> 10;
            case 62:
                return (rc.readSharedArray(30) & 56) >>> 3;
            case 63:
                return (rc.readSharedArray(31) & 28672) >>> 12;
            case 64:
                return (rc.readSharedArray(31) & 224) >>> 5;
            case 65:
                return ((rc.readSharedArray(31) & 1) << 2) + ((rc.readSharedArray(32) & 49152) >>> 14);
            case 66:
                return (rc.readSharedArray(32) & 896) >>> 7;
            case 67:
                return (rc.readSharedArray(32) & 7);
            case 68:
                return (rc.readSharedArray(33) & 3584) >>> 9;
            case 69:
                return (rc.readSharedArray(33) & 28) >>> 2;
            case 70:
                return (rc.readSharedArray(34) & 14336) >>> 11;
            case 71:
                return (rc.readSharedArray(34) & 112) >>> 4;
            case 72:
                return (rc.readSharedArray(35) & 57344) >>> 13;
            case 73:
                return (rc.readSharedArray(35) & 448) >>> 6;
            case 74:
                return ((rc.readSharedArray(35) & 3) << 1) + ((rc.readSharedArray(36) & 32768) >>> 15);
            case 75:
                return (rc.readSharedArray(36) & 1792) >>> 8;
            case 76:
                return (rc.readSharedArray(36) & 14) >>> 1;
            case 77:
                return (rc.readSharedArray(37) & 7168) >>> 10;
            case 78:
                return (rc.readSharedArray(37) & 56) >>> 3;
            case 79:
                return (rc.readSharedArray(38) & 28672) >>> 12;
            case 80:
                return (rc.readSharedArray(38) & 224) >>> 5;
            case 81:
                return ((rc.readSharedArray(38) & 1) << 2) + ((rc.readSharedArray(39) & 49152) >>> 14);
            case 82:
                return (rc.readSharedArray(39) & 896) >>> 7;
            case 83:
                return (rc.readSharedArray(39) & 7);
            case 84:
                return (rc.readSharedArray(40) & 3584) >>> 9;
            case 85:
                return (rc.readSharedArray(40) & 28) >>> 2;
            case 86:
                return (rc.readSharedArray(41) & 14336) >>> 11;
            case 87:
                return (rc.readSharedArray(41) & 112) >>> 4;
            case 88:
                return (rc.readSharedArray(42) & 57344) >>> 13;
            case 89:
                return (rc.readSharedArray(42) & 448) >>> 6;
            case 90:
                return ((rc.readSharedArray(42) & 3) << 1) + ((rc.readSharedArray(43) & 32768) >>> 15);
            case 91:
                return (rc.readSharedArray(43) & 1792) >>> 8;
            case 92:
                return (rc.readSharedArray(43) & 14) >>> 1;
            case 93:
                return (rc.readSharedArray(44) & 7168) >>> 10;
            case 94:
                return (rc.readSharedArray(44) & 56) >>> 3;
            case 95:
                return (rc.readSharedArray(45) & 28672) >>> 12;
            case 96:
                return (rc.readSharedArray(45) & 224) >>> 5;
            case 97:
                return ((rc.readSharedArray(45) & 1) << 2) + ((rc.readSharedArray(46) & 49152) >>> 14);
            case 98:
                return (rc.readSharedArray(46) & 896) >>> 7;
            case 99:
                return (rc.readSharedArray(46) & 7);
            default:
                return -1;
        }
    }

    public static void writeSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65311) | (value << 5));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 16383) | ((value & 3) << 14));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 64639) | (value << 7));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65528) | (value));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 61951) | (value << 9));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65507) | (value << 2));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 51199) | (value << 11));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65423) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 8191) | (value << 13));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65087) | (value << 6));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 32767) | ((value & 1) << 15));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63743) | (value << 8));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65521) | (value << 1));
                break;
            case 13:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 58367) | (value << 10));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65479) | (value << 3));
                break;
            case 15:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 36863) | (value << 12));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65311) | (value << 5));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 16383) | ((value & 3) << 14));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 64639) | (value << 7));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65528) | (value));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 61951) | (value << 9));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65507) | (value << 2));
                break;
            case 22:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 51199) | (value << 11));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65423) | (value << 4));
                break;
            case 24:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 8191) | (value << 13));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65087) | (value << 6));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 32767) | ((value & 1) << 15));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 63743) | (value << 8));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65521) | (value << 1));
                break;
            case 29:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 58367) | (value << 10));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65479) | (value << 3));
                break;
            case 31:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 36863) | (value << 12));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65311) | (value << 5));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 16383) | ((value & 3) << 14));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 64639) | (value << 7));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65528) | (value));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 61951) | (value << 9));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65507) | (value << 2));
                break;
            case 38:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 51199) | (value << 11));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65423) | (value << 4));
                break;
            case 40:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 8191) | (value << 13));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65087) | (value << 6));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 32767) | ((value & 1) << 15));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 63743) | (value << 8));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65521) | (value << 1));
                break;
            case 45:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 58367) | (value << 10));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65479) | (value << 3));
                break;
            case 47:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 36863) | (value << 12));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65311) | (value << 5));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 16383) | ((value & 3) << 14));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 64639) | (value << 7));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65528) | (value));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 61951) | (value << 9));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65507) | (value << 2));
                break;
            case 54:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 51199) | (value << 11));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65423) | (value << 4));
                break;
            case 56:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 8191) | (value << 13));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65087) | (value << 6));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 32767) | ((value & 1) << 15));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63743) | (value << 8));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65521) | (value << 1));
                break;
            case 61:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 58367) | (value << 10));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65479) | (value << 3));
                break;
            case 63:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 36863) | (value << 12));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65311) | (value << 5));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 16383) | ((value & 3) << 14));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 64639) | (value << 7));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65528) | (value));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 61951) | (value << 9));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65507) | (value << 2));
                break;
            case 70:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 51199) | (value << 11));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65423) | (value << 4));
                break;
            case 72:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 8191) | (value << 13));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65087) | (value << 6));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 32767) | ((value & 1) << 15));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 63743) | (value << 8));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65521) | (value << 1));
                break;
            case 77:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 58367) | (value << 10));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65479) | (value << 3));
                break;
            case 79:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 36863) | (value << 12));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65311) | (value << 5));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 16383) | ((value & 3) << 14));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 64639) | (value << 7));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65528) | (value));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 61951) | (value << 9));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65507) | (value << 2));
                break;
            case 86:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 51199) | (value << 11));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65423) | (value << 4));
                break;
            case 88:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 8191) | (value << 13));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65087) | (value << 6));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 32767) | ((value & 1) << 15));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63743) | (value << 8));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65521) | (value << 1));
                break;
            case 93:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 58367) | (value << 10));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65479) | (value << 3));
                break;
            case 95:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 36863) | (value << 12));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65311) | (value << 5));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 16383) | ((value & 3) << 14));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 64639) | (value << 7));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65528) | (value));
                break;
        }
    }

    public static void writeBPSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65311) | (value << 5));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(4, (bufferPool[4] & 16383) | ((value & 3) << 14));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 64639) | (value << 7));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65528) | (value));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 61951) | (value << 9));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65507) | (value << 2));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 51199) | (value << 11));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65423) | (value << 4));
                break;
            case 8:
                writeToBufferPool(7, (bufferPool[7] & 8191) | (value << 13));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 65087) | (value << 6));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(8, (bufferPool[8] & 32767) | ((value & 1) << 15));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 63743) | (value << 8));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65521) | (value << 1));
                break;
            case 13:
                writeToBufferPool(9, (bufferPool[9] & 58367) | (value << 10));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 65479) | (value << 3));
                break;
            case 15:
                writeToBufferPool(10, (bufferPool[10] & 36863) | (value << 12));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 65311) | (value << 5));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(11, (bufferPool[11] & 16383) | ((value & 3) << 14));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 64639) | (value << 7));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65528) | (value));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 61951) | (value << 9));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65507) | (value << 2));
                break;
            case 22:
                writeToBufferPool(13, (bufferPool[13] & 51199) | (value << 11));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 65423) | (value << 4));
                break;
            case 24:
                writeToBufferPool(14, (bufferPool[14] & 8191) | (value << 13));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 65087) | (value << 6));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(15, (bufferPool[15] & 32767) | ((value & 1) << 15));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 63743) | (value << 8));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65521) | (value << 1));
                break;
            case 29:
                writeToBufferPool(16, (bufferPool[16] & 58367) | (value << 10));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 65479) | (value << 3));
                break;
            case 31:
                writeToBufferPool(17, (bufferPool[17] & 36863) | (value << 12));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 65311) | (value << 5));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(18, (bufferPool[18] & 16383) | ((value & 3) << 14));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 64639) | (value << 7));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65528) | (value));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 61951) | (value << 9));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65507) | (value << 2));
                break;
            case 38:
                writeToBufferPool(20, (bufferPool[20] & 51199) | (value << 11));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 65423) | (value << 4));
                break;
            case 40:
                writeToBufferPool(21, (bufferPool[21] & 8191) | (value << 13));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 65087) | (value << 6));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(22, (bufferPool[22] & 32767) | ((value & 1) << 15));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 63743) | (value << 8));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65521) | (value << 1));
                break;
            case 45:
                writeToBufferPool(23, (bufferPool[23] & 58367) | (value << 10));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 65479) | (value << 3));
                break;
            case 47:
                writeToBufferPool(24, (bufferPool[24] & 36863) | (value << 12));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 65311) | (value << 5));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(25, (bufferPool[25] & 16383) | ((value & 3) << 14));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 64639) | (value << 7));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65528) | (value));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 61951) | (value << 9));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65507) | (value << 2));
                break;
            case 54:
                writeToBufferPool(27, (bufferPool[27] & 51199) | (value << 11));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 65423) | (value << 4));
                break;
            case 56:
                writeToBufferPool(28, (bufferPool[28] & 8191) | (value << 13));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 65087) | (value << 6));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(29, (bufferPool[29] & 32767) | ((value & 1) << 15));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 63743) | (value << 8));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65521) | (value << 1));
                break;
            case 61:
                writeToBufferPool(30, (bufferPool[30] & 58367) | (value << 10));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 65479) | (value << 3));
                break;
            case 63:
                writeToBufferPool(31, (bufferPool[31] & 36863) | (value << 12));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 65311) | (value << 5));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(32, (bufferPool[32] & 16383) | ((value & 3) << 14));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 64639) | (value << 7));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65528) | (value));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 61951) | (value << 9));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65507) | (value << 2));
                break;
            case 70:
                writeToBufferPool(34, (bufferPool[34] & 51199) | (value << 11));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 65423) | (value << 4));
                break;
            case 72:
                writeToBufferPool(35, (bufferPool[35] & 8191) | (value << 13));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 65087) | (value << 6));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(36, (bufferPool[36] & 32767) | ((value & 1) << 15));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 63743) | (value << 8));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65521) | (value << 1));
                break;
            case 77:
                writeToBufferPool(37, (bufferPool[37] & 58367) | (value << 10));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 65479) | (value << 3));
                break;
            case 79:
                writeToBufferPool(38, (bufferPool[38] & 36863) | (value << 12));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 65311) | (value << 5));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(39, (bufferPool[39] & 16383) | ((value & 3) << 14));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 64639) | (value << 7));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65528) | (value));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 61951) | (value << 9));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65507) | (value << 2));
                break;
            case 86:
                writeToBufferPool(41, (bufferPool[41] & 51199) | (value << 11));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 65423) | (value << 4));
                break;
            case 88:
                writeToBufferPool(42, (bufferPool[42] & 8191) | (value << 13));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 65087) | (value << 6));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(43, (bufferPool[43] & 32767) | ((value & 1) << 15));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 63743) | (value << 8));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65521) | (value << 1));
                break;
            case 93:
                writeToBufferPool(44, (bufferPool[44] & 58367) | (value << 10));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 65479) | (value << 3));
                break;
            case 95:
                writeToBufferPool(45, (bufferPool[45] & 36863) | (value << 12));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 65311) | (value << 5));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(46, (bufferPool[46] & 16383) | ((value & 3) << 14));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 64639) | (value << 7));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65528) | (value));
                break;
        }
    }

    public static int readSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 4064) >>> 5;
            case 1:
                return ((rc.readSharedArray(3) & 31) << 2) + ((rc.readSharedArray(4) & 49152) >>> 14);
            case 2:
                return (rc.readSharedArray(4) & 16256) >>> 7;
            case 3:
                return (rc.readSharedArray(4) & 127);
            case 4:
                return (rc.readSharedArray(5) & 65024) >>> 9;
            case 5:
                return (rc.readSharedArray(5) & 508) >>> 2;
            case 6:
                return ((rc.readSharedArray(5) & 3) << 5) + ((rc.readSharedArray(6) & 63488) >>> 11);
            case 7:
                return (rc.readSharedArray(6) & 2032) >>> 4;
            case 8:
                return ((rc.readSharedArray(6) & 15) << 3) + ((rc.readSharedArray(7) & 57344) >>> 13);
            case 9:
                return (rc.readSharedArray(7) & 8128) >>> 6;
            case 10:
                return ((rc.readSharedArray(7) & 63) << 1) + ((rc.readSharedArray(8) & 32768) >>> 15);
            case 11:
                return (rc.readSharedArray(8) & 32512) >>> 8;
            case 12:
                return (rc.readSharedArray(8) & 254) >>> 1;
            case 13:
                return ((rc.readSharedArray(8) & 1) << 6) + ((rc.readSharedArray(9) & 64512) >>> 10);
            case 14:
                return (rc.readSharedArray(9) & 1016) >>> 3;
            case 15:
                return ((rc.readSharedArray(9) & 7) << 4) + ((rc.readSharedArray(10) & 61440) >>> 12);
            case 16:
                return (rc.readSharedArray(10) & 4064) >>> 5;
            case 17:
                return ((rc.readSharedArray(10) & 31) << 2) + ((rc.readSharedArray(11) & 49152) >>> 14);
            case 18:
                return (rc.readSharedArray(11) & 16256) >>> 7;
            case 19:
                return (rc.readSharedArray(11) & 127);
            case 20:
                return (rc.readSharedArray(12) & 65024) >>> 9;
            case 21:
                return (rc.readSharedArray(12) & 508) >>> 2;
            case 22:
                return ((rc.readSharedArray(12) & 3) << 5) + ((rc.readSharedArray(13) & 63488) >>> 11);
            case 23:
                return (rc.readSharedArray(13) & 2032) >>> 4;
            case 24:
                return ((rc.readSharedArray(13) & 15) << 3) + ((rc.readSharedArray(14) & 57344) >>> 13);
            case 25:
                return (rc.readSharedArray(14) & 8128) >>> 6;
            case 26:
                return ((rc.readSharedArray(14) & 63) << 1) + ((rc.readSharedArray(15) & 32768) >>> 15);
            case 27:
                return (rc.readSharedArray(15) & 32512) >>> 8;
            case 28:
                return (rc.readSharedArray(15) & 254) >>> 1;
            case 29:
                return ((rc.readSharedArray(15) & 1) << 6) + ((rc.readSharedArray(16) & 64512) >>> 10);
            case 30:
                return (rc.readSharedArray(16) & 1016) >>> 3;
            case 31:
                return ((rc.readSharedArray(16) & 7) << 4) + ((rc.readSharedArray(17) & 61440) >>> 12);
            case 32:
                return (rc.readSharedArray(17) & 4064) >>> 5;
            case 33:
                return ((rc.readSharedArray(17) & 31) << 2) + ((rc.readSharedArray(18) & 49152) >>> 14);
            case 34:
                return (rc.readSharedArray(18) & 16256) >>> 7;
            case 35:
                return (rc.readSharedArray(18) & 127);
            case 36:
                return (rc.readSharedArray(19) & 65024) >>> 9;
            case 37:
                return (rc.readSharedArray(19) & 508) >>> 2;
            case 38:
                return ((rc.readSharedArray(19) & 3) << 5) + ((rc.readSharedArray(20) & 63488) >>> 11);
            case 39:
                return (rc.readSharedArray(20) & 2032) >>> 4;
            case 40:
                return ((rc.readSharedArray(20) & 15) << 3) + ((rc.readSharedArray(21) & 57344) >>> 13);
            case 41:
                return (rc.readSharedArray(21) & 8128) >>> 6;
            case 42:
                return ((rc.readSharedArray(21) & 63) << 1) + ((rc.readSharedArray(22) & 32768) >>> 15);
            case 43:
                return (rc.readSharedArray(22) & 32512) >>> 8;
            case 44:
                return (rc.readSharedArray(22) & 254) >>> 1;
            case 45:
                return ((rc.readSharedArray(22) & 1) << 6) + ((rc.readSharedArray(23) & 64512) >>> 10);
            case 46:
                return (rc.readSharedArray(23) & 1016) >>> 3;
            case 47:
                return ((rc.readSharedArray(23) & 7) << 4) + ((rc.readSharedArray(24) & 61440) >>> 12);
            case 48:
                return (rc.readSharedArray(24) & 4064) >>> 5;
            case 49:
                return ((rc.readSharedArray(24) & 31) << 2) + ((rc.readSharedArray(25) & 49152) >>> 14);
            case 50:
                return (rc.readSharedArray(25) & 16256) >>> 7;
            case 51:
                return (rc.readSharedArray(25) & 127);
            case 52:
                return (rc.readSharedArray(26) & 65024) >>> 9;
            case 53:
                return (rc.readSharedArray(26) & 508) >>> 2;
            case 54:
                return ((rc.readSharedArray(26) & 3) << 5) + ((rc.readSharedArray(27) & 63488) >>> 11);
            case 55:
                return (rc.readSharedArray(27) & 2032) >>> 4;
            case 56:
                return ((rc.readSharedArray(27) & 15) << 3) + ((rc.readSharedArray(28) & 57344) >>> 13);
            case 57:
                return (rc.readSharedArray(28) & 8128) >>> 6;
            case 58:
                return ((rc.readSharedArray(28) & 63) << 1) + ((rc.readSharedArray(29) & 32768) >>> 15);
            case 59:
                return (rc.readSharedArray(29) & 32512) >>> 8;
            case 60:
                return (rc.readSharedArray(29) & 254) >>> 1;
            case 61:
                return ((rc.readSharedArray(29) & 1) << 6) + ((rc.readSharedArray(30) & 64512) >>> 10);
            case 62:
                return (rc.readSharedArray(30) & 1016) >>> 3;
            case 63:
                return ((rc.readSharedArray(30) & 7) << 4) + ((rc.readSharedArray(31) & 61440) >>> 12);
            case 64:
                return (rc.readSharedArray(31) & 4064) >>> 5;
            case 65:
                return ((rc.readSharedArray(31) & 31) << 2) + ((rc.readSharedArray(32) & 49152) >>> 14);
            case 66:
                return (rc.readSharedArray(32) & 16256) >>> 7;
            case 67:
                return (rc.readSharedArray(32) & 127);
            case 68:
                return (rc.readSharedArray(33) & 65024) >>> 9;
            case 69:
                return (rc.readSharedArray(33) & 508) >>> 2;
            case 70:
                return ((rc.readSharedArray(33) & 3) << 5) + ((rc.readSharedArray(34) & 63488) >>> 11);
            case 71:
                return (rc.readSharedArray(34) & 2032) >>> 4;
            case 72:
                return ((rc.readSharedArray(34) & 15) << 3) + ((rc.readSharedArray(35) & 57344) >>> 13);
            case 73:
                return (rc.readSharedArray(35) & 8128) >>> 6;
            case 74:
                return ((rc.readSharedArray(35) & 63) << 1) + ((rc.readSharedArray(36) & 32768) >>> 15);
            case 75:
                return (rc.readSharedArray(36) & 32512) >>> 8;
            case 76:
                return (rc.readSharedArray(36) & 254) >>> 1;
            case 77:
                return ((rc.readSharedArray(36) & 1) << 6) + ((rc.readSharedArray(37) & 64512) >>> 10);
            case 78:
                return (rc.readSharedArray(37) & 1016) >>> 3;
            case 79:
                return ((rc.readSharedArray(37) & 7) << 4) + ((rc.readSharedArray(38) & 61440) >>> 12);
            case 80:
                return (rc.readSharedArray(38) & 4064) >>> 5;
            case 81:
                return ((rc.readSharedArray(38) & 31) << 2) + ((rc.readSharedArray(39) & 49152) >>> 14);
            case 82:
                return (rc.readSharedArray(39) & 16256) >>> 7;
            case 83:
                return (rc.readSharedArray(39) & 127);
            case 84:
                return (rc.readSharedArray(40) & 65024) >>> 9;
            case 85:
                return (rc.readSharedArray(40) & 508) >>> 2;
            case 86:
                return ((rc.readSharedArray(40) & 3) << 5) + ((rc.readSharedArray(41) & 63488) >>> 11);
            case 87:
                return (rc.readSharedArray(41) & 2032) >>> 4;
            case 88:
                return ((rc.readSharedArray(41) & 15) << 3) + ((rc.readSharedArray(42) & 57344) >>> 13);
            case 89:
                return (rc.readSharedArray(42) & 8128) >>> 6;
            case 90:
                return ((rc.readSharedArray(42) & 63) << 1) + ((rc.readSharedArray(43) & 32768) >>> 15);
            case 91:
                return (rc.readSharedArray(43) & 32512) >>> 8;
            case 92:
                return (rc.readSharedArray(43) & 254) >>> 1;
            case 93:
                return ((rc.readSharedArray(43) & 1) << 6) + ((rc.readSharedArray(44) & 64512) >>> 10);
            case 94:
                return (rc.readSharedArray(44) & 1016) >>> 3;
            case 95:
                return ((rc.readSharedArray(44) & 7) << 4) + ((rc.readSharedArray(45) & 61440) >>> 12);
            case 96:
                return (rc.readSharedArray(45) & 4064) >>> 5;
            case 97:
                return ((rc.readSharedArray(45) & 31) << 2) + ((rc.readSharedArray(46) & 49152) >>> 14);
            case 98:
                return (rc.readSharedArray(46) & 16256) >>> 7;
            case 99:
                return (rc.readSharedArray(46) & 127);
            default:
                return -1;
        }
    }

    public static void writeSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 61471) | (value << 5));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 16383) | ((value & 3) << 14));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 49279) | (value << 7));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65408) | (value));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 511) | (value << 9));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65027) | (value << 2));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 2047) | ((value & 31) << 11));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 63503) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 8191) | ((value & 7) << 13));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 57407) | (value << 6));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 32767) | ((value & 1) << 15));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 33023) | (value << 8));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65281) | (value << 1));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 1023) | ((value & 63) << 10));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 64519) | (value << 3));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 4095) | ((value & 15) << 12));
                break;
            case 16:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 61471) | (value << 5));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 16383) | ((value & 3) << 14));
                break;
            case 18:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 49279) | (value << 7));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65408) | (value));
                break;
            case 20:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 511) | (value << 9));
                break;
            case 21:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65027) | (value << 2));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 2047) | ((value & 31) << 11));
                break;
            case 23:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 63503) | (value << 4));
                break;
            case 24:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 8191) | ((value & 7) << 13));
                break;
            case 25:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 57407) | (value << 6));
                break;
            case 26:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 32767) | ((value & 1) << 15));
                break;
            case 27:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 33023) | (value << 8));
                break;
            case 28:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65281) | (value << 1));
                break;
            case 29:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 1023) | ((value & 63) << 10));
                break;
            case 30:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 64519) | (value << 3));
                break;
            case 31:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 4095) | ((value & 15) << 12));
                break;
            case 32:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 61471) | (value << 5));
                break;
            case 33:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 16383) | ((value & 3) << 14));
                break;
            case 34:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 49279) | (value << 7));
                break;
            case 35:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65408) | (value));
                break;
            case 36:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 511) | (value << 9));
                break;
            case 37:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65027) | (value << 2));
                break;
            case 38:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 2047) | ((value & 31) << 11));
                break;
            case 39:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63503) | (value << 4));
                break;
            case 40:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 8191) | ((value & 7) << 13));
                break;
            case 41:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 57407) | (value << 6));
                break;
            case 42:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 32767) | ((value & 1) << 15));
                break;
            case 43:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 33023) | (value << 8));
                break;
            case 44:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65281) | (value << 1));
                break;
            case 45:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 1023) | ((value & 63) << 10));
                break;
            case 46:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 64519) | (value << 3));
                break;
            case 47:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 4095) | ((value & 15) << 12));
                break;
            case 48:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 61471) | (value << 5));
                break;
            case 49:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 16383) | ((value & 3) << 14));
                break;
            case 50:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 49279) | (value << 7));
                break;
            case 51:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65408) | (value));
                break;
            case 52:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 511) | (value << 9));
                break;
            case 53:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65027) | (value << 2));
                break;
            case 54:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 2047) | ((value & 31) << 11));
                break;
            case 55:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 63503) | (value << 4));
                break;
            case 56:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 8191) | ((value & 7) << 13));
                break;
            case 57:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 57407) | (value << 6));
                break;
            case 58:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 32767) | ((value & 1) << 15));
                break;
            case 59:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 33023) | (value << 8));
                break;
            case 60:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65281) | (value << 1));
                break;
            case 61:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 1023) | ((value & 63) << 10));
                break;
            case 62:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 64519) | (value << 3));
                break;
            case 63:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 4095) | ((value & 15) << 12));
                break;
            case 64:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 61471) | (value << 5));
                break;
            case 65:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 16383) | ((value & 3) << 14));
                break;
            case 66:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 49279) | (value << 7));
                break;
            case 67:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65408) | (value));
                break;
            case 68:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 511) | (value << 9));
                break;
            case 69:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65027) | (value << 2));
                break;
            case 70:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 2047) | ((value & 31) << 11));
                break;
            case 71:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 63503) | (value << 4));
                break;
            case 72:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 8191) | ((value & 7) << 13));
                break;
            case 73:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 57407) | (value << 6));
                break;
            case 74:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 32767) | ((value & 1) << 15));
                break;
            case 75:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 33023) | (value << 8));
                break;
            case 76:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65281) | (value << 1));
                break;
            case 77:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 1023) | ((value & 63) << 10));
                break;
            case 78:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 64519) | (value << 3));
                break;
            case 79:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 4095) | ((value & 15) << 12));
                break;
            case 80:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 61471) | (value << 5));
                break;
            case 81:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 16383) | ((value & 3) << 14));
                break;
            case 82:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 49279) | (value << 7));
                break;
            case 83:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65408) | (value));
                break;
            case 84:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 511) | (value << 9));
                break;
            case 85:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65027) | (value << 2));
                break;
            case 86:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 2047) | ((value & 31) << 11));
                break;
            case 87:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63503) | (value << 4));
                break;
            case 88:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 8191) | ((value & 7) << 13));
                break;
            case 89:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 57407) | (value << 6));
                break;
            case 90:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 32767) | ((value & 1) << 15));
                break;
            case 91:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 33023) | (value << 8));
                break;
            case 92:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65281) | (value << 1));
                break;
            case 93:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 1023) | ((value & 63) << 10));
                break;
            case 94:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 64519) | (value << 3));
                break;
            case 95:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 4095) | ((value & 15) << 12));
                break;
            case 96:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 61471) | (value << 5));
                break;
            case 97:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 16383) | ((value & 3) << 14));
                break;
            case 98:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 49279) | (value << 7));
                break;
            case 99:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65408) | (value));
                break;
        }
    }

    public static void writeBPSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 61471) | (value << 5));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(4, (bufferPool[4] & 16383) | ((value & 3) << 14));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 49279) | (value << 7));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65408) | (value));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 511) | (value << 9));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65027) | (value << 2));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(6, (bufferPool[6] & 2047) | ((value & 31) << 11));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 63503) | (value << 4));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(7, (bufferPool[7] & 8191) | ((value & 7) << 13));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 57407) | (value << 6));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(8, (bufferPool[8] & 32767) | ((value & 1) << 15));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 33023) | (value << 8));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65281) | (value << 1));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(9, (bufferPool[9] & 1023) | ((value & 63) << 10));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 64519) | (value << 3));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(10, (bufferPool[10] & 4095) | ((value & 15) << 12));
                break;
            case 16:
                writeToBufferPool(10, (bufferPool[10] & 61471) | (value << 5));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(11, (bufferPool[11] & 16383) | ((value & 3) << 14));
                break;
            case 18:
                writeToBufferPool(11, (bufferPool[11] & 49279) | (value << 7));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 65408) | (value));
                break;
            case 20:
                writeToBufferPool(12, (bufferPool[12] & 511) | (value << 9));
                break;
            case 21:
                writeToBufferPool(12, (bufferPool[12] & 65027) | (value << 2));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(13, (bufferPool[13] & 2047) | ((value & 31) << 11));
                break;
            case 23:
                writeToBufferPool(13, (bufferPool[13] & 63503) | (value << 4));
                break;
            case 24:
                writeToBufferPool(13, (bufferPool[13] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(14, (bufferPool[14] & 8191) | ((value & 7) << 13));
                break;
            case 25:
                writeToBufferPool(14, (bufferPool[14] & 57407) | (value << 6));
                break;
            case 26:
                writeToBufferPool(14, (bufferPool[14] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(15, (bufferPool[15] & 32767) | ((value & 1) << 15));
                break;
            case 27:
                writeToBufferPool(15, (bufferPool[15] & 33023) | (value << 8));
                break;
            case 28:
                writeToBufferPool(15, (bufferPool[15] & 65281) | (value << 1));
                break;
            case 29:
                writeToBufferPool(15, (bufferPool[15] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(16, (bufferPool[16] & 1023) | ((value & 63) << 10));
                break;
            case 30:
                writeToBufferPool(16, (bufferPool[16] & 64519) | (value << 3));
                break;
            case 31:
                writeToBufferPool(16, (bufferPool[16] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(17, (bufferPool[17] & 4095) | ((value & 15) << 12));
                break;
            case 32:
                writeToBufferPool(17, (bufferPool[17] & 61471) | (value << 5));
                break;
            case 33:
                writeToBufferPool(17, (bufferPool[17] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(18, (bufferPool[18] & 16383) | ((value & 3) << 14));
                break;
            case 34:
                writeToBufferPool(18, (bufferPool[18] & 49279) | (value << 7));
                break;
            case 35:
                writeToBufferPool(18, (bufferPool[18] & 65408) | (value));
                break;
            case 36:
                writeToBufferPool(19, (bufferPool[19] & 511) | (value << 9));
                break;
            case 37:
                writeToBufferPool(19, (bufferPool[19] & 65027) | (value << 2));
                break;
            case 38:
                writeToBufferPool(19, (bufferPool[19] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(20, (bufferPool[20] & 2047) | ((value & 31) << 11));
                break;
            case 39:
                writeToBufferPool(20, (bufferPool[20] & 63503) | (value << 4));
                break;
            case 40:
                writeToBufferPool(20, (bufferPool[20] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(21, (bufferPool[21] & 8191) | ((value & 7) << 13));
                break;
            case 41:
                writeToBufferPool(21, (bufferPool[21] & 57407) | (value << 6));
                break;
            case 42:
                writeToBufferPool(21, (bufferPool[21] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(22, (bufferPool[22] & 32767) | ((value & 1) << 15));
                break;
            case 43:
                writeToBufferPool(22, (bufferPool[22] & 33023) | (value << 8));
                break;
            case 44:
                writeToBufferPool(22, (bufferPool[22] & 65281) | (value << 1));
                break;
            case 45:
                writeToBufferPool(22, (bufferPool[22] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(23, (bufferPool[23] & 1023) | ((value & 63) << 10));
                break;
            case 46:
                writeToBufferPool(23, (bufferPool[23] & 64519) | (value << 3));
                break;
            case 47:
                writeToBufferPool(23, (bufferPool[23] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(24, (bufferPool[24] & 4095) | ((value & 15) << 12));
                break;
            case 48:
                writeToBufferPool(24, (bufferPool[24] & 61471) | (value << 5));
                break;
            case 49:
                writeToBufferPool(24, (bufferPool[24] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(25, (bufferPool[25] & 16383) | ((value & 3) << 14));
                break;
            case 50:
                writeToBufferPool(25, (bufferPool[25] & 49279) | (value << 7));
                break;
            case 51:
                writeToBufferPool(25, (bufferPool[25] & 65408) | (value));
                break;
            case 52:
                writeToBufferPool(26, (bufferPool[26] & 511) | (value << 9));
                break;
            case 53:
                writeToBufferPool(26, (bufferPool[26] & 65027) | (value << 2));
                break;
            case 54:
                writeToBufferPool(26, (bufferPool[26] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(27, (bufferPool[27] & 2047) | ((value & 31) << 11));
                break;
            case 55:
                writeToBufferPool(27, (bufferPool[27] & 63503) | (value << 4));
                break;
            case 56:
                writeToBufferPool(27, (bufferPool[27] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(28, (bufferPool[28] & 8191) | ((value & 7) << 13));
                break;
            case 57:
                writeToBufferPool(28, (bufferPool[28] & 57407) | (value << 6));
                break;
            case 58:
                writeToBufferPool(28, (bufferPool[28] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(29, (bufferPool[29] & 32767) | ((value & 1) << 15));
                break;
            case 59:
                writeToBufferPool(29, (bufferPool[29] & 33023) | (value << 8));
                break;
            case 60:
                writeToBufferPool(29, (bufferPool[29] & 65281) | (value << 1));
                break;
            case 61:
                writeToBufferPool(29, (bufferPool[29] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(30, (bufferPool[30] & 1023) | ((value & 63) << 10));
                break;
            case 62:
                writeToBufferPool(30, (bufferPool[30] & 64519) | (value << 3));
                break;
            case 63:
                writeToBufferPool(30, (bufferPool[30] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(31, (bufferPool[31] & 4095) | ((value & 15) << 12));
                break;
            case 64:
                writeToBufferPool(31, (bufferPool[31] & 61471) | (value << 5));
                break;
            case 65:
                writeToBufferPool(31, (bufferPool[31] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(32, (bufferPool[32] & 16383) | ((value & 3) << 14));
                break;
            case 66:
                writeToBufferPool(32, (bufferPool[32] & 49279) | (value << 7));
                break;
            case 67:
                writeToBufferPool(32, (bufferPool[32] & 65408) | (value));
                break;
            case 68:
                writeToBufferPool(33, (bufferPool[33] & 511) | (value << 9));
                break;
            case 69:
                writeToBufferPool(33, (bufferPool[33] & 65027) | (value << 2));
                break;
            case 70:
                writeToBufferPool(33, (bufferPool[33] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(34, (bufferPool[34] & 2047) | ((value & 31) << 11));
                break;
            case 71:
                writeToBufferPool(34, (bufferPool[34] & 63503) | (value << 4));
                break;
            case 72:
                writeToBufferPool(34, (bufferPool[34] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(35, (bufferPool[35] & 8191) | ((value & 7) << 13));
                break;
            case 73:
                writeToBufferPool(35, (bufferPool[35] & 57407) | (value << 6));
                break;
            case 74:
                writeToBufferPool(35, (bufferPool[35] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(36, (bufferPool[36] & 32767) | ((value & 1) << 15));
                break;
            case 75:
                writeToBufferPool(36, (bufferPool[36] & 33023) | (value << 8));
                break;
            case 76:
                writeToBufferPool(36, (bufferPool[36] & 65281) | (value << 1));
                break;
            case 77:
                writeToBufferPool(36, (bufferPool[36] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(37, (bufferPool[37] & 1023) | ((value & 63) << 10));
                break;
            case 78:
                writeToBufferPool(37, (bufferPool[37] & 64519) | (value << 3));
                break;
            case 79:
                writeToBufferPool(37, (bufferPool[37] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(38, (bufferPool[38] & 4095) | ((value & 15) << 12));
                break;
            case 80:
                writeToBufferPool(38, (bufferPool[38] & 61471) | (value << 5));
                break;
            case 81:
                writeToBufferPool(38, (bufferPool[38] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(39, (bufferPool[39] & 16383) | ((value & 3) << 14));
                break;
            case 82:
                writeToBufferPool(39, (bufferPool[39] & 49279) | (value << 7));
                break;
            case 83:
                writeToBufferPool(39, (bufferPool[39] & 65408) | (value));
                break;
            case 84:
                writeToBufferPool(40, (bufferPool[40] & 511) | (value << 9));
                break;
            case 85:
                writeToBufferPool(40, (bufferPool[40] & 65027) | (value << 2));
                break;
            case 86:
                writeToBufferPool(40, (bufferPool[40] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(41, (bufferPool[41] & 2047) | ((value & 31) << 11));
                break;
            case 87:
                writeToBufferPool(41, (bufferPool[41] & 63503) | (value << 4));
                break;
            case 88:
                writeToBufferPool(41, (bufferPool[41] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(42, (bufferPool[42] & 8191) | ((value & 7) << 13));
                break;
            case 89:
                writeToBufferPool(42, (bufferPool[42] & 57407) | (value << 6));
                break;
            case 90:
                writeToBufferPool(42, (bufferPool[42] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(43, (bufferPool[43] & 32767) | ((value & 1) << 15));
                break;
            case 91:
                writeToBufferPool(43, (bufferPool[43] & 33023) | (value << 8));
                break;
            case 92:
                writeToBufferPool(43, (bufferPool[43] & 65281) | (value << 1));
                break;
            case 93:
                writeToBufferPool(43, (bufferPool[43] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(44, (bufferPool[44] & 1023) | ((value & 63) << 10));
                break;
            case 94:
                writeToBufferPool(44, (bufferPool[44] & 64519) | (value << 3));
                break;
            case 95:
                writeToBufferPool(44, (bufferPool[44] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(45, (bufferPool[45] & 4095) | ((value & 15) << 12));
                break;
            case 96:
                writeToBufferPool(45, (bufferPool[45] & 61471) | (value << 5));
                break;
            case 97:
                writeToBufferPool(45, (bufferPool[45] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(46, (bufferPool[46] & 16383) | ((value & 3) << 14));
                break;
            case 98:
                writeToBufferPool(46, (bufferPool[46] & 49279) | (value << 7));
                break;
            case 99:
                writeToBufferPool(46, (bufferPool[46] & 65408) | (value));
                break;
        }
    }

    public static int readCombatSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(47) & 32768) >>> 15;
            case 1:
                return (rc.readSharedArray(47) & 128) >>> 7;
            case 2:
                return (rc.readSharedArray(48) & 32768) >>> 15;
            case 3:
                return (rc.readSharedArray(48) & 128) >>> 7;
            case 4:
                return (rc.readSharedArray(49) & 32768) >>> 15;
            case 5:
                return (rc.readSharedArray(49) & 128) >>> 7;
            case 6:
                return (rc.readSharedArray(50) & 32768) >>> 15;
            case 7:
                return (rc.readSharedArray(50) & 128) >>> 7;
            default:
                return -1;
        }
    }

    public static void writeCombatSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 32767) | (value << 15));
                break;
            case 1:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65407) | (value << 7));
                break;
            case 2:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 32767) | (value << 15));
                break;
            case 3:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65407) | (value << 7));
                break;
            case 4:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 32767) | (value << 15));
                break;
            case 5:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65407) | (value << 7));
                break;
            case 6:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 32767) | (value << 15));
                break;
            case 7:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65407) | (value << 7));
                break;
        }
    }

    public static void writeBPCombatSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(47, (bufferPool[47] & 32767) | (value << 15));
                break;
            case 1:
                writeToBufferPool(47, (bufferPool[47] & 65407) | (value << 7));
                break;
            case 2:
                writeToBufferPool(48, (bufferPool[48] & 32767) | (value << 15));
                break;
            case 3:
                writeToBufferPool(48, (bufferPool[48] & 65407) | (value << 7));
                break;
            case 4:
                writeToBufferPool(49, (bufferPool[49] & 32767) | (value << 15));
                break;
            case 5:
                writeToBufferPool(49, (bufferPool[49] & 65407) | (value << 7));
                break;
            case 6:
                writeToBufferPool(50, (bufferPool[50] & 32767) | (value << 15));
                break;
            case 7:
                writeToBufferPool(50, (bufferPool[50] & 65407) | (value << 7));
                break;
        }
    }

    public static int readCombatSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(47) & 32512) >>> 8;
            case 1:
                return (rc.readSharedArray(47) & 127);
            case 2:
                return (rc.readSharedArray(48) & 32512) >>> 8;
            case 3:
                return (rc.readSharedArray(48) & 127);
            case 4:
                return (rc.readSharedArray(49) & 32512) >>> 8;
            case 5:
                return (rc.readSharedArray(49) & 127);
            case 6:
                return (rc.readSharedArray(50) & 32512) >>> 8;
            case 7:
                return (rc.readSharedArray(50) & 127);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 33023) | (value << 8));
                break;
            case 1:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65408) | (value));
                break;
            case 2:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 33023) | (value << 8));
                break;
            case 3:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65408) | (value));
                break;
            case 4:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 33023) | (value << 8));
                break;
            case 5:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65408) | (value));
                break;
            case 6:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 33023) | (value << 8));
                break;
            case 7:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65408) | (value));
                break;
        }
    }

    public static void writeBPCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(47, (bufferPool[47] & 33023) | (value << 8));
                break;
            case 1:
                writeToBufferPool(47, (bufferPool[47] & 65408) | (value));
                break;
            case 2:
                writeToBufferPool(48, (bufferPool[48] & 33023) | (value << 8));
                break;
            case 3:
                writeToBufferPool(48, (bufferPool[48] & 65408) | (value));
                break;
            case 4:
                writeToBufferPool(49, (bufferPool[49] & 33023) | (value << 8));
                break;
            case 5:
                writeToBufferPool(49, (bufferPool[49] & 65408) | (value));
                break;
            case 6:
                writeToBufferPool(50, (bufferPool[50] & 33023) | (value << 8));
                break;
            case 7:
                writeToBufferPool(50, (bufferPool[50] & 65408) | (value));
                break;
        }
    }

    public static int readCombatSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(47) & 65280) >>> 8;
            case 1:
                return (rc.readSharedArray(47) & 255);
            case 2:
                return (rc.readSharedArray(48) & 65280) >>> 8;
            case 3:
                return (rc.readSharedArray(48) & 255);
            case 4:
                return (rc.readSharedArray(49) & 65280) >>> 8;
            case 5:
                return (rc.readSharedArray(49) & 255);
            case 6:
                return (rc.readSharedArray(50) & 65280) >>> 8;
            case 7:
                return (rc.readSharedArray(50) & 255);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 255) | (value << 8));
                break;
            case 1:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65280) | (value));
                break;
            case 2:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 255) | (value << 8));
                break;
            case 3:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65280) | (value));
                break;
            case 4:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 255) | (value << 8));
                break;
            case 5:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65280) | (value));
                break;
            case 6:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 255) | (value << 8));
                break;
            case 7:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65280) | (value));
                break;
        }
    }

    public static void writeBPCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(47, (bufferPool[47] & 255) | (value << 8));
                break;
            case 1:
                writeToBufferPool(47, (bufferPool[47] & 65280) | (value));
                break;
            case 2:
                writeToBufferPool(48, (bufferPool[48] & 255) | (value << 8));
                break;
            case 3:
                writeToBufferPool(48, (bufferPool[48] & 65280) | (value));
                break;
            case 4:
                writeToBufferPool(49, (bufferPool[49] & 255) | (value << 8));
                break;
            case 5:
                writeToBufferPool(49, (bufferPool[49] & 65280) | (value));
                break;
            case 6:
                writeToBufferPool(50, (bufferPool[50] & 255) | (value << 8));
                break;
            case 7:
                writeToBufferPool(50, (bufferPool[50] & 65280) | (value));
                break;
        }
    }

    public static int readExploreSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(51) & 32768) >>> 15;
            case 1:
                return (rc.readSharedArray(51) & 128) >>> 7;
            case 2:
                return (rc.readSharedArray(52) & 32768) >>> 15;
            case 3:
                return (rc.readSharedArray(52) & 128) >>> 7;
            case 4:
                return (rc.readSharedArray(53) & 32768) >>> 15;
            case 5:
                return (rc.readSharedArray(53) & 128) >>> 7;
            case 6:
                return (rc.readSharedArray(54) & 32768) >>> 15;
            case 7:
                return (rc.readSharedArray(54) & 128) >>> 7;
            case 8:
                return (rc.readSharedArray(55) & 32768) >>> 15;
            case 9:
                return (rc.readSharedArray(55) & 128) >>> 7;
            case 10:
                return (rc.readSharedArray(56) & 32768) >>> 15;
            case 11:
                return (rc.readSharedArray(56) & 128) >>> 7;
            case 12:
                return (rc.readSharedArray(57) & 32768) >>> 15;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 32767) | (value << 15));
                break;
            case 1:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65407) | (value << 7));
                break;
            case 2:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 32767) | (value << 15));
                break;
            case 3:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65407) | (value << 7));
                break;
            case 4:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 32767) | (value << 15));
                break;
            case 5:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65407) | (value << 7));
                break;
            case 6:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 32767) | (value << 15));
                break;
            case 7:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65407) | (value << 7));
                break;
            case 8:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 32767) | (value << 15));
                break;
            case 9:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65407) | (value << 7));
                break;
            case 10:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 32767) | (value << 15));
                break;
            case 11:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65407) | (value << 7));
                break;
            case 12:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 32767) | (value << 15));
                break;
        }
    }

    public static void writeBPExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(51, (bufferPool[51] & 32767) | (value << 15));
                break;
            case 1:
                writeToBufferPool(51, (bufferPool[51] & 65407) | (value << 7));
                break;
            case 2:
                writeToBufferPool(52, (bufferPool[52] & 32767) | (value << 15));
                break;
            case 3:
                writeToBufferPool(52, (bufferPool[52] & 65407) | (value << 7));
                break;
            case 4:
                writeToBufferPool(53, (bufferPool[53] & 32767) | (value << 15));
                break;
            case 5:
                writeToBufferPool(53, (bufferPool[53] & 65407) | (value << 7));
                break;
            case 6:
                writeToBufferPool(54, (bufferPool[54] & 32767) | (value << 15));
                break;
            case 7:
                writeToBufferPool(54, (bufferPool[54] & 65407) | (value << 7));
                break;
            case 8:
                writeToBufferPool(55, (bufferPool[55] & 32767) | (value << 15));
                break;
            case 9:
                writeToBufferPool(55, (bufferPool[55] & 65407) | (value << 7));
                break;
            case 10:
                writeToBufferPool(56, (bufferPool[56] & 32767) | (value << 15));
                break;
            case 11:
                writeToBufferPool(56, (bufferPool[56] & 65407) | (value << 7));
                break;
            case 12:
                writeToBufferPool(57, (bufferPool[57] & 32767) | (value << 15));
                break;
        }
    }

    public static int readExploreSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(51) & 32512) >>> 8;
            case 1:
                return (rc.readSharedArray(51) & 127);
            case 2:
                return (rc.readSharedArray(52) & 32512) >>> 8;
            case 3:
                return (rc.readSharedArray(52) & 127);
            case 4:
                return (rc.readSharedArray(53) & 32512) >>> 8;
            case 5:
                return (rc.readSharedArray(53) & 127);
            case 6:
                return (rc.readSharedArray(54) & 32512) >>> 8;
            case 7:
                return (rc.readSharedArray(54) & 127);
            case 8:
                return (rc.readSharedArray(55) & 32512) >>> 8;
            case 9:
                return (rc.readSharedArray(55) & 127);
            case 10:
                return (rc.readSharedArray(56) & 32512) >>> 8;
            case 11:
                return (rc.readSharedArray(56) & 127);
            case 12:
                return (rc.readSharedArray(57) & 32512) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 33023) | (value << 8));
                break;
            case 1:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65408) | (value));
                break;
            case 2:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 33023) | (value << 8));
                break;
            case 3:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65408) | (value));
                break;
            case 4:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 33023) | (value << 8));
                break;
            case 5:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65408) | (value));
                break;
            case 6:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 33023) | (value << 8));
                break;
            case 7:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65408) | (value));
                break;
            case 8:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 33023) | (value << 8));
                break;
            case 9:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65408) | (value));
                break;
            case 10:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 33023) | (value << 8));
                break;
            case 11:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65408) | (value));
                break;
            case 12:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 33023) | (value << 8));
                break;
        }
    }

    public static void writeBPExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(51, (bufferPool[51] & 33023) | (value << 8));
                break;
            case 1:
                writeToBufferPool(51, (bufferPool[51] & 65408) | (value));
                break;
            case 2:
                writeToBufferPool(52, (bufferPool[52] & 33023) | (value << 8));
                break;
            case 3:
                writeToBufferPool(52, (bufferPool[52] & 65408) | (value));
                break;
            case 4:
                writeToBufferPool(53, (bufferPool[53] & 33023) | (value << 8));
                break;
            case 5:
                writeToBufferPool(53, (bufferPool[53] & 65408) | (value));
                break;
            case 6:
                writeToBufferPool(54, (bufferPool[54] & 33023) | (value << 8));
                break;
            case 7:
                writeToBufferPool(54, (bufferPool[54] & 65408) | (value));
                break;
            case 8:
                writeToBufferPool(55, (bufferPool[55] & 33023) | (value << 8));
                break;
            case 9:
                writeToBufferPool(55, (bufferPool[55] & 65408) | (value));
                break;
            case 10:
                writeToBufferPool(56, (bufferPool[56] & 33023) | (value << 8));
                break;
            case 11:
                writeToBufferPool(56, (bufferPool[56] & 65408) | (value));
                break;
            case 12:
                writeToBufferPool(57, (bufferPool[57] & 33023) | (value << 8));
                break;
        }
    }

    public static int readExploreSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(51) & 65280) >>> 8;
            case 1:
                return (rc.readSharedArray(51) & 255);
            case 2:
                return (rc.readSharedArray(52) & 65280) >>> 8;
            case 3:
                return (rc.readSharedArray(52) & 255);
            case 4:
                return (rc.readSharedArray(53) & 65280) >>> 8;
            case 5:
                return (rc.readSharedArray(53) & 255);
            case 6:
                return (rc.readSharedArray(54) & 65280) >>> 8;
            case 7:
                return (rc.readSharedArray(54) & 255);
            case 8:
                return (rc.readSharedArray(55) & 65280) >>> 8;
            case 9:
                return (rc.readSharedArray(55) & 255);
            case 10:
                return (rc.readSharedArray(56) & 65280) >>> 8;
            case 11:
                return (rc.readSharedArray(56) & 255);
            case 12:
                return (rc.readSharedArray(57) & 65280) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 255) | (value << 8));
                break;
            case 1:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65280) | (value));
                break;
            case 2:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 255) | (value << 8));
                break;
            case 3:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65280) | (value));
                break;
            case 4:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 255) | (value << 8));
                break;
            case 5:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65280) | (value));
                break;
            case 6:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 255) | (value << 8));
                break;
            case 7:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65280) | (value));
                break;
            case 8:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 255) | (value << 8));
                break;
            case 9:
                rc.writeSharedArray(55, (rc.readSharedArray(55) & 65280) | (value));
                break;
            case 10:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 255) | (value << 8));
                break;
            case 11:
                rc.writeSharedArray(56, (rc.readSharedArray(56) & 65280) | (value));
                break;
            case 12:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 255) | (value << 8));
                break;
        }
    }

    public static void writeBPExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(51, (bufferPool[51] & 255) | (value << 8));
                break;
            case 1:
                writeToBufferPool(51, (bufferPool[51] & 65280) | (value));
                break;
            case 2:
                writeToBufferPool(52, (bufferPool[52] & 255) | (value << 8));
                break;
            case 3:
                writeToBufferPool(52, (bufferPool[52] & 65280) | (value));
                break;
            case 4:
                writeToBufferPool(53, (bufferPool[53] & 255) | (value << 8));
                break;
            case 5:
                writeToBufferPool(53, (bufferPool[53] & 65280) | (value));
                break;
            case 6:
                writeToBufferPool(54, (bufferPool[54] & 255) | (value << 8));
                break;
            case 7:
                writeToBufferPool(54, (bufferPool[54] & 65280) | (value));
                break;
            case 8:
                writeToBufferPool(55, (bufferPool[55] & 255) | (value << 8));
                break;
            case 9:
                writeToBufferPool(55, (bufferPool[55] & 65280) | (value));
                break;
            case 10:
                writeToBufferPool(56, (bufferPool[56] & 255) | (value << 8));
                break;
            case 11:
                writeToBufferPool(56, (bufferPool[56] & 65280) | (value));
                break;
            case 12:
                writeToBufferPool(57, (bufferPool[57] & 255) | (value << 8));
                break;
        }
    }

    public static int readMineSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(57) & 224) >>> 5;
            case 1:
                return (rc.readSharedArray(58) & 14336) >>> 11;
            case 2:
                return (rc.readSharedArray(58) & 14) >>> 1;
            case 3:
                return (rc.readSharedArray(59) & 896) >>> 7;
            case 4:
                return (rc.readSharedArray(60) & 57344) >>> 13;
            case 5:
                return (rc.readSharedArray(60) & 56) >>> 3;
            case 6:
                return (rc.readSharedArray(61) & 3584) >>> 9;
            case 7:
                return ((rc.readSharedArray(61) & 3) << 1) + ((rc.readSharedArray(62) & 32768) >>> 15);
            case 8:
                return (rc.readSharedArray(62) & 224) >>> 5;
            case 9:
                return (rc.readSharedArray(63) & 14336) >>> 11;
            default:
                return -1;
        }
    }

    public static void writeMineSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 65311) | (value << 5));
                break;
            case 1:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 51199) | (value << 11));
                break;
            case 2:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 65521) | (value << 1));
                break;
            case 3:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 64639) | (value << 7));
                break;
            case 4:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 8191) | (value << 13));
                break;
            case 5:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 65479) | (value << 3));
                break;
            case 6:
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 61951) | (value << 9));
                break;
            case 7:
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 65532) | ((value & 6) >>> 1));
                rc.writeSharedArray(62, (rc.readSharedArray(62) & 32767) | ((value & 1) << 15));
                break;
            case 8:
                rc.writeSharedArray(62, (rc.readSharedArray(62) & 65311) | (value << 5));
                break;
            case 9:
                rc.writeSharedArray(63, (rc.readSharedArray(63) & 51199) | (value << 11));
                break;
        }
    }

    public static void writeBPMineSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(57, (bufferPool[57] & 65311) | (value << 5));
                break;
            case 1:
                writeToBufferPool(58, (bufferPool[58] & 51199) | (value << 11));
                break;
            case 2:
                writeToBufferPool(58, (bufferPool[58] & 65521) | (value << 1));
                break;
            case 3:
                writeToBufferPool(59, (bufferPool[59] & 64639) | (value << 7));
                break;
            case 4:
                writeToBufferPool(60, (bufferPool[60] & 8191) | (value << 13));
                break;
            case 5:
                writeToBufferPool(60, (bufferPool[60] & 65479) | (value << 3));
                break;
            case 6:
                writeToBufferPool(61, (bufferPool[61] & 61951) | (value << 9));
                break;
            case 7:
                writeToBufferPool(61, (bufferPool[61] & 65532) | ((value & 6) >>> 1));
                writeToBufferPool(62, (bufferPool[62] & 32767) | ((value & 1) << 15));
                break;
            case 8:
                writeToBufferPool(62, (bufferPool[62] & 65311) | (value << 5));
                break;
            case 9:
                writeToBufferPool(63, (bufferPool[63] & 51199) | (value << 11));
                break;
        }
    }

    public static int readMineSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(57) & 31) << 2) + ((rc.readSharedArray(58) & 49152) >>> 14);
            case 1:
                return (rc.readSharedArray(58) & 2032) >>> 4;
            case 2:
                return ((rc.readSharedArray(58) & 1) << 6) + ((rc.readSharedArray(59) & 64512) >>> 10);
            case 3:
                return (rc.readSharedArray(59) & 127);
            case 4:
                return (rc.readSharedArray(60) & 8128) >>> 6;
            case 5:
                return ((rc.readSharedArray(60) & 7) << 4) + ((rc.readSharedArray(61) & 61440) >>> 12);
            case 6:
                return (rc.readSharedArray(61) & 508) >>> 2;
            case 7:
                return (rc.readSharedArray(62) & 32512) >>> 8;
            case 8:
                return ((rc.readSharedArray(62) & 31) << 2) + ((rc.readSharedArray(63) & 49152) >>> 14);
            case 9:
                return (rc.readSharedArray(63) & 2032) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 16383) | ((value & 3) << 14));
                break;
            case 1:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 63503) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 1023) | ((value & 63) << 10));
                break;
            case 3:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 65408) | (value));
                break;
            case 4:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 57407) | (value << 6));
                break;
            case 5:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 4095) | ((value & 15) << 12));
                break;
            case 6:
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 65027) | (value << 2));
                break;
            case 7:
                rc.writeSharedArray(62, (rc.readSharedArray(62) & 33023) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(62, (rc.readSharedArray(62) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(63, (rc.readSharedArray(63) & 16383) | ((value & 3) << 14));
                break;
            case 9:
                rc.writeSharedArray(63, (rc.readSharedArray(63) & 63503) | (value << 4));
                break;
        }
    }

    public static void writeBPMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(57, (bufferPool[57] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(58, (bufferPool[58] & 16383) | ((value & 3) << 14));
                break;
            case 1:
                writeToBufferPool(58, (bufferPool[58] & 63503) | (value << 4));
                break;
            case 2:
                writeToBufferPool(58, (bufferPool[58] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(59, (bufferPool[59] & 1023) | ((value & 63) << 10));
                break;
            case 3:
                writeToBufferPool(59, (bufferPool[59] & 65408) | (value));
                break;
            case 4:
                writeToBufferPool(60, (bufferPool[60] & 57407) | (value << 6));
                break;
            case 5:
                writeToBufferPool(60, (bufferPool[60] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(61, (bufferPool[61] & 4095) | ((value & 15) << 12));
                break;
            case 6:
                writeToBufferPool(61, (bufferPool[61] & 65027) | (value << 2));
                break;
            case 7:
                writeToBufferPool(62, (bufferPool[62] & 33023) | (value << 8));
                break;
            case 8:
                writeToBufferPool(62, (bufferPool[62] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(63, (bufferPool[63] & 16383) | ((value & 3) << 14));
                break;
            case 9:
                writeToBufferPool(63, (bufferPool[63] & 63503) | (value << 4));
                break;
        }
    }

    public static int readMineSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(57) & 255) << 2) + ((rc.readSharedArray(58) & 49152) >>> 14);
            case 1:
                return (rc.readSharedArray(58) & 16368) >>> 4;
            case 2:
                return ((rc.readSharedArray(58) & 15) << 6) + ((rc.readSharedArray(59) & 64512) >>> 10);
            case 3:
                return (rc.readSharedArray(59) & 1023);
            case 4:
                return (rc.readSharedArray(60) & 65472) >>> 6;
            case 5:
                return ((rc.readSharedArray(60) & 63) << 4) + ((rc.readSharedArray(61) & 61440) >>> 12);
            case 6:
                return (rc.readSharedArray(61) & 4092) >>> 2;
            case 7:
                return ((rc.readSharedArray(61) & 3) << 8) + ((rc.readSharedArray(62) & 65280) >>> 8);
            case 8:
                return ((rc.readSharedArray(62) & 255) << 2) + ((rc.readSharedArray(63) & 49152) >>> 14);
            case 9:
                return (rc.readSharedArray(63) & 16368) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(57, (rc.readSharedArray(57) & 65280) | ((value & 1020) >>> 2));
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 16383) | ((value & 3) << 14));
                break;
            case 1:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 49167) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(58, (rc.readSharedArray(58) & 65520) | ((value & 960) >>> 6));
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 1023) | ((value & 63) << 10));
                break;
            case 3:
                rc.writeSharedArray(59, (rc.readSharedArray(59) & 64512) | (value));
                break;
            case 4:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 63) | (value << 6));
                break;
            case 5:
                rc.writeSharedArray(60, (rc.readSharedArray(60) & 65472) | ((value & 1008) >>> 4));
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 4095) | ((value & 15) << 12));
                break;
            case 6:
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 61443) | (value << 2));
                break;
            case 7:
                rc.writeSharedArray(61, (rc.readSharedArray(61) & 65532) | ((value & 768) >>> 8));
                rc.writeSharedArray(62, (rc.readSharedArray(62) & 255) | ((value & 255) << 8));
                break;
            case 8:
                rc.writeSharedArray(62, (rc.readSharedArray(62) & 65280) | ((value & 1020) >>> 2));
                rc.writeSharedArray(63, (rc.readSharedArray(63) & 16383) | ((value & 3) << 14));
                break;
            case 9:
                rc.writeSharedArray(63, (rc.readSharedArray(63) & 49167) | (value << 4));
                break;
        }
    }

    public static void writeBPMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(57, (bufferPool[57] & 65280) | ((value & 1020) >>> 2));
                writeToBufferPool(58, (bufferPool[58] & 16383) | ((value & 3) << 14));
                break;
            case 1:
                writeToBufferPool(58, (bufferPool[58] & 49167) | (value << 4));
                break;
            case 2:
                writeToBufferPool(58, (bufferPool[58] & 65520) | ((value & 960) >>> 6));
                writeToBufferPool(59, (bufferPool[59] & 1023) | ((value & 63) << 10));
                break;
            case 3:
                writeToBufferPool(59, (bufferPool[59] & 64512) | (value));
                break;
            case 4:
                writeToBufferPool(60, (bufferPool[60] & 63) | (value << 6));
                break;
            case 5:
                writeToBufferPool(60, (bufferPool[60] & 65472) | ((value & 1008) >>> 4));
                writeToBufferPool(61, (bufferPool[61] & 4095) | ((value & 15) << 12));
                break;
            case 6:
                writeToBufferPool(61, (bufferPool[61] & 61443) | (value << 2));
                break;
            case 7:
                writeToBufferPool(61, (bufferPool[61] & 65532) | ((value & 768) >>> 8));
                writeToBufferPool(62, (bufferPool[62] & 255) | ((value & 255) << 8));
                break;
            case 8:
                writeToBufferPool(62, (bufferPool[62] & 65280) | ((value & 1020) >>> 2));
                writeToBufferPool(63, (bufferPool[63] & 16383) | ((value & 3) << 14));
                break;
            case 9:
                writeToBufferPool(63, (bufferPool[63] & 49167) | (value << 4));
                break;
        }
    }

    // BUFFER POOL READ AND WRITE METHODS

}
