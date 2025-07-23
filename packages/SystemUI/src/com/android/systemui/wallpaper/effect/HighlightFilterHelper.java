package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class HighlightFilterHelper {
    public static Bitmap createFilteredBitmap(Bitmap bitmap, int i) {
        Log.d("HighlightFilterHelper", "createFilteredBitmap : filterAmount=" + i);
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        WallpaperFilter wallpaperFilter = new WallpaperFilter();
        wallpaperFilter.applyFilterOnMultiThread("highlight", new WallpaperFilter.ProcessingRange(copy.getHeight()), new WallpaperFilter$$ExternalSyntheticLambda0(wallpaperFilter, copy, i, 2));
        return copy;
    }
}
