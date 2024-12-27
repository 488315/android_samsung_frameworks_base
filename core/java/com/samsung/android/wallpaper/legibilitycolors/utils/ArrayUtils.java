package com.samsung.android.wallpaper.legibilitycolors.utils;

public class ArrayUtils {
    public static void arrayChangePos(Object[] array, int src, int dst) {
        Object buf = array[src];
        array[src] = array[dst];
        array[dst] = buf;
        if (src > dst) {
            int size = dst + 1;
            for (int i = src; i > size; i--) {
                Object buf2 = array[i - 1];
                array[i - 1] = array[i];
                array[i] = buf2;
            }
            return;
        }
        if (src < dst) {
            int size2 = dst - 1;
            for (int i2 = src; i2 < size2; i2++) {
                Object buf3 = array[i2 + 1];
                array[i2 + 1] = array[i2];
                array[i2] = buf3;
            }
        }
    }
}
