package com.android.systemui.plugins;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginDependencyProvider_Factory implements Provider {
    private final javax.inject.Provider managerLazyProvider;

    public PluginDependencyProvider_Factory(javax.inject.Provider provider) {
        this.managerLazyProvider = provider;
    }

    public static PluginDependencyProvider_Factory create(javax.inject.Provider provider) {
        return new PluginDependencyProvider_Factory(provider);
    }

    public static PluginDependencyProvider newInstance(Lazy lazy) {
        return new PluginDependencyProvider(lazy);
    }

    @Override // javax.inject.Provider
    public PluginDependencyProvider get() {
        return newInstance(DoubleCheck.lazy(this.managerLazyProvider));
    }
}
