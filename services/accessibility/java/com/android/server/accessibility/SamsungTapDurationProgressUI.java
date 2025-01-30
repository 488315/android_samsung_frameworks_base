package com.android.server.accessibility;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.RemoteException;
import android.os.IInstalld;
import android.provider.Settings;
import android.util.Property;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes.dex */
public class SamsungTapDurationProgressUI {
  public ImageView mArrow;
  public ImageView mBackground;
  public ImageView mCircle;
  public Context mContext;
  public int mDensityDpi;
  public Display mDisplay;
  public long mDuration;
  public ObjectAnimator mFadeInAnimator;
  public ObjectAnimator mFadeOutAnimator;
  public ImageView mHold;
  public boolean mIsRemoveAnimationEnabled;
  public boolean mIsShortThreshold;
  public WindowManager.LayoutParams mParams;
  public ProgressBar mProgress;
  public AnimatorSet mRotationSet;
  public AnimatorSet mScaleSet;
  public int mSize;
  public ImageView mStandBy;
  public int mUiMode;
  public View mView;
  public WindowManager mWindowManager;
  public final Handler mHandler = new Handler();
  public final Thread mUiThread = Thread.currentThread();
  public final int mNavigationBarHeight = getNavigationBarHeight();

  public SamsungTapDurationProgressUI(Context context) {
    this.mContext = context;
    this.mUiMode = context.getResources().getConfiguration().uiMode;
    this.mDensityDpi = this.mContext.getResources().getConfiguration().densityDpi;
    this.mSize = this.mContext.getResources().getDimensionPixelSize(17106190);
    this.mIsRemoveAnimationEnabled =
        Settings.Global.getInt(this.mContext.getContentResolver(), "remove_animations", 0) == 1;
    this.mIsShortThreshold = false;
    makeView();
    makeAnimation();
  }

  public void setDurationTime(long j) {
    this.mDuration = j;
    if (this.mIsShortThreshold != (j < 300)) {
      setShortThresholdView(j < 300);
    }
  }

  public void setViewOnOff(final boolean z) {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SamsungTapDurationProgressUI.this.lambda$setViewOnOff$0(z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setViewOnOff$0(boolean z) {
    this.mView.setVisibility(z ? 0 : 8);
  }

  public void start() {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SamsungTapDurationProgressUI.this.lambda$start$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$start$1() {
    checkConfigurationChanged();
    if (this.mFadeOutAnimator.isRunning()) {
      this.mFadeOutAnimator.end();
    }
    this.mRotationSet.setDuration(this.mDuration);
    this.mScaleSet.start();
  }

  public void cancel() {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SamsungTapDurationProgressUI.this.lambda$cancel$2();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cancel$2() {
    if (this.mScaleSet.isRunning()) {
      this.mScaleSet.cancel();
    }
    if (this.mRotationSet.isRunning()) {
      this.mRotationSet.cancel();
    }
    this.mFadeOutAnimator.start();
  }

  public void end() {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            SamsungTapDurationProgressUI.this.lambda$end$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$end$3() {
    if (this.mRotationSet.isRunning()) {
      this.mRotationSet.end();
    }
    if (this.mIsRemoveAnimationEnabled || this.mIsShortThreshold) {
      this.mStandBy.setVisibility(8);
      this.mHold.setVisibility(0);
    } else {
      this.mFadeOutAnimator.start();
    }
  }

  public void updateView(float f, float f2) {
    if (this.mDisplay.getRotation() == 3) {
      this.mParams.x = (((int) f) - (this.mSize / 2)) - this.mNavigationBarHeight;
    } else {
      this.mParams.x = ((int) f) - (this.mSize / 2);
    }
    this.mParams.y = ((int) f2) - (this.mSize / 2);
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            SamsungTapDurationProgressUI.this.lambda$updateView$4();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateView$4() {
    this.mWindowManager.updateViewLayout(this.mView, this.mParams);
  }

  public void setRemoveAnimationEnabled(boolean z) {
    this.mIsRemoveAnimationEnabled = z;
    int i = 0;
    this.mCircle.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
    this.mArrow.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
    this.mBackground.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
    this.mProgress.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
    ImageView imageView = this.mStandBy;
    if (!z && !this.mIsShortThreshold) {
      i = 8;
    }
    imageView.setVisibility(i);
    this.mHold.setVisibility(8);
  }

  public void setShortThresholdView(boolean z) {
    this.mIsShortThreshold = z;
    int i = 0;
    this.mCircle.setVisibility((z || this.mIsRemoveAnimationEnabled) ? 8 : 0);
    this.mArrow.setVisibility((z || this.mIsRemoveAnimationEnabled) ? 8 : 0);
    this.mBackground.setVisibility((z || this.mIsRemoveAnimationEnabled) ? 8 : 0);
    this.mProgress.setVisibility((z || this.mIsRemoveAnimationEnabled) ? 8 : 0);
    ImageView imageView = this.mStandBy;
    if (!z && !this.mIsRemoveAnimationEnabled) {
      i = 8;
    }
    imageView.setVisibility(i);
    this.mHold.setVisibility(8);
    this.mSize =
        this.mContext
            .getResources()
            .getDimensionPixelSize(z ? R.dimen.chooser_max_collapsed_height : 17106190);
    this.mStandBy.getLayoutParams().width = this.mSize;
    this.mStandBy.getLayoutParams().height = this.mSize;
    this.mHold.getLayoutParams().width = this.mSize;
    this.mHold.getLayoutParams().height = this.mSize;
    this.mStandBy.requestLayout();
    this.mHold.requestLayout();
  }

  public void destroy() {
    this.mContext = null;
    this.mWindowManager.removeView(this.mView);
  }

  public final void checkConfigurationChanged() {
    try {
      Configuration configuration = ActivityManager.getService().getConfiguration();
      int i = this.mUiMode;
      int i2 = configuration.uiMode;
      if (i == i2 && this.mDensityDpi == configuration.densityDpi) {
        return;
      }
      this.mUiMode = i2;
      this.mDensityDpi = configuration.densityDpi;
      this.mContext = this.mContext.createConfigurationContext(configuration);
      this.mWindowManager.removeView(this.mView);
      this.mSize = this.mContext.getResources().getDimensionPixelSize(17106190);
      makeView();
      makeAnimation();
    } catch (RemoteException unused) {
    }
  }

  public final void makeView() {
    WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
    this.mWindowManager = windowManager;
    this.mDisplay = windowManager.getDefaultDisplay();
    if (this.mParams == null) {
      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
      this.mParams = layoutParams;
      layoutParams.width = -2;
      layoutParams.height = -2;
      layoutParams.type = 2006;
      layoutParams.flags = 1800;
      layoutParams.format = -3;
      layoutParams.gravity = 51;
      layoutParams.samsungFlags |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
      layoutParams.layoutInDisplayCutoutMode = 1;
    }
    View inflate =
        LayoutInflater.from(this.mContext)
            .inflate(R.layout.notification_material_action_emphasized, (ViewGroup) null);
    this.mView = inflate;
    inflate.bringToFront();
    this.mView.invalidate();
    this.mView.setVisibility(8);
    this.mCircle = (ImageView) this.mView.findViewById(16909847);
    this.mArrow = (ImageView) this.mView.findViewById(16909845);
    this.mBackground = (ImageView) this.mView.findViewById(16909846);
    this.mProgress = (ProgressBar) this.mView.findViewById(16909853);
    this.mStandBy = (ImageView) this.mView.findViewById(16909854);
    this.mHold = (ImageView) this.mView.findViewById(16909848);
    setRemoveAnimationEnabled(this.mIsRemoveAnimationEnabled);
    this.mWindowManager.addView(this.mView, this.mParams);
  }

  public final void makeAnimation() {
    this.mScaleSet = new AnimatorSet();
    this.mRotationSet = new AnimatorSet();
    this.mScaleSet.playTogether(
        ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) View.SCALE_X, 0.8f, 1.0f),
        ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) View.SCALE_Y, 0.8f, 1.0f));
    this.mScaleSet.setDuration(350L);
    this.mScaleSet.addListener(
        new Animator.AnimatorListener() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI.1
          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationCancel(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationRepeat(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationStart(Animator animator) {
            SamsungTapDurationProgressUI.this.mCircle.setRotation(
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            SamsungTapDurationProgressUI.this.mArrow.setRotation(
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            SamsungTapDurationProgressUI.this.mArrow.setAlpha(
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            SamsungTapDurationProgressUI.this.mView.setScaleX(0.8f);
            SamsungTapDurationProgressUI.this.mView.setScaleY(0.8f);
            SamsungTapDurationProgressUI.this.mView.setAlpha(1.0f);
            SamsungTapDurationProgressUI.this.mProgress.setProgress(0);
            SamsungTapDurationProgressUI.this.mRotationSet.start();
            SamsungTapDurationProgressUI.this.mFadeInAnimator.start();
          }

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            SamsungTapDurationProgressUI.this.mView.setScaleX(1.0f);
            SamsungTapDurationProgressUI.this.mView.setScaleY(1.0f);
          }
        });
    this.mScaleSet.setInterpolator(
        new PathInterpolator(0.22f, 0.17f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f));
    ObjectAnimator ofFloat =
        ObjectAnimator.ofFloat(
            this.mCircle,
            (Property<ImageView, Float>) View.ROTATION,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            360.0f);
    ObjectAnimator ofFloat2 =
        ObjectAnimator.ofFloat(
            this.mArrow,
            (Property<ImageView, Float>) View.ROTATION,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            360.0f);
    ofFloat.addUpdateListener(
        new ValueAnimator.AnimatorUpdateListener() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI.2
          @Override // android.animation.ValueAnimator.AnimatorUpdateListener
          public void onAnimationUpdate(ValueAnimator valueAnimator) {
            SamsungTapDurationProgressUI.this.mProgress.setProgress(
                (int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
          }
        });
    this.mRotationSet.playTogether(ofFloat, ofFloat2);
    ObjectAnimator ofFloat3 =
        ObjectAnimator.ofFloat(
            this.mArrow,
            (Property<ImageView, Float>) View.ALPHA,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
            1.0f);
    this.mFadeInAnimator = ofFloat3;
    ofFloat3.setDuration(100L);
    ObjectAnimator ofFloat4 =
        ObjectAnimator.ofFloat(
            this.mView,
            (Property<View, Float>) View.ALPHA,
            1.0f,
            DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    this.mFadeOutAnimator = ofFloat4;
    ofFloat4.setDuration(150L);
    this.mFadeOutAnimator.addListener(
        new Animator.AnimatorListener() { // from class:
          // com.android.server.accessibility.SamsungTapDurationProgressUI.3
          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationCancel(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationRepeat(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationStart(Animator animator) {
            SamsungTapDurationProgressUI.this.mView.setScaleX(1.0f);
            SamsungTapDurationProgressUI.this.mView.setScaleY(1.0f);
          }

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            SamsungTapDurationProgressUI.this.mStandBy.setVisibility(
                (SamsungTapDurationProgressUI.this.mIsRemoveAnimationEnabled
                        || SamsungTapDurationProgressUI.this.mIsShortThreshold)
                    ? 0
                    : 8);
            SamsungTapDurationProgressUI.this.mHold.setVisibility(8);
            SamsungTapDurationProgressUI.this.mView.setVisibility(8);
          }
        });
  }

  public final int getNavigationBarHeight() {
    if (this.mContext.getResources().getBoolean(R.bool.config_predictShowStartingSurface)) {
      return this.mContext
          .getResources()
          .getDimensionPixelSize(R.dimen.notification_custom_view_max_image_height_low_ram);
    }
    return 0;
  }

  public final void runOnUiThread(Runnable runnable) {
    if (Thread.currentThread() != this.mUiThread) {
      this.mHandler.post(runnable);
    } else {
      runnable.run();
    }
  }
}
