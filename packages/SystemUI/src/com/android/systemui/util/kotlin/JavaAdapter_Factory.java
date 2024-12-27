package com.android.systemui.util.kotlin;

import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
