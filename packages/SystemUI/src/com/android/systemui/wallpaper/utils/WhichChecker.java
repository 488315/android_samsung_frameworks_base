package com.android.systemui.wallpaper.utils;

import android.app.WallpaperManager;
import android.content.Context;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WhichChecker {
    public static int convertDisplayIdToMode(int i, Context context) {
        if (i == 0) {
            boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
            if (!z || LsRune.WALLPAPER_SUB_WATCHFACE) {
                return 4;
            }
            boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            if (z) {
                return wallpaperManager.getLidState() == 0 ? 16 : 4;
            }
            return 0;
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
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "convertDisplayIdToMode: unexpected display id. id=", "WhichChecker");
        return -1;
    }

    public static int getSourceWhich(int i) {
        return isSystemAndLock(i) ? (i & 60) | 1 : i;
    }

    public static boolean isFlagEnabled(int i, int i2) {
        return (i & i2) == i2;
    }

    public static boolean isSystemAndLock(int i) {
        return isFlagEnabled(i, 1) && isFlagEnabled(i, 2);
    }

    public static boolean isVirtualDisplay(int i) {
        return LsRune.COVER_VIRTUAL_DISPLAY && (i & 32) == 32;
    }

    public static boolean isWatchFace(int i) {
        return LsRune.WALLPAPER_SUB_WATCHFACE && isFlagEnabled(i, 1) && isFlagEnabled(i, 16);
    }
}
