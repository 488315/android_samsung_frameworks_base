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
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;
import com.samsung.android.sepunion.Log;

/* loaded from: classes3.dex */
public class CoverHideAnimator {
  public Runnable mCallbackRunnable;
  public Context mContext;
  public Handler mHandler;
  public WindowManager.LayoutParams mWindowLP;
  public WindowManager mWm;
  public final String TAG = "CoverManager_" + CoverHideAnimator.class.getSimpleName();
  public final long FADE_IN_ANIMATION_DURATION = 100;
  public final long FADE_OUT_ANIMATION_START_DELAY = 150;
  public final long FADE_OUT_ANIMATION_DURATION = 300;
  public final int MSG_SEND_COVER_START_ANIMATION = 101;
  public final int MSG_SEND_COVER_CANCEL_ANIMATION = 102;
  public Interpolator mAnimationInterpolator = new SineInOut90();
  public View mCoverHideView = null;
  public AnimatorListenerAdapter mFadeInAnimatorListener =
      new AnimatorListenerAdapter() { // from class:
                                      // com.android.server.sepunion.cover.CoverHideAnimator.2
        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
          if (CoverHideAnimator.this.mCallbackRunnable != null) {
            CoverHideAnimator.this.mHandler.post(CoverHideAnimator.this.mCallbackRunnable);
            CoverHideAnimator.this.mCallbackRunnable = null;
          }
          if (CoverHideAnimator.this.mCoverHideView != null) {
            CoverHideAnimator.this
                .mCoverHideView
                .animate()
                .alpha(DisplayPowerController2.RATE_FROM_DOZE_TO_ON)
                .setStartDelay(150L)
                .setDuration(300L)
                .setInterpolator(CoverHideAnimator.this.mAnimationInterpolator)
                .setListener(CoverHideAnimator.this.mFadeOutAnimatorListener);
          }
          super.onAnimationEnd(animator);
        }
      };
  public AnimatorListenerAdapter mFadeOutAnimatorListener =
      new AnimatorListenerAdapter() { // from class:
                                      // com.android.server.sepunion.cover.CoverHideAnimator.3
        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
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
    WindowManager.LayoutParams layoutParams2 = this.mWindowLP;
    layoutParams2.gravity = 17;
    layoutParams2.height = -1;
    layoutParams2.width = -1;
    layoutParams2.type = 2024;
    layoutParams2.format = -2;
    layoutParams2.layoutInDisplayCutoutMode = 1;
    layoutParams2.flags = 67633160;
    layoutParams2.setFitInsetsTypes(
        layoutParams2.getFitInsetsTypes()
            & (~WindowInsets.Type.navigationBars())
            & (~WindowInsets.Type.statusBars()));
    this.mHandler =
        new Handler(looper) { // from class: com.android.server.sepunion.cover.CoverHideAnimator.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            int i = message.what;
            if (i == 101) {
              CoverHideAnimator.this.handleStartAnimation();
            } else {
              if (i != 102) {
                return;
              }
              CoverHideAnimator.this.handleCancelAnimation();
            }
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

  public void cancelHideAnimation() {
    Log.d(this.TAG, "cancelHideAnimation()");
    this.mHandler.sendEmptyMessage(102);
  }

  public void playCoverHideAnimation(Runnable runnable) {
    Log.d(this.TAG, "playCoverHideAnimation()");
    this.mCallbackRunnable = runnable;
    this.mHandler.sendEmptyMessage(101);
  }

  public final void handleStartAnimation() {
    if (this.mCoverHideView != null) {
      Log.e(this.TAG, "handleStartAnimation : mCoverHideView is not null!!");
      removeViewFromWindow();
    }
    View view = new View(this.mContext);
    this.mCoverHideView = view;
    view.setAlpha(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    this.mCoverHideView.setBackgroundColor(-16777216);
    this.mCoverHideView
        .animate()
        .alpha(1.0f)
        .setDuration(100L)
        .setInterpolator(this.mAnimationInterpolator)
        .setListener(this.mFadeInAnimatorListener);
    this.mWm.addView(this.mCoverHideView, this.mWindowLP);
  }

  public final void handleCancelAnimation() {
    View view = this.mCoverHideView;
    if (view != null) {
      view.animate().cancel();
      removeViewFromWindow();
    }
  }
}
