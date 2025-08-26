package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class DataStoreCoordinator_Factory implements Provider {
    private final Provider notifLiveDataStoreImplProvider;

    public DataStoreCoordinator_Factory(Provider provider) {
        this.notifLiveDataStoreImplProvider = provider;
    }

    public static DataStoreCoordinator_Factory create(javax.inject.Provider provider) {
        return new DataStoreCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static DataStoreCoordinator newInstance(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        return new DataStoreCoordinator(notifLiveDataStoreImpl);
    }

    public static DataStoreCoordinator_Factory create(Provider provider) {
        return new DataStoreCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public DataStoreCoordinator get() {
        return newInstance((NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get());
    }
}
