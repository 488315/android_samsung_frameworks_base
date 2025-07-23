package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisabledPluginLockManager_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final DisabledPluginLockManager_Factory INSTANCE = new DisabledPluginLockManager_Factory();

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
