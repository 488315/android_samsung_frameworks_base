package com.android.server.wm;

import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.util.EventLog;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.WindowContentFrameStats;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class WindowSurfaceController {
  public final WindowStateAnimator mAnimator;
  public final WindowManagerService mService;
  public SurfaceControl mSurfaceControl;
  public final Session mWindowSession;
  public final int mWindowType;
  public final String title;
  public boolean mSurfaceShown = false;
  public float mSurfaceX = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mSurfaceY = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mLastDsdx = 1.0f;
  public float mLastDtdx = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mLastDsdy = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mLastDtdy = 1.0f;
  public float mSurfaceAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public int mSurfaceLayer = 0;

  public WindowSurfaceController(
      String str, int i, int i2, WindowStateAnimator windowStateAnimator, int i3) {
    boolean z = false;
    this.mAnimator = windowStateAnimator;
    this.title = str;
    WindowManagerService windowManagerService = windowStateAnimator.mService;
    this.mService = windowManagerService;
    WindowState windowState = windowStateAnimator.mWin;
    this.mWindowType = i3;
    Session session = windowState.mSession;
    this.mWindowSession = session;
    if (session.mPid != Process.myPid()) {
      str = str + "$_" + windowStateAnimator.mWin.mSession.mPid;
    }
    Trace.traceBegin(32L, "new SurfaceControl");
    SurfaceControl.Builder callsite =
        windowState
            .makeSurface()
            .setParent(windowState.getSurfaceControl())
            .setName(str)
            .setFormat(i)
            .setFlags(i2)
            .setMetadata(2, i3)
            .setMetadata(1, session.mUid)
            .setMetadata(6, session.mPid)
            .setCallsite("WindowSurfaceController");
    if (windowManagerService.mUseBLAST && (windowState.getAttrs().privateFlags & 33554432) != 0) {
      z = true;
    }
    if (z) {
      callsite.setBLASTLayer();
    }
    long uptimeMillis = SystemClock.uptimeMillis();
    this.mSurfaceControl = callsite.build();
    Slog.d(
        StartingSurfaceController.TAG,
        "makeSurface duration=" + (SystemClock.uptimeMillis() - uptimeMillis) + " name=" + str);
    Trace.traceEnd(32L);
  }

  public void hide(SurfaceControl.Transaction transaction, String str) {
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          -1259022216,
          0,
          (String) null,
          new Object[] {String.valueOf(str), String.valueOf(this.title)});
    }
    if (this.mSurfaceShown) {
      hideSurface(transaction);
    }
  }

  public final void hideSurface(SurfaceControl.Transaction transaction) {
    if (this.mSurfaceControl == null) {
      return;
    }
    setShown(false);
    try {
      this.mAnimator.consumeSurfaceDebugTracker(transaction);
      transaction.hide(this.mSurfaceControl);
      WindowStateAnimator windowStateAnimator = this.mAnimator;
      if (windowStateAnimator.mIsWallpaper) {
        DisplayContent displayContent = windowStateAnimator.mWin.getDisplayContent();
        EventLog.writeEvent(
            33001,
            Integer.valueOf(displayContent.mDisplayId),
            0,
            String.valueOf(displayContent.mWallpaperController.getWallpaperTarget()));
      }
    } catch (RuntimeException unused) {
      Slog.w(StartingSurfaceController.TAG, "Exception hiding surface in " + this);
    }
  }

  public void destroy(SurfaceControl.Transaction transaction) {
    if (ProtoLogCache.WM_FORCE_SHOW_SURFACE_ALLOC_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_FORCE_SHOW_SURFACE_ALLOC,
          -1956050429,
          0,
          "Destroying surface %s called by %s",
          new Object[] {String.valueOf(this), String.valueOf(Debug.getCallers(8))});
    }
    try {
      try {
        if (this.mSurfaceControl != null) {
          WindowStateAnimator windowStateAnimator = this.mAnimator;
          if (windowStateAnimator.mIsWallpaper) {
            WindowState windowState = windowStateAnimator.mWin;
            if (!windowState.mWindowRemovalAllowed && !windowState.mRemoveOnExit) {
              Slog.e(
                  StartingSurfaceController.TAG,
                  "Unexpected removing wallpaper surface of "
                      + this.mAnimator.mWin
                      + " by "
                      + Debug.getCallers(8));
            }
          }
          transaction.remove(this.mSurfaceControl);
        }
      } catch (RuntimeException e) {
        Slog.w(StartingSurfaceController.TAG, "Error destroying surface in: " + this, e);
      }
    } finally {
      setShown(false);
      this.mSurfaceControl = null;
    }
  }

  public boolean prepareToShowInTransaction(SurfaceControl.Transaction transaction, float f) {
    SurfaceControl surfaceControl = this.mSurfaceControl;
    if (surfaceControl == null) {
      return false;
    }
    this.mSurfaceAlpha = f;
    transaction.setAlpha(surfaceControl, f);
    return true;
  }

  public void setOpaque(boolean z) {
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          558823034,
          3,
          (String) null,
          new Object[] {Boolean.valueOf(z), String.valueOf(this.title)});
    }
    if (this.mSurfaceControl == null) {
      return;
    }
    this.mService.openSurfaceTransaction();
    try {
      SurfaceControl.getGlobalTransaction().setOpaque(this.mSurfaceControl, z);
    } finally {
      this.mService.closeSurfaceTransaction("setOpaqueLocked");
    }
  }

  public void setSecure(boolean z) {
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          -1176488860,
          3,
          (String) null,
          new Object[] {Boolean.valueOf(z), String.valueOf(this.title)});
    }
    if (this.mSurfaceControl == null) {
      return;
    }
    this.mService.openSurfaceTransaction();
    try {
      SurfaceControl.getGlobalTransaction().setSecure(this.mSurfaceControl, z);
      DisplayContent displayContent = this.mAnimator.mWin.mDisplayContent;
      if (displayContent != null) {
        displayContent.refreshImeSecureFlag(SurfaceControl.getGlobalTransaction());
      }
    } finally {
      this.mService.closeSurfaceTransaction("setSecure");
    }
  }

  public void setColorSpaceAgnostic(SurfaceControl.Transaction transaction, boolean z) {
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          585096182,
          3,
          (String) null,
          new Object[] {Boolean.valueOf(z), String.valueOf(this.title)});
    }
    SurfaceControl surfaceControl = this.mSurfaceControl;
    if (surfaceControl == null) {
      return;
    }
    transaction.setColorSpaceAgnostic(surfaceControl, z);
  }

  public void showRobustly(SurfaceControl.Transaction transaction) {
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          -1089874824,
          0,
          (String) null,
          new Object[] {String.valueOf(this.title)});
    }
    if (this.mSurfaceShown) {
      return;
    }
    this.mAnimator.consumeSurfaceDebugTracker(transaction);
    setShown(true);
    transaction.show(this.mSurfaceControl);
    WindowStateAnimator windowStateAnimator = this.mAnimator;
    if (windowStateAnimator.mIsWallpaper) {
      DisplayContent displayContent = windowStateAnimator.mWin.getDisplayContent();
      EventLog.writeEvent(
          33001,
          Integer.valueOf(displayContent.mDisplayId),
          1,
          String.valueOf(displayContent.mWallpaperController.getWallpaperTarget()));
    }
  }

  public boolean clearWindowContentFrameStats() {
    SurfaceControl surfaceControl = this.mSurfaceControl;
    if (surfaceControl == null) {
      return false;
    }
    return surfaceControl.clearContentFrameStats();
  }

  public boolean getWindowContentFrameStats(WindowContentFrameStats windowContentFrameStats) {
    SurfaceControl surfaceControl = this.mSurfaceControl;
    if (surfaceControl == null) {
      return false;
    }
    return surfaceControl.getContentFrameStats(windowContentFrameStats);
  }

  public boolean hasSurface() {
    return this.mSurfaceControl != null;
  }

  public void getSurfaceControl(SurfaceControl surfaceControl) {
    surfaceControl.copyFrom(this.mSurfaceControl, "WindowSurfaceController.getSurfaceControl");
  }

  public boolean getShown() {
    return this.mSurfaceShown;
  }

  public void setShown(boolean z) {
    boolean z2 = this.mSurfaceShown;
    this.mSurfaceShown = z;
    this.mService.updateNonSystemOverlayWindowsVisibilityIfNeeded(this.mAnimator.mWin, z);
    this.mAnimator.mWin.onSurfaceShownChanged(z);
    Session session = this.mWindowSession;
    if (session != null) {
      session.onWindowSurfaceVisibilityChanged(this, this.mSurfaceShown, this.mWindowType, z2);
    }
  }

  public void startSurfaceAnimation(String str) {
    if (this.mSurfaceControl == null) {
      return;
    }
    this.mService.openSurfaceTransaction();
    try {
      SurfaceControl.getGlobalTransaction().startSurfaceAnimation(this.mSurfaceControl, str);
    } finally {
      this.mService.closeSurfaceTransaction("startSurfaceAnimation");
    }
  }

  public boolean startSurfaceAnimationInTransaction(
      SurfaceControl.Transaction transaction, String str) {
    SurfaceControl surfaceControl = this.mSurfaceControl;
    if (surfaceControl == null) {
      return false;
    }
    transaction.startSurfaceAnimation(surfaceControl, str);
    return true;
  }

  public void setInternalPresentationOnly(boolean z) {
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          249537880,
          3,
          (String) null,
          new Object[] {Boolean.valueOf(z), String.valueOf(this.title)});
    }
    if (this.mSurfaceControl == null) {
      return;
    }
    this.mService.openSurfaceTransaction();
    try {
      SurfaceControl.getGlobalTransaction().setInternalPresentationOnly(this.mSurfaceControl, z);
    } finally {
      this.mService.closeSurfaceTransaction("setInternalPresentationOnly");
    }
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1133871366145L, this.mSurfaceShown);
    protoOutputStream.write(1120986464258L, this.mSurfaceLayer);
    protoOutputStream.end(start);
  }

  public void dump(PrintWriter printWriter, String str, boolean z) {
    if (z) {
      printWriter.print(str);
      printWriter.print("mSurface=");
      printWriter.println(this.mSurfaceControl);
    }
    printWriter.print(str);
    printWriter.print("Surface: shown=");
    printWriter.print(this.mSurfaceShown);
    printWriter.print(" layer=");
    printWriter.print(this.mSurfaceLayer);
    printWriter.print(" alpha=");
    printWriter.print(this.mSurfaceAlpha);
    printWriter.print(" rect=(");
    printWriter.print(this.mSurfaceX);
    printWriter.print(",");
    printWriter.print(this.mSurfaceY);
    printWriter.print(") ");
    printWriter.print(" transform=(");
    printWriter.print(this.mLastDsdx);
    printWriter.print(", ");
    printWriter.print(this.mLastDtdx);
    printWriter.print(", ");
    printWriter.print(this.mLastDsdy);
    printWriter.print(", ");
    printWriter.print(this.mLastDtdy);
    printWriter.println(")");
  }

  public String toString() {
    return this.mSurfaceControl.toString();
  }
}
