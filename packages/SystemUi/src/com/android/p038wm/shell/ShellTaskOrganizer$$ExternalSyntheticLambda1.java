package com.android.p038wm.shell;

import android.app.ActivityManager;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Slog;
import android.window.TaskAppearedInfo;
import com.android.p038wm.shell.recents.GroupedRecentTaskSaveController;
import com.android.p038wm.shell.recents.IRecentTasksListener;
import com.android.p038wm.shell.recents.RecentTasksController;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShellTaskOrganizer$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Parcelable f$0;

    public /* synthetic */ ShellTaskOrganizer$$ExternalSyntheticLambda1(Parcelable parcelable, int i) {
        this.$r8$classId = i;
        this.f$0 = parcelable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.f$0;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                int i = ShellTaskOrganizer.$r8$clinit;
                recentTasksController.getClass();
                recentTasksController.removeSplitPair(runningTaskInfo.taskId);
                recentTasksController.notifyRecentTasksChanged();
                IRecentTasksListener iRecentTasksListener = recentTasksController.mListener;
                if (iRecentTasksListener != null && recentTasksController.mIsDesktopMode && runningTaskInfo.realActivity != null) {
                    try {
                        iRecentTasksListener.onRunningTaskVanished(runningTaskInfo);
                    } catch (RemoteException e) {
                        Slog.w("RecentTasksController", "Failed call onRunningTaskVanished", e);
                    }
                }
                if (recentTasksController.mSplitTasks.size() == 0) {
                    GroupedRecentTaskSaveController groupedRecentTaskSaveController = recentTasksController.mSaveController;
                    synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                        ((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).clear();
                    }
                    return;
                }
                return;
            case 1:
                int i2 = ShellTaskOrganizer.$r8$clinit;
                ((RecentTasksController) obj).notifyRecentTasksChanged();
                return;
            default:
                TaskAppearedInfo taskAppearedInfo = this.f$0;
                RecentTasksController recentTasksController2 = (RecentTasksController) obj;
                int i3 = ShellTaskOrganizer.$r8$clinit;
                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                IRecentTasksListener iRecentTasksListener2 = recentTasksController2.mListener;
                if (iRecentTasksListener2 == null || !recentTasksController2.mIsDesktopMode || taskInfo.realActivity == null) {
                    return;
                }
                try {
                    iRecentTasksListener2.onRunningTaskAppeared(taskInfo);
                    return;
                } catch (RemoteException e2) {
                    Slog.w("RecentTasksController", "Failed call onRunningTaskAppeared", e2);
                    return;
                }
        }
    }
}
