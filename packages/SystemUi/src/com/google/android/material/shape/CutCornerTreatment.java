package com.google.android.material.shape;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CutCornerTreatment extends CornerTreatment {
    public final float size;

    public CutCornerTreatment() {
        this.size = -1.0f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public final void getCornerPath(float f, float f2, ShapePath shapePath) {
        shapePath.reset(0.0f, f2 * f, 180.0f, 90.0f);
        double d = f2;
        double d2 = f;
        shapePath.lineTo((float) (Math.sin(Math.toRadians(90.0f)) * d * d2), (float) (Math.sin(Math.toRadians(0.0f)) * d * d2));
    }

    @Deprecated
    public CutCornerTreatment(float f) {
        this.size = -1.0f;
        this.size = f;
    }
}
