package com.android.server.stats.pull;

import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda6
        implements IBinder.DeathRecipient {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatsPullAtomService f$0;

    public /* synthetic */ StatsPullAtomService$$ExternalSyntheticLambda6(
            StatsPullAtomService statsPullAtomService, int i) {
        this.$r8$classId = i;
        this.f$0 = statsPullAtomService;
    }

    private final void
            binderDied$com$android$server$stats$pull$StatsPullAtomService$$ExternalSyntheticLambda7() {
        StatsPullAtomService statsPullAtomService = this.f$0;
        synchronized (statsPullAtomService.mThermalLock) {
            statsPullAtomService.mThermalService = null;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        switch (this.$r8$classId) {
            case 0:
                StatsPullAtomService statsPullAtomService = this.f$0;
                synchronized (statsPullAtomService.mKeystoreLock) {
                    statsPullAtomService.mIKeystoreMetrics = null;
                }
                return;
            case 1:
                StatsPullAtomService statsPullAtomService2 = this.f$0;
                synchronized (statsPullAtomService2.mNotificationStatsLock) {
                    statsPullAtomService2.mNotificationManagerService = null;
                }
                return;
            case 2:
                StatsPullAtomService statsPullAtomService3 = this.f$0;
                synchronized (statsPullAtomService3.mStoragedLock) {
                    statsPullAtomService3.mStorageService = null;
                }
                return;
            case 3:
                binderDied$com$android$server$stats$pull$StatsPullAtomService$$ExternalSyntheticLambda7();
                return;
            default:
                StatsPullAtomService statsPullAtomService4 = this.f$0;
                synchronized (statsPullAtomService4.mProcStatsLock) {
                    statsPullAtomService4.mProcessStatsService = null;
                }
                return;
        }
    }
}
