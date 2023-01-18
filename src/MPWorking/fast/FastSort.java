package MPWorking.fast;

import battlecode.common.*;
import MPWorking.Debug;

public class FastSort {

    public static RobotController rc;

    public static void init(RobotController r) {
        rc = r;
    }

    // public static void main(String[] args) {
    //     int[] a = new int[]{4, 2, 3, 5, 0, 15, 20};
    //     sort(a);
    //     for(int i = 0; i < FastSort.size; i++) {
    //         System.out.println("" + i + ": " + a[FastSort.indices[i]] + "\n");
    //     }
    // }

    public static void distSort(RobotInfo[] robots) {
        MapLocation currLoc = rc.getLocation();
        int[] distances = new int[Math.min(robots.length,16)];
        switch (robots.length) {
            default:
            case 16: distances[15] = currLoc.distanceSquaredTo(robots[15].getLocation());
            case 15: distances[14] = currLoc.distanceSquaredTo(robots[14].getLocation());
            case 14: distances[13] = currLoc.distanceSquaredTo(robots[13].getLocation());
            case 13: distances[12] = currLoc.distanceSquaredTo(robots[12].getLocation());
            case 12: distances[11] = currLoc.distanceSquaredTo(robots[11].getLocation());
            case 11: distances[10] = currLoc.distanceSquaredTo(robots[10].getLocation());
            case 10: distances[9] = currLoc.distanceSquaredTo(robots[9].getLocation());
            case 9: distances[8] = currLoc.distanceSquaredTo(robots[8].getLocation());
            case 8: distances[7] = currLoc.distanceSquaredTo(robots[7].getLocation());
            case 7: distances[6] = currLoc.distanceSquaredTo(robots[6].getLocation());
            case 6: distances[5] = currLoc.distanceSquaredTo(robots[5].getLocation());
            case 5: distances[4] = currLoc.distanceSquaredTo(robots[4].getLocation());
            case 4: distances[3] = currLoc.distanceSquaredTo(robots[3].getLocation());
            case 3: distances[2] = currLoc.distanceSquaredTo(robots[2].getLocation());
            case 2: distances[1] = currLoc.distanceSquaredTo(robots[1].getLocation());
            case 1: distances[0] = currLoc.distanceSquaredTo(robots[0].getLocation());
                break;
            case 0: size = 0; return;
        }
        sort(distances);
    }

    // Sorts array in increasing order, placing the indices of the sorted array in indices
    // See the commented out main method for an example of how to use this class
    // WARNING: Only sorts arrays of nonnegative integers
    // WARNING: If the array has more than 16 elements, only the first 16 will be sorted.
    public static void sort(int[] array) {
        switch (array.length) {
            case 0: size = 0; return;
            case 1: size = 1; indices[0] = 0; return;
            case 2:
                sort2(array);
                break;
            case 3:
                sort3(array);
                break;
            case 4:
                sort4(array);
                break;
            case 5:
                sort5(array);
                break;
            case 6:
                sort6(array);
                break;
            case 7:
                sort7(array);
                break;
            case 8:
                sort8(array);
                break;
            case 9:
                sort9(array);
                break;
            case 10:
                sort10(array);
                break;
            case 11:
                sort11(array);
                break;
            case 12:
                sort12(array);
                break;
            case 13:
                sort13(array);
                break;
            case 14:
                sort14(array);
                break;
            case 15:
                sort15(array);
                break;
            case 16:
                sort16(array);
                break;
            default:
                sort16(array);
                break;

        }
    }

    public static int[] indices = new int[16];

    public static int size;
    private static long tmp;
    private static long a0;
    private static long a1;
    private static long a2;
    private static long a3;
    private static long a4;
    private static long a5;
    private static long a6;
    private static long a7;
    private static long a8;
    private static long a9;
    private static long a10;
    private static long a11;
    private static long a12;
    private static long a13;
    private static long a14;
    private static long a15;
    private static long a16;


    private static void sort2(int[] array) {
        size = 2;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
    }

    private static void sort3(int[] array) {
        size = 3;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
    }

    private static void sort4(int[] array) {
        size = 4;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
    }

    private static void sort5(int[] array) {
        size = 5;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        if (a0 > a3) {
            tmp = a0;
            a0 = a3;
            a3 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
    }

    private static void sort6(int[] array) {
        size = 6;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        if (a0 > a5) {
            tmp = a0;
            a0 = a5;
            a5 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a0 > a3) {
            tmp = a0;
            a0 = a3;
            a3 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
    }

    private static void sort7(int[] array) {
        size = 7;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        if (a0 > a6) {
            tmp = a0;
            a0 = a6;
            a6 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
    }

    private static void sort8(int[] array) {
        size = 8;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a0 > a4) {
            tmp = a0;
            a0 = a4;
            a4 = tmp;
        }
        if (a1 > a5) {
            tmp = a1;
            a1 = a5;
            a5 = tmp;
        }
        if (a2 > a6) {
            tmp = a2;
            a2 = a6;
            a6 = tmp;
        }
        if (a3 > a7) {
            tmp = a3;
            a3 = a7;
            a7 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
    }

    private static void sort9(int[] array) {
        size = 9;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        if (a0 > a3) {
            tmp = a0;
            a0 = a3;
            a3 = tmp;
        }
        if (a1 > a7) {
            tmp = a1;
            a1 = a7;
            a7 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a4 > a8) {
            tmp = a4;
            a4 = a8;
            a8 = tmp;
        }
        if (a0 > a7) {
            tmp = a0;
            a0 = a7;
            a7 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a8) {
            tmp = a3;
            a3 = a8;
            a8 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
    }

    private static void sort10(int[] array) {
        size = 10;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        if (a0 > a8) {
            tmp = a0;
            a0 = a8;
            a8 = tmp;
        }
        if (a1 > a9) {
            tmp = a1;
            a1 = a9;
            a9 = tmp;
        }
        if (a2 > a7) {
            tmp = a2;
            a2 = a7;
            a7 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a5 > a8) {
            tmp = a5;
            a5 = a8;
            a8 = tmp;
        }
        if (a7 > a9) {
            tmp = a7;
            a7 = a9;
            a9 = tmp;
        }
        if (a0 > a3) {
            tmp = a0;
            a0 = a3;
            a3 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a6 > a9) {
            tmp = a6;
            a6 = a9;
            a9 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a1 > a5) {
            tmp = a1;
            a1 = a5;
            a5 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a8) {
            tmp = a4;
            a4 = a8;
            a8 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
    }

    private static void sort11(int[] array) {
        size = 11;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        a10 = (array[10] << 5) | 10;
        if (a0 > a9) {
            tmp = a0;
            a0 = a9;
            a9 = tmp;
        }
        if (a1 > a6) {
            tmp = a1;
            a1 = a6;
            a6 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a7) {
            tmp = a3;
            a3 = a7;
            a7 = tmp;
        }
        if (a5 > a8) {
            tmp = a5;
            a5 = a8;
            a8 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a4 > a10) {
            tmp = a4;
            a4 = a10;
            a10 = tmp;
        }
        if (a6 > a9) {
            tmp = a6;
            a6 = a9;
            a9 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a4 > a7) {
            tmp = a4;
            a4 = a7;
            a7 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a0 > a4) {
            tmp = a0;
            a0 = a4;
            a4 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a7) {
            tmp = a3;
            a3 = a7;
            a7 = tmp;
        }
        if (a5 > a9) {
            tmp = a5;
            a5 = a9;
            a9 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a6) {
            tmp = a2;
            a2 = a6;
            a6 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
        indices[10] = (int)(a10 & 31);
    }

    private static void sort12(int[] array) {
        size = 12;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        a10 = (array[10] << 5) | 10;
        a11 = (array[11] << 5) | 11;
        if (a0 > a8) {
            tmp = a0;
            a0 = a8;
            a8 = tmp;
        }
        if (a1 > a7) {
            tmp = a1;
            a1 = a7;
            a7 = tmp;
        }
        if (a2 > a6) {
            tmp = a2;
            a2 = a6;
            a6 = tmp;
        }
        if (a3 > a11) {
            tmp = a3;
            a3 = a11;
            a11 = tmp;
        }
        if (a4 > a10) {
            tmp = a4;
            a4 = a10;
            a10 = tmp;
        }
        if (a5 > a9) {
            tmp = a5;
            a5 = a9;
            a9 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a6 > a9) {
            tmp = a6;
            a6 = a9;
            a9 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a10 > a11) {
            tmp = a10;
            a10 = a11;
            a11 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a6) {
            tmp = a1;
            a1 = a6;
            a6 = tmp;
        }
        if (a5 > a10) {
            tmp = a5;
            a5 = a10;
            a10 = tmp;
        }
        if (a9 > a11) {
            tmp = a9;
            a9 = a11;
            a11 = tmp;
        }
        if (a0 > a3) {
            tmp = a0;
            a0 = a3;
            a3 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a8 > a11) {
            tmp = a8;
            a8 = a11;
            a11 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a7 > a10) {
            tmp = a7;
            a7 = a10;
            a10 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a6 > a9) {
            tmp = a6;
            a6 = a9;
            a9 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
        indices[10] = (int)(a10 & 31);
        indices[11] = (int)(a11 & 31);
    }

    private static void sort13(int[] array) {
        size = 13;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        a10 = (array[10] << 5) | 10;
        a11 = (array[11] << 5) | 11;
        a12 = (array[12] << 5) | 12;
        if (a0 > a12) {
            tmp = a0;
            a0 = a12;
            a12 = tmp;
        }
        if (a1 > a10) {
            tmp = a1;
            a1 = a10;
            a10 = tmp;
        }
        if (a2 > a9) {
            tmp = a2;
            a2 = a9;
            a9 = tmp;
        }
        if (a3 > a7) {
            tmp = a3;
            a3 = a7;
            a7 = tmp;
        }
        if (a5 > a11) {
            tmp = a5;
            a5 = a11;
            a11 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a1 > a6) {
            tmp = a1;
            a1 = a6;
            a6 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a11) {
            tmp = a4;
            a4 = a11;
            a11 = tmp;
        }
        if (a7 > a9) {
            tmp = a7;
            a7 = a9;
            a9 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a0 > a4) {
            tmp = a0;
            a0 = a4;
            a4 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a11 > a12) {
            tmp = a11;
            a11 = a12;
            a12 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a5 > a9) {
            tmp = a5;
            a5 = a9;
            a9 = tmp;
        }
        if (a8 > a11) {
            tmp = a8;
            a8 = a11;
            a11 = tmp;
        }
        if (a10 > a12) {
            tmp = a10;
            a10 = a12;
            a12 = tmp;
        }
        if (a0 > a5) {
            tmp = a0;
            a0 = a5;
            a5 = tmp;
        }
        if (a3 > a8) {
            tmp = a3;
            a3 = a8;
            a8 = tmp;
        }
        if (a4 > a7) {
            tmp = a4;
            a4 = a7;
            a7 = tmp;
        }
        if (a6 > a11) {
            tmp = a6;
            a6 = a11;
            a11 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a5) {
            tmp = a2;
            a2 = a5;
            a5 = tmp;
        }
        if (a6 > a9) {
            tmp = a6;
            a6 = a9;
            a9 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a10 > a11) {
            tmp = a10;
            a10 = a11;
            a11 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
        indices[10] = (int)(a10 & 31);
        indices[11] = (int)(a11 & 31);
        indices[12] = (int)(a12 & 31);
    }

    private static void sort14(int[] array) {
        size = 14;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        a10 = (array[10] << 5) | 10;
        a11 = (array[11] << 5) | 11;
        a12 = (array[12] << 5) | 12;
        a13 = (array[13] << 5) | 13;
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a10 > a11) {
            tmp = a10;
            a10 = a11;
            a11 = tmp;
        }
        if (a12 > a13) {
            tmp = a12;
            a12 = a13;
            a13 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a4 > a8) {
            tmp = a4;
            a4 = a8;
            a8 = tmp;
        }
        if (a5 > a9) {
            tmp = a5;
            a5 = a9;
            a9 = tmp;
        }
        if (a10 > a12) {
            tmp = a10;
            a10 = a12;
            a12 = tmp;
        }
        if (a11 > a13) {
            tmp = a11;
            a11 = a13;
            a13 = tmp;
        }
        if (a0 > a4) {
            tmp = a0;
            a0 = a4;
            a4 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a7) {
            tmp = a3;
            a3 = a7;
            a7 = tmp;
        }
        if (a5 > a8) {
            tmp = a5;
            a5 = a8;
            a8 = tmp;
        }
        if (a6 > a10) {
            tmp = a6;
            a6 = a10;
            a10 = tmp;
        }
        if (a9 > a13) {
            tmp = a9;
            a9 = a13;
            a13 = tmp;
        }
        if (a11 > a12) {
            tmp = a11;
            a11 = a12;
            a12 = tmp;
        }
        if (a0 > a6) {
            tmp = a0;
            a0 = a6;
            a6 = tmp;
        }
        if (a1 > a5) {
            tmp = a1;
            a1 = a5;
            a5 = tmp;
        }
        if (a3 > a9) {
            tmp = a3;
            a3 = a9;
            a9 = tmp;
        }
        if (a4 > a10) {
            tmp = a4;
            a4 = a10;
            a10 = tmp;
        }
        if (a7 > a13) {
            tmp = a7;
            a7 = a13;
            a13 = tmp;
        }
        if (a8 > a12) {
            tmp = a8;
            a8 = a12;
            a12 = tmp;
        }
        if (a2 > a10) {
            tmp = a2;
            a2 = a10;
            a10 = tmp;
        }
        if (a3 > a11) {
            tmp = a3;
            a3 = a11;
            a11 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a7 > a9) {
            tmp = a7;
            a7 = a9;
            a9 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a2 > a8) {
            tmp = a2;
            a2 = a8;
            a8 = tmp;
        }
        if (a5 > a11) {
            tmp = a5;
            a5 = a11;
            a11 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a10 > a12) {
            tmp = a10;
            a10 = a12;
            a12 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a2 > a6) {
            tmp = a2;
            a2 = a6;
            a6 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a7 > a11) {
            tmp = a7;
            a7 = a11;
            a11 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a9 > a12) {
            tmp = a9;
            a9 = a12;
            a12 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a5 > a8) {
            tmp = a5;
            a5 = a8;
            a8 = tmp;
        }
        if (a7 > a10) {
            tmp = a7;
            a7 = a10;
            a10 = tmp;
        }
        if (a9 > a11) {
            tmp = a9;
            a9 = a11;
            a11 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
        indices[10] = (int)(a10 & 31);
        indices[11] = (int)(a11 & 31);
        indices[12] = (int)(a12 & 31);
        indices[13] = (int)(a13 & 31);
    }

    private static void sort15(int[] array) {
        size = 15;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        a10 = (array[10] << 5) | 10;
        a11 = (array[11] << 5) | 11;
        a12 = (array[12] << 5) | 12;
        a13 = (array[13] << 5) | 13;
        a14 = (array[14] << 5) | 14;
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a10) {
            tmp = a3;
            a3 = a10;
            a10 = tmp;
        }
        if (a4 > a14) {
            tmp = a4;
            a4 = a14;
            a14 = tmp;
        }
        if (a5 > a8) {
            tmp = a5;
            a5 = a8;
            a8 = tmp;
        }
        if (a6 > a13) {
            tmp = a6;
            a6 = a13;
            a13 = tmp;
        }
        if (a7 > a12) {
            tmp = a7;
            a7 = a12;
            a12 = tmp;
        }
        if (a9 > a11) {
            tmp = a9;
            a9 = a11;
            a11 = tmp;
        }
        if (a0 > a14) {
            tmp = a0;
            a0 = a14;
            a14 = tmp;
        }
        if (a1 > a5) {
            tmp = a1;
            a1 = a5;
            a5 = tmp;
        }
        if (a2 > a8) {
            tmp = a2;
            a2 = a8;
            a8 = tmp;
        }
        if (a3 > a7) {
            tmp = a3;
            a3 = a7;
            a7 = tmp;
        }
        if (a6 > a9) {
            tmp = a6;
            a6 = a9;
            a9 = tmp;
        }
        if (a10 > a12) {
            tmp = a10;
            a10 = a12;
            a12 = tmp;
        }
        if (a11 > a13) {
            tmp = a11;
            a11 = a13;
            a13 = tmp;
        }
        if (a0 > a7) {
            tmp = a0;
            a0 = a7;
            a7 = tmp;
        }
        if (a1 > a6) {
            tmp = a1;
            a1 = a6;
            a6 = tmp;
        }
        if (a2 > a9) {
            tmp = a2;
            a2 = a9;
            a9 = tmp;
        }
        if (a4 > a10) {
            tmp = a4;
            a4 = a10;
            a10 = tmp;
        }
        if (a5 > a11) {
            tmp = a5;
            a5 = a11;
            a11 = tmp;
        }
        if (a8 > a13) {
            tmp = a8;
            a8 = a13;
            a13 = tmp;
        }
        if (a12 > a14) {
            tmp = a12;
            a12 = a14;
            a14 = tmp;
        }
        if (a0 > a6) {
            tmp = a0;
            a0 = a6;
            a6 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a7 > a11) {
            tmp = a7;
            a7 = a11;
            a11 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a9 > a12) {
            tmp = a9;
            a9 = a12;
            a12 = tmp;
        }
        if (a13 > a14) {
            tmp = a13;
            a13 = a14;
            a14 = tmp;
        }
        if (a0 > a3) {
            tmp = a0;
            a0 = a3;
            a3 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a4 > a7) {
            tmp = a4;
            a4 = a7;
            a7 = tmp;
        }
        if (a5 > a9) {
            tmp = a5;
            a5 = a9;
            a9 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a10 > a11) {
            tmp = a10;
            a10 = a11;
            a11 = tmp;
        }
        if (a12 > a13) {
            tmp = a12;
            a12 = a13;
            a13 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a7 > a9) {
            tmp = a7;
            a7 = a9;
            a9 = tmp;
        }
        if (a10 > a12) {
            tmp = a10;
            a10 = a12;
            a12 = tmp;
        }
        if (a11 > a13) {
            tmp = a11;
            a11 = a13;
            a13 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a11 > a12) {
            tmp = a11;
            a11 = a12;
            a12 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a10 > a11) {
            tmp = a10;
            a10 = a11;
            a11 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
        indices[10] = (int)(a10 & 31);
        indices[11] = (int)(a11 & 31);
        indices[12] = (int)(a12 & 31);
        indices[13] = (int)(a13 & 31);
        indices[14] = (int)(a14 & 31);
    }

    private static void sort16(int[] array) {
        size = 16;
        a0 = (array[0] << 5) | 0;
        a1 = (array[1] << 5) | 1;
        a2 = (array[2] << 5) | 2;
        a3 = (array[3] << 5) | 3;
        a4 = (array[4] << 5) | 4;
        a5 = (array[5] << 5) | 5;
        a6 = (array[6] << 5) | 6;
        a7 = (array[7] << 5) | 7;
        a8 = (array[8] << 5) | 8;
        a9 = (array[9] << 5) | 9;
        a10 = (array[10] << 5) | 10;
        a11 = (array[11] << 5) | 11;
        a12 = (array[12] << 5) | 12;
        a13 = (array[13] << 5) | 13;
        a14 = (array[14] << 5) | 14;
        a15 = (array[15] << 5) | 15;
        if (a0 > a13) {
            tmp = a0;
            a0 = a13;
            a13 = tmp;
        }
        if (a1 > a12) {
            tmp = a1;
            a1 = a12;
            a12 = tmp;
        }
        if (a2 > a15) {
            tmp = a2;
            a2 = a15;
            a15 = tmp;
        }
        if (a3 > a14) {
            tmp = a3;
            a3 = a14;
            a14 = tmp;
        }
        if (a4 > a8) {
            tmp = a4;
            a4 = a8;
            a8 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a11) {
            tmp = a7;
            a7 = a11;
            a11 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a0 > a5) {
            tmp = a0;
            a0 = a5;
            a5 = tmp;
        }
        if (a1 > a7) {
            tmp = a1;
            a1 = a7;
            a7 = tmp;
        }
        if (a2 > a9) {
            tmp = a2;
            a2 = a9;
            a9 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a6 > a13) {
            tmp = a6;
            a6 = a13;
            a13 = tmp;
        }
        if (a8 > a14) {
            tmp = a8;
            a8 = a14;
            a14 = tmp;
        }
        if (a10 > a15) {
            tmp = a10;
            a10 = a15;
            a15 = tmp;
        }
        if (a11 > a12) {
            tmp = a11;
            a11 = a12;
            a12 = tmp;
        }
        if (a0 > a1) {
            tmp = a0;
            a0 = a1;
            a1 = tmp;
        }
        if (a2 > a3) {
            tmp = a2;
            a2 = a3;
            a3 = tmp;
        }
        if (a4 > a5) {
            tmp = a4;
            a4 = a5;
            a5 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a7 > a9) {
            tmp = a7;
            a7 = a9;
            a9 = tmp;
        }
        if (a10 > a11) {
            tmp = a10;
            a10 = a11;
            a11 = tmp;
        }
        if (a12 > a13) {
            tmp = a12;
            a12 = a13;
            a13 = tmp;
        }
        if (a14 > a15) {
            tmp = a14;
            a14 = a15;
            a15 = tmp;
        }
        if (a0 > a2) {
            tmp = a0;
            a0 = a2;
            a2 = tmp;
        }
        if (a1 > a3) {
            tmp = a1;
            a1 = a3;
            a3 = tmp;
        }
        if (a4 > a10) {
            tmp = a4;
            a4 = a10;
            a10 = tmp;
        }
        if (a5 > a11) {
            tmp = a5;
            a5 = a11;
            a11 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        if (a12 > a14) {
            tmp = a12;
            a12 = a14;
            a14 = tmp;
        }
        if (a13 > a15) {
            tmp = a13;
            a13 = a15;
            a15 = tmp;
        }
        if (a1 > a2) {
            tmp = a1;
            a1 = a2;
            a2 = tmp;
        }
        if (a3 > a12) {
            tmp = a3;
            a3 = a12;
            a12 = tmp;
        }
        if (a4 > a6) {
            tmp = a4;
            a4 = a6;
            a6 = tmp;
        }
        if (a5 > a7) {
            tmp = a5;
            a5 = a7;
            a7 = tmp;
        }
        if (a8 > a10) {
            tmp = a8;
            a8 = a10;
            a10 = tmp;
        }
        if (a9 > a11) {
            tmp = a9;
            a9 = a11;
            a11 = tmp;
        }
        if (a13 > a14) {
            tmp = a13;
            a13 = a14;
            a14 = tmp;
        }
        if (a1 > a4) {
            tmp = a1;
            a1 = a4;
            a4 = tmp;
        }
        if (a2 > a6) {
            tmp = a2;
            a2 = a6;
            a6 = tmp;
        }
        if (a5 > a8) {
            tmp = a5;
            a5 = a8;
            a8 = tmp;
        }
        if (a7 > a10) {
            tmp = a7;
            a7 = a10;
            a10 = tmp;
        }
        if (a9 > a13) {
            tmp = a9;
            a9 = a13;
            a13 = tmp;
        }
        if (a11 > a14) {
            tmp = a11;
            a11 = a14;
            a14 = tmp;
        }
        if (a2 > a4) {
            tmp = a2;
            a2 = a4;
            a4 = tmp;
        }
        if (a3 > a6) {
            tmp = a3;
            a3 = a6;
            a6 = tmp;
        }
        if (a9 > a12) {
            tmp = a9;
            a9 = a12;
            a12 = tmp;
        }
        if (a11 > a13) {
            tmp = a11;
            a11 = a13;
            a13 = tmp;
        }
        if (a3 > a5) {
            tmp = a3;
            a3 = a5;
            a5 = tmp;
        }
        if (a6 > a8) {
            tmp = a6;
            a6 = a8;
            a8 = tmp;
        }
        if (a7 > a9) {
            tmp = a7;
            a7 = a9;
            a9 = tmp;
        }
        if (a10 > a12) {
            tmp = a10;
            a10 = a12;
            a12 = tmp;
        }
        if (a3 > a4) {
            tmp = a3;
            a3 = a4;
            a4 = tmp;
        }
        if (a5 > a6) {
            tmp = a5;
            a5 = a6;
            a6 = tmp;
        }
        if (a7 > a8) {
            tmp = a7;
            a7 = a8;
            a8 = tmp;
        }
        if (a9 > a10) {
            tmp = a9;
            a9 = a10;
            a10 = tmp;
        }
        if (a11 > a12) {
            tmp = a11;
            a11 = a12;
            a12 = tmp;
        }
        if (a6 > a7) {
            tmp = a6;
            a6 = a7;
            a7 = tmp;
        }
        if (a8 > a9) {
            tmp = a8;
            a8 = a9;
            a9 = tmp;
        }
        indices[0] = (int)(a0 & 31);
        indices[1] = (int)(a1 & 31);
        indices[2] = (int)(a2 & 31);
        indices[3] = (int)(a3 & 31);
        indices[4] = (int)(a4 & 31);
        indices[5] = (int)(a5 & 31);
        indices[6] = (int)(a6 & 31);
        indices[7] = (int)(a7 & 31);
        indices[8] = (int)(a8 & 31);
        indices[9] = (int)(a9 & 31);
        indices[10] = (int)(a10 & 31);
        indices[11] = (int)(a11 & 31);
        indices[12] = (int)(a12 & 31);
        indices[13] = (int)(a13 & 31);
        indices[14] = (int)(a14 & 31);
        indices[15] = (int)(a15 & 31);
    }

}
