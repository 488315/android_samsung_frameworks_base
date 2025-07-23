package com.android.systemui.popup;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SamsungScreenPinningRequest_Factory implements Provider {
    private final Provider broadcastDispatcherProvider;
    private final Provider contextProvider;
    private final Provider logWrapperProvider;

    public SamsungScreenPinningRequest_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.logWrapperProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
    }

    public static SamsungScreenPinningRequest_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SamsungScreenPinningRequest_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static SamsungScreenPinningRequest newInstance(Context context, LogWrapper logWrapper, BroadcastDispatcher broadcastDispatcher) {
        return new SamsungScreenPinningRequest(context, logWrapper, broadcastDispatcher);
    }

    public static SamsungScreenPinningRequest_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SamsungScreenPinningRequest_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public SamsungScreenPinningRequest get() {
        return newInstance((Context) this.contextProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
