package MPWorking;

import battlecode.common.*;
import java.util.Random;
import MPWorking.fast.*;

public class Util {
    static Random rng;

    private static RobotController rc;

    static int MAP_WIDTH;
    static int MAP_HEIGHT;
    static int MAP_AREA;
    static int MAP_MAX_DIST_SQUARED;

    static final int MAX_AREA_FOR_FAST_INIT = 625;
    static final int MAX_AREA_FOR_SEMI_FAST_INIT = 1000;

    static final int CLEAR_ENEMY_INFO_PERIOD = 20;
    static final int CLEAR_COMBAT_SECTOR_TIMEOUT = 10;

    static final double MIN_COOLDOWN_MULT_DIFF = 0.1;
    static final double SYM_TO_COMB_DIST_RATIO = 2;
    static final double SYM_TO_COMB_HOME_AGGRESSIVE_DIST_RATIO = 0.5;
    static final double SYM_TO_COMB_HOME_PASSIVE_DIST_RATIO = 1.5;
    static final double COMB_TO_HOME_DIST = 10;

    static final int EXP_TARGET_DIST_TO_LEAVE_HQ = 15;

    static final int CARRIER_TURNS_TO_FILL = 40;
    static final int AVG_FIRST_COMBAT_LENGTH = 15;

    public static final int TURN_TO_IGNORE_EARLY_MINING_SECTORS = 10;
    public static final int DIST_TO_IGNORE_EARLY_MINING_SECTORS = 10;

    // ~2 sectors
    public static final int DIST_TO_SWITCH_TO_MANA = 12;
    // Max number of turns a combat sector near home before we decide to NOT switch
    public static final int SWITCH_TO_MANA_TIMEOUT = 10;

    public static final int MAX_BLACKLIST_DIST = 15;

    public static final int MIN_MAP_AREA_FOR_ELIXIR = 1500;
    public static final int MIN_ROUND_NUM_FOR_ELIXIR = 500;

    public static final int MIN_ROUNDS_FOR_AMPLIFIER = 200;
    public static final int AMPLIFIER_COOLDOWN = 50;
    public static final int MAX_AMPLIFIER_COUNT = 2;

    public static final int AMP_JUST_INSIDE_VISION_RADIUS = RobotType.AMPLIFIER.visionRadiusSquared - 7;
    public static final int AMP_ROUNDS_TO_EXPLORE = 15;

    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    static final Direction[] X_DIRECTIONS = {
            Direction.CENTER,
            Direction.NORTHEAST,
            Direction.SOUTHEAST,
            Direction.SOUTHWEST,
            Direction.NORTHWEST,
    };

    static final Direction[] CARDINAL_DIRECTIONS = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
    };

    /** Array containing all the possible movement directions. */
    static final Direction[] DIRS_CENTER = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
            Direction.CENTER,
    };

    static final class SymmetryType {
        // Values in accordance with the bit pos in comms. DO NOT CHANGE
        public static final int VERTICAL = 4;
        public static final int HORIZONTAL = 2;
        public static final int ROTATIONAL = 1;
        public static final int ALL = 7;
    }

    static final int JUST_OUTSIDE_OF_VISION_RADIUS = 34;

    static void init(RobotController r) {
        rc = r;
        rng = new Random(rc.getRoundNum() * 23981 + rc.getID() * 10289);

        MAP_HEIGHT = rc.getMapHeight();
        MAP_WIDTH = rc.getMapWidth();
        MAP_AREA = MAP_HEIGHT * MAP_WIDTH;
        MAP_MAX_DIST_SQUARED = MAP_HEIGHT * MAP_HEIGHT + MAP_WIDTH * MAP_WIDTH;
    }

    static int distance(MapLocation A, MapLocation B) {
        return Math.max(Math.abs(A.x - B.x), Math.abs(A.y - B.y));
    }

    static int manhattan(MapLocation A, MapLocation B) {
        return Math.abs(A.x - B.x) + Math.abs(A.y - B.y);
    }

    // Returns the location on the opposite side from loc wrt to your own location
    static MapLocation invertLocation(MapLocation loc) {
        int dx = loc.x - rc.getLocation().x;
        int dy = loc.y - rc.getLocation().y;
        return rc.getLocation().translate(-dx, -dy);
    }

    // Returns the location rotated 90 degrees clockwise wrt to your own location
    static MapLocation rotateLoc90(MapLocation loc) {
        int dx = loc.x - rc.getLocation().x;
        int dy = loc.y - rc.getLocation().y;
        int rotated_dx = dy;
        int rotated_dy = -dx;
        return rc.getLocation().translate(rotated_dx, rotated_dy);
    }

    static MapLocation[] getValidSymmetryLocs(MapLocation hqLoc, int symmetry) throws GameActionException {
        MapLocation verticalFlip = new MapLocation(hqLoc.x, MAP_HEIGHT - hqLoc.y - 1);
        MapLocation horizontalFlip = new MapLocation(MAP_WIDTH - hqLoc.x - 1, hqLoc.y);
        MapLocation rotation = new MapLocation(MAP_WIDTH - hqLoc.x - 1, MAP_HEIGHT - hqLoc.y - 1);
        switch (symmetry) {
            case SymmetryType.VERTICAL | SymmetryType.HORIZONTAL | SymmetryType.ROTATIONAL:
                return new MapLocation[] {
                        verticalFlip,
                        horizontalFlip,
                        rotation,
                };
            case SymmetryType.VERTICAL | SymmetryType.HORIZONTAL:
                return new MapLocation[] {
                        verticalFlip,
                        horizontalFlip,
                };
            case SymmetryType.VERTICAL | SymmetryType.ROTATIONAL:
                return new MapLocation[] {
                        verticalFlip,
                        rotation,
                };
            case SymmetryType.HORIZONTAL | SymmetryType.ROTATIONAL:
                return new MapLocation[] {
                        horizontalFlip,
                        rotation,
                };
            case SymmetryType.VERTICAL:
                return new MapLocation[] {
                        verticalFlip,
                };
            case SymmetryType.HORIZONTAL:
                return new MapLocation[] {
                        horizontalFlip,
                };
            case SymmetryType.ROTATIONAL:
                return new MapLocation[] {
                        rotation,
                };
            default:
                // This shouldn't happen
                Debug.printString("ERROR: No valid symmetry");
                return new MapLocation[] {
                        verticalFlip,
                        horizontalFlip,
                        rotation,
                };
        }
    }

    static MapLocation getRandomAttackLoc(Direction dir) throws GameActionException {
        MapLocation currLoc = rc.getLocation();
        MapLocation[] locs = null;
        MapLocation mainLoc;
        switch (dir) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                mainLoc = currLoc.translate(dir.dx * 4, dir.dy * 4);
                locs = new MapLocation[] {
                        mainLoc, mainLoc.add(dir.opposite().rotateLeft()), mainLoc.add(dir.opposite().rotateRight())
                };
                break;
            case NORTHEAST:
            case SOUTHEAST:
            case SOUTHWEST:
            case NORTHWEST:
                mainLoc = currLoc.translate(dir.dx * 2, dir.dy * 2);
                locs = new MapLocation[] {
                        mainLoc, mainLoc.add(dir.rotateLeft()), mainLoc.add(dir.rotateRight())
                };
                break;
            default:
                locs = new MapLocation[] {
                        currLoc.add(Direction.NORTH)
                };
                // Debug.println("ERROR: Invalid direction");
                break;
        }

        return locs[FastMath.nextInt(locs.length)];
    }

    // Get init locs such that taking direction from currLoc will give dir.
    static MapLocation[] getInitLocs(Direction dir) {
        MapLocation currLoc = rc.getLocation();
        MapLocation mainLoc1 = currLoc.add(dir);
        MapLocation mainLoc2 = mainLoc1.add(dir);
        switch (dir) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return new MapLocation[] { mainLoc2.add(dir), mainLoc2, mainLoc1,
                        mainLoc1.add(dir.rotateLeft()), mainLoc1.add(dir.rotateRight()) };
            case NORTHEAST:
            case SOUTHEAST:
            case SOUTHWEST:
            case NORTHWEST:
                return new MapLocation[] { mainLoc2, mainLoc1 };
            default:
                return new MapLocation[] { currLoc };
        }
    }

    static int getNumOpenCollectSpots(MapLocation wellLoc) throws GameActionException {
        MapLocation loc;
        int count = 0;
        for (int i = Direction.DIRECTION_ORDER.length; --i >= 0;) {
            loc = wellLoc.add(Direction.DIRECTION_ORDER[i]);
            // Off the map don't count
            if (!rc.onTheMap(loc))
                continue;
            // Assume that if we can't sense the location, it's open
            if (!rc.canSenseLocation(loc) || (rc.sensePassability(loc) && rc.senseRobotAtLocation(loc) == null)) {
                count++;
            }
        }
        return count;
    }

    static int getMaxCollectSpots(MapLocation wellLoc) throws GameActionException {
        MapLocation loc;
        int count = 0;
        for (int i = Direction.DIRECTION_ORDER.length; --i >= 0;) {
            loc = wellLoc.add(Direction.DIRECTION_ORDER[i]);
            // Off the map don't count
            if (!rc.onTheMap(loc))
                continue;
            // Unpassable squares don't count.
            // Assume that if we can't sense the location, it's open
            if (!rc.canSenseLocation(loc) || rc.sensePassability(loc)) {
                count++;
            }
        }
        return count;
    }

    // Find the best loc based on
    // 1. If there is only 1 spot left, move into it if you're next to the well
    // 2. Cooldown multiplier
    // 3. Distance to currLoc
    static MapLocation getBestCollectLoc(MapLocation well) throws GameActionException {
        boolean isAdjToWell = rc.getLocation().isAdjacentTo(well);
        // If there is only 1 spot left and it is the well, one of the carriers
        // adajcent to the well should move into it.
        int numOpenSpots = getNumOpenCollectSpots(well);
        if (numOpenSpots == 1 &&
                isAdjToWell &&
                rc.senseRobotAtLocation(well) == null) {
            return well;
        }

        MapLocation currLoc = rc.getLocation();
        MapLocation bestCollect = null;
        double bestMultiplier = Double.MAX_VALUE;
        double bestDist = Double.MAX_VALUE;
        MapLocation loc;
        MapInfo info;
        double multiplier;
        double dist;
        RobotInfo robot;
        for (int i = Direction.DIRECTION_ORDER.length; --i >= 0;) {
            loc = well.add(Direction.DIRECTION_ORDER[i]);
            if (!rc.canSenseLocation(loc) || !rc.sensePassability(loc))
                continue;
            robot = rc.senseRobotAtLocation(loc);
            if (robot != null && robot.ID != rc.getID())
                continue;
            info = rc.senseMapInfo(loc);
            multiplier = info.getCooldownMultiplier(rc.getTeam());
            dist = loc.distanceSquaredTo(currLoc);

            // Disincentivize squares with currents
            if (info.getCurrentDirection() != Direction.CENTER) {
                multiplier += 10;
            }

            if (multiplier < bestMultiplier || (multiplier == bestMultiplier && dist < bestDist)) {
                bestCollect = loc;
                bestMultiplier = multiplier;
                bestDist = dist;
            }
        }

        return bestCollect == null ? well : bestCollect;
    }

    // @pre We are already adjacent to the well
    static MapLocation getBetterCollectLoc(MapLocation wellLoc) throws GameActionException {
        MapLocation currLoc = rc.getLocation();
        MapLocation bestCollect = null;
        double bestMultiplier = Double.MAX_VALUE;
        double bestDist = Double.MAX_VALUE;
        MapLocation loc;
        MapInfo info;
        double multiplier;
        double dist;
        RobotInfo robot;

        if (rc.getWeight() >= 30) {
            // If your weight is at least 30 (you're almost done collecting),
            // just choose the location closest to home
            for (int i = Direction.DIRECTION_ORDER.length; --i >= 0;) {
                loc = wellLoc.add(Direction.DIRECTION_ORDER[i]);
                if (!rc.canSenseLocation(loc) || !rc.sensePassability(loc) || !currLoc.isAdjacentTo(loc))
                    continue;
                robot = rc.senseRobotAtLocation(loc);
                if (robot != null && robot.ID != rc.getID())
                    continue;
                info = rc.senseMapInfo(loc);
                dist = Robot.home.distanceSquaredTo(loc);
                multiplier = info.getCooldownMultiplier(rc.getTeam()) +
                        (info.getCurrentDirection() == Direction.CENTER ? 0 : 10);
                if (multiplier < bestMultiplier || (multiplier == bestMultiplier && dist < bestDist)) {
                    bestCollect = loc;
                    bestDist = dist;
                    bestMultiplier = multiplier;
                }
            }
        } else {
            bestDist = Double.MIN_VALUE;
            // Otherwise, pref the furthest X locations first
            // so that other carriers can filter in
            for (int i = X_DIRECTIONS.length; --i >= 0;) {
                loc = wellLoc.add(X_DIRECTIONS[i]);
                if (!rc.canSenseLocation(loc) || !rc.sensePassability(loc) || !currLoc.isAdjacentTo(loc))
                    continue;
                robot = rc.senseRobotAtLocation(loc);
                if (robot != null && robot.ID != rc.getID())
                    continue;
                info = rc.senseMapInfo(loc);
                dist = Robot.home.distanceSquaredTo(loc);
                multiplier = info.getCooldownMultiplier(rc.getTeam()) +
                        (info.getCurrentDirection() == Direction.CENTER ? 0 : 10);
                if (multiplier < bestMultiplier || (multiplier == bestMultiplier && dist > bestDist)) {
                    bestCollect = loc;
                    bestDist = dist;
                    bestMultiplier = multiplier;
                }
            }

            MapLocation bestCardinalCollect = null;
            double bestCardinalMultiplier = Double.MAX_VALUE;
            double bestCardinalDist = Double.MIN_VALUE;
            // If none of these are open, then check the cardinal directions
            for (int i = CARDINAL_DIRECTIONS.length; --i >= 0;) {
                loc = wellLoc.add(CARDINAL_DIRECTIONS[i]);
                if (!rc.canSenseLocation(loc) || !rc.sensePassability(loc) || !currLoc.isAdjacentTo(loc))
                    continue;
                robot = rc.senseRobotAtLocation(loc);
                if (robot != null && robot.ID != rc.getID())
                    continue;
                info = rc.senseMapInfo(loc);
                dist = Robot.home.distanceSquaredTo(loc);
                multiplier = info.getCooldownMultiplier(rc.getTeam()) +
                        (info.getCurrentDirection() == Direction.CENTER ? 0 : 10);
                if (multiplier < bestCardinalMultiplier
                        || (multiplier == bestCardinalMultiplier && dist > bestCardinalDist)) {
                    bestCardinalCollect = loc;
                    bestCardinalDist = dist;
                    bestCardinalMultiplier = multiplier;
                }
            }

            // If a cardinal direction has a better multiplier, we can choose that.
            if (bestCardinalCollect != null &&
                    (bestCollect == null || bestCardinalMultiplier < bestMultiplier)) {
                bestCollect = bestCardinalCollect;
            }
        }

        return bestCollect;
    }

    static MapLocation findInitLocation(RobotType type, Direction dir) throws GameActionException {
        MapLocation[] possibleLocations;
        MapLocation newLoc;
        for (int i = 8; --i >= 0;) {
            possibleLocations = getInitLocs(dir);
            for (int x = 0; x < possibleLocations.length; x++) {
                newLoc = possibleLocations[x];
                if (rc.canBuildRobot(type, newLoc) &&
                // Don't mix up carriers reading the wrong HQ's flag
                        (type != RobotType.CARRIER ||
                                !Headquarters.nearFriendlyHQ ||
                                Headquarters.friendlyHQLoc.distanceSquaredTo(newLoc) > Robot.home
                                        .distanceSquaredTo(newLoc))) {
                    return newLoc;
                }
            }
            dir = dir.rotateRight();
        }
        return null;
    }

    static MapLocation moveTowardsMe(MapLocation loc) {
        Direction dirToMe = loc.directionTo(rc.getLocation());
        return loc.add(dirToMe);
    }

    static int clip(int n, int lo, int hi) {
        return Math.min(Math.max(n, lo), hi);
    }

    static double clip(double n, double lo, double hi) {
        return Math.min(Math.max(n, lo), hi);
    }

    static MapLocation clipToWithinMap(MapLocation loc) {
        return new MapLocation(clip(loc.x, 0, MAP_WIDTH - 1), clip(loc.y, 0, MAP_HEIGHT - 1));
    }

    public static boolean onTheMap(MapLocation location) {
        return 0 <= location.x && location.x < MAP_WIDTH &&
                0 <= location.y && location.y < MAP_HEIGHT;
    }

    public static boolean onEdgeOfMap(MapLocation location) {
        return location.x == 0 || location.x == MAP_WIDTH - 1 ||
                location.y == 0 || location.y == MAP_HEIGHT - 1;
    }

    static Direction randomDirection() {
        return directions[Util.rng.nextInt(Util.directions.length)];
    }

    static Direction randomDirection(Direction[] newDirections) {
        return newDirections[Util.rng.nextInt(newDirections.length)];
    }

    static Direction[] getInOrderDirections(Direction target_dir) {
        return new Direction[] { target_dir, target_dir.rotateRight(), target_dir.rotateLeft(),
                target_dir.rotateRight().rotateRight(), target_dir.rotateLeft().rotateLeft() };
    }

    static Direction getFirstValidInOrderDirection(Direction dir) {
        for (Direction newDir : Util.getInOrderDirections(dir)) {
            if (rc.canMove(newDir)) {
                return newDir;
            }
        }
        return Direction.CENTER;
    }

    static Direction getFirstMoveableDir(Direction[] dirs) {
        for (Direction dir : dirs) {
            if (rc.canMove(dir)) {
                return dir;
            }
        }
        return Direction.CENTER;
    }

    static boolean isDirAdj(Direction dir, Direction dir2) {
        switch (dir) {
            case NORTH:
                switch (dir2) {
                    case NORTH:
                    case NORTHEAST:
                    case NORTHWEST:
                        return true;
                    default:
                        return false;
                }
            case NORTHEAST:
                switch (dir2) {
                    case NORTH:
                    case NORTHEAST:
                    case EAST:
                        return true;
                    default:
                        return false;
                }
            case EAST:
                switch (dir2) {
                    case NORTHEAST:
                    case EAST:
                    case SOUTHEAST:
                        return true;
                    default:
                        return false;
                }
            case SOUTHEAST:
                switch (dir2) {
                    case EAST:
                    case SOUTHEAST:
                    case SOUTH:
                        return true;
                    default:
                        return false;
                }
            case SOUTH:
                switch (dir2) {
                    case SOUTHEAST:
                    case SOUTH:
                    case SOUTHWEST:
                        return true;
                    default:
                        return false;
                }
            case SOUTHWEST:
                switch (dir2) {
                    case SOUTH:
                    case SOUTHWEST:
                    case WEST:
                        return true;
                    default:
                        return false;
                }
            case WEST:
                switch (dir2) {
                    case SOUTHWEST:
                    case WEST:
                    case NORTHWEST:
                        return true;
                    default:
                        return false;
                }
            case NORTHWEST:
                switch (dir2) {
                    case WEST:
                    case NORTHWEST:
                    case NORTH:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    // Gets the cooldown for a square, assuming you're traveling in a direction dir
    static double getCooldownMultiplier(MapLocation loc, Direction dir, boolean avoidClouds, boolean avoidCurrents,
            boolean onlyExactCurrent)
            throws GameActionException {
        double cooldown = 100;
        if (!rc.onTheMap(loc) || !rc.sensePassability(loc))
            return cooldown;

        // This shouldn't happen, but just in case
        if (!rc.canSenseLocation(loc))
            return 1;

        MapInfo info = rc.senseMapInfo(loc);
        if (avoidClouds && info.hasCloud())
            return cooldown;

        Direction currentDir = info.getCurrentDirection();
        if (avoidCurrents && currentDir != Direction.CENTER)
            return cooldown;
        cooldown = info.getCooldownMultiplier(rc.getTeam());

        if (Util.isDirAdj(currentDir, dir)) {
            if (!onlyExactCurrent || currentDir == dir)
                cooldown *= 0.5;
        } else if (Util.isDirAdj(currentDir, dir.opposite())) {
            cooldown *= 2;
        }

        return cooldown;
    }

    // The best heal location is
    // 1. On the island
    // 2. In a cloud
    // (3. Closest to other launchers) (TODO: implement this)
    // 4. Closest to you
    static MapLocation getBestHealLoc(int islandIdx) throws GameActionException {
        MapLocation[] islandLocs = rc.senseNearbyIslandLocations(islandIdx);
        MapLocation bestLoc = null;
        int bestScore = Integer.MIN_VALUE;
        int score = 0;
        MapLocation loc;
        RobotInfo robot;
        for (int i = islandLocs.length; --i >= 0;) {
            loc = islandLocs[i];
            robot = rc.senseRobotAtLocation(loc);
            if ((loc.x + loc.y) % 2 == 0)
                continue;
            score = 0;
            if (robot != null && robot.ID != rc.getID())
                score -= 1000;
            if (rc.senseCloud(loc))
                score += 1000;
            score -= rc.getLocation().distanceSquaredTo(loc);
            if (score > bestScore) {
                bestScore = score;
                bestLoc = loc;
            }
        }
        return bestLoc;
    }

    // Get the best heal loc within radius 4 of loc
    static MapLocation getBestHealLoc(MapLocation healLoc) throws GameActionException {
        MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(healLoc, 4);
        MapLocation bestLoc = null;
        int bestScore = Integer.MIN_VALUE;
        int score = 0;
        MapLocation loc;
        RobotInfo robot;
        for (int i = locs.length; --i >= 0;) {
            loc = locs[i];
            if (!rc.canSenseLocation(loc))
                continue;
            robot = rc.senseRobotAtLocation(loc);
            if ((loc.x + loc.y) % 2 == 0)
                continue;
            score = 0;
            if (robot != null && robot.ID != rc.getID())
                score -= 1000;
            if (rc.senseCloud(loc))
                score += 1000;
            score -= rc.getLocation().distanceSquaredTo(loc);
            if (score > bestScore) {
                bestScore = score;
                bestLoc = loc;
            }
        }
        return bestLoc;
    }

    static MapLocation getClosestEmptyIslandLoc(int islandIdx) throws GameActionException {
        MapLocation[] islandLocs = rc.senseNearbyIslandLocations(islandIdx);
        MapLocation bestLoc = null;
        int bestDist = Integer.MAX_VALUE;
        int dist = 0;
        MapLocation loc;
        RobotInfo robot;
        for (int i = islandLocs.length; --i >= 0;) {
            loc = islandLocs[i];
            robot = rc.senseRobotAtLocation(loc);
            if (robot != null && robot.ID != rc.getID())
                continue;

            dist = rc.getLocation().distanceSquaredTo(loc);
            if (dist < bestDist) {
                bestDist = dist;
                bestLoc = loc;
            }
        }
        return bestLoc;
    }

    static MapLocation getClosestIslandLoc(int islandIdx) throws GameActionException {
        MapLocation[] islandLocs = rc.senseNearbyIslandLocations(islandIdx);
        MapLocation bestLoc = null;
        int bestDist = Integer.MAX_VALUE;
        int dist = 0;
        MapLocation loc;
        for (int i = islandLocs.length; --i >= 0;) {
            loc = islandLocs[i];
            dist = rc.getLocation().distanceSquaredTo(loc);
            if (dist < bestDist) {
                bestDist = dist;
                bestLoc = loc;
            }
        }
        return bestLoc;
    }

    static boolean seesObstacleInWay(MapLocation target) throws GameActionException {
        MapLocation loc = rc.getLocation();
        Direction dir = loc.directionTo(target);
        MapLocation currLoc = loc;
        while (currLoc.distanceSquaredTo(target) > 2) {
            currLoc = currLoc.add(dir);
            if (!rc.canSenseLocation(currLoc) || !rc.sensePassability(currLoc))
                return true;
        }
        return false;
    }

    static MapLocation getDestabilizeLocToward(MapLocation loc) throws GameActionException {
        MapLocation currLoc = rc.getLocation();
        MapLocation target = loc;
        while (!rc.canDestabilize(target)
                && !target.isWithinDistanceSquared(currLoc, RobotType.DESTABILIZER.actionRadiusSquared)) {
            target = target.add(target.directionTo(currLoc));
        }
        return target;
    }

    static int getValidSymmetries() throws GameActionException {
        return Comms.readSymmetryAll() & MapTracker.validSymmetries;
    }
}
