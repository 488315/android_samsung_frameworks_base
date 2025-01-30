package com.android.wm.shell.windowdecor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskMotionController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskMotionController f$0;

    public /* synthetic */ TaskMotionController$$ExternalSyntheticLambda3(TaskMotionController taskMotionController, int i) {
        this.$r8$classId = i;
        this.f$0 = taskMotionController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mWindowDecoration.closeHandleMenu(true);
                break;
            case 1:
                this.f$0.mAllowTouches = true;
                break;
            default:
                this.f$0.mAllowTouches = true;
                break;
        }
    }
}
