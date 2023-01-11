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
    private int lastRoundEnemyAdded;

    private boolean controlStatusSet[];

    private static final int MAX_SECTOR_AREA = 36;

    public SectorInfo() {
        adamWells = new FastLocSet(MAX_SECTOR_AREA);
        manaWells = new FastLocSet(MAX_SECTOR_AREA);
        elxrWells = new FastLocSet(MAX_SECTOR_AREA);
        neutralIslands = new FastIntSet(MAX_SECTOR_AREA);
        friendlyIslands = new FastIntSet(MAX_SECTOR_AREA);
        enemyIslands = new FastIntSet(MAX_SECTOR_AREA);
        enemies = 0;
        lastRoundEnemyAdded = 0;
        found = false;
        controlStatusSet = new boolean[Comms.ControlStatus.NUM_CONTROL_STATUS];
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
        if (control == Comms.ControlStatus.NEUTRAL_ISLAND) {
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
        } else if (control == Comms.ControlStatus.FRIENDLY_ISLAND) {
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
        } else if (control == Comms.ControlStatus.ENEMY_ISLAND) {
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

    public void addEnemy(int cStatus) {
        found = true;
        enemies++;
        setControlStatus(cStatus);
        lastRoundEnemyAdded = Robot.rc.getRoundNum();
    }

    public int getControlStatus() {
        boolean isEnemyInfoStale = Robot.rc.getRoundNum() > lastRoundEnemyAdded + Util.CLEAR_ENEMY_INFO_PERIOD;

        if (controlStatusSet[Comms.ControlStatus.ENEMY_AGGRESIVE] && !isEnemyInfoStale) {
            return Comms.ControlStatus.ENEMY_AGGRESIVE;
        } else if (controlStatusSet[Comms.ControlStatus.ENEMY_PASSIVE] && !isEnemyInfoStale) {
            return Comms.ControlStatus.ENEMY_PASSIVE;
        } else if (controlStatusSet[Comms.ControlStatus.ENEMY_ISLAND]) {
            return Comms.ControlStatus.ENEMY_ISLAND;
        } else if (controlStatusSet[Comms.ControlStatus.FRIENDLY_ISLAND]) {
            return Comms.ControlStatus.FRIENDLY_ISLAND;
        } else if (controlStatusSet[Comms.ControlStatus.NEUTRAL_ISLAND]) {
            return Comms.ControlStatus.NEUTRAL_ISLAND;
        } else if (controlStatusSet[Comms.ControlStatus.EMPTY]) {
            return Comms.ControlStatus.EMPTY;
        } else {
            return Comms.ControlStatus.UNKNOWN;
        }
    }

    public void setControlStatus(int cStatus) {
        found = true;
        controlStatusSet[cStatus] = true;
    }

    public void resetControlStatus() {
        found = true;
        controlStatusSet = new boolean[Comms.ControlStatus.NUM_CONTROL_STATUS];
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
        lastRoundEnemyAdded = 0;
        found = false;
        unsetAdamWells = false;
        unsetManaWells = false;
        unsetNeutralIslands = false;
        unsetFriendlyIslands = false;
        unsetEnemyIslands = false;
        controlStatusSet = new boolean[Comms.ControlStatus.NUM_CONTROL_STATUS];
    }
}
