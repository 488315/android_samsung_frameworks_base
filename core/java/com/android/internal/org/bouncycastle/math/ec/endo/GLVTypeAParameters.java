package com.android.internal.org.bouncycastle.math.ec.endo;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class GLVTypeAParameters {
    protected final BigInteger i;
    protected final BigInteger lambda;
    protected final ScalarSplitParameters splitParams;

    public GLVTypeAParameters(BigInteger i, BigInteger lambda, ScalarSplitParameters splitParams) {
        this.i = i;
        this.lambda = lambda;
        this.splitParams = splitParams;
    }

    public BigInteger getI() {
        return this.i;
    }

    public BigInteger getLambda() {
        return this.lambda;
    }

    public ScalarSplitParameters getSplitParams() {
        return this.splitParams;
    }
}
