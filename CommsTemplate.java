package examplefuncsplayer;

import battlecode.common.*;

public class Comms {

    private static RobotController rc;

    private static int[] bufferPool;
    private static boolean[] dirtyFlags;

    // CONSTS

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

    // PRIORITY SECTOR INIT

    // SECTOR CONTROL STATUS RESET

    // MAIN READ AND WRITE METHODS

    // BUFFER POOL READ AND WRITE METHODS

}
