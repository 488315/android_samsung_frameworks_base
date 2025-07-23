package com.samsung.android.nexus.particle.emitter;

import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FactorRangeableList {
    public final FloatRangeable[] rangeables;

    public FactorRangeableList() {
        FactorType factorType = FactorType.WIDTH;
        FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
        FloatRangeable[] floatRangeableArr = new FloatRangeable[factorTypeArr.length * 3];
        for (FactorType factorType2 : factorTypeArr) {
            floatRangeableArr[factorType2.valueIdx] = new FloatRangeable(factorType2.min, factorType2.max);
            floatRangeableArr[factorType2.speedIdx] = new FloatRangeable(0.0f);
            floatRangeableArr[factorType2.accelerationIdx] = new FloatRangeable(0.0f);
        }
        this.rangeables = floatRangeableArr;
    }

    public final Object clone() {
        return new FactorRangeableList(this);
    }

    public final void setAcceleration(FactorType factorType) {
        FloatRangeable floatRangeable = this.rangeables[factorType.accelerationIdx];
        floatRangeable.mMin = 0.0f;
        floatRangeable.mMax = 0.0f;
        floatRangeable.onRangeUpdated();
    }

    public final void setSpeed(FactorType factorType) {
        FloatRangeable floatRangeable = this.rangeables[factorType.speedIdx];
        floatRangeable.mMin = 0.0f;
        floatRangeable.mMax = 0.0f;
        floatRangeable.onRangeUpdated();
    }

    public final void setValue(FactorType factorType, float f) {
        FloatRangeable floatRangeable = this.rangeables[factorType.valueIdx];
        floatRangeable.mMin = f;
        floatRangeable.mMax = f;
        floatRangeable.onRangeUpdated();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FactorRangeableList{ranges=");
        FactorType factorType = FactorType.WIDTH;
        FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
        int length = factorTypeArr.length;
        for (int i = 0; i < length; i++) {
            FactorType factorType2 = factorTypeArr[i];
            Locale locale = Locale.ENGLISH;
            Integer valueOf = Integer.valueOf(i);
            String name = factorType2.name();
            int i2 = factorType2.valueIdx;
            FloatRangeable[] floatRangeableArr = this.rangeables;
            sb.append(String.format(locale, "\n#%d: %s: val = %f / spd = %f / acc = %f", valueOf, name, floatRangeableArr[i2], floatRangeableArr[factorType2.speedIdx], floatRangeableArr[factorType2.accelerationIdx]));
            sb.append("}");
        }
        return sb.toString();
    }

    public FactorRangeableList(FactorRangeableList factorRangeableList) {
        this.rangeables = new FloatRangeable[factorRangeableList.rangeables.length];
        FloatRangeable[] floatRangeableArr = factorRangeableList.rangeables;
        int length = floatRangeableArr.length;
        for (int i = 0; i < length; i++) {
            FloatRangeable floatRangeable = floatRangeableArr[i];
            floatRangeable.getClass();
            this.rangeables[i] = new FloatRangeable(floatRangeable);
        }
    }
}
