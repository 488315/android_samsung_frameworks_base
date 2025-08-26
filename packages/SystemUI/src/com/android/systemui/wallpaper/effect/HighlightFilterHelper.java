package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda0;

/* loaded from: classes3.dex */
public class HighlightFilterHelper {
    public static Bitmap createFilteredBitmap(Bitmap bitmap, int i) {
        Log.d("HighlightFilterHelper", "createFilteredBitmap : filterAmount=" + i);
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        WallpaperFilter wallpaperFilter = new WallpaperFilter();
        wallpaperFilter.applyFilterOnMultiThread("highlight", new WallpaperFilter.ProcessingRange(bitmapCopy.getHeight()), new WallpaperFilter$$ExternalSyntheticLambda0(wallpaperFilter, bitmapCopy, i, 2));
        return bitmapCopy;
    }
}
