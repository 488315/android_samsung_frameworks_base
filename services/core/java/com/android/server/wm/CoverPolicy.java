package com.android.server.wm;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import com.android.server.UiThread;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class CoverPolicy {
  public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
  public ICoverManager mCoverManager;
  public DisplayContent mDisplayContent;
  public DisplayPolicyExt mDisplayPolicyExt;
  public boolean mLastCoverAppCovered;
  public DisplayContent mViewCoverDisplay;
  public WindowManagerPolicy mWindowPolicy;
  public WindowManagerService mWmService;
  public WindowState mCoverWindow = null;
  public WindowState mHideSViewCoverWindow = null;
  public HashSet mAppsToBeHiddenBySViewCover = new HashSet();
  public boolean mWallpaperTargetMayChange = false;
  public boolean mLastClearCoverState = false;
  public Handler mHandler = new CoverPolicyHandler(UiThread.getHandler().getLooper());

  public CoverPolicy(DisplayPolicyExt displayPolicyExt) {
    this.mDisplayPolicyExt = displayPolicyExt;
    WindowManagerService windowManagerService = displayPolicyExt.mService;
    this.mWindowPolicy = windowManagerService.mPolicy;
    this.mDisplayContent = displayPolicyExt.mDisplayPolicy.mDisplayContent;
    this.mWmService = windowManagerService;
    windowManagerService.mExt.mExtraDisplayPolicy = new CoverDisplayPolicy();
  }

  public class CoverPolicyHandler extends Handler {
    @Override // android.os.Handler
    public void handleMessage(Message message) {}

    public CoverPolicyHandler(Looper looper) {
      super(looper);
    }
  }

  public final synchronized ICoverManager getCoverManager() {
    if (this.mCoverManager == null) {
      ICoverManager asInterface =
          ICoverManager.Stub.asInterface(ServiceManager.getService("cover"));
      this.mCoverManager = asInterface;
      if (asInterface == null) {
        Slog.w("CoverPolicy", "warning: no COVER_MANAGER_SERVICE");
      }
    }
    return this.mCoverManager;
  }

  public boolean applyForceHidePolicyLw(WindowState windowState) {
    boolean canBeHiddenByKeyguard;
    WmCoverState wmCoverState = WmCoverState.getInstance();
    if (canBeHiddenByViewCoverLw(windowState)) {
      if (windowState.getAttrs().type == 2630) {
        applyForceHideCoverDisplayPolicyLw(windowState);
      } else {
        if (shouldBeHiddenByViewCover(windowState)) {
          if (windowState.hide(false, true)) {
            if (DEBUG) {
              Log.d("CoverPolicy", "applyForceHidePolicyLw, hide by cover, win=" + windowState);
            }
            if (wmCoverState.windowAttrsHasShowWallpaperOrShowWhenLocked(windowState.getAttrs())) {
              this.mWallpaperTargetMayChange = true;
            }
            windowState.setHiddenByViewCover(true);
          }
          return true;
        }
        if (shouldApplyAodPolicy()) {
          canBeHiddenByKeyguard = this.mDisplayPolicyExt.canBeForceHiddenByAodLw(windowState);
        } else {
          canBeHiddenByKeyguard = windowState.canBeHiddenByKeyguard();
        }
        if (!canBeHiddenByKeyguard && windowState.show(false, true)) {
          if (DEBUG) {
            Log.d("CoverPolicy", "applyForceHidePolicyLw, show by cover, win=" + windowState);
          }
          if (wmCoverState.windowAttrsHasShowWallpaperOrShowWhenLocked(windowState.getAttrs())) {
            this.mWallpaperTargetMayChange = true;
          }
          windowState.setHiddenByViewCover(false);
        }
      }
    }
    return false;
  }

  public boolean canBeHiddenByViewCoverLw(WindowState windowState) {
    int coverMode = windowState.getCoverMode();
    if (coverMode != 0) {
      if (coverMode != 1) {
        if (coverMode != 2) {}
      }
      return false;
    }
    int i = windowState.getAttrs().type;
    if (i == 2000 || i == 2005 || i == 2013 || i == 2040 || i == 2411 || i == 2633 || i == 2019
        || i == 2020) {
      return false;
    }
    if (i == 2630 || i == 2631) {
      return true;
    }
    return this.mWindowPolicy.getWindowLayerLw(windowState)
        <= this.mWindowPolicy.getWindowLayerFromTypeLw(2099);
  }

  public boolean shouldBeHiddenByViewCover(WindowState windowState) {
    int i = windowState.getAttrs().type;
    boolean z = i == 2099 || i == 2631;
    if (!WmCoverState.getInstance().isViewCoverClosed()) {
      return z;
    }
    if (z) {
      return false;
    }
    return (windowState == this.mHideSViewCoverWindow || ((windowState.mAttrs.flags & 524288) != 0))
        ? false
        : true;
  }

  public void beginPostLayoutPolicyLw() {
    this.mAppsToBeHiddenBySViewCover.clear();
    this.mHideSViewCoverWindow = null;
    if (!((CoverState) WmCoverState.getInstance()).attached) {
      this.mLastCoverAppCovered = false;
    }
    this.mWallpaperTargetMayChange = false;
  }

  public final void applyForceHideCoverDisplayPolicyLw(WindowState windowState) {
    if (!this.mWmService.mPolicy.isScreenOn(4) || !WmCoverState.getInstance().isViewCoverClosed()) {
      windowState.hide(false, true);
    } else {
      windowState.show(false, true);
    }
  }

  public void applyPostLayoutPolicyLw(
      WindowState windowState, WindowManager.LayoutParams layoutParams) {
    WmCoverState wmCoverState = WmCoverState.getInstance();
    if (wmCoverState.isCoverAppSupported()
        && wmCoverState.isCoverClosed()
        && isApplyWindow(windowState)
        && this.mHideSViewCoverWindow == null) {
      int coverMode = windowState.getCoverMode();
      if (coverMode != 0 && windowState.canShowWhenLocked()) {
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord != null) {
          IBinder iBinder = activityRecord.token;
          if (coverMode == 1) {
            this.mAppsToBeHiddenBySViewCover.remove(iBinder);
          } else if (coverMode == 2) {
            if (windowState.willBeHideSViewCoverOnce()) {
              this.mAppsToBeHiddenBySViewCover.remove(iBinder);
            }
          } else if (iBinder != null) {
            this.mAppsToBeHiddenBySViewCover.add(iBinder);
          }
        }
        if (this.mAppsToBeHiddenBySViewCover.isEmpty()) {
          this.mHideSViewCoverWindow = windowState;
          if (DEBUG) {
            Slog.d(
                "CoverPolicy",
                "applyPostLayoutPolicyLw, set mHideSViewCoverWindow to " + windowState);
          }
        }
      }
    }
  }

  public void finishPostLayoutPolicyLw() {
    if (this.mWallpaperTargetMayChange) {
      this.mDisplayContent.pendingLayoutChanges |= 4;
    }
    if (WmCoverState.getInstance().isCoverAppSupported()) {
      if (DEBUG) {
        Slog.d(
            "CoverPolicy",
            "finishPostLayoutPolicyLw : mHideSViewCoverWindow =" + this.mHideSViewCoverWindow);
      }
      ICoverManager coverManager = getCoverManager();
      if (coverManager != null) {
        int i = 0;
        try {
          if (this.mHideSViewCoverWindow != null) {
            if (!this.mLastCoverAppCovered) {
              i = coverManager.onCoverAppCovered(true);
              if ((i & 16) != 0) {
                this.mLastCoverAppCovered = true;
                updateOrientationListener();
              }
            }
          } else if (this.mLastCoverAppCovered) {
            int onCoverAppCovered = coverManager.onCoverAppCovered(false);
            if ((onCoverAppCovered & 32) != 0) {
              try {
                this.mLastCoverAppCovered = false;
                updateOrientationListener();
              } catch (RemoteException e) {
                i = onCoverAppCovered;
                e = e;
                e.printStackTrace();
                if ((i & 1) == 0) {}
                this.mDisplayContent.pendingLayoutChanges |= 1;
              }
            }
            i = onCoverAppCovered;
          }
        } catch (RemoteException e2) {
          e = e2;
        }
        if ((i & 1) == 0 || (i & 2) != 0) {
          this.mDisplayContent.pendingLayoutChanges |= 1;
        }
      }
    }
  }

  public boolean isApplyWindow(WindowState windowState) {
    if (windowState.mActivityRecord == null) {
      return windowState.mWinAnimator.getShown()
          && !(windowState.mAnimatingExit || windowState.mDestroying);
    }
    return windowState.mDisplayContent.getTopMostActivity() == windowState.mActivityRecord
        && !windowState.getTask().isAnimatingByRecents();
  }

  public void sendPowerKeyToCover() {
    if (WmCoverState.getInstance().isFlipTypeCoverClosed()) {
      try {
        ICoverManager coverManager = getCoverManager();
        if (coverManager != null) {
          coverManager.sendPowerKeyToCover();
        }
      } catch (RemoteException e) {
        Log.w("CoverPolicy", "CoverManager threw RemoteException", e);
      }
    }
  }

  public void updateCoverStateLocked(CoverState coverState) {
    WindowState windowState;
    WmCoverState wmCoverState = WmCoverState.getInstance();
    boolean isCoverClosed = wmCoverState.isCoverClosed();
    wmCoverState.updateCoverState(coverState);
    if (wmCoverState.isCoverAppSupported()
        && !wmCoverState.isCoverClosed()
        && (windowState = this.mHideSViewCoverWindow) != null) {
      windowState.disableHideSViewCoverOnce(true);
    }
    updateCoverStateLocked();
    this.mDisplayContent.getDisplayPolicy().hideImmersiveModeConfirmation();
    if (!isCoverClosed && wmCoverState.isCoverClosed()) {
      this.mWmService.mTaskSnapshotController.snapshotForSleeping(0);
    }
    if (this.mDisplayContent.updateOrientation()) {
      this.mDisplayContent.sendNewConfiguration();
    }
    this.mDisplayContent.setLayoutNeeded();
    this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    updateOrientationListener();
  }

  public boolean isLastCoverAppOpened() {
    return !this.mLastCoverAppCovered;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateOrientationListener$0() {
    this.mDisplayContent.getDisplayRotation().updateOrientationListener();
  }

  public final void updateOrientationListener() {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.wm.CoverPolicy$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            CoverPolicy.this.lambda$updateOrientationListener$0();
          }
        });
  }

  public boolean shouldApplyNoAnimation(WindowState windowState) {
    WmCoverState wmCoverState = WmCoverState.getInstance();
    if (windowState == this.mDisplayPolicyExt.mDisplayPolicy.getStatusBar()) {
      boolean z = (windowState.mAttrs.samsungFlags & 268435456) != 0;
      boolean z2 = wmCoverState.isViewCoverClosed() && this.mHideSViewCoverWindow == null;
      if (z || (z2 && wmCoverState.shouldHideStatusBarForCover())) {
        return true;
      }
    } else if (windowState == this.mDisplayPolicyExt.mDisplayPolicy.getNavigationBar()
        && wmCoverState != null
        && wmCoverState.isClearTypeCover()
        && (wmCoverState.isCoverClosed() || !this.mLastClearCoverState)) {
      this.mLastClearCoverState = ((CoverState) wmCoverState).switchState;
      return true;
    }
    return false;
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.println("CoverPolicy");
    String str2 = str + "  ";
    if (this.mCoverWindow != null) {
      printWriter.print(str2);
      printWriter.print("mCoverWindow=");
      printWriter.println(this.mCoverWindow);
    }
    if (this.mHideSViewCoverWindow != null) {
      printWriter.print(str2);
      printWriter.print("mHideSViewCoverWindow=");
      printWriter.println(this.mHideSViewCoverWindow);
    }
    printWriter.print(str2);
    printWriter.print("mLastCoverAppCovered=");
    printWriter.println(this.mLastCoverAppCovered);
  }

  public boolean shouldApplyAodPolicy() {
    return this.mDisplayContent.isDefaultDisplay;
  }

  public void addViewCoverDisplay(DisplayContent displayContent) {
    if (displayContent.getDisplayId() == 4) {
      this.mViewCoverDisplay = displayContent;
    }
  }

  public void removeViewCoverDisplay(int i) {
    if (i == 4) {
      this.mViewCoverDisplay = null;
    }
  }

  public void updateCoverStateLocked() {
    if (this.mViewCoverDisplay == null) {
      return;
    }
    this.mWmService.mExt.moveDisplayToTop(WmCoverState.getInstance().isCoverClosed() ? 4 : 0);
  }

  public class CoverDisplayPolicy implements ExtraDisplayPolicy {
    @Override // com.android.server.wm.ExtraDisplayPolicy
    public boolean hasCoverHome(int i) {
      return i == 4;
    }

    @Override // com.android.server.wm.ExtraDisplayPolicy
    public boolean isDisplayControlledByPolicy(int i) {
      return i == 4;
    }

    @Override // com.android.server.wm.ExtraDisplayPolicy
    public boolean shouldChooseDefaultTaskDisplayArea(int i) {
      return i == 4;
    }

    @Override // com.android.server.wm.ExtraDisplayPolicy
    public boolean shouldNotHandleForcedResizableTaskIfNeeded(int i, int i2) {
      return i == 4 && i2 == 2;
    }

    public CoverDisplayPolicy() {}

    public final boolean isCoverClosed() {
      return CoverPolicy.this.mViewCoverDisplay != null
          && WmCoverState.getInstance().isCoverClosed();
    }

    @Override // com.android.server.wm.ExtraDisplayPolicy
    public boolean shouldNotTopDisplay(int i) {
      if (isCoverClosed()) {
        if (i == 0) {
          return true;
        }
      } else if (i == 4) {
        return true;
      }
      return false;
    }

    @Override // com.android.server.wm.ExtraDisplayPolicy
    public int getOtherDisplayId(int i) {
      return isCoverClosed() ? i == 4 ? 0 : -1 : i == 0 ? 4 : -1;
    }

    @Override // com.android.server.wm.ExtraDisplayPolicy
    public boolean shouldSkipAppTransition(int i) {
      return i == 4 || isCoverClosed();
    }
  }
}
