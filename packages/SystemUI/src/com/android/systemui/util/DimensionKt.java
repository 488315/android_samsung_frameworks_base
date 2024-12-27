package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
