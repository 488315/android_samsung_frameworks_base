package com.samsung.android.knox.dar.ddar.securesession;

import java.util.Arrays;
import java.util.Base64;

/* loaded from: classes5.dex */
public class Util {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static final String byteArrayToHexString(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int j2 = j + 1;
            char[] cArr = HEX_ARRAY;
            out[j] = cArr[(data[i] & 240) >>> 4];
            j = j2 + 1;
            out[j2] = cArr[data[i] & 15];
        }
        String str = new String(out);
        Arrays.fill(out, '0');
        return str;
    }

    public static final byte[] fromHexString(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static String encodeBase64(byte[] source) {
        try {
            return Base64.getEncoder().encodeToString(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decodeBase64(String source) {
        try {
            return Base64.getDecoder().decode(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
