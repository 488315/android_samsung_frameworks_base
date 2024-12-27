package com.android.systemui.wallpaper.engines.theme;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperSource;
import com.android.systemui.wallpaper.theme.MotionWallpaper;

public final class MotionSource implements WallpaperSource {
    public final String TAG;
    public final MotionWallpaper mRootView;

    public MotionSource(int i, MotionWallpaper motionWallpaper) {
        this.TAG = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[MotionSource]");
        this.mRootView = motionWallpaper;
    }
}
