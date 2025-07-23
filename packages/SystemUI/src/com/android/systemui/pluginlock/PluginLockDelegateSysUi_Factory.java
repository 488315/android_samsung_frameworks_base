package com.android.systemui.pluginlock;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginLockDelegateSysUi_Factory implements Provider {
    private final Provider mediatorProvider;

    public PluginLockDelegateSysUi_Factory(Provider provider) {
        this.mediatorProvider = provider;
    }

    public static PluginLockDelegateSysUi_Factory create(javax.inject.Provider provider) {
        return new PluginLockDelegateSysUi_Factory(Providers.asDaggerProvider(provider));
    }

    public static PluginLockDelegateSysUi newInstance(PluginLockMediator pluginLockMediator) {
        return new PluginLockDelegateSysUi(pluginLockMediator);
    }

    public static PluginLockDelegateSysUi_Factory create(Provider provider) {
        return new PluginLockDelegateSysUi_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PluginLockDelegateSysUi get() {
        return newInstance((PluginLockMediator) this.mediatorProvider.get());
    }
}
