package com.google.android.material.shape;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MarkerEdgeTreatment extends EdgeTreatment {
    public final float radius;

    public MarkerEdgeTreatment(float f) {
        this.radius = f - 0.001f;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public final void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        double d = this.radius;
        float sqrt = (float) ((Math.sqrt(2.0d) * d) / 2.0d);
        float sqrt2 = (float) Math.sqrt(Math.pow(d, 2.0d) - Math.pow(sqrt, 2.0d));
        shapePath.reset(f2 - sqrt, ((float) (-((Math.sqrt(2.0d) * d) - d))) + sqrt2, 270.0f, 0.0f);
        shapePath.lineTo(f2, (float) (-((Math.sqrt(2.0d) * d) - d)));
        shapePath.lineTo(f2 + sqrt, ((float) (-((Math.sqrt(2.0d) * d) - d))) + sqrt2);
    }
}
