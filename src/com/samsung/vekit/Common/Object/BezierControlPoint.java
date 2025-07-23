package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class BezierControlPoint {
    private float controlPointX1;
    private float controlPointX2;
    private float controlPointY1;
    private float controlPointY2;

    public BezierControlPoint() {
        setValues(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public BezierControlPoint(float f, float f2, float f3, float f4) {
        setValues(f, f2, f3, f4);
    }

    public void setValues(float f, float f2, float f3, float f4) {
        this.controlPointX1 = f;
        this.controlPointX2 = f3;
        this.controlPointY1 = f2;
        this.controlPointY2 = f4;
    }

    public float getControlPointX1() {
        return this.controlPointX1;
    }

    public float getControlPointY1() {
        return this.controlPointY1;
    }

    public float getControlPointX2() {
        return this.controlPointX2;
    }

    public float getControlPointY2() {
        return this.controlPointY2;
    }
}
