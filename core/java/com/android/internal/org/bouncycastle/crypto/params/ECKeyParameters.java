package com.android.internal.org.bouncycastle.crypto.params;

public class ECKeyParameters extends AsymmetricKeyParameter {
    private final ECDomainParameters parameters;

    protected ECKeyParameters(boolean isPrivate, ECDomainParameters parameters) {
        super(isPrivate);
        if (parameters == null) {
            throw new NullPointerException("'parameters' cannot be null");
        }
        this.parameters = parameters;
    }

    public ECDomainParameters getParameters() {
        return this.parameters;
    }
}
