package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_BgCoroutineContextFactory implements Provider {
    private final javax.inject.Provider bgCoroutineDispatcherProvider;
    private final SysUICoroutinesModule module;
    private final javax.inject.Provider tracingCoroutineContextProvider;

    public SysUICoroutinesModule_BgCoroutineContextFactory(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.module = sysUICoroutinesModule;
        this.tracingCoroutineContextProvider = provider;
        this.bgCoroutineDispatcherProvider = provider2;
    }

    public static CoroutineContext bgCoroutineContext(SysUICoroutinesModule sysUICoroutinesModule, CoroutineContext coroutineContext, CoroutineDispatcher coroutineDispatcher) {
        CoroutineContext bgCoroutineContext = sysUICoroutinesModule.bgCoroutineContext(coroutineContext, coroutineDispatcher);
        Preconditions.checkNotNullFromProvides(bgCoroutineContext);
        return bgCoroutineContext;
    }

    public static SysUICoroutinesModule_BgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SysUICoroutinesModule_BgCoroutineContextFactory(sysUICoroutinesModule, provider, provider2);
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return bgCoroutineContext(this.module, (CoroutineContext) this.tracingCoroutineContextProvider.get(), (CoroutineDispatcher) this.bgCoroutineDispatcherProvider.get());
    }
}
