package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

public final class HideLocallyDismissedNotifsCoordinator_Factory implements Provider {

    final class InstanceHolder {
        private static final HideLocallyDismissedNotifsCoordinator_Factory INSTANCE = new HideLocallyDismissedNotifsCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static HideLocallyDismissedNotifsCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static HideLocallyDismissedNotifsCoordinator newInstance() {
        return new HideLocallyDismissedNotifsCoordinator();
    }

    @Override // javax.inject.Provider
    public HideLocallyDismissedNotifsCoordinator get() {
        return newInstance();
    }
}
