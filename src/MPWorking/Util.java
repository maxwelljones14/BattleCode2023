package MPWorking;

import battlecode.common.*;
import java.util.Random;

public class Util {
    static Random rng;

    private static RobotController rc;

    static int MAP_WIDTH;
    static int MAP_HEIGHT;
    static int MAP_AREA;
    static int MAP_MAX_DIST_SQUARED;

    static final int CLEAR_ENEMY_INFO_PERIOD = 100;

    static final double MIN_COOLDOWN_MULT_DIFF = 0.1;

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

    static final int JUST_OUTSIDE_OF_VISION_RADIUS = 34;
    static final int AMP_JUST_OUTSIDE_OF_VISION_RADIUS = 45;

    static final int MIN_LAUNCHERS_BEFORE_AMPLIFIER = 15;

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

    static MapLocation[] findInitLocation(MapLocation loc, Direction dir) {
        MapLocation mainLoc1 = loc.add(dir);
        MapLocation mainLoc2 = mainLoc1.add(dir);
        if (dir == Direction.NORTH || dir == Direction.EAST || dir == Direction.SOUTH || dir == Direction.WEST) {
            MapLocation mainLoc3 = mainLoc2.add(dir);
            return new MapLocation[] { mainLoc3, mainLoc1.add(dir.rotateLeft()), mainLoc1.add(dir.rotateRight()),
                    mainLoc2,
                    loc.add(dir.rotateLeft()), loc.add(dir.rotateRight()), mainLoc1 };
        } else {
            return new MapLocation[] { mainLoc2,
                    loc.add(dir.rotateLeft()), loc.add(dir.rotateRight()), mainLoc1 };
        }
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
        return new MapLocation(clip(loc.x, 0, MAP_WIDTH), clip(loc.y, 0, MAP_HEIGHT));
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
                return dir2 == Direction.NORTHEAST || dir2 == Direction.NORTHWEST;
            case NORTHEAST:
                return dir2 == Direction.NORTH || dir2 == Direction.EAST;
            case EAST:
                return dir2 == Direction.NORTHEAST || dir2 == Direction.SOUTHEAST;
            case SOUTHEAST:
                return dir2 == Direction.EAST || dir2 == Direction.SOUTH;
            case SOUTH:
                return dir2 == Direction.SOUTHWEST || dir2 == Direction.SOUTHEAST;
            case SOUTHWEST:
                return dir2 == Direction.SOUTH || dir2 == Direction.WEST;
            case WEST:
                return dir2 == Direction.NORTHWEST || dir2 == Direction.SOUTHWEST;
            case NORTHWEST:
                return dir2 == Direction.WEST || dir2 == Direction.NORTH;
            default:
                return false;
        }
    }

    // Gets the cooldown for a square, assuming you're traveling in a direction dir
    static double getCooldownMultiplier(MapLocation loc, Direction dir) throws GameActionException {
        double cooldown = 100;
        if (!rc.onTheMap(loc) || !rc.sensePassability(loc))
            return cooldown;

        // This shouldn't happen, but just in case
        if (!rc.canSenseLocation(loc))
            return 1;

        MapInfo info = rc.senseMapInfo(loc);
        Direction currentDir = info.getCurrentDirection();
        cooldown = info.getCooldownMultiplier(rc.getTeam());

        if (Util.isDirAdj(currentDir, dir)) {
            cooldown *= 0.5;
        } else if (Util.isDirAdj(currentDir, dir.opposite())) {
            cooldown *= 2;
        }

        return cooldown;
    }
}
