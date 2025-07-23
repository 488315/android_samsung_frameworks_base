package com.android.systemui.util;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ColorUtilKt {
    public static final int getColorWithAlpha(int i, float f) {
        return Color.argb((int) (f * 255), Color.red(i), Color.green(i), Color.blue(i));
    }

    public static final int getPrivateAttrColorIfUnset(ContextThemeWrapper contextThemeWrapper, TypedArray typedArray, int i, int i2, int i3) {
        if (typedArray.hasValue(i)) {
            return typedArray.getColor(i, i2);
        }
        TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(new int[]{i3});
        int color = obtainStyledAttributes.getColor(0, i2);
        obtainStyledAttributes.recycle();
        return color;
    }

    public static final String hexColorString(Integer num) {
        if (num == null) {
            return "null";
        }
        int intValue = num.intValue();
        int i = StringCompanionObject.$r8$clinit;
        return String.format("#%08x", Arrays.copyOf(new Object[]{Integer.valueOf(intValue)}, 1));
    }
}
