package com.samsung.sesl.compose.foundation;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslRecoilParameter {
    public final SeslRecoilDrawStrategy drawStrategy;
    public final float scaleRatio;

    public SeslRecoilParameter(float f, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        this.scaleRatio = f;
        this.drawStrategy = seslRecoilDrawStrategy;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslRecoilParameter)) {
            return false;
        }
        SeslRecoilParameter seslRecoilParameter = (SeslRecoilParameter) obj;
        return Float.compare(this.scaleRatio, seslRecoilParameter.scaleRatio) == 0 && Intrinsics.areEqual(this.drawStrategy, seslRecoilParameter.drawStrategy);
    }

    public final int hashCode() {
        return this.drawStrategy.hashCode() + (Float.hashCode(this.scaleRatio) * 31);
    }

    public final String toString() {
        return "SeslRecoilParameter(scaleRatio=" + this.scaleRatio + ", drawStrategy=" + this.drawStrategy + ")";
    }
}
