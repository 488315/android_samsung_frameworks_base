package com.android.server.accessibility.magnification;

import android.accessibilityservice.MagnificationConfig;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.accessibility.MagnificationAnimationCallback;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.wm.WindowManagerInternal;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class MagnificationController
    implements WindowMagnificationManager.Callback,
        MagnificationGestureHandler.Callback,
        FullScreenMagnificationController.MagnificationInfoChangedCallback,
        WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks {
  public static boolean SEC_DEBUG = false;
  public final SparseArray mAccessibilityCallbacksDelegateArray;
  public final AlwaysOnMagnificationFeatureFlag mAlwaysOnMagnificationFeatureFlag;
  public final AccessibilityManagerService mAms;
  public final Executor mBackgroundExecutor;
  public final Context mContext;
  public final SparseIntArray mCurrentMagnificationModeArray;
  public FullScreenMagnificationController mFullScreenMagnificationController;
  public final SparseLongArray mFullScreenModeEnabledTimeArray;
  public final SparseBooleanArray mIsImeVisibleArray;
  public final SparseIntArray mLastMagnificationActivatedModeArray;
  public final Object mLock;
  public int mMagnificationCapabilities;
  public final SparseArray mMagnificationEndRunnableSparseArray;
  public final MagnificationScaleProvider mScaleProvider;
  public final boolean mSupportWindowMagnification;
  public final PointF mTempPoint;
  public final SparseArray mTransitionModes;
  public int mUserId;
  public WindowMagnificationManager mWindowMagnificationMgr;
  public final SparseLongArray mWindowModeEnabledTimeArray;

  public interface TransitionCallBack {
    void onResult(int i, boolean z);
  }

  public MagnificationController(
      final AccessibilityManagerService accessibilityManagerService,
      Object obj,
      Context context,
      MagnificationScaleProvider magnificationScaleProvider,
      Executor executor) {
    this.mTempPoint = new PointF();
    this.mMagnificationEndRunnableSparseArray = new SparseArray();
    this.mMagnificationCapabilities = 1;
    this.mCurrentMagnificationModeArray = new SparseIntArray();
    this.mLastMagnificationActivatedModeArray = new SparseIntArray();
    this.mUserId = 0;
    this.mIsImeVisibleArray = new SparseBooleanArray();
    this.mWindowModeEnabledTimeArray = new SparseLongArray();
    this.mFullScreenModeEnabledTimeArray = new SparseLongArray();
    this.mTransitionModes = new SparseArray();
    this.mAccessibilityCallbacksDelegateArray = new SparseArray();
    this.mAms = accessibilityManagerService;
    this.mLock = obj;
    this.mContext = context;
    this.mScaleProvider = magnificationScaleProvider;
    this.mBackgroundExecutor = executor;
    ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class))
        .getAccessibilityController()
        .setUiChangesForAccessibilityCallbacks(this);
    this.mSupportWindowMagnification = true;
    AlwaysOnMagnificationFeatureFlag alwaysOnMagnificationFeatureFlag =
        new AlwaysOnMagnificationFeatureFlag(context);
    this.mAlwaysOnMagnificationFeatureFlag = alwaysOnMagnificationFeatureFlag;
    Objects.requireNonNull(accessibilityManagerService);
    alwaysOnMagnificationFeatureFlag.addOnChangedListener(
        executor,
        new Runnable() { // from class:
          // com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            AccessibilityManagerService.this.updateAlwaysOnMagnification();
          }
        });
  }

  public MagnificationController(
      AccessibilityManagerService accessibilityManagerService,
      Object obj,
      Context context,
      FullScreenMagnificationController fullScreenMagnificationController,
      WindowMagnificationManager windowMagnificationManager,
      MagnificationScaleProvider magnificationScaleProvider,
      Executor executor) {
    this(accessibilityManagerService, obj, context, magnificationScaleProvider, executor);
    this.mFullScreenMagnificationController = fullScreenMagnificationController;
    this.mWindowMagnificationMgr = windowMagnificationManager;
  }

  @Override // com.android.server.accessibility.magnification.WindowMagnificationManager.Callback
  public void onPerformScaleAction(int i, float f) {
    if (getFullScreenMagnificationController().isActivated(i)) {
      getFullScreenMagnificationController()
          .setScaleAndCenter(i, f, Float.NaN, Float.NaN, false, 0);
      getFullScreenMagnificationController().persistScale(i);
    } else if (getWindowMagnificationMgr().isWindowMagnifierEnabled(i)) {
      getWindowMagnificationMgr().setScale(i, f);
      getWindowMagnificationMgr().persistScale(i);
    }
  }

  @Override // com.android.server.accessibility.magnification.WindowMagnificationManager.Callback
  public void onAccessibilityActionPerformed(int i) {
    updateMagnificationUIControls(i, 2);
  }

  @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback
  public void onTouchInteractionStart(int i, int i2) {
    handleUserInteractionChanged(i, i2);
  }

  @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler.Callback
  public void onTouchInteractionEnd(int i, int i2) {
    handleUserInteractionChanged(i, i2);
  }

  public final void handleUserInteractionChanged(int i, int i2) {
    if (this.mMagnificationCapabilities != 3) {
      return;
    }
    updateMagnificationUIControls(i, i2);
  }

  public final void updateMagnificationUIControls(int i, int i2) {
    boolean z;
    boolean z2;
    int i3;
    boolean isActivated = isActivated(i, i2);
    synchronized (this.mLock) {
      z = true;
      if (isActivated) {
        try {
          if (this.mMagnificationCapabilities == 3) {
            z2 = true;
            if (isActivated || ((i3 = this.mMagnificationCapabilities) != 3 && i3 != 2)) {
              z = false;
            }
          }
        } catch (Throwable th) {
          throw th;
        }
      }
      z2 = false;
      if (isActivated) {}
      z = false;
    }
    if (z2) {
      getWindowMagnificationMgr().showMagnificationButton(i, i2);
    } else {
      getWindowMagnificationMgr().removeMagnificationSettingsPanel(i);
      getWindowMagnificationMgr().removeMagnificationButton(i);
    }
    if (z) {
      return;
    }
    getWindowMagnificationMgr().removeMagnificationSettingsPanel(i);
  }

  public void setSecDebug(boolean z) {
    SEC_DEBUG = z;
  }

  public boolean supportWindowMagnification() {
    return this.mSupportWindowMagnification;
  }

  public void transitionMagnificationModeLocked(
      int i, int i2, TransitionCallBack transitionCallBack) {
    if (isActivated(i, i2)) {
      transitionCallBack.onResult(i, true);
      return;
    }
    PointF currentMagnificationCenterLocked = getCurrentMagnificationCenterLocked(i, i2);
    DisableMagnificationCallback disableMagnificationEndRunnableLocked =
        getDisableMagnificationEndRunnableLocked(i);
    if (currentMagnificationCenterLocked == null && disableMagnificationEndRunnableLocked == null) {
      transitionCallBack.onResult(i, true);
      return;
    }
    if (disableMagnificationEndRunnableLocked != null) {
      if (disableMagnificationEndRunnableLocked.mCurrentMode == i2) {
        disableMagnificationEndRunnableLocked.restoreToCurrentMagnificationMode();
        return;
      } else {
        Slog.w("MagnificationController", "discard duplicate request");
        return;
      }
    }
    if (currentMagnificationCenterLocked == null) {
      Slog.w("MagnificationController", "Invalid center, ignore it");
      transitionCallBack.onResult(i, true);
      return;
    }
    setTransitionState(Integer.valueOf(i), Integer.valueOf(i2));
    FullScreenMagnificationController fullScreenMagnificationController =
        getFullScreenMagnificationController();
    WindowMagnificationManager windowMagnificationMgr = getWindowMagnificationMgr();
    DisableMagnificationCallback disableMagnificationCallback =
        new DisableMagnificationCallback(
            transitionCallBack,
            i,
            i2,
            getTargetModeScaleFromCurrentMagnification(i, i2),
            currentMagnificationCenterLocked,
            true);
    setDisableMagnificationCallbackLocked(i, disableMagnificationCallback);
    if (i2 == 2) {
      fullScreenMagnificationController.reset(i, disableMagnificationCallback);
    } else {
      windowMagnificationMgr.disableWindowMagnification(i, false, disableMagnificationCallback);
    }
  }

  public void transitionMagnificationConfigMode(
      final int i, MagnificationConfig magnificationConfig, boolean z, int i2) {
    float scale;
    float centerX;
    float centerY;
    if (SEC_DEBUG) {
      Slog.d(
          "MagnificationController",
          "transitionMagnificationConfigMode displayId = "
              + i
              + ", config = "
              + magnificationConfig);
    }
    synchronized (this.mLock) {
      final int mode = magnificationConfig.getMode();
      boolean isActivated = magnificationConfig.isActivated();
      PointF currentMagnificationCenterLocked = getCurrentMagnificationCenterLocked(i, mode);
      PointF pointF =
          new PointF(magnificationConfig.getCenterX(), magnificationConfig.getCenterY());
      if (currentMagnificationCenterLocked != null) {
        if (Float.isNaN(magnificationConfig.getCenterX())) {
          centerX = currentMagnificationCenterLocked.x;
        } else {
          centerX = magnificationConfig.getCenterX();
        }
        if (Float.isNaN(magnificationConfig.getCenterY())) {
          centerY = currentMagnificationCenterLocked.y;
        } else {
          centerY = magnificationConfig.getCenterY();
        }
        pointF.set(centerX, centerY);
      }
      DisableMagnificationCallback disableMagnificationEndRunnableLocked =
          getDisableMagnificationEndRunnableLocked(i);
      if (disableMagnificationEndRunnableLocked != null) {
        Slog.w("MagnificationController", "Discard previous animation request");
        disableMagnificationEndRunnableLocked.setExpiredAndRemoveFromListLocked();
      }
      FullScreenMagnificationController fullScreenMagnificationController =
          getFullScreenMagnificationController();
      WindowMagnificationManager windowMagnificationMgr = getWindowMagnificationMgr();
      if (Float.isNaN(magnificationConfig.getScale())) {
        scale = getTargetModeScaleFromCurrentMagnification(i, mode);
      } else {
        scale = magnificationConfig.getScale();
      }
      float f = scale;
      try {
        setTransitionState(Integer.valueOf(i), Integer.valueOf(mode));
        MagnificationAnimationCallback magnificationAnimationCallback =
            z
                ? new MagnificationAnimationCallback() { // from class:
                  // com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda1
                  public final void onResult(boolean z2) {
                    MagnificationController.this.lambda$transitionMagnificationConfigMode$0(
                        i, mode, z2);
                  }
                }
                : null;
        if (mode == 2) {
          fullScreenMagnificationController.reset(i, false);
          if (isActivated) {
            windowMagnificationMgr.enableWindowMagnification(
                i, f, pointF.x, pointF.y, magnificationAnimationCallback, i2);
          } else {
            windowMagnificationMgr.disableWindowMagnification(i, false);
          }
        } else if (mode == 1) {
          windowMagnificationMgr.disableWindowMagnification(i, false, null);
          if (isActivated) {
            if (!fullScreenMagnificationController.isRegistered(i)) {
              fullScreenMagnificationController.register(i);
            }
            fullScreenMagnificationController.setScaleAndCenter(
                i, f, pointF.x, pointF.y, magnificationAnimationCallback, i2);
          } else if (fullScreenMagnificationController.isRegistered(i)) {
            fullScreenMagnificationController.reset(i, false);
          }
        }
      } finally {
        if (!z) {
          this.mAms.changeMagnificationMode(i, mode);
        }
        setTransitionState(Integer.valueOf(i), null);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$transitionMagnificationConfigMode$0(int i, int i2, boolean z) {
    this.mAms.changeMagnificationMode(i, i2);
  }

  public final void setTransitionState(Integer num, Integer num2) {
    synchronized (this.mLock) {
      if (num2 == null && num == null) {
        this.mTransitionModes.clear();
      } else {
        this.mTransitionModes.put(num.intValue(), num2);
      }
    }
  }

  public final float getTargetModeScaleFromCurrentMagnification(int i, int i2) {
    if (i2 == 2) {
      return getFullScreenMagnificationController().getScale(i);
    }
    return getWindowMagnificationMgr().getScale(i);
  }

  public boolean hasDisableMagnificationCallback(int i) {
    synchronized (this.mLock) {
      return getDisableMagnificationEndRunnableLocked(i) != null;
    }
  }

  public final void setCurrentMagnificationModeAndSwitchDelegate(int i, int i2) {
    this.mCurrentMagnificationModeArray.put(i, i2);
    assignMagnificationWindowManagerDelegateByMode(i, i2);
  }

  public final void assignMagnificationWindowManagerDelegateByMode(int i, int i2) {
    if (i2 == 1) {
      this.mAccessibilityCallbacksDelegateArray.put(i, getFullScreenMagnificationController());
    } else if (i2 == 2) {
      this.mAccessibilityCallbacksDelegateArray.put(i, getWindowMagnificationMgr());
    } else {
      this.mAccessibilityCallbacksDelegateArray.delete(i);
    }
  }

  @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
  public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
    WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
        uiChangesForAccessibilityCallbacks;
    synchronized (this.mLock) {
      uiChangesForAccessibilityCallbacks =
          (WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks)
              this.mAccessibilityCallbacksDelegateArray.get(i);
    }
    if (uiChangesForAccessibilityCallbacks != null) {
      uiChangesForAccessibilityCallbacks.onRectangleOnScreenRequested(i, i2, i3, i4, i5);
    }
  }

  @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
  public void onRequestMagnificationSpec(int i, int i2) {
    WindowMagnificationManager windowMagnificationManager;
    synchronized (this.mLock) {
      updateMagnificationUIControls(i, 1);
      windowMagnificationManager = this.mWindowMagnificationMgr;
    }
    if (windowMagnificationManager != null) {
      windowMagnificationManager.disableWindowMagnification(i, false);
    }
  }

  @Override // com.android.server.accessibility.magnification.WindowMagnificationManager.Callback
  public void onWindowMagnificationActivationState(int i, boolean z) {
    long uptimeMillis;
    float lastActivatedScale;
    if (z) {
      synchronized (this.mLock) {
        this.mWindowModeEnabledTimeArray.put(i, SystemClock.uptimeMillis());
        setCurrentMagnificationModeAndSwitchDelegate(i, 2);
        this.mLastMagnificationActivatedModeArray.put(i, 2);
      }
      logMagnificationModeWithImeOnIfNeeded(i);
      disableFullScreenMagnificationIfNeeded(i);
    } else {
      synchronized (this.mLock) {
        setCurrentMagnificationModeAndSwitchDelegate(i, 0);
        uptimeMillis = SystemClock.uptimeMillis() - this.mWindowModeEnabledTimeArray.get(i);
        lastActivatedScale = this.mWindowMagnificationMgr.getLastActivatedScale(i);
      }
      logMagnificationUsageState(2, uptimeMillis, lastActivatedScale);
    }
    updateMagnificationUIControls(i, 2);
  }

  @Override // com.android.server.accessibility.magnification.WindowMagnificationManager.Callback
  public void onChangeMagnificationMode(int i, int i2) {
    this.mAms.changeMagnificationMode(i, i2);
  }

  @Override // com.android.server.accessibility.magnification.WindowMagnificationManager.Callback
  public void onSourceBoundsChanged(int i, Rect rect) {
    if (shouldNotifyMagnificationChange(i, 2)) {
      this.mAms.notifyMagnificationChanged(
          i,
          new Region(rect),
          new MagnificationConfig.Builder()
              .setMode(2)
              .setActivated(getWindowMagnificationMgr().isWindowMagnifierEnabled(i))
              .setScale(getWindowMagnificationMgr().getScale(i))
              .setCenterX(rect.exactCenterX())
              .setCenterY(rect.exactCenterY())
              .build());
    }
  }

  @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
  public void onFullScreenMagnificationChanged(
      int i, Region region, MagnificationConfig magnificationConfig) {
    if (shouldNotifyMagnificationChange(i, 1)) {
      this.mAms.notifyMagnificationChanged(i, region, magnificationConfig);
    }
  }

  public final boolean shouldNotifyMagnificationChange(int i, int i2) {
    synchronized (this.mLock) {
      FullScreenMagnificationController fullScreenMagnificationController =
          this.mFullScreenMagnificationController;
      boolean z =
          fullScreenMagnificationController != null
              && fullScreenMagnificationController.isActivated(i);
      WindowMagnificationManager windowMagnificationManager = this.mWindowMagnificationMgr;
      boolean z2 =
          windowMagnificationManager != null
              && windowMagnificationManager.isWindowMagnifierEnabled(i);
      Integer num = (Integer) this.mTransitionModes.get(i);
      if (((i2 == 1 && z) || (i2 == 2 && z2)) && num == null) {
        return true;
      }
      if (z || z2 || num != null) {
        return num != null && i2 == num.intValue();
      }
      return true;
    }
  }

  public final void disableFullScreenMagnificationIfNeeded(int i) {
    FullScreenMagnificationController fullScreenMagnificationController =
        getFullScreenMagnificationController();
    if ((fullScreenMagnificationController.getIdOfLastServiceToMagnify(i) > 0)
        || isActivated(i, 1)) {
      fullScreenMagnificationController.reset(i, false);
    }
  }

  @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
  public void onFullScreenMagnificationActivationState(int i, boolean z) {
    long uptimeMillis;
    float lastActivatedScale;
    if (z) {
      synchronized (this.mLock) {
        this.mFullScreenModeEnabledTimeArray.put(i, SystemClock.uptimeMillis());
        setCurrentMagnificationModeAndSwitchDelegate(i, 1);
        this.mLastMagnificationActivatedModeArray.put(i, 1);
      }
      logMagnificationModeWithImeOnIfNeeded(i);
      disableWindowMagnificationIfNeeded(i);
    } else {
      synchronized (this.mLock) {
        setCurrentMagnificationModeAndSwitchDelegate(i, 0);
        uptimeMillis = SystemClock.uptimeMillis() - this.mFullScreenModeEnabledTimeArray.get(i);
        lastActivatedScale = this.mFullScreenMagnificationController.getLastActivatedScale(i);
      }
      logMagnificationUsageState(1, uptimeMillis, lastActivatedScale);
    }
    updateMagnificationUIControls(i, 1);
  }

  public final void disableWindowMagnificationIfNeeded(int i) {
    WindowMagnificationManager windowMagnificationMgr = getWindowMagnificationMgr();
    if (isActivated(i, 2)) {
      windowMagnificationMgr.disableWindowMagnification(i, false);
    }
  }

  @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
  public void onImeWindowVisibilityChanged(int i, boolean z) {
    synchronized (this.mLock) {
      this.mIsImeVisibleArray.put(i, z);
    }
    getWindowMagnificationMgr().onImeWindowVisibilityChanged(i, z);
    logMagnificationModeWithImeOnIfNeeded(i);
  }

  public int getLastMagnificationActivatedMode(int i) {
    int i2;
    synchronized (this.mLock) {
      i2 = this.mLastMagnificationActivatedModeArray.get(i, 1);
    }
    return i2;
  }

  public void logMagnificationUsageState(int i, long j, float f) {
    AccessibilityStatsLogUtils.logMagnificationUsageState(i, j, f);
  }

  public void logMagnificationModeWithIme(int i) {
    AccessibilityStatsLogUtils.logMagnificationModeWithImeOn(i);
  }

  public void updateUserIdIfNeeded(int i) {
    FullScreenMagnificationController fullScreenMagnificationController;
    WindowMagnificationManager windowMagnificationManager;
    if (this.mUserId == i) {
      return;
    }
    this.mUserId = i;
    synchronized (this.mLock) {
      fullScreenMagnificationController = this.mFullScreenMagnificationController;
      windowMagnificationManager = this.mWindowMagnificationMgr;
      this.mAccessibilityCallbacksDelegateArray.clear();
      this.mCurrentMagnificationModeArray.clear();
      this.mLastMagnificationActivatedModeArray.clear();
      this.mIsImeVisibleArray.clear();
    }
    this.mScaleProvider.onUserChanged(i);
    if (fullScreenMagnificationController != null) {
      fullScreenMagnificationController.resetAllIfNeeded(false);
    }
    if (windowMagnificationManager != null) {
      windowMagnificationManager.disableAllWindowMagnifiers();
    }
  }

  public void onDisplayRemoved(int i) {
    synchronized (this.mLock) {
      FullScreenMagnificationController fullScreenMagnificationController =
          this.mFullScreenMagnificationController;
      if (fullScreenMagnificationController != null) {
        fullScreenMagnificationController.onDisplayRemoved(i);
      }
      WindowMagnificationManager windowMagnificationManager = this.mWindowMagnificationMgr;
      if (windowMagnificationManager != null) {
        windowMagnificationManager.onDisplayRemoved(i);
      }
      this.mAccessibilityCallbacksDelegateArray.delete(i);
      this.mCurrentMagnificationModeArray.delete(i);
      this.mLastMagnificationActivatedModeArray.delete(i);
      this.mIsImeVisibleArray.delete(i);
    }
    this.mScaleProvider.onDisplayRemoved(i);
  }

  public void onUserRemoved(int i) {
    this.mScaleProvider.onUserRemoved(i);
  }

  public void setMagnificationCapabilities(int i) {
    this.mMagnificationCapabilities = i;
  }

  public void setMagnificationFollowTypingEnabled(boolean z) {
    getWindowMagnificationMgr().setMagnificationFollowTypingEnabled(z);
    getFullScreenMagnificationController().setMagnificationFollowTypingEnabled(z);
  }

  public void setAlwaysOnMagnificationEnabled(boolean z) {
    getFullScreenMagnificationController().setAlwaysOnMagnificationEnabled(z);
  }

  public boolean isAlwaysOnMagnificationFeatureFlagEnabled() {
    return this.mAlwaysOnMagnificationFeatureFlag.isFeatureFlagEnabled();
  }

  public final DisableMagnificationCallback getDisableMagnificationEndRunnableLocked(int i) {
    return (DisableMagnificationCallback) this.mMagnificationEndRunnableSparseArray.get(i);
  }

  public final void setDisableMagnificationCallbackLocked(
      int i, DisableMagnificationCallback disableMagnificationCallback) {
    this.mMagnificationEndRunnableSparseArray.put(i, disableMagnificationCallback);
    if (SEC_DEBUG) {
      Slog.d(
          "MagnificationController",
          "setDisableMagnificationCallbackLocked displayId = "
              + i
              + ", callback = "
              + disableMagnificationCallback);
    }
  }

  public final void logMagnificationModeWithImeOnIfNeeded(int i) {
    synchronized (this.mLock) {
      int i2 = this.mCurrentMagnificationModeArray.get(i, 0);
      if (this.mIsImeVisibleArray.get(i, false) && i2 != 0) {
        logMagnificationModeWithIme(i2);
      }
    }
  }

  public FullScreenMagnificationController getFullScreenMagnificationController() {
    synchronized (this.mLock) {
      if (this.mFullScreenMagnificationController == null) {
        this.mFullScreenMagnificationController =
            new FullScreenMagnificationController(
                this.mContext,
                this.mAms.getTraceManager(),
                this.mLock,
                this,
                this.mScaleProvider,
                this.mBackgroundExecutor);
      }
    }
    return this.mFullScreenMagnificationController;
  }

  public boolean isFullScreenMagnificationControllerInitialized() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mFullScreenMagnificationController != null;
    }
    return z;
  }

  public WindowMagnificationManager getWindowMagnificationMgr() {
    WindowMagnificationManager windowMagnificationManager;
    synchronized (this.mLock) {
      if (this.mWindowMagnificationMgr == null) {
        this.mWindowMagnificationMgr =
            new WindowMagnificationManager(
                this.mContext, this.mLock, this, this.mAms.getTraceManager(), this.mScaleProvider);
      }
      windowMagnificationManager = this.mWindowMagnificationMgr;
    }
    return windowMagnificationManager;
  }

  public final PointF getCurrentMagnificationCenterLocked(int i, int i2) {
    if (i2 == 1) {
      WindowMagnificationManager windowMagnificationManager = this.mWindowMagnificationMgr;
      if (windowMagnificationManager == null
          || !windowMagnificationManager.isWindowMagnifierEnabled(i)) {
        return null;
      }
      this.mTempPoint.set(
          this.mWindowMagnificationMgr.getCenterX(i), this.mWindowMagnificationMgr.getCenterY(i));
    } else {
      FullScreenMagnificationController fullScreenMagnificationController =
          this.mFullScreenMagnificationController;
      if (fullScreenMagnificationController == null
          || !fullScreenMagnificationController.isActivated(i)) {
        return null;
      }
      this.mTempPoint.set(
          this.mFullScreenMagnificationController.getCenterX(i),
          this.mFullScreenMagnificationController.getCenterY(i));
    }
    return this.mTempPoint;
  }

  public boolean isActivated(int i, int i2) {
    boolean z = false;
    if (i2 == 1) {
      synchronized (this.mLock) {
        FullScreenMagnificationController fullScreenMagnificationController =
            this.mFullScreenMagnificationController;
        if (fullScreenMagnificationController == null) {
          return false;
        }
        z = fullScreenMagnificationController.isActivated(i);
      }
    } else if (i2 == 2) {
      synchronized (this.mLock) {
        WindowMagnificationManager windowMagnificationManager = this.mWindowMagnificationMgr;
        if (windowMagnificationManager == null) {
          return false;
        }
        z = windowMagnificationManager.isWindowMagnifierEnabled(i);
      }
    }
    return z;
  }

  public final class DisableMagnificationCallback implements MagnificationAnimationCallback {
    public final boolean mAnimate;
    public final PointF mCurrentCenter;
    public final int mCurrentMode;
    public final float mCurrentScale;
    public final int mDisplayId;
    public boolean mExpired = false;
    public final int mTargetMode;
    public final TransitionCallBack mTransitionCallBack;

    public DisableMagnificationCallback(
        TransitionCallBack transitionCallBack, int i, int i2, float f, PointF pointF, boolean z) {
      PointF pointF2 = new PointF();
      this.mCurrentCenter = pointF2;
      this.mTransitionCallBack = transitionCallBack;
      this.mDisplayId = i;
      this.mTargetMode = i2;
      this.mCurrentMode = i2 ^ 3;
      this.mCurrentScale = f;
      pointF2.set(pointF);
      this.mAnimate = z;
    }

    public void onResult(boolean z) {
      synchronized (MagnificationController.this.mLock) {
        if (MagnificationController.SEC_DEBUG) {
          Slog.d("MagnificationController", "onResult success = " + z);
        }
        if (this.mExpired) {
          return;
        }
        setExpiredAndRemoveFromListLocked();
        MagnificationController.this.setTransitionState(Integer.valueOf(this.mDisplayId), null);
        if (z) {
          adjustCurrentCenterIfNeededLocked();
          applyMagnificationModeLocked(this.mTargetMode);
        } else {
          FullScreenMagnificationController fullScreenMagnificationController =
              MagnificationController.this.getFullScreenMagnificationController();
          if (this.mCurrentMode == 1
              && !fullScreenMagnificationController.isActivated(this.mDisplayId)) {
            MagnificationConfig.Builder builder = new MagnificationConfig.Builder();
            Region region = new Region();
            builder
                .setMode(1)
                .setActivated(fullScreenMagnificationController.isActivated(this.mDisplayId))
                .setScale(fullScreenMagnificationController.getScale(this.mDisplayId))
                .setCenterX(fullScreenMagnificationController.getCenterX(this.mDisplayId))
                .setCenterY(fullScreenMagnificationController.getCenterY(this.mDisplayId));
            fullScreenMagnificationController.getMagnificationRegion(this.mDisplayId, region);
            MagnificationController.this.mAms.notifyMagnificationChanged(
                this.mDisplayId, region, builder.build());
          }
        }
        MagnificationController.this.updateMagnificationUIControls(
            this.mDisplayId, this.mTargetMode);
        TransitionCallBack transitionCallBack = this.mTransitionCallBack;
        if (transitionCallBack != null) {
          transitionCallBack.onResult(this.mDisplayId, z);
        }
      }
    }

    public final void adjustCurrentCenterIfNeededLocked() {
      if (this.mTargetMode == 2) {
        return;
      }
      Region region = new Region();
      MagnificationController.this
          .getFullScreenMagnificationController()
          .getMagnificationRegion(this.mDisplayId, region);
      PointF pointF = this.mCurrentCenter;
      if (region.contains((int) pointF.x, (int) pointF.y)) {
        return;
      }
      Rect bounds = region.getBounds();
      this.mCurrentCenter.set(bounds.exactCenterX(), bounds.exactCenterY());
    }

    public void restoreToCurrentMagnificationMode() {
      synchronized (MagnificationController.this.mLock) {
        if (this.mExpired) {
          return;
        }
        setExpiredAndRemoveFromListLocked();
        MagnificationController.this.setTransitionState(Integer.valueOf(this.mDisplayId), null);
        applyMagnificationModeLocked(this.mCurrentMode);
        MagnificationController.this.updateMagnificationUIControls(
            this.mDisplayId, this.mCurrentMode);
        TransitionCallBack transitionCallBack = this.mTransitionCallBack;
        if (transitionCallBack != null) {
          transitionCallBack.onResult(this.mDisplayId, true);
        }
      }
    }

    public void setExpiredAndRemoveFromListLocked() {
      this.mExpired = true;
      MagnificationController.this.setDisableMagnificationCallbackLocked(this.mDisplayId, null);
    }

    public final void applyMagnificationModeLocked(int i) {
      if (i == 1) {
        FullScreenMagnificationController fullScreenMagnificationController =
            MagnificationController.this.getFullScreenMagnificationController();
        if (!fullScreenMagnificationController.isRegistered(this.mDisplayId)) {
          fullScreenMagnificationController.register(this.mDisplayId);
        }
        int i2 = this.mDisplayId;
        float f = this.mCurrentScale;
        PointF pointF = this.mCurrentCenter;
        fullScreenMagnificationController.setScaleAndCenter(
            i2, f, pointF.x, pointF.y, this.mAnimate, 0);
        return;
      }
      WindowMagnificationManager windowMagnificationMgr =
          MagnificationController.this.getWindowMagnificationMgr();
      int i3 = this.mDisplayId;
      float f2 = this.mCurrentScale;
      PointF pointF2 = this.mCurrentCenter;
      windowMagnificationMgr.enableWindowMagnification(
          i3,
          f2,
          pointF2.x,
          pointF2.y,
          this.mAnimate ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null,
          0);
    }
  }
}
