package com.android.systemui.util;

import android.app.WallpaperManager;
import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperController_Factory implements Provider {
    private final Provider wallpaperManagerProvider;
    private final Provider wallpaperRepositoryProvider;

    public WallpaperController_Factory(Provider provider, Provider provider2) {
        this.wallpaperManagerProvider = provider;
        this.wallpaperRepositoryProvider = provider2;
    }

    public static WallpaperController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new WallpaperController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static WallpaperController newInstance(WallpaperManager wallpaperManager, WallpaperRepository wallpaperRepository) {
        return new WallpaperController(wallpaperManager, wallpaperRepository);
    }

    public static WallpaperController_Factory create(Provider provider, Provider provider2) {
        return new WallpaperController_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public WallpaperController get() {
        return newInstance((WallpaperManager) this.wallpaperManagerProvider.get(), (WallpaperRepository) this.wallpaperRepositoryProvider.get());
    }
}
