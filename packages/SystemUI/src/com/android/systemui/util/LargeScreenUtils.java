package com.android.systemui.util;

import android.content.res.Resources;

/* loaded from: classes3.dex */
public final class LargeScreenUtils {
    public static final int $stable = 0;
    public static final LargeScreenUtils INSTANCE = new LargeScreenUtils();

    private LargeScreenUtils() {
    }

    public static final boolean shouldUseLargeScreenShadeHeader(Resources resources) {
        return true;
    }
}
