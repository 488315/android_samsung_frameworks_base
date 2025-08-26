package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_BgCoroutineContextFactory implements Provider {
    private final Provider bgCoroutineDispatcherProvider;
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgCoroutineContextFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        this.module = sysUICoroutinesModule;
        this.bgCoroutineDispatcherProvider = provider;
    }

    public static CoroutineContext bgCoroutineContext(SysUICoroutinesModule sysUICoroutinesModule, CoroutineDispatcher coroutineDispatcher) {
        CoroutineContext coroutineContextBgCoroutineContext = sysUICoroutinesModule.bgCoroutineContext(coroutineDispatcher);
        coroutineContextBgCoroutineContext.getClass();
        return coroutineContextBgCoroutineContext;
    }

    public static SysUICoroutinesModule_BgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider) {
        return new SysUICoroutinesModule_BgCoroutineContextFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider));
    }

    public static SysUICoroutinesModule_BgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_BgCoroutineContextFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return bgCoroutineContext(this.module, (CoroutineDispatcher) this.bgCoroutineDispatcherProvider.get());
    }
}
