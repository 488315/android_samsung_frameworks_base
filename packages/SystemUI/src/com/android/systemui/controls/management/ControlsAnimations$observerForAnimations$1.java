package com.android.systemui.controls.management;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsAnimations$observerForAnimations$1 implements LifecycleObserver {
    public final /* synthetic */ ViewGroup $view;
    public final /* synthetic */ Window $window;
    public boolean showAnimation;

    public ControlsAnimations$observerForAnimations$1(Intent intent, ViewGroup viewGroup, boolean z, Window window) {
        this.$view = viewGroup;
        this.$window = window;
        this.showAnimation = intent.getBooleanExtra("extra_animate", false);
        viewGroup.setTransitionGroup(true);
        viewGroup.setTransitionAlpha(0.0f);
        if (ControlsAnimations.translationY == -1.0f) {
            if (z) {
                ControlsAnimations.translationY = viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.global_actions_controls_y_translation);
            } else {
                ControlsAnimations.translationY = 0.0f;
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public final void enterAnimation() {
        if (this.showAnimation) {
            ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
            ViewGroup viewGroup = this.$view;
            controlsAnimations.getClass();
            ControlsAnimations.enterAnimation(viewGroup).start();
            this.showAnimation = false;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public final void resetAnimation() {
        this.$view.setTranslationY(0.0f);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public final void setup() {
        Window window = this.$window;
        ViewGroup viewGroup = this.$view;
        window.setAllowEnterTransitionOverlap(true);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        int id = viewGroup.getId();
        controlsAnimations.getClass();
        final int i = 0;
        WindowTransition windowTransition = new WindowTransition(new Function1() { // from class: com.android.systemui.controls.management.ControlsAnimations$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                View view = (View) obj;
                switch (i) {
                    case 0:
                        ControlsAnimations.INSTANCE.getClass();
                        return ControlsAnimations.enterAnimation(view);
                    default:
                        ControlsAnimations controlsAnimations2 = ControlsAnimations.INSTANCE;
                        Log.d("ControlsUiController", "Exit animation for " + view);
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", 0.0f);
                        Interpolator interpolator = Interpolators.ACCELERATE;
                        ofFloat.setInterpolator(interpolator);
                        ofFloat.setDuration(183L);
                        view.setTranslationY(0.0f);
                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", -ControlsAnimations.translationY);
                        ofFloat2.setInterpolator(interpolator);
                        ofFloat2.setDuration(183L);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloat, ofFloat2);
                        return animatorSet;
                }
            }
        });
        windowTransition.addTarget(id);
        window.setEnterTransition(windowTransition);
        int id2 = viewGroup.getId();
        final int i2 = 1;
        WindowTransition windowTransition2 = new WindowTransition(new Function1() { // from class: com.android.systemui.controls.management.ControlsAnimations$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                View view = (View) obj;
                switch (i2) {
                    case 0:
                        ControlsAnimations.INSTANCE.getClass();
                        return ControlsAnimations.enterAnimation(view);
                    default:
                        ControlsAnimations controlsAnimations2 = ControlsAnimations.INSTANCE;
                        Log.d("ControlsUiController", "Exit animation for " + view);
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", 0.0f);
                        Interpolator interpolator = Interpolators.ACCELERATE;
                        ofFloat.setInterpolator(interpolator);
                        ofFloat.setDuration(183L);
                        view.setTranslationY(0.0f);
                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", -ControlsAnimations.translationY);
                        ofFloat2.setInterpolator(interpolator);
                        ofFloat2.setDuration(183L);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloat, ofFloat2);
                        return animatorSet;
                }
            }
        });
        windowTransition2.addTarget(id2);
        window.setExitTransition(windowTransition2);
        int id3 = viewGroup.getId();
        final int i3 = 0;
        WindowTransition windowTransition3 = new WindowTransition(new Function1() { // from class: com.android.systemui.controls.management.ControlsAnimations$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                View view = (View) obj;
                switch (i3) {
                    case 0:
                        ControlsAnimations.INSTANCE.getClass();
                        return ControlsAnimations.enterAnimation(view);
                    default:
                        ControlsAnimations controlsAnimations2 = ControlsAnimations.INSTANCE;
                        Log.d("ControlsUiController", "Exit animation for " + view);
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", 0.0f);
                        Interpolator interpolator = Interpolators.ACCELERATE;
                        ofFloat.setInterpolator(interpolator);
                        ofFloat.setDuration(183L);
                        view.setTranslationY(0.0f);
                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", -ControlsAnimations.translationY);
                        ofFloat2.setInterpolator(interpolator);
                        ofFloat2.setDuration(183L);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloat, ofFloat2);
                        return animatorSet;
                }
            }
        });
        windowTransition3.addTarget(id3);
        window.setReenterTransition(windowTransition3);
        int id4 = viewGroup.getId();
        final int i4 = 1;
        WindowTransition windowTransition4 = new WindowTransition(new Function1() { // from class: com.android.systemui.controls.management.ControlsAnimations$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                View view = (View) obj;
                switch (i4) {
                    case 0:
                        ControlsAnimations.INSTANCE.getClass();
                        return ControlsAnimations.enterAnimation(view);
                    default:
                        ControlsAnimations controlsAnimations2 = ControlsAnimations.INSTANCE;
                        Log.d("ControlsUiController", "Exit animation for " + view);
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", 0.0f);
                        Interpolator interpolator = Interpolators.ACCELERATE;
                        ofFloat.setInterpolator(interpolator);
                        ofFloat.setDuration(183L);
                        view.setTranslationY(0.0f);
                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", -ControlsAnimations.translationY);
                        ofFloat2.setInterpolator(interpolator);
                        ofFloat2.setDuration(183L);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofFloat, ofFloat2);
                        return animatorSet;
                }
            }
        });
        windowTransition4.addTarget(id4);
        window.setReturnTransition(windowTransition4);
    }
}
