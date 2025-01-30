package com.google.android.material.bottomappbar;

import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BottomAppBarTopEdgeTreatment extends EdgeTreatment implements Cloneable {
    public float cradleVerticalOffset;
    public float fabCornerSize = -1.0f;
    public float fabDiameter;
    public final float fabMargin;
    public float horizontalOffset;
    public final float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float f, float f2, float f3) {
        this.fabMargin = f;
        this.roundedCornerRadius = f2;
        if (f3 < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.cradleVerticalOffset = f3;
        this.horizontalOffset = 0.0f;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public final void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        float f4;
        float f5;
        float f6 = this.fabDiameter;
        if (f6 == 0.0f) {
            shapePath.lineTo(f, 0.0f);
            return;
        }
        float f7 = ((this.fabMargin * 2.0f) + f6) / 2.0f;
        float f8 = f3 * this.roundedCornerRadius;
        float f9 = f2 + this.horizontalOffset;
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f3, f7, this.cradleVerticalOffset * f3);
        if (m20m / f7 >= 1.0f) {
            shapePath.lineTo(f, 0.0f);
            return;
        }
        float f10 = this.fabCornerSize;
        float f11 = f10 * f3;
        boolean z = f10 == -1.0f || Math.abs((f10 * 2.0f) - f6) < 0.1f;
        if (z) {
            f4 = m20m;
            f5 = 0.0f;
        } else {
            f5 = 1.75f;
            f4 = 0.0f;
        }
        float f12 = f7 + f8;
        float f13 = f4 + f8;
        float sqrt = (float) Math.sqrt((f12 * f12) - (f13 * f13));
        float f14 = f9 - sqrt;
        float f15 = f9 + sqrt;
        float degrees = (float) Math.toDegrees(Math.atan(sqrt / f13));
        float f16 = (90.0f - degrees) + f5;
        shapePath.lineTo(f14, 0.0f);
        float f17 = f8 * 2.0f;
        shapePath.addArc(f14 - f8, 0.0f, f14 + f8, f17, 270.0f, degrees);
        if (z) {
            shapePath.addArc(f9 - f7, (-f7) - f4, f9 + f7, f7 - f4, 180.0f - f16, (f16 * 2.0f) - 180.0f);
        } else {
            float f18 = this.fabMargin;
            float f19 = f11 * 2.0f;
            float f20 = f9 - f7;
            float f21 = f11 + f18;
            shapePath.addArc(f20, -f21, f20 + f18 + f19, f21, 180.0f - f16, ((f16 * 2.0f) - 180.0f) / 2.0f);
            float f22 = f9 + f7;
            float f23 = this.fabMargin;
            shapePath.lineTo(f22 - ((f23 / 2.0f) + f11), f23 + f11);
            float f24 = this.fabMargin;
            float f25 = f11 + f24;
            shapePath.addArc(f22 - (f19 + f24), -f25, f22, f25, 90.0f, f16 - 90.0f);
        }
        shapePath.addArc(f15 - f8, 0.0f, f15 + f8, f17, 270.0f - degrees, degrees);
        shapePath.lineTo(f, 0.0f);
    }
}
