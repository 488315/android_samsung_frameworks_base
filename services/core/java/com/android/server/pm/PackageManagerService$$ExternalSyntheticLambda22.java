package com.android.server.pm;

import android.os.Handler;

import com.android.server.pm.verify.domain.DomainVerificationService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
