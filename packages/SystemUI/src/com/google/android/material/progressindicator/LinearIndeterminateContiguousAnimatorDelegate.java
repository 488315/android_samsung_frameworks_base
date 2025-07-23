package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LinearIndeterminateContiguousAnimatorDelegate extends IndeterminateAnimatorDelegate {
    public static final AnonymousClass2 ANIMATION_FRACTION = new Property(Float.class, "animationFraction") { // from class: com.google.android.material.progressindicator.LinearIndeterminateContiguousAnimatorDelegate.2
        @Override // android.util.Property
        public final Object get(Object obj) {
            return Float.valueOf(((LinearIndeterminateContiguousAnimatorDelegate) obj).animationFraction);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            ((LinearIndeterminateContiguousAnimatorDelegate) obj).setAnimationFraction(((Float) obj2).floatValue());
        }
    };
    public float animationFraction;
    public ObjectAnimator animator;
    public final LinearProgressIndicatorSpec baseSpec;
    public boolean dirtyColors;
    public final FastOutSlowInInterpolator interpolator;
    public int newIndicatorColorIndex;

    public LinearIndeterminateContiguousAnimatorDelegate(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(3);
        this.newIndicatorColorIndex = 1;
        this.baseSpec = linearProgressIndicatorSpec;
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
    public void resetPropertiesForNewStart() {
        this.dirtyColors = true;
        this.newIndicatorColorIndex = 1;
        ArrayList arrayList = (ArrayList) this.activeIndicators;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) obj;
            LinearProgressIndicatorSpec linearProgressIndicatorSpec = this.baseSpec;
            activeIndicator.color = linearProgressIndicatorSpec.indicatorColors[0];
            activeIndicator.gapSize = linearProgressIndicatorSpec.indicatorTrackGapSize / 2;
        }
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0)).startFraction = 0.0f;
        float fractionInRange = IndeterminateAnimatorDelegate.getFractionInRange((int) (f * 333.0f), 0, 667);
        DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0);
        DrawingDelegate.ActiveIndicator activeIndicator2 = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(1);
        FastOutSlowInInterpolator fastOutSlowInInterpolator = this.interpolator;
        float interpolation = fastOutSlowInInterpolator.getInterpolation(fractionInRange);
        activeIndicator2.startFraction = interpolation;
        activeIndicator.endFraction = interpolation;
        DrawingDelegate.ActiveIndicator activeIndicator3 = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(1);
        DrawingDelegate.ActiveIndicator activeIndicator4 = (DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(2);
        float interpolation2 = fastOutSlowInInterpolator.getInterpolation(fractionInRange + 0.49925038f);
        activeIndicator4.startFraction = interpolation2;
        activeIndicator3.endFraction = interpolation2;
        ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(2)).endFraction = 1.0f;
        if (this.dirtyColors && ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(1)).endFraction < 1.0f) {
            ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(2)).color = ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(1)).color;
            ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(1)).color = ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0)).color;
            ((DrawingDelegate.ActiveIndicator) ((ArrayList) this.activeIndicators).get(0)).color = this.baseSpec.indicatorColors[this.newIndicatorColorIndex];
            this.dirtyColors = false;
        }
        this.drawable.invalidateSelf();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void startAnimator() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, 0.0f, 1.0f);
            this.animator = ofFloat;
            ofFloat.setDuration(333L);
            this.animator.setInterpolator(null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.LinearIndeterminateContiguousAnimatorDelegate.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    LinearIndeterminateContiguousAnimatorDelegate linearIndeterminateContiguousAnimatorDelegate = LinearIndeterminateContiguousAnimatorDelegate.this;
                    linearIndeterminateContiguousAnimatorDelegate.newIndicatorColorIndex = (linearIndeterminateContiguousAnimatorDelegate.newIndicatorColorIndex + 1) % linearIndeterminateContiguousAnimatorDelegate.baseSpec.indicatorColors.length;
                    linearIndeterminateContiguousAnimatorDelegate.dirtyColors = true;
                }
            });
        }
        resetPropertiesForNewStart();
        this.animator.start();
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void requestCancelAnimatorAfterCurrentCycle() {
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void unregisterAnimatorsCompleteCallback() {
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void registerAnimatorsCompleteCallback(BaseProgressIndicator.AnonymousClass3 anonymousClass3) {
    }
}
