package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginLockDelegateSysUi_Factory implements Provider {
    private final javax.inject.Provider mediatorProvider;

    public PluginLockDelegateSysUi_Factory(javax.inject.Provider provider) {
        this.mediatorProvider = provider;
    }

    public static PluginLockDelegateSysUi_Factory create(javax.inject.Provider provider) {
        return new PluginLockDelegateSysUi_Factory(provider);
    }

    public static PluginLockDelegateSysUi newInstance(PluginLockMediator pluginLockMediator) {
        return new PluginLockDelegateSysUi(pluginLockMediator);
    }

    @Override // javax.inject.Provider
    public PluginLockDelegateSysUi get() {
        return newInstance((PluginLockMediator) this.mediatorProvider.get());
    }
}
