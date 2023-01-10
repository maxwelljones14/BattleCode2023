#! /usr/bin/env python3

# Copies from src/MPWorking into src/<package_name> and replaces all instances of MPWorking with <package_name>
import sys
import shutil, errno
import os

def copyanything(src, dst):
    try:
        shutil.copytree(src, dst)
    except OSError as exc: # python >2.5
        if exc.errno in (errno.ENOTDIR, errno.EINVAL):
            shutil.copy(src, dst)
        else: raise

def usage():
    print("Usage: snap.py <package_name>")

if __name__ == '__main__':
    num_args = len(sys.argv)
    if num_args != 2:
        usage()
        quit()

    snapshot_name = sys.argv[1]

    if snapshot_name[:2] != "MP":
        print("Invalid package name:", snapshot_name)
        print("Package name must start with MP")
        quit()

    working_name = "MPWorking"
    src_dir = "src"

    working_src = src_dir + "/" + working_name
    snapshot_dst = src_dir + "/" + snapshot_name

    copyanything(working_src, snapshot_dst)

    for r, d, f in os.walk(snapshot_dst):
        for file in f:
            file_path = os.path.join(r, file)
            # Read in the file
            with open(file_path, 'r') as file :
                filedata = file.read()

            # Replace the target string
            filedata = filedata.replace(working_name, snapshot_name)

            # Write the file out again
            with open(file_path, 'w') as new_file:
                new_file.write(filedata)
