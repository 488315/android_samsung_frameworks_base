package com.android.internal.policy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.internal.R;

/* loaded from: classes5.dex */
public final class SystemBarUtils {
    private static boolean STATUS_LAYOUT_HEIGHT = true;

    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight(context.getResources(), context.getDisplay().getCutout());
    }

    public static int getStatusBarHeight(Resources resources, DisplayCutout displayCutout) {
        if (STATUS_LAYOUT_HEIGHT) {
            return getStatusBarHeight(resources, displayCutout, resources.getConfiguration().orientation == 2);
        }
        return Math.max(displayCutout == null ? 0 : displayCutout.getSafeInsetTop(), resources.getDimensionPixelSize(R.dimen.status_bar_height_default) + (displayCutout != null ? displayCutout.getWaterfallInsets().top : 0));
    }

    public static int getStatusBarHeightForRotation(Context context, int targetRot) {
        Insets insets;
        Insets insets2;
        Display display = context.getDisplay();
        int rotation = display.getRotation();
        DisplayCutout cutout = display.getCutout();
        if (STATUS_LAYOUT_HEIGHT) {
            Resources resources = context.getResources();
            boolean z = true;
            if (targetRot != 1 && targetRot != 3) {
                z = false;
            }
            return getStatusBarHeight(resources, cutout, z);
        }
        DisplayInfo info = new DisplayInfo();
        display.getDisplayInfo(info);
        if (cutout == null) {
            insets = Insets.NONE;
            insets2 = Insets.NONE;
        } else {
            DisplayCutout rotated = cutout.getRotated(info.logicalWidth, info.logicalHeight, rotation, targetRot);
            Insets insets3 = Insets.of(rotated.getSafeInsets());
            Insets waterfallInsets = rotated.getWaterfallInsets();
            insets = insets3;
            insets2 = waterfallInsets;
        }
        int defaultSize = context.getResources().getDimensionPixelSize(R.dimen.status_bar_height_default);
        return Math.max(insets.top, insets2.top + defaultSize);
    }

    private static int getStatusBarHeight(Resources res, DisplayCutout cutout, boolean isLandscape) {
        if (isLandscape) {
            return res.getDimensionPixelSize(R.dimen.status_bar_height_landscape);
        }
        int safeInsetTop = cutout == null ? 0 : cutout.getSafeInsetTop();
        return Math.max(safeInsetTop, res.getDimensionPixelSize(R.dimen.status_bar_height_portrait));
    }

    public static int getQuickQsOffsetHeight(Context context) {
        int defaultSize = context.getResources().getDimensionPixelSize(R.dimen.quick_qs_offset_height);
        int statusBarHeight = getStatusBarHeight(context);
        return Math.max(defaultSize, statusBarHeight);
    }
}
