package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.util.settings.SettingsProxy;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecureSettingsImpl_Factory implements Provider {
    private final Provider contentResolverProvider;
    private final Provider currentUserProvider;
    private final Provider settingsScopeProvider;

    public SecureSettingsImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contentResolverProvider = provider;
        this.currentUserProvider = provider2;
        this.settingsScopeProvider = provider3;
    }

    public static SecureSettingsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SecureSettingsImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static SecureSettingsImpl newInstance(ContentResolver contentResolver, SettingsProxy.CurrentUserIdProvider currentUserIdProvider, CoroutineScope coroutineScope) {
        return new SecureSettingsImpl(contentResolver, currentUserIdProvider, coroutineScope);
    }

    public static SecureSettingsImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SecureSettingsImpl_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public SecureSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (SettingsProxy.CurrentUserIdProvider) this.currentUserProvider.get(), (CoroutineScope) this.settingsScopeProvider.get());
    }
}
