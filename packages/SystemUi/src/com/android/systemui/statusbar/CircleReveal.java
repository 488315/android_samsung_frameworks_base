package com.android.systemui.statusbar;

import com.android.systemui.statusbar.LightRevealEffect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
}
