package com.android.internal.telephony.cdma.sms;

import android.util.SparseBooleanArray;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.util.HexDump;

/* loaded from: classes4.dex */
public class CdmaSmsAddress extends SmsAddress {
    public static final int DIGIT_MODE_4BIT_DTMF = 0;
    public static final int DIGIT_MODE_8BIT_CHAR = 1;
    static final String NA_EmailGateWayAddr = "6245";
    public static final int NUMBERING_PLAN_ISDN_TELEPHONY = 1;
    public static final int NUMBERING_PLAN_UNKNOWN = 0;
    public static final int NUMBER_MODE_DATA_NETWORK = 1;
    public static final int NUMBER_MODE_NOT_DATA_NETWORK = 0;
    public static final int SMS_ADDRESS_MAX = 36;
    public static final int SMS_SUBADDRESS_MAX = 36;
    public static final int TON_ABBREVIATED = 6;
    public static final int TON_ALPHANUMERIC = 5;
    public static final int TON_INTERNATIONAL_OR_IP = 1;
    public static final int TON_NATIONAL_OR_EMAIL = 2;
    public static final int TON_NETWORK = 3;
    public static final int TON_RESERVED = 7;
    public static final int TON_SUBSCRIBER = 4;
    public static final int TON_UNKNOWN = 0;
    private static final SparseBooleanArray numericCharDialableMap;
    private static final char[] numericCharsDialable;
    private static final char[] numericCharsSugar;
    public int digitMode;
    public int numberMode;
    public int numberOfDigits;
    public int numberPlan;

    public String toString() {
        StringBuilder sb = new StringBuilder("CdmaSmsAddress ");
        sb.append("{ digitMode=" + this.digitMode);
        sb.append(", numberMode=" + this.numberMode);
        sb.append(", numberPlan=" + this.numberPlan);
        sb.append(", numberOfDigits=" + this.numberOfDigits);
        sb.append(", ton=" + this.ton);
        sb.append(", address=\"" + this.address + "\"");
        StringBuilder sb2 = new StringBuilder(", origBytes=");
        sb2.append(HexDump.toHexString(this.origBytes));
        sb.append(sb2.toString());
        sb.append(" }");
        return sb.toString();
    }

    public static byte[] parseToDtmf(String str) {
        int i;
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt >= '1' && cCharAt <= '9') {
                i = cCharAt - '0';
            } else if (cCharAt == '0') {
                i = 10;
            } else if (cCharAt == '*') {
                i = 11;
            } else {
                if (cCharAt != '#') {
                    return null;
                }
                i = 12;
            }
            bArr[i2] = (byte) i;
        }
        return bArr;
    }

    static {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '*', '#'};
        numericCharsDialable = cArr;
        char[] cArr2 = {'(', ')', ' ', '-', '+', '.', '/', '\\'};
        numericCharsSugar = cArr2;
        numericCharDialableMap = new SparseBooleanArray(cArr.length + cArr2.length);
        int i = 0;
        while (true) {
            char[] cArr3 = numericCharsDialable;
            if (i >= cArr3.length) {
                break;
            }
            numericCharDialableMap.put(cArr3[i], true);
            i++;
        }
        int i2 = 0;
        while (true) {
            char[] cArr4 = numericCharsSugar;
            if (i2 >= cArr4.length) {
                return;
            }
            numericCharDialableMap.put(cArr4[i2], false);
            i2++;
        }
    }

    private static String filterNumericSugar(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            SparseBooleanArray sparseBooleanArray = numericCharDialableMap;
            int iIndexOfKey = sparseBooleanArray.indexOfKey(cCharAt);
            if (iIndexOfKey < 0) {
                return null;
            }
            if (sparseBooleanArray.valueAt(iIndexOfKey)) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    private static String filterWhitespace(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != ' ' && cCharAt != '\r' && cCharAt != '\n' && cCharAt != '\t') {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static CdmaSmsAddress parse(String str) {
        byte[] bArrStringToAscii;
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        cdmaSmsAddress.address = str;
        cdmaSmsAddress.ton = 0;
        cdmaSmsAddress.digitMode = 0;
        cdmaSmsAddress.numberPlan = 0;
        cdmaSmsAddress.numberMode = 0;
        String strFilterNumericSugar = filterNumericSugar(str);
        if (str.contains("+") || strFilterNumericSugar == null) {
            cdmaSmsAddress.digitMode = 1;
            cdmaSmsAddress.numberMode = 1;
            String strFilterWhitespace = filterWhitespace(str);
            if (str.contains("@")) {
                cdmaSmsAddress.ton = 2;
            } else {
                if (str.contains("+") && filterNumericSugar(str) != null) {
                    cdmaSmsAddress.ton = 1;
                    cdmaSmsAddress.numberPlan = 1;
                    cdmaSmsAddress.numberMode = 0;
                    strFilterNumericSugar = filterNumericSugar(str);
                }
                bArrStringToAscii = UserData.stringToAscii(strFilterNumericSugar);
            }
            strFilterNumericSugar = strFilterWhitespace;
            bArrStringToAscii = UserData.stringToAscii(strFilterNumericSugar);
        } else {
            bArrStringToAscii = parseToDtmf(strFilterNumericSugar);
        }
        if (bArrStringToAscii == null) {
            return null;
        }
        cdmaSmsAddress.origBytes = bArrStringToAscii;
        cdmaSmsAddress.numberOfDigits = bArrStringToAscii.length;
        if (strFilterNumericSugar != null) {
            cdmaSmsAddress.address = strFilterNumericSugar;
        }
        return cdmaSmsAddress;
    }

    @Override // com.android.internal.telephony.SmsAddress
    public boolean couldBeEmailGateway() {
        return this.address.compareTo(NA_EmailGateWayAddr) == 0;
    }
}
