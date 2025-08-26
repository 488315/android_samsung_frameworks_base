package com.android.systemui.statusbar;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.LsRune;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.util.leak.RotationUtils;

/* loaded from: classes3.dex */
public final class PowerButtonReveal implements LightRevealEffect {
    public final float powerButtonY;

    public PowerButtonReveal(float f) {
        this.powerButtonY = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PowerButtonReveal) && Float.compare(this.powerButtonY, ((PowerButtonReveal) obj).powerButtonY) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.powerButtonY);
    }

    @Override // com.android.systemui.statusbar.LightRevealEffect
    public final void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim) {
        Interpolator interpolator;
        boolean z = LsRune.AOD_LIGHT_REVEAL;
        if (z) {
            SecLightRevealScrimHelper.Companion.getClass();
            interpolator = SecLightRevealScrimHelper.SEC_LIGHT_REVEAL_INTERPOLATOR;
        } else {
            interpolator = Interpolators.FAST_OUT_SLOW_IN_REVERSE;
        }
        float interpolation = interpolator.getInterpolation(f);
        LightRevealEffect.Companion.getClass();
        lightRevealScrim.setRevealGradientEndColorAlpha(1.0f - LightRevealEffect.Companion.getPercentPastThreshold(interpolation, 0.5f));
        lightRevealScrim.interpolatedRevealAmount = interpolation;
        if (z) {
            float f2 = 1.0f - f;
            if (lightRevealScrim.revealDimGradientEndColorAlpha != f2) {
                lightRevealScrim.revealDimGradientEndColorAlpha = f2;
                lightRevealScrim.setPaintColorFilter();
            }
        }
        int rotation = RotationUtils.getRotation(lightRevealScrim.getContext());
        float f3 = this.powerButtonY;
        if (rotation == 0) {
            lightRevealScrim.setRevealGradientBounds((lightRevealScrim.getWidth() * 1.05f) - ((lightRevealScrim.getWidth() * 1.25f) * interpolation), f3 - (lightRevealScrim.getHeight() * interpolation), (lightRevealScrim.getWidth() * 1.25f * interpolation) + (1.05f * lightRevealScrim.getWidth()), (lightRevealScrim.getHeight() * interpolation) + f3);
            return;
        }
        if (rotation == 1) {
            lightRevealScrim.setRevealGradientBounds(f3 - (lightRevealScrim.getWidth() * interpolation), ((-lightRevealScrim.getHeight()) * 0.05f) - ((lightRevealScrim.getHeight() * 1.25f) * interpolation), (lightRevealScrim.getWidth() * interpolation) + f3, (lightRevealScrim.getHeight() * 1.25f * interpolation) + ((-lightRevealScrim.getHeight()) * 0.05f));
            return;
        }
        lightRevealScrim.setRevealGradientBounds((lightRevealScrim.getWidth() - f3) - (lightRevealScrim.getWidth() * interpolation), (lightRevealScrim.getHeight() * 1.05f) - ((lightRevealScrim.getHeight() * 1.25f) * interpolation), (lightRevealScrim.getWidth() * interpolation) + (lightRevealScrim.getWidth() - f3), (lightRevealScrim.getHeight() * 1.25f * interpolation) + (1.05f * lightRevealScrim.getHeight()));
    }

    public final String toString() {
        return "PowerButtonReveal(powerButtonY=" + this.powerButtonY + ")";
    }
}
