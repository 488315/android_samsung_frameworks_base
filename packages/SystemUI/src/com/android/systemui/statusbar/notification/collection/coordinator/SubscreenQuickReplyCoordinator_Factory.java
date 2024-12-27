package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenQuickReplyCoordinator_Factory implements Provider {
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider mControllerProvider;
    private final javax.inject.Provider mMainHandlerProvider;

    public SubscreenQuickReplyCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.dumpManagerProvider = provider;
        this.mControllerProvider = provider2;
        this.mMainHandlerProvider = provider3;
    }

    public static SubscreenQuickReplyCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SubscreenQuickReplyCoordinator_Factory(provider, provider2, provider3);
    }

    public static SubscreenQuickReplyCoordinator newInstance(DumpManager dumpManager, SubscreenNotificationController subscreenNotificationController, Handler handler) {
        return new SubscreenQuickReplyCoordinator(dumpManager, subscreenNotificationController, handler);
    }

    @Override // javax.inject.Provider
    public SubscreenQuickReplyCoordinator get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (SubscreenNotificationController) this.mControllerProvider.get(), (Handler) this.mMainHandlerProvider.get());
    }
}
