package com.google.zxing.common.reedsolomon;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GenericGFPoly {
    public final int[] coefficients;
    public final GenericGF field;

    public GenericGFPoly(GenericGF genericGF, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = genericGF;
        int length = iArr.length;
        int i = 1;
        if (length <= 1 || iArr[0] != 0) {
            this.coefficients = iArr;
            return;
        }
        while (i < length && iArr[i] == 0) {
            i++;
        }
        if (i == length) {
            genericGF.checkInit();
            this.coefficients = genericGF.zero.coefficients;
        } else {
            int[] iArr2 = new int[length - i];
            this.coefficients = iArr2;
            System.arraycopy(iArr, i, iArr2, 0, iArr2.length);
        }
    }

    public final GenericGFPoly addOrSubtract(GenericGFPoly genericGFPoly) {
        GenericGF genericGF = genericGFPoly.field;
        GenericGF genericGF2 = this.field;
        if (!genericGF2.equals(genericGF)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (isZero()) {
            return genericGFPoly;
        }
        if (genericGFPoly.isZero()) {
            return this;
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = genericGFPoly.coefficients;
        if (length <= iArr2.length) {
            iArr2 = iArr;
            iArr = iArr2;
        }
        int[] iArr3 = new int[iArr.length];
        int length2 = iArr.length - iArr2.length;
        System.arraycopy(iArr, 0, iArr3, 0, length2);
        for (int i = length2; i < iArr.length; i++) {
            iArr3[i] = iArr2[i - length2] ^ iArr[i];
        }
        return new GenericGFPoly(genericGF2, iArr3);
    }

    public final boolean isZero() {
        return this.coefficients[0] == 0;
    }

    public final GenericGFPoly multiplyByMonomial(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        GenericGF genericGF = this.field;
        if (i2 == 0) {
            genericGF.checkInit();
            return genericGF.zero;
        }
        int[] iArr = this.coefficients;
        int length = iArr.length;
        int[] iArr2 = new int[i + length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr2[i3] = genericGF.multiply(iArr[i3], i2);
        }
        return new GenericGFPoly(genericGF, iArr2);
    }

    public final String toString() {
        int[] iArr = this.coefficients;
        StringBuilder sb = new StringBuilder((iArr.length - 1) * 8);
        int length = iArr.length;
        while (true) {
            length--;
            if (length < 0) {
                return sb.toString();
            }
            int i = iArr[(iArr.length - 1) - length];
            if (i != 0) {
                if (i < 0) {
                    sb.append(" - ");
                    i = -i;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (length == 0 || i != 1) {
                    GenericGF genericGF = this.field;
                    genericGF.checkInit();
                    if (i == 0) {
                        throw new IllegalArgumentException();
                    }
                    int i2 = genericGF.logTable[i];
                    if (i2 == 0) {
                        sb.append('1');
                    } else if (i2 == 1) {
                        sb.append('a');
                    } else {
                        sb.append("a^");
                        sb.append(i2);
                    }
                }
                if (length != 0) {
                    if (length == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(length);
                    }
                }
            }
        }
    }
}
