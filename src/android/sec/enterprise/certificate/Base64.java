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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x007e, code lost:
    
        if (r9 != (-3)) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0084, code lost:
    
        return r0.toByteArray();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0085, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] decode(byte[] bArr, int i) {
        int iMin = Math.min(bArr.length, i);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(((iMin / 4) * 3) + 3);
        int[] iArr = new int[1];
        loop0: while (true) {
            try {
                int i2 = iArr[0];
                if (i2 < iMin) {
                    int i3 = 0;
                    for (int i4 = 0; i4 < 4; i4++) {
                        byte nextByte = getNextByte(bArr, iArr, iMin);
                        if (nextByte == -3 || nextByte == -1) {
                            if (i4 == 0 || i4 == 1) {
                                break loop0;
                            }
                            if (i4 == 2) {
                                if (nextByte == -3) {
                                    return checkNoTrailingAndReturn(byteArrayOutputStream, bArr, iArr[0], iMin);
                                }
                                iArr[0] = iArr[0] + 1;
                                byte nextByte2 = getNextByte(bArr, iArr, iMin);
                                if (nextByte2 == -3) {
                                    return checkNoTrailingAndReturn(byteArrayOutputStream, bArr, iArr[0], iMin);
                                }
                                if (nextByte2 != -1) {
                                    return null;
                                }
                                byteArrayOutputStream.write(i3 >> 4);
                                return checkNoTrailingAndReturn(byteArrayOutputStream, bArr, iArr[0], iMin);
                            }
                            if (i4 == 3) {
                                if (nextByte == -1) {
                                    byteArrayOutputStream.write(i3 >> 10);
                                    byteArrayOutputStream.write((i3 >> 2) & 255);
                                }
                                return checkNoTrailingAndReturn(byteArrayOutputStream, bArr, iArr[0], iMin);
                            }
                        } else {
                            i3 = (i3 << 6) + (nextByte & 255);
                            iArr[0] = iArr[0] + 1;
                        }
                    }
                    byteArrayOutputStream.write(i3 >> 16);
                    byteArrayOutputStream.write((i3 >> 8) & 255);
                    byteArrayOutputStream.write(i3 & 255);
                } else {
                    return checkNoTrailingAndReturn(byteArrayOutputStream, bArr, i2, iMin);
                }
            } catch (InvalidBase64ByteException unused) {
                return null;
            }
        }
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
            byte bBase64AlphabetToNumericalValue = base64AlphabetToNumericalValue(bArr[i2]);
            if (bBase64AlphabetToNumericalValue != -2) {
                return bBase64AlphabetToNumericalValue;
            }
            iArr[0] = iArr[0] + 1;
        }
    }

    private static byte[] checkNoTrailingAndReturn(ByteArrayOutputStream byteArrayOutputStream, byte[] bArr, int i, int i2) throws InvalidBase64ByteException {
        while (i < i2) {
            byte bBase64AlphabetToNumericalValue = base64AlphabetToNumericalValue(bArr[i]);
            if (bBase64AlphabetToNumericalValue != -2 && bBase64AlphabetToNumericalValue != -1) {
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
