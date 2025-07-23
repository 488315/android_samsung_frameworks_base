package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalCoroutinesModule_ApplicationScopeFactory implements Provider {
    private final Provider dispatcherContextProvider;
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_ApplicationScopeFactory(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        this.module = globalCoroutinesModule;
        this.dispatcherContextProvider = provider;
    }

    public static CoroutineScope applicationScope(GlobalCoroutinesModule globalCoroutinesModule, CoroutineContext coroutineContext) {
        CoroutineScope applicationScope = globalCoroutinesModule.applicationScope(coroutineContext);
        applicationScope.getClass();
        return applicationScope;
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
