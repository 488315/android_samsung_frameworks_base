package com.android.server.wm;

import android.app.IApplicationThread;
import android.content.Intent;
import android.content.res.Configuration;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.RemoteAnimationDefinition;
import android.window.ITaskFragmentOrganizer;
import android.window.ITaskFragmentOrganizerController;
import android.window.RemoteTransition;
import android.window.TaskFragmentInfo;
import android.window.TaskFragmentOrganizer;
import android.window.TaskFragmentParentInfo;
import android.window.TaskFragmentTransaction;
import android.window.WindowContainerTransaction;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskFragmentOrganizerController extends ITaskFragmentOrganizerController.Stub {
    public final ActivityTaskManagerService mAtmService;
    public final WindowManagerGlobalLock mGlobalLock;
    public final WindowOrganizerController mWindowOrganizerController;
    public final ArrayMap mTaskFragmentOrganizerState = new ArrayMap();
    public final ArrayMap mPendingTaskFragmentEvents = new ArrayMap();
    public final ArraySet mTmpTaskSet = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingTaskFragmentEvent {
        public final ActivityRecord mActivity;
        public long mDeferTime;
        public final IBinder mErrorCallbackToken;
        public final int mEventType;
        public final Throwable mException;
        public final int mOpType;
        public final ActivityRecord mOtherActivity;
        public final Task mTask;
        public final TaskFragment mTaskFragment;
        public final ITaskFragmentOrganizer mTaskFragmentOrg;
        public final IBinder mTaskFragmentToken;

        public PendingTaskFragmentEvent(
                int i,
                ITaskFragmentOrganizer iTaskFragmentOrganizer,
                TaskFragment taskFragment,
                IBinder iBinder,
                IBinder iBinder2,
                Throwable th,
                ActivityRecord activityRecord,
                ActivityRecord activityRecord2,
                Task task,
                int i2) {
            this.mEventType = i;
            this.mTaskFragmentOrg = iTaskFragmentOrganizer;
            this.mTaskFragment = taskFragment;
            this.mTaskFragmentToken = iBinder;
            this.mErrorCallbackToken = iBinder2;
            this.mException = th;
            this.mActivity = activityRecord;
            this.mOtherActivity = activityRecord2;
            this.mTask = task;
            this.mOpType = i2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskFragmentOrganizerState implements IBinder.DeathRecipient {
        public final IApplicationThread mAppThread;
        public final boolean mIsSystemOrganizer;
        public final ITaskFragmentOrganizer mOrganizer;
        public final int mOrganizerPid;
        public final int mOrganizerUid;
        public RemoteAnimationDefinition mRemoteAnimationDefinition;
        public final ArrayList mOrganizedTaskFragments = new ArrayList();
        public final Map mLastSentTaskFragmentInfos = new WeakHashMap();
        public final Map mTaskFragmentTaskIds = new WeakHashMap();
        public final SparseArray mLastSentTaskFragmentParentInfos = new SparseArray();
        public final Map mTemporaryActivityTokens = new WeakHashMap();
        public final ArrayMap mDeferredTransitions = new ArrayMap();
        public final ArrayMap mInFlightTransactions = new ArrayMap();

        public TaskFragmentOrganizerState(
                ITaskFragmentOrganizer iTaskFragmentOrganizer, int i, int i2, boolean z) {
            if (Flags.bundleClientTransactionFlag()) {
                this.mAppThread = TaskFragmentOrganizerController.this.getAppThread(i, i2);
            } else {
                this.mAppThread = null;
            }
            this.mOrganizer = iTaskFragmentOrganizer;
            this.mOrganizerPid = i;
            this.mOrganizerUid = i2;
            this.mIsSystemOrganizer = z;
            try {
                iTaskFragmentOrganizer.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
                Slog.e(
                        "TaskFragmentOrganizerController",
                        "TaskFragmentOrganizer failed to register death recipient");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    TaskFragmentOrganizerController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    TaskFragmentOrganizerController.this.removeOrganizer(
                            this.mOrganizer, "client died");
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void dispatchTransaction(TaskFragmentTransaction taskFragmentTransaction) {
            if (taskFragmentTransaction.isEmpty()) {
                return;
            }
            try {
                if (Flags.bundleClientTransactionFlag()) {
                    this.mAppThread.scheduleTaskFragmentTransaction(
                            this.mOrganizer, taskFragmentTransaction);
                } else {
                    this.mOrganizer.onTransactionReady(taskFragmentTransaction);
                }
                if (TaskFragmentOrganizerController.this.mWindowOrganizerController
                        .mTransitionController.isCollecting()) {
                    int i = -1;
                    if (CoreRune.MW_EMBED_ACTIVITY) {
                        for (TaskFragmentTransaction.Change change :
                                taskFragmentTransaction.getChanges()) {
                            if (change.getTaskId() != -1) {
                                Task anyTaskForId =
                                        TaskFragmentOrganizerController.this.mAtmService
                                                .mRootWindowContainer.anyTaskForId(
                                                change.getTaskId(), 0, null, false);
                                if (anyTaskForId == null) {
                                    return;
                                }
                                if (change.getType() == 4
                                        && !anyTaskForId.isVisible()
                                        && anyTaskForId.isVisibleRequested()
                                        && anyTaskForId.mSharedStartingData != null) {
                                    return;
                                }
                                if (change.getType() == 2
                                        && TaskFragmentOrganizerController.this
                                                        .mWindowOrganizerController
                                                        .mTransitionController
                                                        .getCollectingTransitionType()
                                                == 1
                                        && !TaskFragmentOrganizerController.this
                                                .mWindowOrganizerController.mTransitionController
                                                .isCollecting(anyTaskForId)
                                        && !anyTaskForId.isVisible()
                                        && !anyTaskForId.isVisibleRequested()) {
                                    return;
                                }
                            }
                        }
                    }
                    int collectingTransitionId =
                            TaskFragmentOrganizerController.this.mWindowOrganizerController
                                    .mTransitionController.getCollectingTransitionId();
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                                7048981249808281819L,
                                1,
                                null,
                                Long.valueOf(collectingTransitionId),
                                String.valueOf(taskFragmentTransaction.getTransactionToken()));
                    }
                    if (CoreRune.MW_EMBED_ACTIVITY_DEBUG_LOG) {
                        Iterator it = taskFragmentTransaction.getChanges().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            TaskFragmentTransaction.Change change2 =
                                    (TaskFragmentTransaction.Change) it.next();
                            if (change2.getTaskId() != -1) {
                                i = change2.getTaskId();
                                break;
                            }
                        }
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        collectingTransitionId,
                                        "Defer transition id=",
                                        " for TaskFragmentTransaction=");
                        m.append(taskFragmentTransaction.getTransactionToken());
                        m.append(", taskId=");
                        m.append(i);
                        Slog.d("TaskFragmentOrganizerController", m.toString());
                    }
                    this.mDeferredTransitions.put(
                            taskFragmentTransaction.getTransactionToken(),
                            Integer.valueOf(collectingTransitionId));
                    TaskFragmentOrganizerController.this.mWindowOrganizerController
                            .mTransitionController.deferTransitionReady();
                    Transition.ReadyCondition readyCondition =
                            new Transition.ReadyCondition(
                                    "task-fragment transaction", taskFragmentTransaction);
                    TaskFragmentOrganizerController.this.mWindowOrganizerController
                            .mTransitionController.waitFor(readyCondition);
                    this.mInFlightTransactions.put(
                            taskFragmentTransaction.getTransactionToken(), readyCondition);
                }
            } catch (RemoteException e) {
                Slog.d(
                        "TaskFragmentOrganizerController",
                        "Exception sending TaskFragmentTransaction",
                        e);
            }
        }

        public final void onTransactionFinished(IBinder iBinder) {
            if (this.mDeferredTransitions.containsKey(iBinder)) {
                int intValue = ((Integer) this.mDeferredTransitions.remove(iBinder)).intValue();
                boolean isCollecting =
                        TaskFragmentOrganizerController.this.mWindowOrganizerController
                                .mTransitionController.isCollecting();
                boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled;
                if (isCollecting
                        && TaskFragmentOrganizerController.this.mWindowOrganizerController
                                        .mTransitionController.getCollectingTransitionId()
                                == intValue) {
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                                7421521217481553621L,
                                1,
                                null,
                                Long.valueOf(intValue),
                                String.valueOf(iBinder));
                    }
                    if (CoreRune.MW_EMBED_ACTIVITY_DEBUG_LOG) {
                        Slog.d(
                                "TaskFragmentOrganizerController",
                                "Continue transition id="
                                        + intValue
                                        + " for TaskFragmentTransaction="
                                        + iBinder);
                    }
                    TaskFragmentOrganizerController.this.mWindowOrganizerController
                            .mTransitionController.continueTransitionReady();
                    return;
                }
                if (zArr[3]) {
                    ProtoLogImpl_54989576.w(
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                            -1315509853595025526L,
                            1,
                            null,
                            Long.valueOf(intValue),
                            String.valueOf(iBinder));
                }
                if (CoreRune.MW_EMBED_ACTIVITY_DEBUG_LOG) {
                    Slog.d(
                            "TaskFragmentOrganizerController",
                            "Deferred transition id="
                                    + intValue
                                    + " has been continued before the TaskFragmentTransaction="
                                    + iBinder);
                }
            }
        }
    }

    public TaskFragmentOrganizerController(
            ActivityTaskManagerService activityTaskManagerService,
            WindowOrganizerController windowOrganizerController) {
        Objects.requireNonNull(activityTaskManagerService);
        this.mAtmService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        Objects.requireNonNull(windowOrganizerController);
        this.mWindowOrganizerController = windowOrganizerController;
    }

    public final void addPendingEvent(PendingTaskFragmentEvent pendingTaskFragmentEvent) {
        ((List)
                        this.mPendingTaskFragmentEvents.get(
                                pendingTaskFragmentEvent.mTaskFragmentOrg.asBinder()))
                .add(pendingTaskFragmentEvent);
    }

    public final void applyTransaction(
            WindowContainerTransaction windowContainerTransaction,
            int i,
            boolean z,
            RemoteTransition remoteTransition) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!isValidTransaction(windowContainerTransaction)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    this.mWindowOrganizerController.applyTaskFragmentTransactionLocked(
                            windowContainerTransaction, i, z, remoteTransition);
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void dispatchPendingInfoChangedEvent(TaskFragment taskFragment) {
        PendingTaskFragmentEvent pendingTaskFragmentEvent =
                getPendingTaskFragmentEvent(taskFragment, 2);
        if (pendingTaskFragmentEvent == null) {
            return;
        }
        ITaskFragmentOrganizer iTaskFragmentOrganizer = taskFragment.mTaskFragmentOrganizer;
        TaskFragmentOrganizerState validateAndGetState =
                validateAndGetState(iTaskFragmentOrganizer);
        TaskFragmentTransaction taskFragmentTransaction = new TaskFragmentTransaction();
        Task task = taskFragment.getTask();
        Objects.requireNonNull(task);
        taskFragmentTransaction.addChange(
                prepareChange(
                        new PendingTaskFragmentEvent(
                                3,
                                iTaskFragmentOrganizer,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                task,
                                0)));
        taskFragmentTransaction.addChange(prepareChange(pendingTaskFragmentEvent));
        validateAndGetState.dispatchTransaction(taskFragmentTransaction);
        ((List) this.mPendingTaskFragmentEvents.get(iTaskFragmentOrganizer.asBinder()))
                .remove(pendingTaskFragmentEvent);
    }

    public IApplicationThread getAppThread(int i, int i2) {
        WindowProcessController process = this.mAtmService.mProcessMap.getProcess(i);
        IApplicationThread iApplicationThread =
                (process == null || process.mUid != i2) ? null : process.mThread;
        if (iApplicationThread != null) {
            return iApplicationThread;
        }
        throw new IllegalArgumentException(
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "Cannot find process for pid=", " uid="));
    }

    public final PendingTaskFragmentEvent getPendingTaskFragmentEvent(
            TaskFragment taskFragment, int i) {
        List list =
                (List)
                        this.mPendingTaskFragmentEvents.get(
                                taskFragment.mTaskFragmentOrganizer.asBinder());
        for (int size = list.size() - 1; size >= 0; size--) {
            PendingTaskFragmentEvent pendingTaskFragmentEvent =
                    (PendingTaskFragmentEvent) list.get(size);
            if (taskFragment == pendingTaskFragmentEvent.mTaskFragment
                    && i == pendingTaskFragmentEvent.mEventType) {
                return pendingTaskFragmentEvent;
            }
        }
        return null;
    }

    public final boolean isActivityEmbedded(IBinder iBinder) {
        boolean z;
        TaskFragment organizedTaskFragment;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                z = false;
                if (forTokenLocked != null
                        && (organizedTaskFragment = forTokenLocked.getOrganizedTaskFragment())
                                != null
                        && organizedTaskFragment.isEmbeddedWithBoundsOverride()) {
                    z = true;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public final boolean isSupportActivityEmbedded(String str) {
        boolean embedActivityPackageEnabled;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                embedActivityPackageEnabled =
                        this.mAtmService.mMultiTaskingController.getEmbedActivityPackageEnabled(
                                str, UserHandle.getCallingUserId());
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return embedActivityPackageEnabled;
    }

    public final boolean isSystemOrganizer(IBinder iBinder) {
        TaskFragmentOrganizerState taskFragmentOrganizerState =
                (TaskFragmentOrganizerState) this.mTaskFragmentOrganizerState.get(iBinder);
        return taskFragmentOrganizerState != null && taskFragmentOrganizerState.mIsSystemOrganizer;
    }

    public final boolean isValidTransaction(WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction.isEmpty()) {
            return false;
        }
        ITaskFragmentOrganizer taskFragmentOrganizer =
                windowContainerTransaction.getTaskFragmentOrganizer();
        if (windowContainerTransaction.getTaskFragmentOrganizer() != null
                && this.mTaskFragmentOrganizerState.containsKey(taskFragmentOrganizer.asBinder())) {
            return true;
        }
        Slog.e(
                "TaskFragmentOrganizerController",
                "Caller organizer=" + taskFragmentOrganizer + " is no longer registered");
        return false;
    }

    public final void onActivityReparentedToTask(final ActivityRecord activityRecord) {
        Task task = activityRecord.task;
        ITaskFragmentOrganizer iTaskFragmentOrganizer =
                activityRecord.mLastTaskFragmentOrganizerBeforePip;
        if (iTaskFragmentOrganizer == null) {
            final TaskFragment[] taskFragmentArr = new TaskFragment[1];
            task.forAllLeafTaskFragments(
                    new Predicate() { // from class:
                                      // com.android.server.wm.TaskFragmentOrganizerController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            TaskFragment[] taskFragmentArr2 = taskFragmentArr;
                            TaskFragment taskFragment = (TaskFragment) obj;
                            if (!taskFragment.isOrganizedTaskFragment()) {
                                return false;
                            }
                            taskFragmentArr2[0] = taskFragment;
                            return true;
                        }
                    });
            TaskFragment taskFragment = taskFragmentArr[0];
            if (taskFragment == null) {
                return;
            } else {
                iTaskFragmentOrganizer = taskFragment.mTaskFragmentOrganizer;
            }
        }
        ITaskFragmentOrganizer iTaskFragmentOrganizer2 = iTaskFragmentOrganizer;
        if (!this.mTaskFragmentOrganizerState.containsKey(iTaskFragmentOrganizer2.asBinder())) {
            Slog.w(
                    "TaskFragmentOrganizerController",
                    "The last TaskFragmentOrganizer no longer exists");
            return;
        }
        final IBinder iBinder = activityRecord.mLastEmbeddedParentTfTokenBeforePip;
        ActivityRecord activity =
                task.getActivity(
                        new Predicate() { // from class:
                                          // com.android.server.wm.TaskFragmentOrganizerController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                                return (activityRecord2 == ActivityRecord.this
                                                || activityRecord2.finishing
                                                || activityRecord2.getTaskFragment().mFragmentToken
                                                        == iBinder)
                                        ? false
                                        : true;
                            }
                        });
        addPendingEvent(
                new PendingTaskFragmentEvent(
                        5,
                        iTaskFragmentOrganizer2,
                        null,
                        iBinder,
                        null,
                        null,
                        activityRecord,
                        (activity == null
                                        || (activity.isEmbedded()
                                                && !activity.getTaskFragment().fillsParent()))
                                ? null
                                : activity,
                        null,
                        0));
    }

    public final void onTransactionHandled(
            IBinder iBinder,
            WindowContainerTransaction windowContainerTransaction,
            int i,
            boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (isValidTransaction(windowContainerTransaction)) {
                    applyTransaction(windowContainerTransaction, i, z, null);
                }
                ITaskFragmentOrganizer taskFragmentOrganizer =
                        windowContainerTransaction.getTaskFragmentOrganizer();
                TaskFragmentOrganizerState taskFragmentOrganizerState =
                        taskFragmentOrganizer != null
                                ? (TaskFragmentOrganizerState)
                                        this.mTaskFragmentOrganizerState.get(
                                                taskFragmentOrganizer.asBinder())
                                : null;
                if (taskFragmentOrganizerState != null) {
                    taskFragmentOrganizerState.onTransactionFinished(iBinder);
                    Transition.ReadyCondition readyCondition =
                            (Transition.ReadyCondition)
                                    taskFragmentOrganizerState.mInFlightTransactions.remove(
                                            iBinder);
                    if (readyCondition != null) {
                        readyCondition.meet();
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final TaskFragmentTransaction.Change prepareChange(
            PendingTaskFragmentEvent pendingTaskFragmentEvent) {
        int i;
        boolean z;
        final IBinder binder;
        ActivityRecord activityRecord;
        final TaskFragmentOrganizerState taskFragmentOrganizerState =
                (TaskFragmentOrganizerState)
                        this.mTaskFragmentOrganizerState.get(
                                pendingTaskFragmentEvent.mTaskFragmentOrg.asBinder());
        if (taskFragmentOrganizerState == null) {
            return null;
        }
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled;
        int i2 = pendingTaskFragmentEvent.mEventType;
        TaskFragment taskFragment = pendingTaskFragmentEvent.mTaskFragment;
        if (i2 == 0) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                        -2808577027789344626L,
                        0,
                        null,
                        String.valueOf(taskFragment.getName()));
            }
            TaskFragmentInfo taskFragmentInfo = taskFragment.getTaskFragmentInfo();
            int i3 = taskFragment.getTask().mTaskId;
            taskFragment.mTaskFragmentAppearedSent = true;
            ((WeakHashMap) taskFragmentOrganizerState.mLastSentTaskFragmentInfos)
                    .put(taskFragment, taskFragmentInfo);
            ((WeakHashMap) taskFragmentOrganizerState.mTaskFragmentTaskIds)
                    .put(taskFragment, Integer.valueOf(i3));
            TaskFragmentTransaction.Change taskId =
                    new TaskFragmentTransaction.Change(1)
                            .setTaskFragmentToken(taskFragment.mFragmentToken)
                            .setTaskFragmentInfo(taskFragmentInfo)
                            .setTaskId(i3);
            if (taskFragmentOrganizerState.mIsSystemOrganizer) {
                taskId.setTaskFragmentSurfaceControl(taskFragment.mSurfaceControl);
            }
            return taskId;
        }
        if (i2 == 1) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                        -3582112419663037270L,
                        0,
                        null,
                        String.valueOf(taskFragment.getName()));
            }
            taskFragment.mTaskFragmentAppearedSent = false;
            ((WeakHashMap) taskFragmentOrganizerState.mLastSentTaskFragmentInfos)
                    .remove(taskFragment);
            if (((WeakHashMap) taskFragmentOrganizerState.mTaskFragmentTaskIds)
                    .containsKey(taskFragment)) {
                Integer num =
                        (Integer)
                                ((WeakHashMap) taskFragmentOrganizerState.mTaskFragmentTaskIds)
                                        .remove(taskFragment);
                i = num.intValue();
                if (!((WeakHashMap) taskFragmentOrganizerState.mTaskFragmentTaskIds)
                        .containsValue(num)) {
                    taskFragmentOrganizerState.mLastSentTaskFragmentParentInfos.remove(i);
                }
            } else {
                i = -1;
            }
            return new TaskFragmentTransaction.Change(3)
                    .setTaskFragmentToken(taskFragment.mFragmentToken)
                    .setTaskFragmentInfo(taskFragment.getTaskFragmentInfo())
                    .setTaskId(i);
        }
        if (i2 == 2) {
            TaskFragmentInfo taskFragmentInfo2 = taskFragment.getTaskFragmentInfo();
            TaskFragmentInfo taskFragmentInfo3 =
                    (TaskFragmentInfo)
                            ((WeakHashMap) taskFragmentOrganizerState.mLastSentTaskFragmentInfos)
                                    .get(taskFragment);
            if (taskFragmentInfo2.equalsForTaskFragmentOrganizer(taskFragmentInfo3)
                    && WindowOrganizerController.configurationsAreEqualForOrganizer(
                            taskFragmentInfo2.getConfiguration(),
                            taskFragmentInfo3.getConfiguration())) {
                return null;
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                        3294593748816836746L,
                        0,
                        null,
                        String.valueOf(taskFragment.getName()));
            }
            ((WeakHashMap) taskFragmentOrganizerState.mLastSentTaskFragmentInfos)
                    .put(taskFragment, taskFragmentInfo2);
            return new TaskFragmentTransaction.Change(2)
                    .setTaskFragmentToken(taskFragment.mFragmentToken)
                    .setTaskFragmentInfo(taskFragmentInfo2)
                    .setTaskId(taskFragment.getTask().mTaskId);
        }
        if (i2 == 3) {
            Task task = pendingTaskFragmentEvent.mTask;
            int i4 = task.mTaskId;
            Configuration configuration = task.getConfiguration();
            int displayId = task.getDisplayId();
            boolean shouldBeVisible = task.shouldBeVisible(null);
            int childCount = task.getChildCount() - 1;
            while (true) {
                if (childCount >= 0) {
                    ActivityRecord asActivityRecord =
                            task.getChildAt(childCount).asActivityRecord();
                    if (asActivityRecord != null && !asActivityRecord.finishing) {
                        z = true;
                        break;
                    }
                    childCount--;
                } else {
                    z = false;
                    break;
                }
            }
            Task.DecorSurfaceContainer decorSurfaceContainer = task.mDecorSurfaceContainer;
            TaskFragmentParentInfo taskFragmentParentInfo =
                    new TaskFragmentParentInfo(
                            configuration,
                            displayId,
                            shouldBeVisible,
                            z,
                            decorSurfaceContainer != null
                                    ? decorSurfaceContainer.mDecorSurface
                                    : null);
            TaskFragmentParentInfo taskFragmentParentInfo2 =
                    (TaskFragmentParentInfo)
                            taskFragmentOrganizerState.mLastSentTaskFragmentParentInfos.get(i4);
            Configuration configuration2 =
                    taskFragmentParentInfo2 != null
                            ? taskFragmentParentInfo2.getConfiguration()
                            : null;
            if (taskFragmentParentInfo.equalsForTaskFragmentOrganizer(taskFragmentParentInfo2)
                    && WindowOrganizerController.configurationsAreEqualForOrganizer(
                            taskFragmentParentInfo.getConfiguration(), configuration2)) {
                return null;
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                        5007230330523630579L,
                        4,
                        null,
                        String.valueOf(task.getName()),
                        Long.valueOf(i4));
            }
            taskFragmentOrganizerState.mLastSentTaskFragmentParentInfos.put(
                    i4, new TaskFragmentParentInfo(taskFragmentParentInfo));
            return new TaskFragmentTransaction.Change(4)
                    .setTaskId(i4)
                    .setTaskFragmentParentInfo(taskFragmentParentInfo);
        }
        if (i2 == 4) {
            IBinder iBinder = pendingTaskFragmentEvent.mErrorCallbackToken;
            Throwable th = pendingTaskFragmentEvent.mException;
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                        6475066005515810081L,
                        0,
                        null,
                        String.valueOf(th.toString()));
            }
            return new TaskFragmentTransaction.Change(5)
                    .setErrorCallbackToken(iBinder)
                    .setErrorBundle(
                            TaskFragmentOrganizer.putErrorInfoInBundle(
                                    th,
                                    taskFragment != null
                                            ? taskFragment.getTaskFragmentInfo()
                                            : null,
                                    pendingTaskFragmentEvent.mOpType));
        }
        if (i2 != 5) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i2, "Unknown TaskFragmentEvent="));
        }
        IBinder iBinder2 = pendingTaskFragmentEvent.mTaskFragmentToken;
        ActivityRecord activityRecord2 = pendingTaskFragmentEvent.mActivity;
        if (activityRecord2.finishing) {
            Slog.d(
                    "TaskFragmentOrganizerController",
                    "Reparent activity=" + activityRecord2.token + " is finishing");
        } else {
            Task task2 = activityRecord2.task;
            if (task2 != null) {
                int i5 = task2.effectiveUid;
                int i6 = taskFragmentOrganizerState.mOrganizerUid;
                if (i5 == i6) {
                    if (task2.isAllowedToEmbedActivity(i6, activityRecord2) != 0) {
                        Slog.d(
                                "TaskFragmentOrganizerController",
                                "Reparent activity="
                                        + activityRecord2.token
                                        + " is not allowed to be embedded.");
                    } else {
                        if (!task2.isAllowedToEmbedActivityInTrustedMode(
                                taskFragmentOrganizerState.mOrganizerUid, activityRecord2)) {
                            if (!(Flags.untrustedEmbeddingStateSharing()
                                    ? activityRecord2.mAllowUntrustedEmbeddingStateSharing
                                    : false)) {
                                Slog.d(
                                        "TaskFragmentOrganizerController",
                                        "Reparent activity="
                                                + activityRecord2.token
                                                + " is not allowed to be shared with untrusted"
                                                + " host.");
                            }
                        }
                        if (activityRecord2.getPid() == taskFragmentOrganizerState.mOrganizerPid) {
                            binder = activityRecord2.token;
                        } else {
                            binder = new Binder("TemporaryActivityToken");
                            ((WeakHashMap) taskFragmentOrganizerState.mTemporaryActivityTokens)
                                    .put(binder, activityRecord2);
                            TaskFragmentOrganizerController.this.mAtmService.mWindowManager.mH
                                    .postDelayed(
                                            new Runnable() { // from class:
                                                             // com.android.server.wm.TaskFragmentOrganizerController$TaskFragmentOrganizerState$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    TaskFragmentOrganizerController
                                                                    .TaskFragmentOrganizerState
                                                            taskFragmentOrganizerState2 =
                                                                    TaskFragmentOrganizerController
                                                                            .TaskFragmentOrganizerState
                                                                            .this;
                                                    IBinder iBinder3 = binder;
                                                    WindowManagerGlobalLock
                                                            windowManagerGlobalLock =
                                                                    TaskFragmentOrganizerController
                                                                            .this
                                                                            .mGlobalLock;
                                                    WindowManagerService
                                                            .boostPriorityForLockedSection();
                                                    synchronized (windowManagerGlobalLock) {
                                                        try {
                                                            ((WeakHashMap)
                                                                            taskFragmentOrganizerState2
                                                                                    .mTemporaryActivityTokens)
                                                                    .remove(iBinder3);
                                                        } catch (Throwable th2) {
                                                            WindowManagerService
                                                                    .resetPriorityAfterLockedSection();
                                                            throw th2;
                                                        }
                                                    }
                                                    WindowManagerService
                                                            .resetPriorityAfterLockedSection();
                                                }
                                            },
                                            5000L);
                        }
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                    -7893265697482064583L,
                                    4,
                                    null,
                                    String.valueOf(activityRecord2.token),
                                    Long.valueOf(task2.mTaskId));
                        }
                        TaskFragmentTransaction.Change taskId2 =
                                new TaskFragmentTransaction.Change(6).setTaskId(task2.mTaskId);
                        Intent intent = activityRecord2.intent;
                        r0 =
                                taskId2.setActivityIntent(
                                                new Intent()
                                                        .setComponent(intent.getComponent())
                                                        .setPackage(intent.getPackage())
                                                        .setAction(intent.getAction()))
                                        .setActivityToken(binder);
                        if (iBinder2 != null) {
                            r0.setTaskFragmentToken(iBinder2);
                        }
                        if (Flags.fixPipRestoreToOverlay()
                                && (activityRecord = pendingTaskFragmentEvent.mOtherActivity)
                                        != null
                                && activityRecord.getPid()
                                        == taskFragmentOrganizerState.mOrganizerPid) {
                            r0.setOtherActivityToken(activityRecord.token);
                        }
                    }
                }
            }
            Slog.d(
                    "TaskFragmentOrganizerController",
                    "Reparent activity="
                            + activityRecord2.token
                            + " is not in a task belong to the organizer app.");
        }
        return r0;
    }

    public final void registerOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z) {
        boolean z2 = Flags.taskFragmentSystemOrganizerFlag() && z;
        if (z2) {
            ActivityTaskManagerService.enforceTaskPermission("registerSystemOrganizer()");
        }
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                            3509684748201636981L,
                            20,
                            null,
                            String.valueOf(iTaskFragmentOrganizer.asBinder()),
                            Long.valueOf(callingUid),
                            Long.valueOf(callingPid));
                }
                if (this.mTaskFragmentOrganizerState.containsKey(
                        iTaskFragmentOrganizer.asBinder())) {
                    throw new IllegalStateException(
                            "Replacing existing organizer currently unsupported");
                }
                this.mTaskFragmentOrganizerState.put(
                        iTaskFragmentOrganizer.asBinder(),
                        new TaskFragmentOrganizerState(
                                iTaskFragmentOrganizer, callingPid, callingUid, z2));
                this.mPendingTaskFragmentEvents.put(
                        iTaskFragmentOrganizer.asBinder(), new ArrayList());
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void registerRemoteAnimations(
            ITaskFragmentOrganizer iTaskFragmentOrganizer,
            RemoteAnimationDefinition remoteAnimationDefinition) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                            1327792561585467865L,
                            20,
                            null,
                            String.valueOf(iTaskFragmentOrganizer.asBinder()),
                            Long.valueOf(callingUid),
                            Long.valueOf(callingPid));
                }
                TaskFragmentOrganizerState taskFragmentOrganizerState =
                        (TaskFragmentOrganizerState)
                                this.mTaskFragmentOrganizerState.get(
                                        iTaskFragmentOrganizer.asBinder());
                if (taskFragmentOrganizerState == null) {
                    throw new IllegalStateException("The organizer hasn't been registered.");
                }
                if (taskFragmentOrganizerState.mRemoteAnimationDefinition != null) {
                    throw new IllegalStateException(
                            "The organizer has already registered remote animations="
                                    + taskFragmentOrganizerState.mRemoteAnimationDefinition);
                }
                remoteAnimationDefinition.setCallingPidUid(callingPid, callingUid);
                taskFragmentOrganizerState.mRemoteAnimationDefinition = remoteAnimationDefinition;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void removeOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer, String str) {
        TaskFragmentOrganizerState taskFragmentOrganizerState =
                (TaskFragmentOrganizerState)
                        this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
        if (taskFragmentOrganizerState == null) {
            Slog.w("TaskFragmentOrganizerController", "The organizer has already been removed.");
            return;
        }
        this.mPendingTaskFragmentEvents.remove(iTaskFragmentOrganizer.asBinder());
        boolean z = false;
        for (int size = taskFragmentOrganizerState.mOrganizedTaskFragments.size() - 1;
                size >= 0;
                size--) {
            TaskFragment taskFragment =
                    (TaskFragment) taskFragmentOrganizerState.mOrganizedTaskFragments.get(size);
            if (taskFragment.isVisibleRequested()) {
                z = true;
            }
            taskFragment.mTaskFragmentOrganizer = null;
        }
        TransitionController transitionController =
                TaskFragmentOrganizerController.this
                        .mAtmService
                        .mWindowOrganizerController
                        .mTransitionController;
        if (z
                && transitionController.isShellTransitionsEnabled()
                && !transitionController.isCollecting()) {
            Task task =
                    ((TaskFragment) taskFragmentOrganizerState.mOrganizedTaskFragments.get(0))
                            .getTask();
            boolean z2 =
                    (task == null
                                    || task.getActivity(
                                                    new TaskFragmentOrganizerController$TaskFragmentOrganizerState$$ExternalSyntheticLambda0())
                                            == null)
                            ? false
                            : true;
            Transition createTransition = transitionController.createTransition(2, 0);
            if (z2) {
                task = null;
            }
            transitionController.requestStartTransition(createTransition, task, null, null);
        }
        TaskFragmentOrganizerController.this.mAtmService.deferWindowLayout();
        while (!taskFragmentOrganizerState.mOrganizedTaskFragments.isEmpty()) {
            try {
                ((TaskFragment) taskFragmentOrganizerState.mOrganizedTaskFragments.remove(0))
                        .removeImmediately();
            } catch (Throwable th) {
                TaskFragmentOrganizerController.this.mAtmService.continueWindowLayout();
                throw th;
            }
        }
        TaskFragmentOrganizerController.this.mAtmService.continueWindowLayout();
        for (int size2 = taskFragmentOrganizerState.mDeferredTransitions.size() - 1;
                size2 >= 0;
                size2--) {
            taskFragmentOrganizerState.onTransactionFinished(
                    (IBinder) taskFragmentOrganizerState.mDeferredTransitions.keyAt(size2));
        }
        for (int size3 = taskFragmentOrganizerState.mInFlightTransactions.size() - 1;
                size3 >= 0;
                size3--) {
            Transition.ReadyCondition readyCondition =
                    (Transition.ReadyCondition)
                            taskFragmentOrganizerState.mInFlightTransactions.valueAt(size3);
            String m = XmlUtils$$ExternalSyntheticOutline0.m("disposed(", str, ")");
            if (!readyCondition.mMet) {
                readyCondition.mAlternate = m;
                readyCondition.meet();
            }
        }
        taskFragmentOrganizerState
                .mOrganizer
                .asBinder()
                .unlinkToDeath(taskFragmentOrganizerState, 0);
        this.mTaskFragmentOrganizerState.remove(iTaskFragmentOrganizer.asBinder());
    }

    public final void unregisterOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        int callingPid = Binder.getCallingPid();
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                -6777461169027010201L,
                                20,
                                null,
                                String.valueOf(iTaskFragmentOrganizer.asBinder()),
                                Long.valueOf(callingUid),
                                Long.valueOf(callingPid));
                    }
                    removeOrganizer(iTaskFragmentOrganizer, "unregistered");
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

    public final void unregisterRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        int callingPid = Binder.getCallingPid();
        long callingUid = Binder.getCallingUid();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                            -2524361347368208519L,
                            20,
                            null,
                            String.valueOf(iTaskFragmentOrganizer.asBinder()),
                            Long.valueOf(callingUid),
                            Long.valueOf(callingPid));
                }
                TaskFragmentOrganizerState taskFragmentOrganizerState =
                        (TaskFragmentOrganizerState)
                                this.mTaskFragmentOrganizerState.get(
                                        iTaskFragmentOrganizer.asBinder());
                if (taskFragmentOrganizerState == null) {
                    Slog.e(
                            "TaskFragmentOrganizerController",
                            "The organizer hasn't been registered.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    taskFragmentOrganizerState.mRemoteAnimationDefinition = null;
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final TaskFragmentOrganizerState validateAndGetState(
            ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        TaskFragmentOrganizerState taskFragmentOrganizerState =
                (TaskFragmentOrganizerState)
                        this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
        if (taskFragmentOrganizerState != null) {
            return taskFragmentOrganizerState;
        }
        throw new IllegalArgumentException(
                "TaskFragmentOrganizer has not been registered. Organizer="
                        + iTaskFragmentOrganizer);
    }
}
