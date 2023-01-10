package MPWorking;

import battlecode.common.*;

public class Explore {
    static RobotController rc;
    static Direction lastExploreDir;
    static final int EXPLORE_BOREDOM = 20;
    static int boredom;

    static final int MIN_DIST_FROM_WALL = 3;

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

    static Direction[] dirPath;

    static final int MAX_MAP_SIZE = 60;
    static final int MAX_MAP_SIZE_SQ = MAX_MAP_SIZE * MAX_MAP_SIZE;
    static final int MAX_MAP_SIZE2 = 120;
    static boolean[][] visited = new boolean[MAX_MAP_SIZE][];

    static int visionRadius;
    static boolean initialized = false;
    static int initRow = 0;
    static final int INIT_BC_LEFT = 300;
    static final int VISITED_BC_LEFT = 100;

    static MapLocation exploreTarget = null;

    static Direction exploreDir = Direction.CENTER;
    static double angle = 0;
    static final double EXPLORE_DIST = 100;
    static MapLocation explore3Target = null;
    static Boolean rotateLeft = null;

    static final int BYTECODES_USED = 2500;

    static Direction lastDirMoved = null;

    static int initialX, initialY;

    static enum ExploreType {
        PATHFINDING,
        GREEDY,
    }

    static ExploreType exploreType;

    public static void init(RobotController r) {
        rc = r;
        visionRadius = rc.getType().visionRadiusSquared;
        fillDirPath();
        Math.random(); // for some reason the first entry is buggy...
        initExploreDir();
        initialX = rc.getLocation().x;
        initialY = rc.getLocation().y;
        exploreType = ExploreType.GREEDY;
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

    public static void pickNewExploreDir90() {
        Direction[] newDirChoices = {
                lastExploreDir.rotateLeft().rotateLeft(),
                lastExploreDir.rotateRight().rotateRight(),
        };
        Direction[] validDirs = new Direction[5];
        int numValidDirs = 0;
        for (Direction dir : newDirChoices) {
            if (isValidExploreDir(dir)) {
                Debug.printString("valid: " + dir);
                validDirs[numValidDirs++] = dir;
            }
        }
        if (numValidDirs > 0) {
            lastExploreDir = validDirs[Util.rng.nextInt(numValidDirs)];
        } else {
            // This can happen if you're going straight into a corner or wall
            // In this case, we choose from close to the opposite current explore dir
            switch (Util.rng.nextInt(3)) {
                case 0:
                    lastExploreDir = lastExploreDir.opposite().rotateLeft();
                    break;
                case 1:
                    lastExploreDir = lastExploreDir.opposite().rotateRight();
                    break;
                default:
                    lastExploreDir = lastExploreDir.opposite();
                    break;
            }
        }
    }

    public static void pickNewExploreDir() {
        Direction[] newDirChoices = {
                // Util.turnLeft90(lastExploreDir),
                lastExploreDir.rotateLeft(),
                lastExploreDir,
                lastExploreDir.rotateRight(),
                // Util.turnRight90(lastExploreDir),
        };

        Direction[] validDirs = new Direction[5];
        int numValidDirs = 0;
        for (Direction dir : newDirChoices) {
            if (isValidExploreDir(dir)) {
                validDirs[numValidDirs++] = dir;
            }
        }

        if (numValidDirs > 0) {
            lastExploreDir = validDirs[Util.rng.nextInt(numValidDirs)];
        } else {
            // This can happen if you're going straight into a corner or wall
            // In this case, we choose from close to the opposite current explore dir
            switch (Util.rng.nextInt(3)) {
                case 0:
                    lastExploreDir = lastExploreDir.opposite().rotateLeft();
                    break;
                case 1:
                    lastExploreDir = lastExploreDir.opposite().rotateRight();
                    break;
                default:
                    lastExploreDir = lastExploreDir.opposite();
                    break;
            }
        }
    }

    // If you're traveling south *right* next to a wall, you should go
    // southwest/east for a turn
    public static Direction rotateAwayFromWallIfNecessary(Direction dir) {
        MapLocation currLoc = rc.getLocation();
        switch (dir) {
            case SOUTH:
                if (currLoc.x < MIN_DIST_FROM_WALL) {
                    return dir.rotateLeft();
                }
                if (Util.MAP_WIDTH - currLoc.x < MIN_DIST_FROM_WALL) {
                    return dir.rotateRight();
                }
                break;
            case NORTH:
                if (currLoc.x < MIN_DIST_FROM_WALL) {
                    return dir.rotateRight();
                }
                if (Util.MAP_WIDTH - currLoc.x < MIN_DIST_FROM_WALL) {
                    return dir.rotateLeft();
                }
                break;
            case WEST:
                if (currLoc.y < MIN_DIST_FROM_WALL) {
                    return dir.rotateRight();
                }
                if (Util.MAP_HEIGHT - currLoc.y < MIN_DIST_FROM_WALL) {
                    return dir.rotateLeft();
                }
                break;
            case EAST:
                if (currLoc.y < MIN_DIST_FROM_WALL) {
                    return dir.rotateLeft();
                }
                if (Util.MAP_HEIGHT - currLoc.y < MIN_DIST_FROM_WALL) {
                    return dir.rotateRight();
                }
                break;
        }
        return dir;
    }

    static void initExploreDir() {
        if (rc.getType() == RobotType.HEADQUARTERS)
            return;
        assignExplore3Dir(directions[Util.rng.nextInt(8)]);
    }

    static void initialize() {
        if (initialized)
            return;

        while (initRow < MAX_MAP_SIZE) {
            if (Clock.getBytecodesLeft() < INIT_BC_LEFT)
                return;
            visited[initRow] = new boolean[MAX_MAP_SIZE];
            // muckrakerLocations[initRow] = new int[MAX_MAP_SIZE];
            initRow++;
        }
        initialized = true;
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
        if (!initialized)
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
            angle = tempAngle + (Util.rng.nextDouble() * 45 - 22.5) / 180 * Math.PI;
            x += Math.cos(angle) * EXPLORE_DIST;
            y += Math.sin(angle) * EXPLORE_DIST;
            explore3Target = new MapLocation((int) x, (int) y);
            if (Util.onTheMap(explore3Target))
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

    static boolean hasVisited(MapLocation loc) {
        if (!initialized)
            return false;
        return visited[loc.x][loc.y];
    }

    static void getNewTarget(int tries) {
        if (exploreTarget != null && Util.onTheMap(exploreTarget) && !hasVisited(exploreTarget))
            return;
        MapLocation currLoc = rc.getLocation();
        for (int i = tries; i-- > 0;) {
            int dx = 4 * (int) (Util.rng.nextInt(16) - 8);
            int dy = 4 * (int) (Util.rng.nextInt(16) - 8);
            exploreTarget = new MapLocation(currLoc.x + dx, currLoc.y + dy);
            exploreTarget = Util.clipToWithinMap(exploreTarget);
            if (!hasVisited(exploreTarget))
                return;
        }
    }

    static void markSeen() {
        if (!initialized)
            return;
        try {
            MapLocation loc = rc.getLocation();
            for (int i = dirPath.length; i-- > 0;) {
                if (Clock.getBytecodesLeft() < VISITED_BC_LEFT)
                    return;
                loc = loc.add(dirPath[i]);
                // TODO: We can't sense clouds right now :(
                try {
                    if (rc.onTheMap(loc))
                        visited[loc.x][loc.y] = true;
                } catch (GameActionException e) {
                    // Debug.println("Found a cloud :(");
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    static void fillDirPath() {
        switch (visionRadius) {
            case 20:
                dirPath = new Direction[] { Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH,
                        Direction.NORTH, Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST,
                        Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST,
                        Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST,
                        Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST,
                        Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH,
                        Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST,
                        Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH,
                        Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST,
                        Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST,
                        Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH,
                        Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.WEST, Direction.WEST,
                        Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST,
                        Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.CENTER };
                break;
            default:
                dirPath = new Direction[] { Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTHWEST,
                        Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST,
                        Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST, Direction.EAST, Direction.EAST,
                        Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTH,
                        Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST,
                        Direction.SOUTHWEST, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST,
                        Direction.NORTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTH, Direction.NORTH,
                        Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST, Direction.EAST, Direction.EAST,
                        Direction.EAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST, Direction.SOUTH,
                        Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST,
                        Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST,
                        Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTHEAST,
                        Direction.EAST, Direction.EAST, Direction.EAST, Direction.EAST, Direction.SOUTHEAST,
                        Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.SOUTHWEST,
                        Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTHWEST, Direction.NORTH,
                        Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST,
                        Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH,
                        Direction.SOUTH, Direction.WEST, Direction.WEST, Direction.WEST, Direction.NORTH,
                        Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.EAST, Direction.SOUTH,
                        Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.CENTER };
                break;
        }
    }
}
