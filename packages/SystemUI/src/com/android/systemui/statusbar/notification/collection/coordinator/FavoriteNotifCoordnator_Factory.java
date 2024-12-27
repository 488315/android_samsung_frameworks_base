package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Provider;

public final class FavoriteNotifCoordnator_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider favoriteHeaderControllerProvider;
    private final javax.inject.Provider notifLiveDataStoreImplProvider;
    private final javax.inject.Provider timeSortCoordnatorProvider;
    private final javax.inject.Provider visualStabilityCoordinatorProvider;

    public FavoriteNotifCoordnator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.contextProvider = provider;
        this.visualStabilityCoordinatorProvider = provider2;
        this.notifLiveDataStoreImplProvider = provider3;
        this.timeSortCoordnatorProvider = provider4;
        this.favoriteHeaderControllerProvider = provider5;
    }

    public static FavoriteNotifCoordnator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new FavoriteNotifCoordnator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static FavoriteNotifCoordnator newInstance(Context context, VisualStabilityCoordinator visualStabilityCoordinator, NotifLiveDataStoreImpl notifLiveDataStoreImpl, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController) {
        return new FavoriteNotifCoordnator(context, visualStabilityCoordinator, notifLiveDataStoreImpl, notifTimeSortCoordnator, nodeController);
    }

    @Override // javax.inject.Provider
    public FavoriteNotifCoordnator get() {
        return newInstance((Context) this.contextProvider.get(), (VisualStabilityCoordinator) this.visualStabilityCoordinatorProvider.get(), (NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get(), (NotifTimeSortCoordnator) this.timeSortCoordnatorProvider.get(), (NodeController) this.favoriteHeaderControllerProvider.get());
    }
}
