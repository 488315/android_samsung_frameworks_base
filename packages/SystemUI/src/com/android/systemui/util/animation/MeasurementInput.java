package com.android.systemui.util.animation;

import android.view.View;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;

public final class MeasurementInput {
    public static final int $stable = 8;
    private int heightMeasureSpec;
    private int widthMeasureSpec;

    public MeasurementInput(int i, int i2) {
        this.widthMeasureSpec = i;
        this.heightMeasureSpec = i2;
    }

    public static /* synthetic */ MeasurementInput copy$default(MeasurementInput measurementInput, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = measurementInput.widthMeasureSpec;
        }
        if ((i3 & 2) != 0) {
            i2 = measurementInput.heightMeasureSpec;
        }
        return measurementInput.copy(i, i2);
    }

    public final int component1() {
        return this.widthMeasureSpec;
    }

    public final int component2() {
        return this.heightMeasureSpec;
    }

    public final MeasurementInput copy(int i, int i2) {
        return new MeasurementInput(i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MeasurementInput)) {
            return false;
        }
        MeasurementInput measurementInput = (MeasurementInput) obj;
        return this.widthMeasureSpec == measurementInput.widthMeasureSpec && this.heightMeasureSpec == measurementInput.heightMeasureSpec;
    }

    public final int getHeight() {
        return View.MeasureSpec.getSize(this.heightMeasureSpec);
    }

    public final int getHeightMeasureSpec() {
        return this.heightMeasureSpec;
    }

    public final int getWidth() {
        return View.MeasureSpec.getSize(this.widthMeasureSpec);
    }

    public final int getWidthMeasureSpec() {
        return this.widthMeasureSpec;
    }

    public int hashCode() {
        return Integer.hashCode(this.heightMeasureSpec) + (Integer.hashCode(this.widthMeasureSpec) * 31);
    }

    public final void setHeightMeasureSpec(int i) {
        this.heightMeasureSpec = i;
    }

    public final void setWidthMeasureSpec(int i) {
        this.widthMeasureSpec = i;
    }

    public String toString() {
        return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(this.widthMeasureSpec, this.heightMeasureSpec, "MeasurementInput(widthMeasureSpec=", ", heightMeasureSpec=", ")");
    }
}
