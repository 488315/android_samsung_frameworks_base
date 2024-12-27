package com.android.systemui.controls.management.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

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
        return Anchor$$ExternalSyntheticOutline0.m(this.padding, ")", new StringBuilder("PaddingWrapper(padding="));
    }
}
