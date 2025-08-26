package com.google.zxing.qrcode.encoder;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class MatrixUtil {
    public static final int[][] POSITION_DETECTION_PATTERN = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};
    public static final int[][] POSITION_ADJUSTMENT_PATTERN = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};
    public static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, 102, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, 102, 126, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, 134, -1}, new int[]{6, 34, 60, 86, 112, 138, -1}, new int[]{6, 30, 58, 86, 114, 142, -1}, new int[]{6, 34, 62, 90, 118, 146, -1}, new int[]{6, 30, 54, 78, 102, 126, 150}, new int[]{6, 24, 50, 76, 102, 128, 154}, new int[]{6, 28, 54, 80, 106, 132, 158}, new int[]{6, 32, 58, 84, 110, 136, 162}, new int[]{6, 26, 54, 82, 110, 138, 166}, new int[]{6, 30, 58, 86, 114, 142, 170}};
    public static final int[][] TYPE_INFO_COORDINATES = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};

    private MatrixUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0224  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void buildMatrix(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, int i, ByteMatrix byteMatrix) throws WriterException {
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        for (byte[] bArr : byteMatrix.bytes) {
            Arrays.fill(bArr, (byte) -1);
        }
        int length = POSITION_DETECTION_PATTERN[0].length;
        embedPositionDetectionPattern(0, 0, byteMatrix);
        int i11 = byteMatrix.width;
        int i12 = i11 - length;
        embedPositionDetectionPattern(i12, 0, byteMatrix);
        embedPositionDetectionPattern(0, i12, byteMatrix);
        embedHorizontalSeparationPattern(0, 7, byteMatrix);
        int i13 = i11 - 8;
        embedHorizontalSeparationPattern(i13, 7, byteMatrix);
        embedHorizontalSeparationPattern(0, i13, byteMatrix);
        embedVerticalSeparationPattern(7, 0, byteMatrix);
        int i14 = byteMatrix.height;
        int i15 = i14 - 8;
        embedVerticalSeparationPattern(i15, 0, byteMatrix);
        int i16 = i14 - 7;
        embedVerticalSeparationPattern(7, i16, byteMatrix);
        if (byteMatrix.get(8, i15) == 0) {
            throw new WriterException();
        }
        byteMatrix.set(8, i15, 1);
        int i17 = version.versionNumber;
        int i18 = 5;
        if (i17 < 2) {
            i2 = 0;
            i3 = 1;
        } else {
            int[] iArr = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[i17 - 1];
            i2 = 0;
            int length2 = iArr.length;
            i3 = 1;
            int i19 = 0;
            while (i19 < length2) {
                int i20 = iArr[i19];
                if (i20 >= 0) {
                    int length3 = iArr.length;
                    int i21 = 0;
                    while (i21 < length3) {
                        int i22 = iArr[i21];
                        if (i22 < 0 || !isEmpty(byteMatrix.get(i22, i20))) {
                            i4 = i21;
                        } else {
                            int i23 = i22 - 2;
                            int i24 = i20 - 2;
                            i4 = i21;
                            int i25 = 0;
                            while (i25 < i18) {
                                int[] iArr2 = POSITION_ADJUSTMENT_PATTERN[i25];
                                int i26 = i25;
                                int i27 = 0;
                                while (i27 < i18) {
                                    int i28 = i27;
                                    byteMatrix.set(i23 + i27, i24 + i26, iArr2[i28]);
                                    i27 = i28 + 1;
                                    i11 = i11;
                                    i18 = 5;
                                }
                                i25 = i26 + 1;
                                i18 = 5;
                            }
                        }
                        i21 = i4 + 1;
                        i11 = i11;
                        i18 = 5;
                    }
                }
                i19++;
                i11 = i11;
                i18 = 5;
            }
        }
        int i29 = i11;
        int i30 = 8;
        while (i30 < i13) {
            int i31 = i30 + 1;
            int i32 = i31 % 2;
            if (isEmpty(byteMatrix.get(i30, 6))) {
                byteMatrix.set(i30, 6, i32);
            }
            if (isEmpty(byteMatrix.get(6, i30))) {
                byteMatrix.set(6, i30, i32);
            }
            i30 = i31;
        }
        BitArray bitArray2 = new BitArray();
        if (i < 0 || i >= 8) {
            throw new WriterException("Invalid mask pattern");
        }
        int bits = (errorCorrectionLevel.getBits() << 3) | i;
        bitArray2.appendBits(bits, 5);
        bitArray2.appendBits(calculateBCHCode(bits, 1335), 10);
        BitArray bitArray3 = new BitArray();
        bitArray3.appendBits(21522, 15);
        if (bitArray2.size != bitArray3.size) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        int i33 = i2;
        while (true) {
            int[] iArr3 = bitArray2.bits;
            if (i33 >= iArr3.length) {
                break;
            }
            iArr3[i33] = iArr3[i33] ^ bitArray3.bits[i33];
            i33++;
        }
        if (bitArray2.size != 15) {
            throw new WriterException("should not happen but we got: " + bitArray2.size);
        }
        int i34 = i2;
        while (true) {
            int i35 = bitArray2.size;
            if (i34 >= i35) {
                break;
            }
            boolean z2 = bitArray2.get((i35 - 1) - i34);
            int[] iArr4 = TYPE_INFO_COORDINATES[i34];
            byteMatrix.set(iArr4[i2], iArr4[i3], z2);
            if (i34 < 8) {
                i10 = (i29 - i34) - 1;
                i9 = 8;
            } else {
                i9 = (i34 - 8) + i16;
                i10 = 8;
            }
            byteMatrix.set(i10, i9, z2);
            i34++;
        }
        if (i17 >= 7) {
            BitArray bitArray4 = new BitArray();
            bitArray4.appendBits(i17, 6);
            bitArray4.appendBits(calculateBCHCode(i17, 7973), 12);
            if (bitArray4.size != 18) {
                throw new WriterException("should not happen but we got: " + bitArray4.size);
            }
            int i36 = 17;
            for (int i37 = i2; i37 < 6; i37++) {
                for (int i38 = i2; i38 < 3; i38++) {
                    boolean z3 = bitArray4.get(i36);
                    i36--;
                    int i39 = (i14 - 11) + i38;
                    byteMatrix.set(i37, i39, z3);
                    byteMatrix.set(i39, i37, z3);
                }
            }
        }
        int i40 = i29 - 1;
        int i41 = i14 - 1;
        int i42 = i2;
        int i43 = -1;
        while (i40 > 0) {
            if (i40 == 6) {
                i40--;
            }
            while (i41 >= 0 && i41 < i14) {
                for (int i44 = i2; i44 < 2; i44++) {
                    int i45 = i40 - i44;
                    if (isEmpty(byteMatrix.get(i45, i41))) {
                        if (i42 < bitArray.size) {
                            z = bitArray.get(i42);
                            i42++;
                        } else {
                            z = i2;
                        }
                        if (i != -1) {
                            switch (i) {
                                case 0:
                                    i5 = i41 + i45;
                                    i6 = i5 & 1;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                        z = !z;
                                        break;
                                    }
                                    break;
                                case 1:
                                    i6 = i41 & 1;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                case 2:
                                    i6 = i45 % 3;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                case 3:
                                    i6 = (i41 + i45) % 3;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                case 4:
                                    i6 = ((i45 / 3) + (i41 / 2)) & 1;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                case 5:
                                    int i46 = i41 * i45;
                                    i6 = (i46 % 3) + (i46 & 1);
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                case 6:
                                    int i47 = i41 * i45;
                                    i7 = i47 & 1;
                                    i8 = i47 % 3;
                                    i5 = i8 + i7;
                                    i6 = i5 & 1;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                case 7:
                                    i8 = (i41 * i45) % 3;
                                    i7 = (i41 + i45) & 1;
                                    i5 = i8 + i7;
                                    i6 = i5 & 1;
                                    if ((i6 != 0 ? i3 : i2) != 0) {
                                    }
                                    break;
                                default:
                                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid mask pattern: "));
                            }
                        }
                        byteMatrix.set(i45, i41, z);
                    }
                }
                i41 += i43;
            }
            i43 = -i43;
            i41 += i43;
            i40 -= 2;
        }
        if (i42 == bitArray.size) {
            return;
        }
        throw new WriterException("Not all bits consumed: " + i42 + '/' + bitArray.size);
    }

    public static int calculateBCHCode(int i, int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("0 polynomial");
        }
        int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(i2);
        int i3 = 32 - iNumberOfLeadingZeros;
        int iNumberOfLeadingZeros2 = i << (31 - iNumberOfLeadingZeros);
        while (32 - Integer.numberOfLeadingZeros(iNumberOfLeadingZeros2) >= i3) {
            iNumberOfLeadingZeros2 ^= i2 << ((32 - Integer.numberOfLeadingZeros(iNumberOfLeadingZeros2)) - i3);
        }
        return iNumberOfLeadingZeros2;
    }

    public static void embedHorizontalSeparationPattern(int i, int i2, ByteMatrix byteMatrix) throws WriterException {
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
            int[] iArr = POSITION_DETECTION_PATTERN[i3];
            for (int i4 = 0; i4 < 7; i4++) {
                byteMatrix.set(i + i4, i2 + i3, iArr[i4]);
            }
        }
    }

    public static void embedVerticalSeparationPattern(int i, int i2, ByteMatrix byteMatrix) throws WriterException {
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
