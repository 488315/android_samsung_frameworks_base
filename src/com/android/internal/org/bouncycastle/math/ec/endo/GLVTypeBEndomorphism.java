package com.android.internal.org.bouncycastle.math.ec.endo;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECPointMap;
import com.android.internal.org.bouncycastle.math.ec.ScaleXPointMap;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class GLVTypeBEndomorphism implements GLVEndomorphism {
    protected final GLVTypeBParameters parameters;
    protected final ECPointMap pointMap;

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism
    public boolean hasEfficientPointMap() {
        return true;
    }

    public GLVTypeBEndomorphism(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        this.parameters = gLVTypeBParameters;
        this.pointMap = new ScaleXPointMap(eCCurve.fromBigInteger(gLVTypeBParameters.getBeta()));
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
