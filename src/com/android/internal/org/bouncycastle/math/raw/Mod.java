package com.android.internal.org.bouncycastle.math.raw;

import com.android.internal.org.bouncycastle.util.Integers;
import java.util.Random;

/* loaded from: classes5.dex */
public abstract class Mod {
    private static final int M30 = 1073741823;
    private static final long M32L = 4294967295L;

    public static int inverse32(int i) {
        int i2 = (2 - (i * i)) * i;
        int i3 = i2 * (2 - (i * i2));
        int i4 = i3 * (2 - (i * i3));
        return i4 * (2 - (i * i4));
    }

    public static void checkedModOddInverse(int[] iArr, int[] iArr2, int[] iArr3) {
        if (modOddInverse(iArr, iArr2, iArr3) == 0) {
            throw new ArithmeticException("Inverse does not exist.");
        }
    }

    public static void checkedModOddInverseVar(int[] iArr, int[] iArr2, int[] iArr3) {
        if (!modOddInverseVar(iArr, iArr2, iArr3)) {
            throw new ArithmeticException("Inverse does not exist.");
        }
    }

    public static int modOddInverse(int[] iArr, int[] iArr2, int[] iArr3) {
        int length = iArr.length;
        int iNumberOfLeadingZeros = (length << 5) - Integers.numberOfLeadingZeros(iArr[length - 1]);
        int i = (iNumberOfLeadingZeros + 29) / 30;
        int[] iArr4 = new int[4];
        int[] iArr5 = new int[i];
        int[] iArr6 = new int[i];
        int[] iArr7 = new int[i];
        int[] iArr8 = new int[i];
        int[] iArr9 = new int[i];
        iArr6[0] = 1;
        encode30(iNumberOfLeadingZeros, iArr2, 0, iArr8, 0);
        encode30(iNumberOfLeadingZeros, iArr, 0, iArr9, 0);
        System.arraycopy(iArr9, 0, iArr7, 0, i);
        int iInverse32 = inverse32(iArr9[0]);
        int maximumDivsteps = getMaximumDivsteps(iNumberOfLeadingZeros);
        int iDivsteps30 = 0;
        for (int i2 = 0; i2 < maximumDivsteps; i2 += 30) {
            iDivsteps30 = divsteps30(iDivsteps30, iArr7[0], iArr8[0], iArr4);
            updateDE30(i, iArr5, iArr6, iArr4, iInverse32, iArr9);
            updateFG30(i, iArr7, iArr8, iArr4);
        }
        int i3 = iArr7[i - 1] >> 31;
        cnegate30(i, i3, iArr7);
        cnormalize30(i, i3, iArr5, iArr9);
        decode30(iNumberOfLeadingZeros, iArr5, 0, iArr3, 0);
        return Nat.equalTo(i, iArr7, 1) & Nat.equalToZero(i, iArr8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r17v1 */
    public static boolean modOddInverseVar(int[] iArr, int[] iArr2, int[] iArr3) {
        int length = iArr.length;
        boolean z = true;
        int iNumberOfLeadingZeros = (length << 5) - Integers.numberOfLeadingZeros(iArr[length - 1]);
        int i = (iNumberOfLeadingZeros + 29) / 30;
        int[] iArr4 = new int[4];
        int[] iArr5 = new int[i];
        int[] iArr6 = new int[i];
        int[] iArr7 = new int[i];
        int[] iArr8 = new int[i];
        int[] iArr9 = new int[i];
        ?? r11 = 0;
        iArr6[0] = 1;
        encode30(iNumberOfLeadingZeros, iArr2, 0, iArr8, 0);
        encode30(iNumberOfLeadingZeros, iArr, 0, iArr9, 0);
        System.arraycopy(iArr9, 0, iArr7, 0, i);
        int i2 = i - 1;
        int iNumberOfLeadingZeros2 = (-1) - (Integers.numberOfLeadingZeros(iArr8[i2] | 1) - (((i * 30) + 2) - iNumberOfLeadingZeros));
        int iInverse32 = inverse32(iArr9[0]);
        int maximumDivsteps = getMaximumDivsteps(iNumberOfLeadingZeros);
        int i3 = i;
        int i4 = 0;
        while (!Nat.isZero(i3, iArr8)) {
            if (i4 >= maximumDivsteps) {
                return r11;
            }
            i4 += 30;
            boolean z2 = z;
            ?? r17 = r11;
            int iDivsteps30Var = divsteps30Var(iNumberOfLeadingZeros2, iArr7[r11], iArr8[r17], iArr4);
            int i5 = iInverse32;
            updateDE30(i, iArr5, iArr6, iArr4, i5, iArr9);
            updateFG30(i3, iArr7, iArr8, iArr4);
            int i6 = i3 - 1;
            int i7 = iArr7[i6];
            int i8 = iArr8[i6];
            int i9 = i3 - 2;
            if (((i9 >> 31) | (i7 ^ (i7 >> 31)) | (i8 ^ (i8 >> 31))) == 0) {
                iArr7[i9] = iArr7[i9] | (i7 << 30);
                iArr8[i9] = (i8 << 30) | iArr8[i9];
                i3--;
            }
            iInverse32 = i5;
            r11 = r17;
            iNumberOfLeadingZeros2 = iDivsteps30Var;
            z = z2;
        }
        boolean z3 = z;
        boolean z4 = r11;
        int i10 = iArr7[i3 - 1] >> 31;
        int iNegate30 = iArr5[i2] >> 31;
        if (iNegate30 < 0) {
            iNegate30 = add30(i, iArr5, iArr9);
        }
        if (i10 < 0) {
            iNegate30 = negate30(i, iArr5);
            negate30(i3, iArr7);
        }
        if (!Nat.isOne(i3, iArr7)) {
            return z4;
        }
        if (iNegate30 < 0) {
            add30(i, iArr5, iArr9);
        }
        decode30(iNumberOfLeadingZeros, iArr5, z4 ? 1 : 0, iArr3, z4 ? 1 : 0);
        return z3;
    }

    public static int[] random(int[] iArr) {
        int length = iArr.length;
        Random random = new Random();
        int[] iArrCreate = Nat.create(length);
        int i = length - 1;
        int i2 = iArr[i];
        int i3 = i2 | (i2 >>> 1);
        int i4 = i3 | (i3 >>> 2);
        int i5 = i4 | (i4 >>> 4);
        int i6 = i5 | (i5 >>> 8);
        int i7 = i6 | (i6 >>> 16);
        do {
            for (int i8 = 0; i8 != length; i8++) {
                iArrCreate[i8] = random.nextInt();
            }
            iArrCreate[i] = iArrCreate[i] & i7;
        } while (Nat.gte(length, iArrCreate, iArr));
        return iArrCreate;
    }

    private static int add30(int i, int[] iArr, int[] iArr2) {
        int i2 = i - 1;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i3 + iArr[i4] + iArr2[i4];
            iArr[i4] = 1073741823 & i5;
            i3 = i5 >> 30;
        }
        int i6 = i3 + iArr[i2] + iArr2[i2];
        iArr[i2] = i6;
        return i6 >> 30;
    }

    private static void cnegate30(int i, int i2, int[] iArr) {
        int i3 = i - 1;
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = i4 + ((iArr[i5] ^ i2) - i2);
            iArr[i5] = 1073741823 & i6;
            i4 = i6 >> 30;
        }
        iArr[i3] = i4 + ((iArr[i3] ^ i2) - i2);
    }

    private static void cnormalize30(int i, int i2, int[] iArr, int[] iArr2) {
        int i3 = i - 1;
        int i4 = iArr[i3] >> 31;
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = i5 + (((iArr[i6] + (iArr2[i6] & i4)) ^ i2) - i2);
            iArr[i6] = 1073741823 & i7;
            i5 = i7 >> 30;
        }
        int i8 = i5 + (((iArr[i3] + (i4 & iArr2[i3])) ^ i2) - i2);
        iArr[i3] = i8;
        int i9 = i8 >> 31;
        int i10 = 0;
        for (int i11 = 0; i11 < i3; i11++) {
            int i12 = i10 + iArr[i11] + (iArr2[i11] & i9);
            iArr[i11] = i12 & 1073741823;
            i10 = i12 >> 30;
        }
        iArr[i3] = i10 + iArr[i3] + (i9 & iArr2[i3]);
    }

    private static void decode30(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = 0;
        long j = 0;
        while (i > 0) {
            while (i4 < Math.min(32, i)) {
                j |= iArr[i2] << i4;
                i4 += 30;
                i2++;
            }
            iArr2[i3] = (int) j;
            j >>>= 32;
            i4 -= 32;
            i -= 32;
            i3++;
        }
    }

    private static int divsteps30(int i, int i2, int i3, int[] iArr) {
        int i4 = 1073741824;
        int i5 = 1073741824;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < 30; i8++) {
            int i9 = i >> 31;
            int i10 = -(i3 & 1);
            int i11 = i3 - ((i2 ^ i9) & i10);
            int i12 = i7 - ((i4 ^ i9) & i10);
            int i13 = i5 - ((i6 ^ i9) & i10);
            int i14 = (~i9) & i10;
            i = (i ^ i14) - (i14 - 1);
            i2 += i11 & i14;
            i4 += i12 & i14;
            i6 += i14 & i13;
            i3 = i11 >> 1;
            i7 = i12 >> 1;
            i5 = i13 >> 1;
        }
        iArr[0] = i4;
        iArr[1] = i6;
        iArr[2] = i7;
        iArr[3] = i5;
        return i;
    }

    private static int divsteps30Var(int i, int i2, int i3, int[] iArr) {
        int i4;
        int i5 = 30;
        int i6 = 1;
        int i7 = 1;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            int iNumberOfTrailingZeros = Integers.numberOfTrailingZeros(((-1) << i5) | i3);
            int i10 = i3 >> iNumberOfTrailingZeros;
            i6 <<= iNumberOfTrailingZeros;
            i8 <<= iNumberOfTrailingZeros;
            i -= iNumberOfTrailingZeros;
            i5 -= iNumberOfTrailingZeros;
            if (i5 <= 0) {
                iArr[0] = i6;
                iArr[1] = i8;
                iArr[2] = i9;
                iArr[3] = i7;
                return i;
            }
            if (i < 0) {
                i = -i;
                int i11 = -i2;
                int i12 = -i6;
                int i13 = -i8;
                int i14 = i + 1;
                if (i14 > i5) {
                    i14 = i5;
                }
                i4 = ((-1) >>> (32 - i14)) & 63 & (i10 * i11 * ((i10 * i10) - 2));
                i10 = i11;
                i2 = i10;
                int i15 = i9;
                i9 = i12;
                i6 = i15;
                int i16 = i7;
                i7 = i13;
                i8 = i16;
            } else {
                int i17 = i + 1;
                if (i17 > i5) {
                    i17 = i5;
                }
                i4 = ((-1) >>> (32 - i17)) & 15 & ((-((((i2 + 1) & 4) << 1) + i2)) * i10);
            }
            i3 = i10 + (i2 * i4);
            i9 += i6 * i4;
            i7 += i4 * i8;
        }
    }

    private static void encode30(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = 0;
        long j = 0;
        while (i > 0) {
            if (i4 < Math.min(30, i)) {
                j |= (iArr[i2] & 4294967295L) << i4;
                i4 += 32;
                i2++;
            }
            iArr2[i3] = ((int) j) & 1073741823;
            j >>>= 30;
            i4 -= 30;
            i -= 30;
            i3++;
        }
    }

    private static int getMaximumDivsteps(int i) {
        return ((i * 49) + (i < 46 ? 80 : 47)) / 17;
    }

    private static int negate30(int i, int[] iArr) {
        int i2 = i - 1;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i3 - iArr[i4];
            iArr[i4] = 1073741823 & i5;
            i3 = i5 >> 30;
        }
        int i6 = i3 - iArr[i2];
        iArr[i2] = i6;
        return i6 >> 30;
    }

    private static void updateDE30(int i, int[] iArr, int[] iArr2, int[] iArr3, int i2, int[] iArr4) {
        int i3 = i;
        int i4 = iArr3[0];
        int i5 = iArr3[1];
        int i6 = iArr3[2];
        int i7 = iArr3[3];
        int i8 = i3 - 1;
        int i9 = iArr[i8] >> 31;
        int i10 = iArr2[i8] >> 31;
        int i11 = (i4 & i9) + (i5 & i10);
        int i12 = (i9 & i6) + (i10 & i7);
        int i13 = iArr4[0];
        long j = i4;
        long j2 = iArr[0];
        long j3 = i5;
        long j4 = iArr2[0];
        long j5 = (j * j2) + (j3 * j4);
        long j6 = i6;
        long j7 = i7;
        long j8 = (j2 * j6) + (j7 * j4);
        int i14 = i11 - (((((int) j5) * i2) + i11) & 1073741823);
        long j9 = i13;
        long j10 = i14;
        long j11 = j5 + (j9 * j10);
        long j12 = i12 - (((((int) j8) * i2) + i12) & 1073741823);
        long j13 = (j8 + (j9 * j12)) >> 30;
        int i15 = 1;
        long j14 = j11 >> 30;
        while (i15 < i3) {
            int i16 = iArr4[i15];
            long j15 = j12;
            long j16 = j6;
            long j17 = iArr[i15];
            long j18 = iArr2[i15];
            long j19 = (j * j17) + (j3 * j18);
            long j20 = i16;
            long j21 = j14 + j19 + (j20 * j10);
            long j22 = j13 + (j16 * j17) + (j7 * j18) + (j20 * j15);
            int i17 = i15 - 1;
            iArr[i17] = ((int) j21) & 1073741823;
            j14 = j21 >> 30;
            iArr2[i17] = ((int) j22) & 1073741823;
            j13 = j22 >> 30;
            i15++;
            i3 = i;
            j12 = j15;
            j6 = j16;
        }
        iArr[i8] = (int) j14;
        iArr2[i8] = (int) j13;
    }

    private static void updateFG30(int i, int[] iArr, int[] iArr2, int[] iArr3) {
        int i2 = i;
        int i3 = iArr3[0];
        boolean z = true;
        int i4 = iArr3[1];
        int i5 = iArr3[2];
        int i6 = iArr3[3];
        long j = i3;
        long j2 = iArr[0];
        long j3 = i4;
        long j4 = iArr2[0];
        long j5 = (j * j2) + (j3 * j4);
        long j6 = i5;
        long j7 = i6;
        long j8 = (j2 * j6) + (j4 * j7);
        char c = 30;
        long j9 = j5 >> 30;
        long j10 = j8 >> 30;
        int i7 = 1;
        while (i7 < i2) {
            char c2 = c;
            long j11 = iArr[i7];
            long j12 = j * j11;
            long j13 = iArr2[i7];
            long j14 = j9 + j12 + (j3 * j13);
            long j15 = j10 + (j6 * j11) + (j13 * j7);
            int i8 = i7 - 1;
            iArr[i8] = ((int) j14) & 1073741823;
            j9 = j14 >> c2;
            iArr2[i8] = ((int) j15) & 1073741823;
            j10 = j15 >> c2;
            i7++;
            i2 = i;
            c = c2;
            z = z;
        }
        int i9 = i - 1;
        iArr[i9] = (int) j9;
        iArr2[i9] = (int) j10;
    }
}
