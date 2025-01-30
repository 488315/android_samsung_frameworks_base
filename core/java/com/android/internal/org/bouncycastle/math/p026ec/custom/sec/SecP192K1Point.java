package com.android.internal.org.bouncycastle.math.p026ec.custom.sec;

import com.android.internal.org.bouncycastle.math.p026ec.ECCurve;
import com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.p026ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.math.raw.Nat192;

/* loaded from: classes5.dex */
public class SecP192K1Point extends ECPoint.AbstractFp {
    SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        super(curve, x, y);
    }

    SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
        super(curve, x, y, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
    protected ECPoint detach() {
        return new SecP192K1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
    public ECPoint add(ECPoint b) {
        int[] S2;
        int[] U2;
        int[] S1;
        int[] S12;
        if (isInfinity()) {
            return b;
        }
        if (b.isInfinity()) {
            return this;
        }
        if (this == b) {
            return twice();
        }
        ECCurve curve = getCurve();
        SecP192K1FieldElement X1 = (SecP192K1FieldElement) this.f941x;
        SecP192K1FieldElement Y1 = (SecP192K1FieldElement) this.f942y;
        SecP192K1FieldElement X2 = (SecP192K1FieldElement) b.getXCoord();
        SecP192K1FieldElement Y2 = (SecP192K1FieldElement) b.getYCoord();
        SecP192K1FieldElement Z1 = (SecP192K1FieldElement) this.f943zs[0];
        SecP192K1FieldElement Z2 = (SecP192K1FieldElement) b.getZCoord(0);
        int[] tt1 = Nat192.createExt();
        int[] t2 = Nat192.create();
        int[] t3 = Nat192.create();
        int[] t4 = Nat192.create();
        boolean Z1IsOne = Z1.isOne();
        if (Z1IsOne) {
            U2 = X2.f950x;
            S2 = Y2.f950x;
        } else {
            S2 = t3;
            SecP192K1Field.square(Z1.f950x, S2);
            U2 = t2;
            SecP192K1Field.multiply(S2, X2.f950x, U2);
            SecP192K1Field.multiply(S2, Z1.f950x, S2);
            SecP192K1Field.multiply(S2, Y2.f950x, S2);
        }
        boolean Z2IsOne = Z2.isOne();
        if (Z2IsOne) {
            int[] U1 = X1.f950x;
            int[] U12 = Y1.f950x;
            S1 = U12;
            S12 = U1;
        } else {
            SecP192K1Field.square(Z2.f950x, t4);
            SecP192K1Field.multiply(t4, X1.f950x, tt1);
            SecP192K1Field.multiply(t4, Z2.f950x, t4);
            SecP192K1Field.multiply(t4, Y1.f950x, t4);
            S1 = t4;
            S12 = tt1;
        }
        int[] H = Nat192.create();
        SecP192K1Field.subtract(S12, U2, H);
        SecP192K1Field.subtract(S1, S2, t2);
        if (Nat192.isZero(H)) {
            if (Nat192.isZero(t2)) {
                return twice();
            }
            return curve.getInfinity();
        }
        SecP192K1Field.square(H, t3);
        int[] G = Nat192.create();
        SecP192K1Field.multiply(t3, H, G);
        SecP192K1Field.multiply(t3, S12, t3);
        SecP192K1Field.negate(G, G);
        Nat192.mul(S1, G, tt1);
        int c = Nat192.addBothTo(t3, t3, G);
        SecP192K1Field.reduce32(c, G);
        SecP192K1FieldElement X3 = new SecP192K1FieldElement(t4);
        int[] HSquared = X3.f950x;
        SecP192K1Field.square(t2, HSquared);
        int[] iArr = X3.f950x;
        int[] S13 = X3.f950x;
        SecP192K1Field.subtract(iArr, G, S13);
        SecP192K1FieldElement Y3 = new SecP192K1FieldElement(G);
        SecP192K1Field.subtract(t3, X3.f950x, Y3.f950x);
        SecP192K1Field.multiplyAddToExt(Y3.f950x, t2, tt1);
        SecP192K1Field.reduce(tt1, Y3.f950x);
        SecP192K1FieldElement Z3 = new SecP192K1FieldElement(H);
        if (!Z1IsOne) {
            int[] iArr2 = Z3.f950x;
            int[] R = Z1.f950x;
            SecP192K1Field.multiply(iArr2, R, Z3.f950x);
        }
        if (!Z2IsOne) {
            SecP192K1Field.multiply(Z3.f950x, Z2.f950x, Z3.f950x);
        }
        ECFieldElement[] zs = {Z3};
        return new SecP192K1Point(curve, X3, Y3, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP192K1FieldElement Y1 = (SecP192K1FieldElement) this.f942y;
        if (Y1.isZero()) {
            return curve.getInfinity();
        }
        SecP192K1FieldElement X1 = (SecP192K1FieldElement) this.f941x;
        SecP192K1FieldElement Z1 = (SecP192K1FieldElement) this.f943zs[0];
        int[] Y1Squared = Nat192.create();
        SecP192K1Field.square(Y1.f950x, Y1Squared);
        int[] T = Nat192.create();
        SecP192K1Field.square(Y1Squared, T);
        int[] M = Nat192.create();
        SecP192K1Field.square(X1.f950x, M);
        int c = Nat192.addBothTo(M, M, M);
        SecP192K1Field.reduce32(c, M);
        SecP192K1Field.multiply(Y1Squared, X1.f950x, Y1Squared);
        int c2 = Nat.shiftUpBits(6, Y1Squared, 2, 0);
        SecP192K1Field.reduce32(c2, Y1Squared);
        int[] t1 = Nat192.create();
        int c3 = Nat.shiftUpBits(6, T, 3, 0, t1);
        SecP192K1Field.reduce32(c3, t1);
        SecP192K1FieldElement X3 = new SecP192K1FieldElement(T);
        SecP192K1Field.square(M, X3.f950x);
        SecP192K1Field.subtract(X3.f950x, Y1Squared, X3.f950x);
        SecP192K1Field.subtract(X3.f950x, Y1Squared, X3.f950x);
        SecP192K1FieldElement Y3 = new SecP192K1FieldElement(Y1Squared);
        SecP192K1Field.subtract(Y1Squared, X3.f950x, Y3.f950x);
        SecP192K1Field.multiply(Y3.f950x, M, Y3.f950x);
        SecP192K1Field.subtract(Y3.f950x, t1, Y3.f950x);
        SecP192K1FieldElement Z3 = new SecP192K1FieldElement(M);
        SecP192K1Field.twice(Y1.f950x, Z3.f950x);
        if (!Z1.isOne()) {
            SecP192K1Field.multiply(Z3.f950x, Z1.f950x, Z3.f950x);
        }
        return new SecP192K1Point(curve, X3, Y3, new ECFieldElement[]{Z3});
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
    public ECPoint twicePlus(ECPoint b) {
        if (this == b) {
            return threeTimes();
        }
        if (isInfinity()) {
            return b;
        }
        if (b.isInfinity()) {
            return twice();
        }
        ECFieldElement Y1 = this.f942y;
        if (Y1.isZero()) {
            return b;
        }
        return twice().add(b);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
    public ECPoint threeTimes() {
        if (isInfinity() || this.f942y.isZero()) {
            return this;
        }
        return twice().add(this);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
    public ECPoint negate() {
        if (isInfinity()) {
            return this;
        }
        return new SecP192K1Point(this.curve, this.f941x, this.f942y.negate(), this.f943zs);
    }
}
