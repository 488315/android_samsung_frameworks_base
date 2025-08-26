package com.android.systemui.qs.panels.shared.model;

/* loaded from: classes2.dex */
public final class PaginatedGridLayoutType implements GridLayoutType {
    public static final PaginatedGridLayoutType INSTANCE = new PaginatedGridLayoutType();

    private PaginatedGridLayoutType() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof PaginatedGridLayoutType);
    }

    public final int hashCode() {
        return -1335098085;
    }

    public final String toString() {
        return "PaginatedGridLayoutType";
    }
}
