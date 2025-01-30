package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.airbnb.lottie.animation.content.RoundedCornersContent;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShapeKeyframeAnimation extends BaseKeyframeAnimation {
    public List shapeModifiers;
    public final Path tempPath;
    public final ShapeData tempShapeData;

    public ShapeKeyframeAnimation(List<Keyframe> list) {
        super(list);
        this.tempShapeData = new ShapeData();
        this.tempPath = new Path();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        int i;
        int i2;
        int i3;
        ArrayList arrayList;
        ShapeData shapeData;
        float f2;
        ShapeKeyframeAnimation shapeKeyframeAnimation = this;
        ShapeData shapeData2 = (ShapeData) keyframe.startValue;
        ShapeData shapeData3 = (ShapeData) keyframe.endValue;
        ShapeData shapeData4 = shapeKeyframeAnimation.tempShapeData;
        if (shapeData4.initialPoint == null) {
            shapeData4.initialPoint = new PointF();
        }
        int i4 = 1;
        shapeData4.closed = shapeData2.closed || shapeData3.closed;
        ArrayList arrayList2 = (ArrayList) shapeData2.curves;
        int size = arrayList2.size();
        int size2 = ((ArrayList) shapeData3.curves).size();
        List list = shapeData3.curves;
        if (size != size2) {
            Logger.warning("Curves must have the same number of control points. Shape 1: " + arrayList2.size() + "\tShape 2: " + ((ArrayList) list).size());
        }
        ArrayList arrayList3 = (ArrayList) list;
        int min = Math.min(arrayList2.size(), arrayList3.size());
        ArrayList arrayList4 = (ArrayList) shapeData4.curves;
        if (arrayList4.size() < min) {
            for (int size3 = arrayList4.size(); size3 < min; size3++) {
                arrayList4.add(new CubicCurveData());
            }
        } else if (arrayList4.size() > min) {
            for (int size4 = arrayList4.size() - 1; size4 >= min; size4--) {
                arrayList4.remove(arrayList4.size() - 1);
            }
        }
        PointF pointF = shapeData2.initialPoint;
        PointF pointF2 = shapeData3.initialPoint;
        float f3 = pointF.x;
        float f4 = pointF2.x;
        PointF pointF3 = MiscUtils.pathFromDataCurrentPoint;
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f4, f3, f, f3);
        float f5 = pointF.y;
        shapeData4.setInitialPoint(m20m, ((pointF2.y - f5) * f) + f5);
        for (int size5 = arrayList4.size() - 1; size5 >= 0; size5--) {
            CubicCurveData cubicCurveData = (CubicCurveData) arrayList2.get(size5);
            CubicCurveData cubicCurveData2 = (CubicCurveData) arrayList3.get(size5);
            PointF pointF4 = cubicCurveData.controlPoint1;
            PointF pointF5 = cubicCurveData2.controlPoint1;
            CubicCurveData cubicCurveData3 = (CubicCurveData) arrayList4.get(size5);
            float f6 = pointF4.x;
            float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF5.x, f6, f, f6);
            float f7 = pointF4.y;
            cubicCurveData3.controlPoint1.set(m20m2, DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF5.y, f7, f, f7));
            CubicCurveData cubicCurveData4 = (CubicCurveData) arrayList4.get(size5);
            PointF pointF6 = cubicCurveData.controlPoint2;
            float f8 = pointF6.x;
            PointF pointF7 = cubicCurveData2.controlPoint2;
            float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF7.x, f8, f, f8);
            float f9 = pointF6.y;
            cubicCurveData4.controlPoint2.set(m20m3, DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF7.y, f9, f, f9));
            CubicCurveData cubicCurveData5 = (CubicCurveData) arrayList4.get(size5);
            PointF pointF8 = cubicCurveData.vertex;
            float f10 = pointF8.x;
            PointF pointF9 = cubicCurveData2.vertex;
            float m20m4 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF9.x, f10, f, f10);
            float f11 = pointF8.y;
            cubicCurveData5.vertex.set(m20m4, DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF9.y, f11, f, f11));
        }
        List list2 = shapeKeyframeAnimation.shapeModifiers;
        if (list2 != null) {
            int size6 = list2.size() - 1;
            while (size6 >= 0) {
                RoundedCornersContent roundedCornersContent = (RoundedCornersContent) shapeKeyframeAnimation.shapeModifiers.get(size6);
                roundedCornersContent.getClass();
                ArrayList arrayList5 = (ArrayList) shapeData4.curves;
                if (arrayList5.size() > 2) {
                    float floatValue = ((Float) roundedCornersContent.roundedCorners.getValue()).floatValue();
                    if (floatValue != 0.0f) {
                        List list3 = shapeData4.curves;
                        boolean z = shapeData4.closed;
                        ArrayList arrayList6 = (ArrayList) list3;
                        int size7 = arrayList6.size() - i4;
                        int i5 = 0;
                        while (size7 >= 0) {
                            CubicCurveData cubicCurveData6 = (CubicCurveData) arrayList6.get(size7);
                            int i6 = size7 - 1;
                            CubicCurveData cubicCurveData7 = (CubicCurveData) arrayList6.get(RoundedCornersContent.floorMod(i6, arrayList6.size()));
                            PointF pointF10 = (size7 != 0 || z) ? cubicCurveData7.vertex : shapeData4.initialPoint;
                            i5 = (((size7 != 0 || z) ? cubicCurveData7.controlPoint2 : pointF10).equals(pointF10) && cubicCurveData6.controlPoint1.equals(pointF10) && ((shapeData4.closed || size7 != 0 || size7 != arrayList6.size() - i4) ? 0 : i4) == 0) ? i5 + 2 : i5 + 1;
                            size7 = i6;
                        }
                        ShapeData shapeData5 = roundedCornersContent.shapeData;
                        if (shapeData5 == null || ((ArrayList) shapeData5.curves).size() != i5) {
                            ArrayList arrayList7 = new ArrayList(i5);
                            for (int i7 = 0; i7 < i5; i7++) {
                                arrayList7.add(new CubicCurveData());
                            }
                            roundedCornersContent.shapeData = new ShapeData(new PointF(0.0f, 0.0f), false, arrayList7);
                            i2 = 0;
                        } else {
                            i2 = 0;
                        }
                        ShapeData shapeData6 = roundedCornersContent.shapeData;
                        shapeData6.closed = z;
                        PointF pointF11 = shapeData4.initialPoint;
                        shapeData6.setInitialPoint(pointF11.x, pointF11.y);
                        List list4 = shapeData6.curves;
                        boolean z2 = shapeData4.closed;
                        int i8 = i2;
                        while (i2 < arrayList5.size()) {
                            CubicCurveData cubicCurveData8 = (CubicCurveData) arrayList5.get(i2);
                            CubicCurveData cubicCurveData9 = (CubicCurveData) arrayList5.get(RoundedCornersContent.floorMod(i2 - 1, arrayList5.size()));
                            CubicCurveData cubicCurveData10 = (CubicCurveData) arrayList5.get(RoundedCornersContent.floorMod(i2 - 2, arrayList5.size()));
                            PointF pointF12 = (i2 != 0 || z2) ? cubicCurveData9.vertex : shapeData4.initialPoint;
                            PointF pointF13 = (i2 != 0 || z2) ? cubicCurveData9.controlPoint2 : pointF12;
                            PointF pointF14 = cubicCurveData8.controlPoint1;
                            PointF pointF15 = cubicCurveData10.vertex;
                            boolean z3 = z2;
                            boolean z4 = !shapeData4.closed && i2 == 0 && i2 == arrayList5.size() + (-1);
                            if (pointF13.equals(pointF12) && pointF14.equals(pointF12) && !z4) {
                                float f12 = pointF12.x;
                                float f13 = f12 - pointF15.x;
                                float f14 = pointF12.y;
                                float f15 = f14 - pointF15.y;
                                PointF pointF16 = cubicCurveData8.vertex;
                                arrayList = arrayList5;
                                float f16 = pointF16.x - f12;
                                float f17 = pointF16.y - f14;
                                i3 = size6;
                                float hypot = (float) Math.hypot(f13, f15);
                                ShapeData shapeData7 = shapeData4;
                                float hypot2 = (float) Math.hypot(f16, f17);
                                float min2 = Math.min(floatValue / hypot, 0.5f);
                                float min3 = Math.min(floatValue / hypot2, 0.5f);
                                float f18 = pointF12.x;
                                float m20m5 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF15.x, f18, min2, f18);
                                float f19 = pointF12.y;
                                float m20m6 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF15.y, f19, min2, f19);
                                float m20m7 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF16.x, f18, min3, f18);
                                float m20m8 = DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF16.y, f19, min3, f19);
                                float f20 = m20m5 - ((m20m5 - f18) * 0.5519f);
                                float f21 = m20m6 - ((m20m6 - f19) * 0.5519f);
                                float f22 = m20m7 - ((m20m7 - f18) * 0.5519f);
                                float f23 = m20m8 - ((m20m8 - f19) * 0.5519f);
                                ArrayList arrayList8 = (ArrayList) list4;
                                shapeData = shapeData7;
                                CubicCurveData cubicCurveData11 = (CubicCurveData) arrayList8.get(RoundedCornersContent.floorMod(i8 - 1, arrayList8.size()));
                                CubicCurveData cubicCurveData12 = (CubicCurveData) arrayList8.get(i8);
                                f2 = floatValue;
                                cubicCurveData11.controlPoint2.set(m20m5, m20m6);
                                cubicCurveData11.vertex.set(m20m5, m20m6);
                                if (i2 == 0) {
                                    shapeData6.setInitialPoint(m20m5, m20m6);
                                }
                                cubicCurveData12.controlPoint1.set(f20, f21);
                                i8++;
                                CubicCurveData cubicCurveData13 = (CubicCurveData) arrayList8.get(i8);
                                cubicCurveData12.controlPoint2.set(f22, f23);
                                cubicCurveData12.vertex.set(m20m7, m20m8);
                                cubicCurveData13.controlPoint1.set(m20m7, m20m8);
                            } else {
                                i3 = size6;
                                arrayList = arrayList5;
                                shapeData = shapeData4;
                                f2 = floatValue;
                                ArrayList arrayList9 = (ArrayList) list4;
                                CubicCurveData cubicCurveData14 = (CubicCurveData) arrayList9.get(RoundedCornersContent.floorMod(i8 - 1, arrayList9.size()));
                                CubicCurveData cubicCurveData15 = (CubicCurveData) arrayList9.get(i8);
                                PointF pointF17 = cubicCurveData9.controlPoint2;
                                cubicCurveData14.controlPoint2.set(pointF17.x, pointF17.y);
                                PointF pointF18 = cubicCurveData9.vertex;
                                cubicCurveData14.vertex.set(pointF18.x, pointF18.y);
                                PointF pointF19 = cubicCurveData8.controlPoint1;
                                cubicCurveData15.controlPoint1.set(pointF19.x, pointF19.y);
                            }
                            i8++;
                            i2++;
                            z2 = z3;
                            arrayList5 = arrayList;
                            size6 = i3;
                            shapeData4 = shapeData;
                            floatValue = f2;
                        }
                        i = size6;
                        shapeData4 = shapeData6;
                        size6 = i - 1;
                        i4 = 1;
                        shapeKeyframeAnimation = this;
                    }
                }
                i = size6;
                size6 = i - 1;
                i4 = 1;
                shapeKeyframeAnimation = this;
            }
        }
        Path path = this.tempPath;
        path.reset();
        PointF pointF20 = shapeData4.initialPoint;
        path.moveTo(pointF20.x, pointF20.y);
        PointF pointF21 = MiscUtils.pathFromDataCurrentPoint;
        pointF21.set(pointF20.x, pointF20.y);
        int i9 = 0;
        while (true) {
            ArrayList arrayList10 = (ArrayList) shapeData4.curves;
            if (i9 >= arrayList10.size()) {
                break;
            }
            CubicCurveData cubicCurveData16 = (CubicCurveData) arrayList10.get(i9);
            PointF pointF22 = cubicCurveData16.controlPoint1;
            boolean equals = pointF22.equals(pointF21);
            PointF pointF23 = cubicCurveData16.controlPoint2;
            PointF pointF24 = cubicCurveData16.vertex;
            if (equals && pointF23.equals(pointF24)) {
                path.lineTo(pointF24.x, pointF24.y);
            } else {
                path.cubicTo(pointF22.x, pointF22.y, pointF23.x, pointF23.y, pointF24.x, pointF24.y);
            }
            pointF21.set(pointF24.x, pointF24.y);
            i9++;
        }
        if (shapeData4.closed) {
            path.close();
        }
        return path;
    }
}
