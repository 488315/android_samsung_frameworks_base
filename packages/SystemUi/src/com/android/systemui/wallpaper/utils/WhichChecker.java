package com.android.systemui.wallpaper.utils;

import android.app.WallpaperManager;
import android.content.Context;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WhichChecker {
    public static int convertDisplayIdToMode(int i, Context context) {
        if (i == 0) {
            if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) {
                return 4;
            }
            return WallpaperUtils.getFolderStateBasedWhich(0, context);
        }
        if (i == 1) {
            return 16;
        }
        if (i == 2) {
            return 8;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            WallpaperManager.getInstance(context);
            if (WallpaperManager.isVirtualWallpaperDisplay(context, i)) {
                return 32;
            }
        }
        NestedScrollView$$ExternalSyntheticOutline0.m34m("convertDisplayIdToMode: unexpected display id. id=", i, "WhichChecker");
        return -1;
    }

    public static boolean isFlagEnabled(int i, int i2) {
        return (i & i2) == i2;
    }

    public static boolean isSubDisplay(int i) {
        return isFlagEnabled(i, 16);
    }

    public static boolean isWatchFace(int i) {
        return LsRune.WALLPAPER_SUB_WATCHFACE && isFlagEnabled(i, 1) && isFlagEnabled(i, 16);
    }
}
