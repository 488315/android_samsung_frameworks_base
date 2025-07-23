package com.android.systemui.plugins.clocks;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AodClockBurnInModel {
    public static final int $stable = 0;
    private final float scale;
    private final float translationX;
    private final float translationY;

    public AodClockBurnInModel(float f, float f2, float f3) {
        this.scale = f;
        this.translationX = f2;
        this.translationY = f3;
    }

    public static /* synthetic */ AodClockBurnInModel copy$default(AodClockBurnInModel aodClockBurnInModel, float f, float f2, float f3, int i, Object obj) {
        if ((i & 1) != 0) {
            f = aodClockBurnInModel.scale;
        }
        if ((i & 2) != 0) {
            f2 = aodClockBurnInModel.translationX;
        }
        if ((i & 4) != 0) {
            f3 = aodClockBurnInModel.translationY;
        }
        return aodClockBurnInModel.copy(f, f2, f3);
    }

    public final float component1() {
        return this.scale;
    }

    public final float component2() {
        return this.translationX;
    }

    public final float component3() {
        return this.translationY;
    }

    public final AodClockBurnInModel copy(float f, float f2, float f3) {
        return new AodClockBurnInModel(f, f2, f3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AodClockBurnInModel)) {
            return false;
        }
        AodClockBurnInModel aodClockBurnInModel = (AodClockBurnInModel) obj;
        return Float.compare(this.scale, aodClockBurnInModel.scale) == 0 && Float.compare(this.translationX, aodClockBurnInModel.translationX) == 0 && Float.compare(this.translationY, aodClockBurnInModel.translationY) == 0;
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getTranslationX() {
        return this.translationX;
    }

    public final float getTranslationY() {
        return this.translationY;
    }

    public int hashCode() {
        return Float.hashCode(this.translationY) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.translationX, Float.hashCode(this.scale) * 31, 31);
    }

    public String toString() {
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.translationY, ")", CubicBezierEasing$$ExternalSyntheticOutline0.m("AodClockBurnInModel(scale=", this.scale, ", translationX=", this.translationX, ", translationY="));
    }
}
