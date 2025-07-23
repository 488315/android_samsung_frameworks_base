package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalCoroutinesModule_MainDispatcherFactory implements Provider {
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_MainDispatcherFactory(GlobalCoroutinesModule globalCoroutinesModule) {
        this.module = globalCoroutinesModule;
    }

    public static GlobalCoroutinesModule_MainDispatcherFactory create(GlobalCoroutinesModule globalCoroutinesModule) {
        return new GlobalCoroutinesModule_MainDispatcherFactory(globalCoroutinesModule);
    }

    public static CoroutineDispatcher mainDispatcher(GlobalCoroutinesModule globalCoroutinesModule) {
        CoroutineDispatcher mainDispatcher = globalCoroutinesModule.mainDispatcher();
        mainDispatcher.getClass();
        return mainDispatcher;
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return mainDispatcher(this.module);
    }
}
