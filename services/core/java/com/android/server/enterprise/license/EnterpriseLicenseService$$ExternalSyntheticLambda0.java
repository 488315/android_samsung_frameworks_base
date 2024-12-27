package com.android.server.enterprise.license;

import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class EnterpriseLicenseService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Bundle f$1;

    public /* synthetic */ EnterpriseLicenseService$$ExternalSyntheticLambda0(
            Object obj, Bundle bundle, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((EnterpriseLicenseService) this.f$0)
                        .callLicenseAgent("KLMDeactivationInternal", null, this.f$1);
                break;
            case 1:
                ((EnterpriseLicenseService) this.f$0)
                        .callLicenseAgent("ELMRegistrationInternal", null, this.f$1);
                break;
            case 2:
                ((EnterpriseLicenseService) this.f$0)
                        .callLicenseAgent("KLMRegistrationInternal", null, this.f$1);
                break;
            case 3:
                ((EnterpriseLicenseService) this.f$0)
                        .callLicenseAgent("licenseValidationInternal", null, this.f$1);
                break;
            default:
                EnterpriseLicenseService.AnonymousClass1 anonymousClass1 =
                        (EnterpriseLicenseService.AnonymousClass1) this.f$0;
                anonymousClass1.this$0.callLicenseAgent("packageRemovedInternal", null, this.f$1);
                break;
        }
    }
}
