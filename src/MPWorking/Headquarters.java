package MPWorking;

import battlecode.common.*;
import MPWorking.Util.*;
import MPWorking.Comms.*;
import MPWorking.Debug.*;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Headquarters extends Robot {
    static enum State {
        INIT,
        CHILLING,
        BUILDING_ANCHOR,
    };

    static int myHqNum = -1;
    static int numHqs = -1;
    static boolean lastHq;

    static ArrayDeque<State> stateStack;
    static State currentState;

    // robot counts
    static int carrierCount;
    static int launcherCount;
    static int anchorCount;

    static final int initCarriersWanted = 4;
    static final int initLaunchersWanted = 4;
    static final int minCarriersBeforeAnchor = 20;

    static MapLocation currLoc;

    public Headquarters(RobotController r) throws GameActionException {
        super(r);
        stateStack = new ArrayDeque<State>();
        currentState = State.INIT;
        currLoc = rc.getLocation();

        if (numHqs == -1)
            numHqs = rc.getRobotCount();
        initSectorPermutation();

        carrierCount = 0;
        launcherCount = 0;
        anchorCount = 0;
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        computeHqNum();
        updateUnitCounts();
        toggleState();
        Debug.printString("current state: " + currentState);
        doStateAction();
        // findWells();
    }

    public void toggleState() throws GameActionException {
        switch (currentState) {
            case INIT:
                if (carrierCount >= initCarriersWanted && launcherCount >= initLaunchersWanted) {
                    changeState(State.CHILLING);
                }
            case CHILLING:
                if (carrierCount >= minCarriersBeforeAnchor && rc.getAnchor() == null) {
                    // start saving for anchor if you have minCarriersBeforeAnchor carriers already
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

    public void firstRounds() throws GameActionException {
        // find nearest wells
        WellInfo[] wellsNearMe = rc.senseNearbyWells(actionRadiusSquared);
        MapLocation nearestAdWell = null;
        int closestAdWellDistance = visionRadiusSquared;

        MapLocation nearestMnWell = null;
        int closestMnWellDistance = visionRadiusSquared;
        for (WellInfo well : wellsNearMe) {
            int dist = currLoc.distanceSquaredTo(well.getMapLocation());
            if (well.getResourceType() == ResourceType.ADAMANTIUM && dist < closestAdWellDistance) {
                closestAdWellDistance = dist;
                nearestAdWell = well.getMapLocation();
            } else if (well.getResourceType() == ResourceType.MANA && dist < closestMnWellDistance) {
                closestMnWellDistance = dist;
                nearestMnWell = well.getMapLocation();
            }
        }

        /*
         * set up 4 locations to build carriers with priority of:
         * 1. on nearest ad well
         * 2. next to nearest ad well
         * 3. on nearest mn well
         * 4. next to nearest mn well
         * 5. in the 4 cardinal directions as far away from us as possible
         */
        MapLocation[] locsToBuildCarriers = new MapLocation[initCarriersWanted];
        int i = 0;
        if (nearestAdWell != null) {
            locsToBuildCarriers[i] = nearestAdWell;
            i++;
            // find location closest to carrier that i can build but 1 square towards me
            // each time
            MapLocation translated = Util.moveTowardsMe(nearestAdWell);
            while (!rc.sensePassability(translated)) {
                translated = Util.moveTowardsMe(translated);
            }
            locsToBuildCarriers[i] = translated;
            i++;
        }
        if (nearestMnWell != null) {
            locsToBuildCarriers[i] = nearestMnWell;
            i++;
            // find location closest to carrier that i can build but 1 square towards me
            // each time
            MapLocation translated = Util.moveTowardsMe(nearestMnWell);
            while (!rc.sensePassability(translated)) {
                translated = Util.moveTowardsMe(translated);
            }
            locsToBuildCarriers[i] = translated;
            i++;
        }
        // cardinal directions if locations array not full yet
        MapLocation startingRandomLocation = currLoc.translate((int) (Math.sqrt(actionRadiusSquared)), 0);
        while (i < initCarriersWanted) {
            MapLocation candidate = startingRandomLocation;
            while (!rc.onTheMap(candidate) || !rc.sensePassability(candidate)) {
                candidate = Util.moveTowardsMe(candidate);
            }
            locsToBuildCarriers[i] = candidate;
            i++;
            startingRandomLocation = Util.rotateLoc90(startingRandomLocation);
        }
        // build carriers
        if (carrierCount < initCarriersWanted) {
            MapLocation locToBuild = locsToBuildCarriers[carrierCount];
            for (int rot = 0; rot < 4; rot++) {
                if (rc.canBuildRobot(RobotType.CARRIER, locToBuild)) {
                    buildCarrier(locToBuild);
                    break;
                } else if (rc.getActionCooldownTurns() == 0) {
                    locToBuild = Util.rotateLoc90(locToBuild);
                }
            }
            return;
        }

        int j = 0;
        MapLocation[] locsToBuildLaunchers = new MapLocation[initLaunchersWanted];

        startingRandomLocation = currLoc.translate((int) (Math.sqrt(actionRadiusSquared)) - 1,
                (int) (Math.sqrt(actionRadiusSquared)) - 1);
        while (j < initLaunchersWanted) {
            MapLocation candidate = startingRandomLocation;
            while (!rc.onTheMap(candidate) || !rc.sensePassability(candidate)) {
                candidate = Util.moveTowardsMe(candidate);
            }
            locsToBuildLaunchers[j] = candidate;
            j++;
            startingRandomLocation = Util.rotateLoc90(startingRandomLocation);
        }

        // set up locations for first launchers in the 4 cardinal directions
        if (launcherCount < initLaunchersWanted) {
            MapLocation locToBuild = locsToBuildLaunchers[launcherCount];
            for (int rot = 0; rot < 4; rot++) {
                if (rc.canBuildRobot(RobotType.LAUNCHER, locToBuild)) {
                    buildLauncher(locToBuild);
                    break;
                } else if (rc.getActionCooldownTurns() == 0) {
                    locToBuild = Util.rotateLoc90(locToBuild);
                }
            }
            return;
        }
    }

    public void doStateAction() throws GameActionException {
        Direction dir = Util.directions[Util.rng.nextInt(Util.directions.length)];
        MapLocation newLoc = rc.getLocation().add(dir);
        switch (currentState) {
            case INIT:
                firstRounds();
                break;
            case CHILLING:
                // Pick a direction to build in.
                if (rc.getRoundNum() % 2 == 0) {
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
     * First rounds, find out our HQ number and set our location
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

        if (myHqNum == numHqs - 1) {
            lastHq = true;
        }

        Debug.println("I am HQ number " + myHqNum);
    }

    public void updateUnitCounts() throws GameActionException {
        carrierCount = Comms.readBotCountCarriers();
        launcherCount = Comms.readBotCountLaunchers();

        if (lastHq) {
            // Debug.println("There are currently " + carrierCount + " carriers and " +
            // launcherCount + " launchers.");
            Comms.writeBotCountAll(0);
        }
    }
}
