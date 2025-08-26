package com.android.systemui.dagger;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class FrameworkServicesModule_ProvideMediaSessionManagerFactory implements Provider {
    public final Provider contextProvider;

    public FrameworkServicesModule_ProvideMediaSessionManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MediaSessionManager provideMediaSessionManager(Context context) {
        MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
        mediaSessionManager.getClass();
        return mediaSessionManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMediaSessionManager((Context) this.contextProvider.get());
    }
}
