package com.android.systemui.util.kotlin;

import android.content.Context;

public final class UtilsKt {
    public static final int toDp(int i, Context context) {
        return (int) (i / context.getResources().getDisplayMetrics().density);
    }

    public static final int toPx(int i, Context context) {
        return (int) (i * context.getResources().getDisplayMetrics().density);
    }
}
