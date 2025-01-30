package com.android.server.biometrics;

import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public abstract class SemBiometricFeature {
  public static final boolean FACE_FEATURE_LANDSCAPE_MODE;
  public static final boolean FEATURE_FINGERPRINT_JDM_HAL;
  public static final boolean FEATURE_INTEGRATED_LOCKOUT;
  public static final boolean FEATURE_JDM_HAL;
  public static final boolean FEATURE_LOGGING_MODE =
      SemFloatingFeature.getInstance()
          .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
  public static final boolean FEATURE_SUPPORT_AOD;
  public static final boolean FEATURE_SUPPORT_DESKTOP_MODE;
  public static final boolean FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP;
  public static final boolean FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD;
  public static final boolean FEATURE_SUPPORT_SPEN;
  public static final boolean FP_FEATURE_EARLY_WAKE_UP;
  public static final boolean FP_FEATURE_FAKE_AOD;
  public static final boolean FP_FEATURE_GESTURE_MODE;
  public static final boolean FP_FEATURE_HW_LIGHT_SOURCE;
  public static final boolean FP_FEATURE_LOCAL_HBM;
  public static final boolean FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
  public static final boolean FP_FEATURE_SENSOR_IS_OPTICAL;
  public static final boolean FP_FEATURE_SENSOR_IS_SIDE;
  public static final boolean FP_FEATURE_SENSOR_IS_ULTRASONIC;
  public static final boolean FP_FEATURE_SENSOR_LIMITATION_SPEN_CHARGER;
  public static final boolean FP_FEATURE_SENSOR_LIMITATION_WIRELESS_CHARGER;
  public static final boolean FP_FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS;
  public static final boolean FP_FEATURE_SUPPORT_FINGERPRINT;
  public static final boolean FP_FEATURE_SUPPORT_GESTURE_CALIBRATION;
  public static final boolean FP_FEATURE_SUPPORT_JDM_LOCAL_HBM;
  public static final boolean FP_FEATURE_SUPPORT_LOCAL_HBM;
  public static final boolean FP_FEATURE_SWIPE_ENROLL;
  public static final boolean FP_FEATURE_TSP_BLOCK;
  public static final boolean FP_FEATURE_USE_AOSP_HAL;
  public static final boolean FP_FEATURE_WOF_OPTION_DEFAULT_OFF;

  static {
    boolean contains =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DEVICE_MANUFACTURING_TYPE")
            .contains("jdm");
    FEATURE_JDM_HAL = contains;
    FEATURE_SUPPORT_DESKTOP_MODE =
        SemFloatingFeature.getInstance()
            .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP");
    FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP =
        SemFloatingFeature.getInstance()
            .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
    FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD =
        SemFloatingFeature.getInstance()
            .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    boolean contains2 =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
            .contains("aodversion");
    FEATURE_SUPPORT_AOD = contains2;
    boolean z =
        SemFloatingFeature.getInstance()
                .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
            > 0;
    FEATURE_SUPPORT_SPEN = z;
    FP_FEATURE_SUPPORT_FINGERPRINT = true;
    boolean z2 = contains;
    FEATURE_FINGERPRINT_JDM_HAL = z2;
    FP_FEATURE_USE_AOSP_HAL = z2;
    FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE = true;
    FP_FEATURE_SENSOR_IS_OPTICAL = true;
    FP_FEATURE_SENSOR_IS_ULTRASONIC =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_BIOAUTH_CONFIG_FINGERPRINT_FEATURES")
            .contains("ultrasonic");
    FP_FEATURE_SENSOR_IS_SIDE =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_BIOAUTH_CONFIG_FINGERPRINT_FEATURES")
            .contains("side");
    FP_FEATURE_GESTURE_MODE = false;
    FP_FEATURE_SUPPORT_GESTURE_CALIBRATION = false;
    FP_FEATURE_SWIPE_ENROLL = false;
    FP_FEATURE_EARLY_WAKE_UP =
        SemFloatingFeature.getInstance()
            .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY");
    FP_FEATURE_HW_LIGHT_SOURCE =
        SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_SELFMASK_VERSION")
            >= 1;
    FP_FEATURE_SENSOR_LIMITATION_WIRELESS_CHARGER = false;
    FP_FEATURE_SENSOR_LIMITATION_SPEN_CHARGER = false;
    FP_FEATURE_FAKE_AOD = !contains2;
    FP_FEATURE_TSP_BLOCK = z;
    FP_FEATURE_WOF_OPTION_DEFAULT_OFF = false;
    FP_FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS =
        SystemProperties.getInt("vendor.display.enable_brightness_drm_prop", 0) != 0;
    boolean z3 =
        SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_LOCAL_HBM") == 1;
    FP_FEATURE_SUPPORT_LOCAL_HBM = z3;
    FP_FEATURE_SUPPORT_JDM_LOCAL_HBM = false;
    FP_FEATURE_LOCAL_HBM = z3;
    FACE_FEATURE_LANDSCAPE_MODE = true;
    FEATURE_INTEGRATED_LOCKOUT = true;
  }
}
