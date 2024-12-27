package com.android.internal.org.bouncycastle.math.ec;

public class ScaleYNegateXPointMap implements ECPointMap {
    protected final ECFieldElement scale;

    public ScaleYNegateXPointMap(ECFieldElement scale) {
        this.scale = scale;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPointMap
    public ECPoint map(ECPoint p) {
        return p.scaleYNegateX(this.scale);
    }
}
