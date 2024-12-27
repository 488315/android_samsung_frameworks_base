package com.android.systemui.controls.management.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return Anchor$$ExternalSyntheticOutline0.m(this.padding, ")", new StringBuilder("VerticalPaddingWrapper(padding="));
    }
}
