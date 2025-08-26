package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ReferenceCoordinatorsModule_NotifCoordinatorsFactory implements Provider {
    private final Provider factoryProvider;

    public ReferenceCoordinatorsModule_NotifCoordinatorsFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static ReferenceCoordinatorsModule_NotifCoordinatorsFactory create(javax.inject.Provider provider) {
        return new ReferenceCoordinatorsModule_NotifCoordinatorsFactory(Providers.asDaggerProvider(provider));
    }

    public static NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        NotifCoordinators notifCoordinators = ReferenceCoordinatorsModule.notifCoordinators(factory);
        notifCoordinators.getClass();
        return notifCoordinators;
    }

    public static ReferenceCoordinatorsModule_NotifCoordinatorsFactory create(Provider provider) {
        return new ReferenceCoordinatorsModule_NotifCoordinatorsFactory(provider);
    }

    @Override // javax.inject.Provider
    public NotifCoordinators get() {
        return notifCoordinators((CoordinatorsSubcomponent.Factory) this.factoryProvider.get());
    }
}
