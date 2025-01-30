package com.android.systemui.assist.ui;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CornerPathRenderer {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Corner {
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_RIGHT,
        TOP_LEFT
    }

    public abstract Path getCornerPath(Corner corner);

    public final Path getInsetPath(Corner corner, float f) {
        PointF pointF;
        PointF pointF2;
        Path cornerPath = getCornerPath(corner);
        float f2 = -f;
        float[] approximate = cornerPath.approximate(0.1f);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < approximate.length; i += 3) {
            arrayList.add(new PointF(approximate[i + 1], approximate[i + 2]));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            PointF pointF3 = (PointF) arrayList.get(i2);
            if (i2 == 0) {
                pointF = new PointF(0.0f, 0.0f);
            } else {
                PointF pointF4 = (PointF) arrayList.get(i2);
                PointF pointF5 = (PointF) arrayList.get(i2 - 1);
                pointF = new PointF(pointF4.x - pointF5.x, pointF4.y - pointF5.y);
            }
            if (i2 == arrayList.size() - 1) {
                pointF2 = new PointF(0.0f, 0.0f);
            } else {
                PointF pointF6 = (PointF) arrayList.get(i2);
                PointF pointF7 = (PointF) arrayList.get(i2 + 1);
                pointF2 = new PointF(pointF7.x - pointF6.x, pointF7.y - pointF6.y);
            }
            PointF pointF8 = new PointF(pointF.x + pointF2.x, pointF.y + pointF2.y);
            float f3 = pointF8.x;
            float f4 = pointF8.y;
            float sqrt = (float) Math.sqrt((f4 * f4) + (f3 * f3));
            if (sqrt != 0.0f) {
                float f5 = 1.0f / sqrt;
                pointF8 = new PointF(pointF8.x * f5, pointF8.y * f5);
            }
            PointF pointF9 = new PointF(-pointF8.y, pointF8.x);
            arrayList2.add(new PointF((pointF9.x * f2) + pointF3.x, (pointF9.y * f2) + pointF3.y));
        }
        Path path = new Path();
        if (arrayList2.size() > 0) {
            path.moveTo(((PointF) arrayList2.get(0)).x, ((PointF) arrayList2.get(0)).y);
            for (PointF pointF10 : arrayList2.subList(1, arrayList2.size())) {
                path.lineTo(pointF10.x, pointF10.y);
            }
        }
        return path;
    }
}
