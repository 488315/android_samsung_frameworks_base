package com.android.server.wm;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.knox.localservice.RemoteInjectionInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class WindowStateAnimator {
  public boolean mAnimationIsEntrance;
  public final WindowAnimator mAnimator;
  public int mAttrType;
  public final Context mContext;
  public int mDrawState;
  public boolean mEnterAnimationPending;
  public boolean mEnteringAnimation;
  public final boolean mIsWallpaper;
  public boolean mLastHidden;
  public final WindowManagerPolicy mPolicy;
  public RemoteInjectionInternal mRemoteInjection;
  public final WindowManagerService mService;
  public final Session mSession;
  public WindowSurfaceController mSurfaceController;
  public final WallpaperController mWallpaperControllerLocked;
  public final WindowState mWin;
  public float mShownAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mLastAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mPopOverAlpha = -1.0f;
  public final Rect mSystemDecorRect = new Rect();
  public String mSurfaceDebugTracker = null;

  public String drawStateToString() {
    int i = this.mDrawState;
    return i != 0
        ? i != 1
            ? i != 2
                ? i != 3 ? i != 4 ? Integer.toString(i) : "HAS_DRAWN" : "READY_TO_SHOW"
                : "COMMIT_DRAW_PENDING"
            : "DRAW_PENDING"
        : "NO_SURFACE";
  }

  public WindowStateAnimator(WindowState windowState) {
    WindowManagerService windowManagerService = windowState.mWmService;
    this.mService = windowManagerService;
    this.mAnimator = windowManagerService.mAnimator;
    this.mPolicy = windowManagerService.mPolicy;
    this.mContext = windowManagerService.mContext;
    this.mWin = windowState;
    this.mSession = windowState.mSession;
    this.mAttrType = windowState.mAttrs.type;
    this.mIsWallpaper = windowState.mIsWallpaper;
    this.mWallpaperControllerLocked = windowState.getDisplayContent().mWallpaperController;
  }

  public void onAnimationFinished() {
    if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
      String valueOf = String.valueOf(this);
      WindowState windowState = this.mWin;
      boolean z = windowState.mAnimatingExit;
      ActivityRecord activityRecord = windowState.mActivityRecord;
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ANIM,
          1810209625,
          60,
          (String) null,
          new Object[] {
            valueOf,
            Boolean.valueOf(z),
            Boolean.valueOf(activityRecord != null && activityRecord.reportedVisible)
          });
    }
    this.mWin.checkPolicyVisibilityChange();
    DisplayContent displayContent = this.mWin.getDisplayContent();
    int i = this.mAttrType;
    if ((i == 2000 || i == 2040) && this.mWin.isVisibleByPolicy()) {
      displayContent.setLayoutNeeded();
    }
    this.mWin.onExitAnimationDone();
    displayContent.pendingLayoutChanges |= 8;
    if (displayContent.mWallpaperController.isWallpaperTarget(this.mWin)) {
      displayContent.pendingLayoutChanges |= 4;
    }
    ActivityRecord activityRecord2 = this.mWin.mActivityRecord;
    if (activityRecord2 != null) {
      activityRecord2.updateReportedVisibilityLocked();
    }
  }

  public void hide(SurfaceControl.Transaction transaction, String str) {
    if (this.mLastHidden) {
      return;
    }
    this.mLastHidden = true;
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController != null) {
      windowSurfaceController.hide(transaction, str);
    }
  }

  public boolean finishDrawingLocked(SurfaceControl.Transaction transaction) {
    WindowState windowState = this.mWin;
    boolean z = false;
    boolean z2 = windowState.mAttrs.type == 3;
    if (z2 && ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
          -344488673,
          0,
          "Finishing drawing window %s: mDrawState=%s",
          new Object[] {String.valueOf(windowState), String.valueOf(drawStateToString())});
    }
    if (this.mDrawState == 1) {
      if (ProtoLogCache.WM_DEBUG_DRAW_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_DRAW,
            -993378225,
            0,
            (String) null,
            new Object[] {String.valueOf(this.mWin), String.valueOf(this.mSurfaceController)});
      }
      if (z2 && ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_STARTING_WINDOW,
            829434921,
            0,
            "Draw state now committed in %s",
            new Object[] {String.valueOf(this.mWin)});
      }
      this.mDrawState = 2;
      z = true;
    }
    if (transaction == null) {
      return z;
    }
    this.mWin.getSyncTransaction().merge(transaction);
    return true;
  }

  public boolean commitFinishDrawingLocked() {
    int i = this.mDrawState;
    if (i != 2 && i != 3) {
      return false;
    }
    if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_ANIM,
          -203358733,
          0,
          (String) null,
          new Object[] {String.valueOf(this.mSurfaceController)});
    }
    this.mDrawState = 3;
    ActivityRecord activityRecord = this.mWin.mActivityRecord;
    if (activityRecord == null || activityRecord.canShowWindows() || this.mWin.mAttrs.type == 3) {
      return this.mWin.performShowLocked();
    }
    return false;
  }

  public void resetDrawState() {
    this.mDrawState = 1;
    ActivityRecord activityRecord = this.mWin.mActivityRecord;
    if (activityRecord == null || activityRecord.isAnimating(1)) {
      return;
    }
    this.mWin.mActivityRecord.clearAllDrawn();
  }

  public WindowSurfaceController createSurfaceLocked() {
    WindowSurfaceController windowSurfaceController;
    AsyncRotationController asyncRotationController;
    WindowState windowState = this.mWin;
    WindowSurfaceController windowSurfaceController2 = this.mSurfaceController;
    if (windowSurfaceController2 != null) {
      return windowSurfaceController2;
    }
    windowState.setHasSurface(false);
    if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_ANIM,
          1335791109,
          0,
          (String) null,
          new Object[] {String.valueOf(this)});
    }
    resetDrawState();
    this.mService.makeWindowFreezingScreenIfNeededLocked(windowState);
    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
    int i = windowState.isSecureLocked() ? 132 : 4;
    try {
      RemoteInjectionInternal remoteInjection = getRemoteInjection();
      if (remoteInjection != null && isWindowNotAnExceptionForDisableRemote(this.mWin)) {
        if (remoteInjection.isRemoteControlDisabled(UserHandle.getUserId(this.mWin.mOwnerUid))) {
          i |= 15728640;
        }
      }
    } catch (Exception unused) {
      Slog.e(
          StartingSurfaceController.TAG,
          "Exception occurred while checking for isRemoteControlDisabled");
    }
    WindowManager.LayoutParams layoutParams2 = this.mWin.mAttrs;
    if ((layoutParams2.privateFlags & 1048576) != 0) {
      i |= 64;
    }
    if ((layoutParams2.samsungFlags & Integer.MIN_VALUE) != 0) {
      i |= Integer.MIN_VALUE;
    }
    if (layoutParams2.type == 2601) {
      i |= 2097152;
    }
    int i2 = i;
    try {
      try {
        try {
          WindowSurfaceController windowSurfaceController3 =
              new WindowSurfaceController(
                  layoutParams.getTitle().toString(),
                  (layoutParams.flags & 16777216) != 0 ? -3 : layoutParams.format,
                  i2,
                  this,
                  layoutParams.type);
          this.mSurfaceController = windowSurfaceController3;
          windowSurfaceController3.setColorSpaceAgnostic(
              windowState.getPendingTransaction(), (layoutParams.privateFlags & 16777216) != 0);
          windowState.setHasSurface(true);
          this.mWin.resetEffects();
          windowState.mInputWindowHandle.forceChange();
          if (windowState.getDisplayContent().hasTopFixedRotationLaunchingApp()
              && (asyncRotationController =
                      windowState.getDisplayContent().getAsyncRotationController())
                  != null) {
            asyncRotationController.hideImmediatelyIfNeeded(windowState, "createSurfaceLocked");
          }
          if (ProtoLogCache.WM_SHOW_SURFACE_ALLOC_enabled) {
            try {
              ProtoLogImpl.i(
                  ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                  745391677,
                  336,
                  (String) null,
                  new Object[] {
                    String.valueOf(this.mSurfaceController),
                    String.valueOf(this.mSession.mSurfaceSession),
                    Long.valueOf(this.mSession.mPid),
                    Long.valueOf(layoutParams.format),
                    Long.valueOf(i2),
                    String.valueOf(this)
                  });
            } catch (Surface.OutOfResourcesException unused2) {
              windowSurfaceController = null;
              Slog.w(StartingSurfaceController.TAG, "OutOfResourcesException creating surface");
              this.mService.mRoot.reclaimSomeSurfaceMemory(this, "create", true);
              this.mDrawState = 0;
              return windowSurfaceController;
            }
          }
          this.mLastHidden = true;
          return this.mSurfaceController;
        } catch (Surface.OutOfResourcesException unused3) {
          windowSurfaceController = null;
        }
      } catch (Exception e) {
        Slog.e(StartingSurfaceController.TAG, "Exception creating surface (parent dead?)", e);
        this.mDrawState = 0;
        return null;
      }
    } catch (Surface.OutOfResourcesException unused4) {
      windowSurfaceController = null;
    }
  }

  public final RemoteInjectionInternal getRemoteInjection() {
    if (this.mRemoteInjection == null) {
      this.mRemoteInjection =
          (RemoteInjectionInternal) LocalServices.getService(RemoteInjectionInternal.class);
    }
    return this.mRemoteInjection;
  }

  public final boolean isWindowNotAnExceptionForDisableRemote(WindowState windowState) {
    if (windowState == null) {
      return true;
    }
    int i = windowState.mAttrs.type;
    if (i == 2000 || i == 2014 || i == 2017 || i == 2024 || i == 2095 || i == 2226 || i == 2621
        || i == 2019 || i == 2020) {
      return false;
    }
    switch (i) {
      case 2401:
      case 2402:
      case 2403:
        return false;
      default:
        return true;
    }
  }

  public boolean hasSurface() {
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    return windowSurfaceController != null && windowSurfaceController.hasSurface();
  }

  public void destroySurfaceLocked(SurfaceControl.Transaction transaction) {
    if (this.mSurfaceController == null) {
      return;
    }
    WindowState windowState = this.mWin;
    windowState.mHidden = true;
    try {
      if (ProtoLogCache.WM_SHOW_SURFACE_ALLOC_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
            -1391944764,
            0,
            (String) null,
            new Object[] {
              String.valueOf(windowState), String.valueOf(new RuntimeException().fillInStackTrace())
            });
      }
      destroySurface(transaction);
      this.mWallpaperControllerLocked.hideWallpapers(this.mWin);
    } catch (RuntimeException e) {
      Slog.w(
          StartingSurfaceController.TAG,
          "Exception thrown when destroying Window "
              + this
              + " surface "
              + this.mSurfaceController
              + " session "
              + this.mSession
              + ": "
              + e.toString());
    }
    this.mWin.setHasSurface(false);
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController != null) {
      windowSurfaceController.setShown(false);
    }
    this.mSurfaceController = null;
    this.mDrawState = 0;
  }

  public void computeShownFrameLocked() {
    if ((this.mIsWallpaper && this.mService.mRoot.mWallpaperActionPending)
        || this.mWin.isDragResizeChanged()) {
      return;
    }
    this.mShownAlpha = this.mAlpha;
    if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
      applyMultiWindowAlpha();
    }
    float f = this.mPopOverAlpha;
    if (f != -1.0f) {
      this.mShownAlpha = f;
    }
  }

  public void prepareSurfaceLocked(SurfaceControl.Transaction transaction) {
    WindowState windowState = this.mWin;
    if (!hasSurface()) {
      if (windowState.getOrientationChanging() && windowState.isGoneForLayout()) {
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ORIENTATION,
              1756082882,
              0,
              (String) null,
              new Object[] {String.valueOf(windowState)});
        }
        windowState.setOrientationChanging(false);
        return;
      }
      return;
    }
    computeShownFrameLocked();
    this.mWin.applyBlurEffectInTransaction(transaction);
    if (!windowState.isOnScreen()) {
      hide(transaction, "prepareSurfaceLocked");
      this.mWallpaperControllerLocked.hideWallpapers(windowState);
      if (windowState.getOrientationChanging() && windowState.isGoneForLayout()) {
        windowState.setOrientationChanging(false);
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ORIENTATION,
              1756082882,
              0,
              (String) null,
              new Object[] {String.valueOf(windowState)});
        }
      }
    } else {
      float f = this.mLastAlpha;
      float f2 = this.mShownAlpha;
      if (f != f2 || this.mLastHidden) {
        this.mLastAlpha = f2;
        if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
          ProtoLogImpl.i(
              ProtoLogGroup.WM_SHOW_TRANSACTIONS,
              -1906387645,
              168,
              (String) null,
              new Object[] {
                String.valueOf(this.mSurfaceController),
                Double.valueOf(this.mShownAlpha),
                Double.valueOf(windowState.mHScale),
                Double.valueOf(windowState.mVScale),
                String.valueOf(windowState)
              });
        }
        if (this.mSurfaceController.prepareToShowInTransaction(transaction, this.mShownAlpha)
            && this.mDrawState == 4
            && this.mLastHidden) {
          this.mSurfaceController.showRobustly(transaction);
          this.mLastHidden = false;
          DisplayContent displayContent = windowState.getDisplayContent();
          if (!displayContent.getLastHasContent()) {
            displayContent.pendingLayoutChanges |= 8;
          }
        }
      }
    }
    if (windowState.getOrientationChanging()) {
      if (!windowState.isDrawn()) {
        if (windowState.mDisplayContent.shouldSyncRotationChange(windowState)) {
          windowState.mWmService.mRoot.mOrientationChangeComplete = false;
          this.mAnimator.mLastWindowFreezeSource = windowState;
        }
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ORIENTATION,
              -1130891072,
              0,
              (String) null,
              new Object[] {String.valueOf(windowState)});
          return;
        }
        return;
      }
      windowState.setOrientationChanging(false);
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION,
            916191774,
            0,
            (String) null,
            new Object[] {String.valueOf(windowState)});
      }
    }
  }

  public void setOpaqueLocked(boolean z) {
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController == null) {
      return;
    }
    windowSurfaceController.setOpaque(z);
  }

  public void setSecureLocked(boolean z) {
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController == null) {
      return;
    }
    windowSurfaceController.setSecure(z);
  }

  public void setColorSpaceAgnosticLocked(boolean z) {
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController == null) {
      return;
    }
    windowSurfaceController.setColorSpaceAgnostic(this.mWin.getPendingTransaction(), z);
  }

  public void applyEnterAnimationLocked() {
    int i;
    if (this.mEnterAnimationPending) {
      this.mEnterAnimationPending = false;
      i = 1;
    } else {
      i = 3;
    }
    if (this.mAttrType != 1 && !this.mIsWallpaper) {
      applyAnimationLocked(i, true);
    }
    if (this.mService.mAccessibilityController.hasCallbacks()) {
      this.mService.mAccessibilityController.onWindowTransition(this.mWin, i);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x009e  */
  /* JADX WARN: Removed duplicated region for block: B:27:0x00df  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean applyAnimationLocked(int i, boolean z) {
    Animation loadAnimationAttr;
    int i2 = 1;
    if (this.mWin.isAnimating() && this.mAnimationIsEntrance == z) {
      return true;
    }
    WindowState windowState = this.mWin;
    if (windowState.mAttrs.type == 2011) {
      windowState.getDisplayContent().adjustForImeIfNeeded();
      if (z) {
        this.mWin.setDisplayLayoutNeeded();
        this.mService.mWindowPlacerLocked.requestTraversal();
      }
    }
    WindowState windowState2 = this.mWin;
    if (windowState2.mControllableInsetProvider != null) {
      return false;
    }
    if (windowState2.mToken.okToAnimate()) {
      int selectAnimation =
          this.mWin.getDisplayContent().getDisplayPolicy().selectAnimation(this.mWin, i);
      int i3 = -1;
      if (selectAnimation != 0) {
        if (selectAnimation != -1) {
          Trace.traceBegin(32L, "WSA#loadAnimation");
          loadAnimationAttr = AnimationUtils.loadAnimation(this.mContext, selectAnimation);
          Trace.traceEnd(32L);
          if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM)
              && ProtoLogCache.WM_DEBUG_ANIM_enabled) {
            long j = selectAnimation;
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_ANIM,
                -1318478129,
                13588,
                (String) null,
                new Object[] {
                  String.valueOf(this),
                  Long.valueOf(j),
                  Long.valueOf(i3),
                  String.valueOf(loadAnimationAttr),
                  Long.valueOf(i),
                  Long.valueOf(this.mAttrType),
                  Boolean.valueOf(z),
                  String.valueOf(Debug.getCallers(20))
                });
          }
          if (loadAnimationAttr != null) {
            Trace.traceBegin(32L, "WSA#startAnimation");
            if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && this.mWin.inFreeformWindowingMode()) {
              Animation loadWindowAnimationIfNeeded =
                  FreeformController.loadWindowAnimationIfNeeded(loadAnimationAttr, this.mWin, i);
              if (loadWindowAnimationIfNeeded != null) {
                loadAnimationAttr = loadWindowAnimationIfNeeded;
              }
            } else if (CoreRune.FW_LOCK_ONLY_LIVE_WALLPAPER && this.mWin.mAttrs.type == 2633) {
              loadAnimationAttr.setDuration(
                  this.mContext.getResources().getInteger(R.integer.config_shortAnimTime));
            }
            this.mWin.startAnimation(loadAnimationAttr);
            Trace.traceEnd(32L);
            this.mAnimationIsEntrance = z;
          }
        }
        loadAnimationAttr = null;
        if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM)) {
          long j2 = selectAnimation;
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ANIM,
              -1318478129,
              13588,
              (String) null,
              new Object[] {
                String.valueOf(this),
                Long.valueOf(j2),
                Long.valueOf(i3),
                String.valueOf(loadAnimationAttr),
                Long.valueOf(i),
                Long.valueOf(this.mAttrType),
                Boolean.valueOf(z),
                String.valueOf(Debug.getCallers(20))
              });
        }
        if (loadAnimationAttr != null) {}
      } else {
        if (i == 1) {
          i2 = 0;
        } else if (i != 2) {
          i2 = 3;
          if (i == 3) {
            i2 = 2;
          } else if (i != 4) {
            i2 = -1;
          }
        }
        if (i2 >= 0) {
          i3 = i2;
          loadAnimationAttr =
              this.mWin
                  .getDisplayContent()
                  .mAppTransition
                  .loadAnimationAttr(this.mWin.mAttrs, i2, 0);
          if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM)) {}
          if (loadAnimationAttr != null) {}
        } else {
          i3 = i2;
          loadAnimationAttr = null;
          if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM)) {}
          if (loadAnimationAttr != null) {}
        }
      }
    } else {
      this.mWin.cancelAnimation();
    }
    return this.mWin.isAnimating(0, 16);
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController != null) {
      windowSurfaceController.dumpDebug(protoOutputStream, 1146756268034L);
    }
    protoOutputStream.write(1159641169923L, this.mDrawState);
    this.mSystemDecorRect.dumpDebug(protoOutputStream, 1146756268036L);
    protoOutputStream.end(start);
  }

  public void dump(PrintWriter printWriter, String str, boolean z) {
    if (this.mAnimationIsEntrance) {
      printWriter.print(str);
      printWriter.print(" mAnimationIsEntrance=");
      printWriter.print(this.mAnimationIsEntrance);
    }
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController != null) {
      windowSurfaceController.dump(printWriter, str, z);
    }
    if (z) {
      printWriter.print(str);
      printWriter.print("mDrawState=");
      printWriter.print(drawStateToString());
      printWriter.print(str);
      printWriter.print(" mLastHidden=");
      printWriter.println(this.mLastHidden);
      printWriter.print(str);
      printWriter.print("mEnterAnimationPending=" + this.mEnterAnimationPending);
      printWriter.print(str);
      printWriter.print("mSystemDecorRect=");
      this.mSystemDecorRect.printShortString(printWriter);
      printWriter.println();
    }
    if (this.mShownAlpha != 1.0f || this.mAlpha != 1.0f || this.mLastAlpha != 1.0f) {
      printWriter.print(str);
      printWriter.print("mShownAlpha=");
      printWriter.print(this.mShownAlpha);
      printWriter.print(" mAlpha=");
      printWriter.print(this.mAlpha);
      printWriter.print(" mLastAlpha=");
      printWriter.println(this.mLastAlpha);
    }
    if (this.mWin.mGlobalScale != 1.0f) {
      printWriter.print(str);
      printWriter.print("mGlobalScale=");
      printWriter.print(this.mWin.mGlobalScale);
    }
  }

  public String toString() {
    StringBuffer stringBuffer = new StringBuffer("WindowStateAnimator{");
    stringBuffer.append(Integer.toHexString(System.identityHashCode(this)));
    stringBuffer.append(' ');
    stringBuffer.append(this.mWin.mAttrs.getTitle());
    stringBuffer.append('}');
    return stringBuffer.toString();
  }

  public boolean getShown() {
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController != null) {
      return windowSurfaceController.getShown();
    }
    return false;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void destroySurface(SurfaceControl.Transaction transaction) {
    try {
      try {
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController != null) {
          windowSurfaceController.destroy(transaction);
        }
      } catch (RuntimeException e) {
        Slog.w(
            StartingSurfaceController.TAG,
            "Exception thrown when destroying surface "
                + this
                + " surface "
                + this.mSurfaceController
                + " session "
                + this.mSession
                + ": "
                + e);
      }
    } finally {
      this.mWin.setHasSurface(false);
      this.mSurfaceController = null;
      this.mDrawState = 0;
    }
  }

  public SurfaceControl getSurfaceControl() {
    if (hasSurface()) {
      return this.mSurfaceController.mSurfaceControl;
    }
    return null;
  }

  public void setInternalPresentationOnly(boolean z) {
    WindowSurfaceController windowSurfaceController = this.mSurfaceController;
    if (windowSurfaceController == null) {
      return;
    }
    windowSurfaceController.setInternalPresentationOnly(z);
  }

  public final void applyMultiWindowAlpha() {
    if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
      Task task = this.mWin.getTask();
      WindowState windowState = this.mWin;
      if (windowState.mIsImWindow) {
        WindowState windowState2 =
            (windowState.getDisplayContent() == null
                    || this.mWin.getDisplayContent().getImeInputTarget() == null)
                ? null
                : this.mWin.getDisplayContent().getImeInputTarget().getWindowState();
        if (windowState2 != null) {
          task = windowState2.getTask();
        }
      }
      if (task == null || !task.inFreeformWindowingMode()) {
        return;
      }
      float f = task.mFreeformAlpha;
      if (f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f >= 1.0f) {
        return;
      }
      this.mShownAlpha *= f;
    }
  }

  public boolean consumeSurfaceDebugTracker(SurfaceControl.Transaction transaction) {
    if (transaction == null || TextUtils.isEmpty(this.mSurfaceDebugTracker)) {
      return false;
    }
    transaction.addDebugName(this.mSurfaceDebugTracker);
    this.mSurfaceDebugTracker = null;
    return true;
  }
}
