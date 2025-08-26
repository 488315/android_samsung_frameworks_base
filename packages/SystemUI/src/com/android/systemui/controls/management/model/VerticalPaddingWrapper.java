package com.android.systemui.controls.management.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final class VerticalPaddingWrapper extends SecElementWrapper {
    public final int padding;

    public VerticalPaddingWrapper(int i) {
        super(null);
        this.padding = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof VerticalPaddingWrapper) && this.padding == ((VerticalPaddingWrapper) obj).padding;
    }

    public final int hashCode() {
        return Integer.hashCode(this.padding);
    }

    public final String toString() {
        return ReorderTile$$ExternalSyntheticOutline0.m(this.padding, ")", new StringBuilder("VerticalPaddingWrapper(padding="));
    }
}
