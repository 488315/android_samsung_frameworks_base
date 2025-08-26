package com.android.internal.policy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.SemSystemProperties;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.internal.R;

/* loaded from: classes5.dex */
public final class SystemBarUtils {
    private static boolean STATUS_LAYOUT_HEIGHT = true;

    public static int getDesktopViewAppHeaderHeightId() {
        return R.dimen.desktop_view_default_header_height;
    }

    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight(context.getResources(), context.getDisplay().getCutout());
    }

    public static int getStatusBarHeight(Resources resources, DisplayCutout displayCutout) throws Resources.NotFoundException {
        if (STATUS_LAYOUT_HEIGHT) {
            return getStatusBarHeight(resources, displayCutout, resources.getConfiguration().orientation == 2);
        }
        return Math.max(displayCutout == null ? 0 : displayCutout.getSafeInsetTop(), resources.getDimensionPixelSize(R.dimen.status_bar_height_default) + (displayCutout != null ? displayCutout.getWaterfallInsets().top : 0));
    }

    public static int getStatusBarHeightForRotation(Context context, int i) {
        Insets waterfallInsets;
        Insets insets;
        Display display = context.getDisplay();
        int rotation = display.getRotation();
        DisplayCutout cutout = display.getCutout();
        if (STATUS_LAYOUT_HEIGHT) {
            boolean z = context.getResources().getConfiguration().semDisplayDeviceType == 0;
            boolean z2 = i == 1 || i == 3;
            if (SemSystemProperties.get("ro.product.name").startsWith("q7m") && z) {
                z2 = !z2;
            }
            return getStatusBarHeight(context.getResources(), cutout, z2);
        }
        display.getDisplayInfo(new DisplayInfo());
        int i2 = context.getResources().getDisplayMetrics().widthPixels;
        int i3 = context.getResources().getDisplayMetrics().heightPixels;
        if (cutout == null) {
            insets = Insets.NONE;
            waterfallInsets = Insets.NONE;
        } else {
            DisplayCutout rotated = cutout.getRotated(i2, i3, rotation, i);
            Insets insetsOf = Insets.of(rotated.getSafeInsets());
            waterfallInsets = rotated.getWaterfallInsets();
            insets = insetsOf;
        }
        return Math.max(insets.top, context.getResources().getDimensionPixelSize(R.dimen.status_bar_height_default) + waterfallInsets.top);
    }

    private static int getStatusBarHeight(Resources resources, DisplayCutout displayCutout, boolean z) {
        if (z) {
            return resources.getDimensionPixelSize(R.dimen.status_bar_height_landscape);
        }
        return Math.max(displayCutout == null ? 0 : displayCutout.getSafeInsetTop(), resources.getDimensionPixelSize(R.dimen.status_bar_height_portrait));
    }

    public static int getQuickQsOffsetHeight(Context context) {
        return Math.max(context.getResources().getDimensionPixelSize(R.dimen.quick_qs_offset_height), getStatusBarHeight(context));
    }

    public static int getTaskbarHeight(Resources resources) {
        return resources.getDimensionPixelSize(R.dimen.taskbar_frame_height);
    }

    public static int getDesktopViewAppHeaderHeightPx(Context context) {
        return context.getResources().getDimensionPixelSize(getDesktopViewAppHeaderHeightId());
    }
}
