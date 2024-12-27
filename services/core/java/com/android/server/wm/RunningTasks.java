package com.android.server.wm;

import android.app.ActivityManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RunningTasks implements Consumer {
    public boolean mAllowed;
    public int mCallingUid;
    public boolean mCrossUser;
    public boolean mFilterOnlyVisibleRecents;
    public boolean mKeepIntentExtra;
    public ArraySet mProfileIds;
    public RecentTasks mRecentTasks;
    public int mUserId;
    public final ArrayList mTmpSortedTasks = new ArrayList();
    public final ArrayList mTmpVisibleTasks = new ArrayList();
    public final ArrayList mTmpInvisibleTasks = new ArrayList();
    public final ArrayList mTmpFocusedTasks = new ArrayList();

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i;
        Task task = (Task) obj;
        if (task.getTopNonFinishingActivity(true, true) == null) {
            return;
        }
        if (task.effectiveUid == this.mCallingUid
                || (((i = task.mUserId) == this.mUserId
                                || this.mCrossUser
                                || this.mProfileIds.contains(Integer.valueOf(i)))
                        && this.mAllowed)) {
            if (!this.mFilterOnlyVisibleRecents
                    || task.getActivityType() == 2
                    || task.getActivityType() == 3
                    || this.mRecentTasks.isVisibleRecentTask(task)) {
                if (task.isVisibleRequested()) {
                    this.mTmpVisibleTasks.add(task);
                } else {
                    this.mTmpInvisibleTasks.add(task);
                }
            }
        }
    }

    public final void getTasks(
            int i,
            List list,
            int i2,
            RecentTasks recentTasks,
            WindowContainer windowContainer,
            int i3,
            ArraySet arraySet,
            boolean z) {
        ActivityRecord activityRecord;
        if (i <= 0) {
            return;
        }
        this.mCallingUid = i3;
        this.mUserId = UserHandle.getUserId(i3);
        int i4 = 0;
        this.mCrossUser = (i2 & 4) == 4;
        this.mProfileIds = arraySet;
        this.mAllowed = (i2 & 2) == 2;
        this.mFilterOnlyVisibleRecents = (i2 & 1) == 1;
        this.mRecentTasks = recentTasks;
        this.mKeepIntentExtra = (i2 & 8) == 8;
        if (windowContainer instanceof RootWindowContainer) {
            ((RootWindowContainer) windowContainer)
                    .forAllDisplays(
                            new Consumer() { // from class:
                                             // com.android.server.wm.RunningTasks$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    RunningTasks runningTasks = RunningTasks.this;
                                    DisplayContent displayContent = (DisplayContent) obj;
                                    runningTasks.getClass();
                                    ActivityRecord activityRecord2 = displayContent.mFocusedApp;
                                    Task task =
                                            activityRecord2 != null ? activityRecord2.task : null;
                                    if (task != null) {
                                        runningTasks.mTmpFocusedTasks.add(task);
                                    }
                                    displayContent.forAllLeafTasks(runningTasks, true);
                                }
                            });
        } else {
            DisplayContent displayContent = windowContainer.getDisplayContent();
            Task task = null;
            if (displayContent != null && (activityRecord = displayContent.mFocusedApp) != null) {
                task = activityRecord.task;
            }
            if (task != null && task.isDescendantOf(windowContainer)) {
                this.mTmpFocusedTasks.add(task);
            }
            windowContainer.forAllLeafTasks(this, true);
        }
        int size = this.mTmpVisibleTasks.size();
        for (int i5 = 0; i5 < this.mTmpFocusedTasks.size(); i5++) {
            Task task2 = (Task) this.mTmpFocusedTasks.get(i5);
            if (this.mTmpVisibleTasks.remove(task2)) {
                this.mTmpSortedTasks.add(task2);
            }
        }
        if (!this.mTmpVisibleTasks.isEmpty()) {
            this.mTmpSortedTasks.addAll(this.mTmpVisibleTasks);
        }
        if (!this.mTmpInvisibleTasks.isEmpty()) {
            this.mTmpSortedTasks.addAll(this.mTmpInvisibleTasks);
        }
        int min = Math.min(i, this.mTmpSortedTasks.size());
        long elapsedRealtime = SystemClock.elapsedRealtime();
        while (i4 < min) {
            Task task3 = (Task) this.mTmpSortedTasks.get(i4);
            if (!z || task3.isVisible()) {
                long j = i4 < size ? (min + elapsedRealtime) - i4 : -1L;
                ActivityManager.RunningTaskInfo runningTaskInfo =
                        new ActivityManager.RunningTaskInfo();
                task3.fillTaskInfo(runningTaskInfo, !this.mKeepIntentExtra);
                if (j > 0) {
                    runningTaskInfo.lastActiveTime = j;
                }
                runningTaskInfo.id = runningTaskInfo.taskId;
                if (!this.mAllowed) {
                    Task.trimIneffectiveInfo(task3, runningTaskInfo);
                }
                list.add(runningTaskInfo);
            }
            i4++;
        }
        this.mTmpFocusedTasks.clear();
        this.mTmpVisibleTasks.clear();
        this.mTmpInvisibleTasks.clear();
        this.mTmpSortedTasks.clear();
    }
}
