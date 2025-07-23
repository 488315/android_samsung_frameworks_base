package com.android.internal.org.bouncycastle.asn1.x9;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;

/* loaded from: classes5.dex */
public abstract class X9ECParametersHolder {
    private ECCurve curve;
    private X9ECParameters params;

    protected abstract X9ECParameters createParameters();

    public synchronized ECCurve getCurve() {
        if (this.curve == null) {
            this.curve = createCurve();
        }
        return this.curve;
    }

    public synchronized X9ECParameters getParameters() {
        if (this.params == null) {
            this.params = createParameters();
        }
        return this.params;
    }

    protected ECCurve createCurve() {
        return createParameters().getCurve();
    }
}
