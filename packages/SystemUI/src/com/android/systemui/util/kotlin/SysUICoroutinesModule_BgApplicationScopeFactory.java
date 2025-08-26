package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_BgApplicationScopeFactory implements Provider {
    private final Provider applicationScopeProvider;
    private final Provider coroutineContextProvider;
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgApplicationScopeFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider, Provider provider2) {
        this.module = sysUICoroutinesModule;
        this.applicationScopeProvider = provider;
        this.coroutineContextProvider = provider2;
    }

    public static CoroutineScope bgApplicationScope(SysUICoroutinesModule sysUICoroutinesModule, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        CoroutineScope coroutineScopeBgApplicationScope = sysUICoroutinesModule.bgApplicationScope(coroutineScope, coroutineContext);
        coroutineScopeBgApplicationScope.getClass();
        return coroutineScopeBgApplicationScope;
    }

    public static SysUICoroutinesModule_BgApplicationScopeFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SysUICoroutinesModule_BgApplicationScopeFactory(sysUICoroutinesModule, Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static SysUICoroutinesModule_BgApplicationScopeFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider, Provider provider2) {
        return new SysUICoroutinesModule_BgApplicationScopeFactory(sysUICoroutinesModule, provider, provider2);
    }

    @Override // javax.inject.Provider
    public CoroutineScope get() {
        return bgApplicationScope(this.module, (CoroutineScope) this.applicationScopeProvider.get(), (CoroutineContext) this.coroutineContextProvider.get());
    }
}
