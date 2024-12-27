package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
