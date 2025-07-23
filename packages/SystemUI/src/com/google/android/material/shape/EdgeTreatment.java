package com.google.android.material.shape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class EdgeTreatment {
    public boolean forceIntersection() {
        return this instanceof MarkerEdgeTreatment;
    }

    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        shapePath.lineTo(f, 0.0f);
    }
}
