package com.android.wm.shell.windowdecor.common;

import android.content.Context;
import com.android.launcher3.icons.BaseIconFactory;

/* loaded from: classes3.dex */
public abstract class WindowDecorTaskResourceLoaderKt {
    public static final BaseIconFactory createIconFactory(int i, Context context) {
        return new BaseIconFactory(context, context.getResources().getDisplayMetrics().densityDpi, context.getResources().getDimensionPixelSize(i));
    }
}
