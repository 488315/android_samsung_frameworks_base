package com.android.server.wm;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Message;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.FastPrintWriter;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.PhoneWindowManagerExt;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class WindowManagerServiceExt {
    public final Context mContext;
    public final List mIgnoreHideNonSystemOverlayWindowApps;
    public final WindowManagerServiceExt$$ExternalSyntheticLambda2
            mLogResumedActivityForHalfOpenMode;
    public final MultiResolutionController mMultiResolutionController;
    public final PhoneWindowManagerExt mPolicyExt;
    public String mSafeModeReason;
    public final WmScreenshotController mScreenshotController;
    public final WindowManagerService mService;
    public final TspStateController mTspStateController;
    public static final String[] SAFE_MODE_REASONS = {
        "KEYCODE_MENU",
        "KEYCODE_S",
        "KEYCODE_DPAD_CENTER",
        "TRACKBALL_BTN_MOUSE",
        "KEYCODE_VOLUME_DOWN"
    };
    public static final String[] SAFE_MODE_PROPERTY_REASONS = {
        "persist.sys.safemode", "ro.sys.safemode", "ro.boot_recovery", "persist.sys.emergency_reset"
    };
    public ExtraDisplayPolicy mExtraDisplayPolicy = new AnonymousClass1();
    public int mLastReportedRotationToAudioManager = -1;
    public ActivityRecord mReportedTaskbarTarget = null;

    /* renamed from: com.android.server.wm.WindowManagerServiceExt$1, reason: invalid class name */
    public final class AnonymousClass1 implements ExtraDisplayPolicy {}

    public WindowManagerServiceExt(Context context, WindowManagerService windowManagerService) {
        new SparseArray();
        new WindowManagerServiceExt$$ExternalSyntheticLambda2(this, 1);
        this.mContext = context;
        this.mService = windowManagerService;
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) windowManagerService.mPolicy;
        phoneWindowManager.getClass();
        PhoneWindowManagerExt phoneWindowManagerExt =
                new PhoneWindowManagerExt(context, phoneWindowManager, this);
        this.mPolicyExt = phoneWindowManagerExt;
        this.mScreenshotController = new WmScreenshotController(context, windowManagerService);
        this.mIgnoreHideNonSystemOverlayWindowApps =
                new ArrayList(Arrays.asList(context.getResources().getStringArray(17236452)));
        this.mMultiResolutionController =
                new MultiResolutionController(context, windowManagerService);
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            TspStateController tspStateController = new TspStateController(context);
            this.mTspStateController = tspStateController;
            phoneWindowManagerExt.mTspStateController = tspStateController;
        }
        if (windowManagerService
                .mContext
                .getPackageManager()
                .hasSystemFeature("com.sec.feature.cover")) {
            WmCoverState.sIsEnabled = true;
            if (WmCoverState.sWmCoverState == null) {
                WmCoverState.sWmCoverState = new WmCoverState();
            }
        }
    }

    public static void logCriticalInfo(String str, List list) {
        File file = new File(Environment.getDataSystemDirectory(), "wmlogs.txt");
        if (file.length() > 5242880) {
            if (file.renameTo(new File(file.getPath() + ".backup"))) {
                file = new File(Environment.getDataSystemDirectory(), "wmlogs.txt");
            }
        }
        try {
            final FastPrintWriter fastPrintWriter =
                    new FastPrintWriter(new FileOutputStream(file, true));
            try {
                fastPrintWriter.print(
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                                .format(new Date(System.currentTimeMillis())));
                fastPrintWriter.print(": ");
                fastPrintWriter.println(str);
                if (list != null && !list.isEmpty()) {
                    list.forEach(
                            new Consumer() { // from class:
                                             // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                                            fastPrintWriter, "  ", (String) obj);
                                }
                            });
                }
                fastPrintWriter.close();
            } finally {
            }
        } catch (FileNotFoundException e) {
            Slog.e("WindowManagerServiceExt", "logCriticalInfo failed FileNotFoundException, " + e);
        }
        FileUtils.setPermissions(file.toString(), 508, -1, -1);
    }

    public final boolean executeShellCommand(
            PrintWriter printWriter, String[] strArr, int i, String str) {
        String[] strArr2 = i < strArr.length ? new String[strArr.length - i] : new String[0];
        System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
        try {
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (PackageFeatureManagerService.LazyHolder.sInstance.executeShellCommand(
                str, strArr2, printWriter)) {
            return true;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Iterator it = ((ArrayList) PackagesChange.sAllPackagesChange).iterator();
                while (it.hasNext()) {
                    ((PackagesChange) it.next()).getClass();
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (CoreRune.MT_APP_COMPAT_STATUS_LOGGING) {
            this.mService.mAtmService.mMultiTaskingAppCompatController
                    .mMultiTaskingAppCompatStatusLogger.getClass();
        }
        return false;
    }

    public final int getFocusedTaskIdWithoutHomeOrRecents() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState focusedWindowLocked = this.mService.getFocusedWindowLocked();
                if (focusedWindowLocked == null
                        || focusedWindowLocked.getTask() == null
                        || focusedWindowLocked.getTask().isActivityTypeHomeOrRecents()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                int i = focusedWindowLocked.getTask().mTaskId;
                WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final List getVisibleWindowInfoList() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent defaultDisplayContentLocked =
                        this.mService.getDefaultDisplayContentLocked();
                if (defaultDisplayContentLocked == null
                        || !defaultDisplayContentLocked.hasAccess(Binder.getCallingUid())) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                defaultDisplayContentLocked.forAllWindows(
                        (Consumer)
                                new DisplayContent$$ExternalSyntheticLambda57(
                                        defaultDisplayContentLocked, arrayList, 1),
                        true);
                WindowManagerService.resetPriorityAfterLockedSection();
                return arrayList;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void moveDisplayToTop(int i) {
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

    public final void sendTakeScreenshotRunnable(int i, int i2) {
        this.mScreenshotController.sendTakeScreenshotRunnable(i, 1, i2, 1, null);
    }

    public final void updateTaskbarTargetIfNeeded(DisplayContent displayContent) {
        if (displayContent == null || !displayContent.isDefaultDisplay) {
            Slog.w(
                    "WindowManagerServiceExt",
                    "updateTaskbarTargetIfNeeded: display is null or not default");
            return;
        }
        final AtomicReference atomicReference = new AtomicReference();
        int i = 1;
        ActivityRecord activity =
                displayContent.getActivity(
                        new Predicate() { // from class:
                                          // com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                AtomicReference atomicReference2 = atomicReference;
                                ActivityRecord activityRecord = (ActivityRecord) obj;
                                if ((!activityRecord.isActivityTypeStandard()
                                                && !activityRecord.isActivityTypeAssistant())
                                        || !activityRecord.isVisibleRequested()) {
                                    return false;
                                }
                                Task task = activityRecord.task;
                                if ((task != null
                                                && task.getWindowConfiguration().tasksAreFloating())
                                        || activityRecord.mPopOverState.mIsActivated) {
                                    return false;
                                }
                                if (atomicReference2.get() == null) {
                                    atomicReference2.set(activityRecord);
                                }
                                return activityRecord.occludesParent(false);
                            }
                        },
                        true);
        if (activity == null) {
            activity = (ActivityRecord) atomicReference.get();
        }
        WindowManagerService windowManagerService = this.mService;
        if (activity == null) {
            if (this.mReportedTaskbarTarget != null) {
                Slog.d("WindowManagerServiceExt", "updateTaskbarTargetIfNeeded: reset");
                TaskChangeNotificationController taskChangeNotificationController =
                        windowManagerService.mAtmService.mTaskChangeNotificationController;
                Message obtainMessage =
                        taskChangeNotificationController.mHandler.obtainMessage(31, 0, 0, null);
                taskChangeNotificationController.forAllLocalListeners(
                        taskChangeNotificationController.mNotifyTaskbarVisibleChanged,
                        obtainMessage);
                obtainMessage.sendToTarget();
                this.mReportedTaskbarTarget = null;
                return;
            }
            return;
        }
        if (this.mReportedTaskbarTarget != activity) {
            ComponentName componentName =
                    new ComponentName(activity.packageName, activity.info.name);
            boolean occludesParent = activity.occludesParent(false);
            boolean inSplitScreenWindowingMode = activity.inSplitScreenWindowingMode();
            StringBuilder sb = new StringBuilder("updateTaskbarTargetIfNeeded: cn=");
            sb.append(componentName);
            sb.append(" occludesParent=");
            sb.append(occludesParent);
            sb.append(" isInSplitScreenMode=");
            sb.append(inSplitScreenWindowingMode);
            sb.append(" styleFloating=");
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "WindowManagerServiceExt", sb, activity.mStyleFloating);
            TaskChangeNotificationController taskChangeNotificationController2 =
                    windowManagerService.mAtmService.mTaskChangeNotificationController;
            if (!occludesParent && activity.mStyleFloating && !inSplitScreenWindowingMode) {
                i = 0;
            }
            Message obtainMessage2 =
                    taskChangeNotificationController2.mHandler.obtainMessage(
                            31, i, 0, componentName);
            taskChangeNotificationController2.forAllLocalListeners(
                    taskChangeNotificationController2.mNotifyTaskbarVisibleChanged, obtainMessage2);
            obtainMessage2.sendToTarget();
            this.mReportedTaskbarTarget = activity;
        }
    }

    public final void updateTspStateControllerWindowPolicyLocked(WindowState windowState) {
        DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
        WindowState windowState2 =
                defaultDisplayContentLocked != null
                        ? defaultDisplayContentLocked.mCurrentFocus
                        : null;
        WindowState windowState3 =
                defaultDisplayContentLocked != null
                        ? defaultDisplayContentLocked.mInputMethodWindow
                        : null;
        TspStateController tspStateController = this.mTspStateController;
        tspStateController.getClass();
        if (windowState != null && windowState != windowState3) {
            if (windowState == windowState2) {
                tspStateController.updateWindowPolicy(windowState2);
            }
        } else if (!tspStateController.mImeWindowVisible
                || tspStateController.mImeTargetWindow != windowState2) {
            if (windowState2 != null) {
                tspStateController.updateWindowPolicy(windowState2);
            }
        } else {
            TspStateController.H h = tspStateController.mH;
            if (h.hasMessages(1)) {
                h.removeMessages(1);
            }
            if (h.hasMessages(2)) {
                h.removeMessages(2);
            }
            h.sendMessage(Message.obtain(h, 2, windowState3));
        }
    }
}
