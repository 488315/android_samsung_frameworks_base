package com.android.systemui.dagger;

import com.android.systemui.BinderProxyDumpHelper;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;

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
