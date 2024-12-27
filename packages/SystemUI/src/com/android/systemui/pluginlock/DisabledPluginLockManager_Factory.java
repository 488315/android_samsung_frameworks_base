package com.android.systemui.pluginlock;

import dagger.internal.Provider;

public final class DisabledPluginLockManager_Factory implements Provider {

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
