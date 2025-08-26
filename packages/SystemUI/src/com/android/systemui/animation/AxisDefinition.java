package com.android.systemui.animation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AxisDefinition {
    public final float animationStep;
    public final float defaultValue;
    public final float maxValue;
    public final float minValue;
    public final String tag;

    public AxisDefinition(String str, float f, float f2, float f3, float f4) {
        this.tag = str;
        this.minValue = f;
        this.defaultValue = f2;
        this.maxValue = f3;
        this.animationStep = f4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AxisDefinition)) {
            return false;
        }
        AxisDefinition axisDefinition = (AxisDefinition) obj;
        return Intrinsics.areEqual(this.tag, axisDefinition.tag) && Float.compare(this.minValue, axisDefinition.minValue) == 0 && Float.compare(this.defaultValue, axisDefinition.defaultValue) == 0 && Float.compare(this.maxValue, axisDefinition.maxValue) == 0 && Float.compare(this.animationStep, axisDefinition.animationStep) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.animationStep) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxValue, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.defaultValue, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.minValue, this.tag.hashCode() * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AxisDefinition(tag=");
        sb.append(this.tag);
        sb.append(", minValue=");
        sb.append(this.minValue);
        sb.append(", defaultValue=");
        sb.append(this.defaultValue);
        sb.append(", maxValue=");
        sb.append(this.maxValue);
        sb.append(", animationStep=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.animationStep, ")", sb);
    }
}
