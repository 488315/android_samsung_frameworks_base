package com.android.systemui.surfaceeffects.turbulencenoise;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class TurbulenceNoiseView extends View {
    public ValueAnimator currentAnimator;
    public TurbulenceNoiseAnimationConfig noiseConfig;
    public final Paint paint;
    public TurbulenceNoiseShader turbulenceNoiseShader;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TurbulenceNoiseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
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

    /* JADX WARN: Removed duplicated region for block: B:8:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initShader(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig) {
        this.noiseConfig = turbulenceNoiseAnimationConfig;
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        if (turbulenceNoiseShader == null) {
            TurbulenceNoiseShader turbulenceNoiseShader2 = new TurbulenceNoiseShader(type);
            this.turbulenceNoiseShader = turbulenceNoiseShader2;
            this.paint.setShader(turbulenceNoiseShader2);
        } else {
            if ((turbulenceNoiseShader != null ? turbulenceNoiseShader.baseType : null) != type) {
            }
        }
        TurbulenceNoiseShader turbulenceNoiseShader3 = this.turbulenceNoiseShader;
        turbulenceNoiseShader3.getClass();
        turbulenceNoiseShader3.applyConfig(turbulenceNoiseAnimationConfig);
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
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) turbulenceNoiseAnimationConfig.maxDuration);
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.play.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                TurbulenceNoiseShader turbulenceNoiseShader2 = turbulenceNoiseShader;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3);
                turbulenceNoiseShader.setOpacity(turbulenceNoiseAnimationConfig.luminosityMultiplier);
                this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.play.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TurbulenceNoiseView.this.currentAnimator = null;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public final void playEaseIn(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) turbulenceNoiseAnimationConfig.easeInDuration);
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseIn.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TurbulenceNoiseShader turbulenceNoiseShader2 = turbulenceNoiseShader;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3);
                turbulenceNoiseShader.setOpacity(fFloatValue * turbulenceNoiseAnimationConfig.luminosityMultiplier);
                this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseIn.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TurbulenceNoiseView.this.currentAnimator = null;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public final void playEaseOut(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) turbulenceNoiseAnimationConfig.easeOutDuration);
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseOut.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TurbulenceNoiseShader turbulenceNoiseShader2 = turbulenceNoiseShader;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3);
                turbulenceNoiseShader.setOpacity((1.0f - fFloatValue) * turbulenceNoiseAnimationConfig.luminosityMultiplier);
                this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseOut.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TurbulenceNoiseView.this.currentAnimator = null;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public static /* synthetic */ void getCurrentAnimator$annotations() {
    }

    public static /* synthetic */ void getNoiseConfig$annotations() {
    }

    public static /* synthetic */ void getTurbulenceNoiseShader$annotations() {
    }
}
