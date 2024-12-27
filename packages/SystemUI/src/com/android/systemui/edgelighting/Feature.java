package com.android.systemui.edgelighting;

import android.os.Build;
import android.os.SemSystemProperties;
import android.text.TextUtils;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Feature {
    public static final boolean FEATURE_CONTEXTSERVICE_ENABLE_SURVEY;
    public static final boolean FEATURE_SUPPORT_AOD;
    public static final boolean FEATURE_SUPPORT_BASIC_LIGHTING;
    public static final boolean FEATURE_SUPPORT_COCKTAIL_COLOR_PHONE_COLOR;
    public static final boolean FEATURE_SUPPORT_EDGE_LIGHTING;
    public static final boolean FEATURE_SUPPORT_EDGE_LIGHTING_TILE;

    static {
        SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "");
        String str = SemSystemProperties.get("persist.omc.country_code");
        String str2 = SemSystemProperties.get("ro.csc.country_code");
        if (TextUtils.isEmpty(str2)) {
            str2 = SemSystemProperties.get("ril.sales_code");
        }
        if (str == null || "".equals(str)) {
            str = str2;
        }
        if (str != null) {
            str.equalsIgnoreCase("CHINA");
        }
        FEATURE_CONTEXTSERVICE_ENABLE_SURVEY = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        if (string != null) {
            string.contains("-edgefeeds");
        }
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BIXBY");
        boolean z = false;
        boolean z2 = Build.VERSION.SEM_PLATFORM_INT >= 120000;
        FEATURE_SUPPORT_EDGE_LIGHTING = z2;
        FEATURE_SUPPORT_EDGE_LIGHTING_TILE = z2;
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP");
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK");
        String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        if (string2 != null) {
            string2.contains("search");
        }
        FEATURE_SUPPORT_COCKTAIL_COLOR_PHONE_COLOR = true;
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ACCESSIBILITY_CONFLICT");
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK");
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        String string3 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_EDGELIGHTING_FRAME_EFFECT");
        if (string3 != null && string3.contains("frame_effect")) {
            z = true;
        }
        FEATURE_SUPPORT_BASIC_LIGHTING = z;
        String string4 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        if (string4 != null) {
            string4.contains("-edge_panel");
        }
        FEATURE_SUPPORT_AOD = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("aodversion");
    }

    public static boolean isEdgeLightingDefaultOff() {
        String string = SemCscFeature.getInstance().getString("CscFeature_Framework_ConfigDefStatusEdgeLighting");
        String string2 = SemCarrierFeature.getInstance().getString(0, "CarrierFeature_SystemUI_ConfigDefStatusEdgeLighting", "", false);
        return (string != null && string.contains("-defaulton")) || (string2 != null && string2.contains("-defaulton"));
    }
}
