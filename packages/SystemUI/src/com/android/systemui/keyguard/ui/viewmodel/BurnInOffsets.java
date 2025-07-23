package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BurnInOffsets {
    public final float progress;
    public final int x;
    public final int y;

    public BurnInOffsets(int i, int i2, float f) {
        this.x = i;
        this.y = i2;
        this.progress = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BurnInOffsets)) {
            return false;
        }
        BurnInOffsets burnInOffsets = (BurnInOffsets) obj;
        return this.x == burnInOffsets.x && this.y == burnInOffsets.y && Float.compare(this.progress, burnInOffsets.progress) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.progress) + ReorderTile$$ExternalSyntheticOutline0.m(this.y, Integer.hashCode(this.x) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BurnInOffsets(x=");
        sb.append(this.x);
        sb.append(", y=");
        sb.append(this.y);
        sb.append(", progress=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.progress, ")", sb);
    }
}
