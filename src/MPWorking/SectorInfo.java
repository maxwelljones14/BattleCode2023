package MPWorking;

import MPWorking.fast.*;
import battlecode.common.*;

public class SectorInfo {
    private boolean found;
    private FastLocSet adamWells;
    private boolean unsetAdamWells;
    private FastLocSet manaWells;
    private boolean unsetManaWells;
    private FastLocSet elxrWells;
    private FastIntSet neutralIslands;
    private boolean unsetNeutralIslands;
    private FastIntSet friendlyIslands;
    private boolean unsetFriendlyIslands;
    private FastIntSet enemyIslands;
    private boolean unsetEnemyIslands;
    private int enemies;
    private int controlStatus;

    private static final int MAX_SECTOR_AREA = 36;

    public SectorInfo() {
        adamWells = new FastLocSet(MAX_SECTOR_AREA);
        manaWells = new FastLocSet(MAX_SECTOR_AREA);
        elxrWells = new FastLocSet(MAX_SECTOR_AREA);
        neutralIslands = new FastIntSet(MAX_SECTOR_AREA);
        friendlyIslands = new FastIntSet(MAX_SECTOR_AREA);
        enemyIslands = new FastIntSet(MAX_SECTOR_AREA);
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
        } else if (type == ResourceType.MANA) {
            unsetManaWells = false;
            manaWells.add(loc);
        } else if (type == ResourceType.ELIXIR) {
            elxrWells.add(loc);
            if (manaWells.contains(loc)) {
                manaWells.remove(loc);
                if (manaWells.size == 0) {
                    unsetManaWells = true;
                }
            }
            if (adamWells.contains(loc)) {
                adamWells.remove(loc);
                if (adamWells.size == 0) {
                    unsetAdamWells = true;
                }
            }
        }
    }

    public void addIsland(int islandIdx, int control) {
        found = true;
        if (control == Comms.ControlStatus.NEUTRAL) {
            unsetNeutralIslands = false;
            neutralIslands.add(islandIdx);
            if (friendlyIslands.contains(islandIdx)) {
                friendlyIslands.remove(islandIdx);
                if (friendlyIslands.size == 0) {
                    unsetFriendlyIslands = true;
                }
            }
            if (enemyIslands.contains(islandIdx)) {
                enemyIslands.remove(islandIdx);
                if (enemyIslands.size == 0) {
                    unsetEnemyIslands = true;
                }
            }
        } else if (control == Comms.ControlStatus.FRIENDLY) {
            unsetFriendlyIslands = false;
            friendlyIslands.add(islandIdx);
            if (neutralIslands.contains(islandIdx)) {
                neutralIslands.remove(islandIdx);
                if (neutralIslands.size == 0) {
                    unsetNeutralIslands = true;
                }
            }
            if (enemyIslands.contains(islandIdx)) {
                enemyIslands.remove(islandIdx);
                if (enemyIslands.size == 0) {
                    unsetEnemyIslands = true;
                }
            }
        } else if (control == Comms.ControlStatus.ENEMY) {
            unsetEnemyIslands = false;
            enemyIslands.add(islandIdx);
            if (neutralIslands.contains(islandIdx)) {
                neutralIslands.remove(islandIdx);
                if (neutralIslands.size == 0) {
                    unsetNeutralIslands = true;
                }
            }
            if (friendlyIslands.contains(islandIdx)) {
                friendlyIslands.remove(islandIdx);
                if (friendlyIslands.size == 0) {
                    unsetFriendlyIslands = true;
                }
            }
        }

        setControlStatus(control);
    }

    public void addEnemy() {
        found = true;
        enemies++;
        setControlStatus(Comms.ControlStatus.ENEMY);
    }

    public int getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(int cStatus) {
        found = true;
        if (cStatus > controlStatus)
            controlStatus = cStatus;
    }

    public void resetControlStatus() {
        found = true;
        controlStatus = Comms.ControlStatus.UNKNOWN;
    }

    public void exploreSector() {
        found = true;
        setControlStatus(Comms.ControlStatus.EMPTY);
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
        controlStatus = Comms.ControlStatus.EXPLORING;
    }
}
