package com.samsung.android.vibrator;

import android.p009os.Build;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes5.dex */
public class VibRune {
  public static final boolean IGNORE_BLOCKED_NOTIFICATION = true;
  public static final boolean SUPPORT_4D_VIBRATION = true;
  public static final boolean SUPPORT_AMPLITUDE_LIST_FROM_HAL = true;
  public static final boolean SUPPORT_CUSTOMIZATION = true;
  public static final boolean SUPPORT_CUSTOM_LOG = true;
  public static final boolean SUPPORT_CUSTOM_PATTERN;
  public static final boolean SUPPORT_FIXED_INTENSITY_LEVEL = true;
  public static final boolean SUPPORT_FIXUP_VIBRATION_USAGES = true;
  public static final boolean SUPPORT_FOLD_STATE = true;
  public static final boolean SUPPORT_GOOD_CATCH = true;
  public static final boolean SUPPORT_HAL_WRAPPER = true;
  public static final boolean SUPPORT_HQM_BIG_DATA = true;
  public static final boolean SUPPORT_INDIVIDUAL_SYSTEM_VIBRATION = true;
  public static final boolean SUPPORT_IS_VIBRATING = true;
  public static final boolean SUPPORT_KEEP_SCREEN_OFF = true;
  public static final boolean SUPPORT_LOW_POWER_MODE = true;
  public static final boolean SUPPORT_PREBAKED_PATTERN;
  public static final boolean SUPPORT_SEC_CONCEPT = true;
  public static final boolean SUPPORT_SEC_PLAY_PPRIORITY = true;
  public static final boolean SUPPORT_SEC_VIBRATION_PICKER;
  public static final boolean SUPPORT_SERVICE_RECOVERY = true;
  public static final boolean SUPPORT_SKIP_CANCEL_VIBRATION_WHEN_SCREEN_OFF = true;
  public static final boolean SUPPORT_VIBRATION_FROM_BACKGROUND_PROCESS = true;
  public static final boolean SUPPORT_VIBRATION_TAG = true;
  public static final boolean SUPPORT_VIRTUAL_VIBRATION_SOUND = false;
  public static final boolean TO_DO = false;
  private static boolean mIsRamIndexSupported = false;
  private static boolean mIsHybridHapticSupported = false;
  public static final boolean SUPPORT_ACH = "".contains("ACH");
  public static final boolean SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR =
      SemFloatingFeature.getInstance()
          .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_DC_MOTOR_HAPTIC_FEEDBACK");
  public static final boolean SUPPORT_ALWAYS_VIBRATE =
      "JP".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
  public static final String DND_EXCEPTION_PACKAGES =
      SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDndExceptionPackage", "");

  static {
    SUPPORT_SEC_VIBRATION_PICKER = Build.VERSION.SEM_PLATFORM_INT >= 120100;
    SUPPORT_CUSTOM_PATTERN = Build.VERSION.SEM_PLATFORM_INT >= 120100;
    SUPPORT_PREBAKED_PATTERN = Build.VERSION.SEM_PLATFORM_INT >= 120100;
  }

  public static void SET_RAM_INDEX_HAPTIC(boolean supportRamIndex) {
    mIsRamIndexSupported = supportRamIndex;
  }

  public static boolean SUPPORT_RAM_INDEX_HAPTIC() {
    return mIsRamIndexSupported;
  }

  public static void SET_HYBRID_HAPTIC(boolean supportHybridPattern) {
    mIsHybridHapticSupported = supportHybridPattern;
  }

  public static boolean SUPPORT_HYBRID_HAPTIC() {
    return mIsHybridHapticSupported;
  }
}
