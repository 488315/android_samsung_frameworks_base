package com.samsung.android.sesl.visualeffect.surfaceeffects.ripple;

import android.view.animation.Interpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.sparkleStrength, ReorderTile$$ExternalSyntheticOutline0.m(this.opacity, ReorderTile$$ExternalSyntheticOutline0.m(this.sparkleColor, ReorderTile$$ExternalSyntheticOutline0.m(this.color, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pixelDensity, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxWidth, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.moveSpeedY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.moveSpeedX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.centerX, MoveResult$$ExternalSyntheticOutline0.m((f == null ? 0 : f.hashCode()) * 31, 31, this.duration), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31);
        RippleShader.FadeParams fadeParams = this.baseRingFadeParams;
        int hashCode = (m + (fadeParams == null ? 0 : fadeParams.hashCode())) * 31;
        RippleShader.FadeParams fadeParams2 = this.sparkleRingFadeParams;
        int hashCode2 = (hashCode + (fadeParams2 == null ? 0 : fadeParams2.hashCode())) * 31;
        RippleShader.FadeParams fadeParams3 = this.centerFillFadeParams;
        return Boolean.hashCode(this.hintSparkleOnly) + ((this.interpolator.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (fadeParams3 != null ? fadeParams3.hashCode() : 0)) * 31, 31, this.shouldDistort), 31, this.isReverse)) * 31);
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ RippleAnimationConfig(java.lang.Float r24, long r25, float r27, float r28, float r29, float r30, float r31, float r32, float r33, float r34, int r35, int r36, int r37, float r38, com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader.FadeParams r39, com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader.FadeParams r40, com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader.FadeParams r41, boolean r42, boolean r43, android.view.animation.Interpolator r44, boolean r45, int r46, kotlin.jvm.internal.DefaultConstructorMarker r47) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleAnimationConfig.<init>(java.lang.Float, long, float, float, float, float, float, float, float, float, int, int, int, float, com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader$FadeParams, com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader$FadeParams, com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader$FadeParams, boolean, boolean, android.view.animation.Interpolator, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
