package com.android.systemui.assist.ui;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DisplayUtils {
    public static int getInvocationCornerRadius(Context context, boolean z) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.assist_disclosure_rounded);
        if (dimensionPixelSize > 0) {
            return dimensionPixelSize;
        }
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size);
        if (dimensionPixelSize2 == 0 && z) {
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size_bottom);
            return dimensionPixelSize3 == 0 ? context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size) : dimensionPixelSize3;
        }
        if (dimensionPixelSize2 != 0 || z) {
            return dimensionPixelSize2;
        }
        int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size_top);
        return dimensionPixelSize4 == 0 ? context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size) : dimensionPixelSize4;
    }
}
