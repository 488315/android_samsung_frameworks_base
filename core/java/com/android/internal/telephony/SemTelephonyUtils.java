package com.android.internal.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.SystemProperties;
import android.telephony.CallState;
import android.telephony.PreciseCallState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.telephony.util.ArrayUtils;
import java.util.List;

/* loaded from: classes5.dex */
public class SemTelephonyUtils {
  public static final int ASR_CARRIER_ID = 1433;
  public static final int ATT_CARRIER_ID = 1187;
  public static final int AU_CARRIER_ID = 1581;
  public static final int CCT_CARRIER_ID = 2032;
  public static final int CELLCOM_CARRIER_ID = 1802;
  public static final int CHA_CARRIER_ID = 2126;
  public static final int CRICKET_CARRIER_ID = 1779;
  public static final int CSPIRE_CARRIER_ID = 1836;
  public static final int DSG_CARRIER_ID = 2518;
  private static final String[] FACTORY_SIM_IMSI;
  public static final int FKR_CARRIER_ID = 2146;
  public static final int JIO_CARRIER_ID = 2018;
  private static final String[] KOR_DOMESTIC_PROP_FOR_DS;
  private static final String LOG_TAG = "SemTelephonyUtils";
  public static final int LYCAMOBILE_CARRIER_ID = 2067;
  public static final int MTR_CARRIER_ID = 1943;
  private static final int NTCTYPE_COUNTRY = 3;
  private static final int NTCTYPE_LENGTH = 15;
  private static final int NTCTYPE_MAINOPERATOR = 0;
  private static final int NTCTYPE_MAX = 3;
  private static final int NTCTYPE_OPERATORTYPE = 2;
  private static final int NTCTYPE_SUBOPERATOR = 1;
  public static final String ONEUI_VERSION;
  public static final int POVO_CARRIER_ID = 2514;
  public static final int RAKUTEN_MNO_CARRIER_ID = 2429;
  public static final boolean SHIP_BUILD;
  public static final int TMO_CARRIER_ID = 1;
  public static final int UNION_CARRIER_ID = 1781;
  public static final int UQM_CARRIER_ID = 2110;
  public static final int USC_CARRIER_ID = 1952;
  public static final int VIAERO_CARRIER_ID = 1193;
  public static final int VZW_CARRIER_ID = 1839;

  static {
    boolean z = true;
    if (!SystemProperties.getBoolean("ro.product_ship", true)
        && !SystemProperties.getBoolean("persist.ril.override.product_ship", false)) {
      z = false;
    }
    SHIP_BUILD = z;
    ONEUI_VERSION = SystemProperties.get("ro.build.version.oneui", "");
    FACTORY_SIM_IMSI =
        new String[] {"001010123456789", "999999999999999", "520360110000010", "512010123456789"};
    KOR_DOMESTIC_PROP_FOR_DS = new String[] {"ril.simtype"};
  }

  private static String getNetworkTypeCapability(String networkTypeCapability, int ntcType) {
    if (TextUtils.isEmpty(networkTypeCapability)
        || networkTypeCapability.length() != 15
        || ntcType > 3) {
      return "---";
    }
    String[] ntcValue = networkTypeCapability.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
    return ntcValue.length != 4 ? "---" : ntcValue[ntcType];
  }

  public static String getMainOperator(String networkTypeCapability) {
    return getNetworkTypeCapability(networkTypeCapability, 0);
  }

  public static String getSubOperator(String networkTypeCapability) {
    return getNetworkTypeCapability(networkTypeCapability, 1);
  }

  public static String getOperatorType(String networkTypeCapability) {
    return getNetworkTypeCapability(networkTypeCapability, 2);
  }

  public static String getCountry(String networkTypeCapability) {
    return getNetworkTypeCapability(networkTypeCapability, 3);
  }

  public static boolean isMainOperatorSpecific(
      String networkTypeCapability, String... mainOperators) {
    String mainOperator = getMainOperator(networkTypeCapability);
    for (String x : mainOperators) {
      if (x.equals(mainOperator)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isSubOperatorSpecific(
      String networkTypeCapability, String... subOperators) {
    String subOperator = getSubOperator(networkTypeCapability);
    for (String x : subOperators) {
      if (x.equals(subOperator)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isGlobalModel(String networkTypeCapability) {
    return "GLB".equals(getOperatorType(networkTypeCapability));
  }

  public static boolean isCountrySpecific(String networkTypeCapability, String... countries) {
    String countryName = getCountry(networkTypeCapability);
    for (String x : countries) {
      if (x.equals(countryName)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isUsaGlobalModel(String networkTypeCapability) {
    return isGlobalModel(networkTypeCapability) && "USA".equals(getCountry(networkTypeCapability));
  }

  public static boolean isChnGlobalModel(String networkTypeCapability) {
    return isGlobalModel(networkTypeCapability)
        && isCountrySpecific(networkTypeCapability, "CHN", "HKG", "TPE");
  }

  public static boolean isFactorySim(String imsi) {
    if (!TextUtils.isEmpty(imsi) && ArrayUtils.contains(FACTORY_SIM_IMSI, imsi)) {
      return true;
    }
    return false;
  }

  public static Object maskPii(Object pii) {
    return SHIP_BUILD ? "<MASKED>" : pii;
  }

  public static String maskPiiFromVoiceMailNumber(String voiceMailNumber) {
    if (!SHIP_BUILD || TextUtils.isEmpty(voiceMailNumber) || "*86".equals(voiceMailNumber)) {
      return voiceMailNumber;
    }
    String number = "length " + voiceMailNumber.length();
    return number;
  }

  public static String maskPiiFromCellIdentity(int pii) {
    if (pii == Integer.MAX_VALUE || pii == 0 || !SHIP_BUILD) {
      return Integer.toString(pii);
    }
    return maskPiiFromNumber(Integer.valueOf(pii));
  }

  public static String maskPiiFromCellIdentity(long pii) {
    if (pii == Long.MAX_VALUE || pii == 0 || !SHIP_BUILD) {
      return Long.toString(pii);
    }
    return maskPiiFromNumber(Long.valueOf(pii));
  }

  private static String maskPiiFromNumber(Object pii) {
    String num = pii.toString();
    if (!TextUtils.isEmpty(num)) {
      int numLen = num.length();
      int remainLen = numLen > 5 ? 3 : numLen / 2;
      int startRemainLen = remainLen / 2;
      StringBuilder sb = new StringBuilder();
      sb.append((CharSequence) num, 0, startRemainLen);
      for (int i = 0; i < numLen - remainLen; i++) {
        sb.append("*");
      }
      int i2 = remainLen - startRemainLen;
      sb.append((CharSequence) num, numLen - i2, numLen);
      return sb.toString();
    }
    return num;
  }

  public static boolean isSilentRedial(Bundle intentExtras) {
    if (intentExtras != null) {
      String callDomain = intentExtras.getString(SemRILConstants.EXTRA_LATEST_CALL_DOMAIN);
      if ("PS".equals(callDomain) || "CS".equals(callDomain)) {
        return true;
      }
      return false;
    }
    return false;
  }

  public static boolean isSilentRedialFromPs(Bundle intentExtras) {
    if (intentExtras != null
        && "PS".equals(intentExtras.getString(SemRILConstants.EXTRA_LATEST_CALL_DOMAIN))) {
      return true;
    }
    return false;
  }

  public static boolean isSilentRedialFromCs(Bundle intentExtras) {
    if (intentExtras != null
        && "CS".equals(intentExtras.getString(SemRILConstants.EXTRA_LATEST_CALL_DOMAIN))) {
      return true;
    }
    return false;
  }

  public static String emergencySearchResultToString(int emergencySearchResult) {
    switch (emergencySearchResult) {
      case 1:
        return "CS(1)";
      case 2:
        return "LTE(2)";
      case 3:
        return "IWLAN(3)";
      case 4:
        return "NONE(4)";
      case 5:
        return "IGNORE(5)";
      case 6:
        return "NR(6)";
      default:
        return "UNKNOWN(" + emergencySearchResult + NavigationBarInflaterView.KEY_CODE_END;
    }
  }

  public static String emergencyControlCommandToString(int emergencyControlCommand) {
    switch (emergencyControlCommand) {
      case 0:
        return "DIALED(0)";
      case 1:
        return "CONNECTED(1)";
      case 2:
        return "FINISHED(2)";
      case 3:
        return "FINISHED_WITH_ECM(3)";
      case 4:
        return "FAILED(4)";
      default:
        if (emergencyControlCommand >= 200) {
          return "FAILED(" + emergencyControlCommand + NavigationBarInflaterView.KEY_CODE_END;
        }
        return "UNKNOWN(" + emergencyControlCommand + NavigationBarInflaterView.KEY_CODE_END;
    }
  }

  public static String internalAttributeToString(int internalAttribute) {
    StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
    if ((internalAttribute & 1) == 1) {
      sb.append("ON_EMERGENCY_SEARCH ");
    }
    if ((internalAttribute & 2) == 2) {
      sb.append("SKIP_EMERGENCY_SEARCH ");
    }
    if ((internalAttribute & 8) == 8) {
      sb.append("START_DURING_VOLTE_ENABLED ");
    }
    if ((internalAttribute & 16) == 16) {
      sb.append("TRIGGER_E911_START_DAN ");
    }
    if ((internalAttribute & 32) == 32) {
      sb.append("PREPARE_CS_ONLY_DAN ");
    }
    if ((internalAttribute & 64) == 64) {
      sb.append("MIGRATE_FROM_IMSPHONE ");
    }
    if ((internalAttribute & 128) == 128) {
      sb.append("USE_ASSISTED_DIALING ");
    }
    if ((internalAttribute & 256) == 256) {
      sb.append("FALLBACK ");
    }
    if ((internalAttribute & 512) == 512) {
      sb.append("IS_MULTIPARTY ");
    }
    sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    return sb.toString();
  }

  public static String preciseCallStateToString(PreciseCallState preciseCallState) {
    StringBuilder sb = new StringBuilder("{");
    if (preciseCallState.getRingingCallState() > 0) {
      sb.append("RingingCallState: ").append(preciseCallState.getRingingCallState()).append(" ");
    }
    if (preciseCallState.getForegroundCallState() > 0) {
      sb.append("ForegroundCallState: ")
          .append(preciseCallState.getForegroundCallState())
          .append(" ");
    }
    if (preciseCallState.getBackgroundCallState() > 0) {
      sb.append("BackgroundCallState: ")
          .append(preciseCallState.getBackgroundCallState())
          .append(" ");
    }
    if (preciseCallState.getDisconnectCause() != -1) {
      sb.append("DisconnectCause: ").append(preciseCallState.getDisconnectCause()).append(" ");
    }
    if (preciseCallState.getPreciseDisconnectCause() != -1) {
      sb.append("PreciseDisconnectCause: ")
          .append(preciseCallState.getPreciseDisconnectCause())
          .append(" ");
    }
    sb.append("}");
    return sb.toString();
  }

  public static String callStateListToString(List<CallState> callStateList) {
    StringBuilder sb = new StringBuilder("{");
    for (CallState callState : callStateList) {
      int classification = callState.getCallClassification();
      if (classification == 0) {
        sb.append("[RG ");
      } else if (classification == 1) {
        sb.append("[FG ");
      } else if (classification == 2) {
        sb.append("[BG ");
      } else {
        sb.append("[UK ");
      }
      sb.append("callState: ").append(callState.getCallState()).append(", ");
      sb.append("networkType: ").append(callState.getNetworkType()).append(", ");
      sb.append("imsCallType: ").append(callState.getImsCallType()).append(", ");
      sb.append("imsCallSessionId: ").append(callState.getImsCallSessionId()).append(", ");
      sb.append("imsCallServiceType: ").append(callState.getImsCallServiceType()).append("] ");
    }
    sb.append("}");
    return sb.toString();
  }

  public static String getKorDomesticPropForDS(String prop, int phoneId) {
    if (TextUtils.isEmpty(prop) || !ArrayUtils.contains(KOR_DOMESTIC_PROP_FOR_DS, prop)) {
      return null;
    }
    return phoneId <= 0 ? prop : prop + phoneId;
  }

  public static String getTelephonyProperty(int phoneId, String property, String defaultVal) {
    String convertedProp = getKorDomesticPropForDS(property, phoneId);
    if (convertedProp != null) {
      return TelephonyManager.getTelephonyProperty(convertedProp, defaultVal);
    }
    return TelephonyManager.getTelephonyProperty(phoneId, property, defaultVal);
  }
}
