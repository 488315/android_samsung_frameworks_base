package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

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
        CoroutineDispatcher coroutineDispatcherUiBgDispatcher = sysUICoroutinesModule.uiBgDispatcher(executor);
        coroutineDispatcherUiBgDispatcher.getClass();
        return coroutineDispatcherUiBgDispatcher;
    }

    public static SysUICoroutinesModule_UiBgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_UiBgDispatcherFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return uiBgDispatcher(this.module, (Executor) this.uiBgExecutorProvider.get());
    }
}
