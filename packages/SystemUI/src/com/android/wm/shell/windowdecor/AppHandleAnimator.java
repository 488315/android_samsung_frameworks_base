package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageButton;
import com.android.wm.shell.shared.animation.Interpolators;
import com.samsung.android.rune.CoreRune;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class AppHandleAnimator {
    public static final PathInterpolator APP_HANDLE_ANIMATION_INTERPOLATOR;
    public static final Interpolator HANDLE_ANIMATION_INTERPOLATOR;
    public ObjectAnimator animator;
    public final View appHandleView;
    public final ImageButton captionHandle;

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
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.captionHandle, (Property<ImageButton, Float>) View.ALPHA, f, f2);
        objectAnimatorOfFloat.setDuration(100L);
        objectAnimatorOfFloat.setInterpolator(HANDLE_ANIMATION_INTERPOLATOR);
        objectAnimatorOfFloat.start();
        this.animator = objectAnimatorOfFloat;
    }

    public final void animateVisibilityChange(int i) {
        if (i != 0) {
            cancel();
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.appHandleView, (Property<View, Float>) View.ALPHA, 0.0f);
            objectAnimatorOfFloat.setDuration(340L);
            objectAnimatorOfFloat.setInterpolator(APP_HANDLE_ANIMATION_INTERPOLATOR);
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.AppHandleAnimator$animateHideAppHandle$lambda$3$$inlined$doOnEnd$1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    this.this$0.appHandleView.setVisibility(8);
                    if (!CoreRune.MW_CAPTION_HANDLE || this.this$0.appHandleView.getRootView() == null) {
                        return;
                    }
                    this.this$0.appHandleView.getRootView().setVisibility(8);
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
            objectAnimatorOfFloat.start();
            this.animator = objectAnimatorOfFloat;
            return;
        }
        cancel();
        this.appHandleView.setAlpha(0.0f);
        this.appHandleView.setVisibility(0);
        if (CoreRune.MW_CAPTION_HANDLE && this.appHandleView.getRootView() != null) {
            this.appHandleView.getRootView().setVisibility(0);
        }
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.appHandleView, (Property<View, Float>) View.ALPHA, 1.0f);
        objectAnimatorOfFloat2.setDuration(275L);
        objectAnimatorOfFloat2.setInterpolator(APP_HANDLE_ANIMATION_INTERPOLATOR);
        objectAnimatorOfFloat2.start();
        this.animator = objectAnimatorOfFloat2;
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
