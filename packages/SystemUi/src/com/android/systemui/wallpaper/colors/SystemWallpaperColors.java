package com.android.systemui.wallpaper.colors;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.util.Log;
import android.util.SparseArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemWallpaperColors {
    public final SparseArray mSystemWallpaperColors = new SparseArray();
    public final WallpaperManager mWallpaperManager;

    public SystemWallpaperColors(WallpaperManager wallpaperManager) {
        this.mWallpaperManager = wallpaperManager;
    }

    public final SemWallpaperColors getColor(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("getColor: which = ", i, "SystemWallpaperColors");
        if ((i & 1) == 0) {
            return null;
        }
        if ((i & 60) == 0) {
            i |= 4;
        }
        SparseArray sparseArray = this.mSystemWallpaperColors;
        SemWallpaperColors semWallpaperColors = (SemWallpaperColors) sparseArray.get(i);
        if (semWallpaperColors != null) {
            return semWallpaperColors;
        }
        SemWallpaperColors semGetWallpaperColors = this.mWallpaperManager.semGetWallpaperColors(i);
        sparseArray.put(i, semGetWallpaperColors);
        Log.i("SystemWallpaperColors", "getColor : put color for which " + i + ", color = " + semGetWallpaperColors);
        return semGetWallpaperColors;
    }
}
