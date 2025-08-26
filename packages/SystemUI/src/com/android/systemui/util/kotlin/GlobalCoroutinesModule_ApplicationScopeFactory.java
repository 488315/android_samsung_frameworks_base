package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class GlobalCoroutinesModule_ApplicationScopeFactory implements Provider {
    private final Provider dispatcherContextProvider;
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_ApplicationScopeFactory(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        this.module = globalCoroutinesModule;
        this.dispatcherContextProvider = provider;
    }

    public static CoroutineScope applicationScope(GlobalCoroutinesModule globalCoroutinesModule, CoroutineContext coroutineContext) {
        CoroutineScope coroutineScopeApplicationScope = globalCoroutinesModule.applicationScope(coroutineContext);
        coroutineScopeApplicationScope.getClass();
        return coroutineScopeApplicationScope;
    }

    public static GlobalCoroutinesModule_ApplicationScopeFactory create(GlobalCoroutinesModule globalCoroutinesModule, javax.inject.Provider provider) {
        return new GlobalCoroutinesModule_ApplicationScopeFactory(globalCoroutinesModule, Providers.asDaggerProvider(provider));
    }

    public static GlobalCoroutinesModule_ApplicationScopeFactory create(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        return new GlobalCoroutinesModule_ApplicationScopeFactory(globalCoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineScope get() {
        return applicationScope(this.module, (CoroutineContext) this.dispatcherContextProvider.get());
    }
}
