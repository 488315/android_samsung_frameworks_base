package android.sec.enterprise.certificate;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/* loaded from: classes3.dex */
public final class Base64 {
    private static final byte[] BASE_64_ALPHABET = initializeBase64Alphabet();
    private static final byte END_OF_INPUT = -3;
    private static final int FIRST_OUTPUT_BYTE_MASK = 16515072;
    private static final int FOURTH_OUTPUT_BYTE_MASK = 63;
    private static final byte PAD_AS_BYTE = -1;
    private static final int SECOND_OUTPUT_BYTE_MASK = 258048;
    private static final int THIRD_OUTPUT_BYTE_MASK = 4032;
    private static final byte WHITESPACE_AS_BYTE = -2;

    private static byte[] initializeBase64Alphabet() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes(StandardCharsets.US_ASCII);
    }

    private Base64() {
    }

    public static String encode(byte[] bArr) {
        int i;
        int length = bArr.length;
        byte[] bArr2 = new byte[computeEncodingOutputLen(length)];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int i4 = bArr[i3] & 255;
            int i5 = i3 + 1;
            if (i5 < length) {
                int i6 = (i4 << 8) | (bArr[i5] & 255);
                int i7 = i3 + 2;
                i = i7 < length ? (i6 << 8) | (bArr[i7] & 255) : i6 << 2;
            } else {
                i = i4 << 4;
            }
            if (i3 + 2 < length) {
                bArr2[i2] = BASE_64_ALPHABET[(FIRST_OUTPUT_BYTE_MASK & i) >>> 18];
                i2++;
            }
            if (i5 < length) {
                bArr2[i2] = BASE_64_ALPHABET[(SECOND_OUTPUT_BYTE_MASK & i) >>> 12];
                i2++;
            }
            int i8 = i2 + 1;
            byte[] bArr3 = BASE_64_ALPHABET;
            bArr2[i2] = bArr3[(i & THIRD_OUTPUT_BYTE_MASK) >>> 6];
            i2 += 2;
            bArr2[i8] = bArr3[i & 63];
        }
        int i9 = length % 3;
        if (i9 > 0) {
            int i10 = i2 + 1;
            bArr2[i2] = 61;
            if (i9 == 1) {
                bArr2[i10] = 61;
            }
        }
        return new String(bArr2, StandardCharsets.US_ASCII);
    }

    private static int computeEncodingOutputLen(int i) {
        int i2 = i % 3;
        int i3 = (i / 3) * 4;
        return (i2 == 2 || i2 == 1) ? i3 + 4 : i3;
    }

    public static byte[] decode(byte[] bArr) {
        return decode(bArr, bArr.length);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0035, code lost:
    
        if (r6 == 1) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0038, code lost:
    
        if (r6 == 2) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003a, code lost:
    
        if (r6 == 3) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003f, code lost:
    
        if (r9 != (-1)) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0041, code lost:
    
        r0.write(r7 >> 10);
        r0.write((r7 >> 2) & 255);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0053, code lost:
    
        return checkNoTrailingAndReturn(r0, r13, r3[0], r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0054, code lost:
    
        if (r9 != (-3)) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005c, code lost:
    
        return checkNoTrailingAndReturn(r0, r13, r3[0], r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005d, code lost:
    
        r3[0] = r3[0] + 1;
        r1 = getNextByte(r13, r3, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0066, code lost:
    
        if (r1 != (-3)) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006e, code lost:
    
        return checkNoTrailingAndReturn(r0, r13, r3[0], r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006f, code lost:
    
        if (r1 != (-1)) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0071, code lost:
    
        r0.write(r7 >> 4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x007c, code lost:
    
        return checkNoTrailingAndReturn(r0, r13, r3[0], r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x007d, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007e, code lost:
    
        if (r9 != (-3)) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0084, code lost:
    
        return r0.toByteArray();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0085, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] decode(byte[] r13, int r14) {
        /*
            int r0 = r13.length
            int r14 = java.lang.Math.min(r0, r14)
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            int r1 = r14 / 4
            r2 = 3
            int r1 = r1 * r2
            int r1 = r1 + r2
            r0.<init>(r1)
            r1 = 1
            int[] r3 = new int[r1]
        L12:
            r4 = 0
            r5 = 0
            r6 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            if (r6 >= r14) goto L99
            r6 = r5
            r7 = r6
        L1a:
            r8 = 4
            if (r6 >= r8) goto L86
            byte r9 = getNextByte(r13, r3, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            r10 = -1
            r11 = -3
            if (r9 == r11) goto L33
            if (r9 != r10) goto L28
            goto L33
        L28:
            int r7 = r7 << 6
            r8 = r9 & 255(0xff, float:3.57E-43)
            int r7 = r7 + r8
            r8 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            int r8 = r8 + r1
            r3[r5] = r8     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            goto L3c
        L33:
            if (r6 == 0) goto L7e
            if (r6 == r1) goto L7e
            r12 = 2
            if (r6 == r12) goto L54
            if (r6 == r2) goto L3f
        L3c:
            int r6 = r6 + 1
            goto L1a
        L3f:
            if (r9 != r10) goto L4d
            int r1 = r7 >> 2
            int r2 = r7 >> 10
            r0.write(r2)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0.write(r1)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
        L4d:
            r1 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            byte[] r13 = checkNoTrailingAndReturn(r0, r13, r1, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            return r13
        L54:
            if (r9 != r11) goto L5d
            r1 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            byte[] r13 = checkNoTrailingAndReturn(r0, r13, r1, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            return r13
        L5d:
            r2 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            int r2 = r2 + r1
            r3[r5] = r2     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            byte r1 = getNextByte(r13, r3, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            if (r1 != r11) goto L6f
            r1 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            byte[] r13 = checkNoTrailingAndReturn(r0, r13, r1, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            return r13
        L6f:
            if (r1 != r10) goto L7d
            int r1 = r7 >> 4
            r0.write(r1)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            r1 = r3[r5]     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            byte[] r13 = checkNoTrailingAndReturn(r0, r13, r1, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            return r13
        L7d:
            return r4
        L7e:
            if (r9 != r11) goto L85
            byte[] r13 = r0.toByteArray()     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            return r13
        L85:
            return r4
        L86:
            int r5 = r7 >> 16
            r0.write(r5)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            int r5 = r7 >> 8
            r5 = r5 & 255(0xff, float:3.57E-43)
            r0.write(r5)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            r5 = r7 & 255(0xff, float:3.57E-43)
            r0.write(r5)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            goto L12
        L99:
            byte[] r13 = checkNoTrailingAndReturn(r0, r13, r6, r14)     // Catch: android.sec.enterprise.certificate.Base64.InvalidBase64ByteException -> L9e
            return r13
        L9e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.enterprise.certificate.Base64.decode(byte[], int):byte[]");
    }

    private static class InvalidBase64ByteException extends Exception {
        private InvalidBase64ByteException() {
        }
    }

    private static byte getNextByte(byte[] bArr, int[] iArr, int i) throws InvalidBase64ByteException {
        while (true) {
            int i2 = iArr[0];
            if (i2 >= i) {
                return END_OF_INPUT;
            }
            byte base64AlphabetToNumericalValue = base64AlphabetToNumericalValue(bArr[i2]);
            if (base64AlphabetToNumericalValue != -2) {
                return base64AlphabetToNumericalValue;
            }
            iArr[0] = iArr[0] + 1;
        }
    }

    private static byte[] checkNoTrailingAndReturn(ByteArrayOutputStream byteArrayOutputStream, byte[] bArr, int i, int i2) throws InvalidBase64ByteException {
        while (i < i2) {
            byte base64AlphabetToNumericalValue = base64AlphabetToNumericalValue(bArr[i]);
            if (base64AlphabetToNumericalValue != -2 && base64AlphabetToNumericalValue != -1) {
                return null;
            }
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static byte base64AlphabetToNumericalValue(byte b) throws InvalidBase64ByteException {
        int i;
        if (65 <= b && b <= 90) {
            i = b - 65;
        } else if (97 <= b && b <= 122) {
            i = b - 71;
        } else {
            if (48 > b || b > 57) {
                if (b == 43) {
                    return (byte) 62;
                }
                if (b == 47) {
                    return (byte) 63;
                }
                if (b == 61) {
                    return (byte) -1;
                }
                if (b == 32 || b == 9 || b == 13 || b == 10) {
                    return (byte) -2;
                }
                throw new InvalidBase64ByteException();
            }
            i = b + 4;
        }
        return (byte) i;
    }
}
