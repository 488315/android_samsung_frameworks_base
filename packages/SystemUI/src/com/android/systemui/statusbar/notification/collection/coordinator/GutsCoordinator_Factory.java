package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class GutsCoordinator_Factory implements Provider {
    private final Provider dumpManagerProvider;
    private final Provider loggerProvider;
    private final Provider notifGutsViewManagerProvider;

    public GutsCoordinator_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.notifGutsViewManagerProvider = provider;
        this.loggerProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public static GutsCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new GutsCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static GutsCoordinator newInstance(NotifGutsViewManager notifGutsViewManager, GutsCoordinatorLogger gutsCoordinatorLogger, DumpManager dumpManager) {
        return new GutsCoordinator(notifGutsViewManager, gutsCoordinatorLogger, dumpManager);
    }

    public static GutsCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new GutsCoordinator_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public GutsCoordinator get() {
        return newInstance((NotifGutsViewManager) this.notifGutsViewManagerProvider.get(), (GutsCoordinatorLogger) this.loggerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
