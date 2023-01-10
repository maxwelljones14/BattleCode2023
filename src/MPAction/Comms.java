package MPAction;

import battlecode.common.*;
import MPAction.Debug.*;
import MPAction.Util.*;
import MPAction.CommsConstants.*;

public class Comms {

    private static RobotController rc;
    private static RobotType robotType;
    private static MapLocation sectorSize;
    private static int[] thresholds = { 0, 1, 2, 3, 5, 10, 50 };

    static void init(RobotController r) {
        rc = r;
        robotType = rc.getType();

        int numRows = rc.getMapHeight() / CommsConstants.gridDimensions;
        if (rc.getMapHeight() % CommsConstants.gridDimensions != 0) {
            numRows++;
        }
        int numCols = rc.getMapWidth() / CommsConstants.gridDimensions;
        if (rc.getMapWidth() % CommsConstants.gridDimensions != 0) {
            numCols++;
        }
        sectorSize = new MapLocation(numCols, numRows);
    }

    static MapLocation getSectorDimensions() {
        return sectorSize;
    }

    static int getSector(MapLocation loc) {
        return (loc.x / sectorSize.x) + (loc.y / sectorSize.y) * CommsConstants.gridDimensions;
    }

    static boolean isSectorReported(int sectorIdx) throws GameActionException {
        return (rc.readSharedArray(CommsConstants.firstSectorIdx + sectorIdx * CommsConstants.idxPerSector)
                & CommsConstants.sectorReportedMask) != 0;
    }

    static int getNumHQs() throws GameActionException {
        int numHQs = rc.readSharedArray(CommsConstants.statusIdx) & CommsConstants.numHQsMask;
        if (numHQs == 0) {
            numHQs = 4;
        }
        return numHQs;
    }

    static void reportHQ(MapLocation loc) throws GameActionException {
        int statusArrayData = rc.readSharedArray(CommsConstants.statusIdx);
        int numHQs = statusArrayData & CommsConstants.numHQsMask;
        int newNumHQs = (numHQs + 1) & CommsConstants.numHQsMask;
        rc.writeSharedArray(CommsConstants.statusIdx, (statusArrayData & ~CommsConstants.numHQsMask) | newNumHQs);
        if (numHQs == 0) {
            int hqArrayData = rc.readSharedArray(CommsConstants.firstTwoHQsIdx);
            rc.writeSharedArray(CommsConstants.firstTwoHQsIdx,
                    (hqArrayData & ~CommsConstants.firstHQSectorMask)
                            | (getSector(loc) << CommsConstants.firstHQSectorOffset));
        } else if (numHQs == 1) {
            int hqArrayData = rc.readSharedArray(CommsConstants.firstTwoHQsIdx);
            rc.writeSharedArray(CommsConstants.firstTwoHQsIdx,
                    (hqArrayData & ~CommsConstants.secondHQSectorMask)
                            | (getSector(loc) << CommsConstants.secondHQSectorOffset));
        } else if (numHQs == 2) {
            int hqArrayData = rc.readSharedArray(CommsConstants.secondTwoHQsIdx);
            rc.writeSharedArray(CommsConstants.secondTwoHQsIdx,
                    (hqArrayData & ~CommsConstants.firstHQSectorMask)
                            | (getSector(loc) << CommsConstants.firstHQSectorOffset));
        } else if (numHQs == 3) {
            int hqArrayData = rc.readSharedArray(CommsConstants.secondTwoHQsIdx);
            rc.writeSharedArray(CommsConstants.secondTwoHQsIdx,
                    (hqArrayData & ~CommsConstants.secondHQSectorMask)
                            | (getSector(loc) << CommsConstants.secondHQSectorOffset));
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

    static void reportSector(int sectorNum, int adamWells, int manaWells, int elxrWells, int enemies, int islands)
            throws GameActionException {
        int adjAdamWells = adjustCount(adamWells);
        int adjManaWells = adjustCount(manaWells);
        int adjElxrWells = adjustCount(elxrWells);
        int adjEnemies = adjustCount(enemies);
        int adjIslands = adjustCount(islands);
        int oldData = rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum);
        int newData = (1 << CommsConstants.sectorReportedOffset) | (adjAdamWells << CommsConstants.sectorWellAdamOffset)
                | (adjManaWells << CommsConstants.sectorWellManaOffset)
                | (adjElxrWells << CommsConstants.sectorWellElxrOffset)
                | (adjEnemies << CommsConstants.sectorEnemyOffset) | (adjIslands << CommsConstants.sectorIslandOffset);
        if (oldData != newData) {
            rc.writeSharedArray(CommsConstants.firstSectorIdx + sectorNum, newData);
        }
    }

    static void updateAdamWells(int sectorNum, int wells) throws GameActionException {
        int adjWells = adjustCount(wells);
        int oldData = rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum);
        int oldCount = (oldData >> CommsConstants.sectorWellAdamOffset) & CommsConstants.sectorCountMask;
        if (oldCount >= adjWells) {
            return;
        }
        int newData = (oldData & ~CommsConstants.sectorWellAdamMask)
                | (adjWells << CommsConstants.sectorWellAdamOffset);
        if (oldData != newData) {
            rc.writeSharedArray(CommsConstants.firstSectorIdx + sectorNum, newData);
        }
    }

    static void updateManaWells(int sectorNum, int wells) throws GameActionException {
        int adjWells = adjustCount(wells);
        int oldData = rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum);
        int oldCount = (oldData >> CommsConstants.sectorWellManaOffset) & CommsConstants.sectorCountMask;
        if (oldCount >= adjWells) {
            return;
        }
        int newData = (oldData & ~CommsConstants.sectorWellManaMask)
                | (adjWells << CommsConstants.sectorWellManaOffset);
        if (oldData != newData) {
            rc.writeSharedArray(CommsConstants.firstSectorIdx + sectorNum, newData);
        }
    }

    static void updateElxrWells(int sectorNum, int wells) throws GameActionException {
        int adjWells = adjustCount(wells);
        int oldData = rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum);
        int oldCount = (oldData >> CommsConstants.sectorWellElxrOffset) & CommsConstants.sectorCountMask;
        if (oldCount >= adjWells) {
            return;
        }
        int newData = (oldData & ~CommsConstants.sectorWellElxrMask)
                | (adjWells << CommsConstants.sectorWellElxrOffset);
        if (oldData != newData) {
            rc.writeSharedArray(CommsConstants.firstSectorIdx + sectorNum, newData);
        }
    }

    static void updateIslands(int sectorNum, int islands) throws GameActionException {
        int adjIslands = adjustCount(islands);
        int oldData = rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum);
        int newData = (oldData & ~CommsConstants.sectorIslandMask) | (adjIslands << CommsConstants.sectorIslandOffset);
        if (oldData != newData) {
            rc.writeSharedArray(CommsConstants.firstSectorIdx + sectorNum, newData);
        }
    }

    static void updateEnemies(int sectorNum, int enemies) throws GameActionException {
        int adjEnemies = adjustCount(enemies);
        int oldData = rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum);
        int newData = (oldData & ~CommsConstants.sectorEnemyMask) | (adjEnemies << CommsConstants.sectorEnemyOffset);
        if (oldData != newData) {
            rc.writeSharedArray(CommsConstants.firstSectorIdx + sectorNum, newData);
        }
    }

    static int getSectorAdamWells(int sectorNum) throws GameActionException {
        return (rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum) >> CommsConstants.sectorWellAdamOffset)
                & CommsConstants.sectorCountMask;
    }

    static int getSectorManaWells(int sectorNum) throws GameActionException {
        return (rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum) >> CommsConstants.sectorWellManaOffset)
                & CommsConstants.sectorCountMask;
    }

    static int getSectorElxrWells(int sectorNum) throws GameActionException {
        return (rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum) >> CommsConstants.sectorWellElxrOffset)
                & CommsConstants.sectorCountMask;
    }

    static int getSectorEnemies(int sectorNum) throws GameActionException {
        return (rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum) >> CommsConstants.sectorEnemyOffset)
                & CommsConstants.sectorCountMask;
    }

    static int getSectorIslands(int sectorNum) throws GameActionException {
        return (rc.readSharedArray(CommsConstants.firstSectorIdx + sectorNum) >> CommsConstants.sectorIslandOffset)
                & CommsConstants.sectorCountMask;
    }
}
