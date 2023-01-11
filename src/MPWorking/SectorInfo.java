package MPWorking;

import MPWorking.fast.*;
import battlecode.common.*;

public class SectorInfo {
    private boolean found;
    private FastIterableLocSet adamWells;
    private boolean unsetAdamWells;
    private FastIterableLocSet manaWells;
    private boolean unsetManaWells;
    private FastIterableLocSet elxrWells;
    private FastIterableIntSet neutralIslands;
    private boolean unsetNeutralIslands;
    private FastIterableIntSet friendlyIslands;
    private boolean unsetFriendlyIslands;
    private FastIterableIntSet enemyIslands;
    private boolean unsetEnemyIslands;
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
            unsetAdamWells = false;
            adamWells.add(loc);
            adamWells.updateIterable();
        } else if (type == ResourceType.MANA) {
            unsetManaWells = false;
            manaWells.add(loc);
            manaWells.updateIterable();
        } else if (type == ResourceType.ELIXIR) {
            elxrWells.add(loc);
            elxrWells.updateIterable();
            if (manaWells.contains(loc)) {
                manaWells.remove(loc);
                manaWells.updateIterable();
                if (manaWells.size == 0) {
                    unsetManaWells = true;
                }
            }
            if (adamWells.contains(loc)) {
                adamWells.remove(loc);
                adamWells.updateIterable();
                if (adamWells.size == 0) {
                    unsetAdamWells = true;
                }
            }
        }
    }

    public void addIsland(int islandIdx, int control) {
        found = true;
        if (control == Comms.IslandTeam.NEUTRAL) {
            unsetNeutralIslands = false;
            neutralIslands.add(islandIdx);
            neutralIslands.updateIterable();
            if (friendlyIslands.contains(islandIdx)) {
                friendlyIslands.remove(islandIdx);
                friendlyIslands.updateIterable();
                if (friendlyIslands.size == 0) {
                    unsetFriendlyIslands = true;
                }
            }
            if (enemyIslands.contains(islandIdx)) {
                enemyIslands.remove(islandIdx);
                enemyIslands.updateIterable();
                if (enemyIslands.size == 0) {
                    unsetEnemyIslands = true;
                }
            }
        } else if (control == Comms.IslandTeam.FRIENDLY) {
            unsetFriendlyIslands = false;
            friendlyIslands.add(islandIdx);
            friendlyIslands.updateIterable();
            if (neutralIslands.contains(islandIdx)) {
                neutralIslands.remove(islandIdx);
                neutralIslands.updateIterable();
                if (neutralIslands.size == 0) {
                    unsetNeutralIslands = true;
                }
            }
            if (enemyIslands.contains(islandIdx)) {
                enemyIslands.remove(islandIdx);
                enemyIslands.updateIterable();
                if (enemyIslands.size == 0) {
                    unsetEnemyIslands = true;
                }
            }
        } else if (control == Comms.IslandTeam.ENEMY) {
            unsetEnemyIslands = false;
            enemyIslands.add(islandIdx);
            enemyIslands.updateIterable();
            if (neutralIslands.contains(islandIdx)) {
                neutralIslands.remove(islandIdx);
                neutralIslands.updateIterable();
                if (neutralIslands.size == 0) {
                    unsetNeutralIslands = true;
                }
            }
            if (friendlyIslands.contains(islandIdx)) {
                friendlyIslands.remove(islandIdx);
                friendlyIslands.updateIterable();
                if (friendlyIslands.size == 0) {
                    unsetFriendlyIslands = true;
                }
            }
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

    public boolean shouldUnsetAdamWells() {
        return unsetAdamWells;
    }

    public boolean shouldUnsetManaWells() {
        return unsetManaWells;
    }

    public boolean shouldUnsetNeutralIslands() {
        return unsetNeutralIslands;
    }

    public boolean shouldUnsetFriendlyIslands() {
        return unsetFriendlyIslands;
    }

    public boolean shouldUnsetEnemyIslands() {
        return unsetEnemyIslands;
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
        unsetAdamWells = false;
        unsetManaWells = false;
        unsetNeutralIslands = false;
        unsetFriendlyIslands = false;
        unsetEnemyIslands = false;
    }
}
