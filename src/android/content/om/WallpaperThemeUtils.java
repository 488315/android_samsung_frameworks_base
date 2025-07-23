package android.content.om;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

/* loaded from: classes.dex */
public class WallpaperThemeUtils {
    private static final String TAG = "SWT_WallpaperThemeUtils";

    public static boolean hasWallpaperThemeNotSupport(ApplicationInfo applicationInfo) {
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return false;
        }
        return WallpaperThemeConstants.WALLPAPERTHEME_NOT_SUPPORT.equals(applicationInfo.metaData.get(WallpaperThemeConstants.THEMING_META));
    }

    public static boolean hasWallpaperThemeMeta(ApplicationInfo applicationInfo) {
        return (applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(WallpaperThemeConstants.THEMING_META)) ? false : true;
    }

    public static boolean hasWallpaperThemeTemplate(ApplicationInfo applicationInfo) {
        return (applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(WallpaperThemeConstants.THEMING_TEMPLATE)) ? false : true;
    }

    public static boolean hasWallpaperThemeOverlays(Context context) {
        if (context != null && context.getApplicationInfo() != null && context.getApplicationInfo().overlayPaths != null) {
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
        }
        return false;
    }

    public static Resources getPackageResources(ApplicationInfo applicationInfo) {
        AssetManager assetManager = new AssetManager();
        assetManager.setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        if (assetManager.addAssetPath(applicationInfo.getBaseCodePath()) == 0) {
            Log.e(TAG, "Failed to parse " + applicationInfo.getBaseCodePath());
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        return new Resources(assetManager, displayMetrics, null);
    }
}
