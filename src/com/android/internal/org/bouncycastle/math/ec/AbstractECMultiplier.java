package com.android.internal.org.bouncycastle.math.ec;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public abstract class AbstractECMultiplier implements ECMultiplier {
    protected abstract ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger);

    @Override // com.android.internal.org.bouncycastle.math.ec.ECMultiplier
    public ECPoint multiply(ECPoint eCPoint, BigInteger bigInteger) {
        int signum = bigInteger.signum();
        if (signum == 0 || eCPoint.isInfinity()) {
            return eCPoint.getCurve().getInfinity();
        }
        ECPoint multiplyPositive = multiplyPositive(eCPoint, bigInteger.abs());
        if (signum <= 0) {
            multiplyPositive = multiplyPositive.negate();
        }
        return checkResult(multiplyPositive);
    }

    protected ECPoint checkResult(ECPoint eCPoint) {
        return ECAlgorithms.implCheckResult(eCPoint);
    }
}
