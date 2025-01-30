package com.android.systemui.volume.util;

import android.graphics.Rect;
import android.view.View;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
