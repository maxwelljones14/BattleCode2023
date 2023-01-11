package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

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
    static int numEnemyLaunchersAttackingUs;

    static boolean healthLow;
    static boolean healthHigh;

    static RobotInfo[] enemyAttackable;

    public Launcher(RobotController r) throws GameActionException {
        super(r);
        currState = LauncherState.EXPLORING;

    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        announceAlive();
        closestEnemy = getBestEnemy(EnemySensable);
        resetShouldRunAway();

        enemyAttackable = getEnemyAttackable();
        numEnemies = enemyAttackable.length;

        trySwitchState();
        doStateAction();
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
                    overallEnemyLauncherDx += currLoc.directionTo(candidateLoc).dx
                            * (100 / (currLoc.distanceSquaredTo(candidateLoc)));
                    overallEnemyLauncherDy += currLoc.directionTo(candidateLoc).dy
                            * (100 / (currLoc.distanceSquaredTo(candidateLoc)));
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
            healthLow = rc.getHealth() <= enemyHealth - 8;
            healthHigh = rc.getHealth() >= 6 + enemyHealth;
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
                    soldierExplore();
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
                // Positive so that we move towards the point mass.
                dest = currLoc.translate(-overallEnemyLauncherDx, -overallEnemyLauncherDy);// (overallFriendlyLauncherDx,
                                                                                           // overallFriendlyLauncherDy);
                Direction possibleDir = currLoc.directionTo(dest);
                dir = chooseBackupDirection(possibleDir);
                Debug.printString("t: " + rc.getMovementCooldownTurns() + " " + dir);
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
                    // We're already close to a non-attacking enemy, and moving would put us in
                    // lower passability
                    int distanceNeeded = 5;
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

    public void soldierExplore() throws GameActionException {
        MapLocation target;
        // if(avgEnemyLoc != null) {
        // target = avgEnemyLoc;
        // if (currLoc.distanceSquaredTo(target) <= visionRadiusSquared) {
        // boolean seeArchonInSensable = false;
        // for (RobotInfo bot : EnemySensable) {
        // if (bot.getType() == RobotType.ARCHON) {
        // seeArchonInSensable = true;
        // }
        // }
        // if (!seeArchonInSensable) {
        // Comms.broadcastLauncherNearSectorButNothingFound();
        // }
        // }
        // Debug.printString("going for it");
        // } else {
        target = Explore.getExploreTarget();
        Debug.printString("Exploring: " + target.toString());
        // }
        if (currLoc.distanceSquaredTo(target) <= Util.JUST_OUTSIDE_OF_VISION_RADIUS) {
            Pathfinding.move(target); // tryMoveSafely
            Debug.printString("saf mov");
        } else {
            Pathfinding.move(target);
            Debug.printString("reg mov");
        }
    }

    public void announceAlive() throws GameActionException {
        int currLaunchers = Comms.readBotCountLaunchers();
        if (currLaunchers < 254) {
            Comms.writeBotCountLaunchers(currLaunchers + 1);
        }
    }
}
