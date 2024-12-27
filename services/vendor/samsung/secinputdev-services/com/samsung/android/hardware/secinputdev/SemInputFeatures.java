package com.samsung.android.hardware.secinputdev;

import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public class SemInputFeatures {
    public static final boolean IS_FACTORY_BUILD = false;
    public static final boolean IS_SHIP_BUILD = true;
    public static final String LCD_CONFIG_HFR_MODE = "3";
    public static final boolean SUPPORT_DEX = true;
    public static final boolean USE_CMDTHREAD;
    public static final boolean SUPPORT_AMOLED_DISPLAY =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY");
    public static final boolean SUPPORT_RAWDATA_FEATURE =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_PROVIDE_TSP_RAWDATA");
    public static final boolean IS_PREMIUM_WATCH =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_CLOCK_SUPPORT_PREMIUM_WATCH");
    public static final boolean IS_JDM_PROJECT = "in_house".contains("jdm");
    public static final boolean IS_IN_HOUSE_PROJECT = "in_house".contains("in_house");
    public static final boolean IS_WEAROS = "phone".contains("watch");
    public static final boolean SUPPORT_TFLITE = "tflite:palmMute".contains("tflite");
    public static final boolean SUPPORT_TFLITE_GPU = "tflite:palmMute".contains("tflite-gpu");
    public static final boolean SUPPORT_PALMMUTE = "tflite:palmMute".contains("palmMute");
    public static final boolean SUPPORT_PALMSWIPE = "tflite:palmMute".contains("palmSwipe");
    public static final boolean SUPPORT_AIVF = "tflite:palmMute".contains("aivf");
    public static final boolean SUPPORT_APD = "tflite:palmMute".contains("pocketDetect");
    public static final boolean SUPPORT_AWD = "tflite:palmMute".contains("awd");

    static {
        USE_CMDTHREAD = SUPPORT_AMOLED_DISPLAY || IS_WEAROS;
    }
}
