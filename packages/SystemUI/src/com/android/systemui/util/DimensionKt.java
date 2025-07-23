package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
