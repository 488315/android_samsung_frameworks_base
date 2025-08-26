package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;

/* loaded from: classes3.dex */
public class WallpaperAnalytics {
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
            ClockEventController$$ExternalSyntheticOutline0.m(i, "getStatusId: mode is missing. which=", "WallpaperAnalytics");
        }
        int iOrdinal = statusField.ordinal();
        String str = iOrdinal != 0 ? iOrdinal != 1 ? null : WhichChecker.isFlagEnabled(i, 2) ? SystemUIAnalytics.STID_LOCK_WALLPAPER_FROM : SystemUIAnalytics.STID_HOME_WALLPAPER_FROM : WhichChecker.isFlagEnabled(i, 2) ? SystemUIAnalytics.STID_LOCK_WALLPAPER_TYPE : SystemUIAnalytics.STID_HOME_WALLPAPER_TYPE;
        return (str == null || !WhichChecker.isFlagEnabled(i, 16)) ? str : str.concat(SystemUIAnalytics.STID_WALLPAPER_POST_FIX_FOR_SUB);
    }

    public final boolean isSggApplied(int i) {
        Uri uriSemGetUri;
        int iLastIndexOf;
        if (this.mWallpaperManager.semGetWallpaperType(i) != 1000 || (uriSemGetUri = this.mWallpaperManager.semGetUri(i)) == null) {
            return false;
        }
        String string = uriSemGetUri.toString();
        if (TextUtils.isEmpty(string) || (iLastIndexOf = string.lastIndexOf("/")) < 0) {
            return false;
        }
        String strSubstring = string.substring(iLastIndexOf + 1);
        return !TextUtils.isEmpty(strSubstring) && strSubstring.contains("sgg");
    }

    public final void setWallpaperStatus(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.i("WallpaperAnalytics", "setWallpaperStatus: " + str + " = " + str2);
        this.mContext.getSharedPreferences(SystemUIAnalytics.WALLPAPER_PREF_NAME, 0).edit().putString(str, str2).apply();
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x015d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateWallpaperStatus(int i) {
        int i2;
        if ((i & 2) == 2 && (i & 1) == 1) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "updateWallpaperStatus : system&lock requested. which=", "WallpaperAnalytics");
            int i3 = i & 60;
            updateWallpaperStatus(i3 | 1);
            updateWallpaperStatus(i3 | 2);
            return;
        }
        if (WhichChecker.isFlagEnabled(i, 16) && WhichChecker.isFlagEnabled(i, 2)) {
            if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "updateWallpaperStatus : which = ", ". This model does not have Lockscreen wallpaper for sub display. Convering to main screen.", "WallpaperAnalytics");
            } else if (LsRune.WALLPAPER_SUB_WATCHFACE) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "updateWallpaperStatus : which = ", ". This model does not have Lockscreen wallpaper for cover display. Convering to main screen.", "WallpaperAnalytics");
            }
            i = 6;
        }
        if ((i & 60) == 0) {
            boolean z = false;
            if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && this.mWallpaperManager.getLidState() == 0) {
                z = true;
            }
            int i4 = i & 3;
            i = z ? i4 | 16 : i4 | 4;
        }
        int screenId = PluginWallpaperManager.getScreenId(i);
        boolean zIsFlagEnabled = WhichChecker.isFlagEnabled(i, 2);
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        if (!zIsFlagEnabled || pluginWallpaperManager == null || !pluginWallpaperManager.isDynamicWallpaperEnabled(screenId)) {
            int i5 = i & 60;
            i2 = this.mWallpaperManager.isSystemAndLockPaired(i5) ? i5 | 1 : i;
        }
        Log.i("WallpaperAnalytics", "updateWallpaperStatus : which=" + i + ", sourceWhich=" + i2);
        int screenId2 = PluginWallpaperManager.getScreenId(i2);
        boolean zIsFlagEnabled2 = WhichChecker.isFlagEnabled(i2, 2);
        String str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_MULTIPACK;
        if (!zIsFlagEnabled2 || pluginWallpaperManager == null || !pluginWallpaperManager.isDynamicWallpaperEnabled(screenId2)) {
            int iSemGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(i2);
            if (iSemGetWallpaperType == 0) {
                str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_IMAGE;
            } else if (iSemGetWallpaperType == 1) {
                str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_MOTION;
            } else if (iSemGetWallpaperType != 3) {
                if (iSemGetWallpaperType == 4) {
                    str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_ANIMATED;
                } else if (iSemGetWallpaperType == 5) {
                    str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_GIF;
                } else if (iSemGetWallpaperType == 7) {
                    str = this.mWallpaperManager.isStockLiveWallpaper(i2) ? SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_INTERNAL_LIVE : SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_3RD_PARTY_LIVE;
                } else if (iSemGetWallpaperType == 8) {
                    str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_VIDEO;
                } else if (iSemGetWallpaperType != 1000) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(iSemGetWallpaperType, "getWallpaperTypeString: Unknown wpType. type=", "WallpaperAnalytics");
                    str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_IMAGE;
                }
            }
        } else if (!isSggApplied(i2)) {
            if (pluginWallpaperManager.isCustomPackApplied(screenId2)) {
                str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_GALLERY_MULTIPACK;
            } else if (pluginWallpaperManager.isMultiPackApplied(screenId2)) {
                str = SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_THEME_MULTIPACK;
            }
        }
        setWallpaperStatus(getStatusId(i, StatusField.TYPE), str);
        int screenId3 = PluginWallpaperManager.getScreenId(i2);
        boolean zIsFlagEnabled3 = WhichChecker.isFlagEnabled(i2, 2);
        String imageCategory = SystemUIAnalytics.DT_WALLPAPER_SET_FROM_THEME;
        if (zIsFlagEnabled3 && pluginWallpaperManager != null && pluginWallpaperManager.isDynamicWallpaperEnabled(screenId3)) {
            boolean zIsCustomPackApplied = pluginWallpaperManager.isCustomPackApplied(screenId3);
            boolean zIsServiceWallpaperApplied = pluginWallpaperManager.isServiceWallpaperApplied(screenId3);
            boolean zIsMultiPackApplied = pluginWallpaperManager.isMultiPackApplied(screenId3);
            boolean zIsSggApplied = isSggApplied(i2);
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("getWallpaperSourceString: isCustomMultiPack = ", ", isSgg = ", ", isServiceWallpaper = ", zIsCustomPackApplied, zIsSggApplied);
            sbM.append(zIsServiceWallpaperApplied);
            sbM.append(", isThemeMultiPack = ");
            sbM.append(zIsMultiPackApplied);
            Log.i("WallpaperAnalytics", sbM.toString());
            if (zIsSggApplied) {
                imageCategory = SystemUIAnalytics.DT_WALLPAPER_SET_FROM_SGG;
            } else if (!zIsCustomPackApplied) {
                if (zIsServiceWallpaperApplied) {
                    imageCategory = SystemUIAnalytics.DT_WALLPAPER_SET_FROM_DLS;
                } else if (!zIsMultiPackApplied) {
                    imageCategory = SystemUIAnalytics.DT_WALLPAPER_SET_FROM_FEATURED;
                }
            }
        } else {
            Context context = this.mContext;
            SemWallpaperProperties semWallpaperProperties = new SemWallpaperProperties(context, i2, context.getUserId());
            String contentType = semWallpaperProperties.getContentType();
            boolean zIsFlagEnabled4 = WhichChecker.isFlagEnabled(i2, 16);
            int lockscreenWallpaperTransparent = WhichChecker.isFlagEnabled(i2, 2) ? this.mSettingsHelper.getLockscreenWallpaperTransparent(zIsFlagEnabled4) : this.mSettingsHelper.getHomescreenWallpaperSource(zIsFlagEnabled4);
            if (lockscreenWallpaperTransparent != 0) {
                if (lockscreenWallpaperTransparent != 1) {
                    if (lockscreenWallpaperTransparent != 2 && lockscreenWallpaperTransparent != 3) {
                    }
                } else if (!TextUtils.isEmpty(contentType)) {
                    if (!"prompt".equals(contentType)) {
                        imageCategory = "layered".equals(contentType) ? SystemUIAnalytics.DT_WALLPAPER_SET_FROM_CUSTOM : contentType;
                    }
                    imageCategory = "prompt_g";
                }
                imageCategory = SystemUIAnalytics.DT_WALLPAPER_SET_FROM_FEATURED;
            } else {
                if (TextUtils.isEmpty(contentType)) {
                    imageCategory = semWallpaperProperties.getImageCategory();
                    if (TextUtils.isEmpty(imageCategory)) {
                    }
                } else {
                    if (!"prompt".equals(contentType)) {
                        if ("layered".equals(contentType)) {
                        }
                    }
                    imageCategory = "prompt_g";
                }
            }
        }
        setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
    }
}
