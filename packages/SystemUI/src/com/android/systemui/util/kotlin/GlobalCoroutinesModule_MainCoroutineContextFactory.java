package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;

/* loaded from: classes3.dex */
public final class GlobalCoroutinesModule_MainCoroutineContextFactory implements Provider {
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_MainCoroutineContextFactory(GlobalCoroutinesModule globalCoroutinesModule) {
        this.module = globalCoroutinesModule;
    }

    public static GlobalCoroutinesModule_MainCoroutineContextFactory create(GlobalCoroutinesModule globalCoroutinesModule) {
        return new GlobalCoroutinesModule_MainCoroutineContextFactory(globalCoroutinesModule);
    }

    public static CoroutineContext mainCoroutineContext(GlobalCoroutinesModule globalCoroutinesModule) {
        CoroutineContext coroutineContextMainCoroutineContext = globalCoroutinesModule.mainCoroutineContext();
        coroutineContextMainCoroutineContext.getClass();
        return coroutineContextMainCoroutineContext;
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return mainCoroutineContext(this.module);
    }
}
