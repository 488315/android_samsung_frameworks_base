package com.android.systemui.qs.panels.shared.model;

public final class StretchedGridLayoutType implements GridLayoutType {
    public static final StretchedGridLayoutType INSTANCE = new StretchedGridLayoutType();

    private StretchedGridLayoutType() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof StretchedGridLayoutType);
    }

    public final int hashCode() {
        return 170165362;
    }

    public final String toString() {
        return "StretchedGridLayoutType";
    }
}
