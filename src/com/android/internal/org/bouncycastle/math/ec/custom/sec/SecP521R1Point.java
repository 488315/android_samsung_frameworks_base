package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;

/* loaded from: classes5.dex */
public class SecP521R1Point extends ECPoint.AbstractFp {
    SecP521R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP521R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    protected ECPoint detach() {
        return new SecP521R1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint add(ECPoint eCPoint) {
        char c;
        int[] iArr;
        int[] iArr2;
        int i;
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
        SecP521R1FieldElement secP521R1FieldElement = (SecP521R1FieldElement) this.x;
        SecP521R1FieldElement secP521R1FieldElement2 = (SecP521R1FieldElement) this.y;
        SecP521R1FieldElement secP521R1FieldElement3 = (SecP521R1FieldElement) eCPoint.getXCoord();
        SecP521R1FieldElement secP521R1FieldElement4 = (SecP521R1FieldElement) eCPoint.getYCoord();
        SecP521R1FieldElement secP521R1FieldElement5 = (SecP521R1FieldElement) this.zs[0];
        SecP521R1FieldElement secP521R1FieldElement6 = (SecP521R1FieldElement) eCPoint.getZCoord(0);
        int[] create = Nat.create(33);
        int[] create2 = Nat.create(17);
        int[] create3 = Nat.create(17);
        int[] create4 = Nat.create(17);
        int[] create5 = Nat.create(17);
        boolean isOne = secP521R1FieldElement5.isOne();
        if (isOne) {
            iArr = secP521R1FieldElement3.x;
            iArr2 = secP521R1FieldElement4.x;
            c = 0;
        } else {
            c = 0;
            SecP521R1Field.square(secP521R1FieldElement5.x, create4, create);
            SecP521R1Field.multiply(create4, secP521R1FieldElement3.x, create3, create);
            SecP521R1Field.multiply(create4, secP521R1FieldElement5.x, create4, create);
            SecP521R1Field.multiply(create4, secP521R1FieldElement4.x, create4, create);
            iArr = create3;
            iArr2 = create4;
        }
        boolean isOne2 = secP521R1FieldElement6.isOne();
        if (isOne2) {
            iArr3 = secP521R1FieldElement.x;
            iArr4 = secP521R1FieldElement2.x;
            i = 17;
        } else {
            i = 17;
            SecP521R1Field.square(secP521R1FieldElement6.x, create5, create);
            SecP521R1Field.multiply(create5, secP521R1FieldElement.x, create2, create);
            SecP521R1Field.multiply(create5, secP521R1FieldElement6.x, create5, create);
            SecP521R1Field.multiply(create5, secP521R1FieldElement2.x, create5, create);
            iArr3 = create2;
            iArr4 = create5;
        }
        int[] create6 = Nat.create(i);
        SecP521R1Field.subtract(iArr3, iArr, create6);
        SecP521R1Field.subtract(iArr4, iArr2, create3);
        int i2 = i;
        if (Nat.isZero(i2, create6)) {
            if (Nat.isZero(i2, create3)) {
                return twice();
            }
            return curve.getInfinity();
        }
        SecP521R1Field.square(create6, create4, create);
        int[] create7 = Nat.create(i2);
        SecP521R1Field.multiply(create4, create6, create7, create);
        SecP521R1Field.multiply(create4, iArr3, create4, create);
        SecP521R1Field.multiply(iArr4, create7, create2, create);
        SecP521R1FieldElement secP521R1FieldElement7 = new SecP521R1FieldElement(create5);
        SecP521R1Field.square(create3, secP521R1FieldElement7.x, create);
        SecP521R1Field.add(secP521R1FieldElement7.x, create7, secP521R1FieldElement7.x);
        SecP521R1Field.subtract(secP521R1FieldElement7.x, create4, secP521R1FieldElement7.x);
        SecP521R1Field.subtract(secP521R1FieldElement7.x, create4, secP521R1FieldElement7.x);
        SecP521R1FieldElement secP521R1FieldElement8 = new SecP521R1FieldElement(create7);
        SecP521R1Field.subtract(create4, secP521R1FieldElement7.x, secP521R1FieldElement8.x);
        SecP521R1Field.multiply(secP521R1FieldElement8.x, create3, create3, create);
        SecP521R1Field.subtract(create3, create2, secP521R1FieldElement8.x);
        SecP521R1FieldElement secP521R1FieldElement9 = new SecP521R1FieldElement(create6);
        if (!isOne) {
            SecP521R1Field.multiply(secP521R1FieldElement9.x, secP521R1FieldElement5.x, secP521R1FieldElement9.x, create);
        }
        if (!isOne2) {
            SecP521R1Field.multiply(secP521R1FieldElement9.x, secP521R1FieldElement6.x, secP521R1FieldElement9.x, create);
        }
        ECFieldElement[] eCFieldElementArr = new ECFieldElement[1];
        eCFieldElementArr[c] = secP521R1FieldElement9;
        return new SecP521R1Point(curve, secP521R1FieldElement7, secP521R1FieldElement8, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP521R1FieldElement secP521R1FieldElement = (SecP521R1FieldElement) this.y;
        if (secP521R1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        SecP521R1FieldElement secP521R1FieldElement2 = (SecP521R1FieldElement) this.x;
        SecP521R1FieldElement secP521R1FieldElement3 = (SecP521R1FieldElement) this.zs[0];
        int[] create = Nat.create(33);
        int[] create2 = Nat.create(17);
        int[] create3 = Nat.create(17);
        int[] create4 = Nat.create(17);
        SecP521R1Field.square(secP521R1FieldElement.x, create4, create);
        int[] create5 = Nat.create(17);
        SecP521R1Field.square(create4, create5, create);
        boolean isOne = secP521R1FieldElement3.isOne();
        int[] iArr = secP521R1FieldElement3.x;
        if (!isOne) {
            SecP521R1Field.square(secP521R1FieldElement3.x, create3, create);
            iArr = create3;
        }
        SecP521R1Field.subtract(secP521R1FieldElement2.x, iArr, create2);
        SecP521R1Field.add(secP521R1FieldElement2.x, iArr, create3);
        SecP521R1Field.multiply(create3, create2, create3, create);
        Nat.addBothTo(17, create3, create3, create3);
        SecP521R1Field.reduce23(create3);
        SecP521R1Field.multiply(create4, secP521R1FieldElement2.x, create4, create);
        Nat.shiftUpBits(17, create4, 2, 0);
        SecP521R1Field.reduce23(create4);
        Nat.shiftUpBits(17, create5, 3, 0, create2);
        SecP521R1Field.reduce23(create2);
        SecP521R1FieldElement secP521R1FieldElement4 = new SecP521R1FieldElement(create5);
        SecP521R1Field.square(create3, secP521R1FieldElement4.x, create);
        SecP521R1Field.subtract(secP521R1FieldElement4.x, create4, secP521R1FieldElement4.x);
        SecP521R1Field.subtract(secP521R1FieldElement4.x, create4, secP521R1FieldElement4.x);
        SecP521R1FieldElement secP521R1FieldElement5 = new SecP521R1FieldElement(create4);
        SecP521R1Field.subtract(create4, secP521R1FieldElement4.x, secP521R1FieldElement5.x);
        SecP521R1Field.multiply(secP521R1FieldElement5.x, create3, secP521R1FieldElement5.x, create);
        SecP521R1Field.subtract(secP521R1FieldElement5.x, create2, secP521R1FieldElement5.x);
        SecP521R1FieldElement secP521R1FieldElement6 = new SecP521R1FieldElement(create3);
        SecP521R1Field.twice(secP521R1FieldElement.x, secP521R1FieldElement6.x);
        if (!isOne) {
            SecP521R1Field.multiply(secP521R1FieldElement6.x, secP521R1FieldElement3.x, secP521R1FieldElement6.x, create);
        }
        return new SecP521R1Point(curve, secP521R1FieldElement4, secP521R1FieldElement5, new ECFieldElement[]{secP521R1FieldElement6});
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

    protected ECFieldElement two(ECFieldElement eCFieldElement) {
        return eCFieldElement.add(eCFieldElement);
    }

    protected ECFieldElement three(ECFieldElement eCFieldElement) {
        return two(eCFieldElement).add(eCFieldElement);
    }

    protected ECFieldElement four(ECFieldElement eCFieldElement) {
        return two(two(eCFieldElement));
    }

    protected ECFieldElement eight(ECFieldElement eCFieldElement) {
        return four(two(eCFieldElement));
    }

    protected ECFieldElement doubleProductFromSquares(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3, ECFieldElement eCFieldElement4) {
        return eCFieldElement.add(eCFieldElement2).square().subtract(eCFieldElement3).subtract(eCFieldElement4);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint negate() {
        return isInfinity() ? this : new SecP521R1Point(this.curve, this.x, this.y.negate(), this.zs);
    }
}
