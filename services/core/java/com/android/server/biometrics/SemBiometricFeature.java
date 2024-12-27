package com.android.server.biometrics;

import android.os.SystemProperties;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SemBiometricFeature {
    public static final boolean FEATURE_FINGERPRINT_JDM_HAL;
    public static final boolean FEATURE_INTEGRATED_LOCKOUT;
    public static final boolean FEATURE_JDM_HAL;
    public static final boolean FEATURE_LOGGING_MODE =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public static final boolean FEATURE_SUPPORT_AOD;
    public static final boolean FEATURE_SUPPORT_DESKTOP_MODE = false;
    public static final boolean FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP;
    public static final boolean FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD;
    public static final boolean FP_FEATURE_EARLY_WAKE_UP;
    public static final boolean FP_FEATURE_HW_LIGHT_SOURCE;
    public static final boolean FP_FEATURE_LOCAL_HBM;
    public static final boolean FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
    public static final boolean FP_FEATURE_SENSOR_IS_OPTICAL;
    public static final boolean FP_FEATURE_SENSOR_IS_SIDE;
    public static final boolean FP_FEATURE_SENSOR_IS_ULTRASONIC;
    public static final boolean FP_FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS;
    public static final boolean FP_FEATURE_SUPPORT_FINGERPRINT;
    public static final boolean FP_FEATURE_SUPPORT_LOCAL_HBM_IN_HOUSE;
    public static final boolean FP_FEATURE_TSP_BLOCK;
    public static final boolean FP_FEATURE_USE_AOSP_HAL;

    static {
        boolean contains =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DEVICE_MANUFACTURING_TYPE")
                        .contains("jdm");
        FEATURE_JDM_HAL = contains;
        SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP");
        FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        FEATURE_SUPPORT_AOD =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                        .contains("aodversion");
        boolean z =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
                        > 0;
        FP_FEATURE_SUPPORT_FINGERPRINT = true;
        FEATURE_FINGERPRINT_JDM_HAL = contains;
        FP_FEATURE_USE_AOSP_HAL = contains;
        FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE = true;
        FP_FEATURE_SENSOR_IS_OPTICAL =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_BIOAUTH_CONFIG_FINGERPRINT_FEATURES")
                        .contains("optical");
        FP_FEATURE_SENSOR_IS_ULTRASONIC = true;
        FP_FEATURE_SENSOR_IS_SIDE =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_BIOAUTH_CONFIG_FINGERPRINT_FEATURES")
                        .contains("side");
        FP_FEATURE_EARLY_WAKE_UP =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY");
        FP_FEATURE_HW_LIGHT_SOURCE =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_SELFMASK_VERSION")
                        >= 1;
        FP_FEATURE_TSP_BLOCK = z;
        FP_FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS =
                SystemProperties.getInt("vendor.display.enable_brightness_drm_prop", 0) != 0;
        boolean z2 =
                SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_LOCAL_HBM")
                        == 1;
        FP_FEATURE_SUPPORT_LOCAL_HBM_IN_HOUSE = z2;
        FP_FEATURE_LOCAL_HBM = z2;
        FEATURE_INTEGRATED_LOCKOUT = true;
    }
}
