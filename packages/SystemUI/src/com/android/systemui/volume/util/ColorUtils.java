package com.android.systemui.volume.util;

import android.content.Context;
import android.content.res.ColorStateList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
