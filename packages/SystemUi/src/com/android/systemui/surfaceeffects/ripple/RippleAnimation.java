package com.android.systemui.surfaceeffects.ripple;

import android.animation.ValueAnimator;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.surfaceeffects.ripple.RippleShader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RippleAnimation {
    public final ValueAnimator animator;
    public final RippleShader rippleShader;

    public RippleAnimation(RippleAnimationConfig rippleAnimationConfig) {
        RippleShader rippleShader = new RippleShader(rippleAnimationConfig.rippleShape);
        this.rippleShader = rippleShader;
        this.animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        rippleShader.setFloatUniform("in_center", rippleAnimationConfig.centerX, rippleAnimationConfig.centerY);
        rippleShader.rippleSize.setMaxSize(rippleAnimationConfig.maxWidth, rippleAnimationConfig.maxHeight);
        rippleShader.setPixelDensity(rippleAnimationConfig.pixelDensity);
        rippleShader.setColorUniform("in_color", ColorUtils.setAlphaComponent(rippleAnimationConfig.color, rippleAnimationConfig.opacity));
        rippleShader.setFloatUniform("in_sparkle_strength", rippleAnimationConfig.sparkleStrength);
        assignFadeParams(rippleShader.baseRingFadeParams, rippleAnimationConfig.baseRingFadeParams);
        assignFadeParams(rippleShader.sparkleRingFadeParams, rippleAnimationConfig.sparkleRingFadeParams);
        assignFadeParams(rippleShader.centerFillFadeParams, rippleAnimationConfig.centerFillFadeParams);
    }

    public static void assignFadeParams(RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2) {
        if (fadeParams2 != null) {
            fadeParams.fadeInStart = fadeParams2.fadeInStart;
            fadeParams.fadeInEnd = fadeParams2.fadeInEnd;
            fadeParams.fadeOutStart = fadeParams2.fadeOutStart;
            fadeParams.fadeOutEnd = fadeParams2.fadeOutEnd;
        }
    }

    public static /* synthetic */ void getRippleShader$annotations() {
    }
}
