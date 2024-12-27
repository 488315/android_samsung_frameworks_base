package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

public final class RowAlertTimeCoordinator_Factory implements Provider {

    final class InstanceHolder {
        private static final RowAlertTimeCoordinator_Factory INSTANCE = new RowAlertTimeCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static RowAlertTimeCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RowAlertTimeCoordinator newInstance() {
        return new RowAlertTimeCoordinator();
    }

    @Override // javax.inject.Provider
    public RowAlertTimeCoordinator get() {
        return newInstance();
    }
}
