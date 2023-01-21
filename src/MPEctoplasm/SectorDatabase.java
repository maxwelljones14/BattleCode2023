package MPEctoplasm;

import MPEctoplasm.SectorInfo.*;

public class SectorDatabase {
    private SectorInfo[] databaseEntries;
    private int databaseSize;

    public SectorDatabase(int size) {
        databaseEntries = new SectorInfo[size];
        databaseSize = size;
    }

    public SectorInfo at(int idx) {
        if (databaseEntries[idx] == null) {
            databaseEntries[idx] = new SectorInfo();
        }
        return databaseEntries[idx];
    }
}
