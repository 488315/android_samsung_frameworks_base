package com.samsung.android.wifi.util;

import android.text.format.DateFormat;

/* loaded from: classes6.dex */
public class SemHexEncoding {
    private static final char[] LOWER_CASE_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.AM_PM, 'b', 'c', DateFormat.DATE, 'e', 'f'};
    private static final char[] UPPER_CASE_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F'};

    private SemHexEncoding() {
    }

    public static String encodeToString(byte b, boolean upperCase) {
        char[] digits = upperCase ? UPPER_CASE_DIGITS : LOWER_CASE_DIGITS;
        char[] buf = {digits[(b >> 4) & 15], digits[b & 15]};
        return new String(buf, 0, 2);
    }

    public static char[] encode(byte[] data) {
        return encode(data, 0, data.length, true);
    }

    public static char[] encode(byte[] data, boolean upperCase) {
        return encode(data, 0, data.length, upperCase);
    }

    public static char[] encode(byte[] data, int offset, int len) {
        return encode(data, offset, len, true);
    }

    private static char[] encode(byte[] data, int offset, int len, boolean upperCase) {
        char[] digits = upperCase ? UPPER_CASE_DIGITS : LOWER_CASE_DIGITS;
        char[] result = new char[len * 2];
        for (int i = 0; i < len; i++) {
            byte b = data[offset + i];
            int resultIndex = i * 2;
            result[resultIndex] = digits[(b >> 4) & 15];
            result[resultIndex + 1] = digits[b & 15];
        }
        return result;
    }

    public static String encodeToString(byte[] data) {
        return encodeToString(data, true);
    }

    public static String encodeToString(byte[] data, boolean upperCase) {
        return new String(encode(data, upperCase));
    }

    public static byte[] decode(String encoded) throws IllegalArgumentException {
        return decode(encoded.toCharArray());
    }

    public static byte[] decode(String encoded, boolean allowSingleChar) throws IllegalArgumentException {
        return decode(encoded.toCharArray(), allowSingleChar);
    }

    public static byte[] decode(char[] encoded) throws IllegalArgumentException {
        return decode(encoded, false);
    }

    public static byte[] decode(char[] encoded, boolean allowSingleChar) throws IllegalArgumentException {
        int encodedLength = encoded.length;
        int resultLengthBytes = (encodedLength + 1) / 2;
        byte[] result = new byte[resultLengthBytes];
        int resultOffset = 0;
        int i = 0;
        if (allowSingleChar) {
            if (encodedLength % 2 != 0) {
                result[0] = (byte) toDigit(encoded, 0);
                i = 0 + 1;
                resultOffset = 0 + 1;
            }
        } else if (encodedLength % 2 != 0) {
            throw new IllegalArgumentException("Invalid input length: " + encodedLength);
        }
        while (i < encodedLength) {
            result[resultOffset] = (byte) ((toDigit(encoded, i) << 4) | toDigit(encoded, i + 1));
            i += 2;
            resultOffset++;
        }
        return result;
    }

    private static int toDigit(char[] str, int offset) throws IllegalArgumentException {
        char c = str[offset];
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' <= c && c <= 'f') {
            return (c - 'a') + 10;
        }
        if ('A' <= c && c <= 'F') {
            return (c - 'A') + 10;
        }
        throw new IllegalArgumentException("Illegal char: " + str[offset] + " at offset " + offset);
    }
}
