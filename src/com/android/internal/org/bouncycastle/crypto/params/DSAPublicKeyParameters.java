package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DSAPublicKeyParameters extends DSAKeyParameters {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private BigInteger y;

    public DSAPublicKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(false, dSAParameters);
        this.y = validate(bigInteger, dSAParameters);
    }

    private BigInteger validate(BigInteger bigInteger, DSAParameters dSAParameters) {
        if (dSAParameters != null) {
            BigInteger bigInteger2 = TWO;
            if (bigInteger2.compareTo(bigInteger) > 0 || dSAParameters.getP().subtract(bigInteger2).compareTo(bigInteger) < 0 || !ONE.equals(bigInteger.modPow(dSAParameters.getQ(), dSAParameters.getP()))) {
                throw new IllegalArgumentException("y value does not appear to be in correct group");
            }
        }
        return bigInteger;
    }

    public BigInteger getY() {
        return this.y;
    }
}
