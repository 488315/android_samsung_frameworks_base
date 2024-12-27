package com.android.systemui.volume.util;

import android.content.Context;
import android.content.res.ColorStateList;

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
