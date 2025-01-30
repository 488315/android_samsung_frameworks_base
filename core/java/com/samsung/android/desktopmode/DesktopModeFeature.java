package com.samsung.android.desktopmode;

import android.content.Context;
import android.content.res.Resources;
import android.media.audio.common.AudioDeviceDescription;
import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes5.dex */
public class DesktopModeFeature {
  public static final boolean DEBUG;
  public static final boolean ENABLED = true;
  public static final boolean FEATURE_AUTO_OPEN_LAST_APP = true;
  public static final boolean FEATURE_COVERS = true;
  public static final boolean FEATURE_OFFICIAL_ADAPTERS = true;
  public static final boolean FEATURE_SPEN = true;
  public static final boolean FEATURE_STANDALONE_MODE_WALLPAPER;
  public static final boolean FEATURE_TOUCHPAD = true;
  public static final boolean FEATURE_UNOFFICIAL_ADAPTERS = true;
  public static final boolean FOLDABLE_TYPE_FOLD;
  public static final boolean IS_FOLDABLE = false;
  public static final boolean IS_TABLET;
  public static final boolean SPEN_INBOX_MODEL;
  private static final int SPEN_USP_LEVEL;
  public static final Set<String> SUPPORTED_MODES;
  public static final boolean SUPPORT_DEX_ON_PC;
  public static final boolean SUPPORT_DUAL;
  public static final boolean SUPPORT_NEW_DEX;
  public static final boolean SUPPORT_SFC;
  public static final boolean SUPPORT_SPEN;
  public static final boolean SUPPORT_STANDALONE;
  public static final boolean SUPPORT_UIBC_EXTENSION_MOUSE_ICON_SYNC;
  public static final boolean SUPPORT_WIRELESS_DEX;

  static {
    boolean z = false;
    DEBUG = Debug.semIsProductDev() || Build.IS_DEBUGGABLE || Log.isLoggable("DMS", 3);
    FEATURE_STANDALONE_MODE_WALLPAPER = Build.VERSION.SEM_PLATFORM_INT < 140100;
    IS_TABLET =
        SystemProperties.get("ro.build.characteristics").contains(BnRConstants.DEVICETYPE_TABLET)
            || isDebuggableAndSysPropSet(BnRConstants.DEVICETYPE_TABLET);
    Set<String> unmodifiableSet =
        Collections.unmodifiableSet(
            new ArraySet(
                Arrays.asList(
                    SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE")
                        .split(","))));
    SUPPORTED_MODES = unmodifiableSet;
    SUPPORT_DUAL = unmodifiableSet.contains("dual") || isDebuggableAndSysPropSet("dual");
    SUPPORT_DEX_ON_PC = unmodifiableSet.contains("dexforpc") || isDebuggableAndSysPropSet("dop");
    SUPPORT_STANDALONE =
        unmodifiableSet.contains("standalone") || isDebuggableAndSysPropSet("standalone");
    boolean z2 =
        unmodifiableSet.contains(AudioDeviceDescription.CONNECTION_WIRELESS)
            || isDebuggableAndSysPropSet(AudioDeviceDescription.CONNECTION_WIRELESS);
    SUPPORT_WIRELESS_DEX = z2;
    SUPPORT_NEW_DEX = unmodifiableSet.contains("newdex") || isDebuggableAndSysPropSet("newdex");
    SUPPORT_UIBC_EXTENSION_MOUSE_ICON_SYNC = z2;
    int i =
        SemFloatingFeature.getInstance()
            .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION");
    SPEN_USP_LEVEL = i;
    boolean z3 = i > 0;
    SUPPORT_SPEN = z3;
    if (z3 && i % 10 == 5) {
      z = true;
    }
    SPEN_INBOX_MODEL = z;
    SUPPORT_SFC =
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PD_HV");
    FOLDABLE_TYPE_FOLD =
        SemFloatingFeature.getInstance()
            .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
  }

  private static boolean isDebuggableAndSysPropSet(String key) {
    return Build.IS_DEBUGGABLE
        && SystemProperties.getBoolean(
            new StringBuilder().append("persist.service.dex.").append(key).toString(), false);
  }

  public static boolean isDesktopMode(Context context) {
    return isDesktopMode(context.getResources());
  }

  public static boolean isDesktopMode(Resources resources) {
    return resources.getConfiguration().dexMode == 1 || resources.getConfiguration().dexMode == 2;
  }
}
