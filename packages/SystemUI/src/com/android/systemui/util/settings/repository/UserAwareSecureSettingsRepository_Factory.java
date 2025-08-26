package com.android.systemui.util.settings.repository;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class UserAwareSecureSettingsRepository_Factory implements Provider {
    private final Provider backgroundDispatcherProvider;
    private final Provider bgContextProvider;
    private final Provider secureSettingsProvider;
    private final Provider userRepositoryProvider;

    public UserAwareSecureSettingsRepository_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.secureSettingsProvider = provider;
        this.userRepositoryProvider = provider2;
        this.backgroundDispatcherProvider = provider3;
        this.bgContextProvider = provider4;
    }

    public static UserAwareSecureSettingsRepository_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new UserAwareSecureSettingsRepository_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static UserAwareSecureSettingsRepository newInstance(SecureSettings secureSettings, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        return new UserAwareSecureSettingsRepository(secureSettings, userRepository, coroutineDispatcher, coroutineContext);
    }

    public static UserAwareSecureSettingsRepository_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new UserAwareSecureSettingsRepository_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public UserAwareSecureSettingsRepository get() {
        return newInstance((SecureSettings) this.secureSettingsProvider.get(), (UserRepository) this.userRepositoryProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (CoroutineContext) this.bgContextProvider.get());
    }
}
