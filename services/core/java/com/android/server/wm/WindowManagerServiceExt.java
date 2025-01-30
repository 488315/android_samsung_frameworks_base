package com.android.server.wm;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Region;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.IWindow;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.android.internal.util.FastPrintWriter;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.policy.WindowManagerPolicyExt;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestResult;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.cover.CoverState;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WindowManagerServiceExt implements WindowManagerPolicyExt.WindowManagerFuncs {
  public final Context mContext;
  public String mHalfOpenResumedPkgName;
  public List mIgnoreHideNonSystemOverlayWindowApps;
  public MultiResolutionController mMultiResolutionController;
  public final WindowManagerPolicyExt mPolicyExt;
  public String mSafeModeReason;
  public WmScreenshotController mScreenshotController;
  public final WindowManagerService mService;
  public boolean mShouldBeHalfOpenModeLogging;
  public TspStateController mTspStateController;
  public static final String[] SAFE_MODE_REASONS = {
    "KEYCODE_MENU", "KEYCODE_S", "KEYCODE_DPAD_CENTER", "TRACKBALL_BTN_MOUSE", "KEYCODE_VOLUME_DOWN"
  };
  public static final String[] SAFE_MODE_PROPERTY_REASONS = {
    "persist.sys.safemode", "ro.sys.safemode", "ro.boot_recovery", "persist.sys.emergency_reset"
  };
  public ActivityRecord mLastOccludesParentReportedTarget = null;
  public ExtraDisplayPolicy mExtraDisplayPolicy =
      new ExtraDisplayPolicy() { // from class: com.android.server.wm.WindowManagerServiceExt.1
      };
  public int mLastReportedRotationToAudioManager = -1;
  public SparseArray mCurrentTaskForDisplayId = new SparseArray();
  public final Runnable mLogResumedActivityForHalfOpenMode =
      new Runnable() { // from class:
                       // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
          WindowManagerServiceExt.this.lambda$new$5();
        }
      };
  public final Runnable mLogHalfOpenMode =
      new Runnable() { // from class:
                       // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
          WindowManagerServiceExt.this.logHalfOpenMode();
        }
      };

  public boolean showForAllUsers(int i) {
    return i == 2411;
  }

  public WindowManagerServiceExt(Context context, WindowManagerService windowManagerService) {
    this.mContext = context;
    this.mService = windowManagerService;
    WindowManagerPolicyExt createPolicyExtension =
        windowManagerService.mPolicy.createPolicyExtension(context, this);
    this.mPolicyExt = createPolicyExtension;
    this.mScreenshotController = new WmScreenshotController(context, windowManagerService);
    this.mIgnoreHideNonSystemOverlayWindowApps =
        new ArrayList(Arrays.asList(context.getResources().getStringArray(17236435)));
    this.mMultiResolutionController = new MultiResolutionController(windowManagerService, context);
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      TspStateController tspStateController = new TspStateController(context);
      this.mTspStateController = tspStateController;
      createPolicyExtension.setTspStateController(tspStateController);
    }
    if (windowManagerService
        .mContext
        .getPackageManager()
        .hasSystemFeature("com.sec.feature.cover")) {
      WmCoverState.enable();
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX) {
      CustomLetterboxConfiguration.setWindowManager(windowManagerService);
    }
  }

  public boolean executeShellCommand(PrintWriter printWriter, String[] strArr, int i, String str) {
    String[] strArr2 = i < strArr.length ? new String[strArr.length - i] : new String[0];
    System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
    try {
    } catch (Throwable th) {
      th.printStackTrace();
    }
    if (this.mService.mAtmService.mExt.mPackageFeatureManagerService.executeShellCommand(
        printWriter, strArr2, str)) {
      return true;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (PackagesChange.executeAllShellCommand(str, strArr2, printWriter)) {
          return true;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (CoreRune.SAFE_DEBUG) {
          WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock2) {
            try {
              if (UdcCutoutPolicy.executeShellCommandLocked(
                  str, strArr2, printWriter, this.mService)) {
                return true;
              }
              WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
              WindowManagerService.resetPriorityAfterLockedSection();
            }
          }
        }
        WindowManagerGlobalLock windowManagerGlobalLock3 = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock3) {
          try {
            if (BoundsCompatAlignmentController.executeShellCommandLocked(
                str, strArr2, printWriter)) {
              WindowManagerService.resetPriorityAfterLockedSection();
              return true;
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (CoreRune.FW_CUSTOM_LETTERBOX
                && CustomLetterboxConfiguration.executeShellCommand(str, strArr2, printWriter)) {
              return true;
            }
            return CoreRune.FW_BOUNDS_COMPAT_STATUS_LOGGING
                && BoundsCompatStatusLogger.executeShellCommand(str, strArr2, printWriter);
          } finally {
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        }
      } finally {
        WindowManagerService.resetPriorityAfterLockedSection();
      }
    }
  }

  public void systemReady() {
    DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
    if (defaultDisplayContentLocked != null
        && defaultDisplayContentLocked.mUdcCutoutPolicy != null) {
      defaultDisplayContentLocked.updateBaseDisplayCutout(
          defaultDisplayContentLocked.mBaseDisplayWidth,
          defaultDisplayContentLocked.mBaseDisplayHeight);
    }
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      this.mTspStateController.systemReady();
    }
  }

  public List getVisibleWindowInfoList() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
        if (defaultDisplayContentLocked == null
            || !defaultDisplayContentLocked.hasAccess(Binder.getCallingUid())) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        List visibleWindowInfoList = defaultDisplayContentLocked.getVisibleWindowInfoList();
        WindowManagerService.resetPriorityAfterLockedSection();
        return visibleWindowInfoList;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void dispatchSmartClipRemoteRequest(
      int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder) {
    if (smartClipRemoteRequestInfo == null) {
      Slog.e("WindowManagerServiceExt", "dispatchSmartClipRemoteRequest : request is null!");
      return;
    }
    smartClipRemoteRequestInfo.mCallerPid = Binder.getCallingPid();
    smartClipRemoteRequestInfo.mCallerUid = Binder.getCallingUid();
    WindowState findTargetSmartClipWindow =
        findTargetSmartClipWindow(i, i2, iBinder, smartClipRemoteRequestInfo.mWindowTargetingType);
    if (findTargetSmartClipWindow != null) {
      smartClipRemoteRequestInfo.mTargetWindowLayer = findTargetSmartClipWindow.mLayer;
      try {
        findTargetSmartClipWindow.mClient.dispatchSmartClipRemoteRequest(
            smartClipRemoteRequestInfo);
        return;
      } catch (Exception e) {
        Slog.d(
            "WindowManagerServiceExt",
            "dispatchSmartClipRemoteRequest : Failed to call"
                + " IWindow.dispatchSmartClipRemoteRequest()! e="
                + e.toString());
        return;
      }
    }
    Slog.e(
        "WindowManagerServiceExt_SmartClip",
        "dispatchSmartClipRemoteRequest : Could not find the target window! x=" + i + " y=" + i2);
    Slog.e(
        "WindowManagerServiceExt_SmartClip",
        "dispatchSmartClipRemoteRequest : Send empty response...");
    try {
      ((SpenGestureManager) this.mContext.getSystemService("spengestureservice"))
          .sendSmartClipRemoteRequestResult(
              new SmartClipRemoteRequestResult(
                  smartClipRemoteRequestInfo.mRequestId,
                  smartClipRemoteRequestInfo.mRequestType,
                  (Parcelable) null));
    } catch (RuntimeException e2) {
      Slog.e(
          "WindowManagerServiceExt_SmartClip",
          "dispatchSmartClipRemoteRequest : Failed to send the empty result! e=" + e2);
    }
  }

  public final WindowState findTargetSmartClipWindow(
      float f, float f2, final IBinder iBinder, final int i) {
    final int i2 = (int) f;
    final int i3 = (int) f2;
    DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
    if (defaultDisplayContentLocked == null) {
      Slog.e(
          "WindowManagerServiceExt", "findTargetSmartClipWindow : failed to get display content");
      return null;
    }
    return defaultDisplayContentLocked.getWindow(
        new Predicate() { // from class:
                          // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda5
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$findTargetSmartClipWindow$0;
            lambda$findTargetSmartClipWindow$0 =
                WindowManagerServiceExt.lambda$findTargetSmartClipWindow$0(
                    i2, i3, i, iBinder, (WindowState) obj);
            return lambda$findTargetSmartClipWindow$0;
          }
        });
  }

  public static /* synthetic */ boolean lambda$findTargetSmartClipWindow$0(
      int i, int i2, int i3, IBinder iBinder, WindowState windowState) {
    Rect rect = new Rect();
    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
    int i4 = layoutParams.flags;
    int i5 = layoutParams.type;
    if ("ScreenshotAnimation".equalsIgnoreCase(windowState.getWindowTag().toString())
        || !windowState.isVisible()
        || (i4 & 16) != 0) {
      return false;
    }
    windowState.getVisibleBounds(rect);
    if (!rect.contains(i, i2)) {
      return false;
    }
    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && i5 == 2604) {
      return false;
    }
    if (i3 == 1) {
      Region region = new Region();
      windowState.getTouchableRegion(region);
      if (!region.contains(i, i2)) {
        return false;
      }
    }
    if (i3 == 0
        && windowState
            .getDisplayContent()
            .getDisplayPolicy()
            .mExt
            .getTaskbarController()
            .isTaskbar(windowState)) {
      return false;
    }
    rect.set(windowState.getFrame());
    int i6 = windowState.mTouchableInsets;
    if (i6 == 1) {
      int i7 = rect.left;
      Rect rect2 = windowState.mGivenContentInsets;
      rect.left = i7 + rect2.left;
      rect.top += rect2.top;
      rect.right -= rect2.right;
      rect.bottom -= rect2.bottom;
    } else if (i6 == 2 || i6 == 3) {
      int i8 = rect.left;
      Rect rect3 = windowState.mGivenVisibleInsets;
      rect.left = i8 + rect3.left;
      rect.top += rect3.top;
      rect.right -= rect3.right;
      rect.bottom -= rect3.bottom;
    }
    return (rect.contains(i, i2) || ((i4 & 40) == 0)) && windowState.mClient.asBinder() != iBinder;
  }

  public void updateImeTargetWindow(WindowState windowState) {
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      this.mTspStateController.updateImeTargetWindow(windowState);
    }
  }

  public void updateImeWindowVisibility(boolean z) {
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      this.mTspStateController.updateImeWindowVisibility(z);
    }
  }

  public void updateTspStateControllerWindowPolicyLocked(WindowState windowState) {
    DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
    this.mTspStateController.updateWindowsPolicy(
        defaultDisplayContentLocked != null ? defaultDisplayContentLocked.mCurrentFocus : null,
        windowState,
        defaultDisplayContentLocked != null
            ? defaultDisplayContentLocked.mInputMethodWindow
            : null);
  }

  public void setTspDeadzone(Session session, IWindow iWindow, Bundle bundle) {
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowState windowForClientLocked =
              this.mService.windowForClientLocked(session, iWindow, false);
          if (windowForClientLocked == null) {
            Slog.e("WindowManagerServiceExt", "setTspDeadzone failed. The win is null.");
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            windowForClientLocked.setTspDeadzone(bundle);
            updateTspStateControllerWindowPolicyLocked(windowForClientLocked);
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
  }

  public void clearTspDeadzone(Session session, IWindow iWindow) {
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowState windowForClientLocked =
              this.mService.windowForClientLocked(session, iWindow, false);
          if (windowForClientLocked == null) {
            Slog.e("WindowManagerServiceExt", "clearTspDeadzone failed. The win is null.");
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            windowForClientLocked.clearTspDeadzone();
            updateTspStateControllerWindowPolicyLocked(windowForClientLocked);
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
  }

  public void setTspNoteMode(Session session, IWindow iWindow, boolean z) {
    if (CoreRune.FW_TSP_NOTE_MODE) {
      int callingUid = Binder.getCallingUid();
      Context context = this.mContext;
      if (!Settings.checkAndNoteWriteSettingsOperation(
          context, callingUid, Settings.getPackageNameForUid(context, callingUid), false)) {
        throw new SecurityException("Requires WRITE_SETTINGS permission.");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            WindowState windowForClientLocked =
                this.mService.windowForClientLocked(session, iWindow, false);
            if (windowForClientLocked == null) {
              Slog.e("WindowManagerServiceExt", "setTspNoteMode failed. The win is null.");
              WindowManagerService.resetPriorityAfterLockedSection();
            } else {
              windowForClientLocked.setTspNoteMode(z);
              updateTspStateControllerWindowPolicyLocked(windowForClientLocked);
              WindowManagerService.resetPriorityAfterLockedSection();
            }
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void setDeadzoneHole(Bundle bundle) {
    if (CoreRune.FW_TSP_DEADZONE) {
      if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS")
          != 0) {
        throw new SecurityException(
            "setDeadzoneHole requires permission android.permission.WRITE_SECURE_SETTINGS");
      }
      this.mTspStateController.setDeadzoneHole(bundle);
    }
  }

  public void moveDisplayToTop(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
        if (displayContent != null && this.mService.mRoot.getTopChild() != displayContent) {
          this.mService.mRoot.positionChildAt(Integer.MAX_VALUE, displayContent, true);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
    this.mPolicyExt.setPendingIntentAfterUnlock(pendingIntent, intent);
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public boolean isRunningRecentAnimation() {
    return this.mService.getRecentsAnimationController() != null;
  }

  public int[] getInitialDisplayProperties(int i) {
    int[] iArr = new int[3];
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
        if (displayContent != null && displayContent.hasAccess(Binder.getCallingUid())) {
          iArr[0] = displayContent.mInitialDisplayWidth;
          iArr[1] = displayContent.mInitialDisplayHeight;
          iArr[2] = displayContent.mInitialDisplayDensity;
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return iArr;
  }

  public void launchHomeForDesktopMode(int i) {
    this.mPolicyExt.launchHomeForDesktopMode(i);
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void reconfigureDisplay(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent displayContentOrCreate = this.mService.mRoot.getDisplayContentOrCreate(i);
        if (displayContentOrCreate == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
        } else {
          displayContentOrCreate.reconfigureDisplayLocked();
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void onLockTaskFeaturesChanged(SparseIntArray sparseIntArray) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mPolicyExt.onLockTaskFeaturesChanged(sparseIntArray);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void sendTakeScreenshotRunnable(int i, int i2) {
    this.mScreenshotController.sendTakeScreenshotRunnable(i, i2);
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void cancelPendingTakeScreenshotRunnable() {
    this.mScreenshotController.cancelPendingTakeScreenshotRunnable();
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void resetScreenshotConnections() {
    this.mScreenshotController.resetScreenshotConnections();
  }

  public void startLockscreenFingerprintAuth() {
    this.mPolicyExt.startLockscreenFingerprintAuth();
  }

  public boolean ignoreHideNoneSystemOverlayWindowAllowed(String str) {
    return this.mIgnoreHideNonSystemOverlayWindowApps.contains(str);
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public int getFocusedTaskIdWithoutHomeOrRecents() {
    WindowState focusedWindow = this.mService.getFocusedWindow();
    if (focusedWindow == null
        || focusedWindow.getTask() == null
        || focusedWindow.getTask().isActivityTypeHomeOrRecents()) {
      return -1;
    }
    return focusedWindow.getTask().mTaskId;
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void sendShortcutKey(KeyEvent keyEvent) {
    int focusedTaskIdWithoutHomeOrRecents = getFocusedTaskIdWithoutHomeOrRecents();
    if (focusedTaskIdWithoutHomeOrRecents == -1) {
      Slog.e("WindowManagerServiceExt", "sendShortcutKey() - there is no focused task.");
    }
    IKeyEventListener iKeyEventListener = this.mService.mAtmService.mKeyEventListener;
    if (iKeyEventListener != null) {
      try {
        iKeyEventListener.sendShortcutKeyWithFocusedTask(
            focusedTaskIdWithoutHomeOrRecents, keyEvent);
      } catch (Exception e) {
        Slog.e("WindowManagerServiceExt", "sendShortcutKey() : e=" + e);
      }
    }
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void removeTask(int i) {
    this.mService.mAtmService.removeTask(i);
  }

  public void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder) {
    String str;
    int callingUid = Binder.getCallingUid();
    if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
      throw new SecurityException(
          "only system signature can use dispatchSPenGestureEvent, but UID("
              + callingUid
              + ") has not system signature");
    }
    WindowState findTargetSPenGestureWindow = findTargetSPenGestureWindow(i, i2, iBinder);
    if (findTargetSPenGestureWindow == null) {
      StringBuilder sb = new StringBuilder();
      sb.append("dispatchSPenGestureEvent : Could not find the target window!");
      if (CoreRune.SAFE_DEBUG) {
        str = " x=" + i + " y=" + i2;
      } else {
        str = "";
      }
      sb.append(str);
      Slog.e("WindowManagerServiceExt", sb.toString());
      return;
    }
    if (findTargetSPenGestureWindow.getAttrs().type == 2019) {
      if (CoreRune.SAFE_DEBUG) {
        Slog.d("WindowManagerServiceExt", "dispatchSPenGestureEvent : The target window is NavBar");
        return;
      }
      return;
    }
    try {
      if (CoreRune.SAFE_DEBUG) {
        Slog.d(
            "WindowManagerServiceExt",
            "dispatchSPenGestureEvent : The target window is " + findTargetSPenGestureWindow);
      }
      findTargetSPenGestureWindow.mClient.dispatchSPenGestureEvent(inputEventArr);
    } catch (Exception e) {
      Slog.e(
          "WindowManagerServiceExt",
          "Failed to call IWindow.dispatchSmartClipRemoteRequest()!, " + e);
    }
  }

  public final WindowState findTargetSPenGestureWindow(float f, float f2, final IBinder iBinder) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      final int i = (int) f;
      final int i2 = (int) f2;
      try {
        DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
        if (defaultDisplayContentLocked == null) {
          Slog.e(
              "WindowManagerServiceExt",
              "findTargetSPenGestureWindow : failed to get display content");
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        WindowState window =
            defaultDisplayContentLocked.getWindow(
                new Predicate() { // from class:
                                  // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda4
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$findTargetSPenGestureWindow$1;
                    lambda$findTargetSPenGestureWindow$1 =
                        WindowManagerServiceExt.lambda$findTargetSPenGestureWindow$1(
                            i, i2, iBinder, (WindowState) obj);
                    return lambda$findTargetSPenGestureWindow$1;
                  }
                });
        WindowManagerService.resetPriorityAfterLockedSection();
        return window;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public static /* synthetic */ boolean lambda$findTargetSPenGestureWindow$1(
      int i, int i2, IBinder iBinder, WindowState windowState) {
    Rect rect = new Rect();
    int i3 = windowState.mAttrs.flags;
    if (!windowState.isVisible() || (i3 & 16) != 0) {
      return false;
    }
    Region region = new Region();
    windowState.getTouchableRegion(region);
    if (!region.contains(i, i2)) {
      return false;
    }
    rect.set(windowState.getFrame());
    int i4 = windowState.mTouchableInsets;
    if (i4 == 1) {
      int i5 = rect.left;
      Rect rect2 = windowState.mGivenContentInsets;
      rect.left = i5 + rect2.left;
      rect.top += rect2.top;
      rect.right -= rect2.right;
      rect.bottom -= rect2.bottom;
    } else if (i4 == 2 || i4 == 3) {
      int i6 = rect.left;
      Rect rect3 = windowState.mGivenVisibleInsets;
      rect.left = i6 + rect3.left;
      rect.top += rect3.top;
      rect.right -= rect3.right;
      rect.bottom -= rect3.bottom;
    }
    return (rect.contains(i, i2) || ((i3 & 40) == 0)) && windowState.mClient.asBinder() != iBinder;
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public int getPenState() {
    int switchState;
    try {
      switchState = this.mService.mInputManager.getSwitchState(-1, -256, 19);
    } catch (Exception unused) {
      Slog.e("WindowManagerServiceExt", "getPenState failed");
    }
    return switchState == 0
        ? this.mService.mInputManager.getSwitchState(-1, -256, 20) == 0 ? 2 : 1
        : switchState > 0 ? 0 : -1;
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public int getDisplayIdForPointerIcon() {
    return this.mService.mInputManager.getDisplayIdForPointerIcon();
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void handleDexDpadShortcut(IBinder iBinder, KeyEvent keyEvent) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mService
            .mAtmService
            .mDexController
            .getDexMetaKeyPolicy()
            .handleMetaKeyEvent(iBinder, keyEvent);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void handleDexMetaKeyForSnapping() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mService
            .mAtmService
            .mDexController
            .getDexMetaKeyPolicy()
            .handleDexMetaKeyForSnapping();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void keyguardGoingAwayWithFingerprintUnlock(boolean z) {
    this.mPolicyExt.keyguardGoingAwayWithFingerprintUnlock(z);
  }

  public void updateSafeModeReason(int[] iArr, int[] iArr2) {
    StringBuilder sb = new StringBuilder();
    sb.append("SafeModeReason={");
    for (int i = 0; i < iArr.length; i++) {
      if (iArr[i] > 0) {
        sb.append(" ");
        sb.append(SAFE_MODE_REASONS[i]);
      }
    }
    for (int i2 = 0; i2 < iArr2.length; i2++) {
      if (iArr2[i2] > 0) {
        sb.append(" ");
        sb.append(SAFE_MODE_PROPERTY_REASONS[i2]);
        sb.append("[");
        sb.append(iArr2[i2]);
        sb.append("]");
      }
    }
    sb.append(" }");
    String sb2 = sb.toString();
    this.mSafeModeReason = sb2;
    logCriticalInfo(sb2);
  }

  public void dumpLocked(PrintWriter printWriter) {
    printWriter.println("WINDOW MANAGER EXTENSION (dumpsys window extension)");
    if (this.mSafeModeReason != null) {
      printWriter.print("  ");
      printWriter.println(this.mSafeModeReason);
      printWriter.println();
    }
    this.mMultiResolutionController.dumpLocked("  ", printWriter);
    dumpCriticalInfo("  ", printWriter, null);
    PolicyControl.dump("  ", printWriter);
    printWriter.print("  ");
    printWriter.println("ExtraDisplayPolicy=" + this.mExtraDisplayPolicy);
    if (CoreRune.FW_CUSTOM_LETTERBOX) {
      CustomLetterboxConfiguration.dump(printWriter, "  ");
    }
  }

  public String getProcessName(int i) {
    String str;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        WindowProcessController process = this.mService.mAtmService.mProcessMap.getProcess(i);
        str = process != null ? process.mName : null;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return str;
  }

  public static File getWmLoggingFile() {
    return new File(Environment.getDataSystemDirectory(), "wmlogs.txt");
  }

  public static void dumpCriticalInfo(String str, PrintWriter printWriter, String str2) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(getWmLoggingFile()));
      try {
        String str3 = str + "  ";
        printWriter.print(str);
        printWriter.println("dumpCriticalInfo");
        while (true) {
          String readLine = bufferedReader.readLine();
          if (readLine != null) {
            if (str2 != null) {
              printWriter.print(str2);
            }
            printWriter.print(str3);
            printWriter.println(readLine);
          } else {
            printWriter.println();
            bufferedReader.close();
            return;
          }
        }
      } finally {
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e2) {
      e2.printStackTrace();
    }
  }

  public static void logCriticalInfo(String str) {
    logCriticalInfo(str, null);
  }

  public static void logCriticalInfo(String str, List list) {
    File wmLoggingFile = getWmLoggingFile();
    if (wmLoggingFile.length() > 5242880) {
      if (wmLoggingFile.renameTo(new File(wmLoggingFile.getPath() + ".backup"))) {
        wmLoggingFile = getWmLoggingFile();
      }
    }
    try {
      final FastPrintWriter fastPrintWriter =
          new FastPrintWriter(new FileOutputStream(wmLoggingFile, true));
      try {
        fastPrintWriter.print(
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                .format(new Date(System.currentTimeMillis())));
        fastPrintWriter.print(": ");
        fastPrintWriter.println(str);
        if (list != null && !list.isEmpty()) {
          list.forEach(
              new Consumer() { // from class:
                               // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  WindowManagerServiceExt.lambda$logCriticalInfo$2(fastPrintWriter, (String) obj);
                }
              });
        }
        fastPrintWriter.close();
      } finally {
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    FileUtils.setPermissions(wmLoggingFile.toString(), 508, -1, -1);
  }

  public static /* synthetic */ void lambda$logCriticalInfo$2(PrintWriter printWriter, String str) {
    printWriter.println("  " + str);
  }

  /* JADX WARN: Removed duplicated region for block: B:27:0x00cb  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean applyScreenRatioToSizeDensity(DisplayContent displayContent, String str, int i) {
    boolean z;
    int i2 = i;
    if (CoreRune.FW_DEFAULT_HIGH_RESOLUTION_DEVICE) {
      Slog.d("WindowManagerServiceExt", "Use the default resolution for high resolution");
      return false;
    }
    if (str != null && str.length() > 0) {
      return false;
    }
    if (isLCDDetached()) {
      Slog.d("WindowManagerServiceExt", "LCD detached.. skip to set high performance concept");
      return false;
    }
    int i3 = (int) (displayContent.mInitialDisplayWidth * 0.75d);
    int i4 = (int) (displayContent.mInitialDisplayHeight * 0.75d);
    float f = (float) (displayContent.mInitialPhysicalXDpi * 0.75d);
    float f2 = (float) (displayContent.mInitialPhysicalYDpi * 0.75d);
    if (i2 <= 0) {
      i2 = displayContent.mInitialDisplayDensity;
    }
    int i5 = (int) (i2 * 0.75d);
    if (displayContent.mBaseDisplayWidth != i3 || displayContent.mBaseDisplayHeight != i4) {
      displayContent.updateBaseDisplayMetrics(i3, i4, i5, f, f2);
    } else if (displayContent.mBaseDisplayDensity != i5) {
      displayContent.mBaseDisplayDensity = i5;
    } else {
      z = false;
      Slog.d(
          "WindowManagerServiceExt",
          "applyScreenRatioToSizeDensity"
              + (" init size: "
                  + displayContent.mInitialDisplayWidth
                  + "x"
                  + displayContent.mInitialDisplayHeight
                  + " "
                  + displayContent.mInitialDisplayDensity
                  + "dpi base size: "
                  + displayContent.mBaseDisplayWidth
                  + "x"
                  + displayContent.mBaseDisplayHeight
                  + " "
                  + displayContent.mBaseDisplayDensity
                  + "dpi baseScreenRatio: 0.75"));
      if (z) {
        if (displayContent.mInitialDisplayWidth != i3
            || displayContent.mInitialDisplayHeight != i4) {
          displayContent.mIsSizeForced = true;
        }
        if (displayContent.mInitialDisplayDensity != i5) {
          displayContent.mIsDensityForced = true;
        }
        this.mService.mDisplayWindowSettings.setForcedSize(
            displayContent, displayContent.mBaseDisplayWidth, displayContent.mBaseDisplayHeight);
        this.mService.mDisplayWindowSettings.setForcedDensity(
            displayContent.getDisplayInfo(), displayContent.mBaseDisplayDensity, 0);
      }
      return z;
    }
    z = true;
    Slog.d(
        "WindowManagerServiceExt",
        "applyScreenRatioToSizeDensity"
            + (" init size: "
                + displayContent.mInitialDisplayWidth
                + "x"
                + displayContent.mInitialDisplayHeight
                + " "
                + displayContent.mInitialDisplayDensity
                + "dpi base size: "
                + displayContent.mBaseDisplayWidth
                + "x"
                + displayContent.mBaseDisplayHeight
                + " "
                + displayContent.mBaseDisplayDensity
                + "dpi baseScreenRatio: 0.75"));
    if (z) {}
    return z;
  }

  public final boolean isLCDDetached() {
    String readResultFromFile = readResultFromFile();
    if (!"ff ff ff".equals(readResultFromFile)) {
      return false;
    }
    Slog.d("WindowManagerServiceExt", "LCD Detached: " + readResultFromFile);
    return true;
  }

  public final String readResultFromFile() {
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader bufferedReader =
          new BufferedReader(new FileReader("/sys/class/lcd/panel/window_type"));
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          sb.append(readLine);
          sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } finally {
        }
      }
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString().trim();
  }

  public void takeLockTaskLog(Task task, int i, boolean z, int i2) {
    logCriticalInfo(
        Debug.getCaller()
            + ", task:"
            + task
            + " Uid="
            + i
            + " isSystemCaller(stopAppPinning)="
            + z
            + " LockTaskModeState="
            + i2
            + " Callers: "
            + Debug.getCallers(5));
  }

  public void updateOccludeTargetIfNeeded(DisplayContent displayContent) {
    updateOccludeTargetIfNeeded(displayContent, null);
  }

  public void updateOccludeTargetIfNeeded(
      DisplayContent displayContent, ActivityRecord activityRecord) {
    if (displayContent == null || !displayContent.isDefaultDisplay) {
      Slog.w(
          "WindowManagerServiceExt", "notifyOccludeChangeNotice: display is null or not default");
      return;
    }
    final AtomicReference atomicReference = new AtomicReference();
    boolean z = true;
    ActivityRecord activity =
        displayContent.getActivity(
            new Predicate() { // from class:
                              // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$updateOccludeTargetIfNeeded$3;
                lambda$updateOccludeTargetIfNeeded$3 =
                    WindowManagerServiceExt.lambda$updateOccludeTargetIfNeeded$3(
                        atomicReference, (ActivityRecord) obj);
                return lambda$updateOccludeTargetIfNeeded$3;
              }
            },
            true);
    if (activity == null) {
      activity = (ActivityRecord) atomicReference.get();
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION
        && displayContent.mTransitionController.hasTransientLaunch()) {
      Slog.d(
          "WindowManagerServiceExt",
          "notifyOccludeChangeNotice: defer to send callback while on transient launch, caller="
              + Debug.getCallers(5));
      return;
    }
    if (activity == null
        || (activityRecord != null && activityRecord.isActivityTypeHomeOrRecents())) {
      activity = null;
      if (this.mLastOccludesParentReportedTarget != null) {
        Slog.d(
            "WindowManagerServiceExt",
            "notifyOccludeChangeNotice: reset caller=" + Debug.getCallers(5));
        this.mService
            .mAtmService
            .getTaskChangeNotificationController()
            .notifyOccludeChangeNotice(null, false);
      }
    } else if (this.mLastOccludesParentReportedTarget != activity
        || activity.equals(activityRecord)) {
      ComponentName componentName = new ComponentName(activity.packageName, activity.info.name);
      boolean occludesParent = activity.occludesParent();
      boolean inSplitScreenWindowingMode = activity.inSplitScreenWindowingMode();
      Slog.d(
          "WindowManagerServiceExt",
          "notifyOccludeChangeNotice: cn="
              + componentName
              + " occludesParent="
              + occludesParent
              + " isInSplitScreenMode="
              + inSplitScreenWindowingMode
              + " styleFloating="
              + activity.mStyleFloating
              + " caller="
              + Debug.getCallers(5));
      TaskChangeNotificationController taskChangeNotificationController =
          this.mService.mAtmService.getTaskChangeNotificationController();
      if (!occludesParent && activity.mStyleFloating && !inSplitScreenWindowingMode) {
        z = false;
      }
      taskChangeNotificationController.notifyOccludeChangeNotice(componentName, z);
    }
    this.mLastOccludesParentReportedTarget = activity;
  }

  public static /* synthetic */ boolean lambda$updateOccludeTargetIfNeeded$3(
      AtomicReference atomicReference, ActivityRecord activityRecord) {
    if ((!activityRecord.isActivityTypeStandard() && !activityRecord.isActivityTypeAssistant())
        || !activityRecord.isVisibleRequested()) {
      return false;
    }
    if ((activityRecord.getTask() != null
            && activityRecord.getTask().getWindowConfiguration().tasksAreFloating())
        || activityRecord.mPopOverState.isActivated()) {
      return false;
    }
    if (atomicReference.get() == null) {
      atomicReference.set(activityRecord);
    }
    return activityRecord.occludesParent();
  }

  public void updateCoverState(CoverState coverState) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
        CoverPolicy coverPolicy =
            defaultDisplayContentLocked != null
                ? defaultDisplayContentLocked.getDisplayPolicy().mExt.mCoverPolicy
                : null;
        if (coverPolicy != null) {
          coverPolicy.updateCoverStateLocked(coverState);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  @Override // com.android.server.policy.WindowManagerPolicyExt.WindowManagerFuncs
  public void postRotationInfoForAudioManager() {
    this.mService.f1749mH.removeCallbacks(
        new Runnable() { // from class:
                         // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            WindowManagerServiceExt.this.setRotationInfoForAudioManager();
          }
        });
    this.mService.f1749mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            WindowManagerServiceExt.this.setRotationInfoForAudioManager();
          }
        });
  }

  public void setRotationInfoForAudioManager() {
    final AudioManager audioManager;
    int defaultDisplayRotation;
    if (!this.mService.mPolicy.isScreenOn()
        || (audioManager = (AudioManager) SafetySystemService.getSystemService(AudioManager.class))
            == null
        || this.mLastReportedRotationToAudioManager
            == (defaultDisplayRotation = this.mService.getDefaultDisplayRotation())) {
      return;
    }
    this.mLastReportedRotationToAudioManager = defaultDisplayRotation;
    final String str = "g_hw_display_rotation=" + defaultDisplayRotation;
    AsyncTask.execute(
        new Runnable() { // from class:
                         // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            audioManager.setParameters(str);
          }
        });
  }

  public void logHalfOpenModeFromResumeTopActivityIfNeededLocked(ActivityRecord activityRecord) {
    this.mService.mExt.getClass();
    throw null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$5() {
    CoreSaLogger.logForBasic("W010", this.mHalfOpenResumedPkgName);
  }

  public final void logHalfOpenMode() {
    ActivityRecord activityRecord = this.mService.mAtmService.mLastResumedActivity;
    CoreSaLogger.logForBasic("W010", activityRecord != null ? activityRecord.packageName : null);
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mShouldBeHalfOpenModeLogging = true;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }
}
