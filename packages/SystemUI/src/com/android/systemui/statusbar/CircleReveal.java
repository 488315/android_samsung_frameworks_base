package com.android.systemui.statusbar;

import com.android.systemui.statusbar.LightRevealEffect;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CircleReveal implements LightRevealEffect {
    public final int centerX;
    public final int centerY;
    public final int endRadius;
    public final int startRadius;

    public CircleReveal(int i, int i2, int i3, int i4) {
        this.centerX = i;
        this.centerY = i2;
        this.startRadius = i3;
        this.endRadius = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CircleReveal)) {
            return false;
        }
        CircleReveal circleReveal = (CircleReveal) obj;
        return this.centerX == circleReveal.centerX && this.centerY == circleReveal.centerY && this.startRadius == circleReveal.startRadius && this.endRadius == circleReveal.endRadius;
    }

    public final int hashCode() {
        return Integer.hashCode(this.endRadius) + ReorderTile$$ExternalSyntheticOutline0.m(this.startRadius, ReorderTile$$ExternalSyntheticOutline0.m(this.centerY, Integer.hashCode(this.centerX) * 31, 31), 31);
    }

    @Override // com.android.systemui.statusbar.LightRevealEffect
    public final void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim) {
        LightRevealEffect.Companion.getClass();
        float percentPastThreshold = LightRevealEffect.Companion.getPercentPastThreshold(f, 0.5f);
        float f2 = ((this.endRadius - r1) * f) + this.startRadius;
        lightRevealScrim.interpolatedRevealAmount = f;
        lightRevealScrim.setRevealGradientEndColorAlpha(1.0f - percentPastThreshold);
        float f3 = this.centerX;
        float f4 = this.centerY;
        lightRevealScrim.setRevealGradientBounds(f3 - f2, f4 - f2, f3 + f2, f4 + f2);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CircleReveal(centerX=");
        sb.append(this.centerX);
        sb.append(", centerY=");
        sb.append(this.centerY);
        sb.append(", startRadius=");
        sb.append(this.startRadius);
        sb.append(", endRadius=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.endRadius, ")", sb);
    }
}
