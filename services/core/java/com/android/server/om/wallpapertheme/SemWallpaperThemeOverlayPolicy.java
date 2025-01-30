package com.android.server.om.wallpapertheme;

import android.content.om.WallpaperThemeConstants;
import android.content.pm.overlay.OverlayPaths;
import android.util.Slog;
import com.android.server.om.OverlayPolicyManager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SemWallpaperThemeOverlayPolicy implements OverlayPolicyManager.OverlayPackagePolicy {
    public static final String TAG = "SemWallpaperThemeOverlayPolicy";
    public final SemWallpaperThemeManager mWallpaperThemeManager;

    public SemWallpaperThemeOverlayPolicy(SemWallpaperThemeManager semWallpaperThemeManager) {
        this.mWallpaperThemeManager = semWallpaperThemeManager;
    }

    @Override // com.android.server.om.OverlayPolicyManager.OverlayPackagePolicy
    public boolean retainOverlay(String str, OverlayPaths overlayPaths, String str2, int i) {
        if (!isSemWallpaperThemeOverlay(str)) {
            return true;
        }
        Slog.e(TAG, "for " + str2 + " checking " + str + " with existing " + overlayPaths);
        if ("android".equals(str2)) {
            if (isGoogleWallpaperThemeOverlay(str)) {
                return false;
            }
        } else {
            if (WallpaperThemeConstants.colorThemingDisableList.contains(str2)) {
                return false;
            }
            ArrayList packageNameList = this.mWallpaperThemeManager.getPackageNameList();
            if (packageNameList == null) {
                return true;
            }
            if (packageNameList.contains(str2)) {
                if (isGoogleWallpaperThemeOverlay(str)) {
                    return false;
                }
            } else if (!isGoogleWallpaperThemeOverlay(str)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isSemWallpaperThemeOverlay(String str) {
        return str.startsWith("/data/resource-cache/android-SemWT");
    }

    public final boolean isGoogleWallpaperThemeOverlay(String str) {
        return str.startsWith("/data/resource-cache/android-SemWT_G_MonetPalette");
    }
}
