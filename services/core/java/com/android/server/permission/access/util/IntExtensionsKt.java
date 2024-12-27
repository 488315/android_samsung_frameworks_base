package com.android.server.permission.access.util;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class IntExtensionsKt {
    public static final boolean hasAnyBit(int i, int i2) {
        return (i & i2) != 0;
    }

    public static final boolean hasBits(int i, int i2) {
        return (i & i2) == i2;
    }
}
