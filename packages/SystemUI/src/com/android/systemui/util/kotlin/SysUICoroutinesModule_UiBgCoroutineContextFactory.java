package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_UiBgCoroutineContextFactory implements Provider {
    private final SysUICoroutinesModule module;
    private final Provider uiBgCoroutineDispatcherProvider;

    public SysUICoroutinesModule_UiBgCoroutineContextFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        this.module = sysUICoroutinesModule;
        this.uiBgCoroutineDispatcherProvider = provider;
    }

    public static SysUICoroutinesModule_UiBgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider) {
        return new SysUICoroutinesModule_UiBgCoroutineContextFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider));
    }

    public static CoroutineContext uiBgCoroutineContext(SysUICoroutinesModule sysUICoroutinesModule, CoroutineDispatcher coroutineDispatcher) {
        CoroutineContext coroutineContextUiBgCoroutineContext = sysUICoroutinesModule.uiBgCoroutineContext(coroutineDispatcher);
        coroutineContextUiBgCoroutineContext.getClass();
        return coroutineContextUiBgCoroutineContext;
    }

    public static SysUICoroutinesModule_UiBgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_UiBgCoroutineContextFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return uiBgCoroutineContext(this.module, (CoroutineDispatcher) this.uiBgCoroutineDispatcherProvider.get());
    }
}
