package com.samsung.android.nexus.particle.emitter;

import android.view.animation.Interpolator;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FactorRangeableKeyFrameSetList extends FactorKeyFrameSetList {
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
        Arrays.fill(this.mFactorRangeablePositions, (Object) null);
        Arrays.fill(this.mFactorRangeableValues, (Object) null);
        Arrays.fill(this.mFactorInterpolator, (Object) null);
        Arrays.fill(this.mFactorInterpolators, (Object) null);
        this.rangeableSize = 0;
    }

    @Override // com.samsung.android.nexus.particle.emitter.FactorKeyFrameSetList
    public final boolean isEmpty() {
        return this.floatKeyFrameSetSize == 0 && this.rangeableSize == 0;
    }
}
