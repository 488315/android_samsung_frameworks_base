package com.android.systemui.p032tv;

import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.p030tv.notifications.TvNotificationHandler;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvSystemUIModule_ProvideTvNotificationHandlerFactory implements Provider {
    public final Provider notificationListenerProvider;

    public TvSystemUIModule_ProvideTvNotificationHandlerFactory(Provider provider) {
        this.notificationListenerProvider = provider;
    }

    public static TvNotificationHandler provideTvNotificationHandler(NotificationListener notificationListener) {
        return new TvNotificationHandler(notificationListener);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TvNotificationHandler((NotificationListener) this.notificationListenerProvider.get());
    }
}
