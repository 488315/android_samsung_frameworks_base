package com.google.zxing.common;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class BitMatrix implements Cloneable {
    public final int[] bits;
    public final int height;
    public final int rowSize;
    public final int width;

    public BitMatrix(int i) {
        this(i, i);
    }

    public final Object clone() {
        return new BitMatrix(this.width, this.height, this.rowSize, (int[]) this.bits.clone());
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BitMatrix)) {
            return false;
        }
        BitMatrix bitMatrix = (BitMatrix) obj;
        return this.width == bitMatrix.width && this.height == bitMatrix.height && this.rowSize == bitMatrix.rowSize && Arrays.equals(this.bits, bitMatrix.bits);
    }

    public final boolean get(int i, int i2) {
        return ((this.bits[(i / 32) + (i2 * this.rowSize)] >>> (i & 31)) & 1) != 0;
    }

    public final int hashCode() {
        int i = this.width;
        return Arrays.hashCode(this.bits) + (((((((i * 31) + i) * 31) + this.height) * 31) + this.rowSize) * 31);
    }

    public final void set(int i, int i2) {
        int i3 = (i / 32) + (i2 * this.rowSize);
        int[] iArr = this.bits;
        iArr[i3] = (1 << (i & 31)) | iArr[i3];
    }

    public final void setRegion(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (i4 < 1 || i3 < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        int i5 = i3 + i;
        int i6 = i4 + i2;
        if (i6 > this.height || i5 > this.width) {
            throw new IllegalArgumentException("The region must fit inside the matrix");
        }
        while (i2 < i6) {
            int i7 = this.rowSize * i2;
            for (int i8 = i; i8 < i5; i8++) {
                int[] iArr = this.bits;
                int i9 = (i8 / 32) + i7;
                iArr[i9] = iArr[i9] | (1 << (i8 & 31));
            }
            i2++;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder((this.width + 1) * this.height);
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                sb.append(get(i2, i) ? "X " : "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public BitMatrix(int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = i;
        this.height = i2;
        int i3 = (i + 31) / 32;
        this.rowSize = i3;
        this.bits = new int[i3 * i2];
    }

    private BitMatrix(int i, int i2, int i3, int[] iArr) {
        this.width = i;
        this.height = i2;
        this.rowSize = i3;
        this.bits = iArr;
    }
}
