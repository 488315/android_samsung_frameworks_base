package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GroupCountCoordinator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final GroupCountCoordinator_Factory INSTANCE = new GroupCountCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static GroupCountCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static GroupCountCoordinator newInstance() {
        return new GroupCountCoordinator();
    }

    @Override // javax.inject.Provider
    public GroupCountCoordinator get() {
        return newInstance();
    }
}
