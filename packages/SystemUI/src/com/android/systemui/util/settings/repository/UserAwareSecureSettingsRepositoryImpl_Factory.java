package com.android.systemui.util.settings.repository;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserAwareSecureSettingsRepositoryImpl_Factory implements Provider {
    private final javax.inject.Provider backgroundDispatcherProvider;
    private final javax.inject.Provider secureSettingsProvider;
    private final javax.inject.Provider userRepositoryProvider;

    public UserAwareSecureSettingsRepositoryImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.secureSettingsProvider = provider;
        this.userRepositoryProvider = provider2;
        this.backgroundDispatcherProvider = provider3;
    }

    public static UserAwareSecureSettingsRepositoryImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new UserAwareSecureSettingsRepositoryImpl_Factory(provider, provider2, provider3);
    }

    public static UserAwareSecureSettingsRepositoryImpl newInstance(SecureSettings secureSettings, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher) {
        return new UserAwareSecureSettingsRepositoryImpl(secureSettings, userRepository, coroutineDispatcher);
    }

    @Override // javax.inject.Provider
    public UserAwareSecureSettingsRepositoryImpl get() {
        return newInstance((SecureSettings) this.secureSettingsProvider.get(), (UserRepository) this.userRepositoryProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}
