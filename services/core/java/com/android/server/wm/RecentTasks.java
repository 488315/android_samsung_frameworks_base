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
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
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
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.ContentDispatcher;
import com.android.server.pm.PackageManagerService;
import com.android.server.wm.RecentTasks;
import com.android.server.wm.TaskPersister;
import com.google.android.collect.Sets;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.localservice.ApplicationPolicyInternal;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class RecentTasks {
    public long mActiveTasksSessionDurationMs;
    public final ArrayList mCallbacks;
    public boolean mCheckTrimmableTasksOnIdle;
    public boolean mFreezeTaskListReordering;
    public long mFreezeTaskListTimeoutMs;
    public int mGlobalMaxNumTasks;
    public final boolean mHasVisibleRecentTasks;
    public final ArrayList mHiddenTasks;
    public int mLauncherInfo;
    public final AnonymousClass1 mListener;
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
    public final TaskChangeNotificationController mTaskNotificationController;
    public final TaskPersister mTaskPersister;
    public final ArrayList mTasks;
    public final HashMap mTmpAvailActCache;
    public final HashMap mTmpAvailAppCache;
    public final SparseBooleanArray mTmpQuietProfileUserIds;
    public final ArrayList mTmpRecents;
    public final Rect mTmpRect;
    public final UserToProcMap mUserToProcs;
    public final SparseArray mUsersWithRecentsLoaded;
    public static final long FREEZE_TASK_LIST_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(5);
    public static final RecentTasks$$ExternalSyntheticLambda0 TASK_ID_COMPARATOR = new RecentTasks$$ExternalSyntheticLambda0();
    public static final ActivityInfo NO_ACTIVITY_INFO_TOKEN = new ActivityInfo();
    public static final ApplicationInfo NO_APPLICATION_INFO_TOKEN = new ApplicationInfo();

    /* renamed from: com.android.server.wm.RecentTasks$1, reason: invalid class name */
    public final class AnonymousClass1 implements WindowManagerPolicyConstants.PointerEventListener {
        public AnonymousClass1() {
        }

        public final void onPointerEvent(MotionEvent motionEvent) {
            if (RecentTasks.this.mFreezeTaskListReordering && motionEvent.getAction() == 0 && motionEvent.getClassification() != 4) {
                final int displayId = motionEvent.getDisplayId();
                final int x = (int) motionEvent.getX();
                final int y = (int) motionEvent.getY();
                ActivityTaskManagerService activityTaskManagerService = RecentTasks.this.mService;
                if (!activityTaskManagerService.mGesutreHintOn) {
                    InsetsState insetsState = activityTaskManagerService.mRootWindowContainer.getDisplayContent(displayId).mDisplayContent.mInsetsStateController.mState;
                    Rect rect = new Rect(insetsState.getDisplayFrame());
                    rect.inset(insetsState.calculateInsets(rect, WindowInsets.Type.mandatorySystemGestures(), true));
                    if (y >= rect.bottom) {
                        Slog.d("ActivityTaskManager", "skip PointerEvent to keep mFreezeTaskListReordering");
                        return;
                    }
                }
                RecentTasks.this.mService.mH.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.wm.RecentTasks$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RecentTasks.AnonymousClass1 anonymousClass1 = RecentTasks.AnonymousClass1.this;
                        int i = displayId;
                        int i2 = x;
                        int i3 = y;
                        WindowManagerGlobalLock windowManagerGlobalLock = RecentTasks.this.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                final DisplayContent displayContent = RecentTasks.this.mService.mRootWindowContainer.getDisplayContent(i).mDisplayContent;
                                displayContent.getClass();
                                final int i4 = i2;
                                final int i5 = i3;
                                WindowState window = displayContent.getWindow(new Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda60
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj2) {
                                        DisplayContent displayContent2 = DisplayContent.this;
                                        int i6 = i4;
                                        int i7 = i5;
                                        WindowState windowState = (WindowState) obj2;
                                        displayContent2.getClass();
                                        int i8 = windowState.mAttrs.flags;
                                        if (!windowState.isVisible() || (i8 & 16) != 0) {
                                            return false;
                                        }
                                        windowState.getVisibleBounds(displayContent2.mTmpRect);
                                        if (!displayContent2.mTmpRect.contains(i6, i7)) {
                                            return false;
                                        }
                                        if (displayContent2.hasOneHandOpSpec() && windowState.mAttrs.type == 2600) {
                                            return false;
                                        }
                                        windowState.getTouchableRegion(displayContent2.mTmpRegion);
                                        return displayContent2.mTmpRegion.contains(i6, i7) || (i8 & 40) == 0;
                                    }
                                });
                                if (window == null) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                InsetsState insetsState2 = displayContent.mInsetsStateController.mState;
                                RecentTasks.this.mTmpRect.set(window.mWindowFrames.mFrame);
                                RecentTasks.this.mTmpRect.inset(insetsState2.calculateInsets(window.mWindowFrames.mFrame, WindowInsets.Type.mandatorySystemGestures(), false));
                                if (!RecentTasks.this.mTmpRect.contains(i2, i3)) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                int i6 = window.mAttrs.type;
                                if (1 <= i6 && i6 <= 99) {
                                    Task topDisplayFocusedRootTask = RecentTasks.this.mService.mRootWindowContainer.getTopDisplayFocusedRootTask();
                                    Task topMostTask = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopMostTask() : null;
                                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[2]) {
                                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_TASKS, -1640401313436844534L, 20, null, String.valueOf(window), Long.valueOf(i2), Long.valueOf(i3), String.valueOf(RecentTasks.this.mTmpRect));
                                    }
                                    RecentTasks.this.resetFreezeTaskListReordering(topMostTask);
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                    }
                }, (Object) null).recycleOnUse());
            }
        }
    }

    public interface Callbacks {
    }

    public final class UserToProcMap extends SparseArray {
        @Override // android.util.SparseArray
        public final HashMap get(int i) {
            HashMap hashMap = (HashMap) super.get(i);
            if (hashMap != null) {
                return hashMap;
            }
            HashMap hashMap2 = new HashMap();
            put(i, hashMap2);
            return hashMap2;
        }
    }

    public RecentTasks(ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor) {
        this.mRecentsUid = -1;
        this.mRecentsComponent = null;
        this.mRecentsUidForDeX = -1;
        this.mRecentsComponentForDeX = null;
        this.mUsersWithRecentsLoaded = new SparseArray(5);
        this.mPersistedTaskIds = new SparseArray(5);
        this.mTasks = new ArrayList();
        this.mCallbacks = new ArrayList();
        this.mHiddenTasks = new ArrayList();
        this.mFreezeTaskListTimeoutMs = FREEZE_TASK_LIST_TIMEOUT_MS;
        this.mTmpRecents = new ArrayList();
        this.mTmpAvailActCache = new HashMap();
        this.mTmpAvailAppCache = new HashMap();
        this.mTmpQuietProfileUserIds = new SparseBooleanArray();
        this.mTmpRect = new Rect();
        this.mUserToProcs = new UserToProcMap();
        this.mLauncherInfo = -1;
        this.mListener = new AnonymousClass1();
        this.mResetFreezeTaskListOnTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda1
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
        this.mTaskNotificationController = activityTaskManagerService.mTaskChangeNotificationController;
        this.mHasVisibleRecentTasks = resources.getBoolean(R.bool.config_isDisplayHingeAlwaysSeparating);
        loadParametersFromResources(resources);
    }

    public RecentTasks(ActivityTaskManagerService activityTaskManagerService, TaskPersister taskPersister) {
        this.mRecentsUid = -1;
        this.mRecentsComponent = null;
        this.mRecentsUidForDeX = -1;
        this.mRecentsComponentForDeX = null;
        this.mUsersWithRecentsLoaded = new SparseArray(5);
        this.mPersistedTaskIds = new SparseArray(5);
        this.mTasks = new ArrayList();
        this.mCallbacks = new ArrayList();
        this.mHiddenTasks = new ArrayList();
        this.mFreezeTaskListTimeoutMs = FREEZE_TASK_LIST_TIMEOUT_MS;
        this.mTmpRecents = new ArrayList();
        this.mTmpAvailActCache = new HashMap();
        this.mTmpAvailAppCache = new HashMap();
        this.mTmpQuietProfileUserIds = new SparseBooleanArray();
        this.mTmpRect = new Rect();
        this.mUserToProcs = new UserToProcMap();
        this.mLauncherInfo = -1;
        this.mListener = new AnonymousClass1();
        this.mResetFreezeTaskListOnTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda1
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
        this.mTaskNotificationController = activityTaskManagerService.mTaskChangeNotificationController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x0256, code lost:
    
        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + r11 + ": middle task " + r15 + " @" + r11 + " has bad next affiliate " + r15.mNextAffiliate + " id " + r15.mNextAffiliateTaskId + ", expected " + r12);
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void add(com.android.server.wm.Task r17) {
        /*
            Method dump skipped, instructions count: 754
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RecentTasks.add(com.android.server.wm.Task):void");
    }

    public final void cleanupDisabledPackageTasksLocked(int i, String str, Set set) {
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (i == -1 || task.mUserId == i) {
                Intent intent = task.intent;
                ComponentName component = intent != null ? intent.getComponent() : null;
                if (component != null && component.getPackageName().equals(str)) {
                    if (set != null) {
                        if (!((ArraySet) set).contains(component.getClassName())) {
                        }
                    }
                    this.mSupervisor.removeTask(task, false, true, "disabled-package", false, 1000, -1);
                }
            }
        }
    }

    public final void cleanupLocked(int i) {
        int i2;
        int size = this.mTasks.size();
        if (size == 0) {
            return;
        }
        this.mTmpAvailActCache.clear();
        this.mTmpAvailAppCache.clear();
        IPackageManager packageManager = AppGlobals.getPackageManager();
        for (int i3 = size - 1; i3 >= 0; i3--) {
            Task task = (Task) this.mTasks.get(i3);
            if (i == -1 || task.mUserId == i) {
                if (task.autoRemoveRecents && task.getTopNonFinishingActivity(true, true) == null && (!CoreRune.FW_DEDICATED_MEMORY || okToRemove(task))) {
                    remove(task);
                    Slog.w("ActivityTaskManager", "Removing auto-remove without activity: " + task);
                } else {
                    ComponentName componentName = task.realActivity;
                    if (componentName != null) {
                        ActivityInfo activityInfo = (ActivityInfo) this.mTmpAvailActCache.get(componentName);
                        if (activityInfo == null) {
                            try {
                                activityInfo = packageManager.getActivityInfo(task.realActivity, 268436480L, i);
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
                                applicationInfo = packageManager.getApplicationInfo(task.realActivity.getPackageName(), 8192L, i);
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
        }
        int size2 = this.mTasks.size();
        int i4 = 0;
        while (i4 < size2) {
            Task task2 = (Task) this.mTasks.get(i4);
            int i5 = task2.mAffiliatedTaskId;
            if (task2.mTaskId == i5 && task2.mPrevAffiliate == null && task2.mNextAffiliate == null) {
                task2.inRecents = true;
                i4++;
            } else {
                this.mTmpRecents.clear();
                for (int size3 = this.mTasks.size() - 1; size3 >= i4; size3--) {
                    Task task3 = (Task) this.mTasks.get(size3);
                    if (task3.mAffiliatedTaskId == i5) {
                        this.mTasks.remove(size3);
                        this.mTmpRecents.add(task3);
                    }
                }
                Collections.sort(this.mTmpRecents, TASK_ID_COMPARATOR);
                Task task4 = (Task) this.mTmpRecents.get(0);
                task4.inRecents = true;
                if (task4.mNextAffiliate != null) {
                    Slog.w("ActivityTaskManager", "Link error 1 first.next=" + task4.mNextAffiliate);
                    task4.setNextAffiliate(null);
                    notifyTaskPersisterLocked(task4, false);
                }
                int size4 = this.mTmpRecents.size();
                int i6 = 0;
                while (true) {
                    i2 = size4 - 1;
                    if (i6 >= i2) {
                        break;
                    }
                    Task task5 = (Task) this.mTmpRecents.get(i6);
                    i6++;
                    Task task6 = (Task) this.mTmpRecents.get(i6);
                    if (task5.mPrevAffiliate != task6) {
                        Slog.w("ActivityTaskManager", "Link error 2 next=" + task5 + " prev=" + task5.mPrevAffiliate + " setting prev=" + task6);
                        task5.setPrevAffiliate(task6);
                        notifyTaskPersisterLocked(task5, false);
                    }
                    if (task6.mNextAffiliate != task5) {
                        Slog.w("ActivityTaskManager", "Link error 3 prev=" + task6 + " next=" + task6.mNextAffiliate + " setting next=" + task5);
                        task6.setNextAffiliate(task5);
                        notifyTaskPersisterLocked(task6, false);
                    }
                    task6.inRecents = true;
                }
                Task task7 = (Task) this.mTmpRecents.get(i2);
                if (task7.mPrevAffiliate != null) {
                    Slog.w("ActivityTaskManager", "Link error 4 last.prev=" + task7.mPrevAffiliate);
                    task7.setPrevAffiliate(null);
                    notifyTaskPersisterLocked(task7, false);
                }
                this.mTasks.addAll(i4, this.mTmpRecents);
                this.mTmpRecents.clear();
                i4 += size4;
            }
        }
    }

    public final void clearRecentTasksLocked(int i) {
        ComponentName componentName;
        int size = this.mTasks.size();
        int i2 = 0;
        while (i2 < size) {
            Task task = (Task) this.mTasks.get(i2);
            if (task.mUserId == i) {
                if (CoreRune.FW_DEDICATED_MEMORY && task.mDedicatedTask && (componentName = task.realActivity) != null) {
                    task.mDedicatedTask = false;
                    removeDedicatedProcessFromPackage(i, componentName.getPackageName());
                }
                this.mTasks.remove(i2);
                notifyTaskRemoved(task, false, false);
                i2--;
                size--;
            }
            i2++;
        }
    }

    public final ActivityManager.RecentTaskInfo createRecentTaskInfo(Task task, boolean z, boolean z2) {
        ActivityManager.RecentTaskInfo recentTaskInfo = new ActivityManager.RecentTaskInfo();
        TaskDisplayArea displayArea = task.isAttached() ? task.getDisplayArea() : this.mService.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
        task.fillTaskInfo(recentTaskInfo, z, displayArea);
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
                    asTask.fillTaskInfo(recentTaskInfo2, true, displayArea);
                    recentTaskInfo.childrenTaskInfos.add(recentTaskInfo2);
                }
            }
        }
        return recentTaskInfo;
    }

    public final void dedicateTo(Task task, boolean z) {
        ActivityTaskManagerService activityTaskManagerService;
        WindowProcessController windowProcessController = task.mRootProcess;
        if (windowProcessController != null) {
            task.mHostProcessName = windowProcessController.mName;
        }
        String str = task.mHostProcessName;
        StringBuilder sb = new StringBuilder("dedicateTo ");
        sb.append(task.mTaskId);
        sb.append(",");
        sb.append(z);
        sb.append(", ");
        BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "ActivityTaskManager");
        if (z && TextUtils.isEmpty(str)) {
            return;
        }
        ComponentName componentName = task.realActivity;
        String packageName = componentName != null ? componentName.getPackageName() : null;
        if (packageName == null) {
            Slog.e("ActivityTaskManager", "dedicateTo: pkgName is null");
            return;
        }
        int size = this.mTasks.size();
        int i = 0;
        while (true) {
            activityTaskManagerService = this.mService;
            if (i >= size) {
                break;
            }
            Task task2 = (Task) this.mTasks.get(i);
            if (z != task2.mDedicatedTask && task2.mUserId == task.mUserId && str.equals(task2.mHostProcessName)) {
                task2.mDedicatedTask = z;
                notifyTaskPersisterLocked(task2, false);
                activityTaskManagerService.notifyDedicated(task2.mTaskId);
            }
            i++;
        }
        if (z != task.mDedicatedTask) {
            task.mDedicatedTask = z;
            notifyTaskPersisterLocked(task, false);
            activityTaskManagerService.notifyDedicated(task.mTaskId);
        }
        int i2 = task.mUserId;
        UserToProcMap userToProcMap = this.mUserToProcs;
        HashMap hashMap = userToProcMap.get(i2);
        if (!z) {
            if (TextUtils.isEmpty(str)) {
                if (hashMap.containsValue(packageName)) {
                    for (Map.Entry entry : hashMap.entrySet()) {
                        if (packageName.equals(entry.getValue())) {
                            hashMap.remove(entry.getKey());
                        }
                    }
                    return;
                }
                return;
            }
            if (hashMap.remove(str) == null) {
                return;
            }
        } else if ((Constants.SYSTEMUI_PACKAGE_NAME.equals(str) || "system:ui".equals(str)) && !hashMap.containsKey(packageName)) {
            hashMap.put(packageName, packageName);
        } else if (hashMap.containsKey(str)) {
            return;
        } else {
            hashMap.put(str, packageName);
        }
        this.mTaskPersister.saveDedicatedProcessName(task.mUserId, userToProcMap.get(task.mUserId));
    }

    public final void dump(PrintWriter printWriter, String str, boolean z) {
        int i;
        int i2;
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "ACTIVITY MANAGER RECENT TASKS (dumpsys activity recents)", "mRecentsUid="), this.mRecentsUid, printWriter, "mRecentsComponent=");
        m.append(this.mRecentsComponent);
        printWriter.println(m.toString());
        StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mRecentsUidForDeX="), this.mRecentsUidForDeX, printWriter, "mRecentsComponentForDeX=");
        m2.append(this.mRecentsComponentForDeX);
        printWriter.println(m2.toString());
        StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mFreezeTaskListReordering="), this.mFreezeTaskListReordering, printWriter, "mFreezeTaskListReorderingPendingTimeout=");
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        m3.append(activityTaskManagerService.mH.hasCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable));
        printWriter.println(m3.toString());
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
            ArrayList recentTasksImpl = getRecentTasksImpl(Integer.MAX_VALUE, 0, activityTaskManagerService.mAmInternal.getCurrentUserId(), 1000, true);
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
            UserToProcMap userToProcMap = this.mUserToProcs;
            int size2 = userToProcMap.size();
            boolean z7 = false;
            for (int i3 = 0; i3 < size2; i3++) {
                if (i3 == 0) {
                    printWriter.print("  Dedicated processes:");
                    z3 = true;
                }
                int keyAt = userToProcMap.keyAt(i3);
                HashMap hashMap = (HashMap) userToProcMap.valueAt(i3);
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

    public final int findRemoveIndexForAddTask(Task task) {
        ComponentName componentName;
        int size = this.mTasks.size();
        Intent intent = task.intent;
        boolean z = intent != null && intent.isDocument();
        int i = task.maxRecents - 1;
        for (int i2 = 0; i2 < size; i2++) {
            Task task2 = (Task) this.mTasks.get(i2);
            if (task != task2) {
                int activityType = task.getActivityType();
                int windowingMode = task.getWindowingMode();
                boolean z2 = activityType == 0;
                boolean z3 = windowingMode == 0;
                int activityType2 = task2.getActivityType();
                int windowingMode2 = task2.getWindowingMode();
                boolean z4 = activityType2 == 0;
                boolean z5 = windowingMode2 == 0;
                boolean z6 = activityType == activityType2 || z2 || z4;
                boolean z7 = windowingMode == windowingMode2 || z3 || z5;
                boolean z8 = task2.topRunningActivityLocked() == null;
                if (z6 && ((z7 || z8) && task.mUserId == task2.mUserId)) {
                    Intent intent2 = task2.intent;
                    String str = task.affinity;
                    boolean z9 = str != null && str.equals(task2.affinity);
                    boolean z10 = intent != null && intent.filterEquals(intent2);
                    int flags = intent != null ? intent.getFlags() : 0;
                    boolean z11 = ((flags & 268959744) == 0 || (flags & 134217728) == 0) ? false : true;
                    int flags2 = intent2 != null ? intent2.getFlags() : 0;
                    boolean z12 = ((flags2 & 268959744) == 0 || (flags2 & 134217728) == 0) ? false : true;
                    boolean z13 = intent2 != null && intent2.isDocument();
                    boolean z14 = z && z13;
                    if (z9 || z10 || z14) {
                        if (z14) {
                            ComponentName componentName2 = task.realActivity;
                            if (componentName2 != null && (componentName = task2.realActivity) != null && componentName2.equals(componentName)) {
                                if (i > 0) {
                                    i--;
                                    if (z10 && !z11) {
                                    }
                                }
                            }
                        } else if (z) {
                            continue;
                        } else if (z13) {
                            continue;
                        } else if (z11) {
                            continue;
                        } else if (z12) {
                            continue;
                        } else {
                            ActivityRecord activityRecord = task.topRunningActivity(false);
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

    public final ArrayList getAppTasksList(int i, String str) {
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

    public int[] getCurrentProfileIds() {
        return this.mService.mAmInternal.getCurrentProfileIds();
    }

    public final ArrayList getDedicatedTaskIdsLocked(final int i) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mTasks.iterator();
        while (it.hasNext()) {
            Task task = (Task) it.next();
            if (task.mDedicatedTask && (i == -1 || i == task.mUserId)) {
                arrayList.add(Integer.valueOf(task.mTaskId));
            }
        }
        if (i != -1) {
            UserToProcMap userToProcMap = this.mUserToProcs;
            if (userToProcMap.get(i).size() != arrayList.size()) {
                ArrayList arrayList2 = new ArrayList();
                userToProcMap.get(i).keySet().forEach(new RecentTasks$$ExternalSyntheticLambda4(0, arrayList2));
                this.mTasks.stream().filter(new Predicate() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        Task task2 = (Task) obj;
                        return task2.mDedicatedTask && i == task2.mUserId;
                    }
                }).map(new RecentTasks$$ExternalSyntheticLambda6()).forEach(new RecentTasks$$ExternalSyntheticLambda4(1, arrayList2));
                arrayList2.forEach(new Consumer() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RecentTasks recentTasks = RecentTasks.this;
                        int i2 = i;
                        recentTasks.removeDedicatedProcessFromPackage(i2, (String) recentTasks.mUserToProcs.get(i2).get((String) obj));
                    }
                });
            }
        }
        return arrayList;
    }

    public Set getProfileIds(int i) {
        ArraySet arraySet = new ArraySet();
        for (int i2 : this.mService.getUserManager().getProfileIds(null, false, false, i)) {
            arraySet.add(Integer.valueOf(i2));
        }
        return arraySet;
    }

    public ArrayList getRawTasks() {
        return this.mTasks;
    }

    public final SparseBooleanArray getRecentTaskIds() {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int size = this.mTasks.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Task task = (Task) this.mTasks.get(i2);
            if (isVisibleRecentTask(task)) {
                i++;
                if (isInVisibleRange(i2, i, task, false)) {
                    sparseBooleanArray.put(task.mTaskId, true);
                }
            }
        }
        return sparseBooleanArray;
    }

    public final ArrayList getRecentTasksImpl(int i, int i2, int i3, int i4, boolean z) {
        ActivityRecord rootActivity;
        boolean z2 = (i2 & 1) != 0;
        Set profileIds = getProfileIds(i3);
        profileIds.add(Integer.valueOf(i3));
        ArrayList arrayList = new ArrayList();
        int size = this.mTasks.size();
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            Task task = (Task) this.mTasks.get(i6);
            if (isVisibleRecentTask(task)) {
                i5++;
                if (isInVisibleRange(i6, i5, task, z2) && arrayList.size() < i && profileIds.contains(Integer.valueOf(task.mUserId)) && !task.realActivitySuspended && ((z || task.isActivityTypeHome() || task.effectiveUid == i4) && ((!task.autoRemoveRecents || task.getTopNonFinishingActivity(true, true) != null || (CoreRune.FW_DEDICATED_MEMORY && !okToRemove(task))) && ((i2 & 2) == 0 || task.isAvailable)))) {
                    if (!task.mUserSetupComplete) {
                        Slog.d("ActivityTaskManager", "Skipping, user setup not complete: " + task);
                    } else if ((i2 & 16) != 0 || (rootActivity = task.getRootActivity(false, true)) == null || !rootActivity.mIsAliasActivity) {
                        arrayList.add(createRecentTaskInfo(task, true, z));
                    }
                }
            }
        }
        return arrayList;
    }

    public final Task getTask(int i) {
        int size = this.mTasks.size();
        for (int i2 = 0; i2 < size; i2++) {
            Task task = (Task) this.mTasks.get(i2);
            if (task.mTaskId == i) {
                return task;
            }
        }
        return null;
    }

    public UserInfo getUserInfo(int i) {
        return this.mService.getUserManager().getUserInfo(i);
    }

    public final boolean isActiveRecentTask(Task task, SparseBooleanArray sparseBooleanArray) {
        Task task2;
        if (sparseBooleanArray.get(task.mUserId)) {
            return false;
        }
        int i = task.mAffiliatedTaskId;
        return i == -1 || i == task.mTaskId || (task2 = getTask(i)) == null || isActiveRecentTask(task2, sparseBooleanArray);
    }

    public final boolean isCallerRecents(int i) {
        return UserHandle.isSameApp(i, this.mRecentsUid) || UserHandle.isSameApp(i, this.mRecentsUidForDeX);
    }

    public boolean isFreezeTaskListReorderingSet() {
        return this.mFreezeTaskListReordering;
    }

    public final boolean isInVisibleRange(int i, int i2, Task task, boolean z) {
        if (!z && task.getBaseIntent() != null && (task.getBaseIntent().getFlags() & 8388608) == 8388608) {
            if (!task.inSplitScreenWindowingMode()) {
                return task.getDisplayId() == 0 && i == 0;
            }
            Task createdByOrganizerTask = task.getCreatedByOrganizerTask();
            return createdByOrganizerTask != null && createdByOrganizerTask.getTopMostTask().equals(task);
        }
        int i3 = this.mMinNumVisibleTasks;
        if ((i3 >= 0 && i2 <= i3) || task.mChildPipActivity != null) {
            return true;
        }
        int i4 = this.mMaxNumVisibleTasks;
        return i4 >= 0 ? i2 <= i4 : this.mActiveTasksSessionDurationMs <= 0 || SystemClock.elapsedRealtime() - task.lastActiveTime <= this.mActiveTasksSessionDurationMs;
    }

    public final boolean isRecentsComponentHomeActivity(int i) {
        ComponentName defaultHomeActivity = ((PackageManagerService.PackageManagerInternalImpl) this.mService.getPackageManagerInternalLocked()).mService.snapshotComputer().getDefaultHomeActivity(i);
        return (defaultHomeActivity == null || this.mRecentsComponent == null || (!defaultHomeActivity.getPackageName().equals(this.mRecentsComponent.getPackageName()) && (this.mRecentsComponentForDeX == null || !defaultHomeActivity.getPackageName().equals(this.mRecentsComponentForDeX.getPackageName())))) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000f, code lost:
    
        if (r0 != 5) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isVisibleRecentTask(com.android.server.wm.Task r5) {
        /*
            r4 = this;
            int r0 = r5.getActivityType()
            r1 = 2
            r2 = 0
            if (r0 == r1) goto L7d
            r3 = 3
            if (r0 == r3) goto L7d
            r3 = 4
            if (r0 == r3) goto L12
            r3 = 5
            if (r0 == r3) goto L7d
            goto L20
        L12:
            android.content.Intent r0 = r5.getBaseIntent()
            int r0 = r0.getFlags()
            r3 = 8388608(0x800000, float:1.17549435E-38)
            r0 = r0 & r3
            if (r0 != r3) goto L20
            return r2
        L20:
            int r0 = r5.getWindowingMode()
            if (r0 == r1) goto L7d
            r1 = 6
            if (r0 == r1) goto L2a
            goto L31
        L2a:
            boolean r0 = r5.isAlwaysOnTopWhenVisible()
            if (r0 == 0) goto L31
            return r2
        L31:
            com.android.server.wm.ActivityTaskManagerService r4 = r4.mService
            com.android.server.wm.LockTaskController r4 = r4.mLockTaskController
            java.util.ArrayList r0 = r4.mLockTaskModeTasks
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L3f
            r4 = 0
            goto L47
        L3f:
            java.util.ArrayList r4 = r4.mLockTaskModeTasks
            java.lang.Object r4 = r4.get(r2)
            com.android.server.wm.Task r4 = (com.android.server.wm.Task) r4
        L47:
            if (r5 != r4) goto L4a
            return r2
        L4a:
            com.android.server.wm.DisplayContent r4 = r5.getDisplayContent()
            r0 = 1
            if (r4 == 0) goto L67
            com.android.server.wm.DisplayContent r4 = r5.getDisplayContent()
            com.android.server.wm.DisplayWindowPolicyControllerHelper r4 = r4.mDwpcHelper
            if (r4 != 0) goto L5b
        L59:
            r4 = r0
            goto L64
        L5b:
            android.window.DisplayWindowPolicyController r4 = r4.mDisplayWindowPolicyController
            if (r4 != 0) goto L60
            goto L59
        L60:
            boolean r4 = r4.canShowTasksInHostDeviceRecents()
        L64:
            if (r4 != 0) goto L67
            return r2
        L67:
            boolean r4 = com.samsung.android.rune.CoreRune.BAIDU_CARLIFE
            if (r4 == 0) goto L7c
            com.android.server.wm.DisplayContent r4 = r5.getDisplayContent()
            if (r4 == 0) goto L7c
            com.android.server.wm.DisplayContent r4 = r5.getDisplayContent()
            boolean r4 = r4.isCarLifeDisplay()
            if (r4 == 0) goto L7c
            return r2
        L7c:
            return r0
        L7d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RecentTasks.isVisibleRecentTask(com.android.server.wm.Task):boolean");
    }

    public void loadParametersFromResources(Resources resources) {
        if (ActivityManager.isLowRamDeviceStatic()) {
            this.mMinNumVisibleTasks = resources.getInteger(R.integer.config_overrideHasPermanentMenuKey);
            this.mMaxNumVisibleTasks = resources.getInteger(R.integer.config_notificationsBatteryFullARGB);
        } else {
            this.mMinNumVisibleTasks = resources.getInteger(R.integer.config_oem_enabled_satellite_location_fresh_duration);
            this.mMaxNumVisibleTasks = resources.getInteger(R.integer.config_notificationWarnRemoteViewSizeBytes);
        }
        this.mActiveTasksSessionDurationMs = -1L;
    }

    public final void loadRecentTasksIfNeeded(int i) {
        AtomicBoolean atomicBoolean;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                atomicBoolean = (AtomicBoolean) this.mUsersWithRecentsLoaded.get(i);
                if (atomicBoolean == null) {
                    SparseArray sparseArray = this.mUsersWithRecentsLoaded;
                    AtomicBoolean atomicBoolean2 = new AtomicBoolean();
                    sparseArray.append(i, atomicBoolean2);
                    atomicBoolean = atomicBoolean2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        synchronized (atomicBoolean) {
            try {
                if (atomicBoolean.get()) {
                    return;
                }
                SparseBooleanArray readPersistedTaskIdsFromFileForUser = this.mTaskPersister.readPersistedTaskIdsFromFileForUser(i);
                TaskPersister.RecentTaskFiles loadTasksForUser = TaskPersister.loadTasksForUser(i);
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        restoreRecentTasksLocked(i, readPersistedTaskIdsFromFileForUser, loadTasksForUser);
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                atomicBoolean.set(true);
            } finally {
            }
        }
    }

    public final void notifyTaskAdded(Task task) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            ((ActivityTaskSupervisor) ((Callbacks) this.mCallbacks.get(i))).getClass();
            task.getClass();
            task.lastActiveTime = SystemClock.elapsedRealtime();
        }
        TaskChangeNotificationController taskChangeNotificationController = this.mTaskNotificationController;
        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(22);
        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskListUpdated, obtainMessage);
        obtainMessage.sendToTarget();
    }

    public final void notifyTaskPersisterLocked(Task task, boolean z) {
        Task rootTask = task != null ? task.getRootTask() : null;
        if (rootTask == null || !rootTask.isActivityTypeHomeOrRecents()) {
            syncPersistentTaskIdsLocked();
            TaskPersister taskPersister = this.mTaskPersister;
            synchronized (taskPersister.mPersisterQueue) {
                try {
                    if (task != null) {
                        TaskPersister.TaskWriteQueueItem taskWriteQueueItem = (TaskPersister.TaskWriteQueueItem) taskPersister.mPersisterQueue.findLastItem(new TaskPersister$$ExternalSyntheticLambda0(0, task), TaskPersister.TaskWriteQueueItem.class);
                        if (taskWriteQueueItem != null && !task.inRecents) {
                            taskPersister.mPersisterQueue.removeItems(new TaskPersister$$ExternalSyntheticLambda0(1, task), TaskPersister.ImageWriteQueueItem.class);
                        }
                        if (taskWriteQueueItem == null && task.isPersistable) {
                            taskPersister.mPersisterQueue.addItem(new TaskPersister.TaskWriteQueueItem(task, taskPersister.mService), z);
                        }
                    } else {
                        taskPersister.mPersisterQueue.addItem(PersisterQueue.EMPTY_ITEM, z);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            taskPersister.mPersisterQueue.yieldIfQueueTooDeep();
        }
    }

    public final void notifyTaskRemoved(Task task, boolean z, boolean z2) {
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository;
        boolean z3;
        if (CoreRune.FW_DEDICATED_MEMORY && task.mDedicatedTask) {
            this.mService.notifyDedicated(task.mTaskId);
        }
        final ContentDispatcher contentDispatcher = this.mService.mContentDispatcher;
        final int i = task.mTaskId;
        contentDispatcher.mHandler.post(new Runnable() { // from class: com.android.server.pm.ContentDispatcher$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ContentDispatcher.this.clearPdfTask(i);
            }
        });
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            ActivityTaskSupervisor activityTaskSupervisor = (ActivityTaskSupervisor) ((Callbacks) this.mCallbacks.get(i2));
            activityTaskSupervisor.getClass();
            if (z) {
                activityTaskSupervisor.removeTaskById(task.mTaskId, 1000, -1, "recent-task-trimmed", z2, false, false);
            }
            Task task2 = task.mPrevAffiliate;
            if (task2 != null) {
                task2.setNextAffiliate(task.mNextAffiliate);
            }
            Task task3 = task.mNextAffiliate;
            if (task3 != null) {
                task3.setPrevAffiliate(task.mPrevAffiliate);
            }
            task.setPrevAffiliate(null);
            task.setNextAffiliate(null);
            if (task.inRecents) {
                task.inRecents = false;
                task.mAtmService.notifyTaskPersisterLocked(task, false);
            }
            task.clearRootProcess();
            MultiWindowSupportPolicyController multiWindowSupportPolicyController = task.mAtmService.mMwSupportPolicyController;
            multiWindowSupportPolicyController.getClass();
            ComponentName componentName = task.realActivity;
            if (componentName != null && (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository = multiWindowSupportPolicyController.mBlockListRepository) != null) {
                String packageName = componentName.getPackageName();
                if (packageName == null) {
                    z3 = false;
                } else {
                    synchronized (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository.mLock) {
                        Set set = multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository.mDeferredPackages;
                        z3 = set != null && ((HashSet) set).remove(packageName);
                    }
                }
                if (z3) {
                    multiWindowSupportPolicyController.updateSupportPolicyLocked(null, task);
                }
            }
            task.mAtmService.mWindowManager.mTaskSnapshotController.removeAndDeleteSnapshot(task.mTaskId, task.mUserId);
        }
        TaskChangeNotificationController taskChangeNotificationController = this.mTaskNotificationController;
        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(22);
        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskListUpdated, obtainMessage);
        obtainMessage.sendToTarget();
    }

    public final boolean okToRemove(final Task task) {
        if (!task.mDedicatedTask || TextUtils.isEmpty(task.mHostProcessName)) {
            return true;
        }
        return this.mTasks.stream().anyMatch(new Predicate() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                Task task2 = Task.this;
                Task task3 = (Task) obj;
                return task2.mTaskId != task3.mTaskId && task2.mUserId == task3.mUserId && task2.mHostProcessName.equals(task3.mHostProcessName);
            }
        });
    }

    public final void onPackagesSuspendedChanged(int i, boolean z, String[] strArr) {
        HashSet newHashSet = Sets.newHashSet(strArr);
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            ComponentName componentName = task.realActivity;
            if (componentName != null && newHashSet.contains(componentName.getPackageName()) && task.mUserId == i && task.realActivitySuspended != z) {
                task.realActivitySuspended = z;
                if (z) {
                    this.mSupervisor.removeTask(task, false, true, "suspended-package", false, 1000, -1);
                }
                notifyTaskPersisterLocked(task, false);
            }
        }
    }

    public final void onSystemReadyLocked() {
        ComponentName unflattenFromString;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        Resources resources = activityTaskManagerService.mContext.getResources();
        if (CoreRune.MW_SA_LOGGING) {
            try {
                ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(KnoxCustomManagerService.LAUNCHER_PACKAGE, 0L, activityTaskManagerService.mContext.getUserId());
                if (applicationInfo != null) {
                    this.mLauncherInfo = applicationInfo.uid;
                }
            } catch (RemoteException unused) {
                Slog.w("ActivityTaskManager", "Could not load application info for recents edge");
            }
        }
        String string = resources.getString(R.string.ext_media_status_mounted);
        if (!TextUtils.isEmpty(string)) {
            ComponentName unflattenFromString2 = ComponentName.unflattenFromString(string);
            if (unflattenFromString2 != null) {
                try {
                    ApplicationInfo applicationInfo2 = AppGlobals.getPackageManager().getApplicationInfo(unflattenFromString2.getPackageName(), 8704L, activityTaskManagerService.mContext.getUserId());
                    if (applicationInfo2 != null) {
                        this.mRecentsUid = applicationInfo2.uid;
                        this.mRecentsComponent = unflattenFromString2;
                    }
                } catch (RemoteException unused2) {
                    Slog.w("ActivityTaskManager", "Could not load application info for recents component: " + unflattenFromString2);
                }
            }
            String string2 = resources.getString(R.string.ext_media_status_mounted_ro);
            if (!TextUtils.isEmpty(string2) && (unflattenFromString = ComponentName.unflattenFromString(string2)) != null) {
                try {
                    ApplicationInfo applicationInfo3 = AppGlobals.getPackageManager().getApplicationInfo(unflattenFromString.getPackageName(), 0L, activityTaskManagerService.mContext.getUserId());
                    if (applicationInfo3 != null) {
                        this.mRecentsUidForDeX = applicationInfo3.uid;
                        this.mRecentsComponentForDeX = unflattenFromString;
                    }
                } catch (RemoteException unused3) {
                    Slog.w("ActivityTaskManager", "Could not load application info for recents componentForDeX: " + unflattenFromString);
                }
            }
        }
        this.mTasks.clear();
    }

    public final void remove(Task task) {
        this.mTasks.remove(task);
        notifyTaskRemoved(task, false, false);
    }

    public final void removeAllVisibleTasks(int i) {
        Set profileIds = getProfileIds(i);
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTasks.get(size);
            if (profileIds.contains(Integer.valueOf(task.mUserId))) {
                ActivityRecord rootActivity = task.getRootActivity(true, false);
                String packageName = rootActivity != null ? rootActivity.packageName : (task.getBaseIntent() == null || task.getBaseIntent().getComponent() == null) ? null : task.getBaseIntent().getComponent().getPackageName();
                if ((packageName == null || packageName.isEmpty() || !((ApplicationPolicyInternal) LocalServices.getService(ApplicationPolicyInternal.class)).isApplicationStopDisabledAsUser(packageName, task.mUserId, (String) null, (String) null, (String) null, true)) && isVisibleRecentTask(task)) {
                    this.mTasks.remove(size);
                    notifyTaskRemoved(task, true, true);
                }
            }
        }
    }

    public final void removeDedicatedProcessFromPackage(int i, String str) {
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
                this.mTaskPersister.saveDedicatedProcessName(i, hashMap);
            }
        }
    }

    public final void resetFreezeTaskListReordering(Task task) {
        if (this.mFreezeTaskListReordering) {
            this.mFreezeTaskListReordering = false;
            this.mService.mH.removeCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable);
            if (task != null) {
                this.mTasks.remove(task);
                this.mTasks.add(0, task);
            }
            trimInactiveRecentTasks();
            TaskChangeNotificationController taskChangeNotificationController = this.mTaskNotificationController;
            taskChangeNotificationController.notifyTaskStackChanged();
            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(23, 0, 0);
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskListFrozen, obtainMessage);
            obtainMessage.sendToTarget();
        }
    }

    public void resetFreezeTaskListReorderingOnTimeout() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mService.mRootWindowContainer.getTopDisplayFocusedRootTask();
                Task task = null;
                Task topMostTask = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopMostTask() : null;
                if (topMostTask != null && topMostTask.hasChild()) {
                    task = topMostTask;
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_TASKS, 4040735335719974079L, 0, null, null);
                }
                resetFreezeTaskListReordering(task);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX WARN: Removed duplicated region for block: B:175:0x0205 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restoreRecentTasksLocked(int r26, android.util.SparseBooleanArray r27, com.android.server.wm.TaskPersister.RecentTaskFiles r28) {
        /*
            Method dump skipped, instructions count: 1151
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.RecentTasks.restoreRecentTasksLocked(int, android.util.SparseBooleanArray, com.android.server.wm.TaskPersister$RecentTaskFiles):void");
    }

    public final void setFreezeTaskListReordering() {
        if (!this.mFreezeTaskListReordering) {
            TaskChangeNotificationController taskChangeNotificationController = this.mTaskNotificationController;
            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(23, 1, 0);
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskListFrozen, obtainMessage);
            obtainMessage.sendToTarget();
            this.mFreezeTaskListReordering = true;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_TASKS, -8803811426486764449L, 0, null, null);
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        activityTaskManagerService.mH.removeCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable);
        activityTaskManagerService.mH.postDelayed(this.mResetFreezeTaskListOnTimeoutRunnable, this.mFreezeTaskListTimeoutMs);
    }

    public void setFreezeTaskListTimeout(long j) {
        this.mFreezeTaskListTimeoutMs = j;
    }

    public void setGlobalMaxNumTasks(int i) {
        this.mGlobalMaxNumTasks = i;
    }

    public void setParameters(int i, int i2, long j) {
        this.mMinNumVisibleTasks = i;
        this.mMaxNumVisibleTasks = i2;
        this.mActiveTasksSessionDurationMs = j;
    }

    public final void syncPersistentTaskIdsLocked() {
        for (int size = this.mPersistedTaskIds.size() - 1; size >= 0; size--) {
            AtomicBoolean atomicBoolean = (AtomicBoolean) this.mUsersWithRecentsLoaded.get(this.mPersistedTaskIds.keyAt(size));
            if (atomicBoolean != null && atomicBoolean.get()) {
                ((SparseBooleanArray) this.mPersistedTaskIds.valueAt(size)).clear();
            }
        }
        for (int size2 = this.mTasks.size() - 1; size2 >= 0; size2--) {
            Task task = (Task) this.mTasks.get(size2);
            Task rootTask = task.getRootTask();
            if (task.isPersistable && (rootTask == null || !rootTask.isActivityTypeHomeOrRecents())) {
                if (this.mPersistedTaskIds.get(task.mUserId) == null) {
                    Slog.wtf("ActivityTaskManager", "No task ids found for userId " + task.mUserId + ". task=" + task + " mPersistedTaskIds=" + this.mPersistedTaskIds);
                    this.mPersistedTaskIds.put(task.mUserId, new SparseBooleanArray());
                }
                ((SparseBooleanArray) this.mPersistedTaskIds.get(task.mUserId)).put(task.mTaskId, true);
            }
        }
    }

    public final void trimInactiveRecentTasks() {
        Task task;
        if (this.mFreezeTaskListReordering) {
            return;
        }
        for (int size = this.mTasks.size(); size > this.mGlobalMaxNumTasks; size--) {
            int i = size - 1;
            Task task2 = (Task) this.mTasks.get(i);
            if (!CoreRune.FW_DEDICATED_MEMORY || !task2.mDedicatedTask) {
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
            Task task3 = (Task) this.mTasks.get(i3);
            if (isActiveRecentTask(task3, this.mTmpQuietProfileUserIds)) {
                if (this.mHasVisibleRecentTasks && isVisibleRecentTask(task3)) {
                    i4++;
                    if (!isInVisibleRange(i3, i4, task3, false)) {
                        if (task3.isAttached()) {
                            if (task3.getDisplayId() == 0 && (task = task3.getDisplayArea().mRootHomeTask) != null && !task3.isMinimized() && !task3.mIsAvoidTrimDexPendingActivityTask && task3.compareTo((WindowContainer) task) < 0) {
                            }
                        }
                    }
                }
                i3++;
            }
            this.mTasks.remove(task3);
            notifyTaskRemoved(task3, true, false);
            notifyTaskPersisterLocked(task3, false);
        }
    }

    public final void unloadUserDataFromMemoryLocked(int i) {
        AtomicBoolean atomicBoolean = (AtomicBoolean) this.mUsersWithRecentsLoaded.get(i);
        if (atomicBoolean != null && atomicBoolean.get()) {
            BootReceiver$$ExternalSyntheticOutline0.m(i, "Unloading recents for user ", " from memory.", "ActivityTaskManager");
            this.mUsersWithRecentsLoaded.delete(i);
            if (i <= 0) {
                HermesService$3$$ExternalSyntheticOutline0.m(i, "Can't remove recent task on user ", "ActivityTaskManager");
            } else {
                for (int size = this.mTasks.size() - 1; size >= 0; size--) {
                    Task task = (Task) this.mTasks.get(size);
                    if (task.mUserId == i) {
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[2]) {
                            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_TASKS, 3308140128142966415L, 4, null, String.valueOf(task), Long.valueOf(i));
                        }
                        remove(task);
                    }
                }
            }
        }
        this.mPersistedTaskIds.delete(i);
        this.mTaskPersister.mTaskIdsInFile.delete(i);
    }

    public final int[] usersWithRecentsLoadedLocked() {
        int size = this.mUsersWithRecentsLoaded.size();
        int[] iArr = new int[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = this.mUsersWithRecentsLoaded.keyAt(i2);
            if (((AtomicBoolean) this.mUsersWithRecentsLoaded.valueAt(i2)).get()) {
                iArr[i] = keyAt;
                i++;
            }
        }
        return i < size ? Arrays.copyOf(iArr, i) : iArr;
    }
}
