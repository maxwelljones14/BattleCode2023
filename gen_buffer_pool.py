#! /usr/bin/env python3

init = """    public static void initBufferPool() throws GameActionException {
        dirtyFlags = new boolean[64];

"""

for i in range(64):
    init += f"        bufferPool[{i}] = rc.readSharedArray({i});\n"

init += """    }
"""

init += """
    public static void flushBufferPool() throws GameActionException {
"""

for i in range(64):
    init += f"        if (dirtyFlags[{i}]) \n            rc.writeSharedArray({i}, bufferPool[{i}]);\n"

init += """    }
"""

print(init)
