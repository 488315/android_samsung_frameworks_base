package com.android.server.pm;

import android.os.Trace;
import com.android.server.pm.InstallingSession;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class InstallingSession$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InstallingSession.MultiPackageInstallingSession f$0;

    public /* synthetic */ InstallingSession$$ExternalSyntheticLambda4(InstallingSession.MultiPackageInstallingSession multiPackageInstallingSession, int i) {
        this.$r8$classId = i;
        this.f$0 = multiPackageInstallingSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        InstallingSession.MultiPackageInstallingSession multiPackageInstallingSession = this.f$0;
        switch (i) {
            case 0:
                Objects.requireNonNull(multiPackageInstallingSession);
                new InstallingSession$$ExternalSyntheticLambda4(multiPackageInstallingSession, 1).run();
                break;
            default:
                multiPackageInstallingSession.getClass();
                Trace.asyncTraceEnd(262144L, "queueInstall", System.identityHashCode(multiPackageInstallingSession));
                Trace.traceBegin(262144L, "start");
                int size = multiPackageInstallingSession.mChildInstallingSessions.size();
                ArrayList arrayList = new ArrayList(size);
                for (int i2 = 0; i2 < size; i2++) {
                    InstallingSession installingSession = (InstallingSession) multiPackageInstallingSession.mChildInstallingSessions.get(i2);
                    InstallRequest installRequest = new InstallRequest(installingSession);
                    arrayList.add(installRequest);
                    installingSession.handleStartCopy(installRequest);
                }
                for (int i3 = 0; i3 < size; i3++) {
                    ((InstallingSession) multiPackageInstallingSession.mChildInstallingSessions.get(i3)).handleReturnCode((InstallRequest) arrayList.get(i3));
                }
                Trace.traceEnd(262144L);
                break;
        }
    }
}
