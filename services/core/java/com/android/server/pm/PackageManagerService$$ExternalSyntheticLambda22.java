package com.android.server.pm;

import android.os.Handler;

import com.android.server.pm.verify.domain.DomainVerificationService;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda22
        implements PackageManagerServiceInjector.Producer, ApkChecksums.Injector.Producer {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda22(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.server.pm.ApkChecksums.Injector.Producer
    public Object produce() {
        return (Handler) this.f$0;
    }

    @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
    public Object produce(
            PackageManagerService packageManagerService,
            PackageManagerServiceInjector packageManagerServiceInjector) {
        return (DomainVerificationService) this.f$0;
    }
}
