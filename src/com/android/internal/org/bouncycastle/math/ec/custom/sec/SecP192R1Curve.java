package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable;
import com.android.internal.org.bouncycastle.math.ec.ECConstants;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECLookupTable;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat192;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class SecP192R1Curve extends ECCurve.AbstractFp {
    private static final int SECP192R1_DEFAULT_COORDS = 2;
    protected SecP192R1Point infinity;
    public static final BigInteger q = SecP192R1FieldElement.Q;
    private static final ECFieldElement[] SECP192R1_AFFINE_ZS = {new SecP192R1FieldElement(ECConstants.ONE)};

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    public SecP192R1Curve() {
        super(q);
        this.infinity = new SecP192R1Point(this, null, null);
        this.a = fromBigInteger(new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC")));
        this.b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1")));
        this.order = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECCurve cloneCurve() {
        return new SecP192R1Curve();
    }

    public BigInteger getQ() {
        return q;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP192R1FieldElement(bigInteger);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecP192R1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecP192R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i, final int i2) {
        final int[] iArr = new int[i2 * 12];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            ECPoint eCPoint = eCPointArr[i + i4];
            Nat192.copy(((SecP192R1FieldElement) eCPoint.getRawXCoord()).x, 0, iArr, i3);
            Nat192.copy(((SecP192R1FieldElement) eCPoint.getRawYCoord()).x, 0, iArr, i3 + 6);
            i3 += 12;
        }
        return new AbstractECLookupTable() { // from class: com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Curve.1
            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i2;
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i5) {
                int[] create = Nat192.create();
                int[] create2 = Nat192.create();
                int i6 = 0;
                for (int i7 = 0; i7 < i2; i7++) {
                    int i8 = ((i7 ^ i5) - 1) >> 31;
                    for (int i9 = 0; i9 < 6; i9++) {
                        int i10 = create[i9];
                        int[] iArr2 = iArr;
                        create[i9] = i10 ^ (iArr2[i6 + i9] & i8);
                        create2[i9] = create2[i9] ^ (iArr2[(i6 + 6) + i9] & i8);
                    }
                    i6 += 12;
                }
                return createPoint(create, create2);
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable, com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i5) {
                int[] create = Nat192.create();
                int[] create2 = Nat192.create();
                int i6 = i5 * 12;
                for (int i7 = 0; i7 < 6; i7++) {
                    int[] iArr2 = iArr;
                    create[i7] = iArr2[i6 + i7];
                    create2[i7] = iArr2[6 + i6 + i7];
                }
                return createPoint(create, create2);
            }

            private ECPoint createPoint(int[] iArr2, int[] iArr3) {
                return SecP192R1Curve.this.createRawPoint(new SecP192R1FieldElement(iArr2), new SecP192R1FieldElement(iArr3), SecP192R1Curve.SECP192R1_AFFINE_ZS);
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElement(SecureRandom secureRandom) {
        int[] create = Nat192.create();
        SecP192R1Field.random(secureRandom, create);
        return new SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElementMult(SecureRandom secureRandom) {
        int[] create = Nat192.create();
        SecP192R1Field.randomMult(secureRandom, create);
        return new SecP192R1FieldElement(create);
    }
}
