package com.android.server.pm.verify.domain.proxy;

import android.content.ComponentName;

import java.util.Set;

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
