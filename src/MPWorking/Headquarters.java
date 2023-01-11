package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;
import java.util.ArrayDeque;

public class Headquarters extends Robot {
    static enum State {
        CHILLING,
        BUILDING_ANCHOR,
    };

    static int myHqNum;

    static ArrayDeque<State> stateStack;
    static State currentState;

    // robot counts
    static int carrierCount;
    static int launcherCount;
    static int anchorCount;

    public Headquarters(RobotController r) throws GameActionException {
        super(r);
        stateStack = new ArrayDeque<State>();
        currentState = State.CHILLING;

        myHqNum = -1;
        initSectorPermutation();
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        Debug.printString("current state: " + currentState);
        computeHqNum();
        toggleState();
        doStateAction();
        // findWells();
    }

    public void toggleState() throws GameActionException {
        switch (currentState) {
            case CHILLING:
                if (carrierCount >= 5 && rc.getAnchor() == null) {
                    // start saving for anchor if you have 5 carriers already
                    stateStack.push(currentState);
                    changeState(State.BUILDING_ANCHOR);
                }
                break;
            case BUILDING_ANCHOR:
                if (rc.getAnchor() != null) {
                    // we have an anchor already
                    changeState(stateStack.pop());
                }
                break;
        }
    }

    public void changeState(State newState) throws GameActionException {
        currentState = newState;
    }

    public void buildCarrier(MapLocation newLoc) throws GameActionException {
        Debug.printString("Trying to build a carrier");
        if (rc.canBuildRobot(RobotType.CARRIER, newLoc)) {
            rc.buildRobot(RobotType.CARRIER, newLoc);
            carrierCount++;
        }
    }

    public void buildLauncher(MapLocation newLoc) throws GameActionException {
        Debug.printString("Trying to build a launcher");
        if (rc.canBuildRobot(RobotType.LAUNCHER, newLoc)) {
            rc.buildRobot(RobotType.LAUNCHER, newLoc);
            launcherCount++;
        }
    }

    public void doStateAction() throws GameActionException {
        Direction dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        MapLocation newLoc = rc.getLocation().add(dir);
        switch (currentState) {
            case CHILLING:
                // Pick a direction to build in.
                if (Util.rng.nextBoolean()) {
                    // Let's try to build a carrier.
                    buildCarrier(newLoc);
                } else {
                    // Let's try to build a launcher.
                    buildLauncher(newLoc);
                }
                break;
            case BUILDING_ANCHOR:
                // make 5 carriers and then switch to anchor mode
                if (rc.canBuildAnchor(Anchor.STANDARD)) {
                    // If we can build an anchor do it!
                    rc.buildAnchor(Anchor.STANDARD);
                    Debug.printString("Building anchor! " + rc.getAnchor());
                    anchorCount++;
                } else {
                    if (rc.getResourceAmount(ResourceType.ADAMANTIUM) >= Util.ANCHOR_COST + Util.CARRIER_COST) {
                        buildCarrier(newLoc);
                    } else if (rc.getResourceAmount(ResourceType.MANA) >= Util.ANCHOR_COST + Util.LAUNCHER_COST) {
                        buildLauncher(newLoc);
                    }
                }
                break;
        }
    }

    /**
     * First round, find out our HQ number and set our location
     * 
     * @throws GameActionException
     */
    public void computeHqNum() throws GameActionException {
        // On round 1, write a bad location to all HQ locations
        if (rc.getRoundNum() == 1) {
            MapLocation badLocation = new MapLocation(
                    GameConstants.MAP_MAX_WIDTH + 1,
                    GameConstants.MAP_MAX_HEIGHT + 1);
            for (int i = 0; i < GameConstants.MAX_STARTING_HEADQUARTERS; i++) {
                Comms.writeOurHqLocation(i, badLocation);
            }
            return;
        }

        if (myHqNum >= 0) {
            return;
        }

        MapLocation lastHqLoc;
        for (int i = 0; i < GameConstants.MAX_STARTING_HEADQUARTERS; i++) {
            lastHqLoc = Comms.readOurHqLocation(i);
            if (!Util.onTheMap(lastHqLoc)) {
                Comms.writeOurHqLocation(i, rc.getLocation());
                myHqNum = i;
                break;
            }
        }

        if (myHqNum == 0) {
            Comms.initPrioritySectors();
        }

        Debug.println("I am HQ number " + myHqNum);
    }

}
