package com.android.server.permission.access.util;

public abstract class IntExtensionsKt {
    public static final boolean hasAnyBit(int i, int i2) {
        return (i & i2) != 0;
    }

    public static final boolean hasBits(int i, int i2) {
        return (i & i2) == i2;
    }
}
