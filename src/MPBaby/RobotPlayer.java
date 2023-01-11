package MPBaby;

import battlecode.common.*;
import MPBaby.Util.*;
import MPBaby.fast.FastMath;
import MPBaby.Comms.*;
import MPBaby.Debug.*;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what
 * we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    static Robot bot;

    /**
     * run() is the method that is called when a robot is instantiated in the
     * Battlecode world.
     * It is like the main function for your robot. If this method returns, the
     * robot dies!
     *
     * @param rc The RobotController object. You use it to perform actions from this
     *           robot, and to get
     *           information on its current status. Essentially your portal to
     *           interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        FastMath.initRand(rc);
        Debug.init(rc);
        Util.init(rc);
        Comms.init(rc);
        Explore.init(rc);
        Pathfinding.init(rc);
        // Nav.init(rc);

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you
        // run a match!
        Debug.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());

        // You can also use indicators to save debug notes in replays.
        switch (rc.getType()) {
            case AMPLIFIER:
                bot = new Amplifier(rc);
                break;
            case BOOSTER:
                bot = new Booster(rc);
                break;
            case CARRIER:
                bot = new Carrier(rc);
                break;
            case DESTABILIZER:
                bot = new Destabilizer(rc);
                break;
            case HEADQUARTERS:
                bot = new Headquarters(rc);
                break;
            case LAUNCHER:
                bot = new Launcher(rc);
                break;
        }
        while (true) {
            try {
                bot.initTurn();
                bot.takeTurn();
                bot.endTurn();
                Debug.flush();
                Clock.yield();

            } catch (Exception e) {
                Debug.println(rc.getType() + " Exception");
                e.printStackTrace();

                reset(rc);
            }
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction
        // imminent...
    }

    // Last resort if a bot errors out in deployed code
    // Certain static variables might need to be cleared to ensure
    // a successful return to execution.
    public static void reset(RobotController rc) throws GameActionException {
        switch (rc.getType()) {
            case AMPLIFIER:
                bot = new Amplifier(rc);
                break;
            case BOOSTER:
                bot = new Booster(rc);
                break;
            case CARRIER:
                bot = new Carrier(rc);
                break;
            case DESTABILIZER:
                bot = new Destabilizer(rc);
                break;
            case HEADQUARTERS:
                bot = new Headquarters(rc);
                break;
            case LAUNCHER:
                bot = new Launcher(rc);
                break;
        }
        // End of loop: go back to the top. Clock.yield() has ended, so it's time for
        // another turn!
    }

}