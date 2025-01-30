package com.android.server.wm;

import android.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.MathUtils;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.ScreenCapture;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WallpaperController {
  public DisplayContent mDisplayContent;
  public final boolean mIsLockscreenLiveWallpaperEnabled;
  public long mLastWallpaperTimeoutTime;
  public final float mMaxWallpaperScale;
  public WindowManagerService mService;
  public boolean mShouldOffsetWallpaperCenter;
  public boolean mShouldUpdateZoom;
  public WindowState mWaitingOnWallpaper;
  public final ArrayList mWallpaperTokens = new ArrayList();
  public WindowState mWallpaperTarget = null;
  public WindowState mPrevWallpaperTarget = null;
  public float mLastWallpaperX = -1.0f;
  public float mLastWallpaperY = -1.0f;
  public float mLastWallpaperXStep = -1.0f;
  public float mLastWallpaperYStep = -1.0f;
  public float mLastWallpaperZoomOut = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public int mLastWallpaperDisplayOffsetX = Integer.MIN_VALUE;
  public int mLastWallpaperDisplayOffsetY = Integer.MIN_VALUE;
  public boolean mLastFrozen = false;
  public int mWallpaperDrawState = 0;
  public Point mLargestDisplaySize = null;
  public final FindWallpaperTargetResult mFindResults = new FindWallpaperTargetResult();
  public final Consumer mFindWallpapers =
      new Consumer() { // from class:
                       // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda0
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
          WallpaperController.this.lambda$new$0((WindowState) obj);
        }
      };
  public final ToBooleanFunction mFindWallpaperTargetFunction =
      new ToBooleanFunction() { // from class:
                                // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda1
        public final boolean apply(Object obj) {
          boolean lambda$new$1;
          lambda$new$1 = WallpaperController.this.lambda$new$1((WindowState) obj);
          return lambda$new$1;
        }
      };
  public Consumer mComputeMaxZoomOutFunction =
      new Consumer() { // from class:
                       // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda2
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
          WallpaperController.this.lambda$new$2((WindowState) obj);
        }
      };
  public final HashMap mRemoteWallpaperAnimAreaMap = new HashMap();

  public static /* synthetic */ boolean lambda$updateWallpaperWindowsTarget$3(
      WindowState windowState, WindowState windowState2) {
    return windowState2 == windowState;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(WindowState windowState) {
    if (windowState.mAttrs.type == 2013) {
      WallpaperWindowToken asWallpaperToken = windowState.mToken.asWallpaperToken();
      if (CoreRune.FW_FOLD_WALLPAPER_POLICY && asWallpaperToken.isWaitingForChangingFoldedType()) {
        return;
      }
      if (asWallpaperToken.canShowWhenLocked()
          && !this.mFindResults.hasTopShowWhenLockedWallpaper()) {
        this.mFindResults.setTopShowWhenLockedWallpaper(windowState);
      } else {
        if (asWallpaperToken.canShowWhenLocked()
            || this.mFindResults.hasTopHideWhenLockedWallpaper()) {
          return;
        }
        this.mFindResults.setTopHideWhenLockedWallpaper(windowState);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* JADX WARN: Code restructure failed: missing block: B:36:0x008f, code lost:

     if (r7.getDisplayId() != 2) goto L81;
  */
  /* JADX WARN: Code restructure failed: missing block: B:38:0x009b, code lost:

     if (r6.mService.mAtmService.mDexController.getDexModeLocked() != 2) goto L81;
  */
  /* JADX WARN: Code restructure failed: missing block: B:40:0x00a7, code lost:

     if (r6.mService.mExt.mPolicyExt.isKeyguardOccluded(2) == false) goto L81;
  */
  /* JADX WARN: Code restructure failed: missing block: B:41:0x00a9, code lost:

     r0 = r6.mFindResults;
  */
  /* JADX WARN: Code restructure failed: missing block: B:42:0x00b1, code lost:

     if (isFullscreen(r7.mAttrs) == false) goto L63;
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x00b3, code lost:

     r5 = r7.mActivityRecord;
  */
  /* JADX WARN: Code restructure failed: missing block: B:44:0x00b5, code lost:

     if (r5 == null) goto L59;
  */
  /* JADX WARN: Code restructure failed: missing block: B:46:0x00bb, code lost:

     if (r5.fillsParent() == false) goto L63;
  */
  /* JADX WARN: Code restructure failed: missing block: B:48:0x00c1, code lost:

     if (r7.inFreeformWindowingMode() == false) goto L62;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x00c4, code lost:

     r5 = false;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x00c7, code lost:

     r0.mNeedsShowWhenLockedWallpaper = r5;
  */
  /* JADX WARN: Code restructure failed: missing block: B:51:0x00c6, code lost:

     r5 = true;
  */
  /* JADX WARN: Removed duplicated region for block: B:69:0x00fd  */
  /* JADX WARN: Removed duplicated region for block: B:76:0x0116 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0122  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public /* synthetic */ boolean lambda$new$1(WindowState windowState) {
    boolean z;
    ActivityRecord activityRecord;
    boolean isShellTransitionsEnabled =
        windowState.mTransitionController.isShellTransitionsEnabled();
    if (!isShellTransitionsEnabled) {
      ActivityRecord activityRecord2 = windowState.mActivityRecord;
      if (activityRecord2 != null
          && !activityRecord2.isVisible()
          && !windowState.mActivityRecord.isAnimating(3)) {
        return false;
      }
    } else {
      ActivityRecord activityRecord3 = windowState.mActivityRecord;
      if (activityRecord3 != null
          && !activityRecord3.isVisibleRequested()
          && !activityRecord3.isVisible()) {
        return false;
      }
    }
    ActivityRecord activityRecord4 = windowState.mActivityRecord;
    WindowContainer animatingContainer =
        activityRecord4 != null ? activityRecord4.getAnimatingContainer() : null;
    if (!isShellTransitionsEnabled
        && animatingContainer != null
        && animatingContainer.isAnimating(3)
        && AppTransition.isKeyguardGoingAwayTransitOld(animatingContainer.mTransit)
        && (animatingContainer.mTransitFlags & 4) != 0) {
      this.mFindResults.setUseTopWallpaperAsTarget(true);
    }
    if (this.mService.mPolicy.isKeyguardLocked()
        && (windowState.canShowWhenLocked() || isShowWhenLockedFreeform(windowState))) {
      if (!this.mService.mPolicy.isKeyguardOccluded()) {
        if (isShellTransitionsEnabled) {}
        boolean z2 =
            !windowState.hasWallpaper()
                || (animatingContainer == null
                    && animatingContainer.getAnimation() != null
                    && animatingContainer.getAnimation().getShowWallpaper());
        if (!isRecentsTransitionTarget(windowState) || isBackNavigationTarget(windowState)) {
          this.mFindResults.setWallpaperTarget(windowState);
          return true;
        }
        if (!z2 || !windowState.isOnScreen()) {
          return false;
        }
        if (this.mWallpaperTarget != windowState && !windowState.isDrawFinishedLw()) {
          return false;
        }
        if (this.mService.mAtmService.mMultiTaskingController.shouldNotSupportWallpaper()
            && windowState.inMultiWindowMode()) {
          return false;
        }
        if (windowState.inFreeformWindowingMode() && !z) {
          return false;
        }
        this.mFindResults.setWallpaperTarget(windowState);
        if (windowState == this.mWallpaperTarget) {
          windowState.isAnimating(3);
        }
        this.mFindResults.setIsWallpaperTargetForLetterbox(
            windowState.hasWallpaperForLetterboxBackground());
        return windowState.mActivityRecord != null
            || (CoreRune.FW_INFINITY_WALLPAPER_IN_AOD
                && this.mService.mPolicy.isKeyguardHostWindow(windowState.mAttrs));
      }
      if (!shouldIgnoreShowWhenLockedWallpaper(windowState)) {
        this.mFindResults.mNeedsShowWhenLockedWallpaper =
            (isFullscreen(windowState.mAttrs)
                    && ((activityRecord = windowState.mActivityRecord) == null
                        || activityRecord.fillsParent()))
                ? false
                : true;
      }
      if (windowState.inFreeformWindowingMode()) {
        this.mFindResults.mNeedsShowWhenLockedWallpaper |= true;
        z = true;
        if (windowState.hasWallpaper()) {}
        if (!isRecentsTransitionTarget(windowState)) {}
        this.mFindResults.setWallpaperTarget(windowState);
        return true;
      }
    }
    z = false;
    if (windowState.hasWallpaper()) {}
    if (!isRecentsTransitionTarget(windowState)) {}
    this.mFindResults.setWallpaperTarget(windowState);
    return true;
  }

  public final boolean shouldIgnoreShowWhenLockedWallpaper(WindowState windowState) {
    ActivityRecord activityRecord;
    Transition collectingTransition;
    if (windowState.isActivityTypeDream()) {
      return true;
    }
    return CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
        && (activityRecord = windowState.mActivityRecord) != null
        && activityRecord.finishing
        && !activityRecord.fillsParent()
        && (collectingTransition = windowState.mTransitionController.getCollectingTransition())
            != null
        && (collectingTransition.getFlags() & 14592) == 0;
  }

  public final boolean isRecentsTransitionTarget(WindowState windowState) {
    if (windowState.mTransitionController.isShellTransitionsEnabled()) {
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_RAPID_RECENTS_TRANSIT
          && canBeWallpaperTargetForTransientLaunch(windowState)) {
        return true;
      }
      return windowState.mActivityRecord != null
          && windowState.mAttrs.type == 1
          && this.mDisplayContent.isKeyguardLocked()
          && windowState.mTransitionController.isTransientHide(windowState.getTask());
    }
    RecentsAnimationController recentsAnimationController =
        this.mService.getRecentsAnimationController();
    return recentsAnimationController != null
        && recentsAnimationController.isWallpaperVisible(windowState);
  }

  public final boolean isBackNavigationTarget(WindowState windowState) {
    return this.mService.mAtmService.mBackNavigationController.isWallpaperVisible(windowState);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$2(WindowState windowState) {
    if (windowState.mIsWallpaper
        || Float.compare(windowState.mWallpaperZoomOut, this.mLastWallpaperZoomOut) <= 0) {
      return;
    }
    this.mLastWallpaperZoomOut = windowState.mWallpaperZoomOut;
  }

  public WallpaperController(
      WindowManagerService windowManagerService, DisplayContent displayContent) {
    this.mService = windowManagerService;
    this.mDisplayContent = displayContent;
    Resources resources = windowManagerService.mContext.getResources();
    this.mMaxWallpaperScale = resources.getFloat(R.dimen.config_scrollbarSize);
    this.mShouldOffsetWallpaperCenter =
        resources.getBoolean(R.bool.config_lockscreenWeatherEnabledByDefault);
    this.mIsLockscreenLiveWallpaperEnabled =
        SystemProperties.getBoolean("persist.wm.debug.lockscreen_live_wallpaper", false);
  }

  public void resetLargestDisplay(Display display) {
    if (display == null || display.getType() != 1) {
      return;
    }
    this.mLargestDisplaySize = null;
  }

  public void setShouldOffsetWallpaperCenter(boolean z) {
    this.mShouldOffsetWallpaperCenter = z;
  }

  public final Point findLargestDisplaySize() {
    if (!this.mShouldOffsetWallpaperCenter) {
      return null;
    }
    Point point = new Point();
    List possibleDisplayInfoLocked = this.mService.getPossibleDisplayInfoLocked(0);
    for (int i = 0; i < possibleDisplayInfoLocked.size(); i++) {
      DisplayInfo displayInfo = (DisplayInfo) possibleDisplayInfoLocked.get(i);
      if (displayInfo.type == 1
          && Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight)
              > Math.max(point.x, point.y)) {
        point.set(displayInfo.logicalWidth, displayInfo.logicalHeight);
      }
    }
    return point;
  }

  public WindowState getWallpaperTarget() {
    return this.mWallpaperTarget;
  }

  public boolean isWallpaperTarget(WindowState windowState) {
    return windowState == this.mWallpaperTarget;
  }

  public boolean isBelowWallpaperTarget(WindowState windowState) {
    WindowState windowState2 = this.mWallpaperTarget;
    return windowState2 != null && windowState2.mLayer >= windowState.mBaseLayer;
  }

  public boolean isWallpaperVisible() {
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      if (((WallpaperWindowToken) this.mWallpaperTokens.get(size)).isVisible()) {
        return true;
      }
    }
    return false;
  }

  public final boolean shouldWallpaperBeVisible(WindowState windowState) {
    return (windowState == null && this.mPrevWallpaperTarget == null) ? false : true;
  }

  public boolean isWallpaperTargetAnimating() {
    ActivityRecord activityRecord;
    WindowState windowState = this.mWallpaperTarget;
    return windowState != null
        && windowState.isAnimating(3)
        && ((activityRecord = this.mWallpaperTarget.mActivityRecord) == null
            || !activityRecord.isWaitingForTransitionStart());
  }

  public void updateWallpaperVisibility() {
    boolean shouldWallpaperBeVisible = shouldWallpaperBeVisible(this.mWallpaperTarget);
    WindowState topWallpaper =
        this.mFindResults.getTopWallpaper(this.mDisplayContent.isKeyguardLocked());
    WallpaperWindowToken asWallpaperToken =
        topWallpaper == null ? null : topWallpaper.mToken.asWallpaperToken();
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      WallpaperWindowToken wallpaperWindowToken =
          (WallpaperWindowToken) this.mWallpaperTokens.get(size);
      wallpaperWindowToken.setVisibility(
          shouldWallpaperBeVisible && wallpaperWindowToken == asWallpaperToken);
    }
  }

  public void showWallpaperInTransition(boolean z) {
    updateWallpaperWindowsTarget(this.mFindResults);
    if (this.mFindResults.hasTopShowWhenLockedWallpaper()) {
      FindWallpaperTargetResult findWallpaperTargetResult = this.mFindResults;
      if (findWallpaperTargetResult.wallpaperTarget != null) {
        FindWallpaperTargetResult.TopWallpaper topWallpaper =
            findWallpaperTargetResult.mTopWallpaper;
        WindowState windowState = topWallpaper.mTopHideWhenLockedWallpaper;
        WindowState windowState2 = topWallpaper.mTopShowWhenLockedWallpaper;
        if (!findWallpaperTargetResult.hasTopHideWhenLockedWallpaper()) {
          windowState2.mToken.asWallpaperToken().updateWallpaperWindows(true);
          return;
        } else {
          windowState.mToken.asWallpaperToken().updateWallpaperWindowsInTransition(z);
          windowState2.mToken.asWallpaperToken().updateWallpaperWindowsInTransition(!z);
          return;
        }
      }
    }
    Slog.w(StartingSurfaceController.TAG, "There is no wallpaper for the lock screen");
  }

  public void hideDeferredWallpapersIfNeededLegacy() {
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      WallpaperWindowToken wallpaperWindowToken =
          (WallpaperWindowToken) this.mWallpaperTokens.get(size);
      if (!wallpaperWindowToken.isVisibleRequested()) {
        wallpaperWindowToken.commitVisibility(false);
      }
    }
  }

  public void hideWallpapers(WindowState windowState) {
    WindowState windowState2 = this.mWallpaperTarget;
    if ((windowState2 == null || (windowState2 == windowState && this.mPrevWallpaperTarget == null))
        && !this.mFindResults.useTopWallpaperAsTarget) {
      for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
        WallpaperWindowToken wallpaperWindowToken =
            (WallpaperWindowToken) this.mWallpaperTokens.get(size);
        wallpaperWindowToken.setVisibility(false);
        if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_WALLPAPER)
            && wallpaperWindowToken.isVisible()
            && ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_WALLPAPER,
              1984843251,
              0,
              (String) null,
              new Object[] {
                String.valueOf(wallpaperWindowToken),
                String.valueOf(windowState),
                String.valueOf(this.mWallpaperTarget),
                String.valueOf(this.mPrevWallpaperTarget),
                String.valueOf(Debug.getCallers(5))
              });
        }
      }
    }
  }

  public boolean updateWallpaperOffset(WindowState windowState, boolean z) {
    boolean z2;
    boolean z3;
    Rect parentFrame = windowState.getParentFrame();
    Rect frame = windowState.getFrame();
    int width = frame.width() - parentFrame.width();
    int height = frame.height() - parentFrame.height();
    if ((windowState.mAttrs.flags & 16384) != 0 && Math.abs(width) > 1 && Math.abs(height) > 1) {
      Slog.d(
          StartingSurfaceController.TAG,
          "Skip wallpaper offset with inconsistent orientation, bounds="
              + parentFrame
              + " frame="
              + frame);
      return false;
    }
    float f = windowState.isRtl() ? 1.0f : 0.0f;
    float f2 = this.mLastWallpaperX;
    if (f2 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f = f2;
    }
    float f3 = this.mLastWallpaperXStep;
    if (f3 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f3 = -1.0f;
    }
    int displayWidthOffset = getDisplayWidthOffset(width, parentFrame, windowState.isRtl());
    int i = width - displayWidthOffset;
    int i2 = i > 0 ? -((int) ((i * f) + 0.5f)) : 0;
    int i3 = this.mLastWallpaperDisplayOffsetX;
    if (i3 != Integer.MIN_VALUE) {
      i2 += i3;
    } else if (!windowState.isRtl()) {
      i2 -= displayWidthOffset;
    }
    if (windowState.mWallpaperX == f && windowState.mWallpaperXStep == f3) {
      z2 = false;
    } else {
      windowState.mWallpaperX = f;
      windowState.mWallpaperXStep = f3;
      z2 = true;
    }
    float f4 = this.mLastWallpaperY;
    if (f4 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f4 = 0.5f;
    }
    float f5 = this.mLastWallpaperYStep;
    float f6 = f5 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? f5 : -1.0f;
    int i4 = height > 0 ? -((int) ((height * f4) + 0.5f)) : 0;
    int i5 = this.mLastWallpaperDisplayOffsetY;
    if (i5 != Integer.MIN_VALUE) {
      i4 += i5;
    }
    int i6 = i4;
    if (windowState.mWallpaperY != f4 || windowState.mWallpaperYStep != f6) {
      windowState.mWallpaperY = f4;
      windowState.mWallpaperYStep = f6;
      z2 = true;
    }
    if (Float.compare(windowState.mWallpaperZoomOut, this.mLastWallpaperZoomOut) != 0) {
      windowState.mWallpaperZoomOut = this.mLastWallpaperZoomOut;
      z3 = true;
    } else {
      z3 = z2;
    }
    boolean wallpaperOffset =
        windowState.setWallpaperOffset(
            i2,
            i6,
            windowState.mShouldScaleWallpaper
                ? zoomOutToScale(windowState.mWallpaperZoomOut)
                : 1.0f);
    if (wallpaperOffset) {
      Slog.d(
          StartingSurfaceController.TAG,
          "updateWallpaperOffset: x="
              + i2
              + ", y="
              + i6
              + ", wFrame="
              + frame
              + ", wBounds="
              + parentFrame
              + ", dOffset="
              + displayWidthOffset
              + ", wpx="
              + f
              + ", wpy="
              + f4
              + ", zoom="
              + windowState.mWallpaperZoomOut
              + ", win="
              + windowState);
    }
    if (z3 && (windowState.mAttrs.privateFlags & 4) != 0) {
      if (z) {
        try {
          this.mWaitingOnWallpaper = windowState;
        } catch (RemoteException unused) {
        }
      }
      windowState.mClient.dispatchWallpaperOffsets(
          windowState.mWallpaperX,
          windowState.mWallpaperY,
          windowState.mWallpaperXStep,
          windowState.mWallpaperYStep,
          windowState.mWallpaperZoomOut,
          z);
      if (z && this.mWaitingOnWallpaper != null) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (this.mLastWallpaperTimeoutTime + 10000 < uptimeMillis) {
          try {
            this.mService.mGlobalLock.wait(150L);
          } catch (InterruptedException unused2) {
          }
          if (150 + uptimeMillis < SystemClock.uptimeMillis()) {
            Slog.i(
                StartingSurfaceController.TAG,
                "Timeout waiting for wallpaper to offset: " + windowState);
            this.mLastWallpaperTimeoutTime = uptimeMillis;
          }
        }
        this.mWaitingOnWallpaper = null;
      }
    }
    return wallpaperOffset;
  }

  public final int getDisplayWidthOffset(int i, Rect rect, boolean z) {
    int width;
    if (!this.mShouldOffsetWallpaperCenter) {
      return 0;
    }
    if (this.mLargestDisplaySize == null) {
      this.mLargestDisplaySize = findLargestDisplaySize();
    }
    if (this.mLargestDisplaySize == null
        || this.mLargestDisplaySize.x == (width = rect.width())
        || rect.width() >= rect.height()) {
      return 0;
    }
    float height = rect.height();
    Point point = this.mLargestDisplaySize;
    int round = Math.round(point.x * (height / point.y));
    if (z) {
      return round - ((width + round) / 2);
    }
    return Math.min(round - width, i) / 2;
  }

  public void setWindowWallpaperPosition(
      WindowState windowState, float f, float f2, float f3, float f4) {
    if (windowState.mWallpaperX == f && windowState.mWallpaperY == f2) {
      return;
    }
    windowState.mWallpaperX = f;
    windowState.mWallpaperY = f2;
    windowState.mWallpaperXStep = f3;
    windowState.mWallpaperYStep = f4;
    updateWallpaperOffsetLocked(windowState, true);
  }

  public void setWallpaperZoomOut(WindowState windowState, float f) {
    if (Float.compare(windowState.mWallpaperZoomOut, f) != 0) {
      windowState.mWallpaperZoomOut = f;
      this.mShouldUpdateZoom = true;
      updateWallpaperOffsetLocked(windowState, false);
    }
  }

  public void setShouldZoomOutWallpaper(WindowState windowState, boolean z) {
    if (z != windowState.mShouldScaleWallpaper) {
      windowState.mShouldScaleWallpaper = z;
      updateWallpaperOffsetLocked(windowState, false);
    }
  }

  public void setWindowWallpaperDisplayOffset(WindowState windowState, int i, int i2) {
    if (windowState.mWallpaperDisplayOffsetX == i && windowState.mWallpaperDisplayOffsetY == i2) {
      return;
    }
    windowState.mWallpaperDisplayOffsetX = i;
    windowState.mWallpaperDisplayOffsetY = i2;
    updateWallpaperOffsetLocked(windowState, true);
  }

  public Bundle sendWindowWallpaperCommand(
      WindowState windowState, String str, int i, int i2, int i3, Bundle bundle, boolean z) {
    if (windowState != this.mWallpaperTarget && windowState != this.mPrevWallpaperTarget) {
      return null;
    }
    sendWindowWallpaperCommand(str, i, i2, i3, bundle, z);
    return null;
  }

  public final void sendWindowWallpaperCommand(
      String str, int i, int i2, int i3, Bundle bundle, boolean z) {
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      ((WallpaperWindowToken) this.mWallpaperTokens.get(size))
          .sendWindowWallpaperCommand(str, i, i2, i3, bundle, z);
    }
  }

  public final void updateWallpaperOffsetLocked(WindowState windowState, boolean z) {
    WindowState windowState2 = this.mWallpaperTarget;
    if ((windowState2 == null
            && windowState.mToken.isVisible()
            && windowState.mTransitionController.inTransition())
        || (windowState2 == null && windowState != null)) {
      windowState2 = windowState;
    }
    if (windowState2 != null) {
      float f = windowState2.mWallpaperX;
      if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mLastWallpaperX = f;
      } else {
        float f2 = windowState.mWallpaperX;
        if (f2 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          this.mLastWallpaperX = f2;
        }
      }
      float f3 = windowState2.mWallpaperY;
      if (f3 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mLastWallpaperY = f3;
      } else {
        float f4 = windowState.mWallpaperY;
        if (f4 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          this.mLastWallpaperY = f4;
        }
      }
      computeLastWallpaperZoomOut();
      int i = windowState2.mWallpaperDisplayOffsetX;
      if (i != Integer.MIN_VALUE) {
        this.mLastWallpaperDisplayOffsetX = i;
      } else {
        int i2 = windowState.mWallpaperDisplayOffsetX;
        if (i2 != Integer.MIN_VALUE) {
          this.mLastWallpaperDisplayOffsetX = i2;
        }
      }
      int i3 = windowState2.mWallpaperDisplayOffsetY;
      if (i3 != Integer.MIN_VALUE) {
        this.mLastWallpaperDisplayOffsetY = i3;
      } else {
        int i4 = windowState.mWallpaperDisplayOffsetY;
        if (i4 != Integer.MIN_VALUE) {
          this.mLastWallpaperDisplayOffsetY = i4;
        }
      }
      float f5 = windowState2.mWallpaperXStep;
      if (f5 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mLastWallpaperXStep = f5;
      } else {
        float f6 = windowState.mWallpaperXStep;
        if (f6 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          this.mLastWallpaperXStep = f6;
        }
      }
      float f7 = windowState2.mWallpaperYStep;
      if (f7 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mLastWallpaperYStep = f7;
      } else {
        float f8 = windowState.mWallpaperYStep;
        if (f8 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          this.mLastWallpaperYStep = f8;
        }
      }
    }
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      ((WallpaperWindowToken) this.mWallpaperTokens.get(size)).updateWallpaperOffset(z);
    }
  }

  public void clearLastWallpaperTimeoutTime() {
    this.mLastWallpaperTimeoutTime = 0L;
  }

  public void wallpaperCommandComplete(IBinder iBinder) {
    WindowState windowState = this.mWaitingOnWallpaper;
    if (windowState == null || windowState.mClient.asBinder() != iBinder) {
      return;
    }
    this.mWaitingOnWallpaper = null;
    this.mService.mGlobalLock.notifyAll();
  }

  public void wallpaperOffsetsComplete(IBinder iBinder) {
    WindowState windowState = this.mWaitingOnWallpaper;
    if (windowState == null || windowState.mClient.asBinder() != iBinder) {
      return;
    }
    this.mWaitingOnWallpaper = null;
    this.mService.mGlobalLock.notifyAll();
  }

  public final void findWallpaperTarget() {
    this.mFindResults.reset();
    if (this.mService.mAtmService.mNaturalSwitchingController.isRunning()
        && (!this.mService.mAtmService.mMultiTaskingController.shouldNotSupportWallpaper())) {
      this.mFindResults.setUseTopWallpaperAsTarget(true);
    }
    this.mDisplayContent.forAllWindows(this.mFindWallpapers, true);
    this.mDisplayContent.forAllWindows(this.mFindWallpaperTargetFunction, true);
    FindWallpaperTargetResult findWallpaperTargetResult = this.mFindResults;
    if (findWallpaperTargetResult.mNeedsShowWhenLockedWallpaper) {
      findWallpaperTargetResult.setUseTopWallpaperAsTarget(true);
    }
    FindWallpaperTargetResult findWallpaperTargetResult2 = this.mFindResults;
    if (findWallpaperTargetResult2.wallpaperTarget == null
        && findWallpaperTargetResult2.useTopWallpaperAsTarget) {
      findWallpaperTargetResult2.setWallpaperTarget(
          findWallpaperTargetResult2.getTopWallpaper(this.mDisplayContent.isKeyguardLocked()));
    }
  }

  public List getAllTopWallpapers() {
    ArrayList arrayList = new ArrayList(2);
    if (this.mFindResults.hasTopShowWhenLockedWallpaper()) {
      arrayList.add(this.mFindResults.mTopWallpaper.mTopShowWhenLockedWallpaper);
    }
    if (this.mFindResults.hasTopHideWhenLockedWallpaper()) {
      arrayList.add(this.mFindResults.mTopWallpaper.mTopHideWhenLockedWallpaper);
    }
    return arrayList;
  }

  public final boolean isFullscreen(WindowManager.LayoutParams layoutParams) {
    return layoutParams.x == 0
        && layoutParams.y == 0
        && layoutParams.width == -1
        && layoutParams.height == -1;
  }

  public final void updateWallpaperWindowsTarget(
      FindWallpaperTargetResult findWallpaperTargetResult) {
    WindowState windowState;
    WindowState windowState2 = findWallpaperTargetResult.wallpaperTarget;
    if (this.mWallpaperTarget == windowState2
        || ((windowState = this.mPrevWallpaperTarget) != null && windowState == windowState2)) {
      WindowState windowState3 = this.mPrevWallpaperTarget;
      if (windowState3 == null || windowState3.isAnimatingLw()) {
        return;
      }
      if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_WALLPAPER, -1478175541, 0, (String) null, (Object[]) null);
      }
      this.mPrevWallpaperTarget = null;
      this.mWallpaperTarget = windowState2;
      return;
    }
    if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_WALLPAPER,
          114070759,
          0,
          (String) null,
          new Object[] {
            String.valueOf(windowState2),
            String.valueOf(this.mWallpaperTarget),
            String.valueOf(Debug.getCallers(5))
          });
    }
    this.mPrevWallpaperTarget = null;
    final WindowState windowState4 = this.mWallpaperTarget;
    this.mWallpaperTarget = windowState2;
    if (windowState4 == null && windowState2 != null) {
      updateWallpaperOffsetLocked(windowState2, false);
    }
    if (windowState2 == null || windowState4 == null) {
      return;
    }
    boolean isAnimatingLw = windowState4.isAnimatingLw();
    boolean isAnimatingLw2 = windowState2.isAnimatingLw();
    if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_WALLPAPER,
          -275077723,
          0,
          (String) null,
          new Object[] {String.valueOf(isAnimatingLw2), String.valueOf(isAnimatingLw)});
    }
    if (isAnimatingLw2
        && isAnimatingLw
        && this.mDisplayContent.getWindow(
                new Predicate() { // from class:
                                  // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda3
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$updateWallpaperWindowsTarget$3;
                    lambda$updateWallpaperWindowsTarget$3 =
                        WallpaperController.lambda$updateWallpaperWindowsTarget$3(
                            WindowState.this, (WindowState) obj);
                    return lambda$updateWallpaperWindowsTarget$3;
                  }
                })
            != null) {
      ActivityRecord activityRecord = windowState2.mActivityRecord;
      boolean z = (activityRecord == null || activityRecord.isVisibleRequested()) ? false : true;
      ActivityRecord activityRecord2 = windowState4.mActivityRecord;
      boolean z2 = (activityRecord2 == null || activityRecord2.isVisibleRequested()) ? false : true;
      if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_WALLPAPER,
            -360208282,
            204,
            (String) null,
            new Object[] {
              String.valueOf(windowState4),
              Boolean.valueOf(z2),
              String.valueOf(windowState2),
              Boolean.valueOf(z)
            });
      }
      this.mPrevWallpaperTarget = windowState4;
      if (z && !z2) {
        if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_WALLPAPER, 1178653181, 0, (String) null, (Object[]) null);
        }
        this.mWallpaperTarget = windowState4;
      } else if (z == z2
          && !this.mDisplayContent.mOpeningApps.contains(windowState2.mActivityRecord)
          && (this.mDisplayContent.mOpeningApps.contains(windowState4.mActivityRecord)
              || this.mDisplayContent.mClosingApps.contains(windowState4.mActivityRecord))) {
        this.mWallpaperTarget = windowState4;
      }
      findWallpaperTargetResult.setWallpaperTarget(windowState2);
    }
  }

  public final void updateWallpaperTokens(boolean z, boolean z2) {
    WindowState topWallpaper = this.mFindResults.getTopWallpaper(z2);
    WallpaperWindowToken asWallpaperToken =
        topWallpaper == null ? null : topWallpaper.mToken.asWallpaperToken();
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      WallpaperWindowToken wallpaperWindowToken =
          (WallpaperWindowToken) this.mWallpaperTokens.get(size);
      wallpaperWindowToken.updateWallpaperWindows(z && wallpaperWindowToken == asWallpaperToken);
    }
  }

  public void adjustWallpaperWindows() {
    this.mDisplayContent.mWallpaperMayChange = false;
    findWallpaperTarget();
    updateWallpaperWindowsTarget(this.mFindResults);
    boolean z = this.mWallpaperTarget != null;
    boolean z2 =
        (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_HIDING_WALLPAPER
                && CustomLetterboxConfiguration.shouldHideWallpaper(this.mDisplayContent))
            ? false
            : true;
    if (z && z2) {
      WindowState windowState = this.mWallpaperTarget;
      float f = windowState.mWallpaperX;
      if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mLastWallpaperX = f;
        this.mLastWallpaperXStep = windowState.mWallpaperXStep;
      }
      computeLastWallpaperZoomOut();
      WindowState windowState2 = this.mWallpaperTarget;
      float f2 = windowState2.mWallpaperY;
      if (f2 >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        this.mLastWallpaperY = f2;
        this.mLastWallpaperYStep = windowState2.mWallpaperYStep;
      }
      int i = windowState2.mWallpaperDisplayOffsetX;
      if (i != Integer.MIN_VALUE) {
        this.mLastWallpaperDisplayOffsetX = i;
      }
      int i2 = windowState2.mWallpaperDisplayOffsetY;
      if (i2 != Integer.MIN_VALUE) {
        this.mLastWallpaperDisplayOffsetY = i2;
      }
    }
    if (!this.mDisplayContent.isKeyguardGoingAway() || !this.mIsLockscreenLiveWallpaperEnabled) {
      updateWallpaperTokens(z && z2, this.mDisplayContent.isKeyguardLocked());
    }
    if ((!CoreRune.FW_CUSTOM_LETTERBOX
            || CustomLetterboxConfiguration.isAllowFreezeWallpaper(
                this.mFindResults.wallpaperTarget))
        && z) {
      boolean z3 = this.mLastFrozen;
      boolean z4 = this.mFindResults.isWallpaperTargetForLetterbox;
      if (z3 != z4) {
        this.mLastFrozen = z4;
        sendWindowWallpaperCommand(
            z4 ? "android.wallpaper.freeze" : "android.wallpaper.unfreeze", 0, 0, 0, null, false);
      }
    }
    if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX) {
      BlurWallpaperLetterbox.onAdjustWallpaperWindows(
          this.mDisplayContent, z && this.mFindResults.isWallpaperTargetForLetterbox);
    }
    if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_WALLPAPER,
          -304728471,
          0,
          (String) null,
          new Object[] {
            String.valueOf(this.mWallpaperTarget), String.valueOf(this.mPrevWallpaperTarget)
          });
    }
  }

  public boolean processWallpaperDrawPendingTimeout() {
    if (this.mWallpaperDrawState != 1) {
      return false;
    }
    this.mWallpaperDrawState = 2;
    if (this.mService.getRecentsAnimationController() != null) {
      this.mService.getRecentsAnimationController().startAnimation();
    }
    this.mService.mAtmService.mBackNavigationController.startAnimation();
    return true;
  }

  public boolean wallpaperTransitionReady() {
    boolean z;
    boolean z2 = true;
    int size = this.mWallpaperTokens.size() - 1;
    while (true) {
      if (size < 0) {
        z = true;
        break;
      }
      if (((WallpaperWindowToken) this.mWallpaperTokens.get(size)).hasVisibleNotDrawnWallpaper()) {
        int i = this.mWallpaperDrawState;
        z = i == 2;
        if (i == 0) {
          this.mWallpaperDrawState = 1;
          this.mService.f1749mH.removeMessages(39, this);
          WindowManagerService.HandlerC2998H handlerC2998H = this.mService.f1749mH;
          handlerC2998H.sendMessageDelayed(handlerC2998H.obtainMessage(39, this), 500L);
        }
        z2 = false;
      } else {
        size--;
      }
    }
    if (z2) {
      this.mWallpaperDrawState = 0;
      this.mService.f1749mH.removeMessages(39, this);
    }
    return z;
  }

  public void adjustWallpaperWindowsForAppTransitionIfNeeded(ArraySet arraySet) {
    boolean z = true;
    if ((this.mDisplayContent.pendingLayoutChanges & 4) == 0) {
      int size = arraySet.size() - 1;
      while (true) {
        if (size < 0) {
          z = false;
          break;
        } else if (((ActivityRecord) arraySet.valueAt(size)).windowsCanBeWallpaperTarget()) {
          break;
        } else {
          size--;
        }
      }
    }
    if (z) {
      adjustWallpaperWindows();
    }
  }

  public void addWallpaperToken(WallpaperWindowToken wallpaperWindowToken) {
    this.mWallpaperTokens.add(wallpaperWindowToken);
  }

  public void removeWallpaperToken(WallpaperWindowToken wallpaperWindowToken) {
    this.mWallpaperTokens.remove(wallpaperWindowToken);
  }

  public boolean canScreenshotWallpaper() {
    return canScreenshotWallpaper(getTopVisibleWallpaper());
  }

  public final boolean canScreenshotWallpaper(WindowState windowState) {
    if (!this.mService.mPolicy.isScreenOn()) {
      Slog.i(StartingSurfaceController.TAG, "Attempted to take screenshot while display was off.");
      return false;
    }
    if (windowState != null) {
      return true;
    }
    Slog.i(StartingSurfaceController.TAG, "No visible wallpaper to screenshot");
    return false;
  }

  public Bitmap screenshotWallpaperLocked() {
    return screenshotWallpaperLocked(null);
  }

  public Bitmap screenshotWallpaperLocked(Rect rect) {
    Rect bounds;
    ScreenCapture.ScreenshotHardwareBuffer captureLayers;
    WindowState topVisibleWallpaper = getTopVisibleWallpaper();
    if (!canScreenshotWallpaper(topVisibleWallpaper)) {
      return null;
    }
    if (rect != null) {
      bounds = new Rect(rect);
      bounds.offset(-topVisibleWallpaper.mXOffset, -topVisibleWallpaper.mYOffset);
    } else {
      bounds = topVisibleWallpaper.getBounds();
      bounds.offsetTo(0, 0);
    }
    WindowToken windowToken = topVisibleWallpaper.mToken;
    if (windowToken != null && windowToken.mIsPortraitWindowToken) {
      captureLayers = ScreenCapture.captureLayers(windowToken.getSurfaceControl(), bounds, 1.0f);
    } else {
      captureLayers =
          ScreenCapture.captureLayers(topVisibleWallpaper.getSurfaceControl(), bounds, 1.0f);
    }
    if (captureLayers == null) {
      Slog.w(StartingSurfaceController.TAG, "Failed to screenshot wallpaper");
      return null;
    }
    return Bitmap.wrapHardwareBuffer(
        captureLayers.getHardwareBuffer(), captureLayers.getColorSpace());
  }

  public SurfaceControl mirrorWallpaperSurface() {
    WindowState topVisibleWallpaper = getTopVisibleWallpaper();
    if (topVisibleWallpaper != null) {
      return SurfaceControl.mirrorSurface(topVisibleWallpaper.getSurfaceControl());
    }
    return null;
  }

  public WindowState getTopVisibleWallpaper() {
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      WallpaperWindowToken wallpaperWindowToken =
          (WallpaperWindowToken) this.mWallpaperTokens.get(size);
      for (int childCount = wallpaperWindowToken.getChildCount() - 1;
          childCount >= 0;
          childCount--) {
        WindowState windowState = (WindowState) wallpaperWindowToken.getChildAt(childCount);
        if (windowState.mWinAnimator.getShown()
            && windowState.mWinAnimator.mLastAlpha > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          return windowState;
        }
      }
    }
    return null;
  }

  public final void computeLastWallpaperZoomOut() {
    if (this.mShouldUpdateZoom) {
      this.mLastWallpaperZoomOut = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.mDisplayContent.forAllWindows(this.mComputeMaxZoomOutFunction, true);
      this.mShouldUpdateZoom = false;
    }
  }

  public final float zoomOutToScale(float f) {
    return MathUtils.lerp(1.0f, this.mMaxWallpaperScale, 1.0f - f);
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.print("displayId=");
    printWriter.println(this.mDisplayContent.getDisplayId());
    printWriter.print(str);
    printWriter.print("mWallpaperTarget=");
    printWriter.println(this.mWallpaperTarget);
    if (this.mPrevWallpaperTarget != null) {
      printWriter.print(str);
      printWriter.print("mPrevWallpaperTarget=");
      printWriter.println(this.mPrevWallpaperTarget);
    }
    printWriter.print(str);
    printWriter.print("mLastWallpaperX=");
    printWriter.print(this.mLastWallpaperX);
    printWriter.print(" mLastWallpaperY=");
    printWriter.println(this.mLastWallpaperY);
    if (this.mLastWallpaperDisplayOffsetX != Integer.MIN_VALUE
        || this.mLastWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
      printWriter.print(str);
      printWriter.print("mLastWallpaperDisplayOffsetX=");
      printWriter.print(this.mLastWallpaperDisplayOffsetX);
      printWriter.print(" mLastWallpaperDisplayOffsetY=");
      printWriter.println(this.mLastWallpaperDisplayOffsetY);
    }
    printWriter.print(" mTopHideWhenLockedWallpaper=");
    printWriter.println(this.mFindResults.mTopWallpaper.mTopHideWhenLockedWallpaper);
    printWriter.print(" mTopShowWhenLockedWallpaper=");
    printWriter.println(this.mFindResults.mTopWallpaper.mTopShowWhenLockedWallpaper);
    for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
      WallpaperWindowToken wallpaperWindowToken =
          (WallpaperWindowToken) this.mWallpaperTokens.get(size);
      printWriter.print(str);
      printWriter.println("token " + wallpaperWindowToken + XmlUtils.STRING_ARRAY_SEPARATOR);
      printWriter.print(str);
      printWriter.print("  canShowWhenLocked=");
      printWriter.println(wallpaperWindowToken.canShowWhenLocked());
      if (CoreRune.FW_FOLD_WALLPAPER_POLICY) {
        printWriter.print(str);
        printWriter.print("  isFoldedType=");
        printWriter.println(wallpaperWindowToken.isFoldedType());
      }
    }
  }

  public final class FindWallpaperTargetResult {
    public boolean isWallpaperTargetForLetterbox;
    public boolean mNeedsShowWhenLockedWallpaper;
    public TopWallpaper mTopWallpaper;
    public boolean useTopWallpaperAsTarget;
    public WindowState wallpaperTarget;

    public FindWallpaperTargetResult() {
      this.mTopWallpaper = new TopWallpaper();
      this.useTopWallpaperAsTarget = false;
      this.wallpaperTarget = null;
      this.isWallpaperTargetForLetterbox = false;
    }

    public final class TopWallpaper {
      public WindowState mTopHideWhenLockedWallpaper = null;
      public WindowState mTopShowWhenLockedWallpaper = null;

      public void reset() {
        this.mTopHideWhenLockedWallpaper = null;
        this.mTopShowWhenLockedWallpaper = null;
      }
    }

    public void setTopHideWhenLockedWallpaper(WindowState windowState) {
      this.mTopWallpaper.mTopHideWhenLockedWallpaper = windowState;
    }

    public void setTopShowWhenLockedWallpaper(WindowState windowState) {
      this.mTopWallpaper.mTopShowWhenLockedWallpaper = windowState;
    }

    public boolean hasTopHideWhenLockedWallpaper() {
      return this.mTopWallpaper.mTopHideWhenLockedWallpaper != null;
    }

    public boolean hasTopShowWhenLockedWallpaper() {
      return this.mTopWallpaper.mTopShowWhenLockedWallpaper != null;
    }

    public WindowState getTopWallpaper(boolean z) {
      if (!z && hasTopHideWhenLockedWallpaper()) {
        return this.mTopWallpaper.mTopHideWhenLockedWallpaper;
      }
      return this.mTopWallpaper.mTopShowWhenLockedWallpaper;
    }

    public void setWallpaperTarget(WindowState windowState) {
      this.wallpaperTarget = windowState;
    }

    public void setUseTopWallpaperAsTarget(boolean z) {
      this.useTopWallpaperAsTarget = z;
    }

    public void setIsWallpaperTargetForLetterbox(boolean z) {
      this.isWallpaperTargetForLetterbox = z;
    }

    public void reset() {
      this.mTopWallpaper.reset();
      this.mNeedsShowWhenLockedWallpaper = false;
      this.wallpaperTarget = null;
      this.useTopWallpaperAsTarget = false;
      this.isWallpaperTargetForLetterbox = false;
    }
  }

  public final boolean isShowWhenLockedFreeform(WindowState windowState) {
    return windowState.mActivityRecord != null
        && windowState.inFreeformWindowingMode()
        && this.mService.mAtmService.mKeyguardController.canShowWhileOccluded(
            windowState.mActivityRecord.containsDismissKeyguardWindow(),
            windowState.canShowWhenLocked());
  }

  public boolean useTopWallpaperAsTarget() {
    return this.mFindResults.useTopWallpaperAsTarget;
  }

  public final boolean canBeWallpaperTargetForTransientLaunch(WindowState windowState) {
    if (windowState.mActivityRecord != null
        && windowState.mAttrs.type == 1
        && windowState.isOnScreen()
        && !this.mDisplayContent.isKeyguardLocked()
        && windowState.mTransitionController.shouldWallpaperBeVisible()) {
      if (windowState.mTransitionController.isTransientLaunch(windowState.mActivityRecord)) {
        return true;
      }
      if (windowState.mTransitionController.isTransientHide(windowState.getTask())
          && !this.mDisplayContent.hasTopFixedRotationLaunchingApp()) {
        return true;
      }
    }
    return false;
  }

  public SurfaceControl startRemoteWallpaperAnimation(final IBinder iBinder, int i) {
    if (this.mRemoteWallpaperAnimAreaMap.containsKey(iBinder)) {
      return null;
    }
    List displayAreas = this.mDisplayContent.mDisplayAreaPolicy.getDisplayAreas(10002);
    DisplayArea displayArea = displayAreas.size() == 1 ? (DisplayArea) displayAreas.get(0) : null;
    if (displayArea == null) {
      return null;
    }
    this.mRemoteWallpaperAnimAreaMap.put(iBinder, displayArea);
    displayArea.startAnimation(
        this.mDisplayContent.getPendingTransaction(),
        new RemoteWallpaperAnimationAdapter(iBinder, displayArea, i),
        false,
        512,
        new SurfaceAnimator
            .OnAnimationFinishedCallback() { // from class:
                                             // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda4
          @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
          public final void onAnimationFinished(int i2, AnimationAdapter animationAdapter) {
            WallpaperController.this.lambda$startRemoteWallpaperAnimation$4(
                iBinder, i2, animationAdapter);
          }
        });
    this.mService.scheduleAnimationLocked();
    return displayArea.getAnimationLeash();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startRemoteWallpaperAnimation$4(
      IBinder iBinder, int i, AnimationAdapter animationAdapter) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mRemoteWallpaperAnimAreaMap.remove(iBinder);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean finishRemoteWallpaperAnimation(IBinder iBinder) {
    DisplayArea displayArea;
    if (!this.mRemoteWallpaperAnimAreaMap.containsKey(iBinder)
        || (displayArea = (DisplayArea) this.mRemoteWallpaperAnimAreaMap.remove(iBinder)) == null) {
      return false;
    }
    try {
      RemoteWallpaperAnimationAdapter remoteWallpaperAnimationAdapter =
          (RemoteWallpaperAnimationAdapter) displayArea.getAnimation();
      if (remoteWallpaperAnimationAdapter != null) {
        iBinder.unlinkToDeath(remoteWallpaperAnimationAdapter, 0);
      }
    } catch (NoSuchElementException unused) {
    }
    displayArea.cancelAnimation(this.mDisplayContent.getPendingTransaction());
    return true;
  }

  public class RemoteWallpaperAnimationAdapter implements AnimationAdapter, IBinder.DeathRecipient {
    public int mCallingPid;
    public IBinder mRemoteToken;
    public DisplayArea mRemoteWallpaperAnimArea;

    @Override // com.android.server.wm.AnimationAdapter
    public void dumpDebug(ProtoOutputStream protoOutputStream) {}

    @Override // com.android.server.wm.AnimationAdapter
    public long getDurationHint() {
      return 0L;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowWallpaper() {
      return false;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getStatusBarTransitionsStartTime() {
      return 0L;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void onAnimationCancelled(SurfaceControl surfaceControl) {}

    public RemoteWallpaperAnimationAdapter(IBinder iBinder, DisplayArea displayArea, int i) {
      this.mRemoteToken = iBinder;
      try {
        iBinder.linkToDeath(this, 0);
      } catch (RemoteException unused) {
      }
      this.mRemoteWallpaperAnimArea = displayArea;
      this.mCallingPid = i;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(
        SurfaceControl surfaceControl,
        SurfaceControl.Transaction transaction,
        int i,
        SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
      transaction.show(surfaceControl);
      transaction.setAlpha(surfaceControl, 1.0f);
      Slog.d(
          StartingSurfaceController.TAG,
          "startAnimation for remote wallpaper, leash=" + surfaceControl);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dump(PrintWriter printWriter, String str) {
      printWriter.print(str + "RemoteWallpaperAnimAdapter callingPid=");
      printWriter.print(this.mCallingPid);
      printWriter.println();
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      WindowManagerGlobalLock windowManagerGlobalLock =
          WallpaperController.this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mRemoteWallpaperAnimArea.cancelAnimation(
              WallpaperController.this.mDisplayContent.getPendingTransaction());
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }
}
