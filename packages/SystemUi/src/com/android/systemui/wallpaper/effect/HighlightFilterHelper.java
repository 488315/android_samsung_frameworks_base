package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter$$ExternalSyntheticLambda0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HighlightFilterHelper {
    public static Boolean canApplyFilterOnHome(int i) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Boolean bool = Boolean.FALSE;
        Log.i("HighlightFilterHelper", "canApplyFilterOnHome : elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime) + ", mode=" + i + ", result=" + bool + ", wait=false");
        return bool;
    }

    public static Bitmap createFilteredBitmap(Bitmap bitmap, int i) {
        Log.d("HighlightFilterHelper", "createFilteredBitmap : filterAmount=" + i);
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        WallpaperFilter wallpaperFilter = new WallpaperFilter();
        wallpaperFilter.applyFilterOnMultiThread("highlight", new WallpaperFilter.ProcessingRange(copy.getHeight()), new WallpaperFilter$$ExternalSyntheticLambda0(wallpaperFilter, copy, i, 2));
        return copy;
    }

    public static int getFilterAmount(SettingsHelper settingsHelper) {
        int intValue = settingsHelper.mItemLists.get("wallpaper_highlight_filter_amount").getIntValue();
        if (intValue < 0 || intValue > 100) {
            return 60;
        }
        return intValue;
    }
}
