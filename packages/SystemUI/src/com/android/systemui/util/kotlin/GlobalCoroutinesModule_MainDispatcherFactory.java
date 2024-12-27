package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
