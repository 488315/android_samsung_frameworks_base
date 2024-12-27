package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public final class DimensionKt {
    public static final float dpToPx(Number number, Context context) {
        return dpToPx(number, context.getResources());
    }

    public static final float dpToPx(Number number, Resources resources) {
        return dpToPx(number, resources.getDisplayMetrics());
    }

    public static final float dpToPx(Number number, DisplayMetrics displayMetrics) {
        return TypedValue.applyDimension(1, number.floatValue(), displayMetrics);
    }
}
