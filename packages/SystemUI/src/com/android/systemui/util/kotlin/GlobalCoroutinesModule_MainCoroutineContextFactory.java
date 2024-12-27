package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;

public final class GlobalCoroutinesModule_MainCoroutineContextFactory implements Provider {
    private final GlobalCoroutinesModule module;
    private final javax.inject.Provider tracingCoroutineContextProvider;

    public GlobalCoroutinesModule_MainCoroutineContextFactory(GlobalCoroutinesModule globalCoroutinesModule, javax.inject.Provider provider) {
        this.module = globalCoroutinesModule;
        this.tracingCoroutineContextProvider = provider;
    }

    public static GlobalCoroutinesModule_MainCoroutineContextFactory create(GlobalCoroutinesModule globalCoroutinesModule, javax.inject.Provider provider) {
        return new GlobalCoroutinesModule_MainCoroutineContextFactory(globalCoroutinesModule, provider);
    }

    public static CoroutineContext mainCoroutineContext(GlobalCoroutinesModule globalCoroutinesModule, CoroutineContext coroutineContext) {
        CoroutineContext mainCoroutineContext = globalCoroutinesModule.mainCoroutineContext(coroutineContext);
        Preconditions.checkNotNullFromProvides(mainCoroutineContext);
        return mainCoroutineContext;
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return mainCoroutineContext(this.module, (CoroutineContext) this.tracingCoroutineContextProvider.get());
    }
}
