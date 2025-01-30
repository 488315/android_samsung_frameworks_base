package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.BaseProgressIndicator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CircularIndeterminateAnimatorDelegate extends IndeterminateAnimatorDelegate {
    public static final C43333 ANIMATION_FRACTION;
    public static final C43344 COMPLETE_END_FRACTION;
    public float animationFraction;
    public ObjectAnimator animator;
    public Animatable2Compat.AnimationCallback animatorCompleteCallback;
    public final CircularProgressIndicatorSpec baseSpec;
    public ObjectAnimator completeEndAnimator;
    public float completeEndFraction;
    public int indicatorColorIndexOffset;
    public final FastOutSlowInInterpolator interpolator;
    public static final int[] DELAY_TO_EXPAND_IN_MS = {0, 1350, 2700, 4050};
    public static final int[] DELAY_TO_COLLAPSE_IN_MS = {667, 2017, 3367, 4717};
    public static final int[] DELAY_TO_FADE_IN_MS = {1000, 2350, 3700, 5050};

    /* JADX WARN: Type inference failed for: r0v6, types: [com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate$3] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate$4] */
    static {
        Class<Float> cls = Float.class;
        ANIMATION_FRACTION = new Property(cls, "animationFraction") { // from class: com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate.3
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((CircularIndeterminateAnimatorDelegate) obj).animationFraction);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                ((CircularIndeterminateAnimatorDelegate) obj).setAnimationFraction(((Float) obj2).floatValue());
            }
        };
        COMPLETE_END_FRACTION = new Property(cls, "completeEndFraction") { // from class: com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate.4
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((CircularIndeterminateAnimatorDelegate) obj).completeEndFraction);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                ((CircularIndeterminateAnimatorDelegate) obj).completeEndFraction = ((Float) obj2).floatValue();
            }
        };
    }

    public CircularIndeterminateAnimatorDelegate(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.indicatorColorIndexOffset = 0;
        this.animatorCompleteCallback = null;
        this.baseSpec = circularProgressIndicatorSpec;
        this.interpolator = new FastOutSlowInInterpolator();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void registerAnimatorsCompleteCallback(BaseProgressIndicator.C43293 c43293) {
        this.animatorCompleteCallback = c43293;
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator == null || objectAnimator.isRunning()) {
            return;
        }
        if (this.drawable.isVisible()) {
            this.completeEndAnimator.start();
        } else {
            cancelAnimatorImmediately();
        }
    }

    public void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        this.segmentColors[0] = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.totalAlpha);
        this.completeEndFraction = 0.0f;
    }

    public void setAnimationFraction(float f) {
        FastOutSlowInInterpolator fastOutSlowInInterpolator;
        this.animationFraction = f;
        int i = (int) (5400.0f * f);
        float f2 = f * 1520.0f;
        float[] fArr = this.segmentPositions;
        fArr[0] = (-20.0f) + f2;
        fArr[1] = f2;
        int i2 = 0;
        while (true) {
            fastOutSlowInInterpolator = this.interpolator;
            if (i2 >= 4) {
                break;
            }
            float f3 = 667;
            fArr[1] = (fastOutSlowInInterpolator.getInterpolation((i - DELAY_TO_EXPAND_IN_MS[i2]) / f3) * 250.0f) + fArr[1];
            fArr[0] = (fastOutSlowInInterpolator.getInterpolation((i - DELAY_TO_COLLAPSE_IN_MS[i2]) / f3) * 250.0f) + fArr[0];
            i2++;
        }
        float f4 = fArr[0];
        float f5 = fArr[1];
        float f6 = ((f5 - f4) * this.completeEndFraction) + f4;
        fArr[0] = f6;
        fArr[0] = f6 / 360.0f;
        fArr[1] = f5 / 360.0f;
        int i3 = 0;
        while (true) {
            if (i3 >= 4) {
                break;
            }
            float f7 = (i - DELAY_TO_FADE_IN_MS[i3]) / 333;
            if (f7 >= 0.0f && f7 <= 1.0f) {
                int i4 = i3 + this.indicatorColorIndexOffset;
                CircularProgressIndicatorSpec circularProgressIndicatorSpec = this.baseSpec;
                int[] iArr = circularProgressIndicatorSpec.indicatorColors;
                int length = i4 % iArr.length;
                int length2 = (length + 1) % iArr.length;
                int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(iArr[length], this.drawable.totalAlpha);
                int compositeARGBWithAlpha2 = MaterialColors.compositeARGBWithAlpha(circularProgressIndicatorSpec.indicatorColors[length2], this.drawable.totalAlpha);
                float interpolation = fastOutSlowInInterpolator.getInterpolation(f7);
                ArgbEvaluatorCompat argbEvaluatorCompat = ArgbEvaluatorCompat.instance;
                Integer valueOf = Integer.valueOf(compositeARGBWithAlpha);
                Integer valueOf2 = Integer.valueOf(compositeARGBWithAlpha2);
                argbEvaluatorCompat.getClass();
                this.segmentColors[0] = ArgbEvaluatorCompat.evaluate(interpolation, valueOf, valueOf2).intValue();
                break;
            }
            i3++;
        }
        this.drawable.invalidateSelf();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void startAnimator() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, 0.0f, 1.0f);
            this.animator = ofFloat;
            ofFloat.setDuration(5400L);
            this.animator.setInterpolator(null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = CircularIndeterminateAnimatorDelegate.this;
                    circularIndeterminateAnimatorDelegate.indicatorColorIndexOffset = (circularIndeterminateAnimatorDelegate.indicatorColorIndexOffset + 4) % circularIndeterminateAnimatorDelegate.baseSpec.indicatorColors.length;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, 0.0f, 1.0f);
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration(333L);
            this.completeEndAnimator.setInterpolator(this.interpolator);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CircularIndeterminateAnimatorDelegate.this.cancelAnimatorImmediately();
                    CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = CircularIndeterminateAnimatorDelegate.this;
                    Animatable2Compat.AnimationCallback animationCallback = circularIndeterminateAnimatorDelegate.animatorCompleteCallback;
                    if (animationCallback != null) {
                        animationCallback.onAnimationEnd(circularIndeterminateAnimatorDelegate.drawable);
                    }
                }
            });
        }
        resetPropertiesForNewStart();
        this.animator.start();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }
}
