package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

import MPWorking.fast.*;

public class Launcher extends Robot {
    static enum LauncherState {
        EXPLORING,
        ATTACKING,
        RUNNING,
        REPORTING,
        HELPING, // UNUSED
        HEALING,
        WAITING,
    }

    static LauncherState currState;

    static int numEnemyLaunchers;
    static double overallEnemyLauncherDx;
    static double overallEnemyLauncherDy;

    static int numFriendlyLaunchers;
    static RobotInfo closestEnemy;
    static int numAggressiveFriendlies;
    static int numFriendlies;
    static int numCloseFriendlies;
    static int numEnemies;
    static MapLocation closestAttackingEnemy;
    static MapLocation lastClosestAttackingEnemy;
    static int turnSawLastClosestAttackingEnemy;
    static int enemyAttackingHealth;
    static int lastEnemyAttackingHealth;
    static int friendlyAttackingHealth;
    static int numEnemyLaunchersAttackingUs;
    public static final int LAST_ATTACKING_ENEMY_TIMEOUT = 2;

    static boolean healthLow;
    static boolean healthHigh;

    static FastLocSet seenSymmetricLocs;
    static int HQwaitCounter;
    static boolean goingToSymLoc;
    static FastLocSet manaSymSet;
    static MapLocation[] symManaSectors;
    static boolean isSymLocHQ;
    static MapLocation exploreTarget;

    static boolean hasReported;

    static FastIntIntMap idToHealth;
    static FastIntIntMap idToTurnInjured;
    static FastIntLocMap idToLocInjured;
    static MapLocation closestInjured;
    public static final int HEALTH_DECREASED_TIMEOUT = 2;

    static int closestFriendlyIslandIdx;
    static MapLocation firstFriendlyIslandLoc;
    static boolean visitedSectorCenter;

    static int turnsSpentAtHQ;

    static int turnsFollowedExploreTarget;
    static int EXPLORE_TARGET_TIMEOUT;
    static final int EXPLORE_TIMEOUT_MULT = 10;

    public static final int MAX_TURNS_SPENT_AT_HQ = 5;

    public static final int MAX_LAUNCHERS_PER_ENEMY_HQ = 4;
    public static final int LOW_HEALTH_REPORT_THRESHOLD = 30;
    public static final int LOW_HEALTH_THRESHOLD = 20;
    public static final int LOW_HEALTH_DIFF = 20;
    public static final int HIGH_HEALTH_DIFF = 30;

    public static final int LOW_HEAL_THRESHOLD = 60;
    public static final int HIGH_HEAL_THRESHOLD = 100;

    public static final int MAX_SYM_MANA_SECTORS = 2;

    public static final int ID = 11785;

    public Launcher(RobotController r) throws GameActionException {
        super(r);
        currState = LauncherState.EXPLORING;
        seenSymmetricLocs = new FastLocSet();
        HQwaitCounter = 0;
        hasReported = false;
        manaSymSet = new FastLocSet();
        idToHealth = new FastIntIntMap();
        idToTurnInjured = new FastIntIntMap();
        idToLocInjured = new FastIntLocMap();
        firstFriendlyIslandLoc = null;
        turnsFollowedExploreTarget = 0;
        EXPLORE_TARGET_TIMEOUT = GameConstants.GAME_MAX_NUMBER_OF_ROUNDS;
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        closestEnemy = getBestEnemy(EnemySensable);
        resetLocalEnemyInformation();

        enemyAttackable = getEnemyAttackable();
        numEnemies = enemyAttackable.length;
        closestFriendlyIslandIdx = Comms.UNDEFINED_SECTOR_INDEX;
        updateSymManaSectors();
        loadExploreTarget();

        trySwitchState();
        Debug.printString("S: " + currState);
        doStateAction();
        attackClouds();
    }

    public void resetLocalEnemyInformation() throws GameActionException {
        numEnemyLaunchersAttackingUs = 0;
        numAggressiveFriendlies = 0;
        numFriendlies = 0;
        numCloseFriendlies = 0;
        closestAttackingEnemy = null;
        numEnemies = 0;
        overallEnemyLauncherDx = 0;
        overallEnemyLauncherDy = 0;
        if (HQwaitCounter > 0) {
            HQwaitCounter--;
        }
        closestInjured = null;
        enemyAttackingHealth = 0;
        friendlyAttackingHealth = rc.getHealth();

        int closestLauncherDist = Integer.MAX_VALUE;
        RobotInfo closestEnemyInfo = null;
        int lowestHealth = Integer.MAX_VALUE;
        boolean inActionRadius = false;
        int numAttackingEnemyCount = 0;
        RobotInfo bot;
        int botHealth;
        MapLocation candidateLoc;
        int candidateDist;
        RobotType botType;
        for (int i = 0; i < EnemySensable.length; i++) {
            bot = EnemySensable[i];
            botHealth = bot.getHealth();
            candidateLoc = bot.getLocation();
            candidateDist = currLoc.distanceSquaredTo(candidateLoc);
            botType = bot.getType();
            if (botType == RobotType.LAUNCHER || botType == RobotType.DESTABILIZER) {
                enemyAttackingHealth += botHealth;
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
            } else if (botType == RobotType.HEADQUARTERS) {
                if (candidateDist <= RobotType.HEADQUARTERS.actionRadiusSquared) {
                    numAttackingEnemyCount++;
                    overallEnemyLauncherDx += (candidateLoc.x - currLoc.x);
                    overallEnemyLauncherDy += (candidateLoc.y - currLoc.y);
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
            turnSawLastClosestAttackingEnemy = rc.getRoundNum();
            lastEnemyAttackingHealth = enemyAttackingHealth;
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

        RobotInfo Fbot;
        RobotType FbotType;
        MapLocation FbotLocation;
        for (int i = FriendlySensable.length; --i >= 0;) {
            Fbot = FriendlySensable[i];
            FbotType = Fbot.getType();
            switch (FbotType) {
                case LAUNCHER:
                case DESTABILIZER:
                    FbotLocation = Fbot.getLocation();
                    overallEnemyLauncherDx += (currLoc.x - FbotLocation.x);
                    overallEnemyLauncherDy += (currLoc.y - FbotLocation.y);
                    if ((FbotLocation).distanceSquaredTo(closestEnemyLocation) <= FbotType.visionRadiusSquared) {
                        numFriendlies++;
                    }
                    numAggressiveFriendlies++;
                    friendlyAttackingHealth += Fbot.getHealth();
                    break;
                default:
                    break;
            }

            // NOTE: CAN CAUSE BYTECODE ISSUES ON INIT (on big maps)
            // if (idToHealth.contains(Fbot.ID)) {
            // int prevHealth = idToHealth.getVal(Fbot.ID);
            // if (prevHealth > Fbot.health) {
            // idToTurnInjured.addReplace(Fbot.ID, rc.getRoundNum());
            // idToLocInjured.addReplace(Fbot.ID, Fbot.location);
            // }
            // idToHealth.addReplace(Fbot.ID, Fbot.health);
            // } else {
            // idToHealth.add(Fbot.ID, Fbot.health);
            // }
        }

        int[] ids = idToTurnInjured.getKeys();
        int id;
        for (int i = ids.length; --i >= 0;) {
            id = ids[i];
            if (idToTurnInjured.getVal(id) + HEALTH_DECREASED_TIMEOUT < rc.getRoundNum()) {
                idToTurnInjured.remove(id);
            }
        }

        closestInjured = getClosestInjured();
    }

    public boolean shouldWait() {
        boolean tooSoon = turnSawLastClosestAttackingEnemy + LAST_ATTACKING_ENEMY_TIMEOUT >= rc.getRoundNum();
        // boolean notMod4 = rc.getRoundNum() % 4 != 0;
        return false; // tooSoon;
    }

    public boolean shouldHelpInjured() {
        // return closestInjured != null &&
        // !rc.getLocation().isAdjacentTo(closestInjured);
        return false;
    }

    public void trySwitchState() throws GameActionException {
        switch (currState) {
            case REPORTING:
                if (sectorToReport == 0) {
                    hasReported = true;
                }

                if (closestEnemy != null) {
                    if (shouldRunAway()) {
                        currState = LauncherState.RUNNING;
                    } else if (shouldHelpInjured()) {
                        currState = LauncherState.HELPING;
                    } else {
                        if (closestEnemy.getType() == RobotType.HEADQUARTERS) {
                            MapLocation closestEnemyLocation = closestEnemy.getLocation();
                            int ourDist = currLoc.distanceSquaredTo(closestEnemyLocation);
                            int numTroopsCloser = rc.senseNearbyRobots(closestEnemyLocation, ourDist - 1,
                                    rc.getTeam()).length;
                            int exploreTargetDist = Util.distance(currLoc, exploreTarget);
                            if (HQwaitCounter != 0 ||
                                    numTroopsCloser >= MAX_LAUNCHERS_PER_ENEMY_HQ ||
                                    exploreTargetDist <= Util.EXP_TARGET_DIST_TO_LEAVE_HQ) {
                                currState = LauncherState.EXPLORING;
                                HQwaitCounter = 8;
                                Debug.println("Switching to exploring because of HQ");
                                return;
                            }
                            if (ourDist <= RobotType.HEADQUARTERS.actionRadiusSquared) {
                                currState = LauncherState.RUNNING;
                                return;
                            }
                        }
                        currState = LauncherState.ATTACKING;
                    }
                } else if (hasReported) {
                    if (shouldHeal()) {
                        enterHealing();
                    } else {
                        currState = LauncherState.EXPLORING;
                    }
                }
                break;
            case HEALING:
                closestFriendlyIslandIdx = getClosestFriendlyIslandIdx();
                if (closestFriendlyIslandIdx == Comms.UNDEFINED_SECTOR_INDEX
                        || rc.getHealth() == rc.getType().health) {
                    if (shouldRunAway()) {
                        currState = LauncherState.RUNNING;
                    } else if (shouldHelpInjured()) {
                        currState = LauncherState.HELPING;
                    } else {
                        currState = LauncherState.EXPLORING;
                    }
                }
                break;
            default:
                if (shouldHeal()) {
                    enterHealing();
                } else if (closestEnemy != null) {
                    if (shouldRunAway()) {
                        currState = LauncherState.RUNNING;
                    } else if (shouldHelpInjured()) {
                        currState = LauncherState.HELPING;
                    } else {
                        if (closestEnemy.getType() == RobotType.HEADQUARTERS) {
                            MapLocation closestEnemyLocation = closestEnemy.getLocation();
                            int ourDist = currLoc.distanceSquaredTo(closestEnemyLocation);
                            int numTroopsCloser = rc.senseNearbyRobots(closestEnemyLocation, ourDist - 1,
                                    rc.getTeam()).length;
                            turnsSpentAtHQ++;
                            if (HQwaitCounter != 0 ||
                                    numTroopsCloser >= MAX_LAUNCHERS_PER_ENEMY_HQ ||
                                    turnsSpentAtHQ >= MAX_TURNS_SPENT_AT_HQ) {
                                currState = LauncherState.EXPLORING;
                                HQwaitCounter = 5;
                                return;
                            }
                            if (ourDist <= RobotType.HEADQUARTERS.actionRadiusSquared) {
                                currState = LauncherState.RUNNING;
                                return;
                            }
                        } else {
                            turnsSpentAtHQ = 0;
                        }
                        currState = LauncherState.ATTACKING;
                    }
                } else if (shouldHelpInjured()) {
                    currState = LauncherState.HELPING;
                } else if (shouldWait()) {
                    Debug.printString("Waiting");
                    currState = LauncherState.WAITING;
                } else if (numAggressiveFriendlies == 0 &&
                        rc.getHealth() <= LOW_HEALTH_REPORT_THRESHOLD &&
                        !hasReported) {
                    // If you don't see any enemies or friends, and your health is low. Go report.
                    currState = LauncherState.REPORTING;
                    sectorToReport = 1;
                } else {
                    currState = LauncherState.EXPLORING;
                }
                break;
        }
    }

    public void enterHealing() {
        currState = LauncherState.HEALING;
        firstFriendlyIslandLoc = null;
        visitedSectorCenter = false;
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
            case REPORTING:
                if (closestEnemy != null && shouldRunAway()) {
                    runAwayFromEnemy();
                } else {
                    Nav.move(home);
                }
                break;
            case HELPING:
                helpLauncher();
                break;
            case HEALING:
                goToNearestIsland();
                break;
            case WAITING:
                break;
        }
    }

    public void moveAndAttack(Direction dir) throws GameActionException {
        // Sync launcher movement
        if (rc.getRoundNum() % 2 == 0) {
            return;
        }

        if (!rc.canMove(dir)) {
            tryAttackBestEnemy();
            return;
        }

        RobotInfo beforeMoveEnemy = getBestEnemy();
        MapLocation nextLoc = rc.getLocation().add(dir);
        RobotInfo afterMoveEnemy = getBestEnemy(rc.senseNearbyRobots(nextLoc, actionRadiusSquared, team.opponent()));

        RobotInfo bestEnemy = getBetterEnemy(beforeMoveEnemy, afterMoveEnemy);
        tryAttackBestEnemy(bestEnemy);
        rc.move(dir);
        tryAttackBestEnemy(bestEnemy);
    }

    public void moveAndAttack(MapLocation loc) throws GameActionException {
        tryAttackBestEnemy();
        Nav.move(loc);
        tryAttackBestEnemy();
    }

    public void helpLauncher() throws GameActionException {
        MapLocation closestInjured = getClosestInjured();
        if (closestInjured == null) {
            moveTowardsEnemy();
            return;
        }

        Direction dir = currLoc.directionTo(closestInjured);
        Direction[] dirs = new Direction[] { dir, dir.rotateLeft(), dir.rotateRight() };
        dir = chooseDirection(dirs);
        if (dir == null) {
            Debug.printString("Help bad");
            tryAttackBestEnemy();
            return;
        }

        Debug.printString("Help");
        Debug.setIndicatorDot(closestInjured, 5, 240, 240);
        Debug.setIndicatorLine(currLoc, closestInjured, 5, 240, 240);
        moveAndAttack(dir);
    }

    public MapLocation getClosestInjured() throws GameActionException {
        MapLocation closestInjured = null;
        MapLocation closestSensableInjured = null;
        MapLocation closestUnsensableInjured = null;
        int closestSensableDist = Integer.MAX_VALUE;
        int closestUnsensableDist = Integer.MAX_VALUE;
        int[] ids = idToTurnInjured.getKeys();
        int dist;
        RobotInfo robot;
        MapLocation loc;
        for (int i = ids.length; --i >= 0;) {
            if (rc.canSenseRobot(ids[i])) {
                robot = rc.senseRobot(ids[i]);
                loc = robot.getLocation();
                dist = currLoc.distanceSquaredTo(loc);
                if (dist < closestSensableDist) {
                    closestSensableDist = dist;
                    closestSensableInjured = loc;
                }
            } else {
                loc = idToLocInjured.getLoc(ids[i]);
                dist = currLoc.distanceSquaredTo(loc);
                if (dist < closestUnsensableDist) {
                    closestUnsensableDist = dist;
                    closestUnsensableInjured = loc;
                }
            }
        }

        if (closestSensableInjured != null) {
            closestInjured = closestSensableInjured;
        } else if (closestUnsensableInjured != null) {
            closestInjured = closestUnsensableInjured;
        }
        return closestInjured;
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

    // If this returns true, loads the friendly island
    public boolean shouldHeal() throws GameActionException {
        if (rc.getHealth() <= LOW_HEAL_THRESHOLD) {
            closestFriendlyIslandIdx = getClosestFriendlyIslandIdx();
            return closestFriendlyIslandIdx != Comms.UNDEFINED_SECTOR_INDEX;
        }

        // If a friendly robot is under the low heal treshold and you're under
        // the high heal threshold, also go heal
        if (rc.getHealth() > HIGH_HEAL_THRESHOLD)
            return false;

        RobotInfo robot;
        for (int i = FriendlySensable.length; --i >= 0;) {
            robot = FriendlySensable[i];
            if (robot.getType() == RobotType.LAUNCHER && robot.getHealth() <= LOW_HEAL_THRESHOLD) {
                closestFriendlyIslandIdx = getClosestFriendlyIslandIdx();
                return closestFriendlyIslandIdx != Comms.UNDEFINED_SECTOR_INDEX;
            }
        }

        return false;
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
            tryAttackBestEnemy();
            return;
        }
        Debug.printString("RA, Dest: " + dir);
        moveAndAttack(dir);
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
            MapLocation closestEnemyLocation = closestEnemy.getLocation();
            if (currLoc.add(currLoc.directionTo(closestEnemyLocation))
                    .distanceSquaredTo(closestEnemyLocation) > RobotType.HEADQUARTERS.actionRadiusSquared) {
                Debug.printString("closing in");
                Nav.move(closestEnemyLocation);
                tryAttackBestEnemy();
            } else {
                Debug.printString("Rotating");
                moveSafely(dest, RobotType.HEADQUARTERS.actionRadiusSquared);
                tryAttackBestEnemy();
            }
        } else if (closestEnemyType == RobotType.CARRIER
                || closestEnemyType == RobotType.AMPLIFIER || closestEnemyType == RobotType.BOOSTER) {
            Debug.printString("enemyfren");
            dir = chooseForwardDirection(dest);
            if (dir == null) {
                Debug.printString("Fw bad; ");
                tryAttackBestEnemy();
                return;
            }
            Debug.printString("Fw, Dest: " + dir);
            moveAndAttack(dir);
        } else {
            if (!shouldStandGround()) {
                dir = chooseForwardDirection(dest);
                if (dir == null) {
                    Debug.printString("Fw bad; ");
                    tryAttackBestEnemy();
                    return;
                }
                Debug.printString("Fw, Dest: " + dir);
                moveAndAttack(dir);
            } else {
                Debug.printString("standing ground");
                tryAttackBestEnemy();
            }
        }
    }

    public void loadExploreTarget() throws GameActionException {
        MapLocation target;
        goingToSymLoc = false;
        MapLocation symLoc = chooseSymmetricLoc();
        MapLocation combatSector = null;

        int combatSectorIdx = getPrioritizedCombatSectorIdx();
        if (combatSectorIdx != Comms.UNDEFINED_SECTOR_INDEX) {
            combatSector = sectorCenters[combatSectorIdx];
        }

        if (lastClosestAttackingEnemy != null
                && turnSawLastClosestAttackingEnemy + LAST_ATTACKING_ENEMY_TIMEOUT >= rc.getRoundNum()
                && friendlyAttackingHealth >= lastEnemyAttackingHealth) {
            Debug.printString("LastEnemy");
            target = lastClosestAttackingEnemy;
        } else if (combatSector != null && symLoc != null) {
            int combSecDist = Util.manhattan(currLoc, combatSector);
            int symLocDist = Util.manhattan(currLoc, symLoc);
            int controlStatus = Comms.readSectorControlStatus(combatSectorIdx);
            boolean protect = combSecDist * Util.SYM_TO_COMB_DIST_RATIO < symLocDist;
            boolean closeToHQ = Util.manhattan(combatSector,
                    getClosestFriendlyHQ(combatSector)) <= Util.COMB_TO_HOME_DIST;
            boolean protectAggressive = combSecDist * Util.SYM_TO_COMB_HOME_AGGRESSIVE_DIST_RATIO < symLocDist &&
                    controlStatus == Comms.ControlStatus.ENEMY_AGGRESIVE;
            boolean protectPassive = (combSecDist * Util.SYM_TO_COMB_HOME_PASSIVE_DIST_RATIO < symLocDist &&
                    controlStatus == Comms.ControlStatus.ENEMY_PASSIVE);
            if (protect || (closeToHQ && (protectAggressive || protectPassive))) {
                target = combatSector;
                Debug.printString("PrefCombSec");
            } else {
                target = symLoc;
                markSymmetricLocSeen(target);
                goingToSymLoc = true;
                Debug.printString("PrefSymLoc");
            }
        } else if (combatSector != null) {
            target = combatSector;
            Debug.printString("CombSec");
        } else if (symLoc != null) {
            target = symLoc;
            markSymmetricLocSeen(target);
            goingToSymLoc = true;
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

        if (goingToSymLoc) {
            // Time out sym locs after a while because we are not guaranteed it
            // is reachable.
            if (!target.equals(exploreTarget)) {
                turnsFollowedExploreTarget = 0;
                EXPLORE_TARGET_TIMEOUT = EXPLORE_TIMEOUT_MULT * Util.distance(currLoc, target);
            }
        } else {
            EXPLORE_TARGET_TIMEOUT = GameConstants.GAME_MAX_NUMBER_OF_ROUNDS;
        }

        exploreTarget = target;
    }

    public void launcherExplore() throws GameActionException {
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
        if (currLoc.distanceSquaredTo(exploreTarget) <= Util.JUST_OUTSIDE_OF_VISION_RADIUS) {
            Nav.move(exploreTarget); // tryMoveSafely
            Debug.printString("saf mov");
        } else {
            Nav.move(exploreTarget);
            Debug.printString("reg mov");
        }
        if (goingToSymLoc) {
            markSymmetricLocSeen(exploreTarget);
        }
        turnsFollowedExploreTarget++;
        tryAttackBestEnemy();
    }

    public void markSymmetricLocSeen(MapLocation target) throws GameActionException {
        if (rc.canSenseLocation(target)) {
            RobotInfo robot = rc.senseRobotAtLocation(target);
            if (isSymLocHQ) {
                if (robot == null || robot.getType() != RobotType.HEADQUARTERS ||
                        rc.getLocation().distanceSquaredTo(target) <= actionRadiusSquared) {
                    seenSymmetricLocs.add(target);
                }
            } else if (rc.getLocation().distanceSquaredTo(target) <= 8) {
                seenSymmetricLocs.add(target);
            } else if (!rc.sensePassability(target)) {
                // If the target is not passable, it is probably not reachable.
                // Check for at least 3 other adjacent tiles that are not passable
                // and mark it as seen if so.
                // Theoretically we might want to remove the symmetry that this target
                // is a part of. But that seems a little risky.
                int numNotPassable = 0;
                for (Direction dir : Util.directions) {
                    if (rc.canSenseLocation(target.add(dir)) &&
                            !rc.sensePassability(target.add(dir))) {
                        numNotPassable++;
                    }
                }

                if (numNotPassable >= 3) {
                    seenSymmetricLocs.add(target);
                }
            }
        }

        if (turnsFollowedExploreTarget > EXPLORE_TARGET_TIMEOUT) {
            // We have been trying to get to this target for a while and it is not
            // reachable. Mark it as seen.
            // Theoretically we might want to remove the symmetry that this target
            // is a part of. But that seems a little risky.
            seenSymmetricLocs.add(target);
        }
    }

    public MapLocation chooseSymmetricLoc() throws GameActionException {
        MapLocation bestLoc = null;
        MapLocation possibleLoc;
        int bestDist = Integer.MAX_VALUE;
        int currDist;
        for (int i = enemyHQs.length; --i >= 0;) {
            possibleLoc = enemyHQs[i];
            currDist = currLoc.distanceSquaredTo(possibleLoc);
            // int controlStatus = Comms.readSectorControlStatus(whichSector(possibleLoc));
            // boolean notTraversed = controlStatus == Comms.ControlStatus.UNKNOWN ||
            // controlStatus == Comms.ControlStatus.EXPLORING;

            if (currDist < bestDist && !seenSymmetricLocs.contains(possibleLoc)) {
                bestLoc = possibleLoc;
                bestDist = currDist;
                isSymLocHQ = true;
            }
        }

        for (int i = symManaSectors.length; --i >= 0;) {
            possibleLoc = symManaSectors[i];
            currDist = currLoc.distanceSquaredTo(possibleLoc);
            if (currDist < bestDist && !seenSymmetricLocs.contains(possibleLoc)) {
                bestLoc = possibleLoc;
                bestDist = currDist;
                isSymLocHQ = false;
            }
        }

        // Consider the center of the map as a symmetric location
        if (isSemiSmallMap()) {
            possibleLoc = new MapLocation(Util.MAP_WIDTH / 2, Util.MAP_HEIGHT / 2);
            currDist = currLoc.distanceSquaredTo(possibleLoc);
            if (currDist < bestDist && !seenSymmetricLocs.contains(possibleLoc)) {
                bestLoc = possibleLoc;
                bestDist = currDist;
                isSymLocHQ = false;
            }
        }

        return bestLoc;
    }

    public void attackClouds() throws GameActionException {
        if (!rc.isActionReady())
            return;
        if (Clock.getBytecodesLeft() <= 500)
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

    public void goToNearestIsland() throws GameActionException {
        // closestFriendlyIslandIdx should be a valid sector
        if (closestFriendlyIslandIdx == Comms.UNDEFINED_SECTOR_INDEX) {
            Debug.println("Error: closestFriendlyIslandIdx is not a valid sector");
            return;
        }

        MapLocation closestFriendlyIsland = sectorCenters[closestFriendlyIslandIdx];
        int[] islandIdxs = rc.senseNearbyIslands();
        // Go to the friendly island
        int islandIdx = -1;
        for (int i = islandIdxs.length; --i >= 0;) {
            if (rc.senseTeamOccupyingIsland(islandIdxs[i]) == team) {
                // Found our team's island
                islandIdx = islandIdxs[i];
                break;
            }
        }

        if (islandIdx == -1) {
            // No islands nearby, so just go to the closest one
            Debug.printString("GOTO ISL");

            MapLocation target = closestFriendlyIsland;
            boolean shouldMarkCorner = false;

            // If we have visited the center of the sector and we can't see the island,
            // travel the corners of the sector until we find it
            if (visitedSectorCenter || currLoc.equals(closestFriendlyIsland)) {
                visitedSectorCenter = true;
                MapLocation nextCorner = sectorDatabase.at(closestFriendlyIslandIdx).getNextCorner();
                target = nextCorner;
                if (target == null) {
                    // We've visited all the corners and still haven't found the well???
                    target = sectorCenters[closestFriendlyIslandIdx];
                    Debug.println("ERROR: Couldn't find island in sector");
                    sectorDatabase.at(closestFriendlyIslandIdx).resetCorners();
                } else {
                    shouldMarkCorner = true;
                }
                // Debug.println("Trying corner: " + target);
            }

            moveAndAttack(target);
            if (shouldMarkCorner && rc.getLocation().isAdjacentTo(target)) {
                sectorDatabase.at(closestFriendlyIslandIdx).visitCorner(target);
            }
            return;
        }

        // Find the best spot to heal.
        MapLocation bestHealLoc = Util.getBestHealLoc(islandIdx);
        if (bestHealLoc == null) {
            // No good spots to heal, so just go to the closest one
            Debug.printString("NO HEAL");
            moveAndAttack(closestFriendlyIsland);
            return;
        }

        RobotInfo robot = rc.senseRobotAtLocation(bestHealLoc);
        if (robot != null && robot.ID != rc.getID()) {
            // There's a robot in this spot, so pick a spot within radius 4 of it.
            bestHealLoc = Util.getBestHealLoc(bestHealLoc);
            Debug.printString("Not on isl");
        }

        if (bestHealLoc == null) {
            // No good spots to heal, so just go to the closest one
            Debug.printString("NO HEAL");
            moveAndAttack(closestFriendlyIsland);
            return;
        }

        Debug.printString("HEAL LOC");
        moveAndAttack(bestHealLoc);
    }

    public void updateSymManaSectors() throws GameActionException {
        boolean shouldUpdate = false;
        if (symmetryChanged) {
            // Symmetry changed, so update
            shouldUpdate = true;
            // manaSymSet.clear();
            // Debug.println("Symmetry changed, so updating mana sectors", ID);
        } else {
            // Check if the closest MAX_SYM_MANA_SECTORS mana sectors have changed
            for (int i = 0; i < MAX_SYM_MANA_SECTORS; i++) {
                int manaSectorIdx = getNearestMineSectorIdx(ResourceType.MANA, manaSymSet);
                if (manaSectorIdx == Comms.UNDEFINED_SECTOR_INDEX)
                    break;
                if (!manaSymSet.contains(sectorCenters[manaSectorIdx]) &&
                        !seenSymmetricLocs.contains(sectorCenters[manaSectorIdx])) {
                    // Found a new mana well, so update
                    shouldUpdate = true;
                    // manaSymSet.clear();
                    // Debug.println("New mana sector found, so updating: " +
                    // sectorCenters[manaSectorIdx], ID);
                    break;
                }
            }
        }

        if (!shouldUpdate)
            return;

        manaSymSet.clear();

        // Put up to MAX_SYM_MANA_SECTORS mana sectors in symmetry
        MapLocation[] manaSym = new MapLocation[MAX_SYM_MANA_SECTORS * 3];
        MapLocation[] possibleFlips;
        int numManaSym = 0;
        for (int i = 0; i < MAX_SYM_MANA_SECTORS; i++) {
            int manaSectorIdx = getNearestMineSectorIdx(ResourceType.MANA, manaSymSet);
            if (manaSectorIdx == Comms.UNDEFINED_SECTOR_INDEX)
                break;
            manaSymSet.add(sectorCenters[manaSectorIdx]);
            // Debug.println("Checking mana sector " + sectorCenters[manaSectorIdx], ID);
            if (seenSymmetricLocs.contains(sectorCenters[manaSectorIdx]))
                continue;
            // Debug.println("New mana sector " + sectorCenters[manaSectorIdx], ID);
            possibleFlips = Util.getValidSymmetryLocs(sectorCenters[manaSectorIdx], symmetryAll);
            for (int j = possibleFlips.length; --j >= 0;) {
                manaSym[numManaSym++] = possibleFlips[j];
                // Debug.println("Possible flip: " + possibleFlips[j], ID);
            }
        }

        symManaSectors = new MapLocation[numManaSym];
        System.arraycopy(manaSym, 0, symManaSectors, 0, numManaSym);
    }
}
