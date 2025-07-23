package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class XofUtils {
    public static byte[] leftEncode(long j) {
        long j2 = j;
        byte b = 1;
        while (true) {
            j2 >>= 8;
            if (j2 == 0) {
                break;
            }
            b = (byte) (b + 1);
        }
        byte[] bArr = new byte[b + 1];
        bArr[0] = b;
        for (int i = 1; i <= b; i++) {
            bArr[i] = (byte) (j >> ((b - i) * 8));
        }
        return bArr;
    }

    public static byte[] rightEncode(long j) {
        long j2 = j;
        byte b = 1;
        while (true) {
            j2 >>= 8;
            if (j2 == 0) {
                break;
            }
            b = (byte) (b + 1);
        }
        byte[] bArr = new byte[b + 1];
        bArr[b] = b;
        for (int i = 0; i < b; i++) {
            bArr[i] = (byte) (j >> (((b - i) - 1) * 8));
        }
        return bArr;
    }

    static byte[] encode(byte b) {
        return Arrays.concatenate(leftEncode(8L), new byte[]{b});
    }

    static byte[] encode(byte[] bArr, int i, int i2) {
        if (bArr.length == i2) {
            return Arrays.concatenate(leftEncode(i2 * 8), bArr);
        }
        return Arrays.concatenate(leftEncode(i2 * 8), Arrays.copyOfRange(bArr, i, i2 + i));
    }
}
