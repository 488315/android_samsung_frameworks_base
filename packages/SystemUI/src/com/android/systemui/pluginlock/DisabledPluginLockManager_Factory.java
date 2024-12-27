package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisabledPluginLockManager_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final DisabledPluginLockManager_Factory INSTANCE = new DisabledPluginLockManager_Factory();

        private InstanceHolder() {
        }
    }

    public static DisabledPluginLockManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisabledPluginLockManager newInstance() {
        return new DisabledPluginLockManager();
    }

    @Override // javax.inject.Provider
    public DisabledPluginLockManager get() {
        return newInstance();
    }
}
