package com.google.zxing.common;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class BitArray implements Cloneable {
    public static final int[] EMPTY_BITS = new int[0];
    public int[] bits;
    public int size;

    public BitArray() {
        this.size = 0;
        this.bits = EMPTY_BITS;
    }

    public final void appendBit(boolean z) {
        ensureCapacity(this.size + 1);
        if (z) {
            int[] iArr = this.bits;
            int i = this.size;
            int i2 = i / 32;
            iArr[i2] = (1 << (i & 31)) | iArr[i2];
        }
        this.size++;
    }

    public final void appendBits(int i, int i2) {
        if (i2 < 0 || i2 > 32) {
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        }
        int i3 = this.size;
        ensureCapacity(i3 + i2);
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            if (((1 << i4) & i) != 0) {
                int[] iArr = this.bits;
                int i5 = i3 / 32;
                iArr[i5] = iArr[i5] | (1 << (i3 & 31));
            }
            i3++;
        }
        this.size = i3;
    }

    public final Object clone() {
        return new BitArray((int[]) this.bits.clone(), this.size);
    }

    public final void ensureCapacity(int i) {
        if (i > this.bits.length * 32) {
            int[] iArr = new int[(((int) Math.ceil(i / 0.75f)) + 31) / 32];
            int[] iArr2 = this.bits;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            this.bits = iArr;
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BitArray)) {
            return false;
        }
        BitArray bitArray = (BitArray) obj;
        return this.size == bitArray.size && Arrays.equals(this.bits, bitArray.bits);
    }

    public final boolean get(int i) {
        return (this.bits[i / 32] & (1 << (i & 31))) != 0;
    }

    public final int getSizeInBytes() {
        return (this.size + 7) / 8;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.bits) + (this.size * 31);
    }

    public final String toString() {
        int i = this.size;
        StringBuilder sb = new StringBuilder((i / 8) + i + 1);
        for (int i2 = 0; i2 < this.size; i2++) {
            if ((i2 & 7) == 0) {
                sb.append(' ');
            }
            sb.append(get(i2) ? 'X' : '.');
        }
        return sb.toString();
    }

    public BitArray(int i) {
        this.size = i;
        this.bits = new int[(i + 31) / 32];
    }

    public BitArray(int[] iArr, int i) {
        this.bits = iArr;
        this.size = i;
    }
}
