package com.android.systemui.dagger;

import com.android.systemui.BinderProxyDumpHelper;
import com.android.systemui.dump.DumpManager;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
