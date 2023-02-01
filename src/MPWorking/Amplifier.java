package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

public class Amplifier extends Robot {
    static enum AmplifierState {
        CHILLING,
        EXPLORING,
    }

    static AmplifierState currState;

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

    static int roundsSinceStartedExploring;

    static int claimedExploreSector;
    static MapLocation exploreSectorLoc;

    public Amplifier(RobotController r) throws GameActionException {
        super(r);
        currState = AmplifierState.CHILLING;
        claimedExploreSector = Comms.UNDEFINED_SECTOR_INDEX;
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        heartbeat();

        enemyAttackable = getEnemyAttackable();
        friendlyAttackable = getFriendlyAttackable();
        closestEnemy = getClosestRobot(enemyAttackable);
        closestFriendly = getClosestRobot(friendlyAttackable);

        trySwitchState();
        Debug.printString("" + currState);
        doStateAction();
    }

    public void trySwitchState() throws GameActionException {
        // if ur near 3 friends set that target and follow them for 15 rounds
        switch (currState) {
            case CHILLING:
                break;
            case EXPLORING:
                // only be going to new destination for 15 rounds then restart normal procedures
                if ((exploreSectorLoc != null
                        && currLoc.distanceSquaredTo(exploreSectorLoc) <= Util.AMP_JUST_INSIDE_VISION_RADIUS)
                        || roundsSinceStartedExploring > Util.AMP_ROUNDS_TO_EXPLORE || friendlyAttackable.length > 3) {
                    // unclaim the index
                    Comms.writeExploreSectorClaimStatus(claimedExploreSector, Comms.ClaimStatus.UNCLAIMED);
                    currState = AmplifierState.CHILLING;
                    roundsSinceStartedExploring = 0;
                    claimedExploreSector = Comms.UNDEFINED_SECTOR_INDEX;
                    exploreSectorLoc = null;
                }
                roundsSinceStartedExploring++;
                break;

        }
    }

    public void switchState(AmplifierState state) throws GameActionException {
        currState = state;
        if (currState == AmplifierState.EXPLORING) {
            // switching into exploring, grab ur sector
            if (claimedExploreSector == Comms.UNDEFINED_SECTOR_INDEX) {
                claimedExploreSector = getNearestExploreSectorIdx();
                if (claimedExploreSector != Comms.UNDEFINED_SECTOR_INDEX) {
                    exploreSectorLoc = sectorCenters[claimedExploreSector];
                }
            }
            roundsSinceStartedExploring = 0;
        }
    }

    public void doStateAction() throws GameActionException {
        switch (currState) {
            case CHILLING:
                resetShouldRunAway();
                if (!moveAwayFromEnemy()) {
                    amplifierExplore();
                }
                break;
            case EXPLORING:
                resetShouldRunAway();
                if (!moveAwayFromEnemy()) {
                    goToNewExploreSector();
                }
                break;
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

    public void goToNewExploreSector() throws GameActionException {
        MapLocation target;
        // grab a sector if u dont have one yet
        if (claimedExploreSector == Comms.UNDEFINED_SECTOR_INDEX) {
            claimedExploreSector = getNearestExploreSectorIdx();
            if (claimedExploreSector != Comms.UNDEFINED_SECTOR_INDEX) {
                exploreSectorLoc = sectorCenters[claimedExploreSector];
            }
        }

        if (claimedExploreSector != Comms.UNDEFINED_SECTOR_INDEX) {
            target = exploreSectorLoc;
        } else {
            Debug.printString("2");
            target = Explore.getExploreTarget();
        }
        Debug.printString("exploring");
        Debug.setIndicatorLine(Debug.INDICATORS, currLoc, target, 255, 0, 200);
        Pathfinding.move(target);
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
        Debug.printString("" + combatSectorIdx);
        boolean goingToFriends = false;
        // go towards friends only if ur not close to home and theres no nearby islands
        // if ur close to home, want to go into battle more
        // if you're near an island you own, want to go into battle more

        // TODO: go to exploreSector after getting to ur destination
        if (centerOfFriends != null &&
                currLoc.distanceSquaredTo(home) > visionRadiusSquared) {
            goingToFriends = true;
            Direction homeToFriendsDir = home.directionTo(centerOfFriends);
            // go slightly behind your friends
            Debug.printString("staying behind");
            target = centerOfFriends.add(homeToFriendsDir).add(homeToFriendsDir).add(homeToFriendsDir);
        } else if (combatSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
            Debug.printString("going for it");
            target = sectorCenters[combatSectorIdx];
        } else {
            goToNewExploreSector();
            return;
        }

        // oscillate near just outside of sector
        if ((!goingToFriends && currLoc.distanceSquaredTo(target) <= Util.AMP_JUST_INSIDE_VISION_RADIUS)) {
            // go explore for a bit
            Debug.printString("switching to explore for a bit");
            switchState(AmplifierState.EXPLORING);
            goToNewExploreSector();
        } else {
            Debug.setIndicatorLine(Debug.INDICATORS, currLoc, target, 255, 0, 200);
            Pathfinding.move(target);
            Debug.printString("move to target");
        }
    }

    public void heartbeat() throws GameActionException {
        // Num amps is only 2 bits large. Don't overflow it
        Comms.writeOurHqNumAmps(homeIdx, Math.min(Comms.readOurHqNumAmps(homeIdx) + 1, 3));
    }

}
