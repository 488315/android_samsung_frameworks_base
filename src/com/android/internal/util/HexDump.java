package com.android.internal.util;

import android.media.MediaMetrics;
import android.text.format.DateFormat;

/* loaded from: classes4.dex */
public class HexDump {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F'};
    private static final char[] HEX_LOWER_CASE_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.AM_PM, 'b', 'c', DateFormat.DATE, 'e', 'f'};

    public static String dumpHexString(byte[] bArr) {
        if (bArr == null) {
            return "(null)";
        }
        return dumpHexString(bArr, 0, bArr.length);
    }

    public static String dumpHexString(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return "(null)";
        }
        StringBuilder sb = new StringBuilder("\n0x");
        byte[] bArr2 = new byte[16];
        sb.append(toHexString(i));
        int i3 = i;
        int i4 = 0;
        while (i3 < i + i2) {
            if (i4 == 16) {
                sb.append(" ");
                for (int i5 = 0; i5 < 16; i5++) {
                    byte b = bArr2[i5];
                    if (b > 32 && b < 126) {
                        sb.append(new String(bArr2, i5, 1));
                    } else {
                        sb.append(MediaMetrics.SEPARATOR);
                    }
                }
                sb.append("\n0x");
                sb.append(toHexString(i3));
                i4 = 0;
            }
            byte b2 = bArr[i3];
            sb.append(" ");
            char[] cArr = HEX_DIGITS;
            sb.append(cArr[(b2 >>> 4) & 15]);
            sb.append(cArr[b2 & 15]);
            bArr2[i4] = b2;
            i3++;
            i4++;
        }
        if (i4 != 16) {
            int i6 = ((16 - i4) * 3) + 1;
            for (int i7 = 0; i7 < i6; i7++) {
                sb.append(" ");
            }
            for (int i8 = 0; i8 < i4; i8++) {
                byte b3 = bArr2[i8];
                if (b3 > 32 && b3 < 126) {
                    sb.append(new String(bArr2, i8, 1));
                } else {
                    sb.append(MediaMetrics.SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    public static String toHexString(byte b) {
        return toHexString(toByteArray(b));
    }

    public static String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length, true);
    }

    public static String toHexString(byte[] bArr, boolean z) {
        return toHexString(bArr, 0, bArr.length, z);
    }

    public static String toHexString(byte[] bArr, int i, int i2) {
        return toHexString(bArr, i, i2, true);
    }

    public static String toHexString(byte[] bArr, int i, int i2, boolean z) {
        char[] cArr = z ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
        char[] cArr2 = new char[i2 * 2];
        int i3 = 0;
        for (int i4 = i; i4 < i + i2; i4++) {
            byte b = bArr[i4];
            int i5 = i3 + 1;
            cArr2[i3] = cArr[(b >>> 4) & 15];
            i3 += 2;
            cArr2[i5] = cArr[b & 15];
        }
        return new String(cArr2);
    }

    public static String toHexString(int i) {
        return toHexString(toByteArray(i));
    }

    public static byte[] toByteArray(byte b) {
        return new byte[]{b};
    }

    public static byte[] toByteArray(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'W';
        }
        throw new RuntimeException("Invalid hex char '" + c + "'");
    }

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((toByte(str.charAt(i)) << 4) | toByte(str.charAt(i + 1)));
        }
        return bArr;
    }

    public static StringBuilder appendByteAsHex(StringBuilder sb, byte b, boolean z) {
        char[] cArr = z ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
        sb.append(cArr[(b >> 4) & 15]);
        sb.append(cArr[b & 15]);
        return sb;
    }
}
