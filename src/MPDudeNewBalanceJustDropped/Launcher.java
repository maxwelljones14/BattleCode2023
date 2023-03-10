package MPDudeNewBalanceJustDropped;

import battlecode.common.*;
import MPDudeNewBalanceJustDropped.Util.*;
import MPDudeNewBalanceJustDropped.Comms.*;
import MPDudeNewBalanceJustDropped.Debug.*;

import MPDudeNewBalanceJustDropped.fast.*;

public class Launcher extends Robot {
    static enum LauncherState {
        EXPLORING,
        ATTACKING,
        RUNNING,
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
    static MapLocation lastClosestAttackingEnemy;
    static MapLocation closestEnemyLocation;
    static int numEnemyLaunchersAttackingUs;

    static boolean healthLow;
    static boolean healthHigh;

    static RobotInfo[] enemyAttackable;

    static FastLocSet seenEnemyHQLocs;
    static int HQwaitCounter;

    public static final int MAX_LAUNCHERS_PER_ENEMY_HQ = 4;
    public static final int LOW_HEALTH_THRESHOLD = 30;
    public static final int LOW_HEALTH_DIFF = 20;
    public static final int HIGH_HEALTH_DIFF = 50;

    public Launcher(RobotController r) throws GameActionException {
        super(r);
        currState = LauncherState.EXPLORING;
        seenEnemyHQLocs = new FastLocSet();
        HQwaitCounter = 0;
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        closestEnemy = getBestEnemy(EnemySensable);
        resetLocalEnemyInformation();

        enemyAttackable = getEnemyAttackable();
        numEnemies = enemyAttackable.length;

        trySwitchState();
        doStateAction();
        attackClouds();
    }

    public void resetLocalEnemyInformation() throws GameActionException {
        numEnemyLaunchersAttackingUs = 0;
        numFriendlies = 0;
        numCloseFriendlies = 0;
        closestAttackingEnemy = null;
        numEnemies = 0;
        overallEnemyLauncherDx = 0;
        overallEnemyLauncherDy = 0;
        if (HQwaitCounter > 0) {
            HQwaitCounter--;
        }

        int closestLauncherDist = Integer.MAX_VALUE;
        RobotInfo closestEnemyInfo = null;
        int lowestHealth = Integer.MAX_VALUE;
        boolean inActionRadius = false;
        int numAttackingEnemyCount = 0;
        for (int i = 0; i < EnemySensable.length; i++) {
            RobotInfo bot = EnemySensable[i];
            int botHealth = bot.getHealth();
            MapLocation candidateLoc = bot.getLocation();
            int candidateDist = currLoc.distanceSquaredTo(candidateLoc);
            RobotType botType = bot.getType();
            if (botType == RobotType.LAUNCHER || botType == RobotType.DESTABILIZER) {
                if (candidateDist <= actionRadiusSquared /* && canAttack */) {
                    numAttackingEnemyCount += 1;
                    overallEnemyLauncherDx += (candidateLoc.x - currLoc.x);
                    overallEnemyLauncherDy += (candidateLoc.y - currLoc.y);
                    numEnemyLaunchersAttackingUs++;

                    if (!inActionRadius || botHealth < lowestHealth
                            || (botHealth == lowestHealth && candidateDist < closestLauncherDist)) {
                        closestLauncherDist = candidateDist;
                        closestAttackingEnemy = candidateLoc;
                        closestEnemyInfo = bot;
                        lowestHealth = botHealth;
                    }
                    inActionRadius = true;
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
            lastClosestAttackingEnemy = closestAttackingEnemy;
            int enemyHealth = closestEnemyInfo.getHealth();
            healthLow = rc.getHealth() <= enemyHealth - LOW_HEALTH_DIFF;
            healthHigh = rc.getHealth() >= HIGH_HEALTH_DIFF + enemyHealth;
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
                overallEnemyLauncherDx += (currLoc.x - FbotLocation.x);
                overallEnemyLauncherDy += (currLoc.y - FbotLocation.y);
                if ((FbotLocation).distanceSquaredTo(closestEnemyLocation) <= FbotType.visionRadiusSquared) {
                    numFriendlies++;
                }
            }
        }
    }

    public void trySwitchState() throws GameActionException {
        if (closestEnemy == null) {
            currState = LauncherState.EXPLORING;
        } else if (shouldRunAway()) {
            currState = LauncherState.RUNNING;
        } else {
            if (closestEnemy.getType() == RobotType.HEADQUARTERS) {
                closestEnemyLocation = closestEnemy.getLocation();
                int ourDist = currLoc.distanceSquaredTo(closestEnemyLocation);
                int numTroopsCloser = rc.senseNearbyRobots(closestEnemyLocation, ourDist - 1, rc.getTeam()).length;
                if (HQwaitCounter != 0 || numTroopsCloser >= MAX_LAUNCHERS_PER_ENEMY_HQ) {
                    currState = LauncherState.EXPLORING;
                    HQwaitCounter = 5;
                    return;
                }
                if (ourDist <= RobotType.HEADQUARTERS.actionRadiusSquared) {
                    currState = LauncherState.RUNNING;
                    return;
                }
            }
            currState = LauncherState.ATTACKING;
        }
    }

    public void doStateAction() throws GameActionException {
        switch (currState) {
            case EXPLORING:
                launcherExplore();
                break;
            case RUNNING:
                runAwayFromEnemy();
                break;
            case ATTACKING:
                moveTowardsEnemy();
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
        boolean healthReallyLow = rc.getHealth() <= LOW_HEALTH_THRESHOLD;
        return healthReallyLow || (numEnemyLaunchersAttackingUs > 0 && numFriendlies <= numEnemies)
                || tooManyEnemies || healthTooLowForEqualFight; // ||
        // numFriendlySages
        // <
        // numEnemySages;
    }

    public boolean shouldStandGround() {
        return (numFriendlies + 1 == numEnemies && !healthHigh && !(closestEnemy.getHealth() <= LOW_HEALTH_THRESHOLD))
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

    public void runAwayFromEnemy() throws GameActionException {
        MapLocation dest;
        Direction dir = null;
        dest = currLoc.translate(-(int) (overallEnemyLauncherDx), -(int) (overallEnemyLauncherDy));// (overallFriendlyLauncherDx,
        // overallFriendlyLauncherDy);
        Direction possibleDir = currLoc.directionTo(dest);
        dir = chooseBackupDirection(possibleDir);
        Debug.printString("t: " + rc.getMovementCooldownTurns() + " " + dir + " " + overallEnemyLauncherDx + " "
                + overallEnemyLauncherDy);
        if (dir == null) {
            Debug.printString("RA bad");
            tryAttackBestEnemy(closestEnemy);
            return;
        }
        Debug.printString("RA, Dest: " + dir);
        Direction[] targetDirs = Util.getInOrderDirections(dir);
        moveAndAttack(targetDirs, true);
    }

    public void moveTowardsEnemy() throws GameActionException {
        MapLocation dest;
        Direction dir = null;
        dest = closestEnemy.getLocation();
        RobotType closestEnemyType = closestEnemy.getType();
        if (closestEnemyType == RobotType.HEADQUARTERS) {
            // if we can go to a sector, leave the headquarters (this amounts to returning
            // false and going to the soldierExplore function)
            // if (closestEnemyLocation != null) {
            // return false;
            // }
            if (currLoc.add(currLoc.directionTo(closestEnemyLocation))
                    .distanceSquaredTo(closestEnemyLocation) > RobotType.HEADQUARTERS.actionRadiusSquared) {
                Debug.printString("closing in");
                Nav.move(closestEnemyLocation);
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
                return;
            }
            // // We're already close to a non-attacking enemy, and moving would put us in
            // // lower passability
            int distanceNeeded = 2;
            if (currLoc.distanceSquaredTo(dest) <= distanceNeeded) {
                Debug.printString("close");
                tryAttackBestEnemy(closestEnemy);
                return;
            }
            Debug.printString("enemyfren");
            Direction[] targetDirs = Util.getInOrderDirections(dir);
            moveAndAttack(targetDirs, false);
        } else {
            if (!shouldStandGround()) {
                dir = chooseForwardDirection(dest);
                if (dir == null) {
                    Debug.printString("Fw bad; ");
                    tryAttackBestEnemy();
                    return;
                }
                Debug.printString("Fw, Dest: " + dir);
                Direction[] targetDirs = Util.getInOrderDirections(dir);
                moveAndAttack(targetDirs, false);
            } else {
                Debug.printString("standing ground");
                tryAttackBestEnemy();
            }
        }
    }

    // Keep loc in action radius if possible, then choose direction
    public Direction chooseForwardDirection(MapLocation loc) throws GameActionException {
        Direction Dir = currLoc.directionTo(loc);
        Direction[] dirsToConsider = Util.getInOrderDirections(Dir);
        int numDirs = 0;
        MapLocation newLoc;
        for (int i = dirsToConsider.length; --i >= 0;) {
            newLoc = currLoc.add(dirsToConsider[i]);
            if (newLoc.distanceSquaredTo(loc) <= actionRadiusSquared) {
                dirsToConsider[numDirs++] = dirsToConsider[i];
            }
        }

        // If no directions in action radius, just do normal chooseDirection
        if (numDirs == 0) {
            return chooseDirection(Util.getInOrderDirections(Dir));
        } else {
            Direction[] newDirs = new Direction[numDirs];
            System.arraycopy(dirsToConsider, 0, newDirs, 0, numDirs);
            return chooseDirection(newDirs);
        }
    }

    public Direction chooseBackupDirection(Direction Dir) throws GameActionException {
        Direction[] dirsToConsider = Util.getInOrderDirections(Dir);
        return chooseDirection(dirsToConsider);
    }

    // Direction priority breaks ties through
    // 1. Least number of enemies seen
    // 2. Least number of enemies in action radius
    // 3. Maximum average distance from enemies
    // 4. Minimum average distance to friends
    public Direction chooseDirection(Direction[] dirsToConsider) throws GameActionException {
        Direction bestDirSoFar = null;
        int bestEnemiesInVision = Integer.MAX_VALUE;
        int bestEnemiesInAction = Integer.MAX_VALUE;
        double bestAvgEnemyDist = Double.MAX_VALUE;
        double bestAvgFriendDist = Double.MAX_VALUE;
        RobotInfo enemy;
        MapLocation enemyLoc;

        Direction newDir;
        Direction extraDir;
        MapLocation targetLoc;
        boolean isPassible;
        boolean hasCloud;
        boolean currLocHasCloud;
        int enemiesInAction;
        int enemiesInVision;
        double avgEnemyDist;
        double avgFriendDist;
        for (int x = 0; x < dirsToConsider.length; x++) {
            newDir = dirsToConsider[x];
            if (rc.canMove(newDir)) {
                extraDir = Direction.CENTER;
                if (rc.onTheMap(currLoc.add(newDir))) {
                    extraDir = rc.senseMapInfo(currLoc.add(newDir)).getCurrentDirection();
                }
                targetLoc = currLoc.add(newDir).add(extraDir);
                hasCloud = rc.senseMapInfo(currLoc.add(newDir)).hasCloud();
                currLocHasCloud = rc.senseMapInfo(currLoc).hasCloud();
                // Debug.printString("best dir " + newDir);
                isPassible = rc.sensePassability(targetLoc);
                enemiesInAction = 0;
                enemiesInVision = 0;
                avgEnemyDist = 0;
                for (int i = enemyAttackable.length; --i >= 0;) {
                    enemy = enemyAttackable[i];
                    enemyLoc = enemy.getLocation();
                    if (enemyLoc.distanceSquaredTo(targetLoc) <= enemy.getType().actionRadiusSquared) {
                        enemiesInAction++;
                        enemiesInVision++;
                    } else if (enemyLoc.distanceSquaredTo(targetLoc) <= visionRadiusSquared) {
                        enemiesInVision++;
                    }
                    avgEnemyDist += enemyLoc.distanceSquaredTo(targetLoc);
                }
                avgEnemyDist /= enemyAttackable.length;
                if (isPassible && (!hasCloud || currLocHasCloud)) {
                    // Vision first
                    if (enemiesInVision < bestEnemiesInVision) {
                        bestDirSoFar = newDir;
                        bestEnemiesInVision = enemiesInVision;
                        bestEnemiesInAction = enemiesInAction;
                        bestAvgEnemyDist = avgEnemyDist;
                        bestAvgFriendDist = Double.MAX_VALUE;
                        continue;
                    } else if (enemiesInVision > bestEnemiesInVision) {
                        continue;
                    }

                    // Action radius second
                    if (enemiesInAction < bestEnemiesInAction) {
                        bestDirSoFar = newDir;
                        bestEnemiesInAction = enemiesInAction;
                        bestAvgEnemyDist = avgEnemyDist;
                        bestAvgFriendDist = Double.MAX_VALUE;
                        continue;
                    } else if (enemiesInAction > bestEnemiesInAction) {
                        continue;
                    }

                    // Distance third
                    if (avgEnemyDist > bestAvgEnemyDist) {
                        bestDirSoFar = newDir;
                        bestAvgEnemyDist = avgEnemyDist;
                        bestAvgFriendDist = Double.MAX_VALUE;
                        continue;
                    } else if (avgEnemyDist < bestAvgEnemyDist) {
                        continue;
                    }

                    // Min dist to friends fourth
                    // Avg friend dist is only calced if we need this tiebreaker
                    // Calc it for the bestLoc first if we haven't already,
                    // and then calc it for the targetLoc
                    if (bestAvgFriendDist == Double.MAX_VALUE) {
                        avgFriendDist = 0;
                        MapLocation bestLoc = currLoc.add(bestDirSoFar);
                        for (int i = FriendlySensable.length; --i >= 0;) {
                            avgFriendDist += FriendlySensable[i].getLocation().distanceSquaredTo(bestLoc);
                        }
                        avgFriendDist /= FriendlySensable.length;
                        bestAvgFriendDist = avgFriendDist;
                    }

                    avgFriendDist = 0;
                    for (int i = FriendlySensable.length; --i >= 0;) {
                        avgFriendDist += FriendlySensable[i].getLocation().distanceSquaredTo(targetLoc);
                    }
                    avgFriendDist /= FriendlySensable.length;
                    if (avgFriendDist < bestAvgFriendDist) {
                        bestDirSoFar = newDir;
                        bestAvgFriendDist = avgFriendDist;
                    }
                }
            }
        }

        return bestDirSoFar;
    }

    public void launcherExplore() throws GameActionException {
        MapLocation target;

        MapLocation symLoc = chooseSymmetricLoc();
        MapLocation combatSector = null;

        int combatSectorIdx = getPrioritizedCombatSectorIdx();
        if (combatSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
            combatSector = sectorCenters[combatSectorIdx];
        }

        if (combatSector != null && symLoc != null) {
            if (Util.manhattan(currLoc, combatSector) * Util.SYM_TO_COMB_DIST_RATIO < Util.manhattan(currLoc, symLoc)) {
                target = combatSector;
                Debug.printString("CombSec");
            } else {
                target = symLoc;
                markSymmetricLocSeen(target);
                Debug.printString("SymLoc");
            }
        } else if (combatSector != null) {
            target = combatSector;
            Debug.printString("CombSec");
        } else if (symLoc != null) {
            target = symLoc;
            markSymmetricLocSeen(target);
            Debug.printString("SymLoc");
        } else {
            int exploreSectorIdx = getNearestExploreSectorIdx();
            if (exploreSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
                target = sectorCenters[exploreSectorIdx];
                Debug.printString("ExpSec");
            } else {
                target = Explore.getExploreTarget();
                Debug.printString("Exploring");
            }
        }
        int numCloseFriendlies = 0;
        int overallFriendlyDx = 0;
        int overallFriendlyDy = 0;
        boolean closeEnough = false;
        for (RobotInfo Fbot : FriendlySensable) {
            RobotType FbotType = Fbot.getType();
            if (FbotType == RobotType.LAUNCHER || FbotType == RobotType.DESTABILIZER
                    && Fbot.getLocation().distanceSquaredTo(currLoc) <= RobotType.LAUNCHER.actionRadiusSquared) {
                MapLocation FbotLocation = Fbot.getLocation();
                numCloseFriendlies++;
                if (Util.distance(currLoc, FbotLocation) <= 1) {
                    closeEnough = true;
                } else {
                    MapLocation FbotLoc = Fbot.getLocation();
                    overallFriendlyDx += FbotLoc.x - currLoc.x;
                    overallFriendlyDy += FbotLoc.y - currLoc.y;
                }
            }
        }
        // if (numCloseFriendlies != 0
        // && !closeEnough) {
        // MapLocation friendDirection = currLoc.add(
        // currLoc.directionTo(new MapLocation(currLoc.x + overallFriendlyDx, currLoc.y
        // + overallFriendlyDy)));
        // tryAttackBestEnemy();
        // MapLocation newTarget = currLoc.add(currLoc.directionTo(target)).add(
        // currLoc.directionTo(friendDirection));
        // Debug.printString("target: " + target + "friend target: " + friendDirection +
        // "new target: " + newTarget);
        // Nav.move(newTarget);
        // return;
        // }
        Debug.setIndicatorLine(Debug.INDICATORS, currLoc, target, 255, 0, 200);
        if (currLoc.distanceSquaredTo(target) <= Util.JUST_OUTSIDE_OF_VISION_RADIUS) {
            Nav.move(target); // tryMoveSafely
            Debug.printString("saf mov");
        } else {
            Nav.move(target);
            Debug.printString("reg mov");
        }
        tryAttackBestEnemy();
    }

    public void markSymmetricLocSeen(MapLocation target) throws GameActionException {
        if (currLoc.distanceSquaredTo(target) <= actionRadiusSquared / 2) {
            seenEnemyHQLocs.add(target);
        }
    }

    public MapLocation chooseSymmetricLoc() throws GameActionException {
        MapLocation bestLoc = null;
        int bestDist = Integer.MAX_VALUE;
        for (int i = 0; i < enemyHQs.length; i++) {
            MapLocation possibleLoc = enemyHQs[i];
            int currDist = currLoc.distanceSquaredTo(possibleLoc);
            int controlStatus = Comms.readSectorControlStatus(whichSector(possibleLoc));
            boolean notTraversed = controlStatus == Comms.ControlStatus.UNKNOWN ||
                    controlStatus == Comms.ControlStatus.EXPLORING;
            if (!seenEnemyHQLocs.contains(possibleLoc) && currDist < bestDist && notTraversed) {
                bestLoc = possibleLoc;
                bestDist = currDist;
            }
        }
        return bestLoc;
    }

    public void attackClouds() throws GameActionException {
        if (!rc.isActionReady())
            return;

        currLoc = rc.getLocation();
        MapInfo info = rc.senseMapInfo(currLoc);
        int nextCooldown = rc.getActionCooldownTurns() +
                (int) (RobotType.LAUNCHER.actionCooldown * info.getCooldownMultiplier(team));

        MapLocation attackLoc = null;
        if (info.hasCloud()) {
            // If attacking out of a cloud would put us over the cooldown limit for
            // NEXT turn, don't attack
            if (nextCooldown >= GameConstants.COOLDOWNS_PER_TURN + GameConstants.COOLDOWN_LIMIT)
                return;
            if (lastClosestAttackingEnemy != null) {
                if (currLoc.distanceSquaredTo(lastClosestAttackingEnemy) <= actionRadiusSquared
                        && !rc.canSenseLocation(lastClosestAttackingEnemy)) {
                    // If we're in a cloud, attack the last enemy you saw, if it's still in range
                    attackLoc = lastClosestAttackingEnemy;
                } else {
                    // Or pick a spot in the direction of the last enemy you saw
                    attackLoc = Util.getRandomAttackLoc(currLoc.directionTo(lastClosestAttackingEnemy));
                }
            } else {
                // If you haven't seen an enemy ever, attack towards a random enemyHQ
                MapLocation enemyHQ = enemyHQs[FastMath.nextInt(enemyHQs.length)];
                attackLoc = Util.getRandomAttackLoc(currLoc.directionTo(enemyHQ));
            }
        } else {
            // If attacking a cloud would put us over the cooldown limit, don't attack
            if (nextCooldown > GameConstants.COOLDOWNS_PER_TURN)
                return;

            // If we're not in a cloud, attack the closest one that we can't sense
            MapLocation[] clouds = rc.senseNearbyCloudLocations(currLoc, actionRadiusSquared);
            int bestCloudDist = Integer.MAX_VALUE;
            for (int i = clouds.length; --i >= 0;) {
                if (rc.canSenseLocation(clouds[i]))
                    continue;
                int dist = currLoc.distanceSquaredTo(clouds[i]);
                if (dist < bestCloudDist) {
                    bestCloudDist = dist;
                    attackLoc = clouds[i];
                }
            }
        }

        if (attackLoc != null && rc.canAttack(attackLoc)) {
            rc.attack(attackLoc);
        }
    }
}
