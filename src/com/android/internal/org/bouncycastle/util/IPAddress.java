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
            int indexOf = str.indexOf(46, i);
            if (!isParseableIPv4Octet(str, i, indexOf)) {
                return false;
            }
            i = indexOf + 1;
        }
        return isParseableIPv4Octet(str, i, length);
    }

    public static boolean isValidIPv4WithNetmask(String str) {
        int indexOf = str.indexOf("/");
        if (indexOf < 1) {
            return false;
        }
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf + 1);
        return isValidIPv4(substring) && (isValidIPv4(substring2) || isParseableIPv4Mask(substring2));
    }

    public static boolean isValidIPv6(String str) {
        int indexOf;
        if (str.length() == 0) {
            return false;
        }
        char charAt = str.charAt(0);
        if (charAt != ':' && Character.digit(charAt, 16) < 0) {
            return false;
        }
        String str2 = str + ":";
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (i < str2.length() && (indexOf = str2.indexOf(58, i)) >= i) {
            if (i2 == 8) {
                return false;
            }
            if (i != indexOf) {
                String substring = str2.substring(i, indexOf);
                if (indexOf == str2.length() - 1 && substring.indexOf(46) > 0) {
                    i2++;
                    if (i2 == 8 || !isValidIPv4(substring)) {
                        return false;
                    }
                } else if (!isParseableIPv6Segment(str2, i, indexOf)) {
                    return false;
                }
            } else {
                if (indexOf != 1 && indexOf != str2.length() - 1 && z) {
                    return false;
                }
                z = true;
            }
            i = indexOf + 1;
            i2++;
        }
        return i2 == 8 || z;
    }

    public static boolean isValidIPv6WithNetmask(String str) {
        int indexOf = str.indexOf("/");
        if (indexOf < 1) {
            return false;
        }
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf + 1);
        return isValidIPv6(substring) && (isValidIPv6(substring2) || isParseableIPv6Mask(substring2));
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
            int digit = Character.digit(str.charAt(i), i3);
            if (digit < 0) {
                return false;
            }
            i8 = (i8 * i3) + digit;
            i = i9;
        }
        return (i8 >= i5) & (i8 <= i6);
    }
}
