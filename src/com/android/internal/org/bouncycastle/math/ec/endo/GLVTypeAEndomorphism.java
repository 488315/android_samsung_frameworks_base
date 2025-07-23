package com.android.internal.org.bouncycastle.math.ec.endo;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECPointMap;
import com.android.internal.org.bouncycastle.math.ec.ScaleYNegateXPointMap;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class GLVTypeAEndomorphism implements GLVEndomorphism {
    protected final GLVTypeAParameters parameters;
    protected final ECPointMap pointMap;

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism
    public boolean hasEfficientPointMap() {
        return true;
    }

    public GLVTypeAEndomorphism(ECCurve eCCurve, GLVTypeAParameters gLVTypeAParameters) {
        this.parameters = gLVTypeAParameters;
        this.pointMap = new ScaleYNegateXPointMap(eCCurve.fromBigInteger(gLVTypeAParameters.getI()));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism
    public BigInteger[] decomposeScalar(BigInteger bigInteger) {
        return EndoUtil.decomposeScalar(this.parameters.getSplitParams(), bigInteger);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism
    public ECPointMap getPointMap() {
        return this.pointMap;
    }
}
