package com.android.internal.org.bouncycastle.math.ec;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import java.math.BigInteger;
import java.util.Hashtable;

/* loaded from: classes5.dex */
public abstract class ECPoint {
    protected static final ECFieldElement[] EMPTY_ZS = new ECFieldElement[0];
    protected ECCurve curve;
    protected Hashtable preCompTable;
    protected ECFieldElement x;
    protected ECFieldElement y;
    protected ECFieldElement[] zs;

    public abstract ECPoint add(ECPoint eCPoint);

    protected abstract ECPoint detach();

    protected abstract boolean getCompressionYTilde();

    public abstract ECPoint negate();

    protected abstract boolean satisfiesCurveEquation();

    public abstract ECPoint subtract(ECPoint eCPoint);

    public abstract ECPoint twice();

    protected static ECFieldElement[] getInitialZCoords(ECCurve eCCurve) {
        int coordinateSystem = eCCurve == null ? 0 : eCCurve.getCoordinateSystem();
        if (coordinateSystem == 0 || coordinateSystem == 5) {
            return EMPTY_ZS;
        }
        ECFieldElement fromBigInteger = eCCurve.fromBigInteger(ECConstants.ONE);
        if (coordinateSystem != 1 && coordinateSystem != 2) {
            if (coordinateSystem == 3) {
                return new ECFieldElement[]{fromBigInteger, fromBigInteger, fromBigInteger};
            }
            if (coordinateSystem == 4) {
                return new ECFieldElement[]{fromBigInteger, eCCurve.getA()};
            }
            if (coordinateSystem != 6) {
                throw new IllegalArgumentException("unknown coordinate system");
            }
        }
        return new ECFieldElement[]{fromBigInteger};
    }

    protected ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, getInitialZCoords(eCCurve));
    }

    protected ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        this.preCompTable = null;
        this.curve = eCCurve;
        this.x = eCFieldElement;
        this.y = eCFieldElement2;
        this.zs = eCFieldElementArr;
    }

    protected boolean satisfiesOrder() {
        BigInteger order;
        return ECConstants.ONE.equals(this.curve.getCofactor()) || (order = this.curve.getOrder()) == null || ECAlgorithms.referenceMultiply(this, order).isInfinity();
    }

    public final ECPoint getDetachedPoint() {
        return normalize().detach();
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    protected int getCurveCoordinateSystem() {
        ECCurve eCCurve = this.curve;
        if (eCCurve == null) {
            return 0;
        }
        return eCCurve.getCoordinateSystem();
    }

    public ECFieldElement getAffineXCoord() {
        checkNormalized();
        return getXCoord();
    }

    public ECFieldElement getAffineYCoord() {
        checkNormalized();
        return getYCoord();
    }

    public ECFieldElement getXCoord() {
        return this.x;
    }

    public ECFieldElement getYCoord() {
        return this.y;
    }

    public ECFieldElement getZCoord(int i) {
        if (i < 0) {
            return null;
        }
        ECFieldElement[] eCFieldElementArr = this.zs;
        if (i >= eCFieldElementArr.length) {
            return null;
        }
        return eCFieldElementArr[i];
    }

    public ECFieldElement[] getZCoords() {
        ECFieldElement[] eCFieldElementArr = this.zs;
        int length = eCFieldElementArr.length;
        if (length == 0) {
            return EMPTY_ZS;
        }
        ECFieldElement[] eCFieldElementArr2 = new ECFieldElement[length];
        System.arraycopy(eCFieldElementArr, 0, eCFieldElementArr2, 0, length);
        return eCFieldElementArr2;
    }

    public final ECFieldElement getRawXCoord() {
        return this.x;
    }

    public final ECFieldElement getRawYCoord() {
        return this.y;
    }

    protected final ECFieldElement[] getRawZCoords() {
        return this.zs;
    }

    protected void checkNormalized() {
        if (!isNormalized()) {
            throw new IllegalStateException("point not in normal form");
        }
    }

    public boolean isNormalized() {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        return curveCoordinateSystem == 0 || curveCoordinateSystem == 5 || isInfinity() || this.zs[0].isOne();
    }

    public ECPoint normalize() {
        int curveCoordinateSystem;
        if (!isInfinity() && (curveCoordinateSystem = getCurveCoordinateSystem()) != 0 && curveCoordinateSystem != 5) {
            ECFieldElement zCoord = getZCoord(0);
            if (!zCoord.isOne()) {
                if (this.curve == null) {
                    throw new IllegalStateException("Detached points must be in affine coordinates");
                }
                ECFieldElement randomFieldElementMult = this.curve.randomFieldElementMult(CryptoServicesRegistrar.getSecureRandom());
                return normalize(zCoord.multiply(randomFieldElementMult).invert().multiply(randomFieldElementMult));
            }
        }
        return this;
    }

    ECPoint normalize(ECFieldElement eCFieldElement) {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        if (curveCoordinateSystem != 1) {
            if (curveCoordinateSystem == 2 || curveCoordinateSystem == 3 || curveCoordinateSystem == 4) {
                ECFieldElement square = eCFieldElement.square();
                return createScaledPoint(square, square.multiply(eCFieldElement));
            }
            if (curveCoordinateSystem != 6) {
                throw new IllegalStateException("not a projective coordinate system");
            }
        }
        return createScaledPoint(eCFieldElement, eCFieldElement);
    }

    protected ECPoint createScaledPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord().multiply(eCFieldElement2));
    }

    public boolean isInfinity() {
        if (this.x == null || this.y == null) {
            return true;
        }
        ECFieldElement[] eCFieldElementArr = this.zs;
        return eCFieldElementArr.length > 0 && eCFieldElementArr[0].isZero();
    }

    public boolean isValid() {
        return implIsValid(false, true);
    }

    boolean isValidPartial() {
        return implIsValid(false, false);
    }

    boolean implIsValid(final boolean z, final boolean z2) {
        if (isInfinity()) {
            return true;
        }
        return !((ValidityPrecompInfo) getCurve().precompute(this, "bc_validity", new PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.ECPoint.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                ValidityPrecompInfo validityPrecompInfo = preCompInfo instanceof ValidityPrecompInfo ? (ValidityPrecompInfo) preCompInfo : null;
                if (validityPrecompInfo == null) {
                    validityPrecompInfo = new ValidityPrecompInfo();
                }
                if (!validityPrecompInfo.hasFailed()) {
                    if (!validityPrecompInfo.hasCurveEquationPassed()) {
                        if (!z && !ECPoint.this.satisfiesCurveEquation()) {
                            validityPrecompInfo.reportFailed();
                            return validityPrecompInfo;
                        }
                        validityPrecompInfo.reportCurveEquationPassed();
                    }
                    if (z2 && !validityPrecompInfo.hasOrderPassed()) {
                        if (!ECPoint.this.satisfiesOrder()) {
                            validityPrecompInfo.reportFailed();
                            return validityPrecompInfo;
                        }
                        validityPrecompInfo.reportOrderPassed();
                    }
                }
                return validityPrecompInfo;
            }
        })).hasFailed();
    }

    public ECPoint scaleX(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord(), getRawZCoords());
    }

    public ECPoint scaleXNegateY(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord().negate(), getRawZCoords());
    }

    public ECPoint scaleY(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord(), getRawYCoord().multiply(eCFieldElement), getRawZCoords());
    }

    public ECPoint scaleYNegateX(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().negate(), getRawYCoord().multiply(eCFieldElement), getRawZCoords());
    }

    public boolean equals(ECPoint eCPoint) {
        if (eCPoint == null) {
            return false;
        }
        ECCurve curve = getCurve();
        ECCurve curve2 = eCPoint.getCurve();
        boolean z = curve == null;
        boolean z2 = curve2 == null;
        boolean isInfinity = isInfinity();
        boolean isInfinity2 = eCPoint.isInfinity();
        if (isInfinity || isInfinity2) {
            return isInfinity && isInfinity2 && (z || z2 || curve.equals(curve2));
        }
        if (!z || !z2) {
            if (z) {
                eCPoint = eCPoint.normalize();
            } else if (z2) {
                this = normalize();
            } else {
                if (!curve.equals(curve2)) {
                    return false;
                }
                ECPoint[] eCPointArr = {this, curve.importPoint(eCPoint)};
                curve.normalizeAll(eCPointArr);
                ECPoint eCPoint2 = eCPointArr[0];
                eCPoint = eCPointArr[1];
                this = eCPoint2;
            }
        }
        return this.getXCoord().equals(eCPoint.getXCoord()) && this.getYCoord().equals(eCPoint.getYCoord());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ECPoint) {
            return equals((ECPoint) obj);
        }
        return false;
    }

    public int hashCode() {
        ECCurve curve = getCurve();
        int i = curve == null ? 0 : ~curve.hashCode();
        if (isInfinity()) {
            return i;
        }
        ECPoint normalize = normalize();
        return (normalize.getYCoord().hashCode() * 257) ^ (i ^ (normalize.getXCoord().hashCode() * 17));
    }

    public String toString() {
        if (isInfinity()) {
            return "INF";
        }
        StringBuffer stringBuffer = new StringBuffer(NavigationBarInflaterView.KEY_CODE_START);
        stringBuffer.append(getRawXCoord());
        stringBuffer.append(',');
        stringBuffer.append(getRawYCoord());
        for (int i = 0; i < this.zs.length; i++) {
            stringBuffer.append(',');
            stringBuffer.append(this.zs[i]);
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public byte[] getEncoded(boolean z) {
        if (isInfinity()) {
            return new byte[1];
        }
        ECPoint normalize = normalize();
        byte[] encoded = normalize.getXCoord().getEncoded();
        if (z) {
            byte[] bArr = new byte[encoded.length + 1];
            bArr[0] = (byte) (normalize.getCompressionYTilde() ? 3 : 2);
            System.arraycopy(encoded, 0, bArr, 1, encoded.length);
            return bArr;
        }
        byte[] encoded2 = normalize.getYCoord().getEncoded();
        byte[] bArr2 = new byte[encoded.length + encoded2.length + 1];
        bArr2[0] = 4;
        System.arraycopy(encoded, 0, bArr2, 1, encoded.length);
        System.arraycopy(encoded2, 0, bArr2, encoded.length + 1, encoded2.length);
        return bArr2;
    }

    public ECPoint timesPow2(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("'e' cannot be negative");
        }
        while (true) {
            i--;
            if (i < 0) {
                return this;
            }
            this = this.twice();
        }
    }

    public ECPoint twicePlus(ECPoint eCPoint) {
        return twice().add(eCPoint);
    }

    public ECPoint threeTimes() {
        return twicePlus(this);
    }

    public ECPoint multiply(BigInteger bigInteger) {
        return getCurve().getMultiplier().multiply(this, bigInteger);
    }

    public static abstract class AbstractFp extends ECPoint {
        protected AbstractFp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        protected AbstractFp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean getCompressionYTilde() {
            return getAffineYCoord().testBitZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesCurveEquation() {
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement eCFieldElement2 = this.y;
            ECFieldElement a = this.curve.getA();
            ECFieldElement b = this.curve.getB();
            ECFieldElement square = eCFieldElement2.square();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 0) {
                if (curveCoordinateSystem == 1) {
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        ECFieldElement square2 = eCFieldElement3.square();
                        ECFieldElement multiply = eCFieldElement3.multiply(square2);
                        square = square.multiply(eCFieldElement3);
                        a = a.multiply(square2);
                        b = b.multiply(multiply);
                    }
                } else if (curveCoordinateSystem == 2 || curveCoordinateSystem == 3 || curveCoordinateSystem == 4) {
                    ECFieldElement eCFieldElement4 = this.zs[0];
                    if (!eCFieldElement4.isOne()) {
                        ECFieldElement square3 = eCFieldElement4.square();
                        ECFieldElement square4 = square3.square();
                        ECFieldElement multiply2 = square3.multiply(square4);
                        a = a.multiply(square4);
                        b = b.multiply(multiply2);
                    }
                } else {
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            return square.equals(eCFieldElement.square().add(a).multiply(eCFieldElement).add(b));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint subtract(ECPoint eCPoint) {
            return eCPoint.isInfinity() ? this : add(eCPoint.negate());
        }
    }

    public static class Fp extends AbstractFp {
        Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected ECPoint detach() {
            return new Fp(null, getAffineXCoord(), getAffineYCoord());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECFieldElement getZCoord(int i) {
            if (i == 1 && 4 == getCurveCoordinateSystem()) {
                return getJacobianModifiedW();
            }
            return super.getZCoord(i);
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x0134  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0140  */
        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.android.internal.org.bouncycastle.math.ec.ECPoint add(com.android.internal.org.bouncycastle.math.ec.ECPoint r19) {
            /*
                Method dump skipped, instructions count: 542
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp.add(com.android.internal.org.bouncycastle.math.ec.ECPoint):com.android.internal.org.bouncycastle.math.ec.ECPoint");
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            ECFieldElement eCFieldElement;
            ECFieldElement four;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement2 = this.y;
            if (eCFieldElement2.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement3 = this.x;
            if (coordinateSystem == 0) {
                ECFieldElement divide = three(eCFieldElement3.square()).add(getCurve().getA()).divide(two(eCFieldElement2));
                ECFieldElement subtract = divide.square().subtract(two(eCFieldElement3));
                return new Fp(curve, subtract, divide.multiply(eCFieldElement3.subtract(subtract)).subtract(eCFieldElement2));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement4 = this.zs[0];
                boolean isOne = eCFieldElement4.isOne();
                ECFieldElement a = curve.getA();
                if (!a.isZero() && !isOne) {
                    a = a.multiply(eCFieldElement4.square());
                }
                ECFieldElement add = a.add(three(eCFieldElement3.square()));
                ECFieldElement multiply = isOne ? eCFieldElement2 : eCFieldElement2.multiply(eCFieldElement4);
                ECFieldElement square = isOne ? eCFieldElement2.square() : multiply.multiply(eCFieldElement2);
                ECFieldElement four2 = four(eCFieldElement3.multiply(square));
                ECFieldElement subtract2 = add.square().subtract(two(four2));
                ECFieldElement two = two(multiply);
                ECFieldElement multiply2 = subtract2.multiply(two);
                ECFieldElement two2 = two(square);
                return new Fp(curve, multiply2, four2.subtract(subtract2).multiply(add).subtract(two(two2.square())), new ECFieldElement[]{two(isOne ? two(two2) : two.square()).multiply(multiply)});
            }
            if (coordinateSystem != 2) {
                if (coordinateSystem == 4) {
                    return twiceJacobianModified(true);
                }
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECFieldElement eCFieldElement5 = this.zs[0];
            boolean isOne2 = eCFieldElement5.isOne();
            ECFieldElement square2 = eCFieldElement2.square();
            ECFieldElement square3 = square2.square();
            ECFieldElement a2 = curve.getA();
            ECFieldElement negate = a2.negate();
            if (negate.toBigInteger().equals(BigInteger.valueOf(3L))) {
                ECFieldElement square4 = isOne2 ? eCFieldElement5 : eCFieldElement5.square();
                eCFieldElement = three(eCFieldElement3.add(square4).multiply(eCFieldElement3.subtract(square4)));
                four = four(square2.multiply(eCFieldElement3));
            } else {
                ECFieldElement three = three(eCFieldElement3.square());
                if (isOne2) {
                    eCFieldElement = three.add(a2);
                } else if (a2.isZero()) {
                    eCFieldElement = three;
                } else {
                    ECFieldElement square5 = eCFieldElement5.square().square();
                    if (negate.bitLength() < a2.bitLength()) {
                        eCFieldElement = three.subtract(square5.multiply(negate));
                    } else {
                        eCFieldElement = three.add(square5.multiply(a2));
                    }
                }
                four = four(eCFieldElement3.multiply(square2));
            }
            ECFieldElement subtract3 = eCFieldElement.square().subtract(two(four));
            ECFieldElement subtract4 = four.subtract(subtract3).multiply(eCFieldElement).subtract(eight(square3));
            ECFieldElement two3 = two(eCFieldElement2);
            if (!isOne2) {
                two3 = two3.multiply(eCFieldElement5);
            }
            return new Fp(curve, subtract3, subtract4, new ECFieldElement[]{two3});
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint twicePlus(ECPoint eCPoint) {
            if (this == eCPoint) {
                return threeTimes();
            }
            if (!isInfinity()) {
                if (eCPoint.isInfinity()) {
                    return twice();
                }
                ECFieldElement eCFieldElement = this.y;
                if (!eCFieldElement.isZero()) {
                    ECCurve curve = getCurve();
                    int coordinateSystem = curve.getCoordinateSystem();
                    if (coordinateSystem != 0) {
                        if (coordinateSystem == 4) {
                            return twiceJacobianModified(false).add(eCPoint);
                        }
                        return twice().add(eCPoint);
                    }
                    ECFieldElement eCFieldElement2 = this.x;
                    ECFieldElement eCFieldElement3 = eCPoint.x;
                    ECFieldElement eCFieldElement4 = eCPoint.y;
                    ECFieldElement subtract = eCFieldElement3.subtract(eCFieldElement2);
                    ECFieldElement subtract2 = eCFieldElement4.subtract(eCFieldElement);
                    if (subtract.isZero()) {
                        return subtract2.isZero() ? threeTimes() : this;
                    }
                    ECFieldElement square = subtract.square();
                    ECFieldElement subtract3 = square.multiply(two(eCFieldElement2).add(eCFieldElement3)).subtract(subtract2.square());
                    if (subtract3.isZero()) {
                        return curve.getInfinity();
                    }
                    ECFieldElement invert = subtract3.multiply(subtract).invert();
                    ECFieldElement multiply = subtract3.multiply(invert).multiply(subtract2);
                    ECFieldElement subtract4 = two(eCFieldElement).multiply(square).multiply(subtract).multiply(invert).subtract(multiply);
                    ECFieldElement add = subtract4.subtract(multiply).multiply(multiply.add(subtract4)).add(eCFieldElement3);
                    return new Fp(curve, add, eCFieldElement2.subtract(add).multiply(subtract4).subtract(eCFieldElement));
                }
            }
            return eCPoint;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint threeTimes() {
            if (!isInfinity()) {
                ECFieldElement eCFieldElement = this.y;
                if (!eCFieldElement.isZero()) {
                    ECCurve curve = getCurve();
                    int coordinateSystem = curve.getCoordinateSystem();
                    if (coordinateSystem != 0) {
                        if (coordinateSystem == 4) {
                            return twiceJacobianModified(false).add(this);
                        }
                        return twice().add(this);
                    }
                    ECFieldElement eCFieldElement2 = this.x;
                    ECFieldElement two = two(eCFieldElement);
                    ECFieldElement square = two.square();
                    ECFieldElement add = three(eCFieldElement2.square()).add(getCurve().getA());
                    ECFieldElement subtract = three(eCFieldElement2).multiply(square).subtract(add.square());
                    if (subtract.isZero()) {
                        return getCurve().getInfinity();
                    }
                    ECFieldElement invert = subtract.multiply(two).invert();
                    ECFieldElement multiply = subtract.multiply(invert).multiply(add);
                    ECFieldElement subtract2 = square.square().multiply(invert).subtract(multiply);
                    ECFieldElement add2 = subtract2.subtract(multiply).multiply(multiply.add(subtract2)).add(eCFieldElement2);
                    return new Fp(curve, add2, eCFieldElement2.subtract(add2).multiply(subtract2).subtract(eCFieldElement));
                }
            }
            return this;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint timesPow2(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("'e' cannot be negative");
            }
            if (i == 0 || isInfinity()) {
                return this;
            }
            if (i == 1) {
                return twice();
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement a = curve.getA();
            ECFieldElement eCFieldElement2 = this.x;
            boolean z = false;
            ECFieldElement fromBigInteger = this.zs.length < 1 ? curve.fromBigInteger(ECConstants.ONE) : this.zs[0];
            if (!fromBigInteger.isOne() && coordinateSystem != 0) {
                if (coordinateSystem == 1) {
                    ECFieldElement square = fromBigInteger.square();
                    eCFieldElement2 = eCFieldElement2.multiply(fromBigInteger);
                    eCFieldElement = eCFieldElement.multiply(square);
                    a = calculateJacobianModifiedW(fromBigInteger, square);
                } else if (coordinateSystem == 2) {
                    a = calculateJacobianModifiedW(fromBigInteger, null);
                } else if (coordinateSystem == 4) {
                    a = getJacobianModifiedW();
                } else {
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            int i2 = 0;
            while (i2 < i) {
                if (eCFieldElement.isZero()) {
                    return curve.getInfinity();
                }
                ECFieldElement three = three(eCFieldElement2.square());
                ECFieldElement two = two(eCFieldElement);
                ECFieldElement multiply = two.multiply(eCFieldElement);
                ECFieldElement two2 = two(eCFieldElement2.multiply(multiply));
                ECFieldElement two3 = two(multiply.square());
                if (!a.isZero()) {
                    three = three.add(a);
                    a = two(two3.multiply(a));
                }
                boolean z2 = z;
                ECFieldElement subtract = three.square().subtract(two(two2));
                eCFieldElement = three.multiply(two2.subtract(subtract)).subtract(two3);
                fromBigInteger = fromBigInteger.isOne() ? two : two.multiply(fromBigInteger);
                i2++;
                eCFieldElement2 = subtract;
                z = z2;
            }
            boolean z3 = z;
            if (coordinateSystem == 0) {
                ECFieldElement invert = fromBigInteger.invert();
                ECFieldElement square2 = invert.square();
                return new Fp(curve, eCFieldElement2.multiply(square2), eCFieldElement.multiply(square2.multiply(invert)));
            }
            if (coordinateSystem == 1) {
                ECFieldElement multiply2 = eCFieldElement2.multiply(fromBigInteger);
                ECFieldElement[] eCFieldElementArr = new ECFieldElement[1];
                eCFieldElementArr[z3 ? 1 : 0] = fromBigInteger.multiply(fromBigInteger.square());
                return new Fp(curve, multiply2, eCFieldElement, eCFieldElementArr);
            }
            if (coordinateSystem == 2) {
                ECFieldElement[] eCFieldElementArr2 = new ECFieldElement[1];
                eCFieldElementArr2[z3 ? 1 : 0] = fromBigInteger;
                return new Fp(curve, eCFieldElement2, eCFieldElement, eCFieldElementArr2);
            }
            if (coordinateSystem == 4) {
                ECFieldElement[] eCFieldElementArr3 = new ECFieldElement[2];
                eCFieldElementArr3[z3 ? 1 : 0] = fromBigInteger;
                eCFieldElementArr3[1] = a;
                return new Fp(curve, eCFieldElement2, eCFieldElement, eCFieldElementArr3);
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        protected ECFieldElement two(ECFieldElement eCFieldElement) {
            return eCFieldElement.add(eCFieldElement);
        }

        protected ECFieldElement three(ECFieldElement eCFieldElement) {
            return two(eCFieldElement).add(eCFieldElement);
        }

        protected ECFieldElement four(ECFieldElement eCFieldElement) {
            return two(two(eCFieldElement));
        }

        protected ECFieldElement eight(ECFieldElement eCFieldElement) {
            return four(two(eCFieldElement));
        }

        protected ECFieldElement doubleProductFromSquares(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3, ECFieldElement eCFieldElement4) {
            return eCFieldElement.add(eCFieldElement2).square().subtract(eCFieldElement3).subtract(eCFieldElement4);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint negate() {
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            if (curve.getCoordinateSystem() != 0) {
                return new Fp(curve, this.x, this.y.negate(), this.zs);
            }
            return new Fp(curve, this.x, this.y.negate());
        }

        protected ECFieldElement calculateJacobianModifiedW(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            ECFieldElement a = getCurve().getA();
            if (a.isZero() || eCFieldElement.isOne()) {
                return a;
            }
            if (eCFieldElement2 == null) {
                eCFieldElement2 = eCFieldElement.square();
            }
            ECFieldElement square = eCFieldElement2.square();
            ECFieldElement negate = a.negate();
            if (negate.bitLength() < a.bitLength()) {
                return square.multiply(negate).negate();
            }
            return square.multiply(a);
        }

        protected ECFieldElement getJacobianModifiedW() {
            ECFieldElement eCFieldElement = this.zs[1];
            if (eCFieldElement != null) {
                return eCFieldElement;
            }
            ECFieldElement[] eCFieldElementArr = this.zs;
            ECFieldElement calculateJacobianModifiedW = calculateJacobianModifiedW(this.zs[0], null);
            eCFieldElementArr[1] = calculateJacobianModifiedW;
            return calculateJacobianModifiedW;
        }

        protected Fp twiceJacobianModified(boolean z) {
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement eCFieldElement2 = this.y;
            ECFieldElement eCFieldElement3 = this.zs[0];
            ECFieldElement jacobianModifiedW = getJacobianModifiedW();
            ECFieldElement add = three(eCFieldElement.square()).add(jacobianModifiedW);
            ECFieldElement two = two(eCFieldElement2);
            ECFieldElement multiply = two.multiply(eCFieldElement2);
            ECFieldElement two2 = two(eCFieldElement.multiply(multiply));
            ECFieldElement subtract = add.square().subtract(two(two2));
            ECFieldElement two3 = two(multiply.square());
            ECFieldElement subtract2 = add.multiply(two2.subtract(subtract)).subtract(two3);
            ECFieldElement two4 = z ? two(two3.multiply(jacobianModifiedW)) : null;
            if (!eCFieldElement3.isOne()) {
                two = two.multiply(eCFieldElement3);
            }
            return new Fp(getCurve(), subtract, subtract2, new ECFieldElement[]{two, two4});
        }
    }

    public static abstract class AbstractF2m extends ECPoint {
        protected AbstractF2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        protected AbstractF2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesCurveEquation() {
            ECFieldElement multiplyPlusProduct;
            ECFieldElement squarePlusProduct;
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement a = curve.getA();
            ECFieldElement b = curve.getB();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 6) {
                ECFieldElement eCFieldElement2 = this.zs[0];
                boolean isOne = eCFieldElement2.isOne();
                if (eCFieldElement.isZero()) {
                    ECFieldElement square = this.y.square();
                    if (!isOne) {
                        b = b.multiply(eCFieldElement2.square());
                    }
                    return square.equals(b);
                }
                ECFieldElement eCFieldElement3 = this.y;
                ECFieldElement square2 = eCFieldElement.square();
                if (isOne) {
                    multiplyPlusProduct = eCFieldElement3.square().add(eCFieldElement3).add(a);
                    squarePlusProduct = square2.square().add(b);
                } else {
                    ECFieldElement square3 = eCFieldElement2.square();
                    ECFieldElement square4 = square3.square();
                    multiplyPlusProduct = eCFieldElement3.add(eCFieldElement2).multiplyPlusProduct(eCFieldElement3, a, square3);
                    squarePlusProduct = square2.squarePlusProduct(b, square4);
                }
                return multiplyPlusProduct.multiply(square2).equals(squarePlusProduct);
            }
            ECFieldElement eCFieldElement4 = this.y;
            ECFieldElement multiply = eCFieldElement4.add(eCFieldElement).multiply(eCFieldElement4);
            if (coordinateSystem != 0) {
                if (coordinateSystem == 1) {
                    ECFieldElement eCFieldElement5 = this.zs[0];
                    if (!eCFieldElement5.isOne()) {
                        ECFieldElement multiply2 = eCFieldElement5.multiply(eCFieldElement5.square());
                        multiply = multiply.multiply(eCFieldElement5);
                        a = a.multiply(eCFieldElement5);
                        b = b.multiply(multiply2);
                    }
                } else {
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            return multiply.equals(eCFieldElement.add(a).multiply(eCFieldElement.square()).add(b));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesOrder() {
            BigInteger cofactor = this.curve.getCofactor();
            if (ECConstants.TWO.equals(cofactor)) {
                return ((ECFieldElement.AbstractF2m) normalize().getAffineXCoord()).trace() != 0;
            }
            if (ECConstants.FOUR.equals(cofactor)) {
                ECPoint normalize = normalize();
                ECFieldElement affineXCoord = normalize.getAffineXCoord();
                ECFieldElement solveQuadraticEquation = ((ECCurve.AbstractF2m) this.curve).solveQuadraticEquation(affineXCoord.add(this.curve.getA()));
                if (solveQuadraticEquation == null) {
                    return false;
                }
                return ((ECFieldElement.AbstractF2m) affineXCoord.multiply(solveQuadraticEquation).add(normalize.getAffineYCoord())).trace() == 0;
            }
            return super.satisfiesOrder();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleX(ECFieldElement eCFieldElement) {
            if (isInfinity()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 5) {
                ECFieldElement rawXCoord = getRawXCoord();
                return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).divide(eCFieldElement).add(rawXCoord.multiply(eCFieldElement)), getRawZCoords());
            }
            if (curveCoordinateSystem == 6) {
                ECFieldElement rawXCoord2 = getRawXCoord();
                ECFieldElement rawYCoord = getRawYCoord();
                ECFieldElement eCFieldElement2 = getRawZCoords()[0];
                ECFieldElement multiply = rawXCoord2.multiply(eCFieldElement.square());
                return getCurve().createRawPoint(multiply, rawYCoord.add(rawXCoord2).add(multiply), new ECFieldElement[]{eCFieldElement2.multiply(eCFieldElement)});
            }
            return super.scaleX(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleXNegateY(ECFieldElement eCFieldElement) {
            return scaleX(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleY(ECFieldElement eCFieldElement) {
            if (isInfinity()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 5 || curveCoordinateSystem == 6) {
                ECFieldElement rawXCoord = getRawXCoord();
                return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).multiply(eCFieldElement).add(rawXCoord), getRawZCoords());
            }
            return super.scaleY(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleYNegateX(ECFieldElement eCFieldElement) {
            return scaleY(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint subtract(ECPoint eCPoint) {
            return eCPoint.isInfinity() ? this : add(eCPoint.negate());
        }

        public AbstractF2m tau() {
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement = this.x;
            if (coordinateSystem != 0) {
                if (coordinateSystem != 1) {
                    if (coordinateSystem != 5) {
                        if (coordinateSystem != 6) {
                            throw new IllegalStateException("unsupported coordinate system");
                        }
                    }
                }
                return (AbstractF2m) curve.createRawPoint(eCFieldElement.square(), this.y.square(), new ECFieldElement[]{this.zs[0].square()});
            }
            return (AbstractF2m) curve.createRawPoint(eCFieldElement.square(), this.y.square());
        }

        public AbstractF2m tauPow(int i) {
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement = this.x;
            if (coordinateSystem != 0) {
                if (coordinateSystem != 1) {
                    if (coordinateSystem != 5) {
                        if (coordinateSystem != 6) {
                            throw new IllegalStateException("unsupported coordinate system");
                        }
                    }
                }
                return (AbstractF2m) curve.createRawPoint(eCFieldElement.squarePow(i), this.y.squarePow(i), new ECFieldElement[]{this.zs[0].squarePow(i)});
            }
            return (AbstractF2m) curve.createRawPoint(eCFieldElement.squarePow(i), this.y.squarePow(i));
        }
    }

    public static class F2m extends AbstractF2m {
        F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected ECPoint detach() {
            return new F2m(null, getAffineXCoord(), getAffineYCoord());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECFieldElement getYCoord() {
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 5 || curveCoordinateSystem == 6) {
                ECFieldElement eCFieldElement = this.x;
                ECFieldElement eCFieldElement2 = this.y;
                if (isInfinity() || eCFieldElement.isZero()) {
                    return eCFieldElement2;
                }
                ECFieldElement multiply = eCFieldElement2.add(eCFieldElement).multiply(eCFieldElement);
                if (6 == curveCoordinateSystem) {
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        return multiply.divide(eCFieldElement3);
                    }
                }
                return multiply;
            }
            return this.y;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean getCompressionYTilde() {
            ECFieldElement rawXCoord = getRawXCoord();
            if (rawXCoord.isZero()) {
                return false;
            }
            ECFieldElement rawYCoord = getRawYCoord();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 5 || curveCoordinateSystem == 6) {
                return rawYCoord.testBitZero() != rawXCoord.testBitZero();
            }
            return rawYCoord.divide(rawXCoord).testBitZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint add(ECPoint eCPoint) {
            ECFieldElement eCFieldElement;
            ECFieldElement eCFieldElement2;
            ECFieldElement eCFieldElement3;
            ECFieldElement multiply;
            ECFieldElement multiply2;
            ECFieldElement squarePlusProduct;
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement4 = this.x;
            ECFieldElement eCFieldElement5 = eCPoint.x;
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElement6 = this.y;
                ECFieldElement eCFieldElement7 = eCPoint.y;
                ECFieldElement add = eCFieldElement4.add(eCFieldElement5);
                ECFieldElement add2 = eCFieldElement6.add(eCFieldElement7);
                if (add.isZero()) {
                    if (add2.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                ECFieldElement divide = add2.divide(add);
                ECFieldElement add3 = divide.square().add(divide).add(add).add(curve.getA());
                return new F2m(curve, add3, divide.multiply(eCFieldElement4.add(add3)).add(add3).add(eCFieldElement6));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement8 = this.y;
                ECFieldElement eCFieldElement9 = this.zs[0];
                ECFieldElement eCFieldElement10 = eCPoint.y;
                ECFieldElement eCFieldElement11 = eCPoint.zs[0];
                boolean isOne = eCFieldElement11.isOne();
                ECFieldElement add4 = eCFieldElement9.multiply(eCFieldElement10).add(isOne ? eCFieldElement8 : eCFieldElement8.multiply(eCFieldElement11));
                ECFieldElement add5 = eCFieldElement9.multiply(eCFieldElement5).add(isOne ? eCFieldElement4 : eCFieldElement4.multiply(eCFieldElement11));
                if (add5.isZero()) {
                    if (add4.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                ECFieldElement square = add5.square();
                ECFieldElement multiply3 = square.multiply(add5);
                if (!isOne) {
                    eCFieldElement9 = eCFieldElement9.multiply(eCFieldElement11);
                }
                ECFieldElement add6 = add4.add(add5);
                ECFieldElement add7 = add6.multiplyPlusProduct(add4, square, curve.getA()).multiply(eCFieldElement9).add(multiply3);
                ECFieldElement multiply4 = add5.multiply(add7);
                if (!isOne) {
                    square = square.multiply(eCFieldElement11);
                }
                return new F2m(curve, multiply4, add4.multiplyPlusProduct(eCFieldElement4, add5, eCFieldElement8).multiplyPlusProduct(square, add6, add7), new ECFieldElement[]{multiply3.multiply(eCFieldElement9)});
            }
            if (coordinateSystem == 6) {
                if (eCFieldElement4.isZero()) {
                    if (eCFieldElement5.isZero()) {
                        return curve.getInfinity();
                    }
                    return eCPoint.add(this);
                }
                ECFieldElement eCFieldElement12 = this.y;
                ECFieldElement eCFieldElement13 = this.zs[0];
                ECFieldElement eCFieldElement14 = eCPoint.y;
                ECFieldElement eCFieldElement15 = eCPoint.zs[0];
                boolean isOne2 = eCFieldElement13.isOne();
                if (isOne2) {
                    eCFieldElement = eCFieldElement5;
                    eCFieldElement2 = eCFieldElement14;
                } else {
                    eCFieldElement = eCFieldElement5.multiply(eCFieldElement13);
                    eCFieldElement2 = eCFieldElement14.multiply(eCFieldElement13);
                }
                boolean isOne3 = eCFieldElement15.isOne();
                if (isOne3) {
                    eCFieldElement3 = eCFieldElement12;
                } else {
                    eCFieldElement4 = eCFieldElement4.multiply(eCFieldElement15);
                    eCFieldElement3 = eCFieldElement12.multiply(eCFieldElement15);
                }
                ECFieldElement add8 = eCFieldElement3.add(eCFieldElement2);
                ECFieldElement add9 = eCFieldElement4.add(eCFieldElement);
                if (add9.isZero()) {
                    if (add8.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                if (eCFieldElement5.isZero()) {
                    ECPoint normalize = normalize();
                    ECFieldElement xCoord = normalize.getXCoord();
                    ECFieldElement yCoord = normalize.getYCoord();
                    ECFieldElement divide2 = yCoord.add(eCFieldElement14).divide(xCoord);
                    multiply = divide2.square().add(divide2).add(xCoord).add(curve.getA());
                    if (multiply.isZero()) {
                        return new F2m(curve, multiply, curve.getB().sqrt());
                    }
                    squarePlusProduct = divide2.multiply(xCoord.add(multiply)).add(multiply).add(yCoord).divide(multiply).add(multiply);
                    multiply2 = curve.fromBigInteger(ECConstants.ONE);
                } else {
                    ECFieldElement square2 = add9.square();
                    ECFieldElement multiply5 = add8.multiply(eCFieldElement4);
                    ECFieldElement multiply6 = add8.multiply(eCFieldElement);
                    multiply = multiply5.multiply(multiply6);
                    if (multiply.isZero()) {
                        return new F2m(curve, multiply, curve.getB().sqrt());
                    }
                    ECFieldElement multiply7 = add8.multiply(square2);
                    multiply2 = !isOne3 ? multiply7.multiply(eCFieldElement15) : multiply7;
                    squarePlusProduct = multiply6.add(square2).squarePlusProduct(multiply2, eCFieldElement12.add(eCFieldElement13));
                    if (!isOne2) {
                        multiply2 = multiply2.multiply(eCFieldElement13);
                    }
                }
                return new F2m(curve, multiply, squarePlusProduct, new ECFieldElement[]{multiply2});
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            ECFieldElement add;
            ECFieldElement squarePlusProduct;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 0) {
                ECFieldElement add2 = this.y.divide(eCFieldElement).add(eCFieldElement);
                ECFieldElement add3 = add2.square().add(add2).add(curve.getA());
                return new F2m(curve, add3, eCFieldElement.squarePlusProduct(add3, add2.addOne()));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement2 = this.y;
                ECFieldElement eCFieldElement3 = this.zs[0];
                boolean isOne = eCFieldElement3.isOne();
                ECFieldElement multiply = isOne ? eCFieldElement : eCFieldElement.multiply(eCFieldElement3);
                if (!isOne) {
                    eCFieldElement2 = eCFieldElement2.multiply(eCFieldElement3);
                }
                ECFieldElement square = eCFieldElement.square();
                ECFieldElement add4 = square.add(eCFieldElement2);
                ECFieldElement square2 = multiply.square();
                ECFieldElement add5 = add4.add(multiply);
                ECFieldElement multiplyPlusProduct = add5.multiplyPlusProduct(add4, square2, curve.getA());
                return new F2m(curve, multiply.multiply(multiplyPlusProduct), square.square().multiplyPlusProduct(multiply, multiplyPlusProduct, add5), new ECFieldElement[]{multiply.multiply(square2)});
            }
            if (coordinateSystem == 6) {
                ECFieldElement eCFieldElement4 = this.y;
                ECFieldElement eCFieldElement5 = this.zs[0];
                boolean isOne2 = eCFieldElement5.isOne();
                ECFieldElement multiply2 = isOne2 ? eCFieldElement4 : eCFieldElement4.multiply(eCFieldElement5);
                ECFieldElement square3 = isOne2 ? eCFieldElement5 : eCFieldElement5.square();
                ECFieldElement a = curve.getA();
                ECFieldElement multiply3 = isOne2 ? a : a.multiply(square3);
                ECFieldElement add6 = eCFieldElement4.square().add(multiply2).add(multiply3);
                if (add6.isZero()) {
                    return new F2m(curve, add6, curve.getB().sqrt());
                }
                ECFieldElement square4 = add6.square();
                ECFieldElement multiply4 = isOne2 ? add6 : add6.multiply(square3);
                ECFieldElement b = curve.getB();
                if (b.bitLength() < (curve.getFieldSize() >> 1)) {
                    ECFieldElement square5 = eCFieldElement4.add(eCFieldElement).square();
                    if (b.isOne()) {
                        squarePlusProduct = multiply3.add(square3).square();
                    } else {
                        squarePlusProduct = multiply3.squarePlusProduct(b, square3.square());
                    }
                    add = square5.add(add6).add(square3).multiply(square5).add(squarePlusProduct).add(square4);
                    if (a.isZero()) {
                        add = add.add(multiply4);
                    } else if (!a.isOne()) {
                        add = add.add(a.addOne().multiply(multiply4));
                    }
                } else {
                    if (!isOne2) {
                        eCFieldElement = eCFieldElement.multiply(eCFieldElement5);
                    }
                    add = eCFieldElement.squarePlusProduct(add6, multiply2).add(square4).add(multiply4);
                }
                return new F2m(curve, square4, add, new ECFieldElement[]{multiply4});
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint twicePlus(ECPoint eCPoint) {
            if (!isInfinity()) {
                if (eCPoint.isInfinity()) {
                    return twice();
                }
                ECCurve curve = getCurve();
                ECFieldElement eCFieldElement = this.x;
                if (!eCFieldElement.isZero()) {
                    if (curve.getCoordinateSystem() == 6) {
                        ECFieldElement eCFieldElement2 = eCPoint.x;
                        ECFieldElement eCFieldElement3 = eCPoint.zs[0];
                        if (eCFieldElement2.isZero() || !eCFieldElement3.isOne()) {
                            return twice().add(eCPoint);
                        }
                        ECFieldElement eCFieldElement4 = this.y;
                        ECFieldElement eCFieldElement5 = this.zs[0];
                        ECFieldElement eCFieldElement6 = eCPoint.y;
                        ECFieldElement square = eCFieldElement.square();
                        ECFieldElement square2 = eCFieldElement4.square();
                        ECFieldElement square3 = eCFieldElement5.square();
                        ECFieldElement add = curve.getA().multiply(square3).add(square2).add(eCFieldElement4.multiply(eCFieldElement5));
                        ECFieldElement addOne = eCFieldElement6.addOne();
                        ECFieldElement multiplyPlusProduct = curve.getA().add(addOne).multiply(square3).add(square2).multiplyPlusProduct(add, square, square3);
                        ECFieldElement multiply = eCFieldElement2.multiply(square3);
                        ECFieldElement square4 = multiply.add(add).square();
                        if (square4.isZero()) {
                            if (multiplyPlusProduct.isZero()) {
                                return eCPoint.twice();
                            }
                            return curve.getInfinity();
                        }
                        if (multiplyPlusProduct.isZero()) {
                            return new F2m(curve, multiplyPlusProduct, curve.getB().sqrt());
                        }
                        ECFieldElement multiply2 = multiplyPlusProduct.square().multiply(multiply);
                        ECFieldElement multiply3 = multiplyPlusProduct.multiply(square4).multiply(square3);
                        return new F2m(curve, multiply2, multiplyPlusProduct.add(square4).square().multiplyPlusProduct(add, addOne, multiply3), new ECFieldElement[]{multiply3});
                    }
                    return twice().add(eCPoint);
                }
            }
            return eCPoint;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint negate() {
            if (!isInfinity()) {
                ECFieldElement eCFieldElement = this.x;
                if (!eCFieldElement.isZero()) {
                    int curveCoordinateSystem = getCurveCoordinateSystem();
                    if (curveCoordinateSystem == 0) {
                        return new F2m(this.curve, eCFieldElement, this.y.add(eCFieldElement));
                    }
                    if (curveCoordinateSystem == 1) {
                        return new F2m(this.curve, eCFieldElement, this.y.add(eCFieldElement), new ECFieldElement[]{this.zs[0]});
                    }
                    if (curveCoordinateSystem == 5) {
                        return new F2m(this.curve, eCFieldElement, this.y.addOne());
                    }
                    if (curveCoordinateSystem == 6) {
                        ECFieldElement eCFieldElement2 = this.y;
                        ECFieldElement eCFieldElement3 = this.zs[0];
                        return new F2m(this.curve, eCFieldElement, eCFieldElement2.add(eCFieldElement3), new ECFieldElement[]{eCFieldElement3});
                    }
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            return this;
        }
    }
}
