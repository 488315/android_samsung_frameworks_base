package com.android.systemui.util.service;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.service.ObservableServiceConnection;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ObservableServiceConnection_Factory<T> implements Provider {
    private final javax.inject.Provider bgExecutorProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider serviceIntentProvider;
    private final javax.inject.Provider transformerProvider;
    private final javax.inject.Provider userTrackerProvider;

    public ObservableServiceConnection_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.contextProvider = provider;
        this.serviceIntentProvider = provider2;
        this.userTrackerProvider = provider3;
        this.bgExecutorProvider = provider4;
        this.transformerProvider = provider5;
    }

    public static <T> ObservableServiceConnection_Factory<T> create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new ObservableServiceConnection_Factory<>(provider, provider2, provider3, provider4, provider5);
    }

    public static <T> ObservableServiceConnection<T> newInstance(Context context, Intent intent, UserTracker userTracker, Executor executor, ObservableServiceConnection.ServiceTransformer<T> serviceTransformer) {
        return new ObservableServiceConnection<>(context, intent, userTracker, executor, serviceTransformer);
    }

    @Override // javax.inject.Provider
    public ObservableServiceConnection<T> get() {
        return newInstance((Context) this.contextProvider.get(), (Intent) this.serviceIntentProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Executor) this.bgExecutorProvider.get(), (ObservableServiceConnection.ServiceTransformer) this.transformerProvider.get());
    }
}
