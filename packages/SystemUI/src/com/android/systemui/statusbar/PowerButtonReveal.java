package com.android.systemui.statusbar;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.LsRune;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.util.leak.RotationUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PowerButtonReveal implements LightRevealEffect {
    public final float powerButtonY;
    public final float OFF_SCREEN_START_AMOUNT = 0.05f;
    public final float INCREASE_MULTIPLIER = 1.25f;

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
        float f3 = this.INCREASE_MULTIPLIER;
        float f4 = this.OFF_SCREEN_START_AMOUNT;
        float f5 = this.powerButtonY;
        if (rotation == 0) {
            float f6 = f4 + 1.0f;
            lightRevealScrim.setRevealGradientBounds((lightRevealScrim.getWidth() * f6) - ((lightRevealScrim.getWidth() * f3) * interpolation), f5 - (lightRevealScrim.getHeight() * interpolation), (lightRevealScrim.getWidth() * f3 * interpolation) + (f6 * lightRevealScrim.getWidth()), (lightRevealScrim.getHeight() * interpolation) + f5);
            return;
        }
        if (rotation == 1) {
            lightRevealScrim.setRevealGradientBounds(f5 - (lightRevealScrim.getWidth() * interpolation), ((-lightRevealScrim.getHeight()) * f4) - ((lightRevealScrim.getHeight() * f3) * interpolation), (lightRevealScrim.getWidth() * interpolation) + f5, (lightRevealScrim.getHeight() * f3 * interpolation) + ((-lightRevealScrim.getHeight()) * f4));
            return;
        }
        float f7 = f4 + 1.0f;
        lightRevealScrim.setRevealGradientBounds((lightRevealScrim.getWidth() - f5) - (lightRevealScrim.getWidth() * interpolation), (lightRevealScrim.getHeight() * f7) - ((lightRevealScrim.getHeight() * f3) * interpolation), (lightRevealScrim.getWidth() * interpolation) + (lightRevealScrim.getWidth() - f5), (lightRevealScrim.getHeight() * f3 * interpolation) + (f7 * lightRevealScrim.getHeight()));
    }

    public final String toString() {
        return "PowerButtonReveal(powerButtonY=" + this.powerButtonY + ")";
    }
}
