package com.android.systemui.util;

import android.graphics.Color;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ColorUtilKt {
    public static final int getColorWithAlpha(float f, int i) {
        return Color.argb((int) (f * 255), Color.red(i), Color.green(i), Color.blue(i));
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
