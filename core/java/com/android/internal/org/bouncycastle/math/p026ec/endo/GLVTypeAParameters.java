package com.android.internal.org.bouncycastle.math.p026ec.endo;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class GLVTypeAParameters {

    /* renamed from: i */
    protected final BigInteger f988i;
    protected final BigInteger lambda;
    protected final ScalarSplitParameters splitParams;

    public GLVTypeAParameters(BigInteger i, BigInteger lambda, ScalarSplitParameters splitParams) {
        this.f988i = i;
        this.lambda = lambda;
        this.splitParams = splitParams;
    }

    public BigInteger getI() {
        return this.f988i;
    }

    public BigInteger getLambda() {
        return this.lambda;
    }

    public ScalarSplitParameters getSplitParams() {
        return this.splitParams;
    }
}
