package com.samsung.systemui.splugins;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SPluginDependencyProvider_Factory implements Provider {
    private final Provider managerProvider;

    public SPluginDependencyProvider_Factory(Provider provider) {
        this.managerProvider = provider;
    }

    public static SPluginDependencyProvider_Factory create(javax.inject.Provider provider) {
        return new SPluginDependencyProvider_Factory(Providers.asDaggerProvider(provider));
    }

    public static SPluginDependencyProvider newInstance(SPluginManager sPluginManager) {
        return new SPluginDependencyProvider(sPluginManager);
    }

    public static SPluginDependencyProvider_Factory create(Provider provider) {
        return new SPluginDependencyProvider_Factory(provider);
    }

    @Override // javax.inject.Provider
    public SPluginDependencyProvider get() {
        return newInstance((SPluginManager) this.managerProvider.get());
    }
}
