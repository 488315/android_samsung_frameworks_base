package com.android.server.wm;

import android.app.ActivityManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class RunningTasks implements Consumer {
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

  public void getTasks(
      int i,
      List list,
      int i2,
      RecentTasks recentTasks,
      WindowContainer windowContainer,
      int i3,
      ArraySet arraySet) {
    getTasks(i, list, i2, recentTasks, windowContainer, i3, arraySet, false);
  }

  public void getTasks(
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
                  RunningTasks.this.lambda$getTasks$0((DisplayContent) obj);
                }
              });
    } else {
      DisplayContent displayContent = windowContainer.getDisplayContent();
      Task task = null;
      if (displayContent != null && (activityRecord = displayContent.mFocusedApp) != null) {
        task = activityRecord.getTask();
      }
      if (task != null && task.isDescendantOf(windowContainer)) {
        this.mTmpFocusedTasks.add(task);
      }
      processTaskInWindowContainer(windowContainer);
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
        list.add(createRunningTaskInfo(task3, i4 < size ? (min + elapsedRealtime) - i4 : -1L));
      }
      i4++;
    }
    this.mTmpFocusedTasks.clear();
    this.mTmpVisibleTasks.clear();
    this.mTmpInvisibleTasks.clear();
    this.mTmpSortedTasks.clear();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$getTasks$0(DisplayContent displayContent) {
    ActivityRecord activityRecord = displayContent.mFocusedApp;
    Task task = activityRecord != null ? activityRecord.getTask() : null;
    if (task != null) {
      this.mTmpFocusedTasks.add(task);
    }
    processTaskInWindowContainer(displayContent);
  }

  public final void processTaskInWindowContainer(WindowContainer windowContainer) {
    windowContainer.forAllLeafTasks(this, true);
  }

  @Override // java.util.function.Consumer
  public void accept(Task task) {
    int i;
    if (task.getTopNonFinishingActivity() == null) {
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
        if (task.isVisible() || task.isVisibleRequested()) {
          this.mTmpVisibleTasks.add(task);
        } else {
          this.mTmpInvisibleTasks.add(task);
        }
      }
    }
  }

  public final ActivityManager.RunningTaskInfo createRunningTaskInfo(Task task, long j) {
    ActivityManager.RunningTaskInfo runningTaskInfo = new ActivityManager.RunningTaskInfo();
    task.fillTaskInfo(runningTaskInfo, !this.mKeepIntentExtra);
    if (j > 0) {
      runningTaskInfo.lastActiveTime = j;
    }
    runningTaskInfo.id = runningTaskInfo.taskId;
    if (!this.mAllowed) {
      Task.trimIneffectiveInfo(task, runningTaskInfo);
    }
    return runningTaskInfo;
  }
}
