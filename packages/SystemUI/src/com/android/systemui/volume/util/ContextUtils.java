package com.android.systemui.volume.util;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ContextUtils {
    public static final ContextUtils INSTANCE = new ContextUtils();

    private ContextUtils() {
    }

    public static final float getDimenFloat(int i, Context context) {
        return context.getResources().getDimension(i);
    }

    public static final int getDimenInt(int i, Context context) {
        return context.getResources().getDimensionPixelSize(i);
    }

    public static final int getDisplayHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static final int getDisplayWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static final boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static final boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 32) > 0;
    }

    public static final boolean isScreenWideMobileDevice(Context context) {
        if (BasicRune.VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG) {
            if ((isLandscape(context) ? getDisplayHeight(context) : getDisplayWidth(context)) > getDimenInt(R.dimen.volume_panel_screen_width_threshold, context)) {
                return true;
            }
        }
        return false;
    }
}
