package com.android.systemui.wallpaper.colors;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.util.Log;
import android.util.SparseArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SystemWallpaperColors {
    public final SparseArray mSystemWallpaperColors = new SparseArray();
    public final WallpaperManager mWallpaperManager;

    public SystemWallpaperColors(WallpaperManager wallpaperManager) {
        this.mWallpaperManager = wallpaperManager;
    }

    public final SemWallpaperColors getColor(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getColor: which = ", "SystemWallpaperColors");
        if ((i & 1) == 0) {
            return null;
        }
        if ((i & 60) == 0) {
            i |= 4;
        }
        SemWallpaperColors semWallpaperColors = (SemWallpaperColors) this.mSystemWallpaperColors.get(i);
        if (semWallpaperColors != null) {
            return semWallpaperColors;
        }
        SemWallpaperColors semGetWallpaperColors = this.mWallpaperManager.semGetWallpaperColors(i);
        this.mSystemWallpaperColors.put(i, semGetWallpaperColors);
        Log.i("SystemWallpaperColors", "getColor : put color for which " + i + ", color = " + semGetWallpaperColors);
        return semGetWallpaperColors;
    }
}
