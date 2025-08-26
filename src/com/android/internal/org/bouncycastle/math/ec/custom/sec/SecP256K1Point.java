package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.math.raw.Nat256;

/* loaded from: classes5.dex */
public class SecP256K1Point extends ECPoint.AbstractFp {
    SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    protected ECPoint detach() {
        return new SecP256K1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint add(ECPoint eCPoint) {
        int[] iArr;
        int[] iArr2;
        char c;
        int[] iArr3;
        int[] iArr4;
        if (isInfinity()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return this;
        }
        if (this == eCPoint) {
            return twice();
        }
        ECCurve curve = getCurve();
        SecP256K1FieldElement secP256K1FieldElement = (SecP256K1FieldElement) this.x;
        SecP256K1FieldElement secP256K1FieldElement2 = (SecP256K1FieldElement) this.y;
        SecP256K1FieldElement secP256K1FieldElement3 = (SecP256K1FieldElement) eCPoint.getXCoord();
        SecP256K1FieldElement secP256K1FieldElement4 = (SecP256K1FieldElement) eCPoint.getYCoord();
        SecP256K1FieldElement secP256K1FieldElement5 = (SecP256K1FieldElement) this.zs[0];
        SecP256K1FieldElement secP256K1FieldElement6 = (SecP256K1FieldElement) eCPoint.getZCoord(0);
        int[] iArrCreateExt = Nat256.createExt();
        int[] iArrCreateExt2 = Nat256.createExt();
        int[] iArrCreate = Nat256.create();
        int[] iArrCreate2 = Nat256.create();
        int[] iArrCreate3 = Nat256.create();
        boolean zIsOne = secP256K1FieldElement5.isOne();
        if (zIsOne) {
            iArr = secP256K1FieldElement3.x;
            iArr2 = secP256K1FieldElement4.x;
        } else {
            SecP256K1Field.square(secP256K1FieldElement5.x, iArrCreate2, iArrCreateExt);
            SecP256K1Field.multiply(iArrCreate2, secP256K1FieldElement3.x, iArrCreate, iArrCreateExt);
            SecP256K1Field.multiply(iArrCreate2, secP256K1FieldElement5.x, iArrCreate2, iArrCreateExt);
            SecP256K1Field.multiply(iArrCreate2, secP256K1FieldElement4.x, iArrCreate2, iArrCreateExt);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = secP256K1FieldElement6.isOne();
        if (zIsOne2) {
            iArr3 = secP256K1FieldElement.x;
            iArr4 = secP256K1FieldElement2.x;
            c = 0;
        } else {
            c = 0;
            SecP256K1Field.square(secP256K1FieldElement6.x, iArrCreate3, iArrCreateExt);
            SecP256K1Field.multiply(iArrCreate3, secP256K1FieldElement.x, iArrCreateExt2, iArrCreateExt);
            SecP256K1Field.multiply(iArrCreate3, secP256K1FieldElement6.x, iArrCreate3, iArrCreateExt);
            SecP256K1Field.multiply(iArrCreate3, secP256K1FieldElement2.x, iArrCreate3, iArrCreateExt);
            iArr3 = iArrCreateExt2;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = Nat256.create();
        SecP256K1Field.subtract(iArr3, iArr, iArrCreate4);
        SecP256K1Field.subtract(iArr4, iArr2, iArrCreate);
        if (Nat256.isZero(iArrCreate4)) {
            if (Nat256.isZero(iArrCreate)) {
                return twice();
            }
            return curve.getInfinity();
        }
        SecP256K1Field.square(iArrCreate4, iArrCreate2, iArrCreateExt);
        int[] iArrCreate5 = Nat256.create();
        SecP256K1Field.multiply(iArrCreate2, iArrCreate4, iArrCreate5, iArrCreateExt);
        SecP256K1Field.multiply(iArrCreate2, iArr3, iArrCreate2, iArrCreateExt);
        SecP256K1Field.negate(iArrCreate5, iArrCreate5);
        Nat256.mul(iArr4, iArrCreate5, iArrCreateExt2);
        SecP256K1Field.reduce32(Nat256.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        SecP256K1FieldElement secP256K1FieldElement7 = new SecP256K1FieldElement(iArrCreate3);
        SecP256K1Field.square(iArrCreate, secP256K1FieldElement7.x, iArrCreateExt);
        SecP256K1Field.subtract(secP256K1FieldElement7.x, iArrCreate5, secP256K1FieldElement7.x);
        SecP256K1FieldElement secP256K1FieldElement8 = new SecP256K1FieldElement(iArrCreate5);
        SecP256K1Field.subtract(iArrCreate2, secP256K1FieldElement7.x, secP256K1FieldElement8.x);
        SecP256K1Field.multiplyAddToExt(secP256K1FieldElement8.x, iArrCreate, iArrCreateExt2);
        SecP256K1Field.reduce(iArrCreateExt2, secP256K1FieldElement8.x);
        SecP256K1FieldElement secP256K1FieldElement9 = new SecP256K1FieldElement(iArrCreate4);
        if (!zIsOne) {
            SecP256K1Field.multiply(secP256K1FieldElement9.x, secP256K1FieldElement5.x, secP256K1FieldElement9.x, iArrCreateExt);
        }
        if (!zIsOne2) {
            SecP256K1Field.multiply(secP256K1FieldElement9.x, secP256K1FieldElement6.x, secP256K1FieldElement9.x, iArrCreateExt);
        }
        ECFieldElement[] eCFieldElementArr = new ECFieldElement[1];
        eCFieldElementArr[c] = secP256K1FieldElement9;
        return new SecP256K1Point(curve, secP256K1FieldElement7, secP256K1FieldElement8, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP256K1FieldElement secP256K1FieldElement = (SecP256K1FieldElement) this.y;
        if (secP256K1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        SecP256K1FieldElement secP256K1FieldElement2 = (SecP256K1FieldElement) this.x;
        SecP256K1FieldElement secP256K1FieldElement3 = (SecP256K1FieldElement) this.zs[0];
        int[] iArrCreateExt = Nat256.createExt();
        int[] iArrCreate = Nat256.create();
        SecP256K1Field.square(secP256K1FieldElement.x, iArrCreate, iArrCreateExt);
        int[] iArrCreate2 = Nat256.create();
        SecP256K1Field.square(iArrCreate, iArrCreate2, iArrCreateExt);
        int[] iArrCreate3 = Nat256.create();
        SecP256K1Field.square(secP256K1FieldElement2.x, iArrCreate3, iArrCreateExt);
        SecP256K1Field.reduce32(Nat256.addBothTo(iArrCreate3, iArrCreate3, iArrCreate3), iArrCreate3);
        SecP256K1Field.multiply(iArrCreate, secP256K1FieldElement2.x, iArrCreate, iArrCreateExt);
        SecP256K1Field.reduce32(Nat.shiftUpBits(8, iArrCreate, 2, 0), iArrCreate);
        int[] iArrCreate4 = Nat256.create();
        SecP256K1Field.reduce32(Nat.shiftUpBits(8, iArrCreate2, 3, 0, iArrCreate4), iArrCreate4);
        SecP256K1FieldElement secP256K1FieldElement4 = new SecP256K1FieldElement(iArrCreate2);
        SecP256K1Field.square(iArrCreate3, secP256K1FieldElement4.x, iArrCreateExt);
        SecP256K1Field.subtract(secP256K1FieldElement4.x, iArrCreate, secP256K1FieldElement4.x);
        SecP256K1Field.subtract(secP256K1FieldElement4.x, iArrCreate, secP256K1FieldElement4.x);
        SecP256K1FieldElement secP256K1FieldElement5 = new SecP256K1FieldElement(iArrCreate);
        SecP256K1Field.subtract(iArrCreate, secP256K1FieldElement4.x, secP256K1FieldElement5.x);
        SecP256K1Field.multiply(secP256K1FieldElement5.x, iArrCreate3, secP256K1FieldElement5.x, iArrCreateExt);
        SecP256K1Field.subtract(secP256K1FieldElement5.x, iArrCreate4, secP256K1FieldElement5.x);
        SecP256K1FieldElement secP256K1FieldElement6 = new SecP256K1FieldElement(iArrCreate3);
        SecP256K1Field.twice(secP256K1FieldElement.x, secP256K1FieldElement6.x);
        if (!secP256K1FieldElement3.isOne()) {
            SecP256K1Field.multiply(secP256K1FieldElement6.x, secP256K1FieldElement3.x, secP256K1FieldElement6.x, iArrCreateExt);
        }
        return new SecP256K1Point(curve, secP256K1FieldElement4, secP256K1FieldElement5, new ECFieldElement[]{secP256K1FieldElement6});
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint twicePlus(ECPoint eCPoint) {
        if (this == eCPoint) {
            return threeTimes();
        }
        if (!isInfinity()) {
            if (eCPoint.isInfinity()) {
                return twice();
            }
            if (!this.y.isZero()) {
                return twice().add(eCPoint);
            }
        }
        return eCPoint;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint threeTimes() {
        return (isInfinity() || this.y.isZero()) ? this : twice().add(this);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint negate() {
        return isInfinity() ? this : new SecP256K1Point(this.curve, this.x, this.y.negate(), this.zs);
    }
}
