package com.android.systemui.wallpaper.theme;

import android.content.Context;

/* loaded from: classes3.dex */
public class DensityUtil {
    public static int sMetricsHeight;
    public static int sMetricsWidth;

    private DensityUtil() {
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
