package com.android.internal.org.bouncycastle.math.ec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
class Tnaf {
    private static final BigInteger MINUS_ONE;
    private static final BigInteger MINUS_THREE;
    private static final BigInteger MINUS_TWO;
    public static final byte WIDTH = 4;
    public static final ZTauElement[] alpha0;
    public static final byte[][] alpha0Tnaf;
    public static final ZTauElement[] alpha1;
    public static final byte[][] alpha1Tnaf;

    public static byte getMu(int i) {
        return (byte) (i == 0 ? -1 : 1);
    }

    Tnaf() {
    }

    static {
        BigInteger bigIntegerNegate = ECConstants.ONE.negate();
        MINUS_ONE = bigIntegerNegate;
        MINUS_TWO = ECConstants.TWO.negate();
        BigInteger bigIntegerNegate2 = ECConstants.THREE.negate();
        MINUS_THREE = bigIntegerNegate2;
        alpha0 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(bigIntegerNegate2, bigIntegerNegate), null, new ZTauElement(bigIntegerNegate, bigIntegerNegate), null, new ZTauElement(ECConstants.ONE, bigIntegerNegate), null, new ZTauElement(bigIntegerNegate, ECConstants.ONE), null, new ZTauElement(ECConstants.ONE, ECConstants.ONE), null, new ZTauElement(ECConstants.THREE, ECConstants.ONE), null, new ZTauElement(bigIntegerNegate, ECConstants.ZERO)};
        alpha0Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
        alpha1 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(bigIntegerNegate2, ECConstants.ONE), null, new ZTauElement(bigIntegerNegate, ECConstants.ONE), null, new ZTauElement(ECConstants.ONE, ECConstants.ONE), null, new ZTauElement(bigIntegerNegate, bigIntegerNegate), null, new ZTauElement(ECConstants.ONE, bigIntegerNegate), null, new ZTauElement(ECConstants.THREE, bigIntegerNegate), null, new ZTauElement(bigIntegerNegate, ECConstants.ZERO)};
        alpha1Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};
    }

    public static BigInteger norm(byte b, ZTauElement zTauElement) {
        BigInteger bigIntegerMultiply = zTauElement.u.multiply(zTauElement.u);
        if (b == 1) {
            return zTauElement.v.shiftLeft(1).add(zTauElement.u).multiply(zTauElement.v).add(bigIntegerMultiply);
        }
        if (b == -1) {
            return zTauElement.v.shiftLeft(1).subtract(zTauElement.u).multiply(zTauElement.v).add(bigIntegerMultiply);
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static SimpleBigDecimal norm(byte b, SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2) {
        SimpleBigDecimal simpleBigDecimalMultiply = simpleBigDecimal.multiply(simpleBigDecimal);
        SimpleBigDecimal simpleBigDecimalMultiply2 = simpleBigDecimal.multiply(simpleBigDecimal2);
        SimpleBigDecimal simpleBigDecimalShiftLeft = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
        if (b == 1) {
            return simpleBigDecimalMultiply.add(simpleBigDecimalMultiply2).add(simpleBigDecimalShiftLeft);
        }
        if (b == -1) {
            return simpleBigDecimalMultiply.subtract(simpleBigDecimalMultiply2).add(simpleBigDecimalShiftLeft);
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0083, code lost:
    
        if (r5.compareTo(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE) >= 0) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ZTauElement round(SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2, byte b) {
        SimpleBigDecimal simpleBigDecimalSubtract;
        SimpleBigDecimal simpleBigDecimalAdd;
        SimpleBigDecimal simpleBigDecimalSubtract2;
        if (simpleBigDecimal2.getScale() != simpleBigDecimal.getScale()) {
            throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
        }
        int i = -1;
        int i2 = 1;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        BigInteger bigIntegerRound = simpleBigDecimal.round();
        BigInteger bigIntegerRound2 = simpleBigDecimal2.round();
        SimpleBigDecimal simpleBigDecimalSubtract3 = simpleBigDecimal.subtract(bigIntegerRound);
        SimpleBigDecimal simpleBigDecimalSubtract4 = simpleBigDecimal2.subtract(bigIntegerRound2);
        SimpleBigDecimal simpleBigDecimalAdd2 = simpleBigDecimalSubtract3.add(simpleBigDecimalSubtract3);
        if (b == 1) {
            simpleBigDecimalSubtract = simpleBigDecimalAdd2.add(simpleBigDecimalSubtract4);
        } else {
            simpleBigDecimalSubtract = simpleBigDecimalAdd2.subtract(simpleBigDecimalSubtract4);
        }
        SimpleBigDecimal simpleBigDecimalAdd3 = simpleBigDecimalSubtract4.add(simpleBigDecimalSubtract4).add(simpleBigDecimalSubtract4);
        SimpleBigDecimal simpleBigDecimalAdd4 = simpleBigDecimalAdd3.add(simpleBigDecimalSubtract4);
        if (b == 1) {
            simpleBigDecimalAdd = simpleBigDecimalSubtract3.subtract(simpleBigDecimalAdd3);
            simpleBigDecimalSubtract2 = simpleBigDecimalSubtract3.add(simpleBigDecimalAdd4);
        } else {
            simpleBigDecimalAdd = simpleBigDecimalSubtract3.add(simpleBigDecimalAdd3);
            simpleBigDecimalSubtract2 = simpleBigDecimalSubtract3.subtract(simpleBigDecimalAdd4);
        }
        byte b2 = 0;
        if (simpleBigDecimalSubtract.compareTo(ECConstants.ONE) >= 0) {
            if (simpleBigDecimalAdd.compareTo(MINUS_ONE) < 0) {
                i2 = 0;
                b2 = b;
            }
        } else if (simpleBigDecimalSubtract2.compareTo(ECConstants.TWO) < 0) {
            i2 = 0;
        }
        if (simpleBigDecimalSubtract.compareTo(MINUS_ONE) >= 0) {
            if (simpleBigDecimalSubtract2.compareTo(MINUS_TWO) < 0) {
            }
            i = i2;
            return new ZTauElement(bigIntegerRound.add(BigInteger.valueOf(i)), bigIntegerRound2.add(BigInteger.valueOf(b2)));
        }
        b2 = (byte) (-b);
        i = i2;
        return new ZTauElement(bigIntegerRound.add(BigInteger.valueOf(i)), bigIntegerRound2.add(BigInteger.valueOf(b2)));
    }

    public static SimpleBigDecimal approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b, int i, int i2) {
        BigInteger bigIntegerMultiply = bigInteger2.multiply(bigInteger.shiftRight(((i - r0) - 2) + b));
        BigInteger bigIntegerAdd = bigIntegerMultiply.add(bigInteger3.multiply(bigIntegerMultiply.shiftRight(i)));
        int i3 = (((i + 5) / 2) + i2) - i2;
        BigInteger bigIntegerShiftRight = bigIntegerAdd.shiftRight(i3);
        if (bigIntegerAdd.testBit(i3 - 1)) {
            bigIntegerShiftRight = bigIntegerShiftRight.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(bigIntegerShiftRight, i2);
    }

    public static byte[] tauAdicNaf(byte b, ZTauElement zTauElement) {
        BigInteger bigIntegerSubtract;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int iBitLength = norm(b, zTauElement).bitLength();
        byte[] bArr = new byte[iBitLength > 30 ? iBitLength + 4 : 34];
        BigInteger bigIntegerAdd = zTauElement.u;
        BigInteger bigInteger = zTauElement.v;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (!bigIntegerAdd.equals(ECConstants.ZERO) || !bigInteger.equals(ECConstants.ZERO)) {
                if (bigIntegerAdd.testBit(0)) {
                    byte bIntValue = (byte) ECConstants.TWO.subtract(bigIntegerAdd.subtract(bigInteger.shiftLeft(1)).mod(ECConstants.FOUR)).intValue();
                    bArr[i2] = bIntValue;
                    if (bIntValue == 1) {
                        bigIntegerAdd = bigIntegerAdd.clearBit(0);
                    } else {
                        bigIntegerAdd = bigIntegerAdd.add(ECConstants.ONE);
                    }
                    i = i2;
                } else {
                    bArr[i2] = 0;
                }
                BigInteger bigIntegerShiftRight = bigIntegerAdd.shiftRight(1);
                if (b == 1) {
                    bigIntegerSubtract = bigInteger.add(bigIntegerShiftRight);
                } else {
                    bigIntegerSubtract = bigInteger.subtract(bigIntegerShiftRight);
                }
                BigInteger bigIntegerNegate = bigIntegerAdd.shiftRight(1).negate();
                i2++;
                bigIntegerAdd = bigIntegerSubtract;
                bigInteger = bigIntegerNegate;
            } else {
                int i3 = i + 1;
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                return bArr2;
            }
        }
    }

    public static ECPoint.AbstractF2m tau(ECPoint.AbstractF2m abstractF2m) {
        return abstractF2m.tau();
    }

    public static byte getMu(ECCurve.AbstractF2m abstractF2m) {
        if (abstractF2m.isKoblitz()) {
            return abstractF2m.getA().isZero() ? (byte) -1 : (byte) 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static byte getMu(ECFieldElement eCFieldElement) {
        return (byte) (eCFieldElement.isZero() ? -1 : 1);
    }

    public static BigInteger[] getLucas(byte b, int i, boolean z) {
        BigInteger bigInteger;
        BigInteger bigIntegerSubtract;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        if (z) {
            bigInteger = ECConstants.TWO;
            bigIntegerSubtract = BigInteger.valueOf(b);
        } else {
            bigInteger = ECConstants.ZERO;
            bigIntegerSubtract = ECConstants.ONE;
        }
        int i2 = 1;
        while (i2 < i) {
            i2++;
            BigInteger bigInteger2 = bigIntegerSubtract;
            bigIntegerSubtract = (b < 0 ? bigIntegerSubtract.negate() : bigIntegerSubtract).subtract(bigInteger.shiftLeft(1));
            bigInteger = bigInteger2;
        }
        return new BigInteger[]{bigInteger, bigIntegerSubtract};
    }

    public static BigInteger getTw(byte b, int i) {
        if (i == 4) {
            if (b == 1) {
                return BigInteger.valueOf(6L);
            }
            return BigInteger.valueOf(10L);
        }
        BigInteger[] lucas = getLucas(b, i, false);
        BigInteger bit = ECConstants.ZERO.setBit(i);
        return lucas[0].shiftLeft(1).multiply(lucas[1].modInverse(bit)).mod(bit);
    }

    public static BigInteger[] getSi(ECCurve.AbstractF2m abstractF2m) {
        if (!abstractF2m.isKoblitz()) {
            throw new IllegalArgumentException("si is defined for Koblitz curves only");
        }
        return getSi(abstractF2m.getFieldSize(), abstractF2m.getA().toBigInteger().intValue(), abstractF2m.getCofactor());
    }

    public static BigInteger[] getSi(int i, int i2, BigInteger bigInteger) {
        byte mu = getMu(i2);
        int shiftsForCofactor = getShiftsForCofactor(bigInteger);
        BigInteger[] lucas = getLucas(mu, (i + 3) - i2, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        return new BigInteger[]{ECConstants.ONE.add(lucas[1]).shiftRight(shiftsForCofactor), ECConstants.ONE.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    protected static int getShiftsForCofactor(BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(ECConstants.TWO)) {
                return 1;
            }
            if (bigInteger.equals(ECConstants.FOUR)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static ZTauElement partModReduction(ECCurve.AbstractF2m abstractF2m, BigInteger bigInteger, byte b, byte b2, byte b3) {
        BigInteger bigIntegerSubtract;
        BigInteger bigIntegerSubtract2;
        int fieldSize = abstractF2m.getFieldSize();
        BigInteger[] si = abstractF2m.getSi();
        if (b2 == 1) {
            bigIntegerSubtract = si[0].add(si[1]);
        } else {
            bigIntegerSubtract = si[0].subtract(si[1]);
        }
        BigInteger bigInteger2 = bigIntegerSubtract;
        if (abstractF2m.isKoblitz()) {
            bigIntegerSubtract2 = ECConstants.ONE.shiftLeft(fieldSize).add(ECConstants.ONE).subtract(abstractF2m.getOrder().multiply(abstractF2m.getCofactor()));
        } else {
            bigIntegerSubtract2 = getLucas(b2, fieldSize, true)[1];
        }
        BigInteger bigInteger3 = bigIntegerSubtract2;
        ZTauElement zTauElementRound = round(approximateDivisionByN(bigInteger, si[0], bigInteger3, b, fieldSize, b3), approximateDivisionByN(bigInteger, si[1], bigInteger3, b, fieldSize, b3), b2);
        return new ZTauElement(bigInteger.subtract(bigInteger2.multiply(zTauElementRound.u)).subtract(si[1].multiply(zTauElementRound.v).shiftLeft(1)), si[1].multiply(zTauElementRound.u).subtract(si[0].multiply(zTauElementRound.v)));
    }

    public static ECPoint.AbstractF2m multiplyRTnaf(ECPoint.AbstractF2m abstractF2m, BigInteger bigInteger) {
        ECCurve.AbstractF2m abstractF2m2 = (ECCurve.AbstractF2m) abstractF2m.getCurve();
        int iIntValue = abstractF2m2.getA().toBigInteger().intValue();
        return multiplyTnaf(abstractF2m, partModReduction(abstractF2m2, bigInteger, (byte) iIntValue, getMu(iIntValue), (byte) 10));
    }

    public static ECPoint.AbstractF2m multiplyTnaf(ECPoint.AbstractF2m abstractF2m, ZTauElement zTauElement) {
        return multiplyFromTnaf(abstractF2m, (ECPoint.AbstractF2m) abstractF2m.negate(), tauAdicNaf(getMu(((ECCurve.AbstractF2m) abstractF2m.getCurve()).getA()), zTauElement));
    }

    public static ECPoint.AbstractF2m multiplyFromTnaf(ECPoint.AbstractF2m abstractF2m, ECPoint.AbstractF2m abstractF2m2, byte[] bArr) {
        ECPoint.AbstractF2m abstractF2m3 = (ECPoint.AbstractF2m) abstractF2m.getCurve().getInfinity();
        int i = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i++;
            byte b = bArr[length];
            if (b != 0) {
                abstractF2m3 = (ECPoint.AbstractF2m) abstractF2m3.tauPow(i).add(b > 0 ? abstractF2m : abstractF2m2);
                i = 0;
            }
        }
        return i > 0 ? abstractF2m3.tauPow(i) : abstractF2m3;
    }

    public static byte[] tauAdicWNaf(byte b, ZTauElement zTauElement, int i, int i2, ZTauElement[] zTauElementArr) {
        BigInteger bigIntegerSubtract;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int iBitLength = norm(b, zTauElement).bitLength();
        byte[] bArr = new byte[iBitLength > 30 ? iBitLength + 4 + i : i + 34];
        int i3 = (1 << i) - 1;
        int i4 = 32 - i;
        BigInteger bigIntegerSubtract2 = zTauElement.u;
        BigInteger bigIntegerSubtract3 = zTauElement.v;
        int i5 = 0;
        while (true) {
            if (bigIntegerSubtract2.bitLength() <= 62 && bigIntegerSubtract3.bitLength() <= 62) {
                break;
            }
            if (bigIntegerSubtract2.testBit(0)) {
                int iIntValue = bigIntegerSubtract2.intValue() + (bigIntegerSubtract3.intValue() * i2);
                int i6 = iIntValue & i3;
                bArr[i5] = (byte) ((iIntValue << i4) >> i4);
                bigIntegerSubtract2 = bigIntegerSubtract2.subtract(zTauElementArr[i6].u);
                bigIntegerSubtract3 = bigIntegerSubtract3.subtract(zTauElementArr[i6].v);
            }
            i5++;
            BigInteger bigIntegerShiftRight = bigIntegerSubtract2.shiftRight(1);
            if (b == 1) {
                bigIntegerSubtract = bigIntegerSubtract3.add(bigIntegerShiftRight);
            } else {
                bigIntegerSubtract = bigIntegerSubtract3.subtract(bigIntegerShiftRight);
            }
            BigInteger bigIntegerNegate = bigIntegerShiftRight.negate();
            bigIntegerSubtract2 = bigIntegerSubtract;
            bigIntegerSubtract3 = bigIntegerNegate;
        }
        long jLongValueExact = BigIntegers.longValueExact(bigIntegerSubtract2);
        long jLongValueExact2 = BigIntegers.longValueExact(bigIntegerSubtract3);
        while ((jLongValueExact | jLongValueExact2) != 0) {
            if ((1 & jLongValueExact) != 0) {
                int i7 = ((int) jLongValueExact) + (((int) jLongValueExact2) * i2);
                int i8 = i7 & i3;
                bArr[i5] = (byte) ((i7 << i4) >> i4);
                jLongValueExact -= zTauElementArr[i8].u.intValue();
                jLongValueExact2 -= zTauElementArr[i8].v.intValue();
            }
            i5++;
            long j = jLongValueExact >> 1;
            long j2 = b == 1 ? jLongValueExact2 + j : jLongValueExact2 - j;
            jLongValueExact2 = -j;
            jLongValueExact = j2;
        }
        return bArr;
    }

    public static ECPoint.AbstractF2m[] getPreComp(ECPoint.AbstractF2m abstractF2m, byte b) {
        ECPoint.AbstractF2m abstractF2m2 = (ECPoint.AbstractF2m) abstractF2m.negate();
        byte[][] bArr = b == 0 ? alpha0Tnaf : alpha1Tnaf;
        ECPoint.AbstractF2m[] abstractF2mArr = new ECPoint.AbstractF2m[(bArr.length + 1) >>> 1];
        abstractF2mArr[0] = abstractF2m;
        int length = bArr.length;
        for (int i = 3; i < length; i += 2) {
            abstractF2mArr[i >>> 1] = multiplyFromTnaf(abstractF2m, abstractF2m2, bArr[i]);
        }
        abstractF2m.getCurve().normalizeAll(abstractF2mArr);
        return abstractF2mArr;
    }
}
