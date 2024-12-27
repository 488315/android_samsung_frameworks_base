package com.android.server.am;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CachedAppOptimizer.FreezeHandler f$0;
    public final /* synthetic */ ProcessRecord f$1;

    public /* synthetic */ CachedAppOptimizer$FreezeHandler$$ExternalSyntheticLambda0(
            CachedAppOptimizer.FreezeHandler freezeHandler, ProcessRecord processRecord, int i) {
        this.$r8$classId = i;
        this.f$0 = freezeHandler;
        this.f$1 = processRecord;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CachedAppOptimizer.FreezeHandler freezeHandler = this.f$0;
                ProcessRecord processRecord = this.f$1;
                ActivityManagerService activityManagerService = CachedAppOptimizer.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        processRecord.killLocked(
                                14,
                                19,
                                "Unable to freeze binder interface",
                                "Unable to freeze binder interface",
                                true,
                                true);
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            case 1:
                CachedAppOptimizer.FreezeHandler freezeHandler2 = this.f$0;
                ProcessRecord processRecord2 = this.f$1;
                ActivityManagerService activityManagerService2 = CachedAppOptimizer.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        processRecord2.killLocked(
                                14,
                                19,
                                "Unable to freeze binder interface",
                                "Unable to freeze binder interface",
                                true,
                                true);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            default:
                CachedAppOptimizer.FreezeHandler freezeHandler3 = this.f$0;
                ProcessRecord processRecord3 = this.f$1;
                ActivityManagerService activityManagerService3 = CachedAppOptimizer.this.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService3) {
                    try {
                        if (processRecord3.mThread == null) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        } else {
                            processRecord3.killLocked(
                                    9,
                                    7,
                                    "excessive binder traffic during cached",
                                    "excessive binder traffic during cached",
                                    true,
                                    true);
                            return;
                        }
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
        }
    }
}
