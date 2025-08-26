package com.google.zxing.common.reedsolomon;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class ReedSolomonEncoder {
    public final List cachedGenerators;
    public final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        ArrayList arrayList = new ArrayList();
        this.cachedGenerators = arrayList;
        arrayList.add(new GenericGFPoly(genericGF, new int[]{1}));
    }

    public final void encode(int i, int[] iArr) {
        GenericGFPoly genericGFPoly;
        if (i == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        int size = ((ArrayList) this.cachedGenerators).size();
        int i2 = 1;
        GenericGF genericGF = this.field;
        if (i >= size) {
            GenericGFPoly genericGFPoly2 = (GenericGFPoly) AlertController$$ExternalSyntheticOutline0.m(1, (ArrayList) this.cachedGenerators);
            int size2 = ((ArrayList) this.cachedGenerators).size();
            while (size2 <= i) {
                GenericGFPoly genericGFPoly3 = new GenericGFPoly(genericGF, new int[]{i2, genericGF.expTable[(size2 - 1) + genericGF.generatorBase]});
                genericGFPoly2.getClass();
                GenericGF genericGF2 = genericGFPoly2.field;
                if (!genericGF2.equals(genericGFPoly3.field)) {
                    throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
                }
                if (genericGFPoly2.isZero() || genericGFPoly3.isZero()) {
                    genericGFPoly2 = genericGF2.zero;
                } else {
                    int[] iArr2 = genericGFPoly2.coefficients;
                    int length2 = iArr2.length;
                    int[] iArr3 = genericGFPoly3.coefficients;
                    int length3 = iArr3.length;
                    int[] iArr4 = new int[(length2 + length3) - i2];
                    for (int i3 = 0; i3 < length2; i3++) {
                        int i4 = iArr2[i3];
                        int i5 = 0;
                        while (i5 < length3) {
                            int i6 = i3 + i5;
                            iArr4[i6] = iArr4[i6] ^ genericGF2.multiply(i4, iArr3[i5]);
                            i5++;
                            iArr2 = iArr2;
                        }
                    }
                    genericGFPoly2 = new GenericGFPoly(genericGF2, iArr4);
                }
                ((ArrayList) this.cachedGenerators).add(genericGFPoly2);
                size2++;
                i2 = 1;
            }
        }
        GenericGFPoly genericGFPoly4 = (GenericGFPoly) ((ArrayList) this.cachedGenerators).get(i);
        int[] iArr5 = new int[length];
        System.arraycopy(iArr, 0, iArr5, 0, length);
        GenericGFPoly genericGFPolyMultiplyByMonomial = new GenericGFPoly(genericGF, iArr5).multiplyByMonomial(i, 1);
        genericGFPolyMultiplyByMonomial.getClass();
        GenericGF genericGF3 = genericGFPoly4.field;
        GenericGF genericGF4 = genericGFPolyMultiplyByMonomial.field;
        if (!genericGF4.equals(genericGF3)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (genericGFPoly4.isZero()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        int degree = genericGFPoly4.getDegree();
        if (genericGFPoly4.coefficients[(r6.length - 1) - degree] == 0) {
            throw new ArithmeticException();
        }
        int i7 = genericGF4.expTable[(genericGF4.size - genericGF4.logTable[r4]) - 1];
        GenericGFPoly genericGFPoly5 = genericGF4.zero;
        GenericGFPoly genericGFPolyAddOrSubtract = genericGFPoly5;
        while (genericGFPolyMultiplyByMonomial.getDegree() >= genericGFPoly4.getDegree() && !genericGFPolyMultiplyByMonomial.isZero()) {
            int degree2 = genericGFPolyMultiplyByMonomial.getDegree() - genericGFPoly4.getDegree();
            int degree3 = genericGFPolyMultiplyByMonomial.getDegree();
            int iMultiply = genericGF4.multiply(genericGFPolyMultiplyByMonomial.coefficients[(r11.length - 1) - degree3], i7);
            GenericGFPoly genericGFPolyMultiplyByMonomial2 = genericGFPoly4.multiplyByMonomial(degree2, iMultiply);
            if (degree2 < 0) {
                throw new IllegalArgumentException();
            }
            if (iMultiply == 0) {
                genericGFPoly = genericGFPoly5;
            } else {
                int[] iArr6 = new int[degree2 + 1];
                iArr6[0] = iMultiply;
                genericGFPoly = new GenericGFPoly(genericGF4, iArr6);
            }
            genericGFPolyAddOrSubtract = genericGFPolyAddOrSubtract.addOrSubtract(genericGFPoly);
            genericGFPolyMultiplyByMonomial = genericGFPolyMultiplyByMonomial.addOrSubtract(genericGFPolyMultiplyByMonomial2);
        }
        int[] iArr7 = new GenericGFPoly[]{genericGFPolyAddOrSubtract, genericGFPolyMultiplyByMonomial}[1].coefficients;
        int length4 = i - iArr7.length;
        for (int i8 = 0; i8 < length4; i8++) {
            iArr[length + i8] = 0;
        }
        System.arraycopy(iArr7, 0, iArr, length + length4, iArr7.length);
    }
}
