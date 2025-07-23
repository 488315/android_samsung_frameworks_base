package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DHPrivateKeyParameters extends DHKeyParameters {
    private BigInteger x;

    public DHPrivateKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(true, dHParameters);
        this.x = bigInteger;
    }

    public BigInteger getX() {
        return this.x;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return super.hashCode() ^ this.x.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof DHPrivateKeyParameters) && ((DHPrivateKeyParameters) obj).getX().equals(this.x) && super.equals(obj);
    }
}
