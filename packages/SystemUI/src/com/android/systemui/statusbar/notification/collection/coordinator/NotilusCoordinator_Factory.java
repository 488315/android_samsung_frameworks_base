package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

public final class NotilusCoordinator_Factory implements Provider {

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
