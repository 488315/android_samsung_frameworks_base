package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.util.Property;
import android.view.View;
import com.android.wm.shell.shared.animation.Interpolators;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class HandleHideAnimator {
    public final View mHandleView;
    public final Handler mHandler;
    public ObjectAnimator mHideHandleAnim;
    public final HandleHideAnimator$$ExternalSyntheticLambda0 mHideRunnable = new HandleHideAnimator$$ExternalSyntheticLambda0(this, 0);
    public boolean mIsHandleHideEnabled = false;
    public boolean mIsHandleMenuActive;
    public boolean mIsHandleViewVisible;
    public ObjectAnimator mShowHandleAnim;

    public HandleHideAnimator(Handler handler, View view) {
        this.mHandler = handler;
        this.mHandleView = view;
    }

    public final void cancelAllHandleAnim() {
        ObjectAnimator objectAnimator = this.mShowHandleAnim;
        if (objectAnimator != null && objectAnimator.isStarted()) {
            this.mShowHandleAnim.cancel();
        }
        ObjectAnimator objectAnimator2 = this.mHideHandleAnim;
        if (objectAnimator2 != null && objectAnimator2.isStarted()) {
            this.mHideHandleAnim.cancel();
        }
        this.mHandler.removeCallbacks(this.mHideRunnable);
    }

    public final void delayedHide() {
        this.mHandler.postDelayed(this.mHideRunnable, 3000L);
    }

    public final void hide(boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mHandleView, (Property<View, Float>) View.ALPHA, 0.0f);
        this.mHideHandleAnim = ofFloat;
        ofFloat.setDuration(200L).setInterpolator(z ? Interpolators.FAST_OUT_SLOW_IN : Interpolators.LINEAR);
        this.mHideHandleAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator, boolean z2) {
                HandleHideAnimator.this.mHandleView.setVisibility(8);
                HandleHideAnimator handleHideAnimator = HandleHideAnimator.this;
                handleHideAnimator.mIsHandleViewVisible = false;
                ObjectAnimator objectAnimator = handleHideAnimator.mHideHandleAnim;
                if (objectAnimator != null) {
                    objectAnimator.removeAllListeners();
                    HandleHideAnimator.this.mHideHandleAnim = null;
                }
            }
        });
        this.mHideHandleAnim.start();
    }

    public final void show(final Runnable runnable, final boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mHandleView, (Property<View, Float>) View.ALPHA, 1.0f);
        this.mShowHandleAnim = ofFloat;
        ofFloat.setDuration(200L).setInterpolator(z ? Interpolators.FAST_OUT_SLOW_IN : Interpolators.LINEAR);
        this.mHandleView.setVisibility(0);
        this.mIsHandleViewVisible = true;
        this.mShowHandleAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator, boolean z2) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                if (z) {
                    HandleHideAnimator.this.mIsHandleMenuActive = false;
                }
                ObjectAnimator objectAnimator = HandleHideAnimator.this.mShowHandleAnim;
                if (objectAnimator != null) {
                    objectAnimator.removeAllListeners();
                    HandleHideAnimator.this.mShowHandleAnim = null;
                }
            }
        });
        this.mShowHandleAnim.start();
    }
}
