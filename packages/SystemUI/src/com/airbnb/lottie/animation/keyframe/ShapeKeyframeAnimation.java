package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.airbnb.lottie.animation.content.RoundedCornersContent;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ShapeKeyframeAnimation extends BaseKeyframeAnimation {
    public List shapeModifiers;
    public final Path tempPath;
    public final ShapeData tempShapeData;

    public ShapeKeyframeAnimation(List<Keyframe> list) {
        super(list);
        this.tempShapeData = new ShapeData();
        this.tempPath = new Path();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12, types: [int] */
    /* JADX WARN: Type inference failed for: r10v13, types: [int] */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15, types: [int] */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8, types: [int] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2, types: [int] */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r15v6, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v7, types: [int] */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        ShapeData shapeData;
        float f2;
        List list;
        boolean z;
        ShapeData shapeData2 = (ShapeData) keyframe.startValue;
        ShapeData shapeData3 = (ShapeData) keyframe.endValue;
        ShapeData shapeData4 = this.tempShapeData;
        if (shapeData4.initialPoint == null) {
            shapeData4.initialPoint = new PointF();
        }
        boolean z2 = true;
        boolean z3 = false;
        shapeData4.closed = shapeData2.closed || shapeData3.closed;
        if (((ArrayList) shapeData2.curves).size() != ((ArrayList) shapeData3.curves).size()) {
            Logger.warning("Curves must have the same number of control points. Shape 1: " + ((ArrayList) shapeData2.curves).size() + "\tShape 2: " + ((ArrayList) shapeData3.curves).size());
        }
        int min = Math.min(((ArrayList) shapeData2.curves).size(), ((ArrayList) shapeData3.curves).size());
        if (((ArrayList) shapeData4.curves).size() < min) {
            for (int size = ((ArrayList) shapeData4.curves).size(); size < min; size++) {
                ((ArrayList) shapeData4.curves).add(new CubicCurveData());
            }
        } else if (((ArrayList) shapeData4.curves).size() > min) {
            for (int size2 = ((ArrayList) shapeData4.curves).size() - 1; size2 >= min; size2--) {
                ArrayList arrayList = (ArrayList) shapeData4.curves;
                arrayList.remove(arrayList.size() - 1);
            }
        }
        PointF pointF = shapeData2.initialPoint;
        PointF pointF2 = shapeData3.initialPoint;
        shapeData4.setInitialPoint(MiscUtils.lerp(pointF.x, pointF2.x, f), MiscUtils.lerp(pointF.y, pointF2.y, f));
        int size3 = ((ArrayList) shapeData4.curves).size() - 1;
        while (size3 >= 0) {
            CubicCurveData cubicCurveData = (CubicCurveData) ((ArrayList) shapeData2.curves).get(size3);
            CubicCurveData cubicCurveData2 = (CubicCurveData) ((ArrayList) shapeData3.curves).get(size3);
            PointF pointF3 = cubicCurveData.controlPoint1;
            PointF pointF4 = cubicCurveData.controlPoint2;
            PointF pointF5 = cubicCurveData.vertex;
            PointF pointF6 = cubicCurveData2.controlPoint1;
            PointF pointF7 = cubicCurveData2.controlPoint2;
            PointF pointF8 = cubicCurveData2.vertex;
            ((CubicCurveData) ((ArrayList) shapeData4.curves).get(size3)).controlPoint1.set(MiscUtils.lerp(pointF3.x, pointF6.x, f), MiscUtils.lerp(pointF3.y, pointF6.y, f));
            ((CubicCurveData) ((ArrayList) shapeData4.curves).get(size3)).controlPoint2.set(MiscUtils.lerp(pointF4.x, pointF7.x, f), MiscUtils.lerp(pointF4.y, pointF7.y, f));
            ((CubicCurveData) ((ArrayList) shapeData4.curves).get(size3)).vertex.set(MiscUtils.lerp(pointF5.x, pointF8.x, f), MiscUtils.lerp(pointF5.y, pointF8.y, f));
            size3--;
            z2 = z2;
        }
        boolean z4 = z2;
        List list2 = this.shapeModifiers;
        if (list2 != null) {
            int size4 = list2.size() - 1;
            while (size4 >= 0) {
                RoundedCornersContent roundedCornersContent = (RoundedCornersContent) this.shapeModifiers.get(size4);
                roundedCornersContent.getClass();
                ArrayList arrayList2 = (ArrayList) shapeData4.curves;
                if (arrayList2.size() > 2) {
                    float floatValue = ((Float) roundedCornersContent.roundedCorners.getValue()).floatValue();
                    if (floatValue != 0.0f) {
                        List list3 = shapeData4.curves;
                        boolean z5 = shapeData4.closed;
                        ArrayList arrayList3 = (ArrayList) list3;
                        int size5 = arrayList3.size() - 1;
                        ?? r11 = z3;
                        while (size5 >= 0) {
                            CubicCurveData cubicCurveData3 = (CubicCurveData) arrayList3.get(size5);
                            CubicCurveData cubicCurveData4 = (CubicCurveData) arrayList3.get(RoundedCornersContent.floorMod(size5 - 1, arrayList3.size()));
                            PointF pointF9 = (size5 != 0 || z5) ? cubicCurveData4.vertex : shapeData4.initialPoint;
                            int i = (((size5 != 0 || z5) ? cubicCurveData4.controlPoint2 : pointF9).equals(pointF9) && cubicCurveData3.controlPoint1.equals(pointF9) && !((shapeData4.closed || size5 != 0 || size5 != arrayList3.size() + (-1)) ? z3 : z4)) ? r11 + 2 : r11 + 1;
                            size5--;
                            r11 = i;
                        }
                        ShapeData shapeData5 = roundedCornersContent.shapeData;
                        if (shapeData5 == null || ((ArrayList) shapeData5.curves).size() != r11) {
                            ArrayList arrayList4 = new ArrayList((int) r11);
                            for (?? r10 = z3; r10 < r11; r10++) {
                                arrayList4.add(new CubicCurveData());
                            }
                            roundedCornersContent.shapeData = new ShapeData(new PointF(0.0f, 0.0f), z3, arrayList4);
                        }
                        ShapeData shapeData6 = roundedCornersContent.shapeData;
                        shapeData6.closed = z5;
                        PointF pointF10 = shapeData4.initialPoint;
                        shapeData6.setInitialPoint(pointF10.x, pointF10.y);
                        List list4 = shapeData6.curves;
                        boolean z6 = shapeData4.closed;
                        boolean z7 = z3;
                        ?? r102 = z7;
                        ?? r3 = arrayList2;
                        ?? r9 = z7;
                        while (r9 < r3.size()) {
                            CubicCurveData cubicCurveData5 = (CubicCurveData) r3.get(r9);
                            CubicCurveData cubicCurveData6 = (CubicCurveData) r3.get(RoundedCornersContent.floorMod(r9 - 1, r3.size()));
                            CubicCurveData cubicCurveData7 = (CubicCurveData) r3.get(RoundedCornersContent.floorMod(r9 - 2, r3.size()));
                            PointF pointF11 = (r9 != 0 || z6) ? cubicCurveData6.vertex : shapeData4.initialPoint;
                            PointF pointF12 = (r9 != 0 || z6) ? cubicCurveData6.controlPoint2 : pointF11;
                            PointF pointF13 = cubicCurveData5.controlPoint1;
                            PointF pointF14 = cubicCurveData7.vertex;
                            int i2 = size4;
                            PointF pointF15 = cubicCurveData5.vertex;
                            List list5 = r3;
                            boolean z8 = (!shapeData4.closed && r9 == 0 && r9 == list5.size() + (-1)) ? z4 : false;
                            if (pointF12.equals(pointF11) && pointF13.equals(pointF11) && !z8) {
                                float f3 = pointF11.x;
                                float f4 = f3 - pointF14.x;
                                float f5 = pointF11.y;
                                float f6 = f5 - pointF14.y;
                                float f7 = pointF15.x - f3;
                                float f8 = pointF15.y - f5;
                                shapeData = shapeData4;
                                f2 = floatValue;
                                float hypot = (float) Math.hypot(f4, f6);
                                float hypot2 = (float) Math.hypot(f7, f8);
                                float min2 = Math.min(f2 / hypot, 0.5f);
                                float min3 = Math.min(f2 / hypot2, 0.5f);
                                float f9 = pointF11.x;
                                float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF14.x, f9, min2, f9);
                                float f10 = pointF11.y;
                                float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF14.y, f10, min2, f10);
                                float m$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF15.x, f9, min3, f9);
                                float m$14 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF15.y, f10, min3, f10);
                                float f11 = m$1 - ((m$1 - f9) * 0.5519f);
                                float f12 = m$12 - ((m$12 - f10) * 0.5519f);
                                float f13 = m$13 - ((m$13 - f9) * 0.5519f);
                                float f14 = m$14 - ((m$14 - f10) * 0.5519f);
                                ?? r15 = (ArrayList) list4;
                                list = list4;
                                CubicCurveData cubicCurveData8 = (CubicCurveData) r15.get(RoundedCornersContent.floorMod(r102 - 1, r15.size()));
                                CubicCurveData cubicCurveData9 = (CubicCurveData) r15.get(r102);
                                z = z6;
                                cubicCurveData8.controlPoint2.set(m$1, m$12);
                                cubicCurveData8.vertex.set(m$1, m$12);
                                if (r9 == 0) {
                                    shapeData6.setInitialPoint(m$1, m$12);
                                }
                                cubicCurveData9.controlPoint1.set(f11, f12);
                                CubicCurveData cubicCurveData10 = (CubicCurveData) r15.get(r102 + 1);
                                cubicCurveData9.controlPoint2.set(f13, f14);
                                cubicCurveData9.vertex.set(m$13, m$14);
                                cubicCurveData10.controlPoint1.set(m$13, m$14);
                                r102 += 2;
                            } else {
                                shapeData = shapeData4;
                                f2 = floatValue;
                                list = list4;
                                z = z6;
                                ?? r6 = (ArrayList) list;
                                CubicCurveData cubicCurveData11 = (CubicCurveData) r6.get(RoundedCornersContent.floorMod(r102 - 1, r6.size()));
                                CubicCurveData cubicCurveData12 = (CubicCurveData) r6.get(r102);
                                PointF pointF16 = cubicCurveData6.controlPoint2;
                                cubicCurveData11.controlPoint2.set(pointF16.x, pointF16.y);
                                PointF pointF17 = cubicCurveData6.vertex;
                                cubicCurveData11.vertex.set(pointF17.x, pointF17.y);
                                PointF pointF18 = cubicCurveData5.controlPoint1;
                                cubicCurveData12.controlPoint1.set(pointF18.x, pointF18.y);
                                r102++;
                            }
                            size4 = i2;
                            r3 = list5;
                            shapeData4 = shapeData;
                            floatValue = f2;
                            list4 = list;
                            z6 = z;
                            r9++;
                        }
                        shapeData4 = shapeData6;
                    }
                }
                size4--;
                z3 = false;
            }
        }
        Path path = this.tempPath;
        path.reset();
        PointF pointF19 = shapeData4.initialPoint;
        path.moveTo(pointF19.x, pointF19.y);
        MiscUtils.pathFromDataCurrentPoint.set(pointF19.x, pointF19.y);
        for (int i3 = 0; i3 < ((ArrayList) shapeData4.curves).size(); i3++) {
            CubicCurveData cubicCurveData13 = (CubicCurveData) ((ArrayList) shapeData4.curves).get(i3);
            PointF pointF20 = cubicCurveData13.controlPoint1;
            PointF pointF21 = cubicCurveData13.controlPoint2;
            PointF pointF22 = cubicCurveData13.vertex;
            PointF pointF23 = MiscUtils.pathFromDataCurrentPoint;
            if (pointF20.equals(pointF23) && pointF21.equals(pointF22)) {
                path.lineTo(pointF22.x, pointF22.y);
            } else {
                path.cubicTo(pointF20.x, pointF20.y, pointF21.x, pointF21.y, pointF22.x, pointF22.y);
            }
            pointF23.set(pointF22.x, pointF22.y);
        }
        if (shapeData4.closed) {
            path.close();
        }
        return this.tempPath;
    }
}
