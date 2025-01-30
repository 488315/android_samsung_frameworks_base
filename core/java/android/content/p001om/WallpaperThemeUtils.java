package android.content.p001om;

import android.content.Context;

/* loaded from: classes.dex */
public class WallpaperThemeUtils {
  private static final String TAG = "SWT_WallpaperThemeUtils";

  public static boolean hasWallpaperThemeOverlays(Context context) {
    if (context == null
        || context.getApplicationInfo() == null
        || context.getApplicationInfo().overlayPaths == null) {
      return false;
    }
    for (String str : context.getApplicationInfo().overlayPaths) {
      if (str != null) {
        if (str.startsWith(WallpaperThemeConstants.PATH_FOVERLAY_SEMWT_G)) {
          return false;
        }
        if (str.startsWith(WallpaperThemeConstants.PATH_FOVERLAY_SEMWT)) {
          return true;
        }
      }
    }
    return false;
  }
}
