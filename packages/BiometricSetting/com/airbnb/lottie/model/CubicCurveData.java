package com.airbnb.lottie.model;

import android.graphics.PointF;

/* loaded from: classes.dex */
public final class CubicCurveData {
    private final PointF controlPoint1;
    private final PointF controlPoint2;
    private final PointF vertex;

    public CubicCurveData() {
        this.controlPoint1 = new PointF();
        this.controlPoint2 = new PointF();
        this.vertex = new PointF();
    }

    public final PointF getControlPoint1() {
        return this.controlPoint1;
    }

    public final PointF getControlPoint2() {
        return this.controlPoint2;
    }

    public final PointF getVertex() {
        return this.vertex;
    }

    public final void setControlPoint1(float f, float f2) {
        this.controlPoint1.set(f, f2);
    }

    public final void setControlPoint2(float f, float f2) {
        this.controlPoint2.set(f, f2);
    }

    public final void setVertex(float f, float f2) {
        this.vertex.set(f, f2);
    }

    public CubicCurveData(PointF pointF, PointF pointF2, PointF pointF3) {
        this.controlPoint1 = pointF;
        this.controlPoint2 = pointF2;
        this.vertex = pointF3;
    }
}
