package MPWorking;

import battlecode.common.*;

public class CommsConstants {

    // Status
    public static int statusIdx = 0;
    public static int numHQsMask = 0b11;

    // HQs
    public static int firstTwoHQsIdx = 1;
    public static int secondTwoHQsIdx = 2;
    public static int firstHQSectorMask = 0b11111;
    public static int firstHQSectorOffset = 0;
    public static int secondHQSectorMask = 0b1111100000;
    public static int secondHQSectorOffset = 5;

    // Sectors
    public static int firstSectorIdx = 3;
    public static int gridDimensions = 5;
    public static int numSectors = 25;
    public static int idxPerSector = 1;
    public static int sectorReportedMask = 0b1000000000000000;
    public static int sectorReportedOffset = 15;
    public static int sectorWellAdamMask = 0b111;
    public static int sectorWellAdamOffset = 0;
    public static int sectorWellManaMask = 0b111000;
    public static int sectorWellManaOffset = 3;
    public static int sectorWellElxrMask = 0b111000000;
    public static int sectorWellElxrOffset = 6;
    public static int sectorEnemyMask = 0b111000000000;
    public static int sectorEnemyOffset = 9;
    public static int sectorIslandMask = 0b111000000000000;
    public static int sectorIslandOffset = 12;
}
