package com.android.internal.org.bouncycastle.math.p026ec.custom.sec;

import com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat192;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SecP192K1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f949Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37"));

    /* renamed from: x */
    protected int[] f950x;

    public SecP192K1FieldElement(BigInteger x) {
        if (x == null || x.signum() < 0 || x.compareTo(f949Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192K1FieldElement");
        }
        this.f950x = SecP192K1Field.fromBigInteger(x);
    }

    public SecP192K1FieldElement() {
        this.f950x = Nat192.create();
    }

    protected SecP192K1FieldElement(int[] x) {
        this.f950x = x;
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public boolean isZero() {
        return Nat192.isZero(this.f950x);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public boolean isOne() {
        return Nat192.isOne(this.f950x);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public boolean testBitZero() {
        return Nat192.getBit(this.f950x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat192.toBigInteger(this.f950x);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public String getFieldName() {
        return "SecP192K1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public int getFieldSize() {
        return f949Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement add(ECFieldElement b) {
        int[] z = Nat192.create();
        SecP192K1Field.add(this.f950x, ((SecP192K1FieldElement) b).f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] z = Nat192.create();
        SecP192K1Field.addOne(this.f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement b) {
        int[] z = Nat192.create();
        SecP192K1Field.subtract(this.f950x, ((SecP192K1FieldElement) b).f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement b) {
        int[] z = Nat192.create();
        SecP192K1Field.multiply(this.f950x, ((SecP192K1FieldElement) b).f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement b) {
        int[] z = Nat192.create();
        SecP192K1Field.inv(((SecP192K1FieldElement) b).f950x, z);
        SecP192K1Field.multiply(z, this.f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement negate() {
        int[] z = Nat192.create();
        SecP192K1Field.negate(this.f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement square() {
        int[] z = Nat192.create();
        SecP192K1Field.square(this.f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement invert() {
        int[] z = Nat192.create();
        SecP192K1Field.inv(this.f950x, z);
        return new SecP192K1FieldElement(z);
    }

    @Override // com.android.internal.org.bouncycastle.math.p026ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] x1 = this.f950x;
        if (Nat192.isZero(x1) || Nat192.isOne(x1)) {
            return this;
        }
        int[] x2 = Nat192.create();
        SecP192K1Field.square(x1, x2);
        SecP192K1Field.multiply(x2, x1, x2);
        int[] x3 = Nat192.create();
        SecP192K1Field.square(x2, x3);
        SecP192K1Field.multiply(x3, x1, x3);
        int[] x6 = Nat192.create();
        SecP192K1Field.squareN(x3, 3, x6);
        SecP192K1Field.multiply(x6, x3, x6);
        SecP192K1Field.squareN(x6, 2, x6);
        SecP192K1Field.multiply(x6, x2, x6);
        SecP192K1Field.squareN(x6, 8, x2);
        SecP192K1Field.multiply(x2, x6, x2);
        SecP192K1Field.squareN(x2, 3, x6);
        SecP192K1Field.multiply(x6, x3, x6);
        int[] x35 = Nat192.create();
        SecP192K1Field.squareN(x6, 16, x35);
        SecP192K1Field.multiply(x35, x2, x35);
        SecP192K1Field.squareN(x35, 35, x2);
        SecP192K1Field.multiply(x2, x35, x2);
        SecP192K1Field.squareN(x2, 70, x35);
        SecP192K1Field.multiply(x35, x2, x35);
        SecP192K1Field.squareN(x35, 19, x2);
        SecP192K1Field.multiply(x2, x6, x2);
        SecP192K1Field.squareN(x2, 20, x2);
        SecP192K1Field.multiply(x2, x6, x2);
        SecP192K1Field.squareN(x2, 4, x2);
        SecP192K1Field.multiply(x2, x3, x2);
        SecP192K1Field.squareN(x2, 6, x2);
        SecP192K1Field.multiply(x2, x3, x2);
        SecP192K1Field.square(x2, x2);
        SecP192K1Field.square(x2, x3);
        if (Nat192.m204eq(x1, x3)) {
            return new SecP192K1FieldElement(x2);
        }
        return null;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SecP192K1FieldElement)) {
            return false;
        }
        SecP192K1FieldElement o = (SecP192K1FieldElement) other;
        return Nat192.m204eq(this.f950x, o.f950x);
    }

    public int hashCode() {
        return f949Q.hashCode() ^ Arrays.hashCode(this.f950x, 0, 6);
    }
}
