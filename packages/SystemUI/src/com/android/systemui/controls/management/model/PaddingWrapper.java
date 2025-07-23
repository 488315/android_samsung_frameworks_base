package com.android.systemui.controls.management.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PaddingWrapper extends StructureElementWrapper {
    public final int padding;

    public PaddingWrapper(int i) {
        super(null);
        this.padding = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PaddingWrapper) && this.padding == ((PaddingWrapper) obj).padding;
    }

    public final int hashCode() {
        return Integer.hashCode(this.padding);
    }

    public final String toString() {
        return ReorderTile$$ExternalSyntheticOutline0.m(this.padding, ")", new StringBuilder("PaddingWrapper(padding="));
    }
}
