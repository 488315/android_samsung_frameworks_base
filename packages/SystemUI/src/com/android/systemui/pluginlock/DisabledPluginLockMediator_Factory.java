package com.android.systemui.pluginlock;

import dagger.internal.Provider;

public final class DisabledPluginLockMediator_Factory implements Provider {

    final class InstanceHolder {
        private static final DisabledPluginLockMediator_Factory INSTANCE = new DisabledPluginLockMediator_Factory();

        private InstanceHolder() {
        }
    }

    public static DisabledPluginLockMediator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisabledPluginLockMediator newInstance() {
        return new DisabledPluginLockMediator();
    }

    @Override // javax.inject.Provider
    public DisabledPluginLockMediator get() {
        return newInstance();
    }
}
