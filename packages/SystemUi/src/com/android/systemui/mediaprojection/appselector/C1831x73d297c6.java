package com.android.systemui.mediaprojection.appselector;

import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory */
/* loaded from: classes.dex */
public final class C1831x73d297c6 implements Provider {
    public final Provider applicationScopeProvider;

    public C1831x73d297c6(Provider provider) {
        this.applicationScopeProvider = provider;
    }

    public static ContextScope provideCoroutineScope(CoroutineScope coroutineScope) {
        MediaProjectionAppSelectorModule.Companion.getClass();
        return CoroutineScopeKt.CoroutineScope(coroutineScope.getCoroutineContext().plus(new SupervisorJobImpl(null)));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCoroutineScope((CoroutineScope) this.applicationScopeProvider.get());
    }
}
