package com.android.wm.shell.windowdecor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HandleHideAnimator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HandleHideAnimator f$0;

    public /* synthetic */ HandleHideAnimator$$ExternalSyntheticLambda0(HandleHideAnimator handleHideAnimator, int i) {
        this.$r8$classId = i;
        this.f$0 = handleHideAnimator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HandleHideAnimator handleHideAnimator = this.f$0;
        switch (i) {
            case 0:
                handleHideAnimator.hide(false);
                break;
            default:
                handleHideAnimator.delayedHide();
                break;
        }
    }
}
