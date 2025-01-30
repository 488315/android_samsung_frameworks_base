package com.android.systemui.controls.management.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("PaddingWrapper(padding="), this.padding, ")");
    }
}
