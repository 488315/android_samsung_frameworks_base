package com.android.systemui.util;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class NotificationChannels_Factory implements Provider {
    private final Provider contextProvider;

    public NotificationChannels_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static NotificationChannels_Factory create(javax.inject.Provider provider) {
        return new NotificationChannels_Factory(Providers.asDaggerProvider(provider));
    }

    public static NotificationChannels newInstance(Context context) {
        return new NotificationChannels(context);
    }

    public static NotificationChannels_Factory create(Provider provider) {
        return new NotificationChannels_Factory(provider);
    }

    @Override // javax.inject.Provider
    public NotificationChannels get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
