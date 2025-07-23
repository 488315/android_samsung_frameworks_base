package com.android.systemui.volume.util;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
            return (isLandscape(context) ? context.getResources().getDisplayMetrics().heightPixels : context.getResources().getDisplayMetrics().widthPixels) > context.getResources().getDimensionPixelSize(R.dimen.volume_panel_screen_width_threshold);
        }
        return false;
    }
}
