package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.util.settings.SettingsProxy;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SystemSettingsImpl_Factory implements Provider {
    private final Provider contentResolverProvider;
    private final Provider currentUserProvider;
    private final Provider settingsScopeProvider;

    public SystemSettingsImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contentResolverProvider = provider;
        this.currentUserProvider = provider2;
        this.settingsScopeProvider = provider3;
    }

    public static SystemSettingsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SystemSettingsImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static SystemSettingsImpl newInstance(ContentResolver contentResolver, SettingsProxy.CurrentUserIdProvider currentUserIdProvider, CoroutineScope coroutineScope) {
        return new SystemSettingsImpl(contentResolver, currentUserIdProvider, coroutineScope);
    }

    public static SystemSettingsImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SystemSettingsImpl_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public SystemSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (SettingsProxy.CurrentUserIdProvider) this.currentUserProvider.get(), (CoroutineScope) this.settingsScopeProvider.get());
    }
}
