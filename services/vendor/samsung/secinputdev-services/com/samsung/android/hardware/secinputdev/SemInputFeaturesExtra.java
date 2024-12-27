package com.samsung.android.hardware.secinputdev;

import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public class SemInputFeatures {
    public static final boolean IS_FACTORY_BUILD = false;
    public static final boolean IS_JDM_PROJECT;
    public static final boolean IS_PREMIUM_WATCH;
    public static final boolean IS_SHIP_BUILD = true;
    public static final boolean IS_WEAROS;
    public static final String LCD_CONFIG_HFR_MODE = "2";
    public static final boolean SUPPORT_AIVF;
    public static final boolean SUPPORT_AMOLED_DISPLAY;
    public static final boolean SUPPORT_APD;
    public static final boolean SUPPORT_AWD;
    public static final boolean SUPPORT_DEX = true;
    public static final boolean SUPPORT_PALMMUTE;
    public static final boolean SUPPORT_PALMSWIPE;
    public static final boolean SUPPORT_RAWDATA_FEATURE;
    public static final boolean SUPPORT_TFLITE;
    public static final boolean SUPPORT_TFLITE_GPU;
    public static final boolean USE_CMDTHREAD;

    static {
        boolean z =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY");
        SUPPORT_AMOLED_DISPLAY = z;
        SUPPORT_RAWDATA_FEATURE =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_PROVIDE_TSP_RAWDATA");
        IS_PREMIUM_WATCH =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_CLOCK_SUPPORT_PREMIUM_WATCH");
        IS_JDM_PROJECT = "in_house".contains("jdm");
        boolean contains = "tablet".contains("watch");
        IS_WEAROS = contains;
        SUPPORT_TFLITE = "default".contains("tflite");
        SUPPORT_TFLITE_GPU = "default".contains("tflite-gpu");
        SUPPORT_PALMMUTE = "default".contains("palmMute");
        SUPPORT_PALMSWIPE = "default".contains("palmSwipe");
        SUPPORT_AIVF = "default".contains("aivf");
        SUPPORT_APD = "default".contains("pocketDetect");
        SUPPORT_AWD = "default".contains("awd");
        USE_CMDTHREAD = z || contains;
    }
}
