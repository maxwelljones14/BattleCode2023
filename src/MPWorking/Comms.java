package MPWorking;

import battlecode.common.*;
import MPWorking.Debug.*;
import MPWorking.Util.*;

public class Comms {

    private static RobotController rc;
    private static RobotType robotType;

    static void init(RobotController r) {
        rc = r;
        robotType = rc.getType();
    }
}
