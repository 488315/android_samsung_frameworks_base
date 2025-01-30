package com.android.p038wm.shell.taskview;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskViewTaskController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskViewTaskController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TaskViewTaskController$$ExternalSyntheticLambda1(TaskViewTaskController taskViewTaskController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = taskViewTaskController;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TaskViewTaskController taskViewTaskController = this.f$0;
                taskViewTaskController.mListener.onBackPressedOnTaskRoot(this.f$1);
                break;
            case 1:
                TaskViewTaskController taskViewTaskController2 = this.f$0;
                taskViewTaskController2.mListener.onTaskRemovalStarted(this.f$1);
                break;
            case 2:
                TaskViewTaskController taskViewTaskController3 = this.f$0;
                taskViewTaskController3.mListener.onTaskVisibilityChanged(taskViewTaskController3.mSurfaceCreated);
                break;
            case 3:
                TaskViewTaskController taskViewTaskController4 = this.f$0;
                taskViewTaskController4.mListener.onTaskRemovalStarted(this.f$1);
                break;
            default:
                TaskViewTaskController taskViewTaskController5 = this.f$0;
                taskViewTaskController5.mListener.onTaskRemovalStarted(this.f$1);
                break;
        }
    }
}
