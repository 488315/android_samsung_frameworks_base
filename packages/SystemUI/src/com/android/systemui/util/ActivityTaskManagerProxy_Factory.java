package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityTaskManagerProxy_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final ActivityTaskManagerProxy_Factory INSTANCE = new ActivityTaskManagerProxy_Factory();

        private InstanceHolder() {
        }
    }

    public static ActivityTaskManagerProxy_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ActivityTaskManagerProxy newInstance() {
        return new ActivityTaskManagerProxy();
    }

    @Override // javax.inject.Provider
    public ActivityTaskManagerProxy get() {
        return newInstance();
    }
}
