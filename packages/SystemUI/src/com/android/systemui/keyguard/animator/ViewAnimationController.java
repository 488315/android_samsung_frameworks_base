package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import kotlin.Unit;

/* loaded from: classes2.dex */
public abstract class ViewAnimationController {
    public final KeyguardTouchAnimator keyguardTouchAnimator;
    public final Interpolator SINE_OUT_33 = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
    public final Interpolator SINE_IN_33 = new PathInterpolator(0.33f, 0.0f, 0.83f, 0.83f);
    public final PathInterpolator scalePathInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public final PathInterpolator alphaPathInterpolator = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);

    public ViewAnimationController(KeyguardTouchAnimator keyguardTouchAnimator) {
        this.keyguardTouchAnimator = keyguardTouchAnimator;
    }

    public final boolean isKeyguardState() {
        return this.keyguardTouchAnimator.sbStateController.getState() == 1;
    }

    public final void setViewAlphaAnimation(AnimatorSet animatorSet, View view, float f, long j, long j2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, f);
        objectAnimatorOfFloat.setInterpolator(this.alphaPathInterpolator);
        objectAnimatorOfFloat.setDuration(j);
        objectAnimatorOfFloat.setStartDelay(j2);
        Unit unit = Unit.INSTANCE;
        animatorSet.playTogether(objectAnimatorOfFloat);
    }

    public final void setViewAnimation(AnimatorSet animatorSet, View view, float f, float f2) {
        if (view == null) {
            return;
        }
        if (f != -1.0f) {
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, view.getScaleX(), f));
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, view.getScaleY(), f));
        }
        if (f2 == -1.0f) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), f2);
        KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
        if (keyguardTouchAnimator != null && keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1) && view.equals(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1))) {
            objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.animator.ViewAnimationController.setViewAnimation.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ViewAnimationController.this.keyguardTouchAnimator.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        animatorSet.playTogether(objectAnimatorOfFloat);
    }

    public final void setViewScaleAnimation(AnimatorSet animatorSet, View view, float f, long j, long j2) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, f);
        objectAnimatorOfFloat.setInterpolator(this.scalePathInterpolator);
        objectAnimatorOfFloat.setDuration(j);
        objectAnimatorOfFloat.setStartDelay(j2);
        Unit unit = Unit.INSTANCE;
        animatorSet.playTogether(objectAnimatorOfFloat);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, f);
        objectAnimatorOfFloat2.setInterpolator(this.scalePathInterpolator);
        objectAnimatorOfFloat2.setDuration(j);
        objectAnimatorOfFloat2.setStartDelay(j2);
        animatorSet.playTogether(objectAnimatorOfFloat2);
    }
}
