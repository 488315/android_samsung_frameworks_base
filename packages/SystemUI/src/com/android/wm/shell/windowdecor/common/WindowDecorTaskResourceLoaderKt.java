package com.android.wm.shell.windowdecor.common;

import android.content.Context;
import com.android.launcher3.icons.BaseIconFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class WindowDecorTaskResourceLoaderKt {
    public static final BaseIconFactory createIconFactory(int i, Context context) {
        return new BaseIconFactory(context, context.getResources().getDisplayMetrics().densityDpi, context.getResources().getDimensionPixelSize(i));
    }
}
