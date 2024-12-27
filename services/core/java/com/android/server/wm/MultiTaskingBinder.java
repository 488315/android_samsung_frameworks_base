package com.android.server.wm;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.view.MotionEvent;
import android.window.WindowContainerToken;

import com.android.internal.os.SomeArgs;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService;

import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IDexSnappingCallback;
import com.samsung.android.multiwindow.IDexTransientCaptionDelayListener;
import com.samsung.android.multiwindow.IFreeformCallback;
import com.samsung.android.multiwindow.IMultiTaskingBinder;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.rune.CoreRune;

import java.io.FileDescriptor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiTaskingBinder extends IMultiTaskingBinder.Stub {
    public final ActivityTaskManagerService mAtm;

    public MultiTaskingBinder(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public final int calculateMaxWidth(int i, int i2, int i3) {
        int i4;
        ActivityTaskManagerService.enforceTaskPermission("calculateMaxWidth");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DexDockingController dexDockingController = this.mAtm.mDexDockingController;
                    dexDockingController.getClass();
                    final int i5 = i == 1 ? 2 : 1;
                    Task task =
                            dexDockingController.mAtm.mRootWindowContainer.getTask(
                                    new Predicate() { // from class:
                                                      // com.android.server.wm.DexDockingController$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return ((Task) obj).getDexTaskDockingState() == i5;
                                        }
                                    });
                    if (task == null) {
                        i4 = -1;
                    } else {
                        int i6 = task.mMinWidth;
                        if (i6 >= 0) {
                            i3 = i6;
                        }
                        if (CoreRune.MW_CAPTION_SHELL_DEX) {
                            i3 += task.getFreeformThickness();
                        }
                        i4 = i2 - i3;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i4;
    }

    public final void changeToHorizontalSplitLayout(IBinder iBinder) {
        String str;
        StringBuilder sb;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    Task task = isInRootTaskLocked != null ? isInRootTaskLocked.task : null;
                    if (task != null && task.inSplitScreenWindowingMode()) {
                        if (task.getDisplayArea() != null
                                && (task.getCreatedByOrganizerTask() == null
                                        || task.equals(
                                                task.getCreatedByOrganizerTask()
                                                        .getTopMostTask()))) {
                            TaskOrganizerController taskOrganizerController =
                                    this.mAtm.mTaskOrganizerController;
                            taskOrganizerController.getClass();
                            TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
                            taskOrganizerInfo.changeToHorizontalSplitLayout();
                            taskOrganizerController.onSplitLayoutChangeRequested(
                                    taskOrganizerInfo.toBundle());
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        str = "MultiTaskingBinder";
                        sb = new StringBuilder("changeToHorizontalSplitLayout called from pid : ");
                        sb.append(Binder.getCallingPid());
                        Slog.w(str, sb.toString());
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    str = "MultiTaskingBinder";
                    sb = new StringBuilder("changeToHorizontalSplitLayout called from pid : ");
                    sb.append(Binder.getCallingPid());
                    Slog.w(str, sb.toString());
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Slog.w(
                    "MultiTaskingBinder",
                    "changeToHorizontalSplitLayout called from pid : " + Binder.getCallingPid());
        }
    }

    public final void clearAllDockingTasks(String str) {
        ActivityTaskManagerService.enforceTaskPermission("clearAllDockingTasks");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAtm.mDexDockingController.clearAllTasks(str);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void dismissSplitTask(IBinder iBinder, boolean z) {
        MultiStarController multiStarController = this.mAtm.mMultiStarController;
        ArrayList arrayList = new ArrayList(Arrays.asList("com.samsung.controlpanel"));
        multiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission("dismissSplitTask", arrayList);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    Task task = null;
                    Task task2 = isInRootTaskLocked != null ? isInRootTaskLocked.task : null;
                    if (isInRootTaskLocked != null && task2 != null) {
                        TaskDisplayArea displayArea = task2.getDisplayArea();
                        if (displayArea != null && displayArea.mDisplayContent.mDisplayId == 0) {
                            boolean z2 = false;
                            Task orCreateRootHomeTask = displayArea.getOrCreateRootHomeTask(false);
                            if (!CoreRune.MW_MULTI_SPLIT || !displayArea.isMultiSplitActive()) {
                                if (task2.getCreatedByOrganizerTask() != null
                                        && task2.getCreatedByOrganizerTask().getTopChild()
                                                == task2) {
                                    if (task2.getStageType() == 2) {
                                        task = displayArea.mRootMainStageTask.getTopLeafTask();
                                    } else if (task2.getStageType() == 1) {
                                        task = displayArea.mRootSideStageTask.getTopLeafTask();
                                    }
                                    if (task != null && !task.mCreatedByOrganizer) {
                                        final Task[] taskArr = new Task[1];
                                        displayArea.forAllLeafTasks(
                                                new Predicate() { // from class:
                                                                  // com.android.server.wm.MultiTaskingBinder$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        Task[] taskArr2 = taskArr;
                                                        Task task3 = (Task) obj;
                                                        if (task3.inMultiWindowMode()
                                                                || task3.topRunningActivity(false)
                                                                        == null) {
                                                            return false;
                                                        }
                                                        taskArr2[0] = task3;
                                                        return true;
                                                    }
                                                });
                                        Task task3 = taskArr[0];
                                        if (task3 == null
                                                || task3.compareTo((WindowContainer) task) <= 0) {
                                            z2 = true;
                                        }
                                        displayArea.onStageSplitScreenDismissed(
                                                task.getTopMostTask(), z2);
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        return;
                                    }
                                    Slog.w(
                                            "MultiTaskingBinder",
                                            "dismissSplitTask: failed, couldn't found opposite"
                                                + " task.");
                                }
                                Slog.w(
                                        "MultiTaskingBinder",
                                        "dismissSplitTask: failed, controlpanel is not top child");
                            } else if (task2.inSplitScreenWindowingMode()
                                    && orCreateRootHomeTask != null
                                    && orCreateRootHomeTask
                                            .getRootTask()
                                            .equals(task2.getRootTask())) {
                                this.mAtm.deferWindowLayout();
                                try {
                                    orCreateRootHomeTask
                                            .getRootTask()
                                            .positionChildAt(
                                                    orCreateRootHomeTask
                                                                    .getRootTask()
                                                                    .getChildCount()
                                                            - 2,
                                                    orCreateRootHomeTask,
                                                    true);
                                    orCreateRootHomeTask.getRootTask().moveTaskToBack(task2, null);
                                } finally {
                                    this.mAtm.continueWindowLayout();
                                }
                            } else {
                                Slog.w(
                                        "MultiTaskingBinder",
                                        "dismissSplitTask: failed, now multi-split");
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                    Slog.w("MultiTaskingBinder", "dismissSplitTask: failed, " + iBinder);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean exitMultiWindow(IBinder iBinder, boolean z) {
        if (z) {
            ActivityTaskManagerService.enforceTaskPermission("exitMultiWindow");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAtm.mMultiTaskingController.exitMultiWindow(iBinder);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void finishNaturalSwitching() {
        ActivityTaskManagerService.enforceTaskPermission("finishNaturalSwitching()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAtm.mNaturalSwitchingController.finishNaturalSwitching();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getAllowedMultiWindowPackageList() {
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository =
                        this.mAtm.mMwSupportPolicyController.mAllowListRepository;
        if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        synchronized (
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                        .mLock) {
            arrayList.addAll(
                    multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                            .getPackages(false));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int getDexTaskInfoFlags(IBinder iBinder) {
        Task task;
        if (UserHandle.isIsolated(Binder.getCallingUid())) {
            throw new SecurityException(
                    "Isolated process not allowed to call ".concat("getDexTaskInfoFlags"));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked != null && (task = forTokenLocked.task) != null) {
                        this.mAtm.mDexController.getClass();
                        boolean inMultiWindowMode = task.inMultiWindowMode();
                        boolean z = inMultiWindowMode;
                        if (ActivityInfo.isPreserveOrientationMode(task.mResizeMode)) {
                            z = (inMultiWindowMode ? 1 : 0) | 4;
                        }
                        int i = z;
                        if (task.isResizeable(true)) {
                            i = (z ? 1 : 0) | 2;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return i;
                    }
                    Slog.w(
                            "MultiTaskingBinder",
                            "getDexTaskInfoFlags: cannot find ActivityRecord, token=" + iBinder);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 0;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean getEmbedActivityPackageEnabled(String str, int i) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return true;
        }
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException(
                    "Non-System UID cannot call getEmbedActivityPackageEnabled");
        }
        if (i != UserHandle.getCallingUserId()) {
            this.mAtm.mAmInternal.enforceCallingPermission(
                    "android.permission.INTERACT_ACROSS_USERS", "getEmbedActivityPackageEnabled");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "getEmbedActivityPackageEnabled, packageName=", str));
        }
        return this.mAtm.mMultiTaskingController.getEmbedActivityPackageEnabled(str, i);
    }

    public final PointF getFreeformContainerPoint() {
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            return this.mAtm.mFreeformController.mFreeformContainerPoint;
        }
        return null;
    }

    public final StringParceledListSlice getMWDisableRequesters() {
        StringParceledListSlice stringParceledListSlice;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAtm.mGlobalLockWithoutBoost) {
                MultiWindowEnableController multiWindowEnableController =
                        this.mAtm.mMultiWindowEnableController;
                List list =
                        (ArrayList)
                                multiWindowEnableController.mMWOffRequesters.get(
                                        multiWindowEnableController
                                                .mAtm
                                                .mWindowManager
                                                .mCurrentUserId);
                if (list == null) {
                    list = Collections.EMPTY_LIST;
                }
                stringParceledListSlice = new StringParceledListSlice(list);
            }
            return stringParceledListSlice;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ParceledListSlice getMinimizedFreeformTasksForCurrentUser() {
        if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            return null;
        }
        ActivityTaskManagerService.enforceTaskPermission("getMinimizedFreeformTasksForCurrentUser");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAtm.mFreeformController.getMinimizedFreeformTasksForCurrentUser();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getMultiSplitFlags() {
        int multiSplitFlags;
        ActivityTaskManagerService.enforceTaskPermission("getMultiSplitFlags");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    multiSplitFlags = this.mAtm.mMultiTaskingController.getMultiSplitFlags();
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return multiSplitFlags;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final StringParceledListSlice getMultiWindowBlockListApp() {
        ArrayList arrayList;
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository =
                        this.mAtm.mMwSupportPolicyController.mBlockListRepository;
        if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                == null) {
            arrayList = new ArrayList();
        } else {
            ArrayList arrayList2 = new ArrayList();
            synchronized (
                    multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                            .mLock) {
                arrayList2.addAll(
                        multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                                .getPackages(true));
            }
            arrayList = arrayList2;
        }
        return new StringParceledListSlice(arrayList);
    }

    public final int getMultiWindowModeStates(int i) {
        int i2;
        MultiTaskingController multiTaskingController = this.mAtm.mMultiTaskingController;
        WindowManagerGlobalLock windowManagerGlobalLock = multiTaskingController.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent =
                        multiTaskingController.mAtm.mRootWindowContainer.getDisplayContent(i);
                i2 = 0;
                if (displayContent == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    int i3 =
                            displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()
                                    ? 2
                                    : 0;
                    final int[] iArr = new int[1];
                    displayContent.forAllRootTasks(
                            new Consumer() { // from class:
                                             // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda11
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int[] iArr2 = iArr;
                                    Task task = (Task) obj;
                                    if (task.topRunningActivityLocked() != null) {
                                        int windowingMode = task.getWindowingMode();
                                        if (windowingMode == 2) {
                                            iArr2[0] = iArr2[0] | 4;
                                        } else if (windowingMode == 5
                                                && !task.isUnderHomeRootTask()) {
                                            iArr2[0] = iArr2[0] | 1;
                                        }
                                    }
                                }
                            });
                    i2 = i3 | iArr[0];
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getResizeMode(android.content.pm.ActivityInfo r9) {
        /*
            r8 = this;
            com.android.server.wm.ActivityTaskManagerService r8 = r8.mAtm
            com.android.server.wm.MultiWindowSupportPolicyController r8 = r8.mMwSupportPolicyController
            r8.getClass()
            r0 = 0
            if (r9 == 0) goto L6e
            java.lang.String r1 = r9.packageName
            if (r1 != 0) goto Lf
            goto L6e
        Lf:
            long r1 = android.os.Binder.clearCallingIdentity()
            android.content.pm.IPackageManager r3 = android.app.AppGlobals.getPackageManager()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            android.content.ComponentName r4 = r9.getComponentName()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            com.android.server.wm.ActivityTaskManagerService r5 = r8.mAtm     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            android.content.Context r5 = r5.mContext     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            int r5 = r5.getUserId()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            r6 = 128(0x80, double:6.32E-322)
            android.content.pm.ActivityInfo r3 = r3.getActivityInfo(r4, r6, r5)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            boolean r3 = com.android.server.wm.MultiWindowSupportPolicyController.isIgnoreDevSettingForNonResizable(r3)     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L36
            android.os.Binder.restoreCallingIdentity(r1)
            goto L3a
        L31:
            r8 = move-exception
            android.os.Binder.restoreCallingIdentity(r1)
            throw r8
        L36:
            android.os.Binder.restoreCallingIdentity(r1)
            r3 = r0
        L3a:
            com.android.server.wm.ActivityTaskManagerService r1 = r8.mAtm
            com.android.server.wm.WindowManagerGlobalLock r1 = r1.mGlobalLockWithoutBoost
            monitor-enter(r1)
            int r2 = r9.resizeMode     // Catch: java.lang.Throwable -> L56
            java.lang.String r4 = r9.packageName     // Catch: java.lang.Throwable -> L56
            int r2 = r8.checkSupportPolicyLocked(r2, r4)     // Catch: java.lang.Throwable -> L56
            boolean r4 = android.content.pm.ActivityInfo.isResizeableMode(r2)     // Catch: java.lang.Throwable -> L56
            if (r4 != 0) goto L58
            boolean r9 = r9.supportsPictureInPicture()     // Catch: java.lang.Throwable -> L56
            if (r9 == 0) goto L54
            goto L58
        L54:
            r9 = r0
            goto L59
        L56:
            r8 = move-exception
            goto L6c
        L58:
            r9 = 1
        L59:
            com.android.server.wm.ActivityTaskManagerService r4 = r8.mAtm     // Catch: java.lang.Throwable -> L56
            com.android.server.wm.RootWindowContainer r4 = r4.mRootWindowContainer     // Catch: java.lang.Throwable -> L56
            com.android.server.wm.DisplayContent r4 = r4.mDefaultDisplay     // Catch: java.lang.Throwable -> L56
            com.android.server.wm.TaskDisplayArea r4 = r4.getDefaultTaskDisplayArea()     // Catch: java.lang.Throwable -> L56
            boolean r8 = r8.supportsMultiWindowInDisplayArea(r4, r2, r9, r3)     // Catch: java.lang.Throwable -> L56
            if (r8 == 0) goto L6a
            r0 = 2
        L6a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L56
            goto L6e
        L6c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L56
            throw r8
        L6e:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.MultiTaskingBinder.getResizeMode(android.content.pm.ActivityInfo):int");
    }

    public final List getSplitActivityAllowPackages() {
        return List.of();
    }

    public final int getSplitActivityPackageEnabled(String str, int i) {
        return 0;
    }

    public final List getSupportEmbedActivityPackages() {
        List list;
        boolean z = CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED;
        if (!z) {
            return List.of();
        }
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException(
                    "Non-System UID cannot call getSupportEmbedActivityPackages");
        }
        MultiTaskingController multiTaskingController = this.mAtm.mMultiTaskingController;
        multiTaskingController.getClass();
        if (!z) {
            return List.of();
        }
        ActivityEmbeddedPackageRepository activityEmbeddedPackageRepository =
                multiTaskingController.mActivityEmbeddedPackageRepository;
        synchronized (activityEmbeddedPackageRepository) {
            list = activityEmbeddedPackageRepository.mRepository;
        }
        return list;
    }

    public final SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) {
        this.mAtm.mAmInternal.enforceCallingPermission(
                "android.permission.READ_FRAME_BUFFER", "getSurfaceFreezerSnapshot");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAtm.mNaturalSwitchingController.getSurfaceFreezerSnapshot(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ParceledListSlice getTaskInfoFromPackageName(String str) {
        ActivityTaskManagerService.enforceTaskPermission("getTaskInfoFromPackageName");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAtm.mMultiTaskingController.getTaskInfoFromPackageName(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ParceledListSlice getVisibleTasks() {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        activityTaskManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        boolean z =
                ActivityManagerService.checkComponentPermission(
                                        callingPid,
                                        callingUid,
                                        "android.permission.INTERACT_ACROSS_USERS",
                                        0,
                                        -1,
                                        true)
                                == 0
                        || ActivityManagerService.checkComponentPermission(
                                        callingPid,
                                        callingUid,
                                        "android.permission.INTERACT_ACROSS_USERS_FULL",
                                        0,
                                        -1,
                                        true)
                                == 0;
        int[] profileIds =
                activityTaskManagerService
                        .getUserManager()
                        .getProfileIds(null, true, false, UserHandle.getUserId(callingUid));
        ArraySet arraySet = new ArraySet();
        for (int i : profileIds) {
            arraySet.add(Integer.valueOf(i));
        }
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                activityTaskManagerService.mTaskSupervisor.mRunningTasks.getTasks(
                        100,
                        arrayList,
                        (z ? 4 : 0)
                                | (activityTaskManagerService.isGetTasksAllowed(
                                                callingPid, callingUid, "getVisibleTasks")
                                        ? 2
                                        : 0),
                        activityTaskManagerService.mRecentTasks,
                        activityTaskManagerService.mRootWindowContainer,
                        callingUid,
                        arraySet,
                        true);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return new ParceledListSlice(arrayList);
    }

    public final boolean hasMinimizedToggleTasks() {
        boolean z;
        ActivityTaskManagerService.enforceTaskPermission("hasMinimizedToggleTasks");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = !((ArrayList) this.mAtm.mDexController.mMinimizedToggleTasks).isEmpty();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public final void initDockingBounds(Rect rect, Rect rect2, int i) {
        ActivityTaskManagerService.enforceTaskPermission("initDockingBounds");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DexDockingController dexDockingController = this.mAtm.mDexDockingController;
                    dexDockingController.mDockingBounds.put(1, rect);
                    dexDockingController.mDockingBounds.put(2, rect2);
                    dexDockingController.mDisplayWidth = i;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final boolean isAllTasksResizable(int i, int i2, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task task = this.mAtm.mTaskSupervisor.mRecentTasks.getTask(i);
                    Task task2 = this.mAtm.mTaskSupervisor.mRecentTasks.getTask(i2);
                    if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                        if (task != null
                                && task2 != null
                                && task.supportsMultiWindowInDefaultDisplayArea()
                                && task2.supportsMultiWindowInDefaultDisplayArea()) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    if (i3 == -1) {
                        if (task != null
                                && task2 != null
                                && task.supportsMultiWindowInDefaultDisplayArea()
                                && task2.supportsMultiWindowInDefaultDisplayArea()) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    Task task3 = this.mAtm.mTaskSupervisor.mRecentTasks.getTask(i3);
                    if (task != null
                            && task2 != null
                            && task3 != null
                            && task.supportsMultiWindowInDefaultDisplayArea()
                            && task2.supportsMultiWindowInDefaultDisplayArea()
                            && task3.supportsMultiWindowInDefaultDisplayArea()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAllowedMultiWindowPackage(String str) {
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository =
                        this.mAtm.mMwSupportPolicyController.mAllowListRepository;
        return multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                        != null
                && multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                        .containsPackage(str, false);
    }

    public final boolean isCornerGestureRunning() {
        boolean z;
        ActivityTaskManagerService.enforceTaskPermission("isCornerGestureRunning");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MultiWindowPointerEventListener multiWindowPointerEventListener =
                            this.mAtm.mWindowManager.getDefaultDisplayContentLocked()
                                    .mMultiWindowPointerEventListener;
                    if (multiWindowPointerEventListener.isAllowCornerGestureState()) {
                        z = multiWindowPointerEventListener.mMultiWindowEdgeDetector.isEdge();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean isDismissedFlexPanelMode() {
        if (!CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            return false;
        }
        ActivityTaskManagerService.enforceTaskPermission("isDismissedFlexPanelMode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    TaskDisplayArea defaultTaskDisplayArea =
                            this.mAtm.mRootWindowContainer.mDefaultDisplay
                                    .getDefaultTaskDisplayArea();
                    Task task =
                            defaultTaskDisplayArea != null
                                    ? defaultTaskDisplayArea.mRootSideStageTask
                                    : null;
                    if (task != null && task.hasChild()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isFlexPanelRunning() {
        boolean isFlexPanelRunning;
        if (!CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    isFlexPanelRunning =
                            this.mAtm.mRootWindowContainer.mDefaultDisplay.isFlexPanelRunning();
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return isFlexPanelRunning;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isMultiWindowBlockListApp(String str) {
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository =
                        this.mAtm.mMwSupportPolicyController.mBlockListRepository;
        return multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                        != null
                && multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository
                        .containsPackage(str, true);
    }

    public final boolean isSplitImmersiveModeEnabled() {
        boolean z;
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MultiWindowEnableController multiWindowEnableController =
                            this.mAtm.mMultiWindowEnableController;
                    if (multiWindowEnableController.mPref == null) {
                        multiWindowEnableController.mPref =
                                multiWindowEnableController.mAtm.mContext.getSharedPreferences(
                                        "multiwindow.property", 0);
                    }
                    z = multiWindowEnableController.mPref.getInt("mw_immersive_mode", 0) == 1;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean isValidCornerGesture(MotionEvent motionEvent) {
        boolean z;
        ActivityTaskManagerService.enforceTaskPermission("isValidCornerGesture");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MultiWindowPointerEventListener multiWindowPointerEventListener =
                            this.mAtm.mWindowManager.getDefaultDisplayContentLocked()
                                    .mMultiWindowPointerEventListener;
                    if (multiWindowPointerEventListener.isAllowCornerGestureState()) {
                        z =
                                multiWindowPointerEventListener.mMultiWindowEdgeDetector
                                        .onTouchEvent(motionEvent);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean isVisibleTaskByTaskIdInDexDisplay(int i) {
        boolean isVisibleTaskByTaskIdInDexDisplay;
        ActivityTaskManagerService.enforceTaskPermission("isVisibleTaskByTaskIdInDexDisplay");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    isVisibleTaskByTaskIdInDexDisplay =
                            this.mAtm.mMultiTaskingController.isVisibleTaskByTaskIdInDexDisplay(i);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return isVisibleTaskByTaskIdInDexDisplay;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) {
        boolean isVisibleTaskInDexDisplay;
        ActivityTaskManagerService.enforceTaskPermission("isTaskInDexDisplay");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    isVisibleTaskInDexDisplay =
                            this.mAtm.mMultiTaskingController.isVisibleTaskInDexDisplay(
                                    pendingIntent);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return isVisibleTaskInDexDisplay;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean minimizeAllTasks(int i) {
        ActivityTaskManagerService.enforceTaskPermission("minimizeAllTasks");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(i, true);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return true;
    }

    public final boolean minimizeAllTasksByRecents(int i) {
        ActivityTaskManagerService.enforceTaskPermission("minimizeAllTasksByRecents");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(i, false);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return true;
    }

    public final boolean minimizeTaskById(int i) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ActivityManagerService.checkComponentPermission(
                                    callingPid,
                                    callingUid,
                                    "android.permission.MANAGE_ACTIVITY_TASKS",
                                    0,
                                    -1,
                                    true)
                            != 0) {
                        Slog.w(
                                "MultiTaskingBinder",
                                "Permission Denial: minimizeTaskById, caller:" + callingUid);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    Task anyTaskForId =
                            this.mAtm.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING && !anyTaskForId.isDexMode()) {
                        final int[] iArr = {1};
                        this.mAtm
                                .mRootWindowContainer
                                .mDefaultDisplay
                                .getDefaultTaskDisplayArea()
                                .forAllLeafTasks(
                                        new Consumer() { // from class:
                                                         // com.android.server.wm.MultiTaskingBinder$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int[] iArr2 = iArr;
                                                Task task = (Task) obj;
                                                if (task.inFreeformWindowingMode()
                                                        && task.isMinimized()) {
                                                    iArr2[0] = iArr2[0] + 1;
                                                }
                                            }
                                        },
                                        true);
                        CoreSaLogger.logForAdvanced("2001", String.valueOf(iArr[0]));
                    }
                    boolean minimizeTaskLocked =
                            this.mAtm.mMultiTaskingController.minimizeTaskLocked(
                                    -1, -1, anyTaskForId, true);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return minimizeTaskLocked;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) {
        boolean minimizeTaskLocked;
        ActivityTaskManagerService.enforceTaskPermission("minimizeTaskToSpecificPosition");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    minimizeTaskLocked =
                            this.mAtm.mMultiTaskingController.minimizeTaskLocked(
                                    i2, i3, this.mAtm.mRootWindowContainer.anyTaskForId(i), z);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return minimizeTaskLocked;
    }

    public final void notifyDragSplitAppIconHasDrawable(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragState dragState = this.mAtm.mWindowManager.mDragDropController.mDragState;
                    if (dragState != null && dragState.mDragSplitAppIconHasDrawable != z) {
                        dragState.mTransaction.setAlpha(
                                dragState.mSurfaceControl,
                                z ? FullScreenMagnificationGestureHandler.MAX_SCALE : 0.3f);
                        dragState.mDragSplitAppIconHasDrawable = z;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) {
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            ActivityTaskManagerService.enforceTaskPermission("notifyFreeformMinimizeAnimationEnd");
            FreeformController freeformController = this.mAtm.mFreeformController;
            WindowManagerGlobalLock windowManagerGlobalLock = freeformController.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId =
                            freeformController.mAtm.mRootWindowContainer.anyTaskForId(i);
                    if (anyTaskForId != null) {
                        freeformController.mFreeformContainerPoint.set(pointF);
                        freeformController.notifyFreeformMinimizeAnimationEnd(anyTaskForId);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Caller must be shell");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            new MultiWindowShellCommand(this.mAtm)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean preventNaturalSwitching(int i) {
        boolean preventNaturalSwitching;
        ActivityTaskManagerService.enforceTaskPermission("preventNaturalSwitching()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    preventNaturalSwitching =
                            this.mAtm.mNaturalSwitchingController.preventNaturalSwitching(i);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return preventNaturalSwitching;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback) {
        this.mAtm.mAmInternal.enforceCallingPermission(
                "android.permission.REAL_GET_TASKS", "registerDexSnappingCallback()");
        DexController dexController = this.mAtm.mDexController;
        synchronized (dexController.mDexSnappingCallbacks) {
            dexController.mDexSnappingCallbacks.register(iDexSnappingCallback);
        }
    }

    public final void registerDexTransientDelayListener(
            IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DexController dexController = this.mAtm.mDexController;
            synchronized (dexController.mDexTransientCaptionDelayCallbacks) {
                dexController.mDexTransientCaptionDelayCallbacks.register(
                        iDexTransientCaptionDelayListener);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerFreeformCallback(IFreeformCallback iFreeformCallback) {
        ActivityTaskManagerService.enforceTaskPermission("registerFreeformCallback");
        FreeformController freeformController = this.mAtm.mFreeformController;
        synchronized (freeformController.mCallbacks) {
            freeformController.mCallbacks.register(iFreeformCallback);
        }
    }

    public final void registerRemoteAppTransitionListener(
            IRemoteAppTransitionListener iRemoteAppTransitionListener) {
        if (iRemoteAppTransitionListener != null) {
            ActivityTaskManagerService.enforceTaskPermission(
                    "registerRemoteAppTransitionListener()");
            MultiTaskingController multiTaskingController = this.mAtm.mMultiTaskingController;
            synchronized (multiTaskingController.mRemoteAppTransitionListeners) {
                multiTaskingController.mRemoteAppTransitionListeners.register(
                        iRemoteAppTransitionListener);
            }
        }
    }

    public final boolean removeFocusedTask(int i) {
        ActivityTaskManagerService.enforceTaskPermission("removeFocusedTask");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAtm.mMultiTaskingController.removeFocusedTask(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reportFreeformContainerPoint(PointF pointF) {
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            this.mAtm.mFreeformController.mFreeformContainerPoint.set(pointF);
        }
    }

    public final void resizeOtherTaskIfNeeded(int i, Rect rect) {
        ActivityTaskManagerService.enforceTaskPermission("resizeOtherTaskIfNeeded");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAtm.mDexDockingController.resizeOtherTaskIfNeeded(
                            this.mAtm.mRootWindowContainer.anyTaskForId(i), rect);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void rotateDexCompatTask(IBinder iBinder) {
        if (UserHandle.isIsolated(Binder.getCallingUid())) {
            throw new SecurityException(
                    "Isolated process not allowed to call ".concat("rotateDexCompatTask"));
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!(iBinder instanceof WindowContainer.RemoteToken)) {
                        Slog.w(
                                "MultiTaskingBinder",
                                "rotateDexCompatTask: token is not RemoteToken, token=" + iBinder);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    Task asTask =
                            ((WindowContainer)
                                            ((WindowContainer.RemoteToken) iBinder).mWeakRef.get())
                                    .asTask();
                    ActivityRecord activityRecord =
                            asTask != null ? asTask.topRunningActivity(false) : null;
                    if (activityRecord != null) {
                        this.mAtm.mDexCompatController.rotateDexCompatTaskLocked(activityRecord);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        Slog.w(
                                "MultiTaskingBinder",
                                "rotateDexCompatTask: cannot find ActivityRecord, token="
                                        + iBinder);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void saveFreeformBounds(int i) {
        ActivityTaskManagerService.enforceTaskPermission("saveFreeformBounds");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAtm.mGlobalLockWithoutBoost) {
                Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                if (anyTaskForId == null) {
                    Slog.w("MultiTaskingBinder", "saveFreeformBounds : invalid task");
                } else {
                    anyTaskForId.saveFreeformBoundsIfNeeded();
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void scheduleNotifyDexSnappingCallback(int i, Rect rect) {
        DexController dexController = this.mAtm.mDexController;
        dexController.getClass();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.arg1 = rect;
        DexController.H h = dexController.mH;
        h.sendMessage(h.obtainMessage(11, obtain));
    }

    public final void setBlockedMinimizeFreeformEnable(boolean z) {
        this.mAtm.mMultiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission(
                "setBlockedMinimizeFreeformEnable", null);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtm.mMultiWindowEnableController.setBlockedMinimizeFreeformEnabled(z);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setBoostFreeformTaskLayer(int i, boolean z) {
        ActivityTaskManagerService.enforceTaskPermission("setBoostFreeformTaskLayer");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task anyTaskForId =
                            this.mAtm.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId != null && anyTaskForId.inFreeformWindowingMode()) {
                        Slog.d(
                                "MultiTaskingBinder",
                                "setBoostFreeformTaskLayer: t #" + i + ", boost=" + z);
                        anyTaskForId.setBoostTaskLayerForFreeform(z, true);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    Slog.w(
                            "MultiTaskingBinder",
                            "setBoostFreeformTaskLayer: failed, cannot find t#" + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setCandidateTask(int i) {
        ActivityTaskManagerService.enforceTaskPermission("setCandidateTask");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i);
                    DexDockingController dexDockingController = this.mAtm.mDexDockingController;
                    dexDockingController.getClass();
                    Slog.d("DexDockingController", "setCandidateTask t=" + anyTaskForId);
                    dexDockingController.mCandidateTask = new WeakReference(anyTaskForId);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setCornerGestureEnabledWithSettings(final boolean z) {
        ActivityTaskManagerService.enforceTaskPermission("setCornerGestureEnabledWithSettings");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAtm.mH.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.MultiTaskingBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MultiTaskingBinder multiTaskingBinder = MultiTaskingBinder.this;
                            boolean z2 = z;
                            WindowManagerGlobalLock windowManagerGlobalLock =
                                    multiTaskingBinder.mAtm.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    if (MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED
                                            == z2) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    } else {
                                        multiTaskingBinder.mAtm.mMultiWindowEnableController
                                                .setCornerGestureEnabled(z2);
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                        }
                    });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setCustomDensityEnabled(int i) {
        this.mAtm.mMultiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission("setCustomDensityEnabled", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED != i) {
                        this.mAtm.mInternal.dismissSplitScreenMode();
                        this.mAtm.mMultiStarController.setCustomDensityEnabled(i);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setEmbedActivityPackageEnabled(String str, boolean z, int i) {
        if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException(
                        "Non-System UID cannot call setEmbedActivityPackageEnabled");
            }
            if (i != UserHandle.getCallingUserId()) {
                this.mAtm.mAmInternal.enforceCallingPermission(
                        "android.permission.INTERACT_ACROSS_USERS",
                        "setEmbedActivityPackageEnabled");
            }
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "setEmbedActivityPackageEnabled, packageName=", str));
            }
            this.mAtm.mMultiTaskingController.setEmbedActivityPackageEnabled(str, z, i);
        }
    }

    public final void setEnsureLaunchSplitEnabled(boolean z) {
        this.mAtm.mMultiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission("setEnsureLaunchSplitEnabled", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (MultiWindowCoreState.MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED != z) {
                        this.mAtm.mMultiWindowEnableController.setEnsureLaunchSplitEnabled(z);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setMaxVisibleFreeformCountForDex(int i, int i2) {
        ActivityTaskManagerService.enforceTaskPermission("setMaxVisibleFreeformCount");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAtm.mFreeformController.setMaxVisibleFreeformCountForDex(i, i2);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) {
        ActivityTaskManagerService.enforceTaskPermission("setMultiWindowEnabledForUser");
        if (i != UserHandle.getCallingUserId()) {
            this.mAtm.mAmInternal.enforceCallingPermission(
                    "android.permission.INTERACT_ACROSS_USERS_FULL",
                    "setMultiWindowEnabledForUser");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MultiWindowEnableController multiWindowEnableController =
                    this.mAtm.mMultiWindowEnableController;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    multiWindowEnableController.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    multiWindowEnableController.setEnableForUser(i, str, str2, z);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setNaviStarSplitImmersiveMode(boolean z) {
        MultiStarController multiStarController = this.mAtm.mMultiStarController;
        ArrayList arrayList = new ArrayList(Arrays.asList("com.samsung.systemui.navillera"));
        multiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission(
                "setNaviBarImmersiveModeLocked", arrayList);
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED != z) {
                        this.mAtm.mMultiWindowEnableController.setNaviStarImmersiveSplitModeLocked(
                                z);
                        this.mAtm.mWindowManager.getDefaultDisplayContentLocked().setLayoutNeeded();
                        this.mAtm.mWindowManager.requestTraversal();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSplitActivityPackageEnabled(String str, int i, int i2) {}

    public final void setSplitImmersiveMode(boolean z) {
        ActivityTaskManagerService.enforceTaskPermission("setSplitImmersiveMode");
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAtm.mMultiWindowEnableController.setSplitImmersiveModeLocked(z);
                    this.mAtm.mWindowManager.getDefaultDisplayContentLocked().setLayoutNeeded();
                    this.mAtm.mWindowManager.requestTraversal();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setStayFocusActivityEnabled(boolean z) {
        this.mAtm.mMultiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission("setStayFocusActivityEnabled", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED
                            != z) {
                        this.mAtm.mMultiWindowEnableController
                                .setStayFocusAndTopResumedActivityEnabled(z, z);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) {
        this.mAtm.mMultiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission(
                "setStayFocusAndTopResumedActivityEnabled", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    boolean z3 =
                            z
                                    != MultiWindowCoreState
                                            .MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED;
                    boolean z4 =
                            z2
                                    != MultiWindowCoreState
                                            .MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED;
                    if (z3 || z4) {
                        this.mAtm.mMultiWindowEnableController
                                .setStayFocusAndTopResumedActivityEnabled(z, z2);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean shouldDeferEnterSplit(List list, List list2) {
        boolean shouldDeferEnterSplit;
        ActivityTaskManagerService.enforceTaskPermission("shouldDeferEnterSplit");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    shouldDeferEnterSplit =
                            this.mAtm.mMultiTaskingController.shouldDeferEnterSplit(list, list2);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return shouldDeferEnterSplit;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startAssistantActivityToSplit(Intent intent, float f) {
        ActivityTaskManagerService.enforceTaskPermission("startAssistantActivityToSplit");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAtm.mMultiTaskingController.startAssistantActivityToSplitLocked(
                            intent, f);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) {
        boolean startNaturalSwitching;
        ActivityTaskManagerService.enforceTaskPermission("startNaturalSwitching()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    startNaturalSwitching =
                            this.mAtm.mNaturalSwitchingController.startNaturalSwitching(
                                    iBinder, iBinder2);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return startNaturalSwitching;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean supportMultiSplitAppMinimumSize() {
        boolean z;
        if (!CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            return false;
        }
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            z = this.mAtm.mMultiTaskingController.mSplitFeasibleMode == 2;
        }
        return z;
    }

    public final boolean supportsMultiWindow(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAtm.mGlobalLockWithoutBoost) {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                Task task = isInRootTaskLocked != null ? isInRootTaskLocked.task : null;
                if (task != null && task.getDisplayArea() != null) {
                    return task.supportsMultiWindowInDisplayArea(task.getTaskDisplayArea(), false);
                }
                Slog.w(
                        "MultiTaskingBinder",
                        "supportsMultiWindow: cannot find task, token=" + iBinder);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void toggleFreeformForDexCompatApp(int i) {
        ActivityTaskManagerService.enforceTaskPermission("toggleFreeformForDexCompatApp");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId =
                            this.mAtm.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        Slog.w(
                                "MultiTaskingBinder",
                                "toggleFreeformForDexCompatApp : invalid task");
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        DexCompatController dexCompatController = this.mAtm.mDexCompatController;
                        dexCompatController.getClass();
                        dexCompatController.scheduleStartActivityAsToggleFreeform(
                                anyTaskForId,
                                new DexCompatController$$ExternalSyntheticLambda0(anyTaskForId),
                                new DexCompatController$$ExternalSyntheticLambda1(0, anyTaskForId),
                                new DexCompatController$$ExternalSyntheticLambda2(0));
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

    public final boolean toggleFreeformWindowingMode() {
        boolean z;
        MultiStarController multiStarController = this.mAtm.mMultiStarController;
        ArrayList arrayList = new ArrayList(Arrays.asList("com.samsung.android.sidegesturepad"));
        multiStarController.getClass();
        MultiStarController.checkMultiStarPackageAndPermission(
                "toggleFreeformWindowingMode", arrayList);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAtm.mGlobalLockWithoutBoost) {
                z = this.mAtm.mMultiStarController.toggleFreeformWindowingMode();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) {
        ActivityTaskManagerService.enforceTaskPermission("toggleFreeformWindowingModeForDex");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task asTask =
                            ((WindowContainer)
                                            ((WindowContainer.RemoteToken)
                                                            windowContainerToken.asBinder())
                                                    .mWeakRef.get())
                                    .asTask();
                    if (asTask == null) {
                        Slog.w(
                                "MultiTaskingBinder",
                                "toggleFreeformWindowingModeForDex: task is null");
                    } else {
                        ActivityRecord activityRecord = asTask.topRunningActivity(false);
                        if (activityRecord != null) {
                            this.mAtm.mActivityClientController.toggleFreeformWindowingMode(
                                    activityRecord.token);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        } else {
                            Slog.w(
                                    "MultiTaskingBinder",
                                    "toggleFreeformWindowingModeForDex: cannot find ActivityRecord,"
                                        + " token= "
                                            + windowContainerToken);
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback) {
        DexController dexController = this.mAtm.mDexController;
        synchronized (dexController.mDexSnappingCallbacks) {
            dexController.mDexSnappingCallbacks.unregister(iDexSnappingCallback);
        }
    }

    public final void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) {
        ActivityTaskManagerService.enforceTaskPermission("unregisterFreeformCallback");
        FreeformController freeformController = this.mAtm.mFreeformController;
        synchronized (freeformController.mCallbacks) {
            freeformController.mCallbacks.unregister(iFreeformCallback);
        }
    }

    public final void unregisterRemoteAppTransitionListener(
            IRemoteAppTransitionListener iRemoteAppTransitionListener) {
        if (iRemoteAppTransitionListener != null) {
            ActivityTaskManagerService.enforceTaskPermission(
                    "unregisterRemoteAppTransitionListener()");
            MultiTaskingController multiTaskingController = this.mAtm.mMultiTaskingController;
            synchronized (multiTaskingController.mRemoteAppTransitionListeners) {
                multiTaskingController.mRemoteAppTransitionListeners.unregister(
                        iRemoteAppTransitionListener);
            }
        }
    }

    public final void updateMultiSplitAppMinimumSize() {
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            ActivityTaskManagerService.enforceTaskPermission("updateMultiSplitAppMinimumSize");
            synchronized (this.mAtm.mGlobalLockWithoutBoost) {
                this.mAtm.mMultiTaskingController.updateMultiSplitAppMinimumSizeLocked();
            }
        }
    }
}
