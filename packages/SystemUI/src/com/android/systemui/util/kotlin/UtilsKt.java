package com.android.systemui.util.kotlin;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UtilsKt {
    public static final int toDp(int i, Context context) {
        return (int) (i / context.getResources().getDisplayMetrics().density);
    }

    public static final int toPx(int i, Context context) {
        return (int) (i * context.getResources().getDisplayMetrics().density);
    }
}
