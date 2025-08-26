package com.android.internal.org.bouncycastle.util;

/* loaded from: classes5.dex */
public class IPAddress {
    public static boolean isValid(String str) {
        return isValidIPv4(str) || isValidIPv6(str);
    }

    public static boolean isValidWithNetMask(String str) {
        return isValidIPv4WithNetmask(str) || isValidIPv6WithNetmask(str);
    }

    public static boolean isValidIPv4(String str) {
        int length = str.length();
        if (length < 7 || length > 15) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            int iIndexOf = str.indexOf(46, i);
            if (!isParseableIPv4Octet(str, i, iIndexOf)) {
                return false;
            }
            i = iIndexOf + 1;
        }
        return isParseableIPv4Octet(str, i, length);
    }

    public static boolean isValidIPv4WithNetmask(String str) {
        int iIndexOf = str.indexOf("/");
        if (iIndexOf < 1) {
            return false;
        }
        String strSubstring = str.substring(0, iIndexOf);
        String strSubstring2 = str.substring(iIndexOf + 1);
        return isValidIPv4(strSubstring) && (isValidIPv4(strSubstring2) || isParseableIPv4Mask(strSubstring2));
    }

    public static boolean isValidIPv6(String str) {
        int iIndexOf;
        if (str.length() == 0) {
            return false;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt != ':' && Character.digit(cCharAt, 16) < 0) {
            return false;
        }
        String str2 = str + ":";
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (i < str2.length() && (iIndexOf = str2.indexOf(58, i)) >= i) {
            if (i2 == 8) {
                return false;
            }
            if (i != iIndexOf) {
                String strSubstring = str2.substring(i, iIndexOf);
                if (iIndexOf == str2.length() - 1 && strSubstring.indexOf(46) > 0) {
                    i2++;
                    if (i2 == 8 || !isValidIPv4(strSubstring)) {
                        return false;
                    }
                } else if (!isParseableIPv6Segment(str2, i, iIndexOf)) {
                    return false;
                }
            } else {
                if (iIndexOf != 1 && iIndexOf != str2.length() - 1 && z) {
                    return false;
                }
                z = true;
            }
            i = iIndexOf + 1;
            i2++;
        }
        return i2 == 8 || z;
    }

    public static boolean isValidIPv6WithNetmask(String str) {
        int iIndexOf = str.indexOf("/");
        if (iIndexOf < 1) {
            return false;
        }
        String strSubstring = str.substring(0, iIndexOf);
        String strSubstring2 = str.substring(iIndexOf + 1);
        return isValidIPv6(strSubstring) && (isValidIPv6(strSubstring2) || isParseableIPv6Mask(strSubstring2));
    }

    private static boolean isParseableIPv4Mask(String str) {
        return isParseable(str, 0, str.length(), 10, 2, false, 0, 32);
    }

    private static boolean isParseableIPv4Octet(String str, int i, int i2) {
        return isParseable(str, i, i2, 10, 3, true, 0, 255);
    }

    private static boolean isParseableIPv6Mask(String str) {
        return isParseable(str, 0, str.length(), 10, 3, false, 1, 128);
    }

    private static boolean isParseableIPv6Segment(String str, int i, int i2) {
        return isParseable(str, i, i2, 16, 4, true, 0, 65535);
    }

    private static boolean isParseable(String str, int i, int i2, int i3, int i4, boolean z, int i5, int i6) {
        int i7 = i2 - i;
        if ((i7 > i4) || (i7 < 1)) {
            return false;
        }
        if (((i7 > 1) && (!z)) && Character.digit(str.charAt(i), i3) <= 0) {
            return false;
        }
        int i8 = 0;
        while (i < i2) {
            int i9 = i + 1;
            int iDigit = Character.digit(str.charAt(i), i3);
            if (iDigit < 0) {
                return false;
            }
            i8 = (i8 * i3) + iDigit;
            i = i9;
        }
        return (i8 >= i5) & (i8 <= i6);
    }
}
