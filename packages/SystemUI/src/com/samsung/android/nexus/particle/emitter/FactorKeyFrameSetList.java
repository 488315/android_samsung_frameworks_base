package com.samsung.android.nexus.particle.emitter;

import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.particle.emitter.FactorType;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FactorKeyFrameSetList {
    public int floatKeyFrameSetSize;
    public final FloatKeyFrameSet[] list;

    public FactorKeyFrameSetList() {
        FactorType factorType = FactorType.WIDTH;
        this.list = new FloatKeyFrameSet[FactorType.Holder.sCount];
        this.floatKeyFrameSetSize = 0;
    }

    public void clear() {
        Arrays.fill(this.list, (Object) null);
        this.floatKeyFrameSetSize = 0;
    }

    public boolean isEmpty() {
        return this.floatKeyFrameSetSize == 0;
    }

    public FactorKeyFrameSetList(FactorKeyFrameSetList factorKeyFrameSetList) {
        FactorType factorType = FactorType.WIDTH;
        FloatKeyFrameSet[] floatKeyFrameSetArr = new FloatKeyFrameSet[FactorType.Holder.sCount];
        this.list = floatKeyFrameSetArr;
        this.floatKeyFrameSetSize = 0;
        FloatKeyFrameSet[] floatKeyFrameSetArr2 = factorKeyFrameSetList.list;
        int length = floatKeyFrameSetArr2.length;
        this.floatKeyFrameSetSize = factorKeyFrameSetList.floatKeyFrameSetSize;
        System.arraycopy(floatKeyFrameSetArr2, 0, floatKeyFrameSetArr, 0, length);
    }
}
