package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.bixby2.controller.NotificationController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationControlActionCoordinator_Factory implements Provider {
    private final Provider notificationControllerProvider;

    public NotificationControlActionCoordinator_Factory(Provider provider) {
        this.notificationControllerProvider = provider;
    }

    public static NotificationControlActionCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotificationControlActionCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static NotificationControlActionCoordinator newInstance(NotificationController notificationController) {
        return new NotificationControlActionCoordinator(notificationController);
    }

    public static NotificationControlActionCoordinator_Factory create(Provider provider) {
        return new NotificationControlActionCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public NotificationControlActionCoordinator get() {
        return newInstance((NotificationController) this.notificationControllerProvider.get());
    }
}
