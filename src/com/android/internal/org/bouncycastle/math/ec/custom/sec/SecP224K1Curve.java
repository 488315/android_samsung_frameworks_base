package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable;
import com.android.internal.org.bouncycastle.math.ec.ECConstants;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECLookupTable;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat224;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class SecP224K1Curve extends ECCurve.AbstractFp {
    private static final int SECP224K1_DEFAULT_COORDS = 2;
    protected SecP224K1Point infinity;
    public static final BigInteger q = SecP224K1FieldElement.Q;
    private static final ECFieldElement[] SECP224K1_AFFINE_ZS = {new SecP224K1FieldElement(ECConstants.ONE)};

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    public SecP224K1Curve() {
        super(q);
        this.infinity = new SecP224K1Point(this, null, null);
        this.a = fromBigInteger(ECConstants.ZERO);
        this.b = fromBigInteger(BigInteger.valueOf(5L));
        this.order = new BigInteger(1, Hex.decodeStrict("010000000000000000000000000001DCE8D2EC6184CAF0A971769FB1F7"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECCurve cloneCurve() {
        return new SecP224K1Curve();
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
        return new SecP224K1FieldElement(bigInteger);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecP224K1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecP224K1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i, final int i2) {
        final int[] iArr = new int[i2 * 14];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            ECPoint eCPoint = eCPointArr[i + i4];
            Nat224.copy(((SecP224K1FieldElement) eCPoint.getRawXCoord()).x, 0, iArr, i3);
            Nat224.copy(((SecP224K1FieldElement) eCPoint.getRawYCoord()).x, 0, iArr, i3 + 7);
            i3 += 14;
        }
        return new AbstractECLookupTable() { // from class: com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Curve.1
            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i2;
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i5) {
                int[] create = Nat224.create();
                int[] create2 = Nat224.create();
                int i6 = 0;
                for (int i7 = 0; i7 < i2; i7++) {
                    int i8 = ((i7 ^ i5) - 1) >> 31;
                    for (int i9 = 0; i9 < 7; i9++) {
                        int i10 = create[i9];
                        int[] iArr2 = iArr;
                        create[i9] = i10 ^ (iArr2[i6 + i9] & i8);
                        create2[i9] = create2[i9] ^ (iArr2[(i6 + 7) + i9] & i8);
                    }
                    i6 += 14;
                }
                return createPoint(create, create2);
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable, com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i5) {
                int[] create = Nat224.create();
                int[] create2 = Nat224.create();
                int i6 = 0;
                for (int i7 = 0; i7 < i2; i7++) {
                    int i8 = ((i7 ^ i5) - 1) >> 31;
                    for (int i9 = 0; i9 < 7; i9++) {
                        int i10 = create[i9];
                        int[] iArr2 = iArr;
                        create[i9] = i10 ^ (iArr2[i6 + i9] & i8);
                        create2[i9] = create2[i9] ^ (iArr2[(i6 + 7) + i9] & i8);
                    }
                    i6 += 14;
                }
                return createPoint(create, create2);
            }

            private ECPoint createPoint(int[] iArr2, int[] iArr3) {
                return SecP224K1Curve.this.createRawPoint(new SecP224K1FieldElement(iArr2), new SecP224K1FieldElement(iArr3), SecP224K1Curve.SECP224K1_AFFINE_ZS);
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElement(SecureRandom secureRandom) {
        int[] create = Nat224.create();
        SecP224K1Field.random(secureRandom, create);
        return new SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElementMult(SecureRandom secureRandom) {
        int[] create = Nat224.create();
        SecP224K1Field.randomMult(secureRandom, create);
        return new SecP224K1FieldElement(create);
    }
}
