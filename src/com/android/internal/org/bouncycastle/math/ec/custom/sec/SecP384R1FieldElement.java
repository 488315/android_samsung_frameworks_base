package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SecP384R1FieldElement extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF"));
    protected int[] x;

    public SecP384R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP384R1FieldElement");
        }
        this.x = SecP384R1Field.fromBigInteger(bigInteger);
    }

    public SecP384R1FieldElement() {
        this.x = Nat.create(12);
    }

    protected SecP384R1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat.isZero(12, this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat.isOne(12, this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat.toBigInteger(12, this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP384R1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(12);
        SecP384R1Field.add(this.x, ((SecP384R1FieldElement) eCFieldElement).x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat.create(12);
        SecP384R1Field.addOne(this.x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(12);
        SecP384R1Field.subtract(this.x, ((SecP384R1FieldElement) eCFieldElement).x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(12);
        SecP384R1Field.multiply(this.x, ((SecP384R1FieldElement) eCFieldElement).x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(12);
        SecP384R1Field.inv(((SecP384R1FieldElement) eCFieldElement).x, create);
        SecP384R1Field.multiply(create, this.x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat.create(12);
        SecP384R1Field.negate(this.x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat.create(12);
        SecP384R1Field.square(this.x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat.create(12);
        SecP384R1Field.inv(this.x, create);
        return new SecP384R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (Nat.isZero(12, iArr) || Nat.isOne(12, iArr)) {
            return this;
        }
        int[] create = Nat.create(24);
        int[] create2 = Nat.create(12);
        int[] create3 = Nat.create(12);
        int[] create4 = Nat.create(12);
        int[] create5 = Nat.create(12);
        SecP384R1Field.square(iArr, create2, create);
        SecP384R1Field.multiply(create2, iArr, create2, create);
        SecP384R1Field.squareN(create2, 2, create3, create);
        SecP384R1Field.multiply(create3, create2, create3, create);
        SecP384R1Field.square(create3, create3, create);
        SecP384R1Field.multiply(create3, iArr, create3, create);
        SecP384R1Field.squareN(create3, 5, create4, create);
        SecP384R1Field.multiply(create4, create3, create4, create);
        SecP384R1Field.squareN(create4, 5, create5, create);
        SecP384R1Field.multiply(create5, create3, create5, create);
        SecP384R1Field.squareN(create5, 15, create3, create);
        SecP384R1Field.multiply(create3, create5, create3, create);
        SecP384R1Field.squareN(create3, 2, create4, create);
        SecP384R1Field.multiply(create2, create4, create2, create);
        SecP384R1Field.squareN(create4, 28, create4, create);
        SecP384R1Field.multiply(create3, create4, create3, create);
        SecP384R1Field.squareN(create3, 60, create4, create);
        SecP384R1Field.multiply(create4, create3, create4, create);
        SecP384R1Field.squareN(create4, 120, create3, create);
        SecP384R1Field.multiply(create3, create4, create3, create);
        SecP384R1Field.squareN(create3, 15, create3, create);
        SecP384R1Field.multiply(create3, create5, create3, create);
        SecP384R1Field.squareN(create3, 33, create3, create);
        SecP384R1Field.multiply(create3, create2, create3, create);
        SecP384R1Field.squareN(create3, 64, create3, create);
        SecP384R1Field.multiply(create3, iArr, create3, create);
        SecP384R1Field.squareN(create3, 30, create2, create);
        SecP384R1Field.square(create2, create3, create);
        if (Nat.eq(12, iArr, create3)) {
            return new SecP384R1FieldElement(create2);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP384R1FieldElement) {
            return Nat.eq(12, this.x, ((SecP384R1FieldElement) obj).x);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.x, 0, 12) ^ Q.hashCode();
    }
}
