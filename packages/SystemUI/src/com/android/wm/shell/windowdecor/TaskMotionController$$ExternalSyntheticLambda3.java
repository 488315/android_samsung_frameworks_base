package com.android.wm.shell.windowdecor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TaskMotionController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TaskMotionController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((TaskMotionController) obj).mWindowDecoration.closeHandleMenu();
                break;
            case 1:
                ((TaskMotionController) obj).mAllowTouches = true;
                break;
            default:
                ((TaskMotionAnimator) obj).mAnimation.start();
                break;
        }
    }
}
