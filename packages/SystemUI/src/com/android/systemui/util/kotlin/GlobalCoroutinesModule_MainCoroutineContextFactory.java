package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
