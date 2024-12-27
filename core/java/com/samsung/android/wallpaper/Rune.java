package com.samsung.android.wallpaper;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;

public class Rune {
    public static final boolean ADJUST_CROPHINT_BY_DEVICE_RATIO_AUTO = false;
    public static final boolean AOD_FULLSCREEN;
    public static final boolean BNR_DOWNLOADED_THEME_WALLPAPER = true;
    public static final boolean BNR_PREVENT_DIFFERENT_DEVICE_TYPES = true;
    public static final boolean BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE;
    public static final boolean DESKTOP_STANDALONE_MODE_WALLPAPER;
    private static final boolean IS_WINNER;
    public static final String SUPPORT_CSC_REPLACE_WALLPAPER_CMF;
    public static final boolean SUPPORT_DESKTOP_MODE = true;
    public static final boolean SUPPORT_DLS_SNAPSHOT;
    public static final boolean SUPPORT_GOOGLE_ORIG = false;
    public static final boolean SUPPORT_HOME_CONTROLLER;
    public static final boolean SUPPORT_LARGE_FRONT_SUB_DISPLAY;
    public static final boolean SUPPORT_LAYERED_WALLPAPER_SNAPSHOT;
    public static final boolean SUPPORT_LIVE_WALLPAPER_PREVIEW = true;
    public static final boolean SUPPORT_PAIRED_DLS_SNAPSHOT;
    public static final boolean SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER;
    public static final boolean SUPPORT_RESTORE_CUSTOM_MULTIPACK = true;
    public static final boolean SUPPORT_SAMSUNG_COMMON = true;
    public static final boolean SUPPORT_WALLPAPER_CGROUP = true;
    public static final boolean SUPPORT_WALLPAPER_LEGIBILITY_COLORS = true;
    public static final boolean SUPPORT_WCG;
    public static final boolean WPAPER_SUPPORT_ROTATABLE_WALLPAPER;
    private static String mDeviceType;
    public static final boolean SUPPORT_SUB_DISPLAY_MODE =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
                    .contains("LID");
    public static final boolean SUPPORT_COVER_DISPLAY =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                    .contains("COVER");
    public static final boolean SUPPORT_COVER_DISPLAY_WATCHFACE =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                    .contains("WATCHFACE");
    public static final boolean SUPPORT_VIDEO_WALLPAPER =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
                    .contains("VIDEO");
    public static final boolean VIRTUAL_DISPLAY_WALLPAPER =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                    .contains("VIRTUAL_DISPLAY");
    public static final boolean WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER =
            !SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
                    .contains("ROTATABLE");
    public static final String CUSTOM_LOCKSCREEN_WALLPAPER_COLOR_REGION =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_COLOR_REGION");

    static {
        WPAPER_SUPPORT_ROTATABLE_WALLPAPER =
                SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
                                .contains("ROTATABLE")
                        || isTablet();
        SUPPORT_HOME_CONTROLLER =
                !TextUtils.isEmpty(
                        SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB"));
        SUPPORT_LARGE_FRONT_SUB_DISPLAY =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                        .contains("LARGESCREEN");
        AOD_FULLSCREEN =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1)
                        == 1;
        SUPPORT_CSC_REPLACE_WALLPAPER_CMF =
                SemCscFeature.getInstance()
                        .getString("CscFeature_LockScreen_ConfigRelpaceWallpaperCMF", "");
        SUPPORT_WCG = Build.VERSION.SEM_PLATFORM_INT > 120000;
        BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE =
                SystemProperties.getInt("ro.build.version.oneui", 0) > 50100;
        DESKTOP_STANDALONE_MODE_WALLPAPER =
                SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
        SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER =
                Build.VERSION.SEM_PLATFORM_INT >= 150100
                        && Build.VERSION.SEM_PLATFORM_INT < 160000
                        && !SystemProperties.getBoolean(
                                "persist.wm.debug.lockscreen_live_wallpaper", false);
        SUPPORT_LAYERED_WALLPAPER_SNAPSHOT = Build.VERSION.SEM_PLATFORM_INT >= 150100;
        SUPPORT_DLS_SNAPSHOT = Build.VERSION.SEM_PLATFORM_INT >= 150500;
        SUPPORT_PAIRED_DLS_SNAPSHOT =
                SUPPORT_DLS_SNAPSHOT
                        && SUPPORT_SUB_DISPLAY_MODE
                        && !SUPPORT_COVER_DISPLAY_WATCHFACE;
        mDeviceType = null;
        IS_WINNER =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
                        .equals("LOCKSCREEN");
    }

    public static boolean isTablet() {
        if (mDeviceType != null && mDeviceType.length() > 0) {
            return mDeviceType.contains(BnRConstants.DEVICETYPE_TABLET);
        }
        mDeviceType = SystemProperties.get("ro.build.characteristics");
        return mDeviceType != null && mDeviceType.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    public static boolean isFolder() {
        return SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    }

    public static boolean isWinner() {
        return IS_WINNER;
    }
}
