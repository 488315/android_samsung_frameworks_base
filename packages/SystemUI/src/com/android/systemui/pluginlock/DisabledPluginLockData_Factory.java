package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisabledPluginLockData_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final DisabledPluginLockData_Factory INSTANCE = new DisabledPluginLockData_Factory();

        private InstanceHolder() {
        }
    }

    public static DisabledPluginLockData_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisabledPluginLockData newInstance() {
        return new DisabledPluginLockData();
    }

    @Override // javax.inject.Provider
    public DisabledPluginLockData get() {
        return newInstance();
    }
}
