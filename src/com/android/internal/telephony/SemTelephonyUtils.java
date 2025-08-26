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

/* loaded from: classes4.dex */
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

    private static String getNetworkTypeCapability(String str, int i) {
        if (TextUtils.isEmpty(str) || str.length() != 15 || i > 3) {
            return "---";
        }
        String[] strArrSplit = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        return strArrSplit.length != 4 ? "---" : strArrSplit[i];
    }

    public static String getMainOperator(String str) {
        return getNetworkTypeCapability(str, 0);
    }

    public static String getSubOperator(String str) {
        return getNetworkTypeCapability(str, 1);
    }

    public static String getOperatorType(String str) {
        return getNetworkTypeCapability(str, 2);
    }

    public static String getCountry(String str) {
        return getNetworkTypeCapability(str, 3);
    }

    public static boolean isMainOperatorSpecific(String str, String... strArr) {
        String mainOperator = getMainOperator(str);
        for (String str2 : strArr) {
            if (str2.equals(mainOperator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubOperatorSpecific(String str, String... strArr) {
        String subOperator = getSubOperator(str);
        for (String str2 : strArr) {
            if (str2.equals(subOperator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGlobalModel(String str) {
        return "GLB".equals(getOperatorType(str));
    }

    public static boolean isCountrySpecific(String str, String... strArr) {
        String country = getCountry(str);
        for (String str2 : strArr) {
            if (str2.equals(country)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsaGlobalModel(String str) {
        return isGlobalModel(str) && "USA".equals(getCountry(str));
    }

    public static boolean isChnGlobalModel(String str) {
        return isGlobalModel(str) && isCountrySpecific(str, "CHN", "HKG", "TPE");
    }

    public static Object maskPii(Object obj) {
        return SHIP_BUILD ? "<MASKED>" : obj;
    }

    public static String maskPiiFromCellIdentity(int i) {
        if (i == Integer.MAX_VALUE || i == 0 || !SHIP_BUILD) {
            return Integer.toString(i);
        }
        return maskPiiFromNumber(Integer.valueOf(i));
    }

    public static String maskPiiFromCellIdentity(long j) {
        if (j == Long.MAX_VALUE || j == 0 || !SHIP_BUILD) {
            return Long.toString(j);
        }
        return maskPiiFromNumber(Long.valueOf(j));
    }

    private static String maskPiiFromNumber(Object obj) {
        String string = obj.toString();
        if (TextUtils.isEmpty(string)) {
            return string;
        }
        int length = string.length();
        int i = length > 5 ? 3 : length / 2;
        int i2 = i / 2;
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) string, 0, i2);
        for (int i3 = 0; i3 < length - i; i3++) {
            sb.append("*");
        }
        sb.append((CharSequence) string, length - (i - i2), length);
        return sb.toString();
    }

    public static String toHexString(int i) {
        return "0x" + Integer.toHexString(i).toUpperCase(Locale.ROOT);
    }

    public static String toHexString(Long l) {
        return "0x" + Long.toHexString(l.longValue()).toUpperCase(Locale.ROOT);
    }

    public static String toReadableNetworkTypeString(int i) {
        return toHexString(i) + NavigationBarInflaterView.KEY_CODE_START + RadioAccessFamily.getNetworkTypeFromRaf(i) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String toReadableNetworkTypeString(long j) {
        return toHexString(Long.valueOf(j)) + NavigationBarInflaterView.KEY_CODE_START + RadioAccessFamily.getNetworkTypeFromRaf((int) j) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String preciseCallStateToString(PreciseCallState preciseCallState) {
        StringBuilder sb = new StringBuilder("{");
        if (preciseCallState.getRingingCallState() > 0) {
            sb.append("RingingCallState: ");
            sb.append(preciseCallState.getRingingCallState());
            sb.append(" ");
        }
        if (preciseCallState.getForegroundCallState() > 0) {
            sb.append("ForegroundCallState: ");
            sb.append(preciseCallState.getForegroundCallState());
            sb.append(" ");
        }
        if (preciseCallState.getBackgroundCallState() > 0) {
            sb.append("BackgroundCallState: ");
            sb.append(preciseCallState.getBackgroundCallState());
            sb.append(" ");
        }
        if (preciseCallState.getDisconnectCause() != -1) {
            sb.append("DisconnectCause: ");
            sb.append(preciseCallState.getDisconnectCause());
            sb.append(" ");
        }
        if (preciseCallState.getPreciseDisconnectCause() != -1) {
            sb.append("PreciseDisconnectCause: ");
            sb.append(preciseCallState.getPreciseDisconnectCause());
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static String callStateListToString(List<CallState> list) {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");
        for (CallState callState : list) {
            int callClassification = callState.getCallClassification();
            if (callClassification == 0) {
                sb.append("[RG ");
            } else if (callClassification == 1) {
                sb.append("[FG ");
            } else if (callClassification == 2) {
                sb.append("[BG ");
            } else {
                sb.append("[UK ");
            }
            sb.append("callState: ");
            sb.append(callState.getCallState());
            sb.append(", networkType: ");
            sb.append(callState.getNetworkType());
            sb.append(", imsCallType: ");
            sb.append(callState.getImsCallType());
            sb.append(", imsCallSessionId: ");
            sb.append(callState.getImsCallSessionId());
            sb.append(", imsCallServiceType: ");
            sb.append(callState.getImsCallServiceType());
            sb.append("] ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static String getKorDomesticPropForDS(String str, int i) {
        if (TextUtils.isEmpty(str) || !ArrayUtils.contains(KOR_DOMESTIC_PROP_FOR_DS, str)) {
            return null;
        }
        if (i <= 0) {
            return str;
        }
        return str + i;
    }

    public static String getTelephonyProperty(int i, String str, String str2) {
        String korDomesticPropForDS = getKorDomesticPropForDS(str, i);
        if (korDomesticPropForDS != null) {
            return TelephonyManager.getTelephonyProperty(korDomesticPropForDS, str2);
        }
        return TelephonyManager.getTelephonyProperty(i, str, str2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getStaticOperatorNameChinese(String str, boolean z) {
        boolean z2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        char c = 0;
        if (z) {
            String str2 = SystemProperties.get("persist.sys.locale", "");
            z2 = "zh-Hans-CN".equals(str2) || "zh-CN".equals(str2);
        }
        String upperCase = str.toUpperCase();
        upperCase.hashCode();
        switch (upperCase.hashCode()) {
            case 2162:
                if (!upperCase.equals("CU")) {
                    c = 65535;
                    break;
                }
                break;
            case 66511:
                if (upperCase.equals("CBN")) {
                    c = 1;
                    break;
                }
                break;
            case 67058:
                if (upperCase.equals("CTC")) {
                    c = 2;
                    break;
                }
                break;
            case 2072138:
                if (upperCase.equals("CMCC")) {
                    c = 3;
                    break;
                }
                break;
            case 64445426:
                if (upperCase.equals("CTCTT")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (z2) {
                }
                break;
            case 1:
                if (z2) {
                }
                break;
            case 2:
            case 4:
                if (z2) {
                }
                break;
            case 3:
                if (z2) {
                }
                break;
        }
        return null;
    }
}
