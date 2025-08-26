package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_BgDispatcherFactory implements Provider {
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgDispatcherFactory(SysUICoroutinesModule sysUICoroutinesModule) {
        this.module = sysUICoroutinesModule;
    }

    public static CoroutineDispatcher bgDispatcher(SysUICoroutinesModule sysUICoroutinesModule) {
        CoroutineDispatcher coroutineDispatcherBgDispatcher = sysUICoroutinesModule.bgDispatcher();
        coroutineDispatcherBgDispatcher.getClass();
        return coroutineDispatcherBgDispatcher;
    }

    public static SysUICoroutinesModule_BgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule) {
        return new SysUICoroutinesModule_BgDispatcherFactory(sysUICoroutinesModule);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return bgDispatcher(this.module);
    }
}
