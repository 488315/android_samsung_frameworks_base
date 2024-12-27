package com.android.systemui.popup;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;

public final class SamsungScreenPinningRequest_Factory implements Provider {
    private final javax.inject.Provider broadcastDispatcherProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider logWrapperProvider;

    public SamsungScreenPinningRequest_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.contextProvider = provider;
        this.logWrapperProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
    }

    public static SamsungScreenPinningRequest_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SamsungScreenPinningRequest_Factory(provider, provider2, provider3);
    }

    public static SamsungScreenPinningRequest newInstance(Context context, LogWrapper logWrapper, BroadcastDispatcher broadcastDispatcher) {
        return new SamsungScreenPinningRequest(context, logWrapper, broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public SamsungScreenPinningRequest get() {
        return newInstance((Context) this.contextProvider.get(), (LogWrapper) this.logWrapperProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
