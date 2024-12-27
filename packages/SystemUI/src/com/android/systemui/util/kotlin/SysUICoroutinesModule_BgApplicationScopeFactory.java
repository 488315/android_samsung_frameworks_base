package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUICoroutinesModule_BgApplicationScopeFactory implements Provider {
    private final javax.inject.Provider applicationScopeProvider;
    private final javax.inject.Provider coroutineContextProvider;
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgApplicationScopeFactory(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.module = sysUICoroutinesModule;
        this.applicationScopeProvider = provider;
        this.coroutineContextProvider = provider2;
    }

    public static CoroutineScope bgApplicationScope(SysUICoroutinesModule sysUICoroutinesModule, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        CoroutineScope bgApplicationScope = sysUICoroutinesModule.bgApplicationScope(coroutineScope, coroutineContext);
        Preconditions.checkNotNullFromProvides(bgApplicationScope);
        return bgApplicationScope;
    }

    public static SysUICoroutinesModule_BgApplicationScopeFactory create(SysUICoroutinesModule sysUICoroutinesModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SysUICoroutinesModule_BgApplicationScopeFactory(sysUICoroutinesModule, provider, provider2);
    }

    @Override // javax.inject.Provider
    public CoroutineScope get() {
        return bgApplicationScope(this.module, (CoroutineScope) this.applicationScopeProvider.get(), (CoroutineContext) this.coroutineContextProvider.get());
    }
}
