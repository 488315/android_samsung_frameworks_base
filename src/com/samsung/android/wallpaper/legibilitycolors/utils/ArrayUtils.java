package com.samsung.android.wallpaper.legibilitycolors.utils;

/* loaded from: classes6.dex */
public class ArrayUtils {
    public static void arrayChangePos(Object[] objArr, int i, int i2) {
        Object obj = objArr[i];
        objArr[i] = objArr[i2];
        objArr[i2] = obj;
        if (i > i2) {
            int i3 = i2 + 1;
            while (i > i3) {
                int i4 = i - 1;
                Object obj2 = objArr[i4];
                objArr[i4] = objArr[i];
                objArr[i] = obj2;
                i--;
            }
            return;
        }
        if (i < i2) {
            int i5 = i2 - 1;
            while (i < i5) {
                int i6 = i + 1;
                Object obj3 = objArr[i6];
                objArr[i6] = objArr[i];
                objArr[i] = obj3;
                i = i6;
            }
        }
    }
}
