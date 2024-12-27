package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DisabledPluginLockMediator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
