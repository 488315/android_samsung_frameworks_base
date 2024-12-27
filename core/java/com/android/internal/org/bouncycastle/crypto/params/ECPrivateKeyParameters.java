package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ECPrivateKeyParameters extends ECKeyParameters {
    private final BigInteger d;

    public ECPrivateKeyParameters(BigInteger d, ECDomainParameters parameters) {
        super(true, parameters);
        this.d = parameters.validatePrivateScalar(d);
    }

    public BigInteger getD() {
        return this.d;
    }
}
