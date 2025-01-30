package com.android.systemui.assist.p002ui;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DisplayUtils {
    public static int getInvocationCornerRadius(Context context, boolean z) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.assist_disclosure_rounded);
        if (dimensionPixelSize3 > 0) {
            return dimensionPixelSize3;
        }
        int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size);
        if (dimensionPixelSize4 == 0 && z) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size_bottom);
            if (dimensionPixelSize == 0) {
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size);
                return dimensionPixelSize2;
            }
            return dimensionPixelSize;
        }
        if (dimensionPixelSize4 != 0 || z) {
            return dimensionPixelSize4;
        }
        dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size_top);
        if (dimensionPixelSize == 0) {
            dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.config_rounded_mask_size);
            return dimensionPixelSize2;
        }
        return dimensionPixelSize;
    }
}
