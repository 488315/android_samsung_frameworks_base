package com.android.systemui.util.settings.repository;

import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class SecureSettingsForUserRepository_Factory implements Provider {
    private final Provider backgroundContextProvider;
    private final Provider backgroundDispatcherProvider;
    private final Provider secureSettingsProvider;

    public SecureSettingsForUserRepository_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.secureSettingsProvider = provider;
        this.backgroundDispatcherProvider = provider2;
        this.backgroundContextProvider = provider3;
    }

    public static SecureSettingsForUserRepository_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SecureSettingsForUserRepository_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static SecureSettingsForUserRepository newInstance(SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        return new SecureSettingsForUserRepository(secureSettings, coroutineDispatcher, coroutineContext);
    }

    public static SecureSettingsForUserRepository_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SecureSettingsForUserRepository_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public SecureSettingsForUserRepository get() {
        return newInstance((SecureSettings) this.secureSettingsProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (CoroutineContext) this.backgroundContextProvider.get());
    }
}
