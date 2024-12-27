package com.android.systemui.dagger;

import android.app.WallpaperManager;
import android.content.Context;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FrameworkServicesModule_ProvideWallpaperManagerFactory implements Provider {
    public final javax.inject.Provider contextProvider;

    public FrameworkServicesModule_ProvideWallpaperManagerFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static WallpaperManager provideWallpaperManager(Context context) {
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
        Preconditions.checkNotNullFromProvides(wallpaperManager);
        return wallpaperManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideWallpaperManager((Context) this.contextProvider.get());
    }
}
