package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginLockInstancePolicy_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
