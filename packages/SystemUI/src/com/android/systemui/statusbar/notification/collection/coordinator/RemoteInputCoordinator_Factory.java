package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RemoteInputCoordinator_Factory implements Provider {
    private final Provider dumpManagerProvider;
    private final Provider mMainHandlerProvider;
    private final Provider mNotificationRemoteInputManagerProvider;
    private final Provider mRebuilderProvider;
    private final Provider mSmartReplyControllerProvider;

    public RemoteInputCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.dumpManagerProvider = provider;
        this.mRebuilderProvider = provider2;
        this.mNotificationRemoteInputManagerProvider = provider3;
        this.mMainHandlerProvider = provider4;
        this.mSmartReplyControllerProvider = provider5;
    }

    public static RemoteInputCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new RemoteInputCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static RemoteInputCoordinator newInstance(DumpManager dumpManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, NotificationRemoteInputManager notificationRemoteInputManager, Handler handler, SmartReplyController smartReplyController) {
        return new RemoteInputCoordinator(dumpManager, remoteInputNotificationRebuilder, notificationRemoteInputManager, handler, smartReplyController);
    }

    public static RemoteInputCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new RemoteInputCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public RemoteInputCoordinator get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (RemoteInputNotificationRebuilder) this.mRebuilderProvider.get(), (NotificationRemoteInputManager) this.mNotificationRemoteInputManagerProvider.get(), (Handler) this.mMainHandlerProvider.get(), (SmartReplyController) this.mSmartReplyControllerProvider.get());
    }
}
