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
}
