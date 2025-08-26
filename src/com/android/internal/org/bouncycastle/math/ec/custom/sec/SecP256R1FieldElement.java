package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.raw.Nat256;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class SecP256R1FieldElement extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));
    protected int[] x;

    public SecP256R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
        }
        this.x = SecP256R1Field.fromBigInteger(bigInteger);
    }

    public SecP256R1FieldElement() {
        this.x = Nat256.create();
    }

    protected SecP256R1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat256.isZero(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat256.isOne(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat256.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat256.toBigInteger(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP256R1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.add(this.x, ((SecP256R1FieldElement) eCFieldElement).x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.addOne(this.x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.subtract(this.x, ((SecP256R1FieldElement) eCFieldElement).x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.multiply(this.x, ((SecP256R1FieldElement) eCFieldElement).x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.inv(((SecP256R1FieldElement) eCFieldElement).x, iArrCreate);
        SecP256R1Field.multiply(iArrCreate, this.x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.negate(this.x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.square(this.x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] iArrCreate = Nat256.create();
        SecP256R1Field.inv(this.x, iArrCreate);
        return new SecP256R1FieldElement(iArrCreate);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (Nat256.isZero(iArr) || Nat256.isOne(iArr)) {
            return this;
        }
        int[] iArrCreateExt = Nat256.createExt();
        int[] iArrCreate = Nat256.create();
        int[] iArrCreate2 = Nat256.create();
        SecP256R1Field.square(iArr, iArrCreate, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate, iArr, iArrCreate, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate, 2, iArrCreate2, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate2, iArrCreate, iArrCreate2, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate2, 4, iArrCreate, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate, iArrCreate2, iArrCreate, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate, 8, iArrCreate2, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate2, iArrCreate, iArrCreate2, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate2, 16, iArrCreate, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate, iArrCreate2, iArrCreate, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate, 32, iArrCreate, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate, iArr, iArrCreate, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate, 96, iArrCreate, iArrCreateExt);
        SecP256R1Field.multiply(iArrCreate, iArr, iArrCreate, iArrCreateExt);
        SecP256R1Field.squareN(iArrCreate, 94, iArrCreate, iArrCreateExt);
        SecP256R1Field.square(iArrCreate, iArrCreate2, iArrCreateExt);
        if (Nat256.eq(iArr, iArrCreate2)) {
            return new SecP256R1FieldElement(iArrCreate);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP256R1FieldElement) {
            return Nat256.eq(this.x, ((SecP256R1FieldElement) obj).x);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.x, 0, 8) ^ Q.hashCode();
    }
}
