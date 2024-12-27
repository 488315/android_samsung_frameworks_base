package com.android.server.aod;

import android.os.FactoryTest;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

public abstract class AODConfig {
    public static final boolean SUPPORT_FRONT_SUB_DISPLAY_WATCHFACE;
    public static final boolean SUPPORT_SUB_DISPLAY_COVER;
    public static final boolean SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_AOD_DOZE_SERVICE =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "")
                    .contains("aodversion");
    public static final String AOD_MODE_DEFAULT_VALUE =
            SemCscFeature.getInstance().getString("CscFeature_AOD_ConfigDefStatus", "ON");
    public static final boolean isFactoryBinary = FactoryTest.isFactoryBinary();
    public static final boolean isAODTouchDisabled =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                    .contains("-touch");
    public static final boolean isTapToShowDisabled =
            SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                    .contains("notaptoshow");

    static {
        boolean contains =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "")
                        .contains("COVER");
        SUPPORT_SUB_DISPLAY_COVER = contains;
        SUPPORT_FRONT_SUB_DISPLAY_WATCHFACE =
                contains
                        && SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY",
                                        "")
                                .contains("WATCHFACE");
    }
}
