package com.samsung.android.sume.core.types;

/* loaded from: classes6.dex */
public enum LoadType {
    NONE,
    LAZY,
    INSTANT,
    CACHED;

    boolean isLazy() {
        return this == LAZY;
    }

    boolean isInstant() {
        return this == INSTANT;
    }

    boolean isCached() {
        return this == CACHED;
    }
}
