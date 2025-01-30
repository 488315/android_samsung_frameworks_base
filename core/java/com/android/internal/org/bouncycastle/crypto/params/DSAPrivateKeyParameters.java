package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DSAPrivateKeyParameters extends DSAKeyParameters {

    /* renamed from: x */
    private BigInteger f876x;

    public DSAPrivateKeyParameters(BigInteger x, DSAParameters params) {
        super(true, params);
        this.f876x = x;
    }

    public BigInteger getX() {
        return this.f876x;
    }
}
