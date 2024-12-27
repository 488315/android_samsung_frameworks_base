package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

public final class Colors {
    public final int contrast;
    public final int tint;

    public Colors(int i, int i2) {
        this.tint = i;
        this.contrast = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Colors)) {
            return false;
        }
        Colors colors = (Colors) obj;
        return this.tint == colors.tint && this.contrast == colors.contrast;
    }

    public final int hashCode() {
        return Integer.hashCode(this.contrast) + (Integer.hashCode(this.tint) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Colors(tint=");
        sb.append(this.tint);
        sb.append(", contrast=");
        return Anchor$$ExternalSyntheticOutline0.m(this.contrast, ")", sb);
    }
}
