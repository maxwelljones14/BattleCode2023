package MPWorking;

import MPWorking.fast.*;
import battlecode.common.*;

public class SectorInfo {
    private boolean found;
    private FastIterableLocSet adamWells;
    private FastIterableLocSet manaWells;
    private FastIterableLocSet elxrWells;
    private FastIterableIntSet neutralIslands;
    private FastIterableIntSet friendlyIslands;
    private FastIterableIntSet enemyIslands;
    private int enemies;

    private static final int MAX_SECTOR_AREA = 36;

    public SectorInfo() {
        adamWells = new FastIterableLocSet(MAX_SECTOR_AREA);
        manaWells = new FastIterableLocSet(MAX_SECTOR_AREA);
        elxrWells = new FastIterableLocSet(MAX_SECTOR_AREA);
        neutralIslands = new FastIterableIntSet(MAX_SECTOR_AREA);
        friendlyIslands = new FastIterableIntSet(MAX_SECTOR_AREA);
        enemyIslands = new FastIterableIntSet(MAX_SECTOR_AREA);
        enemies = 0;
        found = false;
    }

    public boolean hasReports() {
        return found;
    }

    public void addWell(MapLocation loc, ResourceType type) {
        found = true;
        if (type == ResourceType.ADAMANTIUM) {
            adamWells.add(loc);
            adamWells.updateIterable();
        } else if (type == ResourceType.MANA) {
            manaWells.add(loc);
            manaWells.updateIterable();
        } else if (type == ResourceType.ELIXIR) {
            elxrWells.add(loc);
            elxrWells.updateIterable();
            manaWells.remove(loc);
            manaWells.updateIterable();
            adamWells.remove(loc);
            adamWells.updateIterable();
        }
    }

    public void addIsland(int islandIdx, int control) {
        found = true;
        if (control == Comms.IslandTeam.NEUTRAL) {
            friendlyIslands.remove(islandIdx);
            enemyIslands.remove(islandIdx);
            neutralIslands.add(islandIdx);
        } else if (control == Comms.IslandTeam.FRIENDLY) {
            neutralIslands.remove(islandIdx);
            enemyIslands.remove(islandIdx);
            friendlyIslands.add(islandIdx);
        } else if (control == Comms.IslandTeam.ENEMY) {
            neutralIslands.remove(islandIdx);
            friendlyIslands.remove(islandIdx);
            enemyIslands.add(islandIdx);
        }
    }

    public void exploreSector() {
        found = true;
    }

    public int numAdamWells() {
        return adamWells.size;
    }

    public int numManaWells() {
        return manaWells.size;
    }

    public int numElxrWells() {
        return elxrWells.size;
    }

    public int numIslands() {
        return neutralIslands.size + friendlyIslands.size + enemyIslands.size;
    }

    public int numNeutralIslands() {
        return neutralIslands.size;
    }

    public int numFriendlyIslands() {
        return friendlyIslands.size;
    }

    public int numEnemyIslands() {
        return enemyIslands.size;
    }

    public int numEnemies() {
        return enemies;
    }

    public void reset() {
        adamWells.clear();
        manaWells.clear();
        elxrWells.clear();
        neutralIslands.clear();
        friendlyIslands.clear();
        enemyIslands.clear();
        enemies = 0;
        found = false;
    }
}
