package com.android.internal.org.bouncycastle.jce.spec;

import com.android.internal.org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class ECPublicKeySpec extends ECKeySpec {
    private ECPoint q;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        if (eCPoint.getCurve() != null) {
            this.q = eCPoint.normalize();
        } else {
            this.q = eCPoint;
        }
    }

    public ECPoint getQ() {
        return this.q;
    }
}
