#! /usr/bin/env python3

# Based on the sorting networks given in
# https://bertdobbelaere.github.io/sorting_networks_extended.html#N64L521D22
# Costs about ~9 BC per comparison/swap + ~16 BC per element
# This can be done better, a la https://raw.githubusercontent.com/IvanGeffner/BTC/master/src/Godplayer/IfSorting.java
# but this is a good start.

from pathlib import Path
import sys
import math

networks = [
    [],
    [],
    [[(0,1)]],
    [[(0,2)],[(0,1)],[(1,2)]],
    [[(0,2),(1,3)],[(0,1),(2,3)],[(1,2)]],
    [[(0,3),(1,4)],[(0,2),(1,3)],[(0,1),(2,4)],[(1,2),(3,4)],[(2,3)]],
    [[(0,5),(1,3),(2,4)],[(1,2),(3,4)],[(0,3),(2,5)],[(0,1),(2,3),(4,5)],[(1,2),(3,4)]],
    [[(0,6),(2,3),(4,5)],[(0,2),(1,4),(3,6)],[(0,1),(2,5),(3,4)],[(1,2),(4,6)],[(2,3),(4,5)],[(1,2),(3,4),(5,6)]],
    [[(0,2),(1,3),(4,6),(5,7)],[(0,4),(1,5),(2,6),(3,7)],[(0,1),(2,3),(4,5),(6,7)],[(2,4),(3,5)],[(1,4),(3,6)],[(1,2),(3,4),(5,6)]],
    [[(0,3),(1,7),(2,5),(4,8)],[(0,7),(2,4),(3,8),(5,6)],[(0,2),(1,3),(4,5),(7,8)],[(1,4),(3,6),(5,7)],[(0,1),(2,4),(3,5),(6,8)],[(2,3),(4,5),(6,7)],[(1,2),(3,4),(5,6)]],
    [[(0,8),(1,9),(2,7),(3,5),(4,6)],[(0,2),(1,4),(5,8),(7,9)],[(0,3),(2,4),(5,7),(6,9)],[(0,1),(3,6),(8,9)],[(1,5),(2,3),(4,8),(6,7)],[(1,2),(3,5),(4,6),(7,8)],[(2,3),(4,5),(6,7)],[(3,4),(5,6)]],
    [[(0,9),(1,6),(2,4),(3,7),(5,8)],[(0,1),(3,5),(4,10),(6,9),(7,8)],[(1,3),(2,5),(4,7),(8,10)],[(0,4),(1,2),(3,7),(5,9),(6,8)],[(0,1),(2,6),(4,5),(7,8),(9,10)],[(2,4),(3,6),(5,7),(8,9)],[(1,2),(3,4),(5,6),(7,8)],[(2,3),(4,5),(6,7)]],
    [[(0,8),(1,7),(2,6),(3,11),(4,10),(5,9)],[(0,1),(2,5),(3,4),(6,9),(7,8),(10,11)],[(0,2),(1,6),(5,10),(9,11)],[(0,3),(1,2),(4,6),(5,7),(8,11),(9,10)],[(1,4),(3,5),(6,8),(7,10)],[(1,3),(2,5),(6,9),(8,10)],[(2,3),(4,5),(6,7),(8,9)],[(4,6),(5,7)],[(3,4),(5,6),(7,8)]],
    [[(0,12),(1,10),(2,9),(3,7),(5,11),(6,8)],[(1,6),(2,3),(4,11),(7,9),(8,10)],[(0,4),(1,2),(3,6),(7,8),(9,10),(11,12)],[(4,6),(5,9),(8,11),(10,12)],[(0,5),(3,8),(4,7),(6,11),(9,10)],[(0,1),(2,5),(6,9),(7,8),(10,11)],[(1,3),(2,4),(5,6),(9,10)],[(1,2),(3,4),(5,7),(6,8)],[(2,3),(4,5),(6,7),(8,9)],[(3,4),(5,6)]],
    [[(0,1),(2,3),(4,5),(6,7),(8,9),(10,11),(12,13)],[(0,2),(1,3),(4,8),(5,9),(10,12),(11,13)],[(0,4),(1,2),(3,7),(5,8),(6,10),(9,13),(11,12)],[(0,6),(1,5),(3,9),(4,10),(7,13),(8,12)],[(2,10),(3,11),(4,6),(7,9)],[(1,3),(2,8),(5,11),(6,7),(10,12)],[(1,4),(2,6),(3,5),(7,11),(8,10),(9,12)],[(2,4),(3,6),(5,8),(7,10),(9,11)],[(3,4),(5,6),(7,8),(9,10)],[(6,7)]],
    [[(1,2),(3,10),(4,14),(5,8),(6,13),(7,12),(9,11)],[(0,14),(1,5),(2,8),(3,7),(6,9),(10,12),(11,13)],[(0,7),(1,6),(2,9),(4,10),(5,11),(8,13),(12,14)],[(0,6),(2,4),(3,5),(7,11),(8,10),(9,12),(13,14)],[(0,3),(1,2),(4,7),(5,9),(6,8),(10,11),(12,13)],[(0,1),(2,3),(4,6),(7,9),(10,12),(11,13)],[(1,2),(3,5),(8,10),(11,12)],[(3,4),(5,6),(7,8),(9,10)],[(2,3),(4,5),(6,7),(8,9),(10,11)],[(5,6),(7,8)]],
    [[(0,13),(1,12),(2,15),(3,14),(4,8),(5,6),(7,11),(9,10)],[(0,5),(1,7),(2,9),(3,4),(6,13),(8,14),(10,15),(11,12)],[(0,1),(2,3),(4,5),(6,8),(7,9),(10,11),(12,13),(14,15)],[(0,2),(1,3),(4,10),(5,11),(6,7),(8,9),(12,14),(13,15)],[(1,2),(3,12),(4,6),(5,7),(8,10),(9,11),(13,14)],[(1,4),(2,6),(5,8),(7,10),(9,13),(11,14)],[(2,4),(3,6),(9,12),(11,13)],[(3,5),(6,8),(7,9),(10,12)],[(3,4),(5,6),(7,8),(9,10),(11,12)],[(6,7),(8,9)]],
]

def gen_vars():
    out = f"    public static int[] indices = new int[{len(networks) - 1}];\n\n"
    out += "    public static int size;\n"
    out += "    private static long tmp;\n"
    for i in range(len(networks)):
        out += f"    private static long a{i};\n"
    return out

def gen_networks():
    index_bits = math.ceil(math.log2(len(networks)))
    index_mask = (1 << index_bits) - 1
    out = ""
    for i in range(2, len(networks)):
        out += f"\n    private static void sort{i}(int[] array) {{\n"
        out += f"        size = {i};\n"
        for j in range(i):
            out += f"        a{j} = (array[{j}] << {index_bits}) | {j};\n"

        for j in range(len(networks[i])):
            for k in range(len(networks[i][j])):
                out += f"        if (a{networks[i][j][k][0]} > a{networks[i][j][k][1]}) {{\n"
                out += f"            tmp = a{networks[i][j][k][0]};\n"
                out += f"            a{networks[i][j][k][0]} = a{networks[i][j][k][1]};\n"
                out += f"            a{networks[i][j][k][1]} = tmp;\n"
                out += "        }\n"

        # for j in range(i):
        #     out += f"        array[{j}] = a{j};\n"
        for j in range(i):
            out += f"        indices[{j}] = (int)(a{j} & {index_mask});\n"
        out += "    }\n"
    return out

def gen_dist_sort():
    out = f"""    public static void distSort(RobotInfo[] robots) {{\n"""
    out += f"        MapLocation currLoc = rc.getLocation();\n"
    out += f"        int[] distances = new int[Math.min(robots.length,{len(networks) - 1})];\n"
    out += f"""        switch (robots.length) {{\n"""
    out += f"""            default:\n"""
    for i in range(len(networks) - 1, 0, -1):
        out += f"""            case {i}: distances[{i-1}] = currLoc.distanceSquaredTo(robots[{i-1}].getLocation());
"""
    out += f"""                break;\n"""
    out += f"""            case 0: size = 0; return;\n"""
    out += """        }
        sort(distances);
    }"""
    return out

def gen_sort():
    out = ""
    out += """    public static void sort(int[] array) {
        switch (array.length) {
            case 0: size = 0; return;
            case 1: size = 1; indices[0] = 0; return;
"""
    for i in range(2, len(networks)):
        out += f"""            case {i}:
                sort{i}(array);
                break;
"""
    out += f"""            default:
                sort{len(networks) - 1}(array);
                break;
"""
    out += """
        }
    }
"""
    return out

if __name__ == '__main__':
    package_name = len(sys.argv) > 1 and sys.argv[1] or 'MPWorking'
    template_file = Path('./FastSortTemplate.java')
    out_file = Path('./src/') / package_name / 'fast/FastSort.java'
    with open(template_file, 'r') as t:
        with open(out_file, 'w') as f:
            for line in t:
                if 'package examplefuncsplayer.fast;' in line:
                    f.write(f"package {package_name}.fast;\n")
                elif '// MAIN SORT' in line:
                    f.write(gen_sort())
                elif '// DISTANCE SORT' in line:
                    f.write(gen_dist_sort())
                elif '// VARS' in line:
                    f.write(gen_vars())
                elif '// SORTING NETWORKS' in line:
                    f.write(gen_networks())
                else:
                    f.write(line)
