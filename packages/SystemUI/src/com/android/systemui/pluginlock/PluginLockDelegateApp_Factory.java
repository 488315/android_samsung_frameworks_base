package com.android.systemui.pluginlock;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class PluginLockDelegateApp_Factory implements Provider {
    private final Provider utilsProvider;

    public PluginLockDelegateApp_Factory(Provider provider) {
        this.utilsProvider = provider;
    }

    public static PluginLockDelegateApp_Factory create(javax.inject.Provider provider) {
        return new PluginLockDelegateApp_Factory(Providers.asDaggerProvider(provider));
    }

    public static PluginLockDelegateApp newInstance(PluginLockUtils pluginLockUtils) {
        return new PluginLockDelegateApp(pluginLockUtils);
    }

    public static PluginLockDelegateApp_Factory create(Provider provider) {
        return new PluginLockDelegateApp_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PluginLockDelegateApp get() {
        return newInstance((PluginLockUtils) this.utilsProvider.get());
    }
}
