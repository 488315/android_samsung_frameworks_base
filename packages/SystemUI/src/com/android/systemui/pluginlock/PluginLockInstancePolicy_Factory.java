package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class PluginLockInstancePolicy_Factory implements Provider {

    final class InstanceHolder {
        static final PluginLockInstancePolicy_Factory INSTANCE = new PluginLockInstancePolicy_Factory();

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
