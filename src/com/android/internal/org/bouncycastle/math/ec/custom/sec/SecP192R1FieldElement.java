package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat192;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SecP192R1FieldElement extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"));
    protected int[] x;

    public SecP192R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192R1FieldElement");
        }
        this.x = SecP192R1Field.fromBigInteger(bigInteger);
    }

    public SecP192R1FieldElement() {
        this.x = Nat192.create();
    }

    protected SecP192R1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat192.isZero(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat192.isOne(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat192.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat192.toBigInteger(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP192R1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.add(this.x, ((SecP192R1FieldElement) eCFieldElement).x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat192.create();
        SecP192R1Field.addOne(this.x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.subtract(this.x, ((SecP192R1FieldElement) eCFieldElement).x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.multiply(this.x, ((SecP192R1FieldElement) eCFieldElement).x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.inv(((SecP192R1FieldElement) eCFieldElement).x, create);
        SecP192R1Field.multiply(create, this.x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat192.create();
        SecP192R1Field.negate(this.x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat192.create();
        SecP192R1Field.square(this.x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat192.create();
        SecP192R1Field.inv(this.x, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (Nat192.isZero(iArr) || Nat192.isOne(iArr)) {
            return this;
        }
        int[] create = Nat192.create();
        int[] create2 = Nat192.create();
        SecP192R1Field.square(iArr, create);
        SecP192R1Field.multiply(create, iArr, create);
        SecP192R1Field.squareN(create, 2, create2);
        SecP192R1Field.multiply(create2, create, create2);
        SecP192R1Field.squareN(create2, 4, create);
        SecP192R1Field.multiply(create, create2, create);
        SecP192R1Field.squareN(create, 8, create2);
        SecP192R1Field.multiply(create2, create, create2);
        SecP192R1Field.squareN(create2, 16, create);
        SecP192R1Field.multiply(create, create2, create);
        SecP192R1Field.squareN(create, 32, create2);
        SecP192R1Field.multiply(create2, create, create2);
        SecP192R1Field.squareN(create2, 64, create);
        SecP192R1Field.multiply(create, create2, create);
        SecP192R1Field.squareN(create, 62, create);
        SecP192R1Field.square(create, create2);
        if (Nat192.eq(iArr, create2)) {
            return new SecP192R1FieldElement(create);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP192R1FieldElement) {
            return Nat192.eq(this.x, ((SecP192R1FieldElement) obj).x);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.x, 0, 6) ^ Q.hashCode();
    }
}
