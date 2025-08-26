package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

/* loaded from: classes4.dex */
public final class HighLevelEncoder {
    private HighLevelEncoder() {
    }

    public static int findMinimums(float[] fArr, int[] iArr, byte[] bArr) {
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < 6; i2++) {
            int iCeil = (int) Math.ceil(fArr[i2]);
            iArr[i2] = iCeil;
            if (i > iCeil) {
                Arrays.fill(bArr, (byte) 0);
                i = iCeil;
            }
            if (i == iCeil) {
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

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0260, code lost:
    
        r3 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0087, code lost:
    
        r3 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0236, code lost:
    
        r3 = r19;
     */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0290  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int lookAheadTest(int i, int i2, CharSequence charSequence) {
        int i3;
        float[] fArr;
        int i4;
        int i5;
        int i6;
        char cCharAt;
        int iMin;
        int i7 = i;
        int i8 = 0;
        i8 = 0;
        if (i7 < charSequence.length()) {
            i3 = 1;
            int i9 = 6;
            float f = 1.0f;
            int i10 = 5;
            if (i2 == 0) {
                fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
            } else {
                fArr = new float[6];
                fArr[0] = 1.0f;
                fArr[1] = 2.0f;
                fArr[2] = 2.0f;
                fArr[3] = 2.0f;
                fArr[4] = 2.0f;
                fArr[5] = 2.25f;
                fArr[i2] = 0.0f;
            }
            byte[] bArr = new byte[6];
            int[] iArr = new int[6];
            int i11 = 0;
            while (true) {
                int i12 = i7 + i11;
                float f2 = f;
                if (i12 == charSequence.length()) {
                    Arrays.fill(bArr, i8 == true ? (byte) 1 : (byte) 0);
                    Arrays.fill(iArr, i8 == true ? 1 : 0);
                    int iFindMinimums = findMinimums(fArr, iArr, bArr);
                    int i13 = i8 == true ? 1 : 0;
                    int i14 = i13;
                    while (i13 < i9) {
                        i14 += bArr[i13];
                        i13++;
                    }
                    if (iArr[i8 == true ? 1 : 0] == iFindMinimums) {
                        i3 = i8 == true ? 1 : 0;
                        i4 = i3;
                    } else if (i14 == i3) {
                        if (bArr[i10] > 0) {
                            i4 = i8 == true ? 1 : 0;
                            i3 = i10;
                        } else if (bArr[4] > 0) {
                            i3 = 4;
                        } else if (bArr[2] > 0) {
                            i4 = i8 == true ? 1 : 0;
                        } else if (bArr[3] > 0) {
                            i3 = 3;
                        }
                    }
                } else {
                    char cCharAt2 = charSequence.charAt(i12);
                    i11++;
                    if (isDigit(cCharAt2)) {
                        fArr[i8 == true ? 1 : 0] = fArr[i8 == true ? 1 : 0] + 0.5f;
                    } else if (isExtendedASCII(cCharAt2)) {
                        float fCeil = (float) Math.ceil(fArr[i8 == true ? 1 : 0]);
                        fArr[i8 == true ? 1 : 0] = fCeil;
                        fArr[i8 == true ? 1 : 0] = fCeil + 2.0f;
                    } else {
                        float fCeil2 = (float) Math.ceil(fArr[i8 == true ? 1 : 0]);
                        fArr[i8 == true ? 1 : 0] = fCeil2;
                        fArr[i8 == true ? 1 : 0] = fCeil2 + f2;
                    }
                    if (isNativeC40(cCharAt2)) {
                        fArr[i3] = fArr[i3] + 0.6666667f;
                    } else if (isExtendedASCII(cCharAt2)) {
                        fArr[i3] = fArr[i3] + 2.6666667f;
                    } else {
                        fArr[i3] = fArr[i3] + 1.3333334f;
                    }
                    if (isNativeText(cCharAt2)) {
                        fArr[2] = fArr[2] + 0.6666667f;
                    } else if (isExtendedASCII(cCharAt2)) {
                        fArr[2] = fArr[2] + 2.6666667f;
                    } else {
                        fArr[2] = fArr[2] + 1.3333334f;
                    }
                    if (isNativeX12(cCharAt2)) {
                        fArr[3] = fArr[3] + 0.6666667f;
                    } else if (isExtendedASCII(cCharAt2)) {
                        fArr[3] = fArr[3] + 4.3333335f;
                    } else {
                        fArr[3] = fArr[3] + 3.3333333f;
                    }
                    if (isNativeEDIFACT(cCharAt2)) {
                        fArr[4] = fArr[4] + 0.75f;
                    } else if (isExtendedASCII(cCharAt2)) {
                        fArr[4] = fArr[4] + 4.25f;
                    } else {
                        fArr[4] = fArr[4] + 3.25f;
                    }
                    fArr[i10] = fArr[i10] + f2;
                    if (i11 >= 4) {
                        Arrays.fill(bArr, i8 == true ? (byte) 1 : (byte) 0);
                        Arrays.fill(iArr, i8 == true ? 1 : 0);
                        findMinimums(fArr, iArr, bArr);
                        int i15 = iArr[i8 == true ? 1 : 0];
                        int i16 = iArr[i10];
                        int i17 = iArr[i3];
                        i5 = i3;
                        int i18 = iArr[2];
                        i4 = i8 == true ? 1 : 0;
                        i6 = i10;
                        if (i15 >= Math.min(Math.min(i16, Math.min(i17, Math.min(i18, iArr[3]))), iArr[4])) {
                            int i19 = iArr[i6];
                            if (i19 >= iArr[i4] && i19 + 1 >= Math.min(iArr[i5], Math.min(iArr[2], Math.min(iArr[3], iArr[4])))) {
                                if (iArr[4] + 1 >= Math.min(Math.min(iArr[i6], Math.min(iArr[i5], Math.min(iArr[2], iArr[3]))), iArr[i4])) {
                                    if (iArr[2] + 1 >= Math.min(Math.min(iArr[i6], Math.min(iArr[i5], Math.min(iArr[4], iArr[3]))), iArr[i4])) {
                                        if (iArr[3] + 1 < Math.min(Math.min(iArr[i6], Math.min(iArr[i5], Math.min(iArr[4], iArr[2]))), iArr[i4])) {
                                            break;
                                        }
                                        if (iArr[i5] + 1 < Math.min(iArr[i4], Math.min(iArr[i6], Math.min(iArr[4], iArr[2])))) {
                                            int i20 = iArr[i5];
                                            int i21 = iArr[3];
                                            if (i20 < i21) {
                                                break;
                                            }
                                            if (i20 == i21) {
                                                int i22 = i7 + i11;
                                                do {
                                                    i22++;
                                                    if (i22 >= charSequence.length()) {
                                                        break;
                                                    }
                                                    cCharAt = charSequence.charAt(i22);
                                                    if (cCharAt == '\r' || cCharAt == '*' || cCharAt == '>') {
                                                    }
                                                } while (isNativeX12(cCharAt));
                                            }
                                        } else {
                                            continue;
                                        }
                                    } else {
                                        break;
                                    }
                                } else {
                                    i3 = 4;
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            i3 = i4;
                            break;
                        }
                    } else {
                        i5 = i3;
                        i4 = i8 == true ? 1 : 0;
                        i6 = i10;
                    }
                    f = f2;
                    i3 = i5;
                    i8 = i4;
                    i10 = i6;
                    i9 = 6;
                }
            }
            i3 = 3;
            if (i2 != 3 && i3 == 3) {
                int iMin2 = Math.min(i7 + 3, charSequence.length());
                while (i7 < iMin2) {
                    if (!isNativeX12(charSequence.charAt(i7))) {
                        return i4;
                    }
                    i7++;
                }
                return i3;
            }
            if (i2 == 4 && i3 == 4) {
                iMin = Math.min(i7 + 4, charSequence.length());
                while (i7 < iMin) {
                    if (!isNativeEDIFACT(charSequence.charAt(i7))) {
                        return i4;
                    }
                    i7++;
                }
            }
            return i3;
        }
        i3 = i2;
        i4 = i8;
        if (i2 != 3) {
        }
        if (i2 == 4) {
            iMin = Math.min(i7 + 4, charSequence.length());
            while (i7 < iMin) {
            }
        }
        return i3;
    }
}
