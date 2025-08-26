package com.android.systemui.volume.util;

import android.view.View;

/* loaded from: classes3.dex */
public final class ViewVisibilityUtil {
    public static final ViewVisibilityUtil INSTANCE = new ViewVisibilityUtil();

    private ViewVisibilityUtil() {
    }

    public static void setGone(View view) {
        view.setVisibility(8);
    }
}
