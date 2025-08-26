package com.android.wm.shell.taskview;

import android.graphics.Rect;
import android.os.Binder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTransitions;
import java.util.Objects;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public final /* synthetic */ class TaskViewTransitions$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskViewTransitions f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ TaskViewTaskController f$2;

    public /* synthetic */ TaskViewTransitions$$ExternalSyntheticLambda1(TaskViewTransitions taskViewTransitions, TaskViewTaskController taskViewTaskController, Rect rect) {
        this.$r8$classId = 1;
        this.f$0 = taskViewTransitions;
        this.f$2 = taskViewTaskController;
        this.f$1 = rect;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TaskViewTransitions taskViewTransitions = this.f$0;
                taskViewTransitions.mPending.add(new TaskViewTransitions.PendingTransition(2, (WindowContainerTransaction) this.f$1, this.f$2, null));
                taskViewTransitions.startNextTransition();
                break;
            case 1:
                TaskViewTransitions taskViewTransitions2 = this.f$0;
                TaskViewTaskController taskViewTaskController = this.f$2;
                Rect rect = (Rect) this.f$1;
                TaskViewRepository.TaskViewState taskViewState = (TaskViewRepository.TaskViewState) ((WeakHashMap) taskViewTransitions2.mTaskViews).get(taskViewTaskController);
                if (taskViewState != null && !Objects.equals(rect, taskViewState.mBounds)) {
                    taskViewState.mBounds.set(rect);
                    if (taskViewState.mVisible) {
                        Slog.d("TaskViewTransitions", "setTaskBounds: boundsOnScreen=" + rect + ", " + taskViewTaskController);
                        Rect rect2 = new Rect(0, 0, rect.width(), rect.height());
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setBounds(taskViewTaskController.mTaskInfo.token, rect);
                        taskViewTaskController.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                        SurfaceControl.Transaction transaction = taskViewTransitions2.mFinishTransaction;
                        SurfaceControl surfaceControl = taskViewTaskController.mTaskLeash;
                        if (surfaceControl != null && surfaceControl.isValid()) {
                            taskViewTaskController.mTransaction.setCrop(taskViewTaskController.mTaskLeash, rect2).apply();
                            if (transaction != null) {
                                transaction.setCrop(taskViewTaskController.mTaskLeash, rect2);
                                Slog.d("TaskViewTaskController", "updateTaskLeashCropRect: " + transaction + ", cropRect=" + rect2 + ", for " + taskViewTaskController.mTaskLeash);
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                this.f$0.mTaskOrganizer.setPendingLaunchCookieListener((Binder) this.f$1, this.f$2);
                break;
        }
    }

    public /* synthetic */ TaskViewTransitions$$ExternalSyntheticLambda1(TaskViewTransitions taskViewTransitions, Object obj, TaskViewTaskController taskViewTaskController, int i) {
        this.$r8$classId = i;
        this.f$0 = taskViewTransitions;
        this.f$1 = obj;
        this.f$2 = taskViewTaskController;
    }
}
