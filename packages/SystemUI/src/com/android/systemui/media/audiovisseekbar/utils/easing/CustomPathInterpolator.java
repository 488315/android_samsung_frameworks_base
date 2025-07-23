package com.android.systemui.media.audiovisseekbar.utils.easing;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomPathInterpolator extends Path {
    public float pathLegth;
    public PathMeasure pathMeasure = new PathMeasure(this, false);
    public final float[] point = new float[2];
    public final int pointCount = 30;
    public final PointF[] samplingPoints;

    public CustomPathInterpolator() {
        PointF[] pointFArr = new PointF[30];
        for (int i = 0; i < 30; i++) {
            pointFArr[i] = new PointF();
        }
        this.samplingPoints = pointFArr;
    }

    public final void updatePath() {
        PathMeasure pathMeasure = new PathMeasure(this, false);
        this.pathLegth = pathMeasure.getLength();
        this.pathMeasure = pathMeasure;
        int i = this.pointCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.pathMeasure.getPosTan(this.pathLegth * (i2 / this.pointCount), this.point, null);
            PointF[] pointFArr = this.samplingPoints;
            float[] fArr = this.point;
            pointFArr[i2] = new PointF(fArr[0], fArr[1]);
        }
    }
}
