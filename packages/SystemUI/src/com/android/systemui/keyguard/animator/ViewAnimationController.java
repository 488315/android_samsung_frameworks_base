package com.android.systemui.keyguard.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, f);
        ofFloat.setInterpolator(this.alphaPathInterpolator);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        Unit unit = Unit.INSTANCE;
        animatorSet.playTogether(ofFloat);
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
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), f2);
        KeyguardTouchAnimator keyguardTouchAnimator = this.keyguardTouchAnimator;
        if (keyguardTouchAnimator != null && keyguardTouchAnimator.hasView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1) && view.equals(keyguardTouchAnimator.getView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1))) {
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.animator.ViewAnimationController$setViewAnimation$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ViewAnimationController.this.keyguardTouchAnimator.keyguardStatusViewAlphaChangeControllerWrapper.updateAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        animatorSet.playTogether(ofFloat);
    }

    public final void setViewScaleAnimation(AnimatorSet animatorSet, View view, float f, long j, long j2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, f);
        ofFloat.setInterpolator(this.scalePathInterpolator);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        Unit unit = Unit.INSTANCE;
        animatorSet.playTogether(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, f);
        ofFloat2.setInterpolator(this.scalePathInterpolator);
        ofFloat2.setDuration(j);
        ofFloat2.setStartDelay(j2);
        animatorSet.playTogether(ofFloat2);
    }
}
