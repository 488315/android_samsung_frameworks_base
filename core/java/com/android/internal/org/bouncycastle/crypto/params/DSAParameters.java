package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DSAParameters implements CipherParameters {

    /* renamed from: g */
    private BigInteger f873g;

    /* renamed from: p */
    private BigInteger f874p;

    /* renamed from: q */
    private BigInteger f875q;
    private DSAValidationParameters validation;

    public DSAParameters(BigInteger p, BigInteger q, BigInteger g) {
        this.f873g = g;
        this.f874p = p;
        this.f875q = q;
    }

    public DSAParameters(BigInteger p, BigInteger q, BigInteger g, DSAValidationParameters params) {
        this.f873g = g;
        this.f874p = p;
        this.f875q = q;
        this.validation = params;
    }

    public BigInteger getP() {
        return this.f874p;
    }

    public BigInteger getQ() {
        return this.f875q;
    }

    public BigInteger getG() {
        return this.f873g;
    }

    public DSAValidationParameters getValidationParameters() {
        return this.validation;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAParameters)) {
            return false;
        }
        DSAParameters pm = (DSAParameters) obj;
        return pm.getP().equals(this.f874p) && pm.getQ().equals(this.f875q) && pm.getG().equals(this.f873g);
    }

    public int hashCode() {
        return (getP().hashCode() ^ getQ().hashCode()) ^ getG().hashCode();
    }
}
