package com.android.systemui.util;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
