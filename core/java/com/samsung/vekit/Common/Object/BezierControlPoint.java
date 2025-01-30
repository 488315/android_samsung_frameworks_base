package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class BezierControlPoint {
    private float controlPointX1;
    private float controlPointX2;
    private float controlPointY1;
    private float controlPointY2;

    public BezierControlPoint() {
        setValues(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public BezierControlPoint(float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
        setValues(controlPointX1, controlPointY1, controlPointX2, controlPointY2);
    }

    public void setValues(float controlPointX1, float controlPointY1, float controlPointX2, float controlPointY2) {
        this.controlPointX1 = controlPointX1;
        this.controlPointX2 = controlPointX2;
        this.controlPointY1 = controlPointY1;
        this.controlPointY2 = controlPointY2;
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
