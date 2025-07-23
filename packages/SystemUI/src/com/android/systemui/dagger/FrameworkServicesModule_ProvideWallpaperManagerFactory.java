package com.android.systemui.dagger;

import android.app.WallpaperManager;
import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FrameworkServicesModule_ProvideWallpaperManagerFactory implements Provider {
    public final Provider contextProvider;

    public FrameworkServicesModule_ProvideWallpaperManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static WallpaperManager provideWallpaperManager(Context context) {
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
        wallpaperManager.getClass();
        return wallpaperManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideWallpaperManager((Context) this.contextProvider.get());
    }
}
