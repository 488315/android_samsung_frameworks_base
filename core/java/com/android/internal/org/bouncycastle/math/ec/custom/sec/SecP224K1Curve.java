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

public class SecP224K1Curve extends ECCurve.AbstractFp {
    private static final int SECP224K1_DEFAULT_COORDS = 2;
    protected SecP224K1Point infinity;
    public static final BigInteger q = SecP224K1FieldElement.Q;
    private static final ECFieldElement[] SECP224K1_AFFINE_ZS = {
        new SecP224K1FieldElement(ECConstants.ONE)
    };

    public SecP224K1Curve() {
        super(q);
        this.infinity = new SecP224K1Point(this, null, null);
        this.a = fromBigInteger(ECConstants.ZERO);
        this.b = fromBigInteger(BigInteger.valueOf(5L));
        this.order =
                new BigInteger(
                        1,
                        Hex.decodeStrict(
                                "010000000000000000000000000001DCE8D2EC6184CAF0A971769FB1F7"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECCurve cloneCurve() {
        return new SecP224K1Curve();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int coord) {
        switch (coord) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    public BigInteger getQ() {
        return q;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger x) {
        return new SecP224K1FieldElement(x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y) {
        return new SecP224K1Point(this, x, y);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
        return new SecP224K1Point(this, x, y, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] points, int off, final int len) {
        final int[] table = new int[len * 7 * 2];
        int pos = 0;
        for (int i = 0; i < len; i++) {
            ECPoint p = points[off + i];
            Nat224.copy(((SecP224K1FieldElement) p.getRawXCoord()).x, 0, table, pos);
            int pos2 = pos + 7;
            Nat224.copy(((SecP224K1FieldElement) p.getRawYCoord()).x, 0, table, pos2);
            pos = pos2 + 7;
        }
        return new AbstractECLookupTable() { // from class:
                                             // com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Curve.1
            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return len;
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int index) {
                int[] x = Nat224.create();
                int[] y = Nat224.create();
                int pos3 = 0;
                for (int i2 = 0; i2 < len; i2++) {
                    int MASK = ((i2 ^ index) - 1) >> 31;
                    for (int j = 0; j < 7; j++) {
                        x[j] = x[j] ^ (table[pos3 + j] & MASK);
                        y[j] = y[j] ^ (table[(pos3 + 7) + j] & MASK);
                    }
                    pos3 += 14;
                }
                return createPoint(x, y);
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable,
                      // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int index) {
                int[] x = Nat224.create();
                int[] y = Nat224.create();
                int pos3 = 0;
                for (int i2 = 0; i2 < len; i2++) {
                    int MASK = ((i2 ^ index) - 1) >> 31;
                    for (int j = 0; j < 7; j++) {
                        x[j] = x[j] ^ (table[pos3 + j] & MASK);
                        y[j] = y[j] ^ (table[(pos3 + 7) + j] & MASK);
                    }
                    pos3 += 14;
                }
                return createPoint(x, y);
            }

            private ECPoint createPoint(int[] x, int[] y) {
                return SecP224K1Curve.this.createRawPoint(
                        new SecP224K1FieldElement(x),
                        new SecP224K1FieldElement(y),
                        SecP224K1Curve.SECP224K1_AFFINE_ZS);
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp,
              // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElement(SecureRandom r) {
        int[] x = Nat224.create();
        SecP224K1Field.random(r, x);
        return new SecP224K1FieldElement(x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp,
              // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElementMult(SecureRandom r) {
        int[] x = Nat224.create();
        SecP224K1Field.randomMult(r, x);
        return new SecP224K1FieldElement(x);
    }
}
