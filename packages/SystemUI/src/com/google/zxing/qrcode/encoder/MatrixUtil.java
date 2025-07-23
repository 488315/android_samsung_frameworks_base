package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MatrixUtil {
    public static final int[][] POSITION_DETECTION_PATTERN = {new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}};
    public static final int[][] POSITION_ADJUSTMENT_PATTERN = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};
    public static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = {new int[]{-1, -1, -1, -1, -1, -1, -1}, new int[]{6, 18, -1, -1, -1, -1, -1}, new int[]{6, 22, -1, -1, -1, -1, -1}, new int[]{6, 26, -1, -1, -1, -1, -1}, new int[]{6, 30, -1, -1, -1, -1, -1}, new int[]{6, 34, -1, -1, -1, -1, -1}, new int[]{6, 22, 38, -1, -1, -1, -1}, new int[]{6, 24, 42, -1, -1, -1, -1}, new int[]{6, 26, 46, -1, -1, -1, -1}, new int[]{6, 28, 50, -1, -1, -1, -1}, new int[]{6, 30, 54, -1, -1, -1, -1}, new int[]{6, 32, 58, -1, -1, -1, -1}, new int[]{6, 34, 62, -1, -1, -1, -1}, new int[]{6, 26, 46, 66, -1, -1, -1}, new int[]{6, 26, 48, 70, -1, -1, -1}, new int[]{6, 26, 50, 74, -1, -1, -1}, new int[]{6, 30, 54, 78, -1, -1, -1}, new int[]{6, 30, 56, 82, -1, -1, -1}, new int[]{6, 30, 58, 86, -1, -1, -1}, new int[]{6, 34, 62, 90, -1, -1, -1}, new int[]{6, 28, 50, 72, 94, -1, -1}, new int[]{6, 26, 50, 74, 98, -1, -1}, new int[]{6, 30, 54, 78, 102, -1, -1}, new int[]{6, 28, 54, 80, 106, -1, -1}, new int[]{6, 32, 58, 84, 110, -1, -1}, new int[]{6, 30, 58, 86, 114, -1, -1}, new int[]{6, 34, 62, 90, 118, -1, -1}, new int[]{6, 26, 50, 74, 98, 122, -1}, new int[]{6, 30, 54, 78, 102, 126, -1}, new int[]{6, 26, 52, 78, 104, 130, -1}, new int[]{6, 30, 56, 82, 108, 134, -1}, new int[]{6, 34, 60, 86, 112, 138, -1}, new int[]{6, 30, 58, 86, 114, 142, -1}, new int[]{6, 34, 62, 90, 118, 146, -1}, new int[]{6, 30, 54, 78, 102, 126, 150}, new int[]{6, 24, 50, 76, 102, 128, 154}, new int[]{6, 28, 54, 80, 106, 132, 158}, new int[]{6, 32, 58, 84, 110, 136, 162}, new int[]{6, 26, 54, 82, 110, 138, 166}, new int[]{6, 30, 58, 86, 114, 142, 170}};
    public static final int[][] TYPE_INFO_COORDINATES = {new int[]{8, 0}, new int[]{8, 1}, new int[]{8, 2}, new int[]{8, 3}, new int[]{8, 4}, new int[]{8, 5}, new int[]{8, 7}, new int[]{8, 8}, new int[]{7, 8}, new int[]{5, 8}, new int[]{4, 8}, new int[]{3, 8}, new int[]{2, 8}, new int[]{1, 8}, new int[]{0, 8}};

    private MatrixUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:88:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0220  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void buildMatrix(com.google.zxing.common.BitArray r24, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r25, com.google.zxing.qrcode.decoder.Version r26, int r27, com.google.zxing.qrcode.encoder.ByteMatrix r28) {
        /*
            Method dump skipped, instructions count: 684
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.MatrixUtil.buildMatrix(com.google.zxing.common.BitArray, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, com.google.zxing.qrcode.decoder.Version, int, com.google.zxing.qrcode.encoder.ByteMatrix):void");
    }

    public static int calculateBCHCode(int i, int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("0 polynomial");
        }
        int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i2);
        int i3 = 32 - numberOfLeadingZeros;
        int i4 = i << (31 - numberOfLeadingZeros);
        while (32 - Integer.numberOfLeadingZeros(i4) >= i3) {
            i4 ^= i2 << ((32 - Integer.numberOfLeadingZeros(i4)) - i3);
        }
        return i4;
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
            int[] iArr = POSITION_DETECTION_PATTERN[i3];
            for (int i4 = 0; i4 < 7; i4++) {
                byteMatrix.set(i + i4, i2 + i3, iArr[i4]);
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
