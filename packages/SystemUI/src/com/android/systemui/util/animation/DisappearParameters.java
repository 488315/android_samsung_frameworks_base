package com.android.systemui.util.animation;

import android.graphics.PointF;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisappearParameters {
    public static final int $stable = 8;
    private float disappearStart;
    private PointF gonePivot = new PointF(0.0f, 1.0f);
    private PointF disappearSize = new PointF(1.0f, 0.0f);
    private PointF contentTranslationFraction = new PointF(0.0f, 0.8f);
    private float disappearEnd = 1.0f;
    private float fadeStartPosition = 0.9f;

    public final DisappearParameters deepCopy() {
        DisappearParameters disappearParameters = new DisappearParameters();
        disappearParameters.disappearSize.set(this.disappearSize);
        disappearParameters.gonePivot.set(this.gonePivot);
        disappearParameters.contentTranslationFraction.set(this.contentTranslationFraction);
        disappearParameters.disappearStart = this.disappearStart;
        disappearParameters.disappearEnd = this.disappearEnd;
        disappearParameters.fadeStartPosition = this.fadeStartPosition;
        return disappearParameters;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DisappearParameters)) {
            return false;
        }
        DisappearParameters disappearParameters = (DisappearParameters) obj;
        return this.disappearSize.equals(disappearParameters.disappearSize) && this.gonePivot.equals(disappearParameters.gonePivot) && this.contentTranslationFraction.equals(disappearParameters.contentTranslationFraction) && this.disappearStart == disappearParameters.disappearStart && this.disappearEnd == disappearParameters.disappearEnd && this.fadeStartPosition == disappearParameters.fadeStartPosition;
    }

    public final PointF getContentTranslationFraction() {
        return this.contentTranslationFraction;
    }

    public final float getDisappearEnd() {
        return this.disappearEnd;
    }

    public final PointF getDisappearSize() {
        return this.disappearSize;
    }

    public final float getDisappearStart() {
        return this.disappearStart;
    }

    public final float getFadeStartPosition() {
        return this.fadeStartPosition;
    }

    public final PointF getGonePivot() {
        return this.gonePivot;
    }

    public int hashCode() {
        return Float.hashCode(this.fadeStartPosition) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.disappearEnd, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.disappearStart, (this.contentTranslationFraction.hashCode() + ((this.gonePivot.hashCode() + (this.disappearSize.hashCode() * 31)) * 31)) * 31, 31), 31);
    }

    public final void setContentTranslationFraction(PointF pointF) {
        this.contentTranslationFraction = pointF;
    }

    public final void setDisappearEnd(float f) {
        this.disappearEnd = f;
    }

    public final void setDisappearSize(PointF pointF) {
        this.disappearSize = pointF;
    }

    public final void setDisappearStart(float f) {
        this.disappearStart = f;
    }

    public final void setFadeStartPosition(float f) {
        this.fadeStartPosition = f;
    }

    public final void setGonePivot(PointF pointF) {
        this.gonePivot = pointF;
    }
}
