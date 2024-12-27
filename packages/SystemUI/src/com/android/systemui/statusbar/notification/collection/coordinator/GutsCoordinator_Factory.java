package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GutsCoordinator_Factory implements Provider {
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider loggerProvider;
    private final javax.inject.Provider notifGutsViewManagerProvider;

    public GutsCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.notifGutsViewManagerProvider = provider;
        this.loggerProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public static GutsCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new GutsCoordinator_Factory(provider, provider2, provider3);
    }

    public static GutsCoordinator newInstance(NotifGutsViewManager notifGutsViewManager, GutsCoordinatorLogger gutsCoordinatorLogger, DumpManager dumpManager) {
        return new GutsCoordinator(notifGutsViewManager, gutsCoordinatorLogger, dumpManager);
    }

    @Override // javax.inject.Provider
    public GutsCoordinator get() {
        return newInstance((NotifGutsViewManager) this.notifGutsViewManagerProvider.get(), (GutsCoordinatorLogger) this.loggerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
