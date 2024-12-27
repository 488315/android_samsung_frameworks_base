package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.bixby2.controller.NotificationController;
import dagger.internal.Provider;

public final class NotificationControlActionCoordinator_Factory implements Provider {
    private final javax.inject.Provider notificationControllerProvider;

    public NotificationControlActionCoordinator_Factory(javax.inject.Provider provider) {
        this.notificationControllerProvider = provider;
    }

    public static NotificationControlActionCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotificationControlActionCoordinator_Factory(provider);
    }

    public static NotificationControlActionCoordinator newInstance(NotificationController notificationController) {
        return new NotificationControlActionCoordinator(notificationController);
    }

    @Override // javax.inject.Provider
    public NotificationControlActionCoordinator get() {
        return newInstance((NotificationController) this.notificationControllerProvider.get());
    }
}
