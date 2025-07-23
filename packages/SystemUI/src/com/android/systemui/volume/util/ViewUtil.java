package com.android.systemui.volume.util;

import android.graphics.Rect;
import android.view.View;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewUtil {
    public static final ViewUtil INSTANCE = new ViewUtil();

    private ViewUtil() {
    }

    public static boolean isTouched(View view, float f, float f2) {
        ViewLocationUtil.INSTANCE.getClass();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        return new Rect(i, iArr[1], view.getWidth() + i, view.getHeight() + iArr[1]).contains(MathKt__MathJVMKt.roundToInt(f), MathKt__MathJVMKt.roundToInt(f2));
    }
}
