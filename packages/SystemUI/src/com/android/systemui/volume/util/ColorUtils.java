package com.android.systemui.volume.util;

import android.content.Context;
import android.content.res.ColorStateList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ColorUtils {
    static {
        new ColorUtils();
    }

    private ColorUtils() {
    }

    public static final ColorStateList getSingleColorStateList(int i, Context context) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{context.getResources().getColor(i, null)});
    }
}
