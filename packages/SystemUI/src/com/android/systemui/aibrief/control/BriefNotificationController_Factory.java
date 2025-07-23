package com.android.systemui.aibrief.control;

import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BriefNotificationController_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider notificationManagerProvider;

    public BriefNotificationController_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.notificationManagerProvider = provider2;
    }

    public static BriefNotificationController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new BriefNotificationController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static BriefNotificationController newInstance(Context context, NotificationManagerCompat notificationManagerCompat) {
        return new BriefNotificationController(context, notificationManagerCompat);
    }

    public static BriefNotificationController_Factory create(Provider provider, Provider provider2) {
        return new BriefNotificationController_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public BriefNotificationController get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationManagerCompat) this.notificationManagerProvider.get());
    }
}
