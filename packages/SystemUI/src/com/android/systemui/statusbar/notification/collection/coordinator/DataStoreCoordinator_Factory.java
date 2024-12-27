package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
