package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class CoordinatorsModule_NotifCoordinatorsFactory implements Provider {
    private final javax.inject.Provider factoryProvider;

    public CoordinatorsModule_NotifCoordinatorsFactory(javax.inject.Provider provider) {
        this.factoryProvider = provider;
    }

    public static CoordinatorsModule_NotifCoordinatorsFactory create(javax.inject.Provider provider) {
        return new CoordinatorsModule_NotifCoordinatorsFactory(provider);
    }

    public static NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        NotifCoordinators notifCoordinators = CoordinatorsModule.notifCoordinators(factory);
        Preconditions.checkNotNullFromProvides(notifCoordinators);
        return notifCoordinators;
    }

    @Override // javax.inject.Provider
    public NotifCoordinators get() {
        return notifCoordinators((CoordinatorsSubcomponent.Factory) this.factoryProvider.get());
    }
}
