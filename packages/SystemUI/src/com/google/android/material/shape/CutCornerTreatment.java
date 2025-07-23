package com.google.android.material.shape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CutCornerTreatment extends CornerTreatment {
    public final float size;

    public CutCornerTreatment() {
        this.size = -1.0f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public final void getCornerPath(ShapePath shapePath, float f, float f2) {
        shapePath.reset(0.0f, f2 * f, 180.0f, 90.0f);
        double d = f2;
        double d2 = f;
        shapePath.lineTo((float) (Math.sin(Math.toRadians(90.0f)) * d * d2), (float) (Math.sin(Math.toRadians(0.0f)) * d * d2));
    }

    @Deprecated
    public CutCornerTreatment(float f) {
        this.size = f;
    }
}
