package com.samsung.android.wallpaper.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;
import com.samsung.android.wallpaper.Rune;

/* loaded from: classes5.dex */
public class WhichChecker {
  private static final String TAG = "WhichChecker";

  public static int getType(int which) {
    return which & 3;
  }

  public static int getMode(int which) {
    return which & 60;
  }

  public static boolean isSingleType(int which) {
    int type = getType(which);
    return type == 1 || type == 2;
  }

  public static boolean isModeAbsent(int which) {
    return getMode(which) == 0;
  }

  public static boolean isPhone(int which) {
    int mode = getMode(which);
    return mode == 0 || (mode & 4) == 4;
  }

  public static boolean isDex(int which) {
    return (which & 8) == 8;
  }

  public static boolean isSubDisplay(int which) {
    return Rune.SUPPORT_SUB_DISPLAY_MODE && (which & 16) == 16;
  }

  public static boolean isVirtualDisplay(int which) {
    return Rune.VIRTUAL_DISPLAY_WALLPAPER && (which & 32) == 32;
  }

  public static boolean isWatchFaceDisplay(int which) {
    return !isLock(which) && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && isSubDisplay(which);
  }

  public static boolean isSystem(int which) {
    return (which & 3) == 1;
  }

  public static boolean isLock(int which) {
    return (which & 3) == 2;
  }

  public static boolean isSystemAndLock(int which) {
    return (which & 3) == 3;
  }

  public static boolean containsLock(int which) {
    return (which & 2) == 2;
  }

  public static boolean containsSystem(int which) {
    return (which & 1) == 1;
  }

  public static boolean isSupportLock(int which) {
    return ((Rune.SUPPORT_SUB_DISPLAY_MODE
                && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                && isSubDisplay(which))
            || (Rune.VIRTUAL_DISPLAY_WALLPAPER && isVirtualDisplay(which)))
        ? false
        : true;
  }

  public static int determineMode(boolean isLidClosed) {
    if (isLidClosed) {
      return 16;
    }
    return 4;
  }

  public static int getCurrentImplicitMode(Context context) {
    boolean isLidClosed = false;
    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
      isLidClosed = WallpaperManager.getInstance(context).getLidState() == 0;
    }
    return determineMode(isLidClosed);
  }

  public static void assertModeIsPresent(int which) {
    if (getMode(which) == 0) {
      Log.m97e(TAG, "assertModeIsPresent: mode is not present. which = " + which, new Exception());
    }
  }
}
