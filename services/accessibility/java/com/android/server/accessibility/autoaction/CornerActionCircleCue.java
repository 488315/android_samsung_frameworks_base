package com.android.server.accessibility.autoaction;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.p005os.IInstalld;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes.dex */
public class CornerActionCircleCue {
  public Context mContext;
  public Display mDisplay;
  public ImageView mDragPointImageView;
  public long mDurationTime;
  public int mNavigationBarHeight;
  public WindowManager.LayoutParams mParams;
  public ImageView mProgressImageView;
  public float mScale;
  public ImageView mStandbyImageView;
  public int mType;
  public View mView;
  public int mWidth;
  public WindowManager mWindowManager;
  public final Handler mHandler = new Handler();
  public Thread mUiThread = Thread.currentThread();

  public void finalize() {
    super.finalize();
  }

  public CornerActionCircleCue(Context context, int i) {
    this.mNavigationBarHeight = 0;
    this.mContext = context;
    if (context != null) {
      this.mType = i;
      this.mScale = context.getDisplay().getDisplayId() == 2 ? 0.5f : 1.0f;
      this.mDurationTime = 0L;
      makeView();
      this.mNavigationBarHeight = getNavigationBarHeight();
    }
  }

  public final void makeView() {
    WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
    this.mWindowManager = windowManager;
    this.mDisplay = windowManager.getDefaultDisplay();
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    this.mParams = layoutParams;
    layoutParams.width = -2;
    layoutParams.height = -2;
    layoutParams.type = 2024;
    layoutParams.flags = 1816;
    layoutParams.format = -3;
    layoutParams.gravity = 51;
    layoutParams.samsungFlags |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
    layoutParams.layoutInDisplayCutoutMode = 1;
    View inflate =
        LayoutInflater.from(this.mContext)
            .inflate(R.layout.date_picker_view_animator_material, (ViewGroup) null);
    this.mView = inflate;
    inflate.bringToFront();
    this.mView.invalidate();
    this.mView.setVisibility(8);
    ImageView imageView = (ImageView) this.mView.findViewById(16909849);
    ImageView imageView2 = (ImageView) this.mView.findViewById(16909851);
    imageView.setVisibility(8);
    imageView2.setVisibility(8);
    this.mStandbyImageView = (ImageView) this.mView.findViewById(16909855);
    this.mProgressImageView = (ImageView) this.mView.findViewById(16909853);
    this.mDragPointImageView = (ImageView) this.mView.findViewById(16909852);
    this.mStandbyImageView.setScaleX(this.mScale);
    this.mStandbyImageView.setScaleY(this.mScale);
    this.mProgressImageView.setScaleX(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    this.mProgressImageView.setScaleY(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    int i = this.mType;
    if (i == 0) {
      this.mStandbyImageView.setVisibility(0);
      this.mProgressImageView.setVisibility(0);
      this.mDragPointImageView.setVisibility(8);
    } else if (i == 1) {
      this.mStandbyImageView.setVisibility(8);
      this.mProgressImageView.setVisibility(8);
      this.mDragPointImageView.setVisibility(0);
    }
    this.mView.measure(0, 0);
    this.mWidth = this.mView.getMeasuredWidth();
    this.mWindowManager.addView(this.mView, this.mParams);
  }

  public void updateView(final float f, final float f2) {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.autoaction.CornerActionCircleCue$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            CornerActionCircleCue.this.lambda$updateView$0(f, f2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateView$0(float f, float f2) {
    if (this.mDisplay.getRotation() == 3) {
      this.mParams.x = (((int) f) - (this.mWidth / 2)) - this.mNavigationBarHeight;
    } else {
      this.mParams.x = ((int) f) - (this.mWidth / 2);
    }
    WindowManager.LayoutParams layoutParams = this.mParams;
    layoutParams.y = ((int) f2) - (this.mWidth / 2);
    this.mWindowManager.updateViewLayout(this.mView, layoutParams);
  }

  public void setViewOnOff(final boolean z) {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.autoaction.CornerActionCircleCue$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            CornerActionCircleCue.this.lambda$setViewOnOff$1(z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setViewOnOff$1(boolean z) {
    if (z) {
      this.mView.setVisibility(0);
    } else {
      this.mView.setVisibility(8);
    }
  }

  public void setDurationTime(long j) {
    this.mDurationTime = j;
  }

  public void startAnimation() {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.autoaction.CornerActionCircleCue$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            CornerActionCircleCue.this.lambda$startAnimation$2();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startAnimation$2() {
    this.mProgressImageView.setVisibility(0);
    this.mProgressImageView
        .animate()
        .scaleX(this.mScale)
        .scaleY(this.mScale)
        .setDuration(this.mDurationTime)
        .start();
  }

  public void clearAnimation() {
    runOnUiThread(
        new Runnable() { // from class:
          // com.android.server.accessibility.autoaction.CornerActionCircleCue$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            CornerActionCircleCue.this.lambda$clearAnimation$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$clearAnimation$3() {
    this.mProgressImageView.setVisibility(8);
    this.mProgressImageView.clearAnimation();
    this.mProgressImageView.setScaleX(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    this.mProgressImageView.setScaleY(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public void announceForAccessibility(CharSequence charSequence) {
    this.mView.announceForAccessibility(charSequence);
  }

  public void destroy() {
    this.mContext = null;
    this.mWindowManager.removeView(this.mView);
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
