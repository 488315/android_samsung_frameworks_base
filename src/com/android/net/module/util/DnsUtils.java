package com.android.net.module.util;

/* loaded from: classes6.dex */
public class DnsUtils {
    private static char toDnsUpperCase(char c) {
        return (c < 'a' || c > 'z') ? c : (char) (c - ' ');
    }

    private DnsUtils() {
    }

    public static String toDnsUpperCase(String str) {
        char[] cArr = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            cArr[i] = toDnsUpperCase(str.charAt(i));
        }
        return new String(cArr);
    }

    public static String[] toDnsLabelsUpperCase(String[] strArr) {
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = toDnsUpperCase(strArr[i]);
        }
        return strArr2;
    }

    public static boolean equalsIgnoreDnsCase(String str, String str2) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (str.length() != str2.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (toDnsUpperCase(str.charAt(i)) != toDnsUpperCase(str2.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsDnsLabelIgnoreDnsCase(String[] strArr, String[] strArr2) {
        if (strArr == strArr2) {
            return true;
        }
        int length = strArr.length;
        if (strArr2.length != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!equalsIgnoreDnsCase(strArr[i], strArr2[i])) {
                return false;
            }
        }
        return true;
    }
}
