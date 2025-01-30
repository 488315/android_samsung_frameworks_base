package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.WindowInsets;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.appprelauncher.AppPrelaunchManagerService;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.RecentTasks;
import com.google.android.collect.Sets;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class RecentTasks {
    public long mActiveTasksSessionDurationMs;
    public final ArrayList mCallbacks;
    public boolean mCheckTrimmableTasksOnIdle;
    public String mFeatureId;
    public boolean mFreezeTaskListReordering;
    public long mFreezeTaskListTimeoutMs;
    public int mGlobalMaxNumTasks;
    public boolean mHasVisibleRecentTasks;
    public final ArrayList mHiddenTasks;
    public int mLauncherInfo;
    public final WindowManagerPolicyConstants.PointerEventListener mListener;
    public int mMaxNumVisibleTasks;
    public int mMinNumVisibleTasks;
    public final SparseArray mPersistedTaskIds;
    public ComponentName mRecentsComponent;
    public ComponentName mRecentsComponentForDeX;
    public int mRecentsUid;
    public int mRecentsUidForDeX;
    public final Runnable mResetFreezeTaskListOnTimeoutRunnable;
    public final ActivityTaskManagerService mService;
    public final ActivityTaskSupervisor mSupervisor;
    public TaskChangeNotificationController mTaskNotificationController;
    public final TaskPersister mTaskPersister;
    public final ArrayList mTasks;
    public final HashMap mTmpAvailActCache;
    public final HashMap mTmpAvailAppCache;
    public final SparseBooleanArray mTmpQuietProfileUserIds;
    public final ArrayList mTmpRecents;
    public final UserToProcMap mUserToProcs;
    public final SparseBooleanArray mUsersWithRecentsLoaded;
    public static final long FREEZE_TASK_LIST_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(5);
    public static final Comparator TASK_ID_COMPARATOR = new Comparator() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda3
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int lambda$static$0;
            lambda$static$0 = RecentTasks.lambda$static$0((Task) obj, (Task) obj2);
            return lambda$static$0;
        }
    };
    public static final ActivityInfo NO_ACTIVITY_INFO_TOKEN = new ActivityInfo();
    public static final ApplicationInfo NO_APPLICATION_INFO_TOKEN = new ApplicationInfo();

    public interface Callbacks {
        void onRecentTaskAdded(Task task);

        void onRecentTaskRemoved(Task task, boolean z, boolean z2);
    }

    public static /* synthetic */ int lambda$static$0(Task task, Task task2) {
        return task2.mTaskId - task.mTaskId;
    }

    /* renamed from: com.android.server.wm.RecentTasks$1 */
    public class WindowManagerPolicyConstantsPointerEventListenerC29611 implements WindowManagerPolicyConstants.PointerEventListener {
        public WindowManagerPolicyConstantsPointerEventListenerC29611() {
        }

        public void onPointerEvent(MotionEvent motionEvent) {
            if (RecentTasks.this.mFreezeTaskListReordering && motionEvent.getAction() == 0 && motionEvent.getClassification() != 4) {
                final int displayId = motionEvent.getDisplayId();
                final int x = (int) motionEvent.getX();
                final int y = (int) motionEvent.getY();
                if (RecentTasks.this.checkGestureHintRegionWhenHintOff(displayId, y)) {
                    Slog.d("ActivityTaskManager", "skip PointerEvent to keep mFreezeTaskListReordering");
                } else {
                    RecentTasks.this.mService.f1734mH.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.wm.RecentTasks$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RecentTasks.WindowManagerPolicyConstantsPointerEventListenerC29611.this.lambda$onPointerEvent$0(displayId, x, y, obj);
                        }
                    }, (Object) null).recycleOnUse());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPointerEvent$0(int i, int i2, int i3, Object obj) {
            WindowManagerGlobalLock windowManagerGlobalLock = RecentTasks.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState touchableWinAtPointLocked = RecentTasks.this.mService.mRootWindowContainer.getDisplayContent(i).mDisplayContent.getTouchableWinAtPointLocked(i2, i3);
                    if (touchableWinAtPointLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    int i4 = touchableWinAtPointLocked.mAttrs.type;
                    boolean z = true;
                    if (1 > i4 || i4 > 99) {
                        z = false;
                    }
                    if (z) {
                        Task topDisplayFocusedRootTask = RecentTasks.this.mService.getTopDisplayFocusedRootTask();
                        RecentTasks.this.resetFreezeTaskListReordering(topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopMostTask() : null);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public final boolean checkGestureHintRegionWhenHintOff(int i, int i2) {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mGesutreHint == 1) {
            return false;
        }
        InsetsState rawInsetsState = activityTaskManagerService.mRootWindowContainer.getDisplayContent(i).mDisplayContent.getInsetsStateController().getRawInsetsState();
        Rect rect = new Rect(rawInsetsState.getDisplayFrame());
        rect.inset(rawInsetsState.calculateInsets(rect, WindowInsets.Type.mandatorySystemGestures(), true));
        return i2 >= rect.bottom;
    }

    public RecentTasks(ActivityTaskManagerService activityTaskManagerService, TaskPersister taskPersister) {
        this.mRecentsUid = -1;
        this.mRecentsComponent = null;
        this.mRecentsUidForDeX = -1;
        this.mRecentsComponentForDeX = null;
        this.mUsersWithRecentsLoaded = new SparseBooleanArray(5);
        this.mPersistedTaskIds = new SparseArray(5);
        this.mTasks = new ArrayList();
        this.mCallbacks = new ArrayList();
        this.mHiddenTasks = new ArrayList();
        this.mFreezeTaskListTimeoutMs = FREEZE_TASK_LIST_TIMEOUT_MS;
        this.mTmpRecents = new ArrayList();
        this.mTmpAvailActCache = new HashMap();
        this.mTmpAvailAppCache = new HashMap();
        this.mTmpQuietProfileUserIds = new SparseBooleanArray();
        this.mUserToProcs = new UserToProcMap();
        this.mLauncherInfo = -1;
        this.mListener = new WindowManagerPolicyConstantsPointerEventListenerC29611();
        this.mResetFreezeTaskListOnTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RecentTasks.this.resetFreezeTaskListReorderingOnTimeout();
            }
        };
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mTaskPersister = taskPersister;
        this.mGlobalMaxNumTasks = ActivityTaskManager.getMaxRecentTasksStatic();
        this.mHasVisibleRecentTasks = true;
        this.mTaskNotificationController = activityTaskManagerService.getTaskChangeNotificationController();
    }

    public RecentTasks(ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor) {
        this.mRecentsUid = -1;
        this.mRecentsComponent = null;
        this.mRecentsUidForDeX = -1;
        this.mRecentsComponentForDeX = null;
        this.mUsersWithRecentsLoaded = new SparseBooleanArray(5);
        this.mPersistedTaskIds = new SparseArray(5);
        this.mTasks = new ArrayList();
        this.mCallbacks = new ArrayList();
        this.mHiddenTasks = new ArrayList();
        this.mFreezeTaskListTimeoutMs = FREEZE_TASK_LIST_TIMEOUT_MS;
        this.mTmpRecents = new ArrayList();
        this.mTmpAvailActCache = new HashMap();
        this.mTmpAvailAppCache = new HashMap();
        this.mTmpQuietProfileUserIds = new SparseBooleanArray();
        this.mUserToProcs = new UserToProcMap();
        this.mLauncherInfo = -1;
        this.mListener = new WindowManagerPolicyConstantsPointerEventListenerC29611();
        this.mResetFreezeTaskListOnTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RecentTasks.this.resetFreezeTaskListReorderingOnTimeout();
            }
        };
        File dataSystemDirectory = Environment.getDataSystemDirectory();
        Resources resources = activityTaskManagerService.mContext.getResources();
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mTaskPersister = new TaskPersister(dataSystemDirectory, activityTaskSupervisor, activityTaskManagerService, this, activityTaskSupervisor.mPersisterQueue);
        this.mGlobalMaxNumTasks = ActivityTaskManager.getMaxRecentTasksStatic();
        this.mTaskNotificationController = activityTaskManagerService.getTaskChangeNotificationController();
        this.mHasVisibleRecentTasks = resources.getBoolean(R.bool.config_enable_iwlan_handover_policy);
        loadParametersFromResources(resources);
    }

    public void setParameters(int i, int i2, long j) {
        this.mMinNumVisibleTasks = i;
        this.mMaxNumVisibleTasks = i2;
        this.mActiveTasksSessionDurationMs = j;
    }

    public void setGlobalMaxNumTasks(int i) {
        this.mGlobalMaxNumTasks = i;
    }

    public void setFreezeTaskListTimeout(long j) {
        this.mFreezeTaskListTimeoutMs = j;
    }

    public WindowManagerPolicyConstants.PointerEventListener getInputListener() {
        return this.mListener;
    }

    public void setFreezeTaskListReordering() {
        if (!this.mFreezeTaskListReordering) {
            this.mTaskNotificationController.notifyTaskListFrozen(true);
            this.mFreezeTaskListReordering = true;
        }
        this.mService.f1734mH.removeCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable);
        this.mService.f1734mH.postDelayed(this.mResetFreezeTaskListOnTimeoutRunnable, this.mFreezeTaskListTimeoutMs);
    }

    public void resetFreezeTaskListReordering(Task task) {
        if (this.mFreezeTaskListReordering) {
            this.mFreezeTaskListReordering = false;
            this.mService.f1734mH.removeCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable);
            if (task != null) {
                this.mTasks.remove(task);
                this.mTasks.add(0, task);
            }
            trimInactiveRecentTasks();
            this.mTaskNotificationController.notifyTaskStackChanged();
            this.mTaskNotificationController.notifyTaskListFrozen(false);
        }
    }

    public void resetFreezeTaskListReorderingOnTimeout() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mService.getTopDisplayFocusedRootTask();
                Task task = null;
                Task topMostTask = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopMostTask() : null;
                if (topMostTask != null && topMostTask.hasChild()) {
                    task = topMostTask;
                }
                resetFreezeTaskListReordering(task);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean isFreezeTaskListReorderingSet() {
        return this.mFreezeTaskListReordering;
    }

    public void loadParametersFromResources(Resources resources) {
        if (ActivityManager.isLowRamDeviceStatic()) {
            this.mMinNumVisibleTasks = resources.getInteger(R.integer.config_nightDisplayColorTemperatureDefault);
            this.mMaxNumVisibleTasks = resources.getInteger(R.integer.config_multiuserMaxRunningUsers);
        } else if (SystemProperties.getBoolean("ro.recents.grid", false)) {
            this.mMinNumVisibleTasks = resources.getInteger(R.integer.config_networkWakeupPacketMask);
            this.mMaxNumVisibleTasks = resources.getInteger(R.integer.config_motionPredictionOffsetNanos);
        } else {
            this.mMinNumVisibleTasks = resources.getInteger(R.integer.config_networkWakeupPacketMark);
            this.mMaxNumVisibleTasks = resources.getInteger(R.integer.config_mobile_mtu);
        }
        this.mActiveTasksSessionDurationMs = -1L;
    }

    public void clearRecentTasksLocked(int i) {
        int size = this.mTasks.size();
        int i2 = 0;
        while (i2 < size) {
            if (((Task) this.mTasks.get(i2)).mUserId == i) {
                this.mTasks.remove(i2);
                i2--;
                size--;
            }
            i2++;
        }
    }

    public void loadRecentsComponent(Resources resources) {
        ComponentName unflattenFromString;
        if (CoreRune.MW_SA_LOGGING) {
            try {
                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(KnoxCustomManagerService.LAUNCHER_PACKAGE, 0L, this.mService.mContext.getUserId());
                if (applicationInfo != null) {
                    this.mLauncherInfo = applicationInfo.uid;
                }
            } catch (RemoteException unused) {
                Slog.w("ActivityTaskManager", "Could not load application info for recents edge");
            }
        }
        String string = resources.getString(R.string.etws_primary_default_message_test);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(string);
        if (unflattenFromString2 != null) {
            try {
                ApplicationInfo applicationInfo2 = AppGlobals.getPackageManager().getApplicationInfo(unflattenFromString2.getPackageName(), 8704L, this.mService.mContext.getUserId());
                if (applicationInfo2 != null) {
                    this.mRecentsUid = applicationInfo2.uid;
                    this.mRecentsComponent = unflattenFromString2;
                }
            } catch (RemoteException unused2) {
                Slog.w("ActivityTaskManager", "Could not load application info for recents component: " + unflattenFromString2);
            }
        }
        String string2 = resources.getString(R.string.etws_primary_default_message_tsunami);
        if (TextUtils.isEmpty(string2) || (unflattenFromString = ComponentName.unflattenFromString(string2)) == null) {
            return;
        }
        try {
            ApplicationInfo applicationInfo3 = AppGlobals.getPackageManager().getApplicationInfo(unflattenFromString.getPackageName(), 0L, this.mService.mContext.getUserId());
            if (applicationInfo3 != null) {
                this.mRecentsUidForDeX = applicationInfo3.uid;
                this.mRecentsComponentForDeX = unflattenFromString;
            }
        } catch (RemoteException unused3) {
            Slog.w("ActivityTaskManager", "Could not load application info for recents componentForDeX: " + unflattenFromString);
        }
    }

    public boolean isCallerRecents(int i) {
        return UserHandle.isSameApp(i, this.mRecentsUid) || UserHandle.isSameApp(i, this.mRecentsUidForDeX);
    }

    public boolean isRecentsUid(int i) {
        return UserHandle.isSameApp(i, this.mRecentsUid);
    }

    public boolean isLauncherUid(int i) {
        return UserHandle.isSameApp(i, this.mLauncherInfo);
    }

    public boolean isRecentsComponent(ComponentName componentName, int i) {
        return (componentName.equals(this.mRecentsComponent) && UserHandle.isSameApp(i, this.mRecentsUid)) || (componentName.equals(this.mRecentsComponentForDeX) && UserHandle.isSameApp(i, this.mRecentsUidForDeX));
    }

    public boolean isRecentsComponentHomeActivity(int i) {
        ComponentName defaultHomeActivity = this.mService.getPackageManagerInternalLocked().getDefaultHomeActivity(i);
        return (defaultHomeActivity == null || this.mRecentsComponent == null || (!defaultHomeActivity.getPackageName().equals(this.mRecentsComponent.getPackageName()) && (this.mRecentsComponentForDeX == null || !defaultHomeActivity.getPackageName().equals(this.mRecentsComponentForDeX.getPackageName())))) ? false : true;
    }

    public ComponentName getRecentsComponent() {
        return this.mRecentsComponent;
    }

    public String getRecentsComponentFeatureId() {
        return this.mFeatureId;
    }

    public int getRecentsComponentUid() {
        return this.mRecentsUid;
    }

    public void registerCallback(Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
    }

    public void unregisterCallback(Callbacks callbacks) {
        this.mCallbacks.remove(callbacks);
    }

    public final void notifyTaskAdded(Task task) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            ((Callbacks) this.mCallbacks.get(i)).onRecentTaskAdded(task);
        }
        this.mTaskNotificationController.notifyTaskListUpdated();
    }

    public final void notifyTaskRemoved(Task task, boolean z, boolean z2) {
        if (CoreRune.FW_DEDICATED_MEMORY && task.mDedicatedTask) {
            this.mService.notifyDedicated(task.mTaskId);
        }
        this.mService.mContentDispatcher.scheduleClearPdfTask(task.mTaskId);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            ((Callbacks) this.mCallbacks.get(i)).onRecentTaskRemoved(task, z, z2);
        }
        this.mTaskNotificationController.notifyTaskListUpdated();
    }

    public void loadUserRecentsLocked(int i) {
        HashMap restoreDedicatedProcessForUserLocked;
        if (this.mUsersWithRecentsLoaded.get(i)) {
            return;
        }
        loadPersistedTaskIdsForUserLocked(i);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        Iterator it = this.mTasks.iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            if (task.mUserId == i && shouldPersistTaskLocked(task)) {
                sparseBooleanArray.put(task.mTaskId, true);
            }
        }
        Slog.i("ActivityTaskManager", "Loading recents for user " + i + " into memory.");
        this.mTasks.addAll(this.mTaskPersister.restoreTasksForUserLocked(i, sparseBooleanArray));
        cleanupLocked(i);
        this.mUsersWithRecentsLoaded.put(i, true);
        if (sparseBooleanArray.size() > 0) {
            syncPersistentTaskIdsLocked();
        }
        if (!CoreRune.FW_DEDICATED_MEMORY || (restoreDedicatedProcessForUserLocked = this.mTaskPersister.restoreDedicatedProcessForUserLocked(i)) == null) {
            return;
        }
        this.mUserToProcs.put(i, restoreDedicatedProcessForUserLocked);
        this.mTasks.forEach(new Consumer() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RecentTasks.this.lambda$loadUserRecentsLocked$1((Task) obj);
            }
        });
        this.mService.notifyDedicated(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadUserRecentsLocked$1(Task task) {
        task.mDedicatedTask = task.mHostProcessName != null && this.mUserToProcs.get(task.mUserId).containsKey(task.mHostProcessName);
    }

    public final void loadPersistedTaskIdsForUserLocked(int i) {
        if (this.mPersistedTaskIds.get(i) == null) {
            this.mPersistedTaskIds.put(i, this.mTaskPersister.loadPersistedTaskIdsForUser(i));
            Slog.i("ActivityTaskManager", "Loaded persisted task ids for user " + i);
        }
    }

    public boolean containsTaskId(int i, int i2) {
        loadPersistedTaskIdsForUserLocked(i2);
        return ((SparseBooleanArray) this.mPersistedTaskIds.get(i2)).get(i);
    }

    public SparseBooleanArray getTaskIdsForUser(int i) {
        loadPersistedTaskIdsForUserLocked(i);
        return (SparseBooleanArray) this.mPersistedTaskIds.get(i);
    }

    public void notifyTaskPersisterLocked(Task task, boolean z) {
        Task rootTask = task != null ? task.getRootTask() : null;
        if (rootTask == null || !rootTask.isActivityTypeHomeOrRecents()) {
            syncPersistentTaskIdsLocked();
            this.mTaskPersister.wakeup(task, z);
        }
    }

    public final void syncPersistentTaskIdsLocked() {
        for (int size = this.mPersistedTaskIds.size() - 1; size >= 0; size--) {
            if (this.mUsersWithRecentsLoaded.get(this.mPersistedTaskIds.keyAt(size))) {
                ((SparseBooleanArray) this.mPersistedTaskIds.valueAt(size)).clear();
            }
        }
        for (int size2 = this.mTasks.size() - 1; size2 >= 0; size2--) {
            Task task = (Task) this.mTasks.get(size2);
            if (shouldPersistTaskLocked(task)) {
                if (this.mPersistedTaskIds.get(task.mUserId) == null) {
                    Slog.wtf("ActivityTaskManager", "No task ids found for userId " + task.mUserId + ". task=" + task + " mPersistedTaskIds=" + this.mPersistedTaskIds);
                    this.mPersistedTaskIds.put(task.mUserId, new SparseBooleanArray());
                }
                ((SparseBooleanArray) this.mPersistedTaskIds.get(task.mUserId)).put(task.mTaskId, true);
            }
        }
    }

    public static boolean shouldPersistTaskLocked(Task task) {
        Task rootTask = task.getRootTask();
        return task.isPersistable && (rootTask == null || !rootTask.isActivityTypeHomeOrRecents());
    }

    public void onSystemReadyLocked() {
        loadRecentsComponent(this.mService.mContext.getResources());
        this.mTasks.clear();
    }

    public Bitmap getTaskDescriptionIcon(String str) {
        return this.mTaskPersister.getTaskDescriptionIcon(str);
    }

    public void saveImage(Bitmap bitmap, String str) {
        this.mTaskPersister.saveImage(bitmap, str);
    }

    public void flush() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                syncPersistentTaskIdsLocked();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        this.mTaskPersister.flush();
    }

    public int[] usersWithRecentsLoadedLocked() {
        int size = this.mUsersWithRecentsLoaded.size();
        int[] iArr = new int[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = this.mUsersWithRecentsLoaded.keyAt(i2);
            if (this.mUsersWithRecentsLoaded.valueAt(i2)) {
                iArr[i] = keyAt;
                i++;
            }
        }
        return i < size ? Arrays.copyOf(iArr, i) : iArr;
    }

    public void unloadUserDataFromMemoryLocked(int i) {
        if (this.mUsersWithRecentsLoaded.get(i)) {
            Slog.i("ActivityTaskManager", "Unloading recents for user " + i + " from memory.");
            this.mUsersWithRecentsLoaded.delete(i);
            removeTasksForUserLocked(i);
        }
        this.mPersistedTaskIds.delete(i);
        this.mTaskPersister.unloadUserDataFromMemory(i);
    }

    public final void removeTasksForUserLocked(int i) {
        if (i <= 0) {
            Slog.i("ActivityTaskManager", "Can't remove recent task on user " + i);
            return;
        }
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (task.mUserId == i) {
                if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_TASKS, -1647332198, 4, (String) null, new Object[]{String.valueOf(task), Long.valueOf(i)});
                }
                remove(task);
            }
        }
    }

    public void onPackagesSuspendedChanged(String[] strArr, boolean z, int i) {
        HashSet newHashSet = Sets.newHashSet(strArr);
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            ComponentName componentName = task.realActivity;
            if (componentName != null && newHashSet.contains(componentName.getPackageName()) && task.mUserId == i && task.realActivitySuspended != z) {
                task.realActivitySuspended = z;
                if (z) {
                    this.mSupervisor.removeTask(task, false, true, "suspended-package");
                }
                notifyTaskPersisterLocked(task, false);
            }
        }
    }

    public void onLockTaskModeStateChanged(int i, int i2) {
        if (i != 1) {
            return;
        }
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (task.mUserId == i2) {
                this.mService.getLockTaskController();
                if (!LockTaskController.isTaskAuthAllowlisted(task.mLockTaskAuth)) {
                    remove(task);
                }
            }
        }
    }

    public void removeTasksByPackageName(String str, int i) {
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (task.mUserId == i && task.getBasePackageName().equals(str)) {
                this.mSupervisor.removeTask(task, true, true, "remove-package-task");
            }
        }
    }

    public void removeAllVisibleTasks(int i) {
        String packageName;
        Set profileIds = getProfileIds(i);
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (profileIds.contains(Integer.valueOf(task.mUserId))) {
                ActivityRecord rootActivity = task.getRootActivity();
                if (rootActivity != null) {
                    packageName = rootActivity.packageName;
                } else {
                    packageName = (task.getBaseIntent() == null || task.getBaseIntent().getComponent() == null) ? null : task.getBaseIntent().getComponent().getPackageName();
                }
                String str = packageName;
                if ((str == null || str.isEmpty() || !((ApplicationPolicyInternal) LocalServices.getService(ApplicationPolicyInternal.class)).isApplicationStopDisabledAsUser(str, task.mUserId, (String) null, (String) null, (String) null, true)) && isVisibleRecentTask(task)) {
                    this.mTasks.remove(size);
                    notifyTaskRemoved(task, true, true);
                }
            }
        }
    }

    public void cleanupDisabledPackageTasksLocked(String str, Set set, int i) {
        if (CoreRune.FW_TEMP_UPSM_DEBUG_LOG) {
            this.mService.writeDebugLogForUPSM("cleanupDisabledPackageTasksLocked pkg=" + str + ", filterBy=" + set);
        }
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (i == -1 || task.mUserId == i) {
                Intent intent = task.intent;
                ComponentName component = intent != null ? intent.getComponent() : null;
                if (component != null && component.getPackageName().equals(str) && (set == null || set.contains(component.getClassName()))) {
                    if (CoreRune.FW_TEMP_UPSM_DEBUG_LOG) {
                        this.mService.writeDebugLogForUPSM("remove Task for pkg=" + str + ", task=" + task);
                    }
                    this.mSupervisor.removeTask(task, false, true, "disabled-package");
                }
            }
        }
    }

    public void cleanupLocked(int i) {
        int i2;
        int size = this.mTasks.size();
        if (size == 0) {
            return;
        }
        this.mTmpAvailActCache.clear();
        this.mTmpAvailAppCache.clear();
        IPackageManager packageManager = AppGlobals.getPackageManager();
        int i3 = size - 1;
        while (true) {
            i2 = 0;
            if (i3 < 0) {
                break;
            }
            Task task = (Task) this.mTasks.get(i3);
            if (i == -1 || task.mUserId == i) {
                if (task.autoRemoveRecents && task.getTopNonFinishingActivity() == null && (!CoreRune.FW_DEDICATED_MEMORY || okToRemove(task))) {
                    remove(task);
                    Slog.w("ActivityTaskManager", "Removing auto-remove without activity: " + task);
                } else {
                    ComponentName componentName = task.realActivity;
                    if (componentName != null) {
                        ActivityInfo activityInfo = (ActivityInfo) this.mTmpAvailActCache.get(componentName);
                        if (activityInfo == null) {
                            try {
                                activityInfo = packageManager.getActivityInfo(task.realActivity, 268436480L, task.mUserId);
                                if (activityInfo == null) {
                                    activityInfo = NO_ACTIVITY_INFO_TOKEN;
                                }
                                this.mTmpAvailActCache.put(task.realActivity, activityInfo);
                            } catch (RemoteException unused) {
                            }
                        }
                        if (activityInfo == NO_ACTIVITY_INFO_TOKEN) {
                            ApplicationInfo applicationInfo = (ApplicationInfo) this.mTmpAvailAppCache.get(task.realActivity.getPackageName());
                            if (applicationInfo == null) {
                                applicationInfo = packageManager.getApplicationInfo(task.realActivity.getPackageName(), 8192L, task.mUserId);
                                if (applicationInfo == null) {
                                    applicationInfo = NO_APPLICATION_INFO_TOKEN;
                                }
                                this.mTmpAvailAppCache.put(task.realActivity.getPackageName(), applicationInfo);
                            }
                            if (applicationInfo == NO_APPLICATION_INFO_TOKEN || (applicationInfo.flags & 8388608) == 0) {
                                remove(task);
                                Slog.w("ActivityTaskManager", "Removing no longer valid recent: " + task);
                            } else {
                                task.isAvailable = false;
                            }
                        } else {
                            if (activityInfo.enabled) {
                                ApplicationInfo applicationInfo2 = activityInfo.applicationInfo;
                                if (applicationInfo2.enabled && (applicationInfo2.flags & 8388608) != 0) {
                                    task.isAvailable = true;
                                }
                            }
                            task.isAvailable = false;
                        }
                    }
                }
            }
            i3--;
        }
        int size2 = this.mTasks.size();
        while (i2 < size2) {
            i2 = processNextAffiliateChainLocked(i2);
        }
    }

    public final boolean canAddTaskWithoutTrim(Task task) {
        return findRemoveIndexForAddTask(task) == -1;
    }

    public ArrayList getAppTasksList(int i, String str) {
        ArrayList arrayList = new ArrayList();
        int size = this.mTasks.size();
        for (int i2 = 0; i2 < size; i2++) {
            Task task = (Task) this.mTasks.get(i2);
            if (task.effectiveUid == i && str.equals(task.getBasePackageName())) {
                arrayList.add(new AppTaskImpl(this.mService, task.mTaskId, i).asBinder());
            }
        }
        return arrayList;
    }

    public Set getProfileIds(int i) {
        ArraySet arraySet = new ArraySet();
        for (int i2 : this.mService.getUserManager().getProfileIds(i, false)) {
            arraySet.add(Integer.valueOf(i2));
        }
        return arraySet;
    }

    public UserInfo getUserInfo(int i) {
        return this.mService.getUserManager().getUserInfo(i);
    }

    public int[] getCurrentProfileIds() {
        return this.mService.mAmInternal.getCurrentProfileIds();
    }

    public boolean isUserRunning(int i, int i2) {
        return this.mService.mAmInternal.isUserRunning(i, i2);
    }

    public ParceledListSlice getRecentTasks(int i, int i2, boolean z, int i3, int i4) {
        return new ParceledListSlice(getRecentTasksImpl(i, i2, z, i3, i4));
    }

    public final ArrayList getRecentTasksImpl(int i, int i2, boolean z, int i3, int i4) {
        ActivityRecord rootActivity;
        boolean z2 = (i2 & 1) != 0;
        if (!isUserRunning(i3, 4)) {
            Slog.i("ActivityTaskManager", "user " + i3 + " is still locked. Cannot load recents");
            return new ArrayList();
        }
        loadUserRecentsLocked(i3);
        Set profileIds = getProfileIds(i3);
        profileIds.add(Integer.valueOf(i3));
        Set recentExcludedUsers = this.mService.getPersonaActivityHelper().getRecentExcludedUsers();
        if (recentExcludedUsers != null) {
            Iterator it = recentExcludedUsers.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (profileIds.contains(Integer.valueOf(intValue))) {
                    Slog.d("ActivityTaskManager", "user " + i3 + " is skipped. It's a knox excluded user id");
                    profileIds.remove(Integer.valueOf(intValue));
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        int size = this.mTasks.size();
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            Task task = (Task) this.mTasks.get(i6);
            if (isVisibleRecentTask(task)) {
                i5++;
                if (isInVisibleRange(task, i6, i5, z2) && arrayList.size() < i && profileIds.contains(Integer.valueOf(task.mUserId)) && !task.realActivitySuspended && ((z || task.isActivityTypeHome() || task.effectiveUid == i4) && ((!task.autoRemoveRecents || task.getTopNonFinishingActivity() != null || (CoreRune.FW_DEDICATED_MEMORY && !okToRemove(task))) && ((i2 & 2) == 0 || task.isAvailable)))) {
                    if (!task.mUserSetupComplete) {
                        Slog.d("ActivityTaskManager", "Skipping, user setup not complete: " + task);
                    } else if ((i2 & 16) != 0 || (rootActivity = task.getRootActivity(true)) == null || !rootActivity.mIsAliasActivity) {
                        arrayList.add(createRecentTaskInfo(task, true, z));
                    }
                }
            }
        }
        return arrayList;
    }

    public void getPersistableTaskIds(ArraySet arraySet) {
        int size = this.mTasks.size();
        for (int i = 0; i < size; i++) {
            Task task = (Task) this.mTasks.get(i);
            Task rootTask = task.getRootTask();
            if ((task.isPersistable || task.inRecents) && (rootTask == null || !rootTask.isActivityTypeHomeOrRecents())) {
                arraySet.add(Integer.valueOf(task.mTaskId));
            }
        }
    }

    public ArrayList getRawTasks() {
        return this.mTasks;
    }

    public SparseBooleanArray getRecentTaskIds() {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int size = this.mTasks.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Task task = (Task) this.mTasks.get(i2);
            if (isVisibleRecentTask(task)) {
                i++;
                if (isInVisibleRange(task, i2, i, false)) {
                    sparseBooleanArray.put(task.mTaskId, true);
                }
            }
        }
        return sparseBooleanArray;
    }

    public Task getTask(int i) {
        int size = this.mTasks.size();
        for (int i2 = 0; i2 < size; i2++) {
            Task task = (Task) this.mTasks.get(i2);
            if (task.mTaskId == i) {
                return task;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void add(Task task) {
        boolean z;
        int removeForAddTask;
        int indexOf;
        boolean z2 = (task.mAffiliatedTaskId == task.mTaskId && task.mNextAffiliateTaskId == -1 && task.mPrevAffiliateTaskId == -1) ? false : true;
        int size = this.mTasks.size();
        if (task.voiceSession != null) {
            return;
        }
        if (z2 || size <= 0 || this.mTasks.get(0) != task) {
            if (z2 && size > 0 && task.inRecents && task.mAffiliatedTaskId == ((Task) this.mTasks.get(0)).mAffiliatedTaskId) {
                return;
            }
            if (task.inRecents) {
                int indexOf2 = this.mTasks.indexOf(task);
                if (indexOf2 < 0) {
                    Slog.wtf("ActivityTaskManager", "Task with inRecent not in recents: " + task);
                    z = true;
                    removeForAddTask = removeForAddTask(task);
                    task.inRecents = true;
                    if (z2 || z) {
                        if (this.mFreezeTaskListReordering || removeForAddTask == -1) {
                            removeForAddTask = 0;
                        }
                        this.mTasks.add(removeForAddTask, task);
                        notifyTaskAdded(task);
                    } else if (z2) {
                        Task task2 = task.mNextAffiliate;
                        if (task2 == null) {
                            task2 = task.mPrevAffiliate;
                        }
                        if (task2 != null && (indexOf = this.mTasks.indexOf(task2)) >= 0) {
                            if (task2 == task.mNextAffiliate) {
                                indexOf++;
                            }
                            this.mTasks.add(indexOf, task);
                            notifyTaskAdded(task);
                            if (moveAffiliatedTasksToFront(task, indexOf)) {
                                return;
                            }
                        }
                        z = true;
                    }
                    if (z) {
                        cleanupLocked(task.mUserId);
                    }
                    this.mCheckTrimmableTasksOnIdle = true;
                    notifyTaskPersisterLocked(task, false);
                }
                if (!z2) {
                    if (!this.mFreezeTaskListReordering) {
                        this.mTasks.remove(indexOf2);
                        this.mTasks.add(0, task);
                        if (indexOf2 != 0) {
                            this.mTaskNotificationController.notifyTaskListUpdated();
                        }
                    }
                    notifyTaskPersisterLocked(task, false);
                    return;
                }
            }
            z = false;
            removeForAddTask = removeForAddTask(task);
            task.inRecents = true;
            if (z2) {
            }
            if (this.mFreezeTaskListReordering) {
            }
            removeForAddTask = 0;
            this.mTasks.add(removeForAddTask, task);
            notifyTaskAdded(task);
            if (z) {
            }
            this.mCheckTrimmableTasksOnIdle = true;
            notifyTaskPersisterLocked(task, false);
        }
    }

    public boolean addToBottom(Task task) {
        if (!canAddTaskWithoutTrim(task)) {
            return false;
        }
        add(task);
        return true;
    }

    public void remove(Task task) {
        this.mTasks.remove(task);
        notifyTaskRemoved(task, false, false);
    }

    public void onActivityIdle(ActivityRecord activityRecord) {
        if (!this.mHiddenTasks.isEmpty() && activityRecord.isActivityTypeHome() && activityRecord.isState(ActivityRecord.State.RESUMED)) {
            removeUnreachableHiddenTasks(activityRecord.getWindowingMode());
        }
        if (this.mCheckTrimmableTasksOnIdle) {
            this.mCheckTrimmableTasksOnIdle = false;
            trimInactiveRecentTasks();
        }
    }

    public final void trimInactiveRecentTasks() {
        if (this.mFreezeTaskListReordering) {
            return;
        }
        for (int size = this.mTasks.size(); size > this.mGlobalMaxNumTasks; size--) {
            int i = size - 1;
            Task task = (Task) this.mTasks.get(i);
            if (!CoreRune.FW_DEDICATED_MEMORY || !task.mDedicatedTask) {
                notifyTaskRemoved((Task) this.mTasks.remove(i), true, false);
            }
        }
        int[] currentProfileIds = getCurrentProfileIds();
        this.mTmpQuietProfileUserIds.clear();
        for (int i2 : currentProfileIds) {
            UserInfo userInfo = getUserInfo(i2);
            if (userInfo != null && userInfo.isManagedProfile() && userInfo.isQuietModeEnabled()) {
                this.mTmpQuietProfileUserIds.put(i2, true);
            }
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.mTasks.size()) {
            Task task2 = (Task) this.mTasks.get(i3);
            if (isActiveRecentTask(task2, this.mTmpQuietProfileUserIds)) {
                if (this.mHasVisibleRecentTasks && isVisibleRecentTask(task2)) {
                    i4++;
                    if (!isInVisibleRange(task2, i3, i4, false) && isTrimmable(task2)) {
                    }
                }
                i3++;
            }
            this.mTasks.remove(task2);
            notifyTaskRemoved(task2, true, false);
            notifyTaskPersisterLocked(task2, false);
        }
    }

    public final boolean isActiveRecentTask(Task task, SparseBooleanArray sparseBooleanArray) {
        Task task2;
        if (sparseBooleanArray.get(task.mUserId)) {
            return false;
        }
        int i = task.mAffiliatedTaskId;
        return i == -1 || i == task.mTaskId || (task2 = getTask(i)) == null || isActiveRecentTask(task2, sparseBooleanArray);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000f, code lost:
    
        if (r0 != 5) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isVisibleRecentTask(Task task) {
        AppPrelaunchManagerService appPrelaunchManagerService;
        int activityType = task.getActivityType();
        if (activityType != 2 && activityType != 3) {
            if (activityType == 4) {
                if ((task.getBaseIntent().getFlags() & 8388608) == 8388608) {
                    return false;
                }
            }
            int windowingMode = task.getWindowingMode();
            if (windowingMode != 2) {
                if ((windowingMode == 6 && task.isAlwaysOnTopWhenVisible()) || task == this.mService.getLockTaskController().getRootTask()) {
                    return false;
                }
                if (task.getDisplayContent() != null && !task.getDisplayContent().canShowTasksInHostDeviceRecents()) {
                    return false;
                }
                if (CoreRune.BAIDU_CARLIFE && task.getDisplayContent() != null && task.getDisplayContent().isCarLifeDisplay()) {
                    return false;
                }
                return (CoreRune.SYSFW_APP_PREL && (appPrelaunchManagerService = this.mService.mAps) != null && appPrelaunchManagerService.isAppPrelaunched(task.effectiveUid)) ? false : true;
            }
        }
        return false;
    }

    public final boolean isInVisibleRange(Task task, int i, int i2, boolean z) {
        if (!z) {
            if ((task.getBaseIntent().getFlags() & 8388608) == 8388608) {
                if (!task.inSplitScreenWindowingMode()) {
                    return task.isOnHomeDisplay() && i == 0;
                }
                Task createdByOrganizerTask = task.getCreatedByOrganizerTask();
                return createdByOrganizerTask != null && createdByOrganizerTask.getTopMostTask().equals(task);
            }
        }
        int i3 = this.mMinNumVisibleTasks;
        if ((i3 >= 0 && i2 <= i3) || task.mChildPipActivity != null) {
            return true;
        }
        int i4 = this.mMaxNumVisibleTasks;
        return i4 >= 0 ? i2 <= i4 : this.mActiveTasksSessionDurationMs <= 0 || task.getInactiveDuration() <= this.mActiveTasksSessionDurationMs;
    }

    public boolean isTrimmable(Task task) {
        Task rootHomeTask;
        if (task.isAttached()) {
            return (!task.isOnHomeDisplay() || (rootHomeTask = task.getDisplayArea().getRootHomeTask()) == null || task.isMinimized() || task.isAvoidTrimDexPendingActivityTask() || task.compareTo((WindowContainer) rootHomeTask) >= 0) ? false : true;
        }
        return true;
    }

    public final void removeUnreachableHiddenTasks(int i) {
        for (int size = this.mHiddenTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mHiddenTasks.get(size);
            if (!task.hasChild() || task.inRecents) {
                this.mHiddenTasks.remove(size);
            } else if (task.getWindowingMode() == i && task.getTopVisibleActivity() == null) {
                this.mHiddenTasks.remove(size);
                this.mSupervisor.removeTask(task, false, false, "remove-hidden-task");
            }
        }
    }

    public final int removeForAddTask(Task task) {
        this.mHiddenTasks.remove(task);
        int findRemoveIndexForAddTask = findRemoveIndexForAddTask(task);
        if (findRemoveIndexForAddTask == -1) {
            return findRemoveIndexForAddTask;
        }
        Task task2 = (Task) this.mTasks.remove(findRemoveIndexForAddTask);
        if (task2 != task) {
            if (task2.hasChild() && !task2.isActivityTypeHome()) {
                Slog.i("ActivityTaskManager", "Add " + task2 + " to hidden list because adding " + task);
                this.mHiddenTasks.add(task2);
            }
            if (CoreRune.FW_DEDICATED_MEMORY) {
                task.mDedicatedTask = task2.mDedicatedTask;
                task.mOldHostProcessName = task2.mHostProcessName;
                if (task2.mDedicatedTask) {
                    task2.mDedicatedTask = false;
                    this.mService.notifyDedicated(task.mTaskId);
                }
            }
            notifyTaskRemoved(task2, false, false);
        }
        notifyTaskPersisterLocked(task2, false);
        return findRemoveIndexForAddTask;
    }

    public final String getTaskComponentHash(Task task) {
        Intent intent = task.intent;
        if (intent == null || intent.getComponent() == null) {
            return null;
        }
        return String.valueOf(task.intent.getComponent().hashCode());
    }

    public final boolean isSameTaskAffinity(Task task, Task task2) {
        String str = task.affinity;
        String str2 = task2.affinity;
        if (str == null || str2 == null) {
            return false;
        }
        String taskComponentHash = getTaskComponentHash(task);
        if (taskComponentHash != null && str.endsWith(taskComponentHash)) {
            str = str.substring(0, str.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR));
        }
        String taskComponentHash2 = getTaskComponentHash(task2);
        if (taskComponentHash2 != null && str2.endsWith(taskComponentHash2)) {
            str2 = str2.substring(0, str2.lastIndexOf(XmlUtils.STRING_ARRAY_SEPARATOR));
        }
        return str.equals(str2);
    }

    public final int findRemoveIndexForAddTask(Task task) {
        ComponentName componentName;
        int size = this.mTasks.size();
        Intent intent = task.intent;
        boolean z = intent != null && intent.isDocument();
        int i = task.maxRecents - 1;
        for (int i2 = 0; i2 < size; i2++) {
            Task task2 = (Task) this.mTasks.get(i2);
            if (task != task2) {
                if (hasCompatibleActivityTypeAndWindowingMode(task, task2) && task.mUserId == task2.mUserId) {
                    Intent intent2 = task2.intent;
                    boolean isSameTaskAffinity = isSameTaskAffinity(task, task2);
                    boolean z2 = intent != null && intent.filterEquals(intent2);
                    int flags = intent != null ? intent.getFlags() : 0;
                    boolean z3 = ((268959744 & flags) == 0 || (flags & 134217728) == 0) ? false : true;
                    boolean z4 = intent2 != null && intent2.isDocument();
                    boolean z5 = z && z4;
                    if (isSameTaskAffinity || z2 || z5) {
                        if (z5) {
                            ComponentName componentName2 = task.realActivity;
                            if (!((componentName2 == null || (componentName = task2.realActivity) == null || !componentName2.equals(componentName)) ? false : true)) {
                                continue;
                            } else if (i > 0) {
                                i--;
                                if (z2 && !z3) {
                                }
                            }
                        } else if (z) {
                            continue;
                        } else if (z4) {
                            continue;
                        } else if (z3) {
                            continue;
                        } else {
                            ActivityRecord activityRecord = task.topRunningActivity();
                            if (activityRecord != null && activityRecord.launchMode == 4 && task2.hasChild() && (flags & 134217728) != 0) {
                            }
                        }
                    }
                }
            }
            return i2;
        }
        return -1;
    }

    public final int processNextAffiliateChainLocked(int i) {
        int i2;
        Task task = (Task) this.mTasks.get(i);
        int i3 = task.mAffiliatedTaskId;
        if (task.mTaskId == i3 && task.mPrevAffiliate == null && task.mNextAffiliate == null) {
            task.inRecents = true;
            return i + 1;
        }
        this.mTmpRecents.clear();
        for (int size = this.mTasks.size() - 1; size >= i; size--) {
            Task task2 = (Task) this.mTasks.get(size);
            if (task2.mAffiliatedTaskId == i3) {
                this.mTasks.remove(size);
                this.mTmpRecents.add(task2);
            }
        }
        Collections.sort(this.mTmpRecents, TASK_ID_COMPARATOR);
        Task task3 = (Task) this.mTmpRecents.get(0);
        task3.inRecents = true;
        if (task3.mNextAffiliate != null) {
            Slog.w("ActivityTaskManager", "Link error 1 first.next=" + task3.mNextAffiliate);
            task3.setNextAffiliate(null);
            notifyTaskPersisterLocked(task3, false);
        }
        int size2 = this.mTmpRecents.size();
        int i4 = 0;
        while (true) {
            i2 = size2 - 1;
            if (i4 >= i2) {
                break;
            }
            Task task4 = (Task) this.mTmpRecents.get(i4);
            i4++;
            Task task5 = (Task) this.mTmpRecents.get(i4);
            if (task4.mPrevAffiliate != task5) {
                Slog.w("ActivityTaskManager", "Link error 2 next=" + task4 + " prev=" + task4.mPrevAffiliate + " setting prev=" + task5);
                task4.setPrevAffiliate(task5);
                notifyTaskPersisterLocked(task4, false);
            }
            if (task5.mNextAffiliate != task4) {
                Slog.w("ActivityTaskManager", "Link error 3 prev=" + task5 + " next=" + task5.mNextAffiliate + " setting next=" + task4);
                task5.setNextAffiliate(task4);
                notifyTaskPersisterLocked(task5, false);
            }
            task5.inRecents = true;
        }
        Task task6 = (Task) this.mTmpRecents.get(i2);
        if (task6.mPrevAffiliate != null) {
            Slog.w("ActivityTaskManager", "Link error 4 last.prev=" + task6.mPrevAffiliate);
            task6.setPrevAffiliate(null);
            notifyTaskPersisterLocked(task6, false);
        }
        this.mTasks.addAll(i, this.mTmpRecents);
        this.mTmpRecents.clear();
        return i + size2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x003e, code lost:
    
        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + r7 + ": first task has next affiliate: " + r10);
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x006e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean moveAffiliatedTasksToFront(Task task, int i) {
        Task task2;
        int size = this.mTasks.size();
        Task task3 = task;
        int i2 = i;
        while (true) {
            Task task4 = task3.mNextAffiliate;
            if (task4 == null || i2 <= 0) {
                break;
            }
            i2--;
            task3 = task4;
        }
        boolean z = task3.mAffiliatedTaskId == task.mAffiliatedTaskId;
        Task task5 = task3;
        int i3 = i2;
        while (true) {
            if (i3 >= size) {
                break;
            }
            task2 = (Task) this.mTasks.get(i3);
            if (task2 == task3) {
                if (task2.mNextAffiliate != null || task2.mNextAffiliateTaskId != -1) {
                    break;
                }
                if (task2.mPrevAffiliateTaskId != -1) {
                    if (task2.mPrevAffiliate != null) {
                        Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": last task " + task2 + " has previous affiliate " + task2.mPrevAffiliate);
                    }
                } else {
                    if (task2.mPrevAffiliate == null) {
                        Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": task " + task2 + " has previous affiliate " + task2.mPrevAffiliate + " but should be id " + task2.mPrevAffiliate);
                        break;
                    }
                    if (task2.mAffiliatedTaskId != task.mAffiliatedTaskId) {
                        Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": task " + task2 + " has affiliated id " + task2.mAffiliatedTaskId + " but should be " + task.mAffiliatedTaskId);
                        break;
                    }
                    i3++;
                    if (i3 >= size) {
                        Slog.wtf("ActivityTaskManager", "Bad chain ran off index " + i3 + ": last task " + task2);
                        break;
                    }
                    task5 = task2;
                }
            } else {
                if (task2.mNextAffiliate != task5 || task2.mNextAffiliateTaskId != task5.mTaskId) {
                    break;
                }
                if (task2.mPrevAffiliateTaskId != -1) {
                }
            }
        }
        Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": middle task " + task2 + " @" + i3 + " has bad next affiliate " + task2.mNextAffiliate + " id " + task2.mNextAffiliateTaskId + ", expected " + task5);
        z = false;
        if (z && i3 < i) {
            Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": did not extend to task " + task + " @" + i);
            z = false;
        }
        if (!z) {
            return false;
        }
        for (int i4 = i2; i4 <= i3; i4++) {
            this.mTasks.add(i4 - i2, (Task) this.mTasks.remove(i4));
        }
        return true;
    }

    public void dump(PrintWriter printWriter, boolean z, String str) {
        int i;
        int i2;
        printWriter.println("ACTIVITY MANAGER RECENT TASKS (dumpsys activity recents)");
        printWriter.println("mRecentsUid=" + this.mRecentsUid);
        printWriter.println("mRecentsComponent=" + this.mRecentsComponent);
        printWriter.println("mRecentsUidForDeX=" + this.mRecentsUidForDeX);
        printWriter.println("mRecentsComponentForDeX=" + this.mRecentsComponentForDeX);
        printWriter.println("mFreezeTaskListReordering=" + this.mFreezeTaskListReordering);
        printWriter.println("mFreezeTaskListReorderingPendingTimeout=" + this.mService.f1734mH.hasCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable));
        if (!this.mHiddenTasks.isEmpty()) {
            printWriter.println("mHiddenTasks=" + this.mHiddenTasks);
        }
        if (this.mTasks.isEmpty()) {
            return;
        }
        int size = this.mTasks.size();
        boolean z2 = false;
        boolean z3 = false;
        for (0; i < size; i + 1) {
            Task task = (Task) this.mTasks.get(i);
            if (str != null) {
                Intent intent = task.intent;
                boolean z4 = (intent == null || intent.getComponent() == null || !str.equals(task.intent.getComponent().getPackageName())) ? false : true;
                if (!z4) {
                    Intent intent2 = task.affinityIntent;
                    z4 |= (intent2 == null || intent2.getComponent() == null || !str.equals(task.affinityIntent.getComponent().getPackageName())) ? false : true;
                }
                if (!z4) {
                    ComponentName componentName = task.origActivity;
                    z4 |= componentName != null && str.equals(componentName.getPackageName());
                }
                if (!z4) {
                    ComponentName componentName2 = task.realActivity;
                    z4 |= componentName2 != null && str.equals(componentName2.getPackageName());
                }
                if (!z4) {
                    z4 |= str.equals(task.mCallingPackage);
                }
                i = z4 ? 0 : i + 1;
            }
            if (!z2) {
                printWriter.println("  Recent tasks:");
                z2 = true;
                z3 = true;
            }
            printWriter.print("  * Recent #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(task);
            if (z) {
                task.dump(printWriter, "    ");
            }
        }
        if (this.mHasVisibleRecentTasks) {
            ArrayList recentTasksImpl = getRecentTasksImpl(Integer.MAX_VALUE, 0, true, this.mService.getCurrentUserId(), 1000);
            boolean z5 = false;
            for (0; i2 < recentTasksImpl.size(); i2 + 1) {
                ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) recentTasksImpl.get(i2);
                if (str != null) {
                    Intent intent3 = recentTaskInfo.baseIntent;
                    boolean z6 = (intent3 == null || intent3.getComponent() == null || !str.equals(recentTaskInfo.baseIntent.getComponent().getPackageName())) ? false : true;
                    if (!z6) {
                        ComponentName componentName3 = recentTaskInfo.baseActivity;
                        z6 |= componentName3 != null && str.equals(componentName3.getPackageName());
                    }
                    if (!z6) {
                        ComponentName componentName4 = recentTaskInfo.topActivity;
                        z6 |= componentName4 != null && str.equals(componentName4.getPackageName());
                    }
                    if (!z6) {
                        ComponentName componentName5 = recentTaskInfo.origActivity;
                        z6 |= componentName5 != null && str.equals(componentName5.getPackageName());
                    }
                    if (!z6) {
                        ComponentName componentName6 = recentTaskInfo.realActivity;
                        z6 |= componentName6 != null && str.equals(componentName6.getPackageName());
                    }
                    i2 = z6 ? 0 : i2 + 1;
                }
                if (!z5) {
                    if (z3) {
                        printWriter.println();
                    }
                    printWriter.println("  Visible recent tasks (most recent first):");
                    z5 = true;
                    z3 = true;
                }
                printWriter.print("  * RecentTaskInfo #");
                printWriter.print(i2);
                printWriter.print(": ");
                recentTaskInfo.dump(printWriter, "    ");
            }
        }
        if (CoreRune.FW_DEDICATED_MEMORY) {
            printWriter.println();
            int size2 = this.mUserToProcs.size();
            boolean z7 = false;
            for (int i3 = 0; i3 < size2; i3++) {
                if (i3 == 0) {
                    printWriter.print("  Dedicated processes:");
                    z3 = true;
                }
                int keyAt = this.mUserToProcs.keyAt(i3);
                HashMap hashMap = (HashMap) this.mUserToProcs.valueAt(i3);
                if (!hashMap.isEmpty()) {
                    printWriter.println();
                    printWriter.print("    #");
                    printWriter.print(keyAt);
                    printWriter.print(": ");
                    boolean z8 = true;
                    for (String str2 : hashMap.keySet()) {
                        if (z8) {
                            z8 = false;
                        } else {
                            printWriter.print(", ");
                        }
                        String str3 = (String) hashMap.get(str2);
                        if (str2.equals(str3)) {
                            printWriter.print(str2);
                        } else {
                            printWriter.print("(");
                            printWriter.print(str2);
                            printWriter.print(", ");
                            printWriter.print(str3);
                            printWriter.print(")");
                        }
                    }
                    z7 = true;
                }
            }
            if (!z7) {
                printWriter.print("(nothing)");
            }
            printWriter.println();
        }
        if (z3) {
            return;
        }
        printWriter.println("  (nothing)");
    }

    public ActivityManager.RecentTaskInfo createRecentTaskInfo(Task task, boolean z, boolean z2) {
        TaskDisplayArea defaultTaskDisplayArea;
        ActivityManager.RecentTaskInfo recentTaskInfo = new ActivityManager.RecentTaskInfo();
        if (task.isAttached()) {
            defaultTaskDisplayArea = task.getDisplayArea();
        } else {
            defaultTaskDisplayArea = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea();
        }
        task.fillTaskInfo(recentTaskInfo, z, defaultTaskDisplayArea);
        recentTaskInfo.id = recentTaskInfo.isRunning ? recentTaskInfo.taskId : -1;
        recentTaskInfo.persistentId = recentTaskInfo.taskId;
        recentTaskInfo.lastSnapshotData.set(task.mLastTaskSnapshotData);
        if (!z2) {
            Task.trimIneffectiveInfo(task, recentTaskInfo);
        }
        if (task.mCreatedByOrganizer) {
            for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
                Task asTask = task.getChildAt(childCount).asTask();
                if (asTask != null && asTask.isOrganized()) {
                    ActivityManager.RecentTaskInfo recentTaskInfo2 = new ActivityManager.RecentTaskInfo();
                    asTask.fillTaskInfo(recentTaskInfo2, true, defaultTaskDisplayArea);
                    recentTaskInfo.childrenTaskInfos.add(recentTaskInfo2);
                }
            }
        }
        return recentTaskInfo;
    }

    public final boolean hasCompatibleActivityTypeAndWindowingMode(Task task, Task task2) {
        int activityType = task.getActivityType();
        int windowingMode = task.getWindowingMode();
        boolean z = activityType == 0;
        boolean z2 = windowingMode == 0;
        int activityType2 = task2.getActivityType();
        int windowingMode2 = task2.getWindowingMode();
        return (activityType == activityType2 || z || (activityType2 == 0)) && ((windowingMode == windowingMode2 || z2 || (windowingMode2 == 0)) || (task2.topRunningActivityLocked() == null));
    }

    public class UserToProcMap extends SparseArray {
        @Override // android.util.SparseArray
        public HashMap get(int i) {
            HashMap hashMap = (HashMap) super.get(i);
            if (hashMap != null) {
                return hashMap;
            }
            HashMap hashMap2 = new HashMap();
            super.put(i, hashMap2);
            return hashMap2;
        }
    }

    public void dedicateToIfNeeded(Task task, String str) {
        if (isDedicatedProcess(task.mUserId, task.mHostProcessName)) {
            dedicateTo(task, true);
            return;
        }
        if (task.mDedicatedTask) {
            Slog.v("ActivityTaskManager", KnoxAnalyticsDataConverter.TIMESTAMP + task.mTaskId + " hostprocess " + str + " changed to " + task.mHostProcessName);
            task.mDedicatedTask = false;
            this.mService.notifyDedicated(task.mTaskId);
        }
    }

    public final boolean isSystemProcessName(String str) {
        return "com.android.systemui".equals(str) || "system:ui".equals(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b9, code lost:
    
        if (r2.remove(r0) != null) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dedicateTo(Task task, boolean z) {
        String rootProcessName = task.getRootProcessName();
        Slog.d("ActivityTaskManager", "dedicateTo " + task.mTaskId + "," + z + ", " + rootProcessName);
        if (z && TextUtils.isEmpty(rootProcessName)) {
            return;
        }
        ComponentName componentName = task.realActivity;
        String packageName = componentName != null ? componentName.getPackageName() : null;
        if (packageName == null) {
            Slog.e("ActivityTaskManager", "dedicateTo: pkgName is null");
            return;
        }
        int size = this.mTasks.size();
        for (int i = 0; i < size; i++) {
            Task task2 = (Task) this.mTasks.get(i);
            if (z != task2.mDedicatedTask && task2.mUserId == task.mUserId && rootProcessName.equals(task2.mHostProcessName)) {
                task2.mDedicatedTask = z;
                notifyTaskPersisterLocked(task2, false);
                this.mService.notifyDedicated(task2.mTaskId);
            }
        }
        if (z != task.mDedicatedTask) {
            task.mDedicatedTask = z;
            notifyTaskPersisterLocked(task, false);
            this.mService.notifyDedicated(task.mTaskId);
        }
        HashMap hashMap = this.mUserToProcs.get(task.mUserId);
        boolean z2 = true;
        if (z) {
            if (isSystemProcessName(rootProcessName) && !hashMap.containsKey(packageName)) {
                hashMap.put(packageName, packageName);
            } else {
                if (!hashMap.containsKey(rootProcessName)) {
                    hashMap.put(rootProcessName, packageName);
                }
                z2 = false;
            }
        } else if (TextUtils.isEmpty(rootProcessName)) {
            if (hashMap.containsValue(packageName)) {
                for (Map.Entry entry : hashMap.entrySet()) {
                    if (packageName.equals(entry.getValue())) {
                        hashMap.remove(entry.getKey());
                        break;
                    }
                }
            }
            z2 = false;
        }
        if (z2) {
            this.mTaskPersister.saveDedicatedProcessName(this.mUserToProcs.get(task.mUserId), false, task.mUserId);
        }
    }

    public void removeDedicatedProcessFromPackage(String str, int i) {
        HashMap hashMap = this.mUserToProcs.get(i);
        if (hashMap.containsValue(str)) {
            Slog.d("ActivityTaskManager", "remove dedicated process of " + str);
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (String str2 : hashMap.keySet()) {
                if (((String) hashMap.get(str2)).equals(str)) {
                    arrayList.add(str2);
                    z = true;
                }
            }
            if (z) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    hashMap.remove((String) it.next());
                }
                this.mTaskPersister.saveDedicatedProcessName(hashMap, false, i);
            }
        }
    }

    public int getDedicatedProcessCount(int i) {
        return this.mUserToProcs.get(i).size();
    }

    public ArrayList getDedicatedProcesses(int i) {
        if (i == -1) {
            ArrayList arrayList = new ArrayList();
            int size = this.mUserToProcs.size();
            for (int i2 = 0; i2 < size; i2++) {
                UserToProcMap userToProcMap = this.mUserToProcs;
                arrayList.addAll(userToProcMap.get(userToProcMap.keyAt(i2)).keySet());
            }
            return arrayList;
        }
        return new ArrayList(this.mUserToProcs.get(i).keySet());
    }

    public ArrayList getDedicatedTaskIdsLocked(int i) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mTasks.iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            if (task.mDedicatedTask && (i == -1 || i == task.mUserId)) {
                arrayList.add(Integer.valueOf(task.mTaskId));
            }
        }
        if (i != -1 && getDedicatedProcessCount(i) != arrayList.size()) {
            cleanUpPackageNotInTasks(i);
        }
        return arrayList;
    }

    public final void cleanUpPackageNotInTasks(final int i) {
        final ArrayList arrayList = new ArrayList();
        this.mUserToProcs.get(i).keySet().forEach(new Consumer() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((String) obj);
            }
        });
        this.mTasks.stream().filter(new Predicate() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$cleanUpPackageNotInTasks$3;
                lambda$cleanUpPackageNotInTasks$3 = RecentTasks.lambda$cleanUpPackageNotInTasks$3(i, (Task) obj);
                return lambda$cleanUpPackageNotInTasks$3;
            }
        }).map(new Function() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Task) obj).getRootProcessName();
            }
        }).forEach(new Consumer() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.remove((String) obj);
            }
        });
        arrayList.forEach(new Consumer() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RecentTasks.this.lambda$cleanUpPackageNotInTasks$5(i, (String) obj);
            }
        });
    }

    public static /* synthetic */ boolean lambda$cleanUpPackageNotInTasks$3(int i, Task task) {
        return task.mDedicatedTask && i == task.mUserId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanUpPackageNotInTasks$5(int i, String str) {
        removeDedicatedProcessFromPackage((String) this.mUserToProcs.get(i).get(str), i);
    }

    public boolean isDedicatedProcess(int i, String str) {
        return this.mUserToProcs.get(i).containsKey(str);
    }

    public boolean okToRemove(final Task task) {
        if (!task.mDedicatedTask || TextUtils.isEmpty(task.mHostProcessName)) {
            return true;
        }
        return this.mTasks.stream().anyMatch(new Predicate() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$okToRemove$6;
                lambda$okToRemove$6 = RecentTasks.lambda$okToRemove$6(Task.this, (Task) obj);
                return lambda$okToRemove$6;
            }
        });
    }

    public static /* synthetic */ boolean lambda$okToRemove$6(Task task, Task task2) {
        return task.mTaskId != task2.mTaskId && task.mUserId == task2.mUserId && task.mHostProcessName.equals(task2.mHostProcessName);
    }

    public void adjustTopExcludeFromRecentTaskOrder(Task task) {
        if ((((task.getBaseIntent() != null ? task.getBaseIntent().getFlags() : 0) & 8388608) == 8388608) && this.mTasks.contains(task) && ((Task) this.mTasks.get(0)).mTaskId != task.mTaskId) {
            this.mTasks.remove(task);
            this.mTasks.add(0, task);
        }
    }
}
