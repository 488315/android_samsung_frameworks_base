package com.android.systemui.util.service;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PersistentConnectionManager_Factory<T> implements Provider {
    private final Provider baseReconnectDelayMsProvider;
    private final Provider bgExecutorProvider;
    private final Provider clockProvider;
    private final Provider dumpManagerProvider;
    private final Provider dumpsysNameProvider;
    private final Provider maxReconnectAttemptsProvider;
    private final Provider minConnectionDurationMsProvider;
    private final Provider observerProvider;
    private final Provider serviceConnectionProvider;

    public PersistentConnectionManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.clockProvider = provider;
        this.bgExecutorProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.dumpsysNameProvider = provider4;
        this.serviceConnectionProvider = provider5;
        this.maxReconnectAttemptsProvider = provider6;
        this.baseReconnectDelayMsProvider = provider7;
        this.minConnectionDurationMsProvider = provider8;
        this.observerProvider = provider9;
    }

    public static <T> PersistentConnectionManager_Factory<T> create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        return new PersistentConnectionManager_Factory<>(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9));
    }

    public static <T> PersistentConnectionManager<T> newInstance(SystemClock systemClock, DelayableExecutor delayableExecutor, DumpManager dumpManager, String str, ObservableServiceConnection<T> observableServiceConnection, int i, int i2, int i3, Observer observer) {
        return new PersistentConnectionManager<>(systemClock, delayableExecutor, dumpManager, str, observableServiceConnection, i, i2, i3, observer);
    }

    public static <T> PersistentConnectionManager_Factory<T> create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new PersistentConnectionManager_Factory<>(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    @Override // javax.inject.Provider
    public PersistentConnectionManager<T> get() {
        return newInstance((SystemClock) this.clockProvider.get(), (DelayableExecutor) this.bgExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (String) this.dumpsysNameProvider.get(), (ObservableServiceConnection) this.serviceConnectionProvider.get(), ((Integer) this.maxReconnectAttemptsProvider.get()).intValue(), ((Integer) this.baseReconnectDelayMsProvider.get()).intValue(), ((Integer) this.minConnectionDurationMsProvider.get()).intValue(), (Observer) this.observerProvider.get());
    }
}
