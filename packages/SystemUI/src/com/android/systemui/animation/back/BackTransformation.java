package com.android.systemui.animation.back;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BackTransformation {
    public float scale;
    public ScalePivotPosition scalePivotPosition;
    public float translateX;
    public float translateY;

    public BackTransformation() {
        this(0.0f, 0.0f, 0.0f, null, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BackTransformation)) {
            return false;
        }
        BackTransformation backTransformation = (BackTransformation) obj;
        return Float.compare(this.translateX, backTransformation.translateX) == 0 && Float.compare(this.translateY, backTransformation.translateY) == 0 && Float.compare(this.scale, backTransformation.scale) == 0 && this.scalePivotPosition == backTransformation.scalePivotPosition;
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.translateY, Float.hashCode(this.translateX) * 31, 31), 31);
        ScalePivotPosition scalePivotPosition = this.scalePivotPosition;
        return m + (scalePivotPosition == null ? 0 : scalePivotPosition.hashCode());
    }

    public final String toString() {
        float f = this.translateX;
        float f2 = this.translateY;
        float f3 = this.scale;
        ScalePivotPosition scalePivotPosition = this.scalePivotPosition;
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("BackTransformation(translateX=", f, ", translateY=", f2, ", scale=");
        m.append(f3);
        m.append(", scalePivotPosition=");
        m.append(scalePivotPosition);
        m.append(")");
        return m.toString();
    }

    public BackTransformation(float f, float f2, float f3, ScalePivotPosition scalePivotPosition) {
        this.translateX = f;
        this.translateY = f2;
        this.scale = f3;
        this.scalePivotPosition = scalePivotPosition;
    }

    public /* synthetic */ BackTransformation(float f, float f2, float f3, ScalePivotPosition scalePivotPosition, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Float.NaN : f, (i & 2) != 0 ? Float.NaN : f2, (i & 4) != 0 ? Float.NaN : f3, (i & 8) != 0 ? null : scalePivotPosition);
    }
}
