package com.android.systemui.wallpaper.effect;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ColorDecorFilterHelper {
    public static Bitmap createFilteredBitmap(Bitmap bitmap, String str) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        try {
            new WallpaperFilter().applyFilter(copy, str);
            Log.d("ColorDecorFilterHelper", "createFilteredBitmap : elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime) + ", filter = " + str);
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("createFilteredBitmap: e=", e, "ColorDecorFilterHelper");
        }
        return copy;
    }

    public static String getFilterData(int i, Context context, int i2) {
        Bundle wallpaperExtras = WallpaperManager.getInstance(context).getWallpaperExtras(i, i2);
        if (wallpaperExtras == null) {
            return null;
        }
        return wallpaperExtras.getString("imageFilterParams");
    }
}
