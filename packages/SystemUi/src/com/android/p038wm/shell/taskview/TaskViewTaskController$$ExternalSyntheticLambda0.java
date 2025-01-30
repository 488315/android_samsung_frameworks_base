package com.android.p038wm.shell.taskview;

import android.util.Log;
import android.window.TaskAppearedInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.taskview.TaskViewTransitions;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskViewTaskController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskViewTaskController f$0;

    public /* synthetic */ TaskViewTaskController$$ExternalSyntheticLambda0(TaskViewTaskController taskViewTaskController, int i) {
        this.$r8$classId = i;
        this.f$0 = taskViewTaskController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                TaskViewTaskController taskViewTaskController = this.f$0;
                taskViewTaskController.finishRecreateSurface("timeout");
                taskViewTaskController.updateTaskVisibility();
                return;
            case 1:
                TaskViewTaskController taskViewTaskController2 = this.f$0;
                TaskViewTransitions taskViewTransitions = taskViewTaskController2.mTaskViewTransitions;
                if (taskViewTransitions != null) {
                    synchronized (taskViewTransitions.mRegistered) {
                        boolean[] zArr = taskViewTransitions.mRegistered;
                        if (!zArr[0]) {
                            zArr[0] = true;
                            taskViewTransitions.mTransitions.addHandler(taskViewTransitions);
                        }
                    }
                    taskViewTransitions.mTaskViews.put(taskViewTaskController2, new TaskViewTransitions.TaskViewRequestedState(i));
                    return;
                }
                return;
            case 2:
                TaskViewTaskController taskViewTaskController3 = this.f$0;
                TaskViewTransitions taskViewTransitions2 = taskViewTaskController3.mTaskViewTransitions;
                if (taskViewTransitions2 != null) {
                    taskViewTransitions2.mTaskViews.remove(taskViewTaskController3);
                }
                ShellTaskOrganizer shellTaskOrganizer = taskViewTaskController3.mTaskOrganizer;
                synchronized (shellTaskOrganizer.mLock) {
                    try {
                        if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, -1340279385, 0, null, String.valueOf(taskViewTaskController3));
                        }
                        if (shellTaskOrganizer.mTaskListeners.indexOfValue(taskViewTaskController3) == -1) {
                            Log.w("ShellTaskOrganizer", "No registered listener found");
                        } else {
                            ArrayList arrayList = new ArrayList();
                            for (int size = shellTaskOrganizer.mTasks.size() - 1; size >= 0; size--) {
                                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.valueAt(size);
                                if (shellTaskOrganizer.getTaskListener(taskAppearedInfo.getTaskInfo(), false) == taskViewTaskController3) {
                                    arrayList.add(taskAppearedInfo);
                                }
                            }
                            for (int size2 = shellTaskOrganizer.mTaskListeners.size() - 1; size2 >= 0; size2--) {
                                if (shellTaskOrganizer.mTaskListeners.valueAt(size2) == taskViewTaskController3) {
                                    shellTaskOrganizer.mTaskListeners.removeAt(size2);
                                }
                            }
                            for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                                TaskAppearedInfo taskAppearedInfo2 = (TaskAppearedInfo) arrayList.get(size3);
                                shellTaskOrganizer.updateTaskListenerIfNeeded(taskAppearedInfo2.getTaskInfo(), taskAppearedInfo2.getLeash(), null, shellTaskOrganizer.getTaskListener(taskAppearedInfo2.getTaskInfo(), false));
                            }
                        }
                    } finally {
                    }
                }
                taskViewTaskController3.resetTaskInfo();
                return;
            case 3:
                TaskViewTaskController taskViewTaskController4 = this.f$0;
                if (taskViewTaskController4.mTaskToken == null) {
                    return;
                }
                if (taskViewTaskController4.isUsingShellTransitions()) {
                    taskViewTaskController4.mTaskViewTransitions.setTaskViewVisible(taskViewTaskController4, false);
                    return;
                } else {
                    taskViewTaskController4.mTransaction.reparent(taskViewTaskController4.mTaskLeash, null).apply();
                    taskViewTaskController4.updateTaskVisibility();
                    return;
                }
            case 4:
                TaskViewTaskController taskViewTaskController5 = this.f$0;
                if (taskViewTaskController5.mTaskToken == null) {
                    return;
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.removeTask(taskViewTaskController5.mTaskToken);
                TaskViewTransitions taskViewTransitions3 = taskViewTaskController5.mTaskViewTransitions;
                taskViewTransitions3.updateVisibilityState(taskViewTaskController5, false);
                taskViewTransitions3.mPending.add(new TaskViewTransitions.PendingTransition(2, windowContainerTransaction, taskViewTaskController5, null));
                taskViewTransitions3.startNextTransition();
                return;
            case 5:
                TaskViewTaskController taskViewTaskController6 = this.f$0;
                if (taskViewTaskController6.mTaskToken == null) {
                    return;
                }
                if (taskViewTaskController6.isUsingShellTransitions()) {
                    taskViewTaskController6.mTaskViewTransitions.setTaskViewVisible(taskViewTaskController6, true);
                    return;
                } else {
                    taskViewTaskController6.mTransaction.reparent(taskViewTaskController6.mTaskLeash, taskViewTaskController6.mSurfaceControl).show(taskViewTaskController6.mTaskLeash).apply();
                    taskViewTaskController6.updateTaskVisibility();
                    return;
                }
            case 6:
                this.f$0.mListener.onInitialized();
                return;
            default:
                this.f$0.mListener.onReleased();
                return;
        }
    }
}
