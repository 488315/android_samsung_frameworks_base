package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.os.Handler;
import android.util.Property;
import android.view.View;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public class HandleHideAnimator {
    public final HandleHideAnimator$$ExternalSyntheticLambda0 mDelayedHideFromTouchRunnable;
    public final HandleHideAnimator$$ExternalSyntheticLambda0 mDelayedHideWithoutTouchRunnable;
    public final View mHandleView;
    public final Handler mHandler;
    public ObjectAnimator mHideHandleAnim;
    public final HandleHideAnimator$$ExternalSyntheticLambda0 mHideRunnable;
    public boolean mIsFocusedTask;
    public boolean mIsHandleMenuActive;
    public boolean mIsHandleViewVisible;
    public boolean mIsNightMode;
    public ObjectAnimator mShowHandleAnim;
    public ActivityManager.RunningTaskInfo taskInfo;
    public boolean mIsHandleTouching = false;
    public boolean mIsStatusBarVisible = true;
    public int mHandleHideState = 0;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0] */
    public HandleHideAnimator(Handler handler, View view) {
        final int i = 0;
        this.mHideRunnable = new Runnable(this) { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0
            public final /* synthetic */ HandleHideAnimator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                HandleHideAnimator handleHideAnimator = this.f$0;
                switch (i2) {
                    case 0:
                        handleHideAnimator.hide(false);
                        break;
                    case 1:
                        handleHideAnimator.delayedHide(true);
                        break;
                    default:
                        handleHideAnimator.delayedHide(false);
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mDelayedHideFromTouchRunnable = new Runnable(this) { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0
            public final /* synthetic */ HandleHideAnimator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                HandleHideAnimator handleHideAnimator = this.f$0;
                switch (i22) {
                    case 0:
                        handleHideAnimator.hide(false);
                        break;
                    case 1:
                        handleHideAnimator.delayedHide(true);
                        break;
                    default:
                        handleHideAnimator.delayedHide(false);
                        break;
                }
            }
        };
        final int i3 = 2;
        this.mDelayedHideWithoutTouchRunnable = new Runnable(this) { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator$$ExternalSyntheticLambda0
            public final /* synthetic */ HandleHideAnimator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i3;
                HandleHideAnimator handleHideAnimator = this.f$0;
                switch (i22) {
                    case 0:
                        handleHideAnimator.hide(false);
                        break;
                    case 1:
                        handleHideAnimator.delayedHide(true);
                        break;
                    default:
                        handleHideAnimator.delayedHide(false);
                        break;
                }
            }
        };
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

    public final void delayedHide(boolean z) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        HandleHideAnimator$$ExternalSyntheticLambda0 handleHideAnimator$$ExternalSyntheticLambda0 = this.mHideRunnable;
        Handler handler = this.mHandler;
        if (!z || (runningTaskInfo = this.taskInfo) == null) {
            handler.postDelayed(handleHideAnimator$$ExternalSyntheticLambda0, 3000L);
        } else if (runningTaskInfo.getWindowingMode() == 1) {
            handler.postDelayed(handleHideAnimator$$ExternalSyntheticLambda0, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
        } else if (this.taskInfo.isSplitScreen()) {
            handler.postDelayed(handleHideAnimator$$ExternalSyntheticLambda0, 3000L);
        }
    }

    public final void hide(boolean z) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mHandleView, (Property<View, Float>) View.ALPHA, (z || this.mHandleHideState != 2) ? 0.0f : this.mIsNightMode ? 0.2f : 0.1f);
        this.mHideHandleAnim = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(200L).setInterpolator(z ? Interpolators.FAST_OUT_SLOW_IN : Interpolators.LINEAR);
        this.mHideHandleAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator, boolean z2) {
                HandleHideAnimator handleHideAnimator = HandleHideAnimator.this;
                if ((handleHideAnimator.mHandleHideState == 1 || !handleHideAnimator.mIsStatusBarVisible) && !handleHideAnimator.mIsHandleTouching) {
                    handleHideAnimator.setHandleVisibility(false);
                }
                ObjectAnimator objectAnimator = HandleHideAnimator.this.mHideHandleAnim;
                if (objectAnimator != null) {
                    objectAnimator.removeAllListeners();
                    HandleHideAnimator.this.mHideHandleAnim = null;
                }
            }
        });
        this.mHideHandleAnim.start();
    }

    public final boolean isHandleHideEnabled() {
        return this.mHandleHideState != 0 && this.mIsStatusBarVisible;
    }

    public final void setHandleVisibility(boolean z) {
        this.mIsHandleViewVisible = z;
        int i = z ? 0 : 8;
        this.mHandleView.setVisibility(i);
        if (this.mHandleView.getRootView() != null) {
            this.mHandleView.getRootView().setVisibility(i);
        }
    }

    public final void setState(int i) {
        if (this.mIsHandleTouching) {
            return;
        }
        if (this.mHandleHideState == i) {
            if (this.mIsFocusedTask && isHandleHideEnabled()) {
                this.mIsFocusedTask = false;
                cancelAllHandleAnim();
                show(this.mDelayedHideWithoutTouchRunnable, false, false, true);
                return;
            }
            return;
        }
        this.mHandleHideState = i;
        if (isHandleHideEnabled()) {
            delayedHide(false);
        } else if (this.taskInfo.getWindowingMode() != 1 || this.mIsStatusBarVisible) {
            cancelAllHandleAnim();
            show(null, false, false, false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(final Runnable runnable, final boolean z, boolean z2, boolean z3) {
        float f;
        if (this.taskInfo.getWindowingMode() != 1) {
            f = 1.0f;
        } else if (!z2 && isHandleHideEnabled() && !z3) {
            f = this.mIsNightMode ? 0.9f : 0.7f;
        } else if (this.mHandleHideState == 1 && z2) {
            f = this.mIsNightMode ? 0.3f : 0.2f;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mHandleView, (Property<View, Float>) View.ALPHA, f);
        this.mShowHandleAnim = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(200L).setInterpolator(z ? Interpolators.FAST_OUT_SLOW_IN : Interpolators.LINEAR);
        setHandleVisibility(true);
        this.mShowHandleAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.HandleHideAnimator.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator, boolean z4) {
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
