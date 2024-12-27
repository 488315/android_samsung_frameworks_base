package com.android.systemui.haptics.slider;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SliderHapticFeedbackConfig {
    public final float additionalVelocityMaxBump;
    public final float deltaMillisForDragInterval;
    public final float deltaProgressForDragThreshold;
    public final float exponent;
    public final float lowerBookendScale;
    public final float maxVelocityToScale;
    public final int numberOfLowTicks;
    public final float progressBasedDragMaxScale;
    public final float progressBasedDragMinScale;
    public final float progressInterpolatorFactor;
    public final float upperBookendScale;
    public final int velocityAxis;
    public final float velocityInterpolatorFactor;

    public SliderHapticFeedbackConfig() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, 0.0f, 0.0f, 0.0f, 8191, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SliderHapticFeedbackConfig)) {
            return false;
        }
        SliderHapticFeedbackConfig sliderHapticFeedbackConfig = (SliderHapticFeedbackConfig) obj;
        return Float.compare(this.velocityInterpolatorFactor, sliderHapticFeedbackConfig.velocityInterpolatorFactor) == 0 && Float.compare(this.progressInterpolatorFactor, sliderHapticFeedbackConfig.progressInterpolatorFactor) == 0 && Float.compare(this.progressBasedDragMinScale, sliderHapticFeedbackConfig.progressBasedDragMinScale) == 0 && Float.compare(this.progressBasedDragMaxScale, sliderHapticFeedbackConfig.progressBasedDragMaxScale) == 0 && Float.compare(this.additionalVelocityMaxBump, sliderHapticFeedbackConfig.additionalVelocityMaxBump) == 0 && Float.compare(this.deltaMillisForDragInterval, sliderHapticFeedbackConfig.deltaMillisForDragInterval) == 0 && Float.compare(this.deltaProgressForDragThreshold, sliderHapticFeedbackConfig.deltaProgressForDragThreshold) == 0 && this.numberOfLowTicks == sliderHapticFeedbackConfig.numberOfLowTicks && Float.compare(this.maxVelocityToScale, sliderHapticFeedbackConfig.maxVelocityToScale) == 0 && this.velocityAxis == sliderHapticFeedbackConfig.velocityAxis && Float.compare(this.upperBookendScale, sliderHapticFeedbackConfig.upperBookendScale) == 0 && Float.compare(this.lowerBookendScale, sliderHapticFeedbackConfig.lowerBookendScale) == 0 && Float.compare(this.exponent, sliderHapticFeedbackConfig.exponent) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.exponent) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.lowerBookendScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.upperBookendScale, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.velocityAxis, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxVelocityToScale, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.numberOfLowTicks, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.deltaProgressForDragThreshold, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.deltaMillisForDragInterval, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.additionalVelocityMaxBump, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.progressBasedDragMaxScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.progressBasedDragMinScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.progressInterpolatorFactor, Float.hashCode(this.velocityInterpolatorFactor) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SliderHapticFeedbackConfig(velocityInterpolatorFactor=");
        sb.append(this.velocityInterpolatorFactor);
        sb.append(", progressInterpolatorFactor=");
        sb.append(this.progressInterpolatorFactor);
        sb.append(", progressBasedDragMinScale=");
        sb.append(this.progressBasedDragMinScale);
        sb.append(", progressBasedDragMaxScale=");
        sb.append(this.progressBasedDragMaxScale);
        sb.append(", additionalVelocityMaxBump=");
        sb.append(this.additionalVelocityMaxBump);
        sb.append(", deltaMillisForDragInterval=");
        sb.append(this.deltaMillisForDragInterval);
        sb.append(", deltaProgressForDragThreshold=");
        sb.append(this.deltaProgressForDragThreshold);
        sb.append(", numberOfLowTicks=");
        sb.append(this.numberOfLowTicks);
        sb.append(", maxVelocityToScale=");
        sb.append(this.maxVelocityToScale);
        sb.append(", velocityAxis=");
        sb.append(this.velocityAxis);
        sb.append(", upperBookendScale=");
        sb.append(this.upperBookendScale);
        sb.append(", lowerBookendScale=");
        sb.append(this.lowerBookendScale);
        sb.append(", exponent=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.exponent, ")");
    }

    public SliderHapticFeedbackConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, int i, float f8, int i2, float f9, float f10, float f11) {
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
    }

    public /* synthetic */ SliderHapticFeedbackConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, int i, float f8, int i2, float f9, float f10, float f11, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 1.0f : f, (i3 & 2) != 0 ? 1.0f : f2, (i3 & 4) != 0 ? 0.0f : f3, (i3 & 8) != 0 ? 0.2f : f4, (i3 & 16) != 0 ? 0.15f : f5, (i3 & 32) == 0 ? f6 : 0.0f, (i3 & 64) != 0 ? 0.015f : f7, (i3 & 128) != 0 ? 5 : i, (i3 & 256) != 0 ? 2000.0f : f8, (i3 & 512) != 0 ? 0 : i2, (i3 & 1024) == 0 ? f9 : 1.0f, (i3 & 2048) != 0 ? 0.05f : f10, (i3 & 4096) != 0 ? 1.1235955f : f11);
    }
}
