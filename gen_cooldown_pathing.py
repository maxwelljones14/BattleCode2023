#! /usr/bin/env python3

import sys
from pathlib import Path
from math import *

def encode(x, y):
    return (x+7) + 15*(y+7)

"""
    static String n = "\00\01\02";

    static boolean isIn2(Direction dir) {
        return n.indexOf(dir.ordinal()) != -1;
    }
"""
RADII = {'34': 34, '20': 20, '10': 10}
SMALLER_RADII = {'34': 20, '20': 10, '10': 5}

DIRECTIONS = {
    (1, 0): 'EAST',
    (-1, 0): 'WEST',
    (0, 1): 'NORTH',
    (0, -1): 'SOUTH',
    (1, 1): 'NORTHEAST',
    (-1, 1): 'NORTHWEST',
    (1, -1): 'SOUTHEAST',
    (-1, -1): 'SOUTHWEST',
}

def min_chain(vals):
    if len(vals) == 1:
        return vals[0]
    return f"Math.min({vals[0]}, {min_chain(vals[1:])})"

def opposite(dir):
    if dir == 'EAST':
        return 'WEST'
    if dir == 'WEST':
        return 'EAST'
    if dir == 'NORTH':
        return 'SOUTH'
    if dir == 'SOUTH':
        return 'NORTH'
    if dir == 'NORTHEAST':
        return 'SOUTHWEST'
    if dir == 'NORTHWEST':
        return 'SOUTHEAST'
    if dir == 'SOUTHEAST':
        return 'NORTHWEST'
    if dir == 'SOUTHWEST':
        return 'NORTHEAST'
    return 'CENTER'

def direction_to(dx, dy):
    if (abs(dx) >= 2.414 * abs(dy)):
        if (dx > 0):
            return "EAST"
        elif (dx < 0):
            return "WEST"
        else:
            return "CENTER"
    elif (abs(dy) >= 2.414 * abs(dx)):
        if (dy > 0):
            return "NORTH"
        else:
            return "SOUTH"
    else:
        if (dy > 0):
            if (dx > 0):
                return "NORTHEAST"
            else:
                return "NORTHWEST"
        else:
            if (dx > 0):
                return "SOUTHEAST"
            else:
                return "SOUTHWEST"

def dist(x, y):
    return x*x + y*y

def gen_constants(radius):
    out = f""""""
    for x in range(-7, 8):
        for y in range(-7, 8):
            if dist(x, y) <= radius:
                out += f"""
    static MapLocation l{encode(x,y)}; // location representing relative coordinate ({x}, {y})
    static double d{encode(x,y)}; // shortest distance to location from current location
    static double score{encode(x,y)}; // heuristic distance from location to target
"""
    return out

def sign(x):
    if x > 0:
        return 1
    if x < 0:
        return -1
    return 0

def gen_init(radius):
    out = f"""
        l{encode(0,0)} = rc.getLocation();
        d{encode(0,0)} = 0;
"""
    for r2 in range(1, radius+1):
        for x in range(-7, 8):
            for y in range(-7, 8):
                if dist(x, y) == r2:
                    out += f"""
        l{encode(x,y)} = l{encode(x - sign(x), y - sign(y))}.add({DIRECTIONS[(sign(x), sign(y))]}); // ({x}, {y}) from ({x - sign(x)}, {y - sign(y)})
        d{encode(x,y)} = 99999;
"""
    return out

def gen_bfs(radius):
    visited = set([encode(0,0)])
    out = f""""""
    for r2 in range(1, radius+1):
        for x in range(-7, 8):
            for y in range(-7, 8):
                if dist(x, y) == r2:
                    indent = ""
                    out += f"""
        // check ({x}, {y})
        if (rc.canSenseLocation(l{encode(x,y)}) && rc.sensePassability(l{encode(x,y)})) {{"""
                    indent = "    "
                    dxdy = [(dx, dy) for dx in range(-1, 2) for dy in range(-1, 2) if (dx, dy) != (0, 0) and dist(x+dx,y+dy) <= radius]
                    dxdy = sorted(dxdy, key=lambda dd: dist(x+dd[0], y+dd[1]))
                    vals = []
                    for dx, dy in dxdy:
                        if encode(x+dx, y+dy) in visited:
                            vals.append(str([1/16, 5/16, 2/16, 6/16, 3/16, 7/16, 4/16, 8/16][(round(atan2(-dy,-dx)/pi*4)+8)%8]) if (x+dx,y+dy) == (0, 0) else f'd{encode(x+dx,y+dy)}')
            #                 out += f"""
            # {indent}if (d{encode(x,y)} > d{encode(x+dx,y+dy)}) {{ // from ({x+dx}, {y+dy})
            #     {indent}d{encode(x,y)} = {str([5/16, 1/16, 6/16, 2/16, 7/16, 3/16, 8/16, 4/16][(round(atan2(-dy,-dx)/pi*4)+8)%8]) if (x+dx,y+dy) == (0, 0) else f'd{encode(x+dx,y+dy)}'};
            # {indent}}}"""
                    out += f"""
        {indent}d{encode(x,y)} = {min_chain(vals)};"""

                    out += f"""
        {indent}mapInfo = rc.senseMapInfo(l{encode(x,y)});
        {indent}currentDir = mapInfo.getCurrentDirection();
        {indent}d{encode(x,y)} += 10 * (mapInfo.getCooldownMultiplier(team)) + 
        {indent}{indent} (currentDir == CENTER ? 0 :
        {indent}{indent} (TARGET_DIR_ADJ.indexOf(currentDir.ordinal()) >= 0 ? -5 : 5));"""
                    out += f"""
        }}"""
                    visited.add(encode(x,y))
                    out += f"""
"""
    return out

def gen_selection(radius, smaller_radius):
    out = f"""
        int target_dx = target.x - l{encode(0,0)}.x;
        int target_dy = target.y - l{encode(0,0)}.y;
        switch (target_dx) {{"""
    for tdx in range(-7, 8):
        if tdx**2 <= radius:
            out += f"""
                case {tdx}:
                    switch (target_dy) {{"""
            for tdy in range(-7, 8):
                if dist(tdx, tdy) <= radius:
                    out += f"""
                        case {tdy}:
                            return direction(d{encode(tdx, tdy)}); // destination is at relative location ({tdx}, {tdy})"""
                            # if (dir{encode(tdx, tdy)} != null)
                            #     return dir{encode(tdx, tdy)}; // destination is at relative location ({tdx}, {tdy})
                            # break;"""
            out += f"""
                    }}
                    break;"""
    out += f"""
        }}
        
        ans = Double.POSITIVE_INFINITY;
        bestScore = 0;
        currDist = Math.sqrt(l{encode(0,0)}.distanceSquaredTo(target));
        """
    for x in range(-7, 8):
        for y in range(-7, 8):
            if smaller_radius < dist(x, y) <= radius: # on the edge of the radius radius
                out += f"""
        score{encode(x,y)} = (currDist - Math.sqrt(l{encode(x,y)}.distanceSquaredTo(target))) / d{encode(x,y)};
        if (score{encode(x,y)} > bestScore) {{
            bestScore = score{encode(x,y)};
            ans = d{encode(x,y)};
        }}
"""
    return out

def gen_print(radius):
    out = f"""
        // System.out.println("LOCAL DISTANCES:");"""
    for y in range(7, -8, -1):
        if y**2 <= radius:
            out += f"""
        // System.out.println("""
            for x in range(-7, 8):
                if dist(x, y) <= radius:
                    out += f""""\\t" + d{encode(x,y)} + """
                else:
                    out += f""""\\t" + """
            out = out[:-3] + """);"""
    return out

def gen_full(bot, rad):
    radius = RADII[rad]
    smaller_radius = SMALLER_RADII[rad]
    out_file = Path('./src/') / bot / f'bfs/BFSCooldown{rad}.java'
    with open(out_file, 'w') as f:
        f.write(f"""package {bot}.bfs;

import battlecode.common.*;

public class BFSCooldown{rad} {{

    public static RobotController rc;
{gen_constants(radius)}

    public static void init(RobotController r) {{
        rc = r;
        team = rc.getTeam();
    }}

    private static final Direction[] DIRECTIONS = new Direction[] {{null, Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHWEST, Direction.SOUTHEAST}};

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

    public static String NORTH_ADJ = "\\07\\00\\01";
    public static String NORTHEAST_ADJ = "\\00\\01\\02";
    public static String EAST_ADJ = "\\01\\02\\03";
    public static String SOUTHEAST_ADJ = "\\02\\03\\04";
    public static String SOUTH_ADJ = "\\03\\04\\05";
    public static String SOUTHWEST_ADJ = "\\04\\05\\06";
    public static String WEST_ADJ = "\\05\\06\\07";
    public static String NORTHWEST_ADJ = "\\06\\07\\00";
    public static String TARGET_DIR_ADJ = "";

    public static Direction direction(double dist) {{
        if (dist==Double.POSITIVE_INFINITY) {{
            return null;
        }}
        return DIRECTIONS[(int)(dist * 16 % 16)];
    }}

    public static Direction bestDir(MapLocation target) throws GameActionException {{
        switch (rc.getLocation().directionTo(target)) {{
            case NORTH: TARGET_DIR_ADJ = NORTH_ADJ; break;
            case NORTHEAST: TARGET_DIR_ADJ = NORTHEAST_ADJ; break;
            case EAST: TARGET_DIR_ADJ = EAST_ADJ; break;
            case SOUTHEAST: TARGET_DIR_ADJ = SOUTHEAST_ADJ; break;
            case SOUTH: TARGET_DIR_ADJ = SOUTH_ADJ; break;
            case SOUTHWEST: TARGET_DIR_ADJ = SOUTHWEST_ADJ; break;
            case WEST: TARGET_DIR_ADJ = WEST_ADJ; break;
            case NORTHWEST: TARGET_DIR_ADJ = NORTHWEST_ADJ; break;
            default: TARGET_DIR_ADJ = ""; break;
        }}
{gen_init(radius)}
{gen_bfs(radius)}
{gen_print(radius)}
{gen_selection(radius, smaller_radius)}
        
        return direction(ans);
    }}
}}
""")

if __name__ == '__main__':
    if len(sys.argv) == 2:
        for rad in ["34", "20", "10"]:
            gen_full(sys.argv[1], rad)
    else:
        gen_full(sys.argv[1], sys.argv[2])
