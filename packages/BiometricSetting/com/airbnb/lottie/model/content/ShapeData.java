package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ShapeData {
    private boolean closed;
    private final List<CubicCurveData> curves;
    private PointF initialPoint;

    public ShapeData(PointF pointF, boolean z, List<CubicCurveData> list) {
        this.initialPoint = pointF;
        this.closed = z;
        this.curves = new ArrayList(list);
    }

    public final List<CubicCurveData> getCurves() {
        return this.curves;
    }

    public final PointF getInitialPoint() {
        return this.initialPoint;
    }

    public final void interpolateBetween(ShapeData shapeData, ShapeData shapeData2, float f) {
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.closed = shapeData.closed || shapeData2.closed;
        List<CubicCurveData> list = shapeData.curves;
        int size = ((ArrayList) list).size();
        int size2 = ((ArrayList) shapeData2.curves).size();
        List<CubicCurveData> list2 = shapeData2.curves;
        if (size != size2) {
            Logger.warning("Curves must have the same number of control points. Shape 1: " + ((ArrayList) list).size() + "\tShape 2: " + ((ArrayList) list2).size());
        }
        int min = Math.min(((ArrayList) list).size(), ((ArrayList) list2).size());
        List<CubicCurveData> list3 = this.curves;
        if (((ArrayList) list3).size() < min) {
            for (int size3 = ((ArrayList) list3).size(); size3 < min; size3++) {
                ((ArrayList) list3).add(new CubicCurveData());
            }
        } else if (((ArrayList) list3).size() > min) {
            for (int size4 = ((ArrayList) list3).size() - 1; size4 >= min; size4--) {
                ((ArrayList) list3).remove(((ArrayList) list3).size() - 1);
            }
        }
        PointF pointF = shapeData.initialPoint;
        PointF pointF2 = shapeData2.initialPoint;
        float f2 = pointF.x;
        float f3 = pointF2.x;
        int i = MiscUtils.$r8$clinit;
        float m3m = RunGroup$$ExternalSyntheticOutline0.m3m(f3, f2, f, f2);
        float f4 = pointF.y;
        float m3m2 = RunGroup$$ExternalSyntheticOutline0.m3m(pointF2.y, f4, f, f4);
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.initialPoint.set(m3m, m3m2);
        for (int size5 = ((ArrayList) list3).size() - 1; size5 >= 0; size5--) {
            CubicCurveData cubicCurveData = (CubicCurveData) ((ArrayList) list).get(size5);
            CubicCurveData cubicCurveData2 = (CubicCurveData) ((ArrayList) list2).get(size5);
            PointF controlPoint1 = cubicCurveData.getControlPoint1();
            PointF controlPoint2 = cubicCurveData.getControlPoint2();
            PointF vertex = cubicCurveData.getVertex();
            PointF controlPoint12 = cubicCurveData2.getControlPoint1();
            PointF controlPoint22 = cubicCurveData2.getControlPoint2();
            PointF vertex2 = cubicCurveData2.getVertex();
            CubicCurveData cubicCurveData3 = (CubicCurveData) ((ArrayList) list3).get(size5);
            float f5 = controlPoint1.x;
            float m3m3 = RunGroup$$ExternalSyntheticOutline0.m3m(controlPoint12.x, f5, f, f5);
            float f6 = controlPoint1.y;
            cubicCurveData3.setControlPoint1(m3m3, ((controlPoint12.y - f6) * f) + f6);
            CubicCurveData cubicCurveData4 = (CubicCurveData) ((ArrayList) list3).get(size5);
            float f7 = controlPoint2.x;
            float m3m4 = RunGroup$$ExternalSyntheticOutline0.m3m(controlPoint22.x, f7, f, f7);
            float f8 = controlPoint2.y;
            cubicCurveData4.setControlPoint2(m3m4, ((controlPoint22.y - f8) * f) + f8);
            CubicCurveData cubicCurveData5 = (CubicCurveData) ((ArrayList) list3).get(size5);
            float f9 = vertex.x;
            float m3m5 = RunGroup$$ExternalSyntheticOutline0.m3m(vertex2.x, f9, f, f9);
            float f10 = vertex.y;
            cubicCurveData5.setVertex(m3m5, ((vertex2.y - f10) * f) + f10);
        }
    }

    public final boolean isClosed() {
        return this.closed;
    }

    public final String toString() {
        return "ShapeData{numCurves=" + this.curves.size() + "closed=" + this.closed + '}';
    }

    public ShapeData() {
        this.curves = new ArrayList();
    }
}
