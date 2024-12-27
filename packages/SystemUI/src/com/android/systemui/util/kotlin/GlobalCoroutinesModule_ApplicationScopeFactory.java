package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

public final class GlobalCoroutinesModule_ApplicationScopeFactory implements Provider {
    private final javax.inject.Provider dispatcherContextProvider;
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_ApplicationScopeFactory(GlobalCoroutinesModule globalCoroutinesModule, javax.inject.Provider provider) {
        this.module = globalCoroutinesModule;
        this.dispatcherContextProvider = provider;
    }

    public static CoroutineScope applicationScope(GlobalCoroutinesModule globalCoroutinesModule, CoroutineContext coroutineContext) {
        CoroutineScope applicationScope = globalCoroutinesModule.applicationScope(coroutineContext);
        Preconditions.checkNotNullFromProvides(applicationScope);
        return applicationScope;
    }

    public static GlobalCoroutinesModule_ApplicationScopeFactory create(GlobalCoroutinesModule globalCoroutinesModule, javax.inject.Provider provider) {
        return new GlobalCoroutinesModule_ApplicationScopeFactory(globalCoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineScope get() {
        return applicationScope(this.module, (CoroutineContext) this.dispatcherContextProvider.get());
    }
}
