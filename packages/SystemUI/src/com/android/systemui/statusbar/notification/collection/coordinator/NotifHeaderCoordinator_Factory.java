package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class NotifHeaderCoordinator_Factory implements Provider {
    private final Provider lockscreenUserManagerProvider;

    public NotifHeaderCoordinator_Factory(Provider provider) {
        this.lockscreenUserManagerProvider = provider;
    }

    public static NotifHeaderCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotifHeaderCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static NotifHeaderCoordinator newInstance(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        return new NotifHeaderCoordinator(notificationLockscreenUserManager);
    }

    public static NotifHeaderCoordinator_Factory create(Provider provider) {
        return new NotifHeaderCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public NotifHeaderCoordinator get() {
        return newInstance((NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get());
    }
}
