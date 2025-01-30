package com.android.internal.telephony;

import android.app.blob.XmlTags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Build;
import android.p009os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;
import com.samsung.telephony.sysprop.SemTelephonyProps;
import java.util.List;

/* loaded from: classes5.dex */
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
  public static final String MULTI_SIM_CONFIG;
  public static final int NO_DUALSIM_NR_MODEL = 0;
  public static final int NTC_FEATURE_ALLOW_HANGUP_WHEN_DIALING = 5;
  public static final int NTC_FEATURE_BLOCK_NETMODE_CHANGE_WITH_CARRIER_CONFIG_CHANGED = 14;
  public static final int NTC_FEATURE_CHECK_OPPOSITE_SLOT_NETMODE_BEFORE_CHANGE = 15;
  public static final int NTC_FEATURE_CSC_SPRINT_CHAMELEON = 2;
  public static final int NTC_FEATURE_DISPLAY_RESCAN_DIALOG = 12;
  public static final int NTC_FEATURE_DISPLAY_TOAST_AFTER_RTT_E911_FAILED = 11;
  public static final int NTC_FEATURE_ERI_ON_AP = 4;
  public static final int NTC_FEATURE_ERI_ON_CP = 3;
  public static final int NTC_FEATURE_FAKE_RADIO_ON_USING_EXTENDED_PARAMETER = 23;
  public static final int NTC_FEATURE_FAKE_RADIO_ON_USING_PROPERTY = 22;
  public static final int NTC_FEATURE_FORCELY_SET_2G_ENABLED = 20;
  public static final int NTC_FEATURE_FORCELY_SET_3G_4G_ENABLED = 21;
  public static final int NTC_FEATURE_IS_3G_NOT_ALLOWED_OPERATOR = 16;
  public static final int NTC_FEATURE_MAX = 25;
  public static final int NTC_FEATURE_RESUME_HELD_CALL_AFTER_MO_FAIL = 18;
  public static final int NTC_FEATURE_RESUME_HELD_CALL_IF_BG_ONLY = 19;
  public static final int NTC_FEATURE_RETRY_EMERGENCY_SEARCH_IN_ALERTING = 17;
  public static final int NTC_FEATURE_SET_CLIR_TO_BOTH_SIDES = 13;
  public static final int NTC_FEATURE_SPR_US_INTERNATIONAL_DIALING = 1;
  public static final int NTC_FEATURE_SUPPORT_IMSCALL_ECBM = 8;
  public static final int NTC_FEATURE_SUPPORT_IMSCALL_ONLY = 7;
  public static final int NTC_FEATURE_SUPPORT_SATELLITE_FOR_CARRIER = 24;
  public static final int NTC_FEATURE_UPDATE_ADDRESS_FOR_CALL_CONTROL = 10;
  public static final int NTC_FEATURE_UPDATE_NETWORK_LIST_WITH_EONS = 6;
  public static final int NTC_FEATURE_USE_SECOND_TTY_MODE_IN_DUAL_SIM = 9;
  public static final int PRIMARY_PHONE_ID = 0;
  private static final int PROJECT_SIM_NUM;
  public static final int SECONDARY_PHONE_ID = 1;
  private static boolean mSimHotswapSupported;
  private static String[] mSimbasedChangeType;
  public static final boolean SHIP_BUILD = SystemProperties.getBoolean("ro.product_ship", true);
  private static final String SALES_CODE =
      SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE);

  static {
    boolean z = true;
    String str = SystemProperties.get("ro.build.characteristics", "");
    DEVICE_TYPE = str;
    boolean contains = str.contains(BnRConstants.DEVICETYPE_TABLET);
    IS_TABLET = contains;
    boolean contains2 = str.contains("watch");
    IS_WATCH = contains2;
    IS_PHONE = (contains || contains2) ? false : true;
    String str2 = SystemProperties.get("ro.boot.hardware", "");
    HARDWARE_TYPE = str2;
    IS_QCOM = str2.contains("qcom");
    if (!str2.contains("exynos") && !str2.contains("s5e")) {
      z = false;
    }
    IS_EXYNOS = z;
    IS_MTK = str2.contains("mt");
    IS_WIFI_ONLY = "wifi-only".equals(SemTelephonyProps.carrier().orElse("unknown"));
    IS_FACTORY_BIN =
        "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"));
    MULTI_SIM_CONFIG = android.sysprop.TelephonyProperties.multi_sim_config().orElse("");
    PROJECT_SIM_NUM = TelephonyManager.getDefault().getActiveModemCount();
    mSimHotswapSupported = false;
    mSimbasedChangeType = null;
  }

  private static void InitializeSimbasedType() {
    String changeType = SystemProperties.get("ro.simbased.changetype", KeyProperties.DIGEST_NONE);
    String[] split = changeType.split(",");
    mSimbasedChangeType = split;
    if (split == null || split.length < 2) {
      mSimbasedChangeType = new String[] {KeyProperties.DIGEST_NONE, "DISABLED"};
      return;
    }
    split[0] = split[0].trim();
    String[] strArr = mSimbasedChangeType;
    strArr[1] = strArr[1].trim();
  }

  public static String getMainOperatorName(int phoneId) {
    String ntcRawData =
        SemCarrierFeature.getInstance()
            .getString(phoneId, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true);
    return SemTelephonyUtils.getMainOperator(ntcRawData);
  }

  public static String getSubOperatorName(int phoneId) {
    String ntcRawData =
        SemCarrierFeature.getInstance()
            .getString(phoneId, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true);
    return SemTelephonyUtils.getSubOperator(ntcRawData);
  }

  public static String getOperatorType(int phoneId) {
    String ntcRawData =
        SemCarrierFeature.getInstance()
            .getString(phoneId, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true);
    return SemTelephonyUtils.getOperatorType(ntcRawData);
  }

  public static String getCountryName(int phoneId) {
    String ntcRawData =
        SemCarrierFeature.getInstance()
            .getString(phoneId, "CarrierFeature_RIL_ConfigNetworkTypeCapability", "", true);
    return SemTelephonyUtils.getCountry(ntcRawData);
  }

  public static String getNotAllowedNetworkMode(int phoneId) {
    String mNotAllowedNetworkMode =
        SemCarrierFeature.getInstance()
            .getString(phoneId, "CarrierFeature_RIL_NotAllowedNetworkMode", "", true);
    Rlog.m77d(LOG_TAG, "getNotAllowedNetworkMode() feature = " + mNotAllowedNetworkMode);
    return mNotAllowedNetworkMode;
  }

  public static boolean isLatinSubOperator(int phoneId) {
    boolean isLtnSub = isSubOperatorSpecific(phoneId, "LTN", "ICE", "IUS", "MNX");
    Rlog.m77d(LOG_TAG, "isLatinSubOperator: " + isLtnSub);
    return isLtnSub;
  }

  public static String getSalesCode() {
    return SALES_CODE;
  }

  public static String getNetworkCode(int phoneId) {
    return SemCarrierFeature.getInstance()
        .getString(phoneId, "CarrierFeature_Common_CarrierGroup", SALES_CODE, true);
  }

  public static boolean getNtcFeature(int phoneId, int ntcFeature) {
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
                .getBoolean(phoneId, "CarrierFeature_RIL_DisableEons", false, true)) {
          return false;
        }
        return SemCarrierFeature.getInstance()
                .getBoolean(phoneId, "CarrierFeature_RIL_ReferEonsWithoutLac", false, true)
            || "VZW".equals(getMainOperatorName(phoneId));
      case 7:
        boolean isSimMobility = IS_TABLET;
        return isSimMobility && isSalesCodeSpecific("ATT", "APP");
      case 8:
        if (isMainOperatorSpecific(phoneId, "VZW", "USC", "SPR")
            && !"ATC".equals(getSubOperatorName(phoneId))) {
          boolean isSimMobility2 =
              ((Boolean) getTelephonyProperty(phoneId, SemTelephonyProps.sim_mobility(), false))
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
        int i = PROJECT_SIM_NUM;
        if (i <= 1 || !isCountrySpecific(phoneId, "CHN", "HKG", "TPE")) {
          return i > 1
              && isSalesCodeSpecific("INU", "INS")
              && 1 == Integer.parseInt(SemTelephonyProps.support_dual_rat().orElse("0"));
        }
        return true;
      case 15:
        return 1 == Integer.parseInt(SemTelephonyProps.support_dual_rat().orElse("0"))
            && !isCountrySpecific(phoneId, "USA", "CAN");
      case 16:
        String str = SALES_CODE;
        return (("XXV".equalsIgnoreCase(str) || "XEV".equalsIgnoreCase(str))
                && isIccOperatorNumericSpecific(phoneId, "45204"))
            || !getNotAllowedNetworkMode(phoneId).isEmpty();
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
        return "TMB".equalsIgnoreCase(SALES_CODE)
            && SemCarrierFeature.getInstance()
                .getBoolean(phoneId, "CarrierFeature_Common_Support_Satellite", false, true);
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

  public static boolean isSimHotswapSupported() {
    return mSimHotswapSupported;
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

  public static boolean needToCheckVolteSubscriber(int phoneId) {
    if (isIccOperatorNumericSpecific(phoneId, "23001", "24001", "26201", "50501")) {
      return true;
    }
    return false;
  }

  public static boolean needToRunLteRoaming(int phoneId) {
    if (!isMainOperatorSpecific(phoneId, "SKT", "KTT", "LGT", "KOO")) {
      return false;
    }
    String simType = SemTelephonyUtils.getTelephonyProperty(phoneId, "ril.simtype", "");
    if (("2".equals(simType) && "KTT".equals(getMainOperatorName(phoneId)))
        || (("3".equals(simType) && "LGT".equals(getMainOperatorName(phoneId)))
            || ("4".equals(simType) && "SKT".equals(getMainOperatorName(phoneId))))) {
      return true;
    }
    return "KOO".equals(getMainOperatorName(phoneId))
        && ("2".equals(simType) || "3".equals(simType) || "4".equals(simType));
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
    String gcfMode = SystemProperties.get("persist.radio.gcfmode", "0");
    return "GCF".equals(getMainOperatorName(phoneId)) || "1".equals(gcfMode);
  }

  public static boolean showVoiceAsDataNetworkType(int phoneId) {
    if (isCountrySpecific(phoneId, "CHN", "HKG", "TPE")) {
      return true;
    }
    return false;
  }

  public static boolean supportDualLte() {
    return PROJECT_SIM_NUM > 1;
  }

  public static boolean isNotAllowedMoCall(int phoneId) {
    String carrierId = SystemProperties.get("ro.boot.carrierid", "UNKNOWN");
    if ("AIS".equals(carrierId) && !isIccOperatorNumericSpecific(phoneId, "52001", "52003")) {
      log("Non AIS carrier. Blocked MO Call");
      return true;
    }
    return false;
  }

  public static boolean isOneTray() {
    if (!"1".equals("1")) {
      return false;
    }
    String sim1Path = SystemProperties.get("ril.simslottype1", "0");
    String sim2Path = SystemProperties.get("ril.simslottype2", "0");
    return ("1".equals(sim1Path) || "1".equals(sim2Path)) ? false : true;
  }

  public static String getSimbasedChangeType() {
    String[] strArr = mSimbasedChangeType;
    if (strArr == null || strArr.length < 2) {
      InitializeSimbasedType();
    }
    return mSimbasedChangeType[1];
  }

  public static String getPropertyMultiSimBased(String prop, int phoneId) {
    if (prop == null || prop.length() < 1) {
      log("Property is wrong");
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(prop);
    if (phoneId == 0) {
      sb.append(0);
    } else {
      sb.append(1);
    }
    return sb.toString();
  }

  public static boolean isKorSimInKorDevice(int phoneId) {
    if (!isCountrySpecific(phoneId, "KOR")) {
      return false;
    }
    String simType = SemTelephonyUtils.getTelephonyProperty(phoneId, "ril.simtype", "");
    return "2".equals(simType) || "3".equals(simType) || "4".equals(simType);
  }

  public static boolean isSupportWakelockLogging() {
    if (IS_WATCH || IS_WIFI_ONLY) {
      return false;
    }
    return true;
  }

  private static <T> T getTelephonyProperty(int phoneId, List<T> prop, T defaultValue) {
    T ret = null;
    if (phoneId >= 0 && phoneId < prop.size()) {
      ret = prop.get(phoneId);
    }
    return ret != null ? ret : defaultValue;
  }

  private static void log(String msg) {
    Rlog.m77d(LOG_TAG, msg);
  }

  public static void dump(int phoneId) {
    log("----- TelephonyFeatures.dump(" + phoneId + ") -----");
    log("getMainOperatorName: " + getMainOperatorName(phoneId));
    log("getSubOperatorName: " + getSubOperatorName(phoneId));
    log("getOperatorType: " + getOperatorType(phoneId));
    log("getCountryName: " + getCountryName(phoneId));
    log("SHIP_BUILD: " + SHIP_BUILD);
    log("SALES_CODE: " + SALES_CODE);
    log("DEVICE_TYPE: " + DEVICE_TYPE);
    log("IS_PHONE: " + IS_PHONE);
    log("IS_TABLET: " + IS_TABLET);
    log("IS_WATCH: " + IS_WATCH);
    log("HARDWARE_TYPE: " + HARDWARE_TYPE);
    log("IS_WIFI_ONLY: " + IS_WIFI_ONLY);
    log("IS_QCOM: " + IS_QCOM);
    log("IS_EXYNOS: " + IS_EXYNOS);
    log("IS_MTK: " + IS_MTK);
    log("MULTI_SIM_CONFIG: " + MULTI_SIM_CONFIG);
    log("getNetworkCode: " + getNetworkCode(phoneId));
    for (int i = 1; i < 25; i++) {
      log("  getNtcFeature(" + featureToString(i) + "): " + getNtcFeature(phoneId, i));
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
        return "NTC_FEATURE_SUPPORT_SATELLITE_FOR_CARRIER";
      default:
        return "Unknown NTC_FEATURE(" + ntcFeature + NavigationBarInflaterView.KEY_CODE_END;
    }
  }
}
