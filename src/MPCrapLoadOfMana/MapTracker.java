package MPCrapLoadOfMana;

import MPCrapLoadOfMana.fast.*;

import battlecode.common.*;

public class MapTracker {
    static RobotController rc;

    static Direction[] dirPath;

    static final int MAX_MAP_SIZE = 60;
    static final int MAX_MAP_SIZE_SQ = MAX_MAP_SIZE * MAX_MAP_SIZE;
    static final int MAX_MAP_SIZE2 = 2 * MAX_MAP_SIZE;
    static boolean[][] visited = new boolean[MAX_MAP_SIZE][];

    static int visionRadius;
    static boolean initialized = false;
    static int initRow = 0;
    static final int INIT_BC_LEFT = 1000;
    static final int BFS_BC_LEFT = 3000;
    static final int VISITED_BC_LEFT = 1000;

    static final int MAX_OBSTACLES = 100;
    static int[][] obstacleIdx = new int[MAX_MAP_SIZE][];
    static Obstacle[] obstacles = new Obstacle[MAX_OBSTACLES];
    static int numObstacles = 0;

    static FastQueue<Obstacle> obstaclesToProcess;

    static int id = 12316;

    public static class Obstacle {
        // If devs make an obstacle that is bigger than this... Oh well...
        public static final int MAX_OBSTACLE_SIZE = 100;

        // We choose to only keep the two POIs which are furthest apart.
        MapLocation poi1;
        MapLocation poi2;

        FastLocIntMap locsToDists1;
        FastLocIntMap locsToDists2;

        boolean calcedPoi1;
        boolean calcedPoi2;
        boolean calcedDists2;

        // Set of locs that need to be seen
        FastLocSet needToBeSeen;

        // Temporaries for BFS distributed over multiple turns
        FastQueue<MapLocation> queue;
        FastLocIntMap visited;
        boolean startedBFS;

        // The initial location of the obstacle
        MapLocation initialLoc;

        // All locations that are on the obstacle
        FastLocSet seen;

        boolean startedProcessing;
        boolean finishedProcessing;

        public Obstacle(MapLocation loc) {
            poi1 = null;
            poi2 = null;
            locsToDists1 = new FastLocIntMap();
            locsToDists2 = new FastLocIntMap();
            needToBeSeen = new FastLocSet();
            queue = new FastQueue<MapLocation>(MAX_OBSTACLE_SIZE);
            visited = new FastLocIntMap();

            calcedPoi1 = false;
            calcedPoi2 = false;
            calcedDists2 = false;
            startedBFS = false;

            initialLoc = loc;
            seen = new FastLocSet();
            seen.add(loc);
            finishedProcessing = false;
        }

        // Returns the last location visited by the BFS or
        // null if the BFS is still in progress.
        public MapLocation doBFS() throws GameActionException {
            MapLocation cur = null;
            int dist;
            while (!queue.isEmpty()) {
                if (Clock.getBytecodesLeft() < BFS_BC_LEFT) {
                    return null;
                }

                cur = queue.poll();
                dist = visited.getVal(cur);
                // Debug.println("BFS: " + cur + ", " + dist, id);

                MapLocation neighbor = cur.add(Direction.NORTH);
                if (onSameObstacle(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor, dist + 1);
                    queue.add(neighbor);
                }
                neighbor = cur.add(Direction.SOUTH);
                if (onSameObstacle(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor, dist + 1);
                    queue.add(neighbor);
                }
                neighbor = cur.add(Direction.EAST);
                if (onSameObstacle(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor, dist + 1);
                    queue.add(neighbor);
                }
                neighbor = cur.add(Direction.WEST);
                if (onSameObstacle(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor, dist + 1);
                    queue.add(neighbor);
                }
            }

            return cur;
        }

        public boolean onSameObstacle(MapLocation loc) throws GameActionException {
            if (!rc.onTheMap(loc))
                return false;
            int idx = obstacleIdx[loc.x][loc.y];
            return idx > 0;
        }

        // Must be called before inferring the shorter rotation direction
        public boolean canInferRotation() {
            // Debug.println("needToBeSeen size: " + needToBeSeen.size(), id);
            return finishedProcessing;
        }

        /**
         * Infer rotation direction around this obstacle to the target.
         * 
         * @param obstacle loc on the obstacle we're next to
         * @param myLoc    current loc
         * @param target   target loc
         * @pre canInferRotation was called before this and returned true
         * @return true if should rotateRight
         */
        public boolean inferRotation(MapLocation obstacle, MapLocation myLoc, MapLocation target)
                throws GameActionException {
            // Debug.println("Pathing around obstacle: " + obstacle, id);
            // Debug.println("POIs: " + poi1 + ", " + poi2, id);

            // Consider pathing to each POI and choose the lowest distance
            MapLocation bestPOI = poi1;
            FastLocIntMap locsToDists = locsToDists1;
            int bestDist = locsToDists1.getVal(obstacle) + Util.manhattan(poi1, target);
            int dist = locsToDists2.getVal(obstacle) + Util.manhattan(poi2, target);
            if (dist < bestDist) {
                bestDist = dist;
                bestPOI = poi2;
                locsToDists = locsToDists2;
            }

            Debug.setIndicatorDot(Debug.INDICATORS, bestPOI, 50, 255, 200);
            Debug.setIndicatorDot(Debug.INDICATORS, poi1, 50, 200, 200);
            Debug.setIndicatorDot(Debug.INDICATORS, poi2, 50, 200, 200);
            Debug.setIndicatorLine(Debug.INDICATORS, myLoc, bestPOI, 50, 200, 200);

            // Figure out which rotation direction will get us closer to the POI
            // Find the first direction we can move in rotating left
            Direction dir = myLoc.directionTo(obstacle);
            Direction leftDir = dir;
            MapLocation leftLoc;
            do {
                leftDir = leftDir.rotateLeft();
                leftLoc = myLoc.add(leftDir);
            } while (rc.onTheMap(leftLoc) && !rc.sensePassability(leftLoc));

            // Find the first direction we can move in rotating right
            Direction rightDir = dir;
            MapLocation rightLoc;
            do {
                rightDir = rightDir.rotateRight();
                rightLoc = myLoc.add(rightDir);
            } while (rc.onTheMap(leftLoc) && !rc.sensePassability(rightLoc));

            // The loc should be next to the obstacle
            // The previous loc is the one that should be on the obstacle
            leftLoc = myLoc.add(leftDir.rotateRight());
            rightLoc = myLoc.add(rightDir.rotateLeft());

            // Debug.println("leftLoc: " + leftLoc + ", " + locsToDists.getVal(leftLoc),
            // id);
            // Debug.println("rightLoc: " + rightLoc + ", " + locsToDists.getVal(rightLoc),
            // id);

            return locsToDists.getVal(rightLoc) < locsToDists.getVal(leftLoc);
        }

        public boolean isAdjacentToPOI(MapLocation loc) {
            if (!finishedProcessing)
                return false;
            return loc.isAdjacentTo(poi1) || loc.isAdjacentTo(poi2);
        }

        public boolean process() throws GameActionException {
            // Debug.println("Processing obstacle: " + this, id);
            if (!calcedPoi1) {
                if (!startedBFS) {
                    // Start this BFS from an arbitrary point on the obstacle
                    startedBFS = true;
                    visited.add(initialLoc, 0);
                    queue.clear();
                    queue.add(initialLoc);
                }

                poi1 = doBFS();
                if (poi1 != null) {
                    // This is the first POI
                    calcedPoi1 = true;
                    startedBFS = false;
                    // Debug.println("Found POI1: " + poi1, id);
                }
                return false;
            } else if (!calcedPoi2) {
                if (!startedBFS) {
                    // Start this BFS from poi1
                    startedBFS = true;
                    visited = new FastLocIntMap();
                    visited.add(poi1, 0);
                    queue.clear();
                    queue.add(poi1);
                }

                poi2 = doBFS();
                if (poi2 != null) {
                    // This is the second POI
                    calcedPoi2 = true;
                    locsToDists1 = visited;
                    startedBFS = false;
                    // Debug.println("Found POI2: " + poi2, id);
                }
                return false;
            } else {
                if (!startedBFS) {
                    // Start this BFS from poi2
                    startedBFS = true;
                    visited = new FastLocIntMap();
                    visited.add(poi2, 0);
                    queue.clear();
                    queue.add(poi2);
                }

                MapLocation done = doBFS();
                if (done != null) {
                    locsToDists2 = visited;
                    finishedProcessing = true;

                    // If one of the POIs is on the edge of the map,
                    // we should never path to it.
                    boolean poi1OnEdge = Util.onEdgeOfMap(poi1);
                    boolean poi2OnEdge = Util.onEdgeOfMap(poi2);
                    if (poi1OnEdge && poi2OnEdge) {
                        // Debug.println("Both POIs on edge of map!", id);
                        // This shouldn't actually happen.
                        // This creates a section of the map that is not reachable.
                        finishedProcessing = false;
                    } else if (poi1OnEdge) {
                        // Debug.println("POI1 on edge of map!", id);
                        poi1 = poi2;
                        locsToDists1 = locsToDists2;
                    } else if (poi2OnEdge) {
                        // Debug.println("POI2 on edge of map!", id);
                        poi2 = poi1;
                        locsToDists2 = locsToDists1;
                    }

                    // Debug.println("Done processing!", id);
                    return true;
                }
                return false;
            }
        }
    }

    public static void init(RobotController r) {
        rc = r;
        visionRadius = rc.getType().visionRadiusSquared;
        fillDirPath();
        obstaclesToProcess = new FastQueue<Obstacle>(100);
    }

    public static boolean mergeObstacles(MapLocation loc, Direction dir) throws GameActionException {
        MapLocation neighbor = loc.add(dir);
        if (rc.onTheMap(neighbor)) {
            int locIdx = obstacleIdx[loc.x][loc.y];
            int neighborIdx = obstacleIdx[neighbor.x][neighbor.y];

            // Debug.println("Checking " + loc + ": " + locIdx + " and " + neighbor + ": " +
            // neighborIdx, id);
            if (neighborIdx <= 0 || locIdx == neighborIdx) {
                // Debug.println("Neighbor is not an obstacle", id);
                return false;
            }

            // Debug.println("Found obstacle at " + neighbor + " connected to " + loc, id);
            // Merge the new one into the old one
            // Don't reset the null obstacle
            if (locIdx > 0) {
                obstacles[neighborIdx].needToBeSeen.union(obstacles[locIdx].needToBeSeen);
                obstacles[neighborIdx].seen.union(obstacles[locIdx].seen);

                // Iterate through all locs in obstacles[locIdx].seen and
                // change their index to neighborIdx
                MapLocation[] locs = obstacles[locIdx].seen.getKeys();
                MapLocation loc2;
                for (int i = locs.length; --i >= 0;) {
                    loc2 = locs[i];
                    obstacleIdx[loc2.x][loc2.y] = neighborIdx;
                }

                obstacles[locIdx] = null;
            } else {
                obstacles[neighborIdx].seen.add(loc);
                obstacleIdx[loc.x][loc.y] = neighborIdx;
            }

            return true;
        }
        return false;
    }

    public static void print(FastLocSet s) {
        MapLocation[] locs = s.getKeys();
        for (int i = locs.length; --i >= 0;) {
            Debug.println("L: " + locs[i], id);
        }
    }

    public static void markObstacleLocSeen(MapLocation loc) throws GameActionException {
        // If the neighbors have obstacles, mark this square as seen
        for (Direction dir : Direction.cardinalDirections()) {
            MapLocation neighbor = loc.add(dir);
            if (rc.onTheMap(neighbor)) {
                int neighborIdx = obstacleIdx[neighbor.x][neighbor.y];
                // Debug.println("Looking at " + neighbor + ": " + neighborIdx, id);
                if (neighborIdx > 0 && obstacles[neighborIdx].needToBeSeen.contains(loc)) {
                    // Debug.println("Removing " + loc + " from obstacle " + obstacles[neighborIdx],
                    // id);
                    // Debug.println("needToBeSeen: " + obstacles[neighborIdx].needToBeSeen.size,
                    // id);
                    obstacles[neighborIdx].needToBeSeen.remove(loc);
                    // Debug.println("needToBeSeen: " + obstacles[neighborIdx].needToBeSeen.size,
                    // id);

                    // If there are no more locations to be seen, calculate the POIs
                    if (obstacles[neighborIdx].needToBeSeen.size == 0 && !obstacles[neighborIdx].startedProcessing) {
                        // Debug.println("Adding obstacle: " + obstacles[neighborIdx] +
                        // " to process queue", id);
                        obstaclesToProcess.add(obstacles[neighborIdx]);
                        obstacles[neighborIdx].startedProcessing = true;
                    }
                }
            }
        }
    }

    public static void printObstacles() {
        for (int x = 0; x < rc.getMapWidth(); x++) {
            for (int y = 0; y < rc.getMapHeight(); y++) {
                Debug.println("ObstacleIdx: " + x + ", " + y + ": " + obstacleIdx[x][y], id);
                if (obstacleIdx[x][y] > 0) {
                    Debug.println("Obstacle: " + new MapLocation(x, y) + ": " + obstacles[obstacleIdx[x][y]], id);
                }
            }
        }
    }

    public static void addObstacle(MapLocation loc) throws GameActionException {
        int x = loc.x;
        int y = loc.y;

        // Debug.println("Adding obstacle at " + loc, id);

        if (rc.sensePassability(loc)) {
            obstacleIdx[x][y] = -1;
            return;
        }

        boolean foundObstacle = false;
        // Check 4 point connectivity for another obstacle
        for (Direction dir : Direction.cardinalDirections()) {
            foundObstacle = mergeObstacles(loc, dir) || foundObstacle;
        }

        if (!foundObstacle) {
            // Create a new obstacle. We skip index 0 because it's the default value.
            obstacleIdx[x][y] = ++numObstacles;

            // I don't really see this being necessary, but just for safety.
            if (obstacles.length <= numObstacles) {
                Obstacle[] newObstacles = new Obstacle[obstacles.length * 2];
                System.arraycopy(obstacles, 0, newObstacles, 0, obstacles.length);
                obstacles = newObstacles;
            }

            obstacles[numObstacles] = new Obstacle(loc);
            // Debug.println(Debug.INFO, "Created obstacle " + numObstacles + " at " + loc +
            // " : " + obstacles[numObstacles], id);
        }

        int idx = obstacleIdx[x][y];

        // Add the four neighbors to the needToBeSeen list
        for (Direction dir : Direction.cardinalDirections()) {
            MapLocation neighbor = loc.add(dir);
            if (rc.onTheMap(neighbor) && !hasVisited(neighbor)) {
                // Debug.println(Debug.INFO, "Adding " + neighbor + " to needToBeSeen " +
                // obstacles[idx], id);
                obstacles[idx].needToBeSeen.add(neighbor);
            }
        }
    }

    /**
     * @pre obstacle is on the map
     */
    public static boolean canInferRotationAroundObstacle(MapLocation obstacle) {
        if (!initialized)
            return false;
        int idx = obstacleIdx[obstacle.x][obstacle.y];
        if (idx <= 0)
            return false;
        // Debug.println("Checking inference for obstacle idx: " + idx + " at " +
        // obstacles[idx], id);
        return obstacles[idx].canInferRotation();
    }

    public static boolean isAdjacentToPOI(MapLocation loc, MapLocation obstacle) {
        if (!initialized)
            return false;
        int idx = obstacleIdx[obstacle.x][obstacle.y];
        if (idx <= 0)
            return false;
        return obstacles[idx].isAdjacentToPOI(loc);
    }

    /**
     * @pre canInferRotationAroundObstacle was called before this and returned true
     */
    public static boolean shouldRotateRightAroundObstacle(MapLocation obstacle, MapLocation myLoc, MapLocation target)
            throws GameActionException {
        int idx = obstacleIdx[obstacle.x][obstacle.y];
        return obstacles[idx].inferRotation(obstacle, myLoc, target);
    }

    static void markSeen() throws GameActionException {
        if (!initialized)
            return;

        // Process any BFS
        while (!obstaclesToProcess.isEmpty()) {
            if (Clock.getBytecodesLeft() < BFS_BC_LEFT)
                break;
            Obstacle obstacle = obstaclesToProcess.peek();
            boolean done = obstacle.process();
            if (done) {
                obstaclesToProcess.poll();
            }
        }

        MapLocation loc = rc.getLocation();
        for (int i = dirPath.length; i-- > 0;) {
            if (Clock.getBytecodesLeft() < VISITED_BC_LEFT)
                break;
            loc = loc.add(dirPath[i]);
            if (rc.canSenseLocation(loc) && !visited[loc.x][loc.y]) {
                addObstacle(loc);
                markObstacleLocSeen(loc);
                visited[loc.x][loc.y] = true;
            }
        }
    }

    static boolean hasVisited(MapLocation loc) {
        if (!initialized)
            return false;
        return visited[loc.x][loc.y];
    }

    static void initialize() {
        if (initialized)
            return;

        while (initRow < MAX_MAP_SIZE) {
            if (Clock.getBytecodesLeft() < INIT_BC_LEFT)
                return;
            visited[initRow] = new boolean[MAX_MAP_SIZE];
            obstacleIdx[initRow] = new int[MAX_MAP_SIZE];
            initRow++;
        }
        initialized = true;
    }

    // Spiral pattern for markSeen
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
