package com.android.systemui.dagger;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
