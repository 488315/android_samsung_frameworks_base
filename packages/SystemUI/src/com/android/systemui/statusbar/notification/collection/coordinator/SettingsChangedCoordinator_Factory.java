package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SettingsChangedCoordinator_Factory implements Provider {
    private final Provider notifLiveDataStoreImplProvider;

    public SettingsChangedCoordinator_Factory(Provider provider) {
        this.notifLiveDataStoreImplProvider = provider;
    }

    public static SettingsChangedCoordinator_Factory create(javax.inject.Provider provider) {
        return new SettingsChangedCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static SettingsChangedCoordinator newInstance(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        return new SettingsChangedCoordinator(notifLiveDataStoreImpl);
    }

    public static SettingsChangedCoordinator_Factory create(Provider provider) {
        return new SettingsChangedCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public SettingsChangedCoordinator get() {
        return newInstance((NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get());
    }
}
