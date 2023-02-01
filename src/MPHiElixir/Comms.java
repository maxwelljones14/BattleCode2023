package MPHiElixir;

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
        rc.writeSharedArray(41, 2039);
        rc.writeSharedArray(42, 63479);
        rc.writeSharedArray(43, 63479);
        rc.writeSharedArray(44, 63479);
        rc.writeSharedArray(45, 63479);
        rc.writeSharedArray(46, 63479);
        rc.writeSharedArray(47, 63479);
        rc.writeSharedArray(48, 63479);
        rc.writeSharedArray(49, 63487);
        rc.writeSharedArray(50, 65535);
        rc.writeSharedArray(51, 65535);
        rc.writeSharedArray(52, 65535);
        rc.writeSharedArray(53, 65535);
        rc.writeSharedArray(54, 65535);
        rc.writeSharedArray(55, 1016);
    }


    public static void resetAllSectorControlStatus() throws GameActionException {
        rc.writeSharedArray(3, rc.readSharedArray(3) & 65534);
        rc.writeSharedArray(4, rc.readSharedArray(4) & 14563);
        rc.writeSharedArray(5, rc.readSharedArray(5) & 36408);
        rc.writeSharedArray(6, rc.readSharedArray(6) & 58254);
        rc.writeSharedArray(7, rc.readSharedArray(7) & 14563);
        rc.writeSharedArray(8, rc.readSharedArray(8) & 36408);
        rc.writeSharedArray(9, rc.readSharedArray(9) & 58254);
        rc.writeSharedArray(10, rc.readSharedArray(10) & 14563);
        rc.writeSharedArray(11, rc.readSharedArray(11) & 36408);
        rc.writeSharedArray(12, rc.readSharedArray(12) & 58254);
        rc.writeSharedArray(13, rc.readSharedArray(13) & 14563);
        rc.writeSharedArray(14, rc.readSharedArray(14) & 36408);
        rc.writeSharedArray(15, rc.readSharedArray(15) & 58254);
        rc.writeSharedArray(16, rc.readSharedArray(16) & 14563);
        rc.writeSharedArray(17, rc.readSharedArray(17) & 36408);
        rc.writeSharedArray(18, rc.readSharedArray(18) & 58254);
        rc.writeSharedArray(19, rc.readSharedArray(19) & 14563);
        rc.writeSharedArray(20, rc.readSharedArray(20) & 36408);
        rc.writeSharedArray(21, rc.readSharedArray(21) & 58254);
        rc.writeSharedArray(22, rc.readSharedArray(22) & 14563);
        rc.writeSharedArray(23, rc.readSharedArray(23) & 36408);
        rc.writeSharedArray(24, rc.readSharedArray(24) & 58254);
        rc.writeSharedArray(25, rc.readSharedArray(25) & 14563);
        rc.writeSharedArray(26, rc.readSharedArray(26) & 36408);
        rc.writeSharedArray(27, rc.readSharedArray(27) & 58254);
        rc.writeSharedArray(28, rc.readSharedArray(28) & 14563);
        rc.writeSharedArray(29, rc.readSharedArray(29) & 36408);
        rc.writeSharedArray(30, rc.readSharedArray(30) & 58254);
        rc.writeSharedArray(31, rc.readSharedArray(31) & 14563);
        rc.writeSharedArray(32, rc.readSharedArray(32) & 36408);
        rc.writeSharedArray(33, rc.readSharedArray(33) & 58254);
        rc.writeSharedArray(34, rc.readSharedArray(34) & 14563);
        rc.writeSharedArray(35, rc.readSharedArray(35) & 36408);
        rc.writeSharedArray(36, rc.readSharedArray(36) & 58254);
        rc.writeSharedArray(37, rc.readSharedArray(37) & 14563);
        rc.writeSharedArray(38, rc.readSharedArray(38) & 36408);
        rc.writeSharedArray(39, rc.readSharedArray(39) & 58254);
        rc.writeSharedArray(40, rc.readSharedArray(40) & 14563);
        rc.writeSharedArray(41, rc.readSharedArray(41) & 36863);
    }

    public static int readOurHqNumAmps(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 49152) >>> 14;
            case 1:
                return ((rc.readSharedArray(0) & 1) << 1) + ((rc.readSharedArray(1) & 32768) >>> 15);
            case 2:
                return (rc.readSharedArray(1) & 3);
            case 3:
                return (rc.readSharedArray(2) & 6) >>> 1;
            default:
                return -1;
        }
    }

    public static void writeOurHqNumAmps(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 16383) | (value << 14));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65534) | ((value & 2) >>> 1));
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 32767) | ((value & 1) << 15));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65532) | (value));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65529) | (value << 1));
                break;
        }
    }

    public static void writeBPOurHqNumAmps(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 16383) | (value << 14));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65534) | ((value & 2) >>> 1));
                writeToBufferPool(1, (bufferPool[1] & 32767) | ((value & 1) << 15));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65532) | (value));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65529) | (value << 1));
                break;
        }
    }

    public static int readOurHqFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 8192) >>> 13;
            case 1:
                return (rc.readSharedArray(1) & 16384) >>> 14;
            case 2:
                return (rc.readSharedArray(2) & 32768) >>> 15;
            case 3:
                return (rc.readSharedArray(2) & 1);
            default:
                return -1;
        }
    }

    public static void writeOurHqFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 57343) | (value << 13));
                break;
            case 1:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 49151) | (value << 14));
                break;
            case 2:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 32767) | (value << 15));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65534) | (value));
                break;
        }
    }

    public static void writeBPOurHqFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 57343) | (value << 13));
                break;
            case 1:
                writeToBufferPool(1, (bufferPool[1] & 49151) | (value << 14));
                break;
            case 2:
                writeToBufferPool(2, (bufferPool[2] & 32767) | (value << 15));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65534) | (value));
                break;
        }
    }

    public static int readOurHqXCoord(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 8064) >>> 7;
            case 1:
                return (rc.readSharedArray(1) & 16128) >>> 8;
            case 2:
                return (rc.readSharedArray(2) & 32256) >>> 9;
            case 3:
                return (rc.readSharedArray(3) & 64512) >>> 10;
            default:
                return -1;
        }
    }

    public static void writeOurHqXCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 57471) | (value << 7));
                break;
            case 1:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 49407) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 33279) | (value << 9));
                break;
            case 3:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 1023) | (value << 10));
                break;
        }
    }

    public static void writeBPOurHqXCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 57471) | (value << 7));
                break;
            case 1:
                writeToBufferPool(1, (bufferPool[1] & 49407) | (value << 8));
                break;
            case 2:
                writeToBufferPool(2, (bufferPool[2] & 33279) | (value << 9));
                break;
            case 3:
                writeToBufferPool(3, (bufferPool[3] & 1023) | (value << 10));
                break;
        }
    }

    public static int readOurHqYCoord(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 126) >>> 1;
            case 1:
                return (rc.readSharedArray(1) & 252) >>> 2;
            case 2:
                return (rc.readSharedArray(2) & 504) >>> 3;
            case 3:
                return (rc.readSharedArray(3) & 1008) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeOurHqYCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65409) | (value << 1));
                break;
            case 1:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65283) | (value << 2));
                break;
            case 2:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65031) | (value << 3));
                break;
            case 3:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 64527) | (value << 4));
                break;
        }
    }

    public static void writeBPOurHqYCoord(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 65409) | (value << 1));
                break;
            case 1:
                writeToBufferPool(1, (bufferPool[1] & 65283) | (value << 2));
                break;
            case 2:
                writeToBufferPool(2, (bufferPool[2] & 65031) | (value << 3));
                break;
            case 3:
                writeToBufferPool(3, (bufferPool[3] & 64527) | (value << 4));
                break;
        }
    }

    public static int readOurHqAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(0) & 65534) >>> 1;
            case 1:
                return ((rc.readSharedArray(0) & 1) << 14) + ((rc.readSharedArray(1) & 65532) >>> 2);
            case 2:
                return ((rc.readSharedArray(1) & 3) << 13) + ((rc.readSharedArray(2) & 65528) >>> 3);
            case 3:
                return ((rc.readSharedArray(2) & 7) << 12) + ((rc.readSharedArray(3) & 65520) >>> 4);
            default:
                return -1;
        }
    }

    public static void writeOurHqAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 1) | (value << 1));
                break;
            case 1:
                rc.writeSharedArray(0, (rc.readSharedArray(0) & 65534) | ((value & 16384) >>> 14));
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 3) | ((value & 16383) << 2));
                break;
            case 2:
                rc.writeSharedArray(1, (rc.readSharedArray(1) & 65532) | ((value & 24576) >>> 13));
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 7) | ((value & 8191) << 3));
                break;
            case 3:
                rc.writeSharedArray(2, (rc.readSharedArray(2) & 65528) | ((value & 28672) >>> 12));
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 15) | ((value & 4095) << 4));
                break;
        }
    }

    public static void writeBPOurHqAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(0, (bufferPool[0] & 1) | (value << 1));
                break;
            case 1:
                writeToBufferPool(0, (bufferPool[0] & 65534) | ((value & 16384) >>> 14));
                writeToBufferPool(1, (bufferPool[1] & 3) | ((value & 16383) << 2));
                break;
            case 2:
                writeToBufferPool(1, (bufferPool[1] & 65532) | ((value & 24576) >>> 13));
                writeToBufferPool(2, (bufferPool[2] & 7) | ((value & 8191) << 3));
                break;
            case 3:
                writeToBufferPool(2, (bufferPool[2] & 65528) | ((value & 28672) >>> 12));
                writeToBufferPool(3, (bufferPool[3] & 15) | ((value & 4095) << 4));
                break;
        }
    }

    public static int readSectorIslands(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 8) >>> 3;
            case 1:
                return (rc.readSharedArray(4) & 8192) >>> 13;
            case 2:
                return (rc.readSharedArray(4) & 128) >>> 7;
            case 3:
                return (rc.readSharedArray(4) & 2) >>> 1;
            case 4:
                return (rc.readSharedArray(5) & 2048) >>> 11;
            case 5:
                return (rc.readSharedArray(5) & 32) >>> 5;
            case 6:
                return (rc.readSharedArray(6) & 32768) >>> 15;
            case 7:
                return (rc.readSharedArray(6) & 512) >>> 9;
            case 8:
                return (rc.readSharedArray(6) & 8) >>> 3;
            case 9:
                return (rc.readSharedArray(7) & 8192) >>> 13;
            case 10:
                return (rc.readSharedArray(7) & 128) >>> 7;
            case 11:
                return (rc.readSharedArray(7) & 2) >>> 1;
            case 12:
                return (rc.readSharedArray(8) & 2048) >>> 11;
            case 13:
                return (rc.readSharedArray(8) & 32) >>> 5;
            case 14:
                return (rc.readSharedArray(9) & 32768) >>> 15;
            case 15:
                return (rc.readSharedArray(9) & 512) >>> 9;
            case 16:
                return (rc.readSharedArray(9) & 8) >>> 3;
            case 17:
                return (rc.readSharedArray(10) & 8192) >>> 13;
            case 18:
                return (rc.readSharedArray(10) & 128) >>> 7;
            case 19:
                return (rc.readSharedArray(10) & 2) >>> 1;
            case 20:
                return (rc.readSharedArray(11) & 2048) >>> 11;
            case 21:
                return (rc.readSharedArray(11) & 32) >>> 5;
            case 22:
                return (rc.readSharedArray(12) & 32768) >>> 15;
            case 23:
                return (rc.readSharedArray(12) & 512) >>> 9;
            case 24:
                return (rc.readSharedArray(12) & 8) >>> 3;
            case 25:
                return (rc.readSharedArray(13) & 8192) >>> 13;
            case 26:
                return (rc.readSharedArray(13) & 128) >>> 7;
            case 27:
                return (rc.readSharedArray(13) & 2) >>> 1;
            case 28:
                return (rc.readSharedArray(14) & 2048) >>> 11;
            case 29:
                return (rc.readSharedArray(14) & 32) >>> 5;
            case 30:
                return (rc.readSharedArray(15) & 32768) >>> 15;
            case 31:
                return (rc.readSharedArray(15) & 512) >>> 9;
            case 32:
                return (rc.readSharedArray(15) & 8) >>> 3;
            case 33:
                return (rc.readSharedArray(16) & 8192) >>> 13;
            case 34:
                return (rc.readSharedArray(16) & 128) >>> 7;
            case 35:
                return (rc.readSharedArray(16) & 2) >>> 1;
            case 36:
                return (rc.readSharedArray(17) & 2048) >>> 11;
            case 37:
                return (rc.readSharedArray(17) & 32) >>> 5;
            case 38:
                return (rc.readSharedArray(18) & 32768) >>> 15;
            case 39:
                return (rc.readSharedArray(18) & 512) >>> 9;
            case 40:
                return (rc.readSharedArray(18) & 8) >>> 3;
            case 41:
                return (rc.readSharedArray(19) & 8192) >>> 13;
            case 42:
                return (rc.readSharedArray(19) & 128) >>> 7;
            case 43:
                return (rc.readSharedArray(19) & 2) >>> 1;
            case 44:
                return (rc.readSharedArray(20) & 2048) >>> 11;
            case 45:
                return (rc.readSharedArray(20) & 32) >>> 5;
            case 46:
                return (rc.readSharedArray(21) & 32768) >>> 15;
            case 47:
                return (rc.readSharedArray(21) & 512) >>> 9;
            case 48:
                return (rc.readSharedArray(21) & 8) >>> 3;
            case 49:
                return (rc.readSharedArray(22) & 8192) >>> 13;
            case 50:
                return (rc.readSharedArray(22) & 128) >>> 7;
            case 51:
                return (rc.readSharedArray(22) & 2) >>> 1;
            case 52:
                return (rc.readSharedArray(23) & 2048) >>> 11;
            case 53:
                return (rc.readSharedArray(23) & 32) >>> 5;
            case 54:
                return (rc.readSharedArray(24) & 32768) >>> 15;
            case 55:
                return (rc.readSharedArray(24) & 512) >>> 9;
            case 56:
                return (rc.readSharedArray(24) & 8) >>> 3;
            case 57:
                return (rc.readSharedArray(25) & 8192) >>> 13;
            case 58:
                return (rc.readSharedArray(25) & 128) >>> 7;
            case 59:
                return (rc.readSharedArray(25) & 2) >>> 1;
            case 60:
                return (rc.readSharedArray(26) & 2048) >>> 11;
            case 61:
                return (rc.readSharedArray(26) & 32) >>> 5;
            case 62:
                return (rc.readSharedArray(27) & 32768) >>> 15;
            case 63:
                return (rc.readSharedArray(27) & 512) >>> 9;
            case 64:
                return (rc.readSharedArray(27) & 8) >>> 3;
            case 65:
                return (rc.readSharedArray(28) & 8192) >>> 13;
            case 66:
                return (rc.readSharedArray(28) & 128) >>> 7;
            case 67:
                return (rc.readSharedArray(28) & 2) >>> 1;
            case 68:
                return (rc.readSharedArray(29) & 2048) >>> 11;
            case 69:
                return (rc.readSharedArray(29) & 32) >>> 5;
            case 70:
                return (rc.readSharedArray(30) & 32768) >>> 15;
            case 71:
                return (rc.readSharedArray(30) & 512) >>> 9;
            case 72:
                return (rc.readSharedArray(30) & 8) >>> 3;
            case 73:
                return (rc.readSharedArray(31) & 8192) >>> 13;
            case 74:
                return (rc.readSharedArray(31) & 128) >>> 7;
            case 75:
                return (rc.readSharedArray(31) & 2) >>> 1;
            case 76:
                return (rc.readSharedArray(32) & 2048) >>> 11;
            case 77:
                return (rc.readSharedArray(32) & 32) >>> 5;
            case 78:
                return (rc.readSharedArray(33) & 32768) >>> 15;
            case 79:
                return (rc.readSharedArray(33) & 512) >>> 9;
            case 80:
                return (rc.readSharedArray(33) & 8) >>> 3;
            case 81:
                return (rc.readSharedArray(34) & 8192) >>> 13;
            case 82:
                return (rc.readSharedArray(34) & 128) >>> 7;
            case 83:
                return (rc.readSharedArray(34) & 2) >>> 1;
            case 84:
                return (rc.readSharedArray(35) & 2048) >>> 11;
            case 85:
                return (rc.readSharedArray(35) & 32) >>> 5;
            case 86:
                return (rc.readSharedArray(36) & 32768) >>> 15;
            case 87:
                return (rc.readSharedArray(36) & 512) >>> 9;
            case 88:
                return (rc.readSharedArray(36) & 8) >>> 3;
            case 89:
                return (rc.readSharedArray(37) & 8192) >>> 13;
            case 90:
                return (rc.readSharedArray(37) & 128) >>> 7;
            case 91:
                return (rc.readSharedArray(37) & 2) >>> 1;
            case 92:
                return (rc.readSharedArray(38) & 2048) >>> 11;
            case 93:
                return (rc.readSharedArray(38) & 32) >>> 5;
            case 94:
                return (rc.readSharedArray(39) & 32768) >>> 15;
            case 95:
                return (rc.readSharedArray(39) & 512) >>> 9;
            case 96:
                return (rc.readSharedArray(39) & 8) >>> 3;
            case 97:
                return (rc.readSharedArray(40) & 8192) >>> 13;
            case 98:
                return (rc.readSharedArray(40) & 128) >>> 7;
            case 99:
                return (rc.readSharedArray(40) & 2) >>> 1;
            default:
                return -1;
        }
    }

    public static void writeSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65527) | (value << 3));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 57343) | (value << 13));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65407) | (value << 7));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65533) | (value << 1));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 63487) | (value << 11));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65503) | (value << 5));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 32767) | (value << 15));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65023) | (value << 9));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65527) | (value << 3));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 57343) | (value << 13));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65407) | (value << 7));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65533) | (value << 1));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 63487) | (value << 11));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65503) | (value << 5));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 32767) | (value << 15));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65023) | (value << 9));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65527) | (value << 3));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 57343) | (value << 13));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65407) | (value << 7));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65533) | (value << 1));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 63487) | (value << 11));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65503) | (value << 5));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 32767) | (value << 15));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65023) | (value << 9));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65527) | (value << 3));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 57343) | (value << 13));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65407) | (value << 7));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65533) | (value << 1));
                break;
            case 28:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 63487) | (value << 11));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65503) | (value << 5));
                break;
            case 30:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 32767) | (value << 15));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65023) | (value << 9));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65527) | (value << 3));
                break;
            case 33:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 57343) | (value << 13));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65407) | (value << 7));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65533) | (value << 1));
                break;
            case 36:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 63487) | (value << 11));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65503) | (value << 5));
                break;
            case 38:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 32767) | (value << 15));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65023) | (value << 9));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65527) | (value << 3));
                break;
            case 41:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 57343) | (value << 13));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65407) | (value << 7));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65533) | (value << 1));
                break;
            case 44:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 63487) | (value << 11));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65503) | (value << 5));
                break;
            case 46:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 32767) | (value << 15));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65023) | (value << 9));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65527) | (value << 3));
                break;
            case 49:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 57343) | (value << 13));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65407) | (value << 7));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65533) | (value << 1));
                break;
            case 52:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 63487) | (value << 11));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65503) | (value << 5));
                break;
            case 54:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 32767) | (value << 15));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65023) | (value << 9));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65527) | (value << 3));
                break;
            case 57:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 57343) | (value << 13));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65407) | (value << 7));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65533) | (value << 1));
                break;
            case 60:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 63487) | (value << 11));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65503) | (value << 5));
                break;
            case 62:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 32767) | (value << 15));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65023) | (value << 9));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65527) | (value << 3));
                break;
            case 65:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 57343) | (value << 13));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65407) | (value << 7));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65533) | (value << 1));
                break;
            case 68:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 63487) | (value << 11));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65503) | (value << 5));
                break;
            case 70:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 32767) | (value << 15));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65023) | (value << 9));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65527) | (value << 3));
                break;
            case 73:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 57343) | (value << 13));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65407) | (value << 7));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65533) | (value << 1));
                break;
            case 76:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 63487) | (value << 11));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65503) | (value << 5));
                break;
            case 78:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 32767) | (value << 15));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65023) | (value << 9));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65527) | (value << 3));
                break;
            case 81:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 57343) | (value << 13));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65407) | (value << 7));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65533) | (value << 1));
                break;
            case 84:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 63487) | (value << 11));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65503) | (value << 5));
                break;
            case 86:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 32767) | (value << 15));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65023) | (value << 9));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65527) | (value << 3));
                break;
            case 89:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 57343) | (value << 13));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65407) | (value << 7));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65533) | (value << 1));
                break;
            case 92:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 63487) | (value << 11));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65503) | (value << 5));
                break;
            case 94:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 32767) | (value << 15));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65023) | (value << 9));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65527) | (value << 3));
                break;
            case 97:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 57343) | (value << 13));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65407) | (value << 7));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65533) | (value << 1));
                break;
        }
    }

    public static void writeBPSectorIslands(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65527) | (value << 3));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 57343) | (value << 13));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65407) | (value << 7));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65533) | (value << 1));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 63487) | (value << 11));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65503) | (value << 5));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 32767) | (value << 15));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65023) | (value << 9));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65527) | (value << 3));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 57343) | (value << 13));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65407) | (value << 7));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65533) | (value << 1));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 63487) | (value << 11));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65503) | (value << 5));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 32767) | (value << 15));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65023) | (value << 9));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65527) | (value << 3));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 57343) | (value << 13));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65407) | (value << 7));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65533) | (value << 1));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 63487) | (value << 11));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 65503) | (value << 5));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 32767) | (value << 15));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 65023) | (value << 9));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65527) | (value << 3));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 57343) | (value << 13));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 65407) | (value << 7));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65533) | (value << 1));
                break;
            case 28:
                writeToBufferPool(14, (bufferPool[14] & 63487) | (value << 11));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 65503) | (value << 5));
                break;
            case 30:
                writeToBufferPool(15, (bufferPool[15] & 32767) | (value << 15));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 65023) | (value << 9));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65527) | (value << 3));
                break;
            case 33:
                writeToBufferPool(16, (bufferPool[16] & 57343) | (value << 13));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 65407) | (value << 7));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65533) | (value << 1));
                break;
            case 36:
                writeToBufferPool(17, (bufferPool[17] & 63487) | (value << 11));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 65503) | (value << 5));
                break;
            case 38:
                writeToBufferPool(18, (bufferPool[18] & 32767) | (value << 15));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 65023) | (value << 9));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65527) | (value << 3));
                break;
            case 41:
                writeToBufferPool(19, (bufferPool[19] & 57343) | (value << 13));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 65407) | (value << 7));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65533) | (value << 1));
                break;
            case 44:
                writeToBufferPool(20, (bufferPool[20] & 63487) | (value << 11));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 65503) | (value << 5));
                break;
            case 46:
                writeToBufferPool(21, (bufferPool[21] & 32767) | (value << 15));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 65023) | (value << 9));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65527) | (value << 3));
                break;
            case 49:
                writeToBufferPool(22, (bufferPool[22] & 57343) | (value << 13));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 65407) | (value << 7));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65533) | (value << 1));
                break;
            case 52:
                writeToBufferPool(23, (bufferPool[23] & 63487) | (value << 11));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 65503) | (value << 5));
                break;
            case 54:
                writeToBufferPool(24, (bufferPool[24] & 32767) | (value << 15));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 65023) | (value << 9));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65527) | (value << 3));
                break;
            case 57:
                writeToBufferPool(25, (bufferPool[25] & 57343) | (value << 13));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 65407) | (value << 7));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65533) | (value << 1));
                break;
            case 60:
                writeToBufferPool(26, (bufferPool[26] & 63487) | (value << 11));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 65503) | (value << 5));
                break;
            case 62:
                writeToBufferPool(27, (bufferPool[27] & 32767) | (value << 15));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 65023) | (value << 9));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65527) | (value << 3));
                break;
            case 65:
                writeToBufferPool(28, (bufferPool[28] & 57343) | (value << 13));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 65407) | (value << 7));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65533) | (value << 1));
                break;
            case 68:
                writeToBufferPool(29, (bufferPool[29] & 63487) | (value << 11));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 65503) | (value << 5));
                break;
            case 70:
                writeToBufferPool(30, (bufferPool[30] & 32767) | (value << 15));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 65023) | (value << 9));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65527) | (value << 3));
                break;
            case 73:
                writeToBufferPool(31, (bufferPool[31] & 57343) | (value << 13));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 65407) | (value << 7));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65533) | (value << 1));
                break;
            case 76:
                writeToBufferPool(32, (bufferPool[32] & 63487) | (value << 11));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 65503) | (value << 5));
                break;
            case 78:
                writeToBufferPool(33, (bufferPool[33] & 32767) | (value << 15));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 65023) | (value << 9));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65527) | (value << 3));
                break;
            case 81:
                writeToBufferPool(34, (bufferPool[34] & 57343) | (value << 13));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 65407) | (value << 7));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65533) | (value << 1));
                break;
            case 84:
                writeToBufferPool(35, (bufferPool[35] & 63487) | (value << 11));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 65503) | (value << 5));
                break;
            case 86:
                writeToBufferPool(36, (bufferPool[36] & 32767) | (value << 15));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 65023) | (value << 9));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65527) | (value << 3));
                break;
            case 89:
                writeToBufferPool(37, (bufferPool[37] & 57343) | (value << 13));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 65407) | (value << 7));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65533) | (value << 1));
                break;
            case 92:
                writeToBufferPool(38, (bufferPool[38] & 63487) | (value << 11));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 65503) | (value << 5));
                break;
            case 94:
                writeToBufferPool(39, (bufferPool[39] & 32767) | (value << 15));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 65023) | (value << 9));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65527) | (value << 3));
                break;
            case 97:
                writeToBufferPool(40, (bufferPool[40] & 57343) | (value << 13));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 65407) | (value << 7));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65533) | (value << 1));
                break;
        }
    }

    public static int readSectorAdamantiumFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 4) >>> 2;
            case 1:
                return (rc.readSharedArray(4) & 4096) >>> 12;
            case 2:
                return (rc.readSharedArray(4) & 64) >>> 6;
            case 3:
                return (rc.readSharedArray(4) & 1);
            case 4:
                return (rc.readSharedArray(5) & 1024) >>> 10;
            case 5:
                return (rc.readSharedArray(5) & 16) >>> 4;
            case 6:
                return (rc.readSharedArray(6) & 16384) >>> 14;
            case 7:
                return (rc.readSharedArray(6) & 256) >>> 8;
            case 8:
                return (rc.readSharedArray(6) & 4) >>> 2;
            case 9:
                return (rc.readSharedArray(7) & 4096) >>> 12;
            case 10:
                return (rc.readSharedArray(7) & 64) >>> 6;
            case 11:
                return (rc.readSharedArray(7) & 1);
            case 12:
                return (rc.readSharedArray(8) & 1024) >>> 10;
            case 13:
                return (rc.readSharedArray(8) & 16) >>> 4;
            case 14:
                return (rc.readSharedArray(9) & 16384) >>> 14;
            case 15:
                return (rc.readSharedArray(9) & 256) >>> 8;
            case 16:
                return (rc.readSharedArray(9) & 4) >>> 2;
            case 17:
                return (rc.readSharedArray(10) & 4096) >>> 12;
            case 18:
                return (rc.readSharedArray(10) & 64) >>> 6;
            case 19:
                return (rc.readSharedArray(10) & 1);
            case 20:
                return (rc.readSharedArray(11) & 1024) >>> 10;
            case 21:
                return (rc.readSharedArray(11) & 16) >>> 4;
            case 22:
                return (rc.readSharedArray(12) & 16384) >>> 14;
            case 23:
                return (rc.readSharedArray(12) & 256) >>> 8;
            case 24:
                return (rc.readSharedArray(12) & 4) >>> 2;
            case 25:
                return (rc.readSharedArray(13) & 4096) >>> 12;
            case 26:
                return (rc.readSharedArray(13) & 64) >>> 6;
            case 27:
                return (rc.readSharedArray(13) & 1);
            case 28:
                return (rc.readSharedArray(14) & 1024) >>> 10;
            case 29:
                return (rc.readSharedArray(14) & 16) >>> 4;
            case 30:
                return (rc.readSharedArray(15) & 16384) >>> 14;
            case 31:
                return (rc.readSharedArray(15) & 256) >>> 8;
            case 32:
                return (rc.readSharedArray(15) & 4) >>> 2;
            case 33:
                return (rc.readSharedArray(16) & 4096) >>> 12;
            case 34:
                return (rc.readSharedArray(16) & 64) >>> 6;
            case 35:
                return (rc.readSharedArray(16) & 1);
            case 36:
                return (rc.readSharedArray(17) & 1024) >>> 10;
            case 37:
                return (rc.readSharedArray(17) & 16) >>> 4;
            case 38:
                return (rc.readSharedArray(18) & 16384) >>> 14;
            case 39:
                return (rc.readSharedArray(18) & 256) >>> 8;
            case 40:
                return (rc.readSharedArray(18) & 4) >>> 2;
            case 41:
                return (rc.readSharedArray(19) & 4096) >>> 12;
            case 42:
                return (rc.readSharedArray(19) & 64) >>> 6;
            case 43:
                return (rc.readSharedArray(19) & 1);
            case 44:
                return (rc.readSharedArray(20) & 1024) >>> 10;
            case 45:
                return (rc.readSharedArray(20) & 16) >>> 4;
            case 46:
                return (rc.readSharedArray(21) & 16384) >>> 14;
            case 47:
                return (rc.readSharedArray(21) & 256) >>> 8;
            case 48:
                return (rc.readSharedArray(21) & 4) >>> 2;
            case 49:
                return (rc.readSharedArray(22) & 4096) >>> 12;
            case 50:
                return (rc.readSharedArray(22) & 64) >>> 6;
            case 51:
                return (rc.readSharedArray(22) & 1);
            case 52:
                return (rc.readSharedArray(23) & 1024) >>> 10;
            case 53:
                return (rc.readSharedArray(23) & 16) >>> 4;
            case 54:
                return (rc.readSharedArray(24) & 16384) >>> 14;
            case 55:
                return (rc.readSharedArray(24) & 256) >>> 8;
            case 56:
                return (rc.readSharedArray(24) & 4) >>> 2;
            case 57:
                return (rc.readSharedArray(25) & 4096) >>> 12;
            case 58:
                return (rc.readSharedArray(25) & 64) >>> 6;
            case 59:
                return (rc.readSharedArray(25) & 1);
            case 60:
                return (rc.readSharedArray(26) & 1024) >>> 10;
            case 61:
                return (rc.readSharedArray(26) & 16) >>> 4;
            case 62:
                return (rc.readSharedArray(27) & 16384) >>> 14;
            case 63:
                return (rc.readSharedArray(27) & 256) >>> 8;
            case 64:
                return (rc.readSharedArray(27) & 4) >>> 2;
            case 65:
                return (rc.readSharedArray(28) & 4096) >>> 12;
            case 66:
                return (rc.readSharedArray(28) & 64) >>> 6;
            case 67:
                return (rc.readSharedArray(28) & 1);
            case 68:
                return (rc.readSharedArray(29) & 1024) >>> 10;
            case 69:
                return (rc.readSharedArray(29) & 16) >>> 4;
            case 70:
                return (rc.readSharedArray(30) & 16384) >>> 14;
            case 71:
                return (rc.readSharedArray(30) & 256) >>> 8;
            case 72:
                return (rc.readSharedArray(30) & 4) >>> 2;
            case 73:
                return (rc.readSharedArray(31) & 4096) >>> 12;
            case 74:
                return (rc.readSharedArray(31) & 64) >>> 6;
            case 75:
                return (rc.readSharedArray(31) & 1);
            case 76:
                return (rc.readSharedArray(32) & 1024) >>> 10;
            case 77:
                return (rc.readSharedArray(32) & 16) >>> 4;
            case 78:
                return (rc.readSharedArray(33) & 16384) >>> 14;
            case 79:
                return (rc.readSharedArray(33) & 256) >>> 8;
            case 80:
                return (rc.readSharedArray(33) & 4) >>> 2;
            case 81:
                return (rc.readSharedArray(34) & 4096) >>> 12;
            case 82:
                return (rc.readSharedArray(34) & 64) >>> 6;
            case 83:
                return (rc.readSharedArray(34) & 1);
            case 84:
                return (rc.readSharedArray(35) & 1024) >>> 10;
            case 85:
                return (rc.readSharedArray(35) & 16) >>> 4;
            case 86:
                return (rc.readSharedArray(36) & 16384) >>> 14;
            case 87:
                return (rc.readSharedArray(36) & 256) >>> 8;
            case 88:
                return (rc.readSharedArray(36) & 4) >>> 2;
            case 89:
                return (rc.readSharedArray(37) & 4096) >>> 12;
            case 90:
                return (rc.readSharedArray(37) & 64) >>> 6;
            case 91:
                return (rc.readSharedArray(37) & 1);
            case 92:
                return (rc.readSharedArray(38) & 1024) >>> 10;
            case 93:
                return (rc.readSharedArray(38) & 16) >>> 4;
            case 94:
                return (rc.readSharedArray(39) & 16384) >>> 14;
            case 95:
                return (rc.readSharedArray(39) & 256) >>> 8;
            case 96:
                return (rc.readSharedArray(39) & 4) >>> 2;
            case 97:
                return (rc.readSharedArray(40) & 4096) >>> 12;
            case 98:
                return (rc.readSharedArray(40) & 64) >>> 6;
            case 99:
                return (rc.readSharedArray(40) & 1);
            default:
                return -1;
        }
    }

    public static void writeSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65531) | (value << 2));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 61439) | (value << 12));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65471) | (value << 6));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65534) | (value));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 64511) | (value << 10));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65519) | (value << 4));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 49151) | (value << 14));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65279) | (value << 8));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65531) | (value << 2));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 61439) | (value << 12));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65471) | (value << 6));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65534) | (value));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 64511) | (value << 10));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65519) | (value << 4));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 49151) | (value << 14));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65279) | (value << 8));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65531) | (value << 2));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 61439) | (value << 12));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65471) | (value << 6));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65534) | (value));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 64511) | (value << 10));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65519) | (value << 4));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 49151) | (value << 14));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65279) | (value << 8));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65531) | (value << 2));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 61439) | (value << 12));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65471) | (value << 6));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65534) | (value));
                break;
            case 28:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 64511) | (value << 10));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65519) | (value << 4));
                break;
            case 30:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 49151) | (value << 14));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65279) | (value << 8));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65531) | (value << 2));
                break;
            case 33:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 61439) | (value << 12));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65471) | (value << 6));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65534) | (value));
                break;
            case 36:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 64511) | (value << 10));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65519) | (value << 4));
                break;
            case 38:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 49151) | (value << 14));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65279) | (value << 8));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65531) | (value << 2));
                break;
            case 41:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 61439) | (value << 12));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65471) | (value << 6));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65534) | (value));
                break;
            case 44:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 64511) | (value << 10));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65519) | (value << 4));
                break;
            case 46:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 49151) | (value << 14));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65279) | (value << 8));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65531) | (value << 2));
                break;
            case 49:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 61439) | (value << 12));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65471) | (value << 6));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65534) | (value));
                break;
            case 52:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 64511) | (value << 10));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65519) | (value << 4));
                break;
            case 54:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 49151) | (value << 14));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65279) | (value << 8));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65531) | (value << 2));
                break;
            case 57:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 61439) | (value << 12));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65471) | (value << 6));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65534) | (value));
                break;
            case 60:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 64511) | (value << 10));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65519) | (value << 4));
                break;
            case 62:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 49151) | (value << 14));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65279) | (value << 8));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65531) | (value << 2));
                break;
            case 65:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 61439) | (value << 12));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65471) | (value << 6));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65534) | (value));
                break;
            case 68:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 64511) | (value << 10));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65519) | (value << 4));
                break;
            case 70:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 49151) | (value << 14));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65279) | (value << 8));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65531) | (value << 2));
                break;
            case 73:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 61439) | (value << 12));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65471) | (value << 6));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65534) | (value));
                break;
            case 76:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 64511) | (value << 10));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65519) | (value << 4));
                break;
            case 78:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 49151) | (value << 14));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65279) | (value << 8));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65531) | (value << 2));
                break;
            case 81:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 61439) | (value << 12));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65471) | (value << 6));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65534) | (value));
                break;
            case 84:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 64511) | (value << 10));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65519) | (value << 4));
                break;
            case 86:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 49151) | (value << 14));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65279) | (value << 8));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65531) | (value << 2));
                break;
            case 89:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 61439) | (value << 12));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65471) | (value << 6));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65534) | (value));
                break;
            case 92:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 64511) | (value << 10));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65519) | (value << 4));
                break;
            case 94:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 49151) | (value << 14));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65279) | (value << 8));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65531) | (value << 2));
                break;
            case 97:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 61439) | (value << 12));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65471) | (value << 6));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65534) | (value));
                break;
        }
    }

    public static void writeBPSectorAdamantiumFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65531) | (value << 2));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 61439) | (value << 12));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65471) | (value << 6));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65534) | (value));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 64511) | (value << 10));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65519) | (value << 4));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 49151) | (value << 14));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65279) | (value << 8));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65531) | (value << 2));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 61439) | (value << 12));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65471) | (value << 6));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65534) | (value));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 64511) | (value << 10));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65519) | (value << 4));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 49151) | (value << 14));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65279) | (value << 8));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65531) | (value << 2));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 61439) | (value << 12));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65471) | (value << 6));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65534) | (value));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 64511) | (value << 10));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 65519) | (value << 4));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 49151) | (value << 14));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 65279) | (value << 8));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65531) | (value << 2));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 61439) | (value << 12));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 65471) | (value << 6));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65534) | (value));
                break;
            case 28:
                writeToBufferPool(14, (bufferPool[14] & 64511) | (value << 10));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 65519) | (value << 4));
                break;
            case 30:
                writeToBufferPool(15, (bufferPool[15] & 49151) | (value << 14));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 65279) | (value << 8));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65531) | (value << 2));
                break;
            case 33:
                writeToBufferPool(16, (bufferPool[16] & 61439) | (value << 12));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 65471) | (value << 6));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65534) | (value));
                break;
            case 36:
                writeToBufferPool(17, (bufferPool[17] & 64511) | (value << 10));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 65519) | (value << 4));
                break;
            case 38:
                writeToBufferPool(18, (bufferPool[18] & 49151) | (value << 14));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 65279) | (value << 8));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65531) | (value << 2));
                break;
            case 41:
                writeToBufferPool(19, (bufferPool[19] & 61439) | (value << 12));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 65471) | (value << 6));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65534) | (value));
                break;
            case 44:
                writeToBufferPool(20, (bufferPool[20] & 64511) | (value << 10));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 65519) | (value << 4));
                break;
            case 46:
                writeToBufferPool(21, (bufferPool[21] & 49151) | (value << 14));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 65279) | (value << 8));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65531) | (value << 2));
                break;
            case 49:
                writeToBufferPool(22, (bufferPool[22] & 61439) | (value << 12));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 65471) | (value << 6));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65534) | (value));
                break;
            case 52:
                writeToBufferPool(23, (bufferPool[23] & 64511) | (value << 10));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 65519) | (value << 4));
                break;
            case 54:
                writeToBufferPool(24, (bufferPool[24] & 49151) | (value << 14));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 65279) | (value << 8));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65531) | (value << 2));
                break;
            case 57:
                writeToBufferPool(25, (bufferPool[25] & 61439) | (value << 12));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 65471) | (value << 6));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65534) | (value));
                break;
            case 60:
                writeToBufferPool(26, (bufferPool[26] & 64511) | (value << 10));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 65519) | (value << 4));
                break;
            case 62:
                writeToBufferPool(27, (bufferPool[27] & 49151) | (value << 14));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 65279) | (value << 8));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65531) | (value << 2));
                break;
            case 65:
                writeToBufferPool(28, (bufferPool[28] & 61439) | (value << 12));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 65471) | (value << 6));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65534) | (value));
                break;
            case 68:
                writeToBufferPool(29, (bufferPool[29] & 64511) | (value << 10));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 65519) | (value << 4));
                break;
            case 70:
                writeToBufferPool(30, (bufferPool[30] & 49151) | (value << 14));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 65279) | (value << 8));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65531) | (value << 2));
                break;
            case 73:
                writeToBufferPool(31, (bufferPool[31] & 61439) | (value << 12));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 65471) | (value << 6));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65534) | (value));
                break;
            case 76:
                writeToBufferPool(32, (bufferPool[32] & 64511) | (value << 10));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 65519) | (value << 4));
                break;
            case 78:
                writeToBufferPool(33, (bufferPool[33] & 49151) | (value << 14));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 65279) | (value << 8));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65531) | (value << 2));
                break;
            case 81:
                writeToBufferPool(34, (bufferPool[34] & 61439) | (value << 12));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 65471) | (value << 6));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65534) | (value));
                break;
            case 84:
                writeToBufferPool(35, (bufferPool[35] & 64511) | (value << 10));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 65519) | (value << 4));
                break;
            case 86:
                writeToBufferPool(36, (bufferPool[36] & 49151) | (value << 14));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 65279) | (value << 8));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65531) | (value << 2));
                break;
            case 89:
                writeToBufferPool(37, (bufferPool[37] & 61439) | (value << 12));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 65471) | (value << 6));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65534) | (value));
                break;
            case 92:
                writeToBufferPool(38, (bufferPool[38] & 64511) | (value << 10));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 65519) | (value << 4));
                break;
            case 94:
                writeToBufferPool(39, (bufferPool[39] & 49151) | (value << 14));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 65279) | (value << 8));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65531) | (value << 2));
                break;
            case 97:
                writeToBufferPool(40, (bufferPool[40] & 61439) | (value << 12));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 65471) | (value << 6));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65534) | (value));
                break;
        }
    }

    public static int readSectorManaFlag(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(3) & 2) >>> 1;
            case 1:
                return (rc.readSharedArray(4) & 2048) >>> 11;
            case 2:
                return (rc.readSharedArray(4) & 32) >>> 5;
            case 3:
                return (rc.readSharedArray(5) & 32768) >>> 15;
            case 4:
                return (rc.readSharedArray(5) & 512) >>> 9;
            case 5:
                return (rc.readSharedArray(5) & 8) >>> 3;
            case 6:
                return (rc.readSharedArray(6) & 8192) >>> 13;
            case 7:
                return (rc.readSharedArray(6) & 128) >>> 7;
            case 8:
                return (rc.readSharedArray(6) & 2) >>> 1;
            case 9:
                return (rc.readSharedArray(7) & 2048) >>> 11;
            case 10:
                return (rc.readSharedArray(7) & 32) >>> 5;
            case 11:
                return (rc.readSharedArray(8) & 32768) >>> 15;
            case 12:
                return (rc.readSharedArray(8) & 512) >>> 9;
            case 13:
                return (rc.readSharedArray(8) & 8) >>> 3;
            case 14:
                return (rc.readSharedArray(9) & 8192) >>> 13;
            case 15:
                return (rc.readSharedArray(9) & 128) >>> 7;
            case 16:
                return (rc.readSharedArray(9) & 2) >>> 1;
            case 17:
                return (rc.readSharedArray(10) & 2048) >>> 11;
            case 18:
                return (rc.readSharedArray(10) & 32) >>> 5;
            case 19:
                return (rc.readSharedArray(11) & 32768) >>> 15;
            case 20:
                return (rc.readSharedArray(11) & 512) >>> 9;
            case 21:
                return (rc.readSharedArray(11) & 8) >>> 3;
            case 22:
                return (rc.readSharedArray(12) & 8192) >>> 13;
            case 23:
                return (rc.readSharedArray(12) & 128) >>> 7;
            case 24:
                return (rc.readSharedArray(12) & 2) >>> 1;
            case 25:
                return (rc.readSharedArray(13) & 2048) >>> 11;
            case 26:
                return (rc.readSharedArray(13) & 32) >>> 5;
            case 27:
                return (rc.readSharedArray(14) & 32768) >>> 15;
            case 28:
                return (rc.readSharedArray(14) & 512) >>> 9;
            case 29:
                return (rc.readSharedArray(14) & 8) >>> 3;
            case 30:
                return (rc.readSharedArray(15) & 8192) >>> 13;
            case 31:
                return (rc.readSharedArray(15) & 128) >>> 7;
            case 32:
                return (rc.readSharedArray(15) & 2) >>> 1;
            case 33:
                return (rc.readSharedArray(16) & 2048) >>> 11;
            case 34:
                return (rc.readSharedArray(16) & 32) >>> 5;
            case 35:
                return (rc.readSharedArray(17) & 32768) >>> 15;
            case 36:
                return (rc.readSharedArray(17) & 512) >>> 9;
            case 37:
                return (rc.readSharedArray(17) & 8) >>> 3;
            case 38:
                return (rc.readSharedArray(18) & 8192) >>> 13;
            case 39:
                return (rc.readSharedArray(18) & 128) >>> 7;
            case 40:
                return (rc.readSharedArray(18) & 2) >>> 1;
            case 41:
                return (rc.readSharedArray(19) & 2048) >>> 11;
            case 42:
                return (rc.readSharedArray(19) & 32) >>> 5;
            case 43:
                return (rc.readSharedArray(20) & 32768) >>> 15;
            case 44:
                return (rc.readSharedArray(20) & 512) >>> 9;
            case 45:
                return (rc.readSharedArray(20) & 8) >>> 3;
            case 46:
                return (rc.readSharedArray(21) & 8192) >>> 13;
            case 47:
                return (rc.readSharedArray(21) & 128) >>> 7;
            case 48:
                return (rc.readSharedArray(21) & 2) >>> 1;
            case 49:
                return (rc.readSharedArray(22) & 2048) >>> 11;
            case 50:
                return (rc.readSharedArray(22) & 32) >>> 5;
            case 51:
                return (rc.readSharedArray(23) & 32768) >>> 15;
            case 52:
                return (rc.readSharedArray(23) & 512) >>> 9;
            case 53:
                return (rc.readSharedArray(23) & 8) >>> 3;
            case 54:
                return (rc.readSharedArray(24) & 8192) >>> 13;
            case 55:
                return (rc.readSharedArray(24) & 128) >>> 7;
            case 56:
                return (rc.readSharedArray(24) & 2) >>> 1;
            case 57:
                return (rc.readSharedArray(25) & 2048) >>> 11;
            case 58:
                return (rc.readSharedArray(25) & 32) >>> 5;
            case 59:
                return (rc.readSharedArray(26) & 32768) >>> 15;
            case 60:
                return (rc.readSharedArray(26) & 512) >>> 9;
            case 61:
                return (rc.readSharedArray(26) & 8) >>> 3;
            case 62:
                return (rc.readSharedArray(27) & 8192) >>> 13;
            case 63:
                return (rc.readSharedArray(27) & 128) >>> 7;
            case 64:
                return (rc.readSharedArray(27) & 2) >>> 1;
            case 65:
                return (rc.readSharedArray(28) & 2048) >>> 11;
            case 66:
                return (rc.readSharedArray(28) & 32) >>> 5;
            case 67:
                return (rc.readSharedArray(29) & 32768) >>> 15;
            case 68:
                return (rc.readSharedArray(29) & 512) >>> 9;
            case 69:
                return (rc.readSharedArray(29) & 8) >>> 3;
            case 70:
                return (rc.readSharedArray(30) & 8192) >>> 13;
            case 71:
                return (rc.readSharedArray(30) & 128) >>> 7;
            case 72:
                return (rc.readSharedArray(30) & 2) >>> 1;
            case 73:
                return (rc.readSharedArray(31) & 2048) >>> 11;
            case 74:
                return (rc.readSharedArray(31) & 32) >>> 5;
            case 75:
                return (rc.readSharedArray(32) & 32768) >>> 15;
            case 76:
                return (rc.readSharedArray(32) & 512) >>> 9;
            case 77:
                return (rc.readSharedArray(32) & 8) >>> 3;
            case 78:
                return (rc.readSharedArray(33) & 8192) >>> 13;
            case 79:
                return (rc.readSharedArray(33) & 128) >>> 7;
            case 80:
                return (rc.readSharedArray(33) & 2) >>> 1;
            case 81:
                return (rc.readSharedArray(34) & 2048) >>> 11;
            case 82:
                return (rc.readSharedArray(34) & 32) >>> 5;
            case 83:
                return (rc.readSharedArray(35) & 32768) >>> 15;
            case 84:
                return (rc.readSharedArray(35) & 512) >>> 9;
            case 85:
                return (rc.readSharedArray(35) & 8) >>> 3;
            case 86:
                return (rc.readSharedArray(36) & 8192) >>> 13;
            case 87:
                return (rc.readSharedArray(36) & 128) >>> 7;
            case 88:
                return (rc.readSharedArray(36) & 2) >>> 1;
            case 89:
                return (rc.readSharedArray(37) & 2048) >>> 11;
            case 90:
                return (rc.readSharedArray(37) & 32) >>> 5;
            case 91:
                return (rc.readSharedArray(38) & 32768) >>> 15;
            case 92:
                return (rc.readSharedArray(38) & 512) >>> 9;
            case 93:
                return (rc.readSharedArray(38) & 8) >>> 3;
            case 94:
                return (rc.readSharedArray(39) & 8192) >>> 13;
            case 95:
                return (rc.readSharedArray(39) & 128) >>> 7;
            case 96:
                return (rc.readSharedArray(39) & 2) >>> 1;
            case 97:
                return (rc.readSharedArray(40) & 2048) >>> 11;
            case 98:
                return (rc.readSharedArray(40) & 32) >>> 5;
            case 99:
                return (rc.readSharedArray(41) & 32768) >>> 15;
            default:
                return -1;
        }
    }

    public static void writeSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65533) | (value << 1));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 63487) | (value << 11));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65503) | (value << 5));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 32767) | (value << 15));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65023) | (value << 9));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65527) | (value << 3));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 57343) | (value << 13));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65407) | (value << 7));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65533) | (value << 1));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 63487) | (value << 11));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65503) | (value << 5));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 32767) | (value << 15));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65023) | (value << 9));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65527) | (value << 3));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 57343) | (value << 13));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65407) | (value << 7));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65533) | (value << 1));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 63487) | (value << 11));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65503) | (value << 5));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 32767) | (value << 15));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65023) | (value << 9));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65527) | (value << 3));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 57343) | (value << 13));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65407) | (value << 7));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65533) | (value << 1));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 63487) | (value << 11));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65503) | (value << 5));
                break;
            case 27:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 32767) | (value << 15));
                break;
            case 28:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65023) | (value << 9));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65527) | (value << 3));
                break;
            case 30:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 57343) | (value << 13));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65407) | (value << 7));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65533) | (value << 1));
                break;
            case 33:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 63487) | (value << 11));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65503) | (value << 5));
                break;
            case 35:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 32767) | (value << 15));
                break;
            case 36:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65023) | (value << 9));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65527) | (value << 3));
                break;
            case 38:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 57343) | (value << 13));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65407) | (value << 7));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65533) | (value << 1));
                break;
            case 41:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 63487) | (value << 11));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65503) | (value << 5));
                break;
            case 43:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 32767) | (value << 15));
                break;
            case 44:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65023) | (value << 9));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65527) | (value << 3));
                break;
            case 46:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 57343) | (value << 13));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65407) | (value << 7));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65533) | (value << 1));
                break;
            case 49:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 63487) | (value << 11));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65503) | (value << 5));
                break;
            case 51:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 32767) | (value << 15));
                break;
            case 52:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65023) | (value << 9));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65527) | (value << 3));
                break;
            case 54:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 57343) | (value << 13));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65407) | (value << 7));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65533) | (value << 1));
                break;
            case 57:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 63487) | (value << 11));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65503) | (value << 5));
                break;
            case 59:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 32767) | (value << 15));
                break;
            case 60:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65023) | (value << 9));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65527) | (value << 3));
                break;
            case 62:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 57343) | (value << 13));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65407) | (value << 7));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65533) | (value << 1));
                break;
            case 65:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 63487) | (value << 11));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65503) | (value << 5));
                break;
            case 67:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 32767) | (value << 15));
                break;
            case 68:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65023) | (value << 9));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65527) | (value << 3));
                break;
            case 70:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 57343) | (value << 13));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65407) | (value << 7));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65533) | (value << 1));
                break;
            case 73:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 63487) | (value << 11));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65503) | (value << 5));
                break;
            case 75:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 32767) | (value << 15));
                break;
            case 76:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65023) | (value << 9));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65527) | (value << 3));
                break;
            case 78:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 57343) | (value << 13));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65407) | (value << 7));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65533) | (value << 1));
                break;
            case 81:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 63487) | (value << 11));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65503) | (value << 5));
                break;
            case 83:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 32767) | (value << 15));
                break;
            case 84:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65023) | (value << 9));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65527) | (value << 3));
                break;
            case 86:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 57343) | (value << 13));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65407) | (value << 7));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65533) | (value << 1));
                break;
            case 89:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 63487) | (value << 11));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65503) | (value << 5));
                break;
            case 91:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 32767) | (value << 15));
                break;
            case 92:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65023) | (value << 9));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65527) | (value << 3));
                break;
            case 94:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 57343) | (value << 13));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65407) | (value << 7));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65533) | (value << 1));
                break;
            case 97:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 63487) | (value << 11));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65503) | (value << 5));
                break;
            case 99:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 32767) | (value << 15));
                break;
        }
    }

    public static void writeBPSectorManaFlag(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65533) | (value << 1));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 63487) | (value << 11));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65503) | (value << 5));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 32767) | (value << 15));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65023) | (value << 9));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65527) | (value << 3));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 57343) | (value << 13));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65407) | (value << 7));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65533) | (value << 1));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 63487) | (value << 11));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65503) | (value << 5));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 32767) | (value << 15));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65023) | (value << 9));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65527) | (value << 3));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 57343) | (value << 13));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65407) | (value << 7));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65533) | (value << 1));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 63487) | (value << 11));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65503) | (value << 5));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 32767) | (value << 15));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65023) | (value << 9));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 65527) | (value << 3));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 57343) | (value << 13));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 65407) | (value << 7));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65533) | (value << 1));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 63487) | (value << 11));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 65503) | (value << 5));
                break;
            case 27:
                writeToBufferPool(14, (bufferPool[14] & 32767) | (value << 15));
                break;
            case 28:
                writeToBufferPool(14, (bufferPool[14] & 65023) | (value << 9));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 65527) | (value << 3));
                break;
            case 30:
                writeToBufferPool(15, (bufferPool[15] & 57343) | (value << 13));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 65407) | (value << 7));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65533) | (value << 1));
                break;
            case 33:
                writeToBufferPool(16, (bufferPool[16] & 63487) | (value << 11));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 65503) | (value << 5));
                break;
            case 35:
                writeToBufferPool(17, (bufferPool[17] & 32767) | (value << 15));
                break;
            case 36:
                writeToBufferPool(17, (bufferPool[17] & 65023) | (value << 9));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 65527) | (value << 3));
                break;
            case 38:
                writeToBufferPool(18, (bufferPool[18] & 57343) | (value << 13));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 65407) | (value << 7));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65533) | (value << 1));
                break;
            case 41:
                writeToBufferPool(19, (bufferPool[19] & 63487) | (value << 11));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 65503) | (value << 5));
                break;
            case 43:
                writeToBufferPool(20, (bufferPool[20] & 32767) | (value << 15));
                break;
            case 44:
                writeToBufferPool(20, (bufferPool[20] & 65023) | (value << 9));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 65527) | (value << 3));
                break;
            case 46:
                writeToBufferPool(21, (bufferPool[21] & 57343) | (value << 13));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 65407) | (value << 7));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65533) | (value << 1));
                break;
            case 49:
                writeToBufferPool(22, (bufferPool[22] & 63487) | (value << 11));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 65503) | (value << 5));
                break;
            case 51:
                writeToBufferPool(23, (bufferPool[23] & 32767) | (value << 15));
                break;
            case 52:
                writeToBufferPool(23, (bufferPool[23] & 65023) | (value << 9));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 65527) | (value << 3));
                break;
            case 54:
                writeToBufferPool(24, (bufferPool[24] & 57343) | (value << 13));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 65407) | (value << 7));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65533) | (value << 1));
                break;
            case 57:
                writeToBufferPool(25, (bufferPool[25] & 63487) | (value << 11));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 65503) | (value << 5));
                break;
            case 59:
                writeToBufferPool(26, (bufferPool[26] & 32767) | (value << 15));
                break;
            case 60:
                writeToBufferPool(26, (bufferPool[26] & 65023) | (value << 9));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 65527) | (value << 3));
                break;
            case 62:
                writeToBufferPool(27, (bufferPool[27] & 57343) | (value << 13));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 65407) | (value << 7));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65533) | (value << 1));
                break;
            case 65:
                writeToBufferPool(28, (bufferPool[28] & 63487) | (value << 11));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 65503) | (value << 5));
                break;
            case 67:
                writeToBufferPool(29, (bufferPool[29] & 32767) | (value << 15));
                break;
            case 68:
                writeToBufferPool(29, (bufferPool[29] & 65023) | (value << 9));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 65527) | (value << 3));
                break;
            case 70:
                writeToBufferPool(30, (bufferPool[30] & 57343) | (value << 13));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 65407) | (value << 7));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65533) | (value << 1));
                break;
            case 73:
                writeToBufferPool(31, (bufferPool[31] & 63487) | (value << 11));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 65503) | (value << 5));
                break;
            case 75:
                writeToBufferPool(32, (bufferPool[32] & 32767) | (value << 15));
                break;
            case 76:
                writeToBufferPool(32, (bufferPool[32] & 65023) | (value << 9));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 65527) | (value << 3));
                break;
            case 78:
                writeToBufferPool(33, (bufferPool[33] & 57343) | (value << 13));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 65407) | (value << 7));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65533) | (value << 1));
                break;
            case 81:
                writeToBufferPool(34, (bufferPool[34] & 63487) | (value << 11));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 65503) | (value << 5));
                break;
            case 83:
                writeToBufferPool(35, (bufferPool[35] & 32767) | (value << 15));
                break;
            case 84:
                writeToBufferPool(35, (bufferPool[35] & 65023) | (value << 9));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 65527) | (value << 3));
                break;
            case 86:
                writeToBufferPool(36, (bufferPool[36] & 57343) | (value << 13));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 65407) | (value << 7));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65533) | (value << 1));
                break;
            case 89:
                writeToBufferPool(37, (bufferPool[37] & 63487) | (value << 11));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 65503) | (value << 5));
                break;
            case 91:
                writeToBufferPool(38, (bufferPool[38] & 32767) | (value << 15));
                break;
            case 92:
                writeToBufferPool(38, (bufferPool[38] & 65023) | (value << 9));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 65527) | (value << 3));
                break;
            case 94:
                writeToBufferPool(39, (bufferPool[39] & 57343) | (value << 13));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 65407) | (value << 7));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65533) | (value << 1));
                break;
            case 97:
                writeToBufferPool(40, (bufferPool[40] & 63487) | (value << 11));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 65503) | (value << 5));
                break;
            case 99:
                writeToBufferPool(41, (bufferPool[41] & 32767) | (value << 15));
                break;
        }
    }

    public static int readSectorControlStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(3) & 1) << 2) + ((rc.readSharedArray(4) & 49152) >>> 14);
            case 1:
                return (rc.readSharedArray(4) & 1792) >>> 8;
            case 2:
                return (rc.readSharedArray(4) & 28) >>> 2;
            case 3:
                return (rc.readSharedArray(5) & 28672) >>> 12;
            case 4:
                return (rc.readSharedArray(5) & 448) >>> 6;
            case 5:
                return (rc.readSharedArray(5) & 7);
            case 6:
                return (rc.readSharedArray(6) & 7168) >>> 10;
            case 7:
                return (rc.readSharedArray(6) & 112) >>> 4;
            case 8:
                return ((rc.readSharedArray(6) & 1) << 2) + ((rc.readSharedArray(7) & 49152) >>> 14);
            case 9:
                return (rc.readSharedArray(7) & 1792) >>> 8;
            case 10:
                return (rc.readSharedArray(7) & 28) >>> 2;
            case 11:
                return (rc.readSharedArray(8) & 28672) >>> 12;
            case 12:
                return (rc.readSharedArray(8) & 448) >>> 6;
            case 13:
                return (rc.readSharedArray(8) & 7);
            case 14:
                return (rc.readSharedArray(9) & 7168) >>> 10;
            case 15:
                return (rc.readSharedArray(9) & 112) >>> 4;
            case 16:
                return ((rc.readSharedArray(9) & 1) << 2) + ((rc.readSharedArray(10) & 49152) >>> 14);
            case 17:
                return (rc.readSharedArray(10) & 1792) >>> 8;
            case 18:
                return (rc.readSharedArray(10) & 28) >>> 2;
            case 19:
                return (rc.readSharedArray(11) & 28672) >>> 12;
            case 20:
                return (rc.readSharedArray(11) & 448) >>> 6;
            case 21:
                return (rc.readSharedArray(11) & 7);
            case 22:
                return (rc.readSharedArray(12) & 7168) >>> 10;
            case 23:
                return (rc.readSharedArray(12) & 112) >>> 4;
            case 24:
                return ((rc.readSharedArray(12) & 1) << 2) + ((rc.readSharedArray(13) & 49152) >>> 14);
            case 25:
                return (rc.readSharedArray(13) & 1792) >>> 8;
            case 26:
                return (rc.readSharedArray(13) & 28) >>> 2;
            case 27:
                return (rc.readSharedArray(14) & 28672) >>> 12;
            case 28:
                return (rc.readSharedArray(14) & 448) >>> 6;
            case 29:
                return (rc.readSharedArray(14) & 7);
            case 30:
                return (rc.readSharedArray(15) & 7168) >>> 10;
            case 31:
                return (rc.readSharedArray(15) & 112) >>> 4;
            case 32:
                return ((rc.readSharedArray(15) & 1) << 2) + ((rc.readSharedArray(16) & 49152) >>> 14);
            case 33:
                return (rc.readSharedArray(16) & 1792) >>> 8;
            case 34:
                return (rc.readSharedArray(16) & 28) >>> 2;
            case 35:
                return (rc.readSharedArray(17) & 28672) >>> 12;
            case 36:
                return (rc.readSharedArray(17) & 448) >>> 6;
            case 37:
                return (rc.readSharedArray(17) & 7);
            case 38:
                return (rc.readSharedArray(18) & 7168) >>> 10;
            case 39:
                return (rc.readSharedArray(18) & 112) >>> 4;
            case 40:
                return ((rc.readSharedArray(18) & 1) << 2) + ((rc.readSharedArray(19) & 49152) >>> 14);
            case 41:
                return (rc.readSharedArray(19) & 1792) >>> 8;
            case 42:
                return (rc.readSharedArray(19) & 28) >>> 2;
            case 43:
                return (rc.readSharedArray(20) & 28672) >>> 12;
            case 44:
                return (rc.readSharedArray(20) & 448) >>> 6;
            case 45:
                return (rc.readSharedArray(20) & 7);
            case 46:
                return (rc.readSharedArray(21) & 7168) >>> 10;
            case 47:
                return (rc.readSharedArray(21) & 112) >>> 4;
            case 48:
                return ((rc.readSharedArray(21) & 1) << 2) + ((rc.readSharedArray(22) & 49152) >>> 14);
            case 49:
                return (rc.readSharedArray(22) & 1792) >>> 8;
            case 50:
                return (rc.readSharedArray(22) & 28) >>> 2;
            case 51:
                return (rc.readSharedArray(23) & 28672) >>> 12;
            case 52:
                return (rc.readSharedArray(23) & 448) >>> 6;
            case 53:
                return (rc.readSharedArray(23) & 7);
            case 54:
                return (rc.readSharedArray(24) & 7168) >>> 10;
            case 55:
                return (rc.readSharedArray(24) & 112) >>> 4;
            case 56:
                return ((rc.readSharedArray(24) & 1) << 2) + ((rc.readSharedArray(25) & 49152) >>> 14);
            case 57:
                return (rc.readSharedArray(25) & 1792) >>> 8;
            case 58:
                return (rc.readSharedArray(25) & 28) >>> 2;
            case 59:
                return (rc.readSharedArray(26) & 28672) >>> 12;
            case 60:
                return (rc.readSharedArray(26) & 448) >>> 6;
            case 61:
                return (rc.readSharedArray(26) & 7);
            case 62:
                return (rc.readSharedArray(27) & 7168) >>> 10;
            case 63:
                return (rc.readSharedArray(27) & 112) >>> 4;
            case 64:
                return ((rc.readSharedArray(27) & 1) << 2) + ((rc.readSharedArray(28) & 49152) >>> 14);
            case 65:
                return (rc.readSharedArray(28) & 1792) >>> 8;
            case 66:
                return (rc.readSharedArray(28) & 28) >>> 2;
            case 67:
                return (rc.readSharedArray(29) & 28672) >>> 12;
            case 68:
                return (rc.readSharedArray(29) & 448) >>> 6;
            case 69:
                return (rc.readSharedArray(29) & 7);
            case 70:
                return (rc.readSharedArray(30) & 7168) >>> 10;
            case 71:
                return (rc.readSharedArray(30) & 112) >>> 4;
            case 72:
                return ((rc.readSharedArray(30) & 1) << 2) + ((rc.readSharedArray(31) & 49152) >>> 14);
            case 73:
                return (rc.readSharedArray(31) & 1792) >>> 8;
            case 74:
                return (rc.readSharedArray(31) & 28) >>> 2;
            case 75:
                return (rc.readSharedArray(32) & 28672) >>> 12;
            case 76:
                return (rc.readSharedArray(32) & 448) >>> 6;
            case 77:
                return (rc.readSharedArray(32) & 7);
            case 78:
                return (rc.readSharedArray(33) & 7168) >>> 10;
            case 79:
                return (rc.readSharedArray(33) & 112) >>> 4;
            case 80:
                return ((rc.readSharedArray(33) & 1) << 2) + ((rc.readSharedArray(34) & 49152) >>> 14);
            case 81:
                return (rc.readSharedArray(34) & 1792) >>> 8;
            case 82:
                return (rc.readSharedArray(34) & 28) >>> 2;
            case 83:
                return (rc.readSharedArray(35) & 28672) >>> 12;
            case 84:
                return (rc.readSharedArray(35) & 448) >>> 6;
            case 85:
                return (rc.readSharedArray(35) & 7);
            case 86:
                return (rc.readSharedArray(36) & 7168) >>> 10;
            case 87:
                return (rc.readSharedArray(36) & 112) >>> 4;
            case 88:
                return ((rc.readSharedArray(36) & 1) << 2) + ((rc.readSharedArray(37) & 49152) >>> 14);
            case 89:
                return (rc.readSharedArray(37) & 1792) >>> 8;
            case 90:
                return (rc.readSharedArray(37) & 28) >>> 2;
            case 91:
                return (rc.readSharedArray(38) & 28672) >>> 12;
            case 92:
                return (rc.readSharedArray(38) & 448) >>> 6;
            case 93:
                return (rc.readSharedArray(38) & 7);
            case 94:
                return (rc.readSharedArray(39) & 7168) >>> 10;
            case 95:
                return (rc.readSharedArray(39) & 112) >>> 4;
            case 96:
                return ((rc.readSharedArray(39) & 1) << 2) + ((rc.readSharedArray(40) & 49152) >>> 14);
            case 97:
                return (rc.readSharedArray(40) & 1792) >>> 8;
            case 98:
                return (rc.readSharedArray(40) & 28) >>> 2;
            case 99:
                return (rc.readSharedArray(41) & 28672) >>> 12;
            default:
                return -1;
        }
    }

    public static void writeSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 16383) | ((value & 3) << 14));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 63743) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65507) | (value << 2));
                break;
            case 3:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 36863) | (value << 12));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65087) | (value << 6));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65528) | (value));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 58367) | (value << 10));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65423) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 16383) | ((value & 3) << 14));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 63743) | (value << 8));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65507) | (value << 2));
                break;
            case 11:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 36863) | (value << 12));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65087) | (value << 6));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65528) | (value));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 58367) | (value << 10));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65423) | (value << 4));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 16383) | ((value & 3) << 14));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 63743) | (value << 8));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65507) | (value << 2));
                break;
            case 19:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 36863) | (value << 12));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65087) | (value << 6));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65528) | (value));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 58367) | (value << 10));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65423) | (value << 4));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 16383) | ((value & 3) << 14));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 63743) | (value << 8));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65507) | (value << 2));
                break;
            case 27:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 36863) | (value << 12));
                break;
            case 28:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65087) | (value << 6));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65528) | (value));
                break;
            case 30:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 58367) | (value << 10));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65423) | (value << 4));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 16383) | ((value & 3) << 14));
                break;
            case 33:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 63743) | (value << 8));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65507) | (value << 2));
                break;
            case 35:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 36863) | (value << 12));
                break;
            case 36:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65087) | (value << 6));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65528) | (value));
                break;
            case 38:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 58367) | (value << 10));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65423) | (value << 4));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 16383) | ((value & 3) << 14));
                break;
            case 41:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 63743) | (value << 8));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65507) | (value << 2));
                break;
            case 43:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 36863) | (value << 12));
                break;
            case 44:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65087) | (value << 6));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65528) | (value));
                break;
            case 46:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 58367) | (value << 10));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65423) | (value << 4));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 16383) | ((value & 3) << 14));
                break;
            case 49:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 63743) | (value << 8));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65507) | (value << 2));
                break;
            case 51:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 36863) | (value << 12));
                break;
            case 52:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65087) | (value << 6));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65528) | (value));
                break;
            case 54:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 58367) | (value << 10));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65423) | (value << 4));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 16383) | ((value & 3) << 14));
                break;
            case 57:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 63743) | (value << 8));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65507) | (value << 2));
                break;
            case 59:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 36863) | (value << 12));
                break;
            case 60:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65087) | (value << 6));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65528) | (value));
                break;
            case 62:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 58367) | (value << 10));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65423) | (value << 4));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 16383) | ((value & 3) << 14));
                break;
            case 65:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 63743) | (value << 8));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65507) | (value << 2));
                break;
            case 67:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 36863) | (value << 12));
                break;
            case 68:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65087) | (value << 6));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65528) | (value));
                break;
            case 70:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 58367) | (value << 10));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65423) | (value << 4));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 16383) | ((value & 3) << 14));
                break;
            case 73:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 63743) | (value << 8));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65507) | (value << 2));
                break;
            case 75:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 36863) | (value << 12));
                break;
            case 76:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65087) | (value << 6));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65528) | (value));
                break;
            case 78:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 58367) | (value << 10));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65423) | (value << 4));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 16383) | ((value & 3) << 14));
                break;
            case 81:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 63743) | (value << 8));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65507) | (value << 2));
                break;
            case 83:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 36863) | (value << 12));
                break;
            case 84:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65087) | (value << 6));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65528) | (value));
                break;
            case 86:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 58367) | (value << 10));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65423) | (value << 4));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 16383) | ((value & 3) << 14));
                break;
            case 89:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 63743) | (value << 8));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65507) | (value << 2));
                break;
            case 91:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 36863) | (value << 12));
                break;
            case 92:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65087) | (value << 6));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65528) | (value));
                break;
            case 94:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 58367) | (value << 10));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65423) | (value << 4));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65534) | ((value & 4) >>> 2));
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 16383) | ((value & 3) << 14));
                break;
            case 97:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 63743) | (value << 8));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65507) | (value << 2));
                break;
            case 99:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 36863) | (value << 12));
                break;
        }
    }

    public static void writeBPSectorControlStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(4, (bufferPool[4] & 16383) | ((value & 3) << 14));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 63743) | (value << 8));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65507) | (value << 2));
                break;
            case 3:
                writeToBufferPool(5, (bufferPool[5] & 36863) | (value << 12));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 65087) | (value << 6));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65528) | (value));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 58367) | (value << 10));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 65423) | (value << 4));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(7, (bufferPool[7] & 16383) | ((value & 3) << 14));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 63743) | (value << 8));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65507) | (value << 2));
                break;
            case 11:
                writeToBufferPool(8, (bufferPool[8] & 36863) | (value << 12));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 65087) | (value << 6));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65528) | (value));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 58367) | (value << 10));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 65423) | (value << 4));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(10, (bufferPool[10] & 16383) | ((value & 3) << 14));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 63743) | (value << 8));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65507) | (value << 2));
                break;
            case 19:
                writeToBufferPool(11, (bufferPool[11] & 36863) | (value << 12));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 65087) | (value << 6));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 65528) | (value));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 58367) | (value << 10));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 65423) | (value << 4));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(13, (bufferPool[13] & 16383) | ((value & 3) << 14));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 63743) | (value << 8));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 65507) | (value << 2));
                break;
            case 27:
                writeToBufferPool(14, (bufferPool[14] & 36863) | (value << 12));
                break;
            case 28:
                writeToBufferPool(14, (bufferPool[14] & 65087) | (value << 6));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 65528) | (value));
                break;
            case 30:
                writeToBufferPool(15, (bufferPool[15] & 58367) | (value << 10));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 65423) | (value << 4));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(16, (bufferPool[16] & 16383) | ((value & 3) << 14));
                break;
            case 33:
                writeToBufferPool(16, (bufferPool[16] & 63743) | (value << 8));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 65507) | (value << 2));
                break;
            case 35:
                writeToBufferPool(17, (bufferPool[17] & 36863) | (value << 12));
                break;
            case 36:
                writeToBufferPool(17, (bufferPool[17] & 65087) | (value << 6));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 65528) | (value));
                break;
            case 38:
                writeToBufferPool(18, (bufferPool[18] & 58367) | (value << 10));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 65423) | (value << 4));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(19, (bufferPool[19] & 16383) | ((value & 3) << 14));
                break;
            case 41:
                writeToBufferPool(19, (bufferPool[19] & 63743) | (value << 8));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 65507) | (value << 2));
                break;
            case 43:
                writeToBufferPool(20, (bufferPool[20] & 36863) | (value << 12));
                break;
            case 44:
                writeToBufferPool(20, (bufferPool[20] & 65087) | (value << 6));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 65528) | (value));
                break;
            case 46:
                writeToBufferPool(21, (bufferPool[21] & 58367) | (value << 10));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 65423) | (value << 4));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(22, (bufferPool[22] & 16383) | ((value & 3) << 14));
                break;
            case 49:
                writeToBufferPool(22, (bufferPool[22] & 63743) | (value << 8));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 65507) | (value << 2));
                break;
            case 51:
                writeToBufferPool(23, (bufferPool[23] & 36863) | (value << 12));
                break;
            case 52:
                writeToBufferPool(23, (bufferPool[23] & 65087) | (value << 6));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 65528) | (value));
                break;
            case 54:
                writeToBufferPool(24, (bufferPool[24] & 58367) | (value << 10));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 65423) | (value << 4));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(25, (bufferPool[25] & 16383) | ((value & 3) << 14));
                break;
            case 57:
                writeToBufferPool(25, (bufferPool[25] & 63743) | (value << 8));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 65507) | (value << 2));
                break;
            case 59:
                writeToBufferPool(26, (bufferPool[26] & 36863) | (value << 12));
                break;
            case 60:
                writeToBufferPool(26, (bufferPool[26] & 65087) | (value << 6));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 65528) | (value));
                break;
            case 62:
                writeToBufferPool(27, (bufferPool[27] & 58367) | (value << 10));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 65423) | (value << 4));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(28, (bufferPool[28] & 16383) | ((value & 3) << 14));
                break;
            case 65:
                writeToBufferPool(28, (bufferPool[28] & 63743) | (value << 8));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 65507) | (value << 2));
                break;
            case 67:
                writeToBufferPool(29, (bufferPool[29] & 36863) | (value << 12));
                break;
            case 68:
                writeToBufferPool(29, (bufferPool[29] & 65087) | (value << 6));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 65528) | (value));
                break;
            case 70:
                writeToBufferPool(30, (bufferPool[30] & 58367) | (value << 10));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 65423) | (value << 4));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(31, (bufferPool[31] & 16383) | ((value & 3) << 14));
                break;
            case 73:
                writeToBufferPool(31, (bufferPool[31] & 63743) | (value << 8));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 65507) | (value << 2));
                break;
            case 75:
                writeToBufferPool(32, (bufferPool[32] & 36863) | (value << 12));
                break;
            case 76:
                writeToBufferPool(32, (bufferPool[32] & 65087) | (value << 6));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 65528) | (value));
                break;
            case 78:
                writeToBufferPool(33, (bufferPool[33] & 58367) | (value << 10));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 65423) | (value << 4));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(34, (bufferPool[34] & 16383) | ((value & 3) << 14));
                break;
            case 81:
                writeToBufferPool(34, (bufferPool[34] & 63743) | (value << 8));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 65507) | (value << 2));
                break;
            case 83:
                writeToBufferPool(35, (bufferPool[35] & 36863) | (value << 12));
                break;
            case 84:
                writeToBufferPool(35, (bufferPool[35] & 65087) | (value << 6));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 65528) | (value));
                break;
            case 86:
                writeToBufferPool(36, (bufferPool[36] & 58367) | (value << 10));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 65423) | (value << 4));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(37, (bufferPool[37] & 16383) | ((value & 3) << 14));
                break;
            case 89:
                writeToBufferPool(37, (bufferPool[37] & 63743) | (value << 8));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 65507) | (value << 2));
                break;
            case 91:
                writeToBufferPool(38, (bufferPool[38] & 36863) | (value << 12));
                break;
            case 92:
                writeToBufferPool(38, (bufferPool[38] & 65087) | (value << 6));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 65528) | (value));
                break;
            case 94:
                writeToBufferPool(39, (bufferPool[39] & 58367) | (value << 10));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 65423) | (value << 4));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65534) | ((value & 4) >>> 2));
                writeToBufferPool(40, (bufferPool[40] & 16383) | ((value & 3) << 14));
                break;
            case 97:
                writeToBufferPool(40, (bufferPool[40] & 63743) | (value << 8));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 65507) | (value << 2));
                break;
            case 99:
                writeToBufferPool(41, (bufferPool[41] & 36863) | (value << 12));
                break;
        }
    }

    public static int readSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(3) & 15) << 2) + ((rc.readSharedArray(4) & 49152) >>> 14);
            case 1:
                return (rc.readSharedArray(4) & 16128) >>> 8;
            case 2:
                return (rc.readSharedArray(4) & 252) >>> 2;
            case 3:
                return ((rc.readSharedArray(4) & 3) << 4) + ((rc.readSharedArray(5) & 61440) >>> 12);
            case 4:
                return (rc.readSharedArray(5) & 4032) >>> 6;
            case 5:
                return (rc.readSharedArray(5) & 63);
            case 6:
                return (rc.readSharedArray(6) & 64512) >>> 10;
            case 7:
                return (rc.readSharedArray(6) & 1008) >>> 4;
            case 8:
                return ((rc.readSharedArray(6) & 15) << 2) + ((rc.readSharedArray(7) & 49152) >>> 14);
            case 9:
                return (rc.readSharedArray(7) & 16128) >>> 8;
            case 10:
                return (rc.readSharedArray(7) & 252) >>> 2;
            case 11:
                return ((rc.readSharedArray(7) & 3) << 4) + ((rc.readSharedArray(8) & 61440) >>> 12);
            case 12:
                return (rc.readSharedArray(8) & 4032) >>> 6;
            case 13:
                return (rc.readSharedArray(8) & 63);
            case 14:
                return (rc.readSharedArray(9) & 64512) >>> 10;
            case 15:
                return (rc.readSharedArray(9) & 1008) >>> 4;
            case 16:
                return ((rc.readSharedArray(9) & 15) << 2) + ((rc.readSharedArray(10) & 49152) >>> 14);
            case 17:
                return (rc.readSharedArray(10) & 16128) >>> 8;
            case 18:
                return (rc.readSharedArray(10) & 252) >>> 2;
            case 19:
                return ((rc.readSharedArray(10) & 3) << 4) + ((rc.readSharedArray(11) & 61440) >>> 12);
            case 20:
                return (rc.readSharedArray(11) & 4032) >>> 6;
            case 21:
                return (rc.readSharedArray(11) & 63);
            case 22:
                return (rc.readSharedArray(12) & 64512) >>> 10;
            case 23:
                return (rc.readSharedArray(12) & 1008) >>> 4;
            case 24:
                return ((rc.readSharedArray(12) & 15) << 2) + ((rc.readSharedArray(13) & 49152) >>> 14);
            case 25:
                return (rc.readSharedArray(13) & 16128) >>> 8;
            case 26:
                return (rc.readSharedArray(13) & 252) >>> 2;
            case 27:
                return ((rc.readSharedArray(13) & 3) << 4) + ((rc.readSharedArray(14) & 61440) >>> 12);
            case 28:
                return (rc.readSharedArray(14) & 4032) >>> 6;
            case 29:
                return (rc.readSharedArray(14) & 63);
            case 30:
                return (rc.readSharedArray(15) & 64512) >>> 10;
            case 31:
                return (rc.readSharedArray(15) & 1008) >>> 4;
            case 32:
                return ((rc.readSharedArray(15) & 15) << 2) + ((rc.readSharedArray(16) & 49152) >>> 14);
            case 33:
                return (rc.readSharedArray(16) & 16128) >>> 8;
            case 34:
                return (rc.readSharedArray(16) & 252) >>> 2;
            case 35:
                return ((rc.readSharedArray(16) & 3) << 4) + ((rc.readSharedArray(17) & 61440) >>> 12);
            case 36:
                return (rc.readSharedArray(17) & 4032) >>> 6;
            case 37:
                return (rc.readSharedArray(17) & 63);
            case 38:
                return (rc.readSharedArray(18) & 64512) >>> 10;
            case 39:
                return (rc.readSharedArray(18) & 1008) >>> 4;
            case 40:
                return ((rc.readSharedArray(18) & 15) << 2) + ((rc.readSharedArray(19) & 49152) >>> 14);
            case 41:
                return (rc.readSharedArray(19) & 16128) >>> 8;
            case 42:
                return (rc.readSharedArray(19) & 252) >>> 2;
            case 43:
                return ((rc.readSharedArray(19) & 3) << 4) + ((rc.readSharedArray(20) & 61440) >>> 12);
            case 44:
                return (rc.readSharedArray(20) & 4032) >>> 6;
            case 45:
                return (rc.readSharedArray(20) & 63);
            case 46:
                return (rc.readSharedArray(21) & 64512) >>> 10;
            case 47:
                return (rc.readSharedArray(21) & 1008) >>> 4;
            case 48:
                return ((rc.readSharedArray(21) & 15) << 2) + ((rc.readSharedArray(22) & 49152) >>> 14);
            case 49:
                return (rc.readSharedArray(22) & 16128) >>> 8;
            case 50:
                return (rc.readSharedArray(22) & 252) >>> 2;
            case 51:
                return ((rc.readSharedArray(22) & 3) << 4) + ((rc.readSharedArray(23) & 61440) >>> 12);
            case 52:
                return (rc.readSharedArray(23) & 4032) >>> 6;
            case 53:
                return (rc.readSharedArray(23) & 63);
            case 54:
                return (rc.readSharedArray(24) & 64512) >>> 10;
            case 55:
                return (rc.readSharedArray(24) & 1008) >>> 4;
            case 56:
                return ((rc.readSharedArray(24) & 15) << 2) + ((rc.readSharedArray(25) & 49152) >>> 14);
            case 57:
                return (rc.readSharedArray(25) & 16128) >>> 8;
            case 58:
                return (rc.readSharedArray(25) & 252) >>> 2;
            case 59:
                return ((rc.readSharedArray(25) & 3) << 4) + ((rc.readSharedArray(26) & 61440) >>> 12);
            case 60:
                return (rc.readSharedArray(26) & 4032) >>> 6;
            case 61:
                return (rc.readSharedArray(26) & 63);
            case 62:
                return (rc.readSharedArray(27) & 64512) >>> 10;
            case 63:
                return (rc.readSharedArray(27) & 1008) >>> 4;
            case 64:
                return ((rc.readSharedArray(27) & 15) << 2) + ((rc.readSharedArray(28) & 49152) >>> 14);
            case 65:
                return (rc.readSharedArray(28) & 16128) >>> 8;
            case 66:
                return (rc.readSharedArray(28) & 252) >>> 2;
            case 67:
                return ((rc.readSharedArray(28) & 3) << 4) + ((rc.readSharedArray(29) & 61440) >>> 12);
            case 68:
                return (rc.readSharedArray(29) & 4032) >>> 6;
            case 69:
                return (rc.readSharedArray(29) & 63);
            case 70:
                return (rc.readSharedArray(30) & 64512) >>> 10;
            case 71:
                return (rc.readSharedArray(30) & 1008) >>> 4;
            case 72:
                return ((rc.readSharedArray(30) & 15) << 2) + ((rc.readSharedArray(31) & 49152) >>> 14);
            case 73:
                return (rc.readSharedArray(31) & 16128) >>> 8;
            case 74:
                return (rc.readSharedArray(31) & 252) >>> 2;
            case 75:
                return ((rc.readSharedArray(31) & 3) << 4) + ((rc.readSharedArray(32) & 61440) >>> 12);
            case 76:
                return (rc.readSharedArray(32) & 4032) >>> 6;
            case 77:
                return (rc.readSharedArray(32) & 63);
            case 78:
                return (rc.readSharedArray(33) & 64512) >>> 10;
            case 79:
                return (rc.readSharedArray(33) & 1008) >>> 4;
            case 80:
                return ((rc.readSharedArray(33) & 15) << 2) + ((rc.readSharedArray(34) & 49152) >>> 14);
            case 81:
                return (rc.readSharedArray(34) & 16128) >>> 8;
            case 82:
                return (rc.readSharedArray(34) & 252) >>> 2;
            case 83:
                return ((rc.readSharedArray(34) & 3) << 4) + ((rc.readSharedArray(35) & 61440) >>> 12);
            case 84:
                return (rc.readSharedArray(35) & 4032) >>> 6;
            case 85:
                return (rc.readSharedArray(35) & 63);
            case 86:
                return (rc.readSharedArray(36) & 64512) >>> 10;
            case 87:
                return (rc.readSharedArray(36) & 1008) >>> 4;
            case 88:
                return ((rc.readSharedArray(36) & 15) << 2) + ((rc.readSharedArray(37) & 49152) >>> 14);
            case 89:
                return (rc.readSharedArray(37) & 16128) >>> 8;
            case 90:
                return (rc.readSharedArray(37) & 252) >>> 2;
            case 91:
                return ((rc.readSharedArray(37) & 3) << 4) + ((rc.readSharedArray(38) & 61440) >>> 12);
            case 92:
                return (rc.readSharedArray(38) & 4032) >>> 6;
            case 93:
                return (rc.readSharedArray(38) & 63);
            case 94:
                return (rc.readSharedArray(39) & 64512) >>> 10;
            case 95:
                return (rc.readSharedArray(39) & 1008) >>> 4;
            case 96:
                return ((rc.readSharedArray(39) & 15) << 2) + ((rc.readSharedArray(40) & 49152) >>> 14);
            case 97:
                return (rc.readSharedArray(40) & 16128) >>> 8;
            case 98:
                return (rc.readSharedArray(40) & 252) >>> 2;
            case 99:
                return ((rc.readSharedArray(40) & 3) << 4) + ((rc.readSharedArray(41) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(3, (rc.readSharedArray(3) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 16383) | ((value & 3) << 14));
                break;
            case 1:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 49407) | (value << 8));
                break;
            case 2:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65283) | (value << 2));
                break;
            case 3:
                rc.writeSharedArray(4, (rc.readSharedArray(4) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 4095) | ((value & 15) << 12));
                break;
            case 4:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 61503) | (value << 6));
                break;
            case 5:
                rc.writeSharedArray(5, (rc.readSharedArray(5) & 65472) | (value));
                break;
            case 6:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 1023) | (value << 10));
                break;
            case 7:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 64527) | (value << 4));
                break;
            case 8:
                rc.writeSharedArray(6, (rc.readSharedArray(6) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 16383) | ((value & 3) << 14));
                break;
            case 9:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 49407) | (value << 8));
                break;
            case 10:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65283) | (value << 2));
                break;
            case 11:
                rc.writeSharedArray(7, (rc.readSharedArray(7) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 4095) | ((value & 15) << 12));
                break;
            case 12:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 61503) | (value << 6));
                break;
            case 13:
                rc.writeSharedArray(8, (rc.readSharedArray(8) & 65472) | (value));
                break;
            case 14:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 1023) | (value << 10));
                break;
            case 15:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 64527) | (value << 4));
                break;
            case 16:
                rc.writeSharedArray(9, (rc.readSharedArray(9) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 16383) | ((value & 3) << 14));
                break;
            case 17:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 49407) | (value << 8));
                break;
            case 18:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65283) | (value << 2));
                break;
            case 19:
                rc.writeSharedArray(10, (rc.readSharedArray(10) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 4095) | ((value & 15) << 12));
                break;
            case 20:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 61503) | (value << 6));
                break;
            case 21:
                rc.writeSharedArray(11, (rc.readSharedArray(11) & 65472) | (value));
                break;
            case 22:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 1023) | (value << 10));
                break;
            case 23:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 64527) | (value << 4));
                break;
            case 24:
                rc.writeSharedArray(12, (rc.readSharedArray(12) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 16383) | ((value & 3) << 14));
                break;
            case 25:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 49407) | (value << 8));
                break;
            case 26:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65283) | (value << 2));
                break;
            case 27:
                rc.writeSharedArray(13, (rc.readSharedArray(13) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 4095) | ((value & 15) << 12));
                break;
            case 28:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 61503) | (value << 6));
                break;
            case 29:
                rc.writeSharedArray(14, (rc.readSharedArray(14) & 65472) | (value));
                break;
            case 30:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 1023) | (value << 10));
                break;
            case 31:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 64527) | (value << 4));
                break;
            case 32:
                rc.writeSharedArray(15, (rc.readSharedArray(15) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 16383) | ((value & 3) << 14));
                break;
            case 33:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 49407) | (value << 8));
                break;
            case 34:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65283) | (value << 2));
                break;
            case 35:
                rc.writeSharedArray(16, (rc.readSharedArray(16) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 4095) | ((value & 15) << 12));
                break;
            case 36:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 61503) | (value << 6));
                break;
            case 37:
                rc.writeSharedArray(17, (rc.readSharedArray(17) & 65472) | (value));
                break;
            case 38:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 1023) | (value << 10));
                break;
            case 39:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 64527) | (value << 4));
                break;
            case 40:
                rc.writeSharedArray(18, (rc.readSharedArray(18) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 16383) | ((value & 3) << 14));
                break;
            case 41:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 49407) | (value << 8));
                break;
            case 42:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65283) | (value << 2));
                break;
            case 43:
                rc.writeSharedArray(19, (rc.readSharedArray(19) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 4095) | ((value & 15) << 12));
                break;
            case 44:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 61503) | (value << 6));
                break;
            case 45:
                rc.writeSharedArray(20, (rc.readSharedArray(20) & 65472) | (value));
                break;
            case 46:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 1023) | (value << 10));
                break;
            case 47:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 64527) | (value << 4));
                break;
            case 48:
                rc.writeSharedArray(21, (rc.readSharedArray(21) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 16383) | ((value & 3) << 14));
                break;
            case 49:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 49407) | (value << 8));
                break;
            case 50:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65283) | (value << 2));
                break;
            case 51:
                rc.writeSharedArray(22, (rc.readSharedArray(22) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 4095) | ((value & 15) << 12));
                break;
            case 52:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 61503) | (value << 6));
                break;
            case 53:
                rc.writeSharedArray(23, (rc.readSharedArray(23) & 65472) | (value));
                break;
            case 54:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 1023) | (value << 10));
                break;
            case 55:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 64527) | (value << 4));
                break;
            case 56:
                rc.writeSharedArray(24, (rc.readSharedArray(24) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 16383) | ((value & 3) << 14));
                break;
            case 57:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 49407) | (value << 8));
                break;
            case 58:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65283) | (value << 2));
                break;
            case 59:
                rc.writeSharedArray(25, (rc.readSharedArray(25) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 4095) | ((value & 15) << 12));
                break;
            case 60:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 61503) | (value << 6));
                break;
            case 61:
                rc.writeSharedArray(26, (rc.readSharedArray(26) & 65472) | (value));
                break;
            case 62:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 1023) | (value << 10));
                break;
            case 63:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 64527) | (value << 4));
                break;
            case 64:
                rc.writeSharedArray(27, (rc.readSharedArray(27) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 16383) | ((value & 3) << 14));
                break;
            case 65:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 49407) | (value << 8));
                break;
            case 66:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65283) | (value << 2));
                break;
            case 67:
                rc.writeSharedArray(28, (rc.readSharedArray(28) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 4095) | ((value & 15) << 12));
                break;
            case 68:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 61503) | (value << 6));
                break;
            case 69:
                rc.writeSharedArray(29, (rc.readSharedArray(29) & 65472) | (value));
                break;
            case 70:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 1023) | (value << 10));
                break;
            case 71:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 64527) | (value << 4));
                break;
            case 72:
                rc.writeSharedArray(30, (rc.readSharedArray(30) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 16383) | ((value & 3) << 14));
                break;
            case 73:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 49407) | (value << 8));
                break;
            case 74:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65283) | (value << 2));
                break;
            case 75:
                rc.writeSharedArray(31, (rc.readSharedArray(31) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 4095) | ((value & 15) << 12));
                break;
            case 76:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 61503) | (value << 6));
                break;
            case 77:
                rc.writeSharedArray(32, (rc.readSharedArray(32) & 65472) | (value));
                break;
            case 78:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 1023) | (value << 10));
                break;
            case 79:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 64527) | (value << 4));
                break;
            case 80:
                rc.writeSharedArray(33, (rc.readSharedArray(33) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 16383) | ((value & 3) << 14));
                break;
            case 81:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 49407) | (value << 8));
                break;
            case 82:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65283) | (value << 2));
                break;
            case 83:
                rc.writeSharedArray(34, (rc.readSharedArray(34) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 4095) | ((value & 15) << 12));
                break;
            case 84:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 61503) | (value << 6));
                break;
            case 85:
                rc.writeSharedArray(35, (rc.readSharedArray(35) & 65472) | (value));
                break;
            case 86:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 1023) | (value << 10));
                break;
            case 87:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 64527) | (value << 4));
                break;
            case 88:
                rc.writeSharedArray(36, (rc.readSharedArray(36) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 16383) | ((value & 3) << 14));
                break;
            case 89:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 49407) | (value << 8));
                break;
            case 90:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65283) | (value << 2));
                break;
            case 91:
                rc.writeSharedArray(37, (rc.readSharedArray(37) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 4095) | ((value & 15) << 12));
                break;
            case 92:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 61503) | (value << 6));
                break;
            case 93:
                rc.writeSharedArray(38, (rc.readSharedArray(38) & 65472) | (value));
                break;
            case 94:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 1023) | (value << 10));
                break;
            case 95:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 64527) | (value << 4));
                break;
            case 96:
                rc.writeSharedArray(39, (rc.readSharedArray(39) & 65520) | ((value & 60) >>> 2));
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 16383) | ((value & 3) << 14));
                break;
            case 97:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 49407) | (value << 8));
                break;
            case 98:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65283) | (value << 2));
                break;
            case 99:
                rc.writeSharedArray(40, (rc.readSharedArray(40) & 65532) | ((value & 48) >>> 4));
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(3, (bufferPool[3] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(4, (bufferPool[4] & 16383) | ((value & 3) << 14));
                break;
            case 1:
                writeToBufferPool(4, (bufferPool[4] & 49407) | (value << 8));
                break;
            case 2:
                writeToBufferPool(4, (bufferPool[4] & 65283) | (value << 2));
                break;
            case 3:
                writeToBufferPool(4, (bufferPool[4] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(5, (bufferPool[5] & 4095) | ((value & 15) << 12));
                break;
            case 4:
                writeToBufferPool(5, (bufferPool[5] & 61503) | (value << 6));
                break;
            case 5:
                writeToBufferPool(5, (bufferPool[5] & 65472) | (value));
                break;
            case 6:
                writeToBufferPool(6, (bufferPool[6] & 1023) | (value << 10));
                break;
            case 7:
                writeToBufferPool(6, (bufferPool[6] & 64527) | (value << 4));
                break;
            case 8:
                writeToBufferPool(6, (bufferPool[6] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(7, (bufferPool[7] & 16383) | ((value & 3) << 14));
                break;
            case 9:
                writeToBufferPool(7, (bufferPool[7] & 49407) | (value << 8));
                break;
            case 10:
                writeToBufferPool(7, (bufferPool[7] & 65283) | (value << 2));
                break;
            case 11:
                writeToBufferPool(7, (bufferPool[7] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(8, (bufferPool[8] & 4095) | ((value & 15) << 12));
                break;
            case 12:
                writeToBufferPool(8, (bufferPool[8] & 61503) | (value << 6));
                break;
            case 13:
                writeToBufferPool(8, (bufferPool[8] & 65472) | (value));
                break;
            case 14:
                writeToBufferPool(9, (bufferPool[9] & 1023) | (value << 10));
                break;
            case 15:
                writeToBufferPool(9, (bufferPool[9] & 64527) | (value << 4));
                break;
            case 16:
                writeToBufferPool(9, (bufferPool[9] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(10, (bufferPool[10] & 16383) | ((value & 3) << 14));
                break;
            case 17:
                writeToBufferPool(10, (bufferPool[10] & 49407) | (value << 8));
                break;
            case 18:
                writeToBufferPool(10, (bufferPool[10] & 65283) | (value << 2));
                break;
            case 19:
                writeToBufferPool(10, (bufferPool[10] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(11, (bufferPool[11] & 4095) | ((value & 15) << 12));
                break;
            case 20:
                writeToBufferPool(11, (bufferPool[11] & 61503) | (value << 6));
                break;
            case 21:
                writeToBufferPool(11, (bufferPool[11] & 65472) | (value));
                break;
            case 22:
                writeToBufferPool(12, (bufferPool[12] & 1023) | (value << 10));
                break;
            case 23:
                writeToBufferPool(12, (bufferPool[12] & 64527) | (value << 4));
                break;
            case 24:
                writeToBufferPool(12, (bufferPool[12] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(13, (bufferPool[13] & 16383) | ((value & 3) << 14));
                break;
            case 25:
                writeToBufferPool(13, (bufferPool[13] & 49407) | (value << 8));
                break;
            case 26:
                writeToBufferPool(13, (bufferPool[13] & 65283) | (value << 2));
                break;
            case 27:
                writeToBufferPool(13, (bufferPool[13] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(14, (bufferPool[14] & 4095) | ((value & 15) << 12));
                break;
            case 28:
                writeToBufferPool(14, (bufferPool[14] & 61503) | (value << 6));
                break;
            case 29:
                writeToBufferPool(14, (bufferPool[14] & 65472) | (value));
                break;
            case 30:
                writeToBufferPool(15, (bufferPool[15] & 1023) | (value << 10));
                break;
            case 31:
                writeToBufferPool(15, (bufferPool[15] & 64527) | (value << 4));
                break;
            case 32:
                writeToBufferPool(15, (bufferPool[15] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(16, (bufferPool[16] & 16383) | ((value & 3) << 14));
                break;
            case 33:
                writeToBufferPool(16, (bufferPool[16] & 49407) | (value << 8));
                break;
            case 34:
                writeToBufferPool(16, (bufferPool[16] & 65283) | (value << 2));
                break;
            case 35:
                writeToBufferPool(16, (bufferPool[16] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(17, (bufferPool[17] & 4095) | ((value & 15) << 12));
                break;
            case 36:
                writeToBufferPool(17, (bufferPool[17] & 61503) | (value << 6));
                break;
            case 37:
                writeToBufferPool(17, (bufferPool[17] & 65472) | (value));
                break;
            case 38:
                writeToBufferPool(18, (bufferPool[18] & 1023) | (value << 10));
                break;
            case 39:
                writeToBufferPool(18, (bufferPool[18] & 64527) | (value << 4));
                break;
            case 40:
                writeToBufferPool(18, (bufferPool[18] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(19, (bufferPool[19] & 16383) | ((value & 3) << 14));
                break;
            case 41:
                writeToBufferPool(19, (bufferPool[19] & 49407) | (value << 8));
                break;
            case 42:
                writeToBufferPool(19, (bufferPool[19] & 65283) | (value << 2));
                break;
            case 43:
                writeToBufferPool(19, (bufferPool[19] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(20, (bufferPool[20] & 4095) | ((value & 15) << 12));
                break;
            case 44:
                writeToBufferPool(20, (bufferPool[20] & 61503) | (value << 6));
                break;
            case 45:
                writeToBufferPool(20, (bufferPool[20] & 65472) | (value));
                break;
            case 46:
                writeToBufferPool(21, (bufferPool[21] & 1023) | (value << 10));
                break;
            case 47:
                writeToBufferPool(21, (bufferPool[21] & 64527) | (value << 4));
                break;
            case 48:
                writeToBufferPool(21, (bufferPool[21] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(22, (bufferPool[22] & 16383) | ((value & 3) << 14));
                break;
            case 49:
                writeToBufferPool(22, (bufferPool[22] & 49407) | (value << 8));
                break;
            case 50:
                writeToBufferPool(22, (bufferPool[22] & 65283) | (value << 2));
                break;
            case 51:
                writeToBufferPool(22, (bufferPool[22] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(23, (bufferPool[23] & 4095) | ((value & 15) << 12));
                break;
            case 52:
                writeToBufferPool(23, (bufferPool[23] & 61503) | (value << 6));
                break;
            case 53:
                writeToBufferPool(23, (bufferPool[23] & 65472) | (value));
                break;
            case 54:
                writeToBufferPool(24, (bufferPool[24] & 1023) | (value << 10));
                break;
            case 55:
                writeToBufferPool(24, (bufferPool[24] & 64527) | (value << 4));
                break;
            case 56:
                writeToBufferPool(24, (bufferPool[24] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(25, (bufferPool[25] & 16383) | ((value & 3) << 14));
                break;
            case 57:
                writeToBufferPool(25, (bufferPool[25] & 49407) | (value << 8));
                break;
            case 58:
                writeToBufferPool(25, (bufferPool[25] & 65283) | (value << 2));
                break;
            case 59:
                writeToBufferPool(25, (bufferPool[25] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(26, (bufferPool[26] & 4095) | ((value & 15) << 12));
                break;
            case 60:
                writeToBufferPool(26, (bufferPool[26] & 61503) | (value << 6));
                break;
            case 61:
                writeToBufferPool(26, (bufferPool[26] & 65472) | (value));
                break;
            case 62:
                writeToBufferPool(27, (bufferPool[27] & 1023) | (value << 10));
                break;
            case 63:
                writeToBufferPool(27, (bufferPool[27] & 64527) | (value << 4));
                break;
            case 64:
                writeToBufferPool(27, (bufferPool[27] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(28, (bufferPool[28] & 16383) | ((value & 3) << 14));
                break;
            case 65:
                writeToBufferPool(28, (bufferPool[28] & 49407) | (value << 8));
                break;
            case 66:
                writeToBufferPool(28, (bufferPool[28] & 65283) | (value << 2));
                break;
            case 67:
                writeToBufferPool(28, (bufferPool[28] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(29, (bufferPool[29] & 4095) | ((value & 15) << 12));
                break;
            case 68:
                writeToBufferPool(29, (bufferPool[29] & 61503) | (value << 6));
                break;
            case 69:
                writeToBufferPool(29, (bufferPool[29] & 65472) | (value));
                break;
            case 70:
                writeToBufferPool(30, (bufferPool[30] & 1023) | (value << 10));
                break;
            case 71:
                writeToBufferPool(30, (bufferPool[30] & 64527) | (value << 4));
                break;
            case 72:
                writeToBufferPool(30, (bufferPool[30] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(31, (bufferPool[31] & 16383) | ((value & 3) << 14));
                break;
            case 73:
                writeToBufferPool(31, (bufferPool[31] & 49407) | (value << 8));
                break;
            case 74:
                writeToBufferPool(31, (bufferPool[31] & 65283) | (value << 2));
                break;
            case 75:
                writeToBufferPool(31, (bufferPool[31] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(32, (bufferPool[32] & 4095) | ((value & 15) << 12));
                break;
            case 76:
                writeToBufferPool(32, (bufferPool[32] & 61503) | (value << 6));
                break;
            case 77:
                writeToBufferPool(32, (bufferPool[32] & 65472) | (value));
                break;
            case 78:
                writeToBufferPool(33, (bufferPool[33] & 1023) | (value << 10));
                break;
            case 79:
                writeToBufferPool(33, (bufferPool[33] & 64527) | (value << 4));
                break;
            case 80:
                writeToBufferPool(33, (bufferPool[33] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(34, (bufferPool[34] & 16383) | ((value & 3) << 14));
                break;
            case 81:
                writeToBufferPool(34, (bufferPool[34] & 49407) | (value << 8));
                break;
            case 82:
                writeToBufferPool(34, (bufferPool[34] & 65283) | (value << 2));
                break;
            case 83:
                writeToBufferPool(34, (bufferPool[34] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(35, (bufferPool[35] & 4095) | ((value & 15) << 12));
                break;
            case 84:
                writeToBufferPool(35, (bufferPool[35] & 61503) | (value << 6));
                break;
            case 85:
                writeToBufferPool(35, (bufferPool[35] & 65472) | (value));
                break;
            case 86:
                writeToBufferPool(36, (bufferPool[36] & 1023) | (value << 10));
                break;
            case 87:
                writeToBufferPool(36, (bufferPool[36] & 64527) | (value << 4));
                break;
            case 88:
                writeToBufferPool(36, (bufferPool[36] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(37, (bufferPool[37] & 16383) | ((value & 3) << 14));
                break;
            case 89:
                writeToBufferPool(37, (bufferPool[37] & 49407) | (value << 8));
                break;
            case 90:
                writeToBufferPool(37, (bufferPool[37] & 65283) | (value << 2));
                break;
            case 91:
                writeToBufferPool(37, (bufferPool[37] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(38, (bufferPool[38] & 4095) | ((value & 15) << 12));
                break;
            case 92:
                writeToBufferPool(38, (bufferPool[38] & 61503) | (value << 6));
                break;
            case 93:
                writeToBufferPool(38, (bufferPool[38] & 65472) | (value));
                break;
            case 94:
                writeToBufferPool(39, (bufferPool[39] & 1023) | (value << 10));
                break;
            case 95:
                writeToBufferPool(39, (bufferPool[39] & 64527) | (value << 4));
                break;
            case 96:
                writeToBufferPool(39, (bufferPool[39] & 65520) | ((value & 60) >>> 2));
                writeToBufferPool(40, (bufferPool[40] & 16383) | ((value & 3) << 14));
                break;
            case 97:
                writeToBufferPool(40, (bufferPool[40] & 49407) | (value << 8));
                break;
            case 98:
                writeToBufferPool(40, (bufferPool[40] & 65283) | (value << 2));
                break;
            case 99:
                writeToBufferPool(40, (bufferPool[40] & 65532) | ((value & 48) >>> 4));
                writeToBufferPool(41, (bufferPool[41] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readCombatSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(41) & 2048) >>> 11;
            case 1:
                return (rc.readSharedArray(41) & 8) >>> 3;
            case 2:
                return (rc.readSharedArray(42) & 2048) >>> 11;
            case 3:
                return (rc.readSharedArray(42) & 8) >>> 3;
            default:
                return -1;
        }
    }

    public static void writeCombatSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63487) | (value << 11));
                break;
            case 1:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65527) | (value << 3));
                break;
            case 2:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 63487) | (value << 11));
                break;
            case 3:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65527) | (value << 3));
                break;
        }
    }

    public static void writeBPCombatSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(41, (bufferPool[41] & 63487) | (value << 11));
                break;
            case 1:
                writeToBufferPool(41, (bufferPool[41] & 65527) | (value << 3));
                break;
            case 2:
                writeToBufferPool(42, (bufferPool[42] & 63487) | (value << 11));
                break;
            case 3:
                writeToBufferPool(42, (bufferPool[42] & 65527) | (value << 3));
                break;
        }
    }

    public static int readCombatSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(41) & 2032) >>> 4;
            case 1:
                return ((rc.readSharedArray(41) & 7) << 4) + ((rc.readSharedArray(42) & 61440) >>> 12);
            case 2:
                return (rc.readSharedArray(42) & 2032) >>> 4;
            case 3:
                return ((rc.readSharedArray(42) & 7) << 4) + ((rc.readSharedArray(43) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 63503) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 63503) | (value << 4));
                break;
            case 3:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPCombatSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(41, (bufferPool[41] & 63503) | (value << 4));
                break;
            case 1:
                writeToBufferPool(41, (bufferPool[41] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(42, (bufferPool[42] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(42, (bufferPool[42] & 63503) | (value << 4));
                break;
            case 3:
                writeToBufferPool(42, (bufferPool[42] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(43, (bufferPool[43] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readCombatSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(41) & 4080) >>> 4;
            case 1:
                return ((rc.readSharedArray(41) & 15) << 4) + ((rc.readSharedArray(42) & 61440) >>> 12);
            case 2:
                return (rc.readSharedArray(42) & 4080) >>> 4;
            case 3:
                return ((rc.readSharedArray(42) & 15) << 4) + ((rc.readSharedArray(43) & 61440) >>> 12);
            default:
                return -1;
        }
    }

    public static void writeCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 61455) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(41, (rc.readSharedArray(41) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 61455) | (value << 4));
                break;
            case 3:
                rc.writeSharedArray(42, (rc.readSharedArray(42) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static void writeBPCombatSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(41, (bufferPool[41] & 61455) | (value << 4));
                break;
            case 1:
                writeToBufferPool(41, (bufferPool[41] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(42, (bufferPool[42] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(42, (bufferPool[42] & 61455) | (value << 4));
                break;
            case 3:
                writeToBufferPool(42, (bufferPool[42] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(43, (bufferPool[43] & 4095) | ((value & 15) << 12));
                break;
        }
    }

    public static int readExploreSectorClaimStatus(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(43) & 2048) >>> 11;
            case 1:
                return (rc.readSharedArray(43) & 8) >>> 3;
            case 2:
                return (rc.readSharedArray(44) & 2048) >>> 11;
            case 3:
                return (rc.readSharedArray(44) & 8) >>> 3;
            case 4:
                return (rc.readSharedArray(45) & 2048) >>> 11;
            case 5:
                return (rc.readSharedArray(45) & 8) >>> 3;
            case 6:
                return (rc.readSharedArray(46) & 2048) >>> 11;
            case 7:
                return (rc.readSharedArray(46) & 8) >>> 3;
            case 8:
                return (rc.readSharedArray(47) & 2048) >>> 11;
            case 9:
                return (rc.readSharedArray(47) & 8) >>> 3;
            case 10:
                return (rc.readSharedArray(48) & 2048) >>> 11;
            case 11:
                return (rc.readSharedArray(48) & 8) >>> 3;
            case 12:
                return (rc.readSharedArray(49) & 2048) >>> 11;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63487) | (value << 11));
                break;
            case 1:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65527) | (value << 3));
                break;
            case 2:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 63487) | (value << 11));
                break;
            case 3:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65527) | (value << 3));
                break;
            case 4:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 63487) | (value << 11));
                break;
            case 5:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65527) | (value << 3));
                break;
            case 6:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63487) | (value << 11));
                break;
            case 7:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65527) | (value << 3));
                break;
            case 8:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 63487) | (value << 11));
                break;
            case 9:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65527) | (value << 3));
                break;
            case 10:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 63487) | (value << 11));
                break;
            case 11:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65527) | (value << 3));
                break;
            case 12:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 63487) | (value << 11));
                break;
        }
    }

    public static void writeBPExploreSectorClaimStatus(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(43, (bufferPool[43] & 63487) | (value << 11));
                break;
            case 1:
                writeToBufferPool(43, (bufferPool[43] & 65527) | (value << 3));
                break;
            case 2:
                writeToBufferPool(44, (bufferPool[44] & 63487) | (value << 11));
                break;
            case 3:
                writeToBufferPool(44, (bufferPool[44] & 65527) | (value << 3));
                break;
            case 4:
                writeToBufferPool(45, (bufferPool[45] & 63487) | (value << 11));
                break;
            case 5:
                writeToBufferPool(45, (bufferPool[45] & 65527) | (value << 3));
                break;
            case 6:
                writeToBufferPool(46, (bufferPool[46] & 63487) | (value << 11));
                break;
            case 7:
                writeToBufferPool(46, (bufferPool[46] & 65527) | (value << 3));
                break;
            case 8:
                writeToBufferPool(47, (bufferPool[47] & 63487) | (value << 11));
                break;
            case 9:
                writeToBufferPool(47, (bufferPool[47] & 65527) | (value << 3));
                break;
            case 10:
                writeToBufferPool(48, (bufferPool[48] & 63487) | (value << 11));
                break;
            case 11:
                writeToBufferPool(48, (bufferPool[48] & 65527) | (value << 3));
                break;
            case 12:
                writeToBufferPool(49, (bufferPool[49] & 63487) | (value << 11));
                break;
        }
    }

    public static int readExploreSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(43) & 2032) >>> 4;
            case 1:
                return ((rc.readSharedArray(43) & 7) << 4) + ((rc.readSharedArray(44) & 61440) >>> 12);
            case 2:
                return (rc.readSharedArray(44) & 2032) >>> 4;
            case 3:
                return ((rc.readSharedArray(44) & 7) << 4) + ((rc.readSharedArray(45) & 61440) >>> 12);
            case 4:
                return (rc.readSharedArray(45) & 2032) >>> 4;
            case 5:
                return ((rc.readSharedArray(45) & 7) << 4) + ((rc.readSharedArray(46) & 61440) >>> 12);
            case 6:
                return (rc.readSharedArray(46) & 2032) >>> 4;
            case 7:
                return ((rc.readSharedArray(46) & 7) << 4) + ((rc.readSharedArray(47) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(47) & 2032) >>> 4;
            case 9:
                return ((rc.readSharedArray(47) & 7) << 4) + ((rc.readSharedArray(48) & 61440) >>> 12);
            case 10:
                return (rc.readSharedArray(48) & 2032) >>> 4;
            case 11:
                return ((rc.readSharedArray(48) & 7) << 4) + ((rc.readSharedArray(49) & 61440) >>> 12);
            case 12:
                return (rc.readSharedArray(49) & 2032) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 63503) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 63503) | (value << 4));
                break;
            case 3:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 4095) | ((value & 15) << 12));
                break;
            case 4:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 63503) | (value << 4));
                break;
            case 5:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 4095) | ((value & 15) << 12));
                break;
            case 6:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 63503) | (value << 4));
                break;
            case 7:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 63503) | (value << 4));
                break;
            case 9:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 4095) | ((value & 15) << 12));
                break;
            case 10:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 63503) | (value << 4));
                break;
            case 11:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 4095) | ((value & 15) << 12));
                break;
            case 12:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 63503) | (value << 4));
                break;
        }
    }

    public static void writeBPExploreSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(43, (bufferPool[43] & 63503) | (value << 4));
                break;
            case 1:
                writeToBufferPool(43, (bufferPool[43] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(44, (bufferPool[44] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(44, (bufferPool[44] & 63503) | (value << 4));
                break;
            case 3:
                writeToBufferPool(44, (bufferPool[44] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(45, (bufferPool[45] & 4095) | ((value & 15) << 12));
                break;
            case 4:
                writeToBufferPool(45, (bufferPool[45] & 63503) | (value << 4));
                break;
            case 5:
                writeToBufferPool(45, (bufferPool[45] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(46, (bufferPool[46] & 4095) | ((value & 15) << 12));
                break;
            case 6:
                writeToBufferPool(46, (bufferPool[46] & 63503) | (value << 4));
                break;
            case 7:
                writeToBufferPool(46, (bufferPool[46] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(47, (bufferPool[47] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(47, (bufferPool[47] & 63503) | (value << 4));
                break;
            case 9:
                writeToBufferPool(47, (bufferPool[47] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(48, (bufferPool[48] & 4095) | ((value & 15) << 12));
                break;
            case 10:
                writeToBufferPool(48, (bufferPool[48] & 63503) | (value << 4));
                break;
            case 11:
                writeToBufferPool(48, (bufferPool[48] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(49, (bufferPool[49] & 4095) | ((value & 15) << 12));
                break;
            case 12:
                writeToBufferPool(49, (bufferPool[49] & 63503) | (value << 4));
                break;
        }
    }

    public static int readExploreSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return (rc.readSharedArray(43) & 4080) >>> 4;
            case 1:
                return ((rc.readSharedArray(43) & 15) << 4) + ((rc.readSharedArray(44) & 61440) >>> 12);
            case 2:
                return (rc.readSharedArray(44) & 4080) >>> 4;
            case 3:
                return ((rc.readSharedArray(44) & 15) << 4) + ((rc.readSharedArray(45) & 61440) >>> 12);
            case 4:
                return (rc.readSharedArray(45) & 4080) >>> 4;
            case 5:
                return ((rc.readSharedArray(45) & 15) << 4) + ((rc.readSharedArray(46) & 61440) >>> 12);
            case 6:
                return (rc.readSharedArray(46) & 4080) >>> 4;
            case 7:
                return ((rc.readSharedArray(46) & 15) << 4) + ((rc.readSharedArray(47) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(47) & 4080) >>> 4;
            case 9:
                return ((rc.readSharedArray(47) & 15) << 4) + ((rc.readSharedArray(48) & 61440) >>> 12);
            case 10:
                return (rc.readSharedArray(48) & 4080) >>> 4;
            case 11:
                return ((rc.readSharedArray(48) & 15) << 4) + ((rc.readSharedArray(49) & 61440) >>> 12);
            case 12:
                return (rc.readSharedArray(49) & 4080) >>> 4;
            default:
                return -1;
        }
    }

    public static void writeExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 61455) | (value << 4));
                break;
            case 1:
                rc.writeSharedArray(43, (rc.readSharedArray(43) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 4095) | ((value & 15) << 12));
                break;
            case 2:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 61455) | (value << 4));
                break;
            case 3:
                rc.writeSharedArray(44, (rc.readSharedArray(44) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 4095) | ((value & 15) << 12));
                break;
            case 4:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 61455) | (value << 4));
                break;
            case 5:
                rc.writeSharedArray(45, (rc.readSharedArray(45) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 4095) | ((value & 15) << 12));
                break;
            case 6:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 61455) | (value << 4));
                break;
            case 7:
                rc.writeSharedArray(46, (rc.readSharedArray(46) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 61455) | (value << 4));
                break;
            case 9:
                rc.writeSharedArray(47, (rc.readSharedArray(47) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 4095) | ((value & 15) << 12));
                break;
            case 10:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 61455) | (value << 4));
                break;
            case 11:
                rc.writeSharedArray(48, (rc.readSharedArray(48) & 65520) | ((value & 240) >>> 4));
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 4095) | ((value & 15) << 12));
                break;
            case 12:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 61455) | (value << 4));
                break;
        }
    }

    public static void writeBPExploreSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(43, (bufferPool[43] & 61455) | (value << 4));
                break;
            case 1:
                writeToBufferPool(43, (bufferPool[43] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(44, (bufferPool[44] & 4095) | ((value & 15) << 12));
                break;
            case 2:
                writeToBufferPool(44, (bufferPool[44] & 61455) | (value << 4));
                break;
            case 3:
                writeToBufferPool(44, (bufferPool[44] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(45, (bufferPool[45] & 4095) | ((value & 15) << 12));
                break;
            case 4:
                writeToBufferPool(45, (bufferPool[45] & 61455) | (value << 4));
                break;
            case 5:
                writeToBufferPool(45, (bufferPool[45] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(46, (bufferPool[46] & 4095) | ((value & 15) << 12));
                break;
            case 6:
                writeToBufferPool(46, (bufferPool[46] & 61455) | (value << 4));
                break;
            case 7:
                writeToBufferPool(46, (bufferPool[46] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(47, (bufferPool[47] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(47, (bufferPool[47] & 61455) | (value << 4));
                break;
            case 9:
                writeToBufferPool(47, (bufferPool[47] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(48, (bufferPool[48] & 4095) | ((value & 15) << 12));
                break;
            case 10:
                writeToBufferPool(48, (bufferPool[48] & 61455) | (value << 4));
                break;
            case 11:
                writeToBufferPool(48, (bufferPool[48] & 65520) | ((value & 240) >>> 4));
                writeToBufferPool(49, (bufferPool[49] & 4095) | ((value & 15) << 12));
                break;
            case 12:
                writeToBufferPool(49, (bufferPool[49] & 61455) | (value << 4));
                break;
        }
    }

    public static int readMineSectorIndex(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(49) & 15) << 3) + ((rc.readSharedArray(50) & 57344) >>> 13);
            case 1:
                return (rc.readSharedArray(50) & 8128) >>> 6;
            case 2:
                return ((rc.readSharedArray(50) & 63) << 1) + ((rc.readSharedArray(51) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(51) & 32512) >>> 8;
            case 4:
                return (rc.readSharedArray(51) & 254) >>> 1;
            case 5:
                return ((rc.readSharedArray(51) & 1) << 6) + ((rc.readSharedArray(52) & 64512) >>> 10);
            case 6:
                return (rc.readSharedArray(52) & 1016) >>> 3;
            case 7:
                return ((rc.readSharedArray(52) & 7) << 4) + ((rc.readSharedArray(53) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(53) & 4064) >>> 5;
            case 9:
                return ((rc.readSharedArray(53) & 31) << 2) + ((rc.readSharedArray(54) & 49152) >>> 14);
            case 10:
                return (rc.readSharedArray(54) & 16256) >>> 7;
            case 11:
                return (rc.readSharedArray(54) & 127);
            default:
                return -1;
        }
    }

    public static void writeMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 8191) | ((value & 7) << 13));
                break;
            case 1:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 57407) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 33023) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65281) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 1023) | ((value & 63) << 10));
                break;
            case 6:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 64519) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 61471) | (value << 5));
                break;
            case 9:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 16383) | ((value & 3) << 14));
                break;
            case 10:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 49279) | (value << 7));
                break;
            case 11:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65408) | (value));
                break;
        }
    }

    public static void writeBPMineSectorIndex(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(49, (bufferPool[49] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(50, (bufferPool[50] & 8191) | ((value & 7) << 13));
                break;
            case 1:
                writeToBufferPool(50, (bufferPool[50] & 57407) | (value << 6));
                break;
            case 2:
                writeToBufferPool(50, (bufferPool[50] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(51, (bufferPool[51] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(51, (bufferPool[51] & 33023) | (value << 8));
                break;
            case 4:
                writeToBufferPool(51, (bufferPool[51] & 65281) | (value << 1));
                break;
            case 5:
                writeToBufferPool(51, (bufferPool[51] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(52, (bufferPool[52] & 1023) | ((value & 63) << 10));
                break;
            case 6:
                writeToBufferPool(52, (bufferPool[52] & 64519) | (value << 3));
                break;
            case 7:
                writeToBufferPool(52, (bufferPool[52] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(53, (bufferPool[53] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(53, (bufferPool[53] & 61471) | (value << 5));
                break;
            case 9:
                writeToBufferPool(53, (bufferPool[53] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(54, (bufferPool[54] & 16383) | ((value & 3) << 14));
                break;
            case 10:
                writeToBufferPool(54, (bufferPool[54] & 49279) | (value << 7));
                break;
            case 11:
                writeToBufferPool(54, (bufferPool[54] & 65408) | (value));
                break;
        }
    }

    public static int readMineSectorAll(int idx) throws GameActionException {
        switch (idx) {
            case 0:
                return ((rc.readSharedArray(49) & 15) << 3) + ((rc.readSharedArray(50) & 57344) >>> 13);
            case 1:
                return (rc.readSharedArray(50) & 8128) >>> 6;
            case 2:
                return ((rc.readSharedArray(50) & 63) << 1) + ((rc.readSharedArray(51) & 32768) >>> 15);
            case 3:
                return (rc.readSharedArray(51) & 32512) >>> 8;
            case 4:
                return (rc.readSharedArray(51) & 254) >>> 1;
            case 5:
                return ((rc.readSharedArray(51) & 1) << 6) + ((rc.readSharedArray(52) & 64512) >>> 10);
            case 6:
                return (rc.readSharedArray(52) & 1016) >>> 3;
            case 7:
                return ((rc.readSharedArray(52) & 7) << 4) + ((rc.readSharedArray(53) & 61440) >>> 12);
            case 8:
                return (rc.readSharedArray(53) & 4064) >>> 5;
            case 9:
                return ((rc.readSharedArray(53) & 31) << 2) + ((rc.readSharedArray(54) & 49152) >>> 14);
            case 10:
                return (rc.readSharedArray(54) & 16256) >>> 7;
            case 11:
                return (rc.readSharedArray(54) & 127);
            default:
                return -1;
        }
    }

    public static void writeMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                rc.writeSharedArray(49, (rc.readSharedArray(49) & 65520) | ((value & 120) >>> 3));
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 8191) | ((value & 7) << 13));
                break;
            case 1:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 57407) | (value << 6));
                break;
            case 2:
                rc.writeSharedArray(50, (rc.readSharedArray(50) & 65472) | ((value & 126) >>> 1));
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 32767) | ((value & 1) << 15));
                break;
            case 3:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 33023) | (value << 8));
                break;
            case 4:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65281) | (value << 1));
                break;
            case 5:
                rc.writeSharedArray(51, (rc.readSharedArray(51) & 65534) | ((value & 64) >>> 6));
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 1023) | ((value & 63) << 10));
                break;
            case 6:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 64519) | (value << 3));
                break;
            case 7:
                rc.writeSharedArray(52, (rc.readSharedArray(52) & 65528) | ((value & 112) >>> 4));
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 4095) | ((value & 15) << 12));
                break;
            case 8:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 61471) | (value << 5));
                break;
            case 9:
                rc.writeSharedArray(53, (rc.readSharedArray(53) & 65504) | ((value & 124) >>> 2));
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 16383) | ((value & 3) << 14));
                break;
            case 10:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 49279) | (value << 7));
                break;
            case 11:
                rc.writeSharedArray(54, (rc.readSharedArray(54) & 65408) | (value));
                break;
        }
    }

    public static void writeBPMineSectorAll(int idx, int value) throws GameActionException {
        switch (idx) {
            case 0:
                writeToBufferPool(49, (bufferPool[49] & 65520) | ((value & 120) >>> 3));
                writeToBufferPool(50, (bufferPool[50] & 8191) | ((value & 7) << 13));
                break;
            case 1:
                writeToBufferPool(50, (bufferPool[50] & 57407) | (value << 6));
                break;
            case 2:
                writeToBufferPool(50, (bufferPool[50] & 65472) | ((value & 126) >>> 1));
                writeToBufferPool(51, (bufferPool[51] & 32767) | ((value & 1) << 15));
                break;
            case 3:
                writeToBufferPool(51, (bufferPool[51] & 33023) | (value << 8));
                break;
            case 4:
                writeToBufferPool(51, (bufferPool[51] & 65281) | (value << 1));
                break;
            case 5:
                writeToBufferPool(51, (bufferPool[51] & 65534) | ((value & 64) >>> 6));
                writeToBufferPool(52, (bufferPool[52] & 1023) | ((value & 63) << 10));
                break;
            case 6:
                writeToBufferPool(52, (bufferPool[52] & 64519) | (value << 3));
                break;
            case 7:
                writeToBufferPool(52, (bufferPool[52] & 65528) | ((value & 112) >>> 4));
                writeToBufferPool(53, (bufferPool[53] & 4095) | ((value & 15) << 12));
                break;
            case 8:
                writeToBufferPool(53, (bufferPool[53] & 61471) | (value << 5));
                break;
            case 9:
                writeToBufferPool(53, (bufferPool[53] & 65504) | ((value & 124) >>> 2));
                writeToBufferPool(54, (bufferPool[54] & 16383) | ((value & 3) << 14));
                break;
            case 10:
                writeToBufferPool(54, (bufferPool[54] & 49279) | (value << 7));
                break;
            case 11:
                writeToBufferPool(54, (bufferPool[54] & 65408) | (value));
                break;
        }
    }

    public static int readSymmetryVertical() throws GameActionException {
        return (rc.readSharedArray(55) & 32768) >>> 15;
    }

    public static void writeSymmetryVertical(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 32767) | (value << 15));
    }

    public static void writeBPSymmetryVertical(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 32767) | (value << 15));
    }

    public static int readSymmetryHorizontal() throws GameActionException {
        return (rc.readSharedArray(55) & 16384) >>> 14;
    }

    public static void writeSymmetryHorizontal(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 49151) | (value << 14));
    }

    public static void writeBPSymmetryHorizontal(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 49151) | (value << 14));
    }

    public static int readSymmetryRotational() throws GameActionException {
        return (rc.readSharedArray(55) & 8192) >>> 13;
    }

    public static void writeSymmetryRotational(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 57343) | (value << 13));
    }

    public static void writeBPSymmetryRotational(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 57343) | (value << 13));
    }

    public static int readSymmetryAll() throws GameActionException {
        return (rc.readSharedArray(55) & 57344) >>> 13;
    }

    public static void writeSymmetryAll(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 8191) | (value << 13));
    }

    public static void writeBPSymmetryAll(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 8191) | (value << 13));
    }

    public static int readNumHqsDoNotCall() throws GameActionException {
        return (rc.readSharedArray(55) & 6144) >>> 11;
    }

    public static void writeNumHqsDoNotCall(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 59391) | (value << 11));
    }

    public static void writeBPNumHqsDoNotCall(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 59391) | (value << 11));
    }

    public static int readNumHqsAll() throws GameActionException {
        return (rc.readSharedArray(55) & 6144) >>> 11;
    }

    public static void writeNumHqsAll(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 59391) | (value << 11));
    }

    public static void writeBPNumHqsAll(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 59391) | (value << 11));
    }

    public static int readElixirSectorConverted() throws GameActionException {
        return (rc.readSharedArray(55) & 1024) >>> 10;
    }

    public static void writeElixirSectorConverted(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 64511) | (value << 10));
    }

    public static void writeBPElixirSectorConverted(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 64511) | (value << 10));
    }

    public static int readElixirSectorIndex() throws GameActionException {
        return (rc.readSharedArray(55) & 1016) >>> 3;
    }

    public static void writeElixirSectorIndex(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 64519) | (value << 3));
    }

    public static void writeBPElixirSectorIndex(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 64519) | (value << 3));
    }

    public static int readElixirSectorAll() throws GameActionException {
        return (rc.readSharedArray(55) & 2040) >>> 3;
    }

    public static void writeElixirSectorAll(int value) throws GameActionException {
        rc.writeSharedArray(55, (rc.readSharedArray(55) & 63495) | (value << 3));
    }

    public static void writeBPElixirSectorAll(int value) throws GameActionException {
        writeToBufferPool(55, (bufferPool[55] & 63495) | (value << 3));
    }

    // BUFFER POOL READ AND WRITE METHODS

}
