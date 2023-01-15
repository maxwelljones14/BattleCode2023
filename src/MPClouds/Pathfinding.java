package MPClouds;

import MPClouds.fast.*;

import battlecode.common.*;

import java.util.HashSet;

public class Pathfinding {

    static RobotController rc;
    static MapLocation target = null;
    static boolean[] impassable = null;

    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
            Direction.CENTER
    };

    public static void init(RobotController r) {
        rc = r;
        BugNav.rotateRight = Util.rng.nextDouble() > 0.5;
    }

    static void setImpassable(boolean[] imp) {
        impassable = imp;
    }

    static void initTurn() {
        impassable = new boolean[directions.length];
    }

    static boolean canMove(Direction dir) {
        if (!rc.canMove(dir))
            return false;
        if (impassable[dir.ordinal()])
            return false;
        return true;
    }

    static public void move(MapLocation loc) {
        Debug.setIndicatorLine(Debug.INDICATORS, rc.getLocation(), loc, 0, 0, 255);
        if (!rc.isMovementReady())
            return;
        target = loc;
        if (!BugNav.move())
            greedyPath();
        if (!rc.isMovementReady())
            return;
        BugNav.move();
    }

    static MapLocation getGreedyTargetAway(MapLocation loc) throws GameActionException {
        Direction opp_direction = rc.getLocation().directionTo(loc).opposite();
        Direction[] dirs = new Direction[] { opp_direction, opp_direction.rotateLeft(), opp_direction.rotateRight() };
        return rc.getLocation().add(Util.getFirstMoveableDir(dirs));
    }

    static final double eps = 1e-5;

    static void greedyPath() {
        try {
            MapLocation myLoc = rc.getLocation();
            Direction bestDir = null;
            double bestEstimation = 0;
            int bestEstimationDist = 0;
            for (Direction dir : directions) {
                MapLocation newLoc = myLoc.add(dir);
                if (!rc.onTheMap(newLoc))
                    continue;

                if (!canMove(dir))
                    continue;
                if (!strictlyCloser(newLoc, myLoc, target))
                    continue;

                int newDist = newLoc.distanceSquaredTo(target);

                // TODO: Better estimation?
                double estimation = 1 + Util.distance(target, newLoc);
                if (bestDir == null || estimation < bestEstimation - eps
                        || (Math.abs(estimation - bestEstimation) <= 2 * eps && newDist < bestEstimationDist)) {
                    bestEstimation = estimation;
                    bestDir = dir;
                    bestEstimationDist = newDist;
                }
            }
            if (bestDir != null)
                rc.move(bestDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean strictlyCloser(MapLocation newLoc, MapLocation oldLoc, MapLocation target) {
        int dOld = Util.distance(target, oldLoc), dNew = Util.distance(target, newLoc);
        if (dOld < dNew)
            return false;
        if (dNew < dOld)
            return true;
        return target.distanceSquaredTo(newLoc) < target.distanceSquaredTo(oldLoc);
    }

    static class BugNav {

        static final int INF = 1000000;
        static final int MAX_MAP_SIZE = GameConstants.MAP_MAX_HEIGHT;

        static boolean shouldGuessRotation = true; // if I should guess which rotation is the best
        static boolean rotateRight = true; // if I should rotate right or left
        static MapLocation lastObstacleFound = null; // latest obstacle I've found in my way
        static int minDistToEnemy = INF; // minimum distance I've been to the enemy while going around an obstacle
        static MapLocation prevTarget = null; // previous target
        static HashSet<Integer> visited = new HashSet<>();
        static int id = 12316;

        static boolean move() {
            try {
                // different target? ==> previous data does not help!
                if (prevTarget == null || target.distanceSquaredTo(prevTarget) > 0) {
                    // Debug.println("New target: " + target, id);
                    resetPathfinding();
                }

                // If I'm at a minimum distance to the target, I'm free!
                MapLocation myLoc = rc.getLocation();
                // int d = myLoc.distanceSquaredTo(target);
                int d = Util.distance(myLoc, target);
                if (d < minDistToEnemy) {
                    // Debug.println("New min dist: " + d + " Old: " + minDistToEnemy, id);
                    resetPathfinding();
                    minDistToEnemy = d;
                }

                int code = getCode();

                if (visited.contains(code)) {
                    // Debug.println("Contains code", id);
                    resetPathfinding();
                }
                visited.add(code);

                // Update data
                prevTarget = target;

                // If there's an obstacle I try to go around it [until I'm free] instead of
                // going to the target directly
                Direction dir = myLoc.directionTo(target);
                if (lastObstacleFound != null) {
                    // Debug.println("Last obstacle found: " + lastObstacleFound, id);
                    dir = myLoc.directionTo(lastObstacleFound);
                }
                if (canMove(dir)) {
                    // Debug.println("can move", id);
                    resetPathfinding();
                }

                // I rotate clockwise or counterclockwise (depends on 'rotateRight'). If I try
                // to go out of the map I change the orientation
                // Note that we have to try at most 16 times since we can switch orientation in
                // the middle of the loop. (It can be done more efficiently)
                for (int i = 8; i-- > 0;) {
                    if (canMove(dir)) {
                        rc.move(dir);
                        // Debug.println("Moving in dir: " + dir, id);
                        return true;
                    }

                    MapLocation newLoc = myLoc.add(dir);
                    if (!rc.onTheMap(newLoc)) {
                        rotateRight = !rotateRight;
                    } else if (!rc.sensePassability(newLoc)) {
                        // This is the latest obstacle found if
                        // - I can't move there
                        // - It's on the map
                        // - It's not passable
                        lastObstacleFound = myLoc.add(dir);
                        // Debug.println("Found obstacle: " + lastObstacleFound, id);
                        if (shouldGuessRotation) {
                            // Debug.println("Inferring rot dir around: " + lastObstacleFound, id);
                            if (MapTracker.canInferRotationAroundObstacle(lastObstacleFound)) {
                                shouldGuessRotation = false;
                                if (!MapTracker.isAdjacentToPOI(myLoc, lastObstacleFound)) {
                                    rotateRight = MapTracker.shouldRotateRightAroundObstacle(
                                            lastObstacleFound, myLoc, target);
                                }
                                // Debug.println("Inferred: " + rotateRight, id);
                            }
                        }
                    } else if (shouldGuessRotation) {
                        // Guessing rotation not on an obstacle is different.
                        shouldGuessRotation = false;
                        // Debug.println("Guessing rot dir", id);
                        // Rotate left and right and find the first dir that you can move in
                        Direction dirL = dir;
                        for (int j = 8; j-- > 0;) {
                            if (canMove(dirL))
                                break;
                            dirL = dirL.rotateLeft();
                        }

                        Direction dirR = dir;
                        for (int j = 8; j-- > 0;) {
                            if (canMove(dirR))
                                break;
                            dirR = dirR.rotateRight();
                        }

                        // Check which results in a location closer to the target
                        MapLocation locL = myLoc.add(dirL);
                        MapLocation locR = myLoc.add(dirR);

                        int lDist = Util.distance(target, locL);
                        int rDist = Util.distance(target, locR);
                        int lDistSq = target.distanceSquaredTo(locL);
                        int rDistSq = target.distanceSquaredTo(locR);

                        if (lDist < rDist) {
                            rotateRight = false;
                        } else if (rDist < lDist) {
                            rotateRight = true;
                        } else {
                            rotateRight = rDistSq < lDistSq;
                        }

                        // Debug.println("Guessed: " + rotateRight, id);
                    }

                    if (rotateRight)
                        dir = dir.rotateRight();
                    else
                        dir = dir.rotateLeft();
                }

                if (canMove(dir))
                    rc.move(dir);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Debug.println("Last exit", id);
            return true;
        }

        // clear some of the previous data
        static void resetPathfinding() {
            // Debug.println("Resetting pathfinding", id);
            lastObstacleFound = null;
            minDistToEnemy = INF;
            visited.clear();
            shouldGuessRotation = true;
        }

        static int getCode() {
            int x = rc.getLocation().x;
            int y = rc.getLocation().y;
            Direction obstacleDir = rc.getLocation().directionTo(target);
            if (lastObstacleFound != null)
                obstacleDir = rc.getLocation().directionTo(lastObstacleFound);
            int bit = rotateRight ? 1 : 0;
            return (((((x << 6) | y) << 4) | obstacleDir.ordinal()) << 1) | bit;
        }
    }

}
