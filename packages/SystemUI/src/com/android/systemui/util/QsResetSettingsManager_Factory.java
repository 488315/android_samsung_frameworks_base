package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class QsResetSettingsManager_Factory implements Provider {
    private final Provider broadcastDispatcherProvider;
    private final Provider contextProvider;

    public QsResetSettingsManager_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }

    public static QsResetSettingsManager_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new QsResetSettingsManager_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static QsResetSettingsManager newInstance(Context context, BroadcastDispatcher broadcastDispatcher) {
        return new QsResetSettingsManager(context, broadcastDispatcher);
    }

    public static QsResetSettingsManager_Factory create(Provider provider, Provider provider2) {
        return new QsResetSettingsManager_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public QsResetSettingsManager get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
