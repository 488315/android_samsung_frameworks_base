package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import com.airbnb.lottie.model.CubicCurveData;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ShapeData {
    public boolean closed;
    public final List curves;
    public PointF initialPoint;

    public ShapeData(PointF pointF, boolean z, List<CubicCurveData> list) {
        this.initialPoint = pointF;
        this.closed = z;
        this.curves = new ArrayList(list);
    }

    public final void setInitialPoint(float f, float f2) {
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.initialPoint.set(f, f2);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShapeData{numCurves=");
        sb.append(this.curves.size());
        sb.append("closed=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.closed, '}');
    }

    public ShapeData() {
        this.curves = new ArrayList();
    }
}
