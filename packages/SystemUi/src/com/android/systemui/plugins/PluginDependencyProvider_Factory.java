package com.android.systemui.plugins;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginDependencyProvider_Factory implements Provider {
    private final Provider managerLazyProvider;

    public PluginDependencyProvider_Factory(Provider provider) {
        this.managerLazyProvider = provider;
    }

    public static PluginDependencyProvider_Factory create(Provider provider) {
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
