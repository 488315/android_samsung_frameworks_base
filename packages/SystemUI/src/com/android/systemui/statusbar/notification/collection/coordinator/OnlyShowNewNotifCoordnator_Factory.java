package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.shade.NotificationPanelViewController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OnlyShowNewNotifCoordnator_Factory implements Provider {
    private final Provider notificationPanelViewControllerProvider;

    public OnlyShowNewNotifCoordnator_Factory(Provider provider) {
        this.notificationPanelViewControllerProvider = provider;
    }

    public static OnlyShowNewNotifCoordnator_Factory create(javax.inject.Provider provider) {
        return new OnlyShowNewNotifCoordnator_Factory(Providers.asDaggerProvider(provider));
    }

    public static OnlyShowNewNotifCoordnator newInstance(NotificationPanelViewController notificationPanelViewController) {
        return new OnlyShowNewNotifCoordnator(notificationPanelViewController);
    }

    public static OnlyShowNewNotifCoordnator_Factory create(Provider provider) {
        return new OnlyShowNewNotifCoordnator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public OnlyShowNewNotifCoordnator get() {
        return newInstance((NotificationPanelViewController) this.notificationPanelViewControllerProvider.get());
    }
}
