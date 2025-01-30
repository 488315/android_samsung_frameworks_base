package com.android.internal.util;

/* loaded from: classes5.dex */
public class IntPair {
    private IntPair() {
    }

    /* renamed from: of */
    public static long m225of(int first, int second) {
        return (first << 32) | (second & 4294967295L);
    }

    public static int first(long intPair) {
        return (int) (intPair >> 32);
    }

    public static int second(long intPair) {
        return (int) intPair;
    }
}
