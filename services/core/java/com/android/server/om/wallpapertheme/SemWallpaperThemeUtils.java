package com.android.server.om.wallpapertheme;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes2.dex */
public abstract class SemWallpaperThemeUtils {
  public static boolean hasWallpaperThemeNotSupport(AndroidPackage androidPackage) {
    return (androidPackage == null
            || androidPackage.getMetaData() == null
            || !"NOT_SUPPORT_COLORTHEME"
                .equals(androidPackage.getMetaData().getString("theming-meta")))
        ? false
        : true;
  }

  public static boolean hasWallpaperThemeMeta(AndroidPackage androidPackage) {
    return (androidPackage == null
            || androidPackage.getMetaData() == null
            || !androidPackage.getMetaData().containsKey("theming-meta"))
        ? false
        : true;
  }

  public static boolean hasWallpaperThemeTemplate(AndroidPackage androidPackage) {
    return (androidPackage == null
            || androidPackage.getMetaData() == null
            || !androidPackage.getMetaData().containsKey("theming-templates"))
        ? false
        : true;
  }

  public static Resources getPackageResources(AndroidPackage androidPackage) {
    AssetManager assetManager = new AssetManager();
    assetManager.setConfiguration(
        0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
    if (assetManager.addAssetPath(androidPackage.getBaseApkPath()) == 0) {
      Log.e("SWT_Utils", "Failed to parse " + androidPackage.getBaseApkPath());
      return null;
    }
    DisplayMetrics displayMetrics = new DisplayMetrics();
    displayMetrics.setToDefaults();
    return new Resources(assetManager, displayMetrics, null);
  }

  public static boolean hasColorThemeOverlay(String[] strArr) {
    if (strArr != null) {
      for (String str : strArr) {
        if (str != null && isColorThemeOverlay(str)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isColorThemeOverlay(String str) {
    return str != null && str.startsWith("/data/resource-cache/android-SemWT");
  }
}
