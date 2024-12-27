package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RowAlertTimeCoordinator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
