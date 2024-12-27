package com.android.server.om.wallpapertheme;

import android.content.om.WallpaperThemeConstants;
import android.content.om.wallpapertheme.MetaDataManager;
import android.content.pm.overlay.OverlayPaths;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.Slog;

import com.android.server.om.OverlayPolicyManager;

import java.util.ArrayList;
import java.util.Iterator;

public final class SemWallpaperThemeOverlayPolicy
        implements OverlayPolicyManager.OverlayPackagePolicy {
    public final SemWallpaperThemeManager mWallpaperThemeManager;

    public SemWallpaperThemeOverlayPolicy(SemWallpaperThemeManager semWallpaperThemeManager) {
        this.mWallpaperThemeManager = semWallpaperThemeManager;
    }

    @Override // com.android.server.om.OverlayPolicyManager.OverlayPackagePolicy
    public final boolean retainOverlay(String str, OverlayPaths overlayPaths, String str2) {
        if (!str.startsWith("/data/resource-cache/android-SemWT")) {
            return true;
        }
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "for ", str2, " checking ", str, " with existing ");
        m.append(overlayPaths);
        Slog.e("SemWallpaperThemeOverlayPolicy", m.toString());
        if ("android".equals(str2)) {
            if (str.startsWith("/data/resource-cache/android-SemWT_G_MonetPalette")) {
                return false;
            }
        } else {
            if (WallpaperThemeConstants.colorThemingDisableList.contains(str2)) {
                return false;
            }
            SemWallpaperThemeManager semWallpaperThemeManager = this.mWallpaperThemeManager;
            semWallpaperThemeManager.getClass();
            ArrayList arrayList = new ArrayList();
            Iterator it = semWallpaperThemeManager.mMetaDataManager.getPackageList().iterator();
            while (it.hasNext()) {
                arrayList.add(((MetaDataManager.Package) it.next()).getPackageName());
            }
            if (arrayList.contains(str2)) {
                if (str.startsWith("/data/resource-cache/android-SemWT_G_MonetPalette")) {
                    return false;
                }
            } else if (!str.startsWith("/data/resource-cache/android-SemWT_G_MonetPalette")) {
                return false;
            }
        }
        return true;
    }
}
