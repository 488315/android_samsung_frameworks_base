package com.android.systemui.plugins.clocks;

import android.content.Context;

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
