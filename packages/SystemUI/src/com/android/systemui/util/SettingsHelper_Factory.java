package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;

public final class SettingsHelper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider dumpManagerProvider;

    public SettingsHelper_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
    }

    public static SettingsHelper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SettingsHelper_Factory(provider, provider2);
    }

    public static SettingsHelper newInstance(Context context, DumpManager dumpManager) {
        return new SettingsHelper(context, dumpManager);
    }

    @Override // javax.inject.Provider
    public SettingsHelper get() {
        return newInstance((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
