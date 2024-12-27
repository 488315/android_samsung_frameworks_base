package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes5.dex */
public class DHKeyParameters extends AsymmetricKeyParameter {
    private DHParameters params;

    protected DHKeyParameters(boolean isPrivate, DHParameters params) {
        super(isPrivate);
        this.params = params;
    }

    public DHParameters getParameters() {
        return this.params;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHKeyParameters)) {
            return false;
        }
        DHKeyParameters dhKey = (DHKeyParameters) obj;
        if (this.params == null) {
            return dhKey.getParameters() == null;
        }
        return this.params.equals(dhKey.getParameters());
    }

    public int hashCode() {
        int i = !isPrivate() ? 1 : 0;
        if (this.params != null) {
            return i ^ this.params.hashCode();
        }
        return i;
    }
}
