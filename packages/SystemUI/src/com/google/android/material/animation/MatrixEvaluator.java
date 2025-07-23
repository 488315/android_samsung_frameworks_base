package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MatrixEvaluator implements TypeEvaluator {
    public final float[] tempStartValues = new float[9];
    public final float[] tempEndValues = new float[9];
    public final Matrix tempMatrix = new Matrix();

    @Override // android.animation.TypeEvaluator
    public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
        matrix.getValues(this.tempStartValues);
        matrix2.getValues(this.tempEndValues);
        for (int i = 0; i < 9; i++) {
            float[] fArr = this.tempEndValues;
            float f2 = fArr[i];
            float f3 = this.tempStartValues[i];
            fArr[i] = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f3, f, f3);
        }
        this.tempMatrix.setValues(this.tempEndValues);
        return this.tempMatrix;
    }
}
