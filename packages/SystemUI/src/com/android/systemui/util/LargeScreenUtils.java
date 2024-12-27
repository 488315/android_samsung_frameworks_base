package com.android.systemui.util;

import android.content.res.Resources;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
