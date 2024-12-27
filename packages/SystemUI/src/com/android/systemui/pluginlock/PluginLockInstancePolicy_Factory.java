package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginLockInstancePolicy_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final PluginLockInstancePolicy_Factory INSTANCE = new PluginLockInstancePolicy_Factory();

        private InstanceHolder() {
        }
    }

    public static PluginLockInstancePolicy_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PluginLockInstancePolicy newInstance() {
        return new PluginLockInstancePolicy();
    }

    @Override // javax.inject.Provider
    public PluginLockInstancePolicy get() {
        return newInstance();
    }
}
