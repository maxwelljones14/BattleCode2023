package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;

public class Robot {
    static RobotController rc;
    static int turnCount;

    public Robot(RobotController r) throws GameActionException {
        rc = r;
    }

    public void initTurn() throws GameActionException {

    }

    public void takeTurn() throws GameActionException {
        turnCount += 1;
        rc.setIndicatorString("Taking turn for Robot! ");
    }

    public void endTurn() throws GameActionException {

    }
}
