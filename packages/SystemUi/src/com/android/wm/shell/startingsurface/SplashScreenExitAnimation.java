package com.android.wm.shell.startingsurface;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.View;
import android.window.SplashScreenView;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.common.TransactionPool;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplashScreenExitAnimation implements Animator.AnimatorListener {
    public final int mAnimationDuration;
    public final int mAppRevealDuration;
    public final float mBrandingStartAlpha;
    public Runnable mFinishCallback;
    public final Rect mFirstWindowFrame;
    public final SurfaceControl mFirstWindowSurface;
    public final int mIconFadeOutDuration;
    public final float mIconStartAlpha;
    public final int mMainWindowShiftLength;
    public final float mRoundedCornerRadius;
    public final SplashScreenView mSplashScreenView;
    public final TransactionPool mTransactionPool;

    public SplashScreenExitAnimation(Context context, SplashScreenView splashScreenView, SurfaceControl surfaceControl, Rect rect, int i, TransactionPool transactionPool, Runnable runnable, float f) {
        Rect rect2 = new Rect();
        this.mFirstWindowFrame = rect2;
        this.mSplashScreenView = splashScreenView;
        this.mFirstWindowSurface = surfaceControl;
        this.mRoundedCornerRadius = f;
        if (rect != null) {
            rect2.set(rect);
        }
        int integer = context.getResources().getInteger(R.integer.starting_window_app_reveal_anim_duration_reduced);
        View iconView = splashScreenView.getIconView();
        if (iconView == null || iconView.getLayoutParams().width == 0 || iconView.getLayoutParams().height == 0) {
            this.mIconFadeOutDuration = 0;
            this.mIconStartAlpha = 0.0f;
            this.mBrandingStartAlpha = 0.0f;
        } else {
            iconView.setLayerType(2, null);
            View brandingView = splashScreenView.getBrandingView();
            if (brandingView != null) {
                this.mBrandingStartAlpha = brandingView.getAlpha();
            } else {
                this.mBrandingStartAlpha = 0.0f;
            }
            this.mIconFadeOutDuration = integer;
            this.mIconStartAlpha = iconView.getAlpha();
        }
        this.mAppRevealDuration = integer;
        this.mAnimationDuration = Math.max(this.mIconFadeOutDuration, 0 + integer);
        this.mMainWindowShiftLength = i;
        this.mFinishCallback = runnable;
        this.mTransactionPool = transactionPool;
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationCancel(Animator animator) {
        if (this.mSplashScreenView.isAttachedToWindow()) {
            this.mSplashScreenView.setVisibility(8);
            Runnable runnable = this.mFinishCallback;
            if (runnable != null) {
                runnable.run();
                this.mFinishCallback = null;
            }
        }
        InteractionJankMonitor.getInstance().cancel(39);
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        if (this.mSplashScreenView.isAttachedToWindow()) {
            this.mSplashScreenView.setVisibility(8);
            Runnable runnable = this.mFinishCallback;
            if (runnable != null) {
                runnable.run();
                this.mFinishCallback = null;
            }
        }
        InteractionJankMonitor.getInstance().end(39);
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        InteractionJankMonitor.getInstance().begin(this.mSplashScreenView, 39);
    }

    @Override // android.animation.Animator.AnimatorListener
    public final void onAnimationRepeat(Animator animator) {
    }
}
