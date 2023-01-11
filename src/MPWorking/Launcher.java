package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

import MPWorking.fast.*;

public class Launcher extends Robot {
    static enum LauncherState {
        EXPLORING,
    }

    static LauncherState currState;

    static int numEnemyLaunchers;
    static int overallEnemyLauncherDx;
    static int overallEnemyLauncherDy;

    static int numFriendlyLaunchers;
    static RobotInfo closestEnemy;
    static int numFriendlies;
    static int numEnemies;
    static MapLocation closestAttackingEnemy;
    static MapLocation closestEnemyLocation;
    static int numEnemyLaunchersAttackingUs;

    static boolean healthLow;
    static boolean healthHigh;

    static RobotInfo[] enemyAttackable;

    static MapLocation[] possibleEnemyHQLocs;
    static FastLocSet seenEnemyHQLocs;

    static int sectorCenterIdx;

    public Launcher(RobotController r) throws GameActionException {
        super(r);
        guessAndSortSymmetryLocs();
        currState = LauncherState.EXPLORING;
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        closestEnemy = getBestEnemy(EnemySensable);
        resetShouldRunAway();

        enemyAttackable = getEnemyAttackable();
        numEnemies = enemyAttackable.length;
        sectorCenterIdx = getNearestCombatSector();
        if (sectorCenterIdx != Comms.UNDEFINED_SECTOR_INDEX) {
            closestEnemyLocation = sectorCenters[sectorCenterIdx];
        }
        trySwitchState();
        doStateAction();
    }

    public void switchCombatSectorIfCurrentEmpty(MapLocation target) throws GameActionException {
        // UNDER DEVELOPMENT
        // if (currLoc.distanceSquaredTo(target) <= actionRadiusSquared / 2) {
        // // if there exists a target closest enemy loc and you're near it, check for
        // // enemy islands or enemy troops
        // for (int idx : rc.senseNearbyIslands()) {
        // if (rc.senseTeamOccupyingIsland(idx) != rc.getTeam()) {
        // return;
        // }
        // }
        // if (enemyAttackable.length > 0) {
        // return;
        // }
        // // now we know theres no enemies at the target location, so set a new target
        // // sector
        // int newTargetSectorIdx = getNextNearestCombatSector(sectorCenterIdx);
        // if (newTargetSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
        // closestEnemyLocation = sectorCenters[newTargetSectorIdx];
        // }
        // }
    }

    public void switchSymmetryLocationIfCurrentEmpty(MapLocation target) throws GameActionException {
        if (currLoc.distanceSquaredTo(target) <= actionRadiusSquared / 2) {
            seenEnemyHQLocs.add(target);
        }
    }

    public void resetShouldRunAway() throws GameActionException {
        numEnemyLaunchersAttackingUs = 0;
        numFriendlies = 0;
        closestAttackingEnemy = null;
        numEnemies = 0;
        overallEnemyLauncherDx = 0;
        overallEnemyLauncherDy = 0;

        int closestLauncherDist = Integer.MAX_VALUE;
        RobotInfo closestEnemyInfo = null;
        for (int i = 0; i < EnemySensable.length; i++) {
            RobotInfo bot = EnemySensable[i];
            MapLocation candidateLoc = bot.getLocation();
            int candidateDist = currLoc.distanceSquaredTo(candidateLoc);
            RobotType botType = bot.getType();
            if (botType == RobotType.LAUNCHER || botType == RobotType.DESTABILIZER) {
                if (candidateDist <= actionRadiusSquared /* && canAttack */) {
                    numEnemyLaunchersAttackingUs++;
                    overallEnemyLauncherDx += (candidateLoc.x - currLoc.x);
                    overallEnemyLauncherDy += (candidateLoc.y - currLoc.y);
                }
                if (candidateDist < closestLauncherDist) {
                    closestLauncherDist = candidateDist;
                    closestAttackingEnemy = candidateLoc;
                    closestEnemyInfo = bot;
                }
            }
        }
        MapLocation closestEnemyLocation = currLoc;
        if (closestAttackingEnemy != null) {
            closestEnemyLocation = closestAttackingEnemy;
            int enemyHealth = closestEnemyInfo.getHealth();
            healthLow = rc.getHealth() <= enemyHealth - 2;
            healthHigh = rc.getHealth() >= 5 + enemyHealth;
            // NOTE: subject to chage as I'm not sure what the optimal health parameters are
            // for this game

        }
        for (RobotInfo Fbot : FriendlySensable) {
            RobotType FbotType = Fbot.getType();
            if (FbotType == RobotType.LAUNCHER || FbotType == RobotType.DESTABILIZER) {
                MapLocation FbotLocation = Fbot.getLocation();
                // Debug.printString(" " + FbotLocation + " ");
                if ((FbotLocation).distanceSquaredTo(closestEnemyLocation) <= FbotType.visionRadiusSquared) {
                    numFriendlies++;
                }
            }
        }
    }

    public void trySwitchState() throws GameActionException {
        switch (currState) {
            case EXPLORING:
                break;
        }
    }

    public void doStateAction() throws GameActionException {
        switch (currState) {
            case EXPLORING:
                if (!tryMoveTowardsEnemy()) {
                    launcherExplore();
                }
                break;
        }
    }

    public boolean shouldRunAway() {
        // Not only should there be no soldiers attacking us, but if we see 2 soldiers
        // between our action radius and our vision radius, we should not go forward
        // Consider changing the numFriendlies < numEnemies to <= and retesting
        // Debug.printString("enemyAction: " + numEnemySoldiersAttackingUs + "enemy: " +
        // numEnemies + "friends: " + numFriendlies);
        boolean tooManyEnemies = numFriendlies + 1 < numEnemies;
        boolean healthTooLowForEqualFight = numFriendlies + 1 == numEnemies && healthLow;
        boolean healthReallyLow = rc.getHealth() <= 6;
        return healthReallyLow || numEnemyLaunchersAttackingUs > 0 || tooManyEnemies || healthTooLowForEqualFight; // ||
                                                                                                                   // numFriendlySages
                                                                                                                   // <
                                                                                                                   // numEnemySages;
    }

    public boolean shouldStandGround() {
        return numFriendlies + 1 == numEnemies && !healthHigh && !(closestEnemy.getHealth() <= 6);
        // stand ground if its an even match and you don't have an overwhelming health
        // advantage and you can't one shot enemy
    }

    public void moveAndAttack(Direction[] targetDirs, boolean attackFirst) throws GameActionException {
        if (attackFirst) {
            tryAttackBestEnemy(closestEnemy);
            tryMoveDest(targetDirs);
        } else {
            tryMoveDest(targetDirs);
            tryAttackBestEnemy();
        }
    }

    public boolean tryMoveTowardsEnemy() throws GameActionException {
        // move towards it if found
        boolean alreadyCalculated = false;
        if (closestEnemy != null) {
            MapLocation dest;
            Direction dir = null;
            boolean attackFirst = false;
            if (shouldRunAway()) {
                attackFirst = true;
                // Positive so that we move towards the point mass. adding a 3 times multiplier
                // so that directoTo dest is not CENTER
                dest = currLoc.translate(-overallEnemyLauncherDx, -overallEnemyLauncherDy);// (overallFriendlyLauncherDx,
                // overallFriendlyLauncherDy);
                Direction possibleDir = currLoc.directionTo(dest);
                dir = chooseBackupDirection(possibleDir);
                Debug.printString("t: " + rc.getMovementCooldownTurns() + " " + dir + " " + overallEnemyLauncherDx + " "
                        + overallEnemyLauncherDy);
                if (dir == null) {
                    Debug.printString("RA bad");
                    tryAttackBestEnemy(closestEnemy);
                    return true;
                }
                Debug.printString("RA, Dest: " + dir);
                Direction[] targetDirs = Util.getInOrderDirections(dir);
                moveAndAttack(targetDirs, attackFirst);
                return true;
            } else {
                dest = closestEnemy.getLocation();
                RobotType closestEnemyType = closestEnemy.getType();
                if (closestEnemyType == RobotType.CARRIER || closestEnemyType == RobotType.HEADQUARTERS
                        || closestEnemyType == RobotType.AMPLIFIER || closestEnemyType == RobotType.BOOSTER) {

                    dir = Util.getFirstValidInOrderDirection(currLoc.directionTo(dest)); // navTo
                    MapLocation targetLoc = currLoc.add(dir);

                    // rubble check
                    boolean isPassable = rc.sensePassability(targetLoc);
                    if (rc.onTheMap(targetLoc) && !isPassable) {
                        Debug.printString("rub high");
                        tryAttackBestEnemy(closestEnemy);
                        return true;
                    }
                    // // We're already close to a non-attacking enemy, and moving would put us in
                    // // lower passability
                    int distanceNeeded = 2;
                    if (currLoc.distanceSquaredTo(dest) <= distanceNeeded) {
                        Debug.printString("close");
                        tryAttackBestEnemy(closestEnemy);
                        return true;
                    }
                    Debug.printString("enemyfren");
                    Direction[] targetDirs = Util.getInOrderDirections(dir);
                    moveAndAttack(targetDirs, attackFirst);
                    return true;
                } else {
                    if (!shouldStandGround()) {
                        dir = chooseForwardDirection(dest);
                        attackFirst = false;
                        if (dir == null) {
                            Debug.printString("Fw bad; ");
                            tryAttackBestEnemy();
                            return true;
                        }
                        Debug.printString("Fw, Dest: " + dir);
                        Direction[] targetDirs = Util.getInOrderDirections(dir);
                        moveAndAttack(targetDirs, attackFirst);
                        return true;
                    } else {
                        Debug.printString("standing ground");
                        tryAttackBestEnemy();
                        return true;
                    }
                }

            }
        }
        return false;
    }

    // NOTE: neither forward nor backward direction have a good way to break ties;
    // this will probably happen more often now that there isn't rubble
    // impassibility
    public Direction chooseForwardDirection(MapLocation loc) throws GameActionException {
        Direction Dir = currLoc.directionTo(loc);
        Direction[] dirsToConsider = Util.getInOrderDirections(Dir);
        Direction bestDirSoFar = null;
        int bestEnemiesSeen = Integer.MAX_VALUE;
        RobotInfo enemy;
        for (int x = 0; x < dirsToConsider.length; x++) {
            Direction newDir = dirsToConsider[x];
            if (rc.canMove(newDir)) {
                Direction extraDir = rc.senseMapInfo(currLoc.add(newDir)).getCurrentDirection();
                MapLocation targetLoc = currLoc.add(newDir).add(extraDir);
                boolean isPassible = rc.sensePassability(targetLoc);
                int currEnemiesSeen = 0;
                for (int i = enemyAttackable.length; --i >= 0;) {
                    enemy = enemyAttackable[i];
                    MapLocation enemyLoc = enemy.getLocation();
                    if (enemyLoc.distanceSquaredTo(targetLoc) <= actionRadiusSquared) {
                        currEnemiesSeen++;
                    }
                }
                if (isPassible && (currEnemiesSeen <= bestEnemiesSeen || bestEnemiesSeen == 0)) {
                    if (currEnemiesSeen != 0) {
                        if (currEnemiesSeen < bestEnemiesSeen || bestEnemiesSeen == 0) {
                            bestDirSoFar = newDir;
                            bestEnemiesSeen = currEnemiesSeen;
                        }
                    } else {
                        if (bestEnemiesSeen == Integer.MAX_VALUE) {
                            bestDirSoFar = newDir;
                            bestEnemiesSeen = currEnemiesSeen;
                        }
                    }
                }
            }
        }
        return bestDirSoFar;
    }

    public Direction chooseBackupDirection(Direction Dir) throws GameActionException {
        Direction[] dirsToConsider = Util.getInOrderDirections(Dir);
        Debug.printString("dir:" + Dir + " " + rc.getMovementCooldownTurns());
        Direction bestDirSoFar = null;
        int bestEnemiesStillSeen = Integer.MAX_VALUE;
        RobotInfo enemy;
        for (int x = 0; x < dirsToConsider.length; x++) {
            Direction newDir = dirsToConsider[x];
            if (rc.canMove(newDir)) {
                Direction extraDir = rc.senseMapInfo(currLoc.add(newDir)).getCurrentDirection();
                MapLocation targetLoc = currLoc.add(newDir).add(extraDir);
                Debug.printString("best dir " + newDir);
                boolean isPassible = rc.sensePassability(targetLoc);
                int currEnemiesStillSeen = 0;
                for (int i = enemyAttackable.length; --i >= 0;) {
                    enemy = enemyAttackable[i];
                    MapLocation enemyLoc = enemy.getLocation();
                    int enemyActionRadius = enemy.getType().actionRadiusSquared;
                    if (enemyLoc.distanceSquaredTo(targetLoc) <= enemyActionRadius) {
                        currEnemiesStillSeen++;
                    }
                }
                if (isPassible && currEnemiesStillSeen <= bestEnemiesStillSeen) {
                    Debug.printString("R");
                    if (currEnemiesStillSeen < bestEnemiesStillSeen) {
                        bestDirSoFar = newDir;
                        bestEnemiesStillSeen = currEnemiesStillSeen;
                    } else if (currEnemiesStillSeen == bestEnemiesStillSeen) {
                        bestDirSoFar = newDir;
                        bestEnemiesStillSeen = currEnemiesStillSeen;
                    }
                }
            }
        }
        return bestDirSoFar;
    }

    public void launcherExplore() throws GameActionException {
        MapLocation target;
        boolean goingToSector = false;
        if (closestEnemyLocation != null) {
            target = closestEnemyLocation;
            goingToSector = true;
            Debug.printString("going for it");
        } else {
            MapLocation symmetricLoc = chooseSymmetricLoc();
            if (symmetricLoc != null) {
                target = symmetricLoc;
                Debug.printString("going to symmetric Loc: " + target.toString());
            } else {
                target = Explore.getExploreTarget();
                Debug.printString("Exploring: " + target.toString());
            }
        }
        if (currLoc.distanceSquaredTo(target) <= Util.JUST_OUTSIDE_OF_VISION_RADIUS) {
            Pathfinding.move(target); // tryMoveSafely
            Debug.printString("saf mov");
        } else {
            Pathfinding.move(target);
            Debug.printString("reg mov");
        }
        tryAttackBestEnemy(getBestEnemy());
        if (goingToSector) {
            switchCombatSectorIfCurrentEmpty(target);
        } else {
            switchSymmetryLocationIfCurrentEmpty(target);
        }

    }

    public static MapLocation[] guessEnemyLoc(MapLocation ourLoc) throws GameActionException {
        MapLocation[] results;

        int height = rc.getMapHeight();
        int width = rc.getMapWidth();

        MapLocation verticalFlip = new MapLocation(ourLoc.x, height - ourLoc.y - 1);
        MapLocation horizontalFlip = new MapLocation(width - ourLoc.x - 1, ourLoc.y);
        MapLocation rotation = new MapLocation(width - ourLoc.x - 1, height - ourLoc.y - 1);

        results = new MapLocation[] { verticalFlip, horizontalFlip, rotation };
        return results;
    }

    public void guessAndSortSymmetryLocs() throws GameActionException {
        FastIterableLocSet possibleLocs = new FastIterableLocSet(12);

        MapLocation[] listOfHQs = new MapLocation[] { Comms.readOurHqLocation(0),
                Comms.readOurHqLocation(1),
                Comms.readOurHqLocation(2),
                Comms.readOurHqLocation(3) };
        for (int i = 0; i < 4; i++) {
            MapLocation HQLoc = listOfHQs[i];
            if (rc.onTheMap(HQLoc)) {
                MapLocation[] possibleFlips = guessEnemyLoc(HQLoc);
                for (int j = 0; j < possibleFlips.length; j++) {
                    MapLocation possibleFlip = possibleFlips[j];
                    Debug.printString("pF: " + possibleFlip);
                    boolean IsOk = true;
                    for (int k = 0; k < listOfHQs.length; k++) {
                        if (possibleFlip
                                .distanceSquaredTo(HQLoc) < RobotType.HEADQUARTERS.visionRadiusSquared) {
                            IsOk = false;
                        }
                    }
                    if (IsOk && rc.onTheMap(possibleFlip)) {
                        possibleLocs.add(possibleFlip);
                        possibleLocs.updateIterable();
                    }
                }
            }
        }
        possibleEnemyHQLocs = new MapLocation[possibleLocs.size];
        for (int i = 0; i < possibleLocs.size; i++) {
            possibleEnemyHQLocs[i] = possibleLocs.locs[i];
        }
        seenEnemyHQLocs = new FastLocSet(12);
    }

    public MapLocation chooseSymmetricLoc() throws GameActionException {
        MapLocation bestLoc = null;
        int bestDist = Integer.MAX_VALUE;
        for (int i = 0; i < possibleEnemyHQLocs.length; i++) {
            MapLocation possibleLoc = possibleEnemyHQLocs[i];
            int currDist = currLoc.distanceSquaredTo(possibleLoc);
            boolean notTraversed = Comms
                    .readSectorControlStatus(whichSector(possibleLoc)) == Comms.ControlStatus.UNKNOWN;
            if (!seenEnemyHQLocs.contains(possibleLoc.x, possibleLoc.y) && currDist < bestDist && notTraversed) {
                bestLoc = possibleLoc;
                bestDist = currDist;
            }
        }
        return bestLoc;
    }
}
