package com.google.android.material.shape;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class EdgeTreatment {
    public boolean forceIntersection() {
        return this instanceof MarkerEdgeTreatment;
    }

    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        shapePath.lineTo(f, 0.0f);
    }
}
