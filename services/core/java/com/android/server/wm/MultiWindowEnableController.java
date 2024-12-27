package com.android.server.wm;

import android.app.ActivityTaskManager;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;

import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public final class MultiWindowEnableController implements IController {
    public final ActivityTaskManagerService mAtm;
    public CoreStateController mCoreStateController;
    public final WindowManagerGlobalLock mGlobalLock;
    public Handler mH;
    public SharedPreferences mPref;
    public final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final SparseArray mMWOffRequesters = new SparseArray();
    public final SparseArray mMWOffRequestersLog = new SparseArray();
    public final ArrayList mMWOffRequestersForAllUsers = new ArrayList();
    public final ArrayList mMWOffRequestersLogForAllUsers = new ArrayList();
    public final SparseArray mMWForceOnRequesters = new SparseArray();
    public final SparseArray mMWForceOnRequestersLog = new SparseArray();
    public final ArrayList mMWForceOnRequestersForAllUsers = new ArrayList();
    public final ArrayList mMWForceOnRequestersLogForAllUsers = new ArrayList();
    public boolean mDeviceSupportsMultiWindow = false;
    public final ArrayList mMultiStarBlockedMinimizeRequestLog = new ArrayList();
    public final ArrayList mSplitImmersiveModeRequestLog = new ArrayList();
    public final ArrayList mSFRequestLog = new ArrayList();
    public final ArrayList mCDRequestLogs = new ArrayList();
    public final ArrayList mELSRequestLog = new ArrayList();
    public final ArrayList mNaviStarSplitImmersiveModeRequestLog = new ArrayList();
    public final ArrayList mCornerGestureRequestLogs = new ArrayList();

    public final class TransitionListener extends WindowManagerInternal.AppTransitionListener {
        public final List mExitAnimatingTasks;
        public final TransitionController mTransitionController;

        public TransitionListener(List list, TransitionController transitionController) {
            ArrayList arrayList = new ArrayList();
            this.mExitAnimatingTasks = arrayList;
            arrayList.addAll(list);
            this.mTransitionController = transitionController;
        }

        public final void handleExitAnimatingTasks(String str) {
            Iterator it = ((ArrayList) this.mExitAnimatingTasks).iterator();
            while (it.hasNext()) {
                Task task = (Task) it.next();
                task.setWindowingMode(1);
                Slog.d(
                        "MultiWindowEnableController",
                        "handleExitAnimatingTasks: #" + task.mTaskId + ", reason=" + str);
            }
            ((ArrayList) this.mExitAnimatingTasks).clear();
            this.mTransitionController.mLegacyListeners.remove(this);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionCancelledLocked(boolean z) {
            handleExitAnimatingTasks("Cancelled");
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionFinishedLocked(IBinder iBinder) {
            handleExitAnimatingTasks("Finished");
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionTimeoutLocked() {
            handleExitAnimatingTasks("Timeout");
        }
    }

    public MultiWindowEnableController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public static void dumpMWFeatureLocked(
            PrintWriter printWriter, ArrayList arrayList, String[] strArr) {
        try {
            printWriter.println(
                    "    "
                            + strArr[0]
                            + " = "
                            + MultiWindowCoreState.class
                                    .getField(strArr[0])
                                    .getBoolean(MultiWindowCoreState.class));
            if (arrayList.size() > 0) {
                dumpMWRequesterLocked(printWriter, arrayList, strArr[1] + " : ");
            }
        } catch (Throwable unused) {
        }
    }

    public static void dumpMWRequesterLocked(
            PrintWriter printWriter, ArrayList arrayList, String str) {
        int size = arrayList.size();
        if (size > 0) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m("      ", str);
            for (int i = 0; i < size; i++) {
                m.append((String) arrayList.get(i));
                if (i < size - 1) {
                    m.append(" - ");
                }
            }
            printWriter.println(m);
        }
    }

    public final void dismissMultiWindowMode() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean z = false;
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(0);
                if (displayContent == null) {
                    Slog.w(
                            "MultiWindowEnableController",
                            "dismissMultiWindowMode: cannot found displayContent #0");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
                if (defaultTaskDisplayArea == null) {
                    Slog.w(
                            "MultiWindowEnableController",
                            "dismissMultiWindowMode: cannot found tda, for display #0");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                ArrayList arrayList = new ArrayList();
                displayContent.forAllRootTasks(
                        new DisplayContent$$ExternalSyntheticLambda8(arrayList, z));
                int size = arrayList.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    Task task = (Task) arrayList.get(size);
                    if (this.mAtm.mLockTaskController.isTaskLocked(task)) {
                        Slog.d(
                                "MultiWindowEnableController",
                                "dismissMultiWindowMode: locked freeform, #" + task.mTaskId);
                        arrayList.remove(task);
                        task.setWindowingMode(1);
                        break;
                    }
                    size--;
                }
                if (!arrayList.isEmpty()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Task task2 = (Task) it.next();
                        task2.moveTaskToBack(task2, null);
                        Slog.d(
                                "MultiWindowEnableController",
                                "dismissMultiWindowMode: freeform to back, #" + task2.mTaskId);
                    }
                    TransitionController transitionController =
                            this.mAtm.mWindowOrganizerController.mTransitionController;
                    transitionController.registerLegacyListener(
                            new TransitionListener(arrayList, transitionController));
                }
                Task task3 = defaultTaskDisplayArea.mRootPinnedTask;
                if (task3 != null) {
                    Slog.d(
                            "MultiWindowEnableController",
                            "dismissMultiWindowMode: remove pip, #" + task3.mTaskId);
                    this.mAtm.mTaskSupervisor.removeRootTask(task3);
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[MultiWindowEnableController]");
        for (int i = 0; i < this.mMWOffRequesters.size(); i++) {
            dumpMWRequesterLocked(
                    printWriter,
                    (ArrayList) this.mMWOffRequesters.valueAt(i),
                    "MWOffRequester[u" + this.mMWOffRequesters.keyAt(i) + "] : ");
        }
        for (int i2 = 0; i2 < this.mMWOffRequestersLog.size(); i2++) {
            dumpMWRequesterLocked(
                    printWriter,
                    (ArrayList) this.mMWOffRequestersLog.valueAt(i2),
                    "MWOffRequesterLog[u" + this.mMWOffRequestersLog.keyAt(i2) + "] : ");
        }
        if (this.mMWOffRequestersForAllUsers.size() > 0) {
            dumpMWRequesterLocked(
                    printWriter, this.mMWOffRequestersForAllUsers, "MWOffRequestersForAllUsers : ");
        }
        if (this.mMWOffRequestersLogForAllUsers.size() > 0) {
            dumpMWRequesterLocked(
                    printWriter,
                    this.mMWOffRequestersLogForAllUsers,
                    "MWOffRequestersLogForAllUsers : ");
        }
        for (int i3 = 0; i3 < this.mMWForceOnRequesters.size(); i3++) {
            dumpMWRequesterLocked(
                    printWriter,
                    (ArrayList) this.mMWForceOnRequesters.valueAt(i3),
                    "MWForceOnRequester[u" + this.mMWForceOnRequesters.keyAt(i3) + "] : ");
        }
        for (int i4 = 0; i4 < this.mMWForceOnRequestersLog.size(); i4++) {
            dumpMWRequesterLocked(
                    printWriter,
                    (ArrayList) this.mMWForceOnRequestersLog.valueAt(i4),
                    "MWForceOnRequesterLog[u" + this.mMWForceOnRequestersLog.keyAt(i4) + "] : ");
        }
        if (this.mMWForceOnRequestersForAllUsers.size() > 0) {
            dumpMWRequesterLocked(
                    printWriter,
                    this.mMWForceOnRequestersForAllUsers,
                    "MWForceOnRequestersForAllUsers : ");
        }
        if (this.mMWForceOnRequestersLogForAllUsers.size() > 0) {
            dumpMWRequesterLocked(
                    printWriter,
                    this.mMWForceOnRequestersLogForAllUsers,
                    "MWForceOnRequestersLogForAllUsers : ");
        }
        dumpMWFeatureLocked(
                printWriter,
                this.mMultiStarBlockedMinimizeRequestLog,
                new String[] {
                    "MW_MULTISTAR_BLOCKED_MINIMIZE_FREEFORM", "mMultiStarBlockedMinimizeRequestLog"
                });
        ArrayList arrayList = this.mCDRequestLogs;
        String[] strArr = {"MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED", "mCDRequestLog"};
        try {
            printWriter.println(
                    "    "
                            + strArr[0]
                            + " = "
                            + MultiWindowCoreState.class
                                    .getField(strArr[0])
                                    .getInt(MultiWindowCoreState.class));
            if (arrayList.size() > 0) {
                dumpMWRequesterLocked(printWriter, arrayList, strArr[1] + " : ");
            }
        } catch (Throwable unused) {
        }
        dumpMWFeatureLocked(
                printWriter,
                this.mELSRequestLog,
                new String[] {"MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED", "mELSRequestLog"});
        dumpMWFeatureLocked(
                printWriter,
                this.mCornerGestureRequestLogs,
                new String[] {"MW_FREEFORM_CORNER_GESTURE_ENABLED", "mCornerGestureRequestLogs"});
        dumpMWFeatureLocked(
                printWriter,
                this.mSplitImmersiveModeRequestLog,
                new String[] {"MW_SPLIT_IMMERSIVE_MODE_ENABLED", "mSplitImmersiveModeRequestLog"});
        dumpMWFeatureLocked(
                printWriter,
                this.mNaviStarSplitImmersiveModeRequestLog,
                new String[] {
                    "MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED",
                    "mNaviStarSplitImmersiveModeRequestLog"
                });
        dumpMWFeatureLocked(
                printWriter,
                this.mSFRequestLog,
                new String[] {"MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED", "mSFRequestLog"});
        printWriter.println("  " + MultiWindowCoreState.getInstance());
        printWriter.println();
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new Handler(this.mAtm.mH.getLooper());
        CoreStateController coreStateController = this.mAtm.mExt.mCoreStateController;
        this.mCoreStateController = coreStateController;
        synchronized (coreStateController.mCallbacks) {
            coreStateController.mCallbacks.add(this);
        }
    }

    public final boolean isMultiWindowForceOnRequested(int i) {
        return this.mMWForceOnRequestersForAllUsers.size() > 0
                || (this.mMWForceOnRequesters.get(i) != null
                        && ((ArrayList) this.mMWForceOnRequesters.get(i)).size() > 0);
    }

    public final boolean isMultiWindowOffRequested(int i) {
        return this.mMWOffRequestersForAllUsers.size() > 0
                || (this.mMWOffRequesters.get(i) != null
                        && ((ArrayList) this.mMWOffRequesters.get(i)).size() > 0);
    }

    public final void onCoreStateChanged(int i) {
        if ((i & 1) != 0) {
            if (this.mAtm.mAmInternal.isSystemReady()) {
                if (!MultiWindowCoreState.MW_ENABLED) {
                    dismissMultiWindowMode();
                }
                ContentResolver contentResolver = this.mAtm.mContext.getContentResolver();
                boolean z =
                        this.mAtm
                                        .mContext
                                        .getPackageManager()
                                        .hasSystemFeature(
                                                "android.software.freeform_window_management")
                                || Settings.Global.getInt(
                                                contentResolver, "enable_freeform_support", 0)
                                        != 0;
                boolean supportsMultiWindow =
                        ActivityTaskManager.supportsMultiWindow(this.mAtm.mContext);
                boolean z2 =
                        supportsMultiWindow
                                && this.mAtm
                                        .mContext
                                        .getPackageManager()
                                        .hasSystemFeature("android.software.picture_in_picture");
                boolean supportsSplitScreenMultiWindow =
                        ActivityTaskManager.supportsSplitScreenMultiWindow(this.mAtm.mContext);
                boolean hasSystemFeature =
                        this.mAtm
                                .mContext
                                .getPackageManager()
                                .hasSystemFeature(
                                        "android.software.activities_on_secondary_displays");
                boolean z3 =
                        Settings.Global.getInt(contentResolver, "force_resizable_activities", 0)
                                != 0;
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    boolean z4 = z || supportsSplitScreenMultiWindow || z2 || hasSystemFeature;
                    try {
                        if (supportsMultiWindow && z4) {
                            ActivityTaskManagerService activityTaskManagerService = this.mAtm;
                            activityTaskManagerService.mSupportsMultiWindow = true;
                            activityTaskManagerService.mSupportsFreeformWindowManagement = z;
                            activityTaskManagerService.mSupportsPictureInPicture = z2;
                            activityTaskManagerService.mSupportsMultiDisplay = hasSystemFeature;
                        } else {
                            ActivityTaskManagerService activityTaskManagerService2 = this.mAtm;
                            activityTaskManagerService2.mSupportsMultiWindow = false;
                            activityTaskManagerService2.mSupportsFreeformWindowManagement = false;
                            activityTaskManagerService2.mSupportsPictureInPicture = false;
                            activityTaskManagerService2.mSupportsMultiDisplay = hasSystemFeature;
                        }
                        ActivityTaskManagerService activityTaskManagerService3 = this.mAtm;
                        if (z3 != activityTaskManagerService3.mForceResizableActivities) {
                            activityTaskManagerService3.mForceResizableActivities = z3;
                            activityTaskManagerService3.mMwSupportPolicyController
                                    .updateAllTasksLocked();
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            if (!MultiWindowCoreState.MW_ENABLED) {
                FreeformController freeformController = this.mAtm.mFreeformController;
                freeformController.scheduleUnbindMinimizeContainerService("mw_off");
                if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                    Message obtainMessage = freeformController.mH.obtainMessage(104);
                    obtainMessage.obj = "mw_off";
                    freeformController.mH.sendMessage(obtainMessage);
                    return;
                }
                return;
            }
            FreeformController freeformController2 = this.mAtm.mFreeformController;
            Message obtainMessage2 = freeformController2.mH.obtainMessage(101);
            obtainMessage2.obj = "mw_on";
            freeformController2.mH.sendMessage(obtainMessage2);
            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                Message obtainMessage3 = freeformController2.mH.obtainMessage(103);
                obtainMessage3.obj = "mw_on";
                freeformController2.mH.sendMessage(obtainMessage3);
            }
        }
    }

    public final void setBlockedMinimizeFreeformEnabled(boolean z) {
        ArrayList arrayList = this.mMultiStarBlockedMinimizeRequestLog;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("(", ", ", z);
        m.append(this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        arrayList.add(m.toString());
        if (this.mMultiStarBlockedMinimizeRequestLog.size() > 50) {
            this.mMultiStarBlockedMinimizeRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState(
                    "mw_blocked_minimized_freeform",
                    Integer.valueOf(z ? 1 : 0),
                    ((Integer) it.next()).intValue(),
                    true,
                    true,
                    null);
        }
    }

    public final void setCornerGestureEnabled(boolean z) {
        ArrayList arrayList = this.mCornerGestureRequestLogs;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("(", ", ", z);
        m.append(this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        arrayList.add(m.toString());
        if (this.mCornerGestureRequestLogs.size() > 50) {
            this.mCornerGestureRequestLogs.remove(0);
        }
        if (this.mPref == null) {
            this.mPref = this.mAtm.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        SharedPreferences.Editor edit = this.mPref.edit();
        edit.putInt("open_in_pop_up_view", z ? 1 : 0);
        edit.apply();
        this.mCoreStateController.setSharedPreferenceEdited();
    }

    public final void setEnableForUser(int i, String str, String str2, boolean z) {
        ArrayList arrayList =
                i == -1
                        ? this.mMWOffRequestersForAllUsers
                        : (ArrayList) this.mMWOffRequesters.get(i);
        ArrayList arrayList2 =
                i == -1
                        ? this.mMWOffRequestersLogForAllUsers
                        : (ArrayList) this.mMWOffRequestersLog.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mMWOffRequesters.put(i, arrayList);
        }
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
            this.mMWOffRequestersLog.put(i, arrayList2);
        }
        arrayList2.add(
                str
                        + "("
                        + z
                        + ", "
                        + str2
                        + ", "
                        + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis()))
                        + ")");
        if (arrayList2.size() > 100) {
            arrayList2.remove(0);
        }
        if (z) {
            arrayList.remove(str);
            if (arrayList.isEmpty()) {
                updateEnableLocked(i, str, true);
            }
        } else if (!arrayList.contains(str)) {
            arrayList.add(str);
            updateEnableLocked(i, str, false);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "updateMultiWindowSetting prev requester : ",
                    (String) it.next(),
                    "MultiWindowEnableController");
        }
    }

    public final void setEnsureLaunchSplitEnabled(boolean z) {
        ArrayList arrayList = this.mELSRequestLog;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("(", ", ", z);
        m.append(this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        arrayList.add(m.toString());
        if (this.mELSRequestLog.size() > 50) {
            this.mELSRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState(
                    "mw_ensure_launch_split",
                    Integer.valueOf(z ? 1 : 0),
                    ((Integer) it.next()).intValue(),
                    true,
                    true,
                    null);
        }
    }

    public final void setForceEnableForUser(String str, boolean z) {
        ArrayList arrayList = this.mMWForceOnRequestersForAllUsers;
        ArrayList arrayList2 = this.mMWForceOnRequestersLogForAllUsers;
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mMWForceOnRequesters.put(-1, arrayList);
        }
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
            this.mMWForceOnRequestersLog.put(-1, arrayList2);
        }
        arrayList2.add(
                "DexController("
                        + z
                        + ", "
                        + str
                        + ", "
                        + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis()))
                        + ")");
        if (arrayList2.size() > 100) {
            arrayList2.remove(0);
        }
        if (!z) {
            arrayList.remove("DexController");
            if (arrayList.isEmpty()) {
                updateEnableLocked(-1, "DexController", false);
            }
        } else if (!arrayList.contains("DexController")) {
            arrayList.add("DexController");
            updateEnableLocked(-1, "DexController", true);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "updateMultiWindowSetting prev requester : ",
                    (String) it.next(),
                    "MultiWindowEnableController");
        }
    }

    public final void setMultiWindowDynamicEnabled(
            int i, String str, boolean z, boolean z2, boolean z3) {
        boolean z4;
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (activityTaskManagerService.mWindowManager.mCurrentUserId == i) {
            DisplayContent displayContent =
                    activityTaskManagerService.mRootWindowContainer.getDisplayContent(0);
            final TaskDisplayArea defaultTaskDisplayArea =
                    displayContent != null ? displayContent.getDefaultTaskDisplayArea() : null;
            if (defaultTaskDisplayArea != null) {
                final int indexOf =
                        defaultTaskDisplayArea.mChildren.indexOf(
                                defaultTaskDisplayArea.mRootHomeTask);
                if (defaultTaskDisplayArea.getRootTask(
                                        new Predicate() { // from class:
                                                          // com.android.server.wm.MultiWindowEnableController$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                Task task = (Task) obj;
                                                return task.inFreeformWindowingMode()
                                                        && defaultTaskDisplayArea.mChildren.indexOf(
                                                                        task)
                                                                > indexOf;
                                            }
                                        })
                                != null
                        || defaultTaskDisplayArea.isSplitScreenModeActivated()
                        || defaultTaskDisplayArea.hasPinnedTask()) {
                    z4 = true;
                    this.mCoreStateController.setVolatileState(
                            "mw_enabled",
                            Integer.valueOf(z ? 1 : 0),
                            i,
                            z2,
                            z3,
                            new MultiWindowEnableController$$ExternalSyntheticLambda0(
                                    this, str, z, i, z4, 0));
                }
            }
        }
        z4 = false;
        this.mCoreStateController.setVolatileState(
                "mw_enabled",
                Integer.valueOf(z ? 1 : 0),
                i,
                z2,
                z3,
                new MultiWindowEnableController$$ExternalSyntheticLambda0(this, str, z, i, z4, 0));
    }

    public final void setNaviStarImmersiveSplitModeLocked(boolean z) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("(", ", ", z);
        m.append(this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        this.mNaviStarSplitImmersiveModeRequestLog.add(m.toString());
        if (this.mNaviStarSplitImmersiveModeRequestLog.size() > 20) {
            this.mNaviStarSplitImmersiveModeRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState(
                    "mw_navibar_immersive_mode",
                    Integer.valueOf(z ? 1 : 0),
                    ((Integer) it.next()).intValue(),
                    true,
                    true,
                    null);
        }
    }

    public final void setSplitImmersiveModeLocked(boolean z) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("(", ", ", z);
        m.append(this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        this.mSplitImmersiveModeRequestLog.add(m.toString());
        if (this.mSplitImmersiveModeRequestLog.size() > 20) {
            this.mSplitImmersiveModeRequestLog.remove(0);
        }
        if (this.mPref == null) {
            this.mPref = this.mAtm.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        SharedPreferences.Editor edit = this.mPref.edit();
        edit.putInt("mw_immersive_mode", z ? 1 : 0);
        edit.apply();
        this.mCoreStateController.setSharedPreferenceEdited();
    }

    public final void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) {
        ArrayList arrayList = this.mSFRequestLog;
        StringBuilder m =
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "(", z, ", ", z2, ", ");
        m.append(this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        m.append(")");
        arrayList.add(m.toString());
        if (this.mSFRequestLog.size() > 50) {
            this.mSFRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            this.mCoreStateController.setVolatileState(
                    "stay_focus_activity", Integer.valueOf(z ? 1 : 0), intValue, true, true, null);
            this.mCoreStateController.setVolatileState(
                    "stay_top_resumed_activity",
                    Integer.valueOf(z2 ? 1 : 0),
                    intValue,
                    true,
                    true,
                    null);
        }
    }

    public final void updateEnableLocked(int i, String str, boolean z) {
        ArrayList startedUserIdsLocked = this.mAtm.mExt.getStartedUserIdsLocked();
        if (z) {
            if (i != -1) {
                boolean isMultiWindowForceOnRequested = isMultiWindowForceOnRequested(i);
                if (!isMultiWindowOffRequested(i) || isMultiWindowForceOnRequested) {
                    setMultiWindowDynamicEnabled(i, str, true, true, isMultiWindowForceOnRequested);
                    Slog.d(
                            "MultiWindowEnableController",
                            "turn on MW[#" + i + "], Requester : " + str);
                    return;
                }
                return;
            }
            Iterator it = startedUserIdsLocked.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                boolean isMultiWindowForceOnRequested2 = isMultiWindowForceOnRequested(intValue);
                if (!isMultiWindowOffRequested(intValue) || isMultiWindowForceOnRequested2) {
                    setMultiWindowDynamicEnabled(
                            intValue, str, true, true, isMultiWindowForceOnRequested2);
                    Slog.d(
                            "MultiWindowEnableController",
                            "turn on MW[#" + intValue + "], Requester : " + str);
                }
            }
            return;
        }
        if (i != -1) {
            if (isMultiWindowForceOnRequested(i)) {
                Slog.d(
                        "MultiWindowEnableController",
                        "force on now, turn off failed, MW[#" + i + "], Requester : " + str);
                return;
            }
            if (isMultiWindowOffRequested(i)) {
                setMultiWindowDynamicEnabled(i, str, false, true, false);
                Slog.d(
                        "MultiWindowEnableController",
                        "turn off MW[#" + i + "], Requester : " + str);
                return;
            }
            return;
        }
        Iterator it2 = startedUserIdsLocked.iterator();
        while (it2.hasNext()) {
            int intValue2 = ((Integer) it2.next()).intValue();
            if (isMultiWindowForceOnRequested(intValue2)) {
                Slog.d(
                        "MultiWindowEnableController",
                        "force on now, turn off failed, MW[#" + i + "], Requester : " + str);
            } else if (isMultiWindowOffRequested(intValue2)) {
                setMultiWindowDynamicEnabled(intValue2, str, false, true, false);
                Slog.d(
                        "MultiWindowEnableController",
                        "turn off MW[#" + intValue2 + "], Requester : " + str);
            }
        }
    }
}
