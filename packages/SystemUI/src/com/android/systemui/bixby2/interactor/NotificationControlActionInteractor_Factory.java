package com.android.systemui.bixby2.interactor;

import android.content.Context;
import com.android.systemui.bixby2.controller.NotificationController;
import dagger.internal.Provider;

public final class NotificationControlActionInteractor_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider notificationControllerProvider;

    public NotificationControlActionInteractor_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.notificationControllerProvider = provider2;
    }

    public static NotificationControlActionInteractor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new NotificationControlActionInteractor_Factory(provider, provider2);
    }

    public static NotificationControlActionInteractor newInstance(Context context, NotificationController notificationController) {
        return new NotificationControlActionInteractor(context, notificationController);
    }

    @Override // javax.inject.Provider
    public NotificationControlActionInteractor get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationController) this.notificationControllerProvider.get());
    }
}
