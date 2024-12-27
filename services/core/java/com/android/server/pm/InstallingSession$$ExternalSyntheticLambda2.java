package com.android.server.pm;

import android.os.Trace;

import java.util.Collections;
import java.util.List;

public final /* synthetic */ class InstallingSession$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InstallingSession f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ InstallingSession$$ExternalSyntheticLambda2(
            InstallingSession installingSession, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = installingSession;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                InstallingSession installingSession = this.f$0;
                List list = (List) this.f$1;
                installingSession.getClass();
                try {
                    Trace.traceBegin(262144L, "installApexPackages");
                    installingSession.installApexPackages(list);
                    return;
                } finally {
                    Trace.traceEnd(262144L);
                }
            case 1:
                this.f$0.processApkInstallRequests((List) this.f$1, true);
                return;
            default:
                InstallingSession installingSession2 = this.f$0;
                installingSession2.processInstallRequests(
                        Collections.singletonList((InstallRequest) this.f$1),
                        installingSession2.mRet == 1);
                return;
        }
    }
}
