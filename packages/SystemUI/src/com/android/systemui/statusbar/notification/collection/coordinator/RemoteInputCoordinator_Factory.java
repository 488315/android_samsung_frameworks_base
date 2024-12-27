package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RemoteInputCoordinator_Factory implements Provider {
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider mMainHandlerProvider;
    private final javax.inject.Provider mNotificationRemoteInputManagerProvider;
    private final javax.inject.Provider mRebuilderProvider;
    private final javax.inject.Provider mSmartReplyControllerProvider;

    public RemoteInputCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.dumpManagerProvider = provider;
        this.mRebuilderProvider = provider2;
        this.mNotificationRemoteInputManagerProvider = provider3;
        this.mMainHandlerProvider = provider4;
        this.mSmartReplyControllerProvider = provider5;
    }

    public static RemoteInputCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new RemoteInputCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static RemoteInputCoordinator newInstance(DumpManager dumpManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, NotificationRemoteInputManager notificationRemoteInputManager, Handler handler, SmartReplyController smartReplyController) {
        return new RemoteInputCoordinator(dumpManager, remoteInputNotificationRebuilder, notificationRemoteInputManager, handler, smartReplyController);
    }

    @Override // javax.inject.Provider
    public RemoteInputCoordinator get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (RemoteInputNotificationRebuilder) this.mRebuilderProvider.get(), (NotificationRemoteInputManager) this.mNotificationRemoteInputManagerProvider.get(), (Handler) this.mMainHandlerProvider.get(), (SmartReplyController) this.mSmartReplyControllerProvider.get());
    }
}
