package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HighLevelEncoder {
    private HighLevelEncoder() {
    }

    public static int findMinimums(float[] fArr, int[] iArr, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
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

    public static boolean isExtendedASCII(char c) {
        return c >= 128 && c <= 255;
    }

    public static boolean isNativeX12(char c) {
        return (c == '\r' || c == '*' || c == '>') || c == ' ' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    /* JADX WARN: Code restructure failed: missing block: B:127:0x01f6, code lost:
    
        return 5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int lookAheadTest(int i, int i2, CharSequence charSequence) {
        float[] fArr;
        int i3;
        String str = (String) charSequence;
        if (i >= str.length()) {
            return i2;
        }
        if (i2 == 0) {
            fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
        } else {
            fArr = new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.25f};
            fArr[i2] = 0.0f;
        }
        int i4 = 0;
        while (true) {
            int i5 = i + i4;
            if (i5 == str.length()) {
                byte[] bArr = new byte[6];
                int[] iArr = new int[6];
                int findMinimums = findMinimums(fArr, iArr, bArr);
                int i6 = 0;
                for (int i7 = 0; i7 < 6; i7++) {
                    i6 += bArr[i7];
                }
                if (iArr[0] == findMinimums) {
                    return 0;
                }
                if (i6 == 1 && bArr[5] > 0) {
                    return 5;
                }
                if (i6 == 1 && bArr[4] > 0) {
                    return 4;
                }
                if (i6 != 1 || bArr[2] <= 0) {
                    return (i6 != 1 || bArr[3] <= 0) ? 1 : 3;
                }
                return 2;
            }
            char charAt = str.charAt(i5);
            i4++;
            if (charAt >= '0' && charAt <= '9') {
                fArr[0] = (float) (fArr[0] + 0.5d);
            } else if (isExtendedASCII(charAt)) {
                float ceil = (int) Math.ceil(fArr[0]);
                fArr[0] = ceil;
                fArr[0] = ceil + 2.0f;
            } else {
                float ceil2 = (int) Math.ceil(fArr[0]);
                fArr[0] = ceil2;
                fArr[0] = ceil2 + 1.0f;
            }
            if (charAt == ' ' || (charAt >= '0' && charAt <= '9') || (charAt >= 'A' && charAt <= 'Z')) {
                fArr[1] = fArr[1] + 0.6666667f;
            } else if (isExtendedASCII(charAt)) {
                fArr[1] = fArr[1] + 2.6666667f;
            } else {
                fArr[1] = fArr[1] + 1.3333334f;
            }
            if (charAt == ' ' || (charAt >= '0' && charAt <= '9') || (charAt >= 'a' && charAt <= 'z')) {
                fArr[2] = fArr[2] + 0.6666667f;
            } else if (isExtendedASCII(charAt)) {
                fArr[2] = fArr[2] + 2.6666667f;
            } else {
                fArr[2] = fArr[2] + 1.3333334f;
            }
            if (isNativeX12(charAt)) {
                fArr[3] = fArr[3] + 0.6666667f;
            } else if (isExtendedASCII(charAt)) {
                fArr[3] = fArr[3] + 4.3333335f;
            } else {
                fArr[3] = fArr[3] + 3.3333333f;
            }
            if (charAt >= ' ' && charAt <= '^') {
                i3 = 4;
                fArr[4] = fArr[4] + 0.75f;
            } else {
                i3 = 4;
                if (isExtendedASCII(charAt)) {
                    fArr[4] = fArr[4] + 4.25f;
                } else {
                    fArr[4] = fArr[4] + 3.25f;
                }
            }
            fArr[5] = fArr[5] + 1.0f;
            if (i4 >= i3) {
                int[] iArr2 = new int[6];
                byte[] bArr2 = new byte[6];
                findMinimums(fArr, iArr2, bArr2);
                int i8 = 0;
                for (int i9 = 0; i9 < 6; i9++) {
                    i8 += bArr2[i9];
                }
                int i10 = iArr2[0];
                int i11 = iArr2[5];
                if (i10 < i11 && i10 < iArr2[1] && i10 < iArr2[2] && i10 < iArr2[3] && i10 < iArr2[4]) {
                    return 0;
                }
                if (i11 < i10) {
                    break;
                }
                byte b = bArr2[1];
                byte b2 = bArr2[2];
                byte b3 = bArr2[3];
                byte b4 = bArr2[4];
                if (b + b2 + b3 + b4 == 0) {
                    break;
                }
                if (i8 == 1 && b4 > 0) {
                    return 4;
                }
                if (i8 == 1 && b2 > 0) {
                    return 2;
                }
                if (i8 == 1 && b3 > 0) {
                    return 3;
                }
                int i12 = iArr2[1];
                int i13 = i12 + 1;
                if (i13 < i10 && i13 < i11 && i13 < iArr2[4] && i13 < iArr2[2]) {
                    int i14 = iArr2[3];
                    if (i12 < i14) {
                        return 1;
                    }
                    if (i12 == i14) {
                        for (int i15 = i + i4 + 1; i15 < str.length(); i15++) {
                            char charAt2 = str.charAt(i15);
                            if (charAt2 == '\r' || charAt2 == '*' || charAt2 == '>') {
                                return 3;
                            }
                            if (!isNativeX12(charAt2)) {
                                break;
                            }
                        }
                        return 1;
                    }
                }
            }
        }
    }
}
