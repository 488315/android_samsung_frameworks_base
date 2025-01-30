package com.android.server.accessibility.magnification;

import android.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.A11yRune;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes.dex */
public class MagnificationThumbnail {
  public final WindowManager.LayoutParams mBackgroundParams;
  public final Context mContext;
  public final Handler mHandler;
  public boolean mIsFadingIn;
  public final WindowManager mSubDisplayWindowManager;
  public ObjectAnimator mThumbnailAnimator;
  public int mThumbnailHeight;
  public FrameLayout mThumbnailLayout;
  public View mThumbnailView;
  public int mThumbnailWidth;
  public boolean mVisible = false;
  public Rect mWindowBounds;
  public final WindowManager mWindowManager;

  public MagnificationThumbnail(Context context, WindowManager windowManager, Handler handler) {
    this.mContext = context;
    this.mWindowManager = windowManager;
    this.mHandler = handler;
    WindowManager subDisplayWindowManager = getSubDisplayWindowManager();
    this.mSubDisplayWindowManager = subDisplayWindowManager;
    if (isFlipCoverScreen()) {
      this.mWindowBounds = subDisplayWindowManager.getCurrentWindowMetrics().getBounds();
    } else {
      this.mWindowBounds = windowManager.getCurrentWindowMetrics().getBounds();
    }
    this.mBackgroundParams = createLayoutParams();
    this.mThumbnailWidth = 0;
    this.mThumbnailHeight = 0;
    handler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            MagnificationThumbnail.this.createThumbnailLayout();
          }
        });
  }

  public final void createThumbnailLayout() {
    FrameLayout frameLayout =
        (FrameLayout) LayoutInflater.from(this.mContext).inflate(17367459, (ViewGroup) null);
    this.mThumbnailLayout = frameLayout;
    this.mThumbnailView = frameLayout.findViewById(R.id.accessibility_performAction_description);
  }

  public void setThumbnailBounds(final Rect rect, final float f, final float f2, final float f3) {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            MagnificationThumbnail.this.lambda$setThumbnailBounds$0(rect, f, f2, f3);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setThumbnailBounds$0(Rect rect, float f, float f2, float f3) {
    this.mWindowBounds = rect;
    setBackgroundBounds();
    if (this.mVisible) {
      lambda$updateThumbnail$1(f, f2, f3);
      redrawThumbnail();
    }
  }

  public final void setBackgroundBounds() {
    Point magnificationThumbnailPadding = getMagnificationThumbnailPadding(this.mContext);
    this.mThumbnailWidth = (int) (this.mWindowBounds.width() / 7.0f);
    int height = (int) (this.mWindowBounds.height() / 7.0f);
    this.mThumbnailHeight = height;
    int i = magnificationThumbnailPadding.x;
    int i2 = magnificationThumbnailPadding.y;
    WindowManager.LayoutParams layoutParams = this.mBackgroundParams;
    layoutParams.width = this.mThumbnailWidth;
    layoutParams.height = height;
    layoutParams.x = i;
    layoutParams.y = i2;
  }

  public final void showThumbnail() {
    animateThumbnail(true);
  }

  public void hideThumbnail() {
    this.mHandler.post(new MagnificationThumbnail$$ExternalSyntheticLambda2(this));
  }

  public final void hideThumbnailMainThread() {
    if (this.mVisible) {
      animateThumbnail(false);
    }
  }

  public void redrawThumbnail() {
    this.mHandler.postDelayed(
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            MagnificationThumbnail.this.redrawThumbnailMainThread();
          }
        },
        200L);
  }

  public final void redrawThumbnailMainThread() {
    if (this.mThumbnailLayout.getParent() != null) {
      if (isFlipCoverScreen()) {
        this.mSubDisplayWindowManager.updateViewLayout(
            this.mThumbnailLayout, this.mBackgroundParams);
      } else {
        this.mWindowManager.updateViewLayout(this.mThumbnailLayout, this.mBackgroundParams);
      }
    }
  }

  public void destroyThumbnail() {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            MagnificationThumbnail.this.destroyThumbnailMainThread();
          }
        });
  }

  public final void destroyThumbnailMainThread() {
    if (this.mVisible) {
      if (this.mThumbnailLayout.getParent() != null) {
        if (isFlipCoverScreen()) {
          this.mSubDisplayWindowManager.removeView(this.mThumbnailLayout);
        } else {
          this.mWindowManager.removeView(this.mThumbnailLayout);
        }
      }
      this.mVisible = false;
    }
  }

  public final void animateThumbnail(final boolean z) {
    this.mHandler.removeCallbacks(new MagnificationThumbnail$$ExternalSyntheticLambda2(this));
    if (z) {
      this.mHandler.postDelayed(new MagnificationThumbnail$$ExternalSyntheticLambda2(this), 500L);
    }
    if (z == this.mIsFadingIn) {
      return;
    }
    this.mIsFadingIn = z;
    if (z && !this.mVisible) {
      if (isFlipCoverScreen()) {
        this.mSubDisplayWindowManager.addView(this.mThumbnailLayout, this.mBackgroundParams);
      } else {
        this.mWindowManager.addView(this.mThumbnailLayout, this.mBackgroundParams);
      }
      this.mVisible = true;
    }
    ObjectAnimator objectAnimator = this.mThumbnailAnimator;
    if (objectAnimator != null) {
      objectAnimator.cancel();
    }
    FrameLayout frameLayout = this.mThumbnailLayout;
    float[] fArr = new float[1];
    fArr[0] = z ? 1.0f : DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(frameLayout, "alpha", fArr);
    this.mThumbnailAnimator = ofFloat;
    ofFloat.setDuration(z ? 200L : 1000L);
    this.mThumbnailAnimator.setInterpolator(new LinearInterpolator());
    this.mThumbnailAnimator.addListener(
        new Animator.AnimatorListener() { // from class:
          // com.android.server.accessibility.magnification.MagnificationThumbnail.1
          public boolean mIsCancelled;

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationRepeat(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationStart(Animator animator) {}

          @Override // android.animation.Animator.AnimatorListener
          public void onAnimationCancel(Animator animator) {
            this.mIsCancelled = true;
          }
        });
    this.mThumbnailAnimator.start();
  }

  public void updateThumbnail(final float f, final float f2, final float f3) {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.MagnificationThumbnail$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            MagnificationThumbnail.this.lambda$updateThumbnail$1(f, f2, f3);
          }
        });
  }

  /* renamed from: updateThumbnailMainThread, reason: merged with bridge method [inline-methods] */
  public final void lambda$updateThumbnail$1(float f, float f2, float f3) {
    float f4;
    int i;
    if (f <= 1.0f) {
      hideThumbnail();
      return;
    }
    showThumbnail();
    float scaleX = Float.isNaN(f) ? this.mThumbnailView.getScaleX() : 1.0f / f;
    if (!Float.isNaN(f)) {
      this.mThumbnailView.setScaleX(scaleX);
      this.mThumbnailView.setScaleY(scaleX);
    }
    if (this.mThumbnailView.getWidth() == 0 || this.mThumbnailView.getHeight() == 0) {
      f4 = this.mThumbnailWidth;
      i = this.mThumbnailHeight;
    } else {
      f4 = this.mThumbnailView.getWidth();
      i = this.mThumbnailView.getHeight();
    }
    float f5 = i;
    if (Float.isNaN(f2)) {
      return;
    }
    float paddingTop = this.mThumbnailView.getPaddingTop();
    this.mThumbnailView.setTranslationX((f2 * 0.14285715f) - ((f4 / 2.0f) + paddingTop));
    this.mThumbnailView.setTranslationY((f3 * 0.14285715f) - ((f5 / 2.0f) + paddingTop));
  }

  public final WindowManager.LayoutParams createLayoutParams() {
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2027, 24, -2);
    layoutParams.inputFeatures = 1;
    layoutParams.gravity = 83;
    layoutParams.setFitInsetsTypes(WindowInsets.Type.ime() | WindowInsets.Type.navigationBars());
    layoutParams.receiveInsetsIgnoringZOrder = true;
    return layoutParams;
  }

  public final Point getMagnificationThumbnailPadding(Context context) {
    Point point = new Point(0, 0);
    int dimensionPixelSize =
        this.mContext
            .getResources()
            .getDimensionPixelSize(R.dimen.leanback_setup_alpha_forward_in_content_end);
    point.x = dimensionPixelSize;
    point.y = dimensionPixelSize;
    return point;
  }

  public final WindowManager getSubDisplayWindowManager() {
    Display display;
    Display[] displays =
        ((DisplayManager) this.mContext.getSystemService("display"))
            .getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
    int length = displays.length;
    int i = 0;
    while (true) {
      if (i >= length) {
        display = null;
        break;
      }
      display = displays[i];
      if (display.getDisplayId() == 1) {
        break;
      }
      i++;
    }
    if (display != null) {
      return (WindowManager)
          this.mContext.createDisplayContext(display).getSystemService(WindowManager.class);
    }
    return null;
  }

  public final boolean isFlipCoverScreen() {
    return this.mSubDisplayWindowManager != null
        && A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP
        && SemWindowManager.getInstance().isFolded();
  }
}
