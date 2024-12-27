package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;

public final class SystemSettingsImpl_Factory implements Provider {
    private final javax.inject.Provider contentResolverProvider;
    private final javax.inject.Provider userTrackerProvider;

    public SystemSettingsImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contentResolverProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static SystemSettingsImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SystemSettingsImpl_Factory(provider, provider2);
    }

    public static SystemSettingsImpl newInstance(ContentResolver contentResolver, UserTracker userTracker) {
        return new SystemSettingsImpl(contentResolver, userTracker);
    }

    @Override // javax.inject.Provider
    public SystemSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
