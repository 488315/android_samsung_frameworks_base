package com.android.server.wm;

import android.os.Trace;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AnrController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AnrController f$0;
    public final /* synthetic */ ActivityRecord f$1;
    public final /* synthetic */ WindowState f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ AnrController$$ExternalSyntheticLambda0(
            AnrController anrController,
            ActivityRecord activityRecord,
            WindowState windowState,
            String str) {
        this.f$0 = anrController;
        this.f$1 = activityRecord;
        this.f$2 = windowState;
        this.f$3 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AnrController anrController = this.f$0;
        ActivityRecord activityRecord = this.f$1;
        WindowState windowState = this.f$2;
        String str = this.f$3;
        anrController.getClass();
        try {
            Trace.traceBegin(64L, "dumpAnrStateLocked()");
            WindowManagerGlobalLock windowManagerGlobalLock = anrController.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    anrController.mService.saveANRStateLocked(activityRecord, windowState, str);
                    anrController.mService.mAtmService.saveANRState(activityRecord, str);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Trace.traceEnd(64L);
        }
    }
}
