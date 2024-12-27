package com.android.systemui.plugins.clocks;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AodClockBurnInModel {
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
        float f = this.scale;
        float f2 = this.translationX;
        return DpCornerSize$$ExternalSyntheticOutline0.m(CubicBezierEasing$$ExternalSyntheticOutline0.m("AodClockBurnInModel(scale=", f, ", translationX=", f2, ", translationY="), this.translationY, ")");
    }
}
