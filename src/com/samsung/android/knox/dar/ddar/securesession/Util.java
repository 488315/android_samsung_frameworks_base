package com.samsung.android.knox.dar.ddar.securesession;

import java.util.Arrays;
import java.util.Base64;

/* loaded from: classes6.dex */
public class Util {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static final String byteArrayToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length << 1];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = HEX_ARRAY;
            cArr[i] = cArr2[(b & 240) >>> 4];
            i += 2;
            cArr[i2] = cArr2[b & 15];
        }
        String str = new String(cArr);
        Arrays.fill(cArr, '0');
        return str;
    }

    public static final byte[] fromHexString(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static String encodeBase64(byte[] bArr) {
        try {
            return Base64.getEncoder().encodeToString(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decodeBase64(String str) {
        try {
            return Base64.getDecoder().decode(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
