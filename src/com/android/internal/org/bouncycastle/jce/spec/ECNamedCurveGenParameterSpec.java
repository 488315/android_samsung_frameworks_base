package com.android.internal.org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes5.dex */
public class ECNamedCurveGenParameterSpec implements AlgorithmParameterSpec {
    private String name;

    public ECNamedCurveGenParameterSpec(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
