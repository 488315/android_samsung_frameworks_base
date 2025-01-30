package com.android.systemui.surfaceeffects.turbulencenoise;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TurbulenceNoiseView extends View {
    public ValueAnimator currentAnimator;
    public TurbulenceNoiseAnimationConfig noiseConfig;
    public final Paint paint;
    public final TurbulenceNoiseShader turbulenceNoiseShader;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TurbulenceNoiseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TurbulenceNoiseShader turbulenceNoiseShader = new TurbulenceNoiseShader(false, 1, null);
        this.turbulenceNoiseShader = turbulenceNoiseShader;
        Paint paint = new Paint();
        paint.setShader(turbulenceNoiseShader);
        this.paint = paint;
    }

    public final void applyConfig(TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig) {
        this.noiseConfig = turbulenceNoiseAnimationConfig;
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.setFloatUniform("in_gridNum", turbulenceNoiseAnimationConfig.gridCount);
        turbulenceNoiseShader.setColorUniform("in_color", turbulenceNoiseAnimationConfig.color);
        turbulenceNoiseShader.setColorUniform("in_backgroundColor", turbulenceNoiseAnimationConfig.backgroundColor);
        float f = turbulenceNoiseAnimationConfig.width;
        float f2 = turbulenceNoiseAnimationConfig.height;
        turbulenceNoiseShader.setFloatUniform("in_size", f, f2);
        turbulenceNoiseShader.setFloatUniform("in_aspectRatio", f / Float.max(f2, 0.001f));
        turbulenceNoiseShader.setFloatUniform("in_pixelDensity", turbulenceNoiseAnimationConfig.pixelDensity);
        turbulenceNoiseShader.setFloatUniform("in_inverseLuma", 1.0f);
        turbulenceNoiseShader.setFloatUniform("in_lumaMatteBlendFactor", turbulenceNoiseAnimationConfig.lumaMatteBlendFactor);
        turbulenceNoiseShader.setFloatUniform("in_lumaMatteOverallBrightness", turbulenceNoiseAnimationConfig.lumaMatteOverallBrightness);
        this.paint.setBlendMode(turbulenceNoiseAnimationConfig.blendMode);
    }

    public final void finish(Runnable runnable) {
        ValueAnimator valueAnimator = this.currentAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
        this.currentAnimator = null;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas.isHardwareAccelerated()) {
            canvas.drawPaint(this.paint);
        }
    }

    public final void play(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        Intrinsics.checkNotNull(turbulenceNoiseAnimationConfig);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration((long) turbulenceNoiseAnimationConfig.maxDuration);
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$play$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                TurbulenceNoiseShader turbulenceNoiseShader2 = TurbulenceNoiseView.this.turbulenceNoiseShader;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                float f5 = (turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4;
                float f6 = (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2;
                float f7 = (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3;
                turbulenceNoiseShader2.noiseOffsetX = f5;
                turbulenceNoiseShader2.noiseOffsetY = f6;
                turbulenceNoiseShader2.noiseOffsetZ = f7;
                turbulenceNoiseShader2.setFloatUniform("in_noiseMove", f5, f6, f7);
                TurbulenceNoiseView.this.turbulenceNoiseShader.setFloatUniform("in_opacity", turbulenceNoiseAnimationConfig.luminosityMultiplier);
                TurbulenceNoiseView.this.invalidate();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$play$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TurbulenceNoiseView.this.currentAnimator = null;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        ofFloat.start();
        this.currentAnimator = ofFloat;
    }

    public final void playEaseIn(final float f, final float f2, final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        Intrinsics.checkNotNull(turbulenceNoiseAnimationConfig);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration((long) turbulenceNoiseAnimationConfig.easeInDuration);
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        final float f3 = turbulenceNoiseShader.noiseOffsetX;
        final float f4 = turbulenceNoiseShader.noiseOffsetY;
        final float f5 = turbulenceNoiseShader.noiseOffsetZ;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$playEaseIn$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TurbulenceNoiseShader turbulenceNoiseShader2 = TurbulenceNoiseView.this.turbulenceNoiseShader;
                float f6 = f + f3;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                float f7 = (turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f6;
                float f8 = (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2 + f4;
                float f9 = (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f5;
                turbulenceNoiseShader2.noiseOffsetX = f7;
                turbulenceNoiseShader2.noiseOffsetY = f8;
                turbulenceNoiseShader2.noiseOffsetZ = f9;
                turbulenceNoiseShader2.setFloatUniform("in_noiseMove", f7, f8, f9);
                TurbulenceNoiseView.this.turbulenceNoiseShader.setFloatUniform("in_opacity", floatValue * turbulenceNoiseAnimationConfig.luminosityMultiplier);
                TurbulenceNoiseView.this.invalidate();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$playEaseIn$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TurbulenceNoiseView.this.currentAnimator = null;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        ofFloat.start();
        this.currentAnimator = ofFloat;
    }

    public final void playEaseOut(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        Intrinsics.checkNotNull(turbulenceNoiseAnimationConfig);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration((long) turbulenceNoiseAnimationConfig.easeOutDuration);
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$playEaseOut$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TurbulenceNoiseShader turbulenceNoiseShader2 = TurbulenceNoiseView.this.turbulenceNoiseShader;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                float f5 = (turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4;
                float f6 = (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2;
                float f7 = (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3;
                turbulenceNoiseShader2.noiseOffsetX = f5;
                turbulenceNoiseShader2.noiseOffsetY = f6;
                turbulenceNoiseShader2.noiseOffsetZ = f7;
                turbulenceNoiseShader2.setFloatUniform("in_noiseMove", f5, f6, f7);
                TurbulenceNoiseView.this.turbulenceNoiseShader.setFloatUniform("in_opacity", (1.0f - floatValue) * turbulenceNoiseAnimationConfig.luminosityMultiplier);
                TurbulenceNoiseView.this.invalidate();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$playEaseOut$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TurbulenceNoiseView.this.currentAnimator = null;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        ofFloat.start();
        this.currentAnimator = ofFloat;
    }

    public static /* synthetic */ void getCurrentAnimator$annotations() {
    }

    public static /* synthetic */ void getNoiseConfig$annotations() {
    }
}
