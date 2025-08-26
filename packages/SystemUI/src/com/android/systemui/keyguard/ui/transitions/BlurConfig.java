package com.android.systemui.keyguard.ui.transitions;

import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final class BlurConfig {
    public final float maxBlurRadiusPx;
    public final float minBlurRadiusPx;

    public BlurConfig(float f, float f2) {
        this.minBlurRadiusPx = f;
        this.maxBlurRadiusPx = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlurConfig)) {
            return false;
        }
        BlurConfig blurConfig = (BlurConfig) obj;
        return Float.compare(this.minBlurRadiusPx, blurConfig.minBlurRadiusPx) == 0 && Float.compare(this.maxBlurRadiusPx, blurConfig.maxBlurRadiusPx) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.maxBlurRadiusPx) + (Float.hashCode(this.minBlurRadiusPx) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BlurConfig(minBlurRadiusPx=");
        sb.append(this.minBlurRadiusPx);
        sb.append(", maxBlurRadiusPx=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.maxBlurRadiusPx, ")", sb);
    }

    public BlurConfig() {
        this(0.0f, 0.0f);
    }
}
