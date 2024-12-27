package com.android.systemui.dagger;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
