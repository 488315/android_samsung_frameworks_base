package com.android.systemui.util.animation;

import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MeasurementOutput {
    public static final int $stable = 8;
    private int measuredHeight;
    private int measuredWidth;

    public MeasurementOutput(int i, int i2) {
        this.measuredWidth = i;
        this.measuredHeight = i2;
    }

    public static /* synthetic */ MeasurementOutput copy$default(MeasurementOutput measurementOutput, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = measurementOutput.measuredWidth;
        }
        if ((i3 & 2) != 0) {
            i2 = measurementOutput.measuredHeight;
        }
        return measurementOutput.copy(i, i2);
    }

    public final int component1() {
        return this.measuredWidth;
    }

    public final int component2() {
        return this.measuredHeight;
    }

    public final MeasurementOutput copy(int i, int i2) {
        return new MeasurementOutput(i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MeasurementOutput)) {
            return false;
        }
        MeasurementOutput measurementOutput = (MeasurementOutput) obj;
        return this.measuredWidth == measurementOutput.measuredWidth && this.measuredHeight == measurementOutput.measuredHeight;
    }

    public final int getMeasuredHeight() {
        return this.measuredHeight;
    }

    public final int getMeasuredWidth() {
        return this.measuredWidth;
    }

    public int hashCode() {
        return Integer.hashCode(this.measuredHeight) + (Integer.hashCode(this.measuredWidth) * 31);
    }

    public final void setMeasuredHeight(int i) {
        this.measuredHeight = i;
    }

    public final void setMeasuredWidth(int i) {
        this.measuredWidth = i;
    }

    public String toString() {
        return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(this.measuredWidth, this.measuredHeight, "MeasurementOutput(measuredWidth=", ", measuredHeight=", ")");
    }
}
