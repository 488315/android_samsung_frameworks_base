package com.android.systemui.volume.util;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
