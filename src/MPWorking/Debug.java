package MPWorking;

import battlecode.common.*;

public class Debug {
    static final boolean VERBOSE = true;
    public static final boolean INFO = true;
    public static final boolean PATHFINDING = false;
    public static final boolean INDICATORS = true;

    private static RobotController rc;

    static void init(RobotController r) {
        rc = r;
        // sb = new StringBuilder();
    }

}
