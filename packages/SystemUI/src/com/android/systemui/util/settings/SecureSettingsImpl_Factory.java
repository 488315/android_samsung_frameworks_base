package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;

public final class SecureSettingsImpl_Factory implements Provider {
    private final javax.inject.Provider contentResolverProvider;
    private final javax.inject.Provider userTrackerProvider;

    public SecureSettingsImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contentResolverProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static SecureSettingsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SecureSettingsImpl_Factory(provider, provider2);
    }

    public static SecureSettingsImpl newInstance(ContentResolver contentResolver, UserTracker userTracker) {
        return new SecureSettingsImpl(contentResolver, userTracker);
    }

    @Override // javax.inject.Provider
    public SecureSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
