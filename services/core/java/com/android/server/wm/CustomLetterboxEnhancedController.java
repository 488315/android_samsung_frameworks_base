package com.android.server.wm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.util.Slog;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.server.UiThread;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;
import com.samsung.android.view.ScreenshotResult;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomLetterboxEnhancedController {
  public final DisplayContent mDisplay;
  public boolean mDisplayDeviceTypeChanged;
  public boolean mEnhancedEnabled;
  public int mEnhancedReason;
  public final Handler mHandler;
  public long mSeq;
  public final SurfaceControl.Transaction mTmpTransaction;
  public boolean mWaitingForEnhancedEnabled;
  public final WindowManagerService mWmService;
  public boolean mShouldShowLetterbox = true;
  public Runnable mOnApplyLetterboxEnhanced =
      new Runnable() { // from class:
                       // com.android.server.wm.CustomLetterboxEnhancedController$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
          CustomLetterboxEnhancedController.this.onApplyLetterboxEnhanced();
        }
      };
  public final Binder mToken = new EnhancedControllerToken();

  public class EnhancedControllerToken extends Binder {}

  public void addWindowLocked(WindowState windowState) {}

  public boolean isAvailable() {
    return false;
  }

  public boolean isViewVisible() {
    return false;
  }

  public void removeCallbacks() {}

  public void removeWindowLocked(WindowState windowState) {}

  public void setViewVisible(boolean z) {}

  public CustomLetterboxEnhancedController(DisplayContent displayContent) {
    this.mDisplay = displayContent;
    WindowManagerService windowManagerService = displayContent.mWmService;
    this.mWmService = windowManagerService;
    this.mTmpTransaction =
        (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
    this.mHandler = new Handler(UiThread.getHandler().getLooper());
  }

  public final boolean isInvalidSeqLocked(long j) {
    return j != this.mSeq;
  }

  public final void applyLetterboxEnhancedIfNeededLocked(
      boolean z, boolean z2, boolean z3, boolean z4) {
    if (isAvailable()) {
      int enhancedReasonLocked = getEnhancedReasonLocked(z, z2);
      this.mEnhancedReason = enhancedReasonLocked;
      boolean z5 = enhancedReasonLocked == 1;
      boolean z6 = z5 != this.mEnhancedEnabled;
      this.mEnhancedEnabled = z5;
      this.mDisplayDeviceTypeChanged = z4;
      if (z6 || (z5 && z3)) {
        applyLetterboxEnhancedLocked(z6);
      }
    }
  }

  public final int getEnhancedReasonLocked(boolean z, boolean z2) {
    if (!z) {
      return 2;
    }
    if (z2) {
      return 3;
    }
    TaskDisplayArea defaultTaskDisplayArea = this.mDisplay.getDefaultTaskDisplayArea();
    if (defaultTaskDisplayArea == null
        || defaultTaskDisplayArea.getRootHomeTask() == null
        || !defaultTaskDisplayArea.getRootHomeTask().isVisibleRequested()) {
      return this.mDisplay.getTask(
                  new Predicate() { // from class:
                                    // com.android.server.wm.CustomLetterboxEnhancedController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean hasCustomLetterboxEnabledLocked;
                      hasCustomLetterboxEnabledLocked =
                          CustomLetterboxEnhancedController.this.hasCustomLetterboxEnabledLocked(
                              (Task) obj);
                      return hasCustomLetterboxEnabledLocked;
                    }
                  })
              != null
          ? 1
          : 6;
    }
    return 5;
  }

  public static /* synthetic */ boolean lambda$hasCustomLetterboxEnabledLocked$0(
      ActivityRecord activityRecord) {
    return (activityRecord.isVisibleRequested()
            || (activityRecord.finishing && activityRecord.isVisible()))
        && CustomLetterboxConfiguration.isCustomLetterboxEnabled(activityRecord);
  }

  public final boolean hasCustomLetterboxEnabledLocked(Task task) {
    return task.getActivity(
            new Predicate() { // from class:
                              // com.android.server.wm.CustomLetterboxEnhancedController$$ExternalSyntheticLambda4
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$hasCustomLetterboxEnabledLocked$0;
                lambda$hasCustomLetterboxEnabledLocked$0 =
                    CustomLetterboxEnhancedController.lambda$hasCustomLetterboxEnabledLocked$0(
                        (ActivityRecord) obj);
                return lambda$hasCustomLetterboxEnabledLocked$0;
              }
            })
        != null;
  }

  public final void applyLetterboxEnhancedLocked(boolean z) {
    this.mSeq++;
    Slog.d(
        "CustomLetterbox",
        "applyLetterboxEnhancedLocked: seq="
            + this.mSeq
            + ", enabled="
            + this.mEnhancedEnabled
            + ", reason="
            + enhancedReasonToString(this.mEnhancedReason)
            + ", enabledChanged="
            + z
            + ", caller="
            + Debug.getCallers(6));
    this.mWaitingForEnhancedEnabled = z;
    refreshLetterboxLocked(true, true);
    removeCallbacks();
    this.mHandler.removeCallbacks(this.mOnApplyLetterboxEnhanced);
    this.mHandler.post(this.mOnApplyLetterboxEnhanced);
  }

  public boolean isDisplayDeviceTypeChanged() {
    boolean z;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        z = this.mDisplayDeviceTypeChanged;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return z;
  }

  public void onApplyLetterboxEnhanced() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mWaitingForEnhancedEnabled = false;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public final void scheduleCompleteLetterboxEnhanced(final long j, final boolean z) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (isInvalidSeqLocked(j)) {
          WindowManagerService.resetPriorityAfterLockedSection();
        } else {
          WindowManagerService.resetPriorityAfterLockedSection();
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.wm.CustomLetterboxEnhancedController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                  CustomLetterboxEnhancedController.this.lambda$scheduleCompleteLetterboxEnhanced$1(
                      j, z);
                }
              });
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleCompleteLetterboxEnhanced$1(long j, boolean z) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        completeLetterboxEnhancedLocked(j, z);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public final void completeLetterboxEnhancedLocked(long j, boolean z) {
    if (isInvalidSeqLocked(j)) {
      return;
    }
    Slog.d(
        "CustomLetterbox",
        "completeLetterboxEnhancedLocked: seq="
            + this.mSeq
            + ", enabled="
            + this.mEnhancedEnabled
            + ", reason="
            + enhancedReasonToString(this.mEnhancedReason)
            + ", applied="
            + z);
    this.mWaitingForEnhancedEnabled = false;
    refreshLetterboxLocked((z && this.mEnhancedEnabled) ? false : true, false);
    if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_HIDING_WALLPAPER && shouldHideWallpaper()) {
      this.mDisplay.pendingLayoutChanges |= 4;
    }
    this.mWmService.requestTraversal();
  }

  public final void failLetterboxEnhanced() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Slog.w(
            "CustomLetterbox",
            "failLetterboxEnhanced: showLetterbox="
                + this.mShouldShowLetterbox
                + ", enabled="
                + this.mEnhancedEnabled
                + ", reason="
                + enhancedReasonToString(this.mEnhancedReason));
        this.mWaitingForEnhancedEnabled = false;
        refreshLetterboxLocked(true, true);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public final void refreshLetterboxLocked(boolean z, final boolean z2) {
    if (z == this.mShouldShowLetterbox) {
      return;
    }
    this.mShouldShowLetterbox = z;
    Slog.d("CustomLetterbox", "refreshLetterboxLocked: shown=" + z + ", immediately=" + z2);
    this.mDisplay.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.CustomLetterboxEnhancedController$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            CustomLetterboxEnhancedController.this.lambda$refreshLetterboxLocked$2(
                z2, (ActivityRecord) obj);
          }
        });
    if (z2) {
      SurfaceControl.mergeToGlobalTransaction(this.mTmpTransaction);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$refreshLetterboxLocked$2(
      boolean z, ActivityRecord activityRecord) {
    activityRecord.mLetterboxUiController.updateLetterboxSurface(z ? this.mTmpTransaction : null);
  }

  public void onCommitVisibleWallpapers() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.CustomLetterboxEnhancedController$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            CustomLetterboxEnhancedController.this.lambda$onCommitVisibleWallpapers$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onCommitVisibleWallpapers$3() {
    if (isViewVisible()) {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (!isHomeOrRecentsVisibleLocked()) {
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            WindowManagerService.resetPriorityAfterLockedSection();
            setViewVisible(false);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
  }

  public final boolean isHomeOrRecentsVisibleLocked() {
    return !this.mEnhancedEnabled && this.mEnhancedReason == 5;
  }

  public final boolean shouldHideView() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (!isHomeOrRecentsVisibleLocked()
            && !this.mDisplay.mAtmService.getTransitionController().inTransition()
            && this.mDisplay.mWallpaperController.isWallpaperVisible()) {
          boolean shouldShowLetterboxLocked = shouldShowLetterboxLocked();
          WindowManagerService.resetPriorityAfterLockedSection();
          return shouldShowLetterboxLocked;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return false;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final boolean shouldShowLetterboxLocked() {
    return this.mShouldShowLetterbox;
  }

  public final boolean isWaitingForEnhancedEnabledLocked() {
    return this.mWaitingForEnhancedEnabled;
  }

  public void dumpLocked(PrintWriter printWriter, String str, String str2) {
    printWriter.print(str);
    printWriter.print("CustomLetterboxEnhancedController(mDisplayId=");
    printWriter.print(this.mDisplay.mDisplayId);
    printWriter.println(")");
    printWriter.print(str2);
    printWriter.print("mSeq=");
    printWriter.print(this.mSeq);
    printWriter.print(", mEnhancedEnabled=");
    printWriter.print(this.mEnhancedEnabled);
    printWriter.print(", mEnhancedReason=");
    printWriter.print(enhancedReasonToString(this.mEnhancedReason));
    printWriter.println();
    printWriter.print(str2);
    printWriter.print("mShouldShowLetterbox=");
    printWriter.print(this.mShouldShowLetterbox);
    printWriter.print(", mWaitingForEnhancedEnabled=");
    printWriter.print(this.mWaitingForEnhancedEnabled);
    printWriter.println();
  }

  public static String enhancedReasonToString(int i) {
    switch (i) {
      case 0:
        return "UNDEFINED";
      case 1:
        return "ENABLED";
      case 2:
        return "MODE_DISABLED";
      case 3:
        return "LIVE_WALLPAPER";
      case 4:
        return "DISPLAY_FOLDED";
      case 5:
        return "HOME_OR_RECENTS_VISIBLE";
      case 6:
        return "NO_LETTERBOX";
      case 7:
        return "VISIBLE_WALLPAPER_NULL";
      case 8:
        return "CAPTURED_WALLPAPER_NULL";
      default:
        return Integer.toString(i);
    }
  }

  public boolean shouldHideWallpaper() {
    return (CustomLetterboxConfiguration.isAllowWallpaperBelowLetterbox()
            || !this.mEnhancedEnabled
            || this.mShouldShowLetterbox)
        ? false
        : true;
  }

  public Binder getToken() {
    return this.mToken;
  }

  public class CapturedBlurWallpaperInfo {
    public int mBlurRadius;
    public boolean mEnabled;
    public int mHeight;
    public int mReason;
    public Bitmap mScreenshot;
    public long mSeq;
    public int mWallpaperHeight;
    public int mWallpaperWidth;
    public int mWidth;

    public CapturedBlurWallpaperInfo() {}

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("CapturedBlurWallpaperInfo{");
      sb.append("mSeq=");
      sb.append(this.mSeq);
      sb.append(", mEnabled=");
      sb.append(this.mEnabled);
      sb.append(", mReason=");
      sb.append(CustomLetterboxEnhancedController.enhancedReasonToString(this.mReason));
      if (this.mEnabled) {
        sb.append(", mBlurRadius=");
        sb.append(this.mBlurRadius);
        sb.append(", mWidth=");
        sb.append(this.mWidth);
        sb.append(", mHeight=");
        sb.append(this.mHeight);
        if (this.mWallpaperWidth > 0 || this.mWallpaperHeight > 0) {
          sb.append(", mWallpaperWidth=");
          sb.append(this.mWallpaperWidth);
          sb.append(", mWallpaperHeight=");
          sb.append(this.mWallpaperHeight);
        }
      }
      sb.append("}");
      return sb.toString();
    }
  }

  public final CapturedBlurWallpaperInfo getCapturedBlurWallpaperInfo() {
    CapturedBlurWallpaperInfo capturedBlurWallpaperInfo = new CapturedBlurWallpaperInfo();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        capturedBlurWallpaperInfo.mSeq = this.mSeq;
        capturedBlurWallpaperInfo.mEnabled = this.mEnhancedEnabled;
        capturedBlurWallpaperInfo.mReason = this.mEnhancedReason;
        if (!this.mEnhancedEnabled) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return capturedBlurWallpaperInfo;
        }
        capturedBlurWallpaperInfo.mBlurRadius =
            CustomLetterboxConfiguration.getLetterboxBackgroundWallpaperBlurRadius();
        WindowState topVisibleWallpaper =
            this.mDisplay.mWallpaperController.getTopVisibleWallpaper();
        if (topVisibleWallpaper == null) {
          capturedBlurWallpaperInfo.mEnabled = false;
          capturedBlurWallpaperInfo.mReason = 7;
          WindowManagerService.resetPriorityAfterLockedSection();
          return capturedBlurWallpaperInfo;
        }
        Rect bounds = this.mDisplay.getConfiguration().windowConfiguration.getBounds();
        capturedBlurWallpaperInfo.mWidth = bounds.width();
        capturedBlurWallpaperInfo.mHeight = bounds.height();
        capturedBlurWallpaperInfo.mScreenshot =
            getScreenshotLocked(
                capturedBlurWallpaperInfo.mWidth, capturedBlurWallpaperInfo.mHeight);
        if (capturedBlurWallpaperInfo.mScreenshot == null) {
          capturedBlurWallpaperInfo.mEnabled = false;
          capturedBlurWallpaperInfo.mReason = 8;
          WindowManagerService.resetPriorityAfterLockedSection();
          return capturedBlurWallpaperInfo;
        }
        Rect bounds2 = topVisibleWallpaper.getConfiguration().windowConfiguration.getBounds();
        if (capturedBlurWallpaperInfo.mWidth != bounds2.width()
            || capturedBlurWallpaperInfo.mHeight != bounds2.height()) {
          capturedBlurWallpaperInfo.mWallpaperWidth = bounds2.width();
          capturedBlurWallpaperInfo.mWallpaperHeight = bounds2.height();
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return capturedBlurWallpaperInfo;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final Bitmap getScreenshotLocked(int i, int i2) {
    ScreenshotResult takeScreenshotToTargetWindow =
        this.mWmService.mExt.mScreenshotController.takeScreenshotToTargetWindow(
            this.mDisplay.getDisplayId(), 2013, true, new Rect(), i, i2, false, true);
    if (takeScreenshotToTargetWindow != null) {
      return takeScreenshotToTargetWindow.getCapturedBitmap();
    }
    return null;
  }

  public class CapturedBlurWallpaper extends CustomLetterboxEnhancedController {
    public boolean mAdded;
    public final Runnable mApplyCapturedBlurWallpaper;
    public Bitmap mBitmap;
    public CapturedBlurWallpaperInfo mCapturedBlurWallpaperInfo;
    public WindowState mCapturedBlurWallpaperWindow;
    public final Context mContext;
    public WindowManager.LayoutParams mLayoutParams;
    public View mView;

    public CapturedBlurWallpaper(DisplayContent displayContent) {
      super(displayContent);
      this.mApplyCapturedBlurWallpaper =
          new Runnable() { // from class:
                           // com.android.server.wm.CustomLetterboxEnhancedController$CapturedBlurWallpaper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              CustomLetterboxEnhancedController.CapturedBlurWallpaper.this
                  .applyCapturedBlurWallpaper();
            }
          };
      this.mContext = displayContent.mWmService.mContext;
    }

    public final void applyCapturedBlurWallpaper() {
      try {
        CapturedBlurWallpaperInfo capturedBlurWallpaperInfo = getCapturedBlurWallpaperInfo();
        this.mCapturedBlurWallpaperInfo = capturedBlurWallpaperInfo;
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
          bitmap.recycle();
          this.mBitmap = null;
        }
        Bitmap bitmap2 = capturedBlurWallpaperInfo.mScreenshot;
        if (bitmap2 != null) {
          this.mBitmap = bitmap2.copy(Bitmap.Config.ARGB_8888, bitmap2.isMutable());
          bitmap2.recycle();
        }
        if (capturedBlurWallpaperInfo.mEnabled && this.mBitmap != null) {
          setViewVisible(true);
          updateLayoutParams(capturedBlurWallpaperInfo.mWidth, capturedBlurWallpaperInfo.mHeight);
          if (this.mView == null) {
            this.mView = new FrameLayout(this.mContext);
          }
          SemBlurInfo.Builder builder = new SemBlurInfo.Builder(1);
          builder.setBitmap(this.mBitmap);
          builder.setRadius(capturedBlurWallpaperInfo.mBlurRadius);
          this.mView.semSetBlurInfo(builder.build());
          if (this.mAdded) {
            ((WindowManager) SafetySystemService.getSystemService(WindowManager.class))
                .updateViewLayout(this.mView, this.mLayoutParams);
            Slog.d(
                "CustomLetterbox",
                "UpdateView: mView=" + this.mView + ", info=" + capturedBlurWallpaperInfo);
          } else {
            ((WindowManager) SafetySystemService.getSystemService(WindowManager.class))
                .addView(this.mView, this.mLayoutParams);
            this.mAdded = true;
            Slog.d(
                "CustomLetterbox",
                "AddView: mView=" + this.mView + ", info=" + capturedBlurWallpaperInfo);
          }
        } else {
          Slog.d(
              "CustomLetterbox",
              "RemoveView: mView=" + this.mView + ", info=" + capturedBlurWallpaperInfo);
          if (this.mView != null) {
            ((WindowManager) SafetySystemService.getSystemService(WindowManager.class))
                .removeView(this.mView);
            this.mView = null;
          }
          this.mAdded = false;
          Bitmap bitmap3 = this.mBitmap;
          if (bitmap3 != null) {
            bitmap3.recycle();
          }
        }
        scheduleCompleteLetterboxEnhanced(capturedBlurWallpaperInfo.mSeq, this.mAdded);
      } catch (Throwable th) {
        Slog.w("CustomLetterbox", "Failed to applyCapturedBlurWallpaper", th);
        failLetterboxEnhanced();
      }
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public void setViewVisible(boolean z) {
      View view = this.mView;
      if (view != null) {
        view.setVisibility(z ? 0 : 4);
      }
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public boolean isViewVisible() {
      View view = this.mView;
      return view != null && view.getVisibility() == 0;
    }

    public final void updateLayoutParams(int i, int i2) {
      if (this.mLayoutParams == null) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2038, 24, -3);
        this.mLayoutParams = layoutParams;
        layoutParams.token = getToken();
        this.mLayoutParams.setFitInsetsSides(0);
        WindowManager.LayoutParams layoutParams2 = this.mLayoutParams;
        layoutParams2.layoutInDisplayCutoutMode = 3;
        layoutParams2.privateFlags |= 536870976;
      }
      WindowManager.LayoutParams layoutParams3 = this.mLayoutParams;
      layoutParams3.width = i;
      layoutParams3.height = i2;
      layoutParams3.setTitle("CustomLetterbox(" + i + "," + i2 + ")");
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public boolean isAvailable() {
      return CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR;
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public void removeCallbacks() {
      this.mHandler.removeCallbacks(this.mApplyCapturedBlurWallpaper);
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public void onApplyLetterboxEnhanced() {
      super.onApplyLetterboxEnhanced();
      if (CoreRune.SAFE_DEBUG) {
        Slog.d("CustomLetterbox", "onApplyLetterboxEnhanced");
      }
      if (shouldHideView()) {
        setViewVisible(false);
      }
      removeCallbacks();
      this.mHandler.postDelayed(
          this.mApplyCapturedBlurWallpaper, (isDisplayDeviceTypeChanged() ? 2 : 1) * 2000);
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public void addWindowLocked(WindowState windowState) {
      if (windowState.mToken.token != getToken()) {
        return;
      }
      if (this.mCapturedBlurWallpaperWindow != null) {
        Slog.w("CustomLetterbox", "mCapturedBlurWallpaperWindow is already not null.");
      }
      this.mCapturedBlurWallpaperWindow = windowState;
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public void removeWindowLocked(WindowState windowState) {
      if (windowState != this.mCapturedBlurWallpaperWindow) {
        return;
      }
      this.mCapturedBlurWallpaperWindow = null;
    }

    @Override // com.android.server.wm.CustomLetterboxEnhancedController
    public void dumpLocked(PrintWriter printWriter, String str, String str2) {
      super.dumpLocked(printWriter, str, str2);
      if (this.mCapturedBlurWallpaperInfo == null) {
        return;
      }
      printWriter.print(str2);
      printWriter.print(this.mCapturedBlurWallpaperInfo);
      if (this.mAdded) {
        printWriter.print(", mAdded=");
        printWriter.print(this.mAdded);
      }
      if (this.mView != null) {
        printWriter.print(", mView=");
        printWriter.print(this.mView);
      }
      if (this.mLayoutParams != null) {
        printWriter.print(", mLayoutParams=");
        printWriter.print(this.mLayoutParams);
      }
      Bitmap bitmap = this.mBitmap;
      if (bitmap != null && !bitmap.isRecycled()) {
        printWriter.print(", mBitmap=");
        printWriter.print(this.mBitmap);
      }
      printWriter.println();
    }
  }
}
