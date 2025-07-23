package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ColorDecorFilterHelper {
    public static Bitmap createFilteredBitmap(String str, Bitmap bitmap) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        try {
            new WallpaperFilter().applyFilter(str, copy);
            Log.d("ColorDecorFilterHelper", "createFilteredBitmap : elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime) + ", filter = " + str);
            return copy;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("createFilteredBitmap: e=", e, "ColorDecorFilterHelper");
            return copy;
        }
    }
}
