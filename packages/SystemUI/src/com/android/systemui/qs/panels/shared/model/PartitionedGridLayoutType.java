package com.android.systemui.qs.panels.shared.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
