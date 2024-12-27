package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SemPriorityCoordinator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final SemPriorityCoordinator_Factory INSTANCE = new SemPriorityCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static SemPriorityCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SemPriorityCoordinator newInstance() {
        return new SemPriorityCoordinator();
    }

    @Override // javax.inject.Provider
    public SemPriorityCoordinator get() {
        return newInstance();
    }
}
