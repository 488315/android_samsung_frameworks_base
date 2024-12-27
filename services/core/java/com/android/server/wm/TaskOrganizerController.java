package com.android.server.wm;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.window.ITaskOrganizer;
import android.window.ITaskOrganizerController;
import android.window.StartingWindowInfo;
import android.window.StartingWindowRemovalInfo;
import android.window.TaskSnapshot;
import android.window.WindowContainerToken;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ArrayUtils;
import com.android.server.wm.TaskOrganizerController.TaskOrganizerState;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TaskOrganizerController extends ITaskOrganizerController.Stub {
    public final WindowManagerGlobalLock mGlobalLock;
    public final ActivityTaskManagerService mService;
    public final ArrayDeque mTaskOrganizers = new ArrayDeque();
    public final ArrayMap mTaskOrganizerStates = new ArrayMap();
    public final HashSet mInterceptBackPressedOnRootTasks = new HashSet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class DeathRecipient implements IBinder.DeathRecipient {
        public final ITaskOrganizer mTaskOrganizer;

        public DeathRecipient(ITaskOrganizer iTaskOrganizer) {
            this.mTaskOrganizer = iTaskOrganizer;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    TaskOrganizerController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    TaskOrganizerState taskOrganizerState =
                            (TaskOrganizerState)
                                    TaskOrganizerController.this.mTaskOrganizerStates.get(
                                            this.mTaskOrganizer.asBinder());
                    if (taskOrganizerState != null) {
                        taskOrganizerState.dispose();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingTaskEvent {
        public final int mEventType;
        public boolean mForce;
        public int mGestureFrom;
        public final Task mTask;

        public PendingTaskEvent(int i, Task task) {
            this.mTask = task;
            this.mEventType = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StartingWindowAnimationAdaptor implements AnimationAdapter {
        @Override // com.android.server.wm.AnimationAdapter
        public final void dump(PrintWriter printWriter, String str) {}

        @Override // com.android.server.wm.AnimationAdapter
        public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {}

        @Override // com.android.server.wm.AnimationAdapter
        public final long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getStatusBarTransitionsStartTime() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void onAnimationCancelled(SurfaceControl surfaceControl) {}

        @Override // com.android.server.wm.AnimationAdapter
        public final void startAnimation(
                SurfaceControl surfaceControl,
                SurfaceControl.Transaction transaction,
                int i,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskOrganizerCallbacks {
        public final ITaskOrganizer mTaskOrganizer;

        public TaskOrganizerCallbacks(ITaskOrganizer iTaskOrganizer) {
            this.mTaskOrganizer = iTaskOrganizer;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskOrganizerPendingEventsQueue {
        public final TaskOrganizerState mOrganizerState;
        public ActivityManager.RunningTaskInfo mTmpTaskInfo;
        public final WeakHashMap mLastSentTaskInfos = new WeakHashMap();
        public final ArrayList mPendingTaskEvents = new ArrayList();

        /* renamed from: -$$Nest$mgetPendingTaskEvent, reason: not valid java name */
        public static PendingTaskEvent m1070$$Nest$mgetPendingTaskEvent(
                TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue, Task task, int i) {
            for (int size = taskOrganizerPendingEventsQueue.mPendingTaskEvents.size() - 1;
                    size >= 0;
                    size--) {
                PendingTaskEvent pendingTaskEvent =
                        (PendingTaskEvent)
                                taskOrganizerPendingEventsQueue.mPendingTaskEvents.get(size);
                if (task.mTaskId == pendingTaskEvent.mTask.mTaskId
                        && i == pendingTaskEvent.mEventType) {
                    return pendingTaskEvent;
                }
            }
            return null;
        }

        public TaskOrganizerPendingEventsQueue(TaskOrganizerState taskOrganizerState) {
            this.mOrganizerState = taskOrganizerState;
        }

        public final void dispatchTaskInfoChanged(Task task, boolean z) {
            ActivityManager.RunningTaskInfo runningTaskInfo =
                    (ActivityManager.RunningTaskInfo) this.mLastSentTaskInfos.get(task);
            if (this.mTmpTaskInfo == null) {
                this.mTmpTaskInfo = new ActivityManager.RunningTaskInfo();
            }
            this.mTmpTaskInfo.configuration.unset();
            task.fillTaskInfo(this.mTmpTaskInfo, true);
            if (this.mTmpTaskInfo.equalsForTaskOrganizer(runningTaskInfo)
                    && WindowOrganizerController.configurationsAreEqualForOrganizer(
                            this.mTmpTaskInfo.configuration, runningTaskInfo.configuration)
                    && !z) {
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTmpTaskInfo;
            if (task.inSplitScreenWindowingMode()
                    && task.isLeafTask()
                    && task.getParent() != null
                    && task.getParent().getTopMostTask() == task) {
                runningTaskInfo2.isTopTaskInStage = true;
            }
            runningTaskInfo2.hasConfigChanged =
                    runningTaskInfo == null
                            || !WindowOrganizerController.configurationsAreEqualForOrganizer(
                                    this.mTmpTaskInfo.configuration, runningTaskInfo.configuration);
            this.mLastSentTaskInfos.put(task, this.mTmpTaskInfo);
            this.mTmpTaskInfo = null;
            if (task.isOrganized()) {
                TaskOrganizerCallbacks taskOrganizerCallbacks = this.mOrganizerState.mOrganizer;
                taskOrganizerCallbacks.getClass();
                if (task.mTaskAppearedSent) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                -6638141753476761854L,
                                1,
                                null,
                                Long.valueOf(task.mTaskId));
                    }
                    if (task.isOrganized()) {
                        try {
                            taskOrganizerCallbacks.mTaskOrganizer.onTaskInfoChanged(
                                    runningTaskInfo2);
                        } catch (RemoteException e) {
                            Slog.e(
                                    "TaskOrganizerController",
                                    "Exception sending onTaskInfoChanged callback",
                                    e);
                        }
                    }
                }
            }
        }

        public ArrayList getPendingEventList() {
            return this.mPendingTaskEvents;
        }

        public PendingTaskEvent getPendingLifecycleTaskEvent(Task task) {
            int i;
            for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
                PendingTaskEvent pendingTaskEvent =
                        (PendingTaskEvent) this.mPendingTaskEvents.get(size);
                if (task.mTaskId == pendingTaskEvent.mTask.mTaskId
                        && ((i = pendingTaskEvent.mEventType) == 0 || i == 1 || i == 2)) {
                    return pendingTaskEvent;
                }
            }
            return null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class TaskOrganizerState {
        public final DeathRecipient mDeathRecipient;
        public final ArrayList mOrganizedTasks = new ArrayList();
        public final TaskOrganizerCallbacks mOrganizer;
        public final TaskOrganizerPendingEventsQueue mPendingEventsQueue;
        public final int mUid;

        public TaskOrganizerState(ITaskOrganizer iTaskOrganizer, int i) {
            this.mOrganizer = new TaskOrganizerCallbacks(iTaskOrganizer);
            DeathRecipient deathRecipient =
                    TaskOrganizerController.this.new DeathRecipient(iTaskOrganizer);
            this.mDeathRecipient = deathRecipient;
            this.mPendingEventsQueue = new TaskOrganizerPendingEventsQueue(this);
            try {
                iTaskOrganizer.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException unused) {
                Slog.e(
                        "TaskOrganizerController",
                        "TaskOrganizer failed to register death recipient");
            }
            this.mUid = i;
        }

        public final void dispose() {
            TaskOrganizerController taskOrganizerController = TaskOrganizerController.this;
            ArrayDeque arrayDeque = taskOrganizerController.mTaskOrganizers;
            TaskOrganizerCallbacks taskOrganizerCallbacks = this.mOrganizer;
            arrayDeque.remove(taskOrganizerCallbacks.mTaskOrganizer);
            while (!this.mOrganizedTasks.isEmpty()) {
                Task task = (Task) this.mOrganizedTasks.get(0);
                if (task.mCreatedByOrganizer) {
                    task.removeImmediately();
                } else {
                    task.updateTaskOrganizerState(false);
                }
                if (this.mOrganizedTasks.contains(task)
                        && removeTask(task, task.mRemoveWithTaskOrganizer)) {
                    TaskOrganizerController.onTaskVanishedInternal(this, task);
                }
                if (taskOrganizerController.mService.mWindowOrganizerController
                                .mTransitionController.isShellTransitionsEnabled()
                        && task.mTaskOrganizer != null
                        && task.mSurfaceControl != null) {
                    task.getSyncTransaction().show(task.mSurfaceControl);
                }
            }
            this.mPendingEventsQueue.mPendingTaskEvents.clear();
            taskOrganizerController.mTaskOrganizerStates.remove(
                    taskOrganizerCallbacks.mTaskOrganizer.asBinder());
        }

        public DeathRecipient getDeathRecipient() {
            return this.mDeathRecipient;
        }

        public TaskOrganizerPendingEventsQueue getPendingEventsQueue() {
            return this.mPendingEventsQueue;
        }

        public final boolean removeTask(Task task, boolean z) {
            BLASTSyncEngine.SyncGroup syncGroup;
            this.mOrganizedTasks.remove(task);
            TaskOrganizerController taskOrganizerController = TaskOrganizerController.this;
            taskOrganizerController.mInterceptBackPressedOnRootTasks.remove(
                    Integer.valueOf(task.mTaskId));
            boolean z2 = task.mTaskAppearedSent;
            if (z2) {
                if (task.mSurfaceControl != null) {
                    if (task.mRemoving
                            && task.syncNextBuffer()
                            && (task.mIsPipReparetingToLastParent
                                    || ((syncGroup = task.mSyncGroup) != null
                                            && syncGroup.mSyncId
                                                    == task.mSyncIdForReparentSurfaceControl))) {
                        Slog.w("TaskOrganizerController", "removeTask: skip to migrate, t=" + task);
                    } else {
                        task.migrateToNewSurfaceControl(task.getSyncTransaction());
                    }
                }
                task.mTaskAppearedSent = false;
            }
            if (z) {
                taskOrganizerController.mService.removeTask(task.mTaskId);
            }
            return z2;
        }
    }

    public TaskOrganizerController(ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public static SurfaceControl applyStartingWindowAnimation(WindowState windowState) {
        Slog.d(
                "TaskOrganizerController",
                "applyStartingWindowAnimation, window="
                        + windowState
                        + ", caller="
                        + Debug.getCallers(3));
        SurfaceControl.Transaction pendingTransaction = windowState.getPendingTransaction();
        windowState.startAnimation(
                pendingTransaction, new StartingWindowAnimationAdaptor(), false, 128);
        SurfaceControl animationLeash = windowState.getAnimationLeash();
        if (animationLeash != null) {
            Point point = windowState.mSurfacePosition;
            pendingTransaction.setPosition(animationLeash, point.x, point.y);
            return animationLeash;
        }
        Slog.e(
                "TaskOrganizerController",
                "Cannot start starting window animation, the window "
                        + windowState
                        + " was removed");
        return null;
    }

    public static void onTaskVanishedInternal(TaskOrganizerState taskOrganizerState, Task task) {
        if (taskOrganizerState == null) {
            Slog.i(
                    "TaskOrganizerController",
                    "cannot send onTaskVanished because organizer state is not present for this"
                        + " organizer");
            return;
        }
        TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
                taskOrganizerState.mPendingEventsQueue;
        boolean z = false;
        for (int size = taskOrganizerPendingEventsQueue.mPendingTaskEvents.size() - 1;
                size >= 0;
                size--) {
            PendingTaskEvent pendingTaskEvent =
                    (PendingTaskEvent) taskOrganizerPendingEventsQueue.mPendingTaskEvents.get(size);
            if (task.mTaskId == pendingTaskEvent.mTask.mTaskId) {
                taskOrganizerPendingEventsQueue.mPendingTaskEvents.remove(size);
                if (pendingTaskEvent.mEventType == 0) {
                    z = true;
                }
            }
        }
        if (z) {
            return;
        }
        ITaskOrganizer iTaskOrganizer = taskOrganizerState.mOrganizer.mTaskOrganizer;
        taskOrganizerPendingEventsQueue.mPendingTaskEvents.add(new PendingTaskEvent(1, task));
    }

    public static void resetStashedFreeform(Task task, boolean z) {
        try {
            if (task.isOrganized()) {
                if (task.mFreeformStashScale != 1.0f) {
                    task.mFreeformStashScale = 1.0f;
                }
                if (task.mFreeformStashMode != 0) {
                    task.mFreeformStashMode = 0;
                }
                task.mTaskOrganizer.resetStashedFreeform(task.mTaskId, z);
            }
        } catch (RemoteException e) {
            Slog.e("TaskOrganizerController", "Exception sending resetStashedFreeform callback", e);
        }
    }

    public final boolean addStartingWindow(
            Task task, ActivityRecord activityRecord, int i, TaskSnapshot taskSnapshot) {
        ITaskOrganizer taskOrganizer;
        if (task.getRootTask() == null
                || activityRecord.mStartingData == null
                || (taskOrganizer = getTaskOrganizer()) == null) {
            return false;
        }
        StartingWindowInfo startingWindowInfo = task.getStartingWindowInfo(activityRecord);
        if (i != 0) {
            startingWindowInfo.splashScreenThemeResId = i;
        }
        startingWindowInfo.taskSnapshot = taskSnapshot;
        startingWindowInfo.appToken = activityRecord.token;
        try {
            taskOrganizer.addStartingWindow(startingWindowInfo);
            return true;
        } catch (RemoteException e) {
            Slog.e("TaskOrganizerController", "Exception sending onTaskStart callback", e);
            return false;
        }
    }

    public Task createRootTask(DisplayContent displayContent, int i, IBinder iBinder) {
        return createRootTask(displayContent, i, iBinder, false, 0);
    }

    public final Task createRootTask(
            DisplayContent displayContent, int i, IBinder iBinder, boolean z, int i2) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    8466395828406204368L,
                    5,
                    null,
                    Long.valueOf(displayContent.mDisplayId),
                    Long.valueOf(i));
        }
        Task.Builder builder = new Task.Builder(this.mService);
        builder.mWindowingMode = i;
        builder.mIntent = new Intent();
        builder.mCreatedByOrganizer = true;
        builder.mDeferTaskAppear = true;
        builder.mLaunchCookie = iBinder;
        builder.mParent = displayContent.getDefaultTaskDisplayArea();
        builder.mRemoveWithTaskOrganizer = z;
        builder.mStageType = i2;
        Task build = builder.build();
        build.setDeferTaskAppear(false);
        return build;
    }

    public final void createRootTask(int i, int i2, IBinder iBinder, boolean z) {
        ActivityTaskManagerService.enforceTaskPermission("createRootTask()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent =
                            this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent != null) {
                        createRootTask(displayContent, i2, iBinder, z, 0);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[4]) {
                            ProtoLogImpl_54989576.e(
                                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                    -2286607251115721394L,
                                    1,
                                    null,
                                    Long.valueOf(i));
                        }
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

    public final void createStageRootTask(int i, int i2, int i3, IBinder iBinder) {
        ActivityTaskManagerService.enforceTaskPermission("createStageRootTask()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent =
                            this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent != null) {
                        createRootTask(displayContent, i2, iBinder, false, i3);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[4]) {
                            ProtoLogImpl_54989576.e(
                                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                    -4327662970496992015L,
                                    1,
                                    null,
                                    Long.valueOf(i));
                        }
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

    /* JADX WARN: Finally extract failed */
    public final boolean deleteRootTask(WindowContainerToken windowContainerToken) {
        boolean z;
        ActivityTaskManagerService.enforceTaskPermission("deleteRootTask()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowContainer fromBinder =
                            WindowContainer.fromBinder(windowContainerToken.asBinder());
                    z = false;
                    if (fromBinder != null) {
                        Task asTask = fromBinder.asTask();
                        if (asTask != null) {
                            if (!asTask.mCreatedByOrganizer) {
                                throw new IllegalArgumentException(
                                        "Attempt to delete task not created by organizer task="
                                                + asTask);
                            }
                            z = true;
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                        6867170298997192615L,
                                        5,
                                        null,
                                        Long.valueOf(asTask.getDisplayId()),
                                        Long.valueOf(asTask.getWindowingMode()));
                            }
                            asTask.remove("deleteRootTask", true);
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dispatchPendingEvents() {
        if (this.mService.mWindowManager.mWindowPlacerLocked.mDeferDepth > 0) {
            return;
        }
        for (int i = 0; i < this.mTaskOrganizerStates.size(); i++) {
            TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
                    ((TaskOrganizerState) this.mTaskOrganizerStates.valueAt(i)).mPendingEventsQueue;
            if (!taskOrganizerPendingEventsQueue.mPendingTaskEvents.isEmpty()) {
                int size = taskOrganizerPendingEventsQueue.mPendingTaskEvents.size();
                for (int i2 = 0; i2 < size; i2++) {
                    PendingTaskEvent pendingTaskEvent =
                            (PendingTaskEvent)
                                    taskOrganizerPendingEventsQueue.mPendingTaskEvents.get(i2);
                    Task task = pendingTaskEvent.mTask;
                    boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled;
                    TaskOrganizerState taskOrganizerState =
                            taskOrganizerPendingEventsQueue.mOrganizerState;
                    int i3 = pendingTaskEvent.mEventType;
                    if (i3 != 0) {
                        if (i3 == 1) {
                            TaskOrganizerCallbacks taskOrganizerCallbacks =
                                    taskOrganizerState.mOrganizer;
                            taskOrganizerCallbacks.getClass();
                            if (zArr[1]) {
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                        6535296991997214354L,
                                        1,
                                        null,
                                        Long.valueOf(task.mTaskId));
                            }
                            try {
                                taskOrganizerCallbacks.mTaskOrganizer.onTaskVanished(
                                        task.getTaskInfo());
                            } catch (RemoteException e) {
                                Slog.e(
                                        "TaskOrganizerController",
                                        "Exception sending onTaskVanished callback",
                                        e);
                            }
                            taskOrganizerPendingEventsQueue.mLastSentTaskInfos.remove(task);
                        } else if (i3 == 2) {
                            taskOrganizerPendingEventsQueue.dispatchTaskInfoChanged(
                                    task, pendingTaskEvent.mForce);
                        } else if (i3 == 3) {
                            TaskOrganizerCallbacks taskOrganizerCallbacks2 =
                                    taskOrganizerState.mOrganizer;
                            taskOrganizerCallbacks2.getClass();
                            if (zArr[1]) {
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                        -8100069665346602959L,
                                        1,
                                        null,
                                        Long.valueOf(task.mTaskId));
                            }
                            if (task.mTaskAppearedSent && task.isOrganized()) {
                                try {
                                    taskOrganizerCallbacks2.mTaskOrganizer.onBackPressedOnTaskRoot(
                                            task.getTaskInfo());
                                } catch (Exception e2) {
                                    Slog.e(
                                            "TaskOrganizerController",
                                            "Exception sending onBackPressedOnTaskRoot callback",
                                            e2);
                                }
                            }
                        } else if (i3 == 4) {
                            TaskOrganizerCallbacks taskOrganizerCallbacks3 =
                                    taskOrganizerState.mOrganizer;
                            int i4 = pendingTaskEvent.mGestureFrom;
                            taskOrganizerCallbacks3.getClass();
                            if (task.mTaskAppearedSent && task.isOrganized()) {
                                try {
                                    taskOrganizerCallbacks3.mTaskOrganizer.requestAffordanceAnim(
                                            task.getTaskInfo(), i4);
                                } catch (Exception e3) {
                                    Slog.e(
                                            "TaskOrganizerController",
                                            "Exception sending onAffordanceAnim callback",
                                            e3);
                                }
                            }
                        }
                    } else if (task.mTaskOrganizer != null
                            && !task.mDeferTaskAppear
                            && (task.mCreatedByOrganizer
                                    || (task.mSurfaceControl != null && task.mHasBeenVisible))) {
                        TaskOrganizerCallbacks taskOrganizerCallbacks4 =
                                taskOrganizerState.mOrganizer;
                        taskOrganizerCallbacks4.getClass();
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                    -6181189296332065162L,
                                    1,
                                    null,
                                    Long.valueOf(task.mTaskId));
                        }
                        try {
                            taskOrganizerCallbacks4.mTaskOrganizer.onTaskAppeared(
                                    task.getTaskInfo(),
                                    new SurfaceControl(
                                            task.mSurfaceControl,
                                            "TaskOrganizerController.onTaskAppeared"));
                        } catch (RemoteException e4) {
                            Slog.e(
                                    "TaskOrganizerController",
                                    "Exception sending onTaskAppeared callback",
                                    e4);
                        }
                    }
                }
                taskOrganizerPendingEventsQueue.mPendingTaskEvents.clear();
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final List getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) {
        ActivityTaskManagerService.enforceTaskPermission("getChildTasks()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (windowContainerToken == null) {
                        throw new IllegalArgumentException("Can't get children of null parent");
                    }
                    WindowContainer fromBinder =
                            WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        Slog.e(
                                "TaskOrganizerController",
                                "Can't get children of "
                                        + windowContainerToken
                                        + " because it is not valid.");
                    } else {
                        Task asTask = fromBinder.asTask();
                        if (asTask == null) {
                            Slog.e("TaskOrganizerController", fromBinder + " is not a task...");
                        } else {
                            if (asTask.mCreatedByOrganizer) {
                                ArrayList arrayList = new ArrayList();
                                for (int childCount = asTask.getChildCount() - 1;
                                        childCount >= 0;
                                        childCount--) {
                                    Task asTask2 = asTask.getChildAt(childCount).asTask();
                                    if (asTask2 != null
                                            && (iArr == null
                                                    || ArrayUtils.contains(
                                                            iArr, asTask2.getActivityType()))) {
                                        arrayList.add(asTask2.getTaskInfo());
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return arrayList;
                            }
                            Slog.w(
                                    "TaskOrganizerController",
                                    "Can only get children of root tasks created via"
                                        + " createRootTask");
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Finally extract failed */
    public final float getFreeformTaskOpacity(int i) {
        ActivityTaskManagerService.enforceTaskPermission("getFreeformTaskOpacity()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId =
                            this.mService.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId != null) {
                        float f = anyTaskForId.mFreeformAlpha;
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return f;
                    }
                    Slog.w("TaskOrganizerController", "Cannot find task #" + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 1.0f;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    /* JADX WARN: Finally extract failed */
    public final WindowContainerToken getImeTarget(int i) {
        WindowContainerToken windowContainerToken;
        ActivityTaskManagerService.enforceTaskPermission("getImeTarget()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent =
                            this.mService.mWindowManager.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
                        if (imeTarget != null && imeTarget.getWindow() != null) {
                            Task task = imeTarget.getWindow().getTask();
                            if (task != null) {
                                if (task.inFreeformWindowingMode()) {
                                    InputTarget inputTarget = displayContent.mImeInputTarget;
                                    WindowState windowState =
                                            inputTarget != null
                                                    ? inputTarget.getWindowState()
                                                    : null;
                                    Task task2 = windowState != null ? windowState.getTask() : null;
                                    if (task2 != null && task2 != task) {
                                        windowContainerToken =
                                                task2.mRemoteToken.toWindowContainerToken();
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        return windowContainerToken;
                                    }
                                }
                                windowContainerToken = task.mRemoteToken.toWindowContainerToken();
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return windowContainerToken;
                            }
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getRootTasks(int i, int[] iArr) {
        ArrayList arrayList;
        ActivityTaskManagerService.enforceTaskPermission("getRootTasks()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent =
                            this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        throw new IllegalArgumentException("Display " + i + " doesn't exist");
                    }
                    arrayList = new ArrayList();
                    displayContent.forAllRootTasks(
                            new TaskOrganizerController$$ExternalSyntheticLambda3(
                                    arrayList, 1, iArr));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ITaskOrganizer getTaskOrganizer() {
        return (ITaskOrganizer) this.mTaskOrganizers.peekLast();
    }

    public TaskOrganizerPendingEventsQueue getTaskOrganizerPendingEvents(IBinder iBinder) {
        return ((TaskOrganizerState) this.mTaskOrganizerStates.get(iBinder)).mPendingEventsQueue;
    }

    public TaskOrganizerState getTaskOrganizerState(IBinder iBinder) {
        return (TaskOrganizerState) this.mTaskOrganizerStates.get(iBinder);
    }

    public final boolean handleInterceptBackPressedOnTaskRoot(Task task) {
        if (!(task != null
                && task.isOrganized()
                && this.mInterceptBackPressedOnRootTasks.contains(Integer.valueOf(task.mTaskId)))) {
            return false;
        }
        TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue =
                ((TaskOrganizerState) this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder()))
                        .mPendingEventsQueue;
        if (taskOrganizerPendingEventsQueue == null) {
            Slog.w(
                    "TaskOrganizerController",
                    "cannot get handle BackPressedOnTaskRoot because organizerState is not"
                        + " present");
            return false;
        }
        if (TaskOrganizerPendingEventsQueue.m1070$$Nest$mgetPendingTaskEvent(
                        taskOrganizerPendingEventsQueue, task, 1)
                != null) {
            return false;
        }
        PendingTaskEvent m1070$$Nest$mgetPendingTaskEvent =
                TaskOrganizerPendingEventsQueue.m1070$$Nest$mgetPendingTaskEvent(
                        taskOrganizerPendingEventsQueue, task, 3);
        if (m1070$$Nest$mgetPendingTaskEvent == null) {
            m1070$$Nest$mgetPendingTaskEvent = new PendingTaskEvent(3, task);
        } else {
            taskOrganizerPendingEventsQueue.mPendingTaskEvents.remove(
                    m1070$$Nest$mgetPendingTaskEvent);
        }
        taskOrganizerPendingEventsQueue.mPendingTaskEvents.add(m1070$$Nest$mgetPendingTaskEvent);
        this.mService.mWindowManager.mWindowPlacerLocked.requestTraversal();
        return true;
    }

    public final boolean isPinStateChangeable(int i) {
        ActivityTaskManagerService.enforceTaskPermission("isPinStateChangeable()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    boolean z = false;
                    Task anyTaskForId =
                            this.mService.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    TaskDisplayArea displayArea = anyTaskForId.getDisplayArea();
                    if (displayArea != null) {
                        FreeformTaskPinningController freeformTaskPinningController =
                                displayArea.mFreeformTaskPinningController;
                        freeformTaskPinningController.getClass();
                        if ((!CoreRune.MT_NEW_DEX_TASK_PINNING
                                        || !freeformTaskPinningController.mTaskDisplayArea
                                                .isNewDexMode())
                                && freeformTaskPinningController.mPinnedTask != null) {
                            z = true;
                        }
                    }
                    boolean z2 = !z;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return z2;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onSplitLayoutChangeRequested(Bundle bundle) {
        Task task =
                this.mService.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea()
                        .mRootMainStageTask;
        if (task == null) {
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = new ActivityManager.RunningTaskInfo();
        task.fillTaskInfo(runningTaskInfo, true);
        try {
            task.mTaskOrganizer.onSplitLayoutChangeRequested(runningTaskInfo, bundle);
        } catch (RemoteException e) {
            Slog.e(
                    "TaskOrganizerController",
                    "Exception sending onSplitLayoutChangeRequested callback",
                    e);
        }
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(
                    e, "TaskOrganizerController");
            throw null;
        }
    }

    public final void preloadSplashScreenAppIcon(
            ActivityInfo activityInfo, int i, Configuration configuration) {
        ITaskOrganizer iTaskOrganizer = (ITaskOrganizer) this.mTaskOrganizers.peekLast();
        if (iTaskOrganizer == null) {
            return;
        }
        try {
            iTaskOrganizer.preloadSplashScreenAppIcon(activityInfo, i, configuration);
        } catch (RemoteException e) {
            Slog.e(
                    "TaskOrganizerController",
                    "Exception sending preloadSplashScreenAppInfo callback",
                    e);
        }
    }

    public final ParceledListSlice registerTaskOrganizer(final ITaskOrganizer iTaskOrganizer) {
        ActivityTaskManagerService.enforceTaskPermission("registerTaskOrganizer()");
        final int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final ArrayList arrayList = new ArrayList();
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizerController taskOrganizerController =
                                    TaskOrganizerController.this;
                            ITaskOrganizer iTaskOrganizer2 = iTaskOrganizer;
                            int i = callingUid;
                            ArrayList arrayList2 = arrayList;
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                                taskOrganizerController.getClass();
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                        -610138383571469481L,
                                        4,
                                        null,
                                        String.valueOf(iTaskOrganizer2.asBinder()),
                                        Long.valueOf(i));
                            }
                            if (!taskOrganizerController.mTaskOrganizerStates.containsKey(
                                    iTaskOrganizer2.asBinder())) {
                                taskOrganizerController.mTaskOrganizers.add(iTaskOrganizer2);
                                taskOrganizerController.mTaskOrganizerStates.put(
                                        iTaskOrganizer2.asBinder(),
                                        taskOrganizerController
                                        .new TaskOrganizerState(iTaskOrganizer2, i));
                            }
                            taskOrganizerController.mService.mRootWindowContainer.forAllTasks(
                                    new TaskOrganizerController$$ExternalSyntheticLambda3(
                                            arrayList2,
                                            0,
                                            (TaskOrganizerController.TaskOrganizerState)
                                                    taskOrganizerController.mTaskOrganizerStates
                                                            .get(iTaskOrganizer2.asBinder())));
                        }
                    };
            if (this.mService.mWindowOrganizerController.mTransitionController
                    .isShellTransitionsEnabled()) {
                this.mService.mWindowOrganizerController.mTransitionController.mRunningLock
                        .runWhenIdle(runnable);
            } else {
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        runnable.run();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            ParceledListSlice parceledListSlice = new ParceledListSlice(arrayList);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return parceledListSlice;
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final void removeStartingWindow(
            Task task, ITaskOrganizer iTaskOrganizer, boolean z, boolean z2) {
        if (task.getRootTask() == null) {
            return;
        }
        if (iTaskOrganizer == null) {
            iTaskOrganizer = getTaskOrganizer();
        }
        if (iTaskOrganizer == null) {
            return;
        }
        StartingWindowRemovalInfo startingWindowRemovalInfo = new StartingWindowRemovalInfo();
        startingWindowRemovalInfo.taskId = task.mTaskId;
        startingWindowRemovalInfo.playRevealAnimation =
                z
                        && task.getDisplayContent() != null
                        && task.mDisplayContent.mDisplayInfo.state == 2;
        boolean z3 = !task.inMultiWindowMode();
        WindowState window = task.getWindow(new Task$$ExternalSyntheticLambda0(6));
        ActivityRecord activityRecord = window != null ? window.mActivityRecord : null;
        if (activityRecord != null) {
            DisplayContent displayContent = activityRecord.getDisplayContent();
            if (z2) {
                if (activityRecord.isVisibleRequested()
                        && displayContent.mInputMethodWindow != null
                        && displayContent.isFixedRotationLaunchingApp(activityRecord)) {
                    startingWindowRemovalInfo.deferRemoveMode = 2;
                } else {
                    startingWindowRemovalInfo.deferRemoveMode = 1;
                }
            }
            WindowState findMainWindow = activityRecord.findMainWindow(false);
            if (findMainWindow == null || findMainWindow.mRemoved) {
                startingWindowRemovalInfo.playRevealAnimation = false;
            } else if (startingWindowRemovalInfo.playRevealAnimation && z3) {
                startingWindowRemovalInfo.roundedCornerRadius =
                        activityRecord.mAppCompatController.mAppCompatLetterboxPolicy
                                .mAppCompatRoundedCorners.getRoundedCornersRadius(findMainWindow);
                startingWindowRemovalInfo.windowAnimationLeash =
                        applyStartingWindowAnimation(findMainWindow);
                Rect rect = new Rect(findMainWindow.mWindowFrames.mFrame);
                startingWindowRemovalInfo.mainFrame = rect;
                Point point = findMainWindow.mSurfacePosition;
                rect.offsetTo(point.x, point.y);
            }
        }
        try {
            iTaskOrganizer.removeStartingWindow(startingWindowRemovalInfo);
        } catch (RemoteException e) {
            Slog.e("TaskOrganizerController", "Exception sending onStartTaskFinished callback", e);
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void restartTaskTopActivityProcessIfVisible(
            WindowContainerToken windowContainerToken) {
        ActivityTaskManagerService.enforceTaskPermission("restartTopActivityProcessIfVisible()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowContainer fromBinder =
                            WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        Slog.w("TaskOrganizerController", "Could not resolve window from token");
                    } else {
                        Task asTask = fromBinder.asTask();
                        if (asTask != null) {
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                        -558727273888268534L,
                                        1,
                                        null,
                                        Long.valueOf(asTask.mTaskId));
                            }
                            ActivityRecord topNonFinishingActivity =
                                    asTask.getTopNonFinishingActivity(true, true);
                            if (topNonFinishingActivity != null) {
                                topNonFinishingActivity.restartProcessIfVisible();
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        Slog.w("TaskOrganizerController", "Could not resolve task from token");
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

    public final void setFreeformTaskOpacity(int i, float f) {
        ActivityTaskManagerService.enforceTaskPermission("setFreeformTaskOpacity()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId =
                            this.mService.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    anyTaskForId.mFreeformAlpha = f;
                    this.mService.mWindowManager.mWindowPlacerLocked.performSurfacePlacement(false);
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

    public final void setFreeformTaskSurfaceOverlappedWithNavi(
            WindowContainerToken windowContainerToken, boolean z) {
        ActivityTaskManagerService.enforceTaskPermission(
                "setFreeformTaskSurfaceOverlappedWithNavi()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowContainer fromBinder =
                            WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        Slog.w("TaskOrganizerController", "Could not resolve window from token");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayContent displayContent = fromBinder.mDisplayContent;
                    if (displayContent != null) {
                        DisplayPolicy displayPolicy = displayContent.mDisplayPolicy;
                        if (z != displayPolicy.mFreeformTaskSurfaceOverlappingWithNavBar) {
                            displayPolicy.mFreeformTaskSurfaceOverlappingWithNavBar = z;
                            if (z != ((displayPolicy.mLastAppearance & 1048576) != 0)) {
                                displayPolicy.updateSystemBarAttributes();
                            }
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

    public final void setInterceptBackPressedOnTaskRoot(
            WindowContainerToken windowContainerToken, boolean z) {
        ActivityTaskManagerService.enforceTaskPermission("setInterceptBackPressedOnTaskRoot()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                -4296644831871159510L,
                                3,
                                null,
                                Boolean.valueOf(z));
                    }
                    WindowContainer fromBinder =
                            WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        Slog.w("TaskOrganizerController", "Could not resolve window from token");
                    } else {
                        Task asTask = fromBinder.asTask();
                        if (asTask != null) {
                            if (z) {
                                this.mInterceptBackPressedOnRootTasks.add(
                                        Integer.valueOf(asTask.mTaskId));
                            } else {
                                this.mInterceptBackPressedOnRootTasks.remove(
                                        Integer.valueOf(asTask.mTaskId));
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        Slog.w("TaskOrganizerController", "Could not resolve task from token");
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

    public final boolean togglePinTaskState(int i) {
        ActivityTaskManagerService.enforceTaskPermission("togglePinTaskState()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId =
                            this.mService.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean isFreeformPinned = anyTaskForId.isFreeformPinned();
                    if (isFreeformPinned) {
                        TaskDisplayArea displayArea = anyTaskForId.getDisplayArea();
                        if (displayArea != null) {
                            displayArea.stopFreeformTaskPinning(anyTaskForId);
                        }
                    } else {
                        anyTaskForId.startFreeformPinning();
                    }
                    boolean z = !isFreeformPinned;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return z;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterTaskOrganizer(final ITaskOrganizer iTaskOrganizer) {
        ActivityTaskManagerService.enforceTaskPermission("unregisterTaskOrganizer()");
        final int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizerController taskOrganizerController =
                                    TaskOrganizerController.this;
                            ITaskOrganizer iTaskOrganizer2 = iTaskOrganizer;
                            int i = callingUid;
                            TaskOrganizerController.TaskOrganizerState taskOrganizerState =
                                    (TaskOrganizerController.TaskOrganizerState)
                                            taskOrganizerController.mTaskOrganizerStates.get(
                                                    iTaskOrganizer2.asBinder());
                            if (taskOrganizerState == null) {
                                return;
                            }
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                        1705860547080436016L,
                                        4,
                                        null,
                                        String.valueOf(iTaskOrganizer2.asBinder()),
                                        Long.valueOf(i));
                            }
                            taskOrganizerState
                                    .mOrganizer
                                    .mTaskOrganizer
                                    .asBinder()
                                    .unlinkToDeath(taskOrganizerState.mDeathRecipient, 0);
                            taskOrganizerState.dispose();
                        }
                    };
            if (this.mService.mWindowOrganizerController.mTransitionController
                    .isShellTransitionsEnabled()) {
                this.mService.mWindowOrganizerController.mTransitionController.mRunningLock
                        .runWhenIdle(runnable);
            } else {
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        runnable.run();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }
}
