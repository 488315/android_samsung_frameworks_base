package com.android.systemui.pluginlock.utils;

import android.content.Context;
import dagger.internal.Provider;

public final class DumpUtils_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public DumpUtils_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static DumpUtils_Factory create(javax.inject.Provider provider) {
        return new DumpUtils_Factory(provider);
    }

    public static DumpUtils newInstance(Context context) {
        return new DumpUtils(context);
    }

    @Override // javax.inject.Provider
    public DumpUtils get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
