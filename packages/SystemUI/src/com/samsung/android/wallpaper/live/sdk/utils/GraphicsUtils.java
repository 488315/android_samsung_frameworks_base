package com.samsung.android.wallpaper.live.sdk.utils;

import android.graphics.Rect;
import android.graphics.RectF;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GraphicsUtils {
    public static Rect getCenterCropRect(int i, int i2, int i3, int i4) {
        RectF rectF;
        float f;
        float f2;
        Rect rect = null;
        if (i <= 0 || i2 <= 0 || i3 <= 0 || i4 <= 0) {
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "getCenterCropRectInternal: incorrect params : ", ", ", ", ");
            m.append(i3);
            m.append(", ");
            m.append(i4);
            SdkLog.e("GraphicsUtils", m.toString());
            rectF = null;
        } else {
            if (i * i4 > i3 * i2) {
                f = i4;
                f2 = i2;
            } else {
                f = i3;
                f2 = i;
            }
            float f3 = f / f2;
            float f4 = i3 / f3;
            float f5 = i4 / f3;
            float f6 = (i / 2.0f) - (f4 / 2.0f);
            float f7 = (i2 / 2.0f) - (f5 / 2.0f);
            rectF = new RectF(f6, f7, f4 + f6, f5 + f7);
        }
        if (rectF != null) {
            Rect rect2 = new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
            rect2.intersect(0, 0, i, i2);
            if (rect2.width() != 0 && rect2.height() != 0) {
                rect = rect2;
            }
        }
        StringBuilder m2 = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "getCenterCropRect: imgWidth=", ", imgHeight=", ", widthToFit=");
        ViewPager$$ExternalSyntheticOutline0.m(m2, i3, ", heightToFit=", i4, ", result=");
        m2.append(rect);
        SdkLog.d("GraphicsUtils", m2.toString());
        return rect;
    }
}
