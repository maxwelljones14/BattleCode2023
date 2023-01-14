package MPWorking;

import battlecode.common.*;

public class Nav {
    static RobotController rc;

    static MapLocation lastCurrLoc;
    static MapLocation currentTarget;
    static int closestDistanceToDest;
    static int turnsSinceClosestDistanceDecreased;
    static int turnsGreedy;

    static final int BYTECODE_REMAINING = 1500;
    static final int BYTECODE_REMAINING_AMPLIFIER = 2000;
    static final int BYTECODE_BFS = 5000;

    static final int GREEDY_TURNS = 4;

    static void init(RobotController r) {
        rc = r;
        turnsGreedy = 0;
        closestDistanceToDest = Integer.MAX_VALUE;
        turnsSinceClosestDistanceDecreased = 0;
        VisitedTracker.reset();
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
        // int bcLeft = Clock.getBytecodesLeft();
        // if (bcLeft >= BFSUnrolled29.MIN_BC_TO_USE + bytecodeCushion &&
        // rc.getType().visionRadiusSquared >= 29) {
        // return BFSUnrolled29.getBestDir(dest);
        // } else if (bcLeft >= BFSUnrolled20.MIN_BC_TO_USE + bytecodeCushion) {
        // return BFSUnrolled20.getBestDir(dest);
        // } else if (bcLeft >= BFSUnrolled18.MIN_BC_TO_USE + bytecodeCushion) {
        // return BFSUnrolled18.getBestDir(dest);
        // } else if (bcLeft >= BFSUnrolled13.MIN_BC_TO_USE + bytecodeCushion) {
        // return BFSUnrolled13.getBestDir(dest);
        // } else {
        // return getGreedyDirection(rc.getLocation().directionTo(dest));
        // }
        return getGreedyDirection(rc.getLocation().directionTo(dest));
    }

    public static Direction getGreedyDirection(Direction dir) throws GameActionException {
        Direction[] bestDirs = greedyDirection(dir);
        if (bestDirs.length > 0) {
            return bestDirs[0];
        } else {
            return Util.getFirstValidInOrderDirection(dir);
        }
    }

    public static Direction[] greedyDirection(Direction dir)
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
        if (rc.canMove(dir)) {
            dirCDMult = Util.getCooldownMultiplier(loc, dir);
            numToInsert++;
        }
        if (rc.canMove(dir)) {
            leftCDMult = Util.getCooldownMultiplier(leftLoc, left);
            numToInsert++;
        }
        if (rc.canMove(dir)) {
            rightCDMult = Util.getCooldownMultiplier(rightLoc, right);
            numToInsert++;
        }

        // Hard coded 3 length array sort lol
        Direction[] orderedDirs = new Direction[3];
        if (dirCDMult <= leftCDMult && leftCDMult <= rightCDMult) {
            orderedDirs[0] = dir;
            orderedDirs[1] = left;
            orderedDirs[2] = right;
        } else if (dirCDMult <= rightCDMult && rightCDMult <= leftCDMult) {
            orderedDirs[0] = dir;
            orderedDirs[1] = right;
            orderedDirs[2] = left;
        } else if (rightCDMult <= dirCDMult && dirCDMult <= leftCDMult) {
            orderedDirs[0] = right;
            orderedDirs[1] = dir;
            orderedDirs[2] = left;
        } else if (rightCDMult <= leftCDMult && leftCDMult <= dirCDMult) {
            orderedDirs[0] = right;
            orderedDirs[1] = left;
            orderedDirs[2] = dir;
        } else if (leftCDMult <= dirCDMult && dirCDMult <= rightCDMult) {
            orderedDirs[0] = left;
            orderedDirs[1] = dir;
            orderedDirs[2] = right;
        } else if (leftCDMult <= rightCDMult && rightCDMult <= dirCDMult) {
            orderedDirs[0] = left;
            orderedDirs[1] = right;
            orderedDirs[2] = dir;
        }

        Direction[] dirs = new Direction[numToInsert];
        System.arraycopy(orderedDirs, 0, dirs, 0, numToInsert);
        return dirs;
    }

    static MapLocation getGreedyTargetAway(MapLocation loc) throws GameActionException {
        Direction[] dirs = greedyDirection(rc.getLocation().directionTo(loc).opposite());
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
            return;
        }

        currentTarget = target;
        VisitedTracker.add(rc.getLocation());

        int dist = rc.getLocation().distanceSquaredTo(target);
        if (dist < closestDistanceToDest) {
            closestDistanceToDest = dist;
            turnsSinceClosestDistanceDecreased = 0;
        } else {
            turnsSinceClosestDistanceDecreased++;
        }
    }

    static void tryMoveSafely(MapLocation target) throws GameActionException {
        Debug.printString("Saf mov");
        MapLocation currLoc = rc.getLocation();

        Direction correctDir = currLoc.directionTo(target);
        Direction[] importantDirs = Util.getInOrderDirections(correctDir);

        double currCooldown = Util.getCooldownMultiplier(currLoc, correctDir);
        double currBestDist = Integer.MAX_VALUE;
        double bestCooldown = Integer.MAX_VALUE;
        Direction currBestDirection = Direction.CENTER;

        for (Direction possibleDir : importantDirs) {
            MapLocation targetLoc = currLoc.add(possibleDir);
            int targetLocDist = currLoc.distanceSquaredTo(targetLoc);
            double nextCooldown = Util.getCooldownMultiplier(targetLoc, possibleDir);
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
        move(target, false);
    }

    static void move(MapLocation target, boolean greedy) throws GameActionException {
        if (target == null)
            return;
        if (!rc.isMovementReady())
            return;
        if (rc.getLocation().distanceSquaredTo(target) == 0)
            return;

        update(target);

        boolean canBFS = turnsSinceClosestDistanceDecreased < 2 && turnsGreedy <= 0;
        if (!greedy && canBFS) {
            Direction dir = getBestDir(target, BYTECODE_REMAINING);
            if (dir != null && rc.canMove(dir)) {
                if (!VisitedTracker.check(rc.adjacentLocation(dir))) {
                    rc.move(dir);
                } else {
                    activateGreedy();
                }
            }

            if (!rc.isMovementReady())
                return;

            VisitedTracker.add(rc.getLocation());
            dir = getBestDir(target, BYTECODE_REMAINING);
            if (dir != null && rc.canMove(dir)) {
                if (!VisitedTracker.check(rc.adjacentLocation(dir))) {
                    rc.move(dir);
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

                    Direction dir = getGreedyDirection(rc.getLocation().directionTo(target));
                    if (dir != null && rc.canMove(dir)) {
                        rc.move(dir);
                    }
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

                    Direction dir = getGreedyDirection(rc.getLocation().directionTo(target));
                    if (dir != null && rc.canMove(dir)) {
                        rc.move(dir);
                    }

                    if (!rc.isMovementReady())
                        return;

                    dir = getGreedyDirection(rc.getLocation().directionTo(target));
                    if (dir != null && rc.canMove(dir)) {
                        rc.move(dir);
                    }
                }
                break;
        }
    }
}
