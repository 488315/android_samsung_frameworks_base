package com.android.systemui.dagger;

import com.android.systemui.BinderProxyDumpHelper;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideBinderProxyDumpHelperFactory implements Provider {
    public final Provider dumpManagerProvider;

    public SamsungServicesModule_ProvideBinderProxyDumpHelperFactory(Provider provider) {
        this.dumpManagerProvider = provider;
    }

    public static BinderProxyDumpHelper provideBinderProxyDumpHelper(DumpManager dumpManager) {
        return new BinderProxyDumpHelper(dumpManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BinderProxyDumpHelper((DumpManager) this.dumpManagerProvider.get());
    }
}
