package com.android.internal.telephony;

import android.app.blob.XmlTags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.telephony.Rlog;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;
import com.samsung.telephony.sysprop.SemTelephonyProps;

import java.util.List;

public class TelephonyFeatures {
    public static final int DUALSIM_WITH_ONE_SLOT_NR = 1;
    public static final int DUALSIM_WITH_TWO_SLOT_NR = 2;
    public static final String HARDWARE_TYPE;
    public static final boolean IS_EXYNOS;
    public static final boolean IS_FACTORY_BIN;
    public static final boolean IS_MTK;
    public static final boolean IS_PHONE;
    public static final boolean IS_QCOM;
    public static final boolean IS_WIFI_ONLY;
    private static final String LOG_TAG = "TelephonyFeatures";
    public static final int NO_DUALSIM_NR_MODEL = 0;
    public static final int NTC_FEATURE_ALLOW_HANGUP_WHEN_DIALING = 5;
    public static final int NTC_FEATURE_BLOCK_NETMODE_CHANGE_WITH_CARRIER_CONFIG_CHANGED = 14;
    public static final int NTC_FEATURE_CHECK_OPPOSITE_SLOT_NETMODE_BEFORE_CHANGE = 15;
    public static final int NTC_FEATURE_CHECK_VOLTE_SUBSCRIBER_FOR_CALL_WAITING = 27;
    public static final int NTC_FEATURE_CSC_SPRINT_CHAMELEON = 2;
    public static final int NTC_FEATURE_DEFAULT_NETWORK_TYPE_2G_REASON_DISABLE = 28;
    public static final int NTC_FEATURE_DISPLAY_RESCAN_DIALOG = 12;
    public static final int NTC_FEATURE_DISPLAY_TOAST_AFTER_RTT_E911_FAILED = 11;
    public static final int NTC_FEATURE_ERI_ON_AP = 4;
    public static final int NTC_FEATURE_ERI_ON_CP = 3;
    public static final int NTC_FEATURE_FAKE_RADIO_ON_USING_EXTENDED_PARAMETER = 23;
    public static final int NTC_FEATURE_FAKE_RADIO_ON_USING_PROPERTY = 22;
    public static final int NTC_FEATURE_FORCELY_SET_2G_ENABLED = 20;
    public static final int NTC_FEATURE_FORCELY_SET_3G_4G_ENABLED = 21;
    public static final int NTC_FEATURE_IS_3G_NOT_ALLOWED_OPERATOR = 16;
    public static final int NTC_FEATURE_MAX = 29;
    public static final int NTC_FEATURE_NEED_FORCE_NETWORK_MODE = 24;
    public static final int NTC_FEATURE_RESUME_HELD_CALL_AFTER_MO_FAIL = 18;
    public static final int NTC_FEATURE_RESUME_HELD_CALL_IF_BG_ONLY = 19;
    public static final int NTC_FEATURE_RETRY_EMERGENCY_SEARCH_IN_ALERTING = 17;
    public static final int NTC_FEATURE_SET_CLIR_TO_BOTH_SIDES = 13;
    public static final int NTC_FEATURE_SHOW_VOICE_AS_DATA_NETWORK_TYPE = 25;
    public static final int NTC_FEATURE_SPR_US_INTERNATIONAL_DIALING = 1;
    public static final int NTC_FEATURE_SUPPORT_IMSCALL_ECBM = 8;
    public static final int NTC_FEATURE_SUPPORT_IMSCALL_ONLY = 7;
    public static final int NTC_FEATURE_SUPPORT_WAKELOCK_LOGGING = 26;
    public static final int NTC_FEATURE_UPDATE_ADDRESS_FOR_CALL_CONTROL = 10;
    public static final int NTC_FEATURE_UPDATE_NETWORK_LIST_WITH_EONS = 6;
    public static final int NTC_FEATURE_USE_SECOND_TTY_MODE_IN_DUAL_SIM = 9;
    public static final int PRIMARY_PHONE_ID = 0;
    public static final String RIL_FEATURES = "onebinary entitlement_sa";
    public static final int SECONDARY_PHONE_ID = 1;
    private static boolean sSimHotswapSupported;
    private static String[] sSimbasedChangeType;
    public static final boolean SHIP_BUILD = SystemProperties.getBoolean("ro.product_ship", true);
    private static final String SALES_CODE =
            SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE);
    public static final String DEVICE_TYPE = SystemProperties.get("ro.build.characteristics", "");
    public static final boolean IS_TABLET = DEVICE_TYPE.contains(BnRConstants.DEVICETYPE_TABLET);
    public static final boolean IS_WATCH = DEVICE_TYPE.contains("watch");

    static {
        IS_PHONE = (IS_TABLET || IS_WATCH) ? false : true;
        HARDWARE_TYPE = SystemProperties.get("ro.boot.hardware", "");
        IS_QCOM = HARDWARE_TYPE.contains("qcom");
        IS_EXYNOS = HARDWARE_TYPE.contains("exynos") || HARDWARE_TYPE.contains("s5e");
        IS_MTK = HARDWARE_TYPE.contains("mt");
        IS_WIFI_ONLY = "wifi-only".equals(SemTelephonyProps.carrier().orElse("unknown"));
        IS_FACTORY_BIN =
                "factory"
                        .equalsIgnoreCase(
                                SystemProperties.get("ro.factory.factory_binary", "Unknown"));
        sSimHotswapSupported = true;
        sSimbasedChangeType = null;
    }

    private static void InitializeSimbasedType() {
        String changeType =
                SystemProperties.get("ro.simbased.changetype", KeyProperties.DIGEST_NONE);
        sSimbasedChangeType = changeType.split(",");
        if (sSimbasedChangeType == null || sSimbasedChangeType.length < 2) {
            sSimbasedChangeType = new String[2];
            sSimbasedChangeType[0] = KeyProperties.DIGEST_NONE;
            sSimbasedChangeType[1] = "DISABLED";
        } else {
            sSimbasedChangeType[0] = sSimbasedChangeType[0].trim();
            sSimbasedChangeType[1] = sSimbasedChangeType[1].trim();
        }
    }

    public static String getMainOperatorName(int phoneId) {
        String ntcRawData =
                SemCarrierFeature.getInstance()
                        .getString(
                                phoneId,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                "",
                                true);
        return SemTelephonyUtils.getMainOperator(ntcRawData);
    }

    public static String getSubOperatorName(int phoneId) {
        String ntcRawData =
                SemCarrierFeature.getInstance()
                        .getString(
                                phoneId,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                "",
                                true);
        return SemTelephonyUtils.getSubOperator(ntcRawData);
    }

    public static String getOperatorType(int phoneId) {
        String ntcRawData =
                SemCarrierFeature.getInstance()
                        .getString(
                                phoneId,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                "",
                                true);
        return SemTelephonyUtils.getOperatorType(ntcRawData);
    }

    public static String getCountryName(int phoneId) {
        String ntcRawData =
                SemCarrierFeature.getInstance()
                        .getString(
                                phoneId,
                                "CarrierFeature_RIL_ConfigNetworkTypeCapability",
                                "",
                                true);
        return SemTelephonyUtils.getCountry(ntcRawData);
    }

    public static String getSalesCode() {
        return SALES_CODE;
    }

    public static String getCarrierGroup(int phoneId) {
        return SemCarrierFeature.getInstance()
                .getString(phoneId, "CarrierFeature_Common_CarrierGroup", "", true);
    }

    public static String getNetworkCode(int phoneId) {
        return SemCarrierFeature.getInstance()
                .getString(phoneId, "CarrierFeature_Common_CarrierGroup", SALES_CODE, true);
    }

    public static boolean getNtcFeature(int phoneId, int ntcFeature) {
        return getNtcFeature(phoneId, ntcFeature, -1);
    }

    public static boolean getNtcFeature(int phoneId, int ntcFeature, int carrierId) {
        switch (ntcFeature) {
            case 1:
                return "SPR".equals(getMainOperatorName(phoneId));
            case 2:
                if ("SPR".equals(getMainOperatorName(phoneId))) {
                    return "SPR".equalsIgnoreCase(getSalesCode())
                            || "BST".equalsIgnoreCase(getSalesCode())
                            || "XAS".equalsIgnoreCase(getSalesCode())
                            || "SPRPRE".equalsIgnoreCase(getSalesCode());
                }
                return false;
            case 3:
                return "VZW".equals(getMainOperatorName(phoneId));
            case 4:
                return isMainOperatorSpecific(phoneId, "SPR", "USC", "XAA");
            case 5:
                return isMainOperatorSpecific(phoneId, "VZW", "SPR", "USC");
            case 6:
                if (isCountrySpecific(phoneId, "CHN", "KOR")
                        || SemCarrierFeature.getInstance()
                                .getBoolean(
                                        phoneId, "CarrierFeature_RIL_DisableEons", false, true)) {
                    return false;
                }
                return SemCarrierFeature.getInstance()
                                .getBoolean(
                                        phoneId,
                                        "CarrierFeature_RIL_ReferEonsWithoutLac",
                                        false,
                                        true)
                        || "VZW".equals(getMainOperatorName(phoneId));
            case 7:
                boolean isSimMobility = IS_TABLET;
                return isSimMobility && isSalesCodeSpecific("ATT", "APP");
            case 8:
                if (isMainOperatorSpecific(phoneId, "VZW", "USC", "SPR")
                        && !"ATC".equals(getSubOperatorName(phoneId))) {
                    boolean isSimMobility2 =
                            ((Boolean)
                                            getTelephonyProperty(
                                                    phoneId,
                                                    SemTelephonyProps.sim_mobility(),
                                                    false))
                                    .booleanValue();
                    TelephonyManager.getDefault();
                    String simCountry = TelephonyManager.getSimCountryIsoForPhone(phoneId);
                    if (!isSimMobility2
                            || XmlTags.ATTR_USER_ID.equals(simCountry)
                            || "pr".equals(simCountry)
                            || "vi".equals(simCountry)) {
                        return true;
                    }
                    log("No ECBM (Reason: SimMobility)");
                    return false;
                }
                return false;
            case 9:
                return isCountrySpecific(phoneId, "USA", "CAN") && phoneId == 1;
            case 10:
                return isSubOperatorSpecific(phoneId, "LTN");
            case 11:
                return isMainOperatorSpecific(phoneId, "TMO", "XAA", "DSG");
            case 12:
                return isMainOperatorSpecific(phoneId, "ATT", "TMO", "BMC");
            case 13:
                return true;
            case 14:
                return isCountrySpecific(phoneId, "CHN", "HKG", "TPE")
                        ? TelephonyManager.getDefault().getActiveModemCount() > 1
                        : isSalesCodeSpecific("INU", "INS")
                                && TelephonyManager.getDefault().getActiveModemCount() > 1
                                && Integer.parseInt(
                                                SemTelephonyProps.support_dual_rat().orElse("0"))
                                        == 1;
            case 15:
                return 1 == Integer.parseInt(SemTelephonyProps.support_dual_rat().orElse("0"))
                        && !isCountrySpecific(phoneId, "USA", "CAN");
            case 16:
                return (("XXV".equalsIgnoreCase(SALES_CODE) || "XEV".equalsIgnoreCase(SALES_CODE))
                                && isIccOperatorNumericSpecific(phoneId, "45204"))
                        || !TextUtils.isEmpty(getNotAllowedNetworkMode(phoneId));
            case 17:
                return !isMainOperatorSpecific(phoneId, "BMC");
            case 18:
                return true;
            case 19:
                return isCountrySpecific(phoneId, "KOR")
                        || isMainOperatorSpecific(phoneId, "VZW", "SPR", "USC", "TGY", "KDI")
                        || isNetworkCodeSpecific(phoneId, "COD", "COB");
            case 20:
                return Build.VERSION.DEVICE_INITIAL_SDK_INT < 33
                        && isSubOperatorSpecific(phoneId, "ATT", "AIO");
            case 21:
                return Build.VERSION.DEVICE_INITIAL_SDK_INT < 34
                        && isSubOperatorSpecific(phoneId, "TMO", "MTR", "ASR");
            case 22:
                return isMainOperatorSpecific(phoneId, "TMO", "ATT", "XAA", "BMC", "DSG");
            case 23:
                return isMainOperatorSpecific(phoneId, "VZW");
            case 24:
                return carrierId == 1187
                        || carrierId == 1779
                        || carrierId == 1
                        || carrierId == 1943
                        || carrierId == 1433;
            case 25:
                return isCountrySpecific(phoneId, "CHN", "HKG", "TPE");
            case 26:
                return (IS_WATCH || IS_WIFI_ONLY) ? false : true;
            case 27:
                return isIccOperatorNumericSpecific(phoneId, "23001", "24001", "26201", "50501");
            case 28:
                return isMainOperatorSpecific(phoneId, "EUR") && carrierId == 1505;
            default:
                log("Unknown NTC feature: " + ntcFeature);
                return false;
        }
    }

    public static boolean isUsaGlobalModel(int phoneId) {
        return isGlobalModel(phoneId) && "USA".equals(getCountryName(phoneId));
    }

    public static boolean isChnGlobalModel() {
        return isChnGlobalModel(0);
    }

    public static boolean isChnGlobalModel(int phoneId) {
        return isGlobalModel(phoneId) && isCountrySpecific(phoneId, "CHN", "HKG", "TPE");
    }

    public static boolean isGlobalModel(int phoneId) {
        return "GLB".equals(getOperatorType(phoneId));
    }

    public static boolean isLatinSubOperator(int phoneId) {
        return isSubOperatorSpecific(phoneId, "LTN", "ICE", "IUS", "MNX");
    }

    public static boolean isMainOperatorSpecific(int phoneId, String... mainOperators) {
        String mainOperator = getMainOperatorName(phoneId);
        for (String x : mainOperators) {
            if (x.equals(mainOperator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubOperatorSpecific(int phoneId, String... subOperators) {
        String subOperator = getSubOperatorName(phoneId);
        for (String x : subOperators) {
            if (x.equals(subOperator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCountrySpecific(int phoneId, String... countries) {
        String country = getCountryName(phoneId);
        for (String x : countries) {
            if (x.equals(country)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSalesCodeSpecific(String... salesCodes) {
        String salesCode = getSalesCode();
        for (String x : salesCodes) {
            if (x.equals(salesCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkCodeSpecific(int phoneId, String... networkCodes) {
        String nwCode = getNetworkCode(phoneId);
        for (String x : networkCodes) {
            if (x.equals(nwCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIccOperatorNumericSpecific(int phoneId, String... numeric) {
        String iccOperatorNumeric =
                TelephonyManager.getDefault().getSimOperatorNumericForPhone(phoneId);
        for (String x : numeric) {
            if (x.equals(iccOperatorNumeric)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRilFeature(String feature) {
        if (TextUtils.isEmpty(RIL_FEATURES) || TextUtils.isEmpty(feature)) {
            return false;
        }
        String[] rilFeatures = RIL_FEATURES.split(" ");
        for (String rilFeature : rilFeatures) {
            if (rilFeature.equalsIgnoreCase(feature)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSimHotswapSupported() {
        return sSimHotswapSupported;
    }

    public static String getNotAllowedNetworkMode(int phoneId) {
        return SemCarrierFeature.getInstance()
                .getString(phoneId, "CarrierFeature_RIL_NotAllowedNetworkMode", "", true);
    }

    public static boolean needToCheckEmergencyNumberForEachSlot(int phoneId) {
        String countryIso = TelephonyManager.getDefault().getNetworkCountryIso(phoneId);
        String lastCountryIso = TelephonyManager.getDefault().getLastNetworkCountryIso(phoneId);
        if ("vn".equals(countryIso)
                || "vn".equals(lastCountryIso)
                || "th".equals(countryIso)
                || "th".equals(lastCountryIso)) {
            return true;
        }
        return false;
    }

    public static boolean needSecSimOnOffEx() {
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 28) {
            if ("SM-M205F".equalsIgnoreCase(SystemProperties.get("ro.product.model", ""))
                    || "SM-M305F".equalsIgnoreCase(SystemProperties.get("ro.product.model", ""))) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isGCFMode(int phoneId) {
        return "GCF".equals(getMainOperatorName(phoneId))
                || "1".equals(SystemProperties.get("persist.radio.gcfmode", "0"));
    }

    public static boolean supportDualLte() {
        return TelephonyManager.getDefault().getActiveModemCount() > 1;
    }

    public static boolean isOneTray() {
        if ("2".equals("1")) {
            return false;
        }
        if (TelephonyManager.getDefault().getActiveModemCount() <= 1) {
            return true;
        }
        String sim1Path = SystemProperties.get("ril.simslottype1", "0");
        String sim2Path = SystemProperties.get("ril.simslottype2", "0");
        return ("1".equals(sim1Path) || "1".equals(sim2Path)) ? false : true;
    }

    public static String getSimbasedChangeType() {
        if (sSimbasedChangeType == null || sSimbasedChangeType.length < 2) {
            InitializeSimbasedType();
        }
        return sSimbasedChangeType[1];
    }

    public static boolean isKorSimInKorDevice(int phoneId) {
        if (!isCountrySpecific(phoneId, "KOR")) {
            return false;
        }
        String simType = SemTelephonyUtils.getTelephonyProperty(phoneId, "ril.simtype", "");
        return "2".equals(simType) || "3".equals(simType) || "4".equals(simType);
    }

    public static boolean isSupportTiantong() {
        return hasRilFeature("tiantong");
    }

    private static <T> T getTelephonyProperty(int phoneId, List<T> prop, T defaultValue) {
        T ret = null;
        if (phoneId >= 0 && phoneId < prop.size()) {
            ret = prop.get(phoneId);
        }
        return ret != null ? ret : defaultValue;
    }

    private static void log(String msg) {
        Rlog.d(LOG_TAG, msg);
    }

    public static void dump(int phoneId) {
        log("----- TelephonyFeatures.dump(" + phoneId + ") -----");
        log("getMainOperatorName: " + getMainOperatorName(phoneId));
        log("getSubOperatorName: " + getSubOperatorName(phoneId));
        log("getOperatorType: " + getOperatorType(phoneId));
        log("getCountryName: " + getCountryName(phoneId));
        log("SHIP_BUILD: " + SHIP_BUILD);
        log("SALES_CODE: " + SALES_CODE);
        log(
                "DEVICE_TYPE: "
                        + DEVICE_TYPE
                        + " (IS_PHONE: "
                        + IS_PHONE
                        + ", IS_TABLET: "
                        + IS_TABLET
                        + ", IS_WATCH: "
                        + IS_WATCH
                        + NavigationBarInflaterView.KEY_CODE_END);
        log(
                "HARDWARE_TYPE: "
                        + HARDWARE_TYPE
                        + " (IS_QCOM: "
                        + IS_QCOM
                        + ", IS_EXYNOS: "
                        + IS_EXYNOS
                        + ", IS_MTK: "
                        + IS_MTK
                        + NavigationBarInflaterView.KEY_CODE_END);
        log("IS_WIFI_ONLY: " + IS_WIFI_ONLY);
        log("IS_FACTORY_BIN: " + IS_FACTORY_BIN);
        log("getNetworkCode: " + getNetworkCode(phoneId));
        for (int i = 1; i < 29; i++) {
            log("  " + featureToString(i) + ": " + getNtcFeature(phoneId, i));
        }
    }

    private static String featureToString(int ntcFeature) {
        switch (ntcFeature) {
            case 1:
                return "NTC_FEATURE_SPR_US_INTERNATIONAL_DIALING";
            case 2:
                return "NTC_FEATURE_CSC_SPRINT_CHAMELEON";
            case 3:
                return "NTC_FEATURE_ERI_ON_CP";
            case 4:
                return "NTC_FEATURE_ERI_ON_AP";
            case 5:
                return "NTC_FEATURE_ALLOW_HANGUP_WHEN_DIALING";
            case 6:
                return "NTC_FEATURE_UPDATE_NETWORK_LIST_WITH_EONS";
            case 7:
                return "NTC_FEATURE_SUPPORT_IMSCALL_ONLY";
            case 8:
                return "NTC_FEATURE_SUPPORT_IMSCALL_ECBM";
            case 9:
                return "NTC_FEATURE_USE_SECOND_TTY_MODE_IN_DUAL_SIM";
            case 10:
                return "NTC_FEATURE_UPDATE_ADDRESS_FOR_CALL_CONTROL";
            case 11:
                return "NTC_FEATURE_DISPLAY_TOAST_AFTER_RTT_E911_FAILED";
            case 12:
                return "NTC_FEATURE_DISPLAY_RESCAN_DIALOG";
            case 13:
                return "NTC_FEATURE_SET_CLIR_TO_BOTH_SIDES";
            case 14:
                return "NTC_FEATURE_BLOCK_NETMODE_CHANGE_WITH_CARRIER_CONFIG_CHANGED";
            case 15:
                return "NTC_FEATURE_CHECK_OPPOSITE_SLOT_NETMODE_BEFORE_CHANGE";
            case 16:
                return "NTC_FEATURE_IS_3G_NOT_ALLOWED_OPERATOR";
            case 17:
                return "NTC_FEATURE_RETRY_EMERGENCY_SEARCH_IN_ALERTING";
            case 18:
                return "NTC_FEATURE_RESUME_HELD_CALL_AFTER_MO_FAIL";
            case 19:
                return "NTC_FEATURE_RESUME_HELD_CALL_IF_BG_ONLY";
            case 20:
                return "NTC_FEATURE_FORCELY_SET_2G_ENABLED";
            case 21:
                return "NTC_FEATURE_FORCELY_SET_3G_4G_ENABLED";
            case 22:
                return "NTC_FEATURE_FAKE_RADIO_ON_USING_PROPERTY";
            case 23:
                return "NTC_FEATURE_FAKE_RADIO_ON_USING_EXTENDED_PARAMETER";
            case 24:
                return "NTC_FEATURE_NEED_FORCE_NETWORK_MODE";
            case 25:
                return "NTC_FEATURE_SHOW_VOICE_AS_DATA_NETWORK_TYPE";
            case 26:
                return "NTC_FEATURE_SUPPORT_WAKELOCK_LOGGING";
            case 27:
                return "NTC_FEATURE_CHECK_VOLTE_SUBSCRIBER_FOR_CALL_WAITING";
            case 28:
                return "NTC_FEATURE_DEFAULT_NETWORK_TYPE_2G_REASON_DISABLE";
            default:
                return "Unknown NTC_FEATURE(" + ntcFeature + NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
