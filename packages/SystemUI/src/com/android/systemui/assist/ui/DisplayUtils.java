package com.android.systemui.assist.ui;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class DisplayUtils {
    public static int getInvocationCornerRadius(Context context, boolean z) throws Resources.NotFoundException {
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
