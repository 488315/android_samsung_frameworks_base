package com.android.server.wm;

import android.app.ActivityOptions;
import android.os.Debug;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ResetTargetTaskHelper implements Consumer, Predicate {
    public int mActivityReparentPosition;
    public ArrayList mAllActivities;
    public boolean mCanMoveOptions;
    public boolean mForceReset;
    public boolean mIsTargetTask;
    public ArrayList mPendingReparentActivities;
    public ArrayList mResultActivities;
    public ActivityRecord mRoot;
    public Task mTargetRootTask;
    public Task mTargetTask;
    public boolean mTargetTaskFound;
    public Task mTask;
    public ActivityOptions mTopOptions;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Task task = (Task) obj;
        this.mTask = task;
        this.mRoot = null;
        this.mCanMoveOptions = true;
        this.mTopOptions = null;
        this.mResultActivities.clear();
        this.mAllActivities.clear();
        ActivityRecord rootActivity = task.getRootActivity(false, true);
        this.mRoot = rootActivity;
        if (rootActivity == null) {
            return;
        }
        boolean z = task == this.mTargetTask;
        this.mIsTargetTask = z;
        if (z) {
            this.mTargetTaskFound = true;
        }
        task.forAllActivities((Predicate) this);
    }

    public final void finishActivities(String str, ArrayList arrayList) {
        boolean z = this.mCanMoveOptions;
        while (!arrayList.isEmpty()) {
            ActivityRecord activityRecord = (ActivityRecord) arrayList.remove(0);
            if (!activityRecord.finishing) {
                this.mCanMoveOptions = false;
                if (z && this.mTopOptions == null) {
                    ActivityOptions activityOptions = activityRecord.mPendingOptions;
                    this.mTopOptions = activityOptions;
                    if (activityOptions != null) {
                        activityRecord.mPendingOptions = null;
                        activityRecord.mPendingRemoteAnimation = null;
                        activityRecord.mPendingRemoteTransition = null;
                        z = false;
                    }
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[3]) {
                    ProtoLogImpl_54989576.w(
                            ProtoLogGroup.WM_DEBUG_TASKS,
                            -4617490621756721600L,
                            0,
                            null,
                            String.valueOf(activityRecord));
                }
                activityRecord.finishIfPossible(str, false);
            }
        }
    }

    public final ActivityOptions process(Task task, boolean z) {
        ActivityRecord activityRecord;
        Task reuseOrCreateTask;
        this.mForceReset = z;
        this.mTargetTask = task;
        this.mTargetTaskFound = false;
        this.mTargetRootTask = task.getRootTask();
        this.mActivityReparentPosition = -1;
        char c = 1;
        task.mWmService.mRoot.forAllLeafTasks(this, true);
        if (!this.mPendingReparentActivities.isEmpty()) {
            Task task2 = this.mTargetRootTask;
            ActivityTaskManagerService activityTaskManagerService = task2.mAtmService;
            TaskDisplayArea displayArea = task2.getDisplayArea();
            int windowingMode = this.mTargetRootTask.getWindowingMode();
            int activityType = this.mTargetRootTask.getActivityType();
            while (!this.mPendingReparentActivities.isEmpty()) {
                ActivityRecord activityRecord2 =
                        (ActivityRecord) this.mPendingReparentActivities.remove(0);
                boolean alwaysCreateRootTask =
                        DisplayContent.alwaysCreateRootTask(windowingMode, activityType);
                Task bottomMostTask =
                        alwaysCreateRootTask
                                ? displayArea.getBottomMostTask()
                                : this.mTargetRootTask.getBottomMostTask();
                if (bottomMostTask == null
                        || !activityRecord2.taskAffinity.equals(bottomMostTask.affinity)) {
                    bottomMostTask = null;
                } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[c]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_TASKS,
                            1730793580703791926L,
                            0,
                            null,
                            String.valueOf(activityRecord2),
                            String.valueOf(bottomMostTask));
                }
                if (bottomMostTask == null) {
                    if (alwaysCreateRootTask) {
                        activityRecord = activityRecord2;
                        reuseOrCreateTask =
                                displayArea.getOrCreateRootTask(
                                        windowingMode, activityType, false, null, null, null, 0);
                    } else {
                        activityRecord = activityRecord2;
                        reuseOrCreateTask =
                                this.mTargetRootTask.reuseOrCreateTask(
                                        activityRecord.info,
                                        null,
                                        null,
                                        null,
                                        false,
                                        null,
                                        null,
                                        null);
                    }
                    bottomMostTask = reuseOrCreateTask;
                    bottomMostTask.affinityIntent = activityRecord.intent;
                } else {
                    activityRecord = activityRecord2;
                }
                activityRecord.reparent(bottomMostTask, 0, "resetTargetTaskIfNeeded");
                activityTaskManagerService.mTaskSupervisor.mRecentTasks.add(bottomMostTask);
                c = 1;
            }
        }
        this.mTask = null;
        this.mRoot = null;
        this.mCanMoveOptions = true;
        this.mTopOptions = null;
        this.mResultActivities.clear();
        this.mAllActivities.clear();
        return this.mTopOptions;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        String str;
        ActivityRecord activityBelow;
        String str2;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        if (activityRecord == this.mRoot) {
            return true;
        }
        this.mAllActivities.add(activityRecord);
        int i = activityRecord.info.flags;
        boolean z = (i & 2) != 0;
        boolean z2 = (i & 64) != 0;
        boolean z3 = (activityRecord.intent.getFlags() & 524288) != 0;
        if (this.mIsTargetTask) {
            if (!z && !z3) {
                if (activityRecord.resultTo != null) {
                    this.mResultActivities.add(activityRecord);
                } else if (z2
                        && (str2 = activityRecord.taskAffinity) != null
                        && !str2.equals(this.mTask.affinity)) {
                    this.mPendingReparentActivities.add(activityRecord);
                }
            }
            if (this.mForceReset || z || z3) {
                if (z3) {
                    finishActivities("clearWhenTaskReset", this.mAllActivities);
                } else {
                    this.mResultActivities.add(activityRecord);
                    finishActivities("reset-task", this.mResultActivities);
                }
                this.mResultActivities.clear();
            } else {
                this.mResultActivities.clear();
            }
        } else if (activityRecord.resultTo != null) {
            this.mResultActivities.add(activityRecord);
        } else if (this.mTargetTaskFound
                && z2
                && (str = this.mTargetTask.affinity) != null
                && str.equals(activityRecord.taskAffinity)) {
            this.mResultActivities.add(activityRecord);
            if (this.mForceReset || z) {
                finishActivities("move-affinity", this.mResultActivities);
            } else {
                if (this.mActivityReparentPosition == -1) {
                    this.mActivityReparentPosition = this.mTargetTask.getChildCount();
                }
                Task task = this.mTargetTask;
                int i2 = this.mActivityReparentPosition;
                while (!this.mResultActivities.isEmpty()) {
                    ActivityRecord activityRecord2 =
                            (ActivityRecord) this.mResultActivities.remove(0);
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[2]) {
                        ProtoLogImpl_54989576.i(
                                ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                                3361857745281957526L,
                                0,
                                null,
                                String.valueOf(activityRecord2),
                                String.valueOf(this.mTask),
                                String.valueOf(task),
                                String.valueOf(Debug.getCallers(4)));
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_TASKS,
                                3958829063955690349L,
                                0,
                                null,
                                String.valueOf(activityRecord2),
                                String.valueOf(activityRecord));
                    }
                    activityRecord2.reparent(task, i2, "resetTargetTaskIfNeeded");
                }
                if (activityRecord.info.launchMode == 1
                        && (activityBelow = this.mTargetTask.getActivityBelow(activityRecord))
                                != null
                        && activityBelow
                                .intent
                                .getComponent()
                                .equals(activityRecord.intent.getComponent())) {
                    activityBelow.finishIfPossible("replace", false);
                }
            }
        }
        return false;
    }
}
