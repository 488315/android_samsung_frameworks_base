package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LinearIndeterminateContiguousAnimatorDelegate extends IndeterminateAnimatorDelegate {
    public static final C43402 ANIMATION_FRACTION = new Property(Float.class, "animationFraction") { // from class: com.google.android.material.progressindicator.LinearIndeterminateContiguousAnimatorDelegate.2
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

    public void resetPropertiesForNewStart() {
        this.dirtyColors = true;
        this.newIndicatorColorIndex = 1;
        Arrays.fill(this.segmentColors, MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.totalAlpha));
    }

    public void setAnimationFraction(float f) {
        this.animationFraction = f;
        float[] fArr = this.segmentPositions;
        fArr[0] = 0.0f;
        float f2 = (((int) (f * 333.0f)) - 0) / 667;
        FastOutSlowInInterpolator fastOutSlowInInterpolator = this.interpolator;
        float interpolation = fastOutSlowInInterpolator.getInterpolation(f2);
        fArr[2] = interpolation;
        fArr[1] = interpolation;
        float interpolation2 = fastOutSlowInInterpolator.getInterpolation(f2 + 0.49925038f);
        fArr[4] = interpolation2;
        fArr[3] = interpolation2;
        fArr[5] = 1.0f;
        if (this.dirtyColors && interpolation2 < 1.0f) {
            int[] iArr = this.segmentColors;
            iArr[2] = iArr[1];
            iArr[1] = iArr[0];
            iArr[0] = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[this.newIndicatorColorIndex], this.drawable.totalAlpha);
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
    public final void registerAnimatorsCompleteCallback(BaseProgressIndicator.C43293 c43293) {
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void requestCancelAnimatorAfterCurrentCycle() {
    }

    @Override // com.google.android.material.progressindicator.IndeterminateAnimatorDelegate
    public final void unregisterAnimatorsCompleteCallback() {
    }
}
