package com.samsung.android.nexus.particle.emitter;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.IntRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.EmissionConfigType;
import com.samsung.android.nexus.particle.emitter.FactorType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EmissionRule {
    public final boolean[] applyParentFactorCheckList;
    public FloatRangeable beginFraction;
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
    public int mDirectionMode;
    public final Matrix mMatrix;
    public final Path mRotatedEmitterShapePath;
    public int mShapeScaleMode;
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
        this.mDirectionMode = 0;
        this.mShapeScaleMode = 0;
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
        this.applyParentFactorCheckList = new boolean[FactorType.Holder.sCount * 3];
        EmissionConfigType emissionConfigType = EmissionConfigType.APPLY_PARENT_ANGULAR_VELOCITY;
        int i = EmissionConfigType.Holder.sCount;
        boolean[] zArr = new boolean[i];
        EmissionConfigType[] emissionConfigTypeArr = EmissionConfigType.Holder.sValuesCache;
        for (int i2 = 0; i2 < i; i2++) {
            zArr[i2] = emissionConfigTypeArr[i2].defaultValue;
        }
        this.configValues = zArr;
        setShapeType();
        int i3 = FactorType.POS_X.valueIdx;
        boolean[] zArr2 = this.applyParentFactorCheckList;
        zArr2[i3] = true;
        zArr2[FactorType.POS_Y.valueIdx] = true;
    }

    public final Object clone() {
        return new EmissionRule(this);
    }

    public final void setShapeType() {
        this.emitterShapeBounds.set(-0.5f, -0.5f, 0.5f, 0.5f);
        Path path = this.emitterShapePath;
        path.reset();
        this.shapeBaseScale = 1.0f;
        path.moveTo(0.0f, 0.0f);
        path.close();
    }

    public EmissionRule(EmissionRule emissionRule) {
        this.mDirectionMode = 0;
        this.mShapeScaleMode = 0;
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
        EmissionConfigType emissionConfigType = EmissionConfigType.APPLY_PARENT_ANGULAR_VELOCITY;
        int i2 = EmissionConfigType.Holder.sCount;
        boolean[] zArr2 = new boolean[i2];
        EmissionConfigType[] emissionConfigTypeArr = EmissionConfigType.Holder.sValuesCache;
        int i3 = 0;
        while (i3 < i2) {
            zArr2[i3] = emissionConfigTypeArr[i3].defaultValue;
            i3++;
            i2 = i2;
        }
        int i4 = i2;
        this.configValues = zArr2;
        setShapeType();
        int i5 = FactorType.POS_X.valueIdx;
        boolean[] zArr3 = this.applyParentFactorCheckList;
        zArr3[i5] = true;
        zArr3[FactorType.POS_Y.valueIdx] = true;
        if (emissionRule == null) {
            return;
        }
        rectF.set(emissionRule.emitterShapeBounds);
        path.set(emissionRule.emitterShapePath);
        this.mShapeScaleMode = emissionRule.mShapeScaleMode;
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
        int i6 = intRangeable.mMax;
        this.pointerSize = i6;
        this.pathTanXArray = new float[i6];
        this.pathTanYArray = new float[i6];
        this.pathPointerOffsetXArray = new float[i6];
        this.pathPointerOffsetYArray = new float[i6];
        this.pathPointerVelocitiesX = new float[i6];
        this.pathPointerVelocitiesY = new float[i6];
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
        this.mDirectionMode = emissionRule.mDirectionMode;
        System.arraycopy(emissionRule.applyParentFactorCheckList, 0, zArr, 0, i);
        System.arraycopy(emissionRule.configValues, 0, zArr2, 0, i4);
    }
}
