package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.ListEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationUtils {
    public static int getFontScaledHeight(int i, Context context) {
        return (int) (context.getResources().getDimensionPixelSize(i) * Math.max(1.0f, context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density));
    }

    public static int getFontScaledMarginHeight(int i, Context context) {
        return (int) (context.getResources().getDimensionPixelSize(i) * ((((context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density) - 1.0f) / 2.0f) + 1.0f));
    }

    public static float interpolate(float f, float f2, float f3) {
        return (f2 * f3) + ((1.0f - f3) * f);
    }

    public static int interpolateColors(float f, int i, int i2) {
        return Color.argb((int) interpolate(Color.alpha(i), Color.alpha(i2), f), (int) interpolate(Color.red(i), Color.red(i2), f), (int) interpolate(Color.green(i), Color.green(i2), f), (int) interpolate(Color.blue(i), Color.blue(i2), f));
    }

    public static boolean isGrayscale(ImageView imageView, ContrastColorUtil contrastColorUtil) {
        Object tag = imageView.getTag(R.id.icon_is_grayscale);
        if (tag != null) {
            return Boolean.TRUE.equals(tag);
        }
        boolean isGrayscaleIcon = contrastColorUtil.isGrayscaleIcon(imageView.getDrawable());
        imageView.setTag(R.id.icon_is_grayscale, Boolean.valueOf(isGrayscaleIcon));
        return isGrayscaleIcon;
    }

    public static String logKey(ListEntry listEntry) {
        return listEntry == null ? "null" : logKey(listEntry.getKey());
    }

    public static String logKey(String str) {
        if (str == null) {
            return "null";
        }
        return str.replace("\n", "");
    }
}
