package com.android.systemui.popup;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
