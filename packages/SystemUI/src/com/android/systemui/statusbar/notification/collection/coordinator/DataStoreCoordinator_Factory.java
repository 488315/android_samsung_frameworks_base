package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Provider;

public final class DataStoreCoordinator_Factory implements Provider {
    private final javax.inject.Provider notifLiveDataStoreImplProvider;

    public DataStoreCoordinator_Factory(javax.inject.Provider provider) {
        this.notifLiveDataStoreImplProvider = provider;
    }

    public static DataStoreCoordinator_Factory create(javax.inject.Provider provider) {
        return new DataStoreCoordinator_Factory(provider);
    }

    public static DataStoreCoordinator newInstance(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        return new DataStoreCoordinator(notifLiveDataStoreImpl);
    }

    @Override // javax.inject.Provider
    public DataStoreCoordinator get() {
        return newInstance((NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get());
    }
}
