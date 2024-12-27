package com.android.systemui.volume.util;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ViewVisibilityUtil {
    public static final ViewVisibilityUtil INSTANCE = new ViewVisibilityUtil();

    private ViewVisibilityUtil() {
    }

    public static void setGone(View view) {
        view.setVisibility(8);
    }
}
