package com.android.systemui.util;

import android.app.WallpaperManager;
import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WallpaperController_Factory implements Provider {
    private final javax.inject.Provider wallpaperManagerProvider;
    private final javax.inject.Provider wallpaperRepositoryProvider;

    public WallpaperController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.wallpaperManagerProvider = provider;
        this.wallpaperRepositoryProvider = provider2;
    }

    public static WallpaperController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new WallpaperController_Factory(provider, provider2);
    }

    public static WallpaperController newInstance(WallpaperManager wallpaperManager, WallpaperRepository wallpaperRepository) {
        return new WallpaperController(wallpaperManager, wallpaperRepository);
    }

    @Override // javax.inject.Provider
    public WallpaperController get() {
        return newInstance((WallpaperManager) this.wallpaperManagerProvider.get(), (WallpaperRepository) this.wallpaperRepositoryProvider.get());
    }
}
