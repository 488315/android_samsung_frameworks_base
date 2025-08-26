package com.samsung.android.sesl.visualeffect.surfaceeffects.ripple;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class RippleAnimationConfig {
    public final RippleShader.FadeParams baseRingFadeParams;
    public final RippleShader.FadeParams centerFillFadeParams;
    public final float centerX;
    public final float centerY;
    public final int color;
    public final long duration;
    public final boolean hintSparkleOnly;
    public final Interpolator interpolator;
    public final boolean isReverse;
    public final float maxHeight;
    public final float maxWidth;
    public final float moveSpeedX;
    public final float moveSpeedY;
    public final int opacity;
    public final float pixelDensity;
    public final Float requestedFrameRate;
    public final float scale;
    public final boolean shouldDistort;
    public final int sparkleColor;
    public final RippleShader.FadeParams sparkleRingFadeParams;
    public final float sparkleStrength;

    public RippleAnimationConfig() {
        this(null, 0L, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, 0.0f, null, null, null, false, false, null, false, 2097151, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RippleAnimationConfig)) {
            return false;
        }
        RippleAnimationConfig rippleAnimationConfig = (RippleAnimationConfig) obj;
        return Intrinsics.areEqual(this.requestedFrameRate, rippleAnimationConfig.requestedFrameRate) && this.duration == rippleAnimationConfig.duration && Float.compare(this.centerX, rippleAnimationConfig.centerX) == 0 && Float.compare(this.centerY, rippleAnimationConfig.centerY) == 0 && Float.compare(this.moveSpeedX, rippleAnimationConfig.moveSpeedX) == 0 && Float.compare(this.moveSpeedY, rippleAnimationConfig.moveSpeedY) == 0 && Float.compare(this.scale, rippleAnimationConfig.scale) == 0 && Float.compare(this.maxWidth, rippleAnimationConfig.maxWidth) == 0 && Float.compare(this.maxHeight, rippleAnimationConfig.maxHeight) == 0 && Float.compare(this.pixelDensity, rippleAnimationConfig.pixelDensity) == 0 && this.color == rippleAnimationConfig.color && this.sparkleColor == rippleAnimationConfig.sparkleColor && this.opacity == rippleAnimationConfig.opacity && Float.compare(this.sparkleStrength, rippleAnimationConfig.sparkleStrength) == 0 && Intrinsics.areEqual(this.baseRingFadeParams, rippleAnimationConfig.baseRingFadeParams) && Intrinsics.areEqual(this.sparkleRingFadeParams, rippleAnimationConfig.sparkleRingFadeParams) && Intrinsics.areEqual(this.centerFillFadeParams, rippleAnimationConfig.centerFillFadeParams) && this.shouldDistort == rippleAnimationConfig.shouldDistort && this.isReverse == rippleAnimationConfig.isReverse && Intrinsics.areEqual(this.interpolator, rippleAnimationConfig.interpolator) && this.hintSparkleOnly == rippleAnimationConfig.hintSparkleOnly;
    }

    public final int hashCode() {
        Float f = this.requestedFrameRate;
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.sparkleStrength, ReorderTile$$ExternalSyntheticOutline0.m(this.opacity, ReorderTile$$ExternalSyntheticOutline0.m(this.sparkleColor, ReorderTile$$ExternalSyntheticOutline0.m(this.color, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pixelDensity, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.moveSpeedY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.moveSpeedX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerX, MoveResult$$ExternalSyntheticOutline0.m((f == null ? 0 : f.hashCode()) * 31, 31, this.duration), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31);
        RippleShader.FadeParams fadeParams = this.baseRingFadeParams;
        int iHashCode = (iM + (fadeParams == null ? 0 : fadeParams.hashCode())) * 31;
        RippleShader.FadeParams fadeParams2 = this.sparkleRingFadeParams;
        int iHashCode2 = (iHashCode + (fadeParams2 == null ? 0 : fadeParams2.hashCode())) * 31;
        RippleShader.FadeParams fadeParams3 = this.centerFillFadeParams;
        return Boolean.hashCode(this.hintSparkleOnly) + ((this.interpolator.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((iHashCode2 + (fadeParams3 != null ? fadeParams3.hashCode() : 0)) * 31, 31, this.shouldDistort), 31, this.isReverse)) * 31);
    }

    public final String toString() {
        return "RippleAnimationConfig(requestedFrameRate=" + this.requestedFrameRate + ", duration=" + this.duration + ", centerX=" + this.centerX + ", centerY=" + this.centerY + ", moveSpeedX=" + this.moveSpeedX + ", moveSpeedY=" + this.moveSpeedY + ", scale=" + this.scale + ", maxWidth=" + this.maxWidth + ", maxHeight=" + this.maxHeight + ", pixelDensity=" + this.pixelDensity + ", color=" + this.color + ", sparkleColor=" + this.sparkleColor + ", opacity=" + this.opacity + ", sparkleStrength=" + this.sparkleStrength + ", baseRingFadeParams=" + this.baseRingFadeParams + ", sparkleRingFadeParams=" + this.sparkleRingFadeParams + ", centerFillFadeParams=" + this.centerFillFadeParams + ", shouldDistort=" + this.shouldDistort + ", isReverse=" + this.isReverse + ", interpolator=" + this.interpolator + ", hintSparkleOnly=" + this.hintSparkleOnly + ")";
    }

    public RippleAnimationConfig(Float f, long j, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int i, int i2, int i3, float f10, RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2, RippleShader.FadeParams fadeParams3, boolean z, boolean z2, Interpolator interpolator, boolean z3) {
        this.requestedFrameRate = f;
        this.duration = j;
        this.centerX = f2;
        this.centerY = f3;
        this.moveSpeedX = f4;
        this.moveSpeedY = f5;
        this.scale = f6;
        this.maxWidth = f7;
        this.maxHeight = f8;
        this.pixelDensity = f9;
        this.color = i;
        this.sparkleColor = i2;
        this.opacity = i3;
        this.sparkleStrength = f10;
        this.baseRingFadeParams = fadeParams;
        this.sparkleRingFadeParams = fadeParams2;
        this.centerFillFadeParams = fadeParams3;
        this.shouldDistort = z;
        this.isReverse = z2;
        this.interpolator = interpolator;
        this.hintSparkleOnly = z3;
    }

    public /* synthetic */ RippleAnimationConfig(Float f, long j, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int i, int i2, int i3, float f10, RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2, RippleShader.FadeParams fadeParams3, boolean z, boolean z2, Interpolator interpolator, boolean z3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        Float f11;
        int i5;
        long j2;
        Interpolator pathInterpolator;
        Float f12 = (i4 & 1) != 0 ? null : f;
        long j3 = (i4 & 2) != 0 ? 0L : j;
        float f13 = (i4 & 4) != 0 ? 0.0f : f2;
        float f14 = (i4 & 8) != 0 ? 0.0f : f3;
        float f15 = (i4 & 16) != 0 ? 0.0f : f4;
        float f16 = (i4 & 32) != 0 ? 0.0f : f5;
        float f17 = (i4 & 64) != 0 ? 1.0f : f6;
        float f18 = (i4 & 128) != 0 ? 0.0f : f7;
        float f19 = (i4 & 256) != 0 ? 0.0f : f8;
        float f20 = (i4 & 512) != 0 ? 1.0f : f9;
        int i6 = (i4 & 1024) != 0 ? -1 : i;
        int i7 = (i4 & 2048) == 0 ? i2 : -1;
        int i8 = (i4 & 4096) != 0 ? 115 : i3;
        float f21 = (i4 & 8192) != 0 ? 0.3f : f10;
        RippleShader.FadeParams fadeParams4 = (i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : fadeParams;
        RippleShader.FadeParams fadeParams5 = (i4 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : fadeParams2;
        RippleShader.FadeParams fadeParams6 = (i4 & 65536) != 0 ? null : fadeParams3;
        boolean z4 = (i4 & 131072) != 0 ? true : z;
        boolean z5 = (i4 & 262144) != 0 ? false : z2;
        if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
            f11 = f12;
            i5 = i8;
            j2 = j3;
            pathInterpolator = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        } else {
            f11 = f12;
            i5 = i8;
            j2 = j3;
            pathInterpolator = interpolator;
        }
        this(f11, j2, f13, f14, f15, f16, f17, f18, f19, f20, i6, i7, i5, f21, fadeParams4, fadeParams5, fadeParams6, z4, z5, pathInterpolator, (i4 & 1048576) != 0 ? false : z3);
    }
}
