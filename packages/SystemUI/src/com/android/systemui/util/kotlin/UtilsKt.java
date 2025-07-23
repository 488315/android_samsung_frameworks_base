package com.android.systemui.util.kotlin;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UtilsKt {
    public static final int toDp(int i, Context context) {
        return (int) (i / context.getResources().getDisplayMetrics().density);
    }

    public static final int toPx(int i, Context context) {
        return (int) (i * context.getResources().getDisplayMetrics().density);
    }
}
