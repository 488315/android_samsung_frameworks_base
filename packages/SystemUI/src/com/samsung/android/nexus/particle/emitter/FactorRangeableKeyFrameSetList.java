package com.samsung.android.nexus.particle.emitter;

import android.view.animation.Interpolator;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FactorRangeableKeyFrameSetList extends FactorKeyFrameSetList {
    public final Interpolator[] mFactorInterpolator;
    public final Interpolator[][] mFactorInterpolators;
    public final FloatRangeable[][] mFactorRangeablePositions;
    public final FloatRangeable[][] mFactorRangeableValues;
    public int rangeableSize = 0;

    public FactorRangeableKeyFrameSetList() {
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount;
        this.mFactorRangeablePositions = new FloatRangeable[i][];
        this.mFactorRangeableValues = new FloatRangeable[i][];
        this.mFactorInterpolator = new Interpolator[i];
        this.mFactorInterpolators = new Interpolator[i][];
    }

    @Override // com.samsung.android.nexus.particle.emitter.FactorKeyFrameSetList
    public final void clear() {
        super.clear();
        FloatRangeable[][] floatRangeableArr = this.mFactorRangeablePositions;
        for (FloatRangeable[] floatRangeableArr2 : floatRangeableArr) {
            if (floatRangeableArr2 != null) {
                Arrays.fill(floatRangeableArr2, (Object) null);
            }
        }
        Arrays.fill(floatRangeableArr, (Object) null);
        FloatRangeable[][] floatRangeableArr3 = this.mFactorRangeableValues;
        for (FloatRangeable[] floatRangeableArr4 : floatRangeableArr3) {
            if (floatRangeableArr4 != null) {
                Arrays.fill(floatRangeableArr4, (Object) null);
            }
        }
        Arrays.fill(floatRangeableArr3, (Object) null);
        Arrays.fill(this.mFactorInterpolator, (Object) null);
        for (Interpolator[] interpolatorArr : this.mFactorInterpolators) {
            if (interpolatorArr != null) {
                Arrays.fill(interpolatorArr, (Object) null);
            }
        }
        Arrays.fill(this.mFactorInterpolators, (Object) null);
        this.rangeableSize = 0;
    }

    @Override // com.samsung.android.nexus.particle.emitter.FactorKeyFrameSetList
    public final boolean isEmpty() {
        return this.floatKeyFrameSetSize == 0 && this.rangeableSize == 0;
    }
}
