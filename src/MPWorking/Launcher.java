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
    static double overallEnemyLauncherDx;
    static double overallEnemyLauncherDy;

    static int numFriendlyLaunchers;
    static RobotInfo closestEnemy;
    static int numFriendlies;
    static int numCloseFriendlies;
    static int numEnemies;
    static MapLocation closestAttackingEnemy;
    static MapLocation closestEnemyLocation;
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
        numCloseFriendlies = 0;
        closestAttackingEnemy = null;
        numEnemies = 0;
        overallEnemyLauncherDx = 0;
        overallEnemyLauncherDy = 0;

        int closestLauncherDist = Integer.MAX_VALUE;
        RobotInfo closestEnemyInfo = null;
        int numAttackingEnemyCount = 0;
        for (int i = 0; i < EnemySensable.length; i++) {
            RobotInfo bot = EnemySensable[i];
            int botHealth = bot.getHealth();
            MapLocation candidateLoc = bot.getLocation();
            int candidateDist = currLoc.distanceSquaredTo(candidateLoc);
            int lowestHealth = Integer.MAX_VALUE;
            boolean inActionRadius = false;
            RobotType botType = bot.getType();
            if (botType == RobotType.LAUNCHER || botType == RobotType.DESTABILIZER) {
                if (candidateDist <= actionRadiusSquared /* && canAttack */) {
                    inActionRadius = true;
                    numAttackingEnemyCount += 1;
                    overallEnemyLauncherDx += (candidateLoc.x - currLoc.x);
                    overallEnemyLauncherDy += (candidateLoc.y - currLoc.y);
                    numEnemyLaunchersAttackingUs++;

                    if (botHealth < lowestHealth
                            || (botHealth == lowestHealth && candidateDist < closestLauncherDist)) {
                        closestLauncherDist = candidateDist;
                        closestAttackingEnemy = candidateLoc;
                        closestEnemyInfo = bot;
                        lowestHealth = botHealth;
                    }

                } else {
                    if (!inActionRadius && botHealth < lowestHealth
                            || (botHealth == lowestHealth && candidateDist < closestLauncherDist)) {
                        closestLauncherDist = candidateDist;
                        closestAttackingEnemy = candidateLoc;
                        closestEnemyInfo = bot;
                        lowestHealth = botHealth;
                    }
                }

            }
        }
        if (numAttackingEnemyCount != 0) {
            overallEnemyLauncherDx = overallEnemyLauncherDx / numAttackingEnemyCount;
            overallEnemyLauncherDy = overallEnemyLauncherDy / numAttackingEnemyCount;
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

        if (overallEnemyLauncherDx == 0 && overallEnemyLauncherDy == 0) {
            overallEnemyLauncherDx += (closestEnemyLocation.x - currLoc.x);
            overallEnemyLauncherDy += (closestEnemyLocation.y - currLoc.y);
        }

        for (RobotInfo Fbot : FriendlySensable) {
            RobotType FbotType = Fbot.getType();
            if (FbotType == RobotType.LAUNCHER || FbotType == RobotType.DESTABILIZER) {
                MapLocation FbotLocation = Fbot.getLocation();
                // Debug.printString(" " + FbotLocation + " ");
                overallEnemyLauncherDx += (closestEnemyLocation.x - FbotLocation.x);
                overallEnemyLauncherDy += (closestEnemyLocation.y - FbotLocation.y);
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
        return healthReallyLow || (numEnemyLaunchersAttackingUs > 0 && numFriendlies <= numEnemies)
                || tooManyEnemies || healthTooLowForEqualFight; // ||
        // numFriendlySages
        // <
        // numEnemySages;
    }

    public boolean shouldStandGround() {
        return (numFriendlies + 1 == numEnemies && !healthHigh && !(closestEnemy.getHealth() <= 6))
                || numEnemyLaunchersAttackingUs > 0;
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
                dest = currLoc.translate(-(int) (overallEnemyLauncherDx), -(int) (overallEnemyLauncherDy));// (overallFriendlyLauncherDx,
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
                if (closestEnemyType == RobotType.HEADQUARTERS) {
                    // if we can go to a sector, leave the headquarters (this amounts to returning
                    // false and going to the soldierExplore function)
                    // if (closestEnemyLocation != null) {
                    // return false;
                    // }
                    closestEnemyLocation = closestEnemy.getLocation();
                    int ourDist = currLoc.distanceSquaredTo(closestEnemyLocation);
                    int numTroopsCloser = rc.senseNearbyRobots(closestEnemyLocation, ourDist - 1, rc.getTeam()).length;
                    if (numTroopsCloser >= 4) {
                        Debug.printString("many close");
                        return false;
                    } else {
                        if (currLoc.distanceSquaredTo(closestEnemyLocation) > 2) {
                            Debug.printString("closing in");
                            Nav.move(closestEnemyLocation);
                        }
                        return true;
                    }
                } else if (closestEnemyType == RobotType.CARRIER
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
                boolean hasCloud = rc.senseMapInfo(currLoc.add(newDir)).hasCloud();
                boolean currLocHasCloud = rc.senseMapInfo(currLoc).hasCloud();
                int currEnemiesSeen = 0;
                for (int i = enemyAttackable.length; --i >= 0;) {
                    enemy = enemyAttackable[i];
                    MapLocation enemyLoc = enemy.getLocation();
                    if (enemyLoc.distanceSquaredTo(targetLoc) <= actionRadiusSquared) {
                        currEnemiesSeen++;
                    }
                }
                if (isPassible && (currLocHasCloud || !hasCloud)
                        && (currEnemiesSeen <= bestEnemiesSeen || bestEnemiesSeen == 0)) {
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
        Debug.printString("back dir:" + Dir + " " + rc.getMovementCooldownTurns());
        Direction bestDirSoFar = null;
        int bestEnemiesStillSeen = Integer.MAX_VALUE;
        RobotInfo enemy;
        for (int x = 0; x < dirsToConsider.length; x++) {
            Direction newDir = dirsToConsider[x];
            if (rc.canMove(newDir)) {
                Direction extraDir = rc.senseMapInfo(currLoc.add(newDir)).getCurrentDirection();
                MapLocation targetLoc = currLoc.add(newDir).add(extraDir);
                // Debug.printString("best dir " + newDir);
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

        int combatSectorIdx = getPrioritizedCombatSectorIdx();
        if (combatSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
            Debug.printString("going for it");
            target = sectorCenters[combatSectorIdx];
            // int numCloseFriendlies = 0;
            // boolean iAmClosest = true;
            // for (RobotInfo Fbot : FriendlySensable) {
            // RobotType FbotType = Fbot.getType();
            // if (FbotType == RobotType.LAUNCHER || FbotType == RobotType.DESTABILIZER) {
            // MapLocation FbotLocation = Fbot.getLocation();
            // numCloseFriendlies++;
            // if (Util.distance(target, FbotLocation) <= Util.distance(target, currLoc)) {
            // iAmClosest = false;
            // }
            // }
            // }
            // if (numCloseFriendlies != 0
            // && iAmClosest
            // && Util.distance(target, currLoc) <= Math.sqrt((double)
            // (rc.getType().visionRadiusSquared) * 1.5)) {
            // Debug.printString("waiting for friend");
            // tryAttackBestEnemy(getBestEnemy());
            // return;
            // }

        } else {
            int exploreSector = getNearestExploreSector();
            if (exploreSector != Comms.UNDEFINED_SECTOR_INDEX) {
                target = sectorCenters[exploreSector];
                // int numCloseFriendlies = 0;
                // boolean iAmClosest = true;
                // for (RobotInfo Fbot : FriendlySensable) {
                // RobotType FbotType = Fbot.getType();
                // if (FbotType == RobotType.LAUNCHER || FbotType == RobotType.DESTABILIZER) {
                // MapLocation FbotLocation = Fbot.getLocation();
                // numCloseFriendlies++;
                // if (Util.distance(target, FbotLocation) <= Util.distance(target, currLoc)) {
                // iAmClosest = false;
                // }
                // }
                // }
                // if (numCloseFriendlies != 0
                // && iAmClosest
                // && Util.distance(target, currLoc) <= Math
                // .sqrt((double) (rc.getType().visionRadiusSquared) * 1.5)) {
                // Debug.printString("waiting for friend");
                // tryAttackBestEnemy(getBestEnemy());
                // return;
                // }

                Debug.printString("going to symmetric Loc: " + target.toString());
            } else {
                target = Explore.getExploreTarget();
                Debug.printString("Exploring: " + target.toString());
            }
        }

        Debug.setIndicatorLine(Debug.INDICATORS, currLoc, target, 255, 0, 200);
        if (currLoc.distanceSquaredTo(target) <= Util.JUST_OUTSIDE_OF_VISION_RADIUS) {
            Nav.move(target); // tryMoveSafely
            Debug.printString("saf mov");
        } else {
            Nav.move(target);
            Debug.printString("reg mov");
        }
        tryAttackBestEnemy(getBestEnemy());

    }
}
