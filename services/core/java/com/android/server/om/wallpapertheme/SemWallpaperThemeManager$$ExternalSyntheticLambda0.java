package com.android.server.om.wallpapertheme;

import android.util.Log;

public final /* synthetic */ class SemWallpaperThemeManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemWallpaperThemeManager f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SemWallpaperThemeManager$$ExternalSyntheticLambda0(
            SemWallpaperThemeManager semWallpaperThemeManager) {
        this.$r8$classId = 0;
        this.f$0 = semWallpaperThemeManager;
        this.f$1 = 0;
    }

    public /* synthetic */ SemWallpaperThemeManager$$ExternalSyntheticLambda0(
            SemWallpaperThemeManager semWallpaperThemeManager, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = semWallpaperThemeManager;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.saveThemeParkSingleThemeState();
                Log.i("SWT_ThemeManager", "[ThemePark FRRO] saveThemeParkSingleThemeState done");
                break;
            case 1:
                this.f$0.saveWallpaperThemeState(this.f$1);
                Log.i("SWT_ThemeManager", "saveWallpaperThemeState done");
                break;
            default:
                this.f$0.syncWallpaperThemeStateForUser(this.f$1);
                Log.i("SWT_ThemeManager", "syncWallpaperThemeStateForUser done");
                break;
        }
    }
}
