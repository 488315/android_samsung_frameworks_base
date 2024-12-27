package com.android.systemui.util.settings;

import android.content.ContentResolver;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlobalSettingsImpl_Factory implements Provider {
    private final javax.inject.Provider contentResolverProvider;

    public GlobalSettingsImpl_Factory(javax.inject.Provider provider) {
        this.contentResolverProvider = provider;
    }

    public static GlobalSettingsImpl_Factory create(javax.inject.Provider provider) {
        return new GlobalSettingsImpl_Factory(provider);
    }

    public static GlobalSettingsImpl newInstance(ContentResolver contentResolver) {
        return new GlobalSettingsImpl(contentResolver);
    }

    @Override // javax.inject.Provider
    public GlobalSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get());
    }
}
