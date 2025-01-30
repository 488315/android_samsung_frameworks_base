package com.google.android.material.ripple;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.Color;
import androidx.core.graphics.ColorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RippleUtils {
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";
    public static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    public static final int[] FOCUSED_STATE_SET = {R.attr.state_focused};
    public static final int[] SELECTED_PRESSED_STATE_SET = {R.attr.state_selected, R.attr.state_pressed};
    public static final int[] SELECTED_STATE_SET = {R.attr.state_selected};
    static final String LOG_TAG = "RippleUtils";

    private RippleUtils() {
    }

    public static int getColorForState(ColorStateList colorStateList, int[] iArr) {
        int colorForState = colorStateList != null ? colorStateList.getColorForState(iArr, colorStateList.getDefaultColor()) : 0;
        return ColorUtils.setAlphaComponent(colorForState, Math.min(Color.alpha(colorForState) * 2, 255));
    }

    public static ColorStateList sanitizeRippleDrawableColor(ColorStateList colorStateList) {
        return colorStateList != null ? colorStateList : ColorStateList.valueOf(0);
    }

    public static boolean shouldDrawRippleCompat(int[] iArr) {
        boolean z = false;
        boolean z2 = false;
        for (int i : iArr) {
            if (i == 16842910) {
                z = true;
            } else if (i == 16842908 || i == 16842919 || i == 16843623) {
                z2 = true;
            }
        }
        return z && z2;
    }
}
