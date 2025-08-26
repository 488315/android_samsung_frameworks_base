package com.android.systemui.media.audiovisseekbar.utils;

import android.content.res.Resources;

/* loaded from: classes2.dex */
public abstract class DimensionUtilsKt {
    public static final int dpToPx(int i) {
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    public static final float dpToPx(float f) {
        return f * Resources.getSystem().getDisplayMetrics().density;
    }
}
