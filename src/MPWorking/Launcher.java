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
    static int numFriendlyLaunchers;
    static RobotInfo closestEnemy;
    static int numFriendlies;
    static int numEnemies;
    static MapLocation closestAttackingEnemy;
    static int numEnemyLaunchersAttackingUs;

    static RobotInfo[] enemyAttackable;

    public Launcher(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        // Try to attack someone
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        if (enemies.length >= 0) {
            // MapLocation toAttack = enemies[0].location;
            MapLocation toAttack = rc.getLocation().add(Direction.EAST);

            if (rc.canAttack(toAttack)) {
                rc.setIndicatorString("Attacking");
                rc.attack(toAttack);
            }
        }

        // Also try to move randomly.
        Direction dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }

}
