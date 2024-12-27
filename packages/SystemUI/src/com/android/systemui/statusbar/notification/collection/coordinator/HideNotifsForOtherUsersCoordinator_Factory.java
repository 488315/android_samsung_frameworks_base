package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Provider;

public final class HideNotifsForOtherUsersCoordinator_Factory implements Provider {
    private final javax.inject.Provider lockscreenUserManagerProvider;

    public HideNotifsForOtherUsersCoordinator_Factory(javax.inject.Provider provider) {
        this.lockscreenUserManagerProvider = provider;
    }

    public static HideNotifsForOtherUsersCoordinator_Factory create(javax.inject.Provider provider) {
        return new HideNotifsForOtherUsersCoordinator_Factory(provider);
    }

    public static HideNotifsForOtherUsersCoordinator newInstance(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        return new HideNotifsForOtherUsersCoordinator(notificationLockscreenUserManager);
    }

    @Override // javax.inject.Provider
    public HideNotifsForOtherUsersCoordinator get() {
        return newInstance((NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get());
    }
}
