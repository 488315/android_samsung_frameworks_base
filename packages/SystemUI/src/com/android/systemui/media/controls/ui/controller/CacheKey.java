package com.android.systemui.media.controls.ui.controller;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CacheKey {
    public float expansion;
    public boolean gutsVisible;
    public int heightMeasureSpec;
    public int widthMeasureSpec;

    public CacheKey() {
        this(0, 0, 0.0f, false, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheKey)) {
            return false;
        }
        CacheKey cacheKey = (CacheKey) obj;
        return this.widthMeasureSpec == cacheKey.widthMeasureSpec && this.heightMeasureSpec == cacheKey.heightMeasureSpec && Float.compare(this.expansion, cacheKey.expansion) == 0 && this.gutsVisible == cacheKey.gutsVisible;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.gutsVisible) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.expansion, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.heightMeasureSpec, Integer.hashCode(this.widthMeasureSpec) * 31, 31), 31);
    }

    public final String toString() {
        int i = this.widthMeasureSpec;
        int i2 = this.heightMeasureSpec;
        float f = this.expansion;
        boolean z = this.gutsVisible;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "CacheKey(widthMeasureSpec=", ", heightMeasureSpec=", ", expansion=");
        m.append(f);
        m.append(", gutsVisible=");
        m.append(z);
        m.append(")");
        return m.toString();
    }

    public CacheKey(int i, int i2, float f, boolean z) {
        this.widthMeasureSpec = i;
        this.heightMeasureSpec = i2;
        this.expansion = f;
        this.gutsVisible = z;
    }

    public /* synthetic */ CacheKey(int i, int i2, float f, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -1 : i2, (i3 & 4) != 0 ? 0.0f : f, (i3 & 8) != 0 ? false : z);
    }
}
