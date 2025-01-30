package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    public static final Animator exitAnimation(View view, final Runnable runnable) {
        Log.d("ControlsUiController", "Exit animation for " + view);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", 0.0f);
        Interpolator interpolator = Interpolators.ACCELERATE;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(183L);
        view.setTranslationY(0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", -translationY);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setDuration(183L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        if (runnable != null) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.management.ControlsAnimations$exitAnimation$1$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                }
            });
        }
        return animatorSet;
    }
}
