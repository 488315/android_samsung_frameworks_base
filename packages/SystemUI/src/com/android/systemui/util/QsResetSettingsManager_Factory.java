package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Provider;

public final class QsResetSettingsManager_Factory implements Provider {
    private final javax.inject.Provider broadcastDispatcherProvider;
    private final javax.inject.Provider contextProvider;

    public QsResetSettingsManager_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }

    public static QsResetSettingsManager_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new QsResetSettingsManager_Factory(provider, provider2);
    }

    public static QsResetSettingsManager newInstance(Context context, BroadcastDispatcher broadcastDispatcher) {
        return new QsResetSettingsManager(context, broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public QsResetSettingsManager get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
