package com.android.internal.org.bouncycastle.math.ec;

public class ScaleXPointMap implements ECPointMap {
    protected final ECFieldElement scale;

    public ScaleXPointMap(ECFieldElement scale) {
        this.scale = scale;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPointMap
    public ECPoint map(ECPoint p) {
        return p.scaleX(this.scale);
    }
}
