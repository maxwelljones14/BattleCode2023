package MPIgneous;

import battlecode.common.*;
import MPIgneous.fast.*;
import MPIgneous.bfs.*;

public class Nav {
    static RobotController rc;

    static MapLocation lastCurrLoc;
    static MapLocation currentTarget;
    static int closestDistanceToDest;
    static int turnsSinceClosestDistanceDecreased;
    static int turnsGreedy;

    static final int BYTECODE_REMAINING = 1500;
    static final int BYTECODE_REMAINING_AMPLIFIER = 2000;

    static final int GREEDY_TURNS = 4;

    static final int DIST_TO_AVOID_CURRENTS = 8;
    static final int DIST_FOR_EXACT_CURRENT = 20;

    static final int id = 13175;

    // public static final int BFS34_COST = 6000;
    // public static final int BFS20_COST = 4000;
    // public static final int BFS10_COST = 2500;

    public static final int BFSCOOLDOWN34_COST = 7000;
    public static final int BFSCOOLDOWN20_COST = 5000;
    public static final int BFSCOOLDOWN10_COST = 3000;

    static void init(RobotController r) {
        rc = r;
        turnsGreedy = 0;
        closestDistanceToDest = Integer.MAX_VALUE;
        turnsSinceClosestDistanceDecreased = 0;
        VisitedTracker.reset();

        BFSCooldown34.init(r);
        BFSCooldown20.init(r);
        BFSCooldown10.init(r);
    }

    // @requires loc is adjacent to currLoc
    public static Direction[] getDirsToAdjSquares(MapLocation loc) {
        switch (rc.getLocation().directionTo(loc)) {
            case SOUTH:
                return new Direction[] { Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.SOUTHEAST,
                        Direction.SOUTHWEST };
            case NORTH:
                return new Direction[] { Direction.NORTH, Direction.EAST, Direction.WEST, Direction.NORTHEAST,
                        Direction.NORTHWEST };
            case EAST:
                return new Direction[] { Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.NORTHEAST,
                        Direction.SOUTHEAST };
            case WEST:
                return new Direction[] { Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.NORTHWEST,
                        Direction.SOUTHWEST };
            case NORTHEAST:
                return new Direction[] { Direction.NORTHEAST, Direction.NORTH, Direction.EAST };
            case SOUTHEAST:
                return new Direction[] { Direction.SOUTHEAST, Direction.SOUTH, Direction.EAST };
            case SOUTHWEST:
                return new Direction[] { Direction.SOUTHWEST, Direction.SOUTH, Direction.WEST };
            case NORTHWEST:
                return new Direction[] { Direction.NORTHWEST, Direction.NORTH, Direction.WEST };
            default:
                return new Direction[] {};
        }
    }

    public static Direction getBestDir(MapLocation dest) throws GameActionException {
        return getBestDir(dest, 0);
    }

    public static Direction getBestDir(MapLocation dest, int bytecodeCushion) throws GameActionException {
        int bcLeft = Clock.getBytecodesLeft();
        Direction dir = null;
        if (bcLeft >= BFSCOOLDOWN34_COST + bytecodeCushion && rc.getType().visionRadiusSquared >= 29) {
            dir = BFSCooldown34.bestDir(dest);
        } else if (bcLeft >= BFSCOOLDOWN20_COST + bytecodeCushion) {
            dir = BFSCooldown20.bestDir(dest);
        } else if (bcLeft >= BFSCOOLDOWN10_COST + bytecodeCushion) {
            dir = BFSCooldown10.bestDir(dest);
        }

        if (dir == null) {
            if (bytecodeCushion == 9999) {
                bytecodeCushion = BYTECODE_REMAINING;
            }
            if (bcLeft >= bytecodeCushion) {
                boolean avoidClouds = false;
                boolean avoidCurrents = rc.getLocation().isWithinDistanceSquared(dest, DIST_TO_AVOID_CURRENTS);
                boolean onlyExactCurrent = rc.getLocation().isWithinDistanceSquared(dest, DIST_FOR_EXACT_CURRENT);
                dir = getGreedyDirection(rc.getLocation().directionTo(dest), avoidClouds, avoidCurrents,
                        onlyExactCurrent);
            } else {
                dir = Util.getFirstValidInOrderDirection(rc.getLocation().directionTo(dest));
            }
        }

        return dir;
    }

    public static Direction getGreedyDirection(Direction dir, boolean avoidClouds, boolean avoidCurrents,
            boolean onlyExactCurrent) throws GameActionException {
        Direction[] bestDirs = greedyDirection(dir, avoidClouds, avoidCurrents, onlyExactCurrent);
        if (bestDirs.length > 0) {
            return bestDirs[0];
        } else {
            return Util.getFirstValidInOrderDirection(dir);
        }
    }

    public static Direction[] greedyDirection(Direction dir, boolean avoidClouds, boolean avoidCurrents,
            boolean onlyExactCurrent)
            throws GameActionException {
        Direction left = dir.rotateLeft();
        Direction right = dir.rotateRight();

        MapLocation currLoc = rc.getLocation();
        MapLocation loc = currLoc.add(dir);
        MapLocation leftLoc = currLoc.add(left);
        MapLocation rightLoc = currLoc.add(right);

        double dirCDMult = 2;
        double leftCDMult = 2;
        double rightCDMult = 2;

        int numToInsert = 0;
        Direction[] allDirs = new Direction[3];
        int allCooldowns[] = new int[3];
        if (rc.canMove(dir)) {
            dirCDMult = Util.getCooldownMultiplier(loc,
                    dir, avoidClouds, avoidCurrents, onlyExactCurrent);
            allDirs[numToInsert++] = dir;
            allCooldowns[numToInsert - 1] = (int) (dirCDMult * 100);
        }
        if (rc.canMove(left)) {
            leftCDMult = Util.getCooldownMultiplier(leftLoc,
                    onlyExactCurrent ? dir : left, avoidClouds, avoidCurrents, onlyExactCurrent);
            allDirs[numToInsert++] = left;
            allCooldowns[numToInsert - 1] = (int) (leftCDMult * 100);
        }
        if (rc.canMove(right)) {
            rightCDMult = Util.getCooldownMultiplier(rightLoc,
                    onlyExactCurrent ? dir : right, avoidClouds, avoidCurrents, onlyExactCurrent);
            allDirs[numToInsert++] = right;
            allCooldowns[numToInsert - 1] = (int) (rightCDMult * 100);
        }

        Direction[] dirs = new Direction[numToInsert];
        System.arraycopy(allDirs, 0, dirs, 0, numToInsert);
        int[] cooldowns = new int[numToInsert];
        System.arraycopy(allCooldowns, 0, cooldowns, 0, numToInsert);

        FastSort.sort(cooldowns);
        Direction[] out = new Direction[numToInsert];
        for (int i = 0; i < FastSort.size; i++) {
            out[i] = dirs[FastSort.indices[i]];
        }

        return out;
    }

    static MapLocation getGreedyTargetAway(MapLocation loc) throws GameActionException {
        boolean avoidClouds = false;
        boolean avoidCurrents = rc.getLocation().isWithinDistanceSquared(loc, DIST_TO_AVOID_CURRENTS);
        boolean onlyExactCurrent = !rc.getLocation().isWithinDistanceSquared(loc, DIST_FOR_EXACT_CURRENT);
        Direction[] dirs = greedyDirection(rc.getLocation().directionTo(loc).opposite(), avoidClouds, avoidCurrents,
                onlyExactCurrent);
        return rc.getLocation().add(Util.getFirstMoveableDir(dirs));
    }

    static void reset() {
        turnsGreedy = 0;
        VisitedTracker.reset();
    }

    static void activateGreedy() {
        turnsGreedy = GREEDY_TURNS;
    }

    static void update(MapLocation target) {
        if (currentTarget == null || target.distanceSquaredTo(currentTarget) > 0) {
            closestDistanceToDest = rc.getLocation().distanceSquaredTo(target);
            turnsSinceClosestDistanceDecreased = 0;
            currentTarget = target;
            reset();
            return;
        }

        currentTarget = target;
        VisitedTracker.add(rc.getLocation());
        turnsGreedy--;

        int dist = rc.getLocation().distanceSquaredTo(target);
        if (dist < closestDistanceToDest) {
            closestDistanceToDest = dist;
            turnsSinceClosestDistanceDecreased = 0;
        } else {
            turnsSinceClosestDistanceDecreased++;
        }
    }

    static void tryMoveSafely(MapLocation target, boolean avoidClouds, boolean avoidCurrents,
            boolean onlyExactCurrent) throws GameActionException {
        Debug.printString("Saf mov");
        MapLocation currLoc = rc.getLocation();

        Direction correctDir = currLoc.directionTo(target);
        Direction[] importantDirs = Util.getInOrderDirections(correctDir);

        double currCooldown = Util.getCooldownMultiplier(currLoc, correctDir, avoidClouds, avoidCurrents,
                onlyExactCurrent);
        double currBestDist = Integer.MAX_VALUE;
        double bestCooldown = Integer.MAX_VALUE;
        Direction currBestDirection = Direction.CENTER;

        for (Direction possibleDir : importantDirs) {
            MapLocation targetLoc = currLoc.add(possibleDir);
            int targetLocDist = currLoc.distanceSquaredTo(targetLoc);
            double nextCooldown = Util.getCooldownMultiplier(targetLoc, possibleDir, avoidClouds, avoidCurrents,
                    onlyExactCurrent);
            if (nextCooldown < currCooldown + Util.MIN_COOLDOWN_MULT_DIFF && rc.canMove(possibleDir)) {
                if (nextCooldown < bestCooldown) {
                    bestCooldown = nextCooldown;
                    currBestDist = targetLocDist;
                    currBestDirection = possibleDir;
                } else if (nextCooldown == bestCooldown && targetLocDist < currBestDist) {
                    bestCooldown = nextCooldown;
                    currBestDist = targetLocDist;
                    currBestDirection = possibleDir;
                }
            }
        }

        if (rc.canMove(currBestDirection)) {
            rc.move(currBestDirection);
            Debug.printString("m" + currBestDirection);
        }
    }

    static void move(MapLocation target) throws GameActionException {
        move(target, false, true, BYTECODE_REMAINING);
    }

    static void move(MapLocation target, int bytecodeCushion) throws GameActionException {
        move(target, false, true, bytecodeCushion);
    }

    static void move(MapLocation target, boolean greedy) throws GameActionException {
        move(target, greedy, true, BYTECODE_REMAINING);
    }

    static void move(MapLocation target, boolean greedy, boolean avoidHQ, int bytecodeCushion)
            throws GameActionException {
        if (target == null)
            return;
        Debug.setIndicatorLine(rc.getLocation(), target, 255, 0, 200);
        if (!rc.isMovementReady())
            return;
        Debug.setIndicatorLine(rc.getLocation(), target, 0, 0, 200);
        if (rc.getLocation().distanceSquaredTo(target) == 0)
            return;
        // Sync launcher movement
        if (rc.getType() == RobotType.LAUNCHER && rc.getRoundNum() % 2 == 0) {
            return;
        }

        MapLocation currLoc = rc.getLocation();
        // Set squares within action radius of an enemy HQ to be impassable
        MapLocation enemyHQ;
        RobotInfo robot;
        boolean[] imp = new boolean[Util.DIRS_CENTER.length];
        boolean setImpassable = false;
        int d;
        for (int i = Robot.enemyHQs.length; --i >= 0;) {
            if (!rc.canSenseLocation(Robot.enemyHQs[i]))
                continue;
            enemyHQ = Robot.enemyHQs[i];
            robot = rc.senseRobotAtLocation(enemyHQ);
            if (robot == null || robot.type != RobotType.HEADQUARTERS)
                continue;

            d = Math.min(currLoc.distanceSquaredTo(enemyHQ), RobotType.HEADQUARTERS.actionRadiusSquared);
            for (int j = Util.DIRS_CENTER.length; --j >= 0;) {
                MapLocation newLoc = currLoc.add(Util.DIRS_CENTER[j]);
                if (newLoc.distanceSquaredTo(enemyHQ) <= d) {
                    imp[j] = true;
                    greedy = true;
                    setImpassable = true;
                }
            }
        }

        // If we are already within action radius, don't bother
        if (greedy && setImpassable) {
            Pathfinding.setImpassable(imp);
        }

        update(target);

        boolean canBFS = turnsSinceClosestDistanceDecreased < 2 && turnsGreedy <= 0;
        if (!greedy && canBFS) {
            Direction dir = getBestDir(target, bytecodeCushion);
            if (dir != null && rc.canMove(dir)) {
                if (!VisitedTracker.check(rc.adjacentLocation(dir))) {
                    rc.move(dir);
                    return;
                } else {
                    activateGreedy();
                }
            }
        }

        switch (rc.getType()) {
            case AMPLIFIER:
                if (Clock.getBytecodesLeft() >= BYTECODE_REMAINING_AMPLIFIER) {
                    Debug.println(Debug.PATHFINDING,
                            "getBestDir failed to get closer in 2 turns: Falling back to bugNav");
                    Pathfinding.move(target);
                } else {
                    Debug.setIndicatorDot(true, rc.getLocation(), 255, 255, 255);
                    Debug.println("Didn't have enough BC");
                }
                break;
            default:
                if (Clock.getBytecodesLeft() >= BYTECODE_REMAINING) {
                    Debug.println(Debug.PATHFINDING,
                            "getBestDir failed to get closer in 2 turns: Falling back to bugNav");
                    Pathfinding.move(target);
                } else {
                    Debug.setIndicatorDot(true, rc.getLocation(), 255, 255, 255);
                    Debug.println("Didn't have enough BC");
                }
                break;
        }
    }
}
