package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.os.Handler;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.samsung.android.util.InterpolatorUtils;

/* loaded from: classes3.dex */
public class DesktopImmersiveCaptionAnimator {
    public final View mCaption;
    public final int mCaptionHeight;
    public long mDownTime;
    public float mDownY;
    public final Handler mHandler;
    public final Animator mHide;
    public final DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0 mHideRunnable;
    public final boolean mIsDefaultDisplay;
    public int mPositionToShow;
    public final Animator mShow;
    public final DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0 mShowRunnable;
    public boolean mIsShowing = false;
    public boolean mShownByTouch = false;
    public boolean mIsPaused = false;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0] */
    public DesktopImmersiveCaptionAnimator(ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler, View view, int i) {
        final int i2 = 0;
        this.mShowRunnable = new Runnable(this) { // from class: com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopImmersiveCaptionAnimator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i3 = i2;
                DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator = this.f$0;
                switch (i3) {
                    case 0:
                        desktopImmersiveCaptionAnimator.show();
                        break;
                    default:
                        desktopImmersiveCaptionAnimator.hide();
                        break;
                }
            }
        };
        final int i3 = 1;
        this.mHideRunnable = new Runnable(this) { // from class: com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopImmersiveCaptionAnimator f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i32 = i3;
                DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator = this.f$0;
                switch (i32) {
                    case 0:
                        desktopImmersiveCaptionAnimator.show();
                        break;
                    default:
                        desktopImmersiveCaptionAnimator.hide();
                        break;
                }
            }
        };
        this.mHandler = handler;
        this.mCaption = view;
        this.mIsDefaultDisplay = runningTaskInfo.displayId == 0;
        this.mCaptionHeight = i;
        Property property = View.TRANSLATION_Y;
        float f = -i;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, f, 0.0f);
        this.mShow = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(300L);
        PathInterpolator pathInterpolator = InterpolatorUtils.SINE_IN_OUT_80;
        objectAnimatorOfFloat.setInterpolator(pathInterpolator);
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DesktopImmersiveCaptionAnimator.this.setShownState(true);
                DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator = DesktopImmersiveCaptionAnimator.this;
                if (desktopImmersiveCaptionAnimator.mIsDefaultDisplay) {
                    return;
                }
                if (desktopImmersiveCaptionAnimator.mShownByTouch || !desktopImmersiveCaptionAnimator.mIsPaused) {
                    desktopImmersiveCaptionAnimator.mHandler.postDelayed(desktopImmersiveCaptionAnimator.mHideRunnable, 1000L);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                DesktopImmersiveCaptionAnimator.this.mCaption.setVisibility(0);
                if (DesktopImmersiveCaptionAnimator.this.mCaption.getRootView() != null) {
                    DesktopImmersiveCaptionAnimator.this.mCaption.getRootView().setVisibility(0);
                }
            }
        });
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, 0.0f, f);
        this.mHide = objectAnimatorOfFloat2;
        objectAnimatorOfFloat2.setDuration(300L);
        objectAnimatorOfFloat2.setInterpolator(pathInterpolator);
        objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DesktopImmersiveCaptionAnimator.this.setShownState(false);
                DesktopImmersiveCaptionAnimator.this.mCaption.setVisibility(8);
                if (DesktopImmersiveCaptionAnimator.this.mCaption.getRootView() != null) {
                    DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator = DesktopImmersiveCaptionAnimator.this;
                    if (desktopImmersiveCaptionAnimator.mIsPaused) {
                        return;
                    }
                    desktopImmersiveCaptionAnimator.mCaption.getRootView().setVisibility(8);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                DesktopImmersiveCaptionAnimator.this.getClass();
            }
        });
    }

    public final void hide() {
        if (this.mShow.isRunning()) {
            this.mShow.cancel();
            Log.d("DesktopImmersiveCaptionAnimator", "ImmersiveCaptionBehavior_hide: cancel show anim");
        }
        if (this.mIsShowing) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(5, "DesktopImmersiveCaptionAnimator", new StringBuilder("ImmersiveCaptionBehavior_hide: callers="));
            if (this.mHide.isRunning()) {
                return;
            }
            this.mHide.start();
        }
    }

    public final void setShownState(boolean z) {
        if (this.mIsShowing != z) {
            this.mIsShowing = z;
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(3, "DesktopImmersiveCaptionAnimator", RowView$$ExternalSyntheticOutline0.m("ImmersiveCaptionBehavior_setShownState=", ", callers=", z));
        }
    }

    public final void show() {
        if (this.mHide.isRunning()) {
            this.mHide.cancel();
            Log.d("DesktopImmersiveCaptionAnimator", "ImmersiveCaptionBehavior_show: cancel hide anim");
        }
        if (this.mIsShowing) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(5, "DesktopImmersiveCaptionAnimator", new StringBuilder("ImmersiveCaptionBehavior_show: callers="));
        if (this.mShow.isRunning()) {
            return;
        }
        this.mShow.start();
    }
}
