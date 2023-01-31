package MPWorking;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;


    public final static int OUR_HQ_SLOTS = 4;
    public final static int SECTOR_SLOTS = 100;
    public final static int COMBAT_SECTOR_SLOTS = 4;
    public final static int EXPLORE_SECTOR_SLOTS = 13;
    public final static int MINE_SECTOR_SLOTS = 12;
    public final static int SYMMETRY_SLOTS = 1;
    public final static int NUM_HQS_SLOTS = 1;
    public final static int ELIXIR_SECTOR_SLOTS = 1;

    // ControlStatus priorities are in increasing priority.
    public class ControlStatus {
        public static final int UNKNOWN = 0;
        public static final int EXPLORING = 1;
        public static final int EMPTY = 2;

        public static final int NEUTRAL_ISLAND = 3;

        public static final int ENEMY_PASSIVE = 4;
        public static final int ENEMY_ISLAND = 5;
        public static final int ENEMY_AGGRESIVE = 6;

        public static final int FRIENDLY_ISLAND = 7;

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

    public static void writeNumHqs(int num) throws GameActionException {
        writeNumHqsDoNotCall(num - 1);
    }

    public static int readNumHqs() throws GameActionException {
        return readNumHqsDoNotCall() + 1;
    }

    public static MapLocation readOurHqLocation(int idx) throws GameActionException {
        return new MapLocation(readOurHqXCoord(idx), readOurHqYCoord(idx));
    }

    public static void writeOurHqLocation(int idx, MapLocation loc) throws GameActionException {
        writeOurHqXCoord(idx, loc.x);
        writeOurHqYCoord(idx, loc.y);
    }

    public static void initSymmetry() throws GameActionException {
        writeSymmetryAll(7);
    }

    public static int pickControlStatus(int newStatus, int currStatus) {
        switch (newStatus) {
            case Comms.ControlStatus.NEUTRAL_ISLAND:
                switch (currStatus) {
                    // If the new status is a neutral island, override enemy and
                    // friendly islands found in this sector.
                    case Comms.ControlStatus.ENEMY_ISLAND:
                    case Comms.ControlStatus.FRIENDLY_ISLAND:
                        return newStatus;
                    default:
                        return Math.max(newStatus, currStatus);
                }
            default:
                return Math.max(newStatus, currStatus);
        }
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

    public static boolean isEnemyControlStatus(int controlStatus) {
        switch (controlStatus) {
            case ControlStatus.ENEMY_PASSIVE:
            case ControlStatus.ENEMY_ISLAND:
            case ControlStatus.ENEMY_AGGRESIVE:
                return true;
            default:
                return false;
        }
    }

    public static void initPrioritySectors() throws GameActionException {
        rc.writeSharedArray(40, 7);
        rc.writeSharedArray(41, 63479);
        rc.writeSharedArray(42, 63479);
        rc.writeSharedArray(43, 63479);
        rc.writeSharedArray(44, 63479);
        rc.writeSharedArray(45, 63479);
        rc.writeSharedArray(46, 63479);
        rc.writeSharedArray(47, 63479);
        rc.writeSharedArray(48, 63479);
        rc.writeSharedArray(49, 65535);
        rc.writeSharedArray(50, 65535);
        rc.writeSharedArray(51, 65535);
        rc.writeSharedArray(52, 65535);
        rc.writeSharedArray(53, 65535);
        rc.writeSharedArray(54, 65283);
        rc.writeSharedArray(55, 63488);
    }


    public static void resetAllSectorControlStatus() throws GameActionException {
        rc.writeSharedArray(3, rc.readSharedArray(3) & 65080);
        rc.writeSharedArray(4, rc.readSharedArray(4) & 58254);
        rc.writeSharedArray(5, rc.readSharedArray(5) & 14563);
        rc.writeSharedArray(6, rc.readSharedArray(6) & 36408);
        rc.writeSharedArray(7, rc.readSharedArray(7) & 58254);
        rc.writeSharedArray(8, rc.readSharedArray(8) & 14563);
        rc.writeSharedArray(9, rc.readSharedArray(9) & 36408);
        rc.writeSharedArray(10, rc.readSharedArray(10) & 58254);
        rc.writeSharedArray(11, rc.readSharedArray(11) & 14563);
        rc.writeSharedArray(12, rc.readSharedArray(12) & 36408);
        rc.writeSharedArray(13, rc.readSharedArray(13) & 58254);
        rc.writeSharedArray(14, rc.readSharedArray(14) & 14563);
        rc.writeSharedArray(15, rc.readSharedArray(15) & 36408);
        rc.writeSharedArray(16, rc.readSharedArray(16) & 58254);
        rc.writeSharedArray(17, rc.readSharedArray(17) & 14563);
        rc.writeSharedArray(18, rc.readSharedArray(18) & 36408);
        rc.writeSharedArray(19, rc.readSharedArray(19) & 58254);
        rc.writeSharedArray(20, rc.readSharedArray(20) & 14563);
        rc.writeSharedArray(21, rc.readSharedArray(21) & 36408);
        rc.writeSharedArray(22, rc.readSharedArray(22) & 58254);
        rc.writeSharedArray(23, rc.readSharedArray(23) & 14563);
        rc.writeSharedArray(24, rc.readSharedArray(24) & 36408);
        rc.writeSharedArray(25, rc.readSharedArray(25) & 58254);
        rc.writeSharedArray(26, rc.readSharedArray(26) & 14563);
        rc.writeSharedArray(27, rc.readSharedArray(27) & 36408);
        rc.writeSharedArray(28, rc.readSharedArray(28) & 58254);
        rc.writeSharedArray(29, rc.readSharedArray(29) & 14563);
        rc.writeSharedArray(30, rc.readSharedArray(30) & 36408);
        rc.writeSharedArray(31, rc.readSharedArray(31) & 58254);
        rc.writeSharedArray(32, rc.readSharedArray(32) & 14563);
        rc.writeSharedArray(33, rc.readSharedArray(33) & 36408);
        rc.writeSharedArray(34, rc.readSharedArray(34) & 58254);
        rc.writeSharedArray(35, rc.readSharedArray(35) & 14563);
        rc.writeSharedArray(36, rc.readSharedArray(36) & 36408);
        rc.writeSharedArray(37, rc.readSharedArray(37) & 58254);
        rc.writeSharedArray(38, rc.readSharedArray(38) & 14563);
        rc.writeSharedArray(39, rc.readSharedArray(39) & 36408);
        rc.writeSharedArray(40, rc.readSharedArray(40) & 58255);
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
                return (rc.readSharedArray(3) & 32) >>> 5;
            case 2:
                return (rc.readSharedArray(4) & 32768) >>> 15;
            case 3:
                return (rc.readSharedArray(4) & 512) >>> 9;
            case 4:
                return (rc.readSharedArray(4) & 8) >>> 3;
            case 5:
                return (rc.readSharedArray(5) & 8192) >>> 13;
            case 6:
                return (rc.readSharedArray(5) & 128) >>> 7;
            case 7:
                return (rc.readSharedArray(5) & 2) >>> 1;
            case 8:
                return (rc.readSharedArray(6) & 2048) >>> 11;
            case 9:
                return (rc.readSharedArray(6) & 32) >>> 5;
            case 10:
                return (rc.readSharedArray(7) & 32768) >>> 15;
            case 11:
                return (rc.readSharedArray(7) & 512) >>> 9;
            case 12:
                return (rc.readSharedArray(7) & 8) >>> 3;
            case 13:
                return (rc.readSharedArray(8) & 8192) >>> 13;
            case 14:
                return (rc.readSharedArray(8) & 128) >>> 7;
            case 15:
                return (rc.readSharedArray(8) & 2) >>> 1;
            case 16:
                return (rc.readSharedArray(9) & 2048) >>> 11;
            case 17:
                return (rc.readSharedArray(9) & 32) >>> 5;
            case 18:
                return (rc.readSharedArray(10) & 32768) >>> 15;
            case 19:
                return (rc.readSharedArray(10) & 512) >>> 9;
            case 20:
                return (rc.readSharedArray(10) & 8) >>> 3;
            case 21:
                return (rc.readSharedArray(11) & 8192) >>> 13;
            case 22:
                return (rc.readSharedArray(11) & 128) >>> 7;
            case 23:
                return (rc.readSharedArray(11) & 2) >>> 1;
            case 24:
                return (rc.readSharedArray(12) & 2048) >>> 11;
            case 25:
                return (rc.readSharedArray(12) & 32) >>> 5;
            case 26:
                return (rc.readSharedArray(13) & 32768) >>> 15;
            case 27:
                return (rc.readSharedArray(13) & 512) >>> 9;
            case 28:
                return (rc.readSharedArray(13) & 8) >>> 3;
            case 29:
                return (rc.readSharedArray(14) & 8192) >>> 13;
            case 30:
                return (rc.readSharedArray(14) & 128) >>> 7;
            case 31:
                return (rc.readSharedArray(14) & 2) >>> 1;
            case 32:
                return (rc.readSharedArray(15) & 2048) >>> 11;
            case 33:
                return (rc.readSharedArray(15) & 32) >>> 5;
            case 34:
                return (rc.readSharedArray(16) & 32768) >>> 15;
            case 35:
                return (rc.readSharedArray(16) & 512) >>> 9;
            case 36:
                return (rc.readSharedArray(16) & 8) >>> 3;
            case 37:
                return (rc.readSharedArray(17) & 8192) >>> 13;
            case 38:
                return (rc.readSharedArray(17) & 128) >>> 7;
            case 39:
                return (rc.readSharedArray(17) & 2) >>> 1;
            case 40:
                return (rc.readSharedArray(18) & 2048) >>> 11;
            case 41:
                return (rc.readSharedArray(18) & 32) >>> 5;
            case 42:
                return (rc.readSharedArray(19) & 32768) >>> 15;
            case 43:
                return (rc.readSharedArray(19) & 512) >>> 9;
            case 44:
                return (rc.readSharedArray(19) & 8) >>> 3;
            case 45:
                return (rc.readSharedArray(20) & 8192) >>> 13;
            case 46:
                return (rc.readSharedArray(20) & 128) >>> 7;
            case 47:
                return (rc.readSharedArray(20) & 2) >>> 1;
            case 48:
                return (rc.readSharedArray(21) & 2048) >>> 11;
            case 49:
                return (rc.readSharedArray(21) & 32) >>> 5;
            case 50:
                return (rc.readSharedArray(22) & 32768) >>> 15;
            case 51:
                return (rc.readSharedArray(22) & 512) >>> 9;
            case 52:
                return (rc.readSharedArray(22) & 8) >>> 3;
            case 53:
                return (rc.readSharedArray(23) & 8192) >>> 13;
            case 54:
                return (rc.readSharedArray(23) & 128) >>> 7;
            case 55:
                return (rc.readSharedArray(23) & 2) >>> 1;
            case 56:
                return (rc.readSharedArray(24) & 2048) >>> 11;
            case 57:
                return (rc.readSharedArray(24) & 32) >>> 5;
            case 58:
                return (rc.readSharedArray(25) & 32768) >>> 15;
            case 59:
                return (rc.readSharedArray(25) & 512) >>> 9;
            case 60:
                return (rc.readSharedArray(25) & 8) >>> 3;
            case 61:
                return (rc.readSharedArray(26) & 8192) >>> 13;
            case 62:
                return (rc.readSharedArray(26) & 128) >>> 7;
            case 63:
                return (rc.readSharedArray(26) & 2) >>> 1;
            case 64:
                return (rc.readSharedArray(27) & 2048) >>> 11;
            case 65:
                return (rc.readSharedArray(27) & 32) >>> 5;
            case 66:
                return (rc.readSharedArray(28) & 32768) >>> 15;
            case 67:
                return (rc.readSharedArray(28) & 512) >>> 9;
            case 68:
                return (rc.readSharedArray(28) & 8) >>> 3;
            case 69:
                return (rc.readSharedArray(29) & 8192) >>> 13;
            case 70:
                return (rc.readSharedArray(29) & 128) >>> 7;
            case 71:
                return (rc.readSharedArray(29) & 2) >>> 1;
            case 72:
                return (rc.readSharedArray(30) & 2048) >>> 11;
            case 73:
                return (rc.readSharedArray(30) & 32) >>> 5;
            case 74:
                return (rc.readSharedArray(31) & 32768) >>> 15;
            case 75:
                return (rc.readSharedArray(31) & 512) >>> 9;
            case 76:
                return (rc.readSharedArray(31) & 8) >>> 3;
            case 77:
                return (rc.readSharedArray(32) & 8192) >>> 13;
            case 78:
                return (rc.readSharedArray(32) & 128) >>> 7;
            case 79:
                return (rc.readSharedArray(32) & 2) >>> 1;
            case 80:
                return (rc.readSharedArray(33) & 2048) >>> 11;
            case 81:
                return (rc.readSharedArray(33) & 32) >>> 5;
            case 82:
                return (rc.readSharedArray(34) & 32768) >>> 15;
            case 83:
                return (rc.readSharedArray(34) & 512) >>> 9;
            case 84:
                return (rc.readSharedArray(34) & 8) >>> 3;
            case 85:
                return (rc.readSharedArray(35) & 8192) >>> 13;
            case 86:
                return (rc.readSharedArray(35) & 128) >>> 7;
            case 87:
                return (rc.readSharedArray(35) & 2) >>> 1;
            case 88:
                return (rc.readSharedArray(36) & 2048) >>> 11;
            case 89:
                return (rc.readSharedArray(36) & 32) >>> 5;
            case 90:
                return (rc.readSharedArray(37) & 32768) >>> 15;
            case 91:
                return (rc.readSharedArray(37) & 512) >>> 9;
            case 92:
                return (rc.readSharedArray(37) & 8) >>> 3;
            case 93:
                return (rc.readSharedArray(38) & 8192) >>> 13;
            case 94:
                return (rc.readSharedArray(38) & 128) >>> 7;
            case 95:
                return (rc.readSharedArray(38) & 2) >>> 1;
            case 96:
                return (rc.readSharedArray(39) & 2048) >>> 11;
            case 97:
                return (rc.readSharedArray(39) & 32) >>> 5;
            case 98:
                return (rc.readSharedArray(40) & 32768) >>> 15;
            case 99:
                return (rc.readSharedArray(40) & 512) >>> 9;
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
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65503) | (value << 5));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 32767) | (value << 15));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65023) | (value << 9));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65527) | (value << 3));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 57343) | (value << 13));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65407) | (value << 7));
                break;
            case 7:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65533) | (value << 1));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 63487) | (value << 11));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65503) | (value << 5));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 32767) | (value << 15));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65023) | (value << 9));
                break;
            case 12:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65527) | (value << 3));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 57343) | (value << 13));
                break;
            case 14:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65407) | (value << 7));
                break;
            case 15:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65533) | (value << 1));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 63487) | (value << 11));
                break;
            case 17:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65503) | (value << 5));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 32767) | (value << 15));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65023) | (value << 9));
                break;
            case 20:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65527) | (value << 3));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 57343) | (value << 13));
                break;
            case 22:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65407) | (value << 7));
                break;
            case 23:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65533) | (value << 1));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 63487) | (value << 11));
                break;
            case 25:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65503) | (value << 5));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 32767) | (value << 15));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65023) | (value << 9));
                break;
            case 28:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65527) | (value << 3));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 57343) | (value << 13));
                break;
            case 30:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65407) | (value << 7));
                break;
            case 31:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65533) | (value << 1));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 63487) | (value << 11));
                break;
            case 33:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65503) | (value << 5));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 32767) | (value << 15));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65023) | (value << 9));
                break;
            case 36:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65527) | (value << 3));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 57343) | (value << 13));
                break;
            case 38:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65407) | (value << 7));
                break;
            case 39:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65533) | (value << 1));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 63487) | (value << 11));
                break;
            case 41:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65503) | (value << 5));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 32767) | (value << 15));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65023) | (value << 9));
                break;
            case 44:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65527) | (value << 3));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 57343) | (value << 13));
                break;
            case 46:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65407) | (value << 7));
                break;
            case 47:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65533) | (value << 1));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 63487) | (value << 11));
                break;
            case 49:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65503) | (value << 5));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 32767) | (value << 15));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65023) | (value << 9));
                break;
            case 52:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65527) | (value << 3));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 57343) | (value << 13));
                break;
            case 54:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65407) | (value << 7));
                break;
            case 55:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65533) | (value << 1));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 63487) | (value << 11));
                break;
            case 57:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65503) | (value << 5));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 32767) | (value << 15));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65023) | (value << 9));
                break;
            case 60:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65527) | (value << 3));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 57343) | (value << 13));
                break;
            case 62:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65407) | (value << 7));
                break;
            case 63:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65533) | (value << 1));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 63487) | (value << 11));
                break;
            case 65:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65503) | (value << 5));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 32767) | (value << 15));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65023) | (value << 9));
                break;
            case 68:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65527) | (value << 3));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 57343) | (value << 13));
                break;
            case 70:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65407) | (value << 7));
                break;
            case 71:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65533) | (value << 1));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 63487) | (value << 11));
                break;
            case 73:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65503) | (value << 5));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 32767) | (value << 15));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65023) | (value << 9));
                break;
            case 76:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65527) | (value << 3));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 57343) | (value << 13));
                break;
            case 78:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65407) | (value << 7));
                break;
            case 79:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65533) | (value << 1));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 63487) | (value << 11));
                break;
            case 81:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65503) | (value << 5));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 32767) | (value << 15));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65023) | (value << 9));
                break;
            case 84:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65527) | (value << 3));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 57343) | (value << 13));
                break;
            case 86:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65407) | (value << 7));
                break;
            case 87:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65533) | (value << 1));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 63487) | (value << 11));
                break;
            case 89:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65503) | (value << 5));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 32767) | (value << 15));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65023) | (value << 9));
                break;
            case 92:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65527) | (value << 3));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 57343) | (value << 13));
                break;
            case 94:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65407) | (value << 7));
                break;
            case 95:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65533) | (value << 1));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 63487) | (value << 11));
                break;
            case 97:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65503) | (value << 5));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 32767) | (value << 15));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65023) | (value << 9));
                break;
        }
    }

    public static void writeBPSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 63487) | (value << 11));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65503) | (value << 5));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 32767) | (value << 15));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65023) | (value << 9));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65527) | (value << 3));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 57343) | (value << 13));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65407) | (value << 7));
                break;
            case 7:
                writeToBufferPool(5, (bufferPool[5] & 65533) | (value << 1));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 63487) | (value << 11));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65503) | (value << 5));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 32767) | (value << 15));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65023) | (value << 9));
                break;
            case 12:
                writeToBufferPool(7, (bufferPool[7] & 65527) | (value << 3));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 57343) | (value << 13));
                break;
            case 14:
                writeToBufferPool(8, (bufferPool[8] & 65407) | (value << 7));
                break;
            case 15:
                writeToBufferPool(8, (bufferPool[8] & 65533) | (value << 1));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 63487) | (value << 11));
                break;
            case 17:
                writeToBufferPool(9, (bufferPool[9] & 65503) | (value << 5));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 32767) | (value << 15));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65023) | (value << 9));
                break;
            case 20:
                writeToBufferPool(10, (bufferPool[10] & 65527) | (value << 3));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 57343) | (value << 13));
                break;
            case 22:
                writeToBufferPool(11, (bufferPool[11] & 65407) | (value << 7));
                break;
            case 23:
                writeToBufferPool(11, (bufferPool[11] & 65533) | (value << 1));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 63487) | (value << 11));
                break;
            case 25:
                writeToBufferPool(12, (bufferPool[12] & 65503) | (value << 5));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 32767) | (value << 15));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65023) | (value << 9));
                break;
            case 28:
                writeToBufferPool(13, (bufferPool[13] & 65527) | (value << 3));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 57343) | (value << 13));
                break;
            case 30:
                writeToBufferPool(14, (bufferPool[14] & 65407) | (value << 7));
                break;
            case 31:
                writeToBufferPool(14, (bufferPool[14] & 65533) | (value << 1));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 63487) | (value << 11));
                break;
            case 33:
                writeToBufferPool(15, (bufferPool[15] & 65503) | (value << 5));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 32767) | (value << 15));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65023) | (value << 9));
                break;
            case 36:
                writeToBufferPool(16, (bufferPool[16] & 65527) | (value << 3));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 57343) | (value << 13));
                break;
            case 38:
                writeToBufferPool(17, (bufferPool[17] & 65407) | (value << 7));
                break;
            case 39:
                writeToBufferPool(17, (bufferPool[17] & 65533) | (value << 1));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 63487) | (value << 11));
                break;
            case 41:
                writeToBufferPool(18, (bufferPool[18] & 65503) | (value << 5));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 32767) | (value << 15));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65023) | (value << 9));
                break;
            case 44:
                writeToBufferPool(19, (bufferPool[19] & 65527) | (value << 3));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 57343) | (value << 13));
                break;
            case 46:
                writeToBufferPool(20, (bufferPool[20] & 65407) | (value << 7));
                break;
            case 47:
                writeToBufferPool(20, (bufferPool[20] & 65533) | (value << 1));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 63487) | (value << 11));
                break;
            case 49:
                writeToBufferPool(21, (bufferPool[21] & 65503) | (value << 5));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 32767) | (value << 15));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65023) | (value << 9));
                break;
            case 52:
                writeToBufferPool(22, (bufferPool[22] & 65527) | (value << 3));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 57343) | (value << 13));
                break;
            case 54:
                writeToBufferPool(23, (bufferPool[23] & 65407) | (value << 7));
                break;
            case 55:
                writeToBufferPool(23, (bufferPool[23] & 65533) | (value << 1));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 63487) | (value << 11));
                break;
            case 57:
                writeToBufferPool(24, (bufferPool[24] & 65503) | (value << 5));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 32767) | (value << 15));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65023) | (value << 9));
                break;
            case 60:
                writeToBufferPool(25, (bufferPool[25] & 65527) | (value << 3));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 57343) | (value << 13));
                break;
            case 62:
                writeToBufferPool(26, (bufferPool[26] & 65407) | (value << 7));
                break;
            case 63:
                writeToBufferPool(26, (bufferPool[26] & 65533) | (value << 1));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 63487) | (value << 11));
                break;
            case 65:
                writeToBufferPool(27, (bufferPool[27] & 65503) | (value << 5));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 32767) | (value << 15));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65023) | (value << 9));
                break;
            case 68:
                writeToBufferPool(28, (bufferPool[28] & 65527) | (value << 3));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 57343) | (value << 13));
                break;
            case 70:
                writeToBufferPool(29, (bufferPool[29] & 65407) | (value << 7));
                break;
            case 71:
                writeToBufferPool(29, (bufferPool[29] & 65533) | (value << 1));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 63487) | (value << 11));
                break;
            case 73:
                writeToBufferPool(30, (bufferPool[30] & 65503) | (value << 5));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 32767) | (value << 15));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65023) | (value << 9));
                break;
            case 76:
                writeToBufferPool(31, (bufferPool[31] & 65527) | (value << 3));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 57343) | (value << 13));
                break;
            case 78:
                writeToBufferPool(32, (bufferPool[32] & 65407) | (value << 7));
                break;
            case 79:
                writeToBufferPool(32, (bufferPool[32] & 65533) | (value << 1));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 63487) | (value << 11));
                break;
            case 81:
                writeToBufferPool(33, (bufferPool[33] & 65503) | (value << 5));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 32767) | (value << 15));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65023) | (value << 9));
                break;
            case 84:
                writeToBufferPool(34, (bufferPool[34] & 65527) | (value << 3));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 57343) | (value << 13));
                break;
            case 86:
                writeToBufferPool(35, (bufferPool[35] & 65407) | (value << 7));
                break;
            case 87:
                writeToBufferPool(35, (bufferPool[35] & 65533) | (value << 1));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 63487) | (value << 11));
                break;
            case 89:
                writeToBufferPool(36, (bufferPool[36] & 65503) | (value << 5));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 32767) | (value << 15));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65023) | (value << 9));
                break;
            case 92:
                writeToBufferPool(37, (bufferPool[37] & 65527) | (value << 3));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 57343) | (value << 13));
                break;
            case 94:
                writeToBufferPool(38, (bufferPool[38] & 65407) | (value << 7));
                break;
            case 95:
                writeToBufferPool(38, (bufferPool[38] & 65533) | (value << 1));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 63487) | (value << 11));
                break;
            case 97:
                writeToBufferPool(39, (bufferPool[39] & 65503) | (value << 5));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 32767) | (value << 15));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65023) | (value << 9));
                break;
        }
    }

    public static int readSectorAdamantiumFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 1024) >>> 10;
            case 1:
                return (rc.readSharedArray(3) & 16) >>> 4;
            case 2:
                return (rc.readSharedArray(4) & 16384) >>> 14;
            case 3:
                return (rc.readSharedArray(4) & 256) >>> 8;
            case 4:
                return (rc.readSharedArray(4) & 4) >>> 2;
            case 5:
                return (rc.readSharedArray(5) & 4096) >>> 12;
            case 6:
                return (rc.readSharedArray(5) & 64) >>> 6;
            case 7:
                return (rc.readSharedArray(5) & 1);
            case 8:
                return (rc.readSharedArray(6) & 1024) >>> 10;
            case 9:
                return (rc.readSharedArray(6) & 16) >>> 4;
            case 10:
                return (rc.readSharedArray(7) & 16384) >>> 14;
            case 11:
                return (rc.readSharedArray(7) & 256) >>> 8;
            case 12:
                return (rc.readSharedArray(7) & 4) >>> 2;
            case 13:
                return (rc.readSharedArray(8) & 4096) >>> 12;
            case 14:
                return (rc.readSharedArray(8) & 64) >>> 6;
            case 15:
                return (rc.readSharedArray(8) & 1);
            case 16:
                return (rc.readSharedArray(9) & 1024) >>> 10;
            case 17:
                return (rc.readSharedArray(9) & 16) >>> 4;
            case 18:
                return (rc.readSharedArray(10) & 16384) >>> 14;
            case 19:
                return (rc.readSharedArray(10) & 256) >>> 8;
            case 20:
                return (rc.readSharedArray(10) & 4) >>> 2;
            case 21:
                return (rc.readSharedArray(11) & 4096) >>> 12;
            case 22:
                return (rc.readSharedArray(11) & 64) >>> 6;
            case 23:
                return (rc.readSharedArray(11) & 1);
            case 24:
                return (rc.readSharedArray(12) & 1024) >>> 10;
            case 25:
                return (rc.readSharedArray(12) & 16) >>> 4;
            case 26:
                return (rc.readSharedArray(13) & 16384) >>> 14;
            case 27:
                return (rc.readSharedArray(13) & 256) >>> 8;
            case 28:
                return (rc.readSharedArray(13) & 4) >>> 2;
            case 29:
                return (rc.readSharedArray(14) & 4096) >>> 12;
            case 30:
                return (rc.readSharedArray(14) & 64) >>> 6;
            case 31:
                return (rc.readSharedArray(14) & 1);
            case 32:
                return (rc.readSharedArray(15) & 1024) >>> 10;
            case 33:
                return (rc.readSharedArray(15) & 16) >>> 4;
            case 34:
                return (rc.readSharedArray(16) & 16384) >>> 14;
            case 35:
                return (rc.readSharedArray(16) & 256) >>> 8;
            case 36:
                return (rc.readSharedArray(16) & 4) >>> 2;
            case 37:
                return (rc.readSharedArray(17) & 4096) >>> 12;
            case 38:
                return (rc.readSharedArray(17) & 64) >>> 6;
            case 39:
                return (rc.readSharedArray(17) & 1);
            case 40:
                return (rc.readSharedArray(18) & 1024) >>> 10;
            case 41:
                return (rc.readSharedArray(18) & 16) >>> 4;
            case 42:
                return (rc.readSharedArray(19) & 16384) >>> 14;
            case 43:
                return (rc.readSharedArray(19) & 256) >>> 8;
            case 44:
                return (rc.readSharedArray(19) & 4) >>> 2;
            case 45:
                return (rc.readSharedArray(20) & 4096) >>> 12;
            case 46:
                return (rc.readSharedArray(20) & 64) >>> 6;
            case 47:
                return (rc.readSharedArray(20) & 1);
            case 48:
                return (rc.readSharedArray(21) & 1024) >>> 10;
            case 49:
                return (rc.readSharedArray(21) & 16) >>> 4;
            case 50:
                return (rc.readSharedArray(22) & 16384) >>> 14;
            case 51:
                return (rc.readSharedArray(22) & 256) >>> 8;
            case 52:
                return (rc.readSharedArray(22) & 4) >>> 2;
            case 53:
                return (rc.readSharedArray(23) & 4096) >>> 12;
            case 54:
                return (rc.readSharedArray(23) & 64) >>> 6;
            case 55:
                return (rc.readSharedArray(23) & 1);
            case 56:
                return (rc.readSharedArray(24) & 1024) >>> 10;
            case 57:
                return (rc.readSharedArray(24) & 16) >>> 4;
            case 58:
                return (rc.readSharedArray(25) & 16384) >>> 14;
            case 59:
                return (rc.readSharedArray(25) & 256) >>> 8;
            case 60:
                return (rc.readSharedArray(25) & 4) >>> 2;
            case 61:
                return (rc.readSharedArray(26) & 4096) >>> 12;
            case 62:
                return (rc.readSharedArray(26) & 64) >>> 6;
            case 63:
                return (rc.readSharedArray(26) & 1);
            case 64:
                return (rc.readSharedArray(27) & 1024) >>> 10;
            case 65:
                return (rc.readSharedArray(27) & 16) >>> 4;
            case 66:
                return (rc.readSharedArray(28) & 16384) >>> 14;
            case 67:
                return (rc.readSharedArray(28) & 256) >>> 8;
            case 68:
                return (rc.readSharedArray(28) & 4) >>> 2;
            case 69:
                return (rc.readSharedArray(29) & 4096) >>> 12;
            case 70:
                return (rc.readSharedArray(29) & 64) >>> 6;
            case 71:
                return (rc.readSharedArray(29) & 1);
            case 72:
                return (rc.readSharedArray(30) & 1024) >>> 10;
            case 73:
                return (rc.readSharedArray(30) & 16) >>> 4;
            case 74:
                return (rc.readSharedArray(31) & 16384) >>> 14;
            case 75:
                return (rc.readSharedArray(31) & 256) >>> 8;
            case 76:
                return (rc.readSharedArray(31) & 4) >>> 2;
            case 77:
                return (rc.readSharedArray(32) & 4096) >>> 12;
            case 78:
                return (rc.readSharedArray(32) & 64) >>> 6;
            case 79:
                return (rc.readSharedArray(32) & 1);
            case 80:
                return (rc.readSharedArray(33) & 1024) >>> 10;
            case 81:
                return (rc.readSharedArray(33) & 16) >>> 4;
            case 82:
                return (rc.readSharedArray(34) & 16384) >>> 14;
            case 83:
                return (rc.readSharedArray(34) & 256) >>> 8;
            case 84:
                return (rc.readSharedArray(34) & 4) >>> 2;
            case 85:
                return (rc.readSharedArray(35) & 4096) >>> 12;
            case 86:
                return (rc.readSharedArray(35) & 64) >>> 6;
            case 87:
                return (rc.readSharedArray(35) & 1);
            case 88:
                return (rc.readSharedArray(36) & 1024) >>> 10;
            case 89:
                return (rc.readSharedArray(36) & 16) >>> 4;
            case 90:
                return (rc.readSharedArray(37) & 16384) >>> 14;
            case 91:
                return (rc.readSharedArray(37) & 256) >>> 8;
            case 92:
                return (rc.readSharedArray(37) & 4) >>> 2;
            case 93:
                return (rc.readSharedArray(38) & 4096) >>> 12;
            case 94:
                return (rc.readSharedArray(38) & 64) >>> 6;
            case 95:
                return (rc.readSharedArray(38) & 1);
            case 96:
                return (rc.readSharedArray(39) & 1024) >>> 10;
            case 97:
                return (rc.readSharedArray(39) & 16) >>> 4;
            case 98:
                return (rc.readSharedArray(40) & 16384) >>> 14;
            case 99:
                return (rc.readSharedArray(40) & 256) >>> 8;
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
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65519) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 49151) | (value << 14));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65279) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65531) | (value << 2));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 61439) | (value << 12));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65471) | (value << 6));
                break;
            case 7:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65534) | (value));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 64511) | (value << 10));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65519) | (value << 4));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 49151) | (value << 14));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65279) | (value << 8));
                break;
            case 12:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65531) | (value << 2));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 61439) | (value << 12));
                break;
            case 14:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65471) | (value << 6));
                break;
            case 15:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65534) | (value));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 64511) | (value << 10));
                break;
            case 17:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65519) | (value << 4));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 49151) | (value << 14));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65279) | (value << 8));
                break;
            case 20:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65531) | (value << 2));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 61439) | (value << 12));
                break;
            case 22:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65471) | (value << 6));
                break;
            case 23:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65534) | (value));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 64511) | (value << 10));
                break;
            case 25:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65519) | (value << 4));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 49151) | (value << 14));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65279) | (value << 8));
                break;
            case 28:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65531) | (value << 2));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 61439) | (value << 12));
                break;
            case 30:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65471) | (value << 6));
                break;
            case 31:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65534) | (value));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 64511) | (value << 10));
                break;
            case 33:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65519) | (value << 4));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 49151) | (value << 14));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65279) | (value << 8));
                break;
            case 36:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65531) | (value << 2));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 61439) | (value << 12));
                break;
            case 38:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65471) | (value << 6));
                break;
            case 39:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65534) | (value));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 64511) | (value << 10));
                break;
            case 41:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65519) | (value << 4));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 49151) | (value << 14));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65279) | (value << 8));
                break;
            case 44:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65531) | (value << 2));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 61439) | (value << 12));
                break;
            case 46:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65471) | (value << 6));
                break;
            case 47:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65534) | (value));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 64511) | (value << 10));
                break;
            case 49:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65519) | (value << 4));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 49151) | (value << 14));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65279) | (value << 8));
                break;
            case 52:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65531) | (value << 2));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 61439) | (value << 12));
                break;
            case 54:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65471) | (value << 6));
                break;
            case 55:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65534) | (value));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 64511) | (value << 10));
                break;
            case 57:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65519) | (value << 4));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 49151) | (value << 14));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65279) | (value << 8));
                break;
            case 60:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65531) | (value << 2));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 61439) | (value << 12));
                break;
            case 62:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65471) | (value << 6));
                break;
            case 63:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65534) | (value));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 64511) | (value << 10));
                break;
            case 65:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65519) | (value << 4));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 49151) | (value << 14));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65279) | (value << 8));
                break;
            case 68:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65531) | (value << 2));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 61439) | (value << 12));
                break;
            case 70:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65471) | (value << 6));
                break;
            case 71:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65534) | (value));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 64511) | (value << 10));
                break;
            case 73:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65519) | (value << 4));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 49151) | (value << 14));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65279) | (value << 8));
                break;
            case 76:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65531) | (value << 2));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 61439) | (value << 12));
                break;
            case 78:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65471) | (value << 6));
                break;
            case 79:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65534) | (value));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 64511) | (value << 10));
                break;
            case 81:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65519) | (value << 4));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 49151) | (value << 14));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65279) | (value << 8));
                break;
            case 84:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65531) | (value << 2));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 61439) | (value << 12));
                break;
            case 86:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65471) | (value << 6));
                break;
            case 87:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65534) | (value));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 64511) | (value << 10));
                break;
            case 89:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65519) | (value << 4));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 49151) | (value << 14));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65279) | (value << 8));
                break;
            case 92:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65531) | (value << 2));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 61439) | (value << 12));
                break;
            case 94:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65471) | (value << 6));
                break;
            case 95:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65534) | (value));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 64511) | (value << 10));
                break;
            case 97:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65519) | (value << 4));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 49151) | (value << 14));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65279) | (value << 8));
                break;
        }
    }

    public static void writeBPSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 64511) | (value << 10));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65519) | (value << 4));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 49151) | (value << 14));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65279) | (value << 8));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65531) | (value << 2));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 61439) | (value << 12));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65471) | (value << 6));
                break;
            case 7:
                writeToBufferPool(5, (bufferPool[5] & 65534) | (value));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 64511) | (value << 10));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65519) | (value << 4));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 49151) | (value << 14));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65279) | (value << 8));
                break;
            case 12:
                writeToBufferPool(7, (bufferPool[7] & 65531) | (value << 2));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 61439) | (value << 12));
                break;
            case 14:
                writeToBufferPool(8, (bufferPool[8] & 65471) | (value << 6));
                break;
            case 15:
                writeToBufferPool(8, (bufferPool[8] & 65534) | (value));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 64511) | (value << 10));
                break;
            case 17:
                writeToBufferPool(9, (bufferPool[9] & 65519) | (value << 4));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 49151) | (value << 14));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65279) | (value << 8));
                break;
            case 20:
                writeToBufferPool(10, (bufferPool[10] & 65531) | (value << 2));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 61439) | (value << 12));
                break;
            case 22:
                writeToBufferPool(11, (bufferPool[11] & 65471) | (value << 6));
                break;
            case 23:
                writeToBufferPool(11, (bufferPool[11] & 65534) | (value));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 64511) | (value << 10));
                break;
            case 25:
                writeToBufferPool(12, (bufferPool[12] & 65519) | (value << 4));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 49151) | (value << 14));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65279) | (value << 8));
                break;
            case 28:
                writeToBufferPool(13, (bufferPool[13] & 65531) | (value << 2));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 61439) | (value << 12));
                break;
            case 30:
                writeToBufferPool(14, (bufferPool[14] & 65471) | (value << 6));
                break;
            case 31:
                writeToBufferPool(14, (bufferPool[14] & 65534) | (value));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 64511) | (value << 10));
                break;
            case 33:
                writeToBufferPool(15, (bufferPool[15] & 65519) | (value << 4));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 49151) | (value << 14));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65279) | (value << 8));
                break;
            case 36:
                writeToBufferPool(16, (bufferPool[16] & 65531) | (value << 2));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 61439) | (value << 12));
                break;
            case 38:
                writeToBufferPool(17, (bufferPool[17] & 65471) | (value << 6));
                break;
            case 39:
                writeToBufferPool(17, (bufferPool[17] & 65534) | (value));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 64511) | (value << 10));
                break;
            case 41:
                writeToBufferPool(18, (bufferPool[18] & 65519) | (value << 4));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 49151) | (value << 14));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65279) | (value << 8));
                break;
            case 44:
                writeToBufferPool(19, (bufferPool[19] & 65531) | (value << 2));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 61439) | (value << 12));
                break;
            case 46:
                writeToBufferPool(20, (bufferPool[20] & 65471) | (value << 6));
                break;
            case 47:
                writeToBufferPool(20, (bufferPool[20] & 65534) | (value));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 64511) | (value << 10));
                break;
            case 49:
                writeToBufferPool(21, (bufferPool[21] & 65519) | (value << 4));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 49151) | (value << 14));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65279) | (value << 8));
                break;
            case 52:
                writeToBufferPool(22, (bufferPool[22] & 65531) | (value << 2));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 61439) | (value << 12));
                break;
            case 54:
                writeToBufferPool(23, (bufferPool[23] & 65471) | (value << 6));
                break;
            case 55:
                writeToBufferPool(23, (bufferPool[23] & 65534) | (value));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 64511) | (value << 10));
                break;
            case 57:
                writeToBufferPool(24, (bufferPool[24] & 65519) | (value << 4));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 49151) | (value << 14));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65279) | (value << 8));
                break;
            case 60:
                writeToBufferPool(25, (bufferPool[25] & 65531) | (value << 2));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 61439) | (value << 12));
                break;
            case 62:
                writeToBufferPool(26, (bufferPool[26] & 65471) | (value << 6));
                break;
            case 63:
                writeToBufferPool(26, (bufferPool[26] & 65534) | (value));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 64511) | (value << 10));
                break;
            case 65:
                writeToBufferPool(27, (bufferPool[27] & 65519) | (value << 4));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 49151) | (value << 14));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65279) | (value << 8));
                break;
            case 68:
                writeToBufferPool(28, (bufferPool[28] & 65531) | (value << 2));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 61439) | (value << 12));
                break;
            case 70:
                writeToBufferPool(29, (bufferPool[29] & 65471) | (value << 6));
                break;
            case 71:
                writeToBufferPool(29, (bufferPool[29] & 65534) | (value));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 64511) | (value << 10));
                break;
            case 73:
                writeToBufferPool(30, (bufferPool[30] & 65519) | (value << 4));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 49151) | (value << 14));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65279) | (value << 8));
                break;
            case 76:
                writeToBufferPool(31, (bufferPool[31] & 65531) | (value << 2));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 61439) | (value << 12));
                break;
            case 78:
                writeToBufferPool(32, (bufferPool[32] & 65471) | (value << 6));
                break;
            case 79:
                writeToBufferPool(32, (bufferPool[32] & 65534) | (value));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 64511) | (value << 10));
                break;
            case 81:
                writeToBufferPool(33, (bufferPool[33] & 65519) | (value << 4));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 49151) | (value << 14));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65279) | (value << 8));
                break;
            case 84:
                writeToBufferPool(34, (bufferPool[34] & 65531) | (value << 2));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 61439) | (value << 12));
                break;
            case 86:
                writeToBufferPool(35, (bufferPool[35] & 65471) | (value << 6));
                break;
            case 87:
                writeToBufferPool(35, (bufferPool[35] & 65534) | (value));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 64511) | (value << 10));
                break;
            case 89:
                writeToBufferPool(36, (bufferPool[36] & 65519) | (value << 4));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 49151) | (value << 14));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65279) | (value << 8));
                break;
            case 92:
                writeToBufferPool(37, (bufferPool[37] & 65531) | (value << 2));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 61439) | (value << 12));
                break;
            case 94:
                writeToBufferPool(38, (bufferPool[38] & 65471) | (value << 6));
                break;
            case 95:
                writeToBufferPool(38, (bufferPool[38] & 65534) | (value));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 64511) | (value << 10));
                break;
            case 97:
                writeToBufferPool(39, (bufferPool[39] & 65519) | (value << 4));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 49151) | (value << 14));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65279) | (value << 8));
                break;
        }
    }

    public static int readSectorManaFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 512) >>> 9;
            case 1:
                return (rc.readSharedArray(3) & 8) >>> 3;
            case 2:
                return (rc.readSharedArray(4) & 8192) >>> 13;
            case 3:
                return (rc.readSharedArray(4) & 128) >>> 7;
            case 4:
                return (rc.readSharedArray(4) & 2) >>> 1;
            case 5:
                return (rc.readSharedArray(5) & 2048) >>> 11;
            case 6:
                return (rc.readSharedArray(5) & 32) >>> 5;
            case 7:
                return (rc.readSharedArray(6) & 32768) >>> 15;
            case 8:
                return (rc.readSharedArray(6) & 512) >>> 9;
            case 9:
                return (rc.readSharedArray(6) & 8) >>> 3;
            case 10:
                return (rc.readSharedArray(7) & 8192) >>> 13;
            case 11:
                return (rc.readSharedArray(7) & 128) >>> 7;
            case 12:
                return (rc.readSharedArray(7) & 2) >>> 1;
            case 13:
                return (rc.readSharedArray(8) & 2048) >>> 11;
            case 14:
                return (rc.readSharedArray(8) & 32) >>> 5;
            case 15:
                return (rc.readSharedArray(9) & 32768) >>> 15;
            case 16:
                return (rc.readSharedArray(9) & 512) >>> 9;
            case 17:
                return (rc.readSharedArray(9) & 8) >>> 3;
            case 18:
                return (rc.readSharedArray(10) & 8192) >>> 13;
            case 19:
                return (rc.readSharedArray(10) & 128) >>> 7;
            case 20:
                return (rc.readSharedArray(10) & 2) >>> 1;
            case 21:
                return (rc.readSharedArray(11) & 2048) >>> 11;
            case 22:
                return (rc.readSharedArray(11) & 32) >>> 5;
            case 23:
                return (rc.readSharedArray(12) & 32768) >>> 15;
            case 24:
                return (rc.readSharedArray(12) & 512) >>> 9;
            case 25:
                return (rc.readSharedArray(12) & 8) >>> 3;
            case 26:
                return (rc.readSharedArray(13) & 8192) >>> 13;
            case 27:
                return (rc.readSharedArray(13) & 128) >>> 7;
            case 28:
                return (rc.readSharedArray(13) & 2) >>> 1;
            case 29:
                return (rc.readSharedArray(14) & 2048) >>> 11;
            case 30:
                return (rc.readSharedArray(14) & 32) >>> 5;
            case 31:
                return (rc.readSharedArray(15) & 32768) >>> 15;
            case 32:
                return (rc.readSharedArray(15) & 512) >>> 9;
            case 33:
                return (rc.readSharedArray(15) & 8) >>> 3;
            case 34:
                return (rc.readSharedArray(16) & 8192) >>> 13;
            case 35:
                return (rc.readSharedArray(16) & 128) >>> 7;
            case 36:
                return (rc.readSharedArray(16) & 2) >>> 1;
            case 37:
                return (rc.readSharedArray(17) & 2048) >>> 11;
            case 38:
                return (rc.readSharedArray(17) & 32) >>> 5;
            case 39:
                return (rc.readSharedArray(18) & 32768) >>> 15;
            case 40:
                return (rc.readSharedArray(18) & 512) >>> 9;
            case 41:
                return (rc.readSharedArray(18) & 8) >>> 3;
            case 42:
                return (rc.readSharedArray(19) & 8192) >>> 13;
            case 43:
                return (rc.readSharedArray(19) & 128) >>> 7;
            case 44:
                return (rc.readSharedArray(19) & 2) >>> 1;
            case 45:
                return (rc.readSharedArray(20) & 2048) >>> 11;
            case 46:
                return (rc.readSharedArray(20) & 32) >>> 5;
            case 47:
                return (rc.readSharedArray(21) & 32768) >>> 15;
            case 48:
                return (rc.readSharedArray(21) & 512) >>> 9;
            case 49:
                return (rc.readSharedArray(21) & 8) >>> 3;
            case 50:
                return (rc.readSharedArray(22) & 8192) >>> 13;
            case 51:
                return (rc.readSharedArray(22) & 128) >>> 7;
            case 52:
                return (rc.readSharedArray(22) & 2) >>> 1;
            case 53:
                return (rc.readSharedArray(23) & 2048) >>> 11;
            case 54:
                return (rc.readSharedArray(23) & 32) >>> 5;
            case 55:
                return (rc.readSharedArray(24) & 32768) >>> 15;
            case 56:
                return (rc.readSharedArray(24) & 512) >>> 9;
            case 57:
                return (rc.readSharedArray(24) & 8) >>> 3;
            case 58:
                return (rc.readSharedArray(25) & 8192) >>> 13;
            case 59:
                return (rc.readSharedArray(25) & 128) >>> 7;
            case 60:
                return (rc.readSharedArray(25) & 2) >>> 1;
            case 61:
                return (rc.readSharedArray(26) & 2048) >>> 11;
            case 62:
                return (rc.readSharedArray(26) & 32) >>> 5;
            case 63:
                return (rc.readSharedArray(27) & 32768) >>> 15;
            case 64:
                return (rc.readSharedArray(27) & 512) >>> 9;
            case 65:
                return (rc.readSharedArray(27) & 8) >>> 3;
            case 66:
                return (rc.readSharedArray(28) & 8192) >>> 13;
            case 67:
                return (rc.readSharedArray(28) & 128) >>> 7;
            case 68:
                return (rc.readSharedArray(28) & 2) >>> 1;
            case 69:
                return (rc.readSharedArray(29) & 2048) >>> 11;
            case 70:
                return (rc.readSharedArray(29) & 32) >>> 5;
            case 71:
                return (rc.readSharedArray(30) & 32768) >>> 15;
            case 72:
                return (rc.readSharedArray(30) & 512) >>> 9;
            case 73:
                return (rc.readSharedArray(30) & 8) >>> 3;
            case 74:
                return (rc.readSharedArray(31) & 8192) >>> 13;
            case 75:
                return (rc.readSharedArray(31) & 128) >>> 7;
            case 76:
                return (rc.readSharedArray(31) & 2) >>> 1;
            case 77:
                return (rc.readSharedArray(32) & 2048) >>> 11;
            case 78:
                return (rc.readSharedArray(32) & 32) >>> 5;
            case 79:
                return (rc.readSharedArray(33) & 32768) >>> 15;
            case 80:
                return (rc.readSharedArray(33) & 512) >>> 9;
            case 81:
                return (rc.readSharedArray(33) & 8) >>> 3;
            case 82:
                return (rc.readSharedArray(34) & 8192) >>> 13;
            case 83:
                return (rc.readSharedArray(34) & 128) >>> 7;
            case 84:
                return (rc.readSharedArray(34) & 2) >>> 1;
            case 85:
                return (rc.readSharedArray(35) & 2048) >>> 11;
            case 86:
                return (rc.readSharedArray(35) & 32) >>> 5;
            case 87:
                return (rc.readSharedArray(36) & 32768) >>> 15;
            case 88:
                return (rc.readSharedArray(36) & 512) >>> 9;
            case 89:
                return (rc.readSharedArray(36) & 8) >>> 3;
            case 90:
                return (rc.readSharedArray(37) & 8192) >>> 13;
            case 91:
                return (rc.readSharedArray(37) & 128) >>> 7;
            case 92:
                return (rc.readSharedArray(37) & 2) >>> 1;
            case 93:
                return (rc.readSharedArray(38) & 2048) >>> 11;
            case 94:
                return (rc.readSharedArray(38) & 32) >>> 5;
            case 95:
                return (rc.readSharedArray(39) & 32768) >>> 15;
            case 96:
                return (rc.readSharedArray(39) & 512) >>> 9;
            case 97:
                return (rc.readSharedArray(39) & 8) >>> 3;
            case 98:
                return (rc.readSharedArray(40) & 8192) >>> 13;
            case 99:
                return (rc.readSharedArray(40) & 128) >>> 7;
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
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65527) | (value << 3));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 57343) | (value << 13));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65407) | (value << 7));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65533) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 63487) | (value << 11));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65503) | (value << 5));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 32767) | (value << 15));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65023) | (value << 9));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65527) | (value << 3));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 57343) | (value << 13));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65407) | (value << 7));
                break;
            case 12:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65533) | (value << 1));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63487) | (value << 11));
                break;
            case 14:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65503) | (value << 5));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 32767) | (value << 15));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65023) | (value << 9));
                break;
            case 17:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65527) | (value << 3));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 57343) | (value << 13));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65407) | (value << 7));
                break;
            case 20:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65533) | (value << 1));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 63487) | (value << 11));
                break;
            case 22:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65503) | (value << 5));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 32767) | (value << 15));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65023) | (value << 9));
                break;
            case 25:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65527) | (value << 3));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 57343) | (value << 13));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65407) | (value << 7));
                break;
            case 28:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65533) | (value << 1));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 63487) | (value << 11));
                break;
            case 30:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65503) | (value << 5));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 32767) | (value << 15));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65023) | (value << 9));
                break;
            case 33:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65527) | (value << 3));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 57343) | (value << 13));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65407) | (value << 7));
                break;
            case 36:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65533) | (value << 1));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 63487) | (value << 11));
                break;
            case 38:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65503) | (value << 5));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 32767) | (value << 15));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65023) | (value << 9));
                break;
            case 41:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65527) | (value << 3));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 57343) | (value << 13));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65407) | (value << 7));
                break;
            case 44:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65533) | (value << 1));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63487) | (value << 11));
                break;
            case 46:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65503) | (value << 5));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 32767) | (value << 15));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65023) | (value << 9));
                break;
            case 49:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65527) | (value << 3));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 57343) | (value << 13));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65407) | (value << 7));
                break;
            case 52:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65533) | (value << 1));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 63487) | (value << 11));
                break;
            case 54:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65503) | (value << 5));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 32767) | (value << 15));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65023) | (value << 9));
                break;
            case 57:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65527) | (value << 3));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 57343) | (value << 13));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65407) | (value << 7));
                break;
            case 60:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65533) | (value << 1));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 63487) | (value << 11));
                break;
            case 62:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65503) | (value << 5));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 32767) | (value << 15));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65023) | (value << 9));
                break;
            case 65:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65527) | (value << 3));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 57343) | (value << 13));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65407) | (value << 7));
                break;
            case 68:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65533) | (value << 1));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63487) | (value << 11));
                break;
            case 70:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65503) | (value << 5));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 32767) | (value << 15));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65023) | (value << 9));
                break;
            case 73:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65527) | (value << 3));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 57343) | (value << 13));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65407) | (value << 7));
                break;
            case 76:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65533) | (value << 1));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 63487) | (value << 11));
                break;
            case 78:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65503) | (value << 5));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 32767) | (value << 15));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65023) | (value << 9));
                break;
            case 81:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65527) | (value << 3));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 57343) | (value << 13));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65407) | (value << 7));
                break;
            case 84:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65533) | (value << 1));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 63487) | (value << 11));
                break;
            case 86:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65503) | (value << 5));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 32767) | (value << 15));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65023) | (value << 9));
                break;
            case 89:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65527) | (value << 3));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 57343) | (value << 13));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65407) | (value << 7));
                break;
            case 92:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65533) | (value << 1));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 63487) | (value << 11));
                break;
            case 94:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65503) | (value << 5));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 32767) | (value << 15));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65023) | (value << 9));
                break;
            case 97:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65527) | (value << 3));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 57343) | (value << 13));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65407) | (value << 7));
                break;
        }
    }

    public static void writeBPSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65023) | (value << 9));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65527) | (value << 3));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 57343) | (value << 13));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65407) | (value << 7));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65533) | (value << 1));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 63487) | (value << 11));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65503) | (value << 5));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 32767) | (value << 15));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65023) | (value << 9));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65527) | (value << 3));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 57343) | (value << 13));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65407) | (value << 7));
                break;
            case 12:
                writeToBufferPool(7, (bufferPool[7] & 65533) | (value << 1));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 63487) | (value << 11));
                break;
            case 14:
                writeToBufferPool(8, (bufferPool[8] & 65503) | (value << 5));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 32767) | (value << 15));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65023) | (value << 9));
                break;
            case 17:
                writeToBufferPool(9, (bufferPool[9] & 65527) | (value << 3));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 57343) | (value << 13));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65407) | (value << 7));
                break;
            case 20:
                writeToBufferPool(10, (bufferPool[10] & 65533) | (value << 1));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 63487) | (value << 11));
                break;
            case 22:
                writeToBufferPool(11, (bufferPool[11] & 65503) | (value << 5));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 32767) | (value << 15));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65023) | (value << 9));
                break;
            case 25:
                writeToBufferPool(12, (bufferPool[12] & 65527) | (value << 3));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 57343) | (value << 13));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65407) | (value << 7));
                break;
            case 28:
                writeToBufferPool(13, (bufferPool[13] & 65533) | (value << 1));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 63487) | (value << 11));
                break;
            case 30:
                writeToBufferPool(14, (bufferPool[14] & 65503) | (value << 5));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 32767) | (value << 15));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65023) | (value << 9));
                break;
            case 33:
                writeToBufferPool(15, (bufferPool[15] & 65527) | (value << 3));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 57343) | (value << 13));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65407) | (value << 7));
                break;
            case 36:
                writeToBufferPool(16, (bufferPool[16] & 65533) | (value << 1));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 63487) | (value << 11));
                break;
            case 38:
                writeToBufferPool(17, (bufferPool[17] & 65503) | (value << 5));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 32767) | (value << 15));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65023) | (value << 9));
                break;
            case 41:
                writeToBufferPool(18, (bufferPool[18] & 65527) | (value << 3));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 57343) | (value << 13));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65407) | (value << 7));
                break;
            case 44:
                writeToBufferPool(19, (bufferPool[19] & 65533) | (value << 1));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 63487) | (value << 11));
                break;
            case 46:
                writeToBufferPool(20, (bufferPool[20] & 65503) | (value << 5));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 32767) | (value << 15));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65023) | (value << 9));
                break;
            case 49:
                writeToBufferPool(21, (bufferPool[21] & 65527) | (value << 3));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 57343) | (value << 13));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65407) | (value << 7));
                break;
            case 52:
                writeToBufferPool(22, (bufferPool[22] & 65533) | (value << 1));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 63487) | (value << 11));
                break;
            case 54:
                writeToBufferPool(23, (bufferPool[23] & 65503) | (value << 5));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 32767) | (value << 15));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65023) | (value << 9));
                break;
            case 57:
                writeToBufferPool(24, (bufferPool[24] & 65527) | (value << 3));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 57343) | (value << 13));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65407) | (value << 7));
                break;
            case 60:
                writeToBufferPool(25, (bufferPool[25] & 65533) | (value << 1));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 63487) | (value << 11));
                break;
            case 62:
                writeToBufferPool(26, (bufferPool[26] & 65503) | (value << 5));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 32767) | (value << 15));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65023) | (value << 9));
                break;
            case 65:
                writeToBufferPool(27, (bufferPool[27] & 65527) | (value << 3));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 57343) | (value << 13));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65407) | (value << 7));
                break;
            case 68:
                writeToBufferPool(28, (bufferPool[28] & 65533) | (value << 1));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 63487) | (value << 11));
                break;
            case 70:
                writeToBufferPool(29, (bufferPool[29] & 65503) | (value << 5));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 32767) | (value << 15));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65023) | (value << 9));
                break;
            case 73:
                writeToBufferPool(30, (bufferPool[30] & 65527) | (value << 3));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 57343) | (value << 13));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65407) | (value << 7));
                break;
            case 76:
                writeToBufferPool(31, (bufferPool[31] & 65533) | (value << 1));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 63487) | (value << 11));
                break;
            case 78:
                writeToBufferPool(32, (bufferPool[32] & 65503) | (value << 5));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 32767) | (value << 15));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65023) | (value << 9));
                break;
            case 81:
                writeToBufferPool(33, (bufferPool[33] & 65527) | (value << 3));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 57343) | (value << 13));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65407) | (value << 7));
                break;
            case 84:
                writeToBufferPool(34, (bufferPool[34] & 65533) | (value << 1));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 63487) | (value << 11));
                break;
            case 86:
                writeToBufferPool(35, (bufferPool[35] & 65503) | (value << 5));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 32767) | (value << 15));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65023) | (value << 9));
                break;
            case 89:
                writeToBufferPool(36, (bufferPool[36] & 65527) | (value << 3));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 57343) | (value << 13));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65407) | (value << 7));
                break;
            case 92:
                writeToBufferPool(37, (bufferPool[37] & 65533) | (value << 1));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 63487) | (value << 11));
                break;
            case 94:
                writeToBufferPool(38, (bufferPool[38] & 65503) | (value << 5));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 32767) | (value << 15));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65023) | (value << 9));
                break;
            case 97:
                writeToBufferPool(39, (bufferPool[39] & 65527) | (value << 3));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 57343) | (value << 13));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65407) | (value << 7));
                break;
        }
    }

    public static int readSectorControlStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 448) >>> 6;
            case 1:
                return (rc.readSharedArray(3) & 7);
            case 2:
                return (rc.readSharedArray(4) & 7168) >>> 10;
            case 3:
                return (rc.readSharedArray(4) & 112) >>> 4;
            case 4:
                return ((rc.readSharedArray(4) & 1) << 2) + ((rc.readSharedArray(5) & 49152) >>> 14);
            case 5:
                return (rc.readSharedArray(5) & 1792) >>> 8;
            case 6:
                return (rc.readSharedArray(5) & 28) >>> 2;
            case 7:
                return (rc.readSharedArray(6) & 28672) >>> 12;
            case 8:
                return (rc.readSharedArray(6) & 448) >>> 6;
            case 9:
                return (rc.readSharedArray(6) & 7);
            case 10:
                return (rc.readSharedArray(7) & 7168) >>> 10;
            case 11:
                return (rc.readSharedArray(7) & 112) >>> 4;
            case 12:
                return ((rc.readSharedArray(7) & 1) << 2) + ((rc.readSharedArray(8) & 49152) >>> 14);
            case 13:
                return (rc.readSharedArray(8) & 1792) >>> 8;
            case 14:
                return (rc.readSharedArray(8) & 28) >>> 2;
            case 15:
                return (rc.readSharedArray(9) & 28672) >>> 12;
            case 16:
                return (rc.readSharedArray(9) & 448) >>> 6;
            case 17:
                return (rc.readSharedArray(9) & 7);
            case 18:
                return (rc.readSharedArray(10) & 7168) >>> 10;
            case 19:
                return (rc.readSharedArray(10) & 112) >>> 4;
            case 20:
                return ((rc.readSharedArray(10) & 1) << 2) + ((rc.readSharedArray(11) & 49152) >>> 14);
            case 21:
                return (rc.readSharedArray(11) & 1792) >>> 8;
            case 22:
                return (rc.readSharedArray(11) & 28) >>> 2;
            case 23:
                return (rc.readSharedArray(12) & 28672) >>> 12;
            case 24:
                return (rc.readSharedArray(12) & 448) >>> 6;
            case 25:
                return (rc.readSharedArray(12) & 7);
            case 26:
                return (rc.readSharedArray(13) & 7168) >>> 10;
            case 27:
                return (rc.readSharedArray(13) & 112) >>> 4;
            case 28:
                return ((rc.readSharedArray(13) & 1) << 2) + ((rc.readSharedArray(14) & 49152) >>> 14);
            case 29:
                return (rc.readSharedArray(14) & 1792) >>> 8;
            case 30:
                return (rc.readSharedArray(14) & 28) >>> 2;
            case 31:
                return (rc.readSharedArray(15) & 28672) >>> 12;
            case 32:
                return (rc.readSharedArray(15) & 448) >>> 6;
            case 33:
                return (rc.readSharedArray(15) & 7);
            case 34:
                return (rc.readSharedArray(16) & 7168) >>> 10;
            case 35:
                return (rc.readSharedArray(16) & 112) >>> 4;
            case 36:
                return ((rc.readSharedArray(16) & 1) << 2) + ((rc.readSharedArray(17) & 49152) >>> 14);
            case 37:
                return (rc.readSharedArray(17) & 1792) >>> 8;
            case 38:
                return (rc.readSharedArray(17) & 28) >>> 2;
            case 39:
                return (rc.readSharedArray(18) & 28672) >>> 12;
            case 40:
                return (rc.readSharedArray(18) & 448) >>> 6;
            case 41:
                return (rc.readSharedArray(18) & 7);
            case 42:
                return (rc.readSharedArray(19) & 7168) >>> 10;
            case 43:
                return (rc.readSharedArray(19) & 112) >>> 4;
            case 44:
                return ((rc.readSharedArray(19) & 1) << 2) + ((rc.readSharedArray(20) & 49152) >>> 14);
            case 45:
                return (rc.readSharedArray(20) & 1792) >>> 8;
            case 46:
                return (rc.readSharedArray(20) & 28) >>> 2;
            case 47:
                return (rc.readSharedArray(21) & 28672) >>> 12;
            case 48:
                return (rc.readSharedArray(21) & 448) >>> 6;
            case 49:
                return (rc.readSharedArray(21) & 7);
            case 50:
                return (rc.readSharedArray(22) & 7168) >>> 10;
            case 51:
                return (rc.readSharedArray(22) & 112) >>> 4;
            case 52:
                return ((rc.readSharedArray(22) & 1) << 2) + ((rc.readSharedArray(23) & 49152) >>> 14);
            case 53:
                return (rc.readSharedArray(23) & 1792) >>> 8;
            case 54:
                return (rc.readSharedArray(23) & 28) >>> 2;
            case 55:
                return (rc.readSharedArray(24) & 28672) >>> 12;
            case 56:
                return (rc.readSharedArray(24) & 448) >>> 6;
            case 57:
                return (rc.readSharedArray(24) & 7);
            case 58:
                return (rc.readSharedArray(25) & 7168) >>> 10;
            case 59:
                return (rc.readSharedArray(25) & 112) >>> 4;
            case 60:
                return ((rc.readSharedArray(25) & 1) << 2) + ((rc.readSharedArray(26) & 49152) >>> 14);
            case 61:
                return (rc.readSharedArray(26) & 1792) >>> 8;
            case 62:
                return (rc.readSharedArray(26) & 28) >>> 2;
            case 63:
                return (rc.readSharedArray(27) & 28672) >>> 12;
            case 64:
                return (rc.readSharedArray(27) & 448) >>> 6;
            case 65:
                return (rc.readSharedArray(27) & 7);
            case 66:
                return (rc.readSharedArray(28) & 7168) >>> 10;
            case 67:
                return (rc.readSharedArray(28) & 112) >>> 4;
            case 68:
                return ((rc.readSharedArray(28) & 1) << 2) + ((rc.readSharedArray(29) & 49152) >>> 14);
            case 69:
                return (rc.readSharedArray(29) & 1792) >>> 8;
            case 70:
                return (rc.readSharedArray(29) & 28) >>> 2;
            case 71:
                return (rc.readSharedArray(30) & 28672) >>> 12;
            case 72:
                return (rc.readSharedArray(30) & 448) >>> 6;
            case 73:
                return (rc.readSharedArray(30) & 7);
            case 74:
                return (rc.readSharedArray(31) & 7168) >>> 10;
            case 75:
                return (rc.readSharedArray(31) & 112) >>> 4;
            case 76:
                return ((rc.readSharedArray(31) & 1) << 2) + ((rc.readSharedArray(32) & 49152) >>> 14);
            case 77:
                return (rc.readSharedArray(32) & 1792) >>> 8;
            case 78:
                return (rc.readSharedArray(32) & 28) >>> 2;
            case 79:
                return (rc.readSharedArray(33) & 28672) >>> 12;
            case 80:
                return (rc.readSharedArray(33) & 448) >>> 6;
            case 81:
                return (rc.readSharedArray(33) & 7);
            case 82:
                return (rc.readSharedArray(34) & 7168) >>> 10;
            case 83:
                return (rc.readSharedArray(34) & 112) >>> 4;
            case 84:
                return ((rc.readSharedArray(34) & 1) << 2) + ((rc.readSharedArray(35) & 49152) >>> 14);
            case 85:
                return (rc.readSharedArray(35) & 1792) >>> 8;
            case 86:
                return (rc.readSharedArray(35) & 28) >>> 2;
            case 87:
                return (rc.readSharedArray(36) & 28672) >>> 12;
            case 88:
                return (rc.readSharedArray(36) & 448) >>> 6;
            case 89:
                return (rc.readSharedArray(36) & 7);
            case 90:
                return (rc.readSharedArray(37) & 7168) >>> 10;
            case 91:
                return (rc.readSharedArray(37) & 112) >>> 4;
            case 92:
                return ((rc.readSharedArray(37) & 1) << 2) + ((rc.readSharedArray(38) & 49152) >>> 14);
            case 93:
                return (rc.readSharedArray(38) & 1792) >>> 8;
            case 94:
                return (rc.readSharedArray(38) & 28) >>> 2;
            case 95:
                return (rc.readSharedArray(39) & 28672) >>> 12;
            case 96:
                return (rc.readSharedArray(39) & 448) >>> 6;
            case 97:
                return (rc.readSharedArray(39) & 7);
            case 98:
                return (rc.readSharedArray(40) & 7168) >>> 10;
            case 99:
                return (rc.readSharedArray(40) & 112) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65087) | (value << 6));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65528) | (value));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 58367) | (value << 10));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65423) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 16383) | ((value & 3) << 14));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 63743) | (value << 8));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65507) | (value << 2));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 36863) | (value << 12));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65087) | (value << 6));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65528) | (value));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 58367) | (value << 10));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65423) | (value << 4));
                break;
            case 12:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 16383) | ((value & 3) << 14));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63743) | (value << 8));
                break;
            case 14:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65507) | (value << 2));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 36863) | (value << 12));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65087) | (value << 6));
                break;
            case 17:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65528) | (value));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 58367) | (value << 10));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65423) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 16383) | ((value & 3) << 14));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 63743) | (value << 8));
                break;
            case 22:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65507) | (value << 2));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 36863) | (value << 12));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65087) | (value << 6));
                break;
            case 25:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65528) | (value));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 58367) | (value << 10));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65423) | (value << 4));
                break;
            case 28:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 16383) | ((value & 3) << 14));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 63743) | (value << 8));
                break;
            case 30:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65507) | (value << 2));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 36863) | (value << 12));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65087) | (value << 6));
                break;
            case 33:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65528) | (value));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 58367) | (value << 10));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65423) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 16383) | ((value & 3) << 14));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 63743) | (value << 8));
                break;
            case 38:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65507) | (value << 2));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 36863) | (value << 12));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65087) | (value << 6));
                break;
            case 41:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65528) | (value));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 58367) | (value << 10));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65423) | (value << 4));
                break;
            case 44:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 16383) | ((value & 3) << 14));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63743) | (value << 8));
                break;
            case 46:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65507) | (value << 2));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 36863) | (value << 12));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65087) | (value << 6));
                break;
            case 49:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65528) | (value));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 58367) | (value << 10));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65423) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 16383) | ((value & 3) << 14));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 63743) | (value << 8));
                break;
            case 54:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65507) | (value << 2));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 36863) | (value << 12));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65087) | (value << 6));
                break;
            case 57:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65528) | (value));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 58367) | (value << 10));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65423) | (value << 4));
                break;
            case 60:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 16383) | ((value & 3) << 14));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 63743) | (value << 8));
                break;
            case 62:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65507) | (value << 2));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 36863) | (value << 12));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65087) | (value << 6));
                break;
            case 65:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65528) | (value));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 58367) | (value << 10));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65423) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 16383) | ((value & 3) << 14));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63743) | (value << 8));
                break;
            case 70:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65507) | (value << 2));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 36863) | (value << 12));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65087) | (value << 6));
                break;
            case 73:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65528) | (value));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 58367) | (value << 10));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65423) | (value << 4));
                break;
            case 76:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 16383) | ((value & 3) << 14));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 63743) | (value << 8));
                break;
            case 78:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65507) | (value << 2));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 36863) | (value << 12));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65087) | (value << 6));
                break;
            case 81:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65528) | (value));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 58367) | (value << 10));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65423) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 16383) | ((value & 3) << 14));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 63743) | (value << 8));
                break;
            case 86:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65507) | (value << 2));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 36863) | (value << 12));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65087) | (value << 6));
                break;
            case 89:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65528) | (value));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 58367) | (value << 10));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65423) | (value << 4));
                break;
            case 92:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 16383) | ((value & 3) << 14));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 63743) | (value << 8));
                break;
            case 94:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65507) | (value << 2));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 36863) | (value << 12));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65087) | (value << 6));
                break;
            case 97:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65528) | (value));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 58367) | (value << 10));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65423) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65087) | (value << 6));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65528) | (value));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 58367) | (value << 10));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65423) | (value << 4));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(5, (bufferPool[5] & 16383) | ((value & 3) << 14));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 63743) | (value << 8));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65507) | (value << 2));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 36863) | (value << 12));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65087) | (value << 6));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65528) | (value));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 58367) | (value << 10));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65423) | (value << 4));
                break;
            case 12:
                writeToBufferPool(7, (bufferPool[7] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(8, (bufferPool[8] & 16383) | ((value & 3) << 14));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 63743) | (value << 8));
                break;
            case 14:
                writeToBufferPool(8, (bufferPool[8] & 65507) | (value << 2));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 36863) | (value << 12));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65087) | (value << 6));
                break;
            case 17:
                writeToBufferPool(9, (bufferPool[9] & 65528) | (value));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 58367) | (value << 10));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65423) | (value << 4));
                break;
            case 20:
                writeToBufferPool(10, (bufferPool[10] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(11, (bufferPool[11] & 16383) | ((value & 3) << 14));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 63743) | (value << 8));
                break;
            case 22:
                writeToBufferPool(11, (bufferPool[11] & 65507) | (value << 2));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 36863) | (value << 12));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65087) | (value << 6));
                break;
            case 25:
                writeToBufferPool(12, (bufferPool[12] & 65528) | (value));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 58367) | (value << 10));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65423) | (value << 4));
                break;
            case 28:
                writeToBufferPool(13, (bufferPool[13] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(14, (bufferPool[14] & 16383) | ((value & 3) << 14));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 63743) | (value << 8));
                break;
            case 30:
                writeToBufferPool(14, (bufferPool[14] & 65507) | (value << 2));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 36863) | (value << 12));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65087) | (value << 6));
                break;
            case 33:
                writeToBufferPool(15, (bufferPool[15] & 65528) | (value));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 58367) | (value << 10));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65423) | (value << 4));
                break;
            case 36:
                writeToBufferPool(16, (bufferPool[16] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(17, (bufferPool[17] & 16383) | ((value & 3) << 14));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 63743) | (value << 8));
                break;
            case 38:
                writeToBufferPool(17, (bufferPool[17] & 65507) | (value << 2));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 36863) | (value << 12));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65087) | (value << 6));
                break;
            case 41:
                writeToBufferPool(18, (bufferPool[18] & 65528) | (value));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 58367) | (value << 10));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65423) | (value << 4));
                break;
            case 44:
                writeToBufferPool(19, (bufferPool[19] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(20, (bufferPool[20] & 16383) | ((value & 3) << 14));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 63743) | (value << 8));
                break;
            case 46:
                writeToBufferPool(20, (bufferPool[20] & 65507) | (value << 2));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 36863) | (value << 12));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65087) | (value << 6));
                break;
            case 49:
                writeToBufferPool(21, (bufferPool[21] & 65528) | (value));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 58367) | (value << 10));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65423) | (value << 4));
                break;
            case 52:
                writeToBufferPool(22, (bufferPool[22] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(23, (bufferPool[23] & 16383) | ((value & 3) << 14));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 63743) | (value << 8));
                break;
            case 54:
                writeToBufferPool(23, (bufferPool[23] & 65507) | (value << 2));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 36863) | (value << 12));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65087) | (value << 6));
                break;
            case 57:
                writeToBufferPool(24, (bufferPool[24] & 65528) | (value));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 58367) | (value << 10));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65423) | (value << 4));
                break;
            case 60:
                writeToBufferPool(25, (bufferPool[25] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(26, (bufferPool[26] & 16383) | ((value & 3) << 14));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 63743) | (value << 8));
                break;
            case 62:
                writeToBufferPool(26, (bufferPool[26] & 65507) | (value << 2));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 36863) | (value << 12));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65087) | (value << 6));
                break;
            case 65:
                writeToBufferPool(27, (bufferPool[27] & 65528) | (value));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 58367) | (value << 10));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65423) | (value << 4));
                break;
            case 68:
                writeToBufferPool(28, (bufferPool[28] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(29, (bufferPool[29] & 16383) | ((value & 3) << 14));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 63743) | (value << 8));
                break;
            case 70:
                writeToBufferPool(29, (bufferPool[29] & 65507) | (value << 2));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 36863) | (value << 12));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65087) | (value << 6));
                break;
            case 73:
                writeToBufferPool(30, (bufferPool[30] & 65528) | (value));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 58367) | (value << 10));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65423) | (value << 4));
                break;
            case 76:
                writeToBufferPool(31, (bufferPool[31] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(32, (bufferPool[32] & 16383) | ((value & 3) << 14));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 63743) | (value << 8));
                break;
            case 78:
                writeToBufferPool(32, (bufferPool[32] & 65507) | (value << 2));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 36863) | (value << 12));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65087) | (value << 6));
                break;
            case 81:
                writeToBufferPool(33, (bufferPool[33] & 65528) | (value));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 58367) | (value << 10));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65423) | (value << 4));
                break;
            case 84:
                writeToBufferPool(34, (bufferPool[34] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(35, (bufferPool[35] & 16383) | ((value & 3) << 14));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 63743) | (value << 8));
                break;
            case 86:
                writeToBufferPool(35, (bufferPool[35] & 65507) | (value << 2));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 36863) | (value << 12));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65087) | (value << 6));
                break;
            case 89:
                writeToBufferPool(36, (bufferPool[36] & 65528) | (value));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 58367) | (value << 10));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65423) | (value << 4));
                break;
            case 92:
                writeToBufferPool(37, (bufferPool[37] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(38, (bufferPool[38] & 16383) | ((value & 3) << 14));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 63743) | (value << 8));
                break;
            case 94:
                writeToBufferPool(38, (bufferPool[38] & 65507) | (value << 2));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 36863) | (value << 12));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65087) | (value << 6));
                break;
            case 97:
                writeToBufferPool(39, (bufferPool[39] & 65528) | (value));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 58367) | (value << 10));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65423) | (value << 4));
                break;
        }
    }

    public static int readSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 4032) >>> 6;
            case 1:
                return (rc.readSharedArray(3) & 63);
            case 2:
                return (rc.readSharedArray(4) & 64512) >>> 10;
            case 3:
                return (rc.readSharedArray(4) & 1008) >>> 4;
            case 4:
                return ((rc.readSharedArray(4) & 15) << 2) + ((rc.readSharedArray(5) & 49152) >>> 14);
            case 5:
                return (rc.readSharedArray(5) & 16128) >>> 8;
            case 6:
                return (rc.readSharedArray(5) & 252) >>> 2;
            case 7:
                return ((rc.readSharedArray(5) & 3) << 4) + ((rc.readSharedArray(6) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(6) & 4032) >>> 6;
            case 9:
                return (rc.readSharedArray(6) & 63);
            case 10:
                return (rc.readSharedArray(7) & 64512) >>> 10;
            case 11:
                return (rc.readSharedArray(7) & 1008) >>> 4;
            case 12:
                return ((rc.readSharedArray(7) & 15) << 2) + ((rc.readSharedArray(8) & 49152) >>> 14);
            case 13:
                return (rc.readSharedArray(8) & 16128) >>> 8;
            case 14:
                return (rc.readSharedArray(8) & 252) >>> 2;
            case 15:
                return ((rc.readSharedArray(8) & 3) << 4) + ((rc.readSharedArray(9) & 61440) >>> 12);
            case 16:
                return (rc.readSharedArray(9) & 4032) >>> 6;
            case 17:
                return (rc.readSharedArray(9) & 63);
            case 18:
                return (rc.readSharedArray(10) & 64512) >>> 10;
            case 19:
                return (rc.readSharedArray(10) & 1008) >>> 4;
            case 20:
                return ((rc.readSharedArray(10) & 15) << 2) + ((rc.readSharedArray(11) & 49152) >>> 14);
            case 21:
                return (rc.readSharedArray(11) & 16128) >>> 8;
            case 22:
                return (rc.readSharedArray(11) & 252) >>> 2;
            case 23:
                return ((rc.readSharedArray(11) & 3) << 4) + ((rc.readSharedArray(12) & 61440) >>> 12);
            case 24:
                return (rc.readSharedArray(12) & 4032) >>> 6;
            case 25:
                return (rc.readSharedArray(12) & 63);
            case 26:
                return (rc.readSharedArray(13) & 64512) >>> 10;
            case 27:
                return (rc.readSharedArray(13) & 1008) >>> 4;
            case 28:
                return ((rc.readSharedArray(13) & 15) << 2) + ((rc.readSharedArray(14) & 49152) >>> 14);
            case 29:
                return (rc.readSharedArray(14) & 16128) >>> 8;
            case 30:
                return (rc.readSharedArray(14) & 252) >>> 2;
            case 31:
                return ((rc.readSharedArray(14) & 3) << 4) + ((rc.readSharedArray(15) & 61440) >>> 12);
            case 32:
                return (rc.readSharedArray(15) & 4032) >>> 6;
            case 33:
                return (rc.readSharedArray(15) & 63);
            case 34:
                return (rc.readSharedArray(16) & 64512) >>> 10;
            case 35:
                return (rc.readSharedArray(16) & 1008) >>> 4;
            case 36:
                return ((rc.readSharedArray(16) & 15) << 2) + ((rc.readSharedArray(17) & 49152) >>> 14);
            case 37:
                return (rc.readSharedArray(17) & 16128) >>> 8;
            case 38:
                return (rc.readSharedArray(17) & 252) >>> 2;
            case 39:
                return ((rc.readSharedArray(17) & 3) << 4) + ((rc.readSharedArray(18) & 61440) >>> 12);
            case 40:
                return (rc.readSharedArray(18) & 4032) >>> 6;
            case 41:
                return (rc.readSharedArray(18) & 63);
            case 42:
                return (rc.readSharedArray(19) & 64512) >>> 10;
            case 43:
                return (rc.readSharedArray(19) & 1008) >>> 4;
            case 44:
                return ((rc.readSharedArray(19) & 15) << 2) + ((rc.readSharedArray(20) & 49152) >>> 14);
            case 45:
                return (rc.readSharedArray(20) & 16128) >>> 8;
            case 46:
                return (rc.readSharedArray(20) & 252) >>> 2;
            case 47:
                return ((rc.readSharedArray(20) & 3) << 4) + ((rc.readSharedArray(21) & 61440) >>> 12);
            case 48:
                return (rc.readSharedArray(21) & 4032) >>> 6;
            case 49:
                return (rc.readSharedArray(21) & 63);
            case 50:
                return (rc.readSharedArray(22) & 64512) >>> 10;
            case 51:
                return (rc.readSharedArray(22) & 1008) >>> 4;
            case 52:
                return ((rc.readSharedArray(22) & 15) << 2) + ((rc.readSharedArray(23) & 49152) >>> 14);
            case 53:
                return (rc.readSharedArray(23) & 16128) >>> 8;
            case 54:
                return (rc.readSharedArray(23) & 252) >>> 2;
            case 55:
                return ((rc.readSharedArray(23) & 3) << 4) + ((rc.readSharedArray(24) & 61440) >>> 12);
            case 56:
                return (rc.readSharedArray(24) & 4032) >>> 6;
            case 57:
                return (rc.readSharedArray(24) & 63);
            case 58:
                return (rc.readSharedArray(25) & 64512) >>> 10;
            case 59:
                return (rc.readSharedArray(25) & 1008) >>> 4;
            case 60:
                return ((rc.readSharedArray(25) & 15) << 2) + ((rc.readSharedArray(26) & 49152) >>> 14);
            case 61:
                return (rc.readSharedArray(26) & 16128) >>> 8;
            case 62:
                return (rc.readSharedArray(26) & 252) >>> 2;
            case 63:
                return ((rc.readSharedArray(26) & 3) << 4) + ((rc.readSharedArray(27) & 61440) >>> 12);
            case 64:
                return (rc.readSharedArray(27) & 4032) >>> 6;
            case 65:
                return (rc.readSharedArray(27) & 63);
            case 66:
                return (rc.readSharedArray(28) & 64512) >>> 10;
            case 67:
                return (rc.readSharedArray(28) & 1008) >>> 4;
            case 68:
                return ((rc.readSharedArray(28) & 15) << 2) + ((rc.readSharedArray(29) & 49152) >>> 14);
            case 69:
                return (rc.readSharedArray(29) & 16128) >>> 8;
            case 70:
                return (rc.readSharedArray(29) & 252) >>> 2;
            case 71:
                return ((rc.readSharedArray(29) & 3) << 4) + ((rc.readSharedArray(30) & 61440) >>> 12);
            case 72:
                return (rc.readSharedArray(30) & 4032) >>> 6;
            case 73:
                return (rc.readSharedArray(30) & 63);
            case 74:
                return (rc.readSharedArray(31) & 64512) >>> 10;
            case 75:
                return (rc.readSharedArray(31) & 1008) >>> 4;
            case 76:
                return ((rc.readSharedArray(31) & 15) << 2) + ((rc.readSharedArray(32) & 49152) >>> 14);
            case 77:
                return (rc.readSharedArray(32) & 16128) >>> 8;
            case 78:
                return (rc.readSharedArray(32) & 252) >>> 2;
            case 79:
                return ((rc.readSharedArray(32) & 3) << 4) + ((rc.readSharedArray(33) & 61440) >>> 12);
            case 80:
                return (rc.readSharedArray(33) & 4032) >>> 6;
            case 81:
                return (rc.readSharedArray(33) & 63);
            case 82:
                return (rc.readSharedArray(34) & 64512) >>> 10;
            case 83:
                return (rc.readSharedArray(34) & 1008) >>> 4;
            case 84:
                return ((rc.readSharedArray(34) & 15) << 2) + ((rc.readSharedArray(35) & 49152) >>> 14);
            case 85:
                return (rc.readSharedArray(35) & 16128) >>> 8;
            case 86:
                return (rc.readSharedArray(35) & 252) >>> 2;
            case 87:
                return ((rc.readSharedArray(35) & 3) << 4) + ((rc.readSharedArray(36) & 61440) >>> 12);
            case 88:
                return (rc.readSharedArray(36) & 4032) >>> 6;
            case 89:
                return (rc.readSharedArray(36) & 63);
            case 90:
                return (rc.readSharedArray(37) & 64512) >>> 10;
            case 91:
                return (rc.readSharedArray(37) & 1008) >>> 4;
            case 92:
                return ((rc.readSharedArray(37) & 15) << 2) + ((rc.readSharedArray(38) & 49152) >>> 14);
            case 93:
                return (rc.readSharedArray(38) & 16128) >>> 8;
            case 94:
                return (rc.readSharedArray(38) & 252) >>> 2;
            case 95:
                return ((rc.readSharedArray(38) & 3) << 4) + ((rc.readSharedArray(39) & 61440) >>> 12);
            case 96:
                return (rc.readSharedArray(39) & 4032) >>> 6;
            case 97:
                return (rc.readSharedArray(39) & 63);
            case 98:
                return (rc.readSharedArray(40) & 64512) >>> 10;
            case 99:
                return (rc.readSharedArray(40) & 1008) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 61503) | (value << 6));
                break;
            case 1:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65472) | (value));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 1023) | (value << 10));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 64527) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 16383) | ((value & 3) << 14));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 49407) | (value << 8));
                break;
            case 6:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65283) | (value << 2));
                break;
            case 7:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 61503) | (value << 6));
                break;
            case 9:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65472) | (value));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 1023) | (value << 10));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 64527) | (value << 4));
                break;
            case 12:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 16383) | ((value & 3) << 14));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 49407) | (value << 8));
                break;
            case 14:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65283) | (value << 2));
                break;
            case 15:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 4095) | ((value & 15) << 12));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 61503) | (value << 6));
                break;
            case 17:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65472) | (value));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 1023) | (value << 10));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 64527) | (value << 4));
                break;
            case 20:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 16383) | ((value & 3) << 14));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 49407) | (value << 8));
                break;
            case 22:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65283) | (value << 2));
                break;
            case 23:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 4095) | ((value & 15) << 12));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 61503) | (value << 6));
                break;
            case 25:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65472) | (value));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 1023) | (value << 10));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 64527) | (value << 4));
                break;
            case 28:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 16383) | ((value & 3) << 14));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 49407) | (value << 8));
                break;
            case 30:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65283) | (value << 2));
                break;
            case 31:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 4095) | ((value & 15) << 12));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 61503) | (value << 6));
                break;
            case 33:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65472) | (value));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 1023) | (value << 10));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 64527) | (value << 4));
                break;
            case 36:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 16383) | ((value & 3) << 14));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 49407) | (value << 8));
                break;
            case 38:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65283) | (value << 2));
                break;
            case 39:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 4095) | ((value & 15) << 12));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 61503) | (value << 6));
                break;
            case 41:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65472) | (value));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 1023) | (value << 10));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 64527) | (value << 4));
                break;
            case 44:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 16383) | ((value & 3) << 14));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 49407) | (value << 8));
                break;
            case 46:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65283) | (value << 2));
                break;
            case 47:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 4095) | ((value & 15) << 12));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 61503) | (value << 6));
                break;
            case 49:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65472) | (value));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 1023) | (value << 10));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 64527) | (value << 4));
                break;
            case 52:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 16383) | ((value & 3) << 14));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 49407) | (value << 8));
                break;
            case 54:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65283) | (value << 2));
                break;
            case 55:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 4095) | ((value & 15) << 12));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 61503) | (value << 6));
                break;
            case 57:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65472) | (value));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 1023) | (value << 10));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 64527) | (value << 4));
                break;
            case 60:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 16383) | ((value & 3) << 14));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 49407) | (value << 8));
                break;
            case 62:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65283) | (value << 2));
                break;
            case 63:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 4095) | ((value & 15) << 12));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 61503) | (value << 6));
                break;
            case 65:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65472) | (value));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 1023) | (value << 10));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 64527) | (value << 4));
                break;
            case 68:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 16383) | ((value & 3) << 14));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 49407) | (value << 8));
                break;
            case 70:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65283) | (value << 2));
                break;
            case 71:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 4095) | ((value & 15) << 12));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 61503) | (value << 6));
                break;
            case 73:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65472) | (value));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 1023) | (value << 10));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 64527) | (value << 4));
                break;
            case 76:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 16383) | ((value & 3) << 14));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 49407) | (value << 8));
                break;
            case 78:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65283) | (value << 2));
                break;
            case 79:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 4095) | ((value & 15) << 12));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 61503) | (value << 6));
                break;
            case 81:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65472) | (value));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 1023) | (value << 10));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 64527) | (value << 4));
                break;
            case 84:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 16383) | ((value & 3) << 14));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 49407) | (value << 8));
                break;
            case 86:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65283) | (value << 2));
                break;
            case 87:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 4095) | ((value & 15) << 12));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 61503) | (value << 6));
                break;
            case 89:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65472) | (value));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 1023) | (value << 10));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 64527) | (value << 4));
                break;
            case 92:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 16383) | ((value & 3) << 14));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 49407) | (value << 8));
                break;
            case 94:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65283) | (value << 2));
                break;
            case 95:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 4095) | ((value & 15) << 12));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 61503) | (value << 6));
                break;
            case 97:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65472) | (value));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 1023) | (value << 10));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 64527) | (value << 4));
                break;
        }
    }

    public static void writeBPSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 61503) | (value << 6));
                break;
            case 1:
                writeToBufferPool(3, (bufferPool[3] & 65472) | (value));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 1023) | (value << 10));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 64527) | (value << 4));
                break;
            case 4:
                writeToBufferPool(4, (bufferPool[4] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(5, (bufferPool[5] & 16383) | ((value & 3) << 14));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 49407) | (value << 8));
                break;
            case 6:
                writeToBufferPool(5, (bufferPool[5] & 65283) | (value << 2));
                break;
            case 7:
                writeToBufferPool(5, (bufferPool[5] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(6, (bufferPool[6] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 61503) | (value << 6));
                break;
            case 9:
                writeToBufferPool(6, (bufferPool[6] & 65472) | (value));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 1023) | (value << 10));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 64527) | (value << 4));
                break;
            case 12:
                writeToBufferPool(7, (bufferPool[7] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(8, (bufferPool[8] & 16383) | ((value & 3) << 14));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 49407) | (value << 8));
                break;
            case 14:
                writeToBufferPool(8, (bufferPool[8] & 65283) | (value << 2));
                break;
            case 15:
                writeToBufferPool(8, (bufferPool[8] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(9, (bufferPool[9] & 4095) | ((value & 15) << 12));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 61503) | (value << 6));
                break;
            case 17:
                writeToBufferPool(9, (bufferPool[9] & 65472) | (value));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 1023) | (value << 10));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 64527) | (value << 4));
                break;
            case 20:
                writeToBufferPool(10, (bufferPool[10] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(11, (bufferPool[11] & 16383) | ((value & 3) << 14));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 49407) | (value << 8));
                break;
            case 22:
                writeToBufferPool(11, (bufferPool[11] & 65283) | (value << 2));
                break;
            case 23:
                writeToBufferPool(11, (bufferPool[11] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(12, (bufferPool[12] & 4095) | ((value & 15) << 12));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 61503) | (value << 6));
                break;
            case 25:
                writeToBufferPool(12, (bufferPool[12] & 65472) | (value));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 1023) | (value << 10));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 64527) | (value << 4));
                break;
            case 28:
                writeToBufferPool(13, (bufferPool[13] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(14, (bufferPool[14] & 16383) | ((value & 3) << 14));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 49407) | (value << 8));
                break;
            case 30:
                writeToBufferPool(14, (bufferPool[14] & 65283) | (value << 2));
                break;
            case 31:
                writeToBufferPool(14, (bufferPool[14] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(15, (bufferPool[15] & 4095) | ((value & 15) << 12));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 61503) | (value << 6));
                break;
            case 33:
                writeToBufferPool(15, (bufferPool[15] & 65472) | (value));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 1023) | (value << 10));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 64527) | (value << 4));
                break;
            case 36:
                writeToBufferPool(16, (bufferPool[16] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(17, (bufferPool[17] & 16383) | ((value & 3) << 14));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 49407) | (value << 8));
                break;
            case 38:
                writeToBufferPool(17, (bufferPool[17] & 65283) | (value << 2));
                break;
            case 39:
                writeToBufferPool(17, (bufferPool[17] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(18, (bufferPool[18] & 4095) | ((value & 15) << 12));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 61503) | (value << 6));
                break;
            case 41:
                writeToBufferPool(18, (bufferPool[18] & 65472) | (value));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 1023) | (value << 10));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 64527) | (value << 4));
                break;
            case 44:
                writeToBufferPool(19, (bufferPool[19] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(20, (bufferPool[20] & 16383) | ((value & 3) << 14));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 49407) | (value << 8));
                break;
            case 46:
                writeToBufferPool(20, (bufferPool[20] & 65283) | (value << 2));
                break;
            case 47:
                writeToBufferPool(20, (bufferPool[20] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(21, (bufferPool[21] & 4095) | ((value & 15) << 12));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 61503) | (value << 6));
                break;
            case 49:
                writeToBufferPool(21, (bufferPool[21] & 65472) | (value));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 1023) | (value << 10));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 64527) | (value << 4));
                break;
            case 52:
                writeToBufferPool(22, (bufferPool[22] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(23, (bufferPool[23] & 16383) | ((value & 3) << 14));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 49407) | (value << 8));
                break;
            case 54:
                writeToBufferPool(23, (bufferPool[23] & 65283) | (value << 2));
                break;
            case 55:
                writeToBufferPool(23, (bufferPool[23] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(24, (bufferPool[24] & 4095) | ((value & 15) << 12));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 61503) | (value << 6));
                break;
            case 57:
                writeToBufferPool(24, (bufferPool[24] & 65472) | (value));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 1023) | (value << 10));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 64527) | (value << 4));
                break;
            case 60:
                writeToBufferPool(25, (bufferPool[25] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(26, (bufferPool[26] & 16383) | ((value & 3) << 14));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 49407) | (value << 8));
                break;
            case 62:
                writeToBufferPool(26, (bufferPool[26] & 65283) | (value << 2));
                break;
            case 63:
                writeToBufferPool(26, (bufferPool[26] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(27, (bufferPool[27] & 4095) | ((value & 15) << 12));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 61503) | (value << 6));
                break;
            case 65:
                writeToBufferPool(27, (bufferPool[27] & 65472) | (value));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 1023) | (value << 10));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 64527) | (value << 4));
                break;
            case 68:
                writeToBufferPool(28, (bufferPool[28] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(29, (bufferPool[29] & 16383) | ((value & 3) << 14));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 49407) | (value << 8));
                break;
            case 70:
                writeToBufferPool(29, (bufferPool[29] & 65283) | (value << 2));
                break;
            case 71:
                writeToBufferPool(29, (bufferPool[29] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(30, (bufferPool[30] & 4095) | ((value & 15) << 12));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 61503) | (value << 6));
                break;
            case 73:
                writeToBufferPool(30, (bufferPool[30] & 65472) | (value));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 1023) | (value << 10));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 64527) | (value << 4));
                break;
            case 76:
                writeToBufferPool(31, (bufferPool[31] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(32, (bufferPool[32] & 16383) | ((value & 3) << 14));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 49407) | (value << 8));
                break;
            case 78:
                writeToBufferPool(32, (bufferPool[32] & 65283) | (value << 2));
                break;
            case 79:
                writeToBufferPool(32, (bufferPool[32] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(33, (bufferPool[33] & 4095) | ((value & 15) << 12));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 61503) | (value << 6));
                break;
            case 81:
                writeToBufferPool(33, (bufferPool[33] & 65472) | (value));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 1023) | (value << 10));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 64527) | (value << 4));
                break;
            case 84:
                writeToBufferPool(34, (bufferPool[34] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(35, (bufferPool[35] & 16383) | ((value & 3) << 14));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 49407) | (value << 8));
                break;
            case 86:
                writeToBufferPool(35, (bufferPool[35] & 65283) | (value << 2));
                break;
            case 87:
                writeToBufferPool(35, (bufferPool[35] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(36, (bufferPool[36] & 4095) | ((value & 15) << 12));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 61503) | (value << 6));
                break;
            case 89:
                writeToBufferPool(36, (bufferPool[36] & 65472) | (value));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 1023) | (value << 10));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 64527) | (value << 4));
                break;
            case 92:
                writeToBufferPool(37, (bufferPool[37] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(38, (bufferPool[38] & 16383) | ((value & 3) << 14));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 49407) | (value << 8));
                break;
            case 94:
                writeToBufferPool(38, (bufferPool[38] & 65283) | (value << 2));
                break;
            case 95:
                writeToBufferPool(38, (bufferPool[38] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(39, (bufferPool[39] & 4095) | ((value & 15) << 12));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 61503) | (value << 6));
                break;
            case 97:
                writeToBufferPool(39, (bufferPool[39] & 65472) | (value));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 1023) | (value << 10));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 64527) | (value << 4));
                break;
        }
    }

    public static int readCombatSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(40) & 8) >>> 3;
            case 1:
                return (rc.readSharedArray(41) & 2048) >>> 11;
            case 2:
                return (rc.readSharedArray(41) & 8) >>> 3;
            case 3:
                return (rc.readSharedArray(42) & 2048) >>> 11;
            default:
                return -1;
        }
    }

    public static void writeCombatSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65527) | (value << 3));
                break;
            case 1:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63487) | (value << 11));
                break;
            case 2:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65527) | (value << 3));
                break;
            case 3:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 63487) | (value << 11));
                break;
        }
    }

    public static void writeBPCombatSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(40, (bufferPool[40] & 65527) | (value << 3));
                break;
            case 1:
                writeToBufferPool(41, (bufferPool[41] & 63487) | (value << 11));
                break;
            case 2:
                writeToBufferPool(41, (bufferPool[41] & 65527) | (value << 3));
                break;
            case 3:
                writeToBufferPool(42, (bufferPool[42] & 63487) | (value << 11));
                break;
        }
    }

    public static int readCombatSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(40) & 7) << 4) + ((rc.readSharedArray(41) & 61440) >>> 12);
            case 1:
                return (rc.readSharedArray(41) & 2032) >>> 4;
            case 2:
                return ((rc.readSharedArray(41) & 7) << 4) + ((rc.readSharedArray(42) & 61440) >>> 12);
            case 3:
                return (rc.readSharedArray(42) & 2032) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 4095) | ((value & 15) << 12));
                break;
            case 1:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63503) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 4095) | ((value & 15) << 12));
                break;
            case 3:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 63503) | (value << 4));
                break;
        }
    }

    public static void writeBPCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(40, (bufferPool[40] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(41, (bufferPool[41] & 4095) | ((value & 15) << 12));
                break;
            case 1:
                writeToBufferPool(41, (bufferPool[41] & 63503) | (value << 4));
                break;
            case 2:
                writeToBufferPool(41, (bufferPool[41] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(42, (bufferPool[42] & 4095) | ((value & 15) << 12));
                break;
            case 3:
                writeToBufferPool(42, (bufferPool[42] & 63503) | (value << 4));
                break;
        }
    }

    public static int readCombatSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(40) & 15) << 4) + ((rc.readSharedArray(41) & 61440) >>> 12);
            case 1:
                return (rc.readSharedArray(41) & 4080) >>> 4;
            case 2:
                return ((rc.readSharedArray(41) & 15) << 4) + ((rc.readSharedArray(42) & 61440) >>> 12);
            case 3:
                return (rc.readSharedArray(42) & 4080) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 4095) | ((value & 15) << 12));
                break;
            case 1:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 61455) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 4095) | ((value & 15) << 12));
                break;
            case 3:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 61455) | (value << 4));
                break;
        }
    }

    public static void writeBPCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(40, (bufferPool[40] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(41, (bufferPool[41] & 4095) | ((value & 15) << 12));
                break;
            case 1:
                writeToBufferPool(41, (bufferPool[41] & 61455) | (value << 4));
                break;
            case 2:
                writeToBufferPool(41, (bufferPool[41] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(42, (bufferPool[42] & 4095) | ((value & 15) << 12));
                break;
            case 3:
                writeToBufferPool(42, (bufferPool[42] & 61455) | (value << 4));
                break;
        }
    }

    public static int readExploreSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(42) & 8) >>> 3;
            case 1:
                return (rc.readSharedArray(43) & 2048) >>> 11;
            case 2:
                return (rc.readSharedArray(43) & 8) >>> 3;
            case 3:
                return (rc.readSharedArray(44) & 2048) >>> 11;
            case 4:
                return (rc.readSharedArray(44) & 8) >>> 3;
            case 5:
                return (rc.readSharedArray(45) & 2048) >>> 11;
            case 6:
                return (rc.readSharedArray(45) & 8) >>> 3;
            case 7:
                return (rc.readSharedArray(46) & 2048) >>> 11;
            case 8:
                return (rc.readSharedArray(46) & 8) >>> 3;
            case 9:
                return (rc.readSharedArray(47) & 2048) >>> 11;
            case 10:
                return (rc.readSharedArray(47) & 8) >>> 3;
            case 11:
                return (rc.readSharedArray(48) & 2048) >>> 11;
            case 12:
                return (rc.readSharedArray(48) & 8) >>> 3;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65527) | (value << 3));
                break;
            case 1:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63487) | (value << 11));
                break;
            case 2:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65527) | (value << 3));
                break;
            case 3:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 63487) | (value << 11));
                break;
            case 4:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65527) | (value << 3));
                break;
            case 5:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 63487) | (value << 11));
                break;
            case 6:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65527) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63487) | (value << 11));
                break;
            case 8:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65527) | (value << 3));
                break;
            case 9:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 63487) | (value << 11));
                break;
            case 10:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65527) | (value << 3));
                break;
            case 11:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 63487) | (value << 11));
                break;
            case 12:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65527) | (value << 3));
                break;
        }
    }

    public static void writeBPExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(42, (bufferPool[42] & 65527) | (value << 3));
                break;
            case 1:
                writeToBufferPool(43, (bufferPool[43] & 63487) | (value << 11));
                break;
            case 2:
                writeToBufferPool(43, (bufferPool[43] & 65527) | (value << 3));
                break;
            case 3:
                writeToBufferPool(44, (bufferPool[44] & 63487) | (value << 11));
                break;
            case 4:
                writeToBufferPool(44, (bufferPool[44] & 65527) | (value << 3));
                break;
            case 5:
                writeToBufferPool(45, (bufferPool[45] & 63487) | (value << 11));
                break;
            case 6:
                writeToBufferPool(45, (bufferPool[45] & 65527) | (value << 3));
                break;
            case 7:
                writeToBufferPool(46, (bufferPool[46] & 63487) | (value << 11));
                break;
            case 8:
                writeToBufferPool(46, (bufferPool[46] & 65527) | (value << 3));
                break;
            case 9:
                writeToBufferPool(47, (bufferPool[47] & 63487) | (value << 11));
                break;
            case 10:
                writeToBufferPool(47, (bufferPool[47] & 65527) | (value << 3));
                break;
            case 11:
                writeToBufferPool(48, (bufferPool[48] & 63487) | (value << 11));
                break;
            case 12:
                writeToBufferPool(48, (bufferPool[48] & 65527) | (value << 3));
                break;
        }
    }

    public static int readExploreSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(42) & 7) << 4) + ((rc.readSharedArray(43) & 61440) >>> 12);
            case 1:
                return (rc.readSharedArray(43) & 2032) >>> 4;
            case 2:
                return ((rc.readSharedArray(43) & 7) << 4) + ((rc.readSharedArray(44) & 61440) >>> 12);
            case 3:
                return (rc.readSharedArray(44) & 2032) >>> 4;
            case 4:
                return ((rc.readSharedArray(44) & 7) << 4) + ((rc.readSharedArray(45) & 61440) >>> 12);
            case 5:
                return (rc.readSharedArray(45) & 2032) >>> 4;
            case 6:
                return ((rc.readSharedArray(45) & 7) << 4) + ((rc.readSharedArray(46) & 61440) >>> 12);
            case 7:
                return (rc.readSharedArray(46) & 2032) >>> 4;
            case 8:
                return ((rc.readSharedArray(46) & 7) << 4) + ((rc.readSharedArray(47) & 61440) >>> 12);
            case 9:
                return (rc.readSharedArray(47) & 2032) >>> 4;
            case 10:
                return ((rc.readSharedArray(47) & 7) << 4) + ((rc.readSharedArray(48) & 61440) >>> 12);
            case 11:
                return (rc.readSharedArray(48) & 2032) >>> 4;
            case 12:
                return ((rc.readSharedArray(48) & 7) << 4) + ((rc.readSharedArray(49) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 4095) | ((value & 15) << 12));
                break;
            case 1:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63503) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 4095) | ((value & 15) << 12));
                break;
            case 3:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 63503) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 4095) | ((value & 15) << 12));
                break;
            case 5:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 63503) | (value << 4));
                break;
            case 6:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 4095) | ((value & 15) << 12));
                break;
            case 7:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63503) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 4095) | ((value & 15) << 12));
                break;
            case 9:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 63503) | (value << 4));
                break;
            case 10:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 4095) | ((value & 15) << 12));
                break;
            case 11:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 63503) | (value << 4));
                break;
            case 12:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(42, (bufferPool[42] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(43, (bufferPool[43] & 4095) | ((value & 15) << 12));
                break;
            case 1:
                writeToBufferPool(43, (bufferPool[43] & 63503) | (value << 4));
                break;
            case 2:
                writeToBufferPool(43, (bufferPool[43] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(44, (bufferPool[44] & 4095) | ((value & 15) << 12));
                break;
            case 3:
                writeToBufferPool(44, (bufferPool[44] & 63503) | (value << 4));
                break;
            case 4:
                writeToBufferPool(44, (bufferPool[44] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(45, (bufferPool[45] & 4095) | ((value & 15) << 12));
                break;
            case 5:
                writeToBufferPool(45, (bufferPool[45] & 63503) | (value << 4));
                break;
            case 6:
                writeToBufferPool(45, (bufferPool[45] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(46, (bufferPool[46] & 4095) | ((value & 15) << 12));
                break;
            case 7:
                writeToBufferPool(46, (bufferPool[46] & 63503) | (value << 4));
                break;
            case 8:
                writeToBufferPool(46, (bufferPool[46] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(47, (bufferPool[47] & 4095) | ((value & 15) << 12));
                break;
            case 9:
                writeToBufferPool(47, (bufferPool[47] & 63503) | (value << 4));
                break;
            case 10:
                writeToBufferPool(47, (bufferPool[47] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(48, (bufferPool[48] & 4095) | ((value & 15) << 12));
                break;
            case 11:
                writeToBufferPool(48, (bufferPool[48] & 63503) | (value << 4));
                break;
            case 12:
                writeToBufferPool(48, (bufferPool[48] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(49, (bufferPool[49] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readExploreSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(42) & 15) << 4) + ((rc.readSharedArray(43) & 61440) >>> 12);
            case 1:
                return (rc.readSharedArray(43) & 4080) >>> 4;
            case 2:
                return ((rc.readSharedArray(43) & 15) << 4) + ((rc.readSharedArray(44) & 61440) >>> 12);
            case 3:
                return (rc.readSharedArray(44) & 4080) >>> 4;
            case 4:
                return ((rc.readSharedArray(44) & 15) << 4) + ((rc.readSharedArray(45) & 61440) >>> 12);
            case 5:
                return (rc.readSharedArray(45) & 4080) >>> 4;
            case 6:
                return ((rc.readSharedArray(45) & 15) << 4) + ((rc.readSharedArray(46) & 61440) >>> 12);
            case 7:
                return (rc.readSharedArray(46) & 4080) >>> 4;
            case 8:
                return ((rc.readSharedArray(46) & 15) << 4) + ((rc.readSharedArray(47) & 61440) >>> 12);
            case 9:
                return (rc.readSharedArray(47) & 4080) >>> 4;
            case 10:
                return ((rc.readSharedArray(47) & 15) << 4) + ((rc.readSharedArray(48) & 61440) >>> 12);
            case 11:
                return (rc.readSharedArray(48) & 4080) >>> 4;
            case 12:
                return ((rc.readSharedArray(48) & 15) << 4) + ((rc.readSharedArray(49) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 4095) | ((value & 15) << 12));
                break;
            case 1:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 61455) | (value << 4));
                break;
            case 2:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 4095) | ((value & 15) << 12));
                break;
            case 3:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 61455) | (value << 4));
                break;
            case 4:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 4095) | ((value & 15) << 12));
                break;
            case 5:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 61455) | (value << 4));
                break;
            case 6:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 4095) | ((value & 15) << 12));
                break;
            case 7:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 61455) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 4095) | ((value & 15) << 12));
                break;
            case 9:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 61455) | (value << 4));
                break;
            case 10:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 4095) | ((value & 15) << 12));
                break;
            case 11:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 61455) | (value << 4));
                break;
            case 12:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(42, (bufferPool[42] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(43, (bufferPool[43] & 4095) | ((value & 15) << 12));
                break;
            case 1:
                writeToBufferPool(43, (bufferPool[43] & 61455) | (value << 4));
                break;
            case 2:
                writeToBufferPool(43, (bufferPool[43] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(44, (bufferPool[44] & 4095) | ((value & 15) << 12));
                break;
            case 3:
                writeToBufferPool(44, (bufferPool[44] & 61455) | (value << 4));
                break;
            case 4:
                writeToBufferPool(44, (bufferPool[44] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(45, (bufferPool[45] & 4095) | ((value & 15) << 12));
                break;
            case 5:
                writeToBufferPool(45, (bufferPool[45] & 61455) | (value << 4));
                break;
            case 6:
                writeToBufferPool(45, (bufferPool[45] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(46, (bufferPool[46] & 4095) | ((value & 15) << 12));
                break;
            case 7:
                writeToBufferPool(46, (bufferPool[46] & 61455) | (value << 4));
                break;
            case 8:
                writeToBufferPool(46, (bufferPool[46] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(47, (bufferPool[47] & 4095) | ((value & 15) << 12));
                break;
            case 9:
                writeToBufferPool(47, (bufferPool[47] & 61455) | (value << 4));
                break;
            case 10:
                writeToBufferPool(47, (bufferPool[47] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(48, (bufferPool[48] & 4095) | ((value & 15) << 12));
                break;
            case 11:
                writeToBufferPool(48, (bufferPool[48] & 61455) | (value << 4));
                break;
            case 12:
                writeToBufferPool(48, (bufferPool[48] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(49, (bufferPool[49] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readMineSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(49) & 4064) >>> 5;
            case 1:
                return ((rc.readSharedArray(49) & 31) << 2) + ((rc.readSharedArray(50) & 49152) >>> 14);
            case 2:
                return (rc.readSharedArray(50) & 16256) >>> 7;
            case 3:
                return (rc.readSharedArray(50) & 127);
            case 4:
                return (rc.readSharedArray(51) & 65024) >>> 9;
            case 5:
                return (rc.readSharedArray(51) & 508) >>> 2;
            case 6:
                return ((rc.readSharedArray(51) & 3) << 5) + ((rc.readSharedArray(52) & 63488) >>> 11);
            case 7:
                return (rc.readSharedArray(52) & 2032) >>> 4;
            case 8:
                return ((rc.readSharedArray(52) & 15) << 3) + ((rc.readSharedArray(53) & 57344) >>> 13);
            case 9:
                return (rc.readSharedArray(53) & 8128) >>> 6;
            case 10:
                return ((rc.readSharedArray(53) & 63) << 1) + ((rc.readSharedArray(54) & 32768) >>> 15);
            case 11:
                return (rc.readSharedArray(54) & 32512) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 61471) | (value << 5));
                break;
            case 1:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 16383) | ((value & 3) << 14));
                break;
            case 2:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 49279) | (value << 7));
                break;
            case 3:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65408) | (value));
                break;
            case 4:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 511) | (value << 9));
                break;
            case 5:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65027) | (value << 2));
                break;
            case 6:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 2047) | ((value & 31) << 11));
                break;
            case 7:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 63503) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 8191) | ((value & 7) << 13));
                break;
            case 9:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 57407) | (value << 6));
                break;
            case 10:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 32767) | ((value & 1) << 15));
                break;
            case 11:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 33023) | (value << 8));
                break;
        }
    }

    public static void writeBPMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(49, (bufferPool[49] & 61471) | (value << 5));
                break;
            case 1:
                writeToBufferPool(49, (bufferPool[49] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(50, (bufferPool[50] & 16383) | ((value & 3) << 14));
                break;
            case 2:
                writeToBufferPool(50, (bufferPool[50] & 49279) | (value << 7));
                break;
            case 3:
                writeToBufferPool(50, (bufferPool[50] & 65408) | (value));
                break;
            case 4:
                writeToBufferPool(51, (bufferPool[51] & 511) | (value << 9));
                break;
            case 5:
                writeToBufferPool(51, (bufferPool[51] & 65027) | (value << 2));
                break;
            case 6:
                writeToBufferPool(51, (bufferPool[51] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(52, (bufferPool[52] & 2047) | ((value & 31) << 11));
                break;
            case 7:
                writeToBufferPool(52, (bufferPool[52] & 63503) | (value << 4));
                break;
            case 8:
                writeToBufferPool(52, (bufferPool[52] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(53, (bufferPool[53] & 8191) | ((value & 7) << 13));
                break;
            case 9:
                writeToBufferPool(53, (bufferPool[53] & 57407) | (value << 6));
                break;
            case 10:
                writeToBufferPool(53, (bufferPool[53] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(54, (bufferPool[54] & 32767) | ((value & 1) << 15));
                break;
            case 11:
                writeToBufferPool(54, (bufferPool[54] & 33023) | (value << 8));
                break;
        }
    }

    public static int readMineSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(49) & 4064) >>> 5;
            case 1:
                return ((rc.readSharedArray(49) & 31) << 2) + ((rc.readSharedArray(50) & 49152) >>> 14);
            case 2:
                return (rc.readSharedArray(50) & 16256) >>> 7;
            case 3:
                return (rc.readSharedArray(50) & 127);
            case 4:
                return (rc.readSharedArray(51) & 65024) >>> 9;
            case 5:
                return (rc.readSharedArray(51) & 508) >>> 2;
            case 6:
                return ((rc.readSharedArray(51) & 3) << 5) + ((rc.readSharedArray(52) & 63488) >>> 11);
            case 7:
                return (rc.readSharedArray(52) & 2032) >>> 4;
            case 8:
                return ((rc.readSharedArray(52) & 15) << 3) + ((rc.readSharedArray(53) & 57344) >>> 13);
            case 9:
                return (rc.readSharedArray(53) & 8128) >>> 6;
            case 10:
                return ((rc.readSharedArray(53) & 63) << 1) + ((rc.readSharedArray(54) & 32768) >>> 15);
            case 11:
                return (rc.readSharedArray(54) & 32512) >>> 8;
            default:
                return -1;
        }
    }

    public static void writeMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 61471) | (value << 5));
                break;
            case 1:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 16383) | ((value & 3) << 14));
                break;
            case 2:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 49279) | (value << 7));
                break;
            case 3:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65408) | (value));
                break;
            case 4:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 511) | (value << 9));
                break;
            case 5:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65027) | (value << 2));
                break;
            case 6:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65532) | ((value & 96) >>> 5));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 2047) | ((value & 31) << 11));
                break;
            case 7:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 63503) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 8191) | ((value & 7) << 13));
                break;
            case 9:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 57407) | (value << 6));
                break;
            case 10:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 32767) | ((value & 1) << 15));
                break;
            case 11:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 33023) | (value << 8));
                break;
        }
    }

    public static void writeBPMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(49, (bufferPool[49] & 61471) | (value << 5));
                break;
            case 1:
                writeToBufferPool(49, (bufferPool[49] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(50, (bufferPool[50] & 16383) | ((value & 3) << 14));
                break;
            case 2:
                writeToBufferPool(50, (bufferPool[50] & 49279) | (value << 7));
                break;
            case 3:
                writeToBufferPool(50, (bufferPool[50] & 65408) | (value));
                break;
            case 4:
                writeToBufferPool(51, (bufferPool[51] & 511) | (value << 9));
                break;
            case 5:
                writeToBufferPool(51, (bufferPool[51] & 65027) | (value << 2));
                break;
            case 6:
                writeToBufferPool(51, (bufferPool[51] & 65532) | ((value & 96) >>> 5));
                writeToBufferPool(52, (bufferPool[52] & 2047) | ((value & 31) << 11));
                break;
            case 7:
                writeToBufferPool(52, (bufferPool[52] & 63503) | (value << 4));
                break;
            case 8:
                writeToBufferPool(52, (bufferPool[52] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(53, (bufferPool[53] & 8191) | ((value & 7) << 13));
                break;
            case 9:
                writeToBufferPool(53, (bufferPool[53] & 57407) | (value << 6));
                break;
            case 10:
                writeToBufferPool(53, (bufferPool[53] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(54, (bufferPool[54] & 32767) | ((value & 1) << 15));
                break;
            case 11:
                writeToBufferPool(54, (bufferPool[54] & 33023) | (value << 8));
                break;
        }
    }

    public static int readSymmetryVertical() throws GameActionException {
        return (rc.readSharedArray(54) & 128) >>> 7;
    }

    public static void writeSymmetryVertical(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65407) | (value << 7));
    }

    public static void writeBPSymmetryVertical(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65407) | (value << 7));
    }

    public static int readSymmetryHorizontal() throws GameActionException {
        return (rc.readSharedArray(54) & 64) >>> 6;
    }

    public static void writeSymmetryHorizontal(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65471) | (value << 6));
    }

    public static void writeBPSymmetryHorizontal(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65471) | (value << 6));
    }

    public static int readSymmetryRotational() throws GameActionException {
        return (rc.readSharedArray(54) & 32) >>> 5;
    }

    public static void writeSymmetryRotational(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65503) | (value << 5));
    }

    public static void writeBPSymmetryRotational(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65503) | (value << 5));
    }

    public static int readSymmetryAll() throws GameActionException {
        return (rc.readSharedArray(54) & 224) >>> 5;
    }

    public static void writeSymmetryAll(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65311) | (value << 5));
    }

    public static void writeBPSymmetryAll(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65311) | (value << 5));
    }

    public static int readNumHqsDoNotCall() throws GameActionException {
        return (rc.readSharedArray(54) & 24) >>> 3;
    }

    public static void writeNumHqsDoNotCall(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65511) | (value << 3));
    }

    public static void writeBPNumHqsDoNotCall(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65511) | (value << 3));
    }

    public static int readNumHqsAll() throws GameActionException {
        return (rc.readSharedArray(54) & 24) >>> 3;
    }

    public static void writeNumHqsAll(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65511) | (value << 3));
    }

    public static void writeBPNumHqsAll(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65511) | (value << 3));
    }

    public static int readElixirSectorConverted() throws GameActionException {
        return (rc.readSharedArray(54) & 4) >>> 2;
    }

    public static void writeElixirSectorConverted(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65531) | (value << 2));
    }

    public static void writeBPElixirSectorConverted(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65531) | (value << 2));
    }

    public static int readElixirSectorIndex() throws GameActionException {
        return ((rc.readSharedArray(54) & 3) << 5) + ((rc.readSharedArray(55) & 63488) >>> 11);
    }

    public static void writeElixirSectorIndex(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65532) | ((value & 96) >>> 5));
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 2047) | ((value & 31) << 11));
    }

    public static void writeBPElixirSectorIndex(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65532) | ((value & 96) >>> 5));
        writeToBufferPool(55, (bufferPool[55] & 2047) | ((value & 31) << 11));
    }

    public static int readElixirSectorAll() throws GameActionException {
        return ((rc.readSharedArray(54) & 7) << 5) + ((rc.readSharedArray(55) & 63488) >>> 11);
    }

    public static void writeElixirSectorAll(int value) throws GameActionException {
        rc.writeSharedArray(54, (rc.readSharedArray(54) & 65528) | ((value & 224) >>> 5));
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 2047) | ((value & 31) << 11));
    }

    public static void writeBPElixirSectorAll(int value) throws GameActionException {
        writeToBufferPool(54, (bufferPool[54] & 65528) | ((value & 224) >>> 5));
        writeToBufferPool(55, (bufferPool[55] & 2047) | ((value & 31) << 11));
    }

    // BUFFER POOL READ AND WRITE METHODS

}
