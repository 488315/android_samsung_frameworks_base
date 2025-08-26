package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

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
        CoroutineDispatcher coroutineDispatcherMainDispatcher = globalCoroutinesModule.mainDispatcher();
        coroutineDispatcherMainDispatcher.getClass();
        return coroutineDispatcherMainDispatcher;
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return mainDispatcher(this.module);
    }
}
