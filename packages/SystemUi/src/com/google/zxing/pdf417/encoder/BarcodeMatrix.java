package com.google.zxing.pdf417.encoder;

import java.lang.reflect.Array;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BarcodeMatrix {
    public int currentRow;
    public final int height;
    public final BarcodeRow[] matrix;
    public final int width;

    public BarcodeMatrix(int i, int i2) {
        BarcodeRow[] barcodeRowArr = new BarcodeRow[i];
        this.matrix = barcodeRowArr;
        int length = barcodeRowArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.matrix[i3] = new BarcodeRow(((i2 + 4) * 17) + 1);
        }
        this.width = i2 * 17;
        this.height = i;
        this.currentRow = -1;
    }

    public final BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public final byte[][] getScaledMatrix(int i, int i2) {
        int i3 = this.height;
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i3 * i2, this.width * i);
        int i4 = i3 * i2;
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = (i4 - i5) - 1;
            byte[] bArr2 = this.matrix[i5 / i2].row;
            int length = bArr2.length * i;
            byte[] bArr3 = new byte[length];
            for (int i7 = 0; i7 < length; i7++) {
                bArr3[i7] = bArr2[i7 / i];
            }
            bArr[i6] = bArr3;
        }
        return bArr;
    }
}
