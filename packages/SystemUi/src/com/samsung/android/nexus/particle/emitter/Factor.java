package com.samsung.android.nexus.particle.emitter;

import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import java.util.Arrays;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Factor {
    public boolean mNeedValidate;
    public final boolean[] mStepSkipList;
    public final float[] values;

    public Factor() {
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount;
        this.values = new float[i * 3];
        this.mNeedValidate = true;
        boolean[] zArr = new boolean[i];
        this.mStepSkipList = zArr;
        Arrays.fill(zArr, false);
        reset();
    }

    public final void initValues(FloatRangeable[] floatRangeableArr, FloatKeyFrameSet[] floatKeyFrameSetArr) {
        FactorType factorType = FactorType.WIDTH;
        FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
        int length = factorTypeArr.length;
        if (floatKeyFrameSetArr != null && (length != floatKeyFrameSetArr.length || length * 3 != floatRangeableArr.length)) {
            throw new IllegalArgumentException("wrong length");
        }
        int i = 0;
        float[] fArr = this.values;
        if (floatKeyFrameSetArr == null) {
            while (i < length) {
                FactorType factorType2 = factorTypeArr[i];
                int i2 = factorType2.valueIdx;
                fArr[i2] = floatRangeableArr[i2].get();
                int i3 = factorType2.speedIdx;
                fArr[i3] = floatRangeableArr[i3].get();
                int i4 = factorType2.accelerationIdx;
                fArr[i4] = floatRangeableArr[i4].get();
                i++;
            }
            return;
        }
        while (i < length) {
            FactorType factorType3 = factorTypeArr[i];
            FloatKeyFrameSet floatKeyFrameSet = floatKeyFrameSetArr[i];
            if (floatKeyFrameSet != null) {
                fArr[factorType3.valueIdx] = floatKeyFrameSet.get(0.0f);
                fArr[factorType3.speedIdx] = 0.0f;
                fArr[factorType3.accelerationIdx] = 0.0f;
            } else {
                int i5 = factorType3.valueIdx;
                fArr[i5] = floatRangeableArr[i5].get();
                int i6 = factorType3.speedIdx;
                fArr[i6] = floatRangeableArr[i6].get();
                int i7 = factorType3.accelerationIdx;
                fArr[i7] = floatRangeableArr[i7].get();
            }
            i++;
        }
    }

    public final void reset() {
        float[] fArr = this.values;
        Arrays.fill(fArr, 0.0f);
        fArr[FactorType.ALPHA.valueIdx] = 1.0f;
        fArr[FactorType.SCALE.valueIdx] = 1.0f;
        fArr[FactorType.SCALE_X.valueIdx] = 1.0f;
        fArr[FactorType.SCALE_Y.valueIdx] = 1.0f;
        FactorType factorType = FactorType.COLOR_ALPHA;
        fArr[factorType.valueIdx] = factorType.max;
        FactorType factorType2 = FactorType.COLOR_RED;
        fArr[factorType2.valueIdx] = factorType2.max;
        FactorType factorType3 = FactorType.COLOR_GREEN;
        fArr[factorType3.valueIdx] = factorType3.max;
        FactorType factorType4 = FactorType.COLOR_BLUE;
        fArr[factorType4.valueIdx] = factorType4.max;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FactorValueList{values=");
        FactorType factorType = FactorType.WIDTH;
        FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
        int length = factorTypeArr.length;
        for (int i = 0; i < length; i++) {
            FactorType factorType2 = factorTypeArr[i];
            Locale locale = Locale.ENGLISH;
            Integer valueOf = Integer.valueOf(i);
            String name = factorType2.name();
            int i2 = factorType2.valueIdx;
            float[] fArr = this.values;
            sb.append(String.format(locale, "\n#%d: %s: val = %f / spd = %f / acc = %f", valueOf, name, Float.valueOf(fArr[i2]), Float.valueOf(fArr[factorType2.speedIdx]), Float.valueOf(fArr[factorType2.accelerationIdx])));
            sb.append("}");
        }
        return sb.toString();
    }

    public final void validate() {
        FactorType factorType = FactorType.WIDTH;
        FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
        int length = factorTypeArr.length;
        for (int i = 0; i < length; i++) {
            FactorType factorType2 = factorTypeArr[i];
            int i2 = factorType2.speedIdx;
            float[] fArr = this.values;
            this.mStepSkipList[i] = 0.0f == fArr[i2] && 0.0f == fArr[factorType2.accelerationIdx];
        }
        this.mNeedValidate = false;
    }

    public Factor(FactorRangeableList factorRangeableList, FloatKeyFrameSet[] floatKeyFrameSetArr) {
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount;
        this.values = new float[i * 3];
        this.mNeedValidate = true;
        initValues(factorRangeableList.rangeables, floatKeyFrameSetArr);
        this.mStepSkipList = new boolean[i];
    }
}
