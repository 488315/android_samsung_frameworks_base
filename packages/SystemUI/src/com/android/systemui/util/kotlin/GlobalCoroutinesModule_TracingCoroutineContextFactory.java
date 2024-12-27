package com.android.systemui.util.kotlin;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlobalCoroutinesModule_TracingCoroutineContextFactory implements Provider {
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_TracingCoroutineContextFactory(GlobalCoroutinesModule globalCoroutinesModule) {
        this.module = globalCoroutinesModule;
    }

    public static GlobalCoroutinesModule_TracingCoroutineContextFactory create(GlobalCoroutinesModule globalCoroutinesModule) {
        return new GlobalCoroutinesModule_TracingCoroutineContextFactory(globalCoroutinesModule);
    }

    public static CoroutineContext tracingCoroutineContext(GlobalCoroutinesModule globalCoroutinesModule) {
        CoroutineContext tracingCoroutineContext = globalCoroutinesModule.tracingCoroutineContext();
        Preconditions.checkNotNullFromProvides(tracingCoroutineContext);
        return tracingCoroutineContext;
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return tracingCoroutineContext(this.module);
    }
}
