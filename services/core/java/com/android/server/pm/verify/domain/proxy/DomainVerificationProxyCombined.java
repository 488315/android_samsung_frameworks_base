package com.android.server.pm.verify.domain.proxy;

import android.content.ComponentName;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationProxyCombined implements DomainVerificationProxy {
    public final DomainVerificationProxy mProxyV1;
    public final DomainVerificationProxy mProxyV2;

    public DomainVerificationProxyCombined(DomainVerificationProxyV1 domainVerificationProxyV1, DomainVerificationProxyV2 domainVerificationProxyV2) {
        this.mProxyV1 = domainVerificationProxyV1;
        this.mProxyV2 = domainVerificationProxyV2;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final ComponentName getComponentName() {
        return this.mProxyV2.getComponentName();
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final boolean isCallerVerifier(int i) {
        return this.mProxyV2.isCallerVerifier(i) || this.mProxyV1.isCallerVerifier(i);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final boolean runMessage(int i, Object obj) {
        return this.mProxyV2.runMessage(i, obj) || this.mProxyV1.runMessage(i, obj);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final void sendBroadcastForPackages(Set set) {
        this.mProxyV2.sendBroadcastForPackages(set);
        this.mProxyV1.sendBroadcastForPackages(set);
    }
}
