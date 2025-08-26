package com.android.wm.shell;

import android.app.ActivityManager;
import android.os.RemoteException;
import android.util.Slog;
import com.android.wm.shell.recents.GroupedRecentTaskSaveController;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.HashMap;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShellTaskOrganizer$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$0;

    public /* synthetic */ ShellTaskOrganizer$$ExternalSyntheticLambda1(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = runningTaskInfo;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$0;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        switch (i) {
            case 0:
                int i2 = ShellTaskOrganizer.$r8$clinit;
                if (-1 != recentTasksController.mSplitTasks.get(runningTaskInfo.taskId, -1)) {
                    recentTasksController.removeSplitPair(runningTaskInfo.taskId);
                    return;
                }
                return;
            case 1:
                int i3 = ShellTaskOrganizer.$r8$clinit;
                recentTasksController.notifyRecentTasksChanged();
                if (recentTasksController.mListener == null || !recentTasksController.shouldEnableRunningTasksForDesktopMode() || runningTaskInfo.realActivity == null || RecentTasksController.excludeTaskFromGeneratedList(runningTaskInfo)) {
                    return;
                }
                try {
                    recentTasksController.mListener.onRunningTaskChanged(runningTaskInfo);
                    return;
                } catch (RemoteException e) {
                    Slog.w("RecentTasksController", "Failed call onRunningTaskChanged", e);
                    return;
                }
            default:
                int i4 = ShellTaskOrganizer.$r8$clinit;
                recentTasksController.getClass();
                recentTasksController.removeSplitPair(runningTaskInfo.taskId);
                if (recentTasksController.mListener != null && recentTasksController.shouldEnableRunningTasksForDesktopMode() && runningTaskInfo.realActivity != null && !RecentTasksController.excludeTaskFromGeneratedList(runningTaskInfo)) {
                    try {
                        recentTasksController.mListener.onRunningTaskVanished(runningTaskInfo);
                    } catch (RemoteException e2) {
                        Slog.w("RecentTasksController", "Failed call onRunningTaskVanished", e2);
                    }
                }
                recentTasksController.notifyRecentTasksChanged();
                if (recentTasksController.mSplitTasks.size() == 0) {
                    GroupedRecentTaskSaveController groupedRecentTaskSaveController = recentTasksController.mSaveController;
                    synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                        ((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).clear();
                    }
                    return;
                }
                return;
        }
    }
}
