package com.android.p038wm.shell.common.split;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DividerResizeController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DividerResizeController f$0;

    public /* synthetic */ DividerResizeController$$ExternalSyntheticLambda0(DividerResizeController dividerResizeController, int i) {
        this.$r8$classId = i;
        this.f$0 = dividerResizeController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.stopWaitingForSyncAppsCallback("timeout");
                break;
            default:
                this.f$0.clear();
                break;
        }
    }
}
