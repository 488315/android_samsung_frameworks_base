package com.android.wm.shell.taskview;

/* loaded from: classes3.dex */
public final /* synthetic */ class TaskView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskView f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TaskView$$ExternalSyntheticLambda1(TaskView taskView, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = taskView;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TaskView taskView = this.f$0;
                int i = this.f$1;
                int i2 = TaskView.$r8$clinit;
                taskView.setResizeBackgroundColor(i);
                break;
            case 1:
                TaskView taskView2 = this.f$0;
                int i3 = this.f$1;
                int i4 = TaskView.$r8$clinit;
                taskView2.setResizeBackgroundColor(i3);
                break;
            default:
                TaskView taskView3 = this.f$0;
                int i5 = this.f$1;
                int i6 = TaskView.$r8$clinit;
                taskView3.setResizeBackgroundColor(i5);
                break;
        }
    }
}
