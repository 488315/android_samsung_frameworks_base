package com.android.systemui.pluginlock;

import android.content.Context;
import dagger.internal.Provider;

public final class PluginLockDataImpl_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider mediatorProvider;

    public PluginLockDataImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.mediatorProvider = provider2;
    }

    public static PluginLockDataImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginLockDataImpl_Factory(provider, provider2);
    }

    public static PluginLockDataImpl newInstance(Context context, PluginLockMediator pluginLockMediator) {
        return new PluginLockDataImpl(context, pluginLockMediator);
    }

    @Override // javax.inject.Provider
    public PluginLockDataImpl get() {
        return newInstance((Context) this.contextProvider.get(), (PluginLockMediator) this.mediatorProvider.get());
    }
}
