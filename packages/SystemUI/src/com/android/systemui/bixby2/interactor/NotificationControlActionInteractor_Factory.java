package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.NotificationController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NotificationControlActionInteractor_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider notificationControllerProvider;

    public NotificationControlActionInteractor_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.notificationControllerProvider = provider2;
    }

    public static NotificationControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new NotificationControlActionInteractor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static NotificationControlActionInteractor newInstance(Context context, NotificationController notificationController) {
        return new NotificationControlActionInteractor(context, notificationController);
    }

    public static NotificationControlActionInteractor_Factory create(Provider provider, Provider provider2) {
        return new NotificationControlActionInteractor_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public NotificationControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationController) this.notificationControllerProvider.get());
    }
}
