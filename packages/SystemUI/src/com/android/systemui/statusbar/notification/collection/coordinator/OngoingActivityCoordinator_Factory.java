package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingActivityCoordinator_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider notificationIconAreaControllerProvider;
    private final Provider ongoingActivityControllerProvider;
    private final Provider ongoingActivityHeaderControllerProvider;
    private final Provider settingsHelperProvider;
    private final Provider timeSortCoordnatorProvider;

    public OngoingActivityCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.timeSortCoordnatorProvider = provider2;
        this.ongoingActivityHeaderControllerProvider = provider3;
        this.ongoingActivityControllerProvider = provider4;
        this.notificationIconAreaControllerProvider = provider5;
        this.settingsHelperProvider = provider6;
    }

    public static OngoingActivityCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new OngoingActivityCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6));
    }

    public static OngoingActivityCoordinator newInstance(Context context, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController, OngoingActivityController ongoingActivityController, NotificationIconAreaController notificationIconAreaController, SettingsHelper settingsHelper) {
        return new OngoingActivityCoordinator(context, notifTimeSortCoordnator, nodeController, ongoingActivityController, notificationIconAreaController, settingsHelper);
    }

    public static OngoingActivityCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new OngoingActivityCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // javax.inject.Provider
    public OngoingActivityCoordinator get() {
        return newInstance((Context) this.contextProvider.get(), (NotifTimeSortCoordnator) this.timeSortCoordnatorProvider.get(), (NodeController) this.ongoingActivityHeaderControllerProvider.get(), (OngoingActivityController) this.ongoingActivityControllerProvider.get(), (NotificationIconAreaController) this.notificationIconAreaControllerProvider.get(), (SettingsHelper) this.settingsHelperProvider.get());
    }
}
