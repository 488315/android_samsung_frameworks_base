package com.android.systemui.media.audiovisseekbar.utils;

import android.content.res.Resources;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DimensionUtilsKt {
    public static final int dpToPx(int i) {
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    public static final float dpToPx(float f) {
        return f * Resources.getSystem().getDisplayMetrics().density;
    }
}
