package android.text;

import android.icu.text.Bidi;
import android.text.Layout;

/* loaded from: classes4.dex */
public class AndroidBidi {
    public static int bidi(int i, char[] cArr, byte[] bArr) {
        if (cArr == null || bArr == null) {
            throw null;
        }
        int length = cArr.length;
        if (bArr.length < length) {
            throw new IndexOutOfBoundsException();
        }
        byte b = i != -2 ? i != -1 ? (i == 1 || i != 2) ? (byte) 0 : (byte) 126 : (byte) 1 : Byte.MAX_VALUE;
        Bidi bidi = new Bidi(length, 0);
        bidi.setPara(cArr, b, (byte[]) null);
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = bidi.getLevelAt(i2);
        }
        return (bidi.getParaLevel() & 1) == 0 ? 1 : -1;
    }

    public static Layout.Directions directions(int i, byte[] bArr, int i2, char[] cArr, int i3, int i4) {
        int i5;
        int i6;
        if (i4 == 0) {
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        int i7 = i == 1 ? 0 : 1;
        int i8 = bArr[i2];
        int i9 = i2 + i4;
        int i10 = 1;
        int i11 = i8;
        for (int i12 = i2 + 1; i12 < i9; i12++) {
            int i13 = bArr[i12];
            if (i13 != i11) {
                i10++;
                i11 = i13;
            }
        }
        if ((i11 & 1) != i7) {
            int i14 = i4;
            while (true) {
                i6 = i14 - 1;
                if (i6 < 0) {
                    break;
                }
                char c = cArr[i3 + i6];
                if (c == '\n') {
                    i6 = i14 - 2;
                    break;
                }
                if (c != ' ' && c != '\t') {
                    break;
                }
                i14 = i6;
            }
            i5 = i6 + 1;
            if (i5 != i4) {
                i10++;
            }
        } else {
            i5 = i4;
        }
        if (i10 == 1 && i8 == i7) {
            if ((i8 & 1) != 0) {
                return Layout.DIRS_ALL_RIGHT_TO_LEFT;
            }
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        int i15 = i10 * 2;
        int[] iArr = new int[i15];
        int i16 = i2 + i5;
        int i17 = i2;
        int i18 = i17;
        int i19 = 1;
        int i20 = i8;
        int i21 = i8 << 26;
        int i22 = i20;
        while (i17 < i16) {
            int i23 = bArr[i17];
            if (i23 != i8) {
                if (i23 > i20) {
                    i20 = i23;
                } else if (i23 < i22) {
                    i22 = i23;
                }
                int i24 = i19 + 1;
                iArr[i19] = i21 | (i17 - i18);
                i19 += 2;
                iArr[i24] = i17 - i2;
                i21 = i23 << 26;
                i18 = i17;
                i8 = i23;
            }
            i17++;
        }
        iArr[i19] = (i16 - i18) | i21;
        if (i5 < i4) {
            iArr[i19 + 1] = i5;
            iArr[i19 + 2] = (i4 - i5) | (i7 << 26);
        }
        if ((i22 & 1) != i7 ? i10 > 1 : i20 > (i22 = i22 + 1)) {
            for (int i25 = i20 - 1; i25 >= i22; i25--) {
                int i26 = 0;
                while (i26 < i15) {
                    if (bArr[iArr[i26]] >= i25) {
                        int i27 = i26 + 2;
                        while (i27 < i15 && bArr[iArr[i27]] >= i25) {
                            i27 += 2;
                        }
                        for (int i28 = i27 - 2; i26 < i28; i28 -= 2) {
                            int i29 = iArr[i26];
                            iArr[i26] = iArr[i28];
                            iArr[i28] = i29;
                            int i30 = i26 + 1;
                            int i31 = iArr[i30];
                            int i32 = i28 + 1;
                            iArr[i30] = iArr[i32];
                            iArr[i32] = i31;
                            i26 += 2;
                        }
                        i26 = i27 + 2;
                    }
                    i26 += 2;
                }
            }
        }
        return new Layout.Directions(iArr);
    }
}
