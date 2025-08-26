package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class JavaAdapter_Factory implements Provider {
    private final Provider scopeProvider;

    public JavaAdapter_Factory(Provider provider) {
        this.scopeProvider = provider;
    }

    public static JavaAdapter_Factory create(javax.inject.Provider provider) {
        return new JavaAdapter_Factory(Providers.asDaggerProvider(provider));
    }

    public static JavaAdapter newInstance(CoroutineScope coroutineScope) {
        return new JavaAdapter(coroutineScope);
    }

    public static JavaAdapter_Factory create(Provider provider) {
        return new JavaAdapter_Factory(provider);
    }

    @Override // javax.inject.Provider
    public JavaAdapter get() {
        return newInstance((CoroutineScope) this.scopeProvider.get());
    }
}
