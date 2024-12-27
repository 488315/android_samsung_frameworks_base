package com.android.systemui;

import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CscRune extends Rune {
    public static String VALUE_CONFIG_CARRIER_TEXT_POLICY = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigCarrierTextPolicy");
    public static String VALUE_CONFIG_CARRIER_SECURITY_POLICY = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigCarrierSecurityPolicy");
    public static String VALUE_CONFIG_CARRIER_EMERGENCY_POLICY = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigEmergencyCallPolicy");
    public static boolean KEYGUARD_DCM_LIVE_UX = VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseDCMSimLockText");
    public static boolean SECURITY_EMERGENCY_BUTTON_KOR = isDisplayUsimText();
    public static boolean SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE = VALUE_CONFIG_CARRIER_EMERGENCY_POLICY.contains("DisableEmergencyCallWhenOffline");
    public static boolean SECURITY_DIRECT_CALL_TO_ECC = VALUE_CONFIG_CARRIER_EMERGENCY_POLICY.contains("DirectCall");
    public static boolean SECURITY_KOR_USIM_TEXT = isDisplayUsimText();
    public static boolean SECURITY_SKT_USIM_TEXT = VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseSKTSimText");
    public static boolean SECURITY_KTT_USIM_TEXT = VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseKTTSimText");
    public static boolean SECURITY_LGU_USIM_TEXT = VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseLGTSimText");
    public static boolean SECURITY_USE_CDMA_CARD_TEXT = VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseCdmaCardText");
    public static boolean SECURITY_SIM_PERM_DISABLED = VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("SupportSimPermanentDisable");
    public static boolean SECURITY_WARNING_WIPE_OUT_MESSAGE = VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("FactoryResetProtectionWarning");
    public static boolean SECURITY_VZW_INSTRUCTION = VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("FactoryResetProtectionWarning");
    public static boolean LOCKUI_LGU_USIM_TEXT = VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("UseLGTSimText");
    public static boolean LOCKUI_BOTTOM_USIM_TEXT = isDisplayUsimText();
    public static boolean LOCKUI_HELP_TEXT_FOR_CHN = VALUE_CONFIG_CARRIER_SECURITY_POLICY.contains("UseSamsungAccountAuth");

    public static boolean isDisplayUsimText() {
        return VALUE_CONFIG_CARRIER_TEXT_POLICY.contains("DisplayUsimText");
    }
}
