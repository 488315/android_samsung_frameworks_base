package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
