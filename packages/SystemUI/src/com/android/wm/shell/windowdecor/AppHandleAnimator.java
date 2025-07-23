package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageButton;
import com.android.wm.shell.shared.animation.Interpolators;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AppHandleAnimator {
    public static final PathInterpolator APP_HANDLE_ANIMATION_INTERPOLATOR;
    public static final Interpolator HANDLE_ANIMATION_INTERPOLATOR;
    public ObjectAnimator animator;
    public final View appHandleView;
    public final ImageButton captionHandle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        APP_HANDLE_ANIMATION_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        HANDLE_ANIMATION_INTERPOLATOR = Interpolators.FAST_OUT_SLOW_IN;
    }

    public AppHandleAnimator(View view, ImageButton imageButton) {
        this.appHandleView = view;
        this.captionHandle = imageButton;
    }

    public final void animateCaptionHandleAlpha(float f, float f2) {
        cancel();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.captionHandle, (Property<ImageButton, Float>) View.ALPHA, f, f2);
        ofFloat.setDuration(100L);
        ofFloat.setInterpolator(HANDLE_ANIMATION_INTERPOLATOR);
        ofFloat.start();
        this.animator = ofFloat;
    }

    public final void animateVisibilityChange(int i) {
        if (i != 0) {
            cancel();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.appHandleView, (Property<View, Float>) View.ALPHA, 0.0f);
            ofFloat.setDuration(340L);
            ofFloat.setInterpolator(APP_HANDLE_ANIMATION_INTERPOLATOR);
            ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.AppHandleAnimator$animateHideAppHandle$lambda$3$$inlined$doOnEnd$1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    AppHandleAnimator.this.appHandleView.setVisibility(8);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            ofFloat.start();
            this.animator = ofFloat;
            return;
        }
        cancel();
        this.appHandleView.setAlpha(0.0f);
        this.appHandleView.setVisibility(0);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.appHandleView, (Property<View, Float>) View.ALPHA, 1.0f);
        ofFloat2.setDuration(275L);
        ofFloat2.setInterpolator(APP_HANDLE_ANIMATION_INTERPOLATOR);
        ofFloat2.start();
        this.animator = ofFloat2;
    }

    public final void cancel() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
        }
        ObjectAnimator objectAnimator2 = this.animator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
        }
        this.animator = null;
    }
}
