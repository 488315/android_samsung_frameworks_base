package com.android.server.devicepolicy;

import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CertificateMonitor$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ CertificateMonitor f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ CertificateMonitor$$ExternalSyntheticLambda0(
            CertificateMonitor certificateMonitor, int i) {
        this.f$0 = certificateMonitor;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CertificateMonitor certificateMonitor = this.f$0;
        int i = this.f$1;
        certificateMonitor.getClass();
        certificateMonitor.updateInstalledCertificates(UserHandle.of(i));
    }
}
