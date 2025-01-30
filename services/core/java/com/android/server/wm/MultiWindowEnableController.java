package com.android.server.wm;

import android.app.ActivityTaskManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.server.corestate.CoreStateCallback;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class MultiWindowEnableController implements IController, CoreStateCallback {
    public static final String TAG = "MultiWindowEnableController";
    public final ActivityTaskManagerService mAtm;
    public CoreStateController mCoreStateController;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public Handler f1744mH;
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
    public final List mExitAnimatingTasks = new ArrayList();
    public final ArrayList mCornerGestureRequestLogs = new ArrayList();
    public final ArrayList mMultiStarBlockedMinimizeRequestLog = new ArrayList();
    public final ArrayList mSFRequestLog = new ArrayList();
    public final ArrayList mCDRequestLogs = new ArrayList();
    public final ArrayList mELSRequestLog = new ArrayList();
    public final ArrayList mSplitImmersiveModeRequestLog = new ArrayList();
    public final ArrayList mNaviStarSplitImmersiveModeRequestLog = new ArrayList();

    public MultiWindowEnableController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.f1744mH = new Handler(this.mAtm.f1734mH.getLooper());
        CoreStateController coreStateController = this.mAtm.mExt.getCoreStateController();
        this.mCoreStateController = coreStateController;
        coreStateController.registerCallbackLocked(this);
    }

    @Override // com.samsung.android.server.corestate.CoreStateCallback
    public void onCoreStateChanged(int i) {
        if ((i & 1) != 0) {
            if (this.mAtm.mAmInternal.isSystemReady()) {
                retrieveMultiWindowSettings();
            }
            if (MultiWindowCoreState.MW_ENABLED) {
                this.mAtm.mFreeformController.bindFreeformContainerService("mw_on");
            } else {
                this.mAtm.mFreeformController.unbindFreeformContainerService("mw_off");
            }
        }
    }

    public void dismissMultiWindowMode(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(TAG, "dismissMultiWindowMode: cannot found displayContent #" + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
                if (defaultTaskDisplayArea == null) {
                    Slog.w(TAG, "dismissMultiWindowMode: cannot found tda, for display #" + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                List<Task> rootTasks = displayContent.getRootTasks(5, 1);
                if (!rootTasks.isEmpty()) {
                    for (Task task : rootTasks) {
                        task.moveTaskToBack(task, null);
                        Slog.d(TAG, "dismissMultiWindowMode: freeform to back, #" + task.mTaskId);
                    }
                    TransitionController transitionController = this.mAtm.getTransitionController();
                    transitionController.registerLegacyListener(new TransitionListener(rootTasks, transitionController));
                }
                Task rootPinnedTask = defaultTaskDisplayArea.getRootPinnedTask();
                if (rootPinnedTask != null) {
                    Slog.d(TAG, "dismissMultiWindowMode: remove pip, #" + rootPinnedTask.mTaskId);
                    this.mAtm.mTaskSupervisor.removeRootTask(rootPinnedTask);
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void updateDeviceSupportsMultiWindow() {
        this.mDeviceSupportsMultiWindow = ActivityTaskManager.deviceSupportsMultiWindow(this.mAtm.mContext);
        Slog.d(TAG, "updateDeviceSupportsMultiWindow: support=" + this.mDeviceSupportsMultiWindow);
    }

    public boolean deviceSupportsMultiWindow() {
        return this.mDeviceSupportsMultiWindow;
    }

    public final void retrieveMultiWindowSettings() {
        ContentResolver contentResolver = this.mAtm.mContext.getContentResolver();
        boolean z = this.mAtm.mContext.getPackageManager().hasSystemFeature("android.software.freeform_window_management") || Settings.Global.getInt(contentResolver, "enable_freeform_support", 0) != 0;
        boolean supportsMultiWindow = ActivityTaskManager.supportsMultiWindow(this.mAtm.mContext);
        boolean z2 = supportsMultiWindow && this.mAtm.mContext.getPackageManager().hasSystemFeature("android.software.picture_in_picture");
        boolean supportsSplitScreenMultiWindow = ActivityTaskManager.supportsSplitScreenMultiWindow(this.mAtm.mContext);
        boolean hasSystemFeature = this.mAtm.mContext.getPackageManager().hasSystemFeature("android.software.activities_on_secondary_displays");
        boolean z3 = Settings.Global.getInt(contentResolver, "force_resizable_activities", 0) != 0;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            boolean z4 = z || supportsSplitScreenMultiWindow || z2 || hasSystemFeature;
            try {
                if (supportsMultiWindow && z4) {
                    ActivityTaskManagerService activityTaskManagerService = this.mAtm;
                    activityTaskManagerService.mSupportsMultiWindow = true;
                    activityTaskManagerService.mSupportsFreeformWindowManagement = z;
                    activityTaskManagerService.mSupportsSplitScreenMultiWindow = supportsSplitScreenMultiWindow;
                    activityTaskManagerService.mSupportsPictureInPicture = z2;
                    activityTaskManagerService.mSupportsMultiDisplay = hasSystemFeature;
                } else {
                    ActivityTaskManagerService activityTaskManagerService2 = this.mAtm;
                    activityTaskManagerService2.mSupportsMultiWindow = false;
                    activityTaskManagerService2.mSupportsFreeformWindowManagement = false;
                    activityTaskManagerService2.mSupportsSplitScreenMultiWindow = false;
                    activityTaskManagerService2.mSupportsPictureInPicture = false;
                    activityTaskManagerService2.mSupportsMultiDisplay = hasSystemFeature;
                }
                ActivityTaskManagerService activityTaskManagerService3 = this.mAtm;
                if (z3 != activityTaskManagerService3.mForceResizableActivities) {
                    activityTaskManagerService3.mForceResizableActivities = z3;
                    activityTaskManagerService3.mMwSupportPolicyController.updateAllTasksLocked();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public List getMWDisableRequestersLocked() {
        ArrayList arrayList = (ArrayList) this.mMWOffRequesters.get(this.mAtm.mWindowManager.mCurrentUserId);
        return arrayList != null ? arrayList : Collections.EMPTY_LIST;
    }

    public void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                setEnableForUser(str, str2, z, i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setMultiWindowForceEnabledForUser(String str, String str2, boolean z, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                setForceEnableForUser(str, str2, z, i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setEnableForUser(String str, String str2, boolean z, int i) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (i == -1) {
            arrayList = this.mMWOffRequestersForAllUsers;
        } else {
            arrayList = (ArrayList) this.mMWOffRequesters.get(i);
        }
        if (i == -1) {
            arrayList2 = this.mMWOffRequestersLogForAllUsers;
        } else {
            arrayList2 = (ArrayList) this.mMWOffRequestersLog.get(i);
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mMWOffRequesters.put(i, arrayList);
        }
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
            this.mMWOffRequestersLog.put(i, arrayList2);
        }
        arrayList2.add(str + "(" + z + ", " + str2 + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (arrayList2.size() > 100) {
            arrayList2.remove(0);
        }
        if (z) {
            arrayList.remove(str);
            if (arrayList.isEmpty()) {
                updateEnableLocked(str, true, i);
            }
        } else if (!arrayList.contains(str)) {
            arrayList.add(str);
            updateEnableLocked(str, false, i);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            Slog.d(TAG, "updateMultiWindowSetting prev requester : " + str3);
        }
    }

    public void setForceEnableForUser(String str, String str2, boolean z, int i) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (i == -1) {
            arrayList = this.mMWForceOnRequestersForAllUsers;
        } else {
            arrayList = (ArrayList) this.mMWForceOnRequesters.get(i);
        }
        if (i == -1) {
            arrayList2 = this.mMWForceOnRequestersLogForAllUsers;
        } else {
            arrayList2 = (ArrayList) this.mMWForceOnRequestersLog.get(i);
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mMWForceOnRequesters.put(i, arrayList);
        }
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
            this.mMWForceOnRequestersLog.put(i, arrayList2);
        }
        arrayList2.add(str + "(" + z + ", " + str2 + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (arrayList2.size() > 100) {
            arrayList2.remove(0);
        }
        if (z) {
            if (!arrayList.contains(str)) {
                arrayList.add(str);
                updateEnableLocked(str, true, i);
            }
        } else {
            arrayList.remove(str);
            if (arrayList.isEmpty()) {
                updateEnableLocked(str, false, i);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            Slog.d(TAG, "updateMultiWindowSetting prev requester : " + str3);
        }
    }

    public final void updateEnableLocked(String str, boolean z, int i) {
        ArrayList startedUserIdsLocked = this.mAtm.mExt.getStartedUserIdsLocked();
        if (z) {
            if (i == -1) {
                Iterator it = startedUserIdsLocked.iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    boolean isMultiWindowForceOnRequested = isMultiWindowForceOnRequested(intValue);
                    if (!isMultiWindowOffRequested(intValue) || isMultiWindowForceOnRequested) {
                        setMultiWindowDynamicEnabled(str, true, intValue, true, isMultiWindowForceOnRequested);
                        Slog.d(TAG, "turn on MW[#" + intValue + "], Requester : " + str);
                    }
                }
                return;
            }
            boolean isMultiWindowForceOnRequested2 = isMultiWindowForceOnRequested(i);
            if (!isMultiWindowOffRequested(i) || isMultiWindowForceOnRequested2) {
                setMultiWindowDynamicEnabled(str, true, i, true, isMultiWindowForceOnRequested2);
                Slog.d(TAG, "turn on MW[#" + i + "], Requester : " + str);
                return;
            }
            return;
        }
        if (i == -1) {
            Iterator it2 = startedUserIdsLocked.iterator();
            while (it2.hasNext()) {
                int intValue2 = ((Integer) it2.next()).intValue();
                if (isMultiWindowForceOnRequested(intValue2)) {
                    Slog.d(TAG, "force on now, turn off failed, MW[#" + i + "], Requester : " + str);
                } else if (isMultiWindowOffRequested(intValue2)) {
                    setMultiWindowDynamicEnabled(str, false, intValue2, true, false);
                    Slog.d(TAG, "turn off MW[#" + intValue2 + "], Requester : " + str);
                }
            }
            return;
        }
        if (isMultiWindowForceOnRequested(i)) {
            Slog.d(TAG, "force on now, turn off failed, MW[#" + i + "], Requester : " + str);
            return;
        }
        if (isMultiWindowOffRequested(i)) {
            setMultiWindowDynamicEnabled(str, false, i, true, false);
            Slog.d(TAG, "turn off MW[#" + i + "], Requester : " + str);
        }
    }

    public final boolean isMultiWindowForceOnRequested(int i) {
        return this.mMWForceOnRequestersForAllUsers.size() > 0 || (this.mMWForceOnRequesters.get(i) != null && ((ArrayList) this.mMWForceOnRequesters.get(i)).size() > 0);
    }

    public final boolean isMultiWindowOffRequested(int i) {
        return this.mMWOffRequestersForAllUsers.size() > 0 || (this.mMWOffRequesters.get(i) != null && ((ArrayList) this.mMWOffRequesters.get(i)).size() > 0);
    }

    public final void setMultiWindowDynamicEnabled(final String str, final boolean z, final int i, boolean z2, boolean z3) {
        boolean z4 = false;
        if (this.mAtm.mWindowManager.mCurrentUserId == i && isInMultiWindowModeLocked(0)) {
            z4 = true;
        }
        final boolean z5 = z4;
        this.mCoreStateController.setVolatileState("mw_enabled", Integer.valueOf(z ? 1 : 0), i, z2, z3, new Runnable() { // from class: com.android.server.wm.MultiWindowEnableController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MultiWindowEnableController.this.lambda$setMultiWindowDynamicEnabled$0(str, z, i, z5);
            }
        });
    }

    public final boolean isInMultiWindowModeLocked(int i) {
        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
        final TaskDisplayArea defaultTaskDisplayArea = displayContent != null ? displayContent.getDefaultTaskDisplayArea() : null;
        if (defaultTaskDisplayArea == null) {
            return false;
        }
        final int indexOf = defaultTaskDisplayArea.mChildren.indexOf(defaultTaskDisplayArea.getRootHomeTask());
        return (defaultTaskDisplayArea.getRootTask(new Predicate() { // from class: com.android.server.wm.MultiWindowEnableController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isInMultiWindowModeLocked$1;
                lambda$isInMultiWindowModeLocked$1 = MultiWindowEnableController.lambda$isInMultiWindowModeLocked$1(TaskDisplayArea.this, indexOf, (Task) obj);
                return lambda$isInMultiWindowModeLocked$1;
            }
        }) != null) || defaultTaskDisplayArea.isSplitScreenModeActivated() || defaultTaskDisplayArea.hasPinnedTask();
    }

    public static /* synthetic */ boolean lambda$isInMultiWindowModeLocked$1(TaskDisplayArea taskDisplayArea, int i, Task task) {
        return task.inFreeformWindowingMode() && taskDisplayArea.mChildren.indexOf(task) > i;
    }

    /* renamed from: scheduleEnableChangedBroadcast, reason: merged with bridge method [inline-methods] */
    public final void lambda$setMultiWindowDynamicEnabled$0(final String str, final boolean z, final int i, final boolean z2) {
        this.f1744mH.post(new Runnable() { // from class: com.android.server.wm.MultiWindowEnableController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MultiWindowEnableController.this.lambda$scheduleEnableChangedBroadcast$2(str, z, i, z2);
            }
        });
    }

    /* renamed from: sendEnableChangedBroadcast, reason: merged with bridge method [inline-methods] */
    public final void lambda$scheduleEnableChangedBroadcast$2(String str, boolean z, int i, boolean z2) {
        Intent intent = new Intent("com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED");
        intent.addFlags(1073741824);
        intent.putExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLE_REQUESTER", str);
        intent.putExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLED", z);
        intent.putExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLED_USER_ID", i);
        intent.putExtra("com.samsung.android.extra.IN_MULTI_WINDOW_MODE", z2);
        this.mAtm.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.permission.MULTI_WINDOW_MONITOR", -1);
    }

    public void stopUserLocked(int i, boolean z) {
        if (z) {
            this.mMWOffRequesters.remove(i);
            this.mMWOffRequestersLog.remove(i);
        }
    }

    public void initializeLocked(int i) {
        updateDeviceSupportsMultiWindow();
        if (this.mDeviceSupportsMultiWindow) {
            setMultiWindowDynamicEnabled("Initialize", isMultiWindowForceOnRequested(i) || !isMultiWindowOffRequested(i), i, false, false);
        }
    }

    public final void dumpMWRequesterLocked(PrintWriter printWriter, ArrayList arrayList, String str) {
        int size = arrayList.size();
        if (size > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("      ");
            sb.append(str);
            for (int i = 0; i < size; i++) {
                sb.append((String) arrayList.get(i));
                if (i < size - 1) {
                    sb.append(" - ");
                }
            }
            printWriter.println(sb);
        }
    }

    public final void dumpMWFeatureLocked(PrintWriter printWriter, ArrayList arrayList, String[] strArr) {
        try {
            printWriter.println("    " + strArr[0] + " = " + MultiWindowCoreState.class.getField(strArr[0]).getBoolean(MultiWindowCoreState.class));
            if (arrayList.size() > 0) {
                dumpMWRequesterLocked(printWriter, arrayList, strArr[1] + " : ");
            }
        } catch (Throwable unused) {
        }
    }

    public final void dumpMWFeatureWithIntLocked(PrintWriter printWriter, ArrayList arrayList, String[] strArr) {
        try {
            printWriter.println("    " + strArr[0] + " = " + MultiWindowCoreState.class.getField(strArr[0]).getInt(MultiWindowCoreState.class));
            if (arrayList.size() > 0) {
                dumpMWRequesterLocked(printWriter, arrayList, strArr[1] + " : ");
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[MultiWindowEnableController]");
        for (int i = 0; i < this.mMWOffRequesters.size(); i++) {
            dumpMWRequesterLocked(printWriter, (ArrayList) this.mMWOffRequesters.valueAt(i), "MWOffRequester[u" + this.mMWOffRequesters.keyAt(i) + "] : ");
        }
        for (int i2 = 0; i2 < this.mMWOffRequestersLog.size(); i2++) {
            dumpMWRequesterLocked(printWriter, (ArrayList) this.mMWOffRequestersLog.valueAt(i2), "MWOffRequesterLog[u" + this.mMWOffRequestersLog.keyAt(i2) + "] : ");
        }
        if (this.mMWOffRequestersForAllUsers.size() > 0) {
            dumpMWRequesterLocked(printWriter, this.mMWOffRequestersForAllUsers, "MWOffRequestersForAllUsers : ");
        }
        if (this.mMWOffRequestersLogForAllUsers.size() > 0) {
            dumpMWRequesterLocked(printWriter, this.mMWOffRequestersLogForAllUsers, "MWOffRequestersLogForAllUsers : ");
        }
        for (int i3 = 0; i3 < this.mMWForceOnRequesters.size(); i3++) {
            dumpMWRequesterLocked(printWriter, (ArrayList) this.mMWForceOnRequesters.valueAt(i3), "MWForceOnRequester[u" + this.mMWForceOnRequesters.keyAt(i3) + "] : ");
        }
        for (int i4 = 0; i4 < this.mMWForceOnRequestersLog.size(); i4++) {
            dumpMWRequesterLocked(printWriter, (ArrayList) this.mMWForceOnRequestersLog.valueAt(i4), "MWForceOnRequesterLog[u" + this.mMWForceOnRequestersLog.keyAt(i4) + "] : ");
        }
        if (this.mMWForceOnRequestersForAllUsers.size() > 0) {
            dumpMWRequesterLocked(printWriter, this.mMWForceOnRequestersForAllUsers, "MWForceOnRequestersForAllUsers : ");
        }
        if (this.mMWForceOnRequestersLogForAllUsers.size() > 0) {
            dumpMWRequesterLocked(printWriter, this.mMWForceOnRequestersLogForAllUsers, "MWForceOnRequestersLogForAllUsers : ");
        }
        dumpMWFeatureLocked(printWriter, this.mCornerGestureRequestLogs, new String[]{"MW_FREEFORM_CORNER_GESTURE_ENABLED", "mCornerGestureRequestLogs"});
        dumpMWFeatureLocked(printWriter, this.mMultiStarBlockedMinimizeRequestLog, new String[]{"MW_MULTISTAR_BLOCKED_MINIMIZE_FREEFORM", "mMultiStarBlockedMinimizeRequestLog"});
        dumpMWFeatureLocked(printWriter, this.mSFRequestLog, new String[]{"MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED", "mSFRequestLog"});
        dumpMWFeatureWithIntLocked(printWriter, this.mCDRequestLogs, new String[]{"MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED", "mCDRequestLog"});
        dumpMWFeatureLocked(printWriter, this.mELSRequestLog, new String[]{"MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED", "mELSRequestLog"});
        dumpMWFeatureLocked(printWriter, this.mSplitImmersiveModeRequestLog, new String[]{"MW_SPLIT_IMMERSIVE_MODE_ENABLED", "mSplitImmersiveModeRequestLog"});
        dumpMWFeatureLocked(printWriter, this.mNaviStarSplitImmersiveModeRequestLog, new String[]{"MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED", "mNaviStarSplitImmersiveModeRequestLog"});
        printWriter.println(str + MultiWindowCoreState.getInstance());
        printWriter.println();
    }

    public void setSplitImmersiveModeLocked(boolean z) {
        this.mSplitImmersiveModeRequestLog.add("(" + z + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mSplitImmersiveModeRequestLog.size() > 20) {
            this.mSplitImmersiveModeRequestLog.remove(0);
        }
        if (this.mPref == null) {
            this.mPref = this.mAtm.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        SharedPreferences.Editor edit = this.mPref.edit();
        edit.putInt("mw_immersive_mode", z ? 1 : 0);
        edit.apply();
        this.mCoreStateController.setSharedPreferenceEdited(0);
    }

    public boolean isSplitImmersiveModeEnabledLocked() {
        if (this.mPref == null) {
            this.mPref = this.mAtm.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        return this.mPref.getInt("mw_immersive_mode", 0) == 1;
    }

    public void setNaviStarImmersiveSplitModeLocked(boolean z) {
        this.mNaviStarSplitImmersiveModeRequestLog.add("(" + z + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mNaviStarSplitImmersiveModeRequestLog.size() > 20) {
            this.mNaviStarSplitImmersiveModeRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState("mw_navibar_immersive_mode", Integer.valueOf(z ? 1 : 0), ((Integer) it.next()).intValue(), true, true, null);
        }
    }

    public void setCornerGestureEnabled(boolean z) {
        this.mCornerGestureRequestLogs.add("(" + z + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mCornerGestureRequestLogs.size() > 50) {
            this.mCornerGestureRequestLogs.remove(0);
        }
        if (this.mPref == null) {
            this.mPref = this.mAtm.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        SharedPreferences.Editor edit = this.mPref.edit();
        edit.putInt("open_in_pop_up_view", z ? 1 : 0);
        edit.apply();
        this.mCoreStateController.setSharedPreferenceEdited(0);
    }

    public void setBlockedMinimizeFreeformEnabled(boolean z) {
        this.mMultiStarBlockedMinimizeRequestLog.add("(" + z + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mMultiStarBlockedMinimizeRequestLog.size() > 50) {
            this.mMultiStarBlockedMinimizeRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState("mw_blocked_minimized_freeform", Integer.valueOf(z ? 1 : 0), ((Integer) it.next()).intValue(), true, true, null);
        }
    }

    public void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) {
        this.mSFRequestLog.add("(" + z + ", " + z2 + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mSFRequestLog.size() > 50) {
            this.mSFRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            this.mCoreStateController.setVolatileState("stay_focus_activity", Integer.valueOf(z ? 1 : 0), intValue, true, true, null);
            this.mCoreStateController.setVolatileState("stay_top_resumed_activity", Integer.valueOf(z2 ? 1 : 0), intValue, true, true, null);
        }
    }

    public void setCustomDensityEnabled(int i) {
        this.mCDRequestLogs.add("(" + i + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mCDRequestLogs.size() > 50) {
            this.mCDRequestLogs.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState("custom_density", Integer.valueOf(i), ((Integer) it.next()).intValue(), true, true, null);
        }
    }

    public void setEnsureLaunchSplitEnabled(boolean z) {
        this.mELSRequestLog.add("(" + z + ", " + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ")");
        if (this.mELSRequestLog.size() > 50) {
            this.mELSRequestLog.remove(0);
        }
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
            this.mCoreStateController.setVolatileState("mw_ensure_launch_split", Integer.valueOf(z ? 1 : 0), ((Integer) it.next()).intValue(), true, true, null);
        }
    }

    public class TransitionListener extends WindowManagerInternal.AppTransitionListener {
        public final List mExitAnimatingTasks;
        public final TransitionController mTransitionController;

        public TransitionListener(List list, TransitionController transitionController) {
            ArrayList arrayList = new ArrayList();
            this.mExitAnimatingTasks = arrayList;
            arrayList.addAll(list);
            this.mTransitionController = transitionController;
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionFinishedLocked(IBinder iBinder) {
            handleExitAnimatingTasks("Finished");
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionCancelledLocked(boolean z) {
            handleExitAnimatingTasks("Cancelled");
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionTimeoutLocked() {
            handleExitAnimatingTasks("Timeout");
        }

        public final void handleExitAnimatingTasks(String str) {
            for (Task task : this.mExitAnimatingTasks) {
                task.setWindowingMode(1);
                Slog.d(MultiWindowEnableController.TAG, "handleExitAnimatingTasks: #" + task.mTaskId + ", reason=" + str);
            }
            this.mExitAnimatingTasks.clear();
            this.mTransitionController.unregisterLegacyListener(this);
        }
    }
}
