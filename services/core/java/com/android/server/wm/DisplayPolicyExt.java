package com.android.server.wm;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.view.InsetsState;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DisplayPolicyExt {
  public final Context mContext;
  public CoverPolicy mCoverPolicy;
  public final DisplayPolicy mDisplayPolicy;
  public WindowState mFakeFocusedWindow;
  public WindowState mGameToolsOverlayWindow;
  public WindowState mGameToolsWindow;
  public final Rect mLastPayHandlerFrame = new Rect();
  public boolean mLastPayHandlerVisible;
  public boolean mNavBarImeBtnAllRotationsAllowed;
  public int mNavigationMode;
  public WindowState mPayHandlerWin;
  public final WindowManagerService mService;
  public final TaskbarController mTaskbarController;

  public TaskbarController getTaskbarController() {
    return this.mTaskbarController;
  }

  public DisplayPolicyExt(
      Context context, WindowManagerService windowManagerService, DisplayPolicy displayPolicy) {
    this.mCoverPolicy = null;
    this.mContext = context;
    this.mService = windowManagerService;
    this.mDisplayPolicy = displayPolicy;
    if (WmCoverState.isEnabled() && displayPolicy.mDisplayContent.isDefaultDisplay) {
      this.mCoverPolicy = new CoverPolicy(this);
    }
    this.mTaskbarController = new TaskbarController(this);
  }

  public void beginPostLayoutPolicyLw() {
    if (this.mDisplayPolicy.getDisplayId() == 0) {
      this.mService.mAtmService.mFreeformController.beginPostLayoutPolicyLw();
    }
    this.mFakeFocusedWindow = null;
    CoverPolicy coverPolicy = this.mCoverPolicy;
    if (coverPolicy != null) {
      coverPolicy.beginPostLayoutPolicyLw();
    }
  }

  public void applyPostLayoutPolicyLw(
      WindowState windowState, WindowManager.LayoutParams layoutParams) {
    if (this.mService.mExt.mPolicyExt.getWakingUpReason() == 110
        && (layoutParams.flags & 2097152) != 0
        && layoutParams.userActivityTimeout < 0) {
      layoutParams.userActivityTimeout = 5226L;
      layoutParams.screenDimDuration = 0L;
    }
    if (this.mFakeFocusedWindow == null && (layoutParams.samsungFlags & 65536) != 0) {
      this.mFakeFocusedWindow = windowState;
    }
    CoverPolicy coverPolicy = this.mCoverPolicy;
    if (coverPolicy != null) {
      coverPolicy.applyPostLayoutPolicyLw(windowState, layoutParams);
    }
  }

  public boolean applyForceHidePolicyLw(WindowState windowState) {
    if (this.mService.mAtmService.mFreeformController.applyForceHidePolicyIfNeededLocked(
        windowState)) {
      return true;
    }
    CoverPolicy coverPolicy = this.mCoverPolicy;
    if (coverPolicy != null && coverPolicy.applyForceHidePolicyLw(windowState)) {
      return true;
    }
    if (!CoreRune.FW_LOCK_ONLY_LIVE_WALLPAPER || windowState.mAttrs.type != 2633) {
      return false;
    }
    if (this.mService
        .mAtmService
        .mTaskSupervisor
        .getKeyguardController()
        .isKeyguardWallpaperShowing(windowState.getDisplayId())) {
      WindowState notificationShade = this.mDisplayPolicy.getNotificationShade();
      if (notificationShade != null && notificationShade.isOnScreen()) {
        windowState.show(false, true);
      }
    } else {
      windowState.hide(windowState.isDrawn(), true);
    }
    return true;
  }

  public void finishPostLayoutPolicyLw() {
    boolean z;
    if (this.mDisplayPolicy.mDisplayContent.isDexMode()
        && this.mService.mAtmService.mDexController.getDexModeLocked() != 0
        && this.mDisplayPolicy.isDexForceImmersiveModeEnabled()
        && !this.mDisplayPolicy.isInDexForceImmersiveMode()
        && this.mDisplayPolicy
            .getInsetsPolicy()
            .hasHiddenSources(WindowInsets.Type.navigationBars())) {
      this.mDisplayPolicy.requestTransientTaskBar();
    }
    if (this.mDisplayPolicy.getDisplayId() == 0) {
      this.mService.mAtmService.mFreeformController.finishPostLayoutPolicyLw();
    }
    CoverPolicy coverPolicy = this.mCoverPolicy;
    if (coverPolicy != null) {
      coverPolicy.finishPostLayoutPolicyLw();
    }
    if (isNavigationGestureMode()) {
      Rect rect = new Rect();
      WindowState windowState = this.mPayHandlerWin;
      if (windowState != null) {
        z = windowState.isVisible();
        rect.set(this.mPayHandlerWin.getFrame());
      } else {
        z = false;
      }
      if (z != this.mLastPayHandlerVisible || (z && !rect.equals(this.mLastPayHandlerFrame))) {
        this.mLastPayHandlerVisible = z;
        this.mLastPayHandlerFrame.set(rect);
        this.mDisplayPolicy
            .getStatusBarManagerInternal()
            .notifySamsungPayInfo(this.mDisplayPolicy.mDisplayContent.getDisplayId(), z, rect);
        Log.d(StartingSurfaceController.TAG, "notifySamsungPayInfo: " + z + ", " + rect);
      }
    }
  }

  public CoverPolicy getCoverPolicy() {
    return this.mCoverPolicy;
  }

  public WindowState getFakeFocusedWindow() {
    return this.mFakeFocusedWindow;
  }

  public void finishScreenTurningOn() {
    WindowState windowState = this.mDisplayPolicy.mDisplayContent.mCurrentFocus;
    if (windowState != null) {
      this.mService.mExt.mTspStateController.updateWindowPolicy(windowState);
      if (CoreRune.FW_TSP_DEADZONE) {
        this.mService.mExt.mTspStateController.finishScreenTurningOn();
      }
    }
  }

  public void addWindowLw(final WindowState windowState, WindowManager.LayoutParams layoutParams) {
    int i = layoutParams.type;
    if (i != 2024) {
      if (i == 2099) {
        CoverPolicy coverPolicy = this.mCoverPolicy;
        if (coverPolicy != null) {
          coverPolicy.mCoverWindow = windowState;
        }
      } else if (i == 2430) {
        this.mGameToolsWindow = windowState;
        this.mService.mExt.mPolicyExt.notifyRequestedGameToolsWin(true);
      } else if (i == 2431) {
        this.mGameToolsOverlayWindow = windowState;
      }
    } else if (layoutParams.packageName.startsWith("com.samsung.android.spay")
        || layoutParams.packageName.startsWith("com.samsung.android.rajaampat")) {
      this.mPayHandlerWin = windowState;
    }
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord != null && activityRecord.mPopOverState.hasOptions()) {
      this.mDisplayPolicy.mDisplayContent.mPopOverController.addPopOverWindowLw(windowState);
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR) {
      CustomLetterboxConfiguration.performEnhancedControllerIfNonNull(
          this.mDisplayPolicy.mDisplayContent,
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayPolicyExt$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((CustomLetterboxEnhancedController) obj).addWindowLocked(WindowState.this);
            }
          });
    }
  }

  public void removeWindowLw(final WindowState windowState) {
    CoverPolicy coverPolicy = this.mCoverPolicy;
    if (coverPolicy != null && coverPolicy.mCoverWindow == windowState) {
      coverPolicy.mCoverWindow = null;
    }
    if (this.mPayHandlerWin == windowState) {
      this.mPayHandlerWin = null;
    }
    if (this.mGameToolsWindow == windowState) {
      this.mGameToolsWindow = null;
      this.mService.mExt.mPolicyExt.notifyRequestedGameToolsWin(false);
    } else if (this.mGameToolsOverlayWindow == windowState) {
      this.mGameToolsOverlayWindow = null;
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR) {
      CustomLetterboxConfiguration.performEnhancedControllerIfNonNull(
          this.mDisplayPolicy.mDisplayContent,
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayPolicyExt$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((CustomLetterboxEnhancedController) obj).removeWindowLocked(WindowState.this);
            }
          });
    }
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord == null || !activityRecord.mPopOverState.hasOptions()) {
      return;
    }
    this.mDisplayPolicy.mDisplayContent.mPopOverController.removePopOverWindowLw(windowState);
  }

  public void onConfigurationChanged() {
    Resources currentUserResources = this.mDisplayPolicy.getCurrentUserResources();
    this.mNavigationMode =
        currentUserResources.getInteger(R.integer.config_notificationsBatteryFullARGB);
    this.mNavBarImeBtnAllRotationsAllowed =
        CoreRune.IS_TABLET_DEVICE
            || currentUserResources.getBoolean(
                R.bool.config_letterboxIsPolicyForIgnoringRequestedOrientationEnabled);
  }

  public boolean isGameToolsVisibleLw() {
    WindowState windowState = this.mGameToolsWindow;
    return windowState != null && windowState.isVisible();
  }

  public boolean isGameToolsOverlayVisibleLw() {
    WindowState windowState = this.mGameToolsOverlayWindow;
    return windowState != null && windowState.isVisible();
  }

  public boolean isNavigationGestureMode() {
    return this.mNavigationMode != 0;
  }

  public boolean isImeBtnOnGestureAllowed(int i) {
    return this.mService.mExt.mPolicyExt.isImeBtnOnGestureEnabled()
        && (this.mNavBarImeBtnAllRotationsAllowed
            || this.mDisplayPolicy.mDisplayContent.getDisplayRotation().isAnyPortrait(i));
  }

  public int getForceLightNavigationBar(WindowManager.LayoutParams layoutParams) {
    return (!needsForceLightNavigationBar(layoutParams)
            || isUsingBlurEffect(layoutParams)
            || isUsingTranslucentNavigation(layoutParams)
            || InsetsState.clearsCompatInsets(layoutParams.type, layoutParams.flags, 0, 0))
        ? 0
        : 16;
  }

  public final boolean needsForceLightNavigationBar(WindowManager.LayoutParams layoutParams) {
    return (layoutParams.samsungFlags & 1048576) != 0;
  }

  public boolean isUsingBlurEffect(WindowManager.LayoutParams layoutParams) {
    return ((layoutParams.samsungFlags & 64) == 0 || (layoutParams.flags & 2) == 0) ? false : true;
  }

  public final boolean isUsingTranslucentNavigation(WindowManager.LayoutParams layoutParams) {
    int i = layoutParams.flags;
    return ((Integer.MIN_VALUE & i) == 0 || (i & 134217728) == 0) ? false : true;
  }

  public boolean canBeNavColorWinLw(WindowState windowState, WindowState windowState2) {
    if (windowState == null || windowState == windowState2) {
      return false;
    }
    if (!DisplayPolicy.intersectsAnyInsets(
            windowState.isDimming() ? windowState.getBounds() : windowState.getFrame(),
            windowState.getInsetsState(),
            WindowInsets.Type.navigationBars())
        || windowState.mAttrs.type == 2440) {
      return false;
    }
    if (windowState == this.mDisplayPolicy.getNotificationShade()
        && !this.mDisplayPolicy.isKeyguardShowing()) {
      return false;
    }
    if ((windowState.isImeLayeringTarget() && windowState2 != null && windowState2.mIsImWindow)
        || isBlurringWinNotAffectingLightBarAppearance(windowState)) {
      return false;
    }
    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
    if ((layoutParams.samsungFlags & 32) != 0) {
      return true;
    }
    return (layoutParams.isFullscreen() || windowState.isDimming())
        && windowState.mActivityRecord == null;
  }

  public boolean isBlurringWinNotAffectingLightBarAppearance(WindowState windowState) {
    return (windowState == null
            || !isUsingBlurEffect(windowState.mAttrs)
            || (windowState.mAttrs.samsungFlags & 4194304) == 0)
        ? false
        : true;
  }

  public boolean canBeForceHiddenByAodLw(WindowState windowState) {
    int i;
    int displayId = windowState.getDisplayId();
    if ((!this.mDisplayPolicy.mDisplayContent.isDefaultDisplay
            && !this.mService.mExt.mExtraDisplayPolicy.hasCoverHome(displayId))
        || windowState.mActivityRecord != null
        || (i = windowState.getAttrs().type) == 2000
        || i == 2013
        || i == 2019
        || i == 2040
        || i == 2099) {
      return false;
    }
    if (i != 2415) {
      if (i == 2633) {
        return false;
      }
      switch (i) {
      }
      return false;
    }
    if (CoreRune.FW_AOD_FACE_WIDGET) {
      return false;
    }
    return !getTaskbarController().isTaskbar(windowState)
        && this.mService.mPolicy.getWindowLayerLw(windowState) < 26;
  }

  public void updateConfigurationAndScreenSizeDependentBehaviors(int i, int i2, int i3, int i4) {
    if (CoreRune.FW_TSP_STATE_CONTROLLER && this.mDisplayPolicy.mDisplayContent.isDefaultDisplay) {
      this.mService.mExt.mTspStateController.setDefaultDisplaySizeDensity(i, i2, i3, i4);
    }
  }

  public void screenTurnedOff() {
    if (CoreRune.FW_CHN_PREMIUM_WATCH) {
      this.mService.mExt.getClass();
      throw null;
    }
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.print(str);
    printWriter.println("DisplayPolicyExt");
    String str2 = str + "  ";
    if (this.mPayHandlerWin != null) {
      printWriter.print(str2);
      printWriter.print("mPayHandlerWin=");
      printWriter.print(this.mPayHandlerWin);
      printWriter.print(" mLastPayHandlerVisible=");
      printWriter.print(this.mLastPayHandlerVisible);
      printWriter.print(" mLastPayHandlerFrame=");
      printWriter.println(this.mLastPayHandlerFrame);
      printWriter.print("mNavigationMode=");
      printWriter.println(this.mNavigationMode);
    }
    CoverPolicy coverPolicy = this.mCoverPolicy;
    if (coverPolicy != null) {
      coverPolicy.dump(printWriter, str2);
    }
    if (this.mFakeFocusedWindow != null) {
      printWriter.print(str2);
      printWriter.print("mFakeFocusedWindow=");
      printWriter.println(this.mFakeFocusedWindow);
    }
  }
}
