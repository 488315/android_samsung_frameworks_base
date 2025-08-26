package com.android.systemui.util;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class InitializationChecker_Factory implements Provider {
    private final Provider instrumentationTestProvider;

    public InitializationChecker_Factory(Provider provider) {
        this.instrumentationTestProvider = provider;
    }

    public static InitializationChecker_Factory create(javax.inject.Provider provider) {
        return new InitializationChecker_Factory(Providers.asDaggerProvider(provider));
    }

    public static InitializationChecker newInstance(boolean z) {
        return new InitializationChecker(z);
    }

    public static InitializationChecker_Factory create(Provider provider) {
        return new InitializationChecker_Factory(provider);
    }

    @Override // javax.inject.Provider
    public InitializationChecker get() {
        return newInstance(((Boolean) this.instrumentationTestProvider.get()).booleanValue());
    }
}
