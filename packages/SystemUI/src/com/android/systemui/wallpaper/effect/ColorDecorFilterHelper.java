package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;

/* loaded from: classes3.dex */
public class ColorDecorFilterHelper {
    public static Bitmap createFilteredBitmap(String str, Bitmap bitmap) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        try {
            new WallpaperFilter().applyFilter(str, bitmapCopy);
            Log.d("ColorDecorFilterHelper", "createFilteredBitmap : elapsed=" + (SystemClock.elapsedRealtime() - jElapsedRealtime) + ", filter = " + str);
            return bitmapCopy;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("createFilteredBitmap: e=", e, "ColorDecorFilterHelper");
            return bitmapCopy;
        }
    }
}
