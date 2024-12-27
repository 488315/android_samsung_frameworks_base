package com.android.systemui.wallpaper.engines.theme;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperSource;
import com.android.systemui.wallpaper.theme.MotionWallpaper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MotionSource implements WallpaperSource {
    public final String TAG;
    public final MotionWallpaper mRootView;

    public MotionSource(int i, MotionWallpaper motionWallpaper) {
        this.TAG = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[MotionSource]");
        this.mRootView = motionWallpaper;
    }
}
