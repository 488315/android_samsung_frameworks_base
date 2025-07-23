package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.view.WindowInsets;
import android.window.DesktopModeFlags;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopTaskChangeListener;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformTaskListener$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FreeformTaskListener$$ExternalSyntheticLambda2(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = runningTaskInfo;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj2;
                DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                desktopTasksController.getClass();
                if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
                    boolean isTaskInFullImmersiveState = desktopTasksController.taskRepository.isTaskInFullImmersiveState(runningTaskInfo.taskId);
                    boolean z = (((TaskInfo) runningTaskInfo).requestedVisibleTypes & WindowInsets.Type.statusBars()) == 0;
                    if (isTaskInFullImmersiveState && !z && desktopTasksController.recentsTransitionState < 2) {
                        desktopTasksController.desktopImmersiveController.moveTaskToNonImmersive(runningTaskInfo, DesktopImmersiveController.ExitReason.APP_NOT_IMMERSIVE);
                        break;
                    }
                }
                break;
            case 1:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj2;
                ((DesktopTaskChangeListener) ((TaskChangeListener) obj)).getClass();
                DesktopTaskChangeListener.logD("onNonTransitionTaskChanging for taskId=%d, displayId=%d", Integer.valueOf(runningTaskInfo2.taskId), Integer.valueOf(runningTaskInfo2.displayId));
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) obj2;
                ((DesktopUserRepositories) obj).getProfile(runningTaskInfo3.userId).addTask(runningTaskInfo3.displayId, runningTaskInfo3.taskId, runningTaskInfo3.isVisible);
                break;
            default:
                ((WindowContainerTransaction) obj2).setExcludeImeInsets(((ActivityManager.RunningTaskInfo) obj).token, false);
                break;
        }
    }

    public /* synthetic */ FreeformTaskListener$$ExternalSyntheticLambda2(WindowContainerTransaction windowContainerTransaction) {
        this.$r8$classId = 3;
        this.f$0 = windowContainerTransaction;
    }
}
