package com.android.wm.shell.taskview;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TaskViewTaskController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskViewTaskController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TaskViewTaskController$$ExternalSyntheticLambda4(TaskViewTaskController taskViewTaskController, int i, int i2) {
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
            default:
                TaskViewTaskController taskViewTaskController3 = this.f$0;
                taskViewTaskController3.mListener.onTaskVisibilityChanged(this.f$1, taskViewTaskController3.mSurfaceCreated);
                break;
        }
    }
}
