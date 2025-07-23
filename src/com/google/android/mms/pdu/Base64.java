package com.google.android.mms.pdu;

/* loaded from: classes6.dex */
public class Base64 {
    static final int BASELENGTH = 255;
    static final int FOURBYTE = 4;
    static final byte PAD = 61;
    private static byte[] base64Alphabet = new byte[255];

    static {
        for (int i = 0; i < 255; i++) {
            base64Alphabet[i] = -1;
        }
        for (int i2 = 90; i2 >= 65; i2--) {
            base64Alphabet[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 122; i3 >= 97; i3--) {
            base64Alphabet[i3] = (byte) (i3 - 71);
        }
        for (int i4 = 57; i4 >= 48; i4--) {
            base64Alphabet[i4] = (byte) (i4 + 4);
        }
        byte[] bArr = base64Alphabet;
        bArr[43] = 62;
        bArr[47] = 63;
    }

    public static byte[] decodeBase64(byte[] bArr) {
        byte[] discardNonBase64 = discardNonBase64(bArr);
        if (discardNonBase64.length == 0) {
            return new byte[0];
        }
        int length = discardNonBase64.length / 4;
        int length2 = discardNonBase64.length;
        while (discardNonBase64[length2 - 1] == 61) {
            length2--;
            if (length2 == 0) {
                return new byte[0];
            }
        }
        byte[] bArr2 = new byte[length2 - length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 4;
            byte b = discardNonBase64[i3 + 2];
            byte b2 = discardNonBase64[i3 + 3];
            byte[] bArr3 = base64Alphabet;
            byte b3 = bArr3[discardNonBase64[i3]];
            byte b4 = bArr3[discardNonBase64[i3 + 1]];
            if (b != 61 && b2 != 61) {
                byte b5 = bArr3[b];
                byte b6 = bArr3[b2];
                bArr2[i] = (byte) ((b3 << 2) | (b4 >> 4));
                bArr2[i + 1] = (byte) (((b4 & 15) << 4) | ((b5 >> 2) & 15));
                bArr2[i + 2] = (byte) ((b5 << 6) | b6);
            } else if (b == 61) {
                bArr2[i] = (byte) ((b4 >> 4) | (b3 << 2));
            } else if (b2 == 61) {
                byte b7 = bArr3[b];
                bArr2[i] = (byte) ((b3 << 2) | (b4 >> 4));
                bArr2[i + 1] = (byte) (((b4 & 15) << 4) | ((b7 >> 2) & 15));
            }
            i += 3;
        }
        return bArr2;
    }

    private static boolean isBase64(byte b) {
        return b == 61 || base64Alphabet[b] != -1;
    }

    static byte[] discardNonBase64(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (isBase64(bArr[i2])) {
                bArr2[i] = bArr[i2];
                i++;
            }
        }
        byte[] bArr3 = new byte[i];
        System.arraycopy(bArr2, 0, bArr3, 0, i);
        return bArr3;
    }
}
