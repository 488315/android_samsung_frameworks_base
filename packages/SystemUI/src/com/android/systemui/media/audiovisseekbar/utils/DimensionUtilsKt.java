package com.android.systemui.media.audiovisseekbar.utils;

import android.content.res.Resources;

public abstract class DimensionUtilsKt {
    public static final float dpToPx(float f) {
        return f * Resources.getSystem().getDisplayMetrics().density;
    }
}
