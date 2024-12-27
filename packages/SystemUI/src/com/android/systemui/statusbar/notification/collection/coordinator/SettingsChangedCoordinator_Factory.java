package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Provider;

public final class SettingsChangedCoordinator_Factory implements Provider {
    private final javax.inject.Provider notifLiveDataStoreImplProvider;

    public SettingsChangedCoordinator_Factory(javax.inject.Provider provider) {
        this.notifLiveDataStoreImplProvider = provider;
    }

    public static SettingsChangedCoordinator_Factory create(javax.inject.Provider provider) {
        return new SettingsChangedCoordinator_Factory(provider);
    }

    public static SettingsChangedCoordinator newInstance(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        return new SettingsChangedCoordinator(notifLiveDataStoreImpl);
    }

    @Override // javax.inject.Provider
    public SettingsChangedCoordinator get() {
        return newInstance((NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get());
    }
}
