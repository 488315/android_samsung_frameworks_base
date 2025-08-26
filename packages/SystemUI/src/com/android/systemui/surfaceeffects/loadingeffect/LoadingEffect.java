package com.android.systemui.surfaceeffects.loadingeffect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import com.android.systemui.surfaceeffects.PaintDrawCallback;
import com.android.systemui.surfaceeffects.RenderEffectDrawCallback;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class LoadingEffect {
    public final AnimationStateChangedCallback animationStateChangedCallback;
    public final TurbulenceNoiseAnimationConfig config;
    public ValueAnimator currentAnimator;
    public final Paint paint;
    public final PaintDrawCallback paintCallback;
    public AnimationState state;
    public final TurbulenceNoiseShader turbulenceNoiseShader;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class AnimationState {
        public static final /* synthetic */ AnimationState[] $VALUES;
        public static final AnimationState EASE_IN;
        public static final AnimationState EASE_OUT;
        public static final AnimationState MAIN;
        public static final AnimationState NOT_PLAYING;

        static {
            AnimationState animationState = new AnimationState("EASE_IN", 0);
            EASE_IN = animationState;
            AnimationState animationState2 = new AnimationState("MAIN", 1);
            MAIN = animationState2;
            AnimationState animationState3 = new AnimationState("EASE_OUT", 2);
            EASE_OUT = animationState3;
            AnimationState animationState4 = new AnimationState("NOT_PLAYING", 3);
            NOT_PLAYING = animationState4;
            AnimationState[] animationStateArr = {animationState, animationState2, animationState3, animationState4};
            $VALUES = animationStateArr;
            EnumEntriesKt.enumEntries(animationStateArr);
        }

        private AnimationState(String str, int i) {
        }

        public static AnimationState valueOf(String str) {
            return (AnimationState) Enum.valueOf(AnimationState.class, str);
        }

        public static AnimationState[] values() {
            return (AnimationState[]) $VALUES.clone();
        }
    }

    public interface AnimationStateChangedCallback {
        void onStateChanged(AnimationState animationState);
    }

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

    private LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, PaintDrawCallback paintDrawCallback, RenderEffectDrawCallback renderEffectDrawCallback, AnimationStateChangedCallback animationStateChangedCallback) {
        Paint paint;
        this.config = turbulenceNoiseAnimationConfig;
        this.paintCallback = paintDrawCallback;
        this.animationStateChangedCallback = animationStateChangedCallback;
        TurbulenceNoiseShader turbulenceNoiseShader = new TurbulenceNoiseShader(type);
        turbulenceNoiseShader.applyConfig(turbulenceNoiseAnimationConfig);
        this.turbulenceNoiseShader = turbulenceNoiseShader;
        this.state = AnimationState.NOT_PLAYING;
        if (paintDrawCallback != null) {
            paint = new Paint();
            paint.setShader(turbulenceNoiseShader);
        } else {
            paint = null;
        }
        this.paint = paint;
    }

    public final void playEaseOut() {
        if (this.state != AnimationState.MAIN) {
            return;
        }
        setState(AnimationState.EASE_OUT);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) this.config.easeOutDuration);
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        final float f = turbulenceNoiseShader.noiseOffsetX;
        final float f2 = turbulenceNoiseShader.noiseOffsetY;
        final float f3 = turbulenceNoiseShader.noiseOffsetZ;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playEaseOut.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LoadingEffect loadingEffect = LoadingEffect.this;
                TurbulenceNoiseShader turbulenceNoiseShader2 = loadingEffect.turbulenceNoiseShader;
                float f4 = f;
                TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = loadingEffect.config;
                turbulenceNoiseShader2.setNoiseMove((turbulenceNoiseAnimationConfig.noiseMoveSpeedX * currentPlayTime) + f4, (turbulenceNoiseAnimationConfig.noiseMoveSpeedY * currentPlayTime) + f2, (currentPlayTime * turbulenceNoiseAnimationConfig.noiseMoveSpeedZ) + f3);
                LoadingEffect loadingEffect2 = LoadingEffect.this;
                loadingEffect2.turbulenceNoiseShader.setOpacity((1.0f - fFloatValue) * loadingEffect2.config.luminosityMultiplier);
                LoadingEffect loadingEffect3 = LoadingEffect.this;
                PaintDrawCallback paintDrawCallback = loadingEffect3.paintCallback;
                if (paintDrawCallback != null) {
                    Paint paint = loadingEffect3.paint;
                    paint.getClass();
                    paintDrawCallback.onDraw(paint);
                }
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playEaseOut.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                LoadingEffect loadingEffect = LoadingEffect.this;
                loadingEffect.currentAnimator = null;
                loadingEffect.setState(AnimationState.NOT_PLAYING);
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public final void setState(AnimationState animationState) {
        if (this.state != animationState) {
            AnimationStateChangedCallback animationStateChangedCallback = this.animationStateChangedCallback;
            if (animationStateChangedCallback != null) {
                animationStateChangedCallback.onStateChanged(animationState);
            }
            this.state = animationState;
        }
    }

    public /* synthetic */ LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, PaintDrawCallback paintDrawCallback, RenderEffectDrawCallback renderEffectDrawCallback, AnimationStateChangedCallback animationStateChangedCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, turbulenceNoiseAnimationConfig, paintDrawCallback, renderEffectDrawCallback, (i & 16) != 0 ? null : animationStateChangedCallback);
    }

    public /* synthetic */ LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, PaintDrawCallback paintDrawCallback, AnimationStateChangedCallback animationStateChangedCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, turbulenceNoiseAnimationConfig, paintDrawCallback, (i & 8) != 0 ? null : animationStateChangedCallback);
    }

    public LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, PaintDrawCallback paintDrawCallback, AnimationStateChangedCallback animationStateChangedCallback) {
        this(type, turbulenceNoiseAnimationConfig, paintDrawCallback, null, animationStateChangedCallback);
    }

    public /* synthetic */ LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, RenderEffectDrawCallback renderEffectDrawCallback, AnimationStateChangedCallback animationStateChangedCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, turbulenceNoiseAnimationConfig, renderEffectDrawCallback, (i & 8) != 0 ? null : animationStateChangedCallback);
    }

    public LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, RenderEffectDrawCallback renderEffectDrawCallback, AnimationStateChangedCallback animationStateChangedCallback) {
        this(type, turbulenceNoiseAnimationConfig, null, renderEffectDrawCallback, animationStateChangedCallback);
    }
}
