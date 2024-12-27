package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifHeaderCoordinator_Factory implements Provider {
    private final javax.inject.Provider lockscreenUserManagerProvider;

    public NotifHeaderCoordinator_Factory(javax.inject.Provider provider) {
        this.lockscreenUserManagerProvider = provider;
    }

    public static NotifHeaderCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotifHeaderCoordinator_Factory(provider);
    }

    public static NotifHeaderCoordinator newInstance(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        return new NotifHeaderCoordinator(notificationLockscreenUserManager);
    }

    @Override // javax.inject.Provider
    public NotifHeaderCoordinator get() {
        return newInstance((NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get());
    }
}
