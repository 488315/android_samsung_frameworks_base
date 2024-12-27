package com.android.systemui.wallpaper.effect;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
