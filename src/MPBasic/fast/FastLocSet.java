package MPBasic.fast;

import battlecode.common.*;

public class FastLocSet {
    public StringBuilder keys;
    public int maxlen;
    public int size;

    public FastLocSet() {
        this(100);
    }

    public FastLocSet(int len) {
        keys = new StringBuilder();
        size = 0;
        maxlen = len;
    }

    public int size() {
        return size;
    }

    private String locToStr(MapLocation loc) {
        return "^" + (char) (loc.x) + (char) (loc.y);
    }

    public void add(MapLocation loc) {
        String key = locToStr(loc);
        if (keys.indexOf(key) == -1) {
            keys.append(key);
            size++;
        }
    }

    public void add(int x, int y) {
        String key = "^" + (char) x + (char) y;
        if (keys.indexOf(key) == -1) {
            keys.append(key);
            size++;
        }
    }

    public void remove(MapLocation loc) {
        String key = locToStr(loc);
        int index;
        if ((index = keys.indexOf(key)) >= 0) {
            keys.delete(index, index + 3);
            size--;
        }
    }

    public void remove(int x, int y) {
        String key = "^" + (char) x + (char) y;
        int index;
        if ((index = keys.indexOf(key)) >= 0) {
            keys.delete(index, index + 3);
            size--;
        }
    }

    public boolean contains(MapLocation loc) {
        return keys.indexOf(locToStr(loc)) >= 0;
    }

    public boolean contains(int x, int y) {
        return keys.indexOf("^" + (char) x + (char) y) >= 0;
    }

    public void clear() {
        size = 0;
        keys = new StringBuilder();
    }

    public void replace(String newSet) {
        keys.replace(0, keys.length(), newSet);
        size = newSet.length() / 3;
    }
}
