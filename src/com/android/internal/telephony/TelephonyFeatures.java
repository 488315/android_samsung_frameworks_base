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
import com.samsung.android.lock.LsConstants;
import com.samsung.android.wallpaperbackup.BnRConstants;
import com.samsung.telephony.sysprop.SemTelephonyProps;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class TelephonyFeatures {
    public static final String DEVICE_TYPE;
    public static final int DUALSIM_WITH_ONE_SLOT_NR = 1;
    public static final int DUALSIM_WITH_TWO_SLOT_NR = 2;
    public static final String HARDWARE_TYPE;
    public static final boolean IS_EXYNOS;
    public static final boolean IS_FACTORY_BIN;
    public static final boolean IS_MTK;
    public static final boolean IS_PHONE;
    public static final boolean IS_QCOM;
    public static final boolean IS_TABLET;
    public static final boolean IS_WATCH;
    public static final boolean IS_WIFI_ONLY;
    private static final String LOG_TAG = "TelephonyFeatures";
    public static final int NO_DUALSIM_NR_MODEL = 0;
    public static final int NTC_FEATURE_ALLOW_HANGUP_WHEN_DIALING = 5;
    public static final int NTC_FEATURE_BLOCK_NETMODE_CHANGE_WITH_CARRIER_CONFIG_CHANGED = 14;
    public static final int NTC_FEATURE_CHECK_OPPOSITE_SLOT_NETMODE_BEFORE_CHANGE = 15;
    public static final int NTC_FEATURE_CHECK_VOLTE_SUBSCRIBER_FOR_CALL_WAITING = 26;
    public static final int NTC_FEATURE_DISPLAY_RESCAN_DIALOG = 12;
    public static final int NTC_FEATURE_DISPLAY_TOAST_AFTER_RTT_E911_FAILED = 11;
    public static final int NTC_FEATURE_ERI_ON_AP = 4;
    public static final int NTC_FEATURE_ERI_ON_CP = 3;
    public static final int NTC_FEATURE_FAKE_RADIO_ON_USING_EXTENDED_PARAMETER = 22;
    public static final int NTC_FEATURE_FORCELY_SET_2G_ENABLED = 20;
    public static final int NTC_FEATURE_FORCELY_SET_3G_4G_ENABLED = 21;
    public static final int NTC_FEATURE_IS_3G_NOT_ALLOWED_OPERATOR = 16;
    public static final int NTC_FEATURE_MAX = 27;
    public static final int NTC_FEATURE_NEED_FORCE_NETWORK_MODE = 23;
    public static final int NTC_FEATURE_RESUME_HELD_CALL_AFTER_MO_FAIL = 18;
    public static final int NTC_FEATURE_RESUME_HELD_CALL_IF_BG_ONLY = 19;
    public static final int NTC_FEATURE_RETRY_EMERGENCY_SEARCH_IN_ALERTING = 17;
    public static final int NTC_FEATURE_SET_CLIR_TO_BOTH_SIDES = 13;
    public static final int NTC_FEATURE_SHOW_VOICE_AS_DATA_NETWORK_TYPE = 24;
    public static final int NTC_FEATURE_SUPPORT_IMSCALL_ECBM = 8;
    public static final int NTC_FEATURE_SUPPORT_IMSCALL_ONLY = 7;
    public static final int NTC_FEATURE_SUPPORT_WAKELOCK_LOGGING = 25;
    public static final int NTC_FEATURE_UPDATE_ADDRESS_FOR_CALL_CONTROL = 10;
    public static final int NTC_FEATURE_UPDATE_NETWORK_LIST_WITH_EONS = 6;
    public static final int NTC_FEATURE_USE_SECOND_TTY_MODE_IN_DUAL_SIM = 9;
    public static final int PRIMARY_PHONE_ID = 0;
    public static final String RIL_FEATURES = "vzwcdmaless lracdmaless uscacgcdmaless xaacdmaless satellite_oem satellite_carrier";
    public static final int SECONDARY_PHONE_ID = 1;
    private static boolean sSimHotswapSupported;
    private static String[] sSimbasedChangeType;
    public static final boolean SHIP_BUILD = SystemProperties.getBoolean("ro.product_ship", true);
    private static final String SALES_CODE = SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE);

    public static boolean isOneTray() {
        return false;
    }

    public static boolean needUpPhonePriority() {
        return false;
    }

    public static boolean supportCpai() {
        return true;
    }

    static {
        String str = SystemProperties.get("ro.build.characteristics", "");
        DEVICE_TYPE = str;
        boolean zContains = str.contains(BnRConstants.DEVICETYPE_TABLET);
        IS_TABLET = zContains;
        boolean zContains2 = str.contains("watch");
        IS_WATCH = zContains2;
        IS_PHONE = (zContains || zContains2) ? false : true;
        String str2 = SystemProperties.get("ro.boot.hardware", "");
        HARDWARE_TYPE = str2;
        IS_QCOM = str2.contains("qcom");
        IS_EXYNOS = str2.contains("exynos") || str2.contains("s5e");
        IS_MTK = str2.contains("mt");
        IS_WIFI_ONLY = "wifi-only".equals(SemTelephonyProps.carrier().orElse("unknown"));
        IS_FACTORY_BIN = "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", LsConstants.TAG_UNKNOWN));
        sSimHotswapSupported = true;
        sSimbasedChangeType = null;
    }

    private static void InitializeSimbasedType() {
        String[] strArrSplit = SystemProperties.get("ro.simbased.changetype", KeyProperties.DIGEST_NONE).split(",");
        sSimbasedChangeType = strArrSplit;
        if (strArrSplit == null || strArrSplit.length < 2) {
            sSimbasedChangeType = new String[]{KeyProperties.DIGEST_NONE, "DISABLED"};
            return;
        }
        strArrSplit[0] = strArrSplit[0].trim();
        String[] strArr = sSimbasedChangeType;
        strArr[1] = strArr[1].trim();
    }

    public static String getMainOperatorName(int i) {
        return SemTelephonyUtils.getMainOperator(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true));
    }

    public static String getSubOperatorName(int i) {
        return SemTelephonyUtils.getSubOperator(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true));
    }

    public static String getOperatorType(int i) {
        return SemTelephonyUtils.getOperatorType(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true));
    }

    public static String getCountryName(int i) {
        return SemTelephonyUtils.getCountry(SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true));
    }

    public static String getSalesCode() {
        return SALES_CODE;
    }

    public static String getCarrierGroup(int i) {
        return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_Common_CarrierGroup", "", true);
    }

    public static String getNetworkCode(int i) {
        return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_Common_CarrierGroup", SALES_CODE, true);
    }

    public static boolean getNtcFeature(int i, int i2) {
        return getNtcFeature(i, i2, -1);
    }

    public static boolean getNtcFeature(int i, int i2, int i3) {
        switch (i2) {
            case 3:
                if ("VZW".equals(getMainOperatorName(i))) {
                }
                break;
            case 4:
                if (isMainOperatorSpecific(i, "USC", "XAA")) {
                }
                break;
            case 5:
                if (isMainOperatorSpecific(i, "VZW", "USC")) {
                }
                break;
            case 6:
                if (!isCountrySpecific(i, "CHN", "KOR") && !SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_RIL_DisableEons", false, true)) {
                    if (SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_RIL_ReferEonsWithoutLac", false, true) || "VZW".equals(getMainOperatorName(i))) {
                    }
                }
                break;
            case 7:
                if (!IS_TABLET || !isSalesCodeSpecific("ATT", "APP")) {
                }
                break;
            case 8:
                if (isMainOperatorSpecific(i, "VZW", "USC")) {
                    boolean zBooleanValue = ((Boolean) getTelephonyProperty(i, SemTelephonyProps.sim_mobility(), false)).booleanValue();
                    TelephonyManager.getDefault();
                    String simCountryIsoForPhone = TelephonyManager.getSimCountryIsoForPhone(i);
                    if (zBooleanValue && !XmlTags.ATTR_USER_ID.equals(simCountryIsoForPhone) && !"pr".equals(simCountryIsoForPhone) && !"vi".equals(simCountryIsoForPhone)) {
                        log("No ECBM (Reason: SimMobility)");
                        break;
                    }
                }
                break;
            case 9:
                if (!isCountrySpecific(i, "USA", "CAN") || i != 1) {
                }
                break;
            case 10:
                if (isSubOperatorSpecific(i, "LTN")) {
                }
                break;
            case 11:
                if (isMainOperatorSpecific(i, "TMO", "XAA", "DSG")) {
                }
                break;
            case 12:
                if (isMainOperatorSpecific(i, "ATT", "TMO", "BMC")) {
                }
                break;
            case 14:
                if (isCountrySpecific(i, "CHN", "HKG", "TPE")) {
                    if (TelephonyManager.getDefault().getActiveModemCount() > 1) {
                    }
                } else if (!isSalesCodeSpecific("INU", "INS") || TelephonyManager.getDefault().getActiveModemCount() <= 1 || Integer.parseInt(SemTelephonyProps.support_dual_rat().orElse("0")) != 1) {
                }
                break;
            case 13:
                return true;
            case 15:
                if (1 != Integer.parseInt(SemTelephonyProps.support_dual_rat().orElse("0")) || isCountrySpecific(i, "USA", "CAN")) {
                }
                break;
            case 16:
                String str = SALES_CODE;
                if ((("XXV".equalsIgnoreCase(str) || "XEV".equalsIgnoreCase(str)) && isIccOperatorNumericSpecific(i, "45204")) || !TextUtils.isEmpty(getNotAllowedNetworkMode(i))) {
                }
                break;
            case 17:
                if (!isMainOperatorSpecific(i, "BMC")) {
                }
                break;
            case 19:
                if (isCountrySpecific(i, "KOR") || isMainOperatorSpecific(i, "VZW", "USC", "TGY", "KDI") || isNetworkCodeSpecific(i, "COD", "COB")) {
                }
                break;
            case 18:
                return true;
            case 20:
                if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 33 || !isSubOperatorSpecific(i, "ATT", "AIO")) {
                }
                break;
            case 21:
                if (Build.VERSION.DEVICE_INITIAL_SDK_INT >= 34 || !isSubOperatorSpecific(i, "TMO", "MTR", "ASR")) {
                }
                break;
            case 22:
                if (isMainOperatorSpecific(i, "VZW", "TMO", "ATT", "XAA", "BMC", "DSG")) {
                }
                break;
            case 23:
                if (i3 == 1187 || i3 == 1779 || i3 == 1 || i3 == 1943 || i3 == 1433) {
                }
                break;
            case 24:
                if (isCountrySpecific(i, "CHN", "HKG", "TPE")) {
                }
                break;
            case 25:
                if (IS_WATCH || IS_WIFI_ONLY) {
                }
                break;
            case 26:
                if (isIccOperatorNumericSpecific(i, "23001", "24001", "26201", "50501")) {
                }
                break;
            default:
                log("Unknown NTC feature: " + i2);
                break;
        }
        return true;
    }

    public static boolean isUsaGlobalModel(int i) {
        return isGlobalModel(i) && "USA".equals(getCountryName(i));
    }

    public static boolean isChnGlobalModel() {
        return isChnGlobalModel(0);
    }

    public static boolean isChnGlobalModel(int i) {
        return isGlobalModel(i) && isCountrySpecific(i, "CHN", "HKG", "TPE");
    }

    public static boolean isGlobalModel(int i) {
        return "GLB".equals(getOperatorType(i));
    }

    public static boolean isLatinSubOperator(int i) {
        return isSubOperatorSpecific(i, "LTN", "ICE", "IUS", "MNX");
    }

    public static boolean isMainOperatorSpecific(int i, String... strArr) {
        String mainOperatorName = getMainOperatorName(i);
        for (String str : strArr) {
            if (str.equals(mainOperatorName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubOperatorSpecific(int i, String... strArr) {
        String subOperatorName = getSubOperatorName(i);
        for (String str : strArr) {
            if (str.equals(subOperatorName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCountrySpecific(int i, String... strArr) {
        String countryName = getCountryName(i);
        for (String str : strArr) {
            if (str.equals(countryName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSalesCodeSpecific(String... strArr) {
        String salesCode = getSalesCode();
        for (String str : strArr) {
            if (str.equals(salesCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkCodeSpecific(int i, String... strArr) {
        String networkCode = getNetworkCode(i);
        for (String str : strArr) {
            if (str.equals(networkCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIccOperatorNumericSpecific(int i, String... strArr) {
        String simOperatorNumericForPhone = TelephonyManager.getDefault().getSimOperatorNumericForPhone(i);
        for (String str : strArr) {
            if (str.equals(simOperatorNumericForPhone)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRilFeature(String str) {
        if (!TextUtils.isEmpty(RIL_FEATURES) && !TextUtils.isEmpty(str)) {
            for (String str2 : RIL_FEATURES.split(" ")) {
                if (str2.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSimHotswapSupported() {
        return sSimHotswapSupported;
    }

    public static String getNotAllowedNetworkMode(int i) {
        return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_RIL_NotAllowedNetworkMode", "", true);
    }

    public static boolean needToCheckEmergencyNumberForEachSlot(int i) {
        String networkCountryIso = TelephonyManager.getDefault().getNetworkCountryIso(i);
        String lastNetworkCountryIso = TelephonyManager.getDefault().getLastNetworkCountryIso(i);
        return "vn".equals(networkCountryIso) || "vn".equals(lastNetworkCountryIso) || "th".equals(networkCountryIso) || "th".equals(lastNetworkCountryIso) || "il".equals(networkCountryIso) || "il".equals(lastNetworkCountryIso);
    }

    public static boolean needSecSimOnOffEx() {
        if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 28) {
            return "SM-M205F".equalsIgnoreCase(SystemProperties.get("ro.product.model", "")) || "SM-M305F".equalsIgnoreCase(SystemProperties.get("ro.product.model", ""));
        }
        return false;
    }

    public static boolean isGCFMode(int i) {
        return "GCF".equals(getMainOperatorName(i)) || "1".equals(SystemProperties.get("persist.radio.gcfmode", "0"));
    }

    public static boolean supportDualLte() {
        return TelephonyManager.getDefault().getActiveModemCount() > 1;
    }

    public static String getSimbasedChangeType() {
        String[] strArr = sSimbasedChangeType;
        if (strArr == null || strArr.length < 2) {
            InitializeSimbasedType();
        }
        return sSimbasedChangeType[1];
    }

    public static boolean isKorSimInKorDevice(int i) {
        if (!isCountrySpecific(i, "KOR")) {
            return false;
        }
        String telephonyProperty = SemTelephonyUtils.getTelephonyProperty(i, "ril.simtype", "");
        return "2".equals(telephonyProperty) || "3".equals(telephonyProperty) || "4".equals(telephonyProperty);
    }

    public static boolean isSupportTiantong() {
        return !"BRI".equalsIgnoreCase(SALES_CODE) && hasRilFeature("tiantong");
    }

    public static boolean isSupportQetCombinedMode() {
        return isSupportTiantong() && hasRilFeature("qet_combined");
    }

    public static boolean needToShowTurnOn2gMmi(String str) {
        return Arrays.asList("22206", "22210", "26202", "26204", "26209").contains(str);
    }

    private static <T> T getTelephonyProperty(int i, List<T> list, T t) {
        T t2 = (i < 0 || i >= list.size()) ? null : list.get(i);
        return t2 != null ? t2 : t;
    }

    private static void log(String str) {
        Rlog.d(LOG_TAG, str);
    }

    public static void dump(int i) {
        log("----- TelephonyFeatures.dump(" + i + ") -----");
        StringBuilder sb = new StringBuilder("getMainOperatorName: ");
        sb.append(getMainOperatorName(i));
        log(sb.toString());
        log("getSubOperatorName: " + getSubOperatorName(i));
        log("getOperatorType: " + getOperatorType(i));
        log("getCountryName: " + getCountryName(i));
        log("SHIP_BUILD: " + SHIP_BUILD);
        log("SALES_CODE: " + SALES_CODE);
        log("DEVICE_TYPE: " + DEVICE_TYPE + " (IS_PHONE: " + IS_PHONE + ", IS_TABLET: " + IS_TABLET + ", IS_WATCH: " + IS_WATCH + NavigationBarInflaterView.KEY_CODE_END);
        log("HARDWARE_TYPE: " + HARDWARE_TYPE + " (IS_QCOM: " + IS_QCOM + ", IS_EXYNOS: " + IS_EXYNOS + ", IS_MTK: " + IS_MTK + NavigationBarInflaterView.KEY_CODE_END);
        StringBuilder sb2 = new StringBuilder("IS_WIFI_ONLY: ");
        sb2.append(IS_WIFI_ONLY);
        log(sb2.toString());
        StringBuilder sb3 = new StringBuilder("IS_FACTORY_BIN: ");
        sb3.append(IS_FACTORY_BIN);
        log(sb3.toString());
        StringBuilder sb4 = new StringBuilder("getNetworkCode: ");
        sb4.append(getNetworkCode(i));
        log(sb4.toString());
        for (int i2 = 1; i2 < 27; i2++) {
            log("  " + featureToString(i2) + ": " + getNtcFeature(i, i2));
        }
    }

    private static String featureToString(int i) {
        switch (i) {
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
                return "NTC_FEATURE_FAKE_RADIO_ON_USING_EXTENDED_PARAMETER";
            case 23:
                return "NTC_FEATURE_NEED_FORCE_NETWORK_MODE";
            case 24:
                return "NTC_FEATURE_SHOW_VOICE_AS_DATA_NETWORK_TYPE";
            case 25:
                return "NTC_FEATURE_SUPPORT_WAKELOCK_LOGGING";
            case 26:
                return "NTC_FEATURE_CHECK_VOLTE_SUBSCRIBER_FOR_CALL_WAITING";
            default:
                return "Unknown NTC_FEATURE(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
