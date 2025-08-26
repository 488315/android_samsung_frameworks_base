package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class CircularIndeterminateAnimatorDelegate extends IndeterminateAnimatorDelegate {
    public static final AnonymousClass3 ANIMATION_FRACTION;
    public static final AnonymousClass4 COMPLETE_END_FRACTION;
    public float animationFraction;
    public ObjectAnimator animator;
    public BaseProgressIndicator.AnonymousClass3 animatorCompleteCallback;
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
    public final void registerAnimatorsCompleteCallback(BaseProgressIndicator.AnonymousClass3 anonymousClass3) {
        this.animatorCompleteCallback = anonymousClass3;
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

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0)).color = this.baseSpec.indicatorColors[0];
        this.completeEndFraction = 0.0f;
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public void setAnimationFraction(float f) {
        FastOutSlowInInterpolator fastOutSlowInInterpolator;
        this.animationFraction = f;
        int i = (int) (f * 5400.0f);
        DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0);
        float f2 = this.animationFraction * 1520.0f;
        activeIndicator.startFraction = (-20.0f) + f2;
        activeIndicator.endFraction = f2;
        int i2 = 0;
        while (true) {
            fastOutSlowInInterpolator = this.interpolator;
            if (i2 >= 4) {
                break;
            }
            activeIndicator.endFraction = (fastOutSlowInInterpolator.getInterpolation(IndeterminateAnimatorDelegate.getFractionInRange(i, DELAY_TO_EXPAND_IN_MS[i2], 667)) * 250.0f) + activeIndicator.endFraction;
            activeIndicator.startFraction = (fastOutSlowInInterpolator.getInterpolation(IndeterminateAnimatorDelegate.getFractionInRange(i, DELAY_TO_COLLAPSE_IN_MS[i2], 667)) * 250.0f) + activeIndicator.startFraction;
            i2++;
        }
        float f3 = activeIndicator.startFraction;
        float f4 = activeIndicator.endFraction;
        activeIndicator.startFraction = (((f4 - f3) * this.completeEndFraction) + f3) / 360.0f;
        activeIndicator.endFraction = f4 / 360.0f;
        int i3 = 0;
        while (true) {
            if (i3 >= 4) {
                break;
            }
            float fractionInRange = IndeterminateAnimatorDelegate.getFractionInRange(i, DELAY_TO_FADE_IN_MS[i3], 333);
            if (fractionInRange >= 0.0f && fractionInRange <= 1.0f) {
                int i4 = i3 + this.indicatorColorIndexOffset;
                int[] iArr = this.baseSpec.indicatorColors;
                int length = i4 % iArr.length;
                int length2 = (length + 1) % iArr.length;
                int i5 = iArr[length];
                int i6 = iArr[length2];
                float interpolation = fastOutSlowInInterpolator.getInterpolation(fractionInRange);
                DrawingDelegate.ActiveIndicator activeIndicator2 = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0);
                ArgbEvaluatorCompat argbEvaluatorCompat = ArgbEvaluatorCompat.instance;
                Integer numValueOf = Integer.valueOf(i5);
                Integer numValueOf2 = Integer.valueOf(i6);
                argbEvaluatorCompat.getClass();
                activeIndicator2.color = ArgbEvaluatorCompat.evaluate(interpolation, numValueOf, numValueOf2).intValue();
                break;
            }
            i3++;
        }
        this.drawable.invalidateSelf();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void startAnimator() {
        if (this.animator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, 0.0f, 1.0f);
            this.animator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(5400L);
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
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, 0.0f, 1.0f);
            this.completeEndAnimator = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(333L);
            this.completeEndAnimator.setInterpolator(this.interpolator);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CircularIndeterminateAnimatorDelegate.this.cancelAnimatorImmediately();
                    CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = CircularIndeterminateAnimatorDelegate.this;
                    BaseProgressIndicator.AnonymousClass3 anonymousClass3 = circularIndeterminateAnimatorDelegate.animatorCompleteCallback;
                    if (anonymousClass3 != null) {
                        anonymousClass3.onAnimationEnd(circularIndeterminateAnimatorDelegate.drawable);
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
