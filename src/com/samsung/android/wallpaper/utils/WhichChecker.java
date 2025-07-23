package com.samsung.android.wallpaper.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;
import com.samsung.android.wallpaper.Rune;

/* loaded from: classes6.dex */
public class WhichChecker {
    private static final String TAG = "WhichChecker";

    public static boolean containsLock(int i) {
        return (i & 2) == 2;
    }

    public static boolean containsSystem(int i) {
        return (i & 1) == 1;
    }

    public static int determineMode(boolean z) {
        return z ? 16 : 4;
    }

    public static int getMode(int i) {
        return i & 60;
    }

    public static int getType(int i) {
        return i & 3;
    }

    public static boolean isLock(int i) {
        return (i & 3) == 2;
    }

    public static boolean isSystem(int i) {
        return (i & 3) == 1;
    }

    public static boolean isSystemAndLock(int i) {
        return (i & 3) == 3;
    }

    public static boolean isSingleType(int i) {
        int type = getType(i);
        return type == 1 || type == 2;
    }

    public static boolean isModeAbsent(int i) {
        return getMode(i) == 0;
    }

    public static boolean isPhone(int i) {
        int mode = getMode(i);
        return mode == 0 || (mode & 4) == 4;
    }

    public static boolean isDex(int i) {
        return Rune.SUPPORT_DESKTOP_MODE && (i & 8) == 8;
    }

    public static boolean isSubDisplay(int i) {
        return Rune.SUPPORT_SUB_DISPLAY_MODE && (i & 16) == 16;
    }

    public static boolean isVirtualDisplay(int i) {
        return Rune.VIRTUAL_DISPLAY_WALLPAPER && (i & 32) == 32;
    }

    public static boolean isWatchFaceDisplay(int i) {
        return !isLock(i) && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && isSubDisplay(i);
    }

    public static boolean isSupportLock(int i) {
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && isSubDisplay(i)) {
            return false;
        }
        return (Rune.VIRTUAL_DISPLAY_WALLPAPER && isVirtualDisplay(i)) ? false : true;
    }

    public static int getCurrentImplicitMode(Context context) {
        boolean z = false;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WallpaperManager.getInstance(context).getLidState() == 0) {
            z = true;
        }
        return determineMode(z);
    }

    public static void assertModeIsPresent(int i) {
        if (getMode(i) == 0) {
            Log.e(TAG, "assertModeIsPresent: mode is not present. which = " + i, new Exception());
        }
    }

    public static int getSourceWhich(int i) {
        return isSystemAndLock(i) ? getMode(i) | 1 : i;
    }
}
