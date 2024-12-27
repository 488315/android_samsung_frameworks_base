package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotilusCoordinator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final NotilusCoordinator_Factory INSTANCE = new NotilusCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static NotilusCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotilusCoordinator newInstance() {
        return new NotilusCoordinator();
    }

    @Override // javax.inject.Provider
    public NotilusCoordinator get() {
        return newInstance();
    }
}
