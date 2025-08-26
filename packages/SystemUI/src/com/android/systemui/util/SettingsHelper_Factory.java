package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SettingsHelper_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;

    public SettingsHelper_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
    }

    public static SettingsHelper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SettingsHelper_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static SettingsHelper newInstance(Context context, DumpManager dumpManager) {
        return new SettingsHelper(context, dumpManager);
    }

    public static SettingsHelper_Factory create(Provider provider, Provider provider2) {
        return new SettingsHelper_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public SettingsHelper get() {
        return newInstance((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
