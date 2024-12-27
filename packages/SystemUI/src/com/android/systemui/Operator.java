package com.android.systemui;

import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Operator {
    public static final boolean QUICK_IS_ACG_BRANDING;
    public static final boolean QUICK_IS_AIO_BRANDING;
    public static final boolean QUICK_IS_APP_BRANDING;
    public static final boolean QUICK_IS_ATT_BRANDING;
    public static final boolean QUICK_IS_BRI_BRANDING;
    public static final boolean QUICK_IS_BST_BRANDING;
    public static final boolean QUICK_IS_CCT_BRANDING;
    public static final boolean QUICK_IS_CHA_BRANDING;
    public static final boolean QUICK_IS_CHC_BRANDING;
    public static final boolean QUICK_IS_CHM_BRANDING;
    public static final boolean QUICK_IS_CHU_BRANDING;
    public static final boolean QUICK_IS_CSP_BRANDING;
    public static final boolean QUICK_IS_CTC_BRANDING;
    public static final boolean QUICK_IS_DCM_BRANDING;
    public static final boolean QUICK_IS_FKR_BRANDING;
    public static final boolean QUICK_IS_KDI_BRANDING;
    public static final boolean QUICK_IS_KOO_BRANDING;
    public static final boolean QUICK_IS_KTT_BRANDING;
    public static final boolean QUICK_IS_LDU_BRANDING;
    public static final boolean QUICK_IS_LGT_BRANDING;
    public static final boolean QUICK_IS_LRA_BRANDING;
    public static final boolean QUICK_IS_MTR_BRANDING;
    public static final boolean QUICK_IS_OJT_BRANDING;
    public static final boolean QUICK_IS_RKT_BRANDING;
    public static final boolean QUICK_IS_SBM_BRANDING;
    public static final boolean QUICK_IS_SKT_BRANDING;
    public static final boolean QUICK_IS_SPR_BRANDING;
    public static final boolean QUICK_IS_TFN_BRANDING;
    public static final boolean QUICK_IS_TFV_BRANDING;
    public static final boolean QUICK_IS_TGY_BRANDING;
    public static final boolean QUICK_IS_TMB_BRANDING;
    public static final boolean QUICK_IS_USC_BRANDING;
    public static final boolean QUICK_IS_VMU_BRANDING;
    public static final boolean QUICK_IS_VPP_BRANDING;
    public static final boolean QUICK_IS_VZW_BRANDING;
    public static final boolean QUICK_IS_XAA_BRANDING;
    public static final boolean QUICK_IS_XAR_BRANDING;
    public static final boolean QUICK_IS_XAS_BRANDING;
    public static final boolean QUICK_IS_XJP_BRANDING;
    public static final boolean QUICK_IS_XNX_BRANDING;
    public static final String smartManagerPackageName;

    static {
        String string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigQuickSettingPopup", "");
        QUICK_IS_VZW_BRANDING = "VZW".equals(string);
        QUICK_IS_ATT_BRANDING = "ATT".equals(string);
        QUICK_IS_AIO_BRANDING = "AIO".equals(string);
        QUICK_IS_APP_BRANDING = "APP".equals(string);
        QUICK_IS_TMB_BRANDING = "TMB".equals(string);
        QUICK_IS_MTR_BRANDING = "MTR".equals(string);
        QUICK_IS_SPR_BRANDING = "SPR".equals(string);
        QUICK_IS_VMU_BRANDING = "VMU".equals(string);
        QUICK_IS_BST_BRANDING = "BST".equals(string);
        QUICK_IS_XAS_BRANDING = "XAS".equals(string);
        QUICK_IS_USC_BRANDING = "USC".equals(string);
        QUICK_IS_LRA_BRANDING = "LRA".equals(string);
        QUICK_IS_TFN_BRANDING = "TFN".equals(string);
        QUICK_IS_CCT_BRANDING = "CCT".equals(string);
        QUICK_IS_CHA_BRANDING = "CHA".equals(string);
        QUICK_IS_ACG_BRANDING = "ACG".equals(string);
        QUICK_IS_CSP_BRANDING = "CSP".equals(string);
        QUICK_IS_XAR_BRANDING = "XAR".equals(string);
        QUICK_IS_XAA_BRANDING = "XAA".equals(string);
        QUICK_IS_XNX_BRANDING = "XNX".equals(string);
        QUICK_IS_TFV_BRANDING = "TFV".equals(string);
        QUICK_IS_FKR_BRANDING = "FKR".equals(string);
        QUICK_IS_VPP_BRANDING = "VPP".equals(string);
        QUICK_IS_OJT_BRANDING = "OJT".equals(string);
        QUICK_IS_LDU_BRANDING = "PAP".equals(string);
        QUICK_IS_CHM_BRANDING = "CHM".equals(string);
        QUICK_IS_CTC_BRANDING = "CTC".equals(string);
        QUICK_IS_CHC_BRANDING = "CHC".equals(string);
        QUICK_IS_CHU_BRANDING = "CHU".equals(string);
        QUICK_IS_BRI_BRANDING = "BRI".equals(string);
        QUICK_IS_TGY_BRANDING = "TGY".equals(string);
        QUICK_IS_SKT_BRANDING = "SKT".equals(string);
        QUICK_IS_KTT_BRANDING = "KTT".equals(string);
        QUICK_IS_LGT_BRANDING = "LGT".equals(string);
        QUICK_IS_KOO_BRANDING = "KOO".equals(string);
        QUICK_IS_DCM_BRANDING = "DCM".equals(string);
        QUICK_IS_KDI_BRANDING = "KDI".equals(string);
        QUICK_IS_SBM_BRANDING = "SBM".equals(string);
        QUICK_IS_XJP_BRANDING = "XJP".equals(string);
        QUICK_IS_RKT_BRANDING = "RKT".equals(string);
        smartManagerPackageName = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.sm");
        SemCscFeature.getInstance().getString("CscFeature_Common_ConfigPco", "");
    }

    public static int getMessageIdForMobileDataOnOffPopup(boolean z) {
        boolean z2 = QUICK_IS_LGT_BRANDING;
        return z ? z2 ? R.string.mobile_data_show_popup_disable_kor_lgt : QUICK_IS_KTT_BRANDING ? DeviceType.isMmsSupport() ? R.string.mobile_data_show_popup_disable_kor_kt_mms_available : R.string.mobile_data_show_popup_disable_kor_kt : R.string.mobile_data_show_popup_disable_kor : z2 ? R.string.mobile_data_show_popup_enable_kor_lgt : R.string.mobile_data_show_popup_enable_kor;
    }

    public static boolean isChinaQsTileBranding() {
        return QUICK_IS_CHM_BRANDING || QUICK_IS_CTC_BRANDING || QUICK_IS_CHC_BRANDING || QUICK_IS_CHU_BRANDING;
    }

    public static boolean isKoreaQsTileBranding() {
        return QUICK_IS_SKT_BRANDING || QUICK_IS_KTT_BRANDING || QUICK_IS_LGT_BRANDING || QUICK_IS_KOO_BRANDING;
    }

    public static boolean isUSAQsTileBranding() {
        return QUICK_IS_VZW_BRANDING || QUICK_IS_ATT_BRANDING || QUICK_IS_AIO_BRANDING || QUICK_IS_APP_BRANDING || QUICK_IS_TMB_BRANDING || QUICK_IS_MTR_BRANDING || QUICK_IS_SPR_BRANDING || QUICK_IS_VMU_BRANDING || QUICK_IS_BST_BRANDING || QUICK_IS_XAS_BRANDING || QUICK_IS_USC_BRANDING || QUICK_IS_LRA_BRANDING || QUICK_IS_TFN_BRANDING || QUICK_IS_CCT_BRANDING || QUICK_IS_CHA_BRANDING || QUICK_IS_ACG_BRANDING || QUICK_IS_CSP_BRANDING || QUICK_IS_XAR_BRANDING || QUICK_IS_XAA_BRANDING;
    }

    public static boolean shouldSupportMobileDataNotDisableVolteCall() {
        return QUICK_IS_VZW_BRANDING || QUICK_IS_VPP_BRANDING || QUICK_IS_CCT_BRANDING || QUICK_IS_CHA_BRANDING || QUICK_IS_TFV_BRANDING || QUICK_IS_FKR_BRANDING || QUICK_IS_TFN_BRANDING || QUICK_IS_ATT_BRANDING || QUICK_IS_AIO_BRANDING || QUICK_IS_APP_BRANDING;
    }
}
