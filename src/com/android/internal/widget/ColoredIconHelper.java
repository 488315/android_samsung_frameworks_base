package com.android.internal.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import com.android.internal.util.ContrastColorUtil;

/* loaded from: classes6.dex */
final class ColoredIconHelper {
    static final int COLOR_INVALID = 1;

    private ColoredIconHelper() {
    }

    static void applyGrayTint(Context context, Drawable drawable, boolean z, int i) {
        if (i == 1) {
            return;
        }
        if (z) {
            drawable.mutate().setColorFilter(ContrastColorUtil.resolveColor(context, 0, (context.getResources().getConfiguration().uiMode & 48) == 32), PorterDuff.Mode.SRC_ATOP);
        } else {
            drawable.mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
