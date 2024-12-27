package com.android.systemui.util.service;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class PersistentConnectionManager_Factory<T> implements Provider {
    private final javax.inject.Provider baseReconnectDelayMsProvider;
    private final javax.inject.Provider bgExecutorProvider;
    private final javax.inject.Provider clockProvider;
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider dumpsysNameProvider;
    private final javax.inject.Provider maxReconnectAttemptsProvider;
    private final javax.inject.Provider minConnectionDurationMsProvider;
    private final javax.inject.Provider observerProvider;
    private final javax.inject.Provider serviceConnectionProvider;

    public PersistentConnectionManager_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
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
        return new PersistentConnectionManager_Factory<>(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static <T> PersistentConnectionManager<T> newInstance(SystemClock systemClock, DelayableExecutor delayableExecutor, DumpManager dumpManager, String str, ObservableServiceConnection<T> observableServiceConnection, int i, int i2, int i3, Observer observer) {
        return new PersistentConnectionManager<>(systemClock, delayableExecutor, dumpManager, str, observableServiceConnection, i, i2, i3, observer);
    }

    @Override // javax.inject.Provider
    public PersistentConnectionManager<T> get() {
        return newInstance((SystemClock) this.clockProvider.get(), (DelayableExecutor) this.bgExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (String) this.dumpsysNameProvider.get(), (ObservableServiceConnection) this.serviceConnectionProvider.get(), ((Integer) this.maxReconnectAttemptsProvider.get()).intValue(), ((Integer) this.baseReconnectDelayMsProvider.get()).intValue(), ((Integer) this.minConnectionDurationMsProvider.get()).intValue(), (Observer) this.observerProvider.get());
    }
}
