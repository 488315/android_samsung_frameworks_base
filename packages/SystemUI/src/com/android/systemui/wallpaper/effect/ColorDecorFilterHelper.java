package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;

public final class ColorDecorFilterHelper {
    public static Bitmap createFilteredBitmap(String str, Bitmap bitmap) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        try {
            new WallpaperFilter().applyFilter(str, copy);
            Log.d("ColorDecorFilterHelper", "createFilteredBitmap : elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime) + ", filter = " + str);
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("createFilteredBitmap: e=", e, "ColorDecorFilterHelper");
        }
        return copy;
    }
}
