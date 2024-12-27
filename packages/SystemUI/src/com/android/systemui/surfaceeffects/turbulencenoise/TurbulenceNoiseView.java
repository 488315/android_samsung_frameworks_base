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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TurbulenceNoiseView extends View {
    public ValueAnimator currentAnimator;
    public TurbulenceNoiseAnimationConfig noiseConfig;
    public final Paint paint;
    public TurbulenceNoiseShader turbulenceNoiseShader;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    
        if ((r0 != null ? r0.baseType : null) != r3) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initShader(com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader.Companion.Type r3, com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig r4) {
        /*
            r2 = this;
            r2.noiseConfig = r4
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r0 = r2.turbulenceNoiseShader
            if (r0 == 0) goto Le
            if (r0 == 0) goto Lb
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader$Companion$Type r0 = r0.baseType
            goto Lc
        Lb:
            r0 = 0
        Lc:
            if (r0 == r3) goto L1a
        Le:
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r0 = new com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader
            r0.<init>(r3)
            r2.turbulenceNoiseShader = r0
            android.graphics.Paint r3 = r2.paint
            r3.setShader(r0)
        L1a:
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r2 = r2.turbulenceNoiseShader
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            r2.getClass()
            float r3 = r4.gridCount
            java.lang.String r0 = "in_gridNum"
            r2.setFloatUniform(r0, r3)
            java.lang.String r3 = "in_pixelDensity"
            float r0 = r4.pixelDensity
            r2.setFloatUniform(r3, r0)
            java.lang.String r3 = "in_color"
            int r0 = r4.color
            r2.setColorUniform(r3, r0)
            java.lang.String r3 = "in_screenColor"
            int r0 = r4.screenColor
            r2.setColorUniform(r3, r0)
            java.lang.String r3 = "in_size"
            float r0 = r4.width
            float r1 = r4.height
            r2.setFloatUniform(r3, r0, r1)
            r3 = 981668463(0x3a83126f, float:0.001)
            float r3 = java.lang.Float.max(r1, r3)
            float r0 = r0 / r3
            java.lang.String r3 = "in_aspectRatio"
            r2.setFloatUniform(r3, r0)
            java.lang.String r3 = "in_lumaMatteBlendFactor"
            float r0 = r4.lumaMatteBlendFactor
            r2.setFloatUniform(r3, r0)
            java.lang.String r3 = "in_lumaMatteOverallBrightness"
            float r0 = r4.lumaMatteOverallBrightness
            r2.setFloatUniform(r3, r0)
            boolean r3 = r4.shouldInverseNoiseLuminosity
            if (r3 == 0) goto L69
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            goto L6b
        L69:
            r3 = 1065353216(0x3f800000, float:1.0)
        L6b:
            java.lang.String r0 = "in_inverseLuma"
            r2.setFloatUniform(r0, r3)
            float r3 = r4.noiseOffsetY
            float r0 = r4.noiseOffsetZ
            float r4 = r4.noiseOffsetX
            r2.setNoiseMove(r4, r3, r0)
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
        Intrinsics.checkNotNull(turbulenceNoiseAnimationConfig);
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        Intrinsics.checkNotNull(turbulenceNoiseShader);
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
        Intrinsics.checkNotNull(turbulenceNoiseAnimationConfig);
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        Intrinsics.checkNotNull(turbulenceNoiseShader);
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
        Intrinsics.checkNotNull(turbulenceNoiseAnimationConfig);
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        Intrinsics.checkNotNull(turbulenceNoiseShader);
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
