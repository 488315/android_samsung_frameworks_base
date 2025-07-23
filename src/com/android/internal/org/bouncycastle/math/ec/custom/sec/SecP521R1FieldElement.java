package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SecP521R1FieldElement extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = new BigInteger(1, Hex.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));
    protected int[] x;

    public SecP521R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP521R1FieldElement");
        }
        this.x = SecP521R1Field.fromBigInteger(bigInteger);
    }

    public SecP521R1FieldElement() {
        this.x = Nat.create(17);
    }

    protected SecP521R1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat.isZero(17, this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat.isOne(17, this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat.toBigInteger(17, this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP521R1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.add(this.x, ((SecP521R1FieldElement) eCFieldElement).x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat.create(17);
        SecP521R1Field.addOne(this.x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.subtract(this.x, ((SecP521R1FieldElement) eCFieldElement).x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.multiply(this.x, ((SecP521R1FieldElement) eCFieldElement).x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.inv(((SecP521R1FieldElement) eCFieldElement).x, create);
        SecP521R1Field.multiply(create, this.x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat.create(17);
        SecP521R1Field.negate(this.x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat.create(17);
        SecP521R1Field.square(this.x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat.create(17);
        SecP521R1Field.inv(this.x, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (Nat.isZero(17, iArr) || Nat.isOne(17, iArr)) {
            return this;
        }
        int[] create = Nat.create(33);
        int[] create2 = Nat.create(17);
        int[] create3 = Nat.create(17);
        SecP521R1Field.squareN(iArr, 519, create2, create);
        SecP521R1Field.square(create2, create3, create);
        if (Nat.eq(17, iArr, create3)) {
            return new SecP521R1FieldElement(create2);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP521R1FieldElement) {
            return Nat.eq(17, this.x, ((SecP521R1FieldElement) obj).x);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.x, 0, 17) ^ Q.hashCode();
    }
}
