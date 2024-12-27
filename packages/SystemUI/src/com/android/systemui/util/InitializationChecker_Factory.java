package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class InitializationChecker_Factory implements Provider {
    private final javax.inject.Provider instrumentationTestProvider;

    public InitializationChecker_Factory(javax.inject.Provider provider) {
        this.instrumentationTestProvider = provider;
    }

    public static InitializationChecker_Factory create(javax.inject.Provider provider) {
        return new InitializationChecker_Factory(provider);
    }

    public static InitializationChecker newInstance(boolean z) {
        return new InitializationChecker(z);
    }

    @Override // javax.inject.Provider
    public InitializationChecker get() {
        return newInstance(((Boolean) this.instrumentationTestProvider.get()).booleanValue());
    }
}
