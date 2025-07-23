package com.android.wm.shell.taskview;

import android.app.ActivityManager;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TaskAppearedInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TaskViewTaskController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskViewTaskController f$0;

    public /* synthetic */ TaskViewTaskController$$ExternalSyntheticLambda0(TaskViewTaskController taskViewTaskController, int i) {
        this.$r8$classId = i;
        this.f$0 = taskViewTaskController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        TaskViewTaskController taskViewTaskController = this.f$0;
        switch (i) {
            case 0:
                TaskViewController taskViewController = taskViewTaskController.mTaskViewController;
                if (taskViewController != null) {
                    taskViewController.registerTaskView(taskViewTaskController);
                    return;
                }
                return;
            case 1:
                taskViewTaskController.mListener.onInitialized();
                return;
            case 2:
                if (taskViewTaskController.mTaskToken == null) {
                    return;
                }
                TaskViewController taskViewController2 = taskViewTaskController.mTaskViewController;
                if (taskViewController2.isUsingShellTransitions()) {
                    taskViewController2.setTaskViewVisible(taskViewTaskController, true);
                    return;
                } else {
                    taskViewTaskController.mTransaction.reparent(taskViewTaskController.mTaskLeash, taskViewTaskController.mSurfaceControl).show(taskViewTaskController.mTaskLeash).apply();
                    taskViewTaskController.updateTaskVisibility();
                    return;
                }
            case 3:
                if (taskViewTaskController.mTaskToken == null) {
                    return;
                }
                TaskViewController taskViewController3 = taskViewTaskController.mTaskViewController;
                if (taskViewController3.isUsingShellTransitions()) {
                    taskViewController3.setTaskViewVisible(taskViewTaskController, false);
                    return;
                } else {
                    taskViewTaskController.mTransaction.reparent(taskViewTaskController.mTaskLeash, null).apply();
                    taskViewTaskController.updateTaskVisibility();
                    return;
                }
            case 4:
                TaskViewController taskViewController4 = taskViewTaskController.mTaskViewController;
                if (taskViewController4 != null) {
                    taskViewController4.unregisterTaskView(taskViewTaskController);
                }
                ShellTaskOrganizer shellTaskOrganizer = taskViewTaskController.mTaskOrganizer;
                synchronized (shellTaskOrganizer.mLock) {
                    try {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -2293990469534138854L, 0, String.valueOf(taskViewTaskController));
                        }
                        if (shellTaskOrganizer.mTaskListeners.indexOfValue(taskViewTaskController) == -1) {
                            Log.w("ShellTaskOrganizer", "No registered listener found");
                        } else {
                            ArrayList arrayList = new ArrayList();
                            for (int size = shellTaskOrganizer.mTasks.size() - 1; size >= 0; size--) {
                                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.valueAt(size);
                                if (shellTaskOrganizer.getTaskListener(taskAppearedInfo.getTaskInfo(), false) == taskViewTaskController) {
                                    arrayList.add(taskAppearedInfo);
                                }
                            }
                            for (int size2 = shellTaskOrganizer.mTaskListeners.size() - 1; size2 >= 0; size2--) {
                                if (shellTaskOrganizer.mTaskListeners.valueAt(size2) == taskViewTaskController) {
                                    shellTaskOrganizer.mTaskListeners.removeAt(size2);
                                }
                            }
                            for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                                TaskAppearedInfo taskAppearedInfo2 = (TaskAppearedInfo) arrayList.get(size3);
                                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo2.getTaskInfo();
                                SurfaceControl leash = taskAppearedInfo2.getLeash();
                                ShellTaskOrganizer.TaskListener taskListener = shellTaskOrganizer.getTaskListener(taskAppearedInfo2.getTaskInfo(), false);
                                if (taskListener != null) {
                                    if (taskListener != null) {
                                        taskListener.onTaskAppeared(taskInfo, leash);
                                    }
                                    if (taskListener == null || !taskListener.isMultiWindow()) {
                                        shellTaskOrganizer.clearForcedResizablePackagesIfNeeded();
                                    }
                                }
                            }
                        }
                    } finally {
                    }
                }
                taskViewTaskController.resetTaskInfo();
                return;
            default:
                taskViewTaskController.mListener.onReleased();
                return;
        }
    }
}
