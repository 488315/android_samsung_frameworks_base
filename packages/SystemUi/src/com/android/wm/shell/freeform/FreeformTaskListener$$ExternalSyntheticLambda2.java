package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository$displayData$1;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.util.KtProtoLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformTaskListener$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$0;

    public /* synthetic */ FreeformTaskListener$$ExternalSyntheticLambda2(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = runningTaskInfo;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityManager.RunningTaskInfo runningTaskInfo = this.f$0;
                DesktopModeTaskRepository desktopModeTaskRepository = (DesktopModeTaskRepository) obj;
                int i = runningTaskInfo.taskId;
                desktopModeTaskRepository.getClass();
                KtProtoLog.Companion companion = KtProtoLog.Companion;
                ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                Object[] objArr = {Integer.valueOf(i)};
                companion.getClass();
                KtProtoLog.Companion.m234d(shellProtoLogGroup, "DesktopTaskRepo: remove freeform task from ordered list taskId=%d", objArr);
                ((ArrayList) desktopModeTaskRepository.freeformTasksInZOrder).remove(Integer.valueOf(i));
                int i2 = runningTaskInfo.taskId;
                DesktopModeTaskRepository$displayData$1 desktopModeTaskRepository$displayData$1 = desktopModeTaskRepository.displayData;
                int size = desktopModeTaskRepository$displayData$1.size();
                boolean z = false;
                for (int i3 = 0; i3 < size; i3++) {
                    desktopModeTaskRepository$displayData$1.keyAt(i3);
                    if (((DesktopModeTaskRepository.DisplayData) desktopModeTaskRepository$displayData$1.valueAt(i3)).activeTasks.remove(Integer.valueOf(i2))) {
                        Iterator it = desktopModeTaskRepository.activeTasksListeners.iterator();
                        while (it.hasNext()) {
                            ((RecentTasksController) ((DesktopModeTaskRepository.ActiveTasksListener) it.next())).notifyRecentTasksChanged();
                        }
                        z = true;
                    }
                }
                if (z) {
                    KtProtoLog.Companion companion2 = KtProtoLog.Companion;
                    ShellProtoLogGroup shellProtoLogGroup2 = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                    Object[] objArr2 = {Integer.valueOf(i2)};
                    companion2.getClass();
                    KtProtoLog.Companion.m234d(shellProtoLogGroup2, "DesktopTaskRepo: remove active task=%d", objArr2);
                }
                if (z && ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, 2026968528, 1, null, Long.valueOf(runningTaskInfo.taskId));
                }
                desktopModeTaskRepository.updateVisibleFreeformTasks(runningTaskInfo.displayId, runningTaskInfo.taskId, false);
                break;
            case 1:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = this.f$0;
                DesktopModeTaskRepository desktopModeTaskRepository2 = (DesktopModeTaskRepository) obj;
                if (runningTaskInfo2.isVisible && desktopModeTaskRepository2.addActiveTask(runningTaskInfo2.displayId, runningTaskInfo2.taskId) && ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1610406480, 1, null, Long.valueOf(runningTaskInfo2.taskId));
                }
                desktopModeTaskRepository2.updateVisibleFreeformTasks(runningTaskInfo2.displayId, runningTaskInfo2.taskId, runningTaskInfo2.isVisible);
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo3 = this.f$0;
                DesktopModeTaskRepository desktopModeTaskRepository3 = (DesktopModeTaskRepository) obj;
                desktopModeTaskRepository3.addOrMoveFreeformTaskToTop(runningTaskInfo3.taskId);
                if (runningTaskInfo3.isVisible) {
                    if (desktopModeTaskRepository3.addActiveTask(runningTaskInfo3.displayId, runningTaskInfo3.taskId) && ShellProtoLogCache.WM_SHELL_DESKTOP_MODE_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -1610406480, 1, null, Long.valueOf(runningTaskInfo3.taskId));
                    }
                    desktopModeTaskRepository3.updateVisibleFreeformTasks(runningTaskInfo3.displayId, runningTaskInfo3.taskId, true);
                    break;
                }
                break;
            default:
                ((DesktopModeTaskRepository) obj).addOrMoveFreeformTaskToTop(this.f$0.taskId);
                break;
        }
    }
}
