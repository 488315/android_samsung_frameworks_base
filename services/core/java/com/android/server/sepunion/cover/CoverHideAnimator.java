package com.android.server.sepunion.cover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;
import com.samsung.android.sepunion.Log;

public final class CoverHideAnimator {
    public Runnable mCallbackRunnable;
    public final Context mContext;
    public final AnonymousClass1 mHandler;
    public final WindowManager.LayoutParams mWindowLP;
    public final WindowManager mWm;
    public final String TAG = "CoverManager_CoverHideAnimator";
    public final Interpolator mAnimationInterpolator = new SineInOut90();
    public View mCoverHideView = null;
    public final AnonymousClass2 mFadeInAnimatorListener =
            new AnimatorListenerAdapter() { // from class:
                                            // com.android.server.sepunion.cover.CoverHideAnimator.2
                @Override // android.animation.AnimatorListenerAdapter,
                          // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CoverHideAnimator coverHideAnimator = CoverHideAnimator.this;
                    Runnable runnable = coverHideAnimator.mCallbackRunnable;
                    if (runnable != null) {
                        coverHideAnimator.mHandler.post(runnable);
                        CoverHideAnimator.this.mCallbackRunnable = null;
                    }
                    View view = CoverHideAnimator.this.mCoverHideView;
                    if (view != null) {
                        view.animate()
                                .alpha(FullScreenMagnificationGestureHandler.MAX_SCALE)
                                .setStartDelay(150L)
                                .setDuration(300L)
                                .setInterpolator(CoverHideAnimator.this.mAnimationInterpolator)
                                .setListener(CoverHideAnimator.this.mFadeOutAnimatorListener);
                    }
                    super.onAnimationEnd(animator);
                }
            };
    public final AnonymousClass3 mFadeOutAnimatorListener =
            new AnimatorListenerAdapter() { // from class:
                                            // com.android.server.sepunion.cover.CoverHideAnimator.3
                @Override // android.animation.AnimatorListenerAdapter,
                          // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CoverHideAnimator.this.removeViewFromWindow();
                    super.onAnimationEnd(animator);
                }
            };

    public CoverHideAnimator(Context context, Looper looper) {
        this.mContext = context;
        this.mWm = (WindowManager) context.getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mWindowLP = layoutParams;
        layoutParams.setTitle("clear cover animation");
        layoutParams.gravity = 17;
        layoutParams.height = -1;
        layoutParams.width = -1;
        layoutParams.type = 2024;
        layoutParams.format = -2;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.flags = 67633160;
        layoutParams.setFitInsetsTypes(
                layoutParams.getFitInsetsTypes()
                        & (~WindowInsets.Type.navigationBars())
                        & (~WindowInsets.Type.statusBars()));
        this.mHandler =
                new Handler(
                        looper) { // from class:
                                  // com.android.server.sepunion.cover.CoverHideAnimator.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        View view;
                        int i = message.what;
                        CoverHideAnimator coverHideAnimator = CoverHideAnimator.this;
                        if (i != 101) {
                            if (i == 102 && (view = coverHideAnimator.mCoverHideView) != null) {
                                view.animate().cancel();
                                coverHideAnimator.removeViewFromWindow();
                                return;
                            }
                            return;
                        }
                        if (coverHideAnimator.mCoverHideView != null) {
                            Log.e(
                                    coverHideAnimator.TAG,
                                    "handleStartAnimation : mCoverHideView is not null!!");
                            coverHideAnimator.removeViewFromWindow();
                        }
                        View view2 = new View(coverHideAnimator.mContext);
                        coverHideAnimator.mCoverHideView = view2;
                        view2.setAlpha(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        coverHideAnimator.mCoverHideView.setBackgroundColor(-16777216);
                        coverHideAnimator
                                .mCoverHideView
                                .animate()
                                .alpha(1.0f)
                                .setDuration(100L)
                                .setInterpolator(coverHideAnimator.mAnimationInterpolator)
                                .setListener(coverHideAnimator.mFadeInAnimatorListener);
                        coverHideAnimator.mWm.addView(
                                coverHideAnimator.mCoverHideView, coverHideAnimator.mWindowLP);
                    }
                };
    }

    public final void removeViewFromWindow() {
        View view = this.mCoverHideView;
        if (view != null) {
            view.setVisibility(8);
            this.mWm.removeView(this.mCoverHideView);
        }
        this.mCoverHideView = null;
    }
}
