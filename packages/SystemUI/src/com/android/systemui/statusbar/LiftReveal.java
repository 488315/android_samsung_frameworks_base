package com.android.systemui.statusbar;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.LightRevealEffect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LiftReveal implements LightRevealEffect {
    public static final LiftReveal INSTANCE = new LiftReveal();
    public static final Interpolator INTERPOLATOR = Interpolators.FAST_OUT_SLOW_IN_REVERSE;

    private LiftReveal() {
    }

    @Override // com.android.systemui.statusbar.LightRevealEffect
    public final void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim) {
        float interpolation = ((PathInterpolator) INTERPOLATOR).getInterpolation(f);
        LightRevealEffect.Companion.getClass();
        float percentPastThreshold = LightRevealEffect.Companion.getPercentPastThreshold(interpolation, 0.35f);
        lightRevealScrim.setRevealGradientEndColorAlpha(1.0f - LightRevealEffect.Companion.getPercentPastThreshold(f, 0.85f));
        lightRevealScrim.setRevealGradientBounds(((-lightRevealScrim.getWidth()) * percentPastThreshold) + (lightRevealScrim.getWidth() * 0.25f), (lightRevealScrim.getHeight() * 1.1f) - (lightRevealScrim.getHeight() * interpolation), (lightRevealScrim.getWidth() * percentPastThreshold) + (lightRevealScrim.getWidth() * 0.75f), (lightRevealScrim.getHeight() * interpolation) + (lightRevealScrim.getHeight() * 1.2f));
    }
}
