package com.android.systemui.dagger;

import android.app.WallpaperManager;
import android.content.Context;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

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
