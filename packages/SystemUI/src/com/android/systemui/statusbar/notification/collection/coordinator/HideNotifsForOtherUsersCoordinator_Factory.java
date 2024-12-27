package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
