package com.android.systemui.util;

import android.content.Context;
import dagger.internal.Provider;

public final class NotificationChannels_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public NotificationChannels_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static NotificationChannels_Factory create(javax.inject.Provider provider) {
        return new NotificationChannels_Factory(provider);
    }

    public static NotificationChannels newInstance(Context context) {
        return new NotificationChannels(context);
    }

    @Override // javax.inject.Provider
    public NotificationChannels get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
