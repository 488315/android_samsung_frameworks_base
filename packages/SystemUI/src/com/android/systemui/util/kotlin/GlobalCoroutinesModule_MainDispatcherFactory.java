package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

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
        Preconditions.checkNotNullFromProvides(mainDispatcher);
        return mainDispatcher;
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return mainDispatcher(this.module);
    }
}
