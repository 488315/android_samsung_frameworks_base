package com.android.server.wm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.PerfLog;
import android.util.Slog;
import android.view.DisplayAddress;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.window.ScreenCapture;
import com.android.internal.util.ScreenshotRequest;
import com.android.server.policy.WindowManagerPolicyExt;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.ScreenshotResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WmScreenshotController {
  public Context mContext;
  public boolean mIgnorePolicy;
  public int mReasonForFailure;
  public String mSecuredWindowName;
  public WindowManagerService mService;
  public WmScreenshotShellCommand mWmScreenshotShellCommand;
  public final Rect mTmpRect = new Rect();
  public int mWindowType = 2015;
  public int mDisplayId = 0;
  public Handler mHandler = new Handler(Looper.myLooper());
  public final Object mScreenshotLock = new Object();
  public HashMap mScreenshotConnections = new HashMap();
  public final TakeScreenshotRunnable mTakeScreenshotRunnable = new TakeScreenshotRunnable();
  public BroadcastReceiver mPalmMotionReceiver =
      new BroadcastReceiver() { // from class: com.android.server.wm.WmScreenshotController.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          int actionToDirection;
          String action = intent.getAction();
          boolean isKeyguardShowingAndNotOccluded =
              WmScreenshotController.this.mService.mPolicy.isKeyguardShowingAndNotOccluded();
          Log.d(
              StartingSurfaceController.TAG,
              "Receive " + action + ", isKeyguardLocked=" + isKeyguardShowingAndNotOccluded);
          if (!isKeyguardShowingAndNotOccluded
              && (actionToDirection = WmScreenshotController.this.actionToDirection(action)) > 0) {
            WmScreenshotController.this.sendTakeScreenshotRunnable(
                1, actionToDirection, 0, 2, null);
          }
        }
      };
  public BroadcastReceiver mCaptureReceiver =
      new BroadcastReceiver() { // from class: com.android.server.wm.WmScreenshotController.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          Bundle bundle;
          int i;
          String action = intent.getAction();
          if ("com.samsung.android.capture.ScreenshotExecutor".equals(action)) {
            int intExtra = intent.getIntExtra("capturedOrigin", -1);
            Bundle extras = intent.getExtras();
            if (intExtra != 3) {
              if (intExtra == 4) {
                String stringExtra = intent.getStringExtra("type");
                if ("Fullscreen".equals(stringExtra)) {
                  i = 1;
                } else {
                  if (!"Region".equals(stringExtra)) {
                    Log.e(
                        StartingSurfaceController.TAG,
                        "Error. extra type was wrong. type=" + stringExtra);
                    return;
                  }
                  i = 2;
                }
                r5 = WmScreenshotController.this.getDexMode() != 1 ? 2 : 0;
                bundle = null;
              } else if (intExtra == 5) {
                bundle = extras;
              } else if (intExtra != 6) {
                if (intExtra == 100) {
                  String stringExtra2 = intent.getStringExtra("callingPackageName");
                  if (TextUtils.isEmpty(stringExtra2)) {
                    Log.e(
                        StartingSurfaceController.TAG,
                        "Can not take a screenshot, callingPackageName is empty.");
                    return;
                  }
                  String stringExtra3 = intent.getStringExtra("type");
                  int i2 =
                      ("Fullscreen".equals(stringExtra3) || !"Region".equals(stringExtra3)) ? 1 : 2;
                  String stringExtra4 = intent.getStringExtra("displayId");
                  r5 = TextUtils.isEmpty(stringExtra4) ? 0 : Integer.parseInt(stringExtra4);
                  Log.d(
                      StartingSurfaceController.TAG,
                      "Custom info, callingPackageName="
                          + stringExtra2
                          + " type="
                          + stringExtra3
                          + " displayId="
                          + stringExtra4);
                  bundle = extras;
                  i = i2;
                } else {
                  Log.e(
                      StartingSurfaceController.TAG,
                      "Error. capturedOrigin(" + intExtra + ") is not supported.");
                  return;
                }
              } else {
                if (extras == null) {
                  Log.e(StartingSurfaceController.TAG, "Error. bundle is null");
                  return;
                }
                Rect rect = (Rect) extras.getParcelable("rect");
                if (rect == null || rect.isEmpty()) {
                  Log.e(StartingSurfaceController.TAG, "Error. rect is " + rect);
                  return;
                }
                i = 101;
                bundle = extras;
              }
              Log.d(
                  StartingSurfaceController.TAG,
                  "Receive "
                      + action
                      + " screenType="
                      + i
                      + " direction=1 display="
                      + r5
                      + " from="
                      + WmScreenshotController.this.fromToString(intExtra));
              WmScreenshotController.this.sendTakeScreenshotRunnable(i, 1, r5, intExtra, bundle);
            }
            bundle = null;
            i = 1;
            Log.d(
                StartingSurfaceController.TAG,
                "Receive "
                    + action
                    + " screenType="
                    + i
                    + " direction=1 display="
                    + r5
                    + " from="
                    + WmScreenshotController.this.fromToString(intExtra));
            WmScreenshotController.this.sendTakeScreenshotRunnable(i, 1, r5, intExtra, bundle);
          }
        }
      };

  public interface DirectoryResolver {
    File getSystemDirectoryForUser(int i);
  }

  public WmScreenshotController(Context context, WindowManagerService windowManagerService) {
    this.mContext = context;
    this.mService = windowManagerService;
    this.mWmScreenshotShellCommand = new WmScreenshotShellCommand(windowManagerService);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.android.motion.SWEEP_LEFT");
    intentFilter.addAction("com.samsung.android.motion.SWEEP_RIGHT");
    intentFilter.addAction("com.samsung.android.motion.SWEEP_FULL_SCREEN");
    this.mContext.registerReceiverAsUser(
        this.mPalmMotionReceiver,
        UserHandle.ALL,
        intentFilter,
        "com.samsung.permission.PALM_MOTION",
        null);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("com.samsung.android.capture.ScreenshotExecutor");
    this.mContext.registerReceiverAsUser(
        this.mCaptureReceiver,
        UserHandle.ALL,
        intentFilter2,
        "com.samsung.permission.CAPTURE",
        null);
  }

  public void sendTakeScreenshotRunnable(int i, int i2) {
    sendTakeScreenshotRunnable(i, 1, i2, 1, null);
  }

  public final void sendTakeScreenshotRunnable(int i, int i2, int i3, int i4, Bundle bundle) {
    this.mHandler.removeCallbacks(this.mTakeScreenshotRunnable);
    this.mTakeScreenshotRunnable.info.set(i, i2, i3, i4, bundle);
    this.mHandler.post(this.mTakeScreenshotRunnable);
  }

  public void cancelPendingTakeScreenshotRunnable() {
    this.mHandler.removeCallbacks(this.mTakeScreenshotRunnable);
  }

  public final void takeScreenshot(final WmScreenshotInfo wmScreenshotInfo) {
    boolean z;
    UserHandle userHandle = UserHandle.CURRENT;
    synchronized (this.mScreenshotLock) {
      if (this.mScreenshotConnections.size() >= 3) {
        Log.e(
            StartingSurfaceController.TAG,
            "Too many screenshot service connection: " + this.mScreenshotConnections.size());
        return;
      }
      if (this.mService.mAtmService.getPersonaActivityHelper() != null) {
        UserHandle currentScreenUserId =
            this.mService.mAtmService.getPersonaActivityHelper().getCurrentScreenUserId(userHandle);
        if (SemPersonaManager.isKnoxId(currentScreenUserId.getIdentifier())) {
          try {
            z =
                EnterpriseDeviceManager.getInstance(this.mContext)
                    .getProfilePolicy()
                    .getRestriction("restriction_property_screencapture_save_to_owner");
          } catch (SecurityException e) {
            Log.e(StartingSurfaceController.TAG, "Exception: " + e);
          }
          if (z || SemPersonaManager.isSecureFolderId(currentScreenUserId.getIdentifier())) {
            userHandle = currentScreenUserId;
          }
        }
        z = false;
        if (z) {}
        userHandle = currentScreenUserId;
      }
      Log.d(
          StartingSurfaceController.TAG,
          "takeScreenshot: info=" + wmScreenshotInfo + ", user=" + userHandle.getIdentifier());
      PerfLog.d(21, (short) 10, "TakeScreenshot");
      Intent intent = new Intent();
      intent.setComponent(
          new ComponentName(
              "com.android.systemui", "com.android.systemui.screenshot.TakeScreenshotService"));
      final ServiceConnection serviceConnection =
          new ServiceConnection() { // from class: com.android.server.wm.WmScreenshotController.1
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {}

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
              WmScreenshotController.this.sendScreenshotMessage(iBinder, this, wmScreenshotInfo);
            }
          };
      if (this.mContext.bindServiceAsUser(intent, serviceConnection, 1, userHandle)) {
        this.mScreenshotConnections.put(
            serviceConnection,
            new Runnable() { // from class:
                             // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                WmScreenshotController.this.lambda$takeScreenshot$0(serviceConnection);
              }
            });
        this.mHandler.postDelayed(
            (Runnable) this.mScreenshotConnections.get(serviceConnection), 10000L);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$takeScreenshot$0(ServiceConnection serviceConnection) {
    resetConnection(serviceConnection, true);
  }

  public final void sendScreenshotMessage(
      IBinder iBinder,
      final ServiceConnection serviceConnection,
      WmScreenshotInfo wmScreenshotInfo) {
    synchronized (this.mScreenshotLock) {
      if (this.mScreenshotConnections.containsKey(serviceConnection)) {
        Message obtain = Message.obtain();
        obtain.what = wmScreenshotInfo.getType();
        obtain.replyTo =
            new Messenger(
                new Handler(
                    this.mHandler
                        .getLooper()) { // from class:
                                        // com.android.server.wm.WmScreenshotController.2
                  @Override // android.os.Handler
                  public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                      if (CoreRune.SAFE_DEBUG) {
                        Slog.d(StartingSurfaceController.TAG, "Get SCREENSHOT_MSG_URI");
                      }
                    } else {
                      if (i != 2) {
                        return;
                      }
                      if (CoreRune.SAFE_DEBUG) {
                        Slog.d(
                            StartingSurfaceController.TAG, "Get SCREENSHOT_MSG_PROCESS_COMPLETE");
                      }
                      WmScreenshotController.this.resetConnection(serviceConnection, true);
                    }
                  }
                });
        obtain.obj = new ScreenshotRequest.Builder(wmScreenshotInfo.getType(), 5).build();
        Bundle bundle = new Bundle();
        bundle.putInt("sweepDirection", wmScreenshotInfo.getSweepDirection());
        bundle.putInt("capturedDisplay", wmScreenshotInfo.getDisplayId());
        bundle.putInt("capturedOrigin", wmScreenshotInfo.getOrigin());
        bundle.putBundle("captureSharedBundle", wmScreenshotInfo.getBundle());
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            DisplayContent displayContent =
                this.mService.mRoot.getDisplayContent(wmScreenshotInfo.getDisplayId());
            if (displayContent != null) {
              putSystemBarVisible(obtain, displayContent);
              putSystemBarHeight(bundle, displayContent);
              putStackBounds(bundle, displayContent);
              putCutoutSafeInsets(bundle, displayContent);
              if (wmScreenshotInfo.getType() == 100
                  && !putFocusedWindowInfo(bundle, displayContent)) {
                obtain.what = 1;
              }
            } else {
              Slog.e(
                  StartingSurfaceController.TAG,
                  "Get screenshot display failed, " + wmScreenshotInfo.getDisplayId());
            }
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        obtain.setData(bundle);
        try {
          new Messenger(iBinder).send(obtain);
        } catch (RemoteException e) {
          Slog.e(StartingSurfaceController.TAG, "Send screenshot message failed, " + e);
        }
      }
    }
  }

  public final void putSystemBarVisible(Message message, DisplayContent displayContent) {
    message.arg1 = displayContent.getDisplayPolicy().isStatusBarVisibleLw() ? 1 : 0;
    message.arg2 = displayContent.getDisplayPolicy().isNavigationBarVisibleLw() ? 1 : 0;
  }

  public final void putSystemBarHeight(Bundle bundle, DisplayContent displayContent) {
    DisplayInfo displayInfo = displayContent.getDisplayInfo();
    this.mTmpRect.set(
        displayContent
            .getDisplayPolicy()
            .getDecorInsetsInfo(
                displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight)
            .mConfigInsets);
    bundle.putInt("statusBarHeight", this.mTmpRect.top);
    int navBarPosition = displayContent.getDisplayPolicy().getNavBarPosition();
    if (navBarPosition == 1) {
      bundle.putInt("navigationBarHeight", this.mTmpRect.left);
    } else if (navBarPosition == 2) {
      bundle.putInt("navigationBarHeight", this.mTmpRect.right);
    } else {
      bundle.putInt("navigationBarHeight", this.mTmpRect.bottom);
    }
  }

  public final void putStackBounds(Bundle bundle, DisplayContent displayContent) {
    WindowState windowState = displayContent.mCurrentFocus;
    Task task = windowState != null ? windowState.getTask() : null;
    if (task == null || task.getParent() == null) {
      return;
    }
    task.getParent().getBounds(this.mTmpRect);
    bundle.putParcelable("stackBounds", this.mTmpRect);
  }

  public final void putCutoutSafeInsets(Bundle bundle, DisplayContent displayContent) {
    ActivityRecord activityRecord;
    WindowState findMainWindow;
    WindowState windowState = displayContent.mCurrentFocus;
    if (windowState == null
        || (activityRecord = windowState.mActivityRecord) == null
        || (findMainWindow = activityRecord.findMainWindow()) == null
        || !findMainWindow.isLetterboxedForDisplayCutout()) {
      return;
    }
    DisplayCutout calculateDisplayCutoutForRotation =
        displayContent.calculateDisplayCutoutForRotation(displayContent.getRotation());
    if (calculateDisplayCutoutForRotation.isEmpty()) {
      return;
    }
    Rect safeInsets = calculateDisplayCutoutForRotation.getSafeInsets();
    bundle.putInt("safeInsetLeft", safeInsets.left);
    bundle.putInt("safeInsetTop", safeInsets.top);
    bundle.putInt("safeInsetRight", safeInsets.right);
    bundle.putInt("safeInsetBottom", safeInsets.bottom);
  }

  public final boolean putFocusedWindowInfo(Bundle bundle, DisplayContent displayContent) {
    WindowState windowState = displayContent.mCurrentFocus;
    Task task = windowState != null ? windowState.getTask() : null;
    if (task == null) {
      return false;
    }
    this.mTmpRect.setEmpty();
    unionAllVisibleWindowsInTask(task, this.mTmpRect);
    if (this.mTmpRect.isEmpty()) {
      return false;
    }
    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(Integer.valueOf(this.mTmpRect.left));
    arrayList.add(Integer.valueOf(this.mTmpRect.top));
    arrayList.add(Integer.valueOf(this.mTmpRect.right));
    arrayList.add(Integer.valueOf(this.mTmpRect.bottom));
    arrayList.add(Integer.valueOf(task.getWindowingMode()));
    bundle.putIntegerArrayList("windowCapture", arrayList);
    return true;
  }

  public final void unionAllVisibleWindowsInTask(Task task, final Rect rect) {
    task.forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda4
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            WmScreenshotController.lambda$unionAllVisibleWindowsInTask$1(rect, (WindowState) obj);
          }
        },
        true);
  }

  public static /* synthetic */ void lambda$unionAllVisibleWindowsInTask$1(
      Rect rect, WindowState windowState) {
    if (windowState.isVisible()) {
      rect.union(windowState.getFrame());
    }
  }

  public final void resetConnection(ServiceConnection serviceConnection, boolean z) {
    synchronized (this.mScreenshotLock) {
      if (this.mScreenshotConnections.containsKey(serviceConnection) && serviceConnection != null) {
        this.mContext.unbindService(serviceConnection);
        this.mHandler.removeCallbacks(
            (Runnable) this.mScreenshotConnections.get(serviceConnection));
        if (z) {
          this.mScreenshotConnections.remove(serviceConnection);
        }
      }
    }
  }

  public void resetScreenshotConnections() {
    synchronized (this.mScreenshotLock) {
      if (!this.mScreenshotConnections.isEmpty()) {
        Iterator it = this.mScreenshotConnections.keySet().iterator();
        while (it.hasNext()) {
          resetConnection((ServiceConnection) it.next(), false);
        }
        this.mScreenshotConnections.clear();
      }
    }
  }

  public class TakeScreenshotRunnable implements Runnable {
    public final WmScreenshotInfo info;

    public TakeScreenshotRunnable() {
      this.info = new WmScreenshotInfo();
    }

    @Override // java.lang.Runnable
    public void run() {
      WmScreenshotController.this.takeScreenshot(this.info);
    }
  }

  public final int actionToDirection(String str) {
    if ("com.samsung.android.motion.SWEEP_LEFT".equals(str)) {
      return 2;
    }
    if ("com.samsung.android.motion.SWEEP_RIGHT".equals(str)) {
      return 3;
    }
    return "com.samsung.android.motion.SWEEP_FULL_SCREEN".equals(str) ? 1 : -1;
  }

  public final String fromToString(int i) {
    switch (i) {
      case 1:
        return "KEY";
      case 2:
        return "PALM";
      case 3:
        return "QUICK_PANEL";
      case 4:
        return "DEX";
      case 5:
        return "BIXBY";
      case 6:
        return "FLEX_PANEL";
      default:
        return Integer.toString(i);
    }
  }

  public final int getDexMode() {
    int dexModeLocked;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        dexModeLocked = this.mService.mAtmService.mDexController.getDexModeLocked();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return dexModeLocked;
  }

  public ScreenshotResult takeScreenshotToTargetWindow(
      int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) {
    return takeScreenshotToTargetWindow(i, i2, z, rect, i3, i4, z2, z3, false);
  }

  /* JADX WARN: Removed duplicated region for block: B:25:0x00d9  */
  /* JADX WARN: Removed duplicated region for block: B:29:0x00ea A[Catch: all -> 0x0144, TryCatch #0 {all -> 0x0144, blocks: (B:3:0x0019, B:4:0x0020, B:27:0x00dd, B:29:0x00ea, B:31:0x00ef, B:33:0x0103, B:36:0x0109, B:37:0x010c, B:40:0x0121, B:42:0x012f, B:44:0x00f9, B:46:0x00fd, B:55:0x0140, B:56:0x0143, B:6:0x0021, B:8:0x002c, B:9:0x0031, B:15:0x0084, B:17:0x008a, B:19:0x0096, B:21:0x009c, B:23:0x00b4, B:26:0x00dc, B:49:0x00a6, B:51:0x00ae), top: B:2:0x0019, inners: #1 }] */
  /* JADX WARN: Removed duplicated region for block: B:35:0x0107 A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:39:0x011f A[ADDED_TO_REGION] */
  /* JADX WARN: Removed duplicated region for block: B:42:0x012f A[Catch: all -> 0x0144, TRY_LEAVE, TryCatch #0 {all -> 0x0144, blocks: (B:3:0x0019, B:4:0x0020, B:27:0x00dd, B:29:0x00ea, B:31:0x00ef, B:33:0x0103, B:36:0x0109, B:37:0x010c, B:40:0x0121, B:42:0x012f, B:44:0x00f9, B:46:0x00fd, B:55:0x0140, B:56:0x0143, B:6:0x0021, B:8:0x002c, B:9:0x0031, B:15:0x0084, B:17:0x008a, B:19:0x0096, B:21:0x009c, B:23:0x00b4, B:26:0x00dc, B:49:0x00a6, B:51:0x00ae), top: B:2:0x0019, inners: #1 }] */
  /* JADX WARN: Removed duplicated region for block: B:48:0x00db  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ScreenshotResult takeScreenshotToTargetWindow(
      int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) {
    String str;
    StringBuilder sb;
    SurfaceControl surfaceControl;
    SurfaceControl findTargetSurfaceForAppWindowTarget;
    DisplayInfo displayInfo;
    IBinder iBinder;
    IBinder displayToken;
    printScreenshotLog(i, i2, z, rect, i3, i4, z2, z3);
    ScreenshotResult.Builder builder = new ScreenshotResult.Builder();
    StringBuilder sb2 = new StringBuilder();
    this.mReasonForFailure = 0;
    this.mSecuredWindowName = "";
    Bitmap bitmap = null;
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
          if (displayContent == null) {
            this.mReasonForFailure |= 1;
            WindowManagerService.resetPriorityAfterLockedSection();
            str = StartingSurfaceController.TAG;
            sb = new StringBuilder();
          } else if (z3 || isScreenshotAllowedByPolicy(displayContent)) {
            if (WindowManagerPolicyExt.isSystemWindowType(i2)) {
              findTargetSurfaceForAppWindowTarget =
                  findTargetSurfaceForSystemWindowTarget(displayContent, i2, z, sb2);
            } else if (WindowManagerPolicyExt.isAppWindowType(i2)) {
              findTargetSurfaceForAppWindowTarget =
                  findTargetSurfaceForAppWindowTarget(displayContent);
            } else {
              surfaceControl = null;
              Log.d(
                  StartingSurfaceController.TAG,
                  "takeScreenshotToTargetWindow: targetSurface="
                      + surfaceControl
                      + ", sourceCrop="
                      + rect);
              final DisplayContent displayContent2 =
                  !CoreRune.FW_SCREENSHOT_FOR_HDR ? displayContent : null;
              WindowManagerService.resetPriorityAfterLockedSection();
              displayInfo = DisplayManagerGlobal.getInstance().getDisplayInfo(i);
              if (displayInfo != null) {
                if (displayInfo.type == 5) {
                  displayToken = this.mService.mDisplayManagerInternal.getRealDisplayToken(i);
                } else {
                  DisplayAddress displayAddress = displayInfo.address;
                  if (displayAddress != null) {
                    displayToken = SurfaceControl.getDisplayToken(displayAddress);
                  }
                }
                iBinder = displayToken;
                if (CoreRune.FW_SCREENSHOT_FOR_HDR && z4) {
                  invalidateForScreenShot(displayContent2, true);
                }
                bitmap = screenshot(iBinder, rect, i3, i4, surfaceControl, z2, z3);
                if (CoreRune.FW_SCREENSHOT_FOR_HDR && z4) {
                  this.mHandler.postDelayed(
                      new Runnable() { // from class:
                                       // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                          WmScreenshotController.this.lambda$takeScreenshotToTargetWindow$2(
                              displayContent2);
                        }
                      },
                      500L);
                }
                if (bitmap == null) {
                  this.mReasonForFailure |= 16;
                }
                str = StartingSurfaceController.TAG;
                sb = new StringBuilder();
              }
              iBinder = null;
              if (CoreRune.FW_SCREENSHOT_FOR_HDR) {
                invalidateForScreenShot(displayContent2, true);
              }
              bitmap = screenshot(iBinder, rect, i3, i4, surfaceControl, z2, z3);
              if (CoreRune.FW_SCREENSHOT_FOR_HDR) {
                this.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda0
                      @Override // java.lang.Runnable
                      public final void run() {
                        WmScreenshotController.this.lambda$takeScreenshotToTargetWindow$2(
                            displayContent2);
                      }
                    },
                    500L);
              }
              if (bitmap == null) {}
              str = StartingSurfaceController.TAG;
              sb = new StringBuilder();
            }
            surfaceControl = findTargetSurfaceForAppWindowTarget;
            Log.d(
                StartingSurfaceController.TAG,
                "takeScreenshotToTargetWindow: targetSurface="
                    + surfaceControl
                    + ", sourceCrop="
                    + rect);
            if (!CoreRune.FW_SCREENSHOT_FOR_HDR) {}
            WindowManagerService.resetPriorityAfterLockedSection();
            displayInfo = DisplayManagerGlobal.getInstance().getDisplayInfo(i);
            if (displayInfo != null) {}
            iBinder = null;
            if (CoreRune.FW_SCREENSHOT_FOR_HDR) {}
            bitmap = screenshot(iBinder, rect, i3, i4, surfaceControl, z2, z3);
            if (CoreRune.FW_SCREENSHOT_FOR_HDR) {}
            if (bitmap == null) {}
            str = StartingSurfaceController.TAG;
            sb = new StringBuilder();
          } else {
            WindowManagerService.resetPriorityAfterLockedSection();
            str = StartingSurfaceController.TAG;
            sb = new StringBuilder();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } catch (Throwable unused) {
      str = StartingSurfaceController.TAG;
      sb = new StringBuilder();
    }
    sb.append("takeScreenshotToTargetWindow, bitmap=");
    sb.append(bitmap);
    sb.append(", failedReason=");
    sb.append(failedReasonToString(this.mReasonForFailure));
    sb.append(", caller=");
    sb.append(Debug.getCallers(6));
    Slog.d(str, sb.toString());
    builder
        .setCapturedBitmap(bitmap)
        .setFailedReason(this.mReasonForFailure)
        .setTargetWindowName(sb2.toString())
        .setSecuredWindowName(this.mSecuredWindowName);
    return builder.build();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$takeScreenshotToTargetWindow$2(DisplayContent displayContent) {
    invalidateForScreenShot(displayContent, false);
  }

  public final void invalidateForScreenShot(DisplayContent displayContent, final boolean z) {
    SystemProperties.set("debug.sf.hdr_capture", z ? "true" : "false");
    if (displayContent != null) {
      final boolean[] zArr = {false};
      WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          displayContent.forAllWindows(
              new Consumer() { // from class:
                               // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  WmScreenshotController.this.lambda$invalidateForScreenShot$3(
                      z, zArr, (WindowState) obj);
                }
              },
              true);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      if (zArr[0] && z) {
        try {
          new CountDownLatch(1).await(50L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          Log.d(StartingSurfaceController.TAG, "InterruptedException " + e.getMessage());
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$invalidateForScreenShot$3(
      boolean z, boolean[] zArr, WindowState windowState) {
    if (windowState.isVisible() && isHdrColorMode(windowState)) {
      try {
        Log.i(
            StartingSurfaceController.TAG,
            "invalidateForScreenShot forceMode=" + z + " w=" + windowState.getName());
        windowState.mClient.invalidateForScreenShot(z);
        zArr[0] = true;
      } catch (Exception e) {
        Log.d(StartingSurfaceController.TAG, "Exception " + e.getMessage());
      }
    }
  }

  public final boolean isHdrColorMode(WindowState windowState) {
    int i;
    if (windowState.getAttrs() != null) {
      i = windowState.getAttrs().getColorMode();
      if (i == 2 || i == 3) {
        Log.i(
            StartingSurfaceController.TAG,
            "isHdrColorMode w=" + windowState.getName() + " colorMode=" + i);
      }
    } else {
      i = 0;
    }
    return i == 2 || i == 3;
  }

  public final boolean isScreenshotAllowedByPolicy(DisplayContent displayContent) {
    WindowState secureWindowOnScreen = displayContent.getSecureWindowOnScreen();
    if (secureWindowOnScreen == null) {
      return true;
    }
    if ((secureWindowOnScreen.mAttrs.flags & IInstalld.FLAG_FORCE) != 0) {
      this.mReasonForFailure |= 32;
    } else {
      this.mReasonForFailure |= 64;
    }
    this.mSecuredWindowName = secureWindowOnScreen.getWindowTag().toString();
    return false;
  }

  public final SurfaceControl findTargetSurfaceForSystemWindowTarget(
      DisplayContent displayContent, int i, boolean z, StringBuilder sb) {
    WindowState findVisibleTargetWindowByType = findVisibleTargetWindowByType(displayContent, i, z);
    if (findVisibleTargetWindowByType == null) {
      this.mReasonForFailure |= 2;
      return null;
    }
    sb.append("[Window_Target:" + ((Object) findVisibleTargetWindowByType.getWindowTag()) + "]");
    return findVisibleTargetWindowByType.mSurfaceControl;
  }

  public final WindowState findVisibleTargetWindowByType(
      DisplayContent displayContent, final int i, final boolean z) {
    final boolean[] zArr = {false};
    return displayContent.getWindow(
        new Predicate() { // from class:
                          // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda2
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$findVisibleTargetWindowByType$4;
            lambda$findVisibleTargetWindowByType$4 =
                WmScreenshotController.this.lambda$findVisibleTargetWindowByType$4(
                    zArr, i, z, (WindowState) obj);
            return lambda$findVisibleTargetWindowByType$4;
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$findVisibleTargetWindowByType$4(
      boolean[] zArr, int i, boolean z, WindowState windowState) {
    if (canBeScreenshotTarget(windowState) && windowState.isVisible()) {
      if (zArr[0]) {
        return true;
      }
      if (windowState.mAttrs.type == i) {
        if (z) {
          return true;
        }
        zArr[0] = true;
      }
    }
    return false;
  }

  public final boolean canBeScreenshotTarget(WindowState windowState) {
    return (windowState.mAttrs.privateFlags & 1048576) == 0 && !windowState.hasRelativeLayer();
  }

  public final SurfaceControl findTargetSurfaceForAppWindowTarget(DisplayContent displayContent) {
    if (displayContent.getDefaultTaskDisplayArea() == null
        || displayContent.getDefaultTaskDisplayArea().mSurfaceControl == null) {
      this.mReasonForFailure |= 4;
      return null;
    }
    return displayContent.getDefaultTaskDisplayArea().mSurfaceControl;
  }

  public final Bitmap screenshot(
      IBinder iBinder,
      Rect rect,
      int i,
      int i2,
      SurfaceControl surfaceControl,
      boolean z,
      boolean z2) {
    if (iBinder == null) {
      throw new IllegalArgumentException("displayToken must not be null");
    }
    ScreenCapture.ScreenshotHardwareBuffer captureDisplay =
        ScreenCapture.captureDisplay(
            new ScreenCapture.DisplayCaptureArgs.Builder(iBinder)
                .setUseIdentityTransform(z)
                .setSourceCrop(rect)
                .setSize(i, i2)
                .setLayer(surfaceControl)
                .setCaptureSecureLayers(z2)
                .build());
    Bitmap asBitmap = captureDisplay == null ? null : captureDisplay.asBitmap();
    if (asBitmap == null) {
      Log.e(StartingSurfaceController.TAG, "Failed to take screenshot with sourceCrop");
      return null;
    }
    if (!z2 && captureDisplay.containsSecureLayers()) {
      Log.e(StartingSurfaceController.TAG, "Take screenshot with contains secure layers");
    }
    return asBitmap;
  }

  public final void printScreenshotLog(
      int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) {
    Log.d(
        StartingSurfaceController.TAG,
        "takeScreenshotToTargetWindow: display="
            + i
            + ", target="
            + i2
            + ", containsTarget="
            + z
            + ", crop="
            + rect.toString()
            + ", w="
            + i3
            + ", h="
            + i4
            + ", useIdentityTransform="
            + z2
            + ", ignorePolicy="
            + z3
            + ", caller="
            + Debug.getCallers(3));
  }

  public final String failedReasonToString(int i) {
    StringBuilder sb = new StringBuilder();
    if ((i & 1) != 0) {
      sb.append("InvalidDisplay ");
    }
    if ((i & 2) != 0) {
      sb.append("InvalidSystemWindow ");
    }
    if ((i & 4) != 0) {
      sb.append(" InvalidDefaultTaskDisplayArea ");
    }
    if ((i & 16) != 0) {
      sb.append("EmptyBitmap ");
    }
    if ((i & 32) != 0) {
      sb.append("Secureflags:");
      sb.append(this.mSecuredWindowName);
      sb.append(" ");
    }
    if ((i & 64) != 0) {
      sb.append("Mdm:");
      sb.append(this.mSecuredWindowName);
    }
    return sb.toString();
  }

  public class WmScreenshotShellCommand {
    public DirectoryResolver mDirectoryResolver =
        new DirectoryResolver() { // from class:
                                  // com.android.server.wm.WmScreenshotController$WmScreenshotShellCommand$$ExternalSyntheticLambda0
          @Override // com.android.server.wm.WmScreenshotController.DirectoryResolver
          public final File getSystemDirectoryForUser(int i) {
            return Environment.getDataSystemCeDirectory(i);
          }
        };
    public int mFilename = 0;
    public WindowManagerService mService;

    public WmScreenshotShellCommand(WindowManagerService windowManagerService) {
      this.mService = windowManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0040, code lost:

       if (r1.equals("target_window") == false) goto L8;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void exec(String[] strArr, PrintWriter printWriter) {
      char c = 0;
      String str = strArr[0];
      if (str == null) {
        runDefaultCommands(null, printWriter);
      }
      str.hashCode();
      switch (str.hashCode()) {
        case -1734954402:
          break;
        case 110066619:
          if (str.equals("fullscreen")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 347141453:
          if (str.equals("focused_task")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 1914790441:
          if (str.equals("window_type")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
          runTakeScreenshotTargetWindow(strArr, printWriter);
          break;
        case 1:
          runTakeScreenshotFullScreen(printWriter);
          break;
        case 2:
          runTakeScreenshotFocusedTask(printWriter);
          break;
        case 3:
          getWindowType(printWriter);
          break;
        default:
          runDefaultCommands(str, printWriter);
          break;
      }
    }

    public final void runTakeScreenshotFullScreen(PrintWriter printWriter) {
      try {
        WmScreenshotController.this.sendTakeScreenshotRunnable(1, 0);
      } catch (Exception e) {
        printWriter.println("Error");
        printWriter.println("-------------------");
        printWriter.println("Reason : " + e.getMessage());
      }
    }

    public final void getWindowType(PrintWriter printWriter) {
      try {
        WindowState focusedWindowLocked = this.mService.getFocusedWindowLocked();
        printWriter.println("focusedWindow:" + ((Object) focusedWindowLocked.getWindowTag()));
        printWriter.println("windowType:" + focusedWindowLocked.getWindowType());
      } catch (Exception e) {
        printWriter.println("Error");
        printWriter.println("-------------------");
        printWriter.println("Reason : " + e.getMessage());
      }
    }

    public final void runTakeScreenshotTargetWindow(String[] strArr, PrintWriter printWriter) {
      if (canStringCmdParseToInt(strArr, printWriter)) {
        try {
          DisplayContent displayContent =
              this.mService.mRoot.getDisplayContent(WmScreenshotController.this.mDisplayId);
          if (displayContent == null) {
            printWriter.println("Error : display is null");
            return;
          }
          DisplayMetrics displayMetrics = displayContent.getDisplayMetrics();
          int i = displayMetrics.widthPixels;
          int i2 = displayMetrics.heightPixels;
          WmScreenshotController wmScreenshotController = WmScreenshotController.this;
          ScreenshotResult takeScreenshotToTargetWindow =
              wmScreenshotController.takeScreenshotToTargetWindow(
                  wmScreenshotController.mDisplayId,
                  WmScreenshotController.this.mWindowType,
                  false,
                  new Rect(0, 0, 0, 0),
                  i,
                  i2,
                  true,
                  WmScreenshotController.this.mIgnorePolicy);
          int failedReason = takeScreenshotToTargetWindow.getFailedReason();
          String targetWindowName = takeScreenshotToTargetWindow.getTargetWindowName();
          if (failedReason == 0) {
            printWriter.println("Success screenshot");
            printWriter.println("Window_Name:" + targetWindowName);
            int i3 = this.mFilename;
            if (i3 < 100) {
              this.mFilename = i3 + 1;
            } else {
              setScreenshotEmpty();
            }
            saveBitmapToScreenshotFile(
                this.mFilename + ".jpg",
                takeScreenshotToTargetWindow.getCapturedBitmap(),
                printWriter);
            return;
          }
          printWriter.println("Failed to screenshot");
          printWriter.println(
              "FailedReason:" + WmScreenshotController.this.failedReasonToString(failedReason));
        } catch (Exception e) {
          printWriter.println("Error");
          printWriter.println("-------------------");
          printWriter.println("Reason : " + e.getMessage());
        }
      }
    }

    public final void runTakeScreenshotFocusedTask(PrintWriter printWriter) {
      try {
        DisplayContent topFocusedDisplayContent = this.mService.mRoot.getTopFocusedDisplayContent();
        if (topFocusedDisplayContent == null) {
          printWriter.println("Error : display is null");
          return;
        }
        Task focusedRootTask = topFocusedDisplayContent.getFocusedRootTask();
        if (focusedRootTask != null && focusedRootTask.isVisible()) {
          Bitmap snapshotAsBitmapLocked = focusedRootTask.getSnapshotAsBitmapLocked();
          if (snapshotAsBitmapLocked != null) {
            printWriter.println("Success screenshot, focused task=" + focusedRootTask);
            int i = this.mFilename;
            if (i < 100) {
              this.mFilename = i + 1;
            } else {
              setScreenshotEmpty();
            }
            saveBitmapToScreenshotFile(
                this.mFilename + ".jpg", snapshotAsBitmapLocked, printWriter);
            return;
          }
          printWriter.println("Failed to screenshot");
          return;
        }
        printWriter.println("Error : focused task is null or invisible");
      } catch (Exception e) {
        printWriter.println("Error");
        printWriter.println("-------------------");
        printWriter.println("Reason : " + e.getMessage());
      }
    }

    public final File getDirectory(int i) {
      return new File(this.mDirectoryResolver.getSystemDirectoryForUser(i), "screenshot");
    }

    public final void setScreenshotEmpty() {
      File directory = getDirectory(this.mService.mCurrentUserId);
      if (directory.exists()) {
        directory.delete();
        this.mFilename = 0;
      }
    }

    public final void saveBitmapToScreenshotFile(
        String str, Bitmap bitmap, PrintWriter printWriter) {
      File directory = getDirectory(this.mService.mCurrentUserId);
      if (!directory.exists() && !directory.mkdir()) {
        printWriter.println("Failed create directory");
        printWriter.println("dir:" + directory);
        return;
      }
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(directory, str));
        try {
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
          fileOutputStream.close();
        } finally {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public final void runDefaultCommands(String str, PrintWriter printWriter) {
      if (str == null) {
        printWriter.println("Screenshot commands:");
        printWriter.println("  fullscreen");
        printWriter.println("    Return take screenshot current window of full screen.");
        printWriter.println("  window_type");
        printWriter.println("    Return the current window type.");
        printWriter.println("  target_window");
        printWriter.println("    Return take screenshot of target window and save screenshot.");
        printWriter.println("  focused_task");
        printWriter.println("    Return take screenshot of top focused task and save screenshot.");
        return;
      }
      printWriter.println("Unknown Command: " + str);
    }

    public final boolean canStringCmdParseToInt(String[] strArr, PrintWriter printWriter) {
      WmScreenshotController.this.mDisplayId = 0;
      WmScreenshotController.this.mWindowType = 2015;
      WmScreenshotController.this.mIgnorePolicy = false;
      String str = strArr[1];
      if (str != null) {
        try {
          WmScreenshotController.this.mWindowType = Integer.parseInt(str);
        } catch (NumberFormatException e) {
          printWriter.println("Number_Format_Error");
          printWriter.println("-------------------");
          printWriter.println("Reason : " + e.getMessage());
          return false;
        }
      }
      String str2 = strArr[2];
      if (str2 != null) {
        try {
          WmScreenshotController.this.mDisplayId = Integer.parseInt(str2);
        } catch (NumberFormatException e2) {
          printWriter.println("Number_Format_Error");
          printWriter.println("-------------------");
          printWriter.println("Reason : " + e2.getMessage());
          return false;
        }
      }
      String str3 = strArr[3];
      if (str3 != null) {
        try {
          if (Integer.parseInt(str3) == 1) {
            WmScreenshotController.this.mIgnorePolicy = true;
          }
        } catch (NumberFormatException e3) {
          printWriter.println("Number_Format_Error");
          printWriter.println("-------------------");
          printWriter.println("Reason : " + e3.getMessage());
          return false;
        }
      }
      return true;
    }
  }
}
