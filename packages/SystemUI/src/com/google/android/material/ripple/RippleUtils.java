package com.google.android.material.ripple;

import android.content.res.ColorStateList;

/* loaded from: classes4.dex */
public class RippleUtils {
    static final String LOG_TAG = "RippleUtils";
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";

    private RippleUtils() {
    }

    public static ColorStateList sanitizeRippleDrawableColor(ColorStateList colorStateList) {
        return colorStateList != null ? colorStateList : ColorStateList.valueOf(0);
    }
}
