package com.android.systemui.assist.ui;

import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
