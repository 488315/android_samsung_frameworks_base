package com.android.server.accessibility.magnification;

import android.R;
import android.accessibilityservice.MagnificationConfig;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.DisplayInfo;
import android.view.MagnificationSpec;
import android.view.WindowManager;
import android.view.accessibility.A11yRune;
import android.view.accessibility.MagnificationAnimationCallback;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.WindowManagerInternal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FullScreenMagnificationController
    implements WindowManagerInternal.AccessibilityControllerInternal
        .UiChangesForAccessibilityCallbacks {
  public static boolean SEC_DEBUG = false;
  public boolean mAlwaysOnMagnificationEnabled;
  public final ControllerContext mControllerCtx;
  public final DisplayManagerInternal mDisplayManagerInternal;
  public final SparseArray mDisplays;
  public final Object mLock;
  public boolean mMagnificationFollowTypingEnabled;
  public final ArrayList mMagnificationInfoChangedCallbacks;
  public final MagnificationThumbnailFeatureFlag mMagnificationThumbnailFeatureFlag;
  public final long mMainThreadId;
  public final MagnificationScaleProvider mScaleProvider;
  public final ScreenStateObserver mScreenStateObserver;
  public final Rect mTempRect;
  public final Supplier mThumbnailSupplier;

  public interface MagnificationInfoChangedCallback {
    void onFullScreenMagnificationActivationState(int i, boolean z);

    void onFullScreenMagnificationChanged(
        int i, Region region, MagnificationConfig magnificationConfig);

    void onImeWindowVisibilityChanged(int i, boolean z);

    void onRequestMagnificationSpec(int i, int i2);
  }

  public final class DisplayMagnification implements WindowManagerInternal.MagnificationCallbacks {
    public boolean mDeleteAfterUnregister;
    public final int mDisplayId;
    public MagnificationThumbnail mMagnificationThumbnail;
    public boolean mRegistered;
    public final SpecAnimationBridge mSpecAnimationBridge;
    public boolean mUnregisterPending;
    public final MagnificationSpec mCurrentMagnificationSpec = new MagnificationSpec();
    public final Region mMagnificationRegion = Region.obtain();
    public final Rect mMagnificationBounds = new Rect();
    public final Rect mTempRect = new Rect();
    public final Rect mTempRect1 = new Rect();
    public int mIdOfLastServiceToMagnify = -1;
    public boolean mMagnificationActivated = false;

    public DisplayMagnification(int i) {
      this.mDisplayId = i;
      this.mSpecAnimationBridge =
          new SpecAnimationBridge(
              FullScreenMagnificationController.this.mControllerCtx,
              FullScreenMagnificationController.this.mLock,
              i);
    }

    public boolean register() {
      if (FullScreenMagnificationController.this.traceEnabled()) {
        FullScreenMagnificationController.this.logTrace(
            "setMagnificationCallbacks", "displayID=" + this.mDisplayId + ";callback=" + this);
      }
      boolean magnificationCallbacks =
          FullScreenMagnificationController.this
              .mControllerCtx
              .getWindowManager()
              .setMagnificationCallbacks(this.mDisplayId, this);
      this.mRegistered = magnificationCallbacks;
      if (!magnificationCallbacks) {
        Slog.w(
            "FullScreenMagnificationController",
            "set magnification callbacks fail, displayId:" + this.mDisplayId);
        return false;
      }
      this.mSpecAnimationBridge.setEnabled(true);
      if (FullScreenMagnificationController.this.traceEnabled()) {
        FullScreenMagnificationController.this.logTrace(
            "getMagnificationRegion",
            "displayID=" + this.mDisplayId + ";region=" + this.mMagnificationRegion);
      }
      FullScreenMagnificationController.this
          .mControllerCtx
          .getWindowManager()
          .getMagnificationRegion(this.mDisplayId, this.mMagnificationRegion);
      this.mMagnificationRegion.getBounds(this.mMagnificationBounds);
      createThumbnailIfSupported();
      return true;
    }

    public void unregister(boolean z) {
      if (this.mRegistered) {
        this.mSpecAnimationBridge.setEnabled(false);
        if (FullScreenMagnificationController.this.traceEnabled()) {
          FullScreenMagnificationController.this.logTrace(
              "setMagnificationCallbacks", "displayID=" + this.mDisplayId + ";callback=null");
        }
        FullScreenMagnificationController.this
            .mControllerCtx
            .getWindowManager()
            .setMagnificationCallbacks(this.mDisplayId, null);
        this.mMagnificationRegion.setEmpty();
        this.mRegistered = false;
        FullScreenMagnificationController.this.unregisterCallbackLocked(this.mDisplayId, z);
        destroyThumbnail();
      }
      this.mUnregisterPending = false;
    }

    public void unregisterPending(boolean z) {
      this.mDeleteAfterUnregister = z;
      this.mUnregisterPending = true;
      reset(true);
    }

    public boolean isRegistered() {
      return this.mRegistered;
    }

    public boolean isActivated() {
      return this.mMagnificationActivated;
    }

    public float getScale() {
      return this.mCurrentMagnificationSpec.scale;
    }

    public float getOffsetX() {
      return this.mCurrentMagnificationSpec.offsetX;
    }

    public float getOffsetY() {
      return this.mCurrentMagnificationSpec.offsetY;
    }

    public float getCenterX() {
      return (((this.mMagnificationBounds.width() / 2.0f) + this.mMagnificationBounds.left)
              - getOffsetX())
          / getScale();
    }

    public float getCenterY() {
      return (((this.mMagnificationBounds.height() / 2.0f) + this.mMagnificationBounds.top)
              - getOffsetY())
          / getScale();
    }

    public float getSentScale() {
      return this.mSpecAnimationBridge.mSentMagnificationSpec.scale;
    }

    public float getSentOffsetX() {
      return this.mSpecAnimationBridge.mSentMagnificationSpec.offsetX;
    }

    public float getSentOffsetY() {
      return this.mSpecAnimationBridge.mSentMagnificationSpec.offsetY;
    }

    @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
    public void onMagnificationRegionChanged(Region region) {
      FullScreenMagnificationController.this
          .mControllerCtx
          .getHandler()
          .sendMessage(
              PooledLambda.obtainMessage(
                  new BiConsumer() { // from class:
                    // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda7
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                      ((FullScreenMagnificationController.DisplayMagnification) obj)
                          .updateMagnificationRegion((Region) obj2);
                    }
                  },
                  this,
                  Region.obtain(region)));
    }

    @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
    public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4) {
      FullScreenMagnificationController.this
          .mControllerCtx
          .getHandler()
          .sendMessage(
              PooledLambda.obtainMessage(
                  new QuintConsumer() { // from class:
                    // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda4
                    public final void accept(
                        Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                      ((FullScreenMagnificationController.DisplayMagnification) obj)
                          .requestRectangleOnScreen(
                              ((Integer) obj2).intValue(),
                              ((Integer) obj3).intValue(),
                              ((Integer) obj4).intValue(),
                              ((Integer) obj5).intValue());
                    }
                  },
                  this,
                  Integer.valueOf(i),
                  Integer.valueOf(i2),
                  Integer.valueOf(i3),
                  Integer.valueOf(i4)));
    }

    @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
    public void onDisplaySizeChanged() {
      onUserContextChanged();
    }

    @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
    public void onUserContextChanged() {
      FullScreenMagnificationController.this
          .mControllerCtx
          .getHandler()
          .sendMessage(
              PooledLambda.obtainMessage(
                  new BiConsumer() { // from class:
                    // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                      ((FullScreenMagnificationController) obj)
                          .onUserContextChanged(((Integer) obj2).intValue());
                    }
                  },
                  FullScreenMagnificationController.this,
                  Integer.valueOf(this.mDisplayId)));
    }

    @Override // com.android.server.wm.WindowManagerInternal.MagnificationCallbacks
    public void onImeWindowVisibilityChanged(boolean z) {
      FullScreenMagnificationController.this
          .mControllerCtx
          .getHandler()
          .sendMessage(
              PooledLambda.obtainMessage(
                  new TriConsumer() { // from class:
                    // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda5
                    public final void accept(Object obj, Object obj2, Object obj3) {
                      ((FullScreenMagnificationController) obj)
                          .notifyImeWindowVisibilityChanged(
                              ((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
                    }
                  },
                  FullScreenMagnificationController.this,
                  Integer.valueOf(this.mDisplayId),
                  Boolean.valueOf(z)));
    }

    public void updateMagnificationRegion(Region region) {
      synchronized (FullScreenMagnificationController.this.mLock) {
        if (this.mRegistered) {
          if (!this.mMagnificationRegion.equals(region)) {
            this.mMagnificationRegion.set(region);
            this.mMagnificationRegion.getBounds(this.mMagnificationBounds);
            refreshThumbnail(getScale(), getCenterX(), getCenterY());
            MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
            if (updateCurrentSpecWithOffsetsLocked(
                magnificationSpec.offsetX, magnificationSpec.offsetY)) {
              sendSpecToAnimation(this.mCurrentMagnificationSpec, null);
            }
            onMagnificationChangedLocked();
          }
          region.recycle();
        }
      }
    }

    public void sendSpecToAnimation(
        MagnificationSpec magnificationSpec,
        MagnificationAnimationCallback magnificationAnimationCallback) {
      if (FullScreenMagnificationController.SEC_DEBUG) {
        Slog.i(
            "FullScreenMagnificationController",
            "sendSpecToAnimation(spec = "
                + magnificationSpec
                + ", animationCallback = "
                + magnificationAnimationCallback
                + ")");
      }
      if (Thread.currentThread().getId() == FullScreenMagnificationController.this.mMainThreadId) {
        this.mSpecAnimationBridge.updateSentSpecMainThread(
            magnificationSpec, magnificationAnimationCallback);
      } else {
        FullScreenMagnificationController.this
            .mControllerCtx
            .getHandler()
            .sendMessage(
                PooledLambda.obtainMessage(
                    new TriConsumer() { // from class:
                      // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2
                      public final void accept(Object obj, Object obj2, Object obj3) {
                        ((FullScreenMagnificationController.SpecAnimationBridge) obj)
                            .updateSentSpecMainThread(
                                (MagnificationSpec) obj2, (MagnificationAnimationCallback) obj3);
                      }
                    },
                    this.mSpecAnimationBridge,
                    magnificationSpec,
                    magnificationAnimationCallback));
      }
    }

    public int getIdOfLastServiceToMagnify() {
      return this.mIdOfLastServiceToMagnify;
    }

    public void onMagnificationChangedLocked() {
      float scale = getScale();
      float centerX = getCenterX();
      float centerY = getCenterY();
      final MagnificationConfig build =
          new MagnificationConfig.Builder()
              .setMode(1)
              .setActivated(this.mMagnificationActivated)
              .setScale(scale)
              .setCenterX(centerX)
              .setCenterY(centerY)
              .build();
      FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(
          new Consumer() { // from class:
            // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              FullScreenMagnificationController.DisplayMagnification.this
                  .lambda$onMagnificationChangedLocked$0(
                      build,
                      (FullScreenMagnificationController.MagnificationInfoChangedCallback) obj);
            }
          });
      if (this.mUnregisterPending && !isActivated()) {
        unregister(this.mDeleteAfterUnregister);
      }
      if (isActivated()) {
        updateThumbnail(scale, centerX, centerY);
      } else {
        hideThumbnail();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMagnificationChangedLocked$0(
        MagnificationConfig magnificationConfig,
        MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
      magnificationInfoChangedCallback.onFullScreenMagnificationChanged(
          this.mDisplayId, this.mMagnificationRegion, magnificationConfig);
    }

    public boolean magnificationRegionContains(float f, float f2) {
      return this.mMagnificationRegion.contains((int) f, (int) f2);
    }

    public void getMagnificationBounds(Rect rect) {
      rect.set(this.mMagnificationBounds);
    }

    public void getMagnificationRegion(Region region) {
      region.set(this.mMagnificationRegion);
    }

    public final DisplayMetrics getDisplayMetricsForId() {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      DisplayInfo displayInfo =
          FullScreenMagnificationController.this.mDisplayManagerInternal.getDisplayInfo(
              this.mDisplayId);
      if (displayInfo != null) {
        displayInfo.getLogicalMetrics(
            displayMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, (Configuration) null);
      } else {
        displayMetrics.setToDefaults();
      }
      return displayMetrics;
    }

    public void requestRectangleOnScreen(int i, int i2, int i3, int i4) {
      float f;
      synchronized (FullScreenMagnificationController.this.mLock) {
        Rect rect = this.mTempRect;
        getMagnificationBounds(rect);
        if (rect.intersects(i, i2, i3, i4)) {
          Rect rect2 = this.mTempRect1;
          getMagnifiedFrameInContentCoordsLocked(rect2);
          float width = rect2.width() / 4.0f;
          float applyDimension = TypedValue.applyDimension(1, 10.0f, getDisplayMetricsForId());
          int i5 = i3 - i;
          int width2 = rect2.width();
          float f2 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
          if (i5 > width2) {
            if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0) {
              f = i - rect2.left;
            } else {
              f = i3 - rect2.right;
            }
          } else {
            if (i < rect2.left) {
              f = (i - r4) - width;
            } else {
              f = i3 > rect2.right ? (i3 - r8) + width : 0.0f;
            }
          }
          if (i4 - i2 > rect2.height()) {
            f2 = i2 - rect2.top;
          } else {
            if (i2 < rect2.top) {
              f2 = (i2 - r10) - applyDimension;
            } else {
              if (i4 > rect2.bottom) {
                f2 = (i4 - r9) + applyDimension;
              }
            }
          }
          float scale = getScale();
          offsetMagnifiedRegion(f * scale, f2 * scale, -1);
        }
      }
    }

    public void getMagnifiedFrameInContentCoordsLocked(Rect rect) {
      float sentScale = getSentScale();
      float sentOffsetX = getSentOffsetX();
      float sentOffsetY = getSentOffsetY();
      getMagnificationBounds(rect);
      rect.offset((int) (-sentOffsetX), (int) (-sentOffsetY));
      rect.scale(1.0f / sentScale);
    }

    public final boolean setActivated(boolean z) {
      boolean z2 = this.mMagnificationActivated != z;
      if (z2) {
        this.mMagnificationActivated = z;
        FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(
            new Consumer() { // from class:
              // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                FullScreenMagnificationController.DisplayMagnification.this.lambda$setActivated$1(
                    (FullScreenMagnificationController.MagnificationInfoChangedCallback) obj);
              }
            });
        FullScreenMagnificationController.this
            .mControllerCtx
            .getWindowManager()
            .setForceShowMagnifiableBounds(this.mDisplayId, z);
      }
      return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActivated$1(
        MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
      magnificationInfoChangedCallback.onFullScreenMagnificationActivationState(
          this.mDisplayId, this.mMagnificationActivated);
    }

    public boolean reset(boolean z) {
      return reset(FullScreenMagnificationController.transformToStubCallback(z));
    }

    public boolean reset(MagnificationAnimationCallback magnificationAnimationCallback) {
      if (!this.mRegistered) {
        return false;
      }
      MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
      boolean isActivated = isActivated();
      setActivated(false);
      if (isActivated) {
        magnificationSpec.clear();
        onMagnificationChangedLocked();
      }
      this.mIdOfLastServiceToMagnify = -1;
      sendSpecToAnimation(magnificationSpec, magnificationAnimationCallback);
      MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
      if (magnificationThumbnail != null) {
        magnificationThumbnail.destroyThumbnail();
      }
      return isActivated;
    }

    public boolean setScale(float f, float f2, float f3, boolean z, int i) {
      if (!this.mRegistered) {
        return false;
      }
      float constrainScale = MagnificationScaleProvider.constrainScale(f);
      this.mMagnificationRegion.getBounds(this.mTempRect);
      MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
      float f4 = magnificationSpec.scale;
      float width = (((r7.width() / 2.0f) - magnificationSpec.offsetX) + r7.left) / f4;
      float f5 = magnificationSpec.offsetY;
      float height = (((r7.height() / 2.0f) - f5) + r7.top) / f4;
      float f6 = (f2 - magnificationSpec.offsetX) / f4;
      float f7 = (f3 - f5) / f4;
      float f8 = f4 / constrainScale;
      float f9 = (height - f7) * f8;
      this.mIdOfLastServiceToMagnify = i;
      return setScaleAndCenter(
          constrainScale,
          f6 + ((width - f6) * f8),
          f7 + f9,
          FullScreenMagnificationController.transformToStubCallback(z),
          i);
    }

    public boolean setScaleAndCenter(
        float f,
        float f2,
        float f3,
        MagnificationAnimationCallback magnificationAnimationCallback,
        int i) {
      if (!this.mRegistered) {
        return false;
      }
      if (FullScreenMagnificationController.SEC_DEBUG) {
        Slog.i(
            "FullScreenMagnificationController",
            "setScaleAndCenterLocked(scale = "
                + f
                + ", centerX = "
                + f2
                + ", centerY = "
                + f3
                + ", endCallback = "
                + magnificationAnimationCallback
                + ", id = "
                + i
                + ")");
      }
      boolean updateMagnificationSpecLocked =
          updateMagnificationSpecLocked(f, f2, f3) | setActivated(true);
      sendSpecToAnimation(this.mCurrentMagnificationSpec, magnificationAnimationCallback);
      if (isActivated() && i != -1) {
        this.mIdOfLastServiceToMagnify = i;
        FullScreenMagnificationController.this.mMagnificationInfoChangedCallbacks.forEach(
            new Consumer() { // from class:
              // com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                FullScreenMagnificationController.DisplayMagnification.this
                    .lambda$setScaleAndCenter$2(
                        (FullScreenMagnificationController.MagnificationInfoChangedCallback) obj);
              }
            });
      }
      return updateMagnificationSpecLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScaleAndCenter$2(
        MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
      magnificationInfoChangedCallback.onRequestMagnificationSpec(
          this.mDisplayId, this.mIdOfLastServiceToMagnify);
    }

    public void updateThumbnail(float f, float f2, float f3) {
      MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
      if (magnificationThumbnail != null) {
        magnificationThumbnail.updateThumbnail(f, f2, f3);
      }
    }

    public void refreshThumbnail(float f, float f2, float f3) {
      MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
      if (magnificationThumbnail != null) {
        magnificationThumbnail.setThumbnailBounds(this.mMagnificationBounds, f, f2, f3);
      }
    }

    public void hideThumbnail() {
      MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
      if (magnificationThumbnail != null) {
        magnificationThumbnail.hideThumbnail();
      }
    }

    public void createThumbnailIfSupported() {
      if (this.mMagnificationThumbnail == null) {
        this.mMagnificationThumbnail =
            (MagnificationThumbnail)
                FullScreenMagnificationController.this.mThumbnailSupplier.get();
        refreshThumbnail(getScale(), getCenterX(), getCenterY());
      }
    }

    public void destroyThumbnail() {
      MagnificationThumbnail magnificationThumbnail = this.mMagnificationThumbnail;
      if (magnificationThumbnail != null) {
        magnificationThumbnail.destroyThumbnail();
        this.mMagnificationThumbnail = null;
      }
    }

    public void onThumbnailFeatureFlagChanged() {
      synchronized (FullScreenMagnificationController.this.mLock) {
        destroyThumbnail();
        createThumbnailIfSupported();
      }
    }

    public boolean updateMagnificationSpecLocked(float f, float f2, float f3) {
      boolean z;
      if (Float.isNaN(f2)) {
        f2 = getCenterX();
      }
      if (Float.isNaN(f3)) {
        f3 = getCenterY();
      }
      if (Float.isNaN(f)) {
        f = getScale();
      }
      float constrainScale = MagnificationScaleProvider.constrainScale(f);
      if (Float.compare(this.mCurrentMagnificationSpec.scale, constrainScale) != 0) {
        this.mCurrentMagnificationSpec.scale = constrainScale;
        z = true;
      } else {
        z = false;
      }
      Rect rect = this.mMagnificationBounds;
      boolean updateCurrentSpecWithOffsetsLocked =
          updateCurrentSpecWithOffsetsLocked(
                  ((this.mMagnificationBounds.width() / 2.0f) + rect.left) - (f2 * constrainScale),
                  ((rect.height() / 2.0f) + this.mMagnificationBounds.top) - (f3 * constrainScale))
              | z;
      if (updateCurrentSpecWithOffsetsLocked) {
        onMagnificationChangedLocked();
      }
      return updateCurrentSpecWithOffsetsLocked;
    }

    public void offsetMagnifiedRegion(float f, float f2, int i) {
      if (this.mRegistered) {
        MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec;
        if (updateCurrentSpecWithOffsetsLocked(
            magnificationSpec.offsetX - f, magnificationSpec.offsetY - f2)) {
          onMagnificationChangedLocked();
        }
        if (i != -1) {
          this.mIdOfLastServiceToMagnify = i;
        }
        sendSpecToAnimation(this.mCurrentMagnificationSpec, null);
      }
    }

    public boolean updateCurrentSpecWithOffsetsLocked(float f, float f2) {
      boolean z;
      if (FullScreenMagnificationController.SEC_DEBUG) {
        Slog.i(
            "FullScreenMagnificationController",
            "updateCurrentSpecWithOffsetsLocked(nonNormOffsetX = "
                + f
                + ", nonNormOffsetY = "
                + f2
                + ")");
      }
      float constrain = MathUtils.constrain(f, getMinOffsetXLocked(), getMaxOffsetXLocked());
      if (Float.compare(this.mCurrentMagnificationSpec.offsetX, constrain) != 0) {
        this.mCurrentMagnificationSpec.offsetX = constrain;
        z = true;
      } else {
        z = false;
      }
      float constrain2 = MathUtils.constrain(f2, getMinOffsetYLocked(), getMaxOffsetYLocked());
      if (Float.compare(this.mCurrentMagnificationSpec.offsetY, constrain2) == 0) {
        return z;
      }
      this.mCurrentMagnificationSpec.offsetY = constrain2;
      return true;
    }

    public float getMinOffsetXLocked() {
      float width = this.mMagnificationBounds.left + this.mMagnificationBounds.width();
      return width - (this.mCurrentMagnificationSpec.scale * width);
    }

    public float getMaxOffsetXLocked() {
      int i = this.mMagnificationBounds.left;
      return i - (i * this.mCurrentMagnificationSpec.scale);
    }

    public float getMinOffsetYLocked() {
      float height = this.mMagnificationBounds.top + this.mMagnificationBounds.height();
      return height - (this.mCurrentMagnificationSpec.scale * height);
    }

    public float getMaxOffsetYLocked() {
      int i = this.mMagnificationBounds.top;
      return i - (i * this.mCurrentMagnificationSpec.scale);
    }

    public String toString() {
      return "DisplayMagnification[mCurrentMagnificationSpec="
          + this.mCurrentMagnificationSpec
          + ", mMagnificationRegion="
          + this.mMagnificationRegion
          + ", mMagnificationBounds="
          + this.mMagnificationBounds
          + ", mDisplayId="
          + this.mDisplayId
          + ", mIdOfLastServiceToMagnify="
          + this.mIdOfLastServiceToMagnify
          + ", mRegistered="
          + this.mRegistered
          + ", mUnregisterPending="
          + this.mUnregisterPending
          + ']';
    }
  }

  public FullScreenMagnificationController(
      Context context,
      AccessibilityTraceManager accessibilityTraceManager,
      Object obj,
      MagnificationInfoChangedCallback magnificationInfoChangedCallback,
      MagnificationScaleProvider magnificationScaleProvider,
      Executor executor) {
    this(
        new ControllerContext(
            context,
            accessibilityTraceManager,
            (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class),
            new Handler(context.getMainLooper()),
            context.getResources().getInteger(R.integer.config_longAnimTime)),
        obj,
        magnificationInfoChangedCallback,
        magnificationScaleProvider,
        (Supplier) null,
        executor);
  }

  public FullScreenMagnificationController(
      final ControllerContext controllerContext,
      Object obj,
      MagnificationInfoChangedCallback magnificationInfoChangedCallback,
      MagnificationScaleProvider magnificationScaleProvider,
      Supplier supplier,
      Executor executor) {
    this.mMagnificationInfoChangedCallbacks = new ArrayList();
    this.mDisplays = new SparseArray(0);
    this.mTempRect = new Rect();
    this.mMagnificationFollowTypingEnabled = true;
    this.mAlwaysOnMagnificationEnabled = false;
    this.mControllerCtx = controllerContext;
    this.mLock = obj;
    this.mMainThreadId = controllerContext.getContext().getMainLooper().getThread().getId();
    this.mScreenStateObserver = new ScreenStateObserver(controllerContext.getContext(), this);
    addInfoChangedCallback(magnificationInfoChangedCallback);
    this.mScaleProvider = magnificationScaleProvider;
    this.mDisplayManagerInternal =
        (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
    MagnificationThumbnailFeatureFlag magnificationThumbnailFeatureFlag =
        new MagnificationThumbnailFeatureFlag();
    this.mMagnificationThumbnailFeatureFlag = magnificationThumbnailFeatureFlag;
    magnificationThumbnailFeatureFlag.addOnChangedListener(
        executor,
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            FullScreenMagnificationController.this.onMagnificationThumbnailFeatureFlagChanged();
          }
        });
    if (supplier != null) {
      this.mThumbnailSupplier = supplier;
    } else {
      this.mThumbnailSupplier =
          new Supplier() { // from class:
            // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
              MagnificationThumbnail lambda$new$0;
              lambda$new$0 = FullScreenMagnificationController.this.lambda$new$0(controllerContext);
              return lambda$new$0;
            }
          };
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ MagnificationThumbnail lambda$new$0(ControllerContext controllerContext) {
    if (this.mMagnificationThumbnailFeatureFlag.isFeatureFlagEnabled()) {
      return new MagnificationThumbnail(
          controllerContext.getContext(),
          (WindowManager) controllerContext.getContext().getSystemService(WindowManager.class),
          new Handler(controllerContext.getContext().getMainLooper()));
    }
    return null;
  }

  public final void onMagnificationThumbnailFeatureFlagChanged() {
    synchronized (this.mLock) {
      for (int i = 0; i < this.mDisplays.size(); i++) {
        onMagnificationThumbnailFeatureFlagChanged(this.mDisplays.keyAt(i));
      }
    }
  }

  public final void onMagnificationThumbnailFeatureFlagChanged(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return;
      }
      displayMagnification.onThumbnailFeatureFlagChanged();
    }
  }

  public void register(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        displayMagnification = new DisplayMagnification(i);
      }
      if (displayMagnification.isRegistered()) {
        return;
      }
      if (displayMagnification.register()) {
        this.mDisplays.put(i, displayMagnification);
        this.mScreenStateObserver.registerIfNecessary();
      }
    }
  }

  public void unregister(int i) {
    synchronized (this.mLock) {
      unregisterLocked(i, false);
    }
  }

  public void unregisterAll() {
    synchronized (this.mLock) {
      SparseArray clone = this.mDisplays.clone();
      for (int i = 0; i < clone.size(); i++) {
        unregisterLocked(clone.keyAt(i), false);
      }
    }
  }

  @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
  public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
    synchronized (this.mLock) {
      if (this.mMagnificationFollowTypingEnabled) {
        DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
        if (displayMagnification == null) {
          return;
        }
        if (displayMagnification.isActivated()) {
          Rect rect = this.mTempRect;
          displayMagnification.getMagnifiedFrameInContentCoordsLocked(rect);
          if (rect.contains(i2, i3, i4, i5)) {
            return;
          }
          displayMagnification.onRectangleOnScreenRequested(i2, i3, i4, i5);
        }
      }
    }
  }

  public void setMagnificationFollowTypingEnabled(boolean z) {
    this.mMagnificationFollowTypingEnabled = z;
  }

  public void setAlwaysOnMagnificationEnabled(boolean z) {
    this.mAlwaysOnMagnificationEnabled = z;
  }

  public boolean isAlwaysOnMagnificationEnabled() {
    return this.mAlwaysOnMagnificationEnabled;
  }

  public void onUserContextChanged(int i) {
    synchronized (this.mLock) {
      if (isActivated(i)) {
        if (isAlwaysOnMagnificationEnabled()) {
          setScaleAndCenter(i, 1.0f, Float.NaN, Float.NaN, true, 0);
          this.mScaleProvider.putScale(1.0f, i);
        } else if (AccessibilityUtils.getVisiblityShortcutDialog()) {
        } else {
          reset(i, true);
        }
      }
    }
  }

  public void onDisplayRemoved(int i) {
    synchronized (this.mLock) {
      unregisterLocked(i, true);
    }
  }

  public boolean isRegistered(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.isRegistered();
    }
  }

  public boolean isActivated(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.isActivated();
    }
  }

  public boolean magnificationRegionContains(int i, float f, float f2) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.magnificationRegionContains(f, f2);
    }
  }

  public void getMagnificationRegion(int i, Region region) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return;
      }
      displayMagnification.getMagnificationRegion(region);
    }
  }

  public float getScale(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return 1.0f;
      }
      return displayMagnification.getScale();
    }
  }

  public float getLastActivatedScale(int i) {
    return getScale(i);
  }

  public float getCenterX(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      }
      return displayMagnification.getCenterX();
    }
  }

  public float getCenterY(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      }
      return displayMagnification.getCenterY();
    }
  }

  public boolean reset(int i, boolean z) {
    return reset(i, z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null);
  }

  public boolean reset(int i, MagnificationAnimationCallback magnificationAnimationCallback) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.reset(magnificationAnimationCallback);
    }
  }

  public boolean setScale(int i, float f, float f2, float f3, boolean z, int i2) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.setScale(f, f2, f3, z, i2);
    }
  }

  public boolean setCenter(int i, float f, float f2, boolean z, int i2) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.setScaleAndCenter(
          Float.NaN, f, f2, z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null, i2);
    }
  }

  public boolean setScaleAndCenter(int i, float f, float f2, float f3, boolean z, int i2) {
    return setScaleAndCenter(i, f, f2, f3, transformToStubCallback(z), i2);
  }

  public boolean setScaleAndCenter(
      int i,
      float f,
      float f2,
      float f3,
      MagnificationAnimationCallback magnificationAnimationCallback,
      int i2) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return false;
      }
      return displayMagnification.setScaleAndCenter(f, f2, f3, magnificationAnimationCallback, i2);
    }
  }

  public void offsetMagnifiedRegion(int i, float f, float f2, int i2) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return;
      }
      displayMagnification.offsetMagnifiedRegion(f, f2, i2);
    }
  }

  public int getIdOfLastServiceToMagnify(int i) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return -1;
      }
      return displayMagnification.getIdOfLastServiceToMagnify();
    }
  }

  public void persistScale(int i) {
    float scale = getScale(A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP ? i : 0);
    if (scale < 1.0f) {
      return;
    }
    this.mScaleProvider.putScale(scale, i);
  }

  public float getPersistedScale(int i) {
    return MathUtils.constrain(this.mScaleProvider.getScale(i), 1.0f, 8.0f);
  }

  public void resetAllIfNeeded(int i) {
    synchronized (this.mLock) {
      for (int i2 = 0; i2 < this.mDisplays.size(); i2++) {
        resetIfNeeded(this.mDisplays.keyAt(i2), i);
      }
    }
  }

  public boolean resetIfNeeded(int i, boolean z) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification != null && displayMagnification.isActivated()) {
        displayMagnification.reset(z);
        return true;
      }
      return false;
    }
  }

  public boolean resetIfNeeded(int i, int i2) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification != null
          && displayMagnification.isActivated()
          && i2 == displayMagnification.getIdOfLastServiceToMagnify()) {
        displayMagnification.reset(true);
        return true;
      }
      return false;
    }
  }

  public void setForceShowMagnifiableBounds(int i, boolean z) {
    synchronized (this.mLock) {
      DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
      if (displayMagnification == null) {
        return;
      }
      if (z) {
        Settings.Secure.putIntForUser(
            this.mControllerCtx.mContext.getContentResolver(),
            "accessibility_magnification_activated",
            1,
            -2);
      }
      displayMagnification.setActivated(z);
    }
  }

  public void notifyImeWindowVisibilityChanged(final int i, final boolean z) {
    synchronized (this.mLock) {
      this.mMagnificationInfoChangedCallbacks.forEach(
          new Consumer() { // from class:
            // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((FullScreenMagnificationController.MagnificationInfoChangedCallback) obj)
                  .onImeWindowVisibilityChanged(i, z);
            }
          });
    }
  }

  public final void onScreenTurnedOff() {
    this.mControllerCtx
        .getHandler()
        .sendMessage(
            PooledLambda.obtainMessage(
                new BiConsumer() { // from class:
                  // com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda2
                  @Override // java.util.function.BiConsumer
                  public final void accept(Object obj, Object obj2) {
                    ((FullScreenMagnificationController) obj)
                        .resetAllIfNeeded(((Boolean) obj2).booleanValue());
                  }
                },
                this,
                Boolean.FALSE));
  }

  public void resetAllIfNeeded(boolean z) {
    synchronized (this.mLock) {
      for (int i = 0; i < this.mDisplays.size(); i++) {
        resetIfNeeded(this.mDisplays.keyAt(i), z);
      }
    }
  }

  public final void unregisterLocked(int i, boolean z) {
    DisplayMagnification displayMagnification = (DisplayMagnification) this.mDisplays.get(i);
    if (displayMagnification == null) {
      return;
    }
    if (!displayMagnification.isRegistered()) {
      if (z) {
        this.mDisplays.remove(i);
      }
    } else if (!displayMagnification.isActivated()) {
      displayMagnification.unregister(z);
    } else {
      displayMagnification.unregisterPending(z);
    }
  }

  public final void unregisterCallbackLocked(int i, boolean z) {
    if (z) {
      this.mDisplays.remove(i);
    }
    boolean z2 = false;
    for (int i2 = 0;
        i2 < this.mDisplays.size()
            && !(z2 = ((DisplayMagnification) this.mDisplays.valueAt(i2)).isRegistered());
        i2++) {}
    if (z2) {
      return;
    }
    this.mScreenStateObserver.unregister();
  }

  public void addInfoChangedCallback(
      MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
    synchronized (this.mLock) {
      this.mMagnificationInfoChangedCallbacks.add(magnificationInfoChangedCallback);
    }
  }

  public void removeInfoChangedCallback(
      MagnificationInfoChangedCallback magnificationInfoChangedCallback) {
    synchronized (this.mLock) {
      this.mMagnificationInfoChangedCallbacks.remove(magnificationInfoChangedCallback);
    }
  }

  public final boolean traceEnabled() {
    return this.mControllerCtx.getTraceManager().isA11yTracingEnabledForTypes(512L);
  }

  public final void logTrace(String str, String str2) {
    this.mControllerCtx.getTraceManager().logTrace("WindowManagerInternal." + str, 512L, str2);
  }

  public void setSecDebug(boolean z) {
    SEC_DEBUG = z;
  }

  public String toString() {
    return "MagnificationController[, mDisplays="
        + this.mDisplays
        + ", mScaleProvider="
        + this.mScaleProvider
        + "]";
  }

  public class SpecAnimationBridge
      implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    public MagnificationAnimationCallback mAnimationCallback;
    public final ControllerContext mControllerCtx;
    public final int mDisplayId;
    public boolean mEnabled;
    public final MagnificationSpec mEndMagnificationSpec;
    public final Object mLock;
    public final MagnificationSpec mSentMagnificationSpec;
    public final MagnificationSpec mStartMagnificationSpec;
    public final ValueAnimator mValueAnimator;

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {}

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {}

    public SpecAnimationBridge(ControllerContext controllerContext, Object obj, int i) {
      this.mSentMagnificationSpec = new MagnificationSpec();
      this.mStartMagnificationSpec = new MagnificationSpec();
      this.mEndMagnificationSpec = new MagnificationSpec();
      this.mEnabled = false;
      this.mControllerCtx = controllerContext;
      this.mLock = obj;
      this.mDisplayId = i;
      long animationDuration = controllerContext.getAnimationDuration();
      ValueAnimator newValueAnimator = controllerContext.newValueAnimator();
      this.mValueAnimator = newValueAnimator;
      newValueAnimator.setDuration(animationDuration);
      newValueAnimator.setInterpolator(new DecelerateInterpolator(2.5f));
      newValueAnimator.setFloatValues(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
      newValueAnimator.addUpdateListener(this);
      newValueAnimator.addListener(this);
    }

    public void setEnabled(boolean z) {
      synchronized (this.mLock) {
        if (z != this.mEnabled) {
          this.mEnabled = z;
          if (!z) {
            this.mSentMagnificationSpec.clear();
            if (this.mControllerCtx.getTraceManager().isA11yTracingEnabledForTypes(512L)) {
              this.mControllerCtx
                  .getTraceManager()
                  .logTrace(
                      "WindowManagerInternal.setMagnificationSpec",
                      512L,
                      "displayID=" + this.mDisplayId + ";spec=" + this.mSentMagnificationSpec);
            }
            this.mControllerCtx
                .getWindowManager()
                .setMagnificationSpec(this.mDisplayId, this.mSentMagnificationSpec);
          }
        }
      }
    }

    public void updateSentSpecMainThread(
        MagnificationSpec magnificationSpec,
        MagnificationAnimationCallback magnificationAnimationCallback) {
      if (this.mValueAnimator.isRunning()) {
        this.mValueAnimator.cancel();
      }
      this.mAnimationCallback = magnificationAnimationCallback;
      synchronized (this.mLock) {
        if (!this.mSentMagnificationSpec.equals(magnificationSpec)) {
          if (this.mAnimationCallback != null) {
            animateMagnificationSpecLocked(magnificationSpec);
          } else {
            setMagnificationSpecLocked(magnificationSpec);
          }
        } else {
          sendEndCallbackMainThread(true);
        }
      }
    }

    public final void sendEndCallbackMainThread(boolean z) {
      if (this.mAnimationCallback != null) {
        if (FullScreenMagnificationController.SEC_DEBUG) {
          Slog.d("FullScreenMagnificationController", "sendEndCallbackMainThread: " + z);
        }
        this.mAnimationCallback.onResult(z);
        this.mAnimationCallback = null;
      }
    }

    public final void setMagnificationSpecLocked(MagnificationSpec magnificationSpec) {
      if (this.mEnabled) {
        if (FullScreenMagnificationController.SEC_DEBUG) {
          Slog.i("FullScreenMagnificationController", "Sending: " + magnificationSpec);
        }
        this.mSentMagnificationSpec.setTo(magnificationSpec);
        if (this.mControllerCtx.getTraceManager().isA11yTracingEnabledForTypes(512L)) {
          this.mControllerCtx
              .getTraceManager()
              .logTrace(
                  "WindowManagerInternal.setMagnificationSpec",
                  512L,
                  "displayID=" + this.mDisplayId + ";spec=" + this.mSentMagnificationSpec);
        }
        this.mControllerCtx
            .getWindowManager()
            .setMagnificationSpec(this.mDisplayId, this.mSentMagnificationSpec);
      }
    }

    public final void animateMagnificationSpecLocked(MagnificationSpec magnificationSpec) {
      this.mEndMagnificationSpec.setTo(magnificationSpec);
      this.mStartMagnificationSpec.setTo(this.mSentMagnificationSpec);
      this.mValueAnimator.start();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
      synchronized (this.mLock) {
        if (this.mEnabled) {
          float animatedFraction = valueAnimator.getAnimatedFraction();
          MagnificationSpec magnificationSpec = new MagnificationSpec();
          MagnificationSpec magnificationSpec2 = this.mStartMagnificationSpec;
          float f = magnificationSpec2.scale;
          MagnificationSpec magnificationSpec3 = this.mEndMagnificationSpec;
          magnificationSpec.scale = f + ((magnificationSpec3.scale - f) * animatedFraction);
          float f2 = magnificationSpec2.offsetX;
          magnificationSpec.offsetX = f2 + ((magnificationSpec3.offsetX - f2) * animatedFraction);
          float f3 = magnificationSpec2.offsetY;
          magnificationSpec.offsetY = f3 + ((magnificationSpec3.offsetY - f3) * animatedFraction);
          setMagnificationSpecLocked(magnificationSpec);
        }
      }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
      sendEndCallbackMainThread(true);
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
      sendEndCallbackMainThread(false);
    }
  }

  public class ScreenStateObserver extends BroadcastReceiver {
    public final Context mContext;
    public final FullScreenMagnificationController mController;
    public boolean mRegistered = false;

    public ScreenStateObserver(
        Context context, FullScreenMagnificationController fullScreenMagnificationController) {
      this.mContext = context;
      this.mController = fullScreenMagnificationController;
    }

    public void registerIfNecessary() {
      if (this.mRegistered) {
        return;
      }
      this.mContext.registerReceiver(this, new IntentFilter("android.intent.action.SCREEN_OFF"));
      this.mRegistered = true;
    }

    public void unregister() {
      if (this.mRegistered) {
        this.mContext.unregisterReceiver(this);
        this.mRegistered = false;
      }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      this.mController.onScreenTurnedOff();
    }
  }

  public class ControllerContext {
    public final Long mAnimationDuration;
    public final Context mContext;
    public final Handler mHandler;
    public final AccessibilityTraceManager mTrace;
    public final WindowManagerInternal mWindowManager;

    public ControllerContext(
        Context context,
        AccessibilityTraceManager accessibilityTraceManager,
        WindowManagerInternal windowManagerInternal,
        Handler handler,
        long j) {
      this.mContext = context;
      this.mTrace = accessibilityTraceManager;
      this.mWindowManager = windowManagerInternal;
      this.mHandler = handler;
      this.mAnimationDuration = Long.valueOf(j);
    }

    public Context getContext() {
      return this.mContext;
    }

    public AccessibilityTraceManager getTraceManager() {
      return this.mTrace;
    }

    public WindowManagerInternal getWindowManager() {
      return this.mWindowManager;
    }

    public Handler getHandler() {
      return this.mHandler;
    }

    public ValueAnimator newValueAnimator() {
      return new ValueAnimator();
    }

    public long getAnimationDuration() {
      return this.mAnimationDuration.longValue();
    }
  }

  public static MagnificationAnimationCallback transformToStubCallback(boolean z) {
    if (z) {
      return MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK;
    }
    return null;
  }
}
