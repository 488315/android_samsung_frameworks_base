package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUICoroutinesModule_BgDispatcherFactory implements Provider {
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgDispatcherFactory(SysUICoroutinesModule sysUICoroutinesModule) {
        this.module = sysUICoroutinesModule;
    }

    public static CoroutineDispatcher bgDispatcher(SysUICoroutinesModule sysUICoroutinesModule) {
        CoroutineDispatcher bgDispatcher = sysUICoroutinesModule.bgDispatcher();
        bgDispatcher.getClass();
        return bgDispatcher;
    }

    public static SysUICoroutinesModule_BgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule) {
        return new SysUICoroutinesModule_BgDispatcherFactory(sysUICoroutinesModule);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return bgDispatcher(this.module);
    }
}
