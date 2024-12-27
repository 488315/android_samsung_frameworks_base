package com.android.systemui.dagger;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class FrameworkServicesModule_ProvideMediaSessionManagerFactory implements Provider {
    public final javax.inject.Provider contextProvider;

    public FrameworkServicesModule_ProvideMediaSessionManagerFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static MediaSessionManager provideMediaSessionManager(Context context) {
        MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
        Preconditions.checkNotNullFromProvides(mediaSessionManager);
        return mediaSessionManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMediaSessionManager((Context) this.contextProvider.get());
    }
}
