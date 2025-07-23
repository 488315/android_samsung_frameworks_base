package com.android.systemui.volume.util;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewVisibilityUtil {
    public static final ViewVisibilityUtil INSTANCE = new ViewVisibilityUtil();

    private ViewVisibilityUtil() {
    }

    public static void setGone(View view) {
        view.setVisibility(8);
    }
}
