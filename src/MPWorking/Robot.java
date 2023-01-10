package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

public class Robot {
    static RobotController rc;
    static int turnCount;
    static RobotType robotType;

    static MapLocation home;
    static MapLocation[] headquarterLocations;
    static RobotInfo[] EnemySensable;
    static RobotInfo[] FriendlySensable;
    static MapLocation currLoc;

    static int actionRadiusSquared;
    static int visionRadiusSquared;

    static RobotInfo maybeAmplifier = null;
    static RobotInfo maybeBooster = null;
    static RobotInfo maybeCarrier = null;
    static RobotInfo maybeDestabilizer = null;
    static RobotInfo maybeLauncher = null;
    static RobotInfo maybeHeadquarters = null;

    static Team team;
    static Team opponent;

    public Robot(RobotController r) throws GameActionException {
        rc = r;
        turnCount = 0;
        robotType = rc.getType();
        actionRadiusSquared = robotType.actionRadiusSquared;
        visionRadiusSquared = robotType.visionRadiusSquared;
        team = rc.getTeam();
        opponent = team.opponent();

        if (robotType == RobotType.HEADQUARTERS) {
            home = rc.getLocation();
        } else {
            RobotInfo[] sensableWithin2 = rc.senseNearbyRobots(2, rc.getTeam());
            for (RobotInfo robot : sensableWithin2) {
                if (robot.getType() == RobotType.HEADQUARTERS) {
                    MapLocation robotLoc = robot.getLocation();
                    home = robotLoc;
                }
            }
            loadArchonLocations();
        }

        if (home == null) {
            home = rc.getLocation();
        }
    }

    public void loadArchonLocations() throws GameActionException {
        // headquarterLocations = Comms.getFriendlyHeadquarterLocations();
    }

    public void initTurn() throws GameActionException {
        Pathfinding.initTurn();
    }

    public void takeTurn() throws GameActionException {
        turnCount += 1;
        EnemySensable = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        FriendlySensable = rc.senseNearbyRobots(-1, rc.getTeam());
        currLoc = rc.getLocation();
        rc.setIndicatorString("Taking turn for Robot! ");
    }

    public void endTurn() throws GameActionException {
        Explore.initialize();
        switch (robotType) {
            case HEADQUARTERS:
                return;
            default:
                Explore.markSeen();
        }
    }

    /*
     * Prioritizes miners in general.
     * Unless you're close enough to an Archon, then prioritize soldiers.
     */
    public RobotInfo getBestEnemy() throws GameActionException {
        return getBestEnemy(rc.senseNearbyRobots(actionRadiusSquared, rc.getTeam().opponent()));
    }

    /*
     * Prioritizes attacking enemies in the given order.
     * Prioritizes attacking lower health enemies.
     */
    public void loadEnemies(RobotInfo[] enemies) {
        maybeAmplifier = null;
        maybeBooster = null;
        maybeCarrier = null;
        maybeDestabilizer = null;
        maybeLauncher = null;
        maybeHeadquarters = null;

        RobotInfo enemy;
        for (int i = enemies.length - 1; i >= 0; i--) {
            enemy = enemies[i];
            switch (enemy.type) {
                case AMPLIFIER:
                    if (maybeAmplifier == null || maybeAmplifier.health > enemy.health) {
                        maybeAmplifier = enemy;
                    }
                    break;
                case BOOSTER:
                    if (maybeBooster == null || maybeBooster.health > enemy.health) {
                        maybeBooster = enemy;
                    }
                    break;
                case CARRIER:
                    if (maybeCarrier == null || maybeCarrier.health > enemy.health) {
                        maybeCarrier = enemy;
                    }
                    break;
                case DESTABILIZER:
                    if (maybeDestabilizer == null || maybeDestabilizer.health > enemy.health) {
                        maybeDestabilizer = enemy;
                    }
                    break;
                case LAUNCHER:
                    if (maybeLauncher == null || maybeLauncher.health > enemy.health) {
                        maybeLauncher = enemy;
                    }
                    break;
                case HEADQUARTERS:
                    if (maybeHeadquarters == null || maybeHeadquarters.health > enemy.health) {
                        maybeHeadquarters = enemy;
                    }
                    break;
            }
        }
    }

    public RobotInfo getBestEnemy(RobotInfo[] sensable) throws GameActionException {
        loadEnemies(sensable);

        RobotInfo res = null;

        // Prioritize these the least
        if (maybeHeadquarters != null)
            res = maybeHeadquarters;
        if (maybeAmplifier != null)
            res = maybeAmplifier;
        if (maybeCarrier != null)
            res = maybeCarrier;
        if (maybeBooster != null)
            res = maybeBooster;
        if (maybeDestabilizer != null)
            res = maybeDestabilizer;
        if (maybeLauncher != null)
            res = maybeLauncher;

        return res;
    }

    public RobotInfo[] getEnemyAttackable() throws GameActionException {
        return getAttackableRobots(EnemySensable);
    }

    public RobotInfo[] getAttackableRobots(RobotInfo[] robots) throws GameActionException {
        int size = 0;
        RobotInfo[] maxAttackable = new RobotInfo[robots.length];
        RobotInfo robot;
        for (int i = robots.length; --i >= 0;) {
            robot = robots[i];
            switch (robot.getType()) {
                case LAUNCHER:
                case DESTABILIZER:
                    maxAttackable[size++] = robot;
                    break;
                default:
                    break;
            }
        }
        RobotInfo[] attackable = new RobotInfo[size];
        System.arraycopy(maxAttackable, 0, attackable, 0, size);
        return attackable;
    }

    public boolean tryAttackBestEnemy(RobotInfo bestEnemy) throws GameActionException {
        // Try to attack someone
        if (bestEnemy != null) {
            if (rc.canAttack(bestEnemy.getLocation())) {
                rc.attack(bestEnemy.getLocation());
                // Debug.printString("Attacking: " + bestEnemy.getLocation().toString());
                return true;
            } else {
                // Debug.printString("Enemy: " + bestEnemy.getLocation().toString());
            }
        }
        return false;
    }

    public boolean tryAttackBestEnemy() throws GameActionException {
        // Try to attack someone
        RobotInfo bestEnemy = getBestEnemy();
        if (bestEnemy != null) {
            if (rc.canAttack(bestEnemy.getLocation())) {
                rc.attack(bestEnemy.getLocation());
                // Debug.printString("Attacking: " + bestEnemy.getLocation().toString());
                return true;
            } else {
                // Debug.printString("Enemy: " + bestEnemy.getLocation().toString());
            }
        }
        return false;
    }

    static boolean tryMoveDest(Direction[] target_dir) throws GameActionException {
        // Debug.println(Debug.info, "Dest direction: " + dir);
        for (Direction dir : target_dir) {
            if (rc.canMove(dir)) {
                rc.move(dir);
                return true;
            }
        }
        return false;
    }

}
