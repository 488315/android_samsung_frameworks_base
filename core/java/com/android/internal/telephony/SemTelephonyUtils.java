package com.android.internal.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemProperties;
import android.telephony.CallState;
import android.telephony.PreciseCallState;
import android.telephony.RadioAccessFamily;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.telephony.util.ArrayUtils;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SemTelephonyUtils {
    private static final String[] KOR_DOMESTIC_PROP_FOR_DS;
    private static final int NTCTYPE_COUNTRY = 3;
    private static final int NTCTYPE_LENGTH = 15;
    private static final int NTCTYPE_MAINOPERATOR = 0;
    private static final int NTCTYPE_MAX = 3;
    private static final int NTCTYPE_OPERATORTYPE = 2;
    private static final int NTCTYPE_SUBOPERATOR = 1;
    public static final String ONEUI_VERSION;
    public static final boolean SHIP_BUILD;

    static {
        boolean z = true;
        if (!SystemProperties.getBoolean("ro.product_ship", true) && !SystemProperties.getBoolean("persist.ril.override.product_ship", false)) {
            z = false;
        }
        SHIP_BUILD = z;
        ONEUI_VERSION = SystemProperties.get("ro.build.version.oneui", "");
        KOR_DOMESTIC_PROP_FOR_DS = new String[]{"ril.simtype"};
    }

    private static String getNetworkTypeCapability(String networkTypeCapability, int ntcType) {
        if (TextUtils.isEmpty(networkTypeCapability) || networkTypeCapability.length() != 15 || ntcType > 3) {
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

    public static boolean isMainOperatorSpecific(String networkTypeCapability, String... mainOperators) {
        String mainOperator = getMainOperator(networkTypeCapability);
        for (String x : mainOperators) {
            if (x.equals(mainOperator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubOperatorSpecific(String networkTypeCapability, String... subOperators) {
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
        return isGlobalModel(networkTypeCapability) && isCountrySpecific(networkTypeCapability, "CHN", "HKG", "TPE");
    }

    public static Object maskPii(Object pii) {
        return SHIP_BUILD ? "<MASKED>" : pii;
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

    public static String toHexString(int i) {
        return "0x" + Integer.toHexString(i).toUpperCase(Locale.ROOT);
    }

    public static String toHexString(Long i) {
        return "0x" + Long.toHexString(i.longValue()).toUpperCase(Locale.ROOT);
    }

    public static String toReadableNetworkTypeString(int networkTypeBitmask) {
        return toHexString(networkTypeBitmask) + NavigationBarInflaterView.KEY_CODE_START + RadioAccessFamily.getNetworkTypeFromRaf(networkTypeBitmask) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String toReadableNetworkTypeString(long networkTypeBitmask) {
        return toHexString(Long.valueOf(networkTypeBitmask)) + NavigationBarInflaterView.KEY_CODE_START + RadioAccessFamily.getNetworkTypeFromRaf((int) networkTypeBitmask) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String preciseCallStateToString(PreciseCallState preciseCallState) {
        StringBuilder sb = new StringBuilder("{");
        if (preciseCallState.getRingingCallState() > 0) {
            sb.append("RingingCallState: ").append(preciseCallState.getRingingCallState()).append(" ");
        }
        if (preciseCallState.getForegroundCallState() > 0) {
            sb.append("ForegroundCallState: ").append(preciseCallState.getForegroundCallState()).append(" ");
        }
        if (preciseCallState.getBackgroundCallState() > 0) {
            sb.append("BackgroundCallState: ").append(preciseCallState.getBackgroundCallState()).append(" ");
        }
        if (preciseCallState.getDisconnectCause() != -1) {
            sb.append("DisconnectCause: ").append(preciseCallState.getDisconnectCause()).append(" ");
        }
        if (preciseCallState.getPreciseDisconnectCause() != -1) {
            sb.append("PreciseDisconnectCause: ").append(preciseCallState.getPreciseDisconnectCause()).append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static String callStateListToString(List<CallState> callStateList) {
        StringBuilder sb = new StringBuilder(128).append("{");
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
