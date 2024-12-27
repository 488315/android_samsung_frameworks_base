package com.android.systemui.pluginlock;

import dagger.internal.Provider;

public final class DisabledPluginLockData_Factory implements Provider {

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
