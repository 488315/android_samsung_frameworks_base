package com.android.wm.shell;

import android.app.ActivityManager;
import android.os.RemoteException;
import android.util.Slog;
import android.window.TaskAppearedInfo;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShellTaskOrganizer$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShellTaskOrganizer$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                int i2 = ShellTaskOrganizer.$r8$clinit;
                ActivityManager.RunningTaskInfo taskInfo = ((TaskAppearedInfo) obj2).getTaskInfo();
                if (recentTasksController.mListener != null && recentTasksController.shouldEnableRunningTasksForDesktopMode() && taskInfo.realActivity != null && !RecentTasksController.excludeTaskFromGeneratedList(taskInfo)) {
                    try {
                        recentTasksController.mListener.onRunningTaskAppeared(taskInfo);
                    } catch (RemoteException e) {
                        Slog.w("RecentTasksController", "Failed call onRunningTaskAppeared", e);
                    }
                }
                recentTasksController.notifyRecentTasksChanged();
                break;
            default:
                ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) obj2;
                CompatUIEvents compatUIEvents = (CompatUIEvents) obj;
                int i3 = ShellTaskOrganizer.$r8$clinit;
                shellTaskOrganizer.getClass();
                int i4 = compatUIEvents.eventId;
                if (i4 == 0) {
                    shellTaskOrganizer.onSizeCompatRestartButtonAppeared((CompatUIEvents.SizeCompatRestartButtonAppeared) compatUIEvents);
                    break;
                } else if (i4 == 1) {
                    shellTaskOrganizer.onSizeCompatRestartButtonClicked((CompatUIEvents.SizeCompatRestartButtonClicked) compatUIEvents);
                    break;
                }
                break;
        }
    }
}
