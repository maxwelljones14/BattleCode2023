package MPEffort;

import battlecode.common.*;

public class Explore {
    static RobotController rc;

    static Direction[] dirPath;

    static final int MAX_MAP_SIZE = 60;
    static final int MAX_MAP_SIZE_SQ = MAX_MAP_SIZE * MAX_MAP_SIZE;
    static final int MAX_MAP_SIZE2 = 2 * MAX_MAP_SIZE;

    static int visionRadius;

    static MapLocation exploreTarget = null;

    static Direction exploreDir = Direction.CENTER;
    static final double EXPLORE_DIST = 10;
    static MapLocation explore3Target = null;

    public static void init(RobotController r) {
        rc = r;
        visionRadius = rc.getType().visionRadiusSquared;
        Math.random(); // for some reason the first entry is buggy...
        initExploreDir();
    }

    // Don't continue in this explore dir if it will bring you too close to a wall.
    public static boolean isValidExploreDir(Direction dir) {
        switch (dir) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return Util.onTheMap(rc.getLocation().translate(dir.dx * 5, dir.dy * 5));
            case NORTHEAST:
            case NORTHWEST:
            case SOUTHEAST:
            case SOUTHWEST:
                return Util.onTheMap(rc.getLocation().translate(dir.dx * 4, dir.dy * 4));
            case CENTER:
                // Should not happen
                return false;
        }
        return false;
    }

    static void initExploreDir() {
        if (rc.getType() == RobotType.HEADQUARTERS)
            return;
        assignExplore3Dir(Util.directions[Util.rng.nextInt(Util.directions.length)]);
    }

    // Finds a new target anywhere on the map outside of the vision radius
    // Only used when visited hasn't been initialized yet
    static void emergencyTarget(int tries) {
        MapLocation myLoc = rc.getLocation();
        if (exploreTarget != null && myLoc.distanceSquaredTo(exploreTarget) > visionRadius)
            return;
        int X = rc.getLocation().x;
        int Y = rc.getLocation().y;
        for (int i = tries; i-- > 0;) {
            int dx = (int) (Util.rng.nextInt(MAX_MAP_SIZE2) - MAX_MAP_SIZE);
            int dy = (int) (Util.rng.nextInt(MAX_MAP_SIZE2) - MAX_MAP_SIZE);
            exploreTarget = new MapLocation(X + dx, Y + dy);
            if (myLoc.distanceSquaredTo(exploreTarget) > visionRadius)
                return;
        }
        exploreTarget = null;
    }

    static MapLocation getExploreTarget() {
        if (!MapTracker.initialized)
            emergencyTarget(10);
        else
            getNewTarget(10);
        return exploreTarget;
    }

    static MapLocation getExplore3Target() {
        checkDirection();
        return explore3Target;
    }

    static void assignExplore3Dir(Direction dir) {
        exploreDir = dir;
        double tempAngle = Math.atan2(exploreDir.dy, exploreDir.dx);
        int tries = 10;
        double x = rc.getLocation().x, y = rc.getLocation().y;
        for (int i = tries; i-- > 0;) {
            // Try for more variance in the direction?
            double angle = tempAngle + (Util.rng.nextDouble() * 45 - 22.5) / 180 * Math.PI;
            x += Math.cos(angle) * EXPLORE_DIST;
            y += Math.sin(angle) * EXPLORE_DIST;
            explore3Target = new MapLocation((int) x, (int) y);
            explore3Target = Util.clipToWithinMap(explore3Target);
            if (!MapTracker.hasVisited(explore3Target))
                return;
        }
    }

    static void checkDirection() {
        if (isValidExploreDir(exploreDir) && explore3Target.isWithinDistanceSquared(rc.getLocation(), visionRadius))
            return;
        // System.err.println("Checking new direction!");
        switch (exploreDir) {
            case SOUTHEAST:
            case NORTHEAST:
            case NORTHWEST:
            case SOUTHWEST:
                getClosestExplore3Direction();
                return;
            case NORTH:
            case SOUTH:
                int east = eastCloser();
                switch (east) {
                    case 1:
                        assignExplore3Dir(Direction.WEST);
                        return;
                    case -1:
                        assignExplore3Dir(Direction.EAST);
                        return;
                }
                // Direction dir = exploreDir.rotateLeft().rotateLeft();
                // if (!isValidExploreDir(dir)) assignExplore3Dir(dir);
                // else assignExplore3Dir(dir.opposite());
                return;
            case EAST:
            case WEST:
                int north = northCloser();
                switch (north) {
                    case 1:
                        assignExplore3Dir(Direction.SOUTH);
                        return;
                    case -1:
                        assignExplore3Dir(Direction.NORTH);
                        return;
                }
                // dir = exploreDir.rotateLeft().rotateLeft();
                // if (!isValidExploreDir(dir)) assignExplore3Dir(dir);
                // else assignExplore3Dir(dir.opposite());
                return;
        }
    }

    static int eastCloser() {
        return rc.getMapWidth() - rc.getLocation().x <= rc.getLocation().x ? 1 : -1;
    }

    static int northCloser() {
        return rc.getMapHeight() - rc.getLocation().y <= rc.getLocation().y ? 1 : -1;
    }

    static void getClosestExplore3Direction() {
        Direction dirl = exploreDir.rotateLeft();
        if (!isValidExploreDir(dirl)) {
            assignExplore3Dir(dirl);
            return;
        }
        Direction dirr = exploreDir.rotateRight();
        if (!isValidExploreDir(dirr)) {
            assignExplore3Dir(dirr);
            return;
        }
        Direction dirll = dirl.rotateLeft();
        if (!isValidExploreDir(dirll)) {
            assignExplore3Dir(dirll);
            return;
        }
        Direction dirrr = dirr.rotateRight();
        if (!isValidExploreDir(dirrr)) {
            assignExplore3Dir(dirrr);
            return;
        }
        Direction dirlll = dirll.rotateLeft();
        if (!isValidExploreDir(dirlll)) {
            assignExplore3Dir(dirlll);
            return;
        }
        Direction dirrrr = dirrr.rotateRight();
        if (!isValidExploreDir(dirrrr)) {
            assignExplore3Dir(dirrrr);
            return;
        }
        Direction dirllll = dirlll.rotateLeft();
        if (!isValidExploreDir(dirllll)) {
            assignExplore3Dir(dirllll);
            return;
        }
    }

    static void getNewTarget(int tries) {
        if (exploreTarget != null && Util.onTheMap(exploreTarget) && !MapTracker.hasVisited(exploreTarget))
            return;
        MapLocation currLoc = rc.getLocation();
        for (int i = tries; i-- > 0;) {
            int dx = 4 * (int) (Util.rng.nextInt(16) - 8);
            int dy = 4 * (int) (Util.rng.nextInt(16) - 8);
            exploreTarget = new MapLocation(currLoc.x + dx, currLoc.y + dy);
            exploreTarget = Util.clipToWithinMap(exploreTarget);
            if (!MapTracker.hasVisited(exploreTarget))
                return;
        }
    }
}
