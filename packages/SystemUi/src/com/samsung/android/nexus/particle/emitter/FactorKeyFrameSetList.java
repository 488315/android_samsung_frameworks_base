package com.samsung.android.nexus.particle.emitter;

import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.particle.emitter.FactorType;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
