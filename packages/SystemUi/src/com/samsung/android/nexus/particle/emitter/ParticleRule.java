package com.samsung.android.nexus.particle.emitter;

import android.view.animation.Interpolator;
import com.samsung.android.nexus.base.utils.keyFrameSet.FloatKeyFrameSet;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import com.samsung.android.nexus.particle.emitter.ParticleConfigType;
import com.samsung.android.nexus.particle.emitter.texture.ParticleTexture;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ParticleRule {
    public final boolean[] applyWorldFactorCheckList;
    public int colorMode;
    public final boolean[] configValues;
    public final FactorRangeableKeyFrameSetList factorKeyFrameList;
    public final FactorRangeableList factorRangeableList;
    public long lastWorldFactorUpdateTime;
    public final LongRangeable lifeTime;
    public ParticleTexture particleTexture;
    public int posMode;
    public int scaleMode;
    public final float[] tempWorldFactorValues;

    public ParticleRule() {
        this.lifeTime = new LongRangeable(500L);
        this.posMode = 10;
        this.scaleMode = 0;
        this.colorMode = 10;
        this.factorRangeableList = new FactorRangeableList();
        this.factorKeyFrameList = new FactorRangeableKeyFrameSetList();
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount * 3;
        this.applyWorldFactorCheckList = new boolean[i];
        this.lastWorldFactorUpdateTime = -1L;
        this.tempWorldFactorValues = new float[i];
        ParticleConfigType particleConfigType = ParticleConfigType.DISABLE_WHEN_DISAPPEARED;
        int i2 = ParticleConfigType.Holder.sCount;
        boolean[] zArr = new boolean[i2];
        ParticleConfigType[] particleConfigTypeArr = ParticleConfigType.Holder.sValuesCache;
        for (int i3 = 0; i3 < i2; i3++) {
            zArr[i3] = particleConfigTypeArr[i3].defaultValue;
        }
        this.configValues = zArr;
        Arrays.fill(this.applyWorldFactorCheckList, false);
    }

    public final Object clone() {
        return new ParticleRule(this);
    }

    public final void setAccelerationRange(FactorType factorType, float f, float f2) {
        FloatRangeable floatRangeable = this.factorRangeableList.rangeables[factorType.accelerationIdx];
        floatRangeable.mMin = f;
        floatRangeable.mMax = f2;
        floatRangeable.onRangeUpdated();
    }

    public final void setKeyFrameListRange(FactorType factorType, FloatRangeable[] floatRangeableArr, FloatRangeable[] floatRangeableArr2) {
        FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList = this.factorKeyFrameList;
        factorRangeableKeyFrameSetList.getClass();
        int i = factorType.idx;
        if (floatRangeableArr.length != floatRangeableArr2.length) {
            throw new IllegalArgumentException("different length");
        }
        int length = floatRangeableArr.length;
        float f = floatRangeableArr[0].mMax;
        FloatRangeable[] floatRangeableArr3 = new FloatRangeable[length];
        FloatRangeable[] floatRangeableArr4 = new FloatRangeable[length];
        for (int i2 = 0; i2 < length; i2++) {
            FloatRangeable floatRangeable = floatRangeableArr[i2];
            floatRangeable.getClass();
            FloatRangeable floatRangeable2 = new FloatRangeable(floatRangeable);
            if (i2 > 0 && floatRangeable2.mMin < f) {
                throw new IllegalArgumentException("position range overlapped. it will occurs non-Ascending positions");
            }
            f = floatRangeable2.mMax;
            floatRangeableArr3[i2] = floatRangeable2;
            FloatRangeable floatRangeable3 = floatRangeableArr2[i2];
            floatRangeable3.getClass();
            floatRangeableArr4[i2] = new FloatRangeable(floatRangeable3);
        }
        FloatRangeable[][] floatRangeableArr5 = factorRangeableKeyFrameSetList.mFactorRangeablePositions;
        if (floatRangeableArr5[i] == null) {
            factorRangeableKeyFrameSetList.rangeableSize++;
        }
        floatRangeableArr5[i] = floatRangeableArr3;
        factorRangeableKeyFrameSetList.mFactorRangeableValues[i] = floatRangeableArr4;
        factorRangeableKeyFrameSetList.mFactorInterpolator[i] = null;
        factorRangeableKeyFrameSetList.mFactorInterpolators[i] = null;
    }

    public final void setSpeedRange(FactorType factorType, float f, float f2) {
        FloatRangeable floatRangeable = this.factorRangeableList.rangeables[factorType.speedIdx];
        floatRangeable.mMin = f;
        floatRangeable.mMax = f2;
        floatRangeable.onRangeUpdated();
    }

    public final void setValueRange(FactorType factorType, float f, float f2) {
        FloatRangeable floatRangeable = this.factorRangeableList.rangeables[factorType.valueIdx];
        floatRangeable.mMin = f;
        floatRangeable.mMax = f2;
        floatRangeable.onRangeUpdated();
    }

    public ParticleRule(ParticleRule particleRule) {
        LongRangeable longRangeable = new LongRangeable(500L);
        this.lifeTime = longRangeable;
        this.posMode = 10;
        this.scaleMode = 0;
        this.colorMode = 10;
        FactorRangeableList factorRangeableList = new FactorRangeableList();
        this.factorRangeableList = factorRangeableList;
        FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList = new FactorRangeableKeyFrameSetList();
        this.factorKeyFrameList = factorRangeableKeyFrameSetList;
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount * 3;
        boolean[] zArr = new boolean[i];
        this.applyWorldFactorCheckList = zArr;
        this.lastWorldFactorUpdateTime = -1L;
        this.tempWorldFactorValues = new float[i];
        ParticleConfigType particleConfigType = ParticleConfigType.DISABLE_WHEN_DISAPPEARED;
        int i2 = ParticleConfigType.Holder.sCount;
        boolean[] zArr2 = new boolean[i2];
        ParticleConfigType[] particleConfigTypeArr = ParticleConfigType.Holder.sValuesCache;
        for (int i3 = 0; i3 < i2; i3++) {
            zArr2[i3] = particleConfigTypeArr[i3].defaultValue;
        }
        this.configValues = zArr2;
        Arrays.fill(this.applyWorldFactorCheckList, false);
        if (particleRule == null) {
            return;
        }
        this.particleTexture = particleRule.particleTexture;
        FactorRangeableKeyFrameSetList factorRangeableKeyFrameSetList2 = particleRule.factorKeyFrameList;
        FloatKeyFrameSet[] floatKeyFrameSetArr = factorRangeableKeyFrameSetList2.list;
        int length = floatKeyFrameSetArr.length;
        factorRangeableKeyFrameSetList.floatKeyFrameSetSize = factorRangeableKeyFrameSetList2.floatKeyFrameSetSize;
        System.arraycopy(floatKeyFrameSetArr, 0, factorRangeableKeyFrameSetList.list, 0, length);
        FloatRangeable[][] floatRangeableArr = factorRangeableKeyFrameSetList.mFactorRangeablePositions;
        System.arraycopy(factorRangeableKeyFrameSetList2.mFactorRangeablePositions, 0, floatRangeableArr, 0, floatRangeableArr.length);
        FloatRangeable[][] floatRangeableArr2 = factorRangeableKeyFrameSetList.mFactorRangeableValues;
        System.arraycopy(factorRangeableKeyFrameSetList2.mFactorRangeableValues, 0, floatRangeableArr2, 0, floatRangeableArr2.length);
        Interpolator[] interpolatorArr = factorRangeableKeyFrameSetList.mFactorInterpolator;
        System.arraycopy(factorRangeableKeyFrameSetList2.mFactorInterpolator, 0, interpolatorArr, 0, interpolatorArr.length);
        Interpolator[][] interpolatorArr2 = factorRangeableKeyFrameSetList.mFactorInterpolators;
        System.arraycopy(factorRangeableKeyFrameSetList2.mFactorInterpolators, 0, interpolatorArr2, 0, interpolatorArr2.length);
        factorRangeableKeyFrameSetList.rangeableSize = factorRangeableKeyFrameSetList2.rangeableSize;
        LongRangeable longRangeable2 = particleRule.lifeTime;
        longRangeable.mMin = longRangeable2.mMin;
        longRangeable.mMax = longRangeable2.mMax;
        longRangeable.onRangeUpdated();
        FloatRangeable[] floatRangeableArr3 = particleRule.factorRangeableList.rangeables;
        int length2 = floatRangeableArr3.length;
        for (int i4 = 0; i4 < length2; i4++) {
            FloatRangeable floatRangeable = floatRangeableArr3[i4];
            floatRangeable.getClass();
            factorRangeableList.rangeables[i4] = new FloatRangeable(floatRangeable);
        }
        this.posMode = particleRule.posMode;
        this.scaleMode = particleRule.scaleMode;
        this.colorMode = particleRule.colorMode;
        System.arraycopy(particleRule.applyWorldFactorCheckList, 0, zArr, 0, i);
        System.arraycopy(particleRule.configValues, 0, zArr2, 0, i2);
    }
}
