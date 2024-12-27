package com.android.server.pm.verify.domain.proxy;

import android.content.ComponentName;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationProxyUnavailable implements DomainVerificationProxy {
    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final ComponentName getComponentName() {
        return null;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final boolean isCallerVerifier(int i) {
        return false;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final boolean runMessage(int i, Object obj) {
        return false;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final void sendBroadcastForPackages(Set set) {}
}
