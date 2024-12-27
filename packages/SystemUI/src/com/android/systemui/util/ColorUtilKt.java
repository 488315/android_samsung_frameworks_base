package com.android.systemui.util;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
