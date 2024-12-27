package com.android.systemui.util;

import dagger.internal.Provider;

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
