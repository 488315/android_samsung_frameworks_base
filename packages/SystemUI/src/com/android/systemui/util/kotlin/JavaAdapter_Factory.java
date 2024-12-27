package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

public final class JavaAdapter_Factory implements Provider {
    private final javax.inject.Provider scopeProvider;

    public JavaAdapter_Factory(javax.inject.Provider provider) {
        this.scopeProvider = provider;
    }

    public static JavaAdapter_Factory create(javax.inject.Provider provider) {
        return new JavaAdapter_Factory(provider);
    }

    public static JavaAdapter newInstance(CoroutineScope coroutineScope) {
        return new JavaAdapter(coroutineScope);
    }

    @Override // javax.inject.Provider
    public JavaAdapter get() {
        return newInstance((CoroutineScope) this.scopeProvider.get());
    }
}
