package com.android.systemui.haptics.slider;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SliderHapticFeedbackConfig {
    public final float additionalVelocityMaxBump;
    public final float deltaMillisForDragInterval;
    public final float deltaProgressForDragThreshold;
    public final float exponent;
    public final SliderHapticFeedbackFilter filter;
    public final float lowerBookendScale;
    public final float maxVelocityToScale;
    public final int numberOfLowTicks;
    public final float progressBasedDragMaxScale;
    public final float progressBasedDragMinScale;
    public final float progressInterpolatorFactor;
    public final float sliderStepSize;
    public final float upperBookendScale;
    public final int velocityAxis;
    public final float velocityInterpolatorFactor;

    public SliderHapticFeedbackConfig() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32767, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SliderHapticFeedbackConfig)) {
            return false;
        }
        SliderHapticFeedbackConfig sliderHapticFeedbackConfig = (SliderHapticFeedbackConfig) obj;
        return Float.compare(this.velocityInterpolatorFactor, sliderHapticFeedbackConfig.velocityInterpolatorFactor) == 0 && Float.compare(this.progressInterpolatorFactor, sliderHapticFeedbackConfig.progressInterpolatorFactor) == 0 && Float.compare(this.progressBasedDragMinScale, sliderHapticFeedbackConfig.progressBasedDragMinScale) == 0 && Float.compare(this.progressBasedDragMaxScale, sliderHapticFeedbackConfig.progressBasedDragMaxScale) == 0 && Float.compare(this.additionalVelocityMaxBump, sliderHapticFeedbackConfig.additionalVelocityMaxBump) == 0 && Float.compare(this.deltaMillisForDragInterval, sliderHapticFeedbackConfig.deltaMillisForDragInterval) == 0 && Float.compare(this.deltaProgressForDragThreshold, sliderHapticFeedbackConfig.deltaProgressForDragThreshold) == 0 && this.numberOfLowTicks == sliderHapticFeedbackConfig.numberOfLowTicks && Float.compare(this.maxVelocityToScale, sliderHapticFeedbackConfig.maxVelocityToScale) == 0 && this.velocityAxis == sliderHapticFeedbackConfig.velocityAxis && Float.compare(this.upperBookendScale, sliderHapticFeedbackConfig.upperBookendScale) == 0 && Float.compare(this.lowerBookendScale, sliderHapticFeedbackConfig.lowerBookendScale) == 0 && Float.compare(this.exponent, sliderHapticFeedbackConfig.exponent) == 0 && Float.compare(this.sliderStepSize, sliderHapticFeedbackConfig.sliderStepSize) == 0 && Intrinsics.areEqual(this.filter, sliderHapticFeedbackConfig.filter);
    }

    public final int hashCode() {
        return this.filter.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.sliderStepSize, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.exponent, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.lowerBookendScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.upperBookendScale, ReorderTile$$ExternalSyntheticOutline0.m(this.velocityAxis, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxVelocityToScale, ReorderTile$$ExternalSyntheticOutline0.m(this.numberOfLowTicks, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.deltaProgressForDragThreshold, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.deltaMillisForDragInterval, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.additionalVelocityMaxBump, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.progressBasedDragMaxScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.progressBasedDragMinScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.progressInterpolatorFactor, Float.hashCode(this.velocityInterpolatorFactor) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        return "SliderHapticFeedbackConfig(velocityInterpolatorFactor=" + this.velocityInterpolatorFactor + ", progressInterpolatorFactor=" + this.progressInterpolatorFactor + ", progressBasedDragMinScale=" + this.progressBasedDragMinScale + ", progressBasedDragMaxScale=" + this.progressBasedDragMaxScale + ", additionalVelocityMaxBump=" + this.additionalVelocityMaxBump + ", deltaMillisForDragInterval=" + this.deltaMillisForDragInterval + ", deltaProgressForDragThreshold=" + this.deltaProgressForDragThreshold + ", numberOfLowTicks=" + this.numberOfLowTicks + ", maxVelocityToScale=" + this.maxVelocityToScale + ", velocityAxis=" + this.velocityAxis + ", upperBookendScale=" + this.upperBookendScale + ", lowerBookendScale=" + this.lowerBookendScale + ", exponent=" + this.exponent + ", sliderStepSize=" + this.sliderStepSize + ", filter=" + this.filter + ")";
    }

    public SliderHapticFeedbackConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, int i, float f8, int i2, float f9, float f10, float f11, float f12, SliderHapticFeedbackFilter sliderHapticFeedbackFilter) {
        this.velocityInterpolatorFactor = f;
        this.progressInterpolatorFactor = f2;
        this.progressBasedDragMinScale = f3;
        this.progressBasedDragMaxScale = f4;
        this.additionalVelocityMaxBump = f5;
        this.deltaMillisForDragInterval = f6;
        this.deltaProgressForDragThreshold = f7;
        this.numberOfLowTicks = i;
        this.maxVelocityToScale = f8;
        this.velocityAxis = i2;
        this.upperBookendScale = f9;
        this.lowerBookendScale = f10;
        this.exponent = f11;
        this.sliderStepSize = f12;
        this.filter = sliderHapticFeedbackFilter;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ SliderHapticFeedbackConfig(float r17, float r18, float r19, float r20, float r21, float r22, float r23, int r24, float r25, int r26, float r27, float r28, float r29, float r30, com.android.systemui.haptics.slider.SliderHapticFeedbackFilter r31, int r32, kotlin.jvm.internal.DefaultConstructorMarker r33) {
        /*
            Method dump skipped, instructions count: 211
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.SliderHapticFeedbackConfig.<init>(float, float, float, float, float, float, float, int, float, int, float, float, float, float, com.android.systemui.haptics.slider.SliderHapticFeedbackFilter, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
