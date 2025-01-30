package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class RSAPrivateCrtKeyParameters extends RSAKeyParameters {

    /* renamed from: dP */
    private BigInteger f886dP;

    /* renamed from: dQ */
    private BigInteger f887dQ;

    /* renamed from: e */
    private BigInteger f888e;

    /* renamed from: p */
    private BigInteger f889p;

    /* renamed from: q */
    private BigInteger f890q;
    private BigInteger qInv;

    public RSAPrivateCrtKeyParameters(BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent, BigInteger p, BigInteger q, BigInteger dP, BigInteger dQ, BigInteger qInv) {
        super(true, modulus, privateExponent);
        this.f888e = publicExponent;
        this.f889p = p;
        this.f890q = q;
        this.f886dP = dP;
        this.f887dQ = dQ;
        this.qInv = qInv;
    }

    public BigInteger getPublicExponent() {
        return this.f888e;
    }

    public BigInteger getP() {
        return this.f889p;
    }

    public BigInteger getQ() {
        return this.f890q;
    }

    public BigInteger getDP() {
        return this.f886dP;
    }

    public BigInteger getDQ() {
        return this.f887dQ;
    }

    public BigInteger getQInv() {
        return this.qInv;
    }
}
