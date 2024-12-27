package com.android.systemui.aibrief.control;

import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import dagger.internal.Provider;

public final class BriefNotificationController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider notificationManagerProvider;

    public BriefNotificationController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.notificationManagerProvider = provider2;
    }

    public static BriefNotificationController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new BriefNotificationController_Factory(provider, provider2);
    }

    public static BriefNotificationController newInstance(Context context, NotificationManagerCompat notificationManagerCompat) {
        return new BriefNotificationController(context, notificationManagerCompat);
    }

    @Override // javax.inject.Provider
    public BriefNotificationController get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationManagerCompat) this.notificationManagerProvider.get());
    }
}
