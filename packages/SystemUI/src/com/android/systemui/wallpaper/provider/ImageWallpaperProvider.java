package com.android.systemui.wallpaper.provider;

import com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ImageWallpaperProvider extends LiveWallpaperProvider {
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProvider
    public final ProviderCallDispatcher getCallDispatcher() {
        return new ProviderCallDispatcher();
    }
}
