package com.android.server.job.controllers;

public final /* synthetic */ class QuotaController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QuotaController f$0;

    public /* synthetic */ QuotaController$$ExternalSyntheticLambda2(
            QuotaController quotaController, int i) {
        this.$r8$classId = i;
        this.f$0 = quotaController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        QuotaController quotaController = this.f$0;
        switch (i) {
            case 0:
                synchronized (quotaController.mLock) {
                    quotaController.maybeUpdateAllConstraintsLocked();
                }
                return;
            default:
                synchronized (quotaController.mLock) {
                    quotaController.invalidateAllExecutionStatsLocked();
                    quotaController.maybeUpdateAllConstraintsLocked();
                }
                return;
        }
    }
}
