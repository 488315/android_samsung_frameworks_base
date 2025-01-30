package com.samsung.systemui.splugins;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SPluginDependencyProvider_Factory implements Provider {
    private final Provider managerProvider;

    public SPluginDependencyProvider_Factory(Provider provider) {
        this.managerProvider = provider;
    }

    public static SPluginDependencyProvider_Factory create(Provider provider) {
        return new SPluginDependencyProvider_Factory(provider);
    }

    public static SPluginDependencyProvider newInstance(SPluginManager sPluginManager) {
        return new SPluginDependencyProvider(sPluginManager);
    }

    @Override // javax.inject.Provider
    public SPluginDependencyProvider get() {
        return newInstance((SPluginManager) this.managerProvider.get());
    }
}
