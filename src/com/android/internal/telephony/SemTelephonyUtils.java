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
        String[] split = str.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        return split.length != 4 ? "---" : split[i];
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
        String obj2 = obj.toString();
        if (TextUtils.isEmpty(obj2)) {
            return obj2;
        }
        int length = obj2.length();
        int i = length > 5 ? 3 : length / 2;
        int i2 = i / 2;
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) obj2, 0, i2);
        for (int i3 = 0; i3 < length - i; i3++) {
            sb.append("*");
        }
        sb.append((CharSequence) obj2, length - (i - i2), length);
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
    /* JADX WARN: Code restructure failed: missing block: B:42:0x006d, code lost:
    
        if (r6.equals("CU") == false) goto L15;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStaticOperatorNameChinese(java.lang.String r6, boolean r7) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            r0 = 0
            r2 = 1
            if (r7 == 0) goto L29
            java.lang.String r7 = "persist.sys.locale"
            java.lang.String r3 = ""
            java.lang.String r7 = android.os.SystemProperties.get(r7, r3)
            java.lang.String r3 = "zh-Hans-CN"
            boolean r3 = r3.equals(r7)
            if (r3 != 0) goto L29
            java.lang.String r3 = "zh-CN"
            boolean r7 = r3.equals(r7)
            if (r7 != 0) goto L29
            r7 = r0
            goto L2a
        L29:
            r7 = r2
        L2a:
            java.lang.String r6 = r6.toUpperCase()
            r6.hashCode()
            int r3 = r6.hashCode()
            java.lang.String r4 = "CMCC"
            r5 = -1
            switch(r3) {
                case 2162: goto L67;
                case 66511: goto L5c;
                case 67058: goto L51;
                case 2072138: goto L48;
                case 64445426: goto L3d;
                default: goto L3b;
            }
        L3b:
            r0 = r5
            goto L70
        L3d:
            java.lang.String r0 = "CTCTT"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L46
            goto L3b
        L46:
            r0 = 4
            goto L70
        L48:
            boolean r6 = r6.equals(r4)
            if (r6 != 0) goto L4f
            goto L3b
        L4f:
            r0 = 3
            goto L70
        L51:
            java.lang.String r0 = "CTC"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L5a
            goto L3b
        L5a:
            r0 = 2
            goto L70
        L5c:
            java.lang.String r0 = "CBN"
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L65
            goto L3b
        L65:
            r0 = r2
            goto L70
        L67:
            java.lang.String r2 = "CU"
            boolean r6 = r6.equals(r2)
            if (r6 != 0) goto L70
            goto L3b
        L70:
            switch(r0) {
                case 0: goto L8d;
                case 1: goto L84;
                case 2: goto L7b;
                case 3: goto L74;
                case 4: goto L7b;
                default: goto L73;
            }
        L73:
            return r1
        L74:
            if (r7 == 0) goto L7a
            java.lang.String r6 = "中国移动"
            return r6
        L7a:
            return r4
        L7b:
            if (r7 == 0) goto L81
            java.lang.String r6 = "中国电信"
            return r6
        L81:
            java.lang.String r6 = "China Telecom"
            return r6
        L84:
            if (r7 == 0) goto L8a
            java.lang.String r6 = "中国广电"
            return r6
        L8a:
            java.lang.String r6 = "CHINA BROADNET"
            return r6
        L8d:
            if (r7 == 0) goto L93
            java.lang.String r6 = "中国联通"
            return r6
        L93:
            java.lang.String r6 = "China Unicom"
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SemTelephonyUtils.getStaticOperatorNameChinese(java.lang.String, boolean):java.lang.String");
    }
}
