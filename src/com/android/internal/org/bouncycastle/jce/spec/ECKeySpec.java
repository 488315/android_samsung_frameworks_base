package com.android.internal.org.bouncycastle.jce.spec;

import java.security.spec.KeySpec;

/* loaded from: classes5.dex */
public class ECKeySpec implements KeySpec {
    private ECParameterSpec spec;

    protected ECKeySpec(ECParameterSpec eCParameterSpec) {
        this.spec = eCParameterSpec;
    }

    public ECParameterSpec getParams() {
        return this.spec;
    }
}
