package com.samsung.android.nexus.particle.emitter;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.IntRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.EmissionConfigType;
import com.samsung.android.nexus.particle.emitter.FactorType;

/* loaded from: classes4.dex */
public class EmissionRule {
    public final int[] applyParentFactorCheckIndexList;
    public final boolean[] applyParentFactorCheckList;
    public int applyParentFactorCheckListCount;
    public final FloatRangeable beginFraction;
    public final LongRangeable beginTime;
    public final boolean[] configValues;
    public final FloatRangeable emissionAngle;
    public final IntRangeable emitterCellCount;
    public final FloatRangeable emitterCellOffset;
    public final RectF emitterShapeBounds;
    public final Path emitterShapePath;
    public final FloatRangeable initialVelocity;
    public FloatRangeable intervalFraction;
    public final LongRangeable intervalTime;
    public final Matrix mMatrix;
    public final Path mRotatedEmitterShapePath;
    public float[] pathPointerOffsetXArray;
    public float[] pathPointerOffsetYArray;
    public float[] pathPointerVelocitiesX;
    public float[] pathPointerVelocitiesY;
    public float[] pathTanXArray;
    public float[] pathTanYArray;
    public int pointerSize;
    public float shapeBaseScale;
    public final FloatRangeable shapeScale;
    public final FloatRangeable shapeScaleX;
    public final FloatRangeable shapeScaleY;

    public EmissionRule() {
        this.mRotatedEmitterShapePath = new Path();
        this.mMatrix = new Matrix();
        this.emitterShapeBounds = new RectF();
        this.emitterShapePath = new Path();
        this.shapeScale = new FloatRangeable(1.0f);
        this.shapeScaleX = new FloatRangeable(1.0f);
        this.shapeScaleY = new FloatRangeable(1.0f);
        this.shapeBaseScale = 1.0f;
        this.pointerSize = 0;
        this.emitterCellCount = new IntRangeable(1);
        this.emitterCellOffset = new FloatRangeable(0.0f, 1.0f);
        this.initialVelocity = new FloatRangeable(0.0f);
        this.emissionAngle = new FloatRangeable(0.0f);
        this.beginTime = new LongRangeable(0L);
        this.beginFraction = null;
        this.intervalTime = new LongRangeable(1000L);
        this.intervalFraction = null;
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount * 3;
        this.applyParentFactorCheckList = new boolean[i];
        this.applyParentFactorCheckIndexList = new int[i];
        this.applyParentFactorCheckListCount = 0;
        EmissionConfigType emissionConfigType = EmissionConfigType.APPLY_PARENT_ANGULAR_VELOCITY;
        int i2 = EmissionConfigType.Holder.sCount;
        boolean[] zArr = new boolean[i2];
        EmissionConfigType[] emissionConfigTypeArr = EmissionConfigType.Holder.sValuesCache;
        for (int i3 = 0; i3 < i2; i3++) {
            zArr[i3] = emissionConfigTypeArr[i3].defaultValue;
        }
        this.configValues = zArr;
        setShapeType();
        applyParentFactorValue(ParentFactorType.POS_X);
        applyParentFactorValue(ParentFactorType.POS_Y);
    }

    public final void applyParentFactorValue(ParentFactorType parentFactorType) {
        int i = parentFactorType.factorType.valueIdx;
        boolean[] zArr = this.applyParentFactorCheckList;
        if (!zArr[i]) {
            zArr[i] = true;
            int i2 = this.applyParentFactorCheckListCount;
            this.applyParentFactorCheckListCount = i2 + 1;
            this.applyParentFactorCheckIndexList[i2] = i;
        }
    }

    public final Object clone() {
        return new EmissionRule(this);
    }

    public final void setShapeType() {
        this.emitterShapeBounds.set(-0.5f, -0.5f, 0.5f, 0.5f);
        this.emitterShapePath.reset();
        this.shapeBaseScale = 1.0f;
        this.emitterShapePath.moveTo(0.0f, 0.0f);
        this.emitterShapePath.close();
    }

    public EmissionRule(EmissionRule emissionRule) {
        this.mRotatedEmitterShapePath = new Path();
        this.mMatrix = new Matrix();
        RectF rectF = new RectF();
        this.emitterShapeBounds = rectF;
        Path path = new Path();
        this.emitterShapePath = path;
        FloatRangeable floatRangeable = new FloatRangeable(1.0f);
        this.shapeScale = floatRangeable;
        FloatRangeable floatRangeable2 = new FloatRangeable(1.0f);
        this.shapeScaleX = floatRangeable2;
        FloatRangeable floatRangeable3 = new FloatRangeable(1.0f);
        this.shapeScaleY = floatRangeable3;
        this.shapeBaseScale = 1.0f;
        this.pointerSize = 0;
        IntRangeable intRangeable = new IntRangeable(1);
        this.emitterCellCount = intRangeable;
        FloatRangeable floatRangeable4 = new FloatRangeable(0.0f, 1.0f);
        this.emitterCellOffset = floatRangeable4;
        FloatRangeable floatRangeable5 = new FloatRangeable(0.0f);
        this.initialVelocity = floatRangeable5;
        FloatRangeable floatRangeable6 = new FloatRangeable(0.0f);
        this.emissionAngle = floatRangeable6;
        LongRangeable longRangeable = new LongRangeable(0L);
        this.beginTime = longRangeable;
        this.beginFraction = null;
        LongRangeable longRangeable2 = new LongRangeable(1000L);
        this.intervalTime = longRangeable2;
        this.intervalFraction = null;
        FactorType factorType = FactorType.WIDTH;
        int i = FactorType.Holder.sCount * 3;
        boolean[] zArr = new boolean[i];
        this.applyParentFactorCheckList = zArr;
        int[] iArr = new int[i];
        this.applyParentFactorCheckIndexList = iArr;
        this.applyParentFactorCheckListCount = 0;
        EmissionConfigType emissionConfigType = EmissionConfigType.APPLY_PARENT_ANGULAR_VELOCITY;
        int i2 = EmissionConfigType.Holder.sCount;
        boolean[] zArr2 = new boolean[i2];
        EmissionConfigType[] emissionConfigTypeArr = EmissionConfigType.Holder.sValuesCache;
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i3;
            zArr2[i4] = emissionConfigTypeArr[i4].defaultValue;
            i3 = i4 + 1;
        }
        this.configValues = zArr2;
        setShapeType();
        applyParentFactorValue(ParentFactorType.POS_X);
        applyParentFactorValue(ParentFactorType.POS_Y);
        if (emissionRule == null) {
            return;
        }
        rectF.set(emissionRule.emitterShapeBounds);
        path.set(emissionRule.emitterShapePath);
        floatRangeable.set(emissionRule.shapeScale);
        floatRangeable2.set(emissionRule.shapeScaleX);
        floatRangeable3.set(emissionRule.shapeScaleY);
        this.shapeBaseScale = emissionRule.shapeBaseScale;
        IntRangeable intRangeable2 = emissionRule.emitterCellCount;
        intRangeable.mMin = intRangeable2.mMin;
        intRangeable.mMax = intRangeable2.mMax;
        intRangeable.onRangeUpdated();
        floatRangeable4.set(emissionRule.emitterCellOffset);
        floatRangeable6.set(emissionRule.emissionAngle);
        floatRangeable5.set(emissionRule.initialVelocity);
        int i5 = intRangeable.mMax;
        this.pointerSize = i5;
        this.pathTanXArray = new float[i5];
        this.pathTanYArray = new float[i5];
        this.pathPointerOffsetXArray = new float[i5];
        this.pathPointerOffsetYArray = new float[i5];
        this.pathPointerVelocitiesX = new float[i5];
        this.pathPointerVelocitiesY = new float[i5];
        LongRangeable longRangeable3 = emissionRule.beginTime;
        longRangeable.mMin = longRangeable3.mMin;
        longRangeable.mMax = longRangeable3.mMax;
        longRangeable.onRangeUpdated();
        LongRangeable longRangeable4 = emissionRule.intervalTime;
        longRangeable2.mMin = longRangeable4.mMin;
        longRangeable2.mMax = longRangeable4.mMax;
        longRangeable2.onRangeUpdated();
        FloatRangeable floatRangeable7 = emissionRule.beginFraction;
        if (floatRangeable7 != null) {
            this.beginFraction = new FloatRangeable(floatRangeable7);
        }
        FloatRangeable floatRangeable8 = emissionRule.intervalFraction;
        if (floatRangeable8 != null) {
            this.intervalFraction = new FloatRangeable(floatRangeable8);
        }
        System.arraycopy(emissionRule.applyParentFactorCheckList, 0, zArr, 0, i);
        System.arraycopy(emissionRule.applyParentFactorCheckIndexList, 0, iArr, 0, i);
        this.applyParentFactorCheckListCount = emissionRule.applyParentFactorCheckListCount;
        System.arraycopy(emissionRule.configValues, 0, zArr2, 0, i2);
    }
}
