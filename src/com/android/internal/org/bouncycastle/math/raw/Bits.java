package com.android.internal.org.bouncycastle.math.raw;

/* loaded from: classes5.dex */
public abstract class Bits {
    public static int bitPermuteStep(int i, int i2, int i3) {
        int i4 = i2 & ((i >>> i3) ^ i);
        return i ^ (i4 ^ (i4 << i3));
    }

    public static long bitPermuteStep(long j, long j2, int i) {
        long j3 = j2 & ((j >>> i) ^ j);
        return j ^ (j3 ^ (j3 << i));
    }

    public static int bitPermuteStepSimple(int i, int i2, int i3) {
        return ((i >>> i3) & i2) | ((i & i2) << i3);
    }

    public static long bitPermuteStepSimple(long j, long j2, int i) {
        return ((j >>> i) & j2) | ((j & j2) << i);
    }
}
