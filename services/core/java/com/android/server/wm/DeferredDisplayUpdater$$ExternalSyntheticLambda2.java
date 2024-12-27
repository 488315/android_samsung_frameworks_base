package com.android.server.wm;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeferredDisplayUpdater$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeferredDisplayUpdater f$0;

    public /* synthetic */ DeferredDisplayUpdater$$ExternalSyntheticLambda2(DeferredDisplayUpdater deferredDisplayUpdater, int i) {
        this.$r8$classId = i;
        this.f$0 = deferredDisplayUpdater;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DeferredDisplayUpdater deferredDisplayUpdater = this.f$0;
        switch (i) {
            case 0:
                deferredDisplayUpdater.getClass();
                Slog.e("DeferredDisplayUpdater", "Timeout waiting for the display switch transition to start");
                deferredDisplayUpdater.continueScreenUnblocking();
                break;
            default:
                deferredDisplayUpdater.continueScreenUnblocking();
                break;
        }
    }
}
