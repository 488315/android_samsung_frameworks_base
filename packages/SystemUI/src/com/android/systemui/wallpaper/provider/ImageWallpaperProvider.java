package com.android.systemui.wallpaper.provider;

import com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProvider;

public class ImageWallpaperProvider extends LiveWallpaperProvider {
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProvider
    public final ProviderCallDispatcher getCallDispatcher() {
        return new ProviderCallDispatcher();
    }
}
