package com.android.systemui.surfaceeffects.ripple;

import android.animation.ValueAnimator;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.surfaceeffects.ripple.RippleShader;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RippleAnimation {
    public final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    public final RippleAnimationConfig config;
    public final RippleShader rippleShader;

    public RippleAnimation(RippleAnimationConfig rippleAnimationConfig) {
        this.config = rippleAnimationConfig;
        this.rippleShader = new RippleShader(rippleAnimationConfig.rippleShape);
        applyConfigToShader();
    }

    public static void assignFadeParams(RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2) {
        if (fadeParams2 != null) {
            fadeParams.fadeInStart = fadeParams2.fadeInStart;
            fadeParams.fadeInEnd = fadeParams2.fadeInEnd;
            fadeParams.fadeOutStart = fadeParams2.fadeOutStart;
            fadeParams.fadeOutEnd = fadeParams2.fadeOutEnd;
        }
    }

    public final void applyConfigToShader() {
        RippleAnimationConfig rippleAnimationConfig = this.config;
        float f = rippleAnimationConfig.centerX;
        RippleShader rippleShader = this.rippleShader;
        rippleShader.setFloatUniform("in_center", f, rippleAnimationConfig.centerY);
        RippleShader.RippleSize rippleSize = rippleShader.rippleSize;
        rippleSize.getClass();
        rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, rippleAnimationConfig.maxWidth, rippleAnimationConfig.maxHeight));
        rippleShader.setPixelDensity(rippleAnimationConfig.pixelDensity);
        rippleShader.setColorUniform("in_color", ColorUtils.setAlphaComponent(rippleAnimationConfig.color, rippleAnimationConfig.opacity));
        rippleShader.setFloatUniform("in_sparkle_strength", rippleAnimationConfig.sparkleStrength);
        assignFadeParams(rippleShader.baseRingFadeParams, rippleAnimationConfig.baseRingFadeParams);
        assignFadeParams(rippleShader.sparkleRingFadeParams, rippleAnimationConfig.sparkleRingFadeParams);
        assignFadeParams(rippleShader.centerFillFadeParams, rippleAnimationConfig.centerFillFadeParams);
    }

    public static /* synthetic */ void getRippleShader$annotations() {
    }
}
