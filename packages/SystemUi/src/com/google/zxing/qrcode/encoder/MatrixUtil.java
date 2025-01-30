package com.google.zxing.qrcode.encoder;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MatrixUtil {
    public static final int[][] POSITION_DETECTION_PATTERN = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};
    public static final int[][] POSITION_ADJUSTMENT_PATTERN = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};
    public static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, 102, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, 102, 126, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, 134, -1}, new int[]{6, 34, 60, 86, 112, 138, -1}, new int[]{6, 30, 58, 86, 114, 142, -1}, new int[]{6, 34, 62, 90, 118, 146, -1}, new int[]{6, 30, 54, 78, 102, 126, 150}, new int[]{6, 24, 50, 76, 102, 128, 154}, new int[]{6, 28, 54, 80, 106, 132, 158}, new int[]{6, 32, 58, 84, 110, 136, 162}, new int[]{6, 26, 54, 82, 110, 138, 166}, new int[]{6, 30, 58, 86, 114, 142, 170}};
    public static final int[][] TYPE_INFO_COORDINATES = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};

    private MatrixUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:144:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0260  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void buildMatrix(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, int i, ByteMatrix byteMatrix) {
        int i2;
        int i3;
        boolean z;
        int i4;
        int i5;
        boolean z2;
        int i6;
        int i7;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            i2 = byteMatrix.width;
            i3 = byteMatrix.height;
            if (i9 >= i3) {
                break;
            }
            for (int i10 = 0; i10 < i2; i10++) {
                byteMatrix.bytes[i9][i10] = -1;
            }
            i9++;
        }
        int length = POSITION_DETECTION_PATTERN[0].length;
        embedPositionDetectionPattern(0, 0, byteMatrix);
        int i11 = i2 - length;
        embedPositionDetectionPattern(i11, 0, byteMatrix);
        embedPositionDetectionPattern(0, i11, byteMatrix);
        embedHorizontalSeparationPattern(0, 7, byteMatrix);
        int i12 = i2 - 8;
        embedHorizontalSeparationPattern(i12, 7, byteMatrix);
        embedHorizontalSeparationPattern(0, i12, byteMatrix);
        embedVerticalSeparationPattern(7, 0, byteMatrix);
        int i13 = i3 - 7;
        embedVerticalSeparationPattern(i13 - 1, 0, byteMatrix);
        embedVerticalSeparationPattern(7, i13, byteMatrix);
        int i14 = i3 - 8;
        if (byteMatrix.get(8, i14) == 0) {
            throw new WriterException();
        }
        byteMatrix.set(8, i14, 1);
        int i15 = version.versionNumber;
        if (i15 >= 2) {
            int[] iArr = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[i15 - 1];
            int length2 = iArr.length;
            int i16 = -1;
            int i17 = 5;
            int i18 = 0;
            while (i8 < length2) {
                while (i18 < length2) {
                    int i19 = iArr[i8];
                    int i20 = iArr[i18];
                    if (i20 != i16 && i19 != i16 && isEmpty(byteMatrix.get(i20, i19))) {
                        int i21 = i20 - 2;
                        int i22 = i19 - 2;
                        int i23 = 0;
                        while (i23 < i17) {
                            int[] iArr2 = iArr;
                            int i24 = 0;
                            while (i24 < i17) {
                                byteMatrix.set(i21 + i24, i22 + i23, POSITION_ADJUSTMENT_PATTERN[i23][i24]);
                                i24++;
                                i17 = 5;
                                length2 = length2;
                                i22 = i22;
                            }
                            i23++;
                            i17 = 5;
                            iArr = iArr2;
                        }
                    }
                    i18++;
                    i16 = -1;
                    i17 = 5;
                    iArr = iArr;
                    length2 = length2;
                }
                i8++;
                i18 = 0;
                i16 = -1;
                i17 = 5;
            }
        }
        int i25 = 8;
        while (i25 < i12) {
            int i26 = i25 + 1;
            int i27 = i26 % 2;
            if (isEmpty(byteMatrix.get(i25, 6))) {
                byteMatrix.set(i25, 6, i27);
            }
            if (isEmpty(byteMatrix.get(6, i25))) {
                byteMatrix.set(6, i25, i27);
            }
            i25 = i26;
        }
        BitArray bitArray2 = new BitArray();
        if (!(i >= 0 && i < 8)) {
            throw new WriterException("Invalid mask pattern");
        }
        int bits = (errorCorrectionLevel.getBits() << 3) | i;
        bitArray2.appendBits(bits, 5);
        int i28 = 0;
        int i29 = 1335;
        while (i29 != 0) {
            i29 >>>= 1;
            i28++;
        }
        int i30 = bits << (i28 - 1);
        while (true) {
            int i31 = 0;
            int i32 = i30;
            while (i32 != 0) {
                i32 >>>= 1;
                i31++;
            }
            if (i31 < i28) {
                break;
            }
            int i33 = 0;
            int i34 = i30;
            while (i34 != 0) {
                i34 >>>= 1;
                i33++;
            }
            i30 ^= 1335 << (i33 - i28);
        }
        bitArray2.appendBits(i30, 10);
        BitArray bitArray3 = new BitArray();
        bitArray3.appendBits(21522, 15);
        if (bitArray2.bits.length != bitArray3.bits.length) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        int i35 = 0;
        while (true) {
            int[] iArr3 = bitArray2.bits;
            if (i35 >= iArr3.length) {
                break;
            }
            iArr3[i35] = iArr3[i35] ^ bitArray3.bits[i35];
            i35++;
        }
        if (bitArray2.size != 15) {
            throw new WriterException("should not happen but we got: " + bitArray2.size);
        }
        int i36 = 0;
        while (true) {
            int i37 = bitArray2.size;
            if (i36 >= i37) {
                break;
            }
            boolean z3 = bitArray2.get((i37 - 1) - i36);
            int[] iArr4 = TYPE_INFO_COORDINATES[i36];
            byteMatrix.set(iArr4[0], iArr4[1], z3);
            if (i36 < 8) {
                byteMatrix.set((i2 - i36) - 1, 8, z3);
            } else {
                byteMatrix.set(8, (i36 - 8) + i13, z3);
            }
            i36++;
        }
        if (i15 >= 7) {
            BitArray bitArray4 = new BitArray();
            bitArray4.appendBits(i15, 6);
            int i38 = 0;
            int i39 = 7973;
            while (i39 != 0) {
                i39 >>>= 1;
                i38++;
            }
            int i40 = i15 << (i38 - 1);
            while (true) {
                int i41 = 0;
                int i42 = i40;
                while (i42 != 0) {
                    i42 >>>= 1;
                    i41++;
                }
                if (i41 < i38) {
                    break;
                }
                int i43 = 0;
                int i44 = i40;
                while (i44 != 0) {
                    i44 >>>= 1;
                    i43++;
                }
                i40 ^= 7973 << (i43 - i38);
            }
            bitArray4.appendBits(i40, 12);
            if (bitArray4.size != 18) {
                throw new WriterException("should not happen but we got: " + bitArray4.size);
            }
            int i45 = 17;
            for (int i46 = 0; i46 < 6; i46++) {
                for (int i47 = 0; i47 < 3; i47++) {
                    boolean z4 = bitArray4.get(i45);
                    i45--;
                    int i48 = (i3 - 11) + i47;
                    byteMatrix.set(i46, i48, z4);
                    byteMatrix.set(i48, i46, z4);
                }
            }
        }
        int i49 = i2 - 1;
        int i50 = i3 - 1;
        int i51 = -1;
        int i52 = 0;
        while (i49 > 0) {
            if (i49 == 6) {
                i49--;
            }
            while (i50 >= 0 && i50 < i3) {
                for (int i53 = 0; i53 < 2; i53++) {
                    int i54 = i49 - i53;
                    if (isEmpty(byteMatrix.get(i54, i50))) {
                        if (i52 < bitArray.size) {
                            z = bitArray.get(i52);
                            i52++;
                        } else {
                            z = false;
                        }
                        if (i != -1) {
                            switch (i) {
                                case 0:
                                    i4 = i50 + i54;
                                    z2 = true;
                                    i5 = i4 & 1;
                                    if (i5 != 0) {
                                        z2 = false;
                                    }
                                    if (z2) {
                                        z = !z;
                                        break;
                                    }
                                    break;
                                case 1:
                                    i4 = i50;
                                    z2 = true;
                                    i5 = i4 & 1;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                case 2:
                                    i5 = i54 % 3;
                                    z2 = true;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                case 3:
                                    i5 = (i50 + i54) % 3;
                                    z2 = true;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                case 4:
                                    i4 = (i50 >>> 1) + (i54 / 3);
                                    z2 = true;
                                    i5 = i4 & 1;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                case 5:
                                    int i55 = i50 * i54;
                                    i5 = (i55 % 3) + (i55 & 1);
                                    z2 = true;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                case 6:
                                    int i56 = i50 * i54;
                                    i6 = i56 & 1;
                                    i7 = i56 % 3;
                                    i4 = i7 + i6;
                                    z2 = true;
                                    i5 = i4 & 1;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                case 7:
                                    i7 = (i50 * i54) % 3;
                                    i6 = (i50 + i54) & 1;
                                    i4 = i7 + i6;
                                    z2 = true;
                                    i5 = i4 & 1;
                                    if (i5 != 0) {
                                    }
                                    if (z2) {
                                    }
                                    break;
                                default:
                                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid mask pattern: ", i));
                            }
                        }
                        byteMatrix.set(i54, i50, z);
                    }
                }
                i50 += i51;
            }
            i51 = -i51;
            i50 += i51;
            i49 -= 2;
        }
        if (i52 == bitArray.size) {
            return;
        }
        throw new WriterException("Not all bits consumed: " + i52 + '/' + bitArray.size);
    }

    public static void embedHorizontalSeparationPattern(int i, int i2, ByteMatrix byteMatrix) {
        for (int i3 = 0; i3 < 8; i3++) {
            int i4 = i + i3;
            if (!isEmpty(byteMatrix.get(i4, i2))) {
                throw new WriterException();
            }
            byteMatrix.set(i4, i2, 0);
        }
    }

    public static void embedPositionDetectionPattern(int i, int i2, ByteMatrix byteMatrix) {
        for (int i3 = 0; i3 < 7; i3++) {
            for (int i4 = 0; i4 < 7; i4++) {
                byteMatrix.set(i + i4, i2 + i3, POSITION_DETECTION_PATTERN[i3][i4]);
            }
        }
    }

    public static void embedVerticalSeparationPattern(int i, int i2, ByteMatrix byteMatrix) {
        for (int i3 = 0; i3 < 7; i3++) {
            int i4 = i2 + i3;
            if (!isEmpty(byteMatrix.get(i, i4))) {
                throw new WriterException();
            }
            byteMatrix.set(i, i4, 0);
        }
    }

    public static boolean isEmpty(int i) {
        return i == -1;
    }
}
