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

    public static boolean hasWallpaperThemeNotSupport(ApplicationInfo appInfo) {
        return (appInfo == null || appInfo.metaData == null || !WallpaperThemeConstants.WALLPAPERTHEME_NOT_SUPPORT.equals(appInfo.metaData.getString(WallpaperThemeConstants.THEMING_META))) ? false : true;
    }

    public static boolean hasWallpaperThemeMeta(ApplicationInfo appInfo) {
        return (appInfo == null || appInfo.metaData == null || !appInfo.metaData.containsKey(WallpaperThemeConstants.THEMING_META)) ? false : true;
    }

    public static boolean hasWallpaperThemeTemplate(ApplicationInfo appInfo) {
        return (appInfo == null || appInfo.metaData == null || !appInfo.metaData.containsKey(WallpaperThemeConstants.THEMING_TEMPLATE)) ? false : true;
    }

    public static boolean hasWallpaperThemeOverlays(Context context) {
        if (context == null || context.getApplicationInfo() == null || context.getApplicationInfo().overlayPaths == null) {
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

    public static Resources getPackageResources(ApplicationInfo appInfo) {
        AssetManager assets = new AssetManager();
        assets.setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        int cookie = assets.addAssetPath(appInfo.getBaseCodePath());
        if (cookie == 0) {
            Log.e(TAG, "Failed to parse " + appInfo.getBaseCodePath());
            return null;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        metrics.setToDefaults();
        return new Resources(assets, metrics, null);
    }
}
