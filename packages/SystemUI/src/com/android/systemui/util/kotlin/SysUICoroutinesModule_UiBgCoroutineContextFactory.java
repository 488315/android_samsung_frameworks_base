package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        CoroutineContext uiBgCoroutineContext = sysUICoroutinesModule.uiBgCoroutineContext(coroutineDispatcher);
        uiBgCoroutineContext.getClass();
        return uiBgCoroutineContext;
    }

    public static SysUICoroutinesModule_UiBgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider) {
        return new SysUICoroutinesModule_UiBgCoroutineContextFactory(sysUICoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return uiBgCoroutineContext(this.module, (CoroutineDispatcher) this.uiBgCoroutineDispatcherProvider.get());
    }
}
