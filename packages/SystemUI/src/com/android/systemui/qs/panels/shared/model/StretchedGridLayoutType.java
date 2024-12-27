package com.android.systemui.qs.panels.shared.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
