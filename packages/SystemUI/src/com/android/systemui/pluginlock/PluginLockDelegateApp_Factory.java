package com.android.systemui.pluginlock;

import dagger.internal.Provider;

public final class PluginLockDelegateApp_Factory implements Provider {
    private final javax.inject.Provider utilsProvider;

    public PluginLockDelegateApp_Factory(javax.inject.Provider provider) {
        this.utilsProvider = provider;
    }

    public static PluginLockDelegateApp_Factory create(javax.inject.Provider provider) {
        return new PluginLockDelegateApp_Factory(provider);
    }

    public static PluginLockDelegateApp newInstance(PluginLockUtils pluginLockUtils) {
        return new PluginLockDelegateApp(pluginLockUtils);
    }

    @Override // javax.inject.Provider
    public PluginLockDelegateApp get() {
        return newInstance((PluginLockUtils) this.utilsProvider.get());
    }
}
