package com.android.systemui.qs.panels.shared.model;

public final class PartitionedGridLayoutType implements GridLayoutType {
    public static final PartitionedGridLayoutType INSTANCE = new PartitionedGridLayoutType();

    private PartitionedGridLayoutType() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof PartitionedGridLayoutType);
    }

    public final int hashCode() {
        return 509637975;
    }

    public final String toString() {
        return "PartitionedGridLayoutType";
    }
}
