package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_BgCoroutineContextFactory implements Provider {
    private final Provider bgCoroutineDispatcherProvider;
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgCoroutineContextFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        this.module = sysUICoroutinesModule;
        this.bgCoroutineDispatcherProvider = provider;
    }

    public static CoroutineContext bgCoroutineContext(SysUICoroutinesModule sysUICoroutinesModule, CoroutineDispatcher coroutineDispatcher) {
        CoroutineContext bgCoroutineContext = sysUICoroutinesModule.bgCoroutineContext(coroutineDispatcher);
        bgCoroutineContext.getClass();
        return bgCoroutineContext;
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
