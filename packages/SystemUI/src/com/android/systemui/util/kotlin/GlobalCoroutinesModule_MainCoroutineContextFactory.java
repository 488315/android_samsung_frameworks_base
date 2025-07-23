package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        CoroutineContext mainCoroutineContext = globalCoroutinesModule.mainCoroutineContext();
        mainCoroutineContext.getClass();
        return mainCoroutineContext;
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return mainCoroutineContext(this.module);
    }
}
