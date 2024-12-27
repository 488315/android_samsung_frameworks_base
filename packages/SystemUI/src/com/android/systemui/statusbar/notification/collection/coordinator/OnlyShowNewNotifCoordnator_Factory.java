package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.shade.NotificationPanelViewController;
import dagger.internal.Provider;

public final class OnlyShowNewNotifCoordnator_Factory implements Provider {
    private final javax.inject.Provider notificationPanelViewControllerProvider;

    public OnlyShowNewNotifCoordnator_Factory(javax.inject.Provider provider) {
        this.notificationPanelViewControllerProvider = provider;
    }

    public static OnlyShowNewNotifCoordnator_Factory create(javax.inject.Provider provider) {
        return new OnlyShowNewNotifCoordnator_Factory(provider);
    }

    public static OnlyShowNewNotifCoordnator newInstance(NotificationPanelViewController notificationPanelViewController) {
        return new OnlyShowNewNotifCoordnator(notificationPanelViewController);
    }

    @Override // javax.inject.Provider
    public OnlyShowNewNotifCoordnator get() {
        return newInstance((NotificationPanelViewController) this.notificationPanelViewControllerProvider.get());
    }
}
