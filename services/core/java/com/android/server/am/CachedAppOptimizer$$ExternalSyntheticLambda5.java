package com.android.server.am;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CachedAppOptimizer$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ CachedAppOptimizer f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int f$3 = 14;
    public final /* synthetic */ int f$4;

    public /* synthetic */ CachedAppOptimizer$$ExternalSyntheticLambda5(
            CachedAppOptimizer cachedAppOptimizer, int i, String str, int i2) {
        this.f$0 = cachedAppOptimizer;
        this.f$1 = i;
        this.f$2 = str;
        this.f$4 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CachedAppOptimizer cachedAppOptimizer = this.f$0;
        int i = this.f$1;
        String str = this.f$2;
        int i2 = this.f$3;
        int i3 = this.f$4;
        ActivityManagerService activityManagerService = cachedAppOptimizer.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerGlobalLock activityManagerGlobalLock = cachedAppOptimizer.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        ProcessRecord processRecord =
                                (ProcessRecord) cachedAppOptimizer.mFrozenProcesses.get(i);
                        if (processRecord != null
                                && processRecord.mThread != null
                                && !processRecord.mKilledByAm) {
                            processRecord.killLocked(i2, i3, str, str, true, true);
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }
}
