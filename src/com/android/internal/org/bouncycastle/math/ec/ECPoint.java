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
        ECFieldElement eCFieldElementFromBigInteger = eCCurve.fromBigInteger(ECConstants.ONE);
        if (coordinateSystem != 1 && coordinateSystem != 2) {
            if (coordinateSystem == 3) {
                return new ECFieldElement[]{eCFieldElementFromBigInteger, eCFieldElementFromBigInteger, eCFieldElementFromBigInteger};
            }
            if (coordinateSystem == 4) {
                return new ECFieldElement[]{eCFieldElementFromBigInteger, eCCurve.getA()};
            }
            if (coordinateSystem != 6) {
                throw new IllegalArgumentException("unknown coordinate system");
            }
        }
        return new ECFieldElement[]{eCFieldElementFromBigInteger};
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
                ECFieldElement eCFieldElementRandomFieldElementMult = this.curve.randomFieldElementMult(CryptoServicesRegistrar.getSecureRandom());
                return normalize(zCoord.multiply(eCFieldElementRandomFieldElementMult).invert().multiply(eCFieldElementRandomFieldElementMult));
            }
        }
        return this;
    }

    ECPoint normalize(ECFieldElement eCFieldElement) {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        if (curveCoordinateSystem != 1) {
            if (curveCoordinateSystem == 2 || curveCoordinateSystem == 3 || curveCoordinateSystem == 4) {
                ECFieldElement eCFieldElementSquare = eCFieldElement.square();
                return createScaledPoint(eCFieldElementSquare, eCFieldElementSquare.multiply(eCFieldElement));
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
        boolean zIsInfinity = isInfinity();
        boolean zIsInfinity2 = eCPoint.isInfinity();
        if (zIsInfinity || zIsInfinity2) {
            return zIsInfinity && zIsInfinity2 && (z || z2 || curve.equals(curve2));
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
        ECPoint eCPointNormalize = normalize();
        return (eCPointNormalize.getYCoord().hashCode() * 257) ^ (i ^ (eCPointNormalize.getXCoord().hashCode() * 17));
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
        ECPoint eCPointNormalize = normalize();
        byte[] encoded = eCPointNormalize.getXCoord().getEncoded();
        if (z) {
            byte[] bArr = new byte[encoded.length + 1];
            bArr[0] = (byte) (eCPointNormalize.getCompressionYTilde() ? 3 : 2);
            System.arraycopy(encoded, 0, bArr, 1, encoded.length);
            return bArr;
        }
        byte[] encoded2 = eCPointNormalize.getYCoord().getEncoded();
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
            ECFieldElement eCFieldElementSquare = eCFieldElement2.square();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 0) {
                if (curveCoordinateSystem == 1) {
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        ECFieldElement eCFieldElementSquare2 = eCFieldElement3.square();
                        ECFieldElement eCFieldElementMultiply = eCFieldElement3.multiply(eCFieldElementSquare2);
                        eCFieldElementSquare = eCFieldElementSquare.multiply(eCFieldElement3);
                        a = a.multiply(eCFieldElementSquare2);
                        b = b.multiply(eCFieldElementMultiply);
                    }
                } else if (curveCoordinateSystem == 2 || curveCoordinateSystem == 3 || curveCoordinateSystem == 4) {
                    ECFieldElement eCFieldElement4 = this.zs[0];
                    if (!eCFieldElement4.isOne()) {
                        ECFieldElement eCFieldElementSquare3 = eCFieldElement4.square();
                        ECFieldElement eCFieldElementSquare4 = eCFieldElementSquare3.square();
                        ECFieldElement eCFieldElementMultiply2 = eCFieldElementSquare3.multiply(eCFieldElementSquare4);
                        a = a.multiply(eCFieldElementSquare4);
                        b = b.multiply(eCFieldElementMultiply2);
                    }
                } else {
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            return eCFieldElementSquare.equals(eCFieldElement.square().add(a).multiply(eCFieldElement).add(b));
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

        /* JADX WARN: Removed duplicated region for block: B:61:0x0134  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0140  */
        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public ECPoint add(ECPoint eCPoint) {
            char c;
            int i;
            ECFieldElement eCFieldElementMultiplyMinusProduct;
            ECFieldElement eCFieldElementMultiply;
            ECFieldElement eCFieldElementSubtract;
            ECFieldElement eCFieldElement;
            ECFieldElement[] eCFieldElementArr;
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            if (this == eCPoint) {
                return twice();
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElementMultiply2 = this.x;
            ECFieldElement eCFieldElementMultiply3 = this.y;
            ECFieldElement eCFieldElementMultiply4 = eCPoint.x;
            ECFieldElement eCFieldElementMultiply5 = eCPoint.y;
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElementSubtract2 = eCFieldElementMultiply4.subtract(eCFieldElementMultiply2);
                ECFieldElement eCFieldElementSubtract3 = eCFieldElementMultiply5.subtract(eCFieldElementMultiply3);
                if (eCFieldElementSubtract2.isZero()) {
                    if (eCFieldElementSubtract3.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                ECFieldElement eCFieldElementDivide = eCFieldElementSubtract3.divide(eCFieldElementSubtract2);
                ECFieldElement eCFieldElementSubtract4 = eCFieldElementDivide.square().subtract(eCFieldElementMultiply2).subtract(eCFieldElementMultiply4);
                return new Fp(curve, eCFieldElementSubtract4, eCFieldElementDivide.multiply(eCFieldElementMultiply2.subtract(eCFieldElementSubtract4)).subtract(eCFieldElementMultiply3));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElementMultiply6 = this.zs[0];
                ECFieldElement eCFieldElement2 = eCPoint.zs[0];
                boolean zIsOne = eCFieldElementMultiply6.isOne();
                boolean zIsOne2 = eCFieldElement2.isOne();
                if (!zIsOne) {
                    eCFieldElementMultiply5 = eCFieldElementMultiply5.multiply(eCFieldElementMultiply6);
                }
                if (!zIsOne2) {
                    eCFieldElementMultiply3 = eCFieldElementMultiply3.multiply(eCFieldElement2);
                }
                ECFieldElement eCFieldElementSubtract5 = eCFieldElementMultiply5.subtract(eCFieldElementMultiply3);
                if (!zIsOne) {
                    eCFieldElementMultiply4 = eCFieldElementMultiply4.multiply(eCFieldElementMultiply6);
                }
                if (!zIsOne2) {
                    eCFieldElementMultiply2 = eCFieldElementMultiply2.multiply(eCFieldElement2);
                }
                ECFieldElement eCFieldElementSubtract6 = eCFieldElementMultiply4.subtract(eCFieldElementMultiply2);
                if (eCFieldElementSubtract6.isZero()) {
                    if (eCFieldElementSubtract5.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                if (zIsOne) {
                    eCFieldElementMultiply6 = eCFieldElement2;
                } else if (!zIsOne2) {
                    eCFieldElementMultiply6 = eCFieldElementMultiply6.multiply(eCFieldElement2);
                }
                ECFieldElement eCFieldElementSquare = eCFieldElementSubtract6.square();
                ECFieldElement eCFieldElementMultiply7 = eCFieldElementSquare.multiply(eCFieldElementSubtract6);
                ECFieldElement eCFieldElementMultiply8 = eCFieldElementSquare.multiply(eCFieldElementMultiply2);
                ECFieldElement eCFieldElementSubtract7 = eCFieldElementSubtract5.square().multiply(eCFieldElementMultiply6).subtract(eCFieldElementMultiply7).subtract(two(eCFieldElementMultiply8));
                return new Fp(curve, eCFieldElementSubtract6.multiply(eCFieldElementSubtract7), eCFieldElementMultiply8.subtract(eCFieldElementSubtract7).multiplyMinusProduct(eCFieldElementSubtract5, eCFieldElementMultiply3, eCFieldElementMultiply7), new ECFieldElement[]{eCFieldElementMultiply7.multiply(eCFieldElementMultiply6)});
            }
            if (coordinateSystem == 2 || coordinateSystem == 4) {
                ECFieldElement eCFieldElement3 = this.zs[0];
                ECFieldElement eCFieldElement4 = eCPoint.zs[0];
                boolean zIsOne3 = eCFieldElement3.isOne();
                if (!zIsOne3 && eCFieldElement3.equals(eCFieldElement4)) {
                    ECFieldElement eCFieldElementSubtract8 = eCFieldElementMultiply2.subtract(eCFieldElementMultiply4);
                    ECFieldElement eCFieldElementSubtract9 = eCFieldElementMultiply3.subtract(eCFieldElementMultiply5);
                    if (eCFieldElementSubtract8.isZero()) {
                        if (eCFieldElementSubtract9.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    ECFieldElement eCFieldElementSquare2 = eCFieldElementSubtract8.square();
                    ECFieldElement eCFieldElementMultiply9 = eCFieldElementMultiply2.multiply(eCFieldElementSquare2);
                    ECFieldElement eCFieldElementMultiply10 = eCFieldElementMultiply4.multiply(eCFieldElementSquare2);
                    ECFieldElement eCFieldElementMultiply11 = eCFieldElementMultiply9.subtract(eCFieldElementMultiply10).multiply(eCFieldElementMultiply3);
                    eCFieldElementSubtract = eCFieldElementSubtract9.square().subtract(eCFieldElementMultiply9).subtract(eCFieldElementMultiply10);
                    eCFieldElementMultiplyMinusProduct = eCFieldElementMultiply9.subtract(eCFieldElementSubtract).multiply(eCFieldElementSubtract9).subtract(eCFieldElementMultiply11);
                    eCFieldElementMultiply = eCFieldElementSubtract8.multiply(eCFieldElement3);
                    i = 1;
                    c = 0;
                } else {
                    if (!zIsOne3) {
                        ECFieldElement eCFieldElementSquare3 = eCFieldElement3.square();
                        eCFieldElementMultiply4 = eCFieldElementSquare3.multiply(eCFieldElementMultiply4);
                        eCFieldElementMultiply5 = eCFieldElementSquare3.multiply(eCFieldElement3).multiply(eCFieldElementMultiply5);
                    }
                    boolean zIsOne4 = eCFieldElement4.isOne();
                    if (zIsOne4) {
                        c = 0;
                    } else {
                        c = 0;
                        ECFieldElement eCFieldElementSquare4 = eCFieldElement4.square();
                        eCFieldElementMultiply2 = eCFieldElementSquare4.multiply(eCFieldElementMultiply2);
                        eCFieldElementMultiply3 = eCFieldElementSquare4.multiply(eCFieldElement4).multiply(eCFieldElementMultiply3);
                    }
                    ECFieldElement eCFieldElementSubtract10 = eCFieldElementMultiply2.subtract(eCFieldElementMultiply4);
                    ECFieldElement eCFieldElementSubtract11 = eCFieldElementMultiply3.subtract(eCFieldElementMultiply5);
                    if (eCFieldElementSubtract10.isZero()) {
                        if (eCFieldElementSubtract11.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    ECFieldElement eCFieldElementSquare5 = eCFieldElementSubtract10.square();
                    ECFieldElement eCFieldElementMultiply12 = eCFieldElementSquare5.multiply(eCFieldElementSubtract10);
                    ECFieldElement eCFieldElementMultiply13 = eCFieldElementSquare5.multiply(eCFieldElementMultiply2);
                    i = 1;
                    ECFieldElement eCFieldElementSubtract12 = eCFieldElementSubtract11.square().add(eCFieldElementMultiply12).subtract(two(eCFieldElementMultiply13));
                    eCFieldElementMultiplyMinusProduct = eCFieldElementMultiply13.subtract(eCFieldElementSubtract12).multiplyMinusProduct(eCFieldElementSubtract11, eCFieldElementMultiply12, eCFieldElementMultiply3);
                    ECFieldElement eCFieldElementMultiply14 = !zIsOne3 ? eCFieldElementSubtract10.multiply(eCFieldElement3) : eCFieldElementSubtract10;
                    eCFieldElementMultiply = !zIsOne4 ? eCFieldElementMultiply14.multiply(eCFieldElement4) : eCFieldElementMultiply14;
                    if (eCFieldElementMultiply == eCFieldElementSubtract10) {
                        eCFieldElementSubtract = eCFieldElementSubtract12;
                        eCFieldElement = eCFieldElementSquare5;
                        if (coordinateSystem != 4) {
                            ECFieldElement eCFieldElementCalculateJacobianModifiedW = calculateJacobianModifiedW(eCFieldElementMultiply, eCFieldElement);
                            eCFieldElementArr = new ECFieldElement[2];
                            eCFieldElementArr[c] = eCFieldElementMultiply;
                            eCFieldElementArr[i] = eCFieldElementCalculateJacobianModifiedW;
                        } else {
                            eCFieldElementArr = new ECFieldElement[i];
                            eCFieldElementArr[c] = eCFieldElementMultiply;
                        }
                        return new Fp(curve, eCFieldElementSubtract, eCFieldElementMultiplyMinusProduct, eCFieldElementArr);
                    }
                    eCFieldElementSubtract = eCFieldElementSubtract12;
                }
                eCFieldElement = null;
                if (coordinateSystem != 4) {
                }
                return new Fp(curve, eCFieldElementSubtract, eCFieldElementMultiplyMinusProduct, eCFieldElementArr);
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            ECFieldElement eCFieldElementAdd;
            ECFieldElement eCFieldElementFour;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement2 = this.x;
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElementDivide = three(eCFieldElement2.square()).add(getCurve().getA()).divide(two(eCFieldElement));
                ECFieldElement eCFieldElementSubtract = eCFieldElementDivide.square().subtract(two(eCFieldElement2));
                return new Fp(curve, eCFieldElementSubtract, eCFieldElementDivide.multiply(eCFieldElement2.subtract(eCFieldElementSubtract)).subtract(eCFieldElement));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement3 = this.zs[0];
                boolean zIsOne = eCFieldElement3.isOne();
                ECFieldElement a = curve.getA();
                if (!a.isZero() && !zIsOne) {
                    a = a.multiply(eCFieldElement3.square());
                }
                ECFieldElement eCFieldElementAdd2 = a.add(three(eCFieldElement2.square()));
                ECFieldElement eCFieldElementMultiply = zIsOne ? eCFieldElement : eCFieldElement.multiply(eCFieldElement3);
                ECFieldElement eCFieldElementSquare = zIsOne ? eCFieldElement.square() : eCFieldElementMultiply.multiply(eCFieldElement);
                ECFieldElement eCFieldElementFour2 = four(eCFieldElement2.multiply(eCFieldElementSquare));
                ECFieldElement eCFieldElementSubtract2 = eCFieldElementAdd2.square().subtract(two(eCFieldElementFour2));
                ECFieldElement eCFieldElementTwo = two(eCFieldElementMultiply);
                ECFieldElement eCFieldElementMultiply2 = eCFieldElementSubtract2.multiply(eCFieldElementTwo);
                ECFieldElement eCFieldElementTwo2 = two(eCFieldElementSquare);
                return new Fp(curve, eCFieldElementMultiply2, eCFieldElementFour2.subtract(eCFieldElementSubtract2).multiply(eCFieldElementAdd2).subtract(two(eCFieldElementTwo2.square())), new ECFieldElement[]{two(zIsOne ? two(eCFieldElementTwo2) : eCFieldElementTwo.square()).multiply(eCFieldElementMultiply)});
            }
            if (coordinateSystem != 2) {
                if (coordinateSystem == 4) {
                    return twiceJacobianModified(true);
                }
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECFieldElement eCFieldElement4 = this.zs[0];
            boolean zIsOne2 = eCFieldElement4.isOne();
            ECFieldElement eCFieldElementSquare2 = eCFieldElement.square();
            ECFieldElement eCFieldElementSquare3 = eCFieldElementSquare2.square();
            ECFieldElement a2 = curve.getA();
            ECFieldElement eCFieldElementNegate = a2.negate();
            if (eCFieldElementNegate.toBigInteger().equals(BigInteger.valueOf(3L))) {
                ECFieldElement eCFieldElementSquare4 = zIsOne2 ? eCFieldElement4 : eCFieldElement4.square();
                eCFieldElementAdd = three(eCFieldElement2.add(eCFieldElementSquare4).multiply(eCFieldElement2.subtract(eCFieldElementSquare4)));
                eCFieldElementFour = four(eCFieldElementSquare2.multiply(eCFieldElement2));
            } else {
                ECFieldElement eCFieldElementThree = three(eCFieldElement2.square());
                if (zIsOne2) {
                    eCFieldElementAdd = eCFieldElementThree.add(a2);
                } else if (a2.isZero()) {
                    eCFieldElementAdd = eCFieldElementThree;
                } else {
                    ECFieldElement eCFieldElementSquare5 = eCFieldElement4.square().square();
                    if (eCFieldElementNegate.bitLength() < a2.bitLength()) {
                        eCFieldElementAdd = eCFieldElementThree.subtract(eCFieldElementSquare5.multiply(eCFieldElementNegate));
                    } else {
                        eCFieldElementAdd = eCFieldElementThree.add(eCFieldElementSquare5.multiply(a2));
                    }
                }
                eCFieldElementFour = four(eCFieldElement2.multiply(eCFieldElementSquare2));
            }
            ECFieldElement eCFieldElementSubtract3 = eCFieldElementAdd.square().subtract(two(eCFieldElementFour));
            ECFieldElement eCFieldElementSubtract4 = eCFieldElementFour.subtract(eCFieldElementSubtract3).multiply(eCFieldElementAdd).subtract(eight(eCFieldElementSquare3));
            ECFieldElement eCFieldElementTwo3 = two(eCFieldElement);
            if (!zIsOne2) {
                eCFieldElementTwo3 = eCFieldElementTwo3.multiply(eCFieldElement4);
            }
            return new Fp(curve, eCFieldElementSubtract3, eCFieldElementSubtract4, new ECFieldElement[]{eCFieldElementTwo3});
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
                    ECFieldElement eCFieldElementSubtract = eCFieldElement3.subtract(eCFieldElement2);
                    ECFieldElement eCFieldElementSubtract2 = eCFieldElement4.subtract(eCFieldElement);
                    if (eCFieldElementSubtract.isZero()) {
                        return eCFieldElementSubtract2.isZero() ? threeTimes() : this;
                    }
                    ECFieldElement eCFieldElementSquare = eCFieldElementSubtract.square();
                    ECFieldElement eCFieldElementSubtract3 = eCFieldElementSquare.multiply(two(eCFieldElement2).add(eCFieldElement3)).subtract(eCFieldElementSubtract2.square());
                    if (eCFieldElementSubtract3.isZero()) {
                        return curve.getInfinity();
                    }
                    ECFieldElement eCFieldElementInvert = eCFieldElementSubtract3.multiply(eCFieldElementSubtract).invert();
                    ECFieldElement eCFieldElementMultiply = eCFieldElementSubtract3.multiply(eCFieldElementInvert).multiply(eCFieldElementSubtract2);
                    ECFieldElement eCFieldElementSubtract4 = two(eCFieldElement).multiply(eCFieldElementSquare).multiply(eCFieldElementSubtract).multiply(eCFieldElementInvert).subtract(eCFieldElementMultiply);
                    ECFieldElement eCFieldElementAdd = eCFieldElementSubtract4.subtract(eCFieldElementMultiply).multiply(eCFieldElementMultiply.add(eCFieldElementSubtract4)).add(eCFieldElement3);
                    return new Fp(curve, eCFieldElementAdd, eCFieldElement2.subtract(eCFieldElementAdd).multiply(eCFieldElementSubtract4).subtract(eCFieldElement));
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
                    ECFieldElement eCFieldElementTwo = two(eCFieldElement);
                    ECFieldElement eCFieldElementSquare = eCFieldElementTwo.square();
                    ECFieldElement eCFieldElementAdd = three(eCFieldElement2.square()).add(getCurve().getA());
                    ECFieldElement eCFieldElementSubtract = three(eCFieldElement2).multiply(eCFieldElementSquare).subtract(eCFieldElementAdd.square());
                    if (eCFieldElementSubtract.isZero()) {
                        return getCurve().getInfinity();
                    }
                    ECFieldElement eCFieldElementInvert = eCFieldElementSubtract.multiply(eCFieldElementTwo).invert();
                    ECFieldElement eCFieldElementMultiply = eCFieldElementSubtract.multiply(eCFieldElementInvert).multiply(eCFieldElementAdd);
                    ECFieldElement eCFieldElementSubtract2 = eCFieldElementSquare.square().multiply(eCFieldElementInvert).subtract(eCFieldElementMultiply);
                    ECFieldElement eCFieldElementAdd2 = eCFieldElementSubtract2.subtract(eCFieldElementMultiply).multiply(eCFieldElementMultiply.add(eCFieldElementSubtract2)).add(eCFieldElement2);
                    return new Fp(curve, eCFieldElementAdd2, eCFieldElement2.subtract(eCFieldElementAdd2).multiply(eCFieldElementSubtract2).subtract(eCFieldElement));
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
            ECFieldElement eCFieldElementSubtract = this.y;
            if (eCFieldElementSubtract.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement a = curve.getA();
            ECFieldElement eCFieldElementMultiply = this.x;
            boolean z = false;
            ECFieldElement eCFieldElementFromBigInteger = this.zs.length < 1 ? curve.fromBigInteger(ECConstants.ONE) : this.zs[0];
            if (!eCFieldElementFromBigInteger.isOne() && coordinateSystem != 0) {
                if (coordinateSystem == 1) {
                    ECFieldElement eCFieldElementSquare = eCFieldElementFromBigInteger.square();
                    eCFieldElementMultiply = eCFieldElementMultiply.multiply(eCFieldElementFromBigInteger);
                    eCFieldElementSubtract = eCFieldElementSubtract.multiply(eCFieldElementSquare);
                    a = calculateJacobianModifiedW(eCFieldElementFromBigInteger, eCFieldElementSquare);
                } else if (coordinateSystem == 2) {
                    a = calculateJacobianModifiedW(eCFieldElementFromBigInteger, null);
                } else if (coordinateSystem == 4) {
                    a = getJacobianModifiedW();
                } else {
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            int i2 = 0;
            while (i2 < i) {
                if (eCFieldElementSubtract.isZero()) {
                    return curve.getInfinity();
                }
                ECFieldElement eCFieldElementThree = three(eCFieldElementMultiply.square());
                ECFieldElement eCFieldElementTwo = two(eCFieldElementSubtract);
                ECFieldElement eCFieldElementMultiply2 = eCFieldElementTwo.multiply(eCFieldElementSubtract);
                ECFieldElement eCFieldElementTwo2 = two(eCFieldElementMultiply.multiply(eCFieldElementMultiply2));
                ECFieldElement eCFieldElementTwo3 = two(eCFieldElementMultiply2.square());
                if (!a.isZero()) {
                    eCFieldElementThree = eCFieldElementThree.add(a);
                    a = two(eCFieldElementTwo3.multiply(a));
                }
                boolean z2 = z;
                ECFieldElement eCFieldElementSubtract2 = eCFieldElementThree.square().subtract(two(eCFieldElementTwo2));
                eCFieldElementSubtract = eCFieldElementThree.multiply(eCFieldElementTwo2.subtract(eCFieldElementSubtract2)).subtract(eCFieldElementTwo3);
                eCFieldElementFromBigInteger = eCFieldElementFromBigInteger.isOne() ? eCFieldElementTwo : eCFieldElementTwo.multiply(eCFieldElementFromBigInteger);
                i2++;
                eCFieldElementMultiply = eCFieldElementSubtract2;
                z = z2;
            }
            boolean z3 = z;
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElementInvert = eCFieldElementFromBigInteger.invert();
                ECFieldElement eCFieldElementSquare2 = eCFieldElementInvert.square();
                return new Fp(curve, eCFieldElementMultiply.multiply(eCFieldElementSquare2), eCFieldElementSubtract.multiply(eCFieldElementSquare2.multiply(eCFieldElementInvert)));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElementMultiply3 = eCFieldElementMultiply.multiply(eCFieldElementFromBigInteger);
                ECFieldElement[] eCFieldElementArr = new ECFieldElement[1];
                eCFieldElementArr[z3 ? 1 : 0] = eCFieldElementFromBigInteger.multiply(eCFieldElementFromBigInteger.square());
                return new Fp(curve, eCFieldElementMultiply3, eCFieldElementSubtract, eCFieldElementArr);
            }
            if (coordinateSystem == 2) {
                ECFieldElement[] eCFieldElementArr2 = new ECFieldElement[1];
                eCFieldElementArr2[z3 ? 1 : 0] = eCFieldElementFromBigInteger;
                return new Fp(curve, eCFieldElementMultiply, eCFieldElementSubtract, eCFieldElementArr2);
            }
            if (coordinateSystem == 4) {
                ECFieldElement[] eCFieldElementArr3 = new ECFieldElement[2];
                eCFieldElementArr3[z3 ? 1 : 0] = eCFieldElementFromBigInteger;
                eCFieldElementArr3[1] = a;
                return new Fp(curve, eCFieldElementMultiply, eCFieldElementSubtract, eCFieldElementArr3);
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
            ECFieldElement eCFieldElementSquare = eCFieldElement2.square();
            ECFieldElement eCFieldElementNegate = a.negate();
            if (eCFieldElementNegate.bitLength() < a.bitLength()) {
                return eCFieldElementSquare.multiply(eCFieldElementNegate).negate();
            }
            return eCFieldElementSquare.multiply(a);
        }

        protected ECFieldElement getJacobianModifiedW() {
            ECFieldElement eCFieldElement = this.zs[1];
            if (eCFieldElement != null) {
                return eCFieldElement;
            }
            ECFieldElement[] eCFieldElementArr = this.zs;
            ECFieldElement eCFieldElementCalculateJacobianModifiedW = calculateJacobianModifiedW(this.zs[0], null);
            eCFieldElementArr[1] = eCFieldElementCalculateJacobianModifiedW;
            return eCFieldElementCalculateJacobianModifiedW;
        }

        protected Fp twiceJacobianModified(boolean z) {
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement eCFieldElement2 = this.y;
            ECFieldElement eCFieldElement3 = this.zs[0];
            ECFieldElement jacobianModifiedW = getJacobianModifiedW();
            ECFieldElement eCFieldElementAdd = three(eCFieldElement.square()).add(jacobianModifiedW);
            ECFieldElement eCFieldElementTwo = two(eCFieldElement2);
            ECFieldElement eCFieldElementMultiply = eCFieldElementTwo.multiply(eCFieldElement2);
            ECFieldElement eCFieldElementTwo2 = two(eCFieldElement.multiply(eCFieldElementMultiply));
            ECFieldElement eCFieldElementSubtract = eCFieldElementAdd.square().subtract(two(eCFieldElementTwo2));
            ECFieldElement eCFieldElementTwo3 = two(eCFieldElementMultiply.square());
            ECFieldElement eCFieldElementSubtract2 = eCFieldElementAdd.multiply(eCFieldElementTwo2.subtract(eCFieldElementSubtract)).subtract(eCFieldElementTwo3);
            ECFieldElement eCFieldElementTwo4 = z ? two(eCFieldElementTwo3.multiply(jacobianModifiedW)) : null;
            if (!eCFieldElement3.isOne()) {
                eCFieldElementTwo = eCFieldElementTwo.multiply(eCFieldElement3);
            }
            return new Fp(getCurve(), eCFieldElementSubtract, eCFieldElementSubtract2, new ECFieldElement[]{eCFieldElementTwo, eCFieldElementTwo4});
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
            ECFieldElement eCFieldElementMultiplyPlusProduct;
            ECFieldElement eCFieldElementSquarePlusProduct;
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement a = curve.getA();
            ECFieldElement b = curve.getB();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 6) {
                ECFieldElement eCFieldElement2 = this.zs[0];
                boolean zIsOne = eCFieldElement2.isOne();
                if (eCFieldElement.isZero()) {
                    ECFieldElement eCFieldElementSquare = this.y.square();
                    if (!zIsOne) {
                        b = b.multiply(eCFieldElement2.square());
                    }
                    return eCFieldElementSquare.equals(b);
                }
                ECFieldElement eCFieldElement3 = this.y;
                ECFieldElement eCFieldElementSquare2 = eCFieldElement.square();
                if (zIsOne) {
                    eCFieldElementMultiplyPlusProduct = eCFieldElement3.square().add(eCFieldElement3).add(a);
                    eCFieldElementSquarePlusProduct = eCFieldElementSquare2.square().add(b);
                } else {
                    ECFieldElement eCFieldElementSquare3 = eCFieldElement2.square();
                    ECFieldElement eCFieldElementSquare4 = eCFieldElementSquare3.square();
                    eCFieldElementMultiplyPlusProduct = eCFieldElement3.add(eCFieldElement2).multiplyPlusProduct(eCFieldElement3, a, eCFieldElementSquare3);
                    eCFieldElementSquarePlusProduct = eCFieldElementSquare2.squarePlusProduct(b, eCFieldElementSquare4);
                }
                return eCFieldElementMultiplyPlusProduct.multiply(eCFieldElementSquare2).equals(eCFieldElementSquarePlusProduct);
            }
            ECFieldElement eCFieldElement4 = this.y;
            ECFieldElement eCFieldElementMultiply = eCFieldElement4.add(eCFieldElement).multiply(eCFieldElement4);
            if (coordinateSystem != 0) {
                if (coordinateSystem == 1) {
                    ECFieldElement eCFieldElement5 = this.zs[0];
                    if (!eCFieldElement5.isOne()) {
                        ECFieldElement eCFieldElementMultiply2 = eCFieldElement5.multiply(eCFieldElement5.square());
                        eCFieldElementMultiply = eCFieldElementMultiply.multiply(eCFieldElement5);
                        a = a.multiply(eCFieldElement5);
                        b = b.multiply(eCFieldElementMultiply2);
                    }
                } else {
                    throw new IllegalStateException("unsupported coordinate system");
                }
            }
            return eCFieldElementMultiply.equals(eCFieldElement.add(a).multiply(eCFieldElement.square()).add(b));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesOrder() {
            BigInteger cofactor = this.curve.getCofactor();
            if (ECConstants.TWO.equals(cofactor)) {
                return ((ECFieldElement.AbstractF2m) normalize().getAffineXCoord()).trace() != 0;
            }
            if (ECConstants.FOUR.equals(cofactor)) {
                ECPoint eCPointNormalize = normalize();
                ECFieldElement affineXCoord = eCPointNormalize.getAffineXCoord();
                ECFieldElement eCFieldElementSolveQuadraticEquation = ((ECCurve.AbstractF2m) this.curve).solveQuadraticEquation(affineXCoord.add(this.curve.getA()));
                if (eCFieldElementSolveQuadraticEquation == null) {
                    return false;
                }
                return ((ECFieldElement.AbstractF2m) affineXCoord.multiply(eCFieldElementSolveQuadraticEquation).add(eCPointNormalize.getAffineYCoord())).trace() == 0;
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
                ECFieldElement eCFieldElementMultiply = rawXCoord2.multiply(eCFieldElement.square());
                return getCurve().createRawPoint(eCFieldElementMultiply, rawYCoord.add(rawXCoord2).add(eCFieldElementMultiply), new ECFieldElement[]{eCFieldElement2.multiply(eCFieldElement)});
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
                ECFieldElement eCFieldElementMultiply = eCFieldElement2.add(eCFieldElement).multiply(eCFieldElement);
                if (6 == curveCoordinateSystem) {
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        return eCFieldElementMultiply.divide(eCFieldElement3);
                    }
                }
                return eCFieldElementMultiply;
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
            ECFieldElement eCFieldElementMultiply;
            ECFieldElement eCFieldElementMultiply2;
            ECFieldElement eCFieldElementMultiply3;
            ECFieldElement eCFieldElementMultiply4;
            ECFieldElement eCFieldElementMultiply5;
            ECFieldElement eCFieldElementSquarePlusProduct;
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElementMultiply6 = this.x;
            ECFieldElement eCFieldElement = eCPoint.x;
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElement2 = this.y;
                ECFieldElement eCFieldElement3 = eCPoint.y;
                ECFieldElement eCFieldElementAdd = eCFieldElementMultiply6.add(eCFieldElement);
                ECFieldElement eCFieldElementAdd2 = eCFieldElement2.add(eCFieldElement3);
                if (eCFieldElementAdd.isZero()) {
                    if (eCFieldElementAdd2.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                ECFieldElement eCFieldElementDivide = eCFieldElementAdd2.divide(eCFieldElementAdd);
                ECFieldElement eCFieldElementAdd3 = eCFieldElementDivide.square().add(eCFieldElementDivide).add(eCFieldElementAdd).add(curve.getA());
                return new F2m(curve, eCFieldElementAdd3, eCFieldElementDivide.multiply(eCFieldElementMultiply6.add(eCFieldElementAdd3)).add(eCFieldElementAdd3).add(eCFieldElement2));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement4 = this.y;
                ECFieldElement eCFieldElementMultiply7 = this.zs[0];
                ECFieldElement eCFieldElement5 = eCPoint.y;
                ECFieldElement eCFieldElement6 = eCPoint.zs[0];
                boolean zIsOne = eCFieldElement6.isOne();
                ECFieldElement eCFieldElementAdd4 = eCFieldElementMultiply7.multiply(eCFieldElement5).add(zIsOne ? eCFieldElement4 : eCFieldElement4.multiply(eCFieldElement6));
                ECFieldElement eCFieldElementAdd5 = eCFieldElementMultiply7.multiply(eCFieldElement).add(zIsOne ? eCFieldElementMultiply6 : eCFieldElementMultiply6.multiply(eCFieldElement6));
                if (eCFieldElementAdd5.isZero()) {
                    if (eCFieldElementAdd4.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                ECFieldElement eCFieldElementSquare = eCFieldElementAdd5.square();
                ECFieldElement eCFieldElementMultiply8 = eCFieldElementSquare.multiply(eCFieldElementAdd5);
                if (!zIsOne) {
                    eCFieldElementMultiply7 = eCFieldElementMultiply7.multiply(eCFieldElement6);
                }
                ECFieldElement eCFieldElementAdd6 = eCFieldElementAdd4.add(eCFieldElementAdd5);
                ECFieldElement eCFieldElementAdd7 = eCFieldElementAdd6.multiplyPlusProduct(eCFieldElementAdd4, eCFieldElementSquare, curve.getA()).multiply(eCFieldElementMultiply7).add(eCFieldElementMultiply8);
                ECFieldElement eCFieldElementMultiply9 = eCFieldElementAdd5.multiply(eCFieldElementAdd7);
                if (!zIsOne) {
                    eCFieldElementSquare = eCFieldElementSquare.multiply(eCFieldElement6);
                }
                return new F2m(curve, eCFieldElementMultiply9, eCFieldElementAdd4.multiplyPlusProduct(eCFieldElementMultiply6, eCFieldElementAdd5, eCFieldElement4).multiplyPlusProduct(eCFieldElementSquare, eCFieldElementAdd6, eCFieldElementAdd7), new ECFieldElement[]{eCFieldElementMultiply8.multiply(eCFieldElementMultiply7)});
            }
            if (coordinateSystem == 6) {
                if (eCFieldElementMultiply6.isZero()) {
                    if (eCFieldElement.isZero()) {
                        return curve.getInfinity();
                    }
                    return eCPoint.add(this);
                }
                ECFieldElement eCFieldElement7 = this.y;
                ECFieldElement eCFieldElement8 = this.zs[0];
                ECFieldElement eCFieldElement9 = eCPoint.y;
                ECFieldElement eCFieldElement10 = eCPoint.zs[0];
                boolean zIsOne2 = eCFieldElement8.isOne();
                if (zIsOne2) {
                    eCFieldElementMultiply = eCFieldElement;
                    eCFieldElementMultiply2 = eCFieldElement9;
                } else {
                    eCFieldElementMultiply = eCFieldElement.multiply(eCFieldElement8);
                    eCFieldElementMultiply2 = eCFieldElement9.multiply(eCFieldElement8);
                }
                boolean zIsOne3 = eCFieldElement10.isOne();
                if (zIsOne3) {
                    eCFieldElementMultiply3 = eCFieldElement7;
                } else {
                    eCFieldElementMultiply6 = eCFieldElementMultiply6.multiply(eCFieldElement10);
                    eCFieldElementMultiply3 = eCFieldElement7.multiply(eCFieldElement10);
                }
                ECFieldElement eCFieldElementAdd8 = eCFieldElementMultiply3.add(eCFieldElementMultiply2);
                ECFieldElement eCFieldElementAdd9 = eCFieldElementMultiply6.add(eCFieldElementMultiply);
                if (eCFieldElementAdd9.isZero()) {
                    if (eCFieldElementAdd8.isZero()) {
                        return twice();
                    }
                    return curve.getInfinity();
                }
                if (eCFieldElement.isZero()) {
                    ECPoint eCPointNormalize = normalize();
                    ECFieldElement xCoord = eCPointNormalize.getXCoord();
                    ECFieldElement yCoord = eCPointNormalize.getYCoord();
                    ECFieldElement eCFieldElementDivide2 = yCoord.add(eCFieldElement9).divide(xCoord);
                    eCFieldElementMultiply4 = eCFieldElementDivide2.square().add(eCFieldElementDivide2).add(xCoord).add(curve.getA());
                    if (eCFieldElementMultiply4.isZero()) {
                        return new F2m(curve, eCFieldElementMultiply4, curve.getB().sqrt());
                    }
                    eCFieldElementSquarePlusProduct = eCFieldElementDivide2.multiply(xCoord.add(eCFieldElementMultiply4)).add(eCFieldElementMultiply4).add(yCoord).divide(eCFieldElementMultiply4).add(eCFieldElementMultiply4);
                    eCFieldElementMultiply5 = curve.fromBigInteger(ECConstants.ONE);
                } else {
                    ECFieldElement eCFieldElementSquare2 = eCFieldElementAdd9.square();
                    ECFieldElement eCFieldElementMultiply10 = eCFieldElementAdd8.multiply(eCFieldElementMultiply6);
                    ECFieldElement eCFieldElementMultiply11 = eCFieldElementAdd8.multiply(eCFieldElementMultiply);
                    eCFieldElementMultiply4 = eCFieldElementMultiply10.multiply(eCFieldElementMultiply11);
                    if (eCFieldElementMultiply4.isZero()) {
                        return new F2m(curve, eCFieldElementMultiply4, curve.getB().sqrt());
                    }
                    ECFieldElement eCFieldElementMultiply12 = eCFieldElementAdd8.multiply(eCFieldElementSquare2);
                    eCFieldElementMultiply5 = !zIsOne3 ? eCFieldElementMultiply12.multiply(eCFieldElement10) : eCFieldElementMultiply12;
                    eCFieldElementSquarePlusProduct = eCFieldElementMultiply11.add(eCFieldElementSquare2).squarePlusProduct(eCFieldElementMultiply5, eCFieldElement7.add(eCFieldElement8));
                    if (!zIsOne2) {
                        eCFieldElementMultiply5 = eCFieldElementMultiply5.multiply(eCFieldElement8);
                    }
                }
                return new F2m(curve, eCFieldElementMultiply4, eCFieldElementSquarePlusProduct, new ECFieldElement[]{eCFieldElementMultiply5});
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            ECFieldElement eCFieldElementAdd;
            ECFieldElement eCFieldElementSquarePlusProduct;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElementMultiply = this.x;
            if (eCFieldElementMultiply.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElementAdd2 = this.y.divide(eCFieldElementMultiply).add(eCFieldElementMultiply);
                ECFieldElement eCFieldElementAdd3 = eCFieldElementAdd2.square().add(eCFieldElementAdd2).add(curve.getA());
                return new F2m(curve, eCFieldElementAdd3, eCFieldElementMultiply.squarePlusProduct(eCFieldElementAdd3, eCFieldElementAdd2.addOne()));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElementMultiply2 = this.y;
                ECFieldElement eCFieldElement = this.zs[0];
                boolean zIsOne = eCFieldElement.isOne();
                ECFieldElement eCFieldElementMultiply3 = zIsOne ? eCFieldElementMultiply : eCFieldElementMultiply.multiply(eCFieldElement);
                if (!zIsOne) {
                    eCFieldElementMultiply2 = eCFieldElementMultiply2.multiply(eCFieldElement);
                }
                ECFieldElement eCFieldElementSquare = eCFieldElementMultiply.square();
                ECFieldElement eCFieldElementAdd4 = eCFieldElementSquare.add(eCFieldElementMultiply2);
                ECFieldElement eCFieldElementSquare2 = eCFieldElementMultiply3.square();
                ECFieldElement eCFieldElementAdd5 = eCFieldElementAdd4.add(eCFieldElementMultiply3);
                ECFieldElement eCFieldElementMultiplyPlusProduct = eCFieldElementAdd5.multiplyPlusProduct(eCFieldElementAdd4, eCFieldElementSquare2, curve.getA());
                return new F2m(curve, eCFieldElementMultiply3.multiply(eCFieldElementMultiplyPlusProduct), eCFieldElementSquare.square().multiplyPlusProduct(eCFieldElementMultiply3, eCFieldElementMultiplyPlusProduct, eCFieldElementAdd5), new ECFieldElement[]{eCFieldElementMultiply3.multiply(eCFieldElementSquare2)});
            }
            if (coordinateSystem == 6) {
                ECFieldElement eCFieldElement2 = this.y;
                ECFieldElement eCFieldElement3 = this.zs[0];
                boolean zIsOne2 = eCFieldElement3.isOne();
                ECFieldElement eCFieldElementMultiply4 = zIsOne2 ? eCFieldElement2 : eCFieldElement2.multiply(eCFieldElement3);
                ECFieldElement eCFieldElementSquare3 = zIsOne2 ? eCFieldElement3 : eCFieldElement3.square();
                ECFieldElement a = curve.getA();
                ECFieldElement eCFieldElementMultiply5 = zIsOne2 ? a : a.multiply(eCFieldElementSquare3);
                ECFieldElement eCFieldElementAdd6 = eCFieldElement2.square().add(eCFieldElementMultiply4).add(eCFieldElementMultiply5);
                if (eCFieldElementAdd6.isZero()) {
                    return new F2m(curve, eCFieldElementAdd6, curve.getB().sqrt());
                }
                ECFieldElement eCFieldElementSquare4 = eCFieldElementAdd6.square();
                ECFieldElement eCFieldElementMultiply6 = zIsOne2 ? eCFieldElementAdd6 : eCFieldElementAdd6.multiply(eCFieldElementSquare3);
                ECFieldElement b = curve.getB();
                if (b.bitLength() < (curve.getFieldSize() >> 1)) {
                    ECFieldElement eCFieldElementSquare5 = eCFieldElement2.add(eCFieldElementMultiply).square();
                    if (b.isOne()) {
                        eCFieldElementSquarePlusProduct = eCFieldElementMultiply5.add(eCFieldElementSquare3).square();
                    } else {
                        eCFieldElementSquarePlusProduct = eCFieldElementMultiply5.squarePlusProduct(b, eCFieldElementSquare3.square());
                    }
                    eCFieldElementAdd = eCFieldElementSquare5.add(eCFieldElementAdd6).add(eCFieldElementSquare3).multiply(eCFieldElementSquare5).add(eCFieldElementSquarePlusProduct).add(eCFieldElementSquare4);
                    if (a.isZero()) {
                        eCFieldElementAdd = eCFieldElementAdd.add(eCFieldElementMultiply6);
                    } else if (!a.isOne()) {
                        eCFieldElementAdd = eCFieldElementAdd.add(a.addOne().multiply(eCFieldElementMultiply6));
                    }
                } else {
                    if (!zIsOne2) {
                        eCFieldElementMultiply = eCFieldElementMultiply.multiply(eCFieldElement3);
                    }
                    eCFieldElementAdd = eCFieldElementMultiply.squarePlusProduct(eCFieldElementAdd6, eCFieldElementMultiply4).add(eCFieldElementSquare4).add(eCFieldElementMultiply6);
                }
                return new F2m(curve, eCFieldElementSquare4, eCFieldElementAdd, new ECFieldElement[]{eCFieldElementMultiply6});
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
                        ECFieldElement eCFieldElementSquare = eCFieldElement.square();
                        ECFieldElement eCFieldElementSquare2 = eCFieldElement4.square();
                        ECFieldElement eCFieldElementSquare3 = eCFieldElement5.square();
                        ECFieldElement eCFieldElementAdd = curve.getA().multiply(eCFieldElementSquare3).add(eCFieldElementSquare2).add(eCFieldElement4.multiply(eCFieldElement5));
                        ECFieldElement eCFieldElementAddOne = eCFieldElement6.addOne();
                        ECFieldElement eCFieldElementMultiplyPlusProduct = curve.getA().add(eCFieldElementAddOne).multiply(eCFieldElementSquare3).add(eCFieldElementSquare2).multiplyPlusProduct(eCFieldElementAdd, eCFieldElementSquare, eCFieldElementSquare3);
                        ECFieldElement eCFieldElementMultiply = eCFieldElement2.multiply(eCFieldElementSquare3);
                        ECFieldElement eCFieldElementSquare4 = eCFieldElementMultiply.add(eCFieldElementAdd).square();
                        if (eCFieldElementSquare4.isZero()) {
                            if (eCFieldElementMultiplyPlusProduct.isZero()) {
                                return eCPoint.twice();
                            }
                            return curve.getInfinity();
                        }
                        if (eCFieldElementMultiplyPlusProduct.isZero()) {
                            return new F2m(curve, eCFieldElementMultiplyPlusProduct, curve.getB().sqrt());
                        }
                        ECFieldElement eCFieldElementMultiply2 = eCFieldElementMultiplyPlusProduct.square().multiply(eCFieldElementMultiply);
                        ECFieldElement eCFieldElementMultiply3 = eCFieldElementMultiplyPlusProduct.multiply(eCFieldElementSquare4).multiply(eCFieldElementSquare3);
                        return new F2m(curve, eCFieldElementMultiply2, eCFieldElementMultiplyPlusProduct.add(eCFieldElementSquare4).square().multiplyPlusProduct(eCFieldElementAdd, eCFieldElementAddOne, eCFieldElementMultiply3), new ECFieldElement[]{eCFieldElementMultiply3});
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
