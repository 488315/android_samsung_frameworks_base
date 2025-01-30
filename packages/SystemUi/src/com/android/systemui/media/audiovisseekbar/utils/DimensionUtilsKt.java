package com.android.systemui.media.audiovisseekbar.utils;

import android.content.res.Resources;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DimensionUtilsKt {
    public static final float dpToPx(float f) {
        return f * Resources.getSystem().getDisplayMetrics().density;
    }
}
