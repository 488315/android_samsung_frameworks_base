package com.android.server.am;

import android.os.SystemClock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ProcessStatsService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ProcessStatsService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2 = false;

    public /* synthetic */ ProcessStatsService$$ExternalSyntheticLambda0(ProcessStatsService processStatsService) {
        this.f$0 = processStatsService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ProcessStatsService processStatsService = this.f$0;
        boolean z = this.f$2;
        ActivityManagerProcLock activityManagerProcLock = processStatsService.mAm.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerProcLock) {
            try {
                processStatsService.mAm.mAppProfiler.requestPssAllProcsLPr(SystemClock.uptimeMillis(), z);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
    }
}
