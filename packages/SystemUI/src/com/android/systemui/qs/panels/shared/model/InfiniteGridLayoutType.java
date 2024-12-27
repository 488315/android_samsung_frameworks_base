package com.android.systemui.qs.panels.shared.model;

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
