package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class HighlightFilterHelper {
    public static Bitmap createFilteredBitmap(Bitmap bitmap, int i) {
        Log.d("HighlightFilterHelper", "createFilteredBitmap : filterAmount=" + i);
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        WallpaperFilter wallpaperFilter = new WallpaperFilter();
        wallpaperFilter.applyFilterOnMultiThread("highlight", new WallpaperFilter.ProcessingRange(copy.getHeight()), new WallpaperFilter$$ExternalSyntheticLambda0(wallpaperFilter, copy, i, 2));
        return copy;
    }
}
