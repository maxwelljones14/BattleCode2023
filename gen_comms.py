from pathlib import Path
import sys

# Modified from smite's Battlecode2022 bot located here
# https://github.com/mvpatel2000/Battlecode2022/blob/main/scripts/generate_comms_handler.py

SCHEMA = {
    'our_HQ': {
        'slots': 4,
        'bits': {
            'flag': 1,
            'x_coord': 6,
            'y_coord': 6,
        }
    },
    'sector': {
        'slots': 100,
        'bits': {
            'islands': 1,
            'adamantium_flag': 1,
            'mana_flag': 1,
            # 'elixir_flag': 1,
            'control_status': 3,
        }
    },
    'combat_sector': {
        'slots': 8,
        'bits': {
            'claim_status': 1,
            'index': 7,
        }
    },
    'explore_sector': {
        'slots': 13,
        'bits': {
            'claim_status': 1,
            'index': 7,
        }
    },
    'mine_sector': {
        'slots': 20,
        'bits': {
            'index': 7,
        }
    },
    'symmetry': {
        'slots': 1,
        'bits': {
            'vertical': 1,
            'horizontal': 1,
            'rotational': 1,
        }
    }
}

def gen_constants():
    out = """"""
    for datatype in SCHEMA:
        out += f"""
    public final static int {datatype.upper()}_SLOTS = {SCHEMA[datatype]['slots']};"""
    return out+"\n"

def gen():
    out = """"""""
    bits_so_far = 0
    for datatype in SCHEMA:
        datatype_bits = sum(SCHEMA[datatype]['bits'].values())
        prefix_bits = 0

        for attribute in [*SCHEMA[datatype]['bits'], 'all']:
            if attribute == 'all':
                attribute_bits = datatype_bits
                prefix_bits = 0
            else:
                attribute_bits = SCHEMA[datatype]['bits'][attribute]

            # read function
            rets = []
            for idx in range(SCHEMA[datatype]['slots']):
                start_bit = bits_so_far + datatype_bits*idx + prefix_bits
                # we want to read attribute_bits starting from start_bit
                start_int = start_bit // 16
                rem = start_bit % 16
                end_int = (start_bit + attribute_bits - 1) // 16
                ret = ""
                if start_int == end_int:
                    bitstring = '1' * attribute_bits + '0' * (16 - attribute_bits - rem)
                    ret = f"(rc.readSharedArray({start_int}) & {int(bitstring, 2)}) >>> {(16 - attribute_bits - rem)}"
                else:
                    part1_bitstring = '1' * (16 - rem)
                    part2_bitstring = '1' * (attribute_bits + rem - 16) + '0' * (32 - attribute_bits - rem)
                    ret = f"((rc.readSharedArray({start_int}) & {int(part1_bitstring, 2)}) << {(attribute_bits + rem - 16)}) + ((rc.readSharedArray({end_int}) & {int(part2_bitstring, 2)}) >>> {(32 - attribute_bits - rem)})"
                rets.append(ret)
            
            if SCHEMA[datatype]['slots'] == 1:
                out += f"""
    public static int read{capitalize(datatype)}{capitalize(attribute)}() throws GameActionException {{
        return {rets[0]};
    }}
"""
            else:
                out += f"""
    public static int read{capitalize(datatype)}{capitalize(attribute)}(int idx) throws GameActionException {{
        switch (idx) {{"""
                for idx, ret in enumerate(rets):
                    out += f"""
            case {idx}:
                return {ret};"""
                out += f"""
            default:
                return -1;
        }}
    }}
"""

            # write function
            writes = []
            for idx in range(SCHEMA[datatype]['slots']):
                start_bit = bits_so_far + datatype_bits*idx + prefix_bits
                # we want to write attribute_bits starting from start_bit
                start_int = start_bit // 16
                rem = start_bit % 16
                end_int = (start_bit + attribute_bits - 1) // 16
                write = []
                if start_int == end_int:
                    bitstring = '1' * rem + '0' * attribute_bits + '1' * (16 - attribute_bits - rem)
                    write.append(f"rc.writeSharedArray({start_int}, (rc.readSharedArray({start_int}) & {int(bitstring, 2)}) | (value << {(16 - attribute_bits - rem)}))")
                else:
                    part1_bitstring = '1' * rem + '0' * (16 - rem)
                    part2_bitstring = '0' * (attribute_bits + rem - 16) + '1' * (32 - attribute_bits - rem)
                    value1_bitstring = '1' * (16 - rem) + '0' * (attribute_bits + rem - 16)
                    value2_bitstring = '1' * (attribute_bits + rem - 16)
                    write.append(f"rc.writeSharedArray({start_int}, (rc.readSharedArray({start_int}) & {int(part1_bitstring, 2)}) | ((value & {int(value1_bitstring, 2)}) >>> {(attribute_bits + rem - 16)}))")
                    write.append(f"rc.writeSharedArray({end_int}, (rc.readSharedArray({end_int}) & {int(part2_bitstring, 2)}) | ((value & {int(value2_bitstring, 2)}) << {(32 - attribute_bits - rem)}))")
                writes.append(write)
            
            if SCHEMA[datatype]['slots'] == 1:
                out += f"""
    public static void write{capitalize(datatype)}{capitalize(attribute)}(int value) throws GameActionException {{"""
                for w in writes[0]:
                    out += f"""
        {w};"""
                out += f"""
    }}
"""
            else:
                out += f"""
    public static void write{capitalize(datatype)}{capitalize(attribute)}(int idx, int value) throws GameActionException {{
        switch (idx) {{"""
                for idx, write in enumerate(writes):
                    out += f"""
            case {idx}:"""
                    for w in write:
                        out += f"""
                {w};"""
                    out += f"""
                break;"""
                out += f"""
        }}
    }}
"""

            # write buffer pool function
            writes = []
            for idx in range(SCHEMA[datatype]['slots']):
                start_bit = bits_so_far + datatype_bits*idx + prefix_bits
                # we want to write attribute_bits starting from start_bit
                start_int = start_bit // 16
                rem = start_bit % 16
                end_int = (start_bit + attribute_bits - 1) // 16
                write = []
                if start_int == end_int:
                    bitstring = '1' * rem + '0' * attribute_bits + '1' * (16 - attribute_bits - rem)
                    write.append(f"writeToBufferPool({start_int}, (bufferPool[{start_int}] & {int(bitstring, 2)}) | (value << {(16 - attribute_bits - rem)}))")
                else:
                    part1_bitstring = '1' * rem + '0' * (16 - rem)
                    part2_bitstring = '0' * (attribute_bits + rem - 16) + '1' * (32 - attribute_bits - rem)
                    value1_bitstring = '1' * (16 - rem) + '0' * (attribute_bits + rem - 16)
                    value2_bitstring = '1' * (attribute_bits + rem - 16)
                    write.append(f"writeToBufferPool({start_int}, (bufferPool[{start_int}] & {int(part1_bitstring, 2)}) | ((value & {int(value1_bitstring, 2)}) >>> {(attribute_bits + rem - 16)}))")
                    write.append(f"writeToBufferPool({end_int}, (bufferPool[{end_int}] & {int(part2_bitstring, 2)}) | ((value & {int(value2_bitstring, 2)}) << {(32 - attribute_bits - rem)}))")
                writes.append(write)
            
            if SCHEMA[datatype]['slots'] == 1:
                out += f"""
    public static void writeBP{capitalize(datatype)}{capitalize(attribute)}(int value) throws GameActionException {{"""
                for w in writes[0]:
                    out += f"""
        {w};"""
                out += f"""
    }}
"""
            else:
                out += f"""
    public static void writeBP{capitalize(datatype)}{capitalize(attribute)}(int idx, int value) throws GameActionException {{
        switch (idx) {{"""
                for idx, write in enumerate(writes):
                    out += f"""
            case {idx}:"""
                    for w in write:
                        out += f"""
                {w};"""
                    out += f"""
                break;"""
                out += f"""
        }}
    }}
"""

            prefix_bits += attribute_bits

        bits_so_far += datatype_bits * SCHEMA[datatype]['slots']
    # remove redundant shifts
    out = out.replace(" >>> 0", "")
    out = out.replace(" << 0", "")
    print("Total bit usage: " + str(bits_so_far))
    return out

# Inits control statuses to EMPTY = 0b000
def gen_sector_control_status_reset():
    # SPECIFIC TO 3 BIT CONTROL STATUS SCHEMA
    out = f"""
    public static void resetAllSectorControlStatus() throws GameActionException {{"""
    shmem = '1'*1024
    bits_so_far = 0
    for datatype in SCHEMA:
        datatype_bits = sum(SCHEMA[datatype]['bits'].values())
        prefix_bits = 0
        if datatype == 'sector':
            for attribute in SCHEMA[datatype]['bits']:
                attribute_bits = SCHEMA[datatype]['bits'][attribute]
                if attribute == 'control_status':
                    for idx in range(SCHEMA[datatype]['slots']):
                        start_bit = bits_so_far + datatype_bits * idx + prefix_bits
                        shmem = shmem[:start_bit] + '000' + shmem[start_bit+attribute_bits:]
                prefix_bits += attribute_bits
        bits_so_far += datatype_bits * SCHEMA[datatype]['slots']

    shmem = [shmem[16*i:16*i+16] for i in range(64)]
    for idx, word in enumerate(shmem):
        if word != '1'*16:
            out += f"""
        rc.writeSharedArray({idx}, rc.readSharedArray({idx}) & {int(word, 2)});"""
    out += f"""
    }}"""
    return out

def capitalize(s):
    return ''.join(x.capitalize() for x in s.split('_'))

# Inits index fields of sectors to UNDEFINED_SECTOR_IDX
# Inits claim_status fields of sectors to 0
def gen_init_sectors():
    shmem = '0'*1024
    out = """    public static void initPrioritySectors() throws GameActionException {"""
    bits_so_far = 0
    for datatype in SCHEMA:
        datatype_bits = sum(SCHEMA[datatype]['bits'].values())
        prefix_bits = 0

        if '_sector' in datatype:
            for attribute in SCHEMA[datatype]['bits']:
                attribute_bits = SCHEMA[datatype]['bits'][attribute]
                if attribute == 'index':
                    for idx in range(SCHEMA[datatype]['slots']):
                        start_bit = bits_so_far + datatype_bits * idx + prefix_bits
                        shmem = shmem[:start_bit] + '1' * attribute_bits + shmem[start_bit+attribute_bits:]
                prefix_bits += attribute_bits
        bits_so_far += datatype_bits * SCHEMA[datatype]['slots']
    
    shmem = [shmem[16*i:16*i+16] for i in range(64)]
    for idx, word in enumerate(shmem):
        if word != '0'*16:
            out += f"""
        rc.writeSharedArray({idx}, {int(word, 2)});"""
    out += f"""
    }}
"""
    return out

if __name__ == '__main__':
    # Check num bits
    total_bits = sum(sum(SCHEMA[datatype]['bits'].values()) for datatype in SCHEMA)
    if total_bits > 1024:
        raise Exception("Too many bits")

    package_name = len(sys.argv) > 1 and sys.argv[1] or 'MPWorking'
    template_file = Path('./CommsTemplate.java')
    out_file = Path('./src/') / package_name / 'Comms.java'
    with open(template_file, 'r') as t:
        with open(out_file, 'w') as f:
            for line in t:
                if 'package examplefuncsplayer;' in line:
                    f.write(f"package {package_name};\n")
                elif '// MAIN READ AND WRITE METHODS' in line:
                    f.write(gen())
                elif '// CONSTS' in line:
                    f.write(gen_constants())
                elif '// PRIORITY SECTOR INIT' in line:
                    f.write(gen_init_sectors())
                elif '// SECTOR CONTROL STATUS RESET' in line:
                    f.write(gen_sector_control_status_reset())
                else:
                    f.write(line)
