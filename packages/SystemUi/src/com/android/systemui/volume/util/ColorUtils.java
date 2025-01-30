package com.android.systemui.volume.util;

import android.content.Context;
import android.content.res.ColorStateList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
