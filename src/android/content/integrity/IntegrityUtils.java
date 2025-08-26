package android.content.integrity;

import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public class IntegrityUtils {
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static byte[] getBytesFromHexDigest(String str) {
        Preconditions.checkArgument(str.length() % 2 == 0, "Invalid hex encoding %s: must have even length", str);
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            char cCharAt = str.charAt(i2);
            bArr[i] = (byte) (hexToDec(str.charAt(i2 + 1)) | (hexToDec(cCharAt) << 4));
        }
        return bArr;
    }

    public static String getHexDigest(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = i * 2;
            cArr[i2] = decToHex((b >>> 4) & 15);
            cArr[i2 + 1] = decToHex(b & 15);
        }
        return new String(cArr);
    }

    private static int hexToDec(int i) {
        if (i >= 48 && i <= 57) {
            return i - 48;
        }
        if (i >= 97 && i <= 102) {
            return i - 87;
        }
        if (i >= 65 && i <= 70) {
            return i - 55;
        }
        throw new IllegalArgumentException("Invalid hex char " + i);
    }

    private static char decToHex(int i) {
        if (i >= 0) {
            char[] cArr = HEX_CHARS;
            if (i < cArr.length) {
                return cArr[i];
            }
        }
        throw new IllegalArgumentException("Invalid dec value to be converted to hex digit " + i);
    }
}
