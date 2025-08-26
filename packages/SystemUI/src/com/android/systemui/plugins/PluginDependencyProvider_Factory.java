package com.android.systemui.plugins;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class PluginDependencyProvider_Factory implements Provider {
    private final Provider managerLazyProvider;

    public PluginDependencyProvider_Factory(Provider provider) {
        this.managerLazyProvider = provider;
    }

    public static PluginDependencyProvider_Factory create(javax.inject.Provider provider) {
        return new PluginDependencyProvider_Factory(Providers.asDaggerProvider(provider));
    }

    public static PluginDependencyProvider newInstance(Lazy lazy) {
        return new PluginDependencyProvider(lazy);
    }

    public static PluginDependencyProvider_Factory create(Provider provider) {
        return new PluginDependencyProvider_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PluginDependencyProvider get() {
        return newInstance(DoubleCheck.lazy(this.managerLazyProvider));
    }
}
