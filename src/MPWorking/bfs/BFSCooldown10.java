package MPWorking.bfs;

import battlecode.common.*;

public class BFSCooldown10 {

    public static RobotController rc;

    static MapLocation l94; // location representing relative coordinate (-3, -1)
    static double d94; // shortest distance to location from current location
    static double score94; // heuristic distance from location to target

    static MapLocation l109; // location representing relative coordinate (-3, 0)
    static double d109; // shortest distance to location from current location
    static double score109; // heuristic distance from location to target

    static MapLocation l124; // location representing relative coordinate (-3, 1)
    static double d124; // shortest distance to location from current location
    static double score124; // heuristic distance from location to target

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

    static MapLocation l100; // location representing relative coordinate (3, -1)
    static double d100; // shortest distance to location from current location
    static double score100; // heuristic distance from location to target

    static MapLocation l115; // location representing relative coordinate (3, 0)
    static double d115; // shortest distance to location from current location
    static double score115; // heuristic distance from location to target

    static MapLocation l130; // location representing relative coordinate (3, 1)
    static double d130; // shortest distance to location from current location
    static double score130; // heuristic distance from location to target


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


        // System.out.println("LOCAL DISTANCES:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d156 + "\t" + d157 + "\t" + d158 + "\t" + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d140 + "\t" + d141 + "\t" + d142 + "\t" + d143 + "\t" + d144 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d124 + "\t" + d125 + "\t" + d126 + "\t" + d127 + "\t" + d128 + "\t" + d129 + "\t" + d130 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d109 + "\t" + d110 + "\t" + d111 + "\t" + d112 + "\t" + d113 + "\t" + d114 + "\t" + d115 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d94 + "\t" + d95 + "\t" + d96 + "\t" + d97 + "\t" + d98 + "\t" + d99 + "\t" + d100 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d80 + "\t" + d81 + "\t" + d82 + "\t" + d83 + "\t" + d84 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d66 + "\t" + d67 + "\t" + d68 + "\t" + "\t" + "\t" + "\t" + "\t" + "\t");

        int target_dx = target.x - l112.x;
        int target_dy = target.y - l112.y;
        switch (target_dx) {
                case -3:
                    switch (target_dy) {
                        case -1:
                            return direction(d94); // destination is at relative location (-3, -1)
                        case 0:
                            return direction(d109); // destination is at relative location (-3, 0)
                        case 1:
                            return direction(d124); // destination is at relative location (-3, 1)
                    }
                    break;
                case -2:
                    switch (target_dy) {
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
                    }
                    break;
                case -1:
                    switch (target_dy) {
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
                    }
                    break;
                case 0:
                    switch (target_dy) {
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
                    }
                    break;
                case 1:
                    switch (target_dy) {
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
                    }
                    break;
                case 2:
                    switch (target_dy) {
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
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -1:
                            return direction(d100); // destination is at relative location (3, -1)
                        case 0:
                            return direction(d115); // destination is at relative location (3, 0)
                        case 1:
                            return direction(d130); // destination is at relative location (3, 1)
                    }
                    break;
        }
        
        ans = Double.POSITIVE_INFINITY;
        bestScore = 0;
        currDist = Math.sqrt(l112.distanceSquaredTo(target));
        
        score94 = (currDist - Math.sqrt(l94.distanceSquaredTo(target))) / d94;
        if (score94 > bestScore) {
            bestScore = score94;
            ans = d94;
        }

        score109 = (currDist - Math.sqrt(l109.distanceSquaredTo(target))) / d109;
        if (score109 > bestScore) {
            bestScore = score109;
            ans = d109;
        }

        score124 = (currDist - Math.sqrt(l124.distanceSquaredTo(target))) / d124;
        if (score124 > bestScore) {
            bestScore = score124;
            ans = d124;
        }

        score80 = (currDist - Math.sqrt(l80.distanceSquaredTo(target))) / d80;
        if (score80 > bestScore) {
            bestScore = score80;
            ans = d80;
        }

        score140 = (currDist - Math.sqrt(l140.distanceSquaredTo(target))) / d140;
        if (score140 > bestScore) {
            bestScore = score140;
            ans = d140;
        }

        score66 = (currDist - Math.sqrt(l66.distanceSquaredTo(target))) / d66;
        if (score66 > bestScore) {
            bestScore = score66;
            ans = d66;
        }

        score156 = (currDist - Math.sqrt(l156.distanceSquaredTo(target))) / d156;
        if (score156 > bestScore) {
            bestScore = score156;
            ans = d156;
        }

        score67 = (currDist - Math.sqrt(l67.distanceSquaredTo(target))) / d67;
        if (score67 > bestScore) {
            bestScore = score67;
            ans = d67;
        }

        score157 = (currDist - Math.sqrt(l157.distanceSquaredTo(target))) / d157;
        if (score157 > bestScore) {
            bestScore = score157;
            ans = d157;
        }

        score68 = (currDist - Math.sqrt(l68.distanceSquaredTo(target))) / d68;
        if (score68 > bestScore) {
            bestScore = score68;
            ans = d68;
        }

        score158 = (currDist - Math.sqrt(l158.distanceSquaredTo(target))) / d158;
        if (score158 > bestScore) {
            bestScore = score158;
            ans = d158;
        }

        score84 = (currDist - Math.sqrt(l84.distanceSquaredTo(target))) / d84;
        if (score84 > bestScore) {
            bestScore = score84;
            ans = d84;
        }

        score144 = (currDist - Math.sqrt(l144.distanceSquaredTo(target))) / d144;
        if (score144 > bestScore) {
            bestScore = score144;
            ans = d144;
        }

        score100 = (currDist - Math.sqrt(l100.distanceSquaredTo(target))) / d100;
        if (score100 > bestScore) {
            bestScore = score100;
            ans = d100;
        }

        score115 = (currDist - Math.sqrt(l115.distanceSquaredTo(target))) / d115;
        if (score115 > bestScore) {
            bestScore = score115;
            ans = d115;
        }

        score130 = (currDist - Math.sqrt(l130.distanceSquaredTo(target))) / d130;
        if (score130 > bestScore) {
            bestScore = score130;
            ans = d130;
        }

        
        return direction(ans);
    }
}
