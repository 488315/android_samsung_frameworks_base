package com.android.systemui.pluginlock.utils;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
