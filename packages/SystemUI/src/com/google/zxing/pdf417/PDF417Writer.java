package com.google.zxing.pdf417;

import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import java.lang.reflect.Array;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PDF417Writer implements Writer {
    public static BitMatrix bitMatrixFromBitArray(byte[][] bArr, int i) {
        int i2 = i * 2;
        BitMatrix bitMatrix = new BitMatrix(bArr[0].length + i2, bArr.length + i2);
        int length = bitMatrix.bits.length;
        for (int i3 = 0; i3 < length; i3++) {
            bitMatrix.bits[i3] = 0;
        }
        int i4 = (bitMatrix.height - i) - 1;
        int i5 = 0;
        while (i5 < bArr.length) {
            byte[] bArr2 = bArr[i5];
            for (int i6 = 0; i6 < bArr[0].length; i6++) {
                if (bArr2[i6] == 1) {
                    bitMatrix.set(i6 + i, i4);
                }
            }
            i5++;
            i4--;
        }
        return bitMatrix;
    }

    public static byte[][] rotateArray(byte[][] bArr) {
        byte[][] bArr2 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bArr[0].length, bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            int length = (bArr.length - i) - 1;
            for (int i2 = 0; i2 < bArr[0].length; i2++) {
                bArr2[i2][length] = bArr[i][i2];
            }
        }
        return bArr2;
    }

    /* JADX WARN: Removed duplicated region for block: B:233:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04dc A[LOOP:14: B:271:0x04da->B:272:0x04dc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x04f9  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0503  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0596  */
    @Override // com.google.zxing.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.common.BitMatrix encode(java.lang.String r31, com.google.zxing.BarcodeFormat r32, int r33, int r34, java.util.Map r35) {
        /*
            Method dump skipped, instructions count: 1473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.PDF417Writer.encode(java.lang.String, com.google.zxing.BarcodeFormat, int, int, java.util.Map):com.google.zxing.common.BitMatrix");
    }
}
