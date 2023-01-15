package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

public class Amplifier extends Robot {

    static RobotInfo[] enemyAttackable;
    static RobotInfo[] friendlyAttackable;
    RobotInfo closestEnemy;
    RobotInfo closestFriendly;

    static int numFriendlyLaunchers;
    static int numFriendlies;
    static int numCloseFriendlies;
    static int numEnemies;
    static MapLocation closestAttackingEnemy;
    static MapLocation closestEnemyLocation;
    static int numEnemyLaunchersAttackingUs;

    static int numEnemyLaunchers;
    static double overallEnemyLauncherDx;
    static double overallEnemyLauncherDy;

    public Amplifier(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        enemyAttackable = getEnemyAttackable();
        friendlyAttackable = getFriendlyAttackable();
        closestEnemy = getClosestRobot(enemyAttackable);
        closestFriendly = getClosestRobot(friendlyAttackable);

        resetShouldRunAway();
        if (!moveAwayFromEnemy()) {
            amplifierExplore();
        }
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
                        lowestHealth = botHealth;
                    }

                } else {
                    if (!inActionRadius && botHealth < lowestHealth
                            || (botHealth == lowestHealth && candidateDist < closestLauncherDist)) {
                        closestLauncherDist = candidateDist;
                        closestAttackingEnemy = candidateLoc;
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
                if ((FbotLocation).distanceSquaredTo(closestEnemyLocation) <= FbotType.visionRadiusSquared) {
                    overallEnemyLauncherDx += (FbotLocation.x - currLoc.x);
                    overallEnemyLauncherDy += (FbotLocation.y - currLoc.y);
                    numFriendlies++;

                }
            }
        }
    }

    public boolean moveAwayFromEnemy() throws GameActionException {
        MapLocation dest;
        Direction dir = null;
        if (shouldRunAway()) {
            dest = currLoc.translate(-(int) (overallEnemyLauncherDx), -(int) (overallEnemyLauncherDy));// (overallFriendlyLauncherDx,
            // overallFriendlyLauncherDy);
            dir = currLoc.directionTo(dest);
            Debug.printString("t: " + rc.getMovementCooldownTurns() + " " + dir + " " + overallEnemyLauncherDx + " "
                    + overallEnemyLauncherDy);
            Debug.printString("RA, Dest: " + dir);
            Direction[] targetDirs = Util.getInOrderDirections(dir);
            tryMoveDest(targetDirs);
            return true;
        }
        return false;
    }

    public boolean shouldRunAway() throws GameActionException {
        String str = "";
        MapLocation target = null;
        // Run away if either
        // - You see fewer friendly attackers than enemy attackers
        // - the closest enemy is closer than the clsoest friendly
        if (closestEnemy != null) {
            if (enemyAttackable.length + 5 >= friendlyAttackable.length) {
                target = Pathfinding.getGreedyTargetAway(closestEnemy.getLocation());
                str = "Enemies++ " + closestEnemy.type;
            }

            if (closestFriendly != null &&
                    currLoc.distanceSquaredTo(closestEnemy.location) < currLoc
                            .distanceSquaredTo(closestFriendly.location)) {
                target = Pathfinding.getGreedyTargetAway(closestEnemy.getLocation());
                str = "Close enemy " + closestEnemy.type;
            }

            Debug.printString(str);
        }
        return target != null;
    }

    public void amplifierExplore() throws GameActionException {
        MapLocation target;

        MapLocation otherAmplifier = null;
        for (RobotInfo bot : FriendlySensable) {
            if (bot.getType() == RobotType.AMPLIFIER) {
                otherAmplifier = bot.getLocation();
            }
        }

        if (otherAmplifier != null) {
            // go away from other friendly amplifiers
            Direction opposite = otherAmplifier.directionTo(currLoc);
            tryMoveDest(Util.getInOrderDirections(opposite));
            return;
        }

        // find center of all friendly attackable nearby
        int sumOfFriendlyAttackableDx = 0;
        int sumOfFriendlyAttackableDy = 0;
        for (RobotInfo bot : friendlyAttackable) {
            sumOfFriendlyAttackableDx += bot.getLocation().x - currLoc.x;
            sumOfFriendlyAttackableDy += bot.getLocation().y - currLoc.y;
        }

        MapLocation centerOfFriends = null;
        int numFriendlyAttackable = friendlyAttackable.length;
        if (numFriendlyAttackable > 0) {
            centerOfFriends = new MapLocation(currLoc.x + sumOfFriendlyAttackableDx / numFriendlyAttackable,
                    currLoc.y + sumOfFriendlyAttackableDy / numFriendlyAttackable);
        }

        int combatSectorIdx = getPrioritizedCombatSectorIdx();
        boolean goingToFriends = false;
        // go towards friends only if ur not close to home and theres no nearby islands
        // if ur close to home, want to go into battle more
        // if you're near an island, want to go into battle more
        if (centerOfFriends != null &&
                currLoc.distanceSquaredTo(home) > 2 * visionRadiusSquared &&
                rc.senseNearbyIslands().length == 0) {
            goingToFriends = true;
            Direction friendsToHomeDir = centerOfFriends.directionTo(home);
            // go slightly behind your friends
            Debug.printString("staying behind");
            target = centerOfFriends.add(friendsToHomeDir).add(friendsToHomeDir).add(friendsToHomeDir);
        } else if (combatSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
            Debug.printString("going for it");
            target = sectorCenters[combatSectorIdx];
        } else {
            target = Explore.getExploreTarget();
            Debug.printString("exploring");
        }

        Debug.setIndicatorLine(Debug.INDICATORS, currLoc, target, 255, 0, 200);
        // oscillate near just outside of sector
        if (currLoc.distanceSquaredTo(target) <= Util.AMP_JUST_INSIDE_VISION_RADIUS && !goingToFriends) {
            // only move away if ur close to combat sector or ur NOT moving towards friends,
            // if ur going to friends then u should go directly to that location
            Pathfinding.move(home);
            Debug.printString("move home");
        } else {
            Pathfinding.move(target);
            Debug.printString("move to target");
        }
    }

}
