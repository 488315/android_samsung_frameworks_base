package com.android.systemui.surfaceeffects.ripple;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class RippleAnimationConfig {
    public final RippleShader.FadeParams baseRingFadeParams;
    public final RippleShader.FadeParams centerFillFadeParams;
    public final float centerX;
    public final float centerY;
    public int color;
    public final long duration;
    public final float maxHeight;
    public final float maxWidth;
    public final int opacity;
    public final float pixelDensity;
    public final RippleShader.RippleShape rippleShape;
    public final boolean shouldDistort;
    public final RippleShader.FadeParams sparkleRingFadeParams;
    public final float sparkleStrength;

    public RippleAnimationConfig() {
        this(null, 0L, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0.0f, null, null, null, false, 16383, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleAnimationConfig)) {
            return false;
        }
        RippleAnimationConfig rippleAnimationConfig = (RippleAnimationConfig) obj;
        return this.rippleShape == rippleAnimationConfig.rippleShape && this.duration == rippleAnimationConfig.duration && Float.compare(this.centerX, rippleAnimationConfig.centerX) == 0 && Float.compare(this.centerY, rippleAnimationConfig.centerY) == 0 && Float.compare(this.maxWidth, rippleAnimationConfig.maxWidth) == 0 && Float.compare(this.maxHeight, rippleAnimationConfig.maxHeight) == 0 && Float.compare(this.pixelDensity, rippleAnimationConfig.pixelDensity) == 0 && this.color == rippleAnimationConfig.color && this.opacity == rippleAnimationConfig.opacity && Float.compare(this.sparkleStrength, rippleAnimationConfig.sparkleStrength) == 0 && Intrinsics.areEqual(this.baseRingFadeParams, rippleAnimationConfig.baseRingFadeParams) && Intrinsics.areEqual(this.sparkleRingFadeParams, rippleAnimationConfig.sparkleRingFadeParams) && Intrinsics.areEqual(this.centerFillFadeParams, rippleAnimationConfig.centerFillFadeParams) && this.shouldDistort == rippleAnimationConfig.shouldDistort;
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.sparkleStrength, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.opacity, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.color, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pixelDensity, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerX, Scale$$ExternalSyntheticOutline0.m(this.rippleShape.hashCode() * 31, 31, this.duration), 31), 31), 31), 31), 31), 31), 31), 31);
        RippleShader.FadeParams fadeParams = this.baseRingFadeParams;
        int hashCode = (m + (fadeParams == null ? 0 : fadeParams.hashCode())) * 31;
        RippleShader.FadeParams fadeParams2 = this.sparkleRingFadeParams;
        int hashCode2 = (hashCode + (fadeParams2 == null ? 0 : fadeParams2.hashCode())) * 31;
        RippleShader.FadeParams fadeParams3 = this.centerFillFadeParams;
        return Boolean.hashCode(this.shouldDistort) + ((hashCode2 + (fadeParams3 != null ? fadeParams3.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "RippleAnimationConfig(rippleShape=" + this.rippleShape + ", duration=" + this.duration + ", centerX=" + this.centerX + ", centerY=" + this.centerY + ", maxWidth=" + this.maxWidth + ", maxHeight=" + this.maxHeight + ", pixelDensity=" + this.pixelDensity + ", color=" + this.color + ", opacity=" + this.opacity + ", sparkleStrength=" + this.sparkleStrength + ", baseRingFadeParams=" + this.baseRingFadeParams + ", sparkleRingFadeParams=" + this.sparkleRingFadeParams + ", centerFillFadeParams=" + this.centerFillFadeParams + ", shouldDistort=" + this.shouldDistort + ")";
    }

    public RippleAnimationConfig(RippleShader.RippleShape rippleShape, long j, float f, float f2, float f3, float f4, float f5, int i, int i2, float f6, RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2, RippleShader.FadeParams fadeParams3, boolean z) {
        this.rippleShape = rippleShape;
        this.duration = j;
        this.centerX = f;
        this.centerY = f2;
        this.maxWidth = f3;
        this.maxHeight = f4;
        this.pixelDensity = f5;
        this.color = i;
        this.opacity = i2;
        this.sparkleStrength = f6;
        this.baseRingFadeParams = fadeParams;
        this.sparkleRingFadeParams = fadeParams2;
        this.centerFillFadeParams = fadeParams3;
        this.shouldDistort = z;
    }

    public /* synthetic */ RippleAnimationConfig(RippleShader.RippleShape rippleShape, long j, float f, float f2, float f3, float f4, float f5, int i, int i2, float f6, RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2, RippleShader.FadeParams fadeParams3, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? RippleShader.RippleShape.CIRCLE : rippleShape, (i3 & 2) != 0 ? 0L : j, (i3 & 4) != 0 ? 0.0f : f, (i3 & 8) != 0 ? 0.0f : f2, (i3 & 16) != 0 ? 0.0f : f3, (i3 & 32) == 0 ? f4 : 0.0f, (i3 & 64) != 0 ? 1.0f : f5, (i3 & 128) != 0 ? -1 : i, (i3 & 256) != 0 ? 115 : i2, (i3 & 512) != 0 ? 0.3f : f6, (i3 & 1024) != 0 ? null : fadeParams, (i3 & 2048) != 0 ? null : fadeParams2, (i3 & 4096) == 0 ? fadeParams3 : null, (i3 & 8192) != 0 ? true : z);
    }
}
