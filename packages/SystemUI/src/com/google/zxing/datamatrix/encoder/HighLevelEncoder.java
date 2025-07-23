package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class HighLevelEncoder {
    private HighLevelEncoder() {
    }

    public static int findMinimums(float[] fArr, int[] iArr, byte[] bArr) {
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < 6; i2++) {
            int ceil = (int) Math.ceil(fArr[i2]);
            iArr[i2] = ceil;
            if (i > ceil) {
                Arrays.fill(bArr, (byte) 0);
                i = ceil;
            }
            if (i == ceil) {
                bArr[i2] = (byte) (bArr[i2] + 1);
            }
        }
        return i;
    }

    public static void illegalCharacter(char c) {
        String hexString = Integer.toHexString(c);
        throw new IllegalArgumentException("Illegal character: " + c + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isExtendedASCII(char c) {
        return c >= 128 && c <= 255;
    }

    public static boolean isNativeC40(char c) {
        if (c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    public static boolean isNativeEDIFACT(char c) {
        return c >= ' ' && c <= '^';
    }

    public static boolean isNativeText(char c) {
        if (c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'a' && c <= 'z';
        }
        return true;
    }

    public static boolean isNativeX12(char c) {
        if (c == '\r' || c == '*' || c == '>' || c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x0236, code lost:
    
        r3 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0260, code lost:
    
        r3 = r21;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0290  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int lookAheadTest(int r22, int r23, java.lang.CharSequence r24) {
        /*
            Method dump skipped, instructions count: 688
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.HighLevelEncoder.lookAheadTest(int, int, java.lang.CharSequence):int");
    }
}
