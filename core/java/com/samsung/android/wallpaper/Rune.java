package com.samsung.android.wallpaper;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;

/* loaded from: classes5.dex */
public class Rune {
  public static final boolean ADJUST_CROPHINT_BY_DEVICE_RATIO_AUTO = false;
  public static final boolean AOD_FULLSCREEN;
  public static final boolean BNR_DOWNLOADED_THEME_WALLPAPER = true;
  public static final boolean BNR_PREVENT_DIFFERENT_DEVICE_TYPES = true;
  public static final boolean BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE;
  private static final String CSC_FEATURE_KEY = "CscFeature_Common_ConfigDynamicLockScreenPolicy";
  private static final String CSC_FEATURE_VALUE_GOS = "GlanceOnSamsung";
  private static final String CSC_FEATURE_VALUE_LSM = "MagazineLockScreen";
  private static final String CSC_FEATURE_VALUE_LSS_31 = "LockScreenStories";
  private static final String CSC_FEATURE_VALUE_LSS_32 = "Global_LockScreen_Stories";
  private static final String CSC_FEATURE_VALUE_LSS_GENERIC = "Generic_LockScreen_Stories";
  public static final String CUSTOM_LOCKSCREEN_WALLPAPER_COLOR_REGION;
  public static final boolean DESKTOP_STANDALONE_MODE_WALLPAPER;
  private static final boolean IS_WINNER;
  public static final boolean SUPPORT_COVER_DISPLAY;
  public static final boolean SUPPORT_COVER_DISPLAY_WATCHFACE;
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
  public static final boolean SUPPORT_SUB_DISPLAY_MODE;
  public static final boolean SUPPORT_VIDEO_WALLPAPER;
  public static final boolean SUPPORT_WALLPAPER_CGROUP = true;
  public static final boolean SUPPORT_WALLPAPER_LEGIBILITY_COLORS = true;
  public static final boolean SUPPORT_WCG;
  public static final boolean VIRTUAL_DISPLAY_WALLPAPER;
  public static final boolean WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER;
  public static final boolean WPAPER_SUPPORT_ROTATABLE_WALLPAPER;
  private static String mDeviceType;

  static {
    boolean contains =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
            .contains("LID");
    SUPPORT_SUB_DISPLAY_MODE = contains;
    SUPPORT_COVER_DISPLAY =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
            .contains("COVER");
    boolean contains2 =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
            .contains("WATCHFACE");
    SUPPORT_COVER_DISPLAY_WATCHFACE = contains2;
    SUPPORT_VIDEO_WALLPAPER =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
            .contains("VIDEO");
    VIRTUAL_DISPLAY_WALLPAPER =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
            .contains("VIRTUAL_DISPLAY");
    WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER =
        !SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
            .contains("ROTATABLE");
    CUSTOM_LOCKSCREEN_WALLPAPER_COLOR_REGION =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_COLOR_REGION");
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
            .getString("CscFeature_LockScreen_ConfigRelpaceWallpaperCMF", (String) null);
    SUPPORT_WCG = Build.VERSION.SEM_PLATFORM_INT > 120000;
    BNR_SUPPORT_BETWEEN_FOLD_AND_PHONE =
        SystemProperties.getInt("ro.build.version.oneui", 0) > 50100;
    DESKTOP_STANDALONE_MODE_WALLPAPER =
        SystemProperties.getInt("ro.build.version.oneui", 0) < 50100;
    SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER =
        Build.VERSION.SEM_PLATFORM_INT >= 150100
            && !SystemProperties.getBoolean("persist.wm.debug.lockscreen_live_wallpaper", false);
    SUPPORT_LAYERED_WALLPAPER_SNAPSHOT = Build.VERSION.SEM_PLATFORM_INT >= 150100;
    boolean z =
        (Build.VERSION.SEM_PLATFORM_INT < 150500
                || supportLsm()
                || supportLss31()
                || supportLss32()
                || supportGos()
                || supportLssGeneric())
            ? false
            : true;
    SUPPORT_DLS_SNAPSHOT = z;
    SUPPORT_PAIRED_DLS_SNAPSHOT = z && contains && !contains2;
    mDeviceType = null;
    IS_WINNER =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY")
            .equals("LOCKSCREEN");
  }

  public static boolean isTablet() {
    String str = mDeviceType;
    if (str != null && str.length() > 0) {
      return mDeviceType.contains(BnRConstants.DEVICETYPE_TABLET);
    }
    String str2 = SystemProperties.get("ro.build.characteristics");
    mDeviceType = str2;
    return str2 != null && str2.contains(BnRConstants.DEVICETYPE_TABLET);
  }

  public static boolean isFolder() {
    return SemFloatingFeature.getInstance()
        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
  }

  public static boolean isWinner() {
    return IS_WINNER;
  }

  private static boolean supportLsm() {
    SemCscFeature feature = SemCscFeature.getInstance();
    if (feature != null) {
      return feature.getString(CSC_FEATURE_KEY).contains(CSC_FEATURE_VALUE_LSM);
    }
    return false;
  }

  private static boolean supportLss31() {
    SemCscFeature feature = SemCscFeature.getInstance();
    if (feature != null) {
      return feature.getString(CSC_FEATURE_KEY).equals(CSC_FEATURE_VALUE_LSS_31);
    }
    return false;
  }

  private static boolean supportLss32() {
    SemCscFeature feature = SemCscFeature.getInstance();
    if (feature != null) {
      return feature.getString(CSC_FEATURE_KEY).equals(CSC_FEATURE_VALUE_LSS_32);
    }
    return false;
  }

  private static boolean supportGos() {
    SemCscFeature feature = SemCscFeature.getInstance();
    if (feature != null) {
      return feature.getString(CSC_FEATURE_KEY).equals(CSC_FEATURE_VALUE_GOS);
    }
    return false;
  }

  private static boolean supportLssGeneric() {
    SemCscFeature feature = SemCscFeature.getInstance();
    if (feature != null) {
      return feature.getString(CSC_FEATURE_KEY).equals(CSC_FEATURE_VALUE_LSS_GENERIC);
    }
    return false;
  }
}
