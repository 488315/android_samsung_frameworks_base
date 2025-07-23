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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TurbulenceNoiseView extends View {
    public ValueAnimator currentAnimator;
    public TurbulenceNoiseAnimationConfig noiseConfig;
    public final Paint paint;
    public TurbulenceNoiseShader turbulenceNoiseShader;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000c, code lost:
    
        if ((r0 != null ? r0.baseType : null) != r2) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initShader(com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader.Companion.Type r2, com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig r3) {
        /*
            r1 = this;
            r1.noiseConfig = r3
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r0 = r1.turbulenceNoiseShader
            if (r0 == 0) goto Le
            if (r0 == 0) goto Lb
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader$Companion$Type r0 = r0.baseType
            goto Lc
        Lb:
            r0 = 0
        Lc:
            if (r0 == r2) goto L1a
        Le:
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r0 = new com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader
            r0.<init>(r2)
            r1.turbulenceNoiseShader = r0
            android.graphics.Paint r2 = r1.paint
            r2.setShader(r0)
        L1a:
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r1 = r1.turbulenceNoiseShader
            r1.getClass()
            r1.applyConfig(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.initShader(com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader$Companion$Type, com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig):void");
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration((long) turbulenceNoiseAnimationConfig.maxDuration);
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$play$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                TurbulenceNoiseShader turbulenceNoiseShader2 = TurbulenceNoiseShader.this;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3);
                TurbulenceNoiseShader.this.setOpacity(turbulenceNoiseAnimationConfig.luminosityMultiplier);
                this.invalidate();
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

    public final void playEaseIn(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration((long) turbulenceNoiseAnimationConfig.easeInDuration);
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$playEaseIn$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TurbulenceNoiseShader turbulenceNoiseShader2 = TurbulenceNoiseShader.this;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3);
                TurbulenceNoiseShader.this.setOpacity(floatValue * turbulenceNoiseAnimationConfig.luminosityMultiplier);
                this.invalidate();
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
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration((long) turbulenceNoiseAnimationConfig.easeOutDuration);
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView$playEaseOut$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TurbulenceNoiseShader turbulenceNoiseShader2 = TurbulenceNoiseShader.this;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig2 = turbulenceNoiseAnimationConfig;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig2.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig2.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig2.noiseMoveSpeedZ) + f3);
                TurbulenceNoiseShader.this.setOpacity((1.0f - floatValue) * turbulenceNoiseAnimationConfig.luminosityMultiplier);
                this.invalidate();
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

    public static /* synthetic */ void getTurbulenceNoiseShader$annotations() {
    }
}
