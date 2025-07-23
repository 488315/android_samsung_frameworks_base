package com.android.wm.shell.common.split;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DividerResizeController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DividerResizeController f$0;

    public /* synthetic */ DividerResizeController$$ExternalSyntheticLambda0(DividerResizeController dividerResizeController, int i) {
        this.$r8$classId = i;
        this.f$0 = dividerResizeController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DividerResizeController dividerResizeController = this.f$0;
        switch (i) {
            case 0:
                dividerResizeController.stopWaitingForSyncAppsCallback("timeout");
                break;
            default:
                dividerResizeController.clear();
                break;
        }
    }
}
