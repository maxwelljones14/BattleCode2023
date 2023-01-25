package MPWorking.bfs;

import battlecode.common.*;

public class BFSCooldown20 {

    public static RobotController rc;

    static MapLocation l78; // location representing relative coordinate (-4, -2)
    static double d78; // shortest distance to location from current location
    static double score78; // heuristic distance from location to target

    static MapLocation l93; // location representing relative coordinate (-4, -1)
    static double d93; // shortest distance to location from current location
    static double score93; // heuristic distance from location to target

    static MapLocation l108; // location representing relative coordinate (-4, 0)
    static double d108; // shortest distance to location from current location
    static double score108; // heuristic distance from location to target

    static MapLocation l123; // location representing relative coordinate (-4, 1)
    static double d123; // shortest distance to location from current location
    static double score123; // heuristic distance from location to target

    static MapLocation l138; // location representing relative coordinate (-4, 2)
    static double d138; // shortest distance to location from current location
    static double score138; // heuristic distance from location to target

    static MapLocation l64; // location representing relative coordinate (-3, -3)
    static double d64; // shortest distance to location from current location
    static double score64; // heuristic distance from location to target

    static MapLocation l79; // location representing relative coordinate (-3, -2)
    static double d79; // shortest distance to location from current location
    static double score79; // heuristic distance from location to target

    static MapLocation l94; // location representing relative coordinate (-3, -1)
    static double d94; // shortest distance to location from current location
    static double score94; // heuristic distance from location to target

    static MapLocation l109; // location representing relative coordinate (-3, 0)
    static double d109; // shortest distance to location from current location
    static double score109; // heuristic distance from location to target

    static MapLocation l124; // location representing relative coordinate (-3, 1)
    static double d124; // shortest distance to location from current location
    static double score124; // heuristic distance from location to target

    static MapLocation l139; // location representing relative coordinate (-3, 2)
    static double d139; // shortest distance to location from current location
    static double score139; // heuristic distance from location to target

    static MapLocation l154; // location representing relative coordinate (-3, 3)
    static double d154; // shortest distance to location from current location
    static double score154; // heuristic distance from location to target

    static MapLocation l50; // location representing relative coordinate (-2, -4)
    static double d50; // shortest distance to location from current location
    static double score50; // heuristic distance from location to target

    static MapLocation l65; // location representing relative coordinate (-2, -3)
    static double d65; // shortest distance to location from current location
    static double score65; // heuristic distance from location to target

    static MapLocation l80; // location representing relative coordinate (-2, -2)
    static double d80; // shortest distance to location from current location
    static double score80; // heuristic distance from location to target

    static MapLocation l95; // location representing relative coordinate (-2, -1)
    static double d95; // shortest distance to location from current location
    static double score95; // heuristic distance from location to target

    static MapLocation l110; // location representing relative coordinate (-2, 0)
    static double d110; // shortest distance to location from current location
    static double score110; // heuristic distance from location to target

    static MapLocation l125; // location representing relative coordinate (-2, 1)
    static double d125; // shortest distance to location from current location
    static double score125; // heuristic distance from location to target

    static MapLocation l140; // location representing relative coordinate (-2, 2)
    static double d140; // shortest distance to location from current location
    static double score140; // heuristic distance from location to target

    static MapLocation l155; // location representing relative coordinate (-2, 3)
    static double d155; // shortest distance to location from current location
    static double score155; // heuristic distance from location to target

    static MapLocation l170; // location representing relative coordinate (-2, 4)
    static double d170; // shortest distance to location from current location
    static double score170; // heuristic distance from location to target

    static MapLocation l51; // location representing relative coordinate (-1, -4)
    static double d51; // shortest distance to location from current location
    static double score51; // heuristic distance from location to target

    static MapLocation l66; // location representing relative coordinate (-1, -3)
    static double d66; // shortest distance to location from current location
    static double score66; // heuristic distance from location to target

    static MapLocation l81; // location representing relative coordinate (-1, -2)
    static double d81; // shortest distance to location from current location
    static double score81; // heuristic distance from location to target

    static MapLocation l96; // location representing relative coordinate (-1, -1)
    static double d96; // shortest distance to location from current location
    static double score96; // heuristic distance from location to target

    static MapLocation l111; // location representing relative coordinate (-1, 0)
    static double d111; // shortest distance to location from current location
    static double score111; // heuristic distance from location to target

    static MapLocation l126; // location representing relative coordinate (-1, 1)
    static double d126; // shortest distance to location from current location
    static double score126; // heuristic distance from location to target

    static MapLocation l141; // location representing relative coordinate (-1, 2)
    static double d141; // shortest distance to location from current location
    static double score141; // heuristic distance from location to target

    static MapLocation l156; // location representing relative coordinate (-1, 3)
    static double d156; // shortest distance to location from current location
    static double score156; // heuristic distance from location to target

    static MapLocation l171; // location representing relative coordinate (-1, 4)
    static double d171; // shortest distance to location from current location
    static double score171; // heuristic distance from location to target

    static MapLocation l52; // location representing relative coordinate (0, -4)
    static double d52; // shortest distance to location from current location
    static double score52; // heuristic distance from location to target

    static MapLocation l67; // location representing relative coordinate (0, -3)
    static double d67; // shortest distance to location from current location
    static double score67; // heuristic distance from location to target

    static MapLocation l82; // location representing relative coordinate (0, -2)
    static double d82; // shortest distance to location from current location
    static double score82; // heuristic distance from location to target

    static MapLocation l97; // location representing relative coordinate (0, -1)
    static double d97; // shortest distance to location from current location
    static double score97; // heuristic distance from location to target

    static MapLocation l112; // location representing relative coordinate (0, 0)
    static double d112; // shortest distance to location from current location
    static double score112; // heuristic distance from location to target

    static MapLocation l127; // location representing relative coordinate (0, 1)
    static double d127; // shortest distance to location from current location
    static double score127; // heuristic distance from location to target

    static MapLocation l142; // location representing relative coordinate (0, 2)
    static double d142; // shortest distance to location from current location
    static double score142; // heuristic distance from location to target

    static MapLocation l157; // location representing relative coordinate (0, 3)
    static double d157; // shortest distance to location from current location
    static double score157; // heuristic distance from location to target

    static MapLocation l172; // location representing relative coordinate (0, 4)
    static double d172; // shortest distance to location from current location
    static double score172; // heuristic distance from location to target

    static MapLocation l53; // location representing relative coordinate (1, -4)
    static double d53; // shortest distance to location from current location
    static double score53; // heuristic distance from location to target

    static MapLocation l68; // location representing relative coordinate (1, -3)
    static double d68; // shortest distance to location from current location
    static double score68; // heuristic distance from location to target

    static MapLocation l83; // location representing relative coordinate (1, -2)
    static double d83; // shortest distance to location from current location
    static double score83; // heuristic distance from location to target

    static MapLocation l98; // location representing relative coordinate (1, -1)
    static double d98; // shortest distance to location from current location
    static double score98; // heuristic distance from location to target

    static MapLocation l113; // location representing relative coordinate (1, 0)
    static double d113; // shortest distance to location from current location
    static double score113; // heuristic distance from location to target

    static MapLocation l128; // location representing relative coordinate (1, 1)
    static double d128; // shortest distance to location from current location
    static double score128; // heuristic distance from location to target

    static MapLocation l143; // location representing relative coordinate (1, 2)
    static double d143; // shortest distance to location from current location
    static double score143; // heuristic distance from location to target

    static MapLocation l158; // location representing relative coordinate (1, 3)
    static double d158; // shortest distance to location from current location
    static double score158; // heuristic distance from location to target

    static MapLocation l173; // location representing relative coordinate (1, 4)
    static double d173; // shortest distance to location from current location
    static double score173; // heuristic distance from location to target

    static MapLocation l54; // location representing relative coordinate (2, -4)
    static double d54; // shortest distance to location from current location
    static double score54; // heuristic distance from location to target

    static MapLocation l69; // location representing relative coordinate (2, -3)
    static double d69; // shortest distance to location from current location
    static double score69; // heuristic distance from location to target

    static MapLocation l84; // location representing relative coordinate (2, -2)
    static double d84; // shortest distance to location from current location
    static double score84; // heuristic distance from location to target

    static MapLocation l99; // location representing relative coordinate (2, -1)
    static double d99; // shortest distance to location from current location
    static double score99; // heuristic distance from location to target

    static MapLocation l114; // location representing relative coordinate (2, 0)
    static double d114; // shortest distance to location from current location
    static double score114; // heuristic distance from location to target

    static MapLocation l129; // location representing relative coordinate (2, 1)
    static double d129; // shortest distance to location from current location
    static double score129; // heuristic distance from location to target

    static MapLocation l144; // location representing relative coordinate (2, 2)
    static double d144; // shortest distance to location from current location
    static double score144; // heuristic distance from location to target

    static MapLocation l159; // location representing relative coordinate (2, 3)
    static double d159; // shortest distance to location from current location
    static double score159; // heuristic distance from location to target

    static MapLocation l174; // location representing relative coordinate (2, 4)
    static double d174; // shortest distance to location from current location
    static double score174; // heuristic distance from location to target

    static MapLocation l70; // location representing relative coordinate (3, -3)
    static double d70; // shortest distance to location from current location
    static double score70; // heuristic distance from location to target

    static MapLocation l85; // location representing relative coordinate (3, -2)
    static double d85; // shortest distance to location from current location
    static double score85; // heuristic distance from location to target

    static MapLocation l100; // location representing relative coordinate (3, -1)
    static double d100; // shortest distance to location from current location
    static double score100; // heuristic distance from location to target

    static MapLocation l115; // location representing relative coordinate (3, 0)
    static double d115; // shortest distance to location from current location
    static double score115; // heuristic distance from location to target

    static MapLocation l130; // location representing relative coordinate (3, 1)
    static double d130; // shortest distance to location from current location
    static double score130; // heuristic distance from location to target

    static MapLocation l145; // location representing relative coordinate (3, 2)
    static double d145; // shortest distance to location from current location
    static double score145; // heuristic distance from location to target

    static MapLocation l160; // location representing relative coordinate (3, 3)
    static double d160; // shortest distance to location from current location
    static double score160; // heuristic distance from location to target

    static MapLocation l86; // location representing relative coordinate (4, -2)
    static double d86; // shortest distance to location from current location
    static double score86; // heuristic distance from location to target

    static MapLocation l101; // location representing relative coordinate (4, -1)
    static double d101; // shortest distance to location from current location
    static double score101; // heuristic distance from location to target

    static MapLocation l116; // location representing relative coordinate (4, 0)
    static double d116; // shortest distance to location from current location
    static double score116; // heuristic distance from location to target

    static MapLocation l131; // location representing relative coordinate (4, 1)
    static double d131; // shortest distance to location from current location
    static double score131; // heuristic distance from location to target

    static MapLocation l146; // location representing relative coordinate (4, 2)
    static double d146; // shortest distance to location from current location
    static double score146; // heuristic distance from location to target


    public static void init(RobotController r) {
        rc = r;
        team = rc.getTeam();
    }

    private static final Direction[] DIRECTIONS = new Direction[] {null, Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHWEST, Direction.SOUTHEAST, Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH};

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
    public static double ans;
    public static double bestScore;
    public static double currDist;

    public static String NORTH_ADJ = "\07\00\01";
    public static String NORTHEAST_ADJ = "\00\01\02";
    public static String EAST_ADJ = "\01\02\03";
    public static String SOUTHEAST_ADJ = "\02\03\04";
    public static String SOUTH_ADJ = "\03\04\05";
    public static String SOUTHWEST_ADJ = "\04\05\06";
    public static String WEST_ADJ = "\05\06\07";
    public static String NORTHWEST_ADJ = "\06\07\00";

    public static Direction direction(double dist) {
        if (dist==Double.POSITIVE_INFINITY) {
            return null;
        }
        return DIRECTIONS[(int)(dist * 16 % 16)];
    }

    public static Direction bestDir(MapLocation target) throws GameActionException {

        l112 = rc.getLocation();
        d112 = 0;

        l111 = l112.add(WEST); // (-1, 0) from (0, 0)
        d111 = 99999;

        l97 = l112.add(SOUTH); // (0, -1) from (0, 0)
        d97 = 99999;

        l127 = l112.add(NORTH); // (0, 1) from (0, 0)
        d127 = 99999;

        l113 = l112.add(EAST); // (1, 0) from (0, 0)
        d113 = 99999;

        l96 = l112.add(SOUTHWEST); // (-1, -1) from (0, 0)
        d96 = 99999;

        l126 = l112.add(NORTHWEST); // (-1, 1) from (0, 0)
        d126 = 99999;

        l98 = l112.add(SOUTHEAST); // (1, -1) from (0, 0)
        d98 = 99999;

        l128 = l112.add(NORTHEAST); // (1, 1) from (0, 0)
        d128 = 99999;

        l110 = l111.add(WEST); // (-2, 0) from (-1, 0)
        d110 = 99999;

        l82 = l97.add(SOUTH); // (0, -2) from (0, -1)
        d82 = 99999;

        l142 = l127.add(NORTH); // (0, 2) from (0, 1)
        d142 = 99999;

        l114 = l113.add(EAST); // (2, 0) from (1, 0)
        d114 = 99999;

        l95 = l111.add(SOUTHWEST); // (-2, -1) from (-1, 0)
        d95 = 99999;

        l125 = l111.add(NORTHWEST); // (-2, 1) from (-1, 0)
        d125 = 99999;

        l81 = l97.add(SOUTHWEST); // (-1, -2) from (0, -1)
        d81 = 99999;

        l141 = l127.add(NORTHWEST); // (-1, 2) from (0, 1)
        d141 = 99999;

        l83 = l97.add(SOUTHEAST); // (1, -2) from (0, -1)
        d83 = 99999;

        l143 = l127.add(NORTHEAST); // (1, 2) from (0, 1)
        d143 = 99999;

        l99 = l113.add(SOUTHEAST); // (2, -1) from (1, 0)
        d99 = 99999;

        l129 = l113.add(NORTHEAST); // (2, 1) from (1, 0)
        d129 = 99999;

        l80 = l96.add(SOUTHWEST); // (-2, -2) from (-1, -1)
        d80 = 99999;

        l140 = l126.add(NORTHWEST); // (-2, 2) from (-1, 1)
        d140 = 99999;

        l84 = l98.add(SOUTHEAST); // (2, -2) from (1, -1)
        d84 = 99999;

        l144 = l128.add(NORTHEAST); // (2, 2) from (1, 1)
        d144 = 99999;

        l109 = l110.add(WEST); // (-3, 0) from (-2, 0)
        d109 = 99999;

        l67 = l82.add(SOUTH); // (0, -3) from (0, -2)
        d67 = 99999;

        l157 = l142.add(NORTH); // (0, 3) from (0, 2)
        d157 = 99999;

        l115 = l114.add(EAST); // (3, 0) from (2, 0)
        d115 = 99999;

        l94 = l110.add(SOUTHWEST); // (-3, -1) from (-2, 0)
        d94 = 99999;

        l124 = l110.add(NORTHWEST); // (-3, 1) from (-2, 0)
        d124 = 99999;

        l66 = l82.add(SOUTHWEST); // (-1, -3) from (0, -2)
        d66 = 99999;

        l156 = l142.add(NORTHWEST); // (-1, 3) from (0, 2)
        d156 = 99999;

        l68 = l82.add(SOUTHEAST); // (1, -3) from (0, -2)
        d68 = 99999;

        l158 = l142.add(NORTHEAST); // (1, 3) from (0, 2)
        d158 = 99999;

        l100 = l114.add(SOUTHEAST); // (3, -1) from (2, 0)
        d100 = 99999;

        l130 = l114.add(NORTHEAST); // (3, 1) from (2, 0)
        d130 = 99999;

        l79 = l95.add(SOUTHWEST); // (-3, -2) from (-2, -1)
        d79 = 99999;

        l139 = l125.add(NORTHWEST); // (-3, 2) from (-2, 1)
        d139 = 99999;

        l65 = l81.add(SOUTHWEST); // (-2, -3) from (-1, -2)
        d65 = 99999;

        l155 = l141.add(NORTHWEST); // (-2, 3) from (-1, 2)
        d155 = 99999;

        l69 = l83.add(SOUTHEAST); // (2, -3) from (1, -2)
        d69 = 99999;

        l159 = l143.add(NORTHEAST); // (2, 3) from (1, 2)
        d159 = 99999;

        l85 = l99.add(SOUTHEAST); // (3, -2) from (2, -1)
        d85 = 99999;

        l145 = l129.add(NORTHEAST); // (3, 2) from (2, 1)
        d145 = 99999;

        l108 = l109.add(WEST); // (-4, 0) from (-3, 0)
        d108 = 99999;

        l52 = l67.add(SOUTH); // (0, -4) from (0, -3)
        d52 = 99999;

        l172 = l157.add(NORTH); // (0, 4) from (0, 3)
        d172 = 99999;

        l116 = l115.add(EAST); // (4, 0) from (3, 0)
        d116 = 99999;

        l93 = l109.add(SOUTHWEST); // (-4, -1) from (-3, 0)
        d93 = 99999;

        l123 = l109.add(NORTHWEST); // (-4, 1) from (-3, 0)
        d123 = 99999;

        l51 = l67.add(SOUTHWEST); // (-1, -4) from (0, -3)
        d51 = 99999;

        l171 = l157.add(NORTHWEST); // (-1, 4) from (0, 3)
        d171 = 99999;

        l53 = l67.add(SOUTHEAST); // (1, -4) from (0, -3)
        d53 = 99999;

        l173 = l157.add(NORTHEAST); // (1, 4) from (0, 3)
        d173 = 99999;

        l101 = l115.add(SOUTHEAST); // (4, -1) from (3, 0)
        d101 = 99999;

        l131 = l115.add(NORTHEAST); // (4, 1) from (3, 0)
        d131 = 99999;

        l64 = l80.add(SOUTHWEST); // (-3, -3) from (-2, -2)
        d64 = 99999;

        l154 = l140.add(NORTHWEST); // (-3, 3) from (-2, 2)
        d154 = 99999;

        l70 = l84.add(SOUTHEAST); // (3, -3) from (2, -2)
        d70 = 99999;

        l160 = l144.add(NORTHEAST); // (3, 3) from (2, 2)
        d160 = 99999;

        l78 = l94.add(SOUTHWEST); // (-4, -2) from (-3, -1)
        d78 = 99999;

        l138 = l124.add(NORTHWEST); // (-4, 2) from (-3, 1)
        d138 = 99999;

        l50 = l66.add(SOUTHWEST); // (-2, -4) from (-1, -3)
        d50 = 99999;

        l170 = l156.add(NORTHWEST); // (-2, 4) from (-1, 3)
        d170 = 99999;

        l54 = l68.add(SOUTHEAST); // (2, -4) from (1, -3)
        d54 = 99999;

        l174 = l158.add(NORTHEAST); // (2, 4) from (1, 3)
        d174 = 99999;

        l86 = l100.add(SOUTHEAST); // (4, -2) from (3, -1)
        d86 = 99999;

        l146 = l130.add(NORTHEAST); // (4, 2) from (3, 1)
        d146 = 99999;


        // check (-1, 0)
        if (rc.canSenseLocation(l111) && rc.sensePassability(l111)) {
            d111 = 0.4375;
            mapInfo = rc.senseMapInfo(l111);
            currentDir = mapInfo.getCurrentDirection();
            d111 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, -1)
        if (rc.canSenseLocation(l97) && rc.sensePassability(l97)) {
            d97 = Math.min(0.5, d111);
            mapInfo = rc.senseMapInfo(l97);
            currentDir = mapInfo.getCurrentDirection();
            d97 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, 1)
        if (rc.canSenseLocation(l127) && rc.sensePassability(l127)) {
            d127 = Math.min(0.375, d111);
            mapInfo = rc.senseMapInfo(l127);
            currentDir = mapInfo.getCurrentDirection();
            d127 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, 0)
        if (rc.canSenseLocation(l113) && rc.sensePassability(l113)) {
            d113 = Math.min(0.3125, Math.min(d97, d127));
            mapInfo = rc.senseMapInfo(l113);
            currentDir = mapInfo.getCurrentDirection();
            d113 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, -1)
        if (rc.canSenseLocation(l96) && rc.sensePassability(l96)) {
            d96 = Math.min(0.1875, Math.min(d111, d97));
            mapInfo = rc.senseMapInfo(l96);
            currentDir = mapInfo.getCurrentDirection();
            d96 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, 1)
        if (rc.canSenseLocation(l126) && rc.sensePassability(l126)) {
            d126 = Math.min(0.125, Math.min(d111, d127));
            mapInfo = rc.senseMapInfo(l126);
            currentDir = mapInfo.getCurrentDirection();
            d126 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, -1)
        if (rc.canSenseLocation(l98) && rc.sensePassability(l98)) {
            d98 = Math.min(0.25, Math.min(d97, d113));
            mapInfo = rc.senseMapInfo(l98);
            currentDir = mapInfo.getCurrentDirection();
            d98 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, 1)
        if (rc.canSenseLocation(l128) && rc.sensePassability(l128)) {
            d128 = Math.min(0.0625, Math.min(d127, d113));
            mapInfo = rc.senseMapInfo(l128);
            currentDir = mapInfo.getCurrentDirection();
            d128 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, 0)
        if (rc.canSenseLocation(l110) && rc.sensePassability(l110)) {
            d110 = Math.min(d111, Math.min(d96, d126));
            mapInfo = rc.senseMapInfo(l110);
            currentDir = mapInfo.getCurrentDirection();
            d110 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, -2)
        if (rc.canSenseLocation(l82) && rc.sensePassability(l82)) {
            d82 = Math.min(d97, Math.min(d96, d98));
            mapInfo = rc.senseMapInfo(l82);
            currentDir = mapInfo.getCurrentDirection();
            d82 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, 2)
        if (rc.canSenseLocation(l142) && rc.sensePassability(l142)) {
            d142 = Math.min(d127, Math.min(d126, d128));
            mapInfo = rc.senseMapInfo(l142);
            currentDir = mapInfo.getCurrentDirection();
            d142 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, 0)
        if (rc.canSenseLocation(l114) && rc.sensePassability(l114)) {
            d114 = Math.min(d113, Math.min(d98, d128));
            mapInfo = rc.senseMapInfo(l114);
            currentDir = mapInfo.getCurrentDirection();
            d114 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, -1)
        if (rc.canSenseLocation(l95) && rc.sensePassability(l95)) {
            d95 = Math.min(d111, Math.min(d96, d110));
            mapInfo = rc.senseMapInfo(l95);
            currentDir = mapInfo.getCurrentDirection();
            d95 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, 1)
        if (rc.canSenseLocation(l125) && rc.sensePassability(l125)) {
            d125 = Math.min(d111, Math.min(d126, d110));
            mapInfo = rc.senseMapInfo(l125);
            currentDir = mapInfo.getCurrentDirection();
            d125 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, -2)
        if (rc.canSenseLocation(l81) && rc.sensePassability(l81)) {
            d81 = Math.min(d97, Math.min(d96, Math.min(d82, d95)));
            mapInfo = rc.senseMapInfo(l81);
            currentDir = mapInfo.getCurrentDirection();
            d81 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, 2)
        if (rc.canSenseLocation(l141) && rc.sensePassability(l141)) {
            d141 = Math.min(d127, Math.min(d126, Math.min(d142, d125)));
            mapInfo = rc.senseMapInfo(l141);
            currentDir = mapInfo.getCurrentDirection();
            d141 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, -2)
        if (rc.canSenseLocation(l83) && rc.sensePassability(l83)) {
            d83 = Math.min(d97, Math.min(d98, d82));
            mapInfo = rc.senseMapInfo(l83);
            currentDir = mapInfo.getCurrentDirection();
            d83 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, 2)
        if (rc.canSenseLocation(l143) && rc.sensePassability(l143)) {
            d143 = Math.min(d127, Math.min(d128, d142));
            mapInfo = rc.senseMapInfo(l143);
            currentDir = mapInfo.getCurrentDirection();
            d143 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, -1)
        if (rc.canSenseLocation(l99) && rc.sensePassability(l99)) {
            d99 = Math.min(d113, Math.min(d98, Math.min(d114, d83)));
            mapInfo = rc.senseMapInfo(l99);
            currentDir = mapInfo.getCurrentDirection();
            d99 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, 1)
        if (rc.canSenseLocation(l129) && rc.sensePassability(l129)) {
            d129 = Math.min(d113, Math.min(d128, Math.min(d114, d143)));
            mapInfo = rc.senseMapInfo(l129);
            currentDir = mapInfo.getCurrentDirection();
            d129 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, -2)
        if (rc.canSenseLocation(l80) && rc.sensePassability(l80)) {
            d80 = Math.min(d96, Math.min(d95, d81));
            mapInfo = rc.senseMapInfo(l80);
            currentDir = mapInfo.getCurrentDirection();
            d80 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, 2)
        if (rc.canSenseLocation(l140) && rc.sensePassability(l140)) {
            d140 = Math.min(d126, Math.min(d125, d141));
            mapInfo = rc.senseMapInfo(l140);
            currentDir = mapInfo.getCurrentDirection();
            d140 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, -2)
        if (rc.canSenseLocation(l84) && rc.sensePassability(l84)) {
            d84 = Math.min(d98, Math.min(d83, d99));
            mapInfo = rc.senseMapInfo(l84);
            currentDir = mapInfo.getCurrentDirection();
            d84 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, 2)
        if (rc.canSenseLocation(l144) && rc.sensePassability(l144)) {
            d144 = Math.min(d128, Math.min(d143, d129));
            mapInfo = rc.senseMapInfo(l144);
            currentDir = mapInfo.getCurrentDirection();
            d144 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, 0)
        if (rc.canSenseLocation(l109) && rc.sensePassability(l109)) {
            d109 = Math.min(d110, Math.min(d95, d125));
            mapInfo = rc.senseMapInfo(l109);
            currentDir = mapInfo.getCurrentDirection();
            d109 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, -3)
        if (rc.canSenseLocation(l67) && rc.sensePassability(l67)) {
            d67 = Math.min(d82, Math.min(d81, d83));
            mapInfo = rc.senseMapInfo(l67);
            currentDir = mapInfo.getCurrentDirection();
            d67 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, 3)
        if (rc.canSenseLocation(l157) && rc.sensePassability(l157)) {
            d157 = Math.min(d142, Math.min(d141, d143));
            mapInfo = rc.senseMapInfo(l157);
            currentDir = mapInfo.getCurrentDirection();
            d157 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, 0)
        if (rc.canSenseLocation(l115) && rc.sensePassability(l115)) {
            d115 = Math.min(d114, Math.min(d99, d129));
            mapInfo = rc.senseMapInfo(l115);
            currentDir = mapInfo.getCurrentDirection();
            d115 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, -1)
        if (rc.canSenseLocation(l94) && rc.sensePassability(l94)) {
            d94 = Math.min(d110, Math.min(d95, Math.min(d80, d109)));
            mapInfo = rc.senseMapInfo(l94);
            currentDir = mapInfo.getCurrentDirection();
            d94 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, 1)
        if (rc.canSenseLocation(l124) && rc.sensePassability(l124)) {
            d124 = Math.min(d110, Math.min(d125, Math.min(d140, d109)));
            mapInfo = rc.senseMapInfo(l124);
            currentDir = mapInfo.getCurrentDirection();
            d124 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, -3)
        if (rc.canSenseLocation(l66) && rc.sensePassability(l66)) {
            d66 = Math.min(d82, Math.min(d81, Math.min(d80, d67)));
            mapInfo = rc.senseMapInfo(l66);
            currentDir = mapInfo.getCurrentDirection();
            d66 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, 3)
        if (rc.canSenseLocation(l156) && rc.sensePassability(l156)) {
            d156 = Math.min(d142, Math.min(d141, Math.min(d140, d157)));
            mapInfo = rc.senseMapInfo(l156);
            currentDir = mapInfo.getCurrentDirection();
            d156 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, -3)
        if (rc.canSenseLocation(l68) && rc.sensePassability(l68)) {
            d68 = Math.min(d82, Math.min(d83, Math.min(d84, d67)));
            mapInfo = rc.senseMapInfo(l68);
            currentDir = mapInfo.getCurrentDirection();
            d68 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, 3)
        if (rc.canSenseLocation(l158) && rc.sensePassability(l158)) {
            d158 = Math.min(d142, Math.min(d143, Math.min(d144, d157)));
            mapInfo = rc.senseMapInfo(l158);
            currentDir = mapInfo.getCurrentDirection();
            d158 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, -1)
        if (rc.canSenseLocation(l100) && rc.sensePassability(l100)) {
            d100 = Math.min(d114, Math.min(d99, Math.min(d84, d115)));
            mapInfo = rc.senseMapInfo(l100);
            currentDir = mapInfo.getCurrentDirection();
            d100 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, 1)
        if (rc.canSenseLocation(l130) && rc.sensePassability(l130)) {
            d130 = Math.min(d114, Math.min(d129, Math.min(d144, d115)));
            mapInfo = rc.senseMapInfo(l130);
            currentDir = mapInfo.getCurrentDirection();
            d130 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, -2)
        if (rc.canSenseLocation(l79) && rc.sensePassability(l79)) {
            d79 = Math.min(d95, Math.min(d80, d94));
            mapInfo = rc.senseMapInfo(l79);
            currentDir = mapInfo.getCurrentDirection();
            d79 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, 2)
        if (rc.canSenseLocation(l139) && rc.sensePassability(l139)) {
            d139 = Math.min(d125, Math.min(d140, d124));
            mapInfo = rc.senseMapInfo(l139);
            currentDir = mapInfo.getCurrentDirection();
            d139 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, -3)
        if (rc.canSenseLocation(l65) && rc.sensePassability(l65)) {
            d65 = Math.min(d81, Math.min(d80, Math.min(d66, d79)));
            mapInfo = rc.senseMapInfo(l65);
            currentDir = mapInfo.getCurrentDirection();
            d65 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, 3)
        if (rc.canSenseLocation(l155) && rc.sensePassability(l155)) {
            d155 = Math.min(d141, Math.min(d140, Math.min(d156, d139)));
            mapInfo = rc.senseMapInfo(l155);
            currentDir = mapInfo.getCurrentDirection();
            d155 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, -3)
        if (rc.canSenseLocation(l69) && rc.sensePassability(l69)) {
            d69 = Math.min(d83, Math.min(d84, d68));
            mapInfo = rc.senseMapInfo(l69);
            currentDir = mapInfo.getCurrentDirection();
            d69 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, 3)
        if (rc.canSenseLocation(l159) && rc.sensePassability(l159)) {
            d159 = Math.min(d143, Math.min(d144, d158));
            mapInfo = rc.senseMapInfo(l159);
            currentDir = mapInfo.getCurrentDirection();
            d159 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, -2)
        if (rc.canSenseLocation(l85) && rc.sensePassability(l85)) {
            d85 = Math.min(d99, Math.min(d84, Math.min(d100, d69)));
            mapInfo = rc.senseMapInfo(l85);
            currentDir = mapInfo.getCurrentDirection();
            d85 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, 2)
        if (rc.canSenseLocation(l145) && rc.sensePassability(l145)) {
            d145 = Math.min(d129, Math.min(d144, Math.min(d130, d159)));
            mapInfo = rc.senseMapInfo(l145);
            currentDir = mapInfo.getCurrentDirection();
            d145 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-4, 0)
        if (rc.canSenseLocation(l108) && rc.sensePassability(l108)) {
            d108 = Math.min(d109, Math.min(d94, d124));
            mapInfo = rc.senseMapInfo(l108);
            currentDir = mapInfo.getCurrentDirection();
            d108 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, -4)
        if (rc.canSenseLocation(l52) && rc.sensePassability(l52)) {
            d52 = Math.min(d67, Math.min(d66, d68));
            mapInfo = rc.senseMapInfo(l52);
            currentDir = mapInfo.getCurrentDirection();
            d52 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (0, 4)
        if (rc.canSenseLocation(l172) && rc.sensePassability(l172)) {
            d172 = Math.min(d157, Math.min(d156, d158));
            mapInfo = rc.senseMapInfo(l172);
            currentDir = mapInfo.getCurrentDirection();
            d172 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (4, 0)
        if (rc.canSenseLocation(l116) && rc.sensePassability(l116)) {
            d116 = Math.min(d115, Math.min(d100, d130));
            mapInfo = rc.senseMapInfo(l116);
            currentDir = mapInfo.getCurrentDirection();
            d116 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-4, -1)
        if (rc.canSenseLocation(l93) && rc.sensePassability(l93)) {
            d93 = Math.min(d109, Math.min(d94, Math.min(d79, d108)));
            mapInfo = rc.senseMapInfo(l93);
            currentDir = mapInfo.getCurrentDirection();
            d93 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-4, 1)
        if (rc.canSenseLocation(l123) && rc.sensePassability(l123)) {
            d123 = Math.min(d109, Math.min(d124, Math.min(d139, d108)));
            mapInfo = rc.senseMapInfo(l123);
            currentDir = mapInfo.getCurrentDirection();
            d123 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (WEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, -4)
        if (rc.canSenseLocation(l51) && rc.sensePassability(l51)) {
            d51 = Math.min(d67, Math.min(d66, Math.min(d65, d52)));
            mapInfo = rc.senseMapInfo(l51);
            currentDir = mapInfo.getCurrentDirection();
            d51 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-1, 4)
        if (rc.canSenseLocation(l171) && rc.sensePassability(l171)) {
            d171 = Math.min(d157, Math.min(d156, Math.min(d155, d172)));
            mapInfo = rc.senseMapInfo(l171);
            currentDir = mapInfo.getCurrentDirection();
            d171 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, -4)
        if (rc.canSenseLocation(l53) && rc.sensePassability(l53)) {
            d53 = Math.min(d67, Math.min(d68, Math.min(d69, d52)));
            mapInfo = rc.senseMapInfo(l53);
            currentDir = mapInfo.getCurrentDirection();
            d53 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (1, 4)
        if (rc.canSenseLocation(l173) && rc.sensePassability(l173)) {
            d173 = Math.min(d157, Math.min(d158, Math.min(d159, d172)));
            mapInfo = rc.senseMapInfo(l173);
            currentDir = mapInfo.getCurrentDirection();
            d173 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTH_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (4, -1)
        if (rc.canSenseLocation(l101) && rc.sensePassability(l101)) {
            d101 = Math.min(d115, Math.min(d100, Math.min(d85, d116)));
            mapInfo = rc.senseMapInfo(l101);
            currentDir = mapInfo.getCurrentDirection();
            d101 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (4, 1)
        if (rc.canSenseLocation(l131) && rc.sensePassability(l131)) {
            d131 = Math.min(d115, Math.min(d130, Math.min(d145, d116)));
            mapInfo = rc.senseMapInfo(l131);
            currentDir = mapInfo.getCurrentDirection();
            d131 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (EAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, -3)
        if (rc.canSenseLocation(l64) && rc.sensePassability(l64)) {
            d64 = Math.min(d80, Math.min(d79, d65));
            mapInfo = rc.senseMapInfo(l64);
            currentDir = mapInfo.getCurrentDirection();
            d64 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-3, 3)
        if (rc.canSenseLocation(l154) && rc.sensePassability(l154)) {
            d154 = Math.min(d140, Math.min(d139, d155));
            mapInfo = rc.senseMapInfo(l154);
            currentDir = mapInfo.getCurrentDirection();
            d154 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, -3)
        if (rc.canSenseLocation(l70) && rc.sensePassability(l70)) {
            d70 = Math.min(d84, Math.min(d69, d85));
            mapInfo = rc.senseMapInfo(l70);
            currentDir = mapInfo.getCurrentDirection();
            d70 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (3, 3)
        if (rc.canSenseLocation(l160) && rc.sensePassability(l160)) {
            d160 = Math.min(d144, Math.min(d159, d145));
            mapInfo = rc.senseMapInfo(l160);
            currentDir = mapInfo.getCurrentDirection();
            d160 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-4, -2)
        if (rc.canSenseLocation(l78) && rc.sensePassability(l78)) {
            d78 = Math.min(d94, Math.min(d79, Math.min(d93, d64)));
            mapInfo = rc.senseMapInfo(l78);
            currentDir = mapInfo.getCurrentDirection();
            d78 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-4, 2)
        if (rc.canSenseLocation(l138) && rc.sensePassability(l138)) {
            d138 = Math.min(d124, Math.min(d139, Math.min(d123, d154)));
            mapInfo = rc.senseMapInfo(l138);
            currentDir = mapInfo.getCurrentDirection();
            d138 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, -4)
        if (rc.canSenseLocation(l50) && rc.sensePassability(l50)) {
            d50 = Math.min(d66, Math.min(d65, Math.min(d51, d64)));
            mapInfo = rc.senseMapInfo(l50);
            currentDir = mapInfo.getCurrentDirection();
            d50 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (-2, 4)
        if (rc.canSenseLocation(l170) && rc.sensePassability(l170)) {
            d170 = Math.min(d156, Math.min(d155, Math.min(d171, d154)));
            mapInfo = rc.senseMapInfo(l170);
            currentDir = mapInfo.getCurrentDirection();
            d170 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHWEST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, -4)
        if (rc.canSenseLocation(l54) && rc.sensePassability(l54)) {
            d54 = Math.min(d68, Math.min(d69, Math.min(d53, d70)));
            mapInfo = rc.senseMapInfo(l54);
            currentDir = mapInfo.getCurrentDirection();
            d54 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (2, 4)
        if (rc.canSenseLocation(l174) && rc.sensePassability(l174)) {
            d174 = Math.min(d158, Math.min(d159, Math.min(d173, d160)));
            mapInfo = rc.senseMapInfo(l174);
            currentDir = mapInfo.getCurrentDirection();
            d174 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (4, -2)
        if (rc.canSenseLocation(l86) && rc.sensePassability(l86)) {
            d86 = Math.min(d100, Math.min(d85, Math.min(d101, d70)));
            mapInfo = rc.senseMapInfo(l86);
            currentDir = mapInfo.getCurrentDirection();
            d86 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (SOUTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
        }

        // check (4, 2)
        if (rc.canSenseLocation(l146) && rc.sensePassability(l146)) {
            d146 = Math.min(d130, Math.min(d145, Math.min(d131, d160)));
            mapInfo = rc.senseMapInfo(l146);
            currentDir = mapInfo.getCurrentDirection();
            d146 += 10 * (mapInfo.getCooldownMultiplier(team)) + 
                 (currentDir == CENTER ? 0 :
                 (NORTHEAST_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));
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

        int target_dx = target.x - l112.x;
        int target_dy = target.y - l112.y;
        switch (target_dx) {
                case -4:
                    switch (target_dy) {
                        case -2:
                            return direction(d78); // destination is at relative location (-4, -2)
                        case -1:
                            return direction(d93); // destination is at relative location (-4, -1)
                        case 0:
                            return direction(d108); // destination is at relative location (-4, 0)
                        case 1:
                            return direction(d123); // destination is at relative location (-4, 1)
                        case 2:
                            return direction(d138); // destination is at relative location (-4, 2)
                    }
                    break;
                case -3:
                    switch (target_dy) {
                        case -3:
                            return direction(d64); // destination is at relative location (-3, -3)
                        case -2:
                            return direction(d79); // destination is at relative location (-3, -2)
                        case -1:
                            return direction(d94); // destination is at relative location (-3, -1)
                        case 0:
                            return direction(d109); // destination is at relative location (-3, 0)
                        case 1:
                            return direction(d124); // destination is at relative location (-3, 1)
                        case 2:
                            return direction(d139); // destination is at relative location (-3, 2)
                        case 3:
                            return direction(d154); // destination is at relative location (-3, 3)
                    }
                    break;
                case -2:
                    switch (target_dy) {
                        case -4:
                            return direction(d50); // destination is at relative location (-2, -4)
                        case -3:
                            return direction(d65); // destination is at relative location (-2, -3)
                        case -2:
                            return direction(d80); // destination is at relative location (-2, -2)
                        case -1:
                            return direction(d95); // destination is at relative location (-2, -1)
                        case 0:
                            return direction(d110); // destination is at relative location (-2, 0)
                        case 1:
                            return direction(d125); // destination is at relative location (-2, 1)
                        case 2:
                            return direction(d140); // destination is at relative location (-2, 2)
                        case 3:
                            return direction(d155); // destination is at relative location (-2, 3)
                        case 4:
                            return direction(d170); // destination is at relative location (-2, 4)
                    }
                    break;
                case -1:
                    switch (target_dy) {
                        case -4:
                            return direction(d51); // destination is at relative location (-1, -4)
                        case -3:
                            return direction(d66); // destination is at relative location (-1, -3)
                        case -2:
                            return direction(d81); // destination is at relative location (-1, -2)
                        case -1:
                            return direction(d96); // destination is at relative location (-1, -1)
                        case 0:
                            return direction(d111); // destination is at relative location (-1, 0)
                        case 1:
                            return direction(d126); // destination is at relative location (-1, 1)
                        case 2:
                            return direction(d141); // destination is at relative location (-1, 2)
                        case 3:
                            return direction(d156); // destination is at relative location (-1, 3)
                        case 4:
                            return direction(d171); // destination is at relative location (-1, 4)
                    }
                    break;
                case 0:
                    switch (target_dy) {
                        case -4:
                            return direction(d52); // destination is at relative location (0, -4)
                        case -3:
                            return direction(d67); // destination is at relative location (0, -3)
                        case -2:
                            return direction(d82); // destination is at relative location (0, -2)
                        case -1:
                            return direction(d97); // destination is at relative location (0, -1)
                        case 0:
                            return direction(d112); // destination is at relative location (0, 0)
                        case 1:
                            return direction(d127); // destination is at relative location (0, 1)
                        case 2:
                            return direction(d142); // destination is at relative location (0, 2)
                        case 3:
                            return direction(d157); // destination is at relative location (0, 3)
                        case 4:
                            return direction(d172); // destination is at relative location (0, 4)
                    }
                    break;
                case 1:
                    switch (target_dy) {
                        case -4:
                            return direction(d53); // destination is at relative location (1, -4)
                        case -3:
                            return direction(d68); // destination is at relative location (1, -3)
                        case -2:
                            return direction(d83); // destination is at relative location (1, -2)
                        case -1:
                            return direction(d98); // destination is at relative location (1, -1)
                        case 0:
                            return direction(d113); // destination is at relative location (1, 0)
                        case 1:
                            return direction(d128); // destination is at relative location (1, 1)
                        case 2:
                            return direction(d143); // destination is at relative location (1, 2)
                        case 3:
                            return direction(d158); // destination is at relative location (1, 3)
                        case 4:
                            return direction(d173); // destination is at relative location (1, 4)
                    }
                    break;
                case 2:
                    switch (target_dy) {
                        case -4:
                            return direction(d54); // destination is at relative location (2, -4)
                        case -3:
                            return direction(d69); // destination is at relative location (2, -3)
                        case -2:
                            return direction(d84); // destination is at relative location (2, -2)
                        case -1:
                            return direction(d99); // destination is at relative location (2, -1)
                        case 0:
                            return direction(d114); // destination is at relative location (2, 0)
                        case 1:
                            return direction(d129); // destination is at relative location (2, 1)
                        case 2:
                            return direction(d144); // destination is at relative location (2, 2)
                        case 3:
                            return direction(d159); // destination is at relative location (2, 3)
                        case 4:
                            return direction(d174); // destination is at relative location (2, 4)
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -3:
                            return direction(d70); // destination is at relative location (3, -3)
                        case -2:
                            return direction(d85); // destination is at relative location (3, -2)
                        case -1:
                            return direction(d100); // destination is at relative location (3, -1)
                        case 0:
                            return direction(d115); // destination is at relative location (3, 0)
                        case 1:
                            return direction(d130); // destination is at relative location (3, 1)
                        case 2:
                            return direction(d145); // destination is at relative location (3, 2)
                        case 3:
                            return direction(d160); // destination is at relative location (3, 3)
                    }
                    break;
                case 4:
                    switch (target_dy) {
                        case -2:
                            return direction(d86); // destination is at relative location (4, -2)
                        case -1:
                            return direction(d101); // destination is at relative location (4, -1)
                        case 0:
                            return direction(d116); // destination is at relative location (4, 0)
                        case 1:
                            return direction(d131); // destination is at relative location (4, 1)
                        case 2:
                            return direction(d146); // destination is at relative location (4, 2)
                    }
                    break;
        }
        
        ans = Double.POSITIVE_INFINITY;
        bestScore = 0;
        currDist = Math.sqrt(l112.distanceSquaredTo(target));
        
        score78 = (currDist - Math.sqrt(l78.distanceSquaredTo(target))) / d78;
        if (score78 > bestScore) {
            bestScore = score78;
            ans = d78;
        }

        score93 = (currDist - Math.sqrt(l93.distanceSquaredTo(target))) / d93;
        if (score93 > bestScore) {
            bestScore = score93;
            ans = d93;
        }

        score108 = (currDist - Math.sqrt(l108.distanceSquaredTo(target))) / d108;
        if (score108 > bestScore) {
            bestScore = score108;
            ans = d108;
        }

        score123 = (currDist - Math.sqrt(l123.distanceSquaredTo(target))) / d123;
        if (score123 > bestScore) {
            bestScore = score123;
            ans = d123;
        }

        score138 = (currDist - Math.sqrt(l138.distanceSquaredTo(target))) / d138;
        if (score138 > bestScore) {
            bestScore = score138;
            ans = d138;
        }

        score64 = (currDist - Math.sqrt(l64.distanceSquaredTo(target))) / d64;
        if (score64 > bestScore) {
            bestScore = score64;
            ans = d64;
        }

        score79 = (currDist - Math.sqrt(l79.distanceSquaredTo(target))) / d79;
        if (score79 > bestScore) {
            bestScore = score79;
            ans = d79;
        }

        score139 = (currDist - Math.sqrt(l139.distanceSquaredTo(target))) / d139;
        if (score139 > bestScore) {
            bestScore = score139;
            ans = d139;
        }

        score154 = (currDist - Math.sqrt(l154.distanceSquaredTo(target))) / d154;
        if (score154 > bestScore) {
            bestScore = score154;
            ans = d154;
        }

        score50 = (currDist - Math.sqrt(l50.distanceSquaredTo(target))) / d50;
        if (score50 > bestScore) {
            bestScore = score50;
            ans = d50;
        }

        score65 = (currDist - Math.sqrt(l65.distanceSquaredTo(target))) / d65;
        if (score65 > bestScore) {
            bestScore = score65;
            ans = d65;
        }

        score155 = (currDist - Math.sqrt(l155.distanceSquaredTo(target))) / d155;
        if (score155 > bestScore) {
            bestScore = score155;
            ans = d155;
        }

        score170 = (currDist - Math.sqrt(l170.distanceSquaredTo(target))) / d170;
        if (score170 > bestScore) {
            bestScore = score170;
            ans = d170;
        }

        score51 = (currDist - Math.sqrt(l51.distanceSquaredTo(target))) / d51;
        if (score51 > bestScore) {
            bestScore = score51;
            ans = d51;
        }

        score171 = (currDist - Math.sqrt(l171.distanceSquaredTo(target))) / d171;
        if (score171 > bestScore) {
            bestScore = score171;
            ans = d171;
        }

        score52 = (currDist - Math.sqrt(l52.distanceSquaredTo(target))) / d52;
        if (score52 > bestScore) {
            bestScore = score52;
            ans = d52;
        }

        score172 = (currDist - Math.sqrt(l172.distanceSquaredTo(target))) / d172;
        if (score172 > bestScore) {
            bestScore = score172;
            ans = d172;
        }

        score53 = (currDist - Math.sqrt(l53.distanceSquaredTo(target))) / d53;
        if (score53 > bestScore) {
            bestScore = score53;
            ans = d53;
        }

        score173 = (currDist - Math.sqrt(l173.distanceSquaredTo(target))) / d173;
        if (score173 > bestScore) {
            bestScore = score173;
            ans = d173;
        }

        score54 = (currDist - Math.sqrt(l54.distanceSquaredTo(target))) / d54;
        if (score54 > bestScore) {
            bestScore = score54;
            ans = d54;
        }

        score69 = (currDist - Math.sqrt(l69.distanceSquaredTo(target))) / d69;
        if (score69 > bestScore) {
            bestScore = score69;
            ans = d69;
        }

        score159 = (currDist - Math.sqrt(l159.distanceSquaredTo(target))) / d159;
        if (score159 > bestScore) {
            bestScore = score159;
            ans = d159;
        }

        score174 = (currDist - Math.sqrt(l174.distanceSquaredTo(target))) / d174;
        if (score174 > bestScore) {
            bestScore = score174;
            ans = d174;
        }

        score70 = (currDist - Math.sqrt(l70.distanceSquaredTo(target))) / d70;
        if (score70 > bestScore) {
            bestScore = score70;
            ans = d70;
        }

        score85 = (currDist - Math.sqrt(l85.distanceSquaredTo(target))) / d85;
        if (score85 > bestScore) {
            bestScore = score85;
            ans = d85;
        }

        score145 = (currDist - Math.sqrt(l145.distanceSquaredTo(target))) / d145;
        if (score145 > bestScore) {
            bestScore = score145;
            ans = d145;
        }

        score160 = (currDist - Math.sqrt(l160.distanceSquaredTo(target))) / d160;
        if (score160 > bestScore) {
            bestScore = score160;
            ans = d160;
        }

        score86 = (currDist - Math.sqrt(l86.distanceSquaredTo(target))) / d86;
        if (score86 > bestScore) {
            bestScore = score86;
            ans = d86;
        }

        score101 = (currDist - Math.sqrt(l101.distanceSquaredTo(target))) / d101;
        if (score101 > bestScore) {
            bestScore = score101;
            ans = d101;
        }

        score116 = (currDist - Math.sqrt(l116.distanceSquaredTo(target))) / d116;
        if (score116 > bestScore) {
            bestScore = score116;
            ans = d116;
        }

        score131 = (currDist - Math.sqrt(l131.distanceSquaredTo(target))) / d131;
        if (score131 > bestScore) {
            bestScore = score131;
            ans = d131;
        }

        score146 = (currDist - Math.sqrt(l146.distanceSquaredTo(target))) / d146;
        if (score146 > bestScore) {
            bestScore = score146;
            ans = d146;
        }

        
        return direction(ans);
    }
}
