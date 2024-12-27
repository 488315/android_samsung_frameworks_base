package com.android.server.pm;

import android.os.Trace;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class InstallingSession$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InstallingSession f$0;

    public /* synthetic */ InstallingSession$$ExternalSyntheticLambda0(
            InstallingSession installingSession, int i) {
        this.$r8$classId = i;
        this.f$0 = installingSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        InstallingSession installingSession = this.f$0;
        installingSession.getClass();
        switch (i) {
            case 0:
                Trace.asyncTraceEnd(
                        262144L, "queueInstall", System.identityHashCode(installingSession));
                Trace.traceBegin(262144L, "startInstall");
                InstallRequest installRequest = new InstallRequest(installingSession);
                installingSession.handleStartCopy(installRequest);
                installingSession.handleReturnCode(installRequest);
                Trace.traceEnd(262144L);
                break;
            default:
                new InstallingSession$$ExternalSyntheticLambda0(installingSession, 0).run();
                break;
        }
    }
}
