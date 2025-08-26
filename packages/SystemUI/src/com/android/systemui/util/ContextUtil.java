package com.android.systemui.util;

import android.app.ActivityManager;
import android.content.Context;

/* loaded from: classes3.dex */
public final class ContextUtil {
    public static final int $stable = 0;
    public static final ContextUtil INSTANCE = new ContextUtil();

    private ContextUtil() {
    }

    public final ActivityManager getActivityManager(Context context) {
        return (ActivityManager) context.getSystemService(ActivityManager.class);
    }
}
