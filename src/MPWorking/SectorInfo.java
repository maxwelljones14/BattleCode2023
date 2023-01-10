package MPWorking;

import MPWorking.fast.*;
import battlecode.common.*;

public class SectorInfo {
    private FastIterableLocSet adamWells;
    private FastIterableLocSet manaWells;
    private FastIterableLocSet elxrWells;
    private FastIterableLocSet neutralIslands;
    private FastIterableLocSet friendlyIslands;
    private FastIterableLocSet enemyIslands;
    private int enemies;

    public void addWell(MapLocation loc, ResourceType type) {
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

    // 0 = Neutral, 1 = Ours, 2 = Enemy, can make this an enum later
    public void addIsland(MapLocation loc, int control) {
        if (control == 0) {
            friendlyIslands.remove(loc);
            enemyIslands.remove(loc);
            neutralIslands.add(loc);
        } else if (control == 1) {
            neutralIslands.remove(loc);
            enemyIslands.remove(loc);
            friendlyIslands.add(loc);
        } else if (control == 2) {
            neutralIslands.remove(loc);
            friendlyIslands.remove(loc);
            enemyIslands.add(loc);
        }
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
    }
}
