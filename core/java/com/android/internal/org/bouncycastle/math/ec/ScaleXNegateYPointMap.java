package com.android.internal.org.bouncycastle.math.ec;

public class ScaleXNegateYPointMap implements ECPointMap {
    protected final ECFieldElement scale;

    public ScaleXNegateYPointMap(ECFieldElement scale) {
        this.scale = scale;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPointMap
    public ECPoint map(ECPoint p) {
        return p.scaleXNegateY(this.scale);
    }
}
