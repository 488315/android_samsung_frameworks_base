package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.utils.WhichChecker;

public final class WallpaperAnalytics {
    public final Context mContext;
    public final PluginWallpaperManager mPluginWallpaperManager;
    private SettingsHelper mSettingsHelper;
    public final WallpaperManager mWallpaperManager;

    enum StatusField {
        TYPE,
        FROM
    }

    public WallpaperAnalytics(Context context, PluginWallpaperManager pluginWallpaperManager, SettingsHelper settingsHelper) {
        this.mContext = context;
        this.mWallpaperManager = WallpaperManager.getInstance(context);
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mSettingsHelper = settingsHelper;
    }

    public static String getStatusId(int i, StatusField statusField) {
        if ((i & 60) == 0) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "getStatusId: mode is missing. which=", "WallpaperAnalytics");
        }
        int ordinal = statusField.ordinal();
        String str = ordinal != 0 ? ordinal != 1 ? null : WhichChecker.isFlagEnabled(i, 2) ? SystemUIAnalytics.STID_LOCK_WALLPAPER_FROM : SystemUIAnalytics.STID_HOME_WALLPAPER_FROM : WhichChecker.isFlagEnabled(i, 2) ? SystemUIAnalytics.STID_LOCK_WALLPAPER_TYPE : SystemUIAnalytics.STID_HOME_WALLPAPER_TYPE;
        return (str == null || !WhichChecker.isFlagEnabled(i, 16)) ? str : str.concat(SystemUIAnalytics.STID_WALLPAPER_POST_FIX_FOR_SUB);
    }

    public final boolean isSggApplied(int i) {
        Uri semGetUri;
        int lastIndexOf;
        if (this.mWallpaperManager.semGetWallpaperType(i) != 1000 || (semGetUri = this.mWallpaperManager.semGetUri(i)) == null) {
            return false;
        }
        String uri = semGetUri.toString();
        if (TextUtils.isEmpty(uri) || (lastIndexOf = uri.lastIndexOf("/")) < 0) {
            return false;
        }
        String substring = uri.substring(lastIndexOf + 1);
        return !TextUtils.isEmpty(substring) && substring.contains("sgg");
    }

    public final void setWallpaperStatus(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.i("WallpaperAnalytics", "setWallpaperStatus: " + str + " = " + str2);
        this.mContext.getSharedPreferences(SystemUIAnalytics.WALLPAPER_PREF_NAME, 0).edit().putString(str, str2).apply();
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0166, code lost:
    
        if (r2 != false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0197, code lost:
    
        if (r0 != 3) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x019e, code lost:
    
        if (android.text.TextUtils.isEmpty(r5) == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01a0, code lost:
    
        r8 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01bb, code lost:
    
        if ("layered".equals(r5) != false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01c6, code lost:
    
        if (android.text.TextUtils.isEmpty(r8) == false) goto L115;
     */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWallpaperStatus(int r13) {
        /*
            Method dump skipped, instructions count: 466
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.WallpaperAnalytics.updateWallpaperStatus(int):void");
    }
}
