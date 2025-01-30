package com.android.internal.org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ECPrivateKeySpec extends ECKeySpec {

    /* renamed from: d */
    private BigInteger f924d;

    public ECPrivateKeySpec(BigInteger d, ECParameterSpec spec) {
        super(spec);
        this.f924d = d;
    }

    public BigInteger getD() {
        return this.f924d;
    }
}
