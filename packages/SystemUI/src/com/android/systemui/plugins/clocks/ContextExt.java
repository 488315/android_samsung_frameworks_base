package com.android.systemui.plugins.clocks;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ContextExt {
    public static final int $stable = 0;
    public static final ContextExt INSTANCE = new ContextExt();

    private ContextExt() {
    }

    public final int getDimen(Context context, String str) {
        int identifier = context.getResources().getIdentifier(str, "dimen", context.getPackageName());
        if (identifier == 0) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(identifier);
    }

    public final int getId(Context context, String str) {
        return context.getPackageManager().getResourcesForApplication(context.getPackageName()).getIdentifier(str, "id", context.getPackageName());
    }
}
