package MPWorking.bfs;

import battlecode.common.*;

public class BFS20 {

    public static RobotController rc;

    static MapLocation l78; // location representing relative coordinate (-4, -2)
    static double d78; // shortest distance to location from current location
    static Direction dir78; // best direction to take now to optimally get to location

    static MapLocation l93; // location representing relative coordinate (-4, -1)
    static double d93; // shortest distance to location from current location
    static Direction dir93; // best direction to take now to optimally get to location

    static MapLocation l108; // location representing relative coordinate (-4, 0)
    static double d108; // shortest distance to location from current location
    static Direction dir108; // best direction to take now to optimally get to location

    static MapLocation l123; // location representing relative coordinate (-4, 1)
    static double d123; // shortest distance to location from current location
    static Direction dir123; // best direction to take now to optimally get to location

    static MapLocation l138; // location representing relative coordinate (-4, 2)
    static double d138; // shortest distance to location from current location
    static Direction dir138; // best direction to take now to optimally get to location

    static MapLocation l64; // location representing relative coordinate (-3, -3)
    static double d64; // shortest distance to location from current location
    static Direction dir64; // best direction to take now to optimally get to location

    static MapLocation l79; // location representing relative coordinate (-3, -2)
    static double d79; // shortest distance to location from current location
    static Direction dir79; // best direction to take now to optimally get to location

    static MapLocation l94; // location representing relative coordinate (-3, -1)
    static double d94; // shortest distance to location from current location
    static Direction dir94; // best direction to take now to optimally get to location

    static MapLocation l109; // location representing relative coordinate (-3, 0)
    static double d109; // shortest distance to location from current location
    static Direction dir109; // best direction to take now to optimally get to location

    static MapLocation l124; // location representing relative coordinate (-3, 1)
    static double d124; // shortest distance to location from current location
    static Direction dir124; // best direction to take now to optimally get to location

    static MapLocation l139; // location representing relative coordinate (-3, 2)
    static double d139; // shortest distance to location from current location
    static Direction dir139; // best direction to take now to optimally get to location

    static MapLocation l154; // location representing relative coordinate (-3, 3)
    static double d154; // shortest distance to location from current location
    static Direction dir154; // best direction to take now to optimally get to location

    static MapLocation l50; // location representing relative coordinate (-2, -4)
    static double d50; // shortest distance to location from current location
    static Direction dir50; // best direction to take now to optimally get to location

    static MapLocation l65; // location representing relative coordinate (-2, -3)
    static double d65; // shortest distance to location from current location
    static Direction dir65; // best direction to take now to optimally get to location

    static MapLocation l80; // location representing relative coordinate (-2, -2)
    static double d80; // shortest distance to location from current location
    static Direction dir80; // best direction to take now to optimally get to location

    static MapLocation l95; // location representing relative coordinate (-2, -1)
    static double d95; // shortest distance to location from current location
    static Direction dir95; // best direction to take now to optimally get to location

    static MapLocation l110; // location representing relative coordinate (-2, 0)
    static double d110; // shortest distance to location from current location
    static Direction dir110; // best direction to take now to optimally get to location

    static MapLocation l125; // location representing relative coordinate (-2, 1)
    static double d125; // shortest distance to location from current location
    static Direction dir125; // best direction to take now to optimally get to location

    static MapLocation l140; // location representing relative coordinate (-2, 2)
    static double d140; // shortest distance to location from current location
    static Direction dir140; // best direction to take now to optimally get to location

    static MapLocation l155; // location representing relative coordinate (-2, 3)
    static double d155; // shortest distance to location from current location
    static Direction dir155; // best direction to take now to optimally get to location

    static MapLocation l170; // location representing relative coordinate (-2, 4)
    static double d170; // shortest distance to location from current location
    static Direction dir170; // best direction to take now to optimally get to location

    static MapLocation l51; // location representing relative coordinate (-1, -4)
    static double d51; // shortest distance to location from current location
    static Direction dir51; // best direction to take now to optimally get to location

    static MapLocation l66; // location representing relative coordinate (-1, -3)
    static double d66; // shortest distance to location from current location
    static Direction dir66; // best direction to take now to optimally get to location

    static MapLocation l81; // location representing relative coordinate (-1, -2)
    static double d81; // shortest distance to location from current location
    static Direction dir81; // best direction to take now to optimally get to location

    static MapLocation l96; // location representing relative coordinate (-1, -1)
    static double d96; // shortest distance to location from current location
    static Direction dir96; // best direction to take now to optimally get to location

    static MapLocation l111; // location representing relative coordinate (-1, 0)
    static double d111; // shortest distance to location from current location
    static Direction dir111; // best direction to take now to optimally get to location

    static MapLocation l126; // location representing relative coordinate (-1, 1)
    static double d126; // shortest distance to location from current location
    static Direction dir126; // best direction to take now to optimally get to location

    static MapLocation l141; // location representing relative coordinate (-1, 2)
    static double d141; // shortest distance to location from current location
    static Direction dir141; // best direction to take now to optimally get to location

    static MapLocation l156; // location representing relative coordinate (-1, 3)
    static double d156; // shortest distance to location from current location
    static Direction dir156; // best direction to take now to optimally get to location

    static MapLocation l171; // location representing relative coordinate (-1, 4)
    static double d171; // shortest distance to location from current location
    static Direction dir171; // best direction to take now to optimally get to location

    static MapLocation l52; // location representing relative coordinate (0, -4)
    static double d52; // shortest distance to location from current location
    static Direction dir52; // best direction to take now to optimally get to location

    static MapLocation l67; // location representing relative coordinate (0, -3)
    static double d67; // shortest distance to location from current location
    static Direction dir67; // best direction to take now to optimally get to location

    static MapLocation l82; // location representing relative coordinate (0, -2)
    static double d82; // shortest distance to location from current location
    static Direction dir82; // best direction to take now to optimally get to location

    static MapLocation l97; // location representing relative coordinate (0, -1)
    static double d97; // shortest distance to location from current location
    static Direction dir97; // best direction to take now to optimally get to location

    static MapLocation l112; // location representing relative coordinate (0, 0)
    static double d112; // shortest distance to location from current location
    static Direction dir112; // best direction to take now to optimally get to location

    static MapLocation l127; // location representing relative coordinate (0, 1)
    static double d127; // shortest distance to location from current location
    static Direction dir127; // best direction to take now to optimally get to location

    static MapLocation l142; // location representing relative coordinate (0, 2)
    static double d142; // shortest distance to location from current location
    static Direction dir142; // best direction to take now to optimally get to location

    static MapLocation l157; // location representing relative coordinate (0, 3)
    static double d157; // shortest distance to location from current location
    static Direction dir157; // best direction to take now to optimally get to location

    static MapLocation l172; // location representing relative coordinate (0, 4)
    static double d172; // shortest distance to location from current location
    static Direction dir172; // best direction to take now to optimally get to location

    static MapLocation l53; // location representing relative coordinate (1, -4)
    static double d53; // shortest distance to location from current location
    static Direction dir53; // best direction to take now to optimally get to location

    static MapLocation l68; // location representing relative coordinate (1, -3)
    static double d68; // shortest distance to location from current location
    static Direction dir68; // best direction to take now to optimally get to location

    static MapLocation l83; // location representing relative coordinate (1, -2)
    static double d83; // shortest distance to location from current location
    static Direction dir83; // best direction to take now to optimally get to location

    static MapLocation l98; // location representing relative coordinate (1, -1)
    static double d98; // shortest distance to location from current location
    static Direction dir98; // best direction to take now to optimally get to location

    static MapLocation l113; // location representing relative coordinate (1, 0)
    static double d113; // shortest distance to location from current location
    static Direction dir113; // best direction to take now to optimally get to location

    static MapLocation l128; // location representing relative coordinate (1, 1)
    static double d128; // shortest distance to location from current location
    static Direction dir128; // best direction to take now to optimally get to location

    static MapLocation l143; // location representing relative coordinate (1, 2)
    static double d143; // shortest distance to location from current location
    static Direction dir143; // best direction to take now to optimally get to location

    static MapLocation l158; // location representing relative coordinate (1, 3)
    static double d158; // shortest distance to location from current location
    static Direction dir158; // best direction to take now to optimally get to location

    static MapLocation l173; // location representing relative coordinate (1, 4)
    static double d173; // shortest distance to location from current location
    static Direction dir173; // best direction to take now to optimally get to location

    static MapLocation l54; // location representing relative coordinate (2, -4)
    static double d54; // shortest distance to location from current location
    static Direction dir54; // best direction to take now to optimally get to location

    static MapLocation l69; // location representing relative coordinate (2, -3)
    static double d69; // shortest distance to location from current location
    static Direction dir69; // best direction to take now to optimally get to location

    static MapLocation l84; // location representing relative coordinate (2, -2)
    static double d84; // shortest distance to location from current location
    static Direction dir84; // best direction to take now to optimally get to location

    static MapLocation l99; // location representing relative coordinate (2, -1)
    static double d99; // shortest distance to location from current location
    static Direction dir99; // best direction to take now to optimally get to location

    static MapLocation l114; // location representing relative coordinate (2, 0)
    static double d114; // shortest distance to location from current location
    static Direction dir114; // best direction to take now to optimally get to location

    static MapLocation l129; // location representing relative coordinate (2, 1)
    static double d129; // shortest distance to location from current location
    static Direction dir129; // best direction to take now to optimally get to location

    static MapLocation l144; // location representing relative coordinate (2, 2)
    static double d144; // shortest distance to location from current location
    static Direction dir144; // best direction to take now to optimally get to location

    static MapLocation l159; // location representing relative coordinate (2, 3)
    static double d159; // shortest distance to location from current location
    static Direction dir159; // best direction to take now to optimally get to location

    static MapLocation l174; // location representing relative coordinate (2, 4)
    static double d174; // shortest distance to location from current location
    static Direction dir174; // best direction to take now to optimally get to location

    static MapLocation l70; // location representing relative coordinate (3, -3)
    static double d70; // shortest distance to location from current location
    static Direction dir70; // best direction to take now to optimally get to location

    static MapLocation l85; // location representing relative coordinate (3, -2)
    static double d85; // shortest distance to location from current location
    static Direction dir85; // best direction to take now to optimally get to location

    static MapLocation l100; // location representing relative coordinate (3, -1)
    static double d100; // shortest distance to location from current location
    static Direction dir100; // best direction to take now to optimally get to location

    static MapLocation l115; // location representing relative coordinate (3, 0)
    static double d115; // shortest distance to location from current location
    static Direction dir115; // best direction to take now to optimally get to location

    static MapLocation l130; // location representing relative coordinate (3, 1)
    static double d130; // shortest distance to location from current location
    static Direction dir130; // best direction to take now to optimally get to location

    static MapLocation l145; // location representing relative coordinate (3, 2)
    static double d145; // shortest distance to location from current location
    static Direction dir145; // best direction to take now to optimally get to location

    static MapLocation l160; // location representing relative coordinate (3, 3)
    static double d160; // shortest distance to location from current location
    static Direction dir160; // best direction to take now to optimally get to location

    static MapLocation l86; // location representing relative coordinate (4, -2)
    static double d86; // shortest distance to location from current location
    static Direction dir86; // best direction to take now to optimally get to location

    static MapLocation l101; // location representing relative coordinate (4, -1)
    static double d101; // shortest distance to location from current location
    static Direction dir101; // best direction to take now to optimally get to location

    static MapLocation l116; // location representing relative coordinate (4, 0)
    static double d116; // shortest distance to location from current location
    static Direction dir116; // best direction to take now to optimally get to location

    static MapLocation l131; // location representing relative coordinate (4, 1)
    static double d131; // shortest distance to location from current location
    static Direction dir131; // best direction to take now to optimally get to location

    static MapLocation l146; // location representing relative coordinate (4, 2)
    static double d146; // shortest distance to location from current location
    static Direction dir146; // best direction to take now to optimally get to location


    public static void init(RobotController r) {
        rc = r;
        team = rc.getTeam();
    }

    public final static Direction NORTH = Direction.NORTH;
    public final static Direction NORTHEAST = Direction.NORTHEAST;
    public final static Direction EAST = Direction.EAST;
    public final static Direction SOUTHEAST = Direction.SOUTHEAST;
    public final static Direction SOUTH = Direction.SOUTH;
    public final static Direction SOUTHWEST = Direction.SOUTHWEST;
    public final static Direction WEST = Direction.WEST;
    public final static Direction NORTHWEST = Direction.NORTHWEST;
    public final static Direction CENTER = Direction.CENTER;

    public static MapInfo mapInfo;
    public static Direction currentDir;
    public static Team team;

    public static Direction bestDir(MapLocation target) throws GameActionException {

        l112 = rc.getLocation();
        d112 = 0;
        dir112 = CENTER;

        l111 = l112.add(WEST); // (-1, 0) from (0, 0)
        d111 = 99999;
        dir111 = null;

        l97 = l112.add(SOUTH); // (0, -1) from (0, 0)
        d97 = 99999;
        dir97 = null;

        l127 = l112.add(NORTH); // (0, 1) from (0, 0)
        d127 = 99999;
        dir127 = null;

        l113 = l112.add(EAST); // (1, 0) from (0, 0)
        d113 = 99999;
        dir113 = null;

        l96 = l112.add(SOUTHWEST); // (-1, -1) from (0, 0)
        d96 = 99999;
        dir96 = null;

        l126 = l112.add(NORTHWEST); // (-1, 1) from (0, 0)
        d126 = 99999;
        dir126 = null;

        l98 = l112.add(SOUTHEAST); // (1, -1) from (0, 0)
        d98 = 99999;
        dir98 = null;

        l128 = l112.add(NORTHEAST); // (1, 1) from (0, 0)
        d128 = 99999;
        dir128 = null;

        l110 = l111.add(WEST); // (-2, 0) from (-1, 0)
        d110 = 99999;
        dir110 = null;

        l82 = l97.add(SOUTH); // (0, -2) from (0, -1)
        d82 = 99999;
        dir82 = null;

        l142 = l127.add(NORTH); // (0, 2) from (0, 1)
        d142 = 99999;
        dir142 = null;

        l114 = l113.add(EAST); // (2, 0) from (1, 0)
        d114 = 99999;
        dir114 = null;

        l95 = l111.add(SOUTHWEST); // (-2, -1) from (-1, 0)
        d95 = 99999;
        dir95 = null;

        l125 = l111.add(NORTHWEST); // (-2, 1) from (-1, 0)
        d125 = 99999;
        dir125 = null;

        l81 = l97.add(SOUTHWEST); // (-1, -2) from (0, -1)
        d81 = 99999;
        dir81 = null;

        l141 = l127.add(NORTHWEST); // (-1, 2) from (0, 1)
        d141 = 99999;
        dir141 = null;

        l83 = l97.add(SOUTHEAST); // (1, -2) from (0, -1)
        d83 = 99999;
        dir83 = null;

        l143 = l127.add(NORTHEAST); // (1, 2) from (0, 1)
        d143 = 99999;
        dir143 = null;

        l99 = l113.add(SOUTHEAST); // (2, -1) from (1, 0)
        d99 = 99999;
        dir99 = null;

        l129 = l113.add(NORTHEAST); // (2, 1) from (1, 0)
        d129 = 99999;
        dir129 = null;

        l80 = l96.add(SOUTHWEST); // (-2, -2) from (-1, -1)
        d80 = 99999;
        dir80 = null;

        l140 = l126.add(NORTHWEST); // (-2, 2) from (-1, 1)
        d140 = 99999;
        dir140 = null;

        l84 = l98.add(SOUTHEAST); // (2, -2) from (1, -1)
        d84 = 99999;
        dir84 = null;

        l144 = l128.add(NORTHEAST); // (2, 2) from (1, 1)
        d144 = 99999;
        dir144 = null;

        l109 = l110.add(WEST); // (-3, 0) from (-2, 0)
        d109 = 99999;
        dir109 = null;

        l67 = l82.add(SOUTH); // (0, -3) from (0, -2)
        d67 = 99999;
        dir67 = null;

        l157 = l142.add(NORTH); // (0, 3) from (0, 2)
        d157 = 99999;
        dir157 = null;

        l115 = l114.add(EAST); // (3, 0) from (2, 0)
        d115 = 99999;
        dir115 = null;

        l94 = l110.add(SOUTHWEST); // (-3, -1) from (-2, 0)
        d94 = 99999;
        dir94 = null;

        l124 = l110.add(NORTHWEST); // (-3, 1) from (-2, 0)
        d124 = 99999;
        dir124 = null;

        l66 = l82.add(SOUTHWEST); // (-1, -3) from (0, -2)
        d66 = 99999;
        dir66 = null;

        l156 = l142.add(NORTHWEST); // (-1, 3) from (0, 2)
        d156 = 99999;
        dir156 = null;

        l68 = l82.add(SOUTHEAST); // (1, -3) from (0, -2)
        d68 = 99999;
        dir68 = null;

        l158 = l142.add(NORTHEAST); // (1, 3) from (0, 2)
        d158 = 99999;
        dir158 = null;

        l100 = l114.add(SOUTHEAST); // (3, -1) from (2, 0)
        d100 = 99999;
        dir100 = null;

        l130 = l114.add(NORTHEAST); // (3, 1) from (2, 0)
        d130 = 99999;
        dir130 = null;

        l79 = l95.add(SOUTHWEST); // (-3, -2) from (-2, -1)
        d79 = 99999;
        dir79 = null;

        l139 = l125.add(NORTHWEST); // (-3, 2) from (-2, 1)
        d139 = 99999;
        dir139 = null;

        l65 = l81.add(SOUTHWEST); // (-2, -3) from (-1, -2)
        d65 = 99999;
        dir65 = null;

        l155 = l141.add(NORTHWEST); // (-2, 3) from (-1, 2)
        d155 = 99999;
        dir155 = null;

        l69 = l83.add(SOUTHEAST); // (2, -3) from (1, -2)
        d69 = 99999;
        dir69 = null;

        l159 = l143.add(NORTHEAST); // (2, 3) from (1, 2)
        d159 = 99999;
        dir159 = null;

        l85 = l99.add(SOUTHEAST); // (3, -2) from (2, -1)
        d85 = 99999;
        dir85 = null;

        l145 = l129.add(NORTHEAST); // (3, 2) from (2, 1)
        d145 = 99999;
        dir145 = null;

        l108 = l109.add(WEST); // (-4, 0) from (-3, 0)
        d108 = 99999;
        dir108 = null;

        l52 = l67.add(SOUTH); // (0, -4) from (0, -3)
        d52 = 99999;
        dir52 = null;

        l172 = l157.add(NORTH); // (0, 4) from (0, 3)
        d172 = 99999;
        dir172 = null;

        l116 = l115.add(EAST); // (4, 0) from (3, 0)
        d116 = 99999;
        dir116 = null;

        l93 = l109.add(SOUTHWEST); // (-4, -1) from (-3, 0)
        d93 = 99999;
        dir93 = null;

        l123 = l109.add(NORTHWEST); // (-4, 1) from (-3, 0)
        d123 = 99999;
        dir123 = null;

        l51 = l67.add(SOUTHWEST); // (-1, -4) from (0, -3)
        d51 = 99999;
        dir51 = null;

        l171 = l157.add(NORTHWEST); // (-1, 4) from (0, 3)
        d171 = 99999;
        dir171 = null;

        l53 = l67.add(SOUTHEAST); // (1, -4) from (0, -3)
        d53 = 99999;
        dir53 = null;

        l173 = l157.add(NORTHEAST); // (1, 4) from (0, 3)
        d173 = 99999;
        dir173 = null;

        l101 = l115.add(SOUTHEAST); // (4, -1) from (3, 0)
        d101 = 99999;
        dir101 = null;

        l131 = l115.add(NORTHEAST); // (4, 1) from (3, 0)
        d131 = 99999;
        dir131 = null;

        l64 = l80.add(SOUTHWEST); // (-3, -3) from (-2, -2)
        d64 = 99999;
        dir64 = null;

        l154 = l140.add(NORTHWEST); // (-3, 3) from (-2, 2)
        d154 = 99999;
        dir154 = null;

        l70 = l84.add(SOUTHEAST); // (3, -3) from (2, -2)
        d70 = 99999;
        dir70 = null;

        l160 = l144.add(NORTHEAST); // (3, 3) from (2, 2)
        d160 = 99999;
        dir160 = null;

        l78 = l94.add(SOUTHWEST); // (-4, -2) from (-3, -1)
        d78 = 99999;
        dir78 = null;

        l138 = l124.add(NORTHWEST); // (-4, 2) from (-3, 1)
        d138 = 99999;
        dir138 = null;

        l50 = l66.add(SOUTHWEST); // (-2, -4) from (-1, -3)
        d50 = 99999;
        dir50 = null;

        l170 = l156.add(NORTHWEST); // (-2, 4) from (-1, 3)
        d170 = 99999;
        dir170 = null;

        l54 = l68.add(SOUTHEAST); // (2, -4) from (1, -3)
        d54 = 99999;
        dir54 = null;

        l174 = l158.add(NORTHEAST); // (2, 4) from (1, 3)
        d174 = 99999;
        dir174 = null;

        l86 = l100.add(SOUTHEAST); // (4, -2) from (3, -1)
        d86 = 99999;
        dir86 = null;

        l146 = l130.add(NORTHEAST); // (4, 2) from (3, 1)
        d146 = 99999;
        dir146 = null;



        if (rc.onTheMap(l111)) { // check (-1, 0)
            if (rc.canSenseLocation(l111) && rc.sensePassability(l111)) { 
                if (d111 > d112) { // from (0, 0)
                    d111 = d112;
                    dir111 = WEST;
                }
                d111 += 1;
            }
        }

        if (rc.onTheMap(l97)) { // check (0, -1)
            if (rc.canSenseLocation(l97) && rc.sensePassability(l97)) { 
                if (d97 > d112) { // from (0, 0)
                    d97 = d112;
                    dir97 = SOUTH;
                }
                if (d97 > d111) { // from (-1, 0)
                    d97 = d111;
                    dir97 = dir111;
                }
                d97 += 1;
            }
        }

        if (rc.onTheMap(l127)) { // check (0, 1)
            if (rc.canSenseLocation(l127) && rc.sensePassability(l127)) { 
                if (d127 > d112) { // from (0, 0)
                    d127 = d112;
                    dir127 = NORTH;
                }
                if (d127 > d111) { // from (-1, 0)
                    d127 = d111;
                    dir127 = dir111;
                }
                d127 += 1;
            }
        }

        if (rc.onTheMap(l113)) { // check (1, 0)
            if (rc.canSenseLocation(l113) && rc.sensePassability(l113)) { 
                if (d113 > d112) { // from (0, 0)
                    d113 = d112;
                    dir113 = EAST;
                }
                if (d113 > d97) { // from (0, -1)
                    d113 = d97;
                    dir113 = dir97;
                }
                if (d113 > d127) { // from (0, 1)
                    d113 = d127;
                    dir113 = dir127;
                }
                d113 += 1;
            }
        }

        if (rc.onTheMap(l96)) { // check (-1, -1)
            if (rc.canSenseLocation(l96) && rc.sensePassability(l96)) { 
                if (d96 > d112) { // from (0, 0)
                    d96 = d112;
                    dir96 = SOUTHWEST;
                }
                if (d96 > d111) { // from (-1, 0)
                    d96 = d111;
                    dir96 = dir111;
                }
                if (d96 > d97) { // from (0, -1)
                    d96 = d97;
                    dir96 = dir97;
                }
                d96 += 1;
            }
        }

        if (rc.onTheMap(l126)) { // check (-1, 1)
            if (rc.canSenseLocation(l126) && rc.sensePassability(l126)) { 
                if (d126 > d112) { // from (0, 0)
                    d126 = d112;
                    dir126 = NORTHWEST;
                }
                if (d126 > d111) { // from (-1, 0)
                    d126 = d111;
                    dir126 = dir111;
                }
                if (d126 > d127) { // from (0, 1)
                    d126 = d127;
                    dir126 = dir127;
                }
                d126 += 1;
            }
        }

        if (rc.onTheMap(l98)) { // check (1, -1)
            if (rc.canSenseLocation(l98) && rc.sensePassability(l98)) { 
                if (d98 > d112) { // from (0, 0)
                    d98 = d112;
                    dir98 = SOUTHEAST;
                }
                if (d98 > d97) { // from (0, -1)
                    d98 = d97;
                    dir98 = dir97;
                }
                if (d98 > d113) { // from (1, 0)
                    d98 = d113;
                    dir98 = dir113;
                }
                d98 += 1;
            }
        }

        if (rc.onTheMap(l128)) { // check (1, 1)
            if (rc.canSenseLocation(l128) && rc.sensePassability(l128)) { 
                if (d128 > d112) { // from (0, 0)
                    d128 = d112;
                    dir128 = NORTHEAST;
                }
                if (d128 > d127) { // from (0, 1)
                    d128 = d127;
                    dir128 = dir127;
                }
                if (d128 > d113) { // from (1, 0)
                    d128 = d113;
                    dir128 = dir113;
                }
                d128 += 1;
            }
        }

        if (rc.onTheMap(l110)) { // check (-2, 0)
            if (rc.canSenseLocation(l110) && rc.sensePassability(l110)) { 
                if (d110 > d111) { // from (-1, 0)
                    d110 = d111;
                    dir110 = dir111;
                }
                if (d110 > d96) { // from (-1, -1)
                    d110 = d96;
                    dir110 = dir96;
                }
                if (d110 > d126) { // from (-1, 1)
                    d110 = d126;
                    dir110 = dir126;
                }
                d110 += 1;
            }
        }

        if (rc.onTheMap(l82)) { // check (0, -2)
            if (rc.canSenseLocation(l82) && rc.sensePassability(l82)) { 
                if (d82 > d97) { // from (0, -1)
                    d82 = d97;
                    dir82 = dir97;
                }
                if (d82 > d96) { // from (-1, -1)
                    d82 = d96;
                    dir82 = dir96;
                }
                if (d82 > d98) { // from (1, -1)
                    d82 = d98;
                    dir82 = dir98;
                }
                d82 += 1;
            }
        }

        if (rc.onTheMap(l142)) { // check (0, 2)
            if (rc.canSenseLocation(l142) && rc.sensePassability(l142)) { 
                if (d142 > d127) { // from (0, 1)
                    d142 = d127;
                    dir142 = dir127;
                }
                if (d142 > d126) { // from (-1, 1)
                    d142 = d126;
                    dir142 = dir126;
                }
                if (d142 > d128) { // from (1, 1)
                    d142 = d128;
                    dir142 = dir128;
                }
                d142 += 1;
            }
        }

        if (rc.onTheMap(l114)) { // check (2, 0)
            if (rc.canSenseLocation(l114) && rc.sensePassability(l114)) { 
                if (d114 > d113) { // from (1, 0)
                    d114 = d113;
                    dir114 = dir113;
                }
                if (d114 > d98) { // from (1, -1)
                    d114 = d98;
                    dir114 = dir98;
                }
                if (d114 > d128) { // from (1, 1)
                    d114 = d128;
                    dir114 = dir128;
                }
                d114 += 1;
            }
        }

        if (rc.onTheMap(l95)) { // check (-2, -1)
            if (rc.canSenseLocation(l95) && rc.sensePassability(l95)) { 
                if (d95 > d111) { // from (-1, 0)
                    d95 = d111;
                    dir95 = dir111;
                }
                if (d95 > d96) { // from (-1, -1)
                    d95 = d96;
                    dir95 = dir96;
                }
                if (d95 > d110) { // from (-2, 0)
                    d95 = d110;
                    dir95 = dir110;
                }
                d95 += 1;
            }
        }

        if (rc.onTheMap(l125)) { // check (-2, 1)
            if (rc.canSenseLocation(l125) && rc.sensePassability(l125)) { 
                if (d125 > d111) { // from (-1, 0)
                    d125 = d111;
                    dir125 = dir111;
                }
                if (d125 > d126) { // from (-1, 1)
                    d125 = d126;
                    dir125 = dir126;
                }
                if (d125 > d110) { // from (-2, 0)
                    d125 = d110;
                    dir125 = dir110;
                }
                d125 += 1;
            }
        }

        if (rc.onTheMap(l81)) { // check (-1, -2)
            if (rc.canSenseLocation(l81) && rc.sensePassability(l81)) { 
                if (d81 > d97) { // from (0, -1)
                    d81 = d97;
                    dir81 = dir97;
                }
                if (d81 > d96) { // from (-1, -1)
                    d81 = d96;
                    dir81 = dir96;
                }
                if (d81 > d82) { // from (0, -2)
                    d81 = d82;
                    dir81 = dir82;
                }
                if (d81 > d95) { // from (-2, -1)
                    d81 = d95;
                    dir81 = dir95;
                }
                d81 += 1;
            }
        }

        if (rc.onTheMap(l141)) { // check (-1, 2)
            if (rc.canSenseLocation(l141) && rc.sensePassability(l141)) { 
                if (d141 > d127) { // from (0, 1)
                    d141 = d127;
                    dir141 = dir127;
                }
                if (d141 > d126) { // from (-1, 1)
                    d141 = d126;
                    dir141 = dir126;
                }
                if (d141 > d142) { // from (0, 2)
                    d141 = d142;
                    dir141 = dir142;
                }
                if (d141 > d125) { // from (-2, 1)
                    d141 = d125;
                    dir141 = dir125;
                }
                d141 += 1;
            }
        }

        if (rc.onTheMap(l83)) { // check (1, -2)
            if (rc.canSenseLocation(l83) && rc.sensePassability(l83)) { 
                if (d83 > d97) { // from (0, -1)
                    d83 = d97;
                    dir83 = dir97;
                }
                if (d83 > d98) { // from (1, -1)
                    d83 = d98;
                    dir83 = dir98;
                }
                if (d83 > d82) { // from (0, -2)
                    d83 = d82;
                    dir83 = dir82;
                }
                d83 += 1;
            }
        }

        if (rc.onTheMap(l143)) { // check (1, 2)
            if (rc.canSenseLocation(l143) && rc.sensePassability(l143)) { 
                if (d143 > d127) { // from (0, 1)
                    d143 = d127;
                    dir143 = dir127;
                }
                if (d143 > d128) { // from (1, 1)
                    d143 = d128;
                    dir143 = dir128;
                }
                if (d143 > d142) { // from (0, 2)
                    d143 = d142;
                    dir143 = dir142;
                }
                d143 += 1;
            }
        }

        if (rc.onTheMap(l99)) { // check (2, -1)
            if (rc.canSenseLocation(l99) && rc.sensePassability(l99)) { 
                if (d99 > d113) { // from (1, 0)
                    d99 = d113;
                    dir99 = dir113;
                }
                if (d99 > d98) { // from (1, -1)
                    d99 = d98;
                    dir99 = dir98;
                }
                if (d99 > d114) { // from (2, 0)
                    d99 = d114;
                    dir99 = dir114;
                }
                if (d99 > d83) { // from (1, -2)
                    d99 = d83;
                    dir99 = dir83;
                }
                d99 += 1;
            }
        }

        if (rc.onTheMap(l129)) { // check (2, 1)
            if (rc.canSenseLocation(l129) && rc.sensePassability(l129)) { 
                if (d129 > d113) { // from (1, 0)
                    d129 = d113;
                    dir129 = dir113;
                }
                if (d129 > d128) { // from (1, 1)
                    d129 = d128;
                    dir129 = dir128;
                }
                if (d129 > d114) { // from (2, 0)
                    d129 = d114;
                    dir129 = dir114;
                }
                if (d129 > d143) { // from (1, 2)
                    d129 = d143;
                    dir129 = dir143;
                }
                d129 += 1;
            }
        }

        if (rc.onTheMap(l80)) { // check (-2, -2)
            if (rc.canSenseLocation(l80) && rc.sensePassability(l80)) { 
                if (d80 > d96) { // from (-1, -1)
                    d80 = d96;
                    dir80 = dir96;
                }
                if (d80 > d95) { // from (-2, -1)
                    d80 = d95;
                    dir80 = dir95;
                }
                if (d80 > d81) { // from (-1, -2)
                    d80 = d81;
                    dir80 = dir81;
                }
                d80 += 1;
            }
        }

        if (rc.onTheMap(l140)) { // check (-2, 2)
            if (rc.canSenseLocation(l140) && rc.sensePassability(l140)) { 
                if (d140 > d126) { // from (-1, 1)
                    d140 = d126;
                    dir140 = dir126;
                }
                if (d140 > d125) { // from (-2, 1)
                    d140 = d125;
                    dir140 = dir125;
                }
                if (d140 > d141) { // from (-1, 2)
                    d140 = d141;
                    dir140 = dir141;
                }
                d140 += 1;
            }
        }

        if (rc.onTheMap(l84)) { // check (2, -2)
            if (rc.canSenseLocation(l84) && rc.sensePassability(l84)) { 
                if (d84 > d98) { // from (1, -1)
                    d84 = d98;
                    dir84 = dir98;
                }
                if (d84 > d83) { // from (1, -2)
                    d84 = d83;
                    dir84 = dir83;
                }
                if (d84 > d99) { // from (2, -1)
                    d84 = d99;
                    dir84 = dir99;
                }
                d84 += 1;
            }
        }

        if (rc.onTheMap(l144)) { // check (2, 2)
            if (rc.canSenseLocation(l144) && rc.sensePassability(l144)) { 
                if (d144 > d128) { // from (1, 1)
                    d144 = d128;
                    dir144 = dir128;
                }
                if (d144 > d143) { // from (1, 2)
                    d144 = d143;
                    dir144 = dir143;
                }
                if (d144 > d129) { // from (2, 1)
                    d144 = d129;
                    dir144 = dir129;
                }
                d144 += 1;
            }
        }

        if (rc.onTheMap(l109)) { // check (-3, 0)
            if (rc.canSenseLocation(l109) && rc.sensePassability(l109)) { 
                if (d109 > d110) { // from (-2, 0)
                    d109 = d110;
                    dir109 = dir110;
                }
                if (d109 > d95) { // from (-2, -1)
                    d109 = d95;
                    dir109 = dir95;
                }
                if (d109 > d125) { // from (-2, 1)
                    d109 = d125;
                    dir109 = dir125;
                }
                d109 += 1;
            }
        }

        if (rc.onTheMap(l67)) { // check (0, -3)
            if (rc.canSenseLocation(l67) && rc.sensePassability(l67)) { 
                if (d67 > d82) { // from (0, -2)
                    d67 = d82;
                    dir67 = dir82;
                }
                if (d67 > d81) { // from (-1, -2)
                    d67 = d81;
                    dir67 = dir81;
                }
                if (d67 > d83) { // from (1, -2)
                    d67 = d83;
                    dir67 = dir83;
                }
                d67 += 1;
            }
        }

        if (rc.onTheMap(l157)) { // check (0, 3)
            if (rc.canSenseLocation(l157) && rc.sensePassability(l157)) { 
                if (d157 > d142) { // from (0, 2)
                    d157 = d142;
                    dir157 = dir142;
                }
                if (d157 > d141) { // from (-1, 2)
                    d157 = d141;
                    dir157 = dir141;
                }
                if (d157 > d143) { // from (1, 2)
                    d157 = d143;
                    dir157 = dir143;
                }
                d157 += 1;
            }
        }

        if (rc.onTheMap(l115)) { // check (3, 0)
            if (rc.canSenseLocation(l115) && rc.sensePassability(l115)) { 
                if (d115 > d114) { // from (2, 0)
                    d115 = d114;
                    dir115 = dir114;
                }
                if (d115 > d99) { // from (2, -1)
                    d115 = d99;
                    dir115 = dir99;
                }
                if (d115 > d129) { // from (2, 1)
                    d115 = d129;
                    dir115 = dir129;
                }
                d115 += 1;
            }
        }

        if (rc.onTheMap(l94)) { // check (-3, -1)
            if (rc.canSenseLocation(l94) && rc.sensePassability(l94)) { 
                if (d94 > d110) { // from (-2, 0)
                    d94 = d110;
                    dir94 = dir110;
                }
                if (d94 > d95) { // from (-2, -1)
                    d94 = d95;
                    dir94 = dir95;
                }
                if (d94 > d80) { // from (-2, -2)
                    d94 = d80;
                    dir94 = dir80;
                }
                if (d94 > d109) { // from (-3, 0)
                    d94 = d109;
                    dir94 = dir109;
                }
                d94 += 1;
            }
        }

        if (rc.onTheMap(l124)) { // check (-3, 1)
            if (rc.canSenseLocation(l124) && rc.sensePassability(l124)) { 
                if (d124 > d110) { // from (-2, 0)
                    d124 = d110;
                    dir124 = dir110;
                }
                if (d124 > d125) { // from (-2, 1)
                    d124 = d125;
                    dir124 = dir125;
                }
                if (d124 > d140) { // from (-2, 2)
                    d124 = d140;
                    dir124 = dir140;
                }
                if (d124 > d109) { // from (-3, 0)
                    d124 = d109;
                    dir124 = dir109;
                }
                d124 += 1;
            }
        }

        if (rc.onTheMap(l66)) { // check (-1, -3)
            if (rc.canSenseLocation(l66) && rc.sensePassability(l66)) { 
                if (d66 > d82) { // from (0, -2)
                    d66 = d82;
                    dir66 = dir82;
                }
                if (d66 > d81) { // from (-1, -2)
                    d66 = d81;
                    dir66 = dir81;
                }
                if (d66 > d80) { // from (-2, -2)
                    d66 = d80;
                    dir66 = dir80;
                }
                if (d66 > d67) { // from (0, -3)
                    d66 = d67;
                    dir66 = dir67;
                }
                d66 += 1;
            }
        }

        if (rc.onTheMap(l156)) { // check (-1, 3)
            if (rc.canSenseLocation(l156) && rc.sensePassability(l156)) { 
                if (d156 > d142) { // from (0, 2)
                    d156 = d142;
                    dir156 = dir142;
                }
                if (d156 > d141) { // from (-1, 2)
                    d156 = d141;
                    dir156 = dir141;
                }
                if (d156 > d140) { // from (-2, 2)
                    d156 = d140;
                    dir156 = dir140;
                }
                if (d156 > d157) { // from (0, 3)
                    d156 = d157;
                    dir156 = dir157;
                }
                d156 += 1;
            }
        }

        if (rc.onTheMap(l68)) { // check (1, -3)
            if (rc.canSenseLocation(l68) && rc.sensePassability(l68)) { 
                if (d68 > d82) { // from (0, -2)
                    d68 = d82;
                    dir68 = dir82;
                }
                if (d68 > d83) { // from (1, -2)
                    d68 = d83;
                    dir68 = dir83;
                }
                if (d68 > d84) { // from (2, -2)
                    d68 = d84;
                    dir68 = dir84;
                }
                if (d68 > d67) { // from (0, -3)
                    d68 = d67;
                    dir68 = dir67;
                }
                d68 += 1;
            }
        }

        if (rc.onTheMap(l158)) { // check (1, 3)
            if (rc.canSenseLocation(l158) && rc.sensePassability(l158)) { 
                if (d158 > d142) { // from (0, 2)
                    d158 = d142;
                    dir158 = dir142;
                }
                if (d158 > d143) { // from (1, 2)
                    d158 = d143;
                    dir158 = dir143;
                }
                if (d158 > d144) { // from (2, 2)
                    d158 = d144;
                    dir158 = dir144;
                }
                if (d158 > d157) { // from (0, 3)
                    d158 = d157;
                    dir158 = dir157;
                }
                d158 += 1;
            }
        }

        if (rc.onTheMap(l100)) { // check (3, -1)
            if (rc.canSenseLocation(l100) && rc.sensePassability(l100)) { 
                if (d100 > d114) { // from (2, 0)
                    d100 = d114;
                    dir100 = dir114;
                }
                if (d100 > d99) { // from (2, -1)
                    d100 = d99;
                    dir100 = dir99;
                }
                if (d100 > d84) { // from (2, -2)
                    d100 = d84;
                    dir100 = dir84;
                }
                if (d100 > d115) { // from (3, 0)
                    d100 = d115;
                    dir100 = dir115;
                }
                d100 += 1;
            }
        }

        if (rc.onTheMap(l130)) { // check (3, 1)
            if (rc.canSenseLocation(l130) && rc.sensePassability(l130)) { 
                if (d130 > d114) { // from (2, 0)
                    d130 = d114;
                    dir130 = dir114;
                }
                if (d130 > d129) { // from (2, 1)
                    d130 = d129;
                    dir130 = dir129;
                }
                if (d130 > d144) { // from (2, 2)
                    d130 = d144;
                    dir130 = dir144;
                }
                if (d130 > d115) { // from (3, 0)
                    d130 = d115;
                    dir130 = dir115;
                }
                d130 += 1;
            }
        }

        if (rc.onTheMap(l79)) { // check (-3, -2)
            if (rc.canSenseLocation(l79) && rc.sensePassability(l79)) { 
                if (d79 > d95) { // from (-2, -1)
                    d79 = d95;
                    dir79 = dir95;
                }
                if (d79 > d80) { // from (-2, -2)
                    d79 = d80;
                    dir79 = dir80;
                }
                if (d79 > d94) { // from (-3, -1)
                    d79 = d94;
                    dir79 = dir94;
                }
                d79 += 1;
            }
        }

        if (rc.onTheMap(l139)) { // check (-3, 2)
            if (rc.canSenseLocation(l139) && rc.sensePassability(l139)) { 
                if (d139 > d125) { // from (-2, 1)
                    d139 = d125;
                    dir139 = dir125;
                }
                if (d139 > d140) { // from (-2, 2)
                    d139 = d140;
                    dir139 = dir140;
                }
                if (d139 > d124) { // from (-3, 1)
                    d139 = d124;
                    dir139 = dir124;
                }
                d139 += 1;
            }
        }

        if (rc.onTheMap(l65)) { // check (-2, -3)
            if (rc.canSenseLocation(l65) && rc.sensePassability(l65)) { 
                if (d65 > d81) { // from (-1, -2)
                    d65 = d81;
                    dir65 = dir81;
                }
                if (d65 > d80) { // from (-2, -2)
                    d65 = d80;
                    dir65 = dir80;
                }
                if (d65 > d66) { // from (-1, -3)
                    d65 = d66;
                    dir65 = dir66;
                }
                if (d65 > d79) { // from (-3, -2)
                    d65 = d79;
                    dir65 = dir79;
                }
                d65 += 1;
            }
        }

        if (rc.onTheMap(l155)) { // check (-2, 3)
            if (rc.canSenseLocation(l155) && rc.sensePassability(l155)) { 
                if (d155 > d141) { // from (-1, 2)
                    d155 = d141;
                    dir155 = dir141;
                }
                if (d155 > d140) { // from (-2, 2)
                    d155 = d140;
                    dir155 = dir140;
                }
                if (d155 > d156) { // from (-1, 3)
                    d155 = d156;
                    dir155 = dir156;
                }
                if (d155 > d139) { // from (-3, 2)
                    d155 = d139;
                    dir155 = dir139;
                }
                d155 += 1;
            }
        }

        if (rc.onTheMap(l69)) { // check (2, -3)
            if (rc.canSenseLocation(l69) && rc.sensePassability(l69)) { 
                if (d69 > d83) { // from (1, -2)
                    d69 = d83;
                    dir69 = dir83;
                }
                if (d69 > d84) { // from (2, -2)
                    d69 = d84;
                    dir69 = dir84;
                }
                if (d69 > d68) { // from (1, -3)
                    d69 = d68;
                    dir69 = dir68;
                }
                d69 += 1;
            }
        }

        if (rc.onTheMap(l159)) { // check (2, 3)
            if (rc.canSenseLocation(l159) && rc.sensePassability(l159)) { 
                if (d159 > d143) { // from (1, 2)
                    d159 = d143;
                    dir159 = dir143;
                }
                if (d159 > d144) { // from (2, 2)
                    d159 = d144;
                    dir159 = dir144;
                }
                if (d159 > d158) { // from (1, 3)
                    d159 = d158;
                    dir159 = dir158;
                }
                d159 += 1;
            }
        }

        if (rc.onTheMap(l85)) { // check (3, -2)
            if (rc.canSenseLocation(l85) && rc.sensePassability(l85)) { 
                if (d85 > d99) { // from (2, -1)
                    d85 = d99;
                    dir85 = dir99;
                }
                if (d85 > d84) { // from (2, -2)
                    d85 = d84;
                    dir85 = dir84;
                }
                if (d85 > d100) { // from (3, -1)
                    d85 = d100;
                    dir85 = dir100;
                }
                if (d85 > d69) { // from (2, -3)
                    d85 = d69;
                    dir85 = dir69;
                }
                d85 += 1;
            }
        }

        if (rc.onTheMap(l145)) { // check (3, 2)
            if (rc.canSenseLocation(l145) && rc.sensePassability(l145)) { 
                if (d145 > d129) { // from (2, 1)
                    d145 = d129;
                    dir145 = dir129;
                }
                if (d145 > d144) { // from (2, 2)
                    d145 = d144;
                    dir145 = dir144;
                }
                if (d145 > d130) { // from (3, 1)
                    d145 = d130;
                    dir145 = dir130;
                }
                if (d145 > d159) { // from (2, 3)
                    d145 = d159;
                    dir145 = dir159;
                }
                d145 += 1;
            }
        }

        if (rc.onTheMap(l108)) { // check (-4, 0)
            if (rc.canSenseLocation(l108) && rc.sensePassability(l108)) { 
                if (d108 > d109) { // from (-3, 0)
                    d108 = d109;
                    dir108 = dir109;
                }
                if (d108 > d94) { // from (-3, -1)
                    d108 = d94;
                    dir108 = dir94;
                }
                if (d108 > d124) { // from (-3, 1)
                    d108 = d124;
                    dir108 = dir124;
                }
                d108 += 1;
            }
        }

        if (rc.onTheMap(l52)) { // check (0, -4)
            if (rc.canSenseLocation(l52) && rc.sensePassability(l52)) { 
                if (d52 > d67) { // from (0, -3)
                    d52 = d67;
                    dir52 = dir67;
                }
                if (d52 > d66) { // from (-1, -3)
                    d52 = d66;
                    dir52 = dir66;
                }
                if (d52 > d68) { // from (1, -3)
                    d52 = d68;
                    dir52 = dir68;
                }
                d52 += 1;
            }
        }

        if (rc.onTheMap(l172)) { // check (0, 4)
            if (rc.canSenseLocation(l172) && rc.sensePassability(l172)) { 
                if (d172 > d157) { // from (0, 3)
                    d172 = d157;
                    dir172 = dir157;
                }
                if (d172 > d156) { // from (-1, 3)
                    d172 = d156;
                    dir172 = dir156;
                }
                if (d172 > d158) { // from (1, 3)
                    d172 = d158;
                    dir172 = dir158;
                }
                d172 += 1;
            }
        }

        if (rc.onTheMap(l116)) { // check (4, 0)
            if (rc.canSenseLocation(l116) && rc.sensePassability(l116)) { 
                if (d116 > d115) { // from (3, 0)
                    d116 = d115;
                    dir116 = dir115;
                }
                if (d116 > d100) { // from (3, -1)
                    d116 = d100;
                    dir116 = dir100;
                }
                if (d116 > d130) { // from (3, 1)
                    d116 = d130;
                    dir116 = dir130;
                }
                d116 += 1;
            }
        }

        if (rc.onTheMap(l93)) { // check (-4, -1)
            if (rc.canSenseLocation(l93) && rc.sensePassability(l93)) { 
                if (d93 > d109) { // from (-3, 0)
                    d93 = d109;
                    dir93 = dir109;
                }
                if (d93 > d94) { // from (-3, -1)
                    d93 = d94;
                    dir93 = dir94;
                }
                if (d93 > d79) { // from (-3, -2)
                    d93 = d79;
                    dir93 = dir79;
                }
                if (d93 > d108) { // from (-4, 0)
                    d93 = d108;
                    dir93 = dir108;
                }
                d93 += 1;
            }
        }

        if (rc.onTheMap(l123)) { // check (-4, 1)
            if (rc.canSenseLocation(l123) && rc.sensePassability(l123)) { 
                if (d123 > d109) { // from (-3, 0)
                    d123 = d109;
                    dir123 = dir109;
                }
                if (d123 > d124) { // from (-3, 1)
                    d123 = d124;
                    dir123 = dir124;
                }
                if (d123 > d139) { // from (-3, 2)
                    d123 = d139;
                    dir123 = dir139;
                }
                if (d123 > d108) { // from (-4, 0)
                    d123 = d108;
                    dir123 = dir108;
                }
                d123 += 1;
            }
        }

        if (rc.onTheMap(l51)) { // check (-1, -4)
            if (rc.canSenseLocation(l51) && rc.sensePassability(l51)) { 
                if (d51 > d67) { // from (0, -3)
                    d51 = d67;
                    dir51 = dir67;
                }
                if (d51 > d66) { // from (-1, -3)
                    d51 = d66;
                    dir51 = dir66;
                }
                if (d51 > d65) { // from (-2, -3)
                    d51 = d65;
                    dir51 = dir65;
                }
                if (d51 > d52) { // from (0, -4)
                    d51 = d52;
                    dir51 = dir52;
                }
                d51 += 1;
            }
        }

        if (rc.onTheMap(l171)) { // check (-1, 4)
            if (rc.canSenseLocation(l171) && rc.sensePassability(l171)) { 
                if (d171 > d157) { // from (0, 3)
                    d171 = d157;
                    dir171 = dir157;
                }
                if (d171 > d156) { // from (-1, 3)
                    d171 = d156;
                    dir171 = dir156;
                }
                if (d171 > d155) { // from (-2, 3)
                    d171 = d155;
                    dir171 = dir155;
                }
                if (d171 > d172) { // from (0, 4)
                    d171 = d172;
                    dir171 = dir172;
                }
                d171 += 1;
            }
        }

        if (rc.onTheMap(l53)) { // check (1, -4)
            if (rc.canSenseLocation(l53) && rc.sensePassability(l53)) { 
                if (d53 > d67) { // from (0, -3)
                    d53 = d67;
                    dir53 = dir67;
                }
                if (d53 > d68) { // from (1, -3)
                    d53 = d68;
                    dir53 = dir68;
                }
                if (d53 > d69) { // from (2, -3)
                    d53 = d69;
                    dir53 = dir69;
                }
                if (d53 > d52) { // from (0, -4)
                    d53 = d52;
                    dir53 = dir52;
                }
                d53 += 1;
            }
        }

        if (rc.onTheMap(l173)) { // check (1, 4)
            if (rc.canSenseLocation(l173) && rc.sensePassability(l173)) { 
                if (d173 > d157) { // from (0, 3)
                    d173 = d157;
                    dir173 = dir157;
                }
                if (d173 > d158) { // from (1, 3)
                    d173 = d158;
                    dir173 = dir158;
                }
                if (d173 > d159) { // from (2, 3)
                    d173 = d159;
                    dir173 = dir159;
                }
                if (d173 > d172) { // from (0, 4)
                    d173 = d172;
                    dir173 = dir172;
                }
                d173 += 1;
            }
        }

        if (rc.onTheMap(l101)) { // check (4, -1)
            if (rc.canSenseLocation(l101) && rc.sensePassability(l101)) { 
                if (d101 > d115) { // from (3, 0)
                    d101 = d115;
                    dir101 = dir115;
                }
                if (d101 > d100) { // from (3, -1)
                    d101 = d100;
                    dir101 = dir100;
                }
                if (d101 > d85) { // from (3, -2)
                    d101 = d85;
                    dir101 = dir85;
                }
                if (d101 > d116) { // from (4, 0)
                    d101 = d116;
                    dir101 = dir116;
                }
                d101 += 1;
            }
        }

        if (rc.onTheMap(l131)) { // check (4, 1)
            if (rc.canSenseLocation(l131) && rc.sensePassability(l131)) { 
                if (d131 > d115) { // from (3, 0)
                    d131 = d115;
                    dir131 = dir115;
                }
                if (d131 > d130) { // from (3, 1)
                    d131 = d130;
                    dir131 = dir130;
                }
                if (d131 > d145) { // from (3, 2)
                    d131 = d145;
                    dir131 = dir145;
                }
                if (d131 > d116) { // from (4, 0)
                    d131 = d116;
                    dir131 = dir116;
                }
                d131 += 1;
            }
        }

        if (rc.onTheMap(l64)) { // check (-3, -3)
            if (rc.canSenseLocation(l64) && rc.sensePassability(l64)) { 
                if (d64 > d80) { // from (-2, -2)
                    d64 = d80;
                    dir64 = dir80;
                }
                if (d64 > d79) { // from (-3, -2)
                    d64 = d79;
                    dir64 = dir79;
                }
                if (d64 > d65) { // from (-2, -3)
                    d64 = d65;
                    dir64 = dir65;
                }
                d64 += 1;
            }
        }

        if (rc.onTheMap(l154)) { // check (-3, 3)
            if (rc.canSenseLocation(l154) && rc.sensePassability(l154)) { 
                if (d154 > d140) { // from (-2, 2)
                    d154 = d140;
                    dir154 = dir140;
                }
                if (d154 > d139) { // from (-3, 2)
                    d154 = d139;
                    dir154 = dir139;
                }
                if (d154 > d155) { // from (-2, 3)
                    d154 = d155;
                    dir154 = dir155;
                }
                d154 += 1;
            }
        }

        if (rc.onTheMap(l70)) { // check (3, -3)
            if (rc.canSenseLocation(l70) && rc.sensePassability(l70)) { 
                if (d70 > d84) { // from (2, -2)
                    d70 = d84;
                    dir70 = dir84;
                }
                if (d70 > d69) { // from (2, -3)
                    d70 = d69;
                    dir70 = dir69;
                }
                if (d70 > d85) { // from (3, -2)
                    d70 = d85;
                    dir70 = dir85;
                }
                d70 += 1;
            }
        }

        if (rc.onTheMap(l160)) { // check (3, 3)
            if (rc.canSenseLocation(l160) && rc.sensePassability(l160)) { 
                if (d160 > d144) { // from (2, 2)
                    d160 = d144;
                    dir160 = dir144;
                }
                if (d160 > d159) { // from (2, 3)
                    d160 = d159;
                    dir160 = dir159;
                }
                if (d160 > d145) { // from (3, 2)
                    d160 = d145;
                    dir160 = dir145;
                }
                d160 += 1;
            }
        }

        if (rc.onTheMap(l78)) { // check (-4, -2)
            if (rc.canSenseLocation(l78) && rc.sensePassability(l78)) { 
                if (d78 > d94) { // from (-3, -1)
                    d78 = d94;
                    dir78 = dir94;
                }
                if (d78 > d79) { // from (-3, -2)
                    d78 = d79;
                    dir78 = dir79;
                }
                if (d78 > d93) { // from (-4, -1)
                    d78 = d93;
                    dir78 = dir93;
                }
                if (d78 > d64) { // from (-3, -3)
                    d78 = d64;
                    dir78 = dir64;
                }
                d78 += 1;
            }
        }

        if (rc.onTheMap(l138)) { // check (-4, 2)
            if (rc.canSenseLocation(l138) && rc.sensePassability(l138)) { 
                if (d138 > d124) { // from (-3, 1)
                    d138 = d124;
                    dir138 = dir124;
                }
                if (d138 > d139) { // from (-3, 2)
                    d138 = d139;
                    dir138 = dir139;
                }
                if (d138 > d123) { // from (-4, 1)
                    d138 = d123;
                    dir138 = dir123;
                }
                if (d138 > d154) { // from (-3, 3)
                    d138 = d154;
                    dir138 = dir154;
                }
                d138 += 1;
            }
        }

        if (rc.onTheMap(l50)) { // check (-2, -4)
            if (rc.canSenseLocation(l50) && rc.sensePassability(l50)) { 
                if (d50 > d66) { // from (-1, -3)
                    d50 = d66;
                    dir50 = dir66;
                }
                if (d50 > d65) { // from (-2, -3)
                    d50 = d65;
                    dir50 = dir65;
                }
                if (d50 > d51) { // from (-1, -4)
                    d50 = d51;
                    dir50 = dir51;
                }
                if (d50 > d64) { // from (-3, -3)
                    d50 = d64;
                    dir50 = dir64;
                }
                d50 += 1;
            }
        }

        if (rc.onTheMap(l170)) { // check (-2, 4)
            if (rc.canSenseLocation(l170) && rc.sensePassability(l170)) { 
                if (d170 > d156) { // from (-1, 3)
                    d170 = d156;
                    dir170 = dir156;
                }
                if (d170 > d155) { // from (-2, 3)
                    d170 = d155;
                    dir170 = dir155;
                }
                if (d170 > d171) { // from (-1, 4)
                    d170 = d171;
                    dir170 = dir171;
                }
                if (d170 > d154) { // from (-3, 3)
                    d170 = d154;
                    dir170 = dir154;
                }
                d170 += 1;
            }
        }

        if (rc.onTheMap(l54)) { // check (2, -4)
            if (rc.canSenseLocation(l54) && rc.sensePassability(l54)) { 
                if (d54 > d68) { // from (1, -3)
                    d54 = d68;
                    dir54 = dir68;
                }
                if (d54 > d69) { // from (2, -3)
                    d54 = d69;
                    dir54 = dir69;
                }
                if (d54 > d53) { // from (1, -4)
                    d54 = d53;
                    dir54 = dir53;
                }
                if (d54 > d70) { // from (3, -3)
                    d54 = d70;
                    dir54 = dir70;
                }
                d54 += 1;
            }
        }

        if (rc.onTheMap(l174)) { // check (2, 4)
            if (rc.canSenseLocation(l174) && rc.sensePassability(l174)) { 
                if (d174 > d158) { // from (1, 3)
                    d174 = d158;
                    dir174 = dir158;
                }
                if (d174 > d159) { // from (2, 3)
                    d174 = d159;
                    dir174 = dir159;
                }
                if (d174 > d173) { // from (1, 4)
                    d174 = d173;
                    dir174 = dir173;
                }
                if (d174 > d160) { // from (3, 3)
                    d174 = d160;
                    dir174 = dir160;
                }
                d174 += 1;
            }
        }

        if (rc.onTheMap(l86)) { // check (4, -2)
            if (rc.canSenseLocation(l86) && rc.sensePassability(l86)) { 
                if (d86 > d100) { // from (3, -1)
                    d86 = d100;
                    dir86 = dir100;
                }
                if (d86 > d85) { // from (3, -2)
                    d86 = d85;
                    dir86 = dir85;
                }
                if (d86 > d101) { // from (4, -1)
                    d86 = d101;
                    dir86 = dir101;
                }
                if (d86 > d70) { // from (3, -3)
                    d86 = d70;
                    dir86 = dir70;
                }
                d86 += 1;
            }
        }

        if (rc.onTheMap(l146)) { // check (4, 2)
            if (rc.canSenseLocation(l146) && rc.sensePassability(l146)) { 
                if (d146 > d130) { // from (3, 1)
                    d146 = d130;
                    dir146 = dir130;
                }
                if (d146 > d145) { // from (3, 2)
                    d146 = d145;
                    dir146 = dir145;
                }
                if (d146 > d131) { // from (4, 1)
                    d146 = d131;
                    dir146 = dir131;
                }
                if (d146 > d160) { // from (3, 3)
                    d146 = d160;
                    dir146 = dir160;
                }
                d146 += 1;
            }
        }


        // System.out.println("LOCAL DISTANCES:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d170 + "\t" + d171 + "\t" + d172 + "\t" + d173 + "\t" + d174 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d154 + "\t" + d155 + "\t" + d156 + "\t" + d157 + "\t" + d158 + "\t" + d159 + "\t" + d160 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d138 + "\t" + d139 + "\t" + d140 + "\t" + d141 + "\t" + d142 + "\t" + d143 + "\t" + d144 + "\t" + d145 + "\t" + d146 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d123 + "\t" + d124 + "\t" + d125 + "\t" + d126 + "\t" + d127 + "\t" + d128 + "\t" + d129 + "\t" + d130 + "\t" + d131 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d108 + "\t" + d109 + "\t" + d110 + "\t" + d111 + "\t" + d112 + "\t" + d113 + "\t" + d114 + "\t" + d115 + "\t" + d116 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d93 + "\t" + d94 + "\t" + d95 + "\t" + d96 + "\t" + d97 + "\t" + d98 + "\t" + d99 + "\t" + d100 + "\t" + d101 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d78 + "\t" + d79 + "\t" + d80 + "\t" + d81 + "\t" + d82 + "\t" + d83 + "\t" + d84 + "\t" + d85 + "\t" + d86 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d64 + "\t" + d65 + "\t" + d66 + "\t" + d67 + "\t" + d68 + "\t" + d69 + "\t" + d70 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d50 + "\t" + d51 + "\t" + d52 + "\t" + d53 + "\t" + d54 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("DIRECTIONS:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + dir170 + "\t" + dir171 + "\t" + dir172 + "\t" + dir173 + "\t" + dir174 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir154 + "\t" + dir155 + "\t" + dir156 + "\t" + dir157 + "\t" + dir158 + "\t" + dir159 + "\t" + dir160 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir138 + "\t" + dir139 + "\t" + dir140 + "\t" + dir141 + "\t" + dir142 + "\t" + dir143 + "\t" + dir144 + "\t" + dir145 + "\t" + dir146 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir123 + "\t" + dir124 + "\t" + dir125 + "\t" + dir126 + "\t" + dir127 + "\t" + dir128 + "\t" + dir129 + "\t" + dir130 + "\t" + dir131 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir108 + "\t" + dir109 + "\t" + dir110 + "\t" + dir111 + "\t" + dir112 + "\t" + dir113 + "\t" + dir114 + "\t" + dir115 + "\t" + dir116 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir93 + "\t" + dir94 + "\t" + dir95 + "\t" + dir96 + "\t" + dir97 + "\t" + dir98 + "\t" + dir99 + "\t" + dir100 + "\t" + dir101 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir78 + "\t" + dir79 + "\t" + dir80 + "\t" + dir81 + "\t" + dir82 + "\t" + dir83 + "\t" + dir84 + "\t" + dir85 + "\t" + dir86 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir64 + "\t" + dir65 + "\t" + dir66 + "\t" + dir67 + "\t" + dir68 + "\t" + dir69 + "\t" + dir70 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + dir50 + "\t" + dir51 + "\t" + dir52 + "\t" + dir53 + "\t" + dir54 + "\t" + "\t" + "\t" + "\t" + "\t");

        int target_dx = target.x - l112.x;
        int target_dy = target.y - l112.y;
        switch (target_dx) {
                case -4:
                    switch (target_dy) {
                        case -2:
                            return dir78; // destination is at relative location (-4, -2)
                        case -1:
                            return dir93; // destination is at relative location (-4, -1)
                        case 0:
                            return dir108; // destination is at relative location (-4, 0)
                        case 1:
                            return dir123; // destination is at relative location (-4, 1)
                        case 2:
                            return dir138; // destination is at relative location (-4, 2)
                    }
                    break;
                case -3:
                    switch (target_dy) {
                        case -3:
                            return dir64; // destination is at relative location (-3, -3)
                        case -2:
                            return dir79; // destination is at relative location (-3, -2)
                        case -1:
                            return dir94; // destination is at relative location (-3, -1)
                        case 0:
                            return dir109; // destination is at relative location (-3, 0)
                        case 1:
                            return dir124; // destination is at relative location (-3, 1)
                        case 2:
                            return dir139; // destination is at relative location (-3, 2)
                        case 3:
                            return dir154; // destination is at relative location (-3, 3)
                    }
                    break;
                case -2:
                    switch (target_dy) {
                        case -4:
                            return dir50; // destination is at relative location (-2, -4)
                        case -3:
                            return dir65; // destination is at relative location (-2, -3)
                        case -2:
                            return dir80; // destination is at relative location (-2, -2)
                        case -1:
                            return dir95; // destination is at relative location (-2, -1)
                        case 0:
                            return dir110; // destination is at relative location (-2, 0)
                        case 1:
                            return dir125; // destination is at relative location (-2, 1)
                        case 2:
                            return dir140; // destination is at relative location (-2, 2)
                        case 3:
                            return dir155; // destination is at relative location (-2, 3)
                        case 4:
                            return dir170; // destination is at relative location (-2, 4)
                    }
                    break;
                case -1:
                    switch (target_dy) {
                        case -4:
                            return dir51; // destination is at relative location (-1, -4)
                        case -3:
                            return dir66; // destination is at relative location (-1, -3)
                        case -2:
                            return dir81; // destination is at relative location (-1, -2)
                        case -1:
                            return dir96; // destination is at relative location (-1, -1)
                        case 0:
                            return dir111; // destination is at relative location (-1, 0)
                        case 1:
                            return dir126; // destination is at relative location (-1, 1)
                        case 2:
                            return dir141; // destination is at relative location (-1, 2)
                        case 3:
                            return dir156; // destination is at relative location (-1, 3)
                        case 4:
                            return dir171; // destination is at relative location (-1, 4)
                    }
                    break;
                case 0:
                    switch (target_dy) {
                        case -4:
                            return dir52; // destination is at relative location (0, -4)
                        case -3:
                            return dir67; // destination is at relative location (0, -3)
                        case -2:
                            return dir82; // destination is at relative location (0, -2)
                        case -1:
                            return dir97; // destination is at relative location (0, -1)
                        case 0:
                            return dir112; // destination is at relative location (0, 0)
                        case 1:
                            return dir127; // destination is at relative location (0, 1)
                        case 2:
                            return dir142; // destination is at relative location (0, 2)
                        case 3:
                            return dir157; // destination is at relative location (0, 3)
                        case 4:
                            return dir172; // destination is at relative location (0, 4)
                    }
                    break;
                case 1:
                    switch (target_dy) {
                        case -4:
                            return dir53; // destination is at relative location (1, -4)
                        case -3:
                            return dir68; // destination is at relative location (1, -3)
                        case -2:
                            return dir83; // destination is at relative location (1, -2)
                        case -1:
                            return dir98; // destination is at relative location (1, -1)
                        case 0:
                            return dir113; // destination is at relative location (1, 0)
                        case 1:
                            return dir128; // destination is at relative location (1, 1)
                        case 2:
                            return dir143; // destination is at relative location (1, 2)
                        case 3:
                            return dir158; // destination is at relative location (1, 3)
                        case 4:
                            return dir173; // destination is at relative location (1, 4)
                    }
                    break;
                case 2:
                    switch (target_dy) {
                        case -4:
                            return dir54; // destination is at relative location (2, -4)
                        case -3:
                            return dir69; // destination is at relative location (2, -3)
                        case -2:
                            return dir84; // destination is at relative location (2, -2)
                        case -1:
                            return dir99; // destination is at relative location (2, -1)
                        case 0:
                            return dir114; // destination is at relative location (2, 0)
                        case 1:
                            return dir129; // destination is at relative location (2, 1)
                        case 2:
                            return dir144; // destination is at relative location (2, 2)
                        case 3:
                            return dir159; // destination is at relative location (2, 3)
                        case 4:
                            return dir174; // destination is at relative location (2, 4)
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -3:
                            return dir70; // destination is at relative location (3, -3)
                        case -2:
                            return dir85; // destination is at relative location (3, -2)
                        case -1:
                            return dir100; // destination is at relative location (3, -1)
                        case 0:
                            return dir115; // destination is at relative location (3, 0)
                        case 1:
                            return dir130; // destination is at relative location (3, 1)
                        case 2:
                            return dir145; // destination is at relative location (3, 2)
                        case 3:
                            return dir160; // destination is at relative location (3, 3)
                    }
                    break;
                case 4:
                    switch (target_dy) {
                        case -2:
                            return dir86; // destination is at relative location (4, -2)
                        case -1:
                            return dir101; // destination is at relative location (4, -1)
                        case 0:
                            return dir116; // destination is at relative location (4, 0)
                        case 1:
                            return dir131; // destination is at relative location (4, 1)
                        case 2:
                            return dir146; // destination is at relative location (4, 2)
                    }
                    break;
        }
        
        Direction ans = null;
        double bestScore = 0;
        double currDist = Math.sqrt(l112.distanceSquaredTo(target));
        
        double score78 = (currDist - Math.sqrt(l78.distanceSquaredTo(target))) / d78;
        if (score78 > bestScore) {
            bestScore = score78;
            ans = dir78;
        }

        double score93 = (currDist - Math.sqrt(l93.distanceSquaredTo(target))) / d93;
        if (score93 > bestScore) {
            bestScore = score93;
            ans = dir93;
        }

        double score108 = (currDist - Math.sqrt(l108.distanceSquaredTo(target))) / d108;
        if (score108 > bestScore) {
            bestScore = score108;
            ans = dir108;
        }

        double score123 = (currDist - Math.sqrt(l123.distanceSquaredTo(target))) / d123;
        if (score123 > bestScore) {
            bestScore = score123;
            ans = dir123;
        }

        double score138 = (currDist - Math.sqrt(l138.distanceSquaredTo(target))) / d138;
        if (score138 > bestScore) {
            bestScore = score138;
            ans = dir138;
        }

        double score64 = (currDist - Math.sqrt(l64.distanceSquaredTo(target))) / d64;
        if (score64 > bestScore) {
            bestScore = score64;
            ans = dir64;
        }

        double score79 = (currDist - Math.sqrt(l79.distanceSquaredTo(target))) / d79;
        if (score79 > bestScore) {
            bestScore = score79;
            ans = dir79;
        }

        double score139 = (currDist - Math.sqrt(l139.distanceSquaredTo(target))) / d139;
        if (score139 > bestScore) {
            bestScore = score139;
            ans = dir139;
        }

        double score154 = (currDist - Math.sqrt(l154.distanceSquaredTo(target))) / d154;
        if (score154 > bestScore) {
            bestScore = score154;
            ans = dir154;
        }

        double score50 = (currDist - Math.sqrt(l50.distanceSquaredTo(target))) / d50;
        if (score50 > bestScore) {
            bestScore = score50;
            ans = dir50;
        }

        double score65 = (currDist - Math.sqrt(l65.distanceSquaredTo(target))) / d65;
        if (score65 > bestScore) {
            bestScore = score65;
            ans = dir65;
        }

        double score155 = (currDist - Math.sqrt(l155.distanceSquaredTo(target))) / d155;
        if (score155 > bestScore) {
            bestScore = score155;
            ans = dir155;
        }

        double score170 = (currDist - Math.sqrt(l170.distanceSquaredTo(target))) / d170;
        if (score170 > bestScore) {
            bestScore = score170;
            ans = dir170;
        }

        double score51 = (currDist - Math.sqrt(l51.distanceSquaredTo(target))) / d51;
        if (score51 > bestScore) {
            bestScore = score51;
            ans = dir51;
        }

        double score171 = (currDist - Math.sqrt(l171.distanceSquaredTo(target))) / d171;
        if (score171 > bestScore) {
            bestScore = score171;
            ans = dir171;
        }

        double score52 = (currDist - Math.sqrt(l52.distanceSquaredTo(target))) / d52;
        if (score52 > bestScore) {
            bestScore = score52;
            ans = dir52;
        }

        double score172 = (currDist - Math.sqrt(l172.distanceSquaredTo(target))) / d172;
        if (score172 > bestScore) {
            bestScore = score172;
            ans = dir172;
        }

        double score53 = (currDist - Math.sqrt(l53.distanceSquaredTo(target))) / d53;
        if (score53 > bestScore) {
            bestScore = score53;
            ans = dir53;
        }

        double score173 = (currDist - Math.sqrt(l173.distanceSquaredTo(target))) / d173;
        if (score173 > bestScore) {
            bestScore = score173;
            ans = dir173;
        }

        double score54 = (currDist - Math.sqrt(l54.distanceSquaredTo(target))) / d54;
        if (score54 > bestScore) {
            bestScore = score54;
            ans = dir54;
        }

        double score69 = (currDist - Math.sqrt(l69.distanceSquaredTo(target))) / d69;
        if (score69 > bestScore) {
            bestScore = score69;
            ans = dir69;
        }

        double score159 = (currDist - Math.sqrt(l159.distanceSquaredTo(target))) / d159;
        if (score159 > bestScore) {
            bestScore = score159;
            ans = dir159;
        }

        double score174 = (currDist - Math.sqrt(l174.distanceSquaredTo(target))) / d174;
        if (score174 > bestScore) {
            bestScore = score174;
            ans = dir174;
        }

        double score70 = (currDist - Math.sqrt(l70.distanceSquaredTo(target))) / d70;
        if (score70 > bestScore) {
            bestScore = score70;
            ans = dir70;
        }

        double score85 = (currDist - Math.sqrt(l85.distanceSquaredTo(target))) / d85;
        if (score85 > bestScore) {
            bestScore = score85;
            ans = dir85;
        }

        double score145 = (currDist - Math.sqrt(l145.distanceSquaredTo(target))) / d145;
        if (score145 > bestScore) {
            bestScore = score145;
            ans = dir145;
        }

        double score160 = (currDist - Math.sqrt(l160.distanceSquaredTo(target))) / d160;
        if (score160 > bestScore) {
            bestScore = score160;
            ans = dir160;
        }

        double score86 = (currDist - Math.sqrt(l86.distanceSquaredTo(target))) / d86;
        if (score86 > bestScore) {
            bestScore = score86;
            ans = dir86;
        }

        double score101 = (currDist - Math.sqrt(l101.distanceSquaredTo(target))) / d101;
        if (score101 > bestScore) {
            bestScore = score101;
            ans = dir101;
        }

        double score116 = (currDist - Math.sqrt(l116.distanceSquaredTo(target))) / d116;
        if (score116 > bestScore) {
            bestScore = score116;
            ans = dir116;
        }

        double score131 = (currDist - Math.sqrt(l131.distanceSquaredTo(target))) / d131;
        if (score131 > bestScore) {
            bestScore = score131;
            ans = dir131;
        }

        double score146 = (currDist - Math.sqrt(l146.distanceSquaredTo(target))) / d146;
        if (score146 > bestScore) {
            bestScore = score146;
            ans = dir146;
        }

        
        return ans;
    }
}
