package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

public final class SemPriorityCoordinator_Factory implements Provider {

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
