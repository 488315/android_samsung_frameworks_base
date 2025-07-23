package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HideNotifsForOtherUsersCoordinator_Factory implements Provider {
    private final Provider lockscreenUserManagerProvider;

    public HideNotifsForOtherUsersCoordinator_Factory(Provider provider) {
        this.lockscreenUserManagerProvider = provider;
    }

    public static HideNotifsForOtherUsersCoordinator_Factory create(javax.inject.Provider provider) {
        return new HideNotifsForOtherUsersCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static HideNotifsForOtherUsersCoordinator newInstance(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        return new HideNotifsForOtherUsersCoordinator(notificationLockscreenUserManager);
    }

    public static HideNotifsForOtherUsersCoordinator_Factory create(Provider provider) {
        return new HideNotifsForOtherUsersCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public HideNotifsForOtherUsersCoordinator get() {
        return newInstance((NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get());
    }
}
