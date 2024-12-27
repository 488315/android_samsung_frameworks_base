package com.android.server.wm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.PerfLog;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.ScreenCapture;

import com.android.internal.util.ScreenshotRequest;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class WmScreenshotController {
    public final AnonymousClass3 mCaptureReceiver;
    public final Context mContext;
    public final WmScreenshotFileController mFileController;
    public final AnonymousClass3 mPalmMotionReceiver;
    public int mReasonForFailure;
    public String mSecuredWindowName;
    public final WindowManagerService mService;
    public final WmScreenshotShellCommand mShellCommand;
    public final Rect mTmpRect = new Rect();
    public boolean mIsBlockedBySensitiveContents = false;
    public final Handler mHandler = new Handler(Looper.myLooper());
    public final Object mScreenshotLock = new Object();
    public final HashMap mScreenshotConnections = new HashMap();
    public final TakeScreenshotRunnable mTakeScreenshotRunnable = new TakeScreenshotRunnable();

    public final class TakeScreenshotRunnable implements Runnable {
        public final WmScreenshotInfo info;

        public TakeScreenshotRunnable() {
            WmScreenshotInfo wmScreenshotInfo = new WmScreenshotInfo();
            wmScreenshotInfo.mType = 1;
            wmScreenshotInfo.mSweepDirection = 1;
            wmScreenshotInfo.mDisplayId = 0;
            wmScreenshotInfo.mOrigin = 1;
            wmScreenshotInfo.mBundle = null;
            this.info = wmScreenshotInfo;
        }

        @Override // java.lang.Runnable
        public final void run() {
            final WmScreenshotController wmScreenshotController = WmScreenshotController.this;
            final WmScreenshotInfo wmScreenshotInfo = this.info;
            wmScreenshotController.getClass();
            UserHandle userHandle = UserHandle.CURRENT;
            synchronized (wmScreenshotController.mScreenshotLock) {
                try {
                    if (wmScreenshotController.mScreenshotConnections.size() >= 3) {
                        Log.e(
                                "WindowManager",
                                "Too many screenshot service connection: "
                                        + wmScreenshotController.mScreenshotConnections.size());
                        return;
                    }
                    PersonaActivityHelper personaActivityHelper =
                            wmScreenshotController.mService.mAtmService.mPersonaActivityHelper;
                    if (personaActivityHelper != null) {
                        userHandle = personaActivityHelper.getCurrentScreenUserId(userHandle);
                    }
                    Log.d(
                            "WindowManager",
                            "takeScreenshot: info="
                                    + wmScreenshotInfo
                                    + ", user="
                                    + userHandle.getIdentifier());
                    PerfLog.d(21, (short) 10, "TakeScreenshot");
                    Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName(
                                    Constants.SYSTEMUI_PACKAGE_NAME,
                                    "com.android.systemui.screenshot.TakeScreenshotService"));
                    ServiceConnection serviceConnection = new ServiceConnection() { // from class:
                                // com.android.server.wm.WmScreenshotController.1
                                @Override // android.content.ServiceConnection
                                public final void onServiceConnected(
                                        ComponentName componentName, IBinder iBinder) {
                                    WindowState windowState;
                                    final WmScreenshotController wmScreenshotController2 =
                                            WmScreenshotController.this;
                                    WmScreenshotInfo wmScreenshotInfo2 = wmScreenshotInfo;
                                    synchronized (wmScreenshotController2.mScreenshotLock) {
                                        try {
                                            if (wmScreenshotController2.mScreenshotConnections
                                                    .containsKey(this)) {
                                                Message obtain = Message.obtain();
                                                obtain.what = wmScreenshotInfo2.mType;
                                                obtain.replyTo =
                                                        new Messenger(
                                                                new Handler(
                                                                        wmScreenshotController2
                                                                                .mHandler
                                                                                .getLooper()) { // from class: com.android.server.wm.WmScreenshotController.2
                                                                    @Override // android.os.Handler
                                                                    public final void handleMessage(
                                                                            Message message) {
                                                                        if (message.what != 2) {
                                                                            return;
                                                                        }
                                                                        WmScreenshotController.this
                                                                                .resetConnection(
                                                                                        this, true);
                                                                    }
                                                                });
                                                obtain.obj =
                                                        new ScreenshotRequest.Builder(
                                                                        wmScreenshotInfo2.mType, 5)
                                                                .build();
                                                Bundle bundle = new Bundle();
                                                bundle.putInt(
                                                        "sweepDirection",
                                                        wmScreenshotInfo2.mSweepDirection);
                                                bundle.putInt(
                                                        "capturedDisplay",
                                                        wmScreenshotInfo2.mDisplayId);
                                                bundle.putInt(
                                                        "capturedOrigin",
                                                        wmScreenshotInfo2.mOrigin);
                                                bundle.putBundle(
                                                        "captureSharedBundle",
                                                        wmScreenshotInfo2.mBundle);
                                                WindowManagerGlobalLock windowManagerGlobalLock =
                                                        wmScreenshotController2
                                                                .mService
                                                                .mGlobalLock;
                                                WindowManagerService
                                                        .boostPriorityForLockedSection();
                                                synchronized (windowManagerGlobalLock) {
                                                    try {
                                                        DisplayContent displayContent =
                                                                wmScreenshotController2.mService
                                                                        .mRoot.getDisplayContent(
                                                                        wmScreenshotInfo2
                                                                                .mDisplayId);
                                                        if (displayContent != null) {
                                                            WindowState windowState2 =
                                                                    displayContent
                                                                            .mDisplayPolicy
                                                                            .mStatusBar;
                                                            int i = 0;
                                                            obtain.arg1 =
                                                                    (windowState2 == null
                                                                                    || !windowState2
                                                                                            .isVisible())
                                                                            ? 0
                                                                            : 1;
                                                            DisplayPolicy displayPolicy =
                                                                    displayContent.mDisplayPolicy;
                                                            WindowState windowState3 =
                                                                    displayPolicy.mNavigationBar;
                                                            if ((windowState3 != null
                                                                            && windowState3
                                                                                    .isVisible())
                                                                    || ((windowState =
                                                                                            displayPolicy
                                                                                                    .mExt
                                                                                                    .mTaskbarController
                                                                                                    .mTaskbarWin)
                                                                                    != null
                                                                            && windowState
                                                                                    .mHasSurface
                                                                            && windowState
                                                                                    .isVisible())) {
                                                                i = 1;
                                                            }
                                                            obtain.arg2 = i;
                                                            wmScreenshotController2
                                                                    .putSystemBarHeight(
                                                                            bundle, displayContent);
                                                            WindowState windowState4 =
                                                                    displayContent.mCurrentFocus;
                                                            Task task =
                                                                    windowState4 != null
                                                                            ? windowState4.getTask()
                                                                            : null;
                                                            if (task != null
                                                                    && task.getParent() != null) {
                                                                task.getParent()
                                                                        .getBounds(
                                                                                wmScreenshotController2
                                                                                        .mTmpRect);
                                                                bundle.putParcelable(
                                                                        "stackBounds",
                                                                        wmScreenshotController2
                                                                                .mTmpRect);
                                                            }
                                                            WmScreenshotController
                                                                    .putCutoutSafeInsets(
                                                                            bundle, displayContent);
                                                            if (wmScreenshotInfo2.mType == 100
                                                                    && !wmScreenshotController2
                                                                            .putFocusedWindowInfo(
                                                                                    bundle,
                                                                                    displayContent)) {
                                                                obtain.what = 1;
                                                            }
                                                        } else {
                                                            Slog.e(
                                                                    "WindowManager",
                                                                    "Get screenshot display failed,"
                                                                        + " "
                                                                            + wmScreenshotInfo2
                                                                                    .mDisplayId);
                                                        }
                                                    } catch (Throwable th) {
                                                        WindowManagerService
                                                                .resetPriorityAfterLockedSection();
                                                        throw th;
                                                    }
                                                }
                                                WindowManagerService
                                                        .resetPriorityAfterLockedSection();
                                                obtain.setData(bundle);
                                                try {
                                                    new Messenger(iBinder).send(obtain);
                                                } catch (RemoteException e) {
                                                    Slog.e(
                                                            "WindowManager",
                                                            "Send screenshot message failed, " + e);
                                                }
                                            }
                                        } catch (Throwable th2) {
                                            throw th2;
                                        }
                                    }
                                }

                                @Override // android.content.ServiceConnection
                                public final void onServiceDisconnected(
                                        ComponentName componentName) {}
                            };
                    if (wmScreenshotController.mContext.bindServiceAsUser(
                            intent, serviceConnection, 1, userHandle)) {
                        wmScreenshotController.mScreenshotConnections.put(
                                serviceConnection,
                                new WmScreenshotController$$ExternalSyntheticLambda0(
                                        wmScreenshotController, serviceConnection, 1));
                        wmScreenshotController.mHandler.postDelayed(
                                (Runnable)
                                        wmScreenshotController.mScreenshotConnections.get(
                                                serviceConnection),
                                10000L);
                    }
                } finally {
                }
            }
        }
    }

    public WmScreenshotController(Context context, WindowManagerService windowManagerService) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.wm.WmScreenshotController.3
                    public final /* synthetic */ WmScreenshotController this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:45:0x0124  */
                    /* JADX WARN: Removed duplicated region for block: B:48:0x0129  */
                    /* JADX WARN: Removed duplicated region for block: B:49:0x012c  */
                    /* JADX WARN: Removed duplicated region for block: B:50:0x012f  */
                    /* JADX WARN: Removed duplicated region for block: B:51:0x0132  */
                    /* JADX WARN: Removed duplicated region for block: B:52:0x0135  */
                    /* JADX WARN: Removed duplicated region for block: B:53:0x0138  */
                    @Override // android.content.BroadcastReceiver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onReceive(
                            android.content.Context r11, android.content.Intent r12) {
                        /*
                            Method dump skipped, instructions count: 442
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.wm.WmScreenshotController.AnonymousClass3.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.wm.WmScreenshotController.3
                    public final /* synthetic */ WmScreenshotController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        /*
                            Method dump skipped, instructions count: 442
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.wm.WmScreenshotController.AnonymousClass3.onReceive(android.content.Context,"
                                    + " android.content.Intent):void");
                    }
                };
        this.mContext = context;
        this.mService = windowManagerService;
        WmScreenshotShellCommand wmScreenshotShellCommand = new WmScreenshotShellCommand();
        wmScreenshotShellCommand.mWindowType = 2015;
        wmScreenshotShellCommand.mDisplayId = 0;
        wmScreenshotShellCommand.mScreenshotRotationLayer = false;
        wmScreenshotShellCommand.mService = windowManagerService;
        wmScreenshotShellCommand.mController = this;
        this.mShellCommand = wmScreenshotShellCommand;
        WmScreenshotFileController wmScreenshotFileController = new WmScreenshotFileController();
        wmScreenshotFileController.mService = windowManagerService;
        this.mFileController = wmScreenshotFileController;
        IntentFilter m =
                GmsAlarmManager$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.motion.SWEEP_LEFT",
                        "com.samsung.android.motion.SWEEP_RIGHT",
                        "com.samsung.android.motion.SWEEP_FULL_SCREEN");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(
                broadcastReceiver, userHandle, m, "com.samsung.permission.PALM_MOTION", null, 2);
        context.registerReceiverAsUser(
                broadcastReceiver2,
                userHandle,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.capture.ScreenshotExecutor"),
                "com.samsung.permission.CAPTURE",
                null,
                2);
    }

    public static Rect adjustCropForOneHandOp(DisplayContent displayContent, Rect rect) {
        MagnificationSpec magnificationSpec = displayContent.mMagnificationSpec;
        if (magnificationSpec == null) {
            return rect;
        }
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        if (rect != null && !rect.isEmpty()) {
            rect.scale(magnificationSpec.scale);
            rect.offsetTo(
                    ((int) magnificationSpec.offsetX) + rect.left,
                    ((int) magnificationSpec.offsetY) + rect.top);
            return rect;
        }
        int i = (int) magnificationSpec.offsetX;
        int i2 = (int) magnificationSpec.offsetY;
        float f = displayInfo.logicalWidth;
        float f2 = magnificationSpec.scale;
        return new Rect(i, i2, ((int) (f * f2)) + i, ((int) (displayInfo.logicalHeight * f2)) + i2);
    }

    public static void putCutoutSafeInsets(Bundle bundle, DisplayContent displayContent) {
        ActivityRecord activityRecord;
        WindowState findMainWindow;
        WindowState windowState = displayContent.mCurrentFocus;
        if (windowState == null
                || (activityRecord = windowState.mActivityRecord) == null
                || (findMainWindow = activityRecord.findMainWindow(true)) == null
                || !findMainWindow.isLetterboxedForDisplayCutout()) {
            return;
        }
        DisplayCutout calculateDisplayCutoutForRotation =
                displayContent.calculateDisplayCutoutForRotation(
                        displayContent.mDisplayRotation.mRotation, false);
        if (calculateDisplayCutoutForRotation.isEmpty()) {
            return;
        }
        Rect safeInsets = calculateDisplayCutoutForRotation.getSafeInsets();
        bundle.putInt("safeInsetLeft", safeInsets.left);
        bundle.putInt("safeInsetTop", safeInsets.top);
        bundle.putInt("safeInsetRight", safeInsets.right);
        bundle.putInt("safeInsetBottom", safeInsets.bottom);
    }

    public final String failedReasonToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append(" InvalidDisplay");
        }
        if ((i & 2) != 0) {
            sb.append(" InvalidSystemWindow");
        }
        if ((i & 4) != 0) {
            sb.append(" InvalidDefaultTaskDisplayArea");
        }
        if ((i & 8) != 0) {
            sb.append(" EmptyBitmap");
        }
        if ((i & 16) != 0) {
            sb.append(" Secureflags:");
            sb.append(this.mSecuredWindowName);
        }
        if ((i & 32) != 0) {
            sb.append(" Mdm:");
            sb.append(this.mSecuredWindowName);
        }
        return sb.toString();
    }

    public final SurfaceControl findTargetSurfaceForSystemWindowTarget(
            DisplayContent displayContent, final int i, final boolean z, StringBuilder sb) {
        final boolean[] zArr = {false};
        WindowState window =
                displayContent.getWindow(
                        new Predicate() { // from class:
                                          // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                WmScreenshotController wmScreenshotController =
                                        WmScreenshotController.this;
                                boolean[] zArr2 = zArr;
                                int i2 = i;
                                boolean z2 = z;
                                WindowState windowState = (WindowState) obj;
                                wmScreenshotController.getClass();
                                if ((windowState.mAttrs.privateFlags & 1048576) != 0
                                        || windowState.hasRelativeLayer()) {
                                    return false;
                                }
                                if (!zArr2[0]) {
                                    if (windowState.mAttrs.type != i2) {
                                        return false;
                                    }
                                    if (!z2) {
                                        zArr2[0] = true;
                                        return false;
                                    }
                                }
                                return true;
                            }
                        });
        if (window == null) {
            this.mReasonForFailure |= 2;
            return null;
        }
        sb.append("[Window_Target:" + ((Object) window.getWindowTag()) + "]");
        return window.mSurfaceControl;
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
                                             // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i;
                                    WmScreenshotController wmScreenshotController =
                                            WmScreenshotController.this;
                                    boolean z2 = z;
                                    boolean[] zArr2 = zArr;
                                    WindowState windowState = (WindowState) obj;
                                    wmScreenshotController.getClass();
                                    if (windowState.isVisible()) {
                                        WindowManager.LayoutParams layoutParams =
                                                windowState.mAttrs;
                                        if (layoutParams != null) {
                                            i = layoutParams.getColorMode();
                                            if (i == 2 || i == 3) {
                                                Log.i(
                                                        "WindowManager",
                                                        "isHdrColorMode w="
                                                                + windowState.getName()
                                                                + " colorMode="
                                                                + i);
                                            }
                                        } else {
                                            i = 0;
                                        }
                                        if (i == 2 || i == 3) {
                                            try {
                                                Log.i(
                                                        "WindowManager",
                                                        "invalidateForScreenShot forceMode="
                                                                + z2
                                                                + " w="
                                                                + windowState.getName());
                                                windowState.mClient.invalidateForScreenShot(z2);
                                                zArr2[0] = true;
                                            } catch (Exception e) {
                                                AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0
                                                        .m(
                                                                e,
                                                                new StringBuilder("Exception "),
                                                                "WindowManager");
                                            }
                                        }
                                    }
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
                    Log.d("WindowManager", "InterruptedException " + e.getMessage());
                }
            }
        }
    }

    public final boolean isScreenshotAllowedByPolicy(DisplayContent displayContent) {
        WindowState window =
                displayContent.getWindow(new DisplayContent$$ExternalSyntheticLambda7(6));
        if (window == null) {
            return true;
        }
        if (this.mService.mSensitiveContentPackages.shouldBlockScreenCaptureForApp(
                window.mOwnerUid, window.mClient.asBinder(), window.mAttrs.packageName)) {
            this.mIsBlockedBySensitiveContents = true;
            this.mSecuredWindowName = window.getWindowTag().toString();
            return true;
        }
        if ((window.mAttrs.flags & 8192) != 0) {
            this.mReasonForFailure |= 16;
        } else {
            this.mReasonForFailure |= 32;
        }
        this.mSecuredWindowName = window.getWindowTag().toString();
        return false;
    }

    public final boolean putFocusedWindowInfo(Bundle bundle, DisplayContent displayContent) {
        WindowState windowState = displayContent.mCurrentFocus;
        Task task = windowState != null ? windowState.getTask() : null;
        if (task == null) {
            return false;
        }
        this.mTmpRect.setEmpty();
        final Rect rect = this.mTmpRect;
        task.forAllWindows(
                new Consumer() { // from class:
                                 // com.android.server.wm.WmScreenshotController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Rect rect2 = rect;
                        WindowState windowState2 = (WindowState) obj;
                        if (windowState2.isVisible()) {
                            rect2.union(windowState2.mWindowFrames.mFrame);
                        }
                    }
                },
                true);
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

    public final void putSystemBarHeight(Bundle bundle, DisplayContent displayContent) {
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        this.mTmpRect.set(
                this.mService.mFlags.mInsetsDecoupledConfiguration
                        ? displayContent.mDisplayPolicy.getDecorInsetsInfo(
                                        displayInfo.rotation,
                                        displayInfo.logicalWidth,
                                        displayInfo.logicalHeight)
                                .mOverrideConfigInsets
                        : displayContent.mDisplayPolicy.getDecorInsetsInfo(
                                        displayInfo.rotation,
                                        displayInfo.logicalWidth,
                                        displayInfo.logicalHeight)
                                .mConfigInsets);
        bundle.putInt("statusBarHeight", this.mTmpRect.top);
        int i = displayContent.mDisplayPolicy.mNavigationBarPosition;
        if (i == 1) {
            bundle.putInt("navigationBarHeight", this.mTmpRect.left);
        } else if (i != 2) {
            bundle.putInt("navigationBarHeight", this.mTmpRect.bottom);
        } else {
            bundle.putInt("navigationBarHeight", this.mTmpRect.right);
        }
    }

    public final void resetConnection(ServiceConnection serviceConnection, boolean z) {
        synchronized (this.mScreenshotLock) {
            try {
                if (this.mScreenshotConnections.containsKey(serviceConnection)
                        && serviceConnection != null) {
                    this.mContext.unbindService(serviceConnection);
                    this.mHandler.removeCallbacks(
                            (Runnable) this.mScreenshotConnections.get(serviceConnection));
                    if (z) {
                        this.mScreenshotConnections.remove(serviceConnection);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
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
            Log.e("WindowManager", "Failed to take screenshot with sourceCrop");
            return null;
        }
        if (z2 || !captureDisplay.containsSecureLayers()) {
            return asBitmap;
        }
        if (this.mIsBlockedBySensitiveContents) {
            Log.e("WindowManager", "Failed to take screenshot with contains sensitive contents");
            return asBitmap;
        }
        Log.e("WindowManager", "Failed to take screenshot with contains secure layers");
        this.mReasonForFailure |= 16;
        return null;
    }

    public final void sendTakeScreenshotRunnable(int i, int i2, int i3, int i4, Bundle bundle) {
        Handler handler = this.mHandler;
        TakeScreenshotRunnable takeScreenshotRunnable = this.mTakeScreenshotRunnable;
        handler.removeCallbacks(takeScreenshotRunnable);
        WmScreenshotInfo wmScreenshotInfo = takeScreenshotRunnable.info;
        wmScreenshotInfo.mType = i;
        wmScreenshotInfo.mSweepDirection = i2;
        wmScreenshotInfo.mDisplayId = i3;
        wmScreenshotInfo.mOrigin = i4;
        wmScreenshotInfo.mBundle = bundle;
        handler.post(takeScreenshotRunnable);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0151 A[Catch: all -> 0x015f, TryCatch #2 {all -> 0x015f, blocks: (B:3:0x0066, B:4:0x006d, B:35:0x0144, B:37:0x0151, B:39:0x0156, B:42:0x0170, B:43:0x0173, B:55:0x0162, B:57:0x0166, B:74:0x01c6, B:75:0x01c9, B:6:0x006e, B:8:0x0079, B:9:0x007e, B:16:0x00c9, B:18:0x00cf, B:24:0x00e6, B:27:0x0113, B:29:0x0119, B:31:0x0124, B:34:0x0143, B:64:0x00f2, B:66:0x00f8, B:69:0x0101, B:70:0x0108), top: B:2:0x0066, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x016e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0187 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0198 A[Catch: all -> 0x01ca, TRY_LEAVE, TryCatch #1 {all -> 0x01ca, blocks: (B:47:0x0189, B:52:0x0198), top: B:46:0x0189 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0142  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.view.ScreenshotResult takeScreenshotToTargetWindow(
            int r18,
            int r19,
            boolean r20,
            android.graphics.Rect r21,
            int r22,
            int r23,
            boolean r24,
            boolean r25,
            boolean r26) {
        /*
            Method dump skipped, instructions count: 469
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WmScreenshotController.takeScreenshotToTargetWindow(int,"
                    + " int, boolean, android.graphics.Rect, int, int, boolean, boolean,"
                    + " boolean):com.samsung.android.view.ScreenshotResult");
    }
}
