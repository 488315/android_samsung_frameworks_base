package com.android.systemui.qs.panels.shared.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InfiniteGridLayoutType implements GridLayoutType {
    public static final InfiniteGridLayoutType INSTANCE = new InfiniteGridLayoutType();

    private InfiniteGridLayoutType() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof InfiniteGridLayoutType);
    }

    public final int hashCode() {
        return 825513114;
    }

    public final String toString() {
        return "InfiniteGridLayoutType";
    }
}
