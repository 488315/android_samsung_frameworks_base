package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes5.dex */
public class ECKeyParameters extends AsymmetricKeyParameter {
    private final ECDomainParameters parameters;

    protected ECKeyParameters(boolean z, ECDomainParameters eCDomainParameters) {
        super(z);
        if (eCDomainParameters == null) {
            throw new NullPointerException("'parameters' cannot be null");
        }
        this.parameters = eCDomainParameters;
    }

    public ECDomainParameters getParameters() {
        return this.parameters;
    }
}
