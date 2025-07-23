package com.google.android.material.shape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RoundedCornerTreatment extends CornerTreatment {
    public final float radius;

    public RoundedCornerTreatment() {
        this.radius = -1.0f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public final void getCornerPath(ShapePath shapePath, float f, float f2) {
        shapePath.reset(0.0f, f2 * f, 180.0f, 90.0f);
        float f3 = f2 * 2.0f * f;
        shapePath.addArc(0.0f, 0.0f, f3, f3, 180.0f, 90.0f);
    }

    @Deprecated
    public RoundedCornerTreatment(float f) {
        this.radius = f;
    }
}
