package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenQuickReplyCoordinator_Factory implements Provider {
    private final Provider dumpManagerProvider;
    private final Provider mControllerProvider;
    private final Provider mMainHandlerProvider;

    public SubscreenQuickReplyCoordinator_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.dumpManagerProvider = provider;
        this.mControllerProvider = provider2;
        this.mMainHandlerProvider = provider3;
    }

    public static SubscreenQuickReplyCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SubscreenQuickReplyCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static SubscreenQuickReplyCoordinator newInstance(DumpManager dumpManager, SubscreenNotificationController subscreenNotificationController, Handler handler) {
        return new SubscreenQuickReplyCoordinator(dumpManager, subscreenNotificationController, handler);
    }

    public static SubscreenQuickReplyCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SubscreenQuickReplyCoordinator_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public SubscreenQuickReplyCoordinator get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (SubscreenNotificationController) this.mControllerProvider.get(), (Handler) this.mMainHandlerProvider.get());
    }
}
