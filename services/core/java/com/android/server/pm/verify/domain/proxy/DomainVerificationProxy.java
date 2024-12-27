package com.android.server.pm.verify.domain.proxy;

import android.content.ComponentName;
import android.content.Context;

import com.android.server.pm.DomainVerificationConnection;
import com.android.server.pm.verify.domain.DomainVerificationCollector;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;

import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface DomainVerificationProxy {
    static DomainVerificationProxy makeProxy(
            ComponentName componentName,
            ComponentName componentName2,
            Context context,
            DomainVerificationManagerInternal domainVerificationManagerInternal,
            DomainVerificationCollector domainVerificationCollector,
            DomainVerificationConnection domainVerificationConnection) {
        ComponentName componentName3 =
                (componentName2 == null
                                || componentName == null
                                || Objects.equals(
                                        componentName2.getPackageName(),
                                        componentName.getPackageName()))
                        ? componentName
                        : null;
        DomainVerificationProxyV1 domainVerificationProxyV1 =
                componentName3 != null
                        ? new DomainVerificationProxyV1(
                                context,
                                domainVerificationManagerInternal,
                                domainVerificationCollector,
                                domainVerificationConnection,
                                componentName3)
                        : null;
        DomainVerificationProxyV2 domainVerificationProxyV2 =
                componentName2 != null
                        ? new DomainVerificationProxyV2(
                                context, domainVerificationConnection, componentName2)
                        : null;
        return (domainVerificationProxyV1 == null || domainVerificationProxyV2 == null)
                ? domainVerificationProxyV1 != null
                        ? domainVerificationProxyV1
                        : domainVerificationProxyV2 != null
                                ? domainVerificationProxyV2
                                : new DomainVerificationProxyUnavailable()
                : new DomainVerificationProxyCombined(
                        domainVerificationProxyV1, domainVerificationProxyV2);
    }

    ComponentName getComponentName();

    boolean isCallerVerifier(int i);

    boolean runMessage(int i, Object obj);

    void sendBroadcastForPackages(Set set);
}
