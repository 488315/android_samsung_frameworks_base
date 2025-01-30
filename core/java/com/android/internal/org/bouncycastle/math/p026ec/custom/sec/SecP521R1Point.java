package com.android.internal.org.bouncycastle.math.p026ec.custom.sec;

import com.android.internal.org.bouncycastle.math.p026ec.ECCurve;
import com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.p026ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;

/* loaded from: classes5.dex */
public class SecP521R1Point extends ECPoint.AbstractFp {
  SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
    super(curve, x, y);
  }

  SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
    super(curve, x, y, zs);
  }

  @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
  protected ECPoint detach() {
    return new SecP521R1Point(null, getAffineXCoord(), getAffineYCoord());
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
    SecP521R1FieldElement X1 = (SecP521R1FieldElement) this.f941x;
    SecP521R1FieldElement Y1 = (SecP521R1FieldElement) this.f942y;
    SecP521R1FieldElement X2 = (SecP521R1FieldElement) b.getXCoord();
    SecP521R1FieldElement Y2 = (SecP521R1FieldElement) b.getYCoord();
    SecP521R1FieldElement Z1 = (SecP521R1FieldElement) this.f943zs[0];
    SecP521R1FieldElement Z2 = (SecP521R1FieldElement) b.getZCoord(0);
    int[] t1 = Nat.create(17);
    int[] t2 = Nat.create(17);
    int[] t3 = Nat.create(17);
    int[] t4 = Nat.create(17);
    boolean Z1IsOne = Z1.isOne();
    if (Z1IsOne) {
      U2 = X2.f987x;
      S2 = Y2.f987x;
    } else {
      S2 = t3;
      SecP521R1Field.square(Z1.f987x, S2);
      U2 = t2;
      SecP521R1Field.multiply(S2, X2.f987x, U2);
      SecP521R1Field.multiply(S2, Z1.f987x, S2);
      SecP521R1Field.multiply(S2, Y2.f987x, S2);
    }
    boolean Z2IsOne = Z2.isOne();
    if (Z2IsOne) {
      int[] U1 = X1.f987x;
      int[] U12 = Y1.f987x;
      S1 = U12;
      S12 = U1;
    } else {
      SecP521R1Field.square(Z2.f987x, t4);
      SecP521R1Field.multiply(t4, X1.f987x, t1);
      SecP521R1Field.multiply(t4, Z2.f987x, t4);
      SecP521R1Field.multiply(t4, Y1.f987x, t4);
      S1 = t4;
      S12 = t1;
    }
    int[] H = Nat.create(17);
    SecP521R1Field.subtract(S12, U2, H);
    SecP521R1Field.subtract(S1, S2, t2);
    if (Nat.isZero(17, H)) {
      if (Nat.isZero(17, t2)) {
        return twice();
      }
      return curve.getInfinity();
    }
    SecP521R1Field.square(H, t3);
    int[] G = Nat.create(17);
    SecP521R1Field.multiply(t3, H, G);
    SecP521R1Field.multiply(t3, S12, t3);
    SecP521R1Field.multiply(S1, G, t1);
    SecP521R1FieldElement X3 = new SecP521R1FieldElement(t4);
    int[] HSquared = X3.f987x;
    SecP521R1Field.square(t2, HSquared);
    int[] iArr = X3.f987x;
    int[] S13 = X3.f987x;
    SecP521R1Field.add(iArr, G, S13);
    SecP521R1Field.subtract(X3.f987x, t3, X3.f987x);
    SecP521R1Field.subtract(X3.f987x, t3, X3.f987x);
    SecP521R1FieldElement Y3 = new SecP521R1FieldElement(G);
    SecP521R1Field.subtract(t3, X3.f987x, Y3.f987x);
    SecP521R1Field.multiply(Y3.f987x, t2, t2);
    SecP521R1Field.subtract(t2, t1, Y3.f987x);
    SecP521R1FieldElement Z3 = new SecP521R1FieldElement(H);
    if (!Z1IsOne) {
      int[] iArr2 = Z3.f987x;
      int[] H2 = Z1.f987x;
      int[] R = Z3.f987x;
      SecP521R1Field.multiply(iArr2, H2, R);
    }
    if (!Z2IsOne) {
      SecP521R1Field.multiply(Z3.f987x, Z2.f987x, Z3.f987x);
    }
    ECFieldElement[] zs = {Z3};
    return new SecP521R1Point(curve, X3, Y3, zs);
  }

  @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
  public ECPoint twice() {
    if (isInfinity()) {
      return this;
    }
    ECCurve curve = getCurve();
    SecP521R1FieldElement Y1 = (SecP521R1FieldElement) this.f942y;
    if (Y1.isZero()) {
      return curve.getInfinity();
    }
    SecP521R1FieldElement X1 = (SecP521R1FieldElement) this.f941x;
    SecP521R1FieldElement Z1 = (SecP521R1FieldElement) this.f943zs[0];
    int[] t1 = Nat.create(17);
    int[] t2 = Nat.create(17);
    int[] Y1Squared = Nat.create(17);
    SecP521R1Field.square(Y1.f987x, Y1Squared);
    int[] T = Nat.create(17);
    SecP521R1Field.square(Y1Squared, T);
    boolean Z1IsOne = Z1.isOne();
    int[] Z1Squared = Z1.f987x;
    if (!Z1IsOne) {
      Z1Squared = t2;
      SecP521R1Field.square(Z1.f987x, Z1Squared);
    }
    SecP521R1Field.subtract(X1.f987x, Z1Squared, t1);
    SecP521R1Field.add(X1.f987x, Z1Squared, t2);
    SecP521R1Field.multiply(t2, t1, t2);
    Nat.addBothTo(17, t2, t2, t2);
    SecP521R1Field.reduce23(t2);
    SecP521R1Field.multiply(Y1Squared, X1.f987x, Y1Squared);
    Nat.shiftUpBits(17, Y1Squared, 2, 0);
    SecP521R1Field.reduce23(Y1Squared);
    Nat.shiftUpBits(17, T, 3, 0, t1);
    SecP521R1Field.reduce23(t1);
    SecP521R1FieldElement X3 = new SecP521R1FieldElement(T);
    SecP521R1Field.square(t2, X3.f987x);
    SecP521R1Field.subtract(X3.f987x, Y1Squared, X3.f987x);
    SecP521R1Field.subtract(X3.f987x, Y1Squared, X3.f987x);
    SecP521R1FieldElement Y3 = new SecP521R1FieldElement(Y1Squared);
    SecP521R1Field.subtract(Y1Squared, X3.f987x, Y3.f987x);
    SecP521R1Field.multiply(Y3.f987x, t2, Y3.f987x);
    SecP521R1Field.subtract(Y3.f987x, t1, Y3.f987x);
    SecP521R1FieldElement Z3 = new SecP521R1FieldElement(t2);
    SecP521R1Field.twice(Y1.f987x, Z3.f987x);
    if (!Z1IsOne) {
      SecP521R1Field.multiply(Z3.f987x, Z1.f987x, Z3.f987x);
    }
    return new SecP521R1Point(curve, X3, Y3, new ECFieldElement[] {Z3});
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

  protected ECFieldElement two(ECFieldElement x) {
    return x.add(x);
  }

  protected ECFieldElement three(ECFieldElement x) {
    return two(x).add(x);
  }

  protected ECFieldElement four(ECFieldElement x) {
    return two(two(x));
  }

  protected ECFieldElement eight(ECFieldElement x) {
    return four(two(x));
  }

  protected ECFieldElement doubleProductFromSquares(
      ECFieldElement a, ECFieldElement b, ECFieldElement aSquared, ECFieldElement bSquared) {
    return a.add(b).square().subtract(aSquared).subtract(bSquared);
  }

  @Override // com.android.internal.org.bouncycastle.math.p026ec.ECPoint
  public ECPoint negate() {
    if (isInfinity()) {
      return this;
    }
    return new SecP521R1Point(this.curve, this.f941x, this.f942y.negate(), this.f943zs);
  }
}
