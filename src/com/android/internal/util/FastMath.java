package com.android.internal.util;

/* loaded from: classes4.dex */
public class FastMath {
    public static int round(float f) {
        return (int) ((((long) (f * 1.6777216E7f)) + 8388608) >> 24);
    }
}
