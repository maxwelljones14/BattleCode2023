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

    static final int CARRIER_COST = 50;
    static final int LAUNCHER_COST = 60;
    static final int ANCHOR_COST = 100;

    static final int CLEAR_ENEMY_INFO_PERIOD = 100;

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

}
