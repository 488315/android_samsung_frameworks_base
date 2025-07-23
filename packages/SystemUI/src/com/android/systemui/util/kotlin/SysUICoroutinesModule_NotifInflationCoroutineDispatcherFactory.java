package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_NotifInflationCoroutineDispatcherFactory implements Provider {
    private final Provider bgCoroutineDispatcherProvider;
    private final SysUICoroutinesModule module;
    private final Provider notifInflationExecutorProvider;

    public SysUICoroutinesModule_NotifInflationCoroutineDispatcherFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider, Provider provider2) {
        this.module = sysUICoroutinesModule;
        this.notifInflationExecutorProvider = provider;
        this.bgCoroutineDispatcherProvider = provider2;
    }

    public static SysUICoroutinesModule_NotifInflationCoroutineDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SysUICoroutinesModule_NotifInflationCoroutineDispatcherFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static CoroutineDispatcher notifInflationCoroutineDispatcher(SysUICoroutinesModule sysUICoroutinesModule, Executor executor, CoroutineDispatcher coroutineDispatcher) {
        CoroutineDispatcher notifInflationCoroutineDispatcher = sysUICoroutinesModule.notifInflationCoroutineDispatcher(executor, coroutineDispatcher);
        notifInflationCoroutineDispatcher.getClass();
        return notifInflationCoroutineDispatcher;
    }

    public static SysUICoroutinesModule_NotifInflationCoroutineDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider, Provider provider2) {
        return new SysUICoroutinesModule_NotifInflationCoroutineDispatcherFactory(sysUICoroutinesModule, provider, provider2);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return notifInflationCoroutineDispatcher(this.module, (Executor) this.notifInflationExecutorProvider.get(), (CoroutineDispatcher) this.bgCoroutineDispatcherProvider.get());
    }
}
