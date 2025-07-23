package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisabledPluginLockMediator_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final DisabledPluginLockMediator_Factory INSTANCE = new DisabledPluginLockMediator_Factory();

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
