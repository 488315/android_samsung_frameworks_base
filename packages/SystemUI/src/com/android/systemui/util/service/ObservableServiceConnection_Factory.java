package com.android.systemui.util.service;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.service.ObservableServiceConnection;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class ObservableServiceConnection_Factory<T> implements Provider {
    private final Provider bgExecutorProvider;
    private final Provider contextProvider;
    private final Provider serviceIntentProvider;
    private final Provider transformerProvider;
    private final Provider userTrackerProvider;

    public ObservableServiceConnection_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.serviceIntentProvider = provider2;
        this.userTrackerProvider = provider3;
        this.bgExecutorProvider = provider4;
        this.transformerProvider = provider5;
    }

    public static <T> ObservableServiceConnection_Factory<T> create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new ObservableServiceConnection_Factory<>(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static <T> ObservableServiceConnection<T> newInstance(Context context, Intent intent, UserTracker userTracker, Executor executor, ObservableServiceConnection.ServiceTransformer<T> serviceTransformer) {
        return new ObservableServiceConnection<>(context, intent, userTracker, executor, serviceTransformer);
    }

    public static <T> ObservableServiceConnection_Factory<T> create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new ObservableServiceConnection_Factory<>(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public ObservableServiceConnection<T> get() {
        return newInstance((Context) this.contextProvider.get(), (Intent) this.serviceIntentProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Executor) this.bgExecutorProvider.get(), (ObservableServiceConnection.ServiceTransformer) this.transformerProvider.get());
    }
}
