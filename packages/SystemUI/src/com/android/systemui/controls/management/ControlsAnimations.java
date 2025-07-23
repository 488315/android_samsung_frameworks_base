package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsAnimations {
    public static final ControlsAnimations INSTANCE = new ControlsAnimations();
    public static float translationY = -1.0f;

    private ControlsAnimations() {
    }

    public static Animator enterAnimation(View view) {
        Log.d("ControlsUiController", "Enter animation for " + view);
        view.setTransitionAlpha(0.0f);
        view.setAlpha(1.0f);
        view.setTranslationY(translationY);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", 0.0f, 1.0f);
        Interpolator interpolator = Interpolators.DECELERATE_QUINT;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setStartDelay(183L);
        ofFloat.setDuration(167L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", 0.0f);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setStartDelay(217L);
        ofFloat2.setDuration(217L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        return animatorSet;
    }
}
