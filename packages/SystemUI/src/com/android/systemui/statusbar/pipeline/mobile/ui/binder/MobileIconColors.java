package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public final class MobileIconColors {
    public final int contrast;
    public final int tint;

    public MobileIconColors(int i, int i2) {
        this.tint = i;
        this.contrast = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileIconColors)) {
            return false;
        }
        MobileIconColors mobileIconColors = (MobileIconColors) obj;
        return this.tint == mobileIconColors.tint && this.contrast == mobileIconColors.contrast;
    }

    public final int hashCode() {
        return Integer.hashCode(this.contrast) + (Integer.hashCode(this.tint) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MobileIconColors(tint=");
        sb.append(this.tint);
        sb.append(", contrast=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.contrast, ")", sb);
    }
}
