package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.os.IBinder;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda7 implements Function1 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda7(IBinder iBinder, ActivityManager.RunningTaskInfo runningTaskInfo, int i, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason) {
        this.f$0 = iBinder;
        this.f$1 = runningTaskInfo;
        this.f$2 = i;
        this.f$3 = minimizeReason;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Object obj2 = this.f$0;
        Object obj3 = this.f$3;
        switch (this.$r8$classId) {
            case 0:
                IBinder iBinder = (IBinder) obj2;
                ActivityManager.RunningTaskInfo runningTaskInfo = this.f$1;
                DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) obj;
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                int i = runningTaskInfo != null ? runningTaskInfo.displayId : 0;
                desktopTasksLimiter.getClass();
                desktopTasksLimiter.minimizeTransitionObserver.pendingTransitionTokensAndTasks.put(iBinder, new DesktopTasksLimiter.TaskDetails(i, this.f$2, null, null, (DesktopModeEventLogger.Companion.MinimizeReason) obj3, null, 36, null));
                break;
            default:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = this.f$1;
                IBinder iBinder2 = (IBinder) obj;
                DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                DesktopTasksController desktopTasksController = (DesktopTasksController) obj2;
                Integer addAndGetMinimizeChanges = desktopTasksController.addAndGetMinimizeChanges(this.f$2, (WindowContainerTransaction) obj3, Integer.valueOf(runningTaskInfo2.taskId), false);
                if (addAndGetMinimizeChanges != null) {
                    desktopTasksController.addPendingMinimizeTransition(iBinder2, addAndGetMinimizeChanges.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                }
                desktopTasksController.addPendingAppLaunchTransition(iBinder2, runningTaskInfo2.taskId, addAndGetMinimizeChanges);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda7(DesktopTasksController desktopTasksController, int i, WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.f$0 = desktopTasksController;
        this.f$2 = i;
        this.f$3 = windowContainerTransaction;
        this.f$1 = runningTaskInfo;
    }
}
