package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FavoriteNotifCoordnator_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider favoriteHeaderControllerProvider;
    private final Provider notifLiveDataStoreImplProvider;
    private final Provider timeSortCoordnatorProvider;
    private final Provider visualStabilityCoordinatorProvider;

    public FavoriteNotifCoordnator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.visualStabilityCoordinatorProvider = provider2;
        this.notifLiveDataStoreImplProvider = provider3;
        this.timeSortCoordnatorProvider = provider4;
        this.favoriteHeaderControllerProvider = provider5;
    }

    public static FavoriteNotifCoordnator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new FavoriteNotifCoordnator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static FavoriteNotifCoordnator newInstance(Context context, VisualStabilityCoordinator visualStabilityCoordinator, NotifLiveDataStoreImpl notifLiveDataStoreImpl, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController) {
        return new FavoriteNotifCoordnator(context, visualStabilityCoordinator, notifLiveDataStoreImpl, notifTimeSortCoordnator, nodeController);
    }

    public static FavoriteNotifCoordnator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new FavoriteNotifCoordnator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public FavoriteNotifCoordnator get() {
        return newInstance((Context) this.contextProvider.get(), (VisualStabilityCoordinator) this.visualStabilityCoordinatorProvider.get(), (NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get(), (NotifTimeSortCoordnator) this.timeSortCoordnatorProvider.get(), (NodeController) this.favoriteHeaderControllerProvider.get());
    }
}
