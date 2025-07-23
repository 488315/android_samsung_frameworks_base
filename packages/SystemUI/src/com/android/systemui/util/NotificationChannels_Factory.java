package com.android.systemui.util;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
