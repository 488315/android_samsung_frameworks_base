package com.android.systemui.util.settings;

import android.content.ContentResolver;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalSettingsImpl_Factory implements Provider {
    private final Provider contentResolverProvider;
    private final Provider settingsScopeProvider;

    public GlobalSettingsImpl_Factory(Provider provider, Provider provider2) {
        this.contentResolverProvider = provider;
        this.settingsScopeProvider = provider2;
    }

    public static GlobalSettingsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new GlobalSettingsImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static GlobalSettingsImpl newInstance(ContentResolver contentResolver, CoroutineScope coroutineScope) {
        return new GlobalSettingsImpl(contentResolver, coroutineScope);
    }

    public static GlobalSettingsImpl_Factory create(Provider provider, Provider provider2) {
        return new GlobalSettingsImpl_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public GlobalSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (CoroutineScope) this.settingsScopeProvider.get());
    }
}
