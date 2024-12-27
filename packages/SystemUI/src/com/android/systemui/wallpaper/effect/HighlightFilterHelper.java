package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda0;

public final class HighlightFilterHelper {
    public static Bitmap createFilteredBitmap(Bitmap bitmap, int i) {
        Log.d("HighlightFilterHelper", "createFilteredBitmap : filterAmount=" + i);
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        WallpaperFilter wallpaperFilter = new WallpaperFilter();
        wallpaperFilter.applyFilterOnMultiThread("highlight", new WallpaperFilter.ProcessingRange(copy.getHeight()), new WallpaperFilter$$ExternalSyntheticLambda0(wallpaperFilter, copy, i, 2));
        return copy;
    }
}
