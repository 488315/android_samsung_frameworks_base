package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_UiBgDispatcherFactory implements Provider {
    private final SysUICoroutinesModule module;
    private final Provider uiBgExecutorProvider;

    public SysUICoroutinesModule_UiBgDispatcherFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        this.module = sysUICoroutinesModule;
        this.uiBgExecutorProvider = provider;
    }

    public static SysUICoroutinesModule_UiBgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider) {
        return new SysUICoroutinesModule_UiBgDispatcherFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider));
    }

    public static CoroutineDispatcher uiBgDispatcher(SysUICoroutinesModule sysUICoroutinesModule, Executor executor) {
        CoroutineDispatcher uiBgDispatcher = sysUICoroutinesModule.uiBgDispatcher(executor);
        uiBgDispatcher.getClass();
        return uiBgDispatcher;
    }

    public static SysUICoroutinesModule_UiBgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_UiBgDispatcherFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return uiBgDispatcher(this.module, (Executor) this.uiBgExecutorProvider.get());
    }
}
