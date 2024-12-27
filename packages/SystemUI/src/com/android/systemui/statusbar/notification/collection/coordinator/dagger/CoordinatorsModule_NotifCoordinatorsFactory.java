package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
