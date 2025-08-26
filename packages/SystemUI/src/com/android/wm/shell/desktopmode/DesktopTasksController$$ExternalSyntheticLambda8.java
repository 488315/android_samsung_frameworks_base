package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.os.IBinder;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopTasksController$$ExternalSyntheticLambda8 implements Function1 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda8(IBinder iBinder, int i, List list, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason) {
        this.f$0 = iBinder;
        this.f$2 = i;
        this.f$1 = list;
        this.f$3 = minimizeReason;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = this.f$2;
        Object obj2 = this.f$0;
        Object obj3 = this.f$1;
        Object obj4 = this.f$3;
        switch (this.$r8$classId) {
            case 0:
                IBinder iBinder = (IBinder) obj2;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj3;
                DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) obj;
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                int i2 = runningTaskInfo != null ? runningTaskInfo.displayId : 0;
                desktopTasksLimiter.getClass();
                desktopTasksLimiter.minimizeTransitionObserver.pendingTransitionTokensAndTasks.put(iBinder, new DesktopTasksLimiter.TaskDetails(i2, this.f$2, null, null, (DesktopModeEventLogger.Companion.MinimizeReason) obj4, null, 36, null));
                break;
            case 1:
                IBinder iBinder2 = (IBinder) obj2;
                DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                iBinder2.getClass();
                ((DesktopTasksLimiter) obj).addPendingMinimizeChanges(iBinder2, i, (List) obj3, (DesktopModeEventLogger.Companion.MinimizeReason) obj4);
                break;
            default:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj3;
                IBinder iBinder3 = (IBinder) obj;
                DesktopTasksController.Companion companion3 = DesktopTasksController.Companion;
                DesktopTasksController desktopTasksController = (DesktopTasksController) obj2;
                Integer numAddAndGetMinimizeChanges = desktopTasksController.addAndGetMinimizeChanges(i, (WindowContainerTransaction) obj4, Integer.valueOf(runningTaskInfo2.taskId), false);
                if (numAddAndGetMinimizeChanges != null) {
                    desktopTasksController.addPendingMinimizeTransition(iBinder3, numAddAndGetMinimizeChanges.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                }
                desktopTasksController.addPendingAppLaunchTransition(iBinder3, runningTaskInfo2.taskId, numAddAndGetMinimizeChanges);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda8(IBinder iBinder, ActivityManager.RunningTaskInfo runningTaskInfo, int i, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason) {
        this.f$0 = iBinder;
        this.f$1 = runningTaskInfo;
        this.f$2 = i;
        this.f$3 = minimizeReason;
    }

    public /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda8(DesktopTasksController desktopTasksController, int i, WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.f$0 = desktopTasksController;
        this.f$2 = i;
        this.f$3 = windowContainerTransaction;
        this.f$1 = runningTaskInfo;
    }
}
