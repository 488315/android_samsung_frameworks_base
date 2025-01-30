package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SecP521R1FieldElement extends ECFieldElement.AbstractFp {

  /* renamed from: Q */
  public static final BigInteger f986Q =
      new BigInteger(
          1,
          Hex.decodeStrict(
              "01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));

  /* renamed from: x */
  protected int[] f987x;

  public SecP521R1FieldElement(BigInteger x) {
    if (x == null || x.signum() < 0 || x.compareTo(f986Q) >= 0) {
      throw new IllegalArgumentException("x value invalid for SecP521R1FieldElement");
    }
    this.f987x = SecP521R1Field.fromBigInteger(x);
  }

  public SecP521R1FieldElement() {
    this.f987x = Nat.create(17);
  }

  protected SecP521R1FieldElement(int[] x) {
    this.f987x = x;
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public boolean isZero() {
    return Nat.isZero(17, this.f987x);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public boolean isOne() {
    return Nat.isOne(17, this.f987x);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public boolean testBitZero() {
    return Nat.getBit(this.f987x, 0) == 1;
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public BigInteger toBigInteger() {
    return Nat.toBigInteger(17, this.f987x);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public String getFieldName() {
    return "SecP521R1Field";
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public int getFieldSize() {
    return f986Q.bitLength();
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement add(ECFieldElement b) {
    int[] z = Nat.create(17);
    SecP521R1Field.add(this.f987x, ((SecP521R1FieldElement) b).f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement addOne() {
    int[] z = Nat.create(17);
    SecP521R1Field.addOne(this.f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement subtract(ECFieldElement b) {
    int[] z = Nat.create(17);
    SecP521R1Field.subtract(this.f987x, ((SecP521R1FieldElement) b).f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement multiply(ECFieldElement b) {
    int[] z = Nat.create(17);
    SecP521R1Field.multiply(this.f987x, ((SecP521R1FieldElement) b).f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement divide(ECFieldElement b) {
    int[] z = Nat.create(17);
    SecP521R1Field.inv(((SecP521R1FieldElement) b).f987x, z);
    SecP521R1Field.multiply(z, this.f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement negate() {
    int[] z = Nat.create(17);
    SecP521R1Field.negate(this.f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement square() {
    int[] z = Nat.create(17);
    SecP521R1Field.square(this.f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement invert() {
    int[] z = Nat.create(17);
    SecP521R1Field.inv(this.f987x, z);
    return new SecP521R1FieldElement(z);
  }

  @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
  public ECFieldElement sqrt() {
    int[] x1 = this.f987x;
    if (Nat.isZero(17, x1) || Nat.isOne(17, x1)) {
      return this;
    }
    int[] t1 = Nat.create(17);
    int[] t2 = Nat.create(17);
    SecP521R1Field.squareN(x1, 519, t1);
    SecP521R1Field.square(t1, t2);
    if (Nat.m203eq(17, x1, t2)) {
      return new SecP521R1FieldElement(t1);
    }
    return null;
  }

  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof SecP521R1FieldElement)) {
      return false;
    }
    SecP521R1FieldElement o = (SecP521R1FieldElement) other;
    return Nat.m203eq(17, this.f987x, o.f987x);
  }

  public int hashCode() {
    return f986Q.hashCode() ^ Arrays.hashCode(this.f987x, 0, 17);
  }
}
