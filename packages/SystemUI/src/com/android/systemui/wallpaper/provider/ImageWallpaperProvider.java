package com.android.systemui.wallpaper.provider;

import com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ImageWallpaperProvider extends LiveWallpaperProvider {
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProvider
    public final ProviderCallDispatcher getCallDispatcher() {
        return new ProviderCallDispatcher();
    }
}
