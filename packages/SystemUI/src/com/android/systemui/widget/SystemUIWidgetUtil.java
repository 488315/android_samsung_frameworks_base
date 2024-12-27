package com.android.systemui.widget;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;

public final class SystemUIWidgetUtil {
    public static long convertFlag(String str) {
        if (str == null) {
            return 512L;
        }
        switch (str) {
            case "statusbar":
                return 16L;
            case "bottom":
                return 128L;
            case "background":
                return 512L;
            case "mid":
                return 64L;
            case "top":
                return 32L;
            case "none":
                return 0L;
            case "navibar":
                return 256L;
            default:
                return -1L;
        }
    }

    public static boolean needsBlackComponent(Context context, long j, boolean z) {
        return (z || !WallpaperUtils.isOpenThemeLockWallpaper()) ? WallpaperUtils.isWhiteKeyguardWallpaper(j) : context.getResources().getBoolean(R.bool.theme_use_clock_dark_as_default);
    }

    public static void registerSystemUIWidgetCallback(SystemUIWidgetCallback systemUIWidgetCallback, long j) {
        if (j == 0) {
            return;
        }
        if (j != -1) {
            j |= 1;
        }
        if ((32 & j) != 0) {
            j |= 2;
        }
        if (WallpaperEventNotifier.getInstance() != null) {
            WallpaperEventNotifier.getInstance().registerCallback(false, systemUIWidgetCallback, j);
        }
    }
}
