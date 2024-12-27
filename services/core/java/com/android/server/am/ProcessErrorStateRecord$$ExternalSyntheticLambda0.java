package com.android.server.am;

import com.android.internal.os.anr.AnrLatencyTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ProcessErrorStateRecord$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ProcessErrorStateRecord f$0;
    public final /* synthetic */ AnrLatencyTracker f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ ProcessErrorStateRecord$$ExternalSyntheticLambda0(ProcessErrorStateRecord processErrorStateRecord, AnrLatencyTracker anrLatencyTracker, String str) {
        this.f$0 = processErrorStateRecord;
        this.f$1 = anrLatencyTracker;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ProcessErrorStateRecord processErrorStateRecord = this.f$0;
        AnrLatencyTracker anrLatencyTracker = this.f$1;
        String str = this.f$2;
        processErrorStateRecord.getClass();
        anrLatencyTracker.waitingOnAMSLockStarted();
        ActivityManagerService activityManagerService = processErrorStateRecord.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                anrLatencyTracker.waitingOnAMSLockEnded();
                processErrorStateRecord.mAnrAnnotation = str;
                processErrorStateRecord.mApp.killLocked(6, "anr");
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }
}
