package com.android.server.om.wallpapertheme;

import android.content.om.OverlayManagerTransaction;
import android.net.Uri;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes2.dex */
public interface ISemWallpaperThemeManager {
    void applyThemeParkWallpaperColor(Uri uri);

    void applyWallpaperColor(List list, List list2, boolean z);

    void applyWallpaperColors(List list, int i, int i2);

    void dump(PrintWriter printWriter);

    boolean getLastPalette(List list, List list2);

    List getWallpaperColors();

    void initWallpaperTheme();

    boolean isColoThemeApplied();

    boolean isRequestForColorTheme(OverlayManagerTransaction overlayManagerTransaction);

    void onPackageAdded(String str);

    void onUserAdded(int i);

    List readLastPalette();
}
